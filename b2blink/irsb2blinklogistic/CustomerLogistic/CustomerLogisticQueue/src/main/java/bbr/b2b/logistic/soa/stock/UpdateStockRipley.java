package bbr.b2b.logistic.soa.stock;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor.Detalles.Detalle;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.api.managers.classes.GenericResponseResultDTO;
import bbr.b2b.logistic.api.managers.classes.JsonUtils;
import bbr.b2b.logistic.api.managers.classes.RESTConnection;
import bbr.b2b.logistic.api.managers.classes.RESTContentType;
import bbr.b2b.logistic.customer.data.dto.CredentialDTO;
import bbr.b2b.logistic.customer.data.dto.StockDTO;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.customer.ripley.dto.ItemStock;
import bbr.b2b.logistic.customer.ripley.dto.LoginResponseDTO;
import bbr.b2b.logistic.customer.ripley.dto.StockUpdateRequest;
import bbr.b2b.logistic.customer.ripley.dto.StockUpdateResponse;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.esb.services.webservices.facade.InitParamCSDTO;
import bbr.esb.services.webservices.facade.ServiceManagerServer;
import bbr.esb.services.webservices.facade.ServiceManagerServerService;
import sun.misc.BASE64Encoder;


@Stateless(name = "handlers/UpdateStockRipley")
public class UpdateStockRipley implements UpdateStockRipleyLocal {

