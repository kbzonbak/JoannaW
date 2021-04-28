package bbr.b2b.logistic.soa.msgb2btosoa;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
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
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Seccion;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Vendedor;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.buyorders.classes.ClientServerLocal;
import bbr.b2b.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.buyorders.classes.ResponsableServerLocal;
import bbr.b2b.logistic.buyorders.classes.RetailerServerLocal;
import bbr.b2b.logistic.buyorders.classes.SectionServerLocal;
import bbr.b2b.logistic.buyorders.data.wrappers.ClientW;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.logistic.buyorders.data.wrappers.ResponsableW;
import bbr.b2b.logistic.buyorders.data.wrappers.RetailerW;
import bbr.b2b.logistic.buyorders.data.wrappers.SectionW;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderDetailServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateTypeServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrPreDeliveryDetailServerLocal;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderDetailW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateTypeW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrPreDeliveryDetailW;
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
import bbr.b2b.logistic.utils.OrderTypeBBRUtils;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;

@Stateless(name = "handlers/OrderSendToXml")
public class OrderSendToXml implements OrderSendToXmlLocal {

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
	LocationServerLocal locationserver = null;

	@EJB
	DvrOrderDetailServerLocal orderdetailserver = null;

	@EJB
	DvrOrderServerLocal orderserver = null;
	
	@EJB
	ClientServerLocal clientserver = null;
	
	@EJB
	DvrOrderStateServerLocal orderstateserver = null;

	@EJB
	DvrOrderStateTypeServerLocal orderstatetypeserver = null;

	@EJB
	OrderTypeServerLocal ordertypeserver = null;

	@EJB
	DvrPreDeliveryDetailServerLocal predeliverydetailserver = null;

	@EJB
	ResponsableServerLocal responsableserver = null;

	@EJB
	SectionServerLocal sectionserver = null;

	@EJB
	VendorItemServerLocal vendoritemserver = null;

	@EJB
	VendorServerLocal vendorserver = null;

	@EJB
	SOAStateTypeServerLocal soastatetypeserver;

	@EJB
	SOAStateServerLocal soastateserver;
	
	@EJB
	DvrOrderServerLocal dvrorderserver;

	@EJB
	SchedulerManagerLocal schedulermanager;	
	
