package bbr.b2b.soa.integrator.config.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import bbr.b2b.sodimac.soap.webservices.client.classes.AdminArchivoServiceWebClient;

@Configuration
public class SodimacSoapClientConfig {

	@Autowired
	ServiceConfiguration serviceConfig;

	@Bean("sodimac-marshaller")
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPaths("bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_request",
				"bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_carga_response",
				"bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_consulta_request",
				"bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_consulta_response",
				"bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_descarga_request",
				"bbr.b2b.sodimac.soap.webservices.classes.admin_archivo_descarga_response");
		return jaxb2Marshaller;
	}

	@Bean
	public AdminArchivoServiceWebClient adminArchivoService(@Qualifier("sodimac-marshaller") Jaxb2Marshaller jaxb2Marshaller) {
		AdminArchivoServiceWebClient articleClient = new AdminArchivoServiceWebClient();
		articleClient.setDefaultUri(serviceConfig.getEndpoint());
		articleClient.setMarshaller(jaxb2Marshaller);
		articleClient.setUnmarshaller(jaxb2Marshaller);		
		//articleClient.setInterceptors(new ClientInterceptor[]{ securityInterceptor() });
		return articleClient;
	}

	// SE USA PARA AUTENTICACION POR USERNAMETOKEN, SI LAS CREDENCIALES ESTAN DEFINIDAS EN PROPERTIES
//	@Bean
//	public Wss4jSecurityInterceptor securityInterceptor() {
//		Wss4jSecurityInterceptor security = new Wss4jSecurityInterceptor();
//
//		security.setSecurementActions("UsernameToken");		
//
//		security.setSecurementPasswordType("PasswordText");
//		security.setSecurementUsername(serviceConfig.getUserName());
//		security.setSecurementPassword(serviceConfig.getUserPassword());
//		return security;
//	}
}
