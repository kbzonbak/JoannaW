package bbr.b2b.logistic.soa.stock;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.falabella.b2b.schemas.ol.admin_archivo_carga_request.AdminArchivoCargaRequest;
import com.falabella.b2b.schemas.ol.admin_archivo_carga_request.File;
import com.falabella.b2b.schemas.ol.admin_archivo_carga_request.Files;
import com.falabella.b2b.schemas.ol.admin_archivo_carga_response.AdminArchivoCargaResponse;
import com.falabella.b2b.schemas.ol.admin_archivo_carga_response.Message;

import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor.Detalles.Detalle;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.keycloak.classes.JsonUtils;
import bbr.b2b.irsb2blink.wsclient.classes.ClientConnection;
import bbr.b2b.irsb2blink.wsclient.classes.InitParamsWs;
import bbr.b2b.logistic.costumer.walmart.dto.WalmartStockUpdateResponse;
import bbr.b2b.logistic.customer.data.dto.CredentialWSDTO;
import bbr.b2b.logistic.customer.data.dto.StockDTO;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;

@Stateless(name = "handlers/UpdateStockSodimac")
public class UpdateStockSodimac implements UpdateStockSodimacLocal{

	static InitParamsWs initParamsWs = null;
	private static Logger logger = Logger.getLogger("SOALog");

	public StockDTO StockProcessSodimac(InventarioProveedor inventario, VendorW vendor, String credentials) throws Exception{
		
		
		StockDTO result = new StockDTO();
		//setup();
		
		CredentialWSDTO loginCredentials = JsonUtils.getObjectFromJson(credentials, CredentialWSDTO.class);
		
		initParamsWs = new InitParamsWs();
//		initParamsWs.setPasswordWs("320C770E31EA75CD49CC1D9B7B8BBC94");
//		initParamsWs.setUserNameWs("59162570-5|59162570-5");
//		initParamsWs.setWsEndpoint("http://b2b.sodimac.com/b2bwsextsoclpr/ws/adminArchivoService");
		
		initParamsWs.setUserNameWs(loginCredentials.getUser());
		initParamsWs.setPasswordWs(loginCredentials.getPassword());
		initParamsWs.setWsEndpoint(loginCredentials.getUrl());
		
		initParamsWs.setProxy(Boolean.valueOf(B2BSystemPropertiesUtil.getProperty("PROXY")));
		initParamsWs.setProxyAddress(B2BSystemPropertiesUtil.getProperty("PROXY_HOST"));
		initParamsWs.setProxyPort(Integer.valueOf(B2BSystemPropertiesUtil.getProperty("PROXY_PORT")));
		
		ClientConnection connection = ClientConnection.getInstance(initParamsWs);
		
		if(connection == null){
			throw new Exception("Ha ocurrido un error al realizar conexión con el WS");
		}
		
		AdminArchivoCargaRequest requestCarga = new AdminArchivoCargaRequest();
		Files files = new Files();
		
		StringBuilder skus = new StringBuilder();
		for (Detalle detalle : inventario.getDetalles().getDetalle()) {
			
			skus.append(detalle.getSku()+"|"+detalle.getCantidad()+",");
		}
		
		String archivo = skus.toString();
		archivo = StringUtils.chop(archivo);
		
		//System.out.println(archivo);
		
		File file = new File();
		file.setArchivo(archivo);
		file.setExtensionArchivo("TXT");
		file.setTipoArchivo("eSTKM");
		files.getFile().add(file);
		
		requestCarga.setFiles(files);
		
		// HACE LA LLAMADA AL WS
		AdminArchivoCargaResponse response = connection.getPortClient().cargaArchivo(requestCarga);
		
		if (response == null){
			
			String status = "-1";
			String statusMessage = "Error intentando actualizar stock";
			result.setStatuscode(status);
			result.setStatusmessage(statusMessage);
			return result;
			
		}else{
		
			String status = "0";
			String statusMessage = "Inventario procesado exitosamente";
			BaseResultDTO[] detalle = null;

			// PROCESA RESPONSE
			int errorsize = 0;
			List<Message> message = response.getMessages().getMessage();
			for (Message mes : message) {
				logger.info("SKU: " + mes.getCode() +" RespuestaWS: " +mes.getValue());
				if(!mes.getValue().equals("Carga de archivo exitosa")){
					errorsize++;
				}
			}
			
			if (errorsize > 0) {
				String error = "";
				status = "-1";
				statusMessage = "Error al procesar mensaje de inventario";
				detalle = new BaseResultDTO[errorsize];
				int num = 0;
				for (Message mes : message) {
					if(!mes.getValue().equals("Carga de archivo exitosa")){
						BaseResultDTO baseResultDTO = new BaseResultDTO();
						baseResultDTO.setStatuscode("-1");
						baseResultDTO.setStatusmessage(mes.getValue());
						detalle[num] = baseResultDTO;
						error = mes.getCode() + ", ";
						num++;
					}
				}
				logger.error("El stock de los siguientes SKU no se ha podido actualizar. " + error);
			}
			result.setDetalles(detalle);
			result.setStatuscode(status);
			result.setStatusmessage(statusMessage);
			return result;
		}
	}
}
