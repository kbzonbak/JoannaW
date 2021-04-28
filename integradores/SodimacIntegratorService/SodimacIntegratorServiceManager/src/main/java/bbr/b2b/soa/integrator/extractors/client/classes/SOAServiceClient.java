package bbr.b2b.soa.integrator.extractors.client.classes;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bbr.b2b.soa.soap.webservices.client.classes.ServiceManagerWebClient;
import bbr.b2b.soa.soap.webservices.classes.GetCredentialsBySiteServiceResponse;
import bbr.b2b.soa.soap.webservices.classes.InitParamCSDTO;

@Component
public class SOAServiceClient {
	
	@Autowired	
	private ServiceManagerWebClient serviceClient;
	
	public List<InitParamCSDTO> getCredentials(String sitename, String service) throws MalformedURLException{

		GetCredentialsBySiteServiceResponse response = serviceClient.getCredentialsBySiteService(sitename, service);
				
		if (response == null){
			return new ArrayList<InitParamCSDTO>();
		}
		
		List<InitParamCSDTO> data = response.getReturn();		
		return data;		
		
	}
}
