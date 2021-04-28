package bbr.esb.services.webservices.facade;

import javax.ejb.EJB;
import javax.jws.WebService;

import bbr.esb.services.managers.ServiceManagerLocal;
import bbr.esb.services.webservices.interfaces.ICustumerServicesManagerServer;

@WebService
public class CustomerServicesManagerServer implements ICustumerServicesManagerServer {
	
	
	
	@EJB
	private ServiceManagerLocal servicemanagerlocal = null;

	public void doAddServiceEventBySericeProvider(String companyrut, String servicecode, String sitename){
		servicemanagerlocal.doAddServiceEventBySericeProvider(companyrut, servicecode, sitename);
	}
}
