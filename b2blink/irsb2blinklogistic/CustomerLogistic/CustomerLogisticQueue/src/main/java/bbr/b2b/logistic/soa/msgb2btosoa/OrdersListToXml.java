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
import org.jboss.annotation.ejb.TransactionTimeout;

import bbr.b2b.b2blink.logistic.xml.OC_Interno.CargoDescuento;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden;
import bbr.b2b.b2blink.logistic.xml.OC_Interno.Orden.Action;
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
import bbr.b2b.b2blink.logistic.xml.OC_Interno.TipoCargoDescuento;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.customer.classes.ActionServerLocal;
import bbr.b2b.logistic.customer.classes.BuyerServerLocal;
import bbr.b2b.logistic.customer.classes.ClientServerLocal;
import bbr.b2b.logistic.customer.classes.DetailChargeServerLocal;
import bbr.b2b.logistic.customer.classes.DetailDiscountServerLocal;
import bbr.b2b.logistic.customer.classes.DetailServerLocal;
import bbr.b2b.logistic.customer.classes.HomologationServerLocal;
import bbr.b2b.logistic.customer.classes.LocationServerLocal;
import bbr.b2b.logistic.customer.classes.OrderChargeServerLocal;
import bbr.b2b.logistic.customer.classes.OrderDiscountServerLocal;
import bbr.b2b.logistic.customer.classes.OrderServerLocal;
import bbr.b2b.logistic.customer.classes.OrderStateServerLocal;
import bbr.b2b.logistic.customer.classes.OrderStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.OrderTypeB2BServerLocal;
import bbr.b2b.logistic.customer.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.customer.classes.PredistributionServerLocal;
import bbr.b2b.logistic.customer.classes.SectionServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.VendorServerLocal;
import bbr.b2b.logistic.customer.data.wrappers.ActionW;
import bbr.b2b.logistic.customer.data.wrappers.BuyerW;
import bbr.b2b.logistic.customer.data.wrappers.ClientW;
import bbr.b2b.logistic.customer.data.wrappers.DetailChargeW;
import bbr.b2b.logistic.customer.data.wrappers.DetailDiscountW;
import bbr.b2b.logistic.customer.data.wrappers.DetailW;
import bbr.b2b.logistic.customer.data.wrappers.HomologationW;
import bbr.b2b.logistic.customer.data.wrappers.LocationW;
import bbr.b2b.logistic.customer.data.wrappers.OrderChargeW;
import bbr.b2b.logistic.customer.data.wrappers.OrderDiscountW;
import bbr.b2b.logistic.customer.data.wrappers.OrderTypeB2BW;
import bbr.b2b.logistic.customer.data.wrappers.OrderTypeW;
import bbr.b2b.logistic.customer.data.wrappers.OrderW;
import bbr.b2b.logistic.customer.data.wrappers.PredistributionW;
import bbr.b2b.logistic.customer.data.wrappers.SectionW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateTypeW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateW;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.logistic.utils.BackUpUtils;

@Stateless(name = "handlers/OrdersListToXml")
public class OrdersListToXml implements OrdersListToXmlLocal {
	
	String codeFarfana = B2BSystemPropertiesUtil.getProperty("LA_FARFANA_CODE");
	String stgo_code = B2BSystemPropertiesUtil.getProperty("STGO_CODE");
	String shiptoLoespejo = B2BSystemPropertiesUtil.getProperty("LO_ESPEJO_SHIPTO");
	String vta_reg_shipto = B2BSystemPropertiesUtil.getProperty("VTA_REG_SHIPTO");
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
	SchedulerManagerLocal schedulermanager;

	@EJB
	VendorServerLocal vendorServerLocal = null;

	@EJB
	LocationServerLocal locationServerLocal = null;

	@EJB
	DetailServerLocal detailServerLocal = null;

	@EJB
	OrderServerLocal orderServerLocal = null;

	@EJB
	OrderStateTypeServerLocal orderstatetypeserver;

	@EJB
	SoaStateServerLocal soaorderstateserver = null;

	@EJB
	OrderStateServerLocal orderstateserver;

	@EJB
	SoaStateTypeServerLocal soaStateTypeServer;

	@EJB
	BuyerServerLocal buyerServerLocal;

	@EJB
	OrderTypeServerLocal orderTypeServerLocal;
	
	@EJB
	OrderTypeB2BServerLocal orderTypeB2BServerLocal;

	@EJB
	ClientServerLocal clientserver;

	@EJB
	OrderDiscountServerLocal orderDiscountServerLocal;

