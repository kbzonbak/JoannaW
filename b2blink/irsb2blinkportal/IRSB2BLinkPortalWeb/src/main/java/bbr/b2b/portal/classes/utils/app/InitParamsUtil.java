package bbr.b2b.portal.classes.utils.app;

import java.util.Locale;

import bbr.b2b.common.adtclasses.classes.BaseInitParamDTO;
import cl.bbr.core.components.basics.BbrUI;

public class InitParamsUtil 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	
	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	private InitParamsUtil() 
	{
		
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************
	
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PUBLIC METHODS
	// ****************************************************************************************
	@SuppressWarnings("unchecked")
	public static <T> T getBasicInitParamInstance(Class<T> type)
	{
		T result = null;
		try
		{
			result = (T) Class.forName(type.getName()).newInstance();
		}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBaseInitParamInstance(Class<T> type, Locale locale)
	{
		T result = null;
		try
		{
			result = (T) Class.forName(type.getName()).newInstance();
			if(result instanceof BaseInitParamDTO)
			{
				((BaseInitParamDTO)result).setLocale(locale);
			}
		}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBaseInitParamInstance(Class<T> type, BbrUI bbrUI)
	{
		T result = null;
		try
		{
			result = (T) Class.forName(type.getName()).newInstance();
			if(result instanceof BaseInitParamDTO)
			{
				if(bbrUI != null && bbrUI.getUser() != null && bbrUI.getUser().getLocale() != null)
				{
					((BaseInitParamDTO)result).setLocale(bbrUI.getUser().getLocale());	
				}
			}
		}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			PUBLIC METHODS
	// ****************************************************************************************
	
	

}
