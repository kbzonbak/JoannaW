package bbr.b2b.portal.servlets.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.ui.TestUI;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/test/*"})
@VaadinServletConfiguration(productionMode = BbrAppConstants.PRODUCTION_MODE, ui = TestUI.class)
public class TestServlet extends VaadinServlet 
{
	private static final long serialVersionUID = 35717913265117337L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		super.service(request, response);
	}
}
