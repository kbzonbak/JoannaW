package bbr.b2b.logistic.soa.msgb2btosoa;

import java.io.StringWriter;
import java.net.URL;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.customer.classes.BuyerServerLocal;
import bbr.b2b.logistic.customer.classes.BuyerVendorServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.VendorServerLocal;
import bbr.b2b.logistic.customer.data.dto.StockDTO;
import bbr.b2b.logistic.customer.data.wrappers.BuyerVendorW;
import bbr.b2b.logistic.customer.data.wrappers.BuyerW;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.soa.stock.UpdateStockFalabellaLocal;
import bbr.b2b.logistic.soa.stock.UpdateStockRipleyLocal;
import bbr.b2b.logistic.soa.stock.UpdateStockSodimacLocal;
import bbr.b2b.logistic.soa.stock.UpdateStockWalmartLocal;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.esb.services.webservices.facade.InitParamCSDTO;
import bbr.esb.services.webservices.facade.ServiceManagerServer;
import bbr.esb.services.webservices.facade.ServiceManagerServerService;

@Stateless(name = "handlers/XmlToInventarioProveedor")
public class XmlToInventarioProveedor implements XmlToInventarioProveedorLocal {

	private static JAXBContext jc = null;

	private static Logger logger = Logger.getLogger("SOALog");

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.RespuestaTicket");
		return jc;
	}

	@EJB
	VendorServerLocal vendorServerLocal;

	@EJB
	BuyerServerLocal buyerServerLocal;

	@EJB
	BuyerVendorServerLocal buyerVendorServerLocal;

	@EJB
	SoaStateTypeServerLocal soastatetypeserver;

	@EJB
	SchedulerManagerLocal schedulermanager;
	
	@EJB
	UpdateStockRipleyLocal ripleyprocess;
	
	@EJB
	UpdateStockWalmartLocal walmartprocess;
	
	@EJB
	UpdateStockSodimacLocal sodimacprocess;
	
	@EJB
	UpdateStockFalabellaLocal falabellaprocess;

	public void processMessage(Long ticketNumber, InventarioProveedor inventario) throws Exception {
		BuyerW buyerW = null;
		VendorW vendor = null;
		String codProveedor = inventario.getVendedor().getCodigo().trim();
		try {
			buyerW = buyerServerLocal.getByPropertyAsSingleResult("code", inventario.getComprador().getRut());
		} catch (OperationFailedException ex) {
			throw new OperationFailedException("Código de comprador " + inventario.getNombreportal() + " no existe.");
		}
		PropertyInfoDTO[] propertiesVendor = new PropertyInfoDTO[2];
		propertiesVendor[0] = new PropertyInfoDTO("id.buyerid", "buyer_id", buyerW.getId());
		propertiesVendor[1] = new PropertyInfoDTO("id.code", "code", codProveedor);
		List<BuyerVendorW> buyerVendorListW = buyerVendorServerLocal.getByProperties(propertiesVendor);
		
		if (buyerVendorListW.size() > 0) {
			vendor = vendorServerLocal.getById(buyerVendorListW.get(0).getVendorid());
		} else {
			throw new OperationFailedException("Código de vendedor " + codProveedor + " no existe.");
		}

		logger.info("Ejecutando actualización de stock para el proveedor " + vendor.getName() + "Portal: "+ inventario.getNombreportal());
		
		String endpoint = B2BSystemPropertiesUtil.getProperty("WSSOA");
		ServiceManagerServer serviceClient = new ServiceManagerServerService(new URL(endpoint)).getServiceManagerServerPort();
		InitParamCSDTO response = serviceClient.getCredentialsBySiteServiceAndCompany(inventario.getNombreportal(),"AST", vendor.getCode());
		
		if (response == null) {
			throw new OperationFailedException("No se encontraron datos para el portal: " + inventario.getNombreportal()
					+ " del proveedor: " + vendor.getCode());
		} else {
			if (response.getCredenciales() == null || response.getCredenciales().isEmpty()) {
				throw new OperationFailedException("portal: " + inventario.getNombreportal() + " del proveedor: "
						+ vendor.getCode() + " no posee credenciales");
			}
		}
		String credentials = response.getCredenciales();
		StockDTO stockdto = new StockDTO();
		
		try{
			switch (inventario.getNombreportal()) {
			case "CL1201":
				stockdto = ripleyprocess.StockProcessRipley(inventario, vendor, credentials);
				setResponse(stockdto, ticketNumber, inventario);
				break;
			case "CL0701":
				stockdto = walmartprocess.StockProcessWalmart(inventario, vendor, credentials);
				setResponse(stockdto, ticketNumber, inventario);
				break;
			case "CL1001":
				stockdto = sodimacprocess.StockProcessSodimac(inventario, vendor, credentials);
				setResponse(stockdto, ticketNumber, inventario);
				break;
			case "CL1301":
				stockdto = falabellaprocess.StockProcessFalabella(inventario, vendor, credentials);
				setResponse(stockdto, ticketNumber, inventario);
				break;
	
			default:
				break;
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Error al procesar mensaje de inventario, generando ticket de error");
			BaseResultDTO[] errordetail = new BaseResultDTO[1];
			errordetail[0].setStatuscode("-1");
			errordetail[0].setStatusmessage(e.getMessage());
			sendTicketResponse(inventario.getVendedor().getCodigo(), ticketNumber, inventario.getNombreportal(), "-1", "Error al procesar mensaje de inventario", "", errordetail);
		}
	}
	
	private void setResponse(StockDTO stockdto, Long ticketNumber, InventarioProveedor inventario){
		BaseResultDTO[] detalle = stockdto.getDetalles();
		String status = stockdto.getStatuscode();
		String statusMessage = stockdto.getStatusmessage();
		
		sendTicketResponse(inventario.getVendedor().getCodigo(), ticketNumber, inventario.getNombreportal(), status,
				statusMessage, "", detalle);
	}
	
	private void sendTicketResponse(String vendorCode, Long ticketNumber, String codigoportal, String status,
			String statusMessage, String url, BaseResultDTO[] detalle) {
		// RESPUESTA HACIA SOA
		ObjectFactory objFactory = new ObjectFactory();
		RespuestaTicket qrespuestaticket = objFactory.createRespuestaTicket();
		qrespuestaticket.setTicketnumber(ticketNumber != null ? ticketNumber : 0);
		qrespuestaticket.setCodproveedor(vendorCode);
		qrespuestaticket.setNombreportal(codigoportal);
		qrespuestaticket.setServicename("AST");
		qrespuestaticket.setEstadoticket(status);
		qrespuestaticket.setDescripcion(statusMessage);
		qrespuestaticket.setUrl(url);

		// se completan detalles
		bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles detallesticket = objFactory
				.createRespuestaTicketDetalles();
		List<bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles.Detalle> detalleticketlist = detallesticket
				.getDetalle();
		if (detalle != null) {
			BaseResultDTO[] resultserror = detalle;

			// se agregan los resultados de WS
			if (resultserror != null) {
				for (int i = 0; i < resultserror.length; i++) {
					bbr.b2b.b2blink.logistic.xml.RespuestaTicket.RespuestaTicket.Detalles.Detalle detalleticket = objFactory
							.createRespuestaTicketDetallesDetalle();
					detalleticket.setTipo("linea");
					detalleticket.setCodigo(resultserror[i].getStatuscode());
					detalleticket.setEstado("");
					detalleticket.setDescripcion(resultserror[i].getStatusmessage());
					detalleticketlist.add(detalleticket);
				}
			}
			qrespuestaticket.setDetalles(detallesticket);
		}

		try {
			// Obtiene string XML para enviarlo a la cola
			JAXBContext jc = getJC();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(qrespuestaticket, sw);
			String result = sw.toString();

			// RESPALDA MENSAJE
			// doBackUpMessage(result,
			// String.valueOf(qrespuestaticket.getTicketnumber()),
			// "RESPUESTATICKET");
			System.out.println(result);
			try {
				schedulermanager.doAddMessageQueue("jboss/qcf_soa", "jboss/activemq/queue/q_esbgrl", "RespuestaTicket",
						String.valueOf(ticketNumber), result);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}