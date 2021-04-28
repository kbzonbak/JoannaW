package bbr.b2b.portal.servlets.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

//import bbr.b2b.commercial.report.classes.PeriodProcessResultDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.keycloak.classes.JsonUtils;
//import bbr.b2b.logistic.report.classes.OrderReportInitParamDTO;
//import bbr.b2b.logistic.report.classes.OrderStateTypeArrayResultDTO;
//import bbr.b2b.logistic.report.classes.OrdersArrayResultDTO;
//import bbr.b2b.logistic.report.classes.VendorsArrayResultDTO;
import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.ui.MainUI;
import bbr.b2b.portal.constants.BbrUtilsConstants;

/**
 * Servlet para la comunicación entre el monitor y los servicios del portal.
 * 
 * @author Richard Lozada
 * @version 1.0.0
 * 
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/monitor/*" })
@VaadinServletConfiguration(productionMode = BbrAppConstants.PRODUCTION_MODE, ui = MainUI.class)
public class MonitorServlet extends VaadinServlet
{
	private static final long					serialVersionUID			= 35717913265117337L;
	private static final String					KEY_GET_PARAMETER_MODULE	= "module";
	private static final String					KEY_GET_PARAMETER_REPORT	= "report";
//	private static final String					MODULE_LOG					= "log";
//	private static final String					ALL							= "INT_TODOS";
//	private static final String					KEY_GET_PARAMETER_DATA		= "data";
	private static final String					MODULE_USERS				= "users";
	private static final String					MODULE_FIN					= "fin";
//	private static final String					MODULE_COM					= "com";
//	private static final String					COM_PROCESS_DATE			= "com_process_date";
//	private static final String					DATE_SALES_LIMIT			= "date_sales_limit";
//	private static final String					OCS							= "ocs";
	private static final int					ROWS_BY_PAGE				= 20;
	private static final int					PAGE_NUMBER					= 0;
	private static final boolean				IS_PAGINATED				= true;
	private static final String					ALL_USERS					= "all_users";
	private static final String					FINANCES_PROCESS_DATE		= "finances_process_date";
	private static final String					CURRENCIES					= "currencies";
	private static final String					ALL_COMPANIES				= "all_companies";
	private String								status						= "OK";
	private HttpSession							session						= null;
	private KeycloakPrincipal<?>				principal					= null;
	private RefreshableKeycloakSecurityContext	securityContext				= null;
	private HttpServletRequest					request						= null;
	private HttpServletResponse					response					= null;
	private SessionBean							sessionBean					= null;
	private String								report						= null;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.request = request;
		this.response = response;
		this.manageCallToMonitor();
	}

	private void manageCallToMonitor() throws IOException
	{
		// SI LLEGO ACA ES PORQUE EL MONITOREO TIENE PERMISOS POR ENDE CREAMOS
		// UNA SESSION WEB TEMPORAL PARA ACCEDER AL SERVICIO
		principal = (KeycloakPrincipal<?>) request.getUserPrincipal();
		securityContext = (RefreshableKeycloakSecurityContext) principal.getKeycloakSecurityContext();
		if (principal != null)
		{

			if (principal.getKeycloakSecurityContext().getToken().isActive())
			{
				String username = principal.getKeycloakSecurityContext().getToken().getPreferredUsername();
				session = request.getSession(false);

				// SI LA SESION ES NUEVA, NO EXISTE O BIEN NO TIENE ASOCIADO EL
				// SESSIONBEAN SE CREA UNA NUEVA Y SE LE ASIGNA EL SESSIONBEAN
				if (session == null || session.isNew() || session.getAttribute(BbrUtilsConstants.SESSION_BEAN_NAME) == null)
				{
					session = request.getSession(true);
					sessionBean = sessionBeanFactory(username, principal.getKeycloakSecurityContext().getTokenString(), securityContext.getRefreshToken());
					session.setAttribute(BbrUtilsConstants.SESSION_BEAN_NAME, sessionBean);
				}
				
				String module = request.getParameter(KEY_GET_PARAMETER_MODULE);
				this.report = this.request.getParameter(KEY_GET_PARAMETER_REPORT);
				this.checkSelectedModule(module);
			}
			else
			{
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		}
		else
		{
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	private void checkSelectedModule(String module) throws IOException
	{
		switch (module)
		{
			case MODULE_USERS:
				checkUsersModule(this.request, this.response, this.session, this.sessionBean);
				break;
			case MODULE_FIN:
				checkFinModule(this.request, this.response, this.session, this.sessionBean);
				break;
		}
	}

	private SessionBean sessionBeanFactory(String username, String tokenString, String refreshToken)
	{
		SessionBean sessionBean = new SessionBean();
		sessionBean.setUserName(username);
		sessionBean.setAccessToken(principal.getKeycloakSecurityContext().getTokenString());
		sessionBean.setRefreshToken(securityContext.getRefreshToken());
		sessionBean.setLogged(true);
		return sessionBean;
	}


	private void checkUsersModule(HttpServletRequest request, HttpServletResponse response, HttpSession session, SessionBean sessionBean) throws IOException
	{
		this.status = "";
		BaseResultDTO result = null;
		try
		{
			if (this.report.equalsIgnoreCase(ALL_USERS))
			{
				result = EJBFactory.getMonitorEJBFactory().getUserReportManagerLocal().getAllUsers(ROWS_BY_PAGE, PAGE_NUMBER, IS_PAGINATED);
			}
			else if (this.report.equalsIgnoreCase(ALL_COMPANIES))
			{
				result = EJBFactory.getMonitorEJBFactory().getUserReportManagerLocal().getAllCompanies(ROWS_BY_PAGE, PAGE_NUMBER, IS_PAGINATED);
			}
			else
			{
				this.status = "Parametros invalidos";
			}
		}
		catch (Exception e)
		{
			this.status = "Se ha generado un error, causa:" + e.getCause();
		}

		if (result != null)
		{
			try
			{
				printResponse(response, result.getStatuscode(), result.getStatusmessage());
			}
			catch (IOException e)
			{
				this.status = "Se ha generado un error escribiendo la respuesta, causa: " + e.getCause();
				printResponse(response);
			}
		}
		else
		{
			printResponse(response);
		}

		// invalida la sesión
		// session.invalidate();
	}

	private void checkFinModule(HttpServletRequest request, HttpServletResponse response, HttpSession session, SessionBean sessionBean) throws IOException
	{
		this.status = "";
//		BaseResultDTO result = null;
		try
		{
			if (this.report.equalsIgnoreCase(FINANCES_PROCESS_DATE))
			{
//				result = EJBFactory.getMonitorEJBFactory().getFinancesReportManagerLocal().getLastLoadFinancesProcessDate();
			}
			else if (this.report.equalsIgnoreCase(CURRENCIES))
			{
//				result = EJBFactory.getMonitorEJBFactory().getFinancesReportManagerLocal().getCurrencies();
			}
			else
			{
				this.status = "Parametros invalidos";
			}
		}
		catch (Exception e)
		{
			this.status = "Se ha generado un error, causa:" + e.getCause();
		}
//		if (result != null)
//		{
//			try
//			{
//				printResponse(response, result.getStatuscode(), result.getStatusmessage());
//			}
//			catch (IOException e)
//			{
//				this.status = "Se ha generado un error escribiendo la respuesta, causa: " + e.getCause();
//				printResponse(response);
//			}
//		}
//		else
//		{
//			printResponse(response);
//		}

		// invalida la sesión
		// session.invalidate();
	}

//	private void checkComModule(HttpServletRequest request, HttpServletResponse response, HttpSession session, SessionBean sessionBean) throws IOException
//	{
//		this.status = "";
//		PeriodProcessResultDTO result = null;
//		try
//		{
//			if (this.report.equalsIgnoreCase(COM_PROCESS_DATE))
//			{
//				EJBFactory.getMonitorEJBFactory().getCommercialReportManagerLocal().getLastLoadCommercialProcessDate();
//			}
//			else if (this.report.equalsIgnoreCase(DATE_SALES_LIMIT))
//			{
//				result = EJBFactory.getMonitorEJBFactory().getCommercialReportManagerLocal().getDateSalesLimits();
//			}
//			else
//			{
//				this.status = "Parametros invalidos";
//			}
//		}
//		catch (Exception e)
//		{
//			this.status = "Se ha generado un error, causa:" + e.getCause();
//		}
//
//		if (result != null)
//		{
//			try
//			{
//				printResponse(response, result.getStatuscode(), result.getStatusmessage());
//			}
//			catch (IOException e)
//			{
//				this.status = "Se ha generado un error escribiendo la respuesta, causa: " + e.getCause();
//				printResponse(response);
//			}
//		}
//		else
//		{
//			printResponse(response);
//		}
//
//		// invalida la sesión
//		// session.invalidate();
//	}

	private void printResponse(HttpServletResponse response) throws IOException
	{
		printResponse(response, null, null);
	}

	private void printResponse(HttpServletResponse response, String statusCode, String description) throws IOException
	{
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		Properties properties = new Properties();

		if (status != null && !status.isEmpty())
		{
			properties.put("status", status);
		}

		if (description != null && !statusCode.isEmpty())
		{
			properties.put("code", statusCode);
		}

		if (statusCode != null && !description.isEmpty())
		{
			properties.put("description", description);
		}

		JsonObject result = JsonUtils.createJsonObject(properties);
		pw.print(result);
		pw.flush();
	}

//	private String getVendorDni(HttpServletRequest request, SessionBean sessionBean)
//	{
//		String vendorRut = null;
//		String optionalDataRut = request.getParameter(KEY_GET_PARAMETER_DATA);
//		if (optionalDataRut != null && !optionalDataRut.isEmpty())
//		{
//			vendorRut = optionalDataRut;
//		}
//		else
//		{
//			Long vendorId = Long.valueOf(EJBFactory.getMonitorEJBFactory().getLogisticReportManagerLocal().getAllVendorIds().getAllVendorsIds().iterator().next().toString());
//			Long[] vendorIds = { vendorId };
//			VendorsArrayResultDTO vendor = EJBFactory.getMonitorEJBFactory().getLogisticReportManagerLocal().findVendorsByIds(vendorIds);
//			vendorRut = vendor.getVendors()[0].getDni();
//		}
//		return vendorRut;
//	}
}
