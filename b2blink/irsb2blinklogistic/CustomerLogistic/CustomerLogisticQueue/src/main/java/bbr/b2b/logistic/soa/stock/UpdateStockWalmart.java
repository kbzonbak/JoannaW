package bbr.b2b.logistic.soa.stock;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor.Detalles.Detalle;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.api.managers.classes.GenericResponseResultDTO;
import bbr.b2b.logistic.api.managers.classes.JsonUtils;
import bbr.b2b.logistic.api.managers.classes.RESTConnection;
import bbr.b2b.logistic.api.managers.classes.RESTContentType;
import bbr.b2b.logistic.costumer.walmart.dto.CredentialWalmartDTO;
import bbr.b2b.logistic.costumer.walmart.dto.ItemsResponse;
import bbr.b2b.logistic.costumer.walmart.dto.WalmartItem;
import bbr.b2b.logistic.costumer.walmart.dto.WalmartItemStock;
import bbr.b2b.logistic.costumer.walmart.dto.WalmartLoginResponseDTO;
import bbr.b2b.logistic.costumer.walmart.dto.WalmartStockUpdateResponse;
import bbr.b2b.logistic.customer.data.dto.CredentialDTO;
import bbr.b2b.logistic.customer.data.dto.StockDTO;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;


@Stateless(name = "handlers/UpdateStockWalmart")
public class UpdateStockWalmart implements UpdateStockWalmartLocal {

	private static Logger logger = Logger.getLogger("SOALog");

	public StockDTO StockProcessWalmart(InventarioProveedor inventario, VendorW vendor, String credentials) throws Exception {

		StockDTO result = new StockDTO();
		try {
			CredentialDTO loginCredentials = JsonUtils.getObjectFromJson(credentials, CredentialDTO.class);
			CredentialWalmartDTO loginrequest = new CredentialWalmartDTO();
			loginrequest.setPassword(loginCredentials.getPassword());
			loginrequest.setUsername(loginCredentials.getUser());
	//		loginrequest.setPassword("WSO4SYY6GI27Bt31");
	//		loginrequest.setUsername("921390009");
			WalmartLoginResponseDTO LoginResponse = doLogin(loginrequest);
	
			if (LoginResponse.getAccess_token() != null) {
				logger.info(LoginResponse.getAccess_token());
	
				//WalmartStockUpdateRequest stockUpdateRequest = new WalmartStockUpdateRequest();
				List<WalmartItem> items = new ArrayList<WalmartItem>();
				WalmartItemStock walmartstock = new WalmartItemStock();
				for (Detalle detalle : inventario.getDetalles().getDetalle()) {
					
					WalmartItem item = new WalmartItem();
					item.setITEM_NBR(Long.valueOf(detalle.getSku()));
					item.setSTOCK(Long.valueOf(detalle.getCantidad()));
					items.add(item);
				}
				
				walmartstock.setItems(items);
				//stockUpdateRequest.setStockupdate(walmartstock);
				
				WalmartStockUpdateResponse stockUpdateResponse = doStockUpdate(LoginResponse.getAccess_token(), walmartstock);
				
				if (stockUpdateResponse == null){
					
					String error ="Error en actualización de stock Proveedor: " + vendor.getName()+ " no se obtuvo respuesta del servidor";
					logger.error(error);
					BaseResultDTO[] detalle = new BaseResultDTO[1];
					BaseResultDTO baseResultDTO = new BaseResultDTO();
					baseResultDTO.setStatuscode("-1");
					baseResultDTO.setStatusmessage(error);
					detalle[0] = baseResultDTO;
					
					result.setDetalles(detalle);
					result.setStatuscode("-1");
					result.setStatusmessage("Error en actualización de stock");
					return result;
					
				}else{
				
					String status = "0";
					String statusMessage = "Inventario procesado exitosamente";
					BaseResultDTO[] detalle = null;
					int errorsize = stockUpdateResponse.getItems().getError().size();
					if (errorsize > 0) {
						String error = "";
						status = "-1";
						statusMessage = "Error al procesar mensaje de inventario";
						detalle = new BaseResultDTO[errorsize];
						for (int i = 0; i < errorsize; i++) {
							BaseResultDTO baseResultDTO = new BaseResultDTO();
							baseResultDTO.setStatuscode("-1");
							baseResultDTO.setStatusmessage(stockUpdateResponse.getItems().getError().get(i));
							detalle[i] = baseResultDTO;
							error = stockUpdateResponse.getItems().getError().get(i) + ", ";
							
						}
						logger.error("El stock de los siguientes SKU no se ha podido actualizar. " + error);
					}
					result.setDetalles(detalle);
					result.setStatuscode(status);
					result.setStatusmessage(statusMessage);
					return result;
				}
	
			} else {
				String error ="Error en actualización de stock Proveedor: " + vendor.getName() + " no se pudo obtener token (error al realizar login)";
				logger.error(error);
				BaseResultDTO[] detalle = new BaseResultDTO[1];
				BaseResultDTO baseResultDTO = new BaseResultDTO();
				baseResultDTO.setStatuscode("-1");
				baseResultDTO.setStatusmessage(error);
				detalle[0] = baseResultDTO;
				
				result.setDetalles(detalle);
				result.setStatuscode("-1");
				result.setStatusmessage("Error en actualización de stock");
				return result;
	//			throw new OperationFailedException(
	//					"Error en actualización de stock Proveedor: " + vendor.getName() + " no se pudo obtener token");
			}
		} catch (Exception e) {
			String error ="Error en actualización de stock Proveedor: " + vendor.getName() + " no se pudo obtener token (error al realizar login)";
			logger.error(error);
			BaseResultDTO[] detalle = new BaseResultDTO[1];
			BaseResultDTO baseResultDTO = new BaseResultDTO();
			baseResultDTO.setStatuscode("-1");
			baseResultDTO.setStatusmessage(error);
			detalle[0] = baseResultDTO;
			
			result.setDetalles(detalle);
			result.setStatuscode("-1");
			result.setStatusmessage("Error interno en actualización de stock");
			return result;
		}

	}

