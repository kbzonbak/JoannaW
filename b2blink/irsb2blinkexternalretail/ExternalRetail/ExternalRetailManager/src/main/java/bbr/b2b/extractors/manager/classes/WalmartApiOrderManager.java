package bbr.b2b.extractors.manager.classes;

import java.io.StringWriter;
import java.rmi.AccessException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.log4j.Logger;

import bbr.b2b.logistic.utils.ClassUtilities;
import bbr.b2b.b2blink.logistic.xml.OC_customer.DiscountCharge;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Local;
import bbr.b2b.b2blink.logistic.xml.OC_customer.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Action;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Client;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Charges;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Discounts;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Predistributions;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Predistributions.Predistribution;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Section;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.extractors.api.manager.classes.GenericResponseResultDTO;
import bbr.b2b.extractors.api.manager.classes.JsonUtils;
import bbr.b2b.extractors.api.manager.classes.RESTConnection;
import bbr.b2b.extractors.api.manager.classes.RESTContentType;
import bbr.b2b.extractors.manager.interfaces.RipleyApiOrderManagerLocal;
import bbr.b2b.extractors.manager.interfaces.WalmartApiOrderManagerLocal;
import bbr.b2b.extractors.ripley.dto.Ack;
import bbr.b2b.extractors.ripley.dto.AcknowledgeOrdenDTO;
import bbr.b2b.extractors.ripley.dto.AcknowledgeRequestDTO;
import bbr.b2b.extractors.ripley.dto.AcknowledgeResponseDTO;
import bbr.b2b.extractors.ripley.dto.ClientDTO;
import bbr.b2b.extractors.ripley.dto.DetailsDTO;
import bbr.b2b.extractors.ripley.dto.LoginResponseDTO;
import bbr.b2b.extractors.ripley.dto.OperativeInfoDTO;
import bbr.b2b.extractors.ripley.dto.OrderAckDTO;
import bbr.b2b.extractors.ripley.dto.OrderResponseDTO;
import bbr.b2b.extractors.ripley.dto.PurchaseOrderDTO;
import bbr.b2b.extractors.ripley.dto.SupplyDetailDTO;
import bbr.b2b.extractors.ripley.dto.SupplyOrderResponseDTO;
import bbr.b2b.extractors.ripley.dto.SupplyPurchaseOrderDTO;
import bbr.b2b.extractors.timers.manager.interfaces.SchedulerManagerLocal;
import bbr.b2b.extractors.utils.WsSoaService;
import bbr.b2b.extractors.walmart.dto.LoginDTO;
import bbr.b2b.extractors.walmart.dto.LoginResultDTO;
import bbr.b2b.extractors.walmart.dto.VeVOrderDTO;
import bbr.b2b.extractors.walmart.dto.VeVOrderListDTO;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.logistic.utils.JsonParser;
import bbr.esb.services.webservices.facade.InitParamCSDTO;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
@Stateless(name = "managers/WalmartApiOrderManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class WalmartApiOrderManager implements WalmartApiOrderManagerLocal {

	private static Logger logger = Logger.getLogger(WalmartApiOrderManager.class);
	private static Logger soaMessage = Logger.getLogger("SOAMessage");
	private static JAXBContext jc = null;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static final String SITECODE = "CL0701";
	private static final String SITENAME = "WALMART";

	@EJB
	SchedulerManagerLocal schmanager;

	@Resource
	private SessionContext ctx;

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.OC_customer");
		return jc;
	}

	public void doProcess() throws Exception {
		try {
			logger.info("Empresa: " + SITENAME + " SERVICIO DESCARGA ÓRDENES API");
			String service = "LOC";
			WsSoaService wsSoaService = new WsSoaService();
			List<InitParamCSDTO> credentials = wsSoaService.getCredentials(SITECODE, service);
			for (InitParamCSDTO credential : credentials) {
				try {
					logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname()
							+ " Obteniendo credenciales");
					JsonParser jsonParser = new JsonParser();
					HashMap<String, String> keys = jsonParser.JsonToHashMap(credential.getCredenciales(), "user",
							"password");

					LoginResultDTO loginResponseDTO = new LoginResultDTO(); //doLogin(keys.get("user"), keys.get("password"));
					loginResponseDTO.setAccess_token("11");
					if (loginResponseDTO == null || loginResponseDTO.getAccess_token().isEmpty()) {
						throw new AccessException("usuario y/o contraseña no válida");
					} else {
						try {
							VeVOrderDTO[] orders = doGetWSVeV(loginResponseDTO.getAccess_token());
							if (orders != null) {
								HashMap<String, List<VeVOrderDTO>> map = new HashMap<>();
								for (VeVOrderDTO order : orders) {
									if(!map.containsKey(order.getPurchaseOrder())){
										List<VeVOrderDTO> listOrders = new ArrayList<>();
										listOrders.add(order);
										map.put(order.getPurchaseOrder(), listOrders);
									} else {
										List<VeVOrderDTO> listOrders = map.get(order.getPurchaseOrder());
										listOrders.add(order);
										map.put(order.getPurchaseOrder(), listOrders);
									}
								
								}
								for(Map.Entry<String, List<VeVOrderDTO>> order : map.entrySet()) {
								    String key = order.getKey();
								    List<VeVOrderDTO> value = order.getValue();
								    doTransform(credential, value);
								    // do what you have to do here
								    // In your case, another loop.
								}
								
								
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					if (loginResponseDTO.getAccess_token() != null) {
					}
				} catch (Exception ex) {
					logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Error: VeV "
							+ ex.getMessage());
					throw ex;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public LoginResultDTO doLogin(String user, String pass) {

		LoginResultDTO result = new LoginResultDTO();

		LoginDTO login = new LoginDTO();
		login.setPassword(pass);
		login.setUsername(user);
		try {
			// CONVIERTE OBJETO A JSON
			String initiParamJson = JsonUtils.getJsonFromObject(login, LoginDTO.class);

			String url = B2BSystemPropertiesUtil.getProperty("VeVCredentialsURL");

			// SE REALIZA UNA OPERACION POST
			HttpPost httpPost = RESTConnection.getInstance().getHttpPostEndpoint(url, initiParamJson,
					RESTContentType.JSON);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpPost);

			if (!responseResult.getStatuscode().equals("0")) {
				result.setStatuscode(responseResult.getStatuscode());
				result.setStatusmessage(responseResult.getStatusmessage());
				logger.error(
						"Código: " + responseResult.getStatuscode() + " Mensaje: " + responseResult.getStatusmessage());
			} else {

				// CONVIERTE RESPUESTA JSON A OBJETO
				result = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), LoginResultDTO.class);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public VeVOrderDTO[] doGetWSVeV(String accessToken) {

		String response = "[{\"id\":\"5ee2f0013a7c9724e63b0c98\",\"purchaseDate\":\"2020-06-11T22:49:38.630Z\",\"purchaseOrder\":\"7400494593\",\"shippingGroupID\":50004861586,\"deliverTo\":\"Bodega\",\"warehouseCode\":\"6014\",\"warehouse\":\"Centro de distribución\",\"clientRUT\":\"\",\"clientName\":\"\",\"clientPhone\":\"\",\"clientAddress\":\"\",\"city\":\"SANTIAGO\",\"commitmentDate\":\"2020-06-18T00:00:00.000Z\",\"itemNumber\":583589,\"department\":15,\"productNumber\":\"\",\"barcode\":7807311547698,\"productDescription\":\"Estufa a Gas Vitale 40 CP\",\"innerPack\":1,\"cost\":89405,\"unitCost\":89405,\"quantity\":1,\"status\":\"Pendiente Envio\",\"dropShipDate\":\"2020-06-16T00:00:00.000Z\"},{\"id\":\"5ee2b2f03a7c9724e6325a62\",\"purchaseDate\":\"2020-06-11T18:28:25.000Z\",\"purchaseOrder\":\"5900569841\",\"shippingGroupID\":50004856943,\"deliverTo\":\"Bodega\",\"warehouseCode\":\"6014\",\"warehouse\":\"Centro de distribución\",\"clientRUT\":\"\",\"clientName\":\"\",\"clientPhone\":\"\",\"clientAddress\":\"\",\"city\":\"SANTIAGO\",\"commitmentDate\":\"2020-06-17T00:00:00.000Z\",\"itemNumber\":670405,\"department\":77,\"productNumber\":\"\",\"barcode\":7807311770522,\"productDescription\":\"Lavadora Superior 11.5 kg / Brilliant 11.5 SG\",\"innerPack\":1,\"cost\":169825,\"unitCost\":169825,\"quantity\":1,\"status\":\"Pendiente Envio\",\"dropShipDate\":\"2020-06-15T00:00:00.000Z\"},{\"id\":\"5ee2a4dd3a7c9724e6306242\",\"purchaseDate\":\"2020-06-11T17:22:19.473Z\",\"purchaseOrder\":\"5900569846\",\"shippingGroupID\":50004855844,\"deliverTo\":\"Bodega\",\"warehouseCode\":\"6014\",\"warehouse\":\"Centro de distribución\",\"clientRUT\":\"\",\"clientName\":\"\",\"clientPhone\":\"\",\"clientAddress\":\"\",\"city\":\"MARGA MARGA\",\"commitmentDate\":\"2020-06-18T00:00:00.000Z\",\"itemNumber\":637301,\"department\":77,\"productNumber\":\"\",\"barcode\":7807311733329,\"productDescription\":\"Cocina 4 Quemadores / 713B\",\"innerPack\":1,\"cost\":115623,\"unitCost\":115623,\"quantity\":1,\"status\":\"Pendiente Envio\",\"dropShipDate\":\"2020-06-16T00:00:00.000Z\"},{\"id\":\"5ee27f523a7c9724e62ac445\",\"purchaseDate\":\"2020-06-11T14:46:34.140Z\",\"purchaseOrder\":\"5900569841\",\"shippingGroupID\":50004853194,\"deliverTo\":\"Bodega\",\"warehouseCode\":\"6014\",\"warehouse\":\"Centro de distribución\",\"clientRUT\":\"\",\"clientName\":\"\",\"clientPhone\":\"\",\"clientAddress\":\"\",\"city\":\"SANTIAGO\",\"commitmentDate\":\"2020-06-17T00:00:00.000Z\",\"itemNumber\":266476,\"department\":77,\"productNumber\":\"\",\"barcode\":7807311059177,\"productDescription\":\"Cocina 6 Quemadores Diva 870 GL\",\"innerPack\":1,\"cost\":224026,\"unitCost\":224026,\"quantity\":1,\"status\":\"Pendiente Envio\",\"dropShipDate\":\"2020-06-15T00:00:00.000Z\"},{\"id\":\"5ee26c8e3a7c9724e6282eb1\",\"purchaseDate\":\"2020-06-11T13:33:46.087Z\",\"purchaseOrder\":\"5900569846\",\"shippingGroupID\":50004851841,\"deliverTo\":\"Bodega\",\"warehouseCode\":\"6014\",\"warehouse\":\"Centro de distribución\",\"clientRUT\":\"\",\"clientName\":\"\",\"clientPhone\":\"\",\"clientAddress\":\"\",\"city\":\"VALPARAISO\",\"commitmentDate\":\"2020-06-18T00:00:00.000Z\",\"itemNumber\":670397,\"department\":77,\"productNumber\":\"\",\"barcode\":7807311769335,\"productDescription\":\"Refrigerador No Frost 322 litros / MBF60X\",\"innerPack\":1,\"cost\":281842,\"unitCost\":281842,\"quantity\":1,\"status\":\"Pendiente Envio\",\"dropShipDate\":\"2020-06-16T00:00:00.000Z\"},{\"id\":\"5ee25e763a7c9724e6264896\",\"purchaseDate\":\"2020-06-11T12:24:22.557Z\",\"purchaseOrder\":\"5900569846\",\"shippingGroupID\":50004851227,\"deliverTo\":\"Bodega\",\"warehouseCode\":\"6014\",\"warehouse\":\"Centro de distribución\",\"clientRUT\":\"\",\"clientName\":\"\",\"clientPhone\":\"\",\"clientAddress\":\"\",\"city\":\"ELQUI\",\"commitmentDate\":\"2020-06-18T00:00:00.000Z\",\"itemNumber\":683754,\"department\":77,\"productNumber\":\"\",\"barcode\":7807311001541,\"productDescription\":\"Lavadora Superior 19.5 kg / Efficace 19.5 SZG\",\"innerPack\":1,\"cost\":216798,\"unitCost\":216798,\"quantity\":1,\"status\":\"Pendiente Envio\",\"dropShipDate\":\"2020-06-16T00:00:00.000Z\"},{\"id\":\"5ee255143a7c9724e624f7b6\",\"purchaseDate\":\"2020-06-11T11:54:16.677Z\",\"purchaseOrder\":\"5900569841\",\"shippingGroupID\":50004850769,\"deliverTo\":\"Bodega\",\"warehouseCode\":\"6014\",\"warehouse\":\"Centro de distribución\",\"clientRUT\":\"\",\"clientName\":\"\",\"clientPhone\":\"\",\"clientAddress\":\"\",\"city\":\"CACHAPOAL\",\"commitmentDate\":\"2020-06-17T00:00:00.000Z\",\"itemNumber\":637301,\"department\":77,\"productNumber\":\"\",\"barcode\":7807311733329,\"productDescription\":\"Cocina 4 Quemadores / 713B\",\"innerPack\":1,\"cost\":115623,\"unitCost\":115623,\"quantity\":1,\"status\":\"Pendiente Envio\",\"dropShipDate\":\"2020-06-15T00:00:00.000Z\"},{\"id\":\"5ee255133a7c9724e624f6b5\",\"purchaseDate\":\"2020-06-11T11:41:34.567Z\",\"purchaseOrder\":\"7400494590\",\"shippingGroupID\":50004850642,\"deliverTo\":\"Bodega\",\"warehouseCode\":\"6014\",\"warehouse\":\"Centro de distribución\",\"clientRUT\":\"\",\"clientName\":\"\",\"clientPhone\":\"\",\"clientAddress\":\"\",\"city\":\"SANTIAGO\",\"commitmentDate\":\"2020-06-17T00:00:00.000Z\",\"itemNumber\":583589,\"department\":15,\"productNumber\":\"\",\"barcode\":7807311547698,\"productDescription\":\"Estufa a Gas Vitale 40 CP\",\"innerPack\":1,\"cost\":89405,\"unitCost\":89405,\"quantity\":1,\"status\":\"Pendiente Envio\",\"dropShipDate\":\"2020-06-15T00:00:00.000Z\"},{\"id\":\"5ee2343c3a7c9724e6206038\",\"purchaseDate\":\"2020-06-11T09:28:06.017Z\",\"purchaseOrder\":\"5900569841\",\"shippingGroupID\":50004849274,\"deliverTo\":\"Bodega\",\"warehouseCode\":\"6014\",\"warehouse\":\"Centro de distribución\",\"clientRUT\":\"\",\"clientName\":\"\",\"clientPhone\":\"\",\"clientAddress\":\"\",\"city\":\"SANTIAGO\",\"commitmentDate\":\"2020-06-17T00:00:00.000Z\",\"itemNumber\":683754,\"department\":77,\"productNumber\":\"\",\"barcode\":7807311001541,\"productDescription\":\"Lavadora Superior 19.5 kg / Efficace 19.5 SZG\",\"innerPack\":1,\"cost\":216798,\"unitCost\":216798,\"quantity\":1,\"status\":\"Pendiente Envio\",\"dropShipDate\":\"2020-06-15T00:00:00.000Z\"},{\"id\":\"5ee22f8f3a7c9724e61fc077\",\"purchaseDate\":\"2020-06-11T09:08:00.563Z\",\"purchaseOrder\":\"5900569841\",\"shippingGroupID\":50004849169,\"deliverTo\":\"Bodega\",\"warehouseCode\":\"6014\",\"warehouse\":\"Centro de distribución\",\"clientRUT\":\"\",\"clientName\":\"\",\"clientPhone\":\"\",\"clientAddress\":\"\",\"city\":\"SANTIAGO\",\"commitmentDate\":\"2020-06-17T00:00:00.000Z\",\"itemNumber\":683754,\"department\":77,\"productNumber\":\"\",\"barcode\":7807311001541,\"productDescription\":\"Lavadora Superior 19.5 kg / Efficace 19.5 SZG\",\"innerPack\":1,\"cost\":216798,\"unitCost\":216798,\"quantity\":1,\"status\":\"Pendiente Envio\",\"dropShipDate\":\"2020-06-15T00:00:00.000Z\"},{\"id\":\"5ee22adb3a7c9724e61f1f08\",\"purchaseDate\":\"2020-06-11T08:57:00.350Z\",\"purchaseOrder\":\"5900569841\",\"shippingGroupID\":50004849119,\"deliverTo\":\"Bodega\",\"warehouseCode\":\"6014\",\"warehouse\":\"Centro de distribución\",\"clientRUT\":\"\",\"clientName\":\"\",\"clientPhone\":\"\",\"clientAddress\":\"\",\"city\":\"CORDILLERA\",\"commitmentDate\":\"2020-06-17T00:00:00.000Z\",\"itemNumber\":683754,\"department\":77,\"productNumber\":\"\",\"barcode\":7807311001541,\"productDescription\":\"Lavadora Superior 19.5 kg / Efficace 19.5 SZG\",\"innerPack\":1,\"cost\":216798,\"unitCost\":216798,\"quantity\":1,\"status\":\"Pendiente Envio\",\"dropShipDate\":\"2020-06-15T00:00:00.000Z\"},{\"id\":\"5ee1b5f73a7c9724e60fa14b\",\"purchaseDate\":\"2020-06-11T00:21:28.033Z\",\"purchaseOrder\":\"7400494590\",\"shippingGroupID\":50004846703,\"deliverTo\":\"Bodega\",\"warehouseCode\":\"6014\",\"warehouse\":\"Centro de distribución\",\"clientRUT\":\"\",\"clientName\":\"\",\"clientPhone\":\"\",\"clientAddress\":\"\",\"city\":\"Huechuraba\",\"commitmentDate\":\"2020-06-17T00:00:00.000Z\",\"itemNumber\":583589,\"department\":15,\"productNumber\":\"\",\"barcode\":7807311547698,\"productDescription\":\"Estufa a Gas Vitale 40 CP\",\"innerPack\":1,\"cost\":89405,\"unitCost\":89405,\"quantity\":1,\"status\":\"Pendiente Envio\",\"dropShipDate\":\"2020-06-15T00:00:00.000Z\"},{\"id\":\"5ee1b1453a7c9724e60ee585\",\"purchaseDate\":\"2020-06-11T00:12:57.180Z\",\"purchaseOrder\":\"5900569841\",\"shippingGroupID\":50004846549,\"deliverTo\":\"Bodega\",\"warehouseCode\":\"6014\",\"warehouse\":\"Centro de distribución\",\"clientRUT\":\"\",\"clientName\":\"\",\"clientPhone\":\"\",\"clientAddress\":\"\",\"city\":\"SANTIAGO\",\"commitmentDate\":\"2020-06-17T00:00:00.000Z\",\"itemNumber\":637301,\"department\":77,\"productNumber\":\"\",\"barcode\":7807311733329,\"productDescription\":\"Cocina 4 Quemadores / 713B\",\"innerPack\":1,\"cost\":231246,\"unitCost\":115623,\"quantity\":2,\"status\":\"Pendiente Envio\",\"dropShipDate\":\"2020-06-15T00:00:00.000Z\"}]";
		return JsonUtils.getObjectFromJson(response, VeVOrderDTO[].class);
		/*
		GenericResponseResultDTO responseResult = new GenericResponseResultDTO();
		VeVOrderDTO[] result = null;
		try {
			String url = B2BSystemPropertiesUtil.getProperty("VeVOrdersURL");
			boolean withParam = Boolean.parseBoolean(B2BSystemPropertiesUtil.getProperty("VeVWithParam"));
			String Param = B2BSystemPropertiesUtil.getProperty("VeVParamDate");
			int VeVDaysCant = Integer.valueOf(B2BSystemPropertiesUtil.getProperty("VeVDaysCant"));

			if (withParam && VeVDaysCant >= 0) {

				Date d = new Date();
				int day = d.getDate();
				d.setDate(day - VeVDaysCant);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String b = simpleDateFormat.format(d);
				url = url.concat("?" + Param + "=" + b);
			}

			HttpGet httpGet = RESTConnection.getInstance().getHttpGetEndpoint(url, null, accessToken);

			responseResult = RESTConnection.getInstance().executeRequest(httpGet);

			if (!responseResult.getStatuscode().equals("0")) {
				responseResult.setStatuscode(responseResult.getStatuscode());
				responseResult.setStatusmessage(responseResult.getStatusmessage());
				logger.error(
						"Código: " + responseResult.getStatuscode() + " Mensaje: " + responseResult.getStatusmessage());
			} else {
				logger.info(responseResult.getJsonResponse());
				result = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), VeVOrderDTO[].class);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
		*/
	}

	public void doTransform(InitParamCSDTO credential, List<VeVOrderDTO> purchaseOrderDTO)
			throws DatatypeConfigurationException, JAXBException, OperationFailedException {
		ObjectFactory objFactory = new ObjectFactory();
		Order orderXML = objFactory.createOrder();
		orderXML.setBuyer(SITECODE);
		orderXML.setVendor(credential.getAccesscode());
		orderXML.setNumber(purchaseOrderDTO.get(0).getPurchaseOrder());
		orderXML.setStatus(purchaseOrderDTO.get(0).getStatus());
		orderXML.setOrdertype(purchaseOrderDTO.get(0).getDeliverTo().toUpperCase().equals("BODEGA") ? "eOECD" : "eOEPD");
		orderXML.setOrdertypename(purchaseOrderDTO.get(0).getDeliverTo());
		orderXML.setPreviousordertype("");
		orderXML.setComplete(true);

		// orderXML.setTicket("");
		orderXML.setRequest(purchaseOrderDTO.get(0).getShippingGroupID());

		orderXML.setIssuedate(
				ClassUtilities.StringToXMLGregorianCalendar(purchaseOrderDTO.get(0).getPurchaseDate(), formatter));
		orderXML.setEffectivdate(
				ClassUtilities.StringToXMLGregorianCalendar(purchaseOrderDTO.get(0).getDropShipDate(), formatter));
		orderXML.setExpirationdate(
				ClassUtilities.StringToXMLGregorianCalendar(purchaseOrderDTO.get(0).getDropShipDate(), formatter));
		orderXML.setCommitmentdate(
				ClassUtilities.StringToXMLGregorianCalendar(purchaseOrderDTO.get(0).getDropShipDate(), formatter));
		orderXML.setPaymentcondition("");
		orderXML.setObservation("");
		orderXML.setResponsible("");
		orderXML.setResponsibleemail("");
		orderXML.setDeliveryplace(
				setLocal(objFactory, purchaseOrderDTO.get(0).getWarehouseCode(), purchaseOrderDTO.get(0).getWarehouse()));
		// orderXML.setSaleplace(setLocal(objFactory,
		// purchaseOrderDTO.getClient_purchase_order().getOrder_store_code(),
		// purchaseOrderDTO.getClient_purchase_order().getOrder_store_name()));
		orderXML.setClient(setCliente(objFactory, purchaseOrderDTO.get(0)));
		orderXML.setAction(setAction(objFactory));
		// orderXML.setCharges(value);
		// orderXML.setDiscounts(value);
		orderXML.setDetails(setDetails(objFactory, purchaseOrderDTO));

		Section section = objFactory.createOrderSection();
		section.setCode(purchaseOrderDTO.get(0).getDepartment());
		section.setName(purchaseOrderDTO.get(0).getDepartment());
		orderXML.setSection(section);

		JAXBContext jc = getJC();
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		m.marshal(orderXML, sw);
		String msg = sw.toString();
		soaMessage.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Order: "
				+ orderXML.getNumber());
		soaMessage.info(msg);
		schmanager.doSendMessageQueue(SITENAME, credential.getCompanyname(), orderXML.getNumber(), msg, null);
		logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Order: "
				+ orderXML.getNumber() + " enviada a MQ");
	}

	private Details setDetails(ObjectFactory objFactory, List<VeVOrderDTO> details) {
		Details orderDetails = objFactory.createOrderDetails();
		List<Detail> listDetail = orderDetails.getDetail();
		int position = 1;
		for (VeVOrderDTO item : details) {
			Detail detail = objFactory.createOrderDetailsDetail();
			detail.setPosition(position);
			detail.setNumref1(item.getShippingGroupID());
			detail.setSkubuyer(item.getItemNumber());
			detail.setProductdescription(item.getProductDescription());
			//detail.setSkuvendor(item.getVendor_product_code());
			detail.setEan13(item.getBarcode());
			//detail.setEan13Buyer(item.getRipley_ean13());
			detail.setCodeumc("");
			detail.setDescriptionumc("");
			detail.setCodeumb("");
			detail.setQuantityumc(1);
			//CantidadEmpaque detail.getQuantity_umc() * detail.getUmb_x_umc()
			detail.setUmbXUmc(item.getQuantity().intValue());
			detail.setInnerpack(Integer.parseInt(item.getInnerPack()));
			detail.setFinalcost(item.getUnitCost());
			listDetail.add(detail);
			position++;
		}
		return orderDetails;
	}

	
	private Charges setCharges(ObjectFactory objFactory, String value) {
		if (!value.equals("0")) {
			Charges charges = objFactory.createOrderDetailsDetailCharges();
			List<DiscountCharge> listDiscountCharge = charges.getCharge();
			DiscountCharge discountCharge = objFactory.createDiscountCharge();
			discountCharge.setCode("Fijo");
			discountCharge.setDescription("");
			discountCharge.setPercentage(false);
			discountCharge.setValue(Double.valueOf(value));
			listDiscountCharge.add(discountCharge);
			return charges;
		}
		return null;
	}

	private Discounts setDiscounts(ObjectFactory objFactory, String value) {
		if (!value.equals("0")) {
			Discounts discount = objFactory.createOrderDetailsDetailDiscounts();
			List<DiscountCharge> listDiscountCharge = discount.getDiscount();
			DiscountCharge discountCharge = objFactory.createDiscountCharge();
			discountCharge.setCode("Fijo");
			discountCharge.setDescription("");
			discountCharge.setPercentage(false);
			discountCharge.setValue(Double.valueOf(value));
			listDiscountCharge.add(discountCharge);
			return discount;
		}
		return null;
	}

	public Local setLocal(ObjectFactory objFactory, String code, String name) {
		Local local = objFactory.createLocal();
		local.setCode(code);
		local.setName(name);
		return local;
	}

	private Action setAction(ObjectFactory objFactory) {
		Action action = objFactory.createOrderAction();
		action.setCode("");
		action.setDescription("");
		return action;
	}

	private Client setCliente(ObjectFactory objFactory, VeVOrderDTO clientDTO) {
		Client client = objFactory.createOrderClient();
		if (!clientDTO.getClientRUT().isEmpty()) {

			String[] rut = clientDTO.getClientRUT().split("-");
			client.setIdentity(rut[0]);
			client.setCheckdigit(rut[1]);

			client.setAddress(clientDTO.getClientAddress());
			client.setCity(clientDTO.getCity());
			client.setName(clientDTO.getClientName());
			client.setPhone(clientDTO.getClientPhone());
		}
		return client;
	}
}
