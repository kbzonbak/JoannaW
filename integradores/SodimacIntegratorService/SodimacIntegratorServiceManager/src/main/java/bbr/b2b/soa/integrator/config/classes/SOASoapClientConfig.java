package bbr.b2b.soa.integrator.config.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import bbr.b2b.soa.soap.webservices.client.classes.ServiceManagerWebClient;

@Configuration
public class SOASoapClientConfig {
	
	@Autowired
	ServiceConfiguration serviceConfig;
	
    @Bean("soa-marshaller")  
    public Jaxb2Marshaller marshaller(){
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("bbr.b2b.soa.soap.webservices.classes");
        return jaxb2Marshaller;
    }

    @Bean
    public ServiceManagerWebClient serviceManagerClient(@Qualifier("soa-marshaller") Jaxb2Marshaller jaxb2Marshaller) {
    	ServiceManagerWebClient articleClient = new ServiceManagerWebClient();
        articleClient.setDefaultUri(serviceConfig.getEndpoint());
        articleClient.setMarshaller(jaxb2Marshaller);
        articleClient.setUnmarshaller(jaxb2Marshaller);
        return articleClient;
    }
}
