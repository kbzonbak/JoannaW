package bbr.b2b.portal.servlets.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.ui.MeliUI;

/**
 * Servlet implementation class MeliServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/meli/*" })
@VaadinServletConfiguration(productionMode = BbrAppConstants.PRODUCTION_MODE, ui = MeliUI.class)
public class MeliServlet extends VaadinServlet
{
	private static final long serialVersionUID = -6126411699388663890L;
}