	public WalmartLoginResponseDTO doLogin(CredentialWalmartDTO loginCredentials) throws Exception {
		WalmartLoginResponseDTO loginResponse = new WalmartLoginResponseDTO();

		try {
			String baseUrl = B2BSystemPropertiesUtil.getProperty("WALMARTBASEURL");
			String method = B2BSystemPropertiesUtil.getProperty("WALMARTLOGIN");
			String url =  baseUrl + method; //https://api.b2b.staging.walmartdigital.cl/v1/users/login
			// SE REALIZA UNA OPERACION POST
			
			Gson gson = new Gson();
			String authJson = gson.toJson(loginCredentials);
			
			HttpPost httpPost = RESTConnection.getInstance().getHttpPostEndpoint(url, authJson, RESTContentType.JSON);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpPost);

			if (!responseResult.getStatuscode().equals("0")) {
				logger.error("Código: " + responseResult.getStatuscode() + " Mensaje: "
						+ responseResult.getStatusmessage() + "Response: " + responseResult.getJsonResponse());
			} else {
				// CONVIERTE RESPUESTA JSON A OBJETO
				loginResponse = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), WalmartLoginResponseDTO.class);
			}

		} catch (Exception e) {
			String error = "Ha ocurrido un error al realizar login "; //+ e.getMessage()
			//e.printStackTrace();
			loginResponse.setAccess_token(null);
			loginResponse.setStatuscode("-1");
			loginResponse.setStatusmessage(error);
			return loginResponse;
			//throw new Exception("Ha ocurrido un error al realizar login " + e.getMessage());
		}

		return loginResponse;
	}
	

	public WalmartStockUpdateResponse doStockUpdate(String token, WalmartItemStock stockUpdateRequest) {
		WalmartStockUpdateResponse stockUpdateResponse = null;
		try {
			// CONVIERTE OBJETO A JSON
			String initiParamJson = JsonUtils.getJsonFromObject(stockUpdateRequest, WalmartItemStock.class);
			String baseUrl = B2BSystemPropertiesUtil.getProperty("WALMARTBASEURL");
			String method = B2BSystemPropertiesUtil.getProperty("WALMARTSTOCKUPDATEMETHOD");
			String url = baseUrl + method; 

			System.out.println(initiParamJson);

			HttpPut httpPut = RESTConnection.getInstance().getHttpPutEndpoint(url, initiParamJson, token, RESTContentType.JSON);
			GenericResponseResultDTO responseResult = RESTConnection.getInstance().executeRequest(httpPut);

			if (responseResult.getStatuscode().equals("0")) {
				// CONVIERTE RESPUESTA JSON A OBJETO
				stockUpdateResponse = new WalmartStockUpdateResponse();
				logger.info(responseResult.getJsonResponse());
				stockUpdateResponse = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), WalmartStockUpdateResponse.class);
			} else {
				String error = "Código: " + responseResult.getStatuscode() + " Mensaje: "+ responseResult.getStatusmessage() + " response -> " + responseResult.getJsonResponse();
				logger.error(error);
				stockUpdateResponse = new WalmartStockUpdateResponse();
				try{
					stockUpdateResponse = JsonUtils.getObjectFromJson(responseResult.getJsonResponse(), WalmartStockUpdateResponse.class);
				}catch (Exception e) {
					ItemsResponse items = new ItemsResponse();
					List<String> itemerrors = new ArrayList<String>();
					itemerrors.add(error);
					items.setError(itemerrors);
					stockUpdateResponse.setItems(items);
				}
			}

		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getCause().toString());
			return stockUpdateResponse;
		}

		return stockUpdateResponse;
	}

}