	@EJB
	RetailerServerLocal retailerserver;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@TransactionTimeout(1800)
	public void processMessage(OrderW oc, VendorW vendor, boolean acceptorders, int count, int totalcount) throws NotFoundException, OperationFailedException {

		orderserver.getById(oc.getId());	//para bloquear la orden
		DvrOrderW order = dvrorderserver.getById(oc.getId());
		String msgcontent = null;
		String nombreportal = B2BSystemPropertiesUtil.getProperty("NombrePortal");

		// Se obtienen los estados de las OC: Liberada, Modificada y Aceptada
		DvrOrderStateTypeW releasedState = orderstatetypeserver.getByPropertyAsSingleResult("description", "Liberada");
		DvrOrderStateTypeW modifiedState = orderstatetypeserver.getByPropertyAsSingleResult("description", "Modificada");
		DvrOrderStateTypeW acceptedState = orderstatetypeserver.getByPropertyAsSingleResult("description", "Aceptada");
		
		LocalDateTime now = LocalDateTime.now();

		// Estado SOA Enviado
		SOAStateTypeW soaEnvSt = soastatetypeserver.getByPropertyAsSingleResult("code", "ENVIADO");

		// Mapa de ordenes por ID
		Map<Long, DvrOrderW> ordersMap = new TreeMap<Long, DvrOrderW>();
		// Mapa de detalle de ordenes por orden
		Map<Long, DvrOrderDetailW[]> orderdetailsMap = new HashMap<Long, DvrOrderDetailW[]>();
		// Mapa de predistribucion de ordenes por orden
		Map<Long, DvrPreDeliveryDetailW[]> predetaildataMap = new HashMap<Long, DvrPreDeliveryDetailW[]>();
		// Mapa de productos por id (ID, VendorItemDataW)
		Map<Long, VendorItemDataDTO> itemsMap = new HashMap<Long, VendorItemDataDTO>();
		// Mapa de locales de despacho por id (Long, LocationW)
		Map<Long, LocationW> predeliverylocationsMap = new HashMap<Long, LocationW>();

		// Se buscan los productos asociados a la OC
		VendorItemDataDTO[] vendoritems = vendoritemserver.getVendorItemDatasofOrder(order.getId(), order.getVendorid());
		if (vendoritems == null || vendoritems.length == 0)
			throw new OperationFailedException("Error al buscar productos de orden");

		for (VendorItemDataDTO vendoritem : vendoritems) {
			itemsMap.put(vendoritem.getId(), vendoritem);
		}

		// Se buscan los locales de distribucion de la orden
		LocationW[] locations = locationserver.getPredeliveryLocationsOfOrder(order.getId());
		if (locations == null || locations.length == 0)
			throw new OperationFailedException("Error al buscar locales de entrega de orden");

		for (LocationW location : locations) {
			predeliverylocationsMap.put(location.getId(), location);
		}

		// Se agrega el local de entrega en caso que no est� en el mapa
		if (!predeliverylocationsMap.containsKey(order.getDeliverylocationid())) {
			LocationW deliverylocation = locationserver.getById(order.getDeliverylocationid());
			predeliverylocationsMap.put(deliverylocation.getId(), deliverylocation);
		}

		// Se obtiene el detalle de la OC
		DvrOrderDetailW[] details = orderdetailserver.getByPropertyAsArray("dvrorder.id", order.getId());
		if (details == null || details.length == 0)
			throw new OperationFailedException("Error al buscar detalles de ordenes");

		// Se agrega un elemento al mapa de detalles
		orderdetailsMap.put(order.getId(), details);

		// Se obtiene la predistribucion asociada a la OC
		DvrPreDeliveryDetailW[] preddata = predeliverydetailserver.getByPropertyAsArray("dvrorderdetail.dvrorder.id", order.getId());
		if (preddata == null || preddata.length == 0)
			throw new OperationFailedException("Error al buscar predistribuci�n de orden");

		// Se agrega un elemento al mapa de pre-distribuciones
		predetaildataMap.put(order.getId(), preddata);

		// SE CONSTRUYE EL XML para enviar al BUS
		ObjectFactory objFactory = new ObjectFactory();

		try {
			Orden qlistaOC = objFactory.createOrden();

			// Nombre Portal
			qlistaOC.setNombreportal(nombreportal);

			// N�mero de OC
			qlistaOC.setNo(order.getNumber());

			// D�gito Verificador
			Long dv = order.getNumber() % 10; // Definici�n de RAG seg�n documentaci�n EDI
			qlistaOC.setDigitoVerificador(dv.intValue());

			// Estado de OC
			if (order.getCurrentstatetypeid().longValue() == releasedState.getId().longValue())
				qlistaOC.setEstadoOc(releasedState.getDescription());
			else
				qlistaOC.setEstadoOc(modifiedState.getDescription());

			// Tipo de OC
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
				gcal = GregorianCalendar.from(order.getDeliverydate().atZone(ZoneId.systemDefault()));
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

			// Total de la OC
			qlistaOC.setTotal(order.getTotalneed());

			// Codigo Local de entrega
			LocationW deliverylocation = locationserver.getById(order.getDeliverylocationid());
			qlistaOC.setCodLocalEntrega(deliverylocation.getCode());

			// Forma de pago
			qlistaOC.setFormaPago(order.getPaymenttype() != null ? order.getPaymenttype() : "");

			// Comentarios
			String comment = order.getComment() != null && order.getComment().trim().length() > 0 ? order.getComment() : "";
			qlistaOC.setComentarios(comment);

			// Responsable
			ResponsableW responsable = responsableserver.getById(order.getResponsableid());
			qlistaOC.setResponsable(responsable.getName());

			// Cliente
			String clientRegion = ""; 
			if(order.getClientid() != null && order.getClientid().longValue() > 0){
				Cliente client = setCliente(objFactory, order.getClientid());
				qlistaOC.setCliente(client);
				if(client != null && client.getRegion() != null)
					clientRegion = client.getRegion();
			}
			
			RetailerW retailer = retailerserver.getById(1L);
			// Información EDI
			EdiData ediData = setEdiData(objFactory, vendor, deliverylocation, retailer);
			qlistaOC.setEdiData(ediData);

			// Comprador
			Comprador comprador = setComprador(objFactory, retailer);
			qlistaOC.setComprador(comprador);

			// Vendedor
			Vendedor vendedor = setVendedor(objFactory, order.getVendorid());
			qlistaOC.setVendedor(vendedor);


			if(order.getReferencenumber() != null)
			qlistaOC.setNosolicitud(order.getReferencenumber());
			
			// Locales
			Locales locales = setLocales(objFactory, order.getId(), order.getDeliverylocationid());
			qlistaOC.setLocales(locales);

			// Descuentos generales
			DescGenerales descGrales = objFactory.createOrdenDescGenerales();

			// List descgralsList = descGrales.getDescuento();
			// CargosProd dscto = objFactory.createOrdenDetallesDetalleCargosProd();

			// dscto.setGlosa("descuento");
			// dscto.setTipo("tipo desc");
			// dscto.setValor(new Float(0).floatValue());
			// descgralsList.add(dscto);
			qlistaOC.setDescGenerales(descGrales);

			// Cargos generales
			CargosGenerales cargosGrales = objFactory.createOrdenCargosGenerales();

			// List cargosgralsList = cargosGrales.getCargo();
			// CargosProd cargogral = objFactory.createOrdenDetallesDetalleCargosProd();

			// cargogral.setGlosa("cargo");
			// cargogral.setTipo("tipo cargo");
			// cargogral.setValor(new Float(0).floatValue());
			// cargosgralsList.add(cargogral);
			qlistaOC.setCargosGenerales(cargosGrales);

			// Detalle de OC
			Detalles detalles = setDetalleOC(objFactory, order.getId(), orderdetailsMap, itemsMap);
			qlistaOC.setDetalles(detalles);

			// Predistribuci�n de OC
			Predistribuciones predistribuciones = setPredistribucion(objFactory, order.getId(), predetaildataMap, predeliverylocationsMap, itemsMap);
			qlistaOC.setPredistribuciones(predistribuciones);

			// Obtiene string XML para enviarlo a la cola
			JAXBContext jc = getJC();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(qlistaOC, sw);
			msgcontent = sw.toString();

			// RESPALDA MENSAJE
			doBackUpMessage(msgcontent, String.valueOf(order.getNumber()), "SOA");
			 System.out.println(msgcontent);


			schedulermanager.doAddMessageQueue("qcf_soa", "q_esbgrl" , "EnviadoOcSoa", String.valueOf(order.getNumber())+"_SOA",  String.valueOf(order.getNumber()), msgcontent, now);
		} catch (JAXBException e) {
			logger.error("Error al construir mensaje", e);
			throw new OperationFailedException("Error al construir mensaje", e);
		}

		// Si no hay que aceptar, retorna el control
		if (!acceptorders)
			return;

		boolean accept = false;
		
		accept = false;

		// Se marca la OC como enviada a SOA
		order.setCurrentsoastatetypedate(now);
		order.setCurrentsoastatetypeid(soaEnvSt.getId());

		SOAStateW soastate = new SOAStateW();
		soastate.setOrderid(order.getId());
		soastate.setSoastatetypeid(soaEnvSt.getId());
		soastate.setWhen(now);

		soastate = soastateserver.addSOAState(soastate);

		if ((order.getCurrentstatetypeid().equals(releasedState.getId())) ||
				(order.getCurrentstatetypeid().equals(modifiedState.getId()))) {
			order.setCurrentstatetypeid(acceptedState.getId());
			order.setCurrentstatetypedate(now);
			accept = true;
		}

		try {
			order = orderserver.updateDvrOrder(order);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (accept) {
			DvrOrderStateW orderstate = new DvrOrderStateW();
			orderstate.setDvrorderid(order.getId());
			orderstate.setDvrorderstatetypeid(acceptedState.getId());
			orderstate.setWhen(now);

			try {
				orderstate = orderstateserver.addDvrOrderState(orderstate);
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		logger.info("BBR SOA ("+count+" de "+totalcount+"): Se ha enviado a SOA la orden número " + order.getNumber());

		ordersMap.clear();
		orderdetailsMap.clear();
		predetaildataMap.clear();
		itemsMap.clear();
		predeliverylocationsMap.clear();
	}

	private Comprador setComprador(ObjectFactory objFactory, RetailerW retailer) throws NotFoundException, OperationFailedException {
		Comprador comprador = objFactory.createOrdenComprador();

		comprador.setRut(retailer.getDni());
		comprador.setRazonSocial(retailer.getDescription());
		comprador.setUnidadNegocio(LogisticConstants.getInstance().getBUSINESSAREA());
		return comprador;

	}

	private Detalles setDetalleOC(ObjectFactory objFactory, Long orderid, Map<Long, DvrOrderDetailW[]> orderdetailsMap, Map<Long, VendorItemDataDTO> itemsMap) throws NotFoundException, OperationFailedException {
		Detalles detalles = objFactory.createOrdenDetalles();
		List detallesList = detalles.getDetalle();

		// Obtengo el detalle de la OC desde el mapa
		DvrOrderDetailW[] ods = orderdetailsMap.get(orderid);
		for (int j = 0; j < ods.length; j++) {
			DvrOrderDetailW od = ods[j];
			Detalle detalle = objFactory.createOrdenDetallesDetalle();
			Long itemid = od.getItemid();
			VendorItemDataDTO itemdata = (VendorItemDataDTO) itemsMap.get(itemid);
			// Correlativo
			detalle.setItem(od.getPosition());

			// Cod. producto comprador
			detalle.setCodProdComp(itemdata.getSku());

			// Cod. producto vendedor
			detalle.setCodProdVendedor(itemdata.getVendoritemcode());
			
			// Descripci�n producto
			detalle.setDescripcionProd(itemdata.getName());

			// EAN13
			detalle.setEan13(od.getBarcode1() != null ? od.getBarcode1() : "");

			// Código de empaque
			//detalle.setCodEmpaque(od.getUmccode() != null ? od.getUmccode() : ""); 
			detalle.setCodEmpaque(""); 

			// Descripción de empaque
			//detalle.setDescEmpaque(od.getUmcdescription() != null ? od.getUmcdescription() : ""); 
			detalle.setDescEmpaque(""); 

			// Descripción UM
			//detalle.setDescUmUnidad(od.getUmcdescription() != null ? od.getUmcdescription() : "");
			detalle.setDescUmUnidad("");

			// Prod. empaque
			//detalle.setProdEmpaque(od.getUmbxumc().floatValue() );
			detalle.setProdEmpaque(1);

			// Cantidad empaque
			Double cantidadempaque = od.getTotalunits();
			detalle.setCantidadEmpaque(cantidadempaque.intValue());

			// Costo Lista
			detalle.setCostoLista(od.getBasecost());

			// Costo final
			detalle.setCostoFinal(od.getFinalcost());

			// Descuentos de los productos
			DescProducto descuentosType = objFactory.createOrdenDetallesDetalleDescProducto();
			List descTypeList = descuentosType.getDescuento();
			CargoDescuento discount = null;
//			if (od.getDiscount_1() != 0) {
//				discount = objFactory.createCargoDescuento();
//				Double discount1Dbl = Math.abs(od.getDiscount_1());
//				Double discount1percentDbl = Math.abs(od.getDiscount_1());
//				discount.setMonto(discount1Dbl.floatValue());
//				discount.setPorcentaje(discount1percentDbl.floatValue());
//				discount.setTipo(od.getDiscount_1_ispercent() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
//				discount.setGlosa("Desc. Base 1");
//				discount.setCodigo("DE1");
//				descTypeList.add(discount);
//			}
//			if (od.getDiscount_2() != 0) {
//				discount = objFactory.createCargoDescuento();
//				Double discount2Dbl = Math.abs(od.getDiscount_2());
//				Double discount2percentDbl = Math.abs(od.getDiscount_2());
//				discount.setMonto(discount2Dbl.floatValue());
//				discount.setPorcentaje(discount2percentDbl.floatValue());
//				discount.setTipo(od.getDiscount_2_ispercent() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
//				discount.setGlosa("Desc. Base 2");
//				discount.setCodigo("DE2");
//				descTypeList.add(discount);
//			}
//			if (od.getDiscount_3() != 0) {
//				discount = objFactory.createCargoDescuento();
//				Double discount3Dbl = Math.abs(od.getDiscount_3());
//				Double discount3percentDbl = Math.abs(od.getDiscount_3());
//				discount.setMonto(discount3Dbl.floatValue());
//				discount.setPorcentaje(discount3percentDbl.floatValue());
//				discount.setTipo(od.getDiscount_3_ispercent() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
//				discount.setGlosa("Desc. Base 3");
//				discount.setCodigo("DE3");
//				descTypeList.add(discount);
//			}
//			if (od.getDiscount_4() != 0) {
//				discount = objFactory.createCargoDescuento();
//				Double discount4Dbl = Math.abs(od.getDiscount_4());
//				Double discount4percentDbl = Math.abs(od.getDiscount_4());
//				discount.setMonto(discount4Dbl.floatValue());
//				discount.setPorcentaje(discount4percentDbl.floatValue());
//				discount.setTipo(od.getDiscount_4_ispercent() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
//				discount.setGlosa("Desc. Base 4");
//				discount.setCodigo("DE4");
//				descTypeList.add(discount);
//			}
//			if (od.getDiscount_5() != 0) {
//				discount = objFactory.createCargoDescuento();
//				Double discount5Dbl = Math.abs(od.getDiscount_5());
//				Double discount5percentDbl = Math.abs(od.getDiscount_5());
//				discount.setMonto(discount5Dbl.floatValue());
//				discount.setPorcentaje(discount5percentDbl.floatValue());
//				discount.setTipo(od.getDiscount_5_ispercent() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
//				discount.setGlosa("Desc. Base 5");
//				discount.setCodigo("DE5");
//				descTypeList.add(discount);
//			}
//			if (od.getDiscount_6() != 0) {
//				discount = objFactory.createCargoDescuento();
//				Double discount6Dbl = Math.abs(od.getDiscount_6());
//				Double discount6percentDbl = Math.abs(od.getDiscount_6());
//				discount.setMonto(discount6Dbl.floatValue());
//				discount.setPorcentaje(discount6percentDbl.floatValue());
//				discount.setTipo(od.getDiscount_6_ispercent() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
//				discount.setGlosa("Desc. Base 6");
//				discount.setCodigo("DE6");
//				descTypeList.add(discount);
//			}
//			if (od.getDiscount_7() != 0) {
//				discount = objFactory.createCargoDescuento();
//				Double discount7Dbl = Math.abs(od.getDiscount_7());
//				Double discount7percentDbl = Math.abs(od.getDiscount_7());
//				discount.setMonto(discount7Dbl.floatValue());
//				discount.setPorcentaje(discount7percentDbl.floatValue());
//				discount.setTipo(od.getDiscount_7_ispercent() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
//				discount.setGlosa("Desc. Base 7");
//				discount.setCodigo("DE7");
//				descTypeList.add(discount);
//			}
//			if (od.getDiscount_8() != 0) {
//				discount = objFactory.createCargoDescuento();
//				Double discount8Dbl = Math.abs(od.getDiscount_8());
//				Double discount8percentDbl = Math.abs(od.getDiscount_8());
//				discount.setMonto(discount8Dbl.floatValue());
//				discount.setPorcentaje(discount8percentDbl.floatValue());
//				discount.setTipo(od.getDiscount_8_ispercent() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
//				discount.setGlosa("Desc. Base 8");
//				discount.setCodigo("DE8");
//				descTypeList.add(discount);
//			}
//			if (od.getDiscount_9() != 0) {
//				discount = objFactory.createCargoDescuento();
//				Double discount9Dbl = Math.abs(od.getDiscount_9());
//				Double discount9percentDbl = Math.abs(od.getDiscount_9());
//				discount.setMonto(discount9Dbl.floatValue());
//				discount.setPorcentaje(discount9percentDbl.floatValue());
//				discount.setTipo(od.getDiscount_9_ispercent() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
//				discount.setGlosa("Desc. Base 9");
//				discount.setCodigo("DE9");
//				descTypeList.add(discount);
//			}
//			if (od.getDiscount_10() != 0) {
//				discount = objFactory.createCargoDescuento();
//				Double discount10Dbl = Math.abs(od.getDiscount_10());
//				Double discount10percentDbl = Math.abs(od.getDiscount_10());
//				discount.setMonto(discount10Dbl.floatValue());
//				discount.setPorcentaje(discount10percentDbl.floatValue());
//				discount.setTipo(od.getDiscount_10_ispercent() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
//				discount.setGlosa("Desc. Base 10");
//				discount.setCodigo("DE10");
//				descTypeList.add(discount);
//			}

			detalle.setDescProducto(descuentosType);

			// Cargos de los productos
			CargosProd cargosType = objFactory.createOrdenDetallesDetalleCargosProd();
			List cargosTypeList = cargosType.getCargo();
			CargoDescuento cargo = null;

//			if (od.getCharge_1_value() > 0) {
//				cargo = objFactory.createCargoDescuento();
//				Double charge1Dbl = od.getCharge_1_value();
//				Double charge1percentDbl = od.getCharge_1_value();
//				cargo.setMonto(charge1Dbl.floatValue());
//				cargo.setPorcentaje(charge1percentDbl.floatValue());
//				cargo.setTipo(od.isCharge_1_percentage() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
//				cargo.setGlosa(od.getCharge_1_name() != null ? od.getCharge_1_name() : "Cargo Base 1");
//				cargo.setCodigo("CA1");
//				cargosTypeList.add(cargo);
//			}
//			if (od.getCharge_2_value() > 0) {
//				cargo = objFactory.createCargoDescuento();
//				Double charge2Dbl = od.getCharge_2_value();
//				Double charge2percentDbl = od.getCharge_2_value();
//				cargo.setMonto(charge2Dbl.floatValue());
//				cargo.setPorcentaje(charge2percentDbl.floatValue());
//				cargo.setTipo(od.isCharge_2_percentage() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
//				cargo.setGlosa(od.getCharge_2_name() != null ? od.getCharge_2_name() : "Cargo Base 2");
//				cargo.setCodigo("CA2");
//				cargosTypeList.add(cargo);
//			}
//			if (od.getCharge_3_value() > 0) {
//				cargo = objFactory.createCargoDescuento();
//				Double charge3Dbl = od.getCharge_3_value();
//				Double charge3percentDbl = od.getCharge_3_value();
//				cargo.setMonto(charge3Dbl.floatValue());
//				cargo.setPorcentaje(charge3percentDbl.floatValue());
//				cargo.setTipo(od.isCharge_3_percentage() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
//				cargo.setGlosa(od.getCharge_3_name() != null ? od.getCharge_3_name() : "Cargo Base 3");
//				cargo.setCodigo("CA3");
//				cargosTypeList.add(cargo);
//			}
			detalle.setCargosProd(cargosType);

			detallesList.add(detalle);
		}
		return detalles;
	}

	private EdiData setEdiData(ObjectFactory objFactory, VendorW vendor, LocationW location, RetailerW retailer) throws OperationFailedException {
		EdiData qedidata = objFactory.createOrdenEdiData();
		qedidata.setSenderIdentification(retailer.getDni());

		String gln = vendor.getGln() != null && vendor.getGln().trim().length() > 0 ? vendor.getGln() : "";
		qedidata.setBillToPartner("");
		qedidata.setRecipientIdentification(gln);
		qedidata.setCorrelativeVendor("");
		qedidata.setMessageReferenceNumber(System.getProperty("MessageReferenceNumber"));
		qedidata.setCountrycode(LogisticConstants.getInstance().getCOUNTRYCODE());
		qedidata.setBuyerCode(retailer.getCode());
		String buyerlocationcode = retailer.getGln();
		qedidata.setBuyerLocationCode(buyerlocationcode);
		return qedidata;
	}

	private Locales setLocales(ObjectFactory objFactory, Long orderid, Long deliverylocationid) throws NotFoundException, OperationFailedException {
		Locales locales = objFactory.createOrdenLocales();
		List<Local> localesList = locales.getLocal();

		LocationW[] locations = locationserver.getPredeliveryLocationsOfOrder(orderid);
		ArrayList<Long> locationsinpred = new ArrayList<Long>();
		for (LocationW location : locations) {
			Local local = objFactory.createOrdenLocalesLocal();
			local.setCod(location.getCode());
			local.setNombre(location.getName());
			local.setDireccion(location.getAddress());
			//local.setEan(location.getEan() != null ? location.getEan() : "");??
			localesList.add(local);
			locationsinpred.add(location.getId());

		}
		// Se agrega el local de entrega de la OC en caso que no se encuentre en la predistribuci�n
		if (!locationsinpred.contains(deliverylocationid)) {
			LocationW location = locationserver.getById(deliverylocationid);
			Local local = objFactory.createOrdenLocalesLocal();
			local.setCod(location.getCode());
			local.setNombre(location.getName());
			local.setDireccion(location.getAddress());
			local.setEan("");
			localesList.add(local);
		}
		return locales;
	}

	private Predistribuciones setPredistribucion(ObjectFactory objFactory, Long orderid, Map<Long, DvrPreDeliveryDetailW[]> predetaildataMap, Map<Long, LocationW> predeliverylocationsMap, Map<Long, VendorItemDataDTO> itemsMap) throws NotFoundException, OperationFailedException {
		Predistribuciones predistType = objFactory.createOrdenPredistribuciones();
		List predisTypeList = predistType.getPredistribucion();

		// Obtengo la predistribuci�n desde el mapa
		DvrPreDeliveryDetailW[] predeliveries = predetaildataMap.get(orderid);
		for (int j = 0; j < predeliveries.length; j++) {
			DvrPreDeliveryDetailW predelivery = predeliveries[j];
			Predistribucion detallepred = objFactory.createOrdenPredistribucionesPredistribucion();
			Long itemid = predelivery.getItemid();
			Long locationid = predelivery.getLocationid();
			VendorItemDataDTO itemdata = itemsMap.get(itemid);
			LocationW location = predeliverylocationsMap.get(locationid);
			detallepred.setCodLocal(location.getCode());
			detallepred.setSku(itemdata.getSku());
			detallepred.setCantidad(String.valueOf(predelivery.getTotalunits()));
			predisTypeList.add(detallepred);
		}
		return predistType;
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

	private Cliente setCliente(ObjectFactory objFactory, Long clientid) throws NotFoundException, OperationFailedException {
		Cliente cliente = objFactory.createOrdenCliente();

		// Se busca el cliente en la DB
		ClientW client = clientserver.getById(clientid);
		cliente.setCiudad(client.getCity() != null ? client.getCity() : "");
		cliente.setComentario(client.getComment() != null ? client.getComment() : "");
		cliente.setComuna(client.getCommune() != null ? client.getCommune() : "");
		cliente.setDireccion(client.getAddress() != null ? client.getAddress() : "");
		cliente.setNombre(client.getName() != null ? client.getName() : "");
		cliente.setNumcalle(client.getStreetnumber() != null ? client.getStreetnumber() : "");
		cliente.setNumcasa(client.getStreetnumber() != null ? client.getStreetnumber() : "");
		cliente.setNumdepto(client.getDeparmentnumber() != null ? client.getDeparmentnumber() : "");
		cliente.setComuna(client.getCommune() != null ? client.getCommune() : "");
		cliente.setRut(client.getDni() != null ? client.getDni() : "");
		cliente.setTelefono(client.getPhone() != null ? client.getPhone() : "");
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
