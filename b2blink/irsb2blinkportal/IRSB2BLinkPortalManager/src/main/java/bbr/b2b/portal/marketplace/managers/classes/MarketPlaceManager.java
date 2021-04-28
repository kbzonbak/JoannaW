package bbr.b2b.portal.marketplace.managers.classes;


import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.http.client.methods.HttpPut;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.util.LoggingUtil;
import bbr.b2b.portal.api.managers.classes.GenericResponseResultDTO;
import bbr.b2b.portal.api.managers.classes.InitParamMLDTO;
import bbr.b2b.portal.api.managers.classes.JsonUtils;
import bbr.b2b.portal.api.managers.classes.RESTConnection;
import bbr.b2b.portal.api.managers.classes.RESTContentType;
import bbr.b2b.portal.users.managers.classes.UserReportManagerLocal;
import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;

@Stateless(name = "managers/MarketPlaceManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MarketPlaceManager implements MarketPlaceManagerLocal
{
	private static LoggingUtil logger = new LoggingUtil(MarketPlaceManager.class);

	@EJB
	private UserReportManagerLocal userReportManager;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	
	public BaseResultDTO doAuthenticateML(String vendorcode, String code, Long uskey)
	{

		BaseResultDTO result = new BaseResultDTO();

		try {
			
			InitParamMLDTO initparam = new InitParamMLDTO();
			initparam.setAuthorizationCode(code);
			String initiParamJson = JsonUtils.getJsonFromObject(initparam, InitParamMLDTO.class);
			
			String endpoint = B2BSystemPropertiesUtil.getProperty("AUTHMELI_ENDPOINT");
			String url = endpoint +"/api/company/"+vendorcode+"/authorizations";
			System.out.println(url);
			//String url = "http://10.200.200.110:8080/api/company/"+vendorcode+"/authorizations";
			
			HttpPut httpPut = RESTConnection.getInstance().getHttpPutEndpoint(url, initiParamJson, RESTContentType.JSON);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpPut);

			result.setStatuscode(responseResult.getStatuscode());
			result.setStatusmessage(responseResult.getStatusmessage());
			
			if(responseResult.getStatuscode().equals("200")){
				logger.info("Llamada a ws exitosa");
				logger.info("Status: "+responseResult.getStatuscode());
				logger.info("Message: "+ responseResult.getStatusmessage());
			}else{
				logger.error("Error en llamada a ws");
				logger.error("Status: "+responseResult.getStatuscode());
				logger.error("Message: "+ responseResult.getStatusmessage());
			}
			

		} catch (Exception e) {
			logger.error("Error en llamada a ws");
			e.printStackTrace();
			result.setStatuscode("-1");
			result.setStatuscode("Error de conexión");
		}

		return result;
	}

}

