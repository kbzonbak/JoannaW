package bbr.b2b.logistic.soa.msgb2btosoa;

import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
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
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Predistribuciones.Predistribucion;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Seccion;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Vendedor;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.util.DateTimeUtils;
import bbr.b2b.regional.logistic.buyorders.classes.ClientServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.PreDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.ClientW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDetailW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateTypeW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.PreDeliveryDetailW;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryServerLocal;
import bbr.b2b.regional.logistic.items.classes.VendorItemServerLocal;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemDataW;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.regional.logistic.soa.classes.SOAStateServerLocal;
import bbr.b2b.regional.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.regional.logistic.soa.data.classes.SOAStateTypeW;
import bbr.b2b.regional.logistic.soa.data.classes.SOAStateW;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.utils.BackUpUtils;
import bbr.b2b.regional.logistic.utils.MsgRecoveryServices;
import bbr.b2b.regional.logistic.utils.OrderTypeBBRUtils;
import bbr.b2b.regional.logistic.utils.QSender;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.ResponsableServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.DepartmentW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.ResponsableW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;


@Stateless(name = "handlers/OrdersListToXml")
public class OrdersListToXml implements OrdersListToXmlLocal {

	private static JAXBContext jc = null;

	private static Logger logger = Logger.getLogger("SOALog");

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.OC_Interno");
		return jc;
	}
		
	@EJB
	VendorServerLocal vendorserver = null;

	@EJB
	LocationServerLocal locationserver = null;
	
	@EJB
	PreDeliveryDetailServerLocal predeliverydetailserver = null;
	
	@EJB
	OrderDetailServerLocal orderdetailserver = null;

	@EJB
	OrderServerLocal orderserver = null;
	
	@EJB
	OrderStateTypeServerLocal orderstatetypeserver = null;
	
	@EJB
	OrderTypeServerLocal ordertypeserver = null;

	@EJB
	OrderStateServerLocal orderstateserver = null;
	
	@EJB
	VendorItemServerLocal vendoritemserver = null;
	
	@EJB
	ResponsableServerLocal responsableserver = null;
	
	@EJB
	DepartmentServerLocal departmentserver;
	
	@EJB
	ClientServerLocal clientserver;
	
	@EJB
	DeliveryServerLocal deliveryserver;
	
	@EJB
	SOAStateTypeServerLocal soastatetypeserver;
	
	@EJB
	SOAStateServerLocal soastateserver;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@TransactionTimeout(value = 1800)
	public void processMessage(String vendorCode, boolean acceptorders) throws ServiceException {
		
//		String transnumber = "";
		
		RegionalLogisticConstants constants = RegionalLogisticConstants.getInstance();
		String nombreportal = B2BSystemPropertiesUtil.getProperty("NombrePortal");		
		
		// N°MERO DE TRANSACción
//		transnumber = deliveryserver.getNextSequenceTransactionNumber(12);
		
		// Obtener proveedor en base al rut
		VendorW[] vendors = vendorserver.getByPropertyAsArray("code", vendorCode);
		
		if (vendors == null || vendors.length == 0)
			throw new NotFoundException("No se pudo encontrar el proveedor con RUT " + vendorCode);
		
		VendorW vendor = vendors[0];
		
		//Estados de Orden
		// - Liberadas
		OrderStateTypeW releasedState = orderstatetypeserver.getByPropertyAsSingleResult("code", "LIBERADA");
		// - Modificadas
		OrderStateTypeW modifiedState = orderstatetypeserver.getByPropertyAsSingleResult("code", "MODIFICADA");		
		// - Aceptadas
		OrderStateTypeW acceptedState = orderstatetypeserver.getByPropertyAsSingleResult("code", "ACEPTADA");
		
		// Estado SOA Notificado
		SOAStateTypeW soaNotfSt = soastatetypeserver.getByPropertyAsSingleResult("code", "NOTIFICADO");
		// Estado SOA Enviado
		SOAStateTypeW soaEnvSt = soastatetypeserver.getByPropertyAsSingleResult("code", "ENVIADO");
		
		// - recibo con error
		SOAStateTypeW recerrorSt = soastatetypeserver.getByPropertyAsSingleResult("code", "RECIBIDO_ERROR");
		
		// Se obtienen las OC con el flag de envío a SOA en falso, para el proveedor y el comprador. Además,
		// se revisan las cargadas hace 2 d�as atr�s.
		Date since = new Date();
		since = DateTimeUtils.addDays(since, -2);
		
		OrderW[] orderstosend = orderserver.getOrdersToSendSoa(vendor.getId(), since, soaNotfSt.getId(), recerrorSt.getId());
		
		if ((orderstosend == null || orderstosend.length == 0)) {
			logger.info("No se encontraron ordenes para enviar a SOA del proveedor CODE: " + vendorCode);
			return;
		}
		
		logger.info("Se encontraron " + orderstosend.length + " órdenes para enviar a SOA del proveedor RUT: " + vendorCode);

		
		// Mapa de ordenes por ID
		Map<Long, OrderW> ordersMap = new HashMap<Long, OrderW>();
		// Mapa de detalle de ordenes por orden
		Map<Long, OrderDetailW[]> orderdetailsMap = new HashMap<Long, OrderDetailW[]>();
		// Mapa de predistribucion de ordenes por orden
		Map<Long, PreDeliveryDetailW[]> predetaildataMap = new HashMap<Long, PreDeliveryDetailW[]>();
		// Mapa de productos por id (ID, VendorItemDataW)
		Map<Long, VendorItemDataW> itemsMap = new HashMap<Long, VendorItemDataW>();
		// Mapa de locales de despacho por id (Long, LocationW)
		Map<Long, LocationW> predeliverylocationsMap = new HashMap<Long, LocationW>();

		// Se recorren las ordenes liberadas para almacenarlas en un mapa
		for (OrderW order : orderstosend) {
			ordersMap.put(order.getId(), order);
		}

		// Se itera por mapa que contiene las ordenes para preparación de datos
		for (OrderW order : ordersMap.values()){
			// Se buscan los productos asociados a la OC
			VendorItemDataW[] vendoritems = vendoritemserver.getVendorItemDataofOrder(order.getId());
			if (vendoritems == null || vendoritems.length == 0)
				throw new OperationFailedException("Error al buscar productos de orden");

			for (VendorItemDataW vendoritem : vendoritems) {
				itemsMap.put(vendoritem.getId(), vendoritem);
			}
			
			// Se buscan los locales de distribucion de la orden
			LocationW[] locations = locationserver.getByQueryAsArray("select distinct lo from Location as lo, PreDeliveryDetail as pdd where pdd.location = lo and pdd.orderdetail.order.id = " + order.getId());
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
			
			
			// Se agrega el local de venta en caso que no est� en el mapa
			if (order.getSalestoreid() != null && order.getSalestoreid().longValue() > 0 && !predeliverylocationsMap.containsKey(order.getSalestoreid())) {
				LocationW saleslocation = locationserver.getById(order.getSalestoreid());
				predeliverylocationsMap.put(saleslocation.getId(), saleslocation);
			}
			
			// Se obtiene el detalle de la OC
			OrderDetailW[] details = orderdetailserver.getByPropertyAsArrayOrdered("order.id", order.getId(), new OrderCriteriaDTO("visualorder", true));
			if (details == null || details.length == 0)
				throw new OperationFailedException("Error al buscar detalles de ordenes");
			
			// Se agrega un elemento al mapa de detalles
			orderdetailsMap.put(order.getId(), details);

			// Se obtiene la predistribucion asociada a la OC
			PreDeliveryDetailW[] preddata = predeliverydetailserver.getByPropertyAsArray("orderdetail.order.id", order.getId());
			if (preddata == null || preddata.length == 0)
				throw new OperationFailedException("Error al buscar predistribución de orden");

			// Se agrega un elemento al mapa de pre-distribuciones
			predetaildataMap.put(order.getId(), preddata);			
		}
		
		// SE CONSTRUYE EL XML
		ObjectFactory objFactory = new ObjectFactory();

		for (OrderW order : ordersMap.values()) {
			try {
				Orden qlistaOC = objFactory.createOrden();

				// Nombre Portal
				qlistaOC.setNombreportal(nombreportal);

				// N°mero de OC
				qlistaOC.setNo(order.getNumber());
				
				// 2012-07-27 JMA: Se agrega el N°mero de solicitud
				qlistaOC.setNosolicitud(order.getRequestnumber());

				// dígito Verificador
				int dv = order.getNumber().intValue() % 10; // Definición de RAG seg�n documentación EDI
				qlistaOC.setDigitoVerificador(dv);

				// Estado de OC
				if (order.getCurrentstatetypeid().equals(releasedState.getId()))
					qlistaOC.setEstadoOc(releasedState.getName());
				else
					qlistaOC.setEstadoOc(modifiedState.getName());

				// Tipo de OC
				OrderTypeW octype = ordertypeserver.getById(order.getOrdertypeid());
				qlistaOC.setTipoOc(octype.getName());
				
				//tipo de oc BBR
				qlistaOC.setTipoOcBbr(OrderTypeBBRUtils.getInstance().getOrderTypeBBRByCode(octype.getCode()));
				
				// Sección
				DepartmentW department = departmentserver.getById(order.getDepartmentid());
				
				Seccion section = new Seccion();
				section.setCodSeccion(department.getCode());
				section.setSeccion(department.getName());
				
				qlistaOC.setSeccion(section);
				

				Locale locale = new Locale("es", "CL");
				GregorianCalendar gcal = new GregorianCalendar(locale);
								
				// Fecha de Emisión
				gcal.setTime(order.getEmitted());
				XMLGregorianCalendar xmlgcal_emitted = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
				qlistaOC.setFechaEmision(xmlgcal_emitted);

				// Fecha de Vigencia
				gcal.setTime(order.getValid());
				XMLGregorianCalendar xmlgcal_valid = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
				qlistaOC.setFechaVigencia(xmlgcal_valid);

				// Fecha de Vencimiento
				gcal.setTime(order.getExpiration());
				XMLGregorianCalendar xmlgcal_expiration = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
				qlistaOC.setFechaVto(xmlgcal_expiration);
				
				// Fecha de Compromiso
				if(order.getVevcd() && order.getOriginaldeliverydate() != null && !order.getOriginaldeliverydate().equals("")){
					gcal.setTime(order.getOriginaldeliverydate());
					XMLGregorianCalendar xmlgcal_delivery = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
					qlistaOC.setFechaCompromiso(xmlgcal_delivery);
				}

				// Total de la OC
				qlistaOC.setTotal(order.getTotalneed());

				// Codigo Local de entrega
				LocationW deliverylocation = (LocationW) predeliverylocationsMap.get(order.getDeliverylocationid());
				qlistaOC.setCodLocalEntrega(deliverylocation.getCode());

				
				LocationW salestore = null;
				if(order.getSalestoreid() != null && order.getSalestoreid().longValue() > 0) {
					salestore = locationserver.getByPropertyAsSingleResult("id", order.getSalestoreid());
					qlistaOC.setCodigoLocalVenta(salestore.getCode());
				}
				
				// Forma de pago
				qlistaOC.setFormaPago("");

				// Comentarios
				String comment = order.getComment() != null && order.getComment().trim().length() > 0 ? order.getComment() : "";
				qlistaOC.setComentarios(comment);

				// Responsable
				ResponsableW responsable = responsableserver.getById(order.getResponsableid());
				qlistaOC.setResponsable(responsable.getName());

				// Comprador
				// OrdenType.CompradorType comprador = setComprador(objFactory, order.getBuyerid());
				Comprador comprador = setCompradors(objFactory, 0L);
				qlistaOC.setComprador(comprador);

				// Vendedor
				Vendedor vendedor = setVendedor(objFactory, order.getVendorid());
				qlistaOC.setVendedor(vendedor);

				// Cliente
				// EBO 20-07-2015: Se agrega cliente si la oc es OCVEVCD
				String clientRegion = ""; 
				if (order.getVevcd() && order.getClientid() != null && order.getClientid() > 0){
					Cliente cliente = setCliente(objFactory, order.getClientid());					
					qlistaOC.setCliente(cliente);
					if(cliente != null && cliente.getRegion() != null)
						clientRegion = cliente.getRegion();
				}		

				// Información EDI
				EdiData ediData = setEdiData(objFactory, vendor, order, clientRegion);
				qlistaOC.setEdiData(ediData);

				// Locales
				Locales locales = setLocales(objFactory, order.getId(), order.getDeliverylocationid(), order.getSalestoreid());
				qlistaOC.setLocales(locales);

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

				// Predistribución de OC
				Predistribuciones predistribuciones = setPredistribucion(objFactory, order.getId(), predetaildataMap, predeliverylocationsMap, itemsMap);
				qlistaOC.setPredistribuciones(predistribuciones);

				// Obtiene string XML para enviarlo a la cola
				JAXBContext jc = getJC();
				Marshaller m = jc.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter sw = new StringWriter();
				m.marshal(qlistaOC, sw);
				String result = sw.toString();
			
				// RESPALDA MENSAJE			
				doBackUpMessage(result, order.getNumber().toString(), "SOA");

				try {			
					QSender.getInstance().putMessage("jboss/qcf_soa", "jboss/q_esbgrlcenco", result, Charset.forName("UTF-8"));				
				} catch (Exception ex) {
					// Si ocurrió un error al enviar el archivo, se graba el mensaje para reencolarlo
					MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
					String msgtype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() + RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_SOA_" + order.getNumber();
					try {
						msgRecoveryServices.saveMsgToFile(msgtype, result, ex);
					} catch (Exception e1) {
						logger.debug(e1.getLocalizedMessage());
					}
				}
			}catch (DatatypeConfigurationException e) {
				e.printStackTrace();
				throw new OperationFailedException("Error al construir mensaje", e);
			} catch (JAXBException e) {
				e.printStackTrace();
				throw new OperationFailedException("Error al construir mensaje", e);
			}			
		}

		// Si no hay que aceptar, retorna el control
		if (!acceptorders)
			return;

		// Luego de crear el listado para SOA, las ordenes en cuestión se deben marcar como enviadas
		// y aceptar en caso que este liberada o modificada
		boolean accept = false;
		for (OrderW order : ordersMap.values()) {
			Date now = new Date();
			accept = false;
			// Se marca la OC como enviada a SOA
//			order.setSenttosoa(true);

			// Se marca la OC como enviada a SOA			
			order.setCurrentsoastatetypedate(now);
			order.setCurrentsoastatetypeid(soaEnvSt.getId());
			
			SOAStateW soastate = new SOAStateW();
			soastate.setOrderid(order.getId());
			soastate.setSoastatetypeid(soaEnvSt.getId());
			soastate.setWhen(now);
			
			soastate = soastateserver.addSOAState(soastate);					
			
			
			if(order.getCurrentstatetypeid().equals(releasedState.getId()) || order.getCurrentstatetypeid().equals(modifiedState.getId())){
				order.setCurrentstatetypeid(acceptedState.getId());
				order.setCurrentstatetypedate(now);
				accept = true;
			}
			
			order = orderserver.updateOrder(order);			

			if (accept) {
				OrderStateW orderstate = new OrderStateW();
				orderstate.setOrderid(order.getId());
				orderstate.setOrderstatetypeid(acceptedState.getId());
				orderstate.setWhen(now);
				
				orderstate = orderstateserver.addOrderState(orderstate);
				
			}
			logger.info("BBR SOA: Se ha enviado a SOA la orden N°mero " + order.getNumber());
		}
		ordersMap.clear();
		orderdetailsMap.clear();
		predetaildataMap.clear();
		itemsMap.clear();
		predeliverylocationsMap.clear();
	}

	private Comprador setCompradors(ObjectFactory objFactory, Long buyerid) throws NotFoundException, OperationFailedException {
		Comprador comprador = objFactory.createOrdenComprador();

		// Se busca la sociedad en la DB
		// BuyerW buyer = buyerserver.getById(buyerid);

		// Se buscar el tipo de sociedad
		// BuyerTypeW buyerType = buyertypeserver.getById(buyer.getBuyertypeid());
		// comprador.setUnidadNegocio(buyerType.getName());

		comprador.setRut(B2BSystemPropertiesUtil.getProperty("BuyerRut"));
		comprador.setRazonSocial(B2BSystemPropertiesUtil.getProperty("BuyerSocialReason"));
		comprador.setUnidadNegocio(B2BSystemPropertiesUtil.getProperty("BuyerBussinessArea"));
		return comprador;
	}

	private Detalles setDetalleOC(ObjectFactory objFactory, Long orderid, Map<Long, OrderDetailW[]> orderdetailsMap, Map<Long, VendorItemDataW> itemsMap) throws NotFoundException, OperationFailedException {
		Detalles detalles = objFactory.createOrdenDetalles();
		List<Detalle> detallesList = detalles.getDetalle();

		// Obtengo el detalle de la OC desde el mapa
		OrderDetailW[] ods = (OrderDetailW[]) orderdetailsMap.get(orderid);
		for (int j = 0; j < ods.length; j++) {
			OrderDetailW od = ods[j];
			Detalle detalle = objFactory.createOrdenDetallesDetalle();
		
			VendorItemDataW itemdata = (VendorItemDataW) itemsMap.get(od.getItemid());
			// Correlativo
			detalle.setItem(j + 1);

			
			// Cod. producto comprador
			detalle.setCodProdComp(itemdata.getInternalcode());

			// Cod. producto vendedor
			detalle.setCodProdVendedor(itemdata.getVendoritemcode());

			// EAN13
			detalle.setEan13(itemdata.getBarcode());

			// Descripción producto
			detalle.setDescripcionProd(itemdata.getName());

			// código de empaque
			detalle.setCodEmpaque("");

			// Descripción de empaque
			detalle.setDescEmpaque("");

			// Descripción UM
			detalle.setDescUmUnidad("");

			// Prod. empaque
			detalle.setProdEmpaque(1);
			
			// Marca
			detalle.setNumRef1(itemdata.getTrademark() == null ? "" : itemdata.getTrademark());

			// Cantidad empaque
			Double cantidadempaque = od.getUnits();
			detalle.setCantidadEmpaque(cantidadempaque.intValue());

			// Innerpack
			detalle.setInnerpack(itemdata.getInnerpack());
			
			// Costo Lista
			detalle.setCostoLista(od.getListcost());

			// Costo final
			detalle.setCostoFinal(od.getFinalcost());
			
			// Precio de Lista
			detalle.setPrecioLista(od.getListprice());
			
			// Descuentos de los productos
			DescProducto descuentosType = objFactory.createOrdenDetallesDetalleDescProducto();
//			List<CargoDescuento> descTypeList = descuentosType.getDescuento();
			detalle.setDescProducto(descuentosType);

			// Cargos de los productos
			CargosProd cargosType =	objFactory.createOrdenDetallesDetalleCargosProd();
//			List<CargoDescuento> cargosTypeList = cargosType.getCargo();
			detalle.setCargosProd(cargosType);

			
			detalle.setEan1(itemdata.getEan1());
			detalle.setEan2(itemdata.getEan2());
			detalle.setEan3(itemdata.getEan3());
			
			detallesList.add(detalle);
		}
		return detalles;
	}

	private EdiData setEdiData(ObjectFactory objFactory, VendorW vendor, OrderW order, String regioncliente) throws OperationFailedException {
		EdiData qedidata = objFactory.createOrdenEdiData();
		RegionalLogisticConstants constants = RegionalLogisticConstants.getInstance();
		
		
		LocationW local;
		try {
			local = locationserver.getById(order.getDeliverylocationid());
		} catch (NotFoundException e) {
			throw new OperationFailedException(e.getMessage());
		}
		
		
		qedidata.setSenderIdentification(B2BSystemPropertiesUtil.getProperty("SenderIdentification"));
		//Modificado para enviar e vez del RUT el nuevo campo GLN. MHE 27-07-2016
		qedidata.setBillToPartner("");
		qedidata.setRecipientIdentification(vendor.getGln());//vendor.getRut());
		qedidata.setCorrelativeVendor("");
		qedidata.setMessageReferenceNumber(B2BSystemPropertiesUtil.getProperty("MessageReferenceNumber"));
		qedidata.setCountrycode(B2BSystemPropertiesUtil.getProperty("Countrycode"));
		qedidata.setBuyerCode(B2BSystemPropertiesUtil.getProperty("BuyerCode"));
		//MHE verificar si es una orden de LG y realizar la logica requerida MHE 27-07-2016
		String buyerlocationcode = isLGOrder(vendor.getCode()) ? getLGBuyerLocationCode(order, regioncliente,local.getCode() ) : local.getCode();
		qedidata.setBuyerLocationCode(buyerlocationcode);

		return qedidata;
	}

	private Locales setLocales(ObjectFactory objFactory, Long orderid, Long deliverylocationid, Long salestore) throws NotFoundException, OperationFailedException {
		Locales locales = objFactory.createOrdenLocales();
		List<Local> localesList = locales.getLocal();

		LocationW[] locations = locationserver.getByQueryAsArray("select distinct lo from Location as lo, PreDeliveryDetail as pdd where pdd.location = lo and pdd.orderdetail.order.id = " + orderid);
		List<Long> locationsinpred = new ArrayList<Long>();
		for (int i = 0; i < locations.length; i++) {
			LocationW location = locations[i];
			Local local = objFactory.createOrdenLocalesLocal();
			local.setCod(location.getCode());
			local.setNombre(location.getName());
			local.setDireccion(location.getAddress());
			local.setEan("");
			localesList.add(local);
			locationsinpred.add(location.getId());
		}
		// Se agrega el local de entrega de la OC en caso que no se encuentre en la predistribución
		if (deliverylocationid != null && deliverylocationid > 0 && !locationsinpred.contains(new Long(deliverylocationid))) {
			LocationW location = locationserver.getById(deliverylocationid);
			Local local = objFactory.createOrdenLocalesLocal();
			local.setCod(location.getCode());
			local.setNombre(location.getName());
			local.setDireccion(location.getAddress());
			local.setEan("");
			localesList.add(local);
		}

		// Se agrega el local de venta de la OC en caso que no se encuentre en la predistribución
		if (salestore != null && salestore > 0 && locationsinpred.contains(new Long(salestore))) {
			LocationW location = locationserver.getById(salestore);
			Local local = objFactory.createOrdenLocalesLocal();
			local.setCod(location.getCode());
			local.setNombre(location.getName());
			local.setDireccion(location.getAddress());
			local.setEan("");
			localesList.add(local);
		}
		
		return locales;
	}

	private Predistribuciones setPredistribucion(ObjectFactory objFactory, Long orderid, Map<Long, PreDeliveryDetailW[]> predetaildataMap, Map<Long, LocationW> predeliverylocationsMap, Map<Long, VendorItemDataW> itemsMap) throws NotFoundException, OperationFailedException {
		Predistribuciones predistType = objFactory.createOrdenPredistribuciones();
		List<Predistribucion> predisTypeList = predistType.getPredistribucion();

		// Obtengo la predistribución desde el mapa
		PreDeliveryDetailW[] predeliveries = (PreDeliveryDetailW[]) predetaildataMap.get(orderid);
		for (int j = 0; j < predeliveries.length; j++) {
			PreDeliveryDetailW predelivery = predeliveries[j];
			Predistribucion detallepred = objFactory.createOrdenPredistribucionesPredistribucion();
			Long itemid = predelivery.getItemid();
			Long locationid = predelivery.getLocationid();
			VendorItemDataW itemdata = (VendorItemDataW) itemsMap.get(itemid);
			LocationW location = (LocationW) predeliverylocationsMap.get(locationid);
			detallepred.setCodLocal(location.getCode());
			detallepred.setSku(itemdata.getInternalcode());
			detallepred.setCantidad(String.valueOf(predelivery.getUnits()));
			predisTypeList.add(detallepred);
		}
		return predistType;
	}

	private Vendedor setVendedor(ObjectFactory objFactory, Long vendorid) throws NotFoundException, OperationFailedException {
		Vendedor vendedor = objFactory.createOrdenVendedor();
			
		// Se busca el proveedor en la DB
		VendorW vendor = vendorserver.getById(vendorid);
		vendedor.setRut(vendor.getCode());
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
		cliente.setRut(client.getRut() != null ? client.getRut() : "");
		cliente.setTelefono(client.getPhone() != null ? client.getPhone() : "");
		cliente.setDireccion(client.getAddress() != null ? client.getAddress() : "");
		cliente.setNumcalle(client.getRoadnumber() != null ? client.getRoadnumber() : "");
		cliente.setNumdepto(client.getDeptnumber() != null ? client.getDeptnumber() : "");
		cliente.setNumcasa(client.getHousenumber() != null ? client.getHousenumber() : "");
		cliente.setRegion(client.getRegion() != null ? client.getRegion() : "");
		cliente.setComuna(client.getCommune() != null ? client.getCommune() : "");
		cliente.setCiudad(client.getCity() != null ? client.getCity() : "");
		cliente.setComentario(client.getComment() != null ? client.getComment() : "");
		return cliente;
	}
	
	@Resource
	private ManagedExecutorService executor;

	private void doBackUpMessage(String content, String number, String msgType){
		
		// EJECUTA UNA TAREA QUE RESPALDA EL MENSAJE.
		// ESTA ES INDEPENDIENTE DE LA CARGA DEL MENSAJE.
		try{
			executor.submit(new BackUpUtils(content, number, msgType));			
		}catch (RejectedExecutionException e) {
			e.printStackTrace();
		}			
	}

	/*
	 * CU143 Flujo alternativo 5A1
	 * Funcion que verifica que en caso de que el vendor sea LG
	 * Si es una venta en verde, compara las regiones del cliente, si es METROPOLITANA se le coloca 
	 * un código y si no es metropilitana se le coloca otro código
	 * En caso de no ser una venta en verde, se verifica si es de Paris o Johnson
	 * Para ello se usa el local de la orden, si se corresponde con el centro de distribucion de Jhonson
	 * se le coloca un código de jhonson sino es Paris
	 */
	private String getLGBuyerLocationCode(OrderW order, String regioncliente,String localcode){
		
		String result = "";
		RegionalLogisticConstants constants = RegionalLogisticConstants.getInstance();

		String RegionMetropolitanaCode = "METROPOLITANA"; 
		String CodigoVTAVRSTGO = "VTAVRDESTGO-S";
		String CodigoVTAVRSREG_OTRAS = "VTAVRDEREG-S";
		String JhonsonCDCode =  "400";

		try {
			RegionMetropolitanaCode = B2BSystemPropertiesUtil.getProperty("RegionMetropolitanaCode");
			JhonsonCDCode = B2BSystemPropertiesUtil.getProperty("JhonsonCDCode");
		} catch (Exception e) {
			RegionMetropolitanaCode = "METROPOLITANA"; 
			JhonsonCDCode =  "400";
		}
		RegionMetropolitanaCode = RegionMetropolitanaCode.toUpperCase();
		if (regioncliente != null)
			regioncliente = regioncliente.toUpperCase();
		
		// verificar si es una Venta en Verde
		boolean isVV = order.getVevcd();
		// Si es VV hay que comparar la region  si es Metropolitana
		if (isVV){

			if ( regioncliente != null && regioncliente.contains(RegionMetropolitanaCode) ){
				result = CodigoVTAVRSTGO;				
			}else{
				result = CodigoVTAVRSREG_OTRAS;	
			}

		}else{
			//Verificar  el local de entrega si es un CD = 400 es el de jhonson sino es Paris
			if ( localcode.compareTo(JhonsonCDCode) == 0){
				result = "SHIP3525-S";
			}
			else{
				result = "SHIP0009";
			}					
		}
		return result;
	}

	/*
	 * Utility para verificar que el código del vendedor se corresponde con LG 
	 * 
	 */
	private boolean isLGOrder(String vendorCode){
		boolean result = false;
		RegionalLogisticConstants constants = RegionalLogisticConstants.getInstance();

			//Tomar el código de LG
		String LGCode = B2BSystemPropertiesUtil.getProperty("LGCode");
		return vendorCode.compareTo(LGCode)== 0;	


	}

}
