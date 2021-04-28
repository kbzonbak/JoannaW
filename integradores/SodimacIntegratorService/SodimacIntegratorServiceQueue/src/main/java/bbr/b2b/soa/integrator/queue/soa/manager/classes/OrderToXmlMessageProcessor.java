package bbr.b2b.soa.integrator.queue.soa.manager.classes;

import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.b2blink.logistic.xml.OC_Interno.CargoDescuento;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.CargosGenerales;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Cliente;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Comprador;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.DescGenerales;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Detalles;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Detalles.Detalle;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Detalles.Detalle.CargosProd;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Detalles.Detalle.DescProducto;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.EdiData;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Locales;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Locales.Local;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Predistribuciones;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Predistribuciones.Predistribucion;
import bbr.b2b.b2blink.logistic.xml.SolicitudOrdenes.SolicitudOrdenes;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Seccion;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Vendedor;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.TipoCargoDescuento;
import bbr.b2b.soa.integrator.config.classes.ServiceConfiguration;
import bbr.b2b.soa.integrator.facade.entities.Client;
import bbr.b2b.soa.integrator.facade.entities.Order;
import bbr.b2b.soa.integrator.facade.entities.OrderDetail;
import bbr.b2b.soa.integrator.facade.entities.PreDeliveryDetail;
import bbr.b2b.soa.integrator.facade.entities.SoaState;
import bbr.b2b.soa.integrator.facade.entities.SoaStateType;
import bbr.b2b.soa.integrator.facade.entities.Vendor;
import bbr.b2b.soa.integrator.facade.repository.classes.ClientRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.OrderDetailRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.OrderRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.PreDeliveryDetailRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateTypeRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.VendorRepository;
import bbr.b2b.soa.integrator.queue.config.classes.JMSMessageService;
import bbr.b2b.soa.integrator.queue.utils.QueueDefinitions;

