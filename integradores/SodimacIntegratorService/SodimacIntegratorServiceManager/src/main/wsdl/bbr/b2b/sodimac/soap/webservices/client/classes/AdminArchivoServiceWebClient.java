package bbr.b2b.sodimac.soap.webservices.client.classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor.Detalles.Detalle;
import bbr.b2b.soa.integrator.utils.Authentication;
import bbr.b2b.soa.integrator.utils.SecurityHeader;
import bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_request.AdminArchivoCargaRequest;
import bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_response.AdminArchivoCargaResponse;
import bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_descarga_request.AdminArchivoDescargaRequest;
import bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_descarga_request.File;
import bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_descarga_request.Files;
import bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_descarga_request.ObjectFactory;
import bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_descarga_response.AdminArchivoDescargaResponse;

public class AdminArchivoServiceWebClient extends WebServiceGatewaySupport{

	public AdminArchivoDescargaResponse descargaArchivo(String userName, String password, String requestType) {
		AdminArchivoDescargaRequest service = new AdminArchivoDescargaRequest();
		
		ObjectFactory of = new ObjectFactory();
		JAXBElement<AdminArchivoDescargaRequest> reqjaxb = of.createAdminArchivoDescargaRequest(service);
		
		Files files = new Files();
		File file = new File();
		file.setTipo(requestType);
		files.getFile().add(file);
		service.setFiles(files);	
		
		JAXBElement<AdminArchivoDescargaResponse> response = (JAXBElement<AdminArchivoDescargaResponse>) getWebServiceTemplate().marshalSendAndReceive(reqjaxb, 
				new SecurityHeader(
                new Authentication(userName, password)));
		return response.getValue();
	}	
	
	public AdminArchivoDescargaResponse descargaArchivo(String userName, String password, String requestType, LocalDateTime since, LocalDateTime until) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
		
		AdminArchivoDescargaRequest service = new AdminArchivoDescargaRequest();
		
		ObjectFactory of = new ObjectFactory();
		JAXBElement<AdminArchivoDescargaRequest> reqjaxb = of.createAdminArchivoDescargaRequest(service);
		
		Files files = new Files();
		File file = new File();
		file.setTipo(requestType);
		file.setFechaInicio(dtf.format(since));
		file.setFechaTermino(dtf.format(until));
		
		files.getFile().add(file);
		service.setFiles(files);		
		
		JAXBElement<AdminArchivoDescargaResponse> response = (JAXBElement<AdminArchivoDescargaResponse>) getWebServiceTemplate().marshalSendAndReceive(reqjaxb, 
				new SecurityHeader(
                new Authentication(userName, password)));
		return response.getValue();
	}	
	
	public AdminArchivoCargaResponse cargaArchivo(String userName, String password, InventarioProveedor inventario) {
		AdminArchivoCargaRequest service = new AdminArchivoCargaRequest();
		
		bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_request.ObjectFactory of = new bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_request.ObjectFactory();
		JAXBElement<AdminArchivoCargaRequest> reqjaxb = of.createAdminArchivoCargaRequest(service);
		
		bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_request.Files files = new bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_request.Files();
		
		StringBuilder skus = new StringBuilder();
		for (Detalle detalle : inventario.getDetalles().getDetalle()) {
			
			skus.append(detalle.getSku()+"|"+detalle.getCantidad()+",");
		}
		
		String archivo = skus.toString();
		archivo = StringUtils.chop(archivo);		
		
		bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_request.File file = new bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_request.File();
		file.setArchivo(archivo);
		file.setExtensionArchivo("TXT");
		file.setTipoArchivo("eSTKM");
		files.getFile().add(file);		
		service.setFiles(files);	
				
		JAXBElement<AdminArchivoCargaResponse> response = (JAXBElement<AdminArchivoCargaResponse>) getWebServiceTemplate().marshalSendAndReceive(reqjaxb, 
				new SecurityHeader(
                new Authentication(userName, password)));
		return response.getValue();
	}		
}