	@EJB
	OrderChargeServerLocal orderChargeServerLocal;

	@EJB
	DetailChargeServerLocal detailChargeServerLocal;

	@EJB
	DetailDiscountServerLocal detailDiscountServerLocal;

	@EJB
	PredistributionServerLocal predistributionServerLocal;

	@EJB
	HomologationServerLocal homologationServerLocal;

	@EJB
	SectionServerLocal sectionServerLocal;

	@EJB
	ActionServerLocal actionServerLocal;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@TransactionTimeout(1800)
	public void processMessage(String vendorCode, boolean acceptorders) throws ServiceException {

		SoaStateTypeW soaNotfSt = soaStateTypeServer.getByPropertyAsSingleResult("code", "NOTIFICADO");
		// Obtener proveedor en base al rut
		VendorW vendorW = null;
		try {
			vendorW = vendorServerLocal.getByPropertyAsSingleResult("rut", vendorCode);
		} catch (OperationFailedException | NotFoundException ex) {
			throw new NotFoundException("No se pudo encontrar el proveedor con RUT " + vendorCode);
		}

		PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
		properties[0] = new PropertyInfoDTO("vendor.id", "vendor_id", vendorW.getId());
		properties[1] = new PropertyInfoDTO("soastatetype.id", "soastatetype_id", soaNotfSt.getId());
		List<OrderW> listOrderW = orderServerLocal.getByProperties(properties);

		if (listOrderW.isEmpty()) {
			logger.info("No se encontraron órdenes para enviar a SOA del proveedor " + vendorW.getName());
			return;
		}

		// SE CONSTRUYE EL XML
		ObjectFactory objFactory = new ObjectFactory();
		buildAndSendXML(objFactory, vendorW, listOrderW);

	}

	GregorianCalendar gcal = new GregorianCalendar(new Locale("es", "CL"));

