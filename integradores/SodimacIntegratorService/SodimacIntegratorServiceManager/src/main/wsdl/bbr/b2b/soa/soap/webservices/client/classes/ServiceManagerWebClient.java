package bbr.b2b.soa.soap.webservices.client.classes;

import javax.xml.bind.JAXBElement;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import bbr.b2b.soa.soap.webservices.classes.GetCredentialsBySiteService;
import bbr.b2b.soa.soap.webservices.classes.GetCredentialsBySiteServiceResponse;
import bbr.b2b.soa.soap.webservices.classes.ObjectFactory;

public class ServiceManagerWebClient extends WebServiceGatewaySupport{

	public GetCredentialsBySiteServiceResponse getCredentialsBySiteService(String siteCode, String serviceContracted) {
		GetCredentialsBySiteService service = new GetCredentialsBySiteService();
		
		ObjectFactory of = new ObjectFactory();
		JAXBElement<GetCredentialsBySiteService> reqjaxb = of.createGetCredentialsBySiteService(service);
		
		service.setArg0(siteCode);
		service.setArg1(serviceContracted);
				
		JAXBElement<GetCredentialsBySiteServiceResponse> response = (JAXBElement<GetCredentialsBySiteServiceResponse>) getWebServiceTemplate().marshalSendAndReceive(reqjaxb);
		return response.getValue();		
	}	
}
