package bbr.b2b.portal.classes.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.vaadin.server.VaadinSession;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.components.basics.BbrUI;

public class I18NManager
{
	private I18NManager()
	{
	}

	public static String getI18NString(String resource, String name, String... params)
	{
		String result = "N/A";

		ResourceBundle resourceBundle = null;

		Locale userLocale = getUserLocale();

		try
		{
			if (userLocale != null)
			{
				resourceBundle = ResourceBundle.getBundle(resource, userLocale);
			}
			else
			{
				resourceBundle = ResourceBundle.getBundle(resource);
			}

			if (resourceBundle != null)
			{
				result = resourceBundle.getString(name);

				if (params != null && params.length > 0)
				{
					Object[] objParams = (Object[]) params;
					result = MessageFormat.format(result, objParams);
				}
			}
		}
		catch (MissingResourceException e)
		{
			result = "M/R";
			String devResource = resource != null ? resource.substring(resource.lastIndexOf('.') + 1, resource.length()).toUpperCase() : "";
			System.out.println(result + " -> " + devResource + " -> " + name);
		}
		catch (ClassCastException e)
		{
			result = "N/S";
			String devResource = resource != null ? resource.substring(resource.lastIndexOf('.') + 1, resource.length()).toUpperCase() : "";
			System.out.println(result + " -> " + devResource + " -> " + name);
		}

		return result;
	}

	public static String getI18NString(BbrUI bbrUI, String resource, String name, String... params)
	{
		String result = "N/A";

		ResourceBundle resourceBundle = null;

		Locale userLocale = getUserLocale(bbrUI);

		try
		{
			if (userLocale != null)
			{
				resourceBundle = ResourceBundle.getBundle(resource, userLocale);
			}
			else
			{
				resourceBundle = ResourceBundle.getBundle(resource);
			}

			if (resourceBundle != null)
			{
				result = resourceBundle.getString(name);

				if (params != null && params.length > 0)
				{
					Object[] objParams = (Object[]) params;
					result = MessageFormat.format(result, objParams);
				}
			}
		}
		catch (MissingResourceException e)
		{
			result = "M/R";
			String devResource = resource != null ? resource.substring(resource.lastIndexOf('.') + 1, resource.length()).toUpperCase() : "";
			System.out.println(result + " -> " + devResource + " -> " + name);
		}
		catch (ClassCastException e)
		{
			result = "N/S";
			String devResource = resource != null ? resource.substring(resource.lastIndexOf('.') + 1, resource.length()).toUpperCase() : "";
			System.out.println(result + " -> " + devResource + " -> " + name);
		}

		return result;
	}

	public static String getI18NString(String resource, String name)
	{
		return getI18NString(resource, name, "");
	}

	public static String getI18NString(BbrUI bbrUI, String resource, String name)
	{
		return getI18NString(bbrUI, resource, name, "");
	}

	public static String getErrorMessageBaseResult(BaseResultDTO baseResult, String... params)
	{
		String result = "";

		if (baseResult != null && !baseResult.getStatuscode().equalsIgnoreCase("0"))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, baseResult.getStatuscode(), params);
		}

		return result;
	}
	
	public static String getErrorMessageBaseResult(BbrUI bbrUI, BaseResultDTO baseResult, String... params)
	{
		String result = "";

		if (baseResult != null && !baseResult.getStatuscode().equalsIgnoreCase("0"))
		{
			result = I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_SYSTEM, baseResult.getStatuscode(), params);
		}

		return result;
	}

	public static String getErrorMessageBaseResult(BaseResultDTO baseResult)
	{
		return getErrorMessageBaseResult(baseResult, "");
	}

	private static Locale getUserLocale()
	{
		SessionBean sessionBean = (SessionBean) VaadinSession.getCurrent().getSession().getAttribute(BbrUtilsConstants.SESSION_BEAN_NAME);

		Locale result = (sessionBean != null && sessionBean.getLocale() != null) ? sessionBean.getLocale() : new Locale("es", "ES");

		return result;
	}

	private static Locale getUserLocale(BbrUI bbrUI)
	{
		SessionBean sessionBean = (SessionBean) bbrUI.getSession().getSession().getAttribute(BbrUtilsConstants.SESSION_BEAN_NAME);

		Locale result = (sessionBean != null && sessionBean.getLocale() != null) ? sessionBean.getLocale() : new Locale("es", "ES");

		return result;
	}
}
