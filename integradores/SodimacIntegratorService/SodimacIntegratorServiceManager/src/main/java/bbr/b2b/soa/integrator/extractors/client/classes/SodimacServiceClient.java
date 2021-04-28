package bbr.b2b.soa.integrator.extractors.client.classes;

import java.net.MalformedURLException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_response.AdminArchivoCargaResponse;
import bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_descarga_response.AdminArchivoDescargaResponse;
import bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_descarga_response.Messages;
import bbr.b2b.sodimac.soap.webservices.client.classes.AdminArchivoServiceWebClient;

@Component
public class SodimacServiceClient {
	
	@Autowired	
	private AdminArchivoServiceWebClient serviceClient;
	
	public Messages descargaArchivo(String userName, String password, String requestType) throws MalformedURLException{

		AdminArchivoDescargaResponse response = serviceClient.descargaArchivo(userName, password, requestType);
				
		if (response == null){
			return new Messages();
		}
		
		Messages data = response.getMessages();		
		return data;		
		
	}
	
	public Messages descargaArchivo(String userName, String password, String requestType, LocalDateTime since, LocalDateTime until) throws MalformedURLException{

		AdminArchivoDescargaResponse response = serviceClient.descargaArchivo(userName, password, requestType, since, until);
				
		if (response == null){
			return new Messages();
		}
		
		Messages data = response.getMessages();		
		return data;		
		
	}
	
	public bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_response.Messages cargaArchivo(String userName, String password, InventarioProveedor inventario) throws MalformedURLException{

		AdminArchivoCargaResponse response = serviceClient.cargaArchivo(userName, password, inventario);
				
		if (response == null){
			return new bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_response.Messages();
		}
		
		bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_response.Messages data = response.getMessages();		
		return data;		
		
	}
}
