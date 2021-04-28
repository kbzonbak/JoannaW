package bbr.b2b.logistic.rest.client;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.keycloak.classes.JsonUtils;
import bbr.b2b.logistic.buyorders.data.dto.CheckOrderInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckOrderResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckReceptionInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.CheckReceptionResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.HistoryInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderDetailReportInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderDetailReportResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderHistoryDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderStateTypeResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.ResendInitParamDTO;
import bbr.b2b.logistic.rest.classes.RESTConnection;
import bbr.b2b.logistic.rest.classes.RESTContentType;
import bbr.b2b.logistic.rest.dto.GenericResponseResultDTO;


public class CustomerLogisticRestFulWSClient {

	private static Logger logger = Logger.getLogger("LogWSClient");

	private static CustomerLogisticRestFulWSClient _instance = null;
	
	private static final String ORDERSTATETYPES = "getorderstatetypes";

	private static final String REPORT = "getOrderReportByVendorAndFilter";
	
	private static final String DETAIL = "getOrderDetailByOrder";
	
	private static final String RESEND = "doResendOC";
	
	private static final String HISTORY = "getOrderHistory";
	
	private static final String CHECK = "doCheckOrderByRetail";
	
	private static final String CHECKRECEPTION = "doCheckReceptionByRetail";
	
	private static String url;

	
	private CustomerLogisticRestFulWSClient() {
	}
	
