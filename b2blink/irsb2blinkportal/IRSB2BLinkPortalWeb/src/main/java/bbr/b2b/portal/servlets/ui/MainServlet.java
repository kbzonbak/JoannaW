package bbr.b2b.portal.servlets.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.ui.MainUI;
import bbr.b2b.portal.classes.utils.app.AppCustomizedSystemMessages;
import bbr.b2b.portal.constants.PortalConstants;
import cl.bbr.core.classes.utilities.BbrUtils;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/main/*"})
@VaadinServletConfiguration(productionMode = BbrAppConstants.PRODUCTION_MODE, ui = MainUI.class  , heartbeatInterval = -1 )
public class MainServlet extends VaadinServlet 
{
	private static final long serialVersionUID = 7488224768481453850L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		super.service(request, response);
		this.getService().setSystemMessagesProvider(new AppCustomizedSystemMessages());
		String fullContext = BbrUtils.getInstance().getFullContextPathFromRequest(request);
		PortalConstants.getInstance().setWebProjectPath(fullContext);	
	}

}
