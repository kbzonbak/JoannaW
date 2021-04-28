package bbr.b2b.logistic.rest.client;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.rest.mapping.getStock.VendorDNIWS;
import bbr.b2b.logistic.rest.mapping.putAvailableStock.AvailableStockPutDataWSContainer;
import bbr.b2b.logistic.rest.mapping.putAvailableStock.Inventory;
import bbr.b2b.logistic.utils.LogisticStatusCodeUtils;
import bbr.b2b.logistic.vev.report.classes.AvailableAndDailyStockUpdateInitParamDTO;
import bbr.b2b.logistic.vev.report.classes.AvailableStockSendDataWSDTO;
import bbr.b2b.logistic.vev.report.classes.StockDetailWSDTO;
import bbr.b2b.logistic.vev.report.classes.StockWSDTO;
import bbr.b2b.logistic.vev.report.classes.StockWSReportDataDTO;
import bbr.b2b.logistic.vev.report.classes.WSReportUploadDataDTO;


public class  HitesCLRestFulWSClient {

	private static Logger logger = Logger.getLogger("WSCLIENT");

	private static  HitesCLRestFulWSClient _instance = null;

	private Client restClient;
	private String url;
	private String authorizationHeaderName;
	private String authorizationHeaderValue;

	private HitesCLRestFulWSClient(){

		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		
		url =  LogisticConstants.getWSVeVURL();
		authorizationHeaderName = LogisticConstants.getWSVeV_UserKey();
		authorizationHeaderValue = LogisticConstants.getWSVeV_KeyValue();
		
		restClient = new Client(new URLConnectionClientHandler(
									        new HttpURLConnectionFactory() {
											        Proxy proxy = null;
											        @Override
											        public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
												        if (proxy == null) {
												            if (LogisticConstants.getHttpProxy()) {
												                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(LogisticConstants.getHttpProxyIP(),
												                													 LogisticConstants.getHttpProxyPort()));
												            }
												            else {
												                proxy = Proxy.NO_PROXY;
												            }
											        }
											        return (HttpURLConnection) url.openConnection(proxy);
											    }
									        }), clientConfig);
	}

	public static HitesCLRestFulWSClient getInstance(){
		
		if(_instance == null){
			synchronized (HitesCLRestFulWSClient.class) {
				if(_instance == null) {
					_instance = new HitesCLRestFulWSClient();
				}
			}
		}
		return _instance;
	}

	public StockWSReportDataDTO getStockWS (String vendorrut) {
		
		StockWSReportDataDTO resultDTO = new StockWSReportDataDTO();
		StockWSDTO stockheaderdata = new StockWSDTO();
				
		List<StockDetailWSDTO> stockdetailList = new ArrayList<StockDetailWSDTO>();
		StockDetailWSDTO[] stockDetailArr = null;
		
		ClientResponse resp = null;
		WebResource webResource = null;
		
		VendorDNIWS vendordni = new VendorDNIWS();
		vendordni.setProveedor(vendorrut);
		
		logger.info("getStockWS para rut " + vendorrut);
		try {
			// Stock Disponible
			webResource = restClient.resource(url);
				resp = webResource
				.path("inventory")
				.path("get")				
				.accept("application/json")
				.type("application/json")
				.header(authorizationHeaderName, authorizationHeaderValue)
				.post(ClientResponse.class, vendordni);			

			logger.info("getStockWS para rut: " + vendorrut + " Resp:" + resp.toString());
			
			// Status OK
			if (resp.getStatus() == 200) {
				JSONObject jsondata = resp.getEntity(JSONObject.class);
				logger.info("json: " + jsondata.toString());
				
				// Obteniendo datos del JSON con la salida el WS
				String proveedor = (String) jsondata.get("proveedor");
				
				// Valida si hay datos
				if (jsondata.has("productos")) {
					JSONArray inventory = (JSONArray) jsondata.get("productos");
					
					// Itera sobre el leadtimes y alamacena en lista
					for(int i=0; i<inventory.length(); i++){
						JSONObject inventorydata = (JSONObject) inventory.get(i);
											
						String code			= (String) (inventorydata.isNull("codigo")  ? "-" : inventorydata.get("codigo"));
						String description	= (String) (inventorydata.isNull("descripcion")  ? "-" : inventorydata.get("descripcion"));
						String propro		= (String) (inventorydata.isNull("productoProveedor")? "-" : inventorydata.get("productoProveedor"));
						int availablestock	= (Integer) (inventorydata.isNull("disponible")  ? 0 : inventorydata.get("disponible"));
						int dailyrep		= (Integer) (inventorydata.isNull("diario")  ? 0 : inventorydata.get("diario"));
						String warehouse	= (String) (inventorydata.isNull("bodega") ? "-" : inventorydata.get("bodega")); 
						
						// Carga a lista de salida
						StockDetailWSDTO stockdetail = new StockDetailWSDTO();
						stockdetail.setCode(code);
						stockdetail.setDescription(description);
						stockdetail.setPropro(propro);
						stockdetail.setAvailablestock((availablestock));
						stockdetail.setDailyrep((dailyrep));
						stockdetail.setWarehouse(warehouse);
						stockdetailList.add(stockdetail);
					}
				}
								
				// List a array
				stockDetailArr = (StockDetailWSDTO[]) stockdetailList.toArray(new StockDetailWSDTO[stockdetailList.size()]);
				stockheaderdata.setStockdetailws(stockDetailArr);
				stockheaderdata.setVendorcde(proveedor.toString());
				
				resultDTO.setStockwsdata(stockheaderdata);						
			}
			
			// Respuesta del WS no exitosa
			else {
				logger.error("Envío de Mensaje a WS fallido. Status: " + resp.getStatus());
				
				String mensaje = resp.getStatusInfo().getReasonPhrase();
				logger.error(mensaje);
				
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L7000", mensaje, false);
			}

		} catch(Exception e){
			logger.error(e.getMessage(), e);
			return resultDTO;			
		}
		finally {
			if(resp != null) {
				resp.close();
			}
		}
		
		return resultDTO;
	}
		
	public WSReportUploadDataDTO putAvailableAndDailyRepStockWS(AvailableAndDailyStockUpdateInitParamDTO initParam, boolean isExcel) {
		
		WSReportUploadDataDTO resultDTO = new WSReportUploadDataDTO();
		AvailableStockSendDataWSDTO[] availablestockdata = initParam.getStock();
		String vendorrut = initParam.getVendordni();
		ClientResponse resp = null;
		WebResource webResource = null;
		
		logger.info("putAvailableAndDailyRepStockWS para rut " + vendorrut);
		try {
			// Registros a informar
			List<Inventory> availablestockputdataList = new ArrayList<Inventory>();
			for (int i = 0; i < availablestockdata.length; i++) {
				Inventory inventory = new Inventory();
				inventory.setCodigo(availablestockdata[i].getItemcode());
				inventory.setDiario(availablestockdata[i].getDailyrep());
				inventory.setDisponible(availablestockdata[i].getStockdisp());
				inventory.setBodega(availablestockdata[i].getWarehouse());
				
				availablestockputdataList.add(inventory);
			}
			
			// Copiar los datos de entrada al container para enviar a WS
			AvailableStockPutDataWSContainer availablestockcontainer = new AvailableStockPutDataWSContainer(); 
			availablestockcontainer.setProductos(availablestockputdataList);
			availablestockcontainer.setProveedor(vendorrut);
			availablestockcontainer.setFecha(initParam.getDate());
			availablestockcontainer.setFormatoFecha(initParam.getDateformat());
			availablestockcontainer.setUsuario(initParam.getUser());
			availablestockcontainer.setTipoUsuario(initParam.getUsertype());

			// Enviar datos al WS
			webResource = restClient.resource(url);
			resp = webResource
						.path("inventory")
						.path("update")
						.type("application/json")
						.accept("application/json")
						.header(authorizationHeaderName, authorizationHeaderValue)
						.post(ClientResponse.class, availablestockcontainer);
			
			logger.info("putAvailableStockWS para rut:" + vendorrut + " Resp:" + resp.toString()); 
			
			// Status OK
			if (resp.getStatus() == 200) {
				JSONObject jsondata = resp.getEntity(JSONObject.class);
				logger.info("json: " + jsondata.toString());
				
				resultDTO.setCodews(((Integer) resp.getStatus()).toString());
				resultDTO.setDescriptionws(resp.getStatusInfo().getReasonPhrase());
				
				if (jsondata.has("productos")) {
					JSONArray productos = (JSONArray) jsondata.get("productos");
					int numregs = productos.length();
					
					if (isExcel) {
						// Mensajes para actualización masiva
						List<BaseResultDTO> errorWSList = new ArrayList<BaseResultDTO>();
						BaseResultDTO errorWS;
						for (int i = 0; i < numregs; i++) {
							JSONObject data = (JSONObject) productos.get(i);
							int estado = Integer.parseInt((String) data.get("estado")); // 0: Éxito 1: Error
							if (estado != 0) {
								errorWS = new BaseResultDTO();
								errorWS.setStatuscode((String) data.get("estado"));
								errorWS.setStatusmessage((String)(data.get("mensaje")));
								errorWSList.add(errorWS);
							}
						}
						
						int errors = errorWSList.size();
						
						resultDTO.setErrorsws((BaseResultDTO[]) errorWSList.toArray(new BaseResultDTO[errorWSList.size()]));
						resultDTO.setNumRegsws(numregs - errors); // número de registros actualizados
						resultDTO.setNumErrorsws(errors);	// número de registros con error
					}
					else {
						// Mensajes para actualización única
						JSONObject data = (JSONObject) productos.get(0);
						int estado = Integer.parseInt((String) data.get("estado")); // 0: Éxito 1: Error
						String mensaje = (String)(data.get("mensaje"));
						
						resultDTO.setItemstatus(estado);
						resultDTO.setItemmessage(mensaje);
					}
				}
			}
			
			// Error en el WS
			else {
				logger.error("Envío de Mensaje a WS fallido. Status: " + resp.getStatus());
				
				Integer code = resp.getStatus();
				String mensaje = resp.getStatusInfo().getReasonPhrase();
				
				resultDTO.setCodews(code.toString());
				resultDTO.setDescriptionws(mensaje);
				logger.error(resultDTO.getStatusmessage());
				
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L7000", mensaje, false);
			}
			
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1"); 
		}
		finally {
			if (resp != null) {
				resp.close();
			}
		}
		
		return resultDTO;
	}
}