	private void buildAndSendXML(ObjectFactory objFactory, VendorW vendor, List<OrderW> listOrderW)
			throws OperationFailedException, NotFoundException {
		Date now = new Date();
		SoaStateTypeW soaEnvSt = soaStateTypeServer.getByPropertyAsSingleResult("code", "ENVIADO");
		List<BuyerW> buyers = this.buyerServerLocal.getAll();
		for (OrderW orderW : listOrderW) {
			try {
				BuyerW buyer = buyers.stream().filter(o -> o.getId().equals(orderW.getBuyerid())).findAny()
						.orElse(null);
				
				Orden qlistaOC = objFactory.createOrden();
				qlistaOC.setNombreportal(buyer.getSitename());
				qlistaOC.setNo(orderW.getNumber());
				// Definición de RAG según documentación EDI
				qlistaOC.setDigitoVerificador(orderW.getNumber().intValue() % 10); 
				//qlistaOC.setTicket(orderW.getTicket() != null ? orderW.getTicket() : "");
				qlistaOC.setNosolicitud(orderW.getRequest() != null ? orderW.getRequest() : "");
				qlistaOC.setNumRef1(orderW.getNumref1());
				qlistaOC.setNumRef2(orderW.getNumref2());
				qlistaOC.setNumRef3(orderW.getNumref3());
				qlistaOC.setNroBoleta(orderW.getReceiptnumber());
				qlistaOC.setEstadoOc(orderW.getStatus() != null ? orderW.getStatus() : "");

				
				OrderTypeW orderType = orderTypeServerLocal.getById(orderW.getOrdertypeid());
				if(orderType.getOrdertypeb2bid() == null || orderType.getOrdertypeb2bid() == 0){
					throw new OperationFailedException("Tipo de orden no tiene un tipo de orden bbr asignado");
				}
				OrderTypeB2BW orderTypeB2BW = orderTypeB2BServerLocal.getById(orderType.getOrdertypeb2bid());
				qlistaOC.setTipoOc(orderType.getName());
				
				qlistaOC.setTipoOcBbr(orderTypeB2BW.getCode());
				
				if(orderW.getSectionid() > 0) {
					SectionW sectionW = sectionServerLocal.getById(orderW.getSectionid());
					Seccion seccion = new Seccion();
					seccion.setCodSeccion(sectionW.getCode());
					seccion.setSeccion(sectionW.getName());
					qlistaOC.setSeccion(seccion);
				}else{
					Seccion seccion = new Seccion();
					seccion.setCodSeccion("");
					seccion.setSeccion("");
					qlistaOC.setSeccion(seccion);
				}
				
				qlistaOC.setFechaEmision(getGregorianDate(orderW.getIssue_date() != null ? orderW.getIssue_date() : now));
				qlistaOC.setFechaVigencia(getGregorianDate(orderW.getEffectiv_date() != null ? orderW.getEffectiv_date() : now));
				qlistaOC.setFechaVto(getGregorianDate(orderW.getExpiration_date() != null ? orderW.getExpiration_date() : now));
				qlistaOC.setFechaCompromiso(getGregorianDate(orderW.getCommitment_date() != null ? orderW.getCommitment_date() : now));

				LocationW deliveryPlaceW = null;
				if (orderW.getDeliveryplaceid() != null && orderW.getDeliveryplaceid() > 0) {
					deliveryPlaceW = locationServerLocal.getById(orderW.getDeliveryplaceid());
					qlistaOC.setCodLocalEntrega(deliveryPlaceW.getCode());
					qlistaOC.setDescLocalEntrega(deliveryPlaceW.getName());
				} else {
					qlistaOC.setCodLocalEntrega("");
				}

				LocationW salePlaceW = null;
				if (orderW.getSaleplaceid() != null && orderW.getSaleplaceid() > 0) {
					salePlaceW = locationServerLocal.getById(orderW.getSaleplaceid());
					qlistaOC.setCodigoLocalVenta(salePlaceW.getCode());
					qlistaOC.setDescLocalVenta(salePlaceW.getName());
				} else {
					qlistaOC.setCodigoLocalVenta("");
				}

				qlistaOC.setFormaPago(orderW.getPayment_condition() != null ? orderW.getPayment_condition() : "");

				// Comentarios
				qlistaOC.setComentarios(orderW.getObservation() != null ? orderW.getObservation() : "");

				// Responsable
				qlistaOC.setResponsable(orderW.getResponsible() != null ? orderW.getResponsible() : "");
				qlistaOC.setEmailResponsable(orderW.getResponsible_email());

				qlistaOC.setMoneda(orderW.getCurrency() != null ? orderW.getCurrency() : "");
				qlistaOC.setEmailResponsable(orderW.getResponsible_email() != null ? orderW.getResponsible_email() : "");

				// Comprador
				Comprador comprador = setComprador(objFactory, buyer, orderW.getBuyerid());
				qlistaOC.setComprador(comprador);

				// Vendedor
				Vendedor vendedor = setVendedor(objFactory, orderW.getVendorid());
				qlistaOC.setVendedor(vendedor);

				// Cliente
				ClientW client = null;
				if(orderW.getClientid() != null && orderW.getClientid() > 0){
					client = clientserver.getById(orderW.getClientid());
					qlistaOC.setCliente(setCliente(objFactory, client));
				}
				
				// Action
				qlistaOC.setAction(setAction(objFactory, orderW.getActionid()));

				// Información EDI
				qlistaOC.setEdiData(setEdiData(objFactory, buyer, vendor, deliveryPlaceW, orderW, orderType, client));
				qlistaOC.setDescGenerales(setOrdenDiscounts(objFactory, orderW));
				qlistaOC.setCargosGenerales(setOrdenCharges(objFactory, orderW));

				qlistaOC.setTotal(orderW.getTotal() != null ? orderW.getTotal() : 0);
				
				List<DetailW> listDetailW = detailServerLocal.getByProperty("id.orderid", orderW.getId());
				// Detalle de OC
				qlistaOC.setDetalles(setDetalle(objFactory, orderW, listDetailW));

				// Locales
				List<PredistributionW> listPredistribucionW = predistributionServerLocal.getByProperty("id.orderid",orderW.getId());
				List<LocationW> listLocations = getLocales(orderW, listPredistribucionW);
				qlistaOC.setLocales(setLocales(objFactory, listLocations));

				// Predistribución de OC
				qlistaOC.setPredistribuciones(setPredistribucion(objFactory, orderW, listPredistribucionW, listDetailW, listLocations));

				SendXML(orderW.getId(), soaEnvSt.getId(), qlistaOC);
				// Se marca la OC como enviada a SOA
				orderW.setSoastatetypeid(soaEnvSt.getId());
				orderServerLocal.updateOrder(orderW);
				logger.info("BBR SOA: Se ha enviado a SOA la orden número " + orderW.getNumber());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private Action setAction(ObjectFactory objFactory, Long actionid)
			throws OperationFailedException, NotFoundException  {
		if (actionid != null && actionid > 0) {
			Action action = objFactory.createOrdenAction();
			ActionW actionW = actionServerLocal.getById(actionid);
			action.setCode(actionW.getCode());
			action.setDescription(actionW.getName());
			return action;
		}
		return null;
	}

	private DescGenerales setOrdenDiscounts(ObjectFactory objFactory, OrderW orderW) {
		try {
			DescGenerales descGrales = objFactory.createOrdenDescGenerales();
			List<CargoDescuento> descgralsList = descGrales.getDescuento();
			List<OrderDiscountW> ListOrderDiscount = orderDiscountServerLocal.getByProperty("order.id", orderW.getId());
			for (OrderDiscountW orderDiscountW : ListOrderDiscount) {
				CargoDescuento dscto = objFactory.createCargoDescuento();
				
				dscto.setCodigo(orderDiscountW.getCode() != null ? orderDiscountW.getCode() : "");
				//dscto.setCodigo(orderDiscountW.getCode());
				dscto.setGlosa(orderDiscountW.getDescription());
				dscto.setTipo(
						orderDiscountW.getProcentaje() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
				if (orderDiscountW.getProcentaje()) {
					dscto.setPorcentaje(orderDiscountW.getValue().floatValue());
				} else {
					dscto.setMonto(orderDiscountW.getValue().floatValue());
				}
				descgralsList.add(dscto);
			}
			return descGrales;
		} catch (OperationFailedException | NotFoundException e) {
			logger.info("Order: " + orderW.getNumber() + " no tiene descuentos asociados");
			return null;
		}
	}

	private CargosGenerales setOrdenCharges(ObjectFactory objFactory, OrderW orderW) {
		try {
			CargosGenerales cargosGrales = objFactory.createOrdenCargosGenerales();
			List<CargoDescuento> cargosgralsList = cargosGrales.getCargo();
			List<OrderChargeW> ListOrderCharge;
			ListOrderCharge = orderChargeServerLocal.getByProperty("order.id", orderW.getId());
			for (OrderChargeW orderDiscountW : ListOrderCharge) {
				CargoDescuento cargo = objFactory.createCargoDescuento();
				
				cargo.setCodigo(orderDiscountW.getCode() != null ? orderDiscountW.getCode() : "");
				//cargo.setCodigo(orderDiscountW.getCode());
				cargo.setGlosa(orderDiscountW.getDescription());
				cargo.setTipo(
						orderDiscountW.getProcentaje() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
				if (orderDiscountW.getProcentaje()) {
					cargo.setPorcentaje(orderDiscountW.getValue().floatValue());
				} else {
					cargo.setMonto(orderDiscountW.getValue().floatValue());
				}
				cargosgralsList.add(cargo);
			}
			return cargosGrales;
		} catch (OperationFailedException | NotFoundException e) {
			logger.info("Order: " + orderW.getNumber() + " no tiene cargos asociados");
			return null;
		}
	}

	private void SendXML(Long orderId, Long soaEnvId, Orden qlistaOC)
			throws OperationFailedException, JAXBException, AccessDeniedException, NotFoundException {
		JAXBContext jc = getJC();
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		m.marshal(qlistaOC, sw);
		String result = sw.toString();

		// RESPALDA MENSAJE
		doBackUpMessage(result, String.valueOf(qlistaOC.getNo()), "OCInterno_"+qlistaOC.getNombreportal());

		logger.info(result);
		schedulermanager.doAddMessageQueue("jboss/qcf_soa", "jboss/activemq/queue/q_esbgrl", "OcSoa",String.valueOf(qlistaOC.getNo()), result, Charset.forName("UTF-8"));
		Date now = new Date();
		SoaStateW soastate = new SoaStateW();
		soastate.setOrderid(orderId);
		soastate.setSoastatetypeid(soaEnvId);
		soastate.setWhen(now);
		soastate = soaorderstateserver.addSoaState(soastate);

	}

	private Comprador setComprador(ObjectFactory objFactory, BuyerW buyer, Long buyerid)
			throws NotFoundException, OperationFailedException {
		Comprador comprador = objFactory.createOrdenComprador();
		// Se busca la sociedad en la DB
		comprador.setRut(buyer.getRut());
		comprador.setDigitoVerificador(buyer.getDv());
		comprador.setRazonSocial(buyer.getRazonsocial());
		comprador.setUnidadNegocio(buyer.getBusinessarea() != null ? buyer.getBusinessarea() : "");
		return comprador;
	}

	private Detalles setDetalle(ObjectFactory objFactory, OrderW orderW, List<DetailW> listDetailW)
			throws OperationFailedException, DatatypeConfigurationException {
		Detalles detalles = objFactory.createOrdenDetalles();
		List<Detalle> detallesList = detalles.getDetalle();
		for (DetailW detail : listDetailW) {
			Detalle detalle = objFactory.createOrdenDetallesDetalle();
			
			
			detalle.setItem(detail.getPosition());
			
			// Cod. producto comprador
			detalle.setCodProdComp(detail.getSkubuyer());

			// Cod. producto vendedor
			detalle.setCodProdVendedor(detail.getSkuvendor() != null ? detail.getSkuvendor() : "");

			// EAN13
			detalle.setEan13(detail.getEan13() != null ? detail.getEan13() : "");

			// Descripción producto
			detalle.setDescripcionProd(detail.getProduct_description() != null ? detail.getProduct_description() : "");

			detalle.setFechaEntregaProducto(detail.getProduct_delivery_date() != null ? getGregorianDate(detail.getProduct_delivery_date()) : null);

			detalle.setDescEmpaque(detail.getDescription_umc());
			detalle.setDescUmUnidad(detail.getDescription_umb());

			detalle.setCodEmpaque(detail.getCode_umc());

			detalle.setProdEmpaque(detail.getQuantity_umc() != null ? detail.getQuantity_umc() : 0);
			detalle.setInnerpack(detail.getInnerpack() != null ? Math.toIntExact(detail.getInnerpack()) : 0);
			detalle.setCantidadEmpaque(detail.getUmb_x_umc() != null ? detail.getQuantity_umc() * detail.getUmb_x_umc() : 0);

			detalle.setTolerancia(detail.getTolerance());
			detalle.setCostoFlete(detail.getFreight_cost());
			detalle.setPesoFlete(detail.getFreight_weight());
			detalle.setObservacion(detail.getObservation() != null ? detail.getObservation() : "");
			detalle.setNumRef1(detail.getNumref1() != null ? detail.getNumref1() : "");
			detalle.setNumRef2(detail.getNumref2() != null ? detail.getNumref2() : "");

			
			detalle.setCostoLista(detail.getList_cost() != null ? detail.getList_cost() : 0);
			detalle.setCostoFinal(detail.getFinal_cost() != null ? detail.getFinal_cost() : 0);
			detalle.setPrecioLista(detail.getList_price() != null ? detail.getList_price() : 0 );
			

			detalle.setCostoConImpuesto(detail.getCostaftertaxes());
			detalle.setRubro(detail.getItem());
			detalle.setEan1(detail.getEan1());
			detalle.setEan2(detail.getEan2());
			detalle.setEan3(detail.getEan3());
			detalle.setCodEstilo(detail.getStylecode());
			detalle.setDescEstilo(detail.getStyledescription());
			detalle.setMarcaEstilo(detail.getStylebrand());

			// Descuentos y cargos
			//´PASAR EL SKU Y FILTRAR
			detalle.setDescProducto(setDetailDiscounts(objFactory, orderW,detail.getSkubuyer()));
			detalle.setCargosProd(setDetailCharges(objFactory, orderW,detail.getSkubuyer()));

			detallesList.add(detalle);
		}
		return detalles;
	}

	private CargosProd setDetailCharges(ObjectFactory objFactory,OrderW orderW,String sku) {
		try {
			CargosProd cargosType = objFactory.createOrdenDetallesDetalleCargosProd();
			List<CargoDescuento> cargosTypeList = cargosType.getCargo();
			
			//Hacer query por order.id y sku
			PropertyInfoDTO[] propertiesVendor = new PropertyInfoDTO[2];
			propertiesVendor[0] = new PropertyInfoDTO("id.orderid", "order_id",orderW.getId());
			propertiesVendor[1] = new PropertyInfoDTO("id.skubuyer", "sku_buyer",sku);
			List<DetailChargeW> listDetailChargeW = detailChargeServerLocal.getByProperties(propertiesVendor);
			
			for (DetailChargeW detailCharge : listDetailChargeW) {
				CargoDescuento cargo = objFactory.createCargoDescuento();
				
				cargo.setCodigo(detailCharge.getCode() != null ? detailCharge.getCode() : "");
				//cargo.setCodigo(detailCharge.getCode());
				cargo.setGlosa(detailCharge.getDescription());
				cargo.setTipo(detailCharge.getProcentaje() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
				if (detailCharge.getProcentaje()) {
					cargo.setMonto(0);
					cargo.setPorcentaje(detailCharge.getValue().floatValue());
				} else {
					cargo.setMonto(detailCharge.getValue().floatValue());
					cargo.setPorcentaje(0);
				}
				cargosTypeList.add(cargo);
			}
			return cargosType;
		} catch (OperationFailedException | NotFoundException e) {
			logger.info("Order: " + orderW.getNumber() + " detalle de la orden no tiene cargos asociados");
			return null;
		}
	}

	private DescProducto setDetailDiscounts(ObjectFactory objFactory,OrderW orderW ,String sku) {
		try {
			DescProducto descuentosType = objFactory.createOrdenDetallesDetalleDescProducto();
			List<CargoDescuento> descTypeList = descuentosType.getDescuento();
			
			//Hacer query por order.id y sku
			PropertyInfoDTO[] propertiesVendor = new PropertyInfoDTO[2];
			propertiesVendor[0] = new PropertyInfoDTO("id.orderid", "order_id",orderW.getId());
			propertiesVendor[1] = new PropertyInfoDTO("id.skubuyer", "sku_buyer",sku);
			List<DetailDiscountW> listDetailDiscountW = detailDiscountServerLocal.getByProperties(propertiesVendor);
			for (DetailDiscountW detailCharge : listDetailDiscountW) {
				CargoDescuento descuento = objFactory.createCargoDescuento();
				
				descuento.setCodigo(detailCharge.getCode() != null ? detailCharge.getCode() : "");
				//descuento.setCodigo(detailCharge.getCode());
				descuento.setGlosa(detailCharge.getDescription());		
				descuento.setTipo(detailCharge.getProcentaje() ? TipoCargoDescuento.PORCENTAJE : TipoCargoDescuento.MONTO);
				if (detailCharge.getProcentaje()) {
					descuento.setMonto(0);
					descuento.setPorcentaje(detailCharge.getValue().floatValue());
				} else {
					descuento.setMonto(detailCharge.getValue().floatValue());
					descuento.setPorcentaje(0);
				}
				descTypeList.add(descuento);
			}
			return descuentosType;
		} catch (OperationFailedException | NotFoundException e) {
			logger.info("Order: " + orderW.getNumber() + " detalle de la orden no tiene descuentos asociados");
			return null;
		}
	}

	private EdiData setEdiData(ObjectFactory objFactory, BuyerW buyerW, VendorW vendorW, LocationW deliveryPlace, OrderW orderW, OrderTypeW orderTypeW, ClientW client)
			throws ServiceException {
		EdiData qedidata = objFactory.createOrdenEdiData();
		qedidata.setSenderIdentification(buyerW.getGln());
		qedidata.setRecipientIdentification(vendorW.getGln());
		qedidata.setCorrelativeVendor("");
		qedidata.setMessageReferenceNumber("");
		qedidata.setCountrycode("");
		qedidata.setBuyerCode(buyerW.getCode());
		qedidata.setBillToPartner(buyerW.getBilltopartner() != null ? buyerW.getBilltopartner() : "");
		qedidata.setBuyerLocationCode(deliveryPlace != null ? getShipTo(buyerW, vendorW, deliveryPlace, orderW, orderTypeW, client) : "");
		return qedidata;
	}

	private String getShipTo(BuyerW buyer, VendorW vendor, LocationW deliveryPlace, OrderW orderW, OrderTypeW orderTypeW, ClientW client) throws ServiceException {
		//METODO SODIMAC-LG
		if (buyer.getCode().equals("CL1001")) { //SODIMAC
			if(vendor.getCode().equals("76014610")){ //LG
				
				if(orderTypeW.getCode().equals("eOC") || orderTypeW.getCode().equals("eOD") ) {
					String code = "";
					if(deliveryPlace.getCode().contains("214")){
						code = "eOC-214";
					}else {
						code = "eOC-OTRO";
					}
					try {
						PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
						properties[0] = new PropertyInfoDTO("buyer.id", "buyer_id", orderW.getBuyerid());
						properties[1] = new PropertyInfoDTO("vendor.id", "vendor_id", orderW.getVendorid());
						properties[2] = new PropertyInfoDTO("code", "code", code);
						List<HomologationW> listHomologationW = homologationServerLocal.getByProperties(properties);
						if (listHomologationW.size() > 1) {
							throw new ServiceException(
									"Order: " + orderW.getNumber() + " hay mas de una homologación para ShipTo");
						} else if (listHomologationW.size() == 1) {
								return listHomologationW.get(0).getValue();
						}
						
						return deliveryPlace.getCode();
					} catch (OperationFailedException | NotFoundException ex) {
						return deliveryPlace.getCode();
					}
				}else {
					String code = "";
					if(client.getRegion().contains("METROPOLITANA")) {
						code = orderTypeW.getCode() + "-" + "METROPOLITANA";
					}else{
						code = orderTypeW.getCode() + "-" + "OTRO";
					}
					try {
						PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
						properties[0] = new PropertyInfoDTO("buyer.id", "buyer_id", orderW.getBuyerid());
						properties[1] = new PropertyInfoDTO("vendor.id", "vendor_id", orderW.getVendorid());
						properties[2] = new PropertyInfoDTO("code", "code", code);
						List<HomologationW> listHomologationW = homologationServerLocal.getByProperties(properties);
						if (listHomologationW.size() > 1) {
							throw new ServiceException(
									"Order: " + orderW.getNumber() + " hay mas de una homologación para ShipTo");
						} else if (listHomologationW.size() == 1) {
							return listHomologationW.get(0).getValue();
						}
						return deliveryPlace.getCode();
					} catch (OperationFailedException | NotFoundException ex) {
						return deliveryPlace.getCode();
					}
				}
				

				
				
				
			} 
		}
		
		if (buyer.getCode().equals("CL1201")) { //ripley
			if(vendor.getCode().equals("76014610")){ //LG
				String code = orderTypeW.getCode() + "-" + client.getRegion();
				try {
					PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
					properties[0] = new PropertyInfoDTO("buyer.id", "buyer_id", orderW.getBuyerid());
					properties[1] = new PropertyInfoDTO("vendor.id", "vendor_id", orderW.getVendorid());
					properties[2] = new PropertyInfoDTO("code", "code", code);
					List<HomologationW> listHomologationW = homologationServerLocal.getByProperties(properties);
					if (listHomologationW.size() > 1) {
						throw new ServiceException(
								"Order: " + orderW.getNumber() + " hay mas de una homologación para ShipTo");
					} else if (listHomologationW.size() == 1) {
						return listHomologationW.get(0).getValue();
					}
					return deliveryPlace.getCode();
				} catch (OperationFailedException | NotFoundException ex) {
					return deliveryPlace.getCode();
				}
			} 
		}
		
		
		return deliveryPlace.getCode();
	}

	private List<LocationW> getLocales(OrderW orderW, List<PredistributionW> listPredistribucionW) throws OperationFailedException {
		try {
			
			Map<Long, LocationW> localesMap = new HashMap<Long, LocationW>();
			
			for (PredistributionW predistributionW : listPredistribucionW) {
				LocationW local = localesMap.get(predistributionW.getLocalid());
				if (local == null) {
					local = locationServerLocal.getById(predistributionW.getLocalid());
					localesMap.put(local.getId(), local);
				}
			}
			
			if (orderW.getDeliveryplaceid() != null && orderW.getDeliveryplaceid() > 0) {
				LocationW delivery = localesMap.get(orderW.getDeliveryplaceid());
				if (delivery == null) {
					delivery = locationServerLocal.getById(orderW.getDeliveryplaceid());
					localesMap.put(delivery.getId(), delivery);
				}
			}
			
			if (orderW.getSaleplaceid() != null && orderW.getSaleplaceid() > 0) {
				LocationW sale = localesMap.get(orderW.getSaleplaceid());
				if (sale == null) {
					sale = locationServerLocal.getById(orderW.getSaleplaceid());
					localesMap.put(sale.getId(), sale);
				}
			}
			
			if (localesMap.size() > 0) {
				List<LocationW> list = new ArrayList<LocationW>();
				for (LocationW item : localesMap.values()) {
					list.add(item);
				}
				return list;
			}
			return null;
		} catch (OperationFailedException | NotFoundException e) {
			throw new OperationFailedException("Orden: " + orderW.getNumber() + " no se encontraron los locales");
		}
	}

	private Locales setLocales(ObjectFactory objFactory, List<LocationW> list)throws NotFoundException, OperationFailedException {
		
		Locales locales = objFactory.createOrdenLocales();
		List<Local> localesList = locales.getLocal();
		
		for (LocationW locationW : list) {
			Local local = objFactory.createOrdenLocalesLocal();
			local.setCod(locationW.getCode());
			local.setNombre(locationW.getName());
			local.setDireccion(locationW.getName());
			local.setEan(locationW.getEan13());
			localesList.add(local);
		}
		return locales;
	}

	private Predistribuciones setPredistribucion(ObjectFactory objFactory, OrderW orderW,
			List<PredistributionW> listPredistribucionW, List<DetailW> listDetailW, List<LocationW> listLocations)
			throws NotFoundException, OperationFailedException {
		Predistribuciones predistType = objFactory.createOrdenPredistribuciones();
		List<Predistribucion> predisTypeList = predistType.getPredistribucion();
		for (PredistributionW predistributionW : listPredistribucionW) {
			int position = listDetailW.stream().filter(o -> o.getSkubuyer().equals(predistributionW.getSkubuyer()))
					.findFirst().get().getPosition();
			String localCode = listLocations.stream().filter(o -> o.getId().equals(predistributionW.getLocalid()))
					.findFirst().get().getCode();

			Predistribucion detallepred = objFactory.createOrdenPredistribucionesPredistribucion();
			//detallepred.setPosicion(position);
			detallepred.setCodLocal(localCode);
			detallepred.setSku(predistributionW.getSkubuyer());
			detallepred.setCantidad(String.valueOf(predistributionW.getQuantity()));
			detallepred.setCantidadRecepcionada(predistributionW.getReceived_quantity());
			detallepred.setCantidadPendiente(predistributionW.getPending_quantity());
			detallepred.setCantidadEndespacho(predistributionW.getShipping_quantity());
			predisTypeList.add(detallepred);
		}
		return predistType;
	}

	private Vendedor setVendedor(ObjectFactory objFactory, Long vendorid)
			throws NotFoundException, OperationFailedException {
		Vendedor vendedor = objFactory.createOrdenVendedor();
		VendorW vendor = vendorServerLocal.getById(vendorid);
		vendedor.setRut(vendor.getRut());
		vendedor.setCode(vendor.getGln());
		vendedor.setRazonSocial(vendor.getName());
		vendedor.setDigitoVerificador(vendor.getDv() != null ? vendor.getDv() : "");
		vendedor.setContacto(vendor.getEmail() != null ? vendor.getEmail() : "");
		vendedor.setDireccion(vendor.getAddress() != null ? vendor.getAddress() : "");
		vendedor.setTelefono(vendor.getPhone() != null ? vendor.getPhone() : "");
		return vendedor;
	}

	private Cliente setCliente(ObjectFactory objFactory, ClientW client)
			throws JAXBException, OperationFailedException, NotFoundException {
		//if (client != null) {
			Cliente cliente = objFactory.createOrdenCliente();
			
			cliente.setNombre(client.getName() != null ? client.getName() : "");
			cliente.setRut((client.getIdentity() != null ? client.getIdentity() : "") + (client.getCheck_digit() != null ? "-" + client.getCheck_digit() : ""));
			cliente.setTelefono(client.getPhone() != null ? client.getPhone() : "");
			cliente.setTelefonoAlternativo(client.getPhone2() != null ? client.getPhone2() : "");
			cliente.setDireccion(client.getAddress() != null ? client.getAddress() : "");
			cliente.setNumcalle(client.getStreet_number() != null ? client.getStreet_number() : "");
			cliente.setNumdepto(client.getDepartmet_number() != null ? client.getDepartmet_number() : "");
			cliente.setNumcasa(client.getHouse_number() != null ? client.getHouse_number() : "");
			cliente.setRegion(client.getRegion() != null ? client.getRegion() : "");
			cliente.setComuna(client.getCommune() != null ? client.getCommune() : "");
			cliente.setCiudad(client.getCity() != null ? client.getCity() : "");
			cliente.setComentario(client.getObservation() != null ? client.getObservation() : "");
			cliente.setLocalidad(client.getLocation() != null ? client.getLocation() : "");
			return cliente;
		//}//else {
////			Cliente cliente = objFactory.createOrdenCliente();
//			
////			cliente.setNombre("");
////			cliente.setRut(("")+ (""));
////			cliente.setTelefono("");
////			cliente.setTelefonoAlternativo("");
////			cliente.setDireccion("");
////			cliente.setNumcalle("");
////			cliente.setNumdepto("");
////			cliente.setNumcasa("");
////			cliente.setRegion("");
////			cliente.setComuna("");
////			cliente.setCiudad("");
////			cliente.setComentario("");
////			cliente.setLocalidad("");
////			return cliente;
//		}
	}

	private XMLGregorianCalendar getGregorianDate(Date date) throws DatatypeConfigurationException {
		gcal.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
	}
	
	//respaldo mensaje
	private void doBackUpMessage(String content, String number, String msgType) {
		try {
			executor.submit(new BackUpUtils(content, number, msgType));
		} catch (RejectedExecutionException e) {
			e.printStackTrace();
		}
	}

}
