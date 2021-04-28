package bbr.b2b.extractors.manager.classes;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import bbr.b2b.extraactors.falabella.dto.CredentialRipleyDTO;
import bbr.b2b.extractors.api.manager.classes.GenericResponseResultDTO;
import bbr.b2b.extractors.api.manager.classes.JsonUtils;
import bbr.b2b.extractors.api.manager.classes.RESTConnection;
import bbr.b2b.extractors.api.manager.classes.RESTContentType;
import bbr.b2b.extractors.manager.interfaces.RipleyApiOrderManagerLocal;
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
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.esb.services.webservices.facade.InitParamCSDTO;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
@Stateless(name = "managers/RipleyApiOrderManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RipleyApiOrderManager implements RipleyApiOrderManagerLocal {

	private static Logger logger = Logger.getLogger(RipleyApiOrderManager.class);
	private static Logger soaMessage = Logger.getLogger("SOAMessage");
	private static JAXBContext jc = null;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static final String SITECODE = "CL1201";
	private static final String SITENAME = "RIPLEY";

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
			boolean enviaACK = B2BSystemPropertiesUtil.getProperty("RIPLEYACK").equals("true");
			String service = "LOC";
			WsSoaService wsSoaService = new WsSoaService();
			List<InitParamCSDTO> credentials = wsSoaService.getCredentials(SITECODE, service);
			for (InitParamCSDTO credential : credentials) {
				try {

					logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Obteniendo credenciales");
					
					CredentialRipleyDTO credentialRipleyDTO = JsonUtils.getObjectFromJson(credential.getCredenciales(),CredentialRipleyDTO.class);
					LoginResponseDTO loginResponseDTO = doLogin(credentialRipleyDTO.getUser(), credentialRipleyDTO.getPassword());
										
					if (loginResponseDTO.getAccess_token() != null) {
						if (credentialRipleyDTO.isDownloadVeV()) {
							logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Descarga de órdenes VeV");
							try {
								OrderResponseDTO orderResponseDTO = getOrders(loginResponseDTO.getAccess_token());
								AcknowledgeRequestDTO acknowledgeRequest = new AcknowledgeRequestDTO();
								List<AcknowledgeOrdenDTO> order_list = new ArrayList<AcknowledgeOrdenDTO>();
								if(orderResponseDTO.getPurchase_order_list() != null){
									for (PurchaseOrderDTO purchaseOrderDTO : orderResponseDTO.getPurchase_order_list()) {
										try {
											doTransform(credential, purchaseOrderDTO);
											order_list.add(new AcknowledgeOrdenDTO(purchaseOrderDTO.getClient_purchase_order().getOrder_id()));
										} catch (Exception ex) {
											logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Error: " + ex.getMessage());
											throw ex;
										}
									}
									if (enviaACK && order_list.size() > 0) {
										acknowledgeRequest.setOrder_list(order_list);
										doAckownledge(credential, loginResponseDTO.getAccess_token(), acknowledgeRequest);
									}
								}else{
									logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " lista de ordenes del response nula");
								}
							} catch (Exception e) {
								e.printStackTrace();
								throw e;
							}
						}
						if (credentialRipleyDTO.isDownloadSupply()) {
							logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Descarga de órdenes Supply");
							try {
								SupplyOrderResponseDTO orderResponseDTO = getSupplyOrders(loginResponseDTO.getAccess_token());
								AcknowledgeRequestDTO acknowledgeRequest = new AcknowledgeRequestDTO();
								List<AcknowledgeOrdenDTO> order_list = new ArrayList<AcknowledgeOrdenDTO>();
								for (SupplyPurchaseOrderDTO purchaseOrderDTO : orderResponseDTO.getPurchase_order_list()) {
									try {
										doSupplyTransform(credential, purchaseOrderDTO);
										order_list.add(new AcknowledgeOrdenDTO(purchaseOrderDTO.getPurchase_order().getPo_number()));
									} catch (Exception ex) {
										logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Error: " + ex.getMessage());
										throw ex;
									}
								}
								if (enviaACK && order_list.size() > 0) {
									acknowledgeRequest.setOrder_list(order_list);
									doSupplyAckownledge(credential, loginResponseDTO.getAccess_token(), acknowledgeRequest);
								}
							} catch (Exception e) {
								throw e;
							}
						}
					}
				} catch (Exception ex) {
					logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Error: VeV " + ex.getMessage());
					throw ex;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public LoginResponseDTO doLogin(String username, String password) throws Exception {
		LoginResponseDTO loginResponse = new LoginResponseDTO();

		try {
			String baseUrl = B2BSystemPropertiesUtil.getProperty("BASEURL");
			String method = B2BSystemPropertiesUtil.getProperty("LOGIN");
			String url = baseUrl + method; // "http://staging.sellercenter.ripleylabs.com/api/v1/auth/login/vendor";

			// SE REALIZA UNA OPERACION POST
			String authString = username + ":" + password;
			String accessToken = new BASE64Encoder().encode(authString.getBytes());

			logger.info("************************");
			logger.info("Username: "+username);
			logger.info("Password: "+password);
			logger.info("URL: "+url);
			logger.info("AccessToken: "+accessToken);
			
			
			HttpPost httpPost = RESTConnection.getInstance().getHttpPostEndpoint(url, accessToken, true);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpPost);

			if (!responseResult.getStatuscode().equals("0")) {
				logger.error("Código: " + responseResult.getStatuscode() + " Mensaje: "
						+ responseResult.getStatusmessage() + "Response: " + responseResult.getJsonResponse());
			} else {
				// CONVIERTE RESPUESTA JSON A OBJETO
				loginResponse = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), LoginResponseDTO.class);
			}

		} catch (Exception e) {
			throw new Exception("Ha ocurrido un error al realizar login " + e.getMessage());
		}

		return loginResponse;
	}

	public OrderResponseDTO getOrders(String token) throws Exception {

		OrderResponseDTO result = new OrderResponseDTO();
		//String prueba = "{\"status_code\":200,\"purchase_order_list\":[{\"_id\":\"5f978edc43ae580f3a2293e8\",\"client_purchase_order\":{\"order_id\":\"0390050001875475760101\",\"order_type\":\"DDC\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":771773,\"dispatch_order_number\":2796070774,\"order_sale_date\":\"2020-10-27T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098051779\",\"order_delivery_date\":\"2020-12-28T00:00:00.000Z\",\"order_delivery_place\":\"CLIENTE\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"Carlos  Farias\",\"client_fis\":\"13055689-2\",\"client_phone\":\"0940884117\",\"client_email\":null,\"client_address\":\"Las Nubes,2647\",\"client_region\":\"REG.METROP.\",\"client_county\":\"MAIPU\",\"client_city\":\"SANTIAGO\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:11:09.121Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224088\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-28T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":108145,\"po_total_net_cost\":108145,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37579192\",\"vendor_product_code\":\"780642761760-8\",\"ripley_ean13\":\"2000375791921\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503269\",\"item_description\":\"BOX AMERICANO EXC PLUS 1.5P N\",\"item_base_cost\":108145,\"item_sale_price\":149990,\"item_quantity\":1,\"item_net_cost\":108145,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875475760101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978edc43ae580f3a2293db\",\"client_purchase_order\":{\"order_id\":\"0390050001875459850102\",\"order_type\":\"DDC\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":771696,\"dispatch_order_number\":2796070767,\"order_sale_date\":\"2020-10-27T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098036543\",\"order_delivery_date\":\"2020-12-17T00:00:00.000Z\",\"order_delivery_place\":\"CLIENTE\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"KATHERINE PINCHEIRA\",\"client_fis\":\"15944529-1\",\"client_phone\":\"0977920357\",\"client_email\":null,\"client_address\":\"Las Heras,1464,DPTO  1107\",\"client_region\":\"VIII REGION\",\"client_county\":\"CONCEPCION\",\"client_city\":\"CONCEPCION\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:11:08.876Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224077\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-17T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":64700,\"po_total_net_cost\":64700,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"35143073\",\"vendor_product_code\":\"RACK CLASICO\",\"ripley_ean13\":\"2000351430738\",\"vendor_ean13\":null,\"item_department_code\":\"D359\",\"item_line_code\":\"503262\",\"item_description\":\"RACK CLASICO\",\"item_base_cost\":64700,\"item_sale_price\":89990,\"item_quantity\":1,\"item_net_cost\":64700,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875459850102\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978edc43ae580f3a2293ce\",\"client_purchase_order\":{\"order_id\":\"0390050001875459790101\",\"order_type\":\"DDC\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":771696,\"dispatch_order_number\":2796070766,\"order_sale_date\":\"2020-10-27T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098036543\",\"order_delivery_date\":\"2020-12-17T00:00:00.000Z\",\"order_delivery_place\":\"CLIENTE\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"KATHERINE PINCHEIRA\",\"client_fis\":\"15944529-1\",\"client_phone\":\"0977920357\",\"client_email\":null,\"client_address\":\"Las Heras,1464,DPTO  1107\",\"client_region\":\"VIII REGION\",\"client_county\":\"CONCEPCION\",\"client_city\":\"CONCEPCION\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:11:08.573Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224076\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-17T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":41171,\"po_total_net_cost\":41171,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37364804\",\"vendor_product_code\":\"780642773856-3\",\"ripley_ean13\":\"2000373648043\",\"vendor_ean13\":null,\"item_department_code\":\"D359\",\"item_line_code\":\"503262\",\"item_description\":\"RACK 1 PUERTA GANGES 109X67X37 NOGAL\",\"item_base_cost\":41171,\"item_sale_price\":49990,\"item_quantity\":1,\"item_net_cost\":41171,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875459790101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978eda43ae580f3a229331\",\"client_purchase_order\":{\"order_id\":\"0390050001875331490101\",\"order_type\":\"DDC\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":872078,\"dispatch_order_number\":2796070765,\"order_sale_date\":\"2020-10-27T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098047312\",\"order_delivery_date\":\"2020-12-16T00:00:00.000Z\",\"order_delivery_place\":\"CLIENTE\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"erwin  quezada faundez\",\"client_fis\":\"6980368-7\",\"client_phone\":\"0992846995\",\"client_email\":null,\"client_address\":\"Manuel Riofrio,486,Casa\",\"client_region\":\"V REGION\",\"client_county\":\"VALPARAISO\",\"client_city\":\"VALPARAISO\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:11:06.341Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224089\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-16T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":121254,\"po_total_net_cost\":121254,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37222314\",\"vendor_product_code\":\"780642759789-4\",\"ripley_ean13\":\"2000372223142\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503270\",\"item_description\":\"COLCHON ORTOPEDIC ADVANCE 2 PZAS\",\"item_base_cost\":121254,\"item_sale_price\":169990,\"item_quantity\":1,\"item_net_cost\":121254,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875331490101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed843ae580f3a2292be\",\"client_purchase_order\":{\"order_id\":\"0390050001875287760101\",\"order_type\":\"DVO\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":10718,\"dispatch_order_number\":2796070779,\"order_sale_date\":\"2020-10-27T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098041424\",\"order_delivery_date\":\"2020-11-11T00:00:00.000Z\",\"order_delivery_place\":\"OPL\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"Mauricio Loncon\",\"client_fis\":\"18164789-2\",\"client_phone\":\"0965488624\",\"client_email\":null,\"client_address\":\"emilio luppi,Manzana 6, numero 12\",\"client_region\":\"X REGION\",\"client_county\":\"PUERTO VARAS\",\"client_city\":\"PTO. VARAS\",\"client_delivery_place_obs\":\"Manzana 6, N MERO 12, Casa 2 pisos color negro\"},\"created_on_utc\":\"2020-10-27T03:11:04.547Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224079\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-11-06T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":131086,\"po_total_net_cost\":131086,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37634625\",\"vendor_product_code\":\"7806427620707\",\"ripley_ean13\":\"2000376346250\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503268\",\"item_description\":\"CAMA EUROPEA EXCELLENCE PLUS FULL\",\"item_base_cost\":131086,\"item_sale_price\":189990,\"item_quantity\":1,\"item_net_cost\":131086,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875287760101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed843ae580f3a2292a6\",\"client_purchase_order\":{\"order_id\":\"0390050001875286100101\",\"order_type\":\"DDC\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":871983,\"dispatch_order_number\":2796070771,\"order_sale_date\":\"2020-10-27T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098041349\",\"order_delivery_date\":\"2020-12-26T00:00:00.000Z\",\"order_delivery_place\":\"CLIENTE\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"ysela elisabet lopez prez\",\"client_fis\":\"22236402-7\",\"client_phone\":\"0976832524\",\"client_email\":null,\"client_address\":\"Av. Lircay,0157\",\"client_region\":\"REG.METROP.\",\"client_county\":\"RECOLETA\",\"client_city\":\"SANTIAGO\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:11:04.107Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224080\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-26T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":203187,\"po_total_net_cost\":203187,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37222227\",\"vendor_product_code\":\"780642760087-7\",\"ripley_ean13\":\"2000372222275\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503268\",\"item_description\":\"CAMA EUROPEA NEW ORTOPEDIC 2P BD\",\"item_base_cost\":203187,\"item_sale_price\":249990,\"item_quantity\":1,\"item_net_cost\":203187,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875286100101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed743ae580f3a229298\",\"client_purchase_order\":{\"order_id\":\"0390050001875278020101\",\"order_type\":\"DVR\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":771670,\"dispatch_order_number\":null,\"order_sale_date\":\"2020-10-27T00:00:00.000Z\",\"order_billing_date\":\"2020-12-27T00:00:00.000Z\",\"order_delivery_id\":\"0390050098043024\",\"order_delivery_date\":\"2021-01-06T00:00:00.000Z\",\"order_delivery_place\":\"Ripley\",\"order_delivery_address\":\"Almirante Simpsom 1200\",\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"10095\",\"delivery_warehouse_name\":\"REDEX\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"Roberto  Guerrero\",\"client_fis\":\"16012934-4\",\"client_phone\":\"0950423799\",\"client_email\":null,\"client_address\":\"El Inca,684,C 510\",\"client_region\":\"III REGION\",\"client_county\":\"COPIAPO\",\"client_city\":\"COPIAPO\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:11:03.881Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224126\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-30T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":108145,\"po_total_net_cost\":108145,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37579140\",\"vendor_product_code\":\"780642761700-4\",\"ripley_ean13\":\"2000375791402\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503268\",\"item_description\":\"BOX AMERICANO EXC FULL\",\"item_base_cost\":108145,\"item_sale_price\":149990,\"item_quantity\":1,\"item_net_cost\":108145,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875278020101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed743ae580f3a22928c\",\"client_purchase_order\":{\"order_id\":\"0390050001875269910101\",\"order_type\":\"DDC\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":10680,\"dispatch_order_number\":2796070776,\"order_sale_date\":\"2020-10-26T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050097805542\",\"order_delivery_date\":\"2020-12-29T00:00:00.000Z\",\"order_delivery_place\":\"CLIENTE\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"luisa figueroa olate\",\"client_fis\":\"16824877-6\",\"client_phone\":\"0976818219\",\"client_email\":null,\"client_address\":\"Manuel Baquedano,216,404 B\",\"client_region\":\"IX REGION\",\"client_county\":\"LAUTARO\",\"client_city\":\"TEMUCO\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:11:03.654Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224081\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-29T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":321422,\"po_total_net_cost\":321422,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37909253\",\"vendor_product_code\":\"7806427628949\",\"ripley_ean13\":\"2000379092536\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503268\",\"item_description\":\"CAMA EURO PREM 1,05X2 TORIN CARAMEL S/T\",\"item_base_cost\":321422,\"item_sale_price\":389990,\"item_quantity\":1,\"item_net_cost\":321422,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875269910101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed743ae580f3a229281\",\"client_purchase_order\":{\"order_id\":\"0390050001875266450101\",\"order_type\":\"DDC\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":871708,\"dispatch_order_number\":2796070769,\"order_sale_date\":\"2020-10-26T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098018597\",\"order_delivery_date\":\"2020-12-24T00:00:00.000Z\",\"order_delivery_place\":\"CLIENTE\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"maritza soto Araya\",\"client_fis\":\"17107612-9\",\"client_phone\":\"0949619411\",\"client_email\":null,\"client_address\":\"Avenida Brasil,6621,154\",\"client_region\":\"REG.METROP.\",\"client_county\":\"RENCA\",\"client_city\":\"SANTIAGO\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:11:03.431Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224082\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-24T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":203187,\"po_total_net_cost\":203187,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37222227\",\"vendor_product_code\":\"780642760087-7\",\"ripley_ean13\":\"2000372222275\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503268\",\"item_description\":\"CAMA EUROPEA NEW ORTOPEDIC 2P BD\",\"item_base_cost\":203187,\"item_sale_price\":249990,\"item_quantity\":1,\"item_net_cost\":203187,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875266450101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed643ae580f3a229242\",\"client_purchase_order\":{\"order_id\":\"0390050001875238200101\",\"order_type\":\"DDC\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":871710,\"dispatch_order_number\":2796070777,\"order_sale_date\":\"2020-10-26T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098036047\",\"order_delivery_date\":\"2020-12-30T00:00:00.000Z\",\"order_delivery_place\":\"CLIENTE\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"Maria Elena Orellana Olivarez\",\"client_fis\":\"9206343-7\",\"client_phone\":\"0961842380\",\"client_email\":null,\"client_address\":\"Avenida Espana,0411,Casa 7\",\"client_region\":\"VI REGION\",\"client_county\":\"SAN VICENTE\",\"client_city\":\"SN.VICENTE DE\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:11:02.983Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224083\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-30T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":219573,\"po_total_net_cost\":219573,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37722017\",\"vendor_product_code\":\"780642762628-0\",\"ripley_ean13\":\"2000377220177\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503269\",\"item_description\":\"BOX AMERICANO EXCELLENCE PLUS 2P BD NEW VILLARRICA\",\"item_base_cost\":219573,\"item_sale_price\":289990,\"item_quantity\":1,\"item_net_cost\":219573,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875238200101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed643ae580f3a229205\",\"client_purchase_order\":{\"order_id\":\"0390050001875229650101\",\"order_type\":\"DVO\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":771632,\"dispatch_order_number\":2796070778,\"order_sale_date\":\"2020-10-27T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098027479\",\"order_delivery_date\":\"2021-01-02T00:00:00.000Z\",\"order_delivery_place\":\"OPL\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"Luz Edith Vargas Cuyul\",\"client_fis\":\"11414660-9\",\"client_phone\":\"0982819883\",\"client_email\":null,\"client_address\":\"Los Huemules, Manzana A, Alerce Norte,19\",\"client_region\":\"X REGION\",\"client_county\":\"PUERTO MONTT\",\"client_city\":\"PTO. MONTT\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:11:02.523Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224084\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-30T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":229405,\"po_total_net_cost\":229405,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37722581\",\"vendor_product_code\":\"780642762636-5\",\"ripley_ean13\":\"2000377225813\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503268\",\"item_description\":\"DIVAN NEW ORTOPEDIC BLACK NEW VILLARRICA\",\"item_base_cost\":229405,\"item_sale_price\":329990,\"item_quantity\":1,\"item_net_cost\":229405,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875229650101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed643ae580f3a2291fa\",\"client_purchase_order\":{\"order_id\":\"0390050001875201650101\",\"order_type\":\"DDC\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":771498,\"dispatch_order_number\":2796070775,\"order_sale_date\":\"2020-10-26T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098031955\",\"order_delivery_date\":\"2020-12-29T00:00:00.000Z\",\"order_delivery_place\":\"CLIENTE\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"Paulina Araya Jara\",\"client_fis\":\"15128998-3\",\"client_phone\":\"752326859\",\"client_email\":null,\"client_address\":\"POBLACION AGUAS NEGRAS PASAJE LOS GUINDOS,046\",\"client_region\":\"VII REGION\",\"client_county\":\"CURICO\",\"client_city\":\"CURICO\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:11:02.298Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224085\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-29T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":216296,\"po_total_net_cost\":216296,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37402902\",\"vendor_product_code\":\"780642761365-5\",\"ripley_ean13\":\"2000374029025\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503268\",\"item_description\":\"BOX NEW ORTOPEDIC B5 BLACK 2P BN LEGNO S/T\",\"item_base_cost\":216296,\"item_sale_price\":309990,\"item_quantity\":1,\"item_net_cost\":216296,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875201650101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed543ae580f3a2291b5\",\"client_purchase_order\":{\"order_id\":\"0390050001875176930101\",\"order_type\":\"DVR\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":871941,\"dispatch_order_number\":null,\"order_sale_date\":\"2020-10-26T00:00:00.000Z\",\"order_billing_date\":\"2020-12-28T00:00:00.000Z\",\"order_delivery_id\":\"0390050098019917\",\"order_delivery_date\":\"2021-01-18T00:00:00.000Z\",\"order_delivery_place\":\"Ripley\",\"order_delivery_address\":\"Almirante Simpsom 1200\",\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"10095\",\"delivery_warehouse_name\":\"REDEX\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"Soledad Meza\",\"client_fis\":\"15254347-6\",\"client_phone\":\"0954049361\",\"client_email\":null,\"client_address\":\"Avenida Mirador,04325,Casa\",\"client_region\":\"XII REGION\",\"client_county\":\"PUNTA ARENAS\",\"client_city\":\"PTA. ARENAS\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:11:01.848Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224127\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-31T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":62262,\"po_total_net_cost\":62262,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37579130\",\"vendor_product_code\":\"780642759801-3\",\"ripley_ean13\":\"2000375791303\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503270\",\"item_description\":\"COLCHON EXC 1 1/2 PZA\",\"item_base_cost\":62262,\"item_sale_price\":89990,\"item_quantity\":1,\"item_net_cost\":62262,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875176930101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed543ae580f3a22918f\",\"client_purchase_order\":{\"order_id\":\"0390050001875167650101\",\"order_type\":\"DVR\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":771355,\"dispatch_order_number\":null,\"order_sale_date\":\"2020-10-26T00:00:00.000Z\",\"order_billing_date\":\"2020-11-10T00:00:00.000Z\",\"order_delivery_id\":\"0390050098037625\",\"order_delivery_date\":\"2020-11-16T00:00:00.000Z\",\"order_delivery_place\":\"Ripley\",\"order_delivery_address\":\"Almirante Simpsom 1200\",\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"10095\",\"delivery_warehouse_name\":\"REDEX\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"Fany Vera Mansilla\",\"client_fis\":\"16206024-4\",\"client_phone\":\"0997548065\",\"client_email\":null,\"client_address\":\"Trentino Sector cardonal socovesa,2015\",\"client_region\":\"X REGION\",\"client_county\":\"PUERTO MONTT\",\"client_city\":\"PTO. MONTT\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:11:01.394Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224123\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-11-13T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":470582,\"po_total_net_cost\":470582,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37434644\",\"vendor_product_code\":\"780642760993-1\",\"ripley_ean13\":\"2000374346443\",\"vendor_ean13\":null,\"item_department_code\":\"D359\",\"item_line_code\":\"503260\",\"item_description\":\"JGO DE COMEDOR CHICAGO 6 SILLAS 2 SITIALES\",\"item_base_cost\":470582,\"item_sale_price\":639990,\"item_quantity\":1,\"item_net_cost\":470582,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875167650101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed443ae580f3a229179\",\"client_purchase_order\":{\"order_id\":\"0390050001875155630101\",\"order_type\":\"DDC\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":771428,\"dispatch_order_number\":2796070772,\"order_sale_date\":\"2020-10-26T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098033364\",\"order_delivery_date\":\"2020-12-28T00:00:00.000Z\",\"order_delivery_place\":\"CLIENTE\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"Alicia  Henriquez Barra\",\"client_fis\":\"7708759-1\",\"client_phone\":\"0975675208\",\"client_email\":null,\"client_address\":\"Volcan Tupungato,2995\",\"client_region\":\"VIII REGION\",\"client_county\":\"CORONEL\",\"client_city\":\"CONCEPCION\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:11:00.946Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224086\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-28T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":275288,\"po_total_net_cost\":275288,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37722328\",\"vendor_product_code\":\"780642762648-8\",\"ripley_ean13\":\"2000377223284\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503268\",\"item_description\":\"CAMA EUROPEA NEW ORTOPEDIC 2P BD NEW VILLARRICA S/\",\"item_base_cost\":275288,\"item_sale_price\":359990,\"item_quantity\":1,\"item_net_cost\":275288,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875155630101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed443ae580f3a22916d\",\"client_purchase_order\":{\"order_id\":\"0390050001875153740101\",\"order_type\":\"DDC\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":771393,\"dispatch_order_number\":2796070770,\"order_sale_date\":\"2020-10-26T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098033295\",\"order_delivery_date\":\"2020-12-24T00:00:00.000Z\",\"order_delivery_place\":\"CLIENTE\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"patricia berrios\",\"client_fis\":\"15327299-9\",\"client_phone\":\"0931270811\",\"client_email\":null,\"client_address\":\"Las Vertientes,10268\",\"client_region\":\"REG.METROP.\",\"client_county\":\"LA FLORIDA\",\"client_city\":\"SANTIAGO\",\"client_delivery_place_obs\":\"ingresar por el senorial con los arcos norte\"},\"created_on_utc\":\"2020-10-27T03:11:00.728Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224090\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-24T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":203187,\"po_total_net_cost\":203187,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37222227\",\"vendor_product_code\":\"780642760087-7\",\"ripley_ean13\":\"2000372222275\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503268\",\"item_description\":\"CAMA EUROPEA NEW ORTOPEDIC 2P BD\",\"item_base_cost\":203187,\"item_sale_price\":269990,\"item_quantity\":1,\"item_net_cost\":203187,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875153740101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed343ae580f3a229134\",\"client_purchase_order\":{\"order_id\":\"0390050001875136750101\",\"order_type\":\"DVR\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":871697,\"dispatch_order_number\":null,\"order_sale_date\":\"2020-10-26T00:00:00.000Z\",\"order_billing_date\":\"2020-12-28T00:00:00.000Z\",\"order_delivery_id\":\"0390050097870897\",\"order_delivery_date\":\"2021-01-04T00:00:00.000Z\",\"order_delivery_place\":\"Ripley\",\"order_delivery_address\":\"Almirante Simpsom 1200\",\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"10095\",\"delivery_warehouse_name\":\"REDEX\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"vicky  Cortes\",\"client_fis\":\"15025243-1\",\"client_phone\":\"0965834366\",\"client_email\":null,\"client_address\":\"Detective Solon salas,8865\",\"client_region\":\"II REGION\",\"client_county\":\"ANTOFAGASTA\",\"client_city\":\"ANTOFAGASTA\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:10:59.614Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224128\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-31T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":114699,\"po_total_net_cost\":114699,\"po_total_quantity\":2,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37579237\",\"vendor_product_code\":\"780642761808-7\",\"ripley_ean13\":\"2000375792379\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503268\",\"item_description\":\"CAMA EUROPEA EXC PLUS 1.5P\",\"item_base_cost\":114699,\"item_sale_price\":169990,\"item_quantity\":2,\"item_net_cost\":114699,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875136750101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed243ae580f3a22906f\",\"client_purchase_order\":{\"order_id\":\"0390050001875112480101\",\"order_type\":\"DDC\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":771371,\"dispatch_order_number\":2796070764,\"order_sale_date\":\"2020-10-26T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098030323\",\"order_delivery_date\":\"2020-12-15T00:00:00.000Z\",\"order_delivery_place\":\"CLIENTE\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"M NICA CATALINA LIRA\",\"client_fis\":\"18693778-3\",\"client_phone\":\"0991266188\",\"client_email\":null,\"client_address\":\"Avenida Padre Hurtado,9304,Casa esquina\",\"client_region\":\"REG.METROP.\",\"client_county\":\"LA CISTERNA\",\"client_city\":\"SANTIAGO\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:10:58.242Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224087\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-15T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":222851,\"po_total_net_cost\":222851,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37222873\",\"vendor_product_code\":\"780642759963-8\",\"ripley_ean13\":\"2000372228734\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503268\",\"item_description\":\"CAMA EUROPEA ORTOPEDIC ADVANCE 2P BD\",\"item_base_cost\":222851,\"item_sale_price\":279990,\"item_quantity\":1,\"item_net_cost\":222851,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875112480101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed143ae580f3a229063\",\"client_purchase_order\":{\"order_id\":\"0390050001875111080101\",\"order_type\":\"DDC\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":771327,\"dispatch_order_number\":2796070773,\"order_sale_date\":\"2020-10-26T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098030269\",\"order_delivery_date\":\"2020-12-28T00:00:00.000Z\",\"order_delivery_place\":\"CLIENTE\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"silvana nunez\",\"client_fis\":\"10069272-4\",\"client_phone\":\"0981918496\",\"client_email\":null,\"client_address\":\"Normandia,1882,piso 3\",\"client_region\":\"REG.METROP.\",\"client_county\":\"PROVIDENCIA\",\"client_city\":\"SANTIAGO\",\"client_delivery_place_obs\":\"para la Sra Diana Cifuentes\"},\"created_on_utc\":\"2020-10-27T03:10:58.019Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224091\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-28T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":117977,\"po_total_net_cost\":117977,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37579161\",\"vendor_product_code\":\"780642761721-9\",\"ripley_ean13\":\"2000375791617\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503269\",\"item_description\":\"BOX AMERICANO EXC 1.5P SET OL\",\"item_base_cost\":117977,\"item_sale_price\":149990,\"item_quantity\":1,\"item_net_cost\":117977,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875111080101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed143ae580f3a229056\",\"client_purchase_order\":{\"order_id\":\"0390050001875095170101\",\"order_type\":\"DDC\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":771283,\"dispatch_order_number\":2796070768,\"order_sale_date\":\"2020-10-26T00:00:00.000Z\",\"order_billing_date\":null,\"order_delivery_id\":\"0390050098024364\",\"order_delivery_date\":\"2020-12-23T00:00:00.000Z\",\"order_delivery_place\":\"CLIENTE\",\"order_delivery_address\":null,\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"\",\"delivery_warehouse_name\":\"\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"Ivan  De la fuente\",\"client_fis\":\"10916850-5\",\"client_phone\":\"0930800797\",\"client_email\":null,\"client_address\":\"El Encanto,654,10\",\"client_region\":\"V REGION\",\"client_county\":\"VINA DEL MAR\",\"client_city\":\"V.DEL MAR\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:10:57.794Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224078\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-23T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":75372,\"po_total_net_cost\":75372,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37222066\",\"vendor_product_code\":\"780642759781-8\",\"ripley_ean13\":\"2000372220660\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503270\",\"item_description\":\"COLCHON NEW ORTOPEDIC 1 PZA\",\"item_base_cost\":75372,\"item_sale_price\":109990,\"item_quantity\":1,\"item_net_cost\":75372,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875095170101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed043ae580f3a22901d\",\"client_purchase_order\":{\"order_id\":\"0390050001875053940101\",\"order_type\":\"DVR\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":771261,\"dispatch_order_number\":null,\"order_sale_date\":\"2020-10-26T00:00:00.000Z\",\"order_billing_date\":\"2020-12-28T00:00:00.000Z\",\"order_delivery_id\":\"0390050098040172\",\"order_delivery_date\":\"2021-01-04T00:00:00.000Z\",\"order_delivery_place\":\"Ripley\",\"order_delivery_address\":\"Almirante Simpsom 1200\",\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"10095\",\"delivery_warehouse_name\":\"REDEX\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"daniela carozzi\",\"client_fis\":\"15679539-9\",\"client_phone\":\"0965970575\",\"client_email\":null,\"client_address\":\"Aconcagua,923,1405\",\"client_region\":\"II REGION\",\"client_county\":\"ANTOFAGASTA\",\"client_city\":\"ANTOFAGASTA\",\"client_delivery_place_obs\":\"entrega a partir de las 18 00\"},\"created_on_utc\":\"2020-10-27T03:10:56.889Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224125\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-31T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":397053,\"po_total_net_cost\":397053,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37722109\",\"vendor_product_code\":\"780642762212-1\",\"ripley_ean13\":\"2000377221099\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503268\",\"item_description\":\"BOX SPRING NEW ORTOPEDIC B5 BLACK 2P BN TIGRIS ALM\",\"item_base_cost\":397053,\"item_sale_price\":519990,\"item_quantity\":1,\"item_net_cost\":397053,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875053940101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}},{\"_id\":\"5f978ed043ae580f3a229011\",\"client_purchase_order\":{\"order_id\":\"0390050001875051770101\",\"order_type\":\"DVR\",\"order_status\":\"RECIEN INGRESADO SVC\",\"order_ticket_number\":771281,\"dispatch_order_number\":null,\"order_sale_date\":\"2020-10-26T00:00:00.000Z\",\"order_billing_date\":\"2020-12-27T00:00:00.000Z\",\"order_delivery_id\":\"0390050098040085\",\"order_delivery_date\":\"2021-01-08T00:00:00.000Z\",\"order_delivery_place\":\"Ripley\",\"order_delivery_address\":\"Almirante Simpsom 1200\",\"order_store_code\":10039,\"order_store_name\":\"Internet\",\"order_client_obs\":\"\",\"delivery_warehouse_code\":\"10095\",\"delivery_warehouse_name\":\"REDEX\"},\"_available\":true,\"_vendor_id\":\"B2B_CL_93830000\",\"client\":{\"client_name\":\"Paola Vicencio\",\"client_fis\":\"13212431-0\",\"client_phone\":\"0944370073\",\"client_email\":null,\"client_address\":\"Temuco,860\",\"client_region\":\"XV REGION\",\"client_county\":\"ARICA\",\"client_city\":\"ARICA\",\"client_delivery_place_obs\":\"\"},\"created_on_utc\":\"2020-10-27T03:10:56.666Z\",\"operative_info\":{\"action\":\"C\",\"action_info\":\"ORDEN NUEVA\"},\"purchase_order\":{\"po_number\":\"10224124\",\"po_status\":\"On Order\",\"po_type\":\"Almacenaje Proveedor\",\"po_issued_date\":\"2020-10-26T00:00:00.000Z\",\"po_effective_date\":\"2020-12-30T00:00:00.000Z\",\"po_expiration_date\":\"2020-10-26T00:00:00.000Z\",\"po_payment_method\":\"CHQ 60\",\"po_obs\":null,\"po_total_base_cost\":121254,\"po_total_net_cost\":121254,\"po_total_quantity\":1,\"po_total_charges\":0,\"po_total_discounts\":0,\"details\":[{\"item\":1,\"ripley_sku\":\"37579176\",\"vendor_product_code\":\"780642761736-3\",\"ripley_ean13\":\"2000375791761\",\"vendor_ean13\":null,\"item_department_code\":\"D360\",\"item_line_code\":\"503268\",\"item_description\":\"CAMA EUROPEA EXC 1.5P SET OLMO\",\"item_base_cost\":121254,\"item_sale_price\":179990,\"item_quantity\":1,\"item_net_cost\":121254,\"item_charge\":0,\"item_discount\":0,\"item_lpn\":\"21039001875051770101\",\"item_obs\":\"\"}]},\"vendor\":{\"vendor_fis\":\"93830000\",\"vendor_name\":\"COMPANIAS CIC S.A.\",\"vendor_county\":\"CL\",\"vendor_warehouse\":2796}}],\"total_found\":22,\"limit\":50}";
		//return JsonUtils.getObjectFromJson(prueba, OrderResponseDTO.class);
		 	
		try {
			String baseUrl = B2BSystemPropertiesUtil.getProperty("BASEURL");
			String method = B2BSystemPropertiesUtil.getProperty("GETORDERMETHOD");
			String url = baseUrl + method; // "http://staging.sellercenter.ripleylabs.com/api/v1/b2b/document/orders";
			// HACE LA LLAMADA REST
			HttpGet httpGet = RESTConnection.getInstance().getHttpGetEndpoint(url, null, token);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpGet);
			if (!responseResult.getStatuscode().equals("0")) {
				logger.error(
						"Código: " + responseResult.getStatuscode() + " Mensaje: " + responseResult.getStatusmessage());
			} else {
				logger.info(responseResult.getJsonResponse());
				result = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), OrderResponseDTO.class);
			}
		} catch (Exception e) {
			throw new Exception("Ha ocurrido un error al obtener órdenes " + e.getMessage());
		}
		return result;
	}

	public SupplyOrderResponseDTO getSupplyOrders(String token) throws Exception {

		SupplyOrderResponseDTO result = new SupplyOrderResponseDTO();
		try {
			String baseUrl = B2BSystemPropertiesUtil.getProperty("BASEURL");
			String method = B2BSystemPropertiesUtil.getProperty("GETSUPPLYORDERMETHOD");
			String url = baseUrl + method; // "http://staging.sellercenter.ripleylabs.com/api/v1/b2b/document/orders";
			// HACE LA LLAMADA REST
			HttpGet httpGet = RESTConnection.getInstance().getHttpGetEndpoint(url, null, token);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpGet);
			if (!responseResult.getStatuscode().equals("0")) {
				logger.error("Código: " + responseResult.getStatuscode() + " Mensaje: " + responseResult.getStatusmessage());
			} else {
				logger.info(responseResult.getJsonResponse());
				result = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), SupplyOrderResponseDTO.class);
			}
		} catch (Exception e) {
			throw new Exception("Ha ocurrido un error al obtener órdenes " + e.getMessage());
		}
		return result;
	}

	public AcknowledgeResponseDTO doAckownledge(InitParamCSDTO credential, String token, AcknowledgeRequestDTO acknowledgeRequest) {
		AcknowledgeResponseDTO acknowledgeResponse = null;
		try {
			// CONVIERTE OBJETO A JSON
			String initiParamJson = JsonUtils.getJsonFromObject(acknowledgeRequest, AcknowledgeRequestDTO.class);
			System.out.println(initiParamJson);
			String baseUrl = B2BSystemPropertiesUtil.getProperty("BASEURL");
			String method = B2BSystemPropertiesUtil.getProperty("ACKMETHOD");
			String url = baseUrl + method; // "http://staging.sellercenter.ripleylabs.com/api/v1/b2b/document/order";

			// SE REALIZA UNA OPERACION POST
			HttpPatch httpPatch = RESTConnection.getInstance().getHttpPatchEndpoint(url, initiParamJson, token, RESTContentType.JSON);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpPatch);

			if (!responseResult.getStatuscode().equals("0")) {
				logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Código: " + responseResult.getStatuscode() + " Mensaje: " + responseResult.getStatusmessage());
				logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Response: " + responseResult.getJsonResponse());
			} else {
				// CONVIERTE RESPUESTA JSON A OBJETO
				logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Se ha enviado acknowlegde correctamente");
				logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Response: " + responseResult.getJsonResponse());
				acknowledgeResponse = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), AcknowledgeResponseDTO.class);
			}

		} catch (Exception e) {
			logger.error(e);
		}

		return acknowledgeResponse;
	}

	public AcknowledgeResponseDTO doSupplyAckownledge(InitParamCSDTO credential, String token, AcknowledgeRequestDTO acknowledgeRequest) {
		AcknowledgeResponseDTO acknowledgeResponse = null;
		try {
			// CONVIERTE OBJETO A JSON
			String initiParamJson = JsonUtils.getJsonFromObject(acknowledgeRequest, AcknowledgeRequestDTO.class);
			System.out.println(initiParamJson);
			String baseUrl = B2BSystemPropertiesUtil.getProperty("BASEURL");
			String method = B2BSystemPropertiesUtil.getProperty("SUPPLYACKMETHOD");
			String url = baseUrl + method; // "http://staging.sellercenter.ripleylabs.com/api/v1/b2b/document/order";

			// SE REALIZA UNA OPERACION POST
			HttpPatch httpPatch = RESTConnection.getInstance().getHttpPatchEndpoint(url, initiParamJson, token, RESTContentType.JSON);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpPatch);

			if (!responseResult.getStatuscode().equals("0")) {
				logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Código: " + responseResult.getStatuscode() + " Mensaje: " + responseResult.getStatusmessage());
				logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Response: " + responseResult.getJsonResponse());
			} else {
				// CONVIERTE RESPUESTA JSON A OBJETO
				logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Se ha enviado acknowlegde correctamente");
				logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Response: " + responseResult.getJsonResponse());
				acknowledgeResponse = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), AcknowledgeResponseDTO.class);
			}

		} catch (Exception e) {
			logger.error(e);
		}

		return acknowledgeResponse;
	}

	public void doTransform(InitParamCSDTO credential, PurchaseOrderDTO purchaseOrderDTO) throws DatatypeConfigurationException, JAXBException, OperationFailedException {
		ObjectFactory objFactory = new ObjectFactory();
		Order orderXML = objFactory.createOrder();
		orderXML.setBuyer(SITECODE);
		orderXML.setVendor(credential.getAccesscode());
		orderXML.setNumber(purchaseOrderDTO.getPurchase_order().getPo_number());
		orderXML.setReceiptnumber(purchaseOrderDTO.getClient_purchase_order().getOrder_ticket_number());
		orderXML.setStatus(purchaseOrderDTO.getClient_purchase_order().getOrder_status());
		orderXML.setOrdertype(purchaseOrderDTO.getClient_purchase_order().getOrder_type());
		orderXML.setOrdertypename(purchaseOrderDTO.getClient_purchase_order().getOrder_type());
		orderXML.setPreviousordertype("");
		orderXML.setComplete(true);
		orderXML.setRequest(purchaseOrderDTO.getClient_purchase_order().getOrder_delivery_id());
		orderXML.setIssuedate(ClassUtilities.StringToXMLGregorianCalendar(purchaseOrderDTO.getPurchase_order().getPo_issued_date(), formatter));
		orderXML.setEffectivdate(ClassUtilities.StringToXMLGregorianCalendar(purchaseOrderDTO.getPurchase_order().getPo_effective_date(), formatter));
		orderXML.setExpirationdate(ClassUtilities.StringToXMLGregorianCalendar(purchaseOrderDTO.getPurchase_order().getPo_expiration_date(), formatter));
		orderXML.setCommitmentdate(ClassUtilities.StringToXMLGregorianCalendar(purchaseOrderDTO.getClient_purchase_order().getOrder_delivery_date(), formatter));
		orderXML.setPaymentcondition(purchaseOrderDTO.getPurchase_order().getPo_payment_method());
		orderXML.setObservation(purchaseOrderDTO.getPurchase_order().getPo_obs());
		orderXML.setResponsible("");
		orderXML.setResponsibleemail("");
		orderXML.setNumref1(purchaseOrderDTO.getClient_purchase_order().getOrder_id());
		orderXML.setNumref3(purchaseOrderDTO.getClient_purchase_order().getDispatch_order_number());
		
		if (purchaseOrderDTO.getClient_purchase_order().getOrder_type().equals("DVR")) {
			orderXML.setDeliveryplace(setLocal(objFactory, "10095", "REDEX"));
		
		} else if (purchaseOrderDTO.getClient_purchase_order().getOrder_type().equals("DVO")) {
			orderXML.setDeliveryplace(setLocal(objFactory, "1534072490", "OPL"));
		
		}else {
			if(!purchaseOrderDTO.getClient_purchase_order().getDelivery_warehouse_code().isEmpty()) {
				orderXML.setDeliveryplace(setLocal(objFactory, purchaseOrderDTO.getClient_purchase_order().getDelivery_warehouse_code(),purchaseOrderDTO.getClient_purchase_order().getDelivery_warehouse_name()));
			}
		}
		orderXML.setSaleplace(setLocal(objFactory, purchaseOrderDTO.getClient_purchase_order().getOrder_store_code(),purchaseOrderDTO.getClient_purchase_order().getOrder_store_name()));
		orderXML.setClient(setCliente(objFactory, purchaseOrderDTO.getClient()));
		orderXML.setAction(setAction(objFactory, purchaseOrderDTO.getOperative_info()));
		orderXML.setCharges(setOrderCharges(objFactory, "0"));
		orderXML.setDiscounts(setOrderDiscount(objFactory, "0"));
		orderXML.setDetails(setDetails(objFactory, purchaseOrderDTO.getPurchase_order().getDetails(),orderXML.getVendor()));

		Section section = objFactory.createOrderSection();
		section.setCode(purchaseOrderDTO.getPurchase_order().getDetails().get(0).getItem_department_code());
		section.setName(purchaseOrderDTO.getPurchase_order().getDetails().get(0).getItem_department_code());
		orderXML.setSection(section);

		JAXBContext jc = getJC();
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		m.marshal(orderXML, sw);
		String msg = sw.toString();
		soaMessage.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Order: " + orderXML.getNumber());
		soaMessage.info(msg);
		schmanager.doSendMessageQueue(SITENAME, credential.getCompanyname(), orderXML.getNumber(), msg, null);
		logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Order: " + orderXML.getNumber() + " enviada a MQ");
	}

	public void doSupplyTransform(InitParamCSDTO credential, SupplyPurchaseOrderDTO purchaseOrderDTO)
			throws DatatypeConfigurationException, JAXBException, OperationFailedException {
		ObjectFactory objFactory = new ObjectFactory();
		Order orderXML = objFactory.createOrder();
		orderXML.setBuyer(SITECODE);
		orderXML.setVendor(credential.getAccesscode());
		orderXML.setNumber(purchaseOrderDTO.getPurchase_order().getPo_number());
		orderXML.setStatus(purchaseOrderDTO.getPurchase_order().getPo_status());
		orderXML.setOrdertype(purchaseOrderDTO.getPurchase_order().getPo_type());
		orderXML.setOrdertypename(purchaseOrderDTO.getPurchase_order().getPo_type_description());
		orderXML.setPreviousordertype("");
		orderXML.setComplete(true);
		orderXML.setIssuedate(ClassUtilities.StringToXMLGregorianCalendar(purchaseOrderDTO.getPurchase_order().getPo_issued_date(), formatter));
		orderXML.setEffectivdate(ClassUtilities.StringToXMLGregorianCalendar(purchaseOrderDTO.getPurchase_order().getPo_effective_date(), formatter));
		orderXML.setExpirationdate(ClassUtilities.StringToXMLGregorianCalendar(purchaseOrderDTO.getPurchase_order().getPo_expiration_date(), formatter));
		orderXML.setPaymentcondition(purchaseOrderDTO.getPurchase_order().getPo_payment_method());
		orderXML.setResponsible("");
		orderXML.setResponsibleemail("");
		orderXML.setDeliveryplace(setLocal(objFactory, purchaseOrderDTO.getPurchase_order().getDelivery_warehouse_code(),purchaseOrderDTO.getPurchase_order().getDelivery_warehouse_name()));
		orderXML.setCharges(setOrderCharges(objFactory, "0"));
		orderXML.setDiscounts(setOrderDiscount(objFactory, "0"));
		orderXML.setDetails(setSupplyDetail(objFactory, purchaseOrderDTO.getDetails()));

		orderXML.setAction(setAction(objFactory, purchaseOrderDTO.getOperative_info()));

		Section section = objFactory.createOrderSection();
		
		//El campo .getPo_department() viene nulo en el json de Ripley - asi que se repetira el .getPo_department_code()
		//section.setName(purchaseOrderDTO.getPurchase_order().getPo_department());
		section.setCode(purchaseOrderDTO.getPurchase_order().getPo_department_code());
		section.setName(purchaseOrderDTO.getPurchase_order().getPo_department_code());
		orderXML.setSection(section);

		JAXBContext jc = getJC();
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		m.marshal(orderXML, sw);
		String msg = sw.toString();
		soaMessage.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Order: " + orderXML.getNumber());
		soaMessage.info(msg);
		schmanager.doSendMessageQueue(SITENAME, credential.getCompanyname(), orderXML.getNumber(), msg, null);
		logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Order: " + orderXML.getNumber() + " enviada a MQ");
	}

	public Ack createJSON(String number) {
		Ack ack = new Ack();
		List<OrderAckDTO> listOrder = new ArrayList<>();
		listOrder.add(new OrderAckDTO(number));
		ack.setOrder_list(listOrder);
		return ack;
	}

	private Details setDetails(ObjectFactory objFactory, List<DetailsDTO> details , String vendor) {
		Details orderDetails = objFactory.createOrderDetails();
		List<Detail> listDetail = orderDetails.getDetail();
		for (DetailsDTO item : details) {
			Detail detail = objFactory.createOrderDetailsDetail();
			detail.setPosition(Integer.parseInt(item.getItem()));
			detail.setSkubuyer(item.getRipley_sku());
			detail.setSkuvendor(item.getVendor_product_code());
			detail.setEan13(item.getRipley_ean13());
			detail.setEan13Buyer(item.getRipley_ean13());
			detail.setProductdescription(item.getItem_description());
			detail.setCodeumc(item.getItem_lpn());
			detail.setCharges(setDetailCharges(objFactory, "0"));
			detail.setDiscounts(setDetailDiscounts(objFactory, "0"));
			
			//CAMPOS VALIDADOS POR CLIENTE.
			detail.setListcost(Double.valueOf(item.getItem_base_cost()));
			detail.setFinalcost(Double.valueOf(item.getItem_net_cost()));
			detail.setListprice(Double.valueOf(item.getItem_sale_price()));
			
			detail.setDescriptionumc("UN");
			detail.setDescriptionumb("unidad");
			detail.setQuantityumc(Integer.valueOf(item.getItem_quantity()));
			detail.setUmbXUmc(1);
			detail.setInnerpack(1);
			detail.setEan1(item.getVendor_ean13());
			detail.setObservation(item.getItem_obs());
			listDetail.add(detail);
		}
		return orderDetails;
	}

	private Details setSupplyDetail(ObjectFactory objFactory, List<SupplyDetailDTO> details)
			throws DatatypeConfigurationException {
		Details orderDetails = objFactory.createOrderDetails();
		List<Detail> listDetail = orderDetails.getDetail();

		Map<String, List<SupplyDetailDTO>> skus = details.stream()
				.collect(Collectors.groupingBy((SupplyDetailDTO::getItem_ripley_sku)));
		int position = 1;
		for (String sku : skus.keySet()) {
			int quantity = 0;
			List<SupplyDetailDTO> products = skus.get(sku);
			Detail detail = objFactory.createOrderDetailsDetail();
			detail.setPosition(position);
			detail.setSkubuyer(products.get(0).getItem_ripley_sku());
			detail.setSkuvendor(products.get(0).getItem_case_pack_code());
			detail.setEan13Buyer(products.get(0).getItem_upc());
			detail.setProductdescription(products.get(0).getItem_ripley_sku_description());
			detail.setCharges(setDetailCharges(objFactory, "0"));
			detail.setDiscounts(setDetailDiscounts(objFactory, "0"));
			detail.setInnerpack(products.get(0).getPo_case_pack_item_quantity().intValue());
			detail.setListcost(Double.valueOf(products.get(0).getItem_base_cost()));
			detail.setListprice(products.get(0).getItem_sale_price());
			detail.setFinalcost(products.get(0).getItem_net_cost());
			detail.setDescriptionumc("UN");
			detail.setDescriptionumb("unidad");
			detail.setStylecode(products.get(0).getItem_brand_code());
			detail.setStyledescription(products.get(0).getItem_brand_desciption());
			detail.setUmbXUmc(1);
			detail.setProductdeliverydate(ClassUtilities.StringToXMLGregorianCalendar(products.get(0).getItem_effective_date(), formatter));

			Predistributions predistributions = new Predistributions();
			List<Predistribution> predistribution = predistributions.getPredistribution();
			for (SupplyDetailDTO item : products) {
				Predistribution e = new Predistribution();
				e.setDeliveryplace(setLocal(objFactory, item.getDestination_store_code(), item.getDestination_store_name()));
				e.setQuantity(item.getItem_quantity().intValue());
				predistribution.add(e);
				quantity += item.getItem_quantity().intValue();
			}
			detail.setQuantityumc(quantity);
			detail.setPredistributions(predistributions);
			listDetail.add(detail);
			position++;

		}

		return orderDetails;
	}

	private bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Charges setOrderCharges(ObjectFactory objFactory, String value) {
		bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Charges charges = objFactory.createOrderCharges();
		List<DiscountCharge> listDiscountCharge = charges.getCharges();
		DiscountCharge discountCharge = objFactory.createDiscountCharge();
		discountCharge.setCode("");
		discountCharge.setDescription("");
		discountCharge.setPercentage(false);
		discountCharge.setValue(Double.valueOf(value));
		listDiscountCharge.add(discountCharge);
		return charges;
	}
	
	private Charges setDetailCharges(ObjectFactory objFactory, String value) {
		
		Charges charges = objFactory.createOrderDetailsDetailCharges();
		List<DiscountCharge> listDiscountCharge = charges.getCharge();
		DiscountCharge discountCharge = objFactory.createDiscountCharge();
		discountCharge.setCode("");
		discountCharge.setDescription("");
		discountCharge.setPercentage(false);
		discountCharge.setValue(Double.valueOf(value));
		listDiscountCharge.add(discountCharge);
		return charges;
	
	}
	
	private bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Discounts setOrderDiscount(ObjectFactory objFactory, String value) {
		bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Discounts discounts = objFactory.createOrderDiscounts();
		List<DiscountCharge> listDiscountCharge = discounts.getDiscounts();
		DiscountCharge discountCharge = objFactory.createDiscountCharge();
		discountCharge.setCode("");
		discountCharge.setDescription("");
		discountCharge.setPercentage(false);
		discountCharge.setValue(Double.valueOf(value));
		listDiscountCharge.add(discountCharge);
		return discounts;
	}

	private Discounts setDetailDiscounts(ObjectFactory objFactory, String value) {
		
		Discounts discount = objFactory.createOrderDetailsDetailDiscounts();
		List<DiscountCharge> listDiscountCharge = discount.getDiscount();
		DiscountCharge discountCharge = objFactory.createDiscountCharge();
		discountCharge.setCode("");
		discountCharge.setDescription("");
		discountCharge.setPercentage(false);
		discountCharge.setValue(Double.valueOf(value));
		listDiscountCharge.add(discountCharge);
		return discount;
		
	}

	public Local setLocal(ObjectFactory objFactory, String code, String name) {
		Local local = objFactory.createLocal();
		local.setCode(code);
		local.setName(name);
		return local;
	}

	private Action setAction(ObjectFactory objFactory, OperativeInfoDTO operative_info) {
		if (operative_info != null) {
			Action action = objFactory.createOrderAction();
			action.setCode(operative_info.getAction());
			action.setDescription(operative_info.getAction_info());
			return action;
		}
		return null;
	}

	private Client setCliente(ObjectFactory objFactory, ClientDTO clientDTO) {
		Client client = objFactory.createOrderClient();
		client.setName(clientDTO.getClient_name());
		if (clientDTO.getClient_fis() != null && !clientDTO.getClient_fis().isEmpty()) {
			String[] rut = clientDTO.getClient_fis().split("-");
			client.setIdentity(rut[0]);
			client.setCheckdigit(rut[1]);
		}

		client.setPhone(clientDTO.getClient_phone());
		client.setAddress(clientDTO.getClient_address());
		client.setRegion(clientDTO.getClient_region());
		client.setCommune(clientDTO.getClient_county());
		client.setCity(clientDTO.getClient_city());
		client.setObservation(clientDTO.getClient_delivery_place_obs());

		return client;
	}
}
