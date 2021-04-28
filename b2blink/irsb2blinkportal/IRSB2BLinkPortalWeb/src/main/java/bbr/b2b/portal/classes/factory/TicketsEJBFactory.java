//package bbr.b2b.portal.classes.factory;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NameClassPair;
//import javax.naming.NamingEnumeration;
//import javax.rmi.PortableRemoteObject;
//
//import com.vaadin.ui.UI;
//
//import bbr.b2b.portal.classes.beans.SessionBean;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.components.basics.BbrSystemException;
//import bbr.b2b.portal.components.basics.BbrUserException;
//import bbr.b2b.portal.constants.BbrUtilsConstants;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.portal.constants.PortalConstants;
//import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;
//import bbr.b2b.portal.trac.managers.classes.TicketReportManagerLocal;
//import bbr.b2b.portal.users.managers.classes.UserReportManagerLocal;
//import bbr.b2b.users.report.classes.UserDTO;
//import cl.bbr.core.components.basics.BbrUI;
//
//public class TicketsEJBFactory 
//{
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static TicketsEJBFactory _instance = new TicketsEJBFactory();
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//	private TicketsEJBFactory() 
//	{		
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			CONSTRUCTORS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PUBLIC METHODS
//	// ****************************************************************************************
//	public TicketReportManagerLocal getTicketReportManagerLocal() throws BbrUserException, BbrSystemException
//	{	
//		this.validateUserSystem();
//	
//		TicketReportManagerLocal manager = (TicketReportManagerLocal)getEJBHomes("TicketReportManagerLocal");
//		return manager;
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PUBLIC METHODS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PRIVATE METHODS
//	// ****************************************************************************************
//	protected static TicketsEJBFactory getFactory() 
//	{
//		return _instance;
//	}
//	
//	private IGenericEJBInterface getEJBHomes(String interfaceStr) 
//	{
//		IGenericEJBInterface genericInterface = null;
//		try 
//		{		
//			// Raíz de búsqueda
//			Object umreference = getObjectReference(PortalConstants.MANAGER_CLASSPATH, interfaceStr);				
//			genericInterface = (IGenericEJBInterface) PortableRemoteObject.narrow(umreference, umreference.getClass());
//		} 
//		catch (Exception e) 
//		{
//			throw new RuntimeException("Error instanciado session beans", e);
//		}
//
//		return genericInterface;
//	}
//	
//	private Object getObjectReference(String searchParam, String intName)
//	{
//		Object result = null;				
//		try 
//		{
//			Context ctx = (Context) new InitialContext().lookup(searchParam);			
//			NamingEnumeration<NameClassPair> names = ctx.list("");
//			while (names.hasMore()) 
//			{
//				result = (new InitialContext()).lookup(searchParam + "/" + names.next().getName());		
//
//				if (result.getClass().getSimpleName().split("\\$")[0].equals(intName))
//				{
//					break;
//				}
//										
//			}
//		} 
//		catch (Exception e) 
//		{			
//			e.printStackTrace();
//		}
//		
//		return result;					
//	}
//	
//	private void validateUserSystem() throws BbrUserException
//	{
//		if(UI.getCurrent()!= null && UI.getCurrent() instanceof BbrUI && ((BbrUI) UI.getCurrent()).getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME) != null)
//		{
//			SessionBean sessionBean = (SessionBean) ((BbrUI) UI.getCurrent()).getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
//			
//			UserReportManagerLocal manager = (UserReportManagerLocal)getEJBHomes("UserReportManagerLocal");
//
//			UserDTO userData = manager.getUserById(sessionBean.getUserId()).getUser();
//			
//			if(!userData.getSession().equals(sessionBean.getSessionId()))
//			{
//				throw new BbrUserException(BbrUserException.INVALID_SESSION,I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "US1100"));
//			}
//		}
//		else
//		{
//			throw new BbrUserException(BbrUserException.INVALID_SESSION,I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "US1000"));
//		}
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//}