	public static CustomerLogisticRestFulWSClient getInstance(String urlws) {

		if (_instance == null) {
			url = urlws;
			synchronized (CustomerLogisticRestFulWSClient.class) {
				if (_instance == null) {
					_instance = new CustomerLogisticRestFulWSClient();
				}
			}
		}
		return _instance;
	}
	
	
	public OrderStateTypeResultDTO getOrderStateTypesWS() {
		logger.info("***** INICIO *****");
		
		OrderStateTypeResultDTO resultDTO = new OrderStateTypeResultDTO();

		try {
			String method = "/" + ORDERSTATETYPES;
			String newUrl =  url + method;

			// HACE LA LLAMADA REST
			logger.info("[getOrderStateTypesWS IN] url: " + newUrl);
			HttpGet httpGet = RESTConnection.getInstance().getHttpGetEndpoint(newUrl);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpGet);
			
			if (!responseResult.getStatuscode().equals("0")) {
				logger.error("[getOrderStateTypesWS OUT]: Código " + responseResult.getStatuscode() + " Mensaje: " + responseResult.getStatusmessage());

				resultDTO.setStatusmessage("Error en datos via WS ");
				resultDTO.setStatuscode("-1");
			}
			else {
				logger.info("[getOrderStateTypesWS OUT] json: " +  responseResult.getJsonResponse());
				resultDTO = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), OrderStateTypeResultDTO.class);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[getOrderStateTypesWS]: Error de conexión");
			logger.error("[getOrderStateTypesWS] " + e.getMessage(), e);
			resultDTO.setStatusmessage("Error en el proceso de obtención de datos via WS ");
			resultDTO.setStatuscode("-1");
		}
		
		logger.info("***** FIN *****");
		return resultDTO;
	}
	
	
	public OrderReportResultDTO getOrderReportByVendorAndFilterWS(OrderReportInitParamDTO initParamDTO) {
		logger.info("***** INICIO *****");
		
		OrderReportResultDTO resultDTO = new OrderReportResultDTO();
		
		try {
			String initiParamJson = JsonUtils.getJsonFromObject(initParamDTO, OrderReportInitParamDTO.class);
			String method = "/" + REPORT;
			String newUrl =  url + method;
			
			logger.info("[getOrderReportByVendorAndFilterWS IN] url: " + newUrl);
			logger.info("[getOrderReportByVendorAndFilterWS IN] json:" + initiParamJson);
			
			HttpPost httpPost = RESTConnection.getInstance().getHttpPostEndpoint(newUrl, initiParamJson, RESTContentType.JSON);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpPost);
		
			if (!responseResult.getStatuscode().equals("0")) {
				logger.error("[getOrderReportByVendorAndFilterWS OUT]: Código: " + responseResult.getStatuscode() + " Mensaje: " + responseResult.getStatusmessage() + "Response: " + responseResult.getJsonResponse());
				resultDTO.setStatuscode("-1");
				resultDTO.setStatusmessage("Error en el proceso de obtención de datos via WS ");
				return resultDTO;
			}
			else {
				logger.info("[getOrderReportByVendorAndFilterWS OUT] json: " + responseResult.getJsonResponse());
				resultDTO = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), OrderReportResultDTO.class);
				resultDTO.setStatuscode("0");
			}	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[getOrderReportByVendorAndFilterWS]: Eror de conexión");
			logger.error("[getOrderReportByVendorAndFilterWS] " + e.getMessage(), e);
			resultDTO.setStatuscode("-1");
			resultDTO.setStatusmessage("Error en el proceso de obtención de datos via WS ");
			return resultDTO;
		}
		
		logger.info("***** FIN *****");
		return resultDTO;

	}
	
	public OrderDetailReportResultDTO getOrderDetailByOrder(OrderDetailReportInitParamDTO initParamDTO) {
		logger.info("***** INICIO *****");
		
		OrderDetailReportResultDTO resultDTO = new OrderDetailReportResultDTO();
		
		try {
			String initiParamJson = JsonUtils.getJsonFromObject(initParamDTO, OrderDetailReportInitParamDTO.class);
			String method = "/" + DETAIL;
			String newUrl =  url + method;
			
			logger.info("[getOrderDetailByOrder IN] url: " + newUrl);
			logger.info("[getOrderDetailByOrder IN] json:" + initiParamJson);
			
			HttpPost httpPost = RESTConnection.getInstance().getHttpPostEndpoint(newUrl, initiParamJson, RESTContentType.JSON);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpPost);
		
			if (!responseResult.getStatuscode().equals("0")) {
				logger.error("[getOrderDetailByOrder OUT]: Código: " + responseResult.getStatuscode() + " Mensaje: " + responseResult.getStatusmessage() + "Response: " + responseResult.getJsonResponse());
				resultDTO.setStatuscode("-1");
				resultDTO.setStatusmessage("Error en el proceso de obtención de datos via WS ");
				return resultDTO;
			}
			else {
				logger.info("[getOrderDetailByOrder OUT] json: " + responseResult.getJsonResponse());
				resultDTO = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), OrderDetailReportResultDTO.class);
				resultDTO.setStatuscode("0");
			}	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[getOrderDetailByOrder]: Eror de conexión");
			logger.error("[getOrderDetailByOrder] " + e.getMessage(), e);
			resultDTO.setStatuscode("-1");
			resultDTO.setStatusmessage("Error en el proceso de obtención de datos via WS ");
			return resultDTO;
		}
		
		logger.info("***** FIN *****");
		return resultDTO;

	}
	
	public BaseResultDTO doResendOCWS(ResendInitParamDTO initParamDTO) {
		logger.info("***** INICIO *****");
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try {
			String initiParamJson = JsonUtils.getJsonFromObject(initParamDTO, ResendInitParamDTO.class);
			String method = "/" + RESEND;
			String newUrl =  url + method;
			
			logger.info("[doResendOCWS IN] url: " + newUrl);
			logger.info("[doResendOCWS IN] json:" + initiParamJson);
			
			HttpPost httpPost = RESTConnection.getInstance().getHttpPostEndpoint(newUrl, initiParamJson, RESTContentType.JSON);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpPost);
		
			if (!responseResult.getStatuscode().equals("0")) {
				logger.error("[doResendOCWS OUT]: Código: " + responseResult.getStatuscode() + " Mensaje: " + responseResult.getStatusmessage() + "Response: " + responseResult.getJsonResponse());
				resultDTO.setStatuscode("-1");
				resultDTO.setStatusmessage("Error en el proceso de obtención de datos via WS ");
				return resultDTO;
			}
			else {
				logger.info("[doResendOCWS OUT] json: " + responseResult.getJsonResponse());
				resultDTO = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), BaseResultDTO.class);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[doResendOCWS]: Eror de conexión");
			logger.error("[doResendOCWS] " + e.getMessage(), e);
			resultDTO.setStatuscode("-1");
			resultDTO.setStatusmessage("Error en el proceso de obtención de datos via WS ");
			return resultDTO;
		}
		
		logger.info("***** FIN *****");
		return resultDTO;

	}
	
	public OrderHistoryDTO getOrderHistoryWS(HistoryInitParamDTO initParamDTO) {
		logger.info("***** INICIO *****");
		
		OrderHistoryDTO resultDTO = new OrderHistoryDTO();
		
		try {
			String initiParamJson = JsonUtils.getJsonFromObject(initParamDTO, HistoryInitParamDTO.class);
			String method = "/" + HISTORY;
			String newUrl =  url + method;
			
			logger.info("[getOrderHistoryWS IN] url: " + newUrl);
			logger.info("[getOrderHistoryWS IN] json:" + initiParamJson);
			
			HttpPost httpPost = RESTConnection.getInstance().getHttpPostEndpoint(newUrl, initiParamJson,  RESTContentType.JSON);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpPost);
		
			if (!responseResult.getStatuscode().equals("0")) {
				logger.error("[getOrderHistoryWS OUT]: Código: " + responseResult.getStatuscode() + " Mensaje: " + responseResult.getStatusmessage() + "Response: " + responseResult.getJsonResponse());
				resultDTO.setStatuscode("-1");
				resultDTO.setStatusmessage("Error en el proceso de obtención de datos via WS ");
				return resultDTO;
			}
			else {
				logger.info("[getOrderHistoryWS OUT] json: " + responseResult.getJsonResponse());
				resultDTO = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), OrderHistoryDTO.class);
				resultDTO.setStatuscode("0");
			}	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[getOrderHistoryWS]: Eror de conexión");
			logger.error("[getOrderHistoryWS] " + e.getMessage(), e);
			resultDTO.setStatuscode("-1");
			resultDTO.setStatusmessage("Error en el proceso de obtención de datos via WS ");
			return resultDTO;
		}
		
		logger.info("***** FIN *****");
		return resultDTO;

	}
	
	public CheckOrderResultDTO doCheckOrderByRetail(CheckOrderInitParamDTO initParamDTO) {
		logger.info("***** INICIO *****");
		
		CheckOrderResultDTO resultDTO = new CheckOrderResultDTO();
		
		try {
			String initiParamJson = JsonUtils.getJsonFromObject(initParamDTO, CheckOrderInitParamDTO.class);
			String method = "/" + CHECK;
			String newUrl =  url + method;
			
			logger.info("[doCheckOrderByRetail IN] url: " + newUrl);
			logger.info("[doCheckOrderByRetail IN] json:" + initiParamJson);
			
			HttpPost httpPost = RESTConnection.getInstance().getHttpPostEndpoint(newUrl, initiParamJson,  RESTContentType.JSON);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpPost);
		
			if (!responseResult.getStatuscode().equals("0")) {
				logger.error("[doCheckOrderByRetail OUT]: Código: " + responseResult.getStatuscode() + " Mensaje: " + responseResult.getStatusmessage() + "Response: " + responseResult.getJsonResponse());
				resultDTO.setStatuscode("-1");
				resultDTO.setStatusmessage("Error en el proceso de obtención de datos via WS ");
				return resultDTO;
			}
			else {
				logger.info("[doCheckOrderByRetail OUT] json: " + responseResult.getJsonResponse());
				resultDTO = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), CheckOrderResultDTO.class);
				resultDTO.setStatuscode("0");
			}	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[doCheckOrderByRetail]: Error de conexión");
			logger.error("[doCheckOrderByRetail] " + e.getMessage(), e);
			resultDTO.setStatuscode("-1");
			resultDTO.setStatusmessage("Error en el proceso de obtención de datos via WS ");
			return resultDTO;
		}
		logger.info("***** FIN *****");
		return resultDTO;

	}
	
	public CheckReceptionResultDTO doCheckReceptionByRetail(CheckReceptionInitParamDTO initParamDTO) {
		logger.info("***** INICIO *****");
		
		CheckReceptionResultDTO resultDTO = new CheckReceptionResultDTO();
		
		try {
			String initiParamJson = JsonUtils.getJsonFromObject(initParamDTO, CheckReceptionInitParamDTO.class);
			String method = "/" + CHECKRECEPTION;
			String newUrl =  url + method;
			
			logger.info("[doCheckReceptionByRetail IN] url: " + newUrl);
			logger.info("[doCheckReceptionByRetail IN] json:" + initiParamJson);
			
			HttpPost httpPost = RESTConnection.getInstance().getHttpPostEndpoint(newUrl, initiParamJson,  RESTContentType.JSON);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpPost);
		
			if (!responseResult.getStatuscode().equals("0")) {
				logger.error("[doCheckReceptionByRetail OUT]: Código: " + responseResult.getStatuscode() + " Mensaje: " + responseResult.getStatusmessage() + "Response: " + responseResult.getJsonResponse());
				resultDTO.setStatuscode("-1");
				resultDTO.setStatusmessage("Error en el proceso de obtención de datos via WS ");
				return resultDTO;
			}
			else {
				logger.info("[doCheckReceptionByRetail OUT] json: " + responseResult.getJsonResponse());
				resultDTO = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), CheckReceptionResultDTO.class);
				resultDTO.setStatuscode("0");
			}	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[doCheckReceptionByRetail]: Error de conexión");
			logger.error("[doCheckReceptionByRetail] " + e.getMessage(), e);
			resultDTO.setStatuscode("-1");
			resultDTO.setStatusmessage("Error en el proceso de obtención de datos via WS ");
			return resultDTO;
		}
		logger.info("***** FIN *****");
		return resultDTO;

	}

}
