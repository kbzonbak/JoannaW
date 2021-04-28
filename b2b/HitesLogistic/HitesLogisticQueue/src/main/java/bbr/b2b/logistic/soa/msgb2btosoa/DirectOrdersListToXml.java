package bbr.b2b.logistic.soa.msgb2btosoa;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;

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
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Seccion;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Vendedor;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.buyorders.classes.ClientServerLocal;
import bbr.b2b.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.buyorders.classes.ResponsableServerLocal;
import bbr.b2b.logistic.buyorders.classes.RetailerServerLocal;
import bbr.b2b.logistic.buyorders.classes.SectionServerLocal;
import bbr.b2b.logistic.buyorders.data.wrappers.ClientW;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.logistic.buyorders.data.wrappers.ResponsableW;
import bbr.b2b.logistic.buyorders.data.wrappers.RetailerW;
import bbr.b2b.logistic.buyorders.data.wrappers.SectionW;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.ddcdeliveries.classes.DdcDeliveryServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderDetailServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderStateServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderStateTypeServerLocal;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderDetailW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderStateTypeW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderStateW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderW;
import bbr.b2b.logistic.items.classes.VendorItemServerLocal;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.logistic.report.classes.VendorItemDataDTO;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.soa.classes.SOAStateServerLocal;
import bbr.b2b.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.logistic.soa.data.classes.SOAStateTypeW;
import bbr.b2b.logistic.soa.data.classes.SOAStateW;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.logistic.utils.BackUpUtils;
import bbr.b2b.logistic.utils.MsgRecoveryServices;
import bbr.b2b.logistic.utils.OrderTypeBBRUtils;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;


@Stateless(name = "handlers/DirectOrdersListToXml")
public class DirectOrdersListToXml implements DirectOrdersListToXmlLocal {

	private static JAXBContext jc = null;