	private static Logger logger = Logger.getLogger("SOALog");
	
	
	public StockDTO StockProcessRipley(InventarioProveedor inventario, VendorW vendor, String credentials) throws Exception{
		StockDTO result = new StockDTO();
		
		String codProveedor = inventario.getVendedor().getCodigo().trim();
		
		CredentialDTO loginCredentials = JsonUtils.getObjectFromJson(credentials, CredentialDTO.class);
		LoginResponseDTO LoginResponse = doLogin(loginCredentials.getUser(),loginCredentials.getPassword());
		
		
		if (LoginResponse.getAccess_token() != null) {
			logger.info(LoginResponse.getAccess_token());

			StockUpdateRequest stockUpdateRequest = new StockUpdateRequest();
			List<String> skuError = new ArrayList<String>();
			for (Detalle detalle : inventario.getDetalles().getDetalle()) {
				stockUpdateRequest
						.setStock_update(new ItemStock(isLGOrder(codProveedor) ? "2840" : detalle.getCodLocal(),
								detalle.getSku(), detalle.getCantidad(), "", "", ""));
				
				String localError = ProcessStock(LoginResponse.getAccess_token(), stockUpdateRequest);

				if(localError.startsWith("ERROR")){
					skuError.add(localError);
				}
			}

			String status = "0";
			String statusMessage = "Inventario procesado exitosamente";
			BaseResultDTO[] detalle = null;
			if (skuError.size() > 0) {
				
				status = "-1";
				statusMessage = "Error al procesar mensaje de inventario";
				detalle = new BaseResultDTO[skuError.size()];
				for (int i = 0; i < skuError.size(); i++) {
					BaseResultDTO baseResultDTO = new BaseResultDTO();
					baseResultDTO.setStatuscode("-1");
					baseResultDTO.setStatusmessage(skuError.get(i));
					detalle[i] = baseResultDTO;
				}
			}
			
			result.setDetalles(detalle);
			result.setStatuscode(status);
			result.setStatusmessage(statusMessage);
			return result;
			
		} else {
			throw new OperationFailedException("Búsqueda de ordenes normales. Proveedor: " + vendor.getName() + " no se pudo obtener token");
		}
		
		
	}
	
	
	public LoginResponseDTO doLogin(String username, String password) throws Exception {
		LoginResponseDTO loginResponse = new LoginResponseDTO();

		try {
			String baseUrl = B2BSystemPropertiesUtil.getProperty("RIPLEYBASEURL");
			String method = B2BSystemPropertiesUtil.getProperty("RIPLEYLOGIN");
			String url = baseUrl + method; // "http://staging.sellercenter.ripleylabs.com/api/v1/auth/login/vendor";

			// SE REALIZA UNA OPERACION POST
			String authString = username + ":" + password;
			String accessToken = new BASE64Encoder().encode(authString.getBytes());

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

	private boolean isLGOrder(String vendorRut) {
		// Tomar el código de LG
		String LGCode = B2BSystemPropertiesUtil.getProperty("RUTLG");
		return vendorRut.compareTo(LGCode) == 0;
	}

	int numberMaxAttempUpdateStock = 3;

	private String ProcessStock(String token, StockUpdateRequest stockUpdateRequest) throws OperationFailedException {

		for (int i = 0; i < numberMaxAttempUpdateStock; i++) {
			StockUpdateResponse stockUpdateResponse = doStockUpdate(token, stockUpdateRequest);
			if (!stockUpdateResponse.getStatus_code().equals("200")) {
				if (i == (numberMaxAttempUpdateStock - 1)) {
					return "ERROR Sku: " + stockUpdateResponse.getSku() + " Warehouse: " + stockUpdateResponse.getWarehouse()
							+ " Status: " + stockUpdateResponse.getStatus_code() + " Message: "
							+ stockUpdateResponse.getStatus_message();
				}
			} else {
				return "Sku: " + stockUpdateResponse.getSku() + " Warehouse: " + stockUpdateResponse.getWarehouse()
						+ " Status: " + stockUpdateResponse.getStatus_code() + " Message: "
						+ stockUpdateResponse.getStatus_message();
			}
		}
		return "";
	}

	public StockUpdateResponse doStockUpdate(String token, StockUpdateRequest stockUpdateRequest) {
		StockUpdateResponse stockUpdateResponse = new StockUpdateResponse();
		try {
			// CONVIERTE OBJETO A JSON
			String initiParamJson = JsonUtils.getJsonFromObject(stockUpdateRequest, StockUpdateRequest.class);
			String baseUrl = B2BSystemPropertiesUtil.getProperty("RIPLEYBASEURL");
			String method = B2BSystemPropertiesUtil.getProperty("RIPLEYSTOCKUPDATEMETHOD");
			String url = baseUrl + method; // "http://staging.sellercenter.ripleylabs.com/api/v1/inventory/stock";

			System.out.println(initiParamJson);// SE REALIZA UNA OPERACION POST

			HttpPatch httpPatch = RESTConnection.getInstance().getHttpPatchEndpoint(url, initiParamJson, token,
					RESTContentType.JSON);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpPatch);

			if (responseResult.getStatuscode().equals("0")) {
				// CONVIERTE RESPUESTA JSON A OBJETO
				logger.info(responseResult.getJsonResponse());
				stockUpdateResponse = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(),
						StockUpdateResponse.class);
				
				try{
					
					boolean b = stockUpdateResponse.getStatus_code().equals("200");
					
				}catch(NullPointerException e){
					//cuando el status es 500 y/o no funciona la transformacion de json a objeto
					stockUpdateResponse.setStatus_code(responseResult.getStatuscode());
					stockUpdateResponse.setStatus_message(responseResult.getStatusmessage());
					stockUpdateResponse.setSku("-");
					stockUpdateResponse.setWarehouse("-");
				}
				
			} else {
				logger.error("Código: " + responseResult.getStatuscode() + " Mensaje: "
						+ responseResult.getStatusmessage() + " response -> " + responseResult.getJsonResponse());
				stockUpdateResponse = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(),
						StockUpdateResponse.class);
				
				try{
					
					boolean b = stockUpdateResponse.getStatus_code().equals("200");
					
				}catch(NullPointerException e){
					//cuando el status es 500 y no funciona la transformacion de json a objeto
					stockUpdateResponse.setStatus_code(responseResult.getStatuscode());
					stockUpdateResponse.setStatus_message(responseResult.getStatusmessage());
					stockUpdateResponse.setSku("-");
					stockUpdateResponse.setWarehouse("-");
				}
			}

		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("Error de conexión con WS Ripley");
			stockUpdateResponse.setStatus_code("-1");
			stockUpdateResponse.setStatus_message("Error de conexíon con WS");
			stockUpdateResponse.setSku("-");
			stockUpdateResponse.setWarehouse("-");
		}

		return stockUpdateResponse;
	}

}
