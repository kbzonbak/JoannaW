package bbr.b2b.soa.integrator.queue.soa.manager.classes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.zip.GZIPOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.b2blink.commercial.xml.ReporteVentas.ObjectFactory;
import bbr.b2b.b2blink.commercial.xml.ReporteVentas.Ventas;
import bbr.b2b.b2blink.commercial.xml.ReporteVentas.Ventas.Comprador;
import bbr.b2b.b2blink.commercial.xml.ReporteVentas.Ventas.Vendedor;
import bbr.b2b.soa.integrator.config.classes.ServiceConfiguration;
import bbr.b2b.soa.integrator.facade.entities.Vendor;
import bbr.b2b.soa.integrator.facade.entities.SellOut;
import bbr.b2b.soa.integrator.facade.entities.SellOutType;
import bbr.b2b.soa.integrator.facade.entities.SoaState;
import bbr.b2b.soa.integrator.facade.entities.SoaStateType;
import bbr.b2b.soa.integrator.facade.repository.classes.VendorRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SellOutRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SellOutTypeRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateTypeRepository;
import bbr.b2b.soa.integrator.queue.config.classes.JMSMessageService;
import bbr.b2b.soa.integrator.queue.utils.QueueDefinitions;

@Component
@Transactional
public class SalesToXmlMessageProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesToXmlMessageProcessor.class);	

	private static JAXBContext jc = null;
	
	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.commercial.xml.ReporteVentas");
		return jc;
	}
		
	@Autowired
	private ServiceConfiguration serviceConfiguration;
	
	@Autowired
	private SellOutRepository sellOutRepository;
		
	@Autowired
	private SoaStateTypeRepository soaStateTypeRepository;
	
	@Autowired
	private SoaStateRepository soaStateRepository;
	
	@Autowired
	private VendorRepository companyRepository;	
	
	@Autowired
	private SellOutTypeRepository sellOutTypeRepository;
	
	@Autowired
	private JMSMessageService messageService;	
	
	public void processMessage(String vendorCode) {		
		
		try {					
			// PROVEEDOR
			Optional<Vendor> vendorOp = companyRepository.findByCode(vendorCode);
			
			// ESTADO NOTIFICADO
			Optional<SoaStateType> notifiedOp = soaStateTypeRepository.findByCode("NOTIFICADO");
			
			// ESTADO ENVIADO
			Optional<SoaStateType> sendedOp = soaStateTypeRepository.findByCode("ENVIADO");
			
			// TIPO SELLOUT VENTAS
			Optional<SellOutType> salesTypeOp = sellOutTypeRepository.findByCode("VTA");			
			
			if (vendorOp.isPresent() && notifiedOp.isPresent() && sendedOp.isPresent() && salesTypeOp.isPresent()) {
				
				Vendor vendor = vendorOp.get();
				SoaStateType notified = notifiedOp.get();
				SoaStateType sended = sendedOp.get();
				SellOutType salesType = salesTypeOp.get(); 

				// BUSCAMOS LAS VENTAS
				List<SellOut> sellOuts = sellOutRepository.findBySoaStateTypeIdAndSellOutTypeIdAndVendorId(notified.getId(), salesType.getId(), vendor.getId());
				
				if (sellOuts != null && !sellOuts.isEmpty()) {
					
					for (SellOut sellOut : sellOuts) {
						
						String fileMessage = "";
						
						// BUSCAMOS ARCHIVO SEGÚN REFERENCIA
						Path path = Paths.get(sellOut.getPath());
						
						if (path != null && Files.exists(path)) {
							
							try {
								fileMessage = Files.readString(path);
								
								String salesMessage = getSalesMessage(fileMessage, vendor);	

								LOGGER.info("Mensaje de Ventas: " + salesMessage);
								
								// ENVIA A LA COLA DE SOA						
								messageService.send(QueueDefinitions.Q_SOA_OUT, salesMessage);
								
								// ACTUALIZA EL ESTADO DEL SELLOUT
								sellOut.setSoaStateType(sended);
								sellOutRepository.save(sellOut);
								
								// SE DEJA EN ESTADO POR NOTIFICAR
								SoaState state = new SoaState();
								state.setSellOut(sellOut);
								state.setSoaStateType(sended);
								state.setWhen(LocalDateTime.now());
								state.setComment("Enviado");
								
								soaStateRepository.save(state);									
								
							}catch (IOException e) {
								e.printStackTrace();
								LOGGER.error("Error al obtener archivo de Ventas: " + e.getMessage());
							}
							
						}else {
							LOGGER.info("Archivo de Ventas no existe en ruta especificada: " + sellOut.getPath());
						}													
					}									
				}	
				else
					LOGGER.info("No hay ventas notificadas para el proveedor : " + vendorCode);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private String getSalesMessage(String message, Vendor vendor) {
		
		ObjectFactory objFactory = new ObjectFactory();		
		String result = "";
		
		try {
			
			Ventas ventasrequest = objFactory.createVentas();
			ventasrequest.setNombreportal(serviceConfiguration.getPortalName());

			Comprador comprador = objFactory.createVentasComprador();
			comprador.setUnidadNegocio("BuyerBussinessArea");
			comprador.setRut(serviceConfiguration.getBuyerCode());
			comprador.setRazonSocial(serviceConfiguration.getBuyerName());
			ventasrequest.setComprador(comprador);

			Vendedor vendedor = objFactory.createVentasVendedor();
			vendedor.setRut(vendor.getCode());
			vendedor.setRazonSocial(vendor.getName());
			ventasrequest.setVendedor(vendedor);

			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			GZIPOutputStream gzipstream = new GZIPOutputStream(outstream);
			
			byte[] databytes = message.getBytes();
			gzipstream.write(databytes);
			gzipstream.close();
			
			String data_compressed = Base64.getEncoder().encodeToString(outstream.toByteArray());
			ventasrequest.setData(data_compressed);

			// OBTIENE EL XML FINAL					
			JAXBContext jc = getJC();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter sw = new StringWriter();
			m.marshal(ventasrequest, sw);
			result = sw.toString();			
						
			
		}catch (JAXBException | IOException e) {
			LOGGER.error("Error al crear mensaje de Ventas");
			e.printStackTrace();
		}		
		
		return result;		
	}	
}
