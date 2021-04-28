package bbr.b2b.extractors.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.esb.services.webservices.facade.AccessDeniedException_Exception;
import bbr.esb.services.webservices.facade.InitParamCSDTO;
import bbr.esb.services.webservices.facade.NotFoundException_Exception;
import bbr.esb.services.webservices.facade.OperationFailedException_Exception;
import bbr.esb.services.webservices.facade.ServiceManagerServer;
import bbr.esb.services.webservices.facade.ServiceManagerServerService;

public class WsSoaService {
	
	public List<InitParamCSDTO> getCredentials(String sitename, String service) throws MalformedURLException, AccessDeniedException_Exception, OperationFailedException_Exception, NotFoundException_Exception{

		String endpoint = B2BSystemPropertiesUtil.getProperty("WSSOA");
		ServiceManagerServer serviceClient = new ServiceManagerServerService(new URL(endpoint)).getServiceManagerServerPort();
		List<InitParamCSDTO> response = serviceClient.getCredentialsBySiteService(sitename, service);
		if (response == null){
			return new ArrayList<InitParamCSDTO>();
		}
		return response;
	}
}
