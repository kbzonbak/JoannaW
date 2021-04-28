package bbr.b2b.portal.classes.utils.app;

import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.vaadin.server.VaadinServletService;
import com.vaadin.ui.UI;

import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.jms.constants.ChatProfiles;
import bbr.b2b.portal.components.basics.BbrValidateCommercialException;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.UserDTO;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrMessageBox;
import cl.bbr.core.components.basics.BbrUI;

public class AppUtils
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static AppUtils instance = new AppUtils();

	public static AppUtils getInstance()
	{
		return instance;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	private AppUtils()
	{

	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************
	public BbrUser getBbrUserFromUserBean(SessionBean sessionBean)
	{
		BbrUser result = null;
		if (sessionBean != null && sessionBean.getUser() != null)
		{
			UserDTO user = sessionBean.getUser();
			result = new BbrUser();

			result.setName(sessionBean.getUserName());
			result.setLastName(sessionBean.getUserLastName());
			result.setFullName(sessionBean.getUserFullName());
			result.setEnterpriseId(sessionBean.getEnterpriseId());
			result.setEnterpriseCode(sessionBean.getEnterpriseCode());
			result.setEnterpriseDescription(sessionBean.getEnterpriseName());
			result.setLocale(sessionBean.getLocale());

			result.setId(user.getId());
			result.setSessionId(user.getSession());
			result.setRut(user.getPersonalid());
			result.setEmail(user.getEmail());
			result.setPosition(user.getPosition());
			result.setPhoneNumber(user.getPhone());
			result.setChatProfile(ChatProfiles.ADMINISTRATOR);

			if (sessionBean.getCompanyType() != null)
			{
				result.setTypeID(sessionBean.getCompanyType().getId());
				result.setTypeDescription(sessionBean.getCompanyType().getName());
			}
		}

		return result;
	}

	public void doLogOut()
	{
		this.logout();
	}

	public void doLogOut(String message, BbrUI currentUI)
	{
		if (message != null && message.length() > 0)
		{
			if (!currentUI.hasAlertWindowOpen())
			{
				showLogoutMessage(message, currentUI);
			}
		}
		else
		{
			this.logout(currentUI);
		}
	}

	private void logout(BbrUI currentUI)
	{
		Object sessionBeanObj = currentUI.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		SessionBean sessionBean = (sessionBeanObj == null) ? new SessionBean() : (SessionBean) currentUI.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		currentUI.doLogInfo(BbrUtils.getInstance().substitute(BbrAppConstants.SESSION_DESTROYED + " {0} : {1}", sessionBean.getSessionId(), sessionBean.getUserFullName()));

		String loginUrl = currentUI.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "main");
		currentUI.goToURL(loginUrl);

		currentUI.getSession().close();
		currentUI.getSession().getSession().invalidate();
		try
		{
			HttpServletRequest currentRequest = VaadinServletService.getCurrentServletRequest();
			currentRequest.logout();
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void logout()
	{
		BbrUI currentUI = (BbrUI) UI.getCurrent();
		logout(currentUI);
	}

	public String getFullServerContextPath(BbrUI bbrUI, String contextRoot)
	{
		String result = null;
		if (bbrUI != null && bbrUI.getPage() != null && bbrUI.getPage().getLocation() != null)
		{
			URI url = bbrUI.getPage().getLocation();

			result = url.getScheme() + "://" + url.getHost() + ("http".equals(url.getScheme()) && url.getPort() == 80 || "https".equals(url.getScheme()) && url.getPort() == 443 || url.getPort() <= 0 ? "" : ":" + url.getPort());

			result += contextRoot;
		}

		return result;
	}

	/**
	 * Imprime el mensaje con el nombre del metodo.
	 */
	public void showCommerialIsLoadingMessage(BbrValidateCommercialException ex, String methodName)
	{
		String result = (I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, ex.getCode()) != null ? I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, ex.getCode()) : ex.toString());
		String message = " - M/N -> " + methodName;
		System.out.println(result + message);
	}

	/**
	 * El {@link showModalMessage} permite definir si se quiere que aparezca o
	 * no el mensaje, en forma de ventana emergente.
	 * 
	 * @param showModalMessage
	 *            El valor por defecto es true
	 * 
	 */
	public void showCommerialIsLoadingMessage(BbrUI currentUI, String messageCode)
	{
		this.showCommerialIsLoadingMessage(currentUI, messageCode, true);
	}

	/**
	 * 
	 * El {@link showModalMessage} permite definir si se quiere que aparezca o
	 * no el mensaje, en forma de ventana emergente.
	 * 
	 * @param showModalMessage
	 *            si es true se muestra en forma de modal
	 */
	public void showCommerialIsLoadingMessage(BbrUI currentUI, String messageCode, boolean showModalMessage)
	{
		if (showModalMessage)
		{
			if (!currentUI.hasAlertWindowOpen())
			{
				BbrMessageBox.createError(currentUI)
						.withCaption(I18NManager.getI18NString(currentUI, BbrUtilsResources.RES_GENERIC, "windows_title_error"))
						.withHtmlMessage(I18NManager.getI18NString(currentUI, BbrUtilsResources.RES_SYSTEM, messageCode))
						.withWidthForAllButtons("100px")
						.asModal(showModalMessage)
						.open();
			}
		}
	}

	private void showLogoutMessage(String message, BbrUI currentUI)
	{
		BbrMessageBox.createError(currentUI)
				.withCloseButton(() -> logout(currentUI))
				.withCaption(I18NManager.getI18NString(currentUI, BbrUtilsResources.RES_GENERIC, "windows_title_error"))
				.withHtmlMessage(message)
				.withWidthForAllButtons("100px")
				.open();
	}

	public String getFullServerContextPath(BbrUI bbrUI)
	{
		String result = null;
		if (bbrUI != null && bbrUI.getPage() != null && bbrUI.getPage().getLocation() != null)
		{
			URI url = bbrUI.getPage().getLocation();

			result = url.getScheme() + "://" + url.getHost() + ("http".equals(url.getScheme()) && url.getPort() == 80 || "https".equals(url.getScheme()) && url.getPort() == 443 || url.getPort() <= 0 ? "" : ":" + url.getPort());

			result += BbrAppConstants.CONTEXT_PATH;
		}

		return result;
	}
	
	public String getFormattedDate(String bbrDate)
	{
		String result = "";
//		if (BbrDateUtils.getInstance().isValidBbrDate(bbrDate))
//		{
//			LocalDateTime date = (bbrDate != null) ? ZonedDateTime.parse(bbrDate, BbrDateUtils.getInstance().getFormatterDate()).toLocalDateTime() : null;
//			if (date != null)
//			{
//				result = BbrDateUtils.getInstance().toShortDate(date);
//			}
//		}
		return result;
	}
	
}
