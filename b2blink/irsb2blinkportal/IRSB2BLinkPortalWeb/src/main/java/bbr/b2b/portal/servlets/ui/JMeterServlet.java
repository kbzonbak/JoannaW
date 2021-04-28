package bbr.b2b.portal.servlets.ui;

import com.vaadin.server.ClientConnector;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;

public class JMeterServlet extends VaadinServlet {

	private static final long serialVersionUID = 898354532369443197L;

	public JMeterServlet() {
		System.setProperty(getPackageName() + "." + "disable-xsrf-protection", "true");
		System.setProperty(getPackageName() + "." + "syncIdCheck", "false");
	}

	@Override
	protected VaadinServletService createServletService(DeploymentConfiguration deploymentConfiguration) throws ServiceException {
		JMeterService service = new JMeterService(this, deploymentConfiguration);
		service.init();

		return service;
	}

	private String getPackageName() {
		String pkgName;
		final Package pkg = this.getClass().getPackage();
		if (pkg != null) {
			pkgName = pkg.getName();
		} else {
			final String className = this.getClass().getName();
			pkgName = new String(className.toCharArray(), 0, className.lastIndexOf('.'));
		}
		return pkgName;
	}

	public static class JMeterService extends VaadinServletService {
		private static final long serialVersionUID = -5874716650679865909L;

		public JMeterService(VaadinServlet servlet, DeploymentConfiguration deploymentConfiguration) throws ServiceException {
			super(servlet, deploymentConfiguration);
		}

		@Override
		protected VaadinSession createVaadinSession(VaadinRequest request) throws ServiceException {
			return new JMeterSession(this);
		}
	}

	public static class JMeterSession extends VaadinSession {
		private static final long serialVersionUID = 4596901275146146127L;

		public JMeterSession(VaadinService service) {
			super(service);
		}

		@Override
		public String createConnectorId(ClientConnector connector) 
		{
			String result = "";
			if (connector instanceof Component) 
			{
				Component component = (Component) connector;
				result = component.getId() == null ?  VaadinService.getCurrent().generateConnectorId(VaadinSession.getCurrent(), connector) : component.getId();
			}
			else
			{
				result = VaadinService.getCurrent().generateConnectorId(VaadinSession.getCurrent(), connector) ;	
			}
			
			return result;
		}
	}
}
