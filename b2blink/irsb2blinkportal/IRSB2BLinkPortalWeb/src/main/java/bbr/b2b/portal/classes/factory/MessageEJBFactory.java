package bbr.b2b.portal.classes.factory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.rmi.PortableRemoteObject;

import bbr.b2b.portal.classes.jms.managers.interfaces.AppMessagesMngrLocal;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;

public class MessageEJBFactory 
{

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static MessageEJBFactory _instance = new MessageEJBFactory();
	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	private MessageEJBFactory() 
	{		
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PUBLIC METHODS
	// ****************************************************************************************
	public AppMessagesMngrLocal getAppMessagesMngrLocal() 
	{	
		AppMessagesMngrLocal manager = (AppMessagesMngrLocal)getEJBHomes("AppMessagesMngrLocal");
		return manager;
	}
	
	// ****************************************************************************************
	// ENDING SECTION 		---->			PUBLIC METHODS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PRIVATE METHODS
	// ****************************************************************************************
	protected static MessageEJBFactory getFactory() 
	{
		return _instance;
	}
	
	private IGenericEJBInterface getEJBHomes(String interfaceStr) 
	{
		IGenericEJBInterface genericInterface = null;
		try 
		{		
			// Raíz de búsqueda
			Object umreference = getObjectReference(PortalConstants.WEB_CLASSPATH, interfaceStr);				
			genericInterface = (IGenericEJBInterface) PortableRemoteObject.narrow(umreference, umreference.getClass());
		} 
		catch (Exception e) 
		{
			throw new RuntimeException("Error instanciado session beans", e);
		}

		return genericInterface;
	}
	
	private Object getObjectReference(String searchParam, String intName)
	{
		Object result = null;				
		try 
		{
			Context ctx = (Context) new InitialContext().lookup(searchParam);			
			NamingEnumeration<NameClassPair> names = ctx.list("");
			while (names.hasMore()) 
			{
				result = (new InitialContext()).lookup(searchParam + "/" + names.next().getName());		

				if (result.getClass().getSimpleName().split("\\$")[0].equals(intName))
				{
					break;
				}
										
			}
		} 
		catch (Exception e) 
		{			
			e.printStackTrace();
		}
		
		return result;					
	}
	
	
	// ****************************************************************************************
	// ENDING SECTION 		---->			PRIVATE METHODS
	// ****************************************************************************************

	

		

	

	
}
