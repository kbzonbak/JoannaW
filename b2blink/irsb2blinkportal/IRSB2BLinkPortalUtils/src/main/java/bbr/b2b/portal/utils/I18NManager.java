package bbr.b2b.portal.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.constants.BbrUtilsResources;


public class I18NManager 
{
	private I18NManager(){}
	
	public static String getI18NString(String resource, String name, Locale locale, String... params){
		String result = "N/A";
		
		ResourceBundle resourceBundle = null ;
		
		try 
		{
			if(locale != null)
			{
				resourceBundle = ResourceBundle.getBundle(resource, locale);
			}
			else
			{
				resourceBundle = ResourceBundle.getBundle(resource);
			}
			
			if(resourceBundle != null)
			{
				result = resourceBundle.getString(name);
				
				if (params != null && params.length > 0){
					Object[] objParams = (Object[]) params;
					result = MessageFormat.format(result, objParams);
				}						
			}
		} 
		catch(MissingResourceException e)
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
	
	
	public static String getI18NString(String resource, String name, Locale locale)
	{
		return getI18NString(resource, name, locale,"");
	}

	public static String getErrorMessageBaseResult(BaseResultDTO baseResult, Locale locale, String... params)
	{
		String result = "";

		if(baseResult != null && !baseResult.getStatuscode().equalsIgnoreCase("0"))		  
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, baseResult.getStatuscode(), locale, params);
		}		

		return result;
	}
	
	
	public static String getErrorMessageBaseResult(BaseResultDTO baseResult, Locale locale)
	{
		return getErrorMessageBaseResult(baseResult, locale, "");
	}
}