@Component
@Transactional
public class OrderToXmlMessageProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderToXmlMessageProcessor.class);	

	private static JAXBContext jc = null;
	
	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.OC_Interno");
		return jc;
	}
		
	@Autowired
	private ServiceConfiguration serviceConfiguration;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private PreDeliveryDetailRepository preDeliveryDetailRepository;
	
	@Autowired
	private ClientRepository clientRepository;
		
	@Autowired
	private SoaStateTypeRepository soaStateTypeRepository;
	
	@Autowired
	private SoaStateRepository soaStateRepository;
	
	@Autowired
	private VendorRepository companyRepository;	
	
	@Autowired
	private JMSMessageService messageService;	
	
	public void processMessage(SolicitudOrdenes message) {
		
		// MAPA DE DETALLES DE OC
		Map<Long, List<OrderDetail>> odMap = new HashMap<Long, List<OrderDetail>>();
		
		// MAPA DE PREDISTRIBUCIONES
		Map<Long, List<PreDeliveryDetail>> pddMap = new HashMap<Long, List<PreDeliveryDetail>>();
				
		// MAPA DE DATOS CLIENTE
		Map<Long, Client> clMap = new HashMap<Long, Client>();
				
		try {					
			
			// PROVEEDOR
			String vendorCode = message.getCodproveedor();
			
			Optional<Vendor> vendorOp = companyRepository.findByCode(vendorCode);
			
			// ESTADO NOTIFICADO
			Optional<SoaStateType> notifiedOp = soaStateTypeRepository.findByCode("NOTIFICADO");
			
			// ESTADO RECIBIDO ERROR
			Optional<SoaStateType> errorOp = soaStateTypeRepository.findByCode("RECIBIDO_ERROR");
			
			// ESTADO ENVIADO
			Optional<SoaStateType> sendedOp = soaStateTypeRepository.findByCode("ENVIADO");
			
			// FECHA DE ACTIVACIÓN
			LocalDateTime activationTime = null;
			
			try {
				activationTime = LocalDateTime.parse(message.getFechaActivacion(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
			} catch (DateTimeParseException e) {				
				LOGGER.info("El formato de la fecha de activación no es válido, yyyy-MM-dd HH:mm:ss z");								
			}			
						
			if (vendorOp.isPresent() && notifiedOp.isPresent() && sendedOp.isPresent() && errorOp.isPresent() && activationTime != null) {
				
				Vendor vendor = vendorOp.get();
				SoaStateType notified = notifiedOp.get();
				SoaStateType error = errorOp.get();
				SoaStateType sended = sendedOp.get();

				// ANALIZAMOS QUE CONSULTA DE OC SE REALIZARA EN BASE AL MENSAJE
				// requestType = 1 --> POR DEFECTO BUSCA OCS NOTIFICADAS Y RECIBIDAS CON ERROR
				// requestType = 2 --> BUSCA OCS POR ESTADO SOA ESPECIFICO
				// requestType = 3 --> BUSCA OCS POR NUMEROS 
				Integer requestType = getRequestTypeOfMessage(message);
				
				List<Order> orders = null;
								
				switch (requestType) {
					case 1:
						orders = orderRepository.getOrdersByVendorIdAndEmittedAndSoaStateTypeIds(vendor.getId(), activationTime.toLocalDate(), Arrays.asList(notified.getId(), error.getId()));
						break;
					case 2:
						orders = orderRepository.getOrdersByVendorIdAndEmittedAndSoaStateTypeIds(vendor.getId(), activationTime.toLocalDate(), Arrays.asList(notified.getId()));
						break;
					case 3:
						List<Long> ocNumbers = null;
												
						orders = orderRepository.getOrdersByNumbersAndVendorIdAndEmitted(ocNumbers, vendor.getId(), activationTime.toLocalDate());
						break;	
					default:
						break;
				}
				
				// BUSCAMOS LAS OC NOTIFICADAS PARA ESE PROVEEDOR Y POR FECHA DE EMISIÓN
				//List<Order> orders = orderRepository.findByCurrentSoaStateTypeIdAndVendorId(notified.getId(), vendor.getId());
				
				if (orders != null && !orders.isEmpty()) {
					
					for (Order order : orders) {
						
						// BUSCA LOS DETALLES
						List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
						
						if (orderDetails != null && !orderDetails.isEmpty()) {
							
							if (!odMap.containsKey(order.getId()))
								odMap.put(order.getId(), orderDetails);
						}	
						
						// BUSCA LAS PREDISTRIBUCIONES DE LOS DETALLES
						List<PreDeliveryDetail> predDetails = preDeliveryDetailRepository.findByOrderDetailOrderId(order.getId());
							
						if (predDetails != null && !predDetails.isEmpty()) {
							if (!pddMap.containsKey(order.getId()))
								pddMap.put(order.getId(), predDetails);									
						}							
						
						if (order.getVev()) {
							
							// BUSCA LOS DATOS DE CLIENTE
							List<Client> clients = clientRepository.findByOrderId(order.getId()); 
												
							if (!clMap.containsKey(order.getId()) && !clients.isEmpty()) {
								
								Optional<Client> clientOp = clients.stream().findFirst();
								
								clMap.put(order.getId(), clientOp.get());								
							}														
						}					
					}	
					
					// GENERAMOS LOS MENSAJES OC INTERNO
					for (Order order : orders) {
						
						String flowId = UUID.randomUUID().toString();	
						
						String ocMessage = getOcMessage(order, odMap.get(order.getId()), pddMap.get(order.getId()), clMap.get(order.getId()), vendor, flowId);	

						LOGGER.info("Mensaje OC Interno N° " + order.getOcNumber() + ": " + ocMessage);
						
						// ENVIA A LA COLA DE SOA						
						messageService.send(QueueDefinitions.Q_SOA_OUT, ocMessage);
						
						// ACTUALIZA EL ESTADO DE LA OC
						order.setCurrentSoaStateType(sended);
						order.setCurrentSoaStateTypeDate(LocalDateTime.now());
						orderRepository.save(order);
						
						// SE DEJA EN ESTADO POR NOTIFICAR
						SoaState state = new SoaState();
						state.setOrder(order);
						state.setSoaStateType(sended);
						state.setWhen(LocalDateTime.now());
						state.setFlowId(flowId);
						state.setComment("Enviado");
						
						soaStateRepository.save(state);							
					}					
				}				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private String getOcMessage(Order order, List<OrderDetail> odList, List<PreDeliveryDetail> pddList, Client cl, Vendor vendor, String flowId) {
		
		ObjectFactory objFactory = new ObjectFactory();		
		String result = "";
		
		try {				
			
			Client client = null;
			
			if (order.getVev())
				client = cl;			
			
			Orden qlistaOC = objFactory.createOrden();

			// NOMBRE PORTAL
			qlistaOC.setNombreportal(serviceConfiguration.getPortalName());

			// NUMERO DE OC
			qlistaOC.setNo(order.getOcNumber());

			// SI ES VEV (RESERVE NUMBER)
			qlistaOC.setNosolicitud(client != null ? client.getReserveNumber().toString() : "");

			// DIGITO VERIFICADOR
			int dv = order.getOcNumber().intValue() % 10; // Definición de RAG según documentación EDI
			qlistaOC.setDigitoVerificador(dv);
			
			// ESTADO OC
			qlistaOC.setEstadoOc(order.getOcState() != null && !order.getOcState().isEmpty() ? order.getOcState() : "");
			
			// ESTADO OC BBR
			qlistaOC.setEstadoOcBbr("LIBERADA");

			// TIPO DE OC
			// SI LA OC ES VeV
			if (order.getVev()) {
				
				if (order.getDeliveryTo().equalsIgnoreCase("Cliente"))
					qlistaOC.setTipoOc("VeV PD");
				else
					qlistaOC.setTipoOc("VeV CD");					
				
			}else {
				
				qlistaOC.setTipoOc(order.getOcType() != null ? order.getOcType() : "");
			}
			
			// TIPO DE OC BBR
			// SI LA OC ES VeV
			if (order.getVev()) {
				
				if (order.getDeliveryTo().equalsIgnoreCase("Cliente"))
					qlistaOC.setTipoOcBbr("VEV DDC");
				else
					qlistaOC.setTipoOcBbr("VEV DVR");					
				
			}else {				
				qlistaOC.setTipoOcBbr("REG");
			}
						
			// SECCION			
			Seccion seccion = new Seccion();
			seccion.setCodSeccion("");
			seccion.setSeccion("");
			qlistaOC.setSeccion(seccion);

			// FECHA DE EMISION
			LocalDate date = order.getEmittedDate();						
			XMLGregorianCalendar xmlgcal_emitted = DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());		
			qlistaOC.setFechaEmision(xmlgcal_emitted);

			// FECHA DE VIGENCIA
			date = order.getExpectedDate();						
			XMLGregorianCalendar xmlgcal_expected = DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());		
			qlistaOC.setFechaVigencia(xmlgcal_expected);		

			// FECHA DE VENCIMIENTO
			date = order.getExpirationDate();						
			XMLGregorianCalendar xmlgcal_expiration = DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());		
			qlistaOC.setFechaVto(xmlgcal_expiration);
			
			//FECHA DE COMPROMISO
			if (order.getVev())
				date = order.getCommitmentDeliveryDate();
			else
				date = order.getExpectedDate();
											
			XMLGregorianCalendar xmlgcal_delivery = DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());		
			qlistaOC.setFechaCompromiso(xmlgcal_delivery);

			// TOTAL DE LA OC
			qlistaOC.setTotal(order.getTotalGross());

			// CODIGO DE LOCAL DE ENTREGA
			if (order.getVev())
				qlistaOC.setCodLocalEntrega(getVeVLocalCode(order));
			else
				qlistaOC.setCodLocalEntrega(order.getEodLocalCode());				
			
						
			// FORMA DE PAGO
			qlistaOC.setFormaPago(order.getPaymentType());

			// COMENTARIOS
			qlistaOC.setComentarios("");

			// RESPONSABLE
			qlistaOC.setResponsable(order.getBuyer() != null && !order.getBuyer().isEmpty() ? order.getBuyer() : "");

			// COMPRADOR
			Comprador comprador = fillComprador(objFactory);
			qlistaOC.setComprador(comprador);

			// PROVEEDOR
			Vendedor vendedor = fillVendedor(objFactory, vendor);
			qlistaOC.setVendedor(vendedor);

			// CLIENTE
			if (order.getVev()) {
				Cliente cliente = fillCliente(objFactory, order, client);
				qlistaOC.setCliente(cliente);
			}
			
			// EDIDATA						
			String shipTo = "";
			// CASO ESPECIAL PARA LG
			if (vendor.getCode().equals(serviceConfiguration.getLgRut())) {				
				if (order.getVev()) {
					shipTo = getLGBuyerLocationCode(order, client.getRegion());			
					
				} else {
					shipTo = getLGBuyerLocationCode(order, order.getEodLocalCode());					
				}

			} else {
				
				if (order.getVev())
					shipTo = getVeVLocalCode(order);
				else				
					shipTo = order.getEodLocalCode();
			}

			// FLOW ID
			qlistaOC.setFlowid(flowId);		
			
			EdiData ediData = fillEdiData(objFactory, vendor, order, shipTo);
			qlistaOC.setEdiData(ediData);

			// LOCALES			
			Locales locales = fillLocales(objFactory, order, pddList);
			qlistaOC.setLocales(locales);

			// DESCUENTOS GENERALES
			DescGenerales descGrales = objFactory.createOrdenDescGenerales();
			List descgralsList = descGrales.getDescuento();
			CargoDescuento dscto = objFactory.createCargoDescuento();
			dscto.setCodigo("");
			dscto.setPorcentaje(0);
			dscto.setGlosa("");
			dscto.setTipo(TipoCargoDescuento.MONTO);
			dscto.setMonto(order.getTotalDiscount() != null ? order.getTotalDiscount().floatValue() : 0);
			descgralsList.add(dscto);
			qlistaOC.setDescGenerales(descGrales);

			// CARGOS GENERALES
			CargosGenerales cargosGrales = objFactory.createOrdenCargosGenerales();
			List cargosgralsList = cargosGrales.getCargo();
			CargoDescuento cargogral = objFactory.createCargoDescuento();
			cargogral.setCodigo("");
			cargogral.setPorcentaje(0);
			cargogral.setGlosa("");
			cargogral.setTipo(TipoCargoDescuento.MONTO);
			cargogral.setMonto(order.getTotalCharge() != null ? order.getTotalCharge().floatValue() : 0);
			cargosgralsList.add(cargogral);
			qlistaOC.setCargosGenerales(cargosGrales);

			// DETALLE DE OC
			Detalles detalles = fillDetalleOC(objFactory, order, odList, vendor, client);
			qlistaOC.setDetalles(detalles);

			// PREDISTRIBUCION
			Predistribuciones predistribuciones = fillPredistribucion(objFactory, order, pddList);
			qlistaOC.setPredistribuciones(predistribuciones);

			// OBTIENE EL XML FINAL					
			JAXBContext jc = getJC();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter sw = new StringWriter();
			m.marshal(qlistaOC, sw);
			result = sw.toString();			
						
			
		}catch (DatatypeConfigurationException e) {
			LOGGER.error("No se pudo convertir la fecha al formato requerido");
			e.printStackTrace();
		}catch (JAXBException e) {
			LOGGER.error("Error al crear mensaje de OC");
			e.printStackTrace();
		}		
		
		return result;		
	}
	
	private String getVeVLocalCode(Order order) {	
		String localCode = "";
		String desp = order.getDeliveryTo();	

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		int code = new BigInteger(1, md.digest(desp.getBytes())).intValue();
		code = Math.abs(code);		

		localCode = Integer.toString(code);
		return localCode;	
	}
	
	private String getVeVLocalGLN(Order order) {	
		String localCode = "";
		String desp = order.getDeliveryTo();	

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		localCode = new BigInteger(1, md.digest(desp.getBytes())).toString(16);
		return localCode;	
	}
	
	private String getLGBuyerLocationCode(Order order, String regioncliente) {

		String result = "";		

		String laFarfanaShipTo = "SHIP4449-S";
		String loEspejoShipTo = "SHIP0049";
		String laFarfanaCode = "214";		

		String VTAVRSTGOShipTo = "SHIP9954-S";
		String VTAVRSREG_OTRASShipTo = "SHIP9955-S";
		String stgoCode = "METROPOLITANA";

		if (order.getVev()) {

			if (regioncliente != null && regioncliente.toUpperCase().contains(stgoCode.toUpperCase())) {
				result = VTAVRSTGOShipTo;
			} else {
				result = VTAVRSREG_OTRASShipTo;
			}

		} else {
			if (regioncliente != null && regioncliente.toUpperCase().contains(laFarfanaCode.toUpperCase())) {
				result = laFarfanaShipTo;
			} else {
				result = loEspejoShipTo;
			}
		}

		return result;
	}
	
	
	private Comprador fillComprador(ObjectFactory objFactory) {
		Comprador comprador = objFactory.createOrdenComprador();
		
		comprador.setRut(serviceConfiguration.getBuyerCode());
		comprador.setRazonSocial(serviceConfiguration.getBuyerName());
		comprador.setUnidadNegocio(serviceConfiguration.getBuyerArea());			
		return comprador;
	}
	
	private Vendedor fillVendedor(ObjectFactory objFactory, Vendor vendor) {
		Vendedor vendedor = objFactory.createOrdenVendedor();

		vendedor.setRut(vendor.getCode());
		vendedor.setRazonSocial(vendor.getName());
		vendedor.setContacto("");
		vendedor.setDireccion("");
		vendedor.setTelefono("");
		return vendedor;
	}
	
	private Cliente fillCliente(ObjectFactory objFactory, Order order, Client client) {
		Cliente cliente = objFactory.createOrdenCliente();

		cliente.setNombre(client.getBuyerName());
		cliente.setRut(client.getBuyerDni());	
		cliente.setTelefono(client.getBuyerPhone());
		cliente.setDireccion(client.getReceiverAddress());
		cliente.setNumcalle(client.getStreet());
		cliente.setNumdepto(client.getHouseOffice());
		cliente.setNumcasa(order.getDeliveryTo());
		cliente.setRegion(client.getRegion());
		cliente.setComuna(client.getReceiverCommune());
		cliente.setCiudad(client.getReceiverCity());
		cliente.setComentario(client.getObservation());		
		return cliente;
	}
	
	private EdiData fillEdiData(ObjectFactory objFactory, Vendor vendor, Order order, String shipTo) {
		EdiData qedidata = objFactory.createOrdenEdiData();
		String gln = serviceConfiguration.getSiteCode();

		if (vendor.getCode().equals(serviceConfiguration.getLgRut())) {			
			qedidata.setSenderIdentification(gln);
			qedidata.setRecipientIdentification(vendor.getCode());
		} else {
			qedidata.setSenderIdentification(gln);
			qedidata.setRecipientIdentification(vendor.getGln());
		}

		qedidata.setCorrelativeVendor("");
		qedidata.setMessageReferenceNumber("");
		qedidata.setCountrycode("");
		qedidata.setBuyerCode(gln);
		qedidata.setBillToPartner(serviceConfiguration.getBillTo());		
		qedidata.setBuyerLocationCode(shipTo);
		return qedidata;
	}
	
	private Locales fillLocales(ObjectFactory objFactory, Order order, List<PreDeliveryDetail> pddList) {
		Locales locales = objFactory.createOrdenLocales();
		List<Local> localesList = locales.getLocal();
		Map<String, Local> localesMap = new HashMap<String, Local>();

		// BUSCA LAS PREDISTRIBUCIONES PARA LA OC		
		List<PreDeliveryDetail> predList = pddList;
		
		if (predList != null) {
			for (PreDeliveryDetail pred : predList) {
				
				String localCode = pred.getStoreNumber();
				
				if (!localesMap.containsKey(localCode)) {
					
					Local local = objFactory.createOrdenLocalesLocal();
					local.setCod(localCode);
					local.setNombre(pred.getStore());
					local.setDireccion("");
					local.setEan(localCode); 
					
					localesMap.put(localCode, local);
				}		
			}			
		}		
		
		// SE AGREGA EL LOCAL DE ENTREGA
		String dlLocalCode = order.getVev() ? getVeVLocalCode(order) : order.getEodLocalCode();
		
		if (!localesMap.containsKey(dlLocalCode)) {
			
			Local local = objFactory.createOrdenLocalesLocal();
			local.setCod(dlLocalCode);
			local.setNombre(order.getVev() ? order.getDeliveryTo() : order.getEodLocalDescription());
			local.setDireccion(order.getVev() ? "": order.getEodLocalAddress());
			local.setEan(order.getVev() ? getVeVLocalGLN(order) : order.getEodLocalCode()); 
			
			localesMap.put(local.getCod(), local);			
		}
		
		localesList.addAll(localesMap.values().stream().collect(Collectors.toList()));
		return locales;
	}
	
	private Detalles fillDetalleOC(ObjectFactory objFactory, Order order, List<OrderDetail> odList, Vendor vendor, Client client)  {
		Detalles detalles = objFactory.createOrdenDetalles();
		List<Detalle> detallesList = detalles.getDetalle();

		// BUSCA LOS DETALLES DE LA OC
		List<OrderDetail> details = odList;		
					
		for (OrderDetail detail : details) {
			
			Detalle detalle = objFactory.createOrdenDetallesDetalle();

			// CORRELATIVO
			detalle.setItem(detail.getPosition());

			// DESCRIPCIÓN UM
			if (order.getVev())
				detalle.setDescUmUnidad(client.getReserveNumber().toString());
			else if (vendor.getCode().equals(serviceConfiguration.getLgRut()))
				detalle.setDescUmUnidad("");
			else
				detalle.setDescUmUnidad(detail.getUmPack());

			// SKU
			detalle.setCodProdComp(detail.getSku());

			// CODIGO VENDEDOR
			detalle.setCodProdVendedor(""); // TODO EBO ESTE DATO NO HAY DE DONDE SACARLO (VENDOR-ITEM)

			// EAN13
			detalle.setEan13(detail.getUpc());

			// DESCRIPCION PRODUCTO
			detalle.setDescripcionProd(order.getVev() ? detail.getShortDescription() : detail.getLongDescription());

			//CODIGO EMPAQUE
			detalle.setCodEmpaque(detail.getCodePack());

			// DESCRIPCION DE EMPAQUE
			detalle.setDescEmpaque(detail.getUmPack());

			// PRODUCTO POR EMPAQUE
			detalle.setProdEmpaque(order.getVev() ? 1 : detail.getPackAmount().floatValue());

			// CANTIDAD DE EMPAQUE			
			detalle.setCantidadEmpaque(detail.getTotalUnits().intValue());

			// INNERPACK
			detalle.setInnerpack(1);

			// COSTO LISTA
			detalle.setCostoLista(detail.getUnitPrice());

			// COSTO FINAL
			detalle.setCostoFinal(detail.getUnitPrice());

			// PRECIO LISTA
			detalle.setPrecioLista(detail.getUnitPrice());
			
			//DESCUENTOS
			DescProducto descuentosType = objFactory.createOrdenDetallesDetalleDescProducto();
			List<CargoDescuento> descTypeList = descuentosType.getDescuento();
			CargoDescuento cargoDescuento = objFactory.createCargoDescuento();
			cargoDescuento.setCodigo("DE1");
			cargoDescuento.setPorcentaje(0);
			cargoDescuento.setGlosa("");
			cargoDescuento.setTipo(TipoCargoDescuento.MONTO);
			cargoDescuento.setMonto(detail.getAdditionalDiscount() != null ? detail.getAdditionalDiscount().floatValue() : 0);
			descTypeList.add(cargoDescuento);
			detalle.setDescProducto(descuentosType);

			// Cargos de los productos
			CargosProd cargosType = objFactory.createOrdenDetallesDetalleCargosProd();
			List<CargoDescuento> cargosTypeList = cargosType.getCargo();
			CargoDescuento cargo = objFactory.createCargoDescuento();
			cargo.setCodigo("CA1");
			cargo.setPorcentaje(0);
			cargo.setGlosa("");
			cargo.setTipo(TipoCargoDescuento.MONTO);
			cargo.setMonto(detail.getAdditionalCharge() != null ? detail.getAdditionalCharge().floatValue() : 0);
			cargosTypeList.add(cargo);
			detalle.setCargosProd(cargosType);

			detallesList.add(detalle);
		}
		
		return detalles;
	}
	
	private Predistribuciones fillPredistribucion(ObjectFactory objFactory, Order order, List<PreDeliveryDetail> pddList) {
		Predistribuciones predistType = objFactory.createOrdenPredistribuciones();
		List<Predistribucion> predisTypeList = predistType.getPredistribucion();

		if (pddList != null && !pddList.isEmpty()) {
			
			List<PreDeliveryDetail> predDetails = pddList;
			
			for (PreDeliveryDetail detail : predDetails) {
				Predistribucion detallepred = objFactory.createOrdenPredistribucionesPredistribucion();
				detallepred.setCodLocal(detail.getStoreNumber());
				detallepred.setSku(detail.getSku());
				detallepred.setCantidad(detail.getProductAmount().toString());
				predisTypeList.add(detallepred);
			}
		}

		return predistType;
	}	
	
	private Integer getRequestTypeOfMessage(SolicitudOrdenes message) {
		
		Integer requestType = 1;
		
		if (message.getEstadoSoa() != null && !message.getEstadoSoa().isEmpty())
			requestType = 2;			
		else if (message.getDocumentos() != null && message.getDocumentos().getDocumento() != null && !message.getDocumentos().getDocumento().isEmpty())
			requestType = 3;
		
		return requestType;		
	}
}