	private static Logger logger = Logger.getLogger("SOALog");

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.OC_Interno");
		return jc;
	}
		
	@Resource
	private ManagedExecutorService executor;
	
	@EJB
	VendorServerLocal vendorserver = null;

	@EJB
	LocationServerLocal locationserver = null;
	
	@EJB
	DdcOrderDetailServerLocal directorderdetailserver = null;
	
	@EJB
	DdcOrderStateTypeServerLocal directorderstatetypeserver = null;
	
	@EJB
	DdcOrderStateServerLocal directorderstateserver = null;
	
	@EJB
	VendorItemServerLocal vendoritemserver = null;
	
	@EJB
	ResponsableServerLocal responsableserver = null;
	
	@EJB
	OrderServerLocal orderserver;
	
	@EJB
	ClientServerLocal clientserver;
	
	@EJB
	DdcDeliveryServerLocal deliveryserver;
	
	@EJB
	SOAStateTypeServerLocal soastatetypeserver;
	
	@EJB
	DdcOrderServerLocal directorderserver = null;
	
	@EJB
	SOAStateServerLocal directordersoastateserver;
	
	@EJB
	SchedulerManagerLocal schedulermanager;
	
	@EJB
	OrderTypeServerLocal ordertypeserver;
	
	@EJB
	SectionServerLocal sectionserver;
	
	@EJB
	RetailerServerLocal retailerserver;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@TransactionTimeout(1800)
	public void processMessage(String vendorCode, boolean acceptorders) throws ServiceException {
		
		
		String nombreportal = B2BSystemPropertiesUtil.getProperty("NombrePortal");		
		
		// Obtener proveedor en base al rut
		VendorW[] vendors = vendorserver.getByPropertyAsArray("code", vendorCode);
		
		if (vendors == null || vendors.length == 0)
			throw new NotFoundException("No se pudo encontrar el proveedor con RUT " + vendorCode);
		
		VendorW vendor = vendors[0];
		
		//Estados de Orden
		// - Liberadas
		DdcOrderStateTypeW releasedState = directorderstatetypeserver.getByPropertyAsSingleResult("code", "LIBERADA");
		// - Aceptadas
		DdcOrderStateTypeW acceptedState = directorderstatetypeserver.getByPropertyAsSingleResult("code", "ACEPTADA");
		
		// Estado SOA Notificado
		SOAStateTypeW soaNotfSt = soastatetypeserver.getByPropertyAsSingleResult("code", "NOTIFICADO");
		// Estado SOA Enviado
		SOAStateTypeW soaEnvSt = soastatetypeserver.getByPropertyAsSingleResult("code", "ENVIADO");
		
		// Se obtienen las OC con el flag de env�o a SOA en falso, para el proveedor y el comprador. Adem�s,
		LocalDateTime since = LocalDateTime.now().minusDays(5);
		
		// Buscar las OC no enviadas a SOA
		DdcOrderW[] orderstosend = directorderserver.getOrdersToSendSoa(vendor.getId(), since, soaNotfSt.getId());
		
		if ((orderstosend == null || orderstosend.length == 0)) {
			logger.debug("No se encontraron ordenes directas para enviar a SOA del proveedor RUT: " + vendorCode);
			return;
		}
		
		// Mapa de ordenes por ID
		Map<Long, DdcOrderW> ordersMap = new HashMap<Long, DdcOrderW>();
		// Mapa de detalle de ordenes por orden
		Map<Long, DdcOrderDetailW[]> orderdetailsMap = new HashMap<Long, DdcOrderDetailW[]>();
		// Mapa de productos por id (ID, VendorItemDataW)
		Map<Long, VendorItemDataDTO> itemsMap = new HashMap<Long, VendorItemDataDTO>();
		// Mapa de locales de despacho por id (Long, LocationW)
		Map<Long, LocationW> predeliverylocationsMap = new HashMap<Long, LocationW>();

		// Se recorren las ordenes liberadas para almacenarlas en un mapa
		for (DdcOrderW order : orderstosend) {
			ordersMap.put(order.getId(), order); 
		}

		// Se itera por mapa que contiene las ordenes para preparaci�n de datos
		for (DdcOrderW order : ordersMap.values()){
			// Se buscan los productos asociados a la OC
			VendorItemDataDTO[] vendoritems = vendoritemserver.getVendorItemDataofDirectOrder(order.getId());
			if (vendoritems == null || vendoritems.length == 0)
				throw new OperationFailedException("Error al buscar productos de orden");

			for (VendorItemDataDTO vendoritem : vendoritems) {
				itemsMap.put(vendoritem.getId(), vendoritem);
			}
			
			// Se obtiene el detalle de la OC
			DdcOrderDetailW[] details = directorderdetailserver.getByPropertyAsArrayOrdered("ddcorder.id", order.getId(), new OrderCriteriaDTO("ddcorder.id",true), new OrderCriteriaDTO("item.id",true));
				
			if (details == null || details.length == 0)
				throw new OperationFailedException("Error al buscar detalles de ordenes");
			
			// Se agrega un elemento al mapa de detalles
			orderdetailsMap.put(order.getId(), details);


		}
		
		// SE CONSTRUYE EL XML
		ObjectFactory objFactory = new ObjectFactory();

		for (DdcOrderW order : ordersMap.values()) {
			try {
				Orden qlistaOC = objFactory.createOrden();

				// Nombre Portal
				qlistaOC.setNombreportal(nombreportal);

				// Número de OC
				qlistaOC.setNo(order.getNumber());
				
				qlistaOC.setNosolicitud(order.getReferencenumber());

				// Dígito Verificador
				int dv = order.getNumber().intValue() % 10; // Definición de RAG según documentación EDI
				qlistaOC.setDigitoVerificador(dv);

				// Estado de OC
				DdcOrderStateTypeW ordertate = directorderstatetypeserver.getById(order.getCurrentstatetypeid())  ; 
				if (order.getCurrentstatetypeid().equals(releasedState.getId()))
					qlistaOC.setEstadoOc(releasedState.getName());
				else{
					qlistaOC.setEstadoOc(ordertate.getName());
				}

				// Tipo de OC Fijo
				OrderTypeW octype = ordertypeserver.getById(order.getOrdertypeid());
				qlistaOC.setTipoOc(octype.getDescription());

				//tipo de oc BBR
				qlistaOC.setTipoOcBbr(OrderTypeBBRUtils.getInstance().getOrderTypeBBRByCode(octype.getCode()));
				
				// Sección
				Seccion section = new Seccion();
				if(order.getSectionid() != null && !order.getSectionid().equals("") && order.getSectionid() > 0){
					SectionW sectionw = sectionserver.getById(order.getSectionid());
					section.setCodSeccion(sectionw.getCode());
					section.setSeccion(sectionw.getName());
				}else{
					section.setCodSeccion("");
					section.setSeccion("");
				}
				qlistaOC.setSeccion(section);

				Locale locale = new Locale("es", "CL");
				GregorianCalendar gcal = new GregorianCalendar(locale);
				
				// // Fecha de Emisión
				try {
					gcal = GregorianCalendar.from(order.getEmitted().atZone(ZoneId.systemDefault()));
					XMLGregorianCalendar xmlgcal_emitted;
					xmlgcal_emitted = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					qlistaOC.setFechaEmision(xmlgcal_emitted);
				} catch (DatatypeConfigurationException e) {
					e.printStackTrace();
				}
				// // Fecha de Vigencia
				try {
					gcal = GregorianCalendar.from(order.getCurrentdeliverydate().atZone(ZoneId.systemDefault()));//getvalid?????
					XMLGregorianCalendar xmlgcal_delivery;
					xmlgcal_delivery = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					qlistaOC.setFechaVigencia(xmlgcal_delivery);
				} catch (DatatypeConfigurationException e) {
					e.printStackTrace();
				}
				// Fecha de Vencimiento
				try {
					gcal = GregorianCalendar.from(order.getExpiration() == null ? LocalDateTime.now().atZone(ZoneId.systemDefault()) : order.getExpiration().atZone(ZoneId.systemDefault()));
					XMLGregorianCalendar xmlgcal_expiration;
					xmlgcal_expiration = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					qlistaOC.setFechaVto(xmlgcal_expiration);
				} catch (DatatypeConfigurationException e) {
					e.printStackTrace();
				}
				
				// Fecha de Compromiso
				try {
					gcal = GregorianCalendar.from(order.getOriginaldeliverydate() == null ? LocalDateTime.now().atZone(ZoneId.systemDefault()) : order.getExpiration().atZone(ZoneId.systemDefault()));
					XMLGregorianCalendar xmlgcal_originaldelivery;
					xmlgcal_originaldelivery = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					qlistaOC.setFechaCompromiso(xmlgcal_originaldelivery);
				} catch (DatatypeConfigurationException e) {
					e.printStackTrace();
				}
				
				// Total de la OC
				qlistaOC.setTotal(order.getTotalneed());

				// Codigo Local de entrega Verificar NO tiene
				qlistaOC.setCodLocalEntrega(""); 

				// Forma de pago
				qlistaOC.setFormaPago("");

				// Comentarios
				String comment = order.getComment() != null && order.getComment().trim().length() > 0 ? order.getComment() : "";
				qlistaOC.setComentarios(comment);

				// Responsable
				ResponsableW responsable = responsableserver.getById(order.getResponsableid());
				qlistaOC.setResponsable(responsable.getName());
				
				
				RetailerW retailer = retailerserver.getById(1L);
				// Informaci�n EDI
				EdiData ediData = setEdiData(objFactory, vendor, order, retailer);
				qlistaOC.setEdiData(ediData);

				// Comprador
				
				Comprador comprador = setCompradors(objFactory, 1L);
				qlistaOC.setComprador(comprador);

				// Vendedor
				Vendedor vendedor = setVendedor(objFactory, order.getVendorid());
				qlistaOC.setVendedor(vendedor);

				// Cliente
				// EBO 20-07-2015: Se agrega cliente si la oc 
				if (order.getClientid() != null && order.getClientid() > 0){
					Cliente cliente = setCliente(objFactory, order.getClientid());
					qlistaOC.setCliente(cliente);
				}		

				// Locales  Verificar si esto va
				Locales locales = setLocalenBlanco(objFactory);
				qlistaOC.setLocales (locales);

				// Descuentos generales
				DescGenerales descGrales = objFactory.createOrdenDescGenerales();
//				List descgralsList = descGrales.getDescuento();
//				CargosProd dscto = objFactory.createOrdenDetallesDetalleCargosProd();
				// dscto.setGlosa("descuento");
				// dscto.setTipo("tipo desc");
				// dscto.setValor(new Float(0).floatValue());
				// descgralsList.add(dscto);
				qlistaOC.setDescGenerales(descGrales);

				// Cargos generales
				CargosGenerales cargosGrales = objFactory.createOrdenCargosGenerales();
//				List cargosgralsList = cargosGrales.getCargo();
//				CargosProd cargogral = objFactory.createOrdenDetallesDetalleCargosProd();
				// cargogral.setGlosa("cargo");
				// cargogral.setTipo("tipo cargo");
				// cargogral.setValor(new Float(0).floatValue());
				// cargosgralsList.add(cargogral);
				qlistaOC.setCargosGenerales(cargosGrales);

				// Detalle de OC
				Detalles detalles = setDetalleOC(objFactory, order.getId(), orderdetailsMap, itemsMap);
				qlistaOC.setDetalles(detalles);

				// Predistribuci�n de OC
				Predistribuciones predistribuciones = objFactory.createOrdenPredistribuciones(); //setPredistribucion(objFactory, order.getId(), predetaildataMap, predeliverylocationsMap, itemsMap);
				qlistaOC.setPredistribuciones(predistribuciones);

				// Obtiene string XML para enviarlo a la cola
				JAXBContext jc = getJC();
				Marshaller m = jc.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter sw = new StringWriter();
				m.marshal(qlistaOC, sw);
				String result = sw.toString();
				LocalDateTime now = LocalDateTime.now();
			
				// RESPALDA MENSAJE			
				doBackUpMessage(result, order.getNumber().toString(), "SOA");

				try {			
					schedulermanager.doAddMessageQueue("qcf_soa", "q_esbgrl" , "EnviadoOcSoa", String.valueOf(order.getNumber())+"_SOA",  String.valueOf(order.getNumber()), result, now);
				} catch (Exception ex) {
					// Si ocurri� un error al enviar el archivo, se graba el mensaje para reencolarlo
					MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
					String msgtype = LogisticConstants.getInstance().getBUSINESSAREA() + LogisticConstants.getInstance().getCOUNTRYCODE() + "_SOA_" + order.getNumber();
					try {
						msgRecoveryServices.saveMsgToFile(msgtype, result, ex);
					} catch (Exception e1) {
						logger.debug(e1.getLocalizedMessage());
					}
				}
			}catch (JAXBException e) {
				e.printStackTrace();
				throw new OperationFailedException("Error al construir mensaje", e);
			}			
		}

		// Si no hay que aceptar, retorna el control
		if (!acceptorders)
			return;

		// Luego de crear el listado para SOA, las ordenes en cuesti�n se deben marcar como enviadas
		// y aceptar en caso que no lo est�n
		boolean accept = false;
		for (DdcOrderW order : ordersMap.values()) {
			LocalDateTime now = LocalDateTime.now();
			accept = false;

			// Se marca la OC como enviada a SOA			
			order.setCurrentsoastatetypedate(now);
			order.setCurrentsoastatetypeid(soaEnvSt.getId());
			
			SOAStateW soastate = new SOAStateW();
			soastate.setOrderid(order.getId());
			soastate.setSoastatetypeid(soaEnvSt.getId());
			soastate.setWhen(now);
			
			soastate = directordersoastateserver.addSOAState(soastate);					
			
			if (!order.getCurrentstatetypeid().equals(acceptedState.getId())) {
				order.setCurrentstatetypeid(acceptedState.getId());
				order.setCurrentstatetypedate(now);
				accept = true;
			}
			
			order = directorderserver.updateDdcOrder(order);			

			if (accept) {
				DdcOrderStateW orderstate = new DdcOrderStateW();
				orderstate.setDdcorderid(order.getId());
				orderstate.setDdcorderstatetypeid(acceptedState.getId());
				orderstate.setWhen(now);
				
				orderstate = directorderstateserver.addDdcOrderState(orderstate);
				
			}
			logger.info("BBR SOA: Se ha enviado a SOA la orden directa n�mero " + order.getNumber());
		}
		ordersMap.clear();
		orderdetailsMap.clear();
		itemsMap.clear();
		predeliverylocationsMap.clear();
	}

	private Comprador setCompradors(ObjectFactory objFactory, Long retailerid) throws NotFoundException, OperationFailedException {
		Comprador comprador = objFactory.createOrdenComprador();
		
		RetailerW retailer = retailerserver.getById(retailerid);
		
		comprador.setRut(retailer.getDni());
		comprador.setRazonSocial(retailer.getDescription());
		comprador.setUnidadNegocio(LogisticConstants.getInstance().getBUSINESSAREA());
		return comprador;
	}

	private Detalles setDetalleOC(ObjectFactory objFactory, Long orderid, Map<Long, DdcOrderDetailW[]> orderdetailsMap, Map<Long, VendorItemDataDTO> itemsMap) throws NotFoundException, OperationFailedException {
		Detalles detalles = objFactory.createOrdenDetalles();
		List<Detalle> detallesList = detalles.getDetalle();

		// Obtengo el detalle de la OC desde el mapa
		DdcOrderDetailW[] ods = (DdcOrderDetailW[]) orderdetailsMap.get(orderid);
		for (int j = 0; j < ods.length; j++) {
			DdcOrderDetailW od = ods[j];
			Detalle detalle = objFactory.createOrdenDetallesDetalle();
		
			VendorItemDataDTO itemdata = (VendorItemDataDTO) itemsMap.get(od.getItemid());
			// Correlativo
			detalle.setItem(od.getPosition());

			// Cod. producto comprador
			detalle.setCodProdComp(itemdata.getSku());

			// Cod. producto vendedor
			detalle.setCodProdVendedor(itemdata.getVendoritemcode());

			// EAN13
			detalle.setEan13(od.getBarcode2());

			// Descripci�n producto
			detalle.setDescripcionProd(itemdata.getName());

			// C�digo de empaque
			detalle.setCodEmpaque(od.getPackingcode() != null ? od.getPackingcode() : "");

			// Descripci�n de empaque
			detalle.setDescEmpaque(od.getPackingdescription() != null ? od.getPackingdescription() : "" );

			// Descripci�n UM
			detalle.setDescUmUnidad("");

			// Prod. empaque
			detalle.setProdEmpaque(1);

			// Cantidad empaque
			Double cantidadempaque = od.getNeedunits();
			detalle.setCantidadEmpaque(cantidadempaque.intValue());
			
			// Innerpack
			detalle.setInnerpack(0);

			// Costo Lista
			detalle.setCostoLista(od.getBasecost());

			// Costo final
			detalle.setCostoFinal(od.getFinalcost());
			
			// Precio de Lista
			detalle.setPrecioLista(od.getNormalprice());

			// Descuentos de los productos
			DescProducto descuentosType = objFactory.createOrdenDetallesDetalleDescProducto();
//			List<CargoDescuento> descTypeList = descuentosType.getDescuento();
			detalle.setDescProducto(descuentosType);

			// Cargos de los productos
			CargosProd cargosType =	objFactory.createOrdenDetallesDetalleCargosProd();
//			List<CargoDescuento> cargosTypeList = cargosType.getCargo();
			detalle.setCargosProd(cargosType);

			detallesList.add(detalle);
		}
		return detalles;
	}

	private EdiData setEdiData(ObjectFactory objFactory, VendorW vendor, DdcOrderW order, RetailerW retailer) throws OperationFailedException {
		EdiData qedidata = objFactory.createOrdenEdiData();
		
		LocationW local;
		try {
		  local = locationserver.getById(order.getSalelocationid()); 
		} catch (NotFoundException e) {
			throw new OperationFailedException(e.getMessage());
		}
		String gln = vendor.getGln() != null && vendor.getGln().trim().length() > 0 ? vendor.getGln() : "";
		qedidata.setSenderIdentification(retailer.getDni());
		qedidata.setBillToPartner("");
		qedidata.setRecipientIdentification(gln);
		qedidata.setCorrelativeVendor("");
		qedidata.setMessageReferenceNumber(System.getProperty("MessageReferenceNumber"));
		qedidata.setCountrycode(LogisticConstants.getInstance().getCOUNTRYCODE());
		qedidata.setBuyerCode(retailer.getCode());
		qedidata.setBuyerLocationCode(retailer.getGln());
		return qedidata;
	}
	
	private Locales setLocalenBlanco(ObjectFactory objFactory) throws NotFoundException, OperationFailedException {
		Locales locales = objFactory.createOrdenLocales();
		List<Local> localesList = locales.getLocal();
		Local local = objFactory.createOrdenLocalesLocal();
		local.setCod("");
		local.setDireccion("");
		local.setEan("");
		local.setNombre("");
		localesList.add(local);		

		return locales;
	}


	private Vendedor setVendedor(ObjectFactory objFactory, Long vendorid) throws NotFoundException, OperationFailedException {
		Vendedor vendedor = objFactory.createOrdenVendedor();
			
		// Se busca el proveedor en la DB
		VendorW vendor = vendorserver.getById(vendorid);
		vendedor.setRut(vendor.getDni());
		vendedor.setRazonSocial(vendor.getName());
		vendedor.setContacto(vendor.getEmail() != null ? vendor.getEmail() : "");
		vendedor.setDireccion(vendor.getAddress() != null ? vendor.getAddress() : "");
		vendedor.setTelefono(vendor.getPhone() != null ? vendor.getPhone() : "");
		return vendedor;
	}
	
	private Cliente setCliente(ObjectFactory objFactory, long clientid) throws NotFoundException, OperationFailedException, JAXBException {
		Cliente cliente = objFactory.createOrdenCliente();
		
		// Se busca el proveedor en la DB
		ClientW client = clientserver.getById(clientid);
		cliente.setNombre(client.getName() != null ? client.getName() : "");
		cliente.setRut(client.getDni() != null ? client.getDni() : "");
		cliente.setTelefono(client.getPhone() != null ? client.getPhone() : "");
		cliente.setDireccion(client.getAddress() != null ? client.getAddress() : "");
		cliente.setNumcalle(client.getStreetnumber() != null ? client.getStreetnumber() : "");
		cliente.setNumdepto(client.getDeparmentnumber() != null ? client.getDeparmentnumber() : "");
		cliente.setNumcasa(client.getStreetnumber() != null ? client.getStreetnumber() : "");
		cliente.setComuna(client.getCommune() != null ? client.getCommune() : "");
		cliente.setCiudad(client.getCity() != null ? client.getCity() : "");
		cliente.setComentario(client.getComment() != null ? client.getComment() : "");
		cliente.setRegion("");
		return cliente;
	}
	
	private void doBackUpMessage(String content, String number, String msgType) {

		// EJECUTA UNA TAREA QUE RESPALDA EL MENSAJE.
		// ESTA ES INDEPENDIENTE DE LA CARGA DEL MENSAJE.
		try {
			executor.submit(new BackUpUtils(content, number, msgType));
		} catch (RejectedExecutionException e) {
			e.printStackTrace();
		}
	}
	
}
