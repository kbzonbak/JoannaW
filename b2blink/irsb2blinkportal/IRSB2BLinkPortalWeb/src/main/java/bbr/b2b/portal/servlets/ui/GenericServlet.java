package bbr.b2b.portal.servlets.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.ui.GenericUI;

/**
 * Servlet implementation class GenericServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/*"})
@VaadinServletConfiguration(productionMode = BbrAppConstants.PRODUCTION_MODE, ui = GenericUI.class)
public class GenericServlet extends VaadinServlet 
{
	private static final long serialVersionUID = 8204166476707424833L;
}
