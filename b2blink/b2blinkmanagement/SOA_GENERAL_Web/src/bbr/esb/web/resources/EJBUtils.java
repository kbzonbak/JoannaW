package bbr.esb.web.resources;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import bbr.esb.services.managers.ServiceManagerLocal;
import bbr.esb.services.managers.UserManagerLocal;

public class EJBUtils {

	private static EJBUtils instance;

	private EJBUtils() {

	}

	public static EJBUtils getInstance() {
		if (instance != null)
			return instance;
		else {
			synchronized (EJBUtils.class) {
				instance = new EJBUtils();
				return instance;
			}
		}
	}

	public ServiceManagerLocal getServiceManagerLocalEJB() {
		try {
			Object umreference = (new InitialContext()).lookup("java:global/SOA_GENERAL_EAR/SOA_GENERAL_Manager/managers/ServiceManager!bbr.esb.services.managers.ServiceManagerLocal");
			ServiceManagerLocal servicemanagerlocal = (ServiceManagerLocal) PortableRemoteObject.narrow(umreference, ServiceManagerLocal.class);
			return servicemanagerlocal;
		} catch (Exception e) {
			throw new RuntimeException("Error instanciado session beans", e);
		}
	}

	public UserManagerLocal getUserManagerLocalEJB() {
		try {
			Object umreference = (new InitialContext()).lookup("java:global/SOA_GENERAL_EAR/SOA_GENERAL_Manager/managers/UserManager!bbr.esb.services.managers.UserManagerLocal");
			UserManagerLocal usermanagerlocal = (UserManagerLocal) PortableRemoteObject.narrow(umreference, UserManagerLocal.class);
			return usermanagerlocal;
		} catch (Exception e) {
			throw new RuntimeException("Error instanciado session beans", e);
		}
	}

}
