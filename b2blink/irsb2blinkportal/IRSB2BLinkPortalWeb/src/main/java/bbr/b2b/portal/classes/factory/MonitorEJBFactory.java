package bbr.b2b.portal.classes.factory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.rmi.PortableRemoteObject;

import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;
import bbr.b2b.portal.users.managers.classes.UserReportManagerLocal;

public class MonitorEJBFactory
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static MonitorEJBFactory _instance = new MonitorEJBFactory();
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	private MonitorEJBFactory()
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

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	protected static MonitorEJBFactory getFactory()
	{
		return _instance;
	}

	private IGenericEJBInterface getEJBHomes(String interfaceStr)
	{
		IGenericEJBInterface genericInterface = null;
		try
		{
			// Raíz de búsqueda
			Object umreference = getObjectReference(PortalConstants.MANAGER_CLASSPATH, interfaceStr);
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

//	public LogisticReportManagerLocal getLogisticReportManagerLocal()
//	{
//		LogisticReportManagerLocal manager = (LogisticReportManagerLocal) getEJBHomes("LogisticReportManagerLocal");
//		return manager;
//	}
//
//	public BuyOrderReportManagerLocal getBuyOrderReportManagerLocal()
//	{
//		BuyOrderReportManagerLocal manager = (BuyOrderReportManagerLocal) getEJBHomes("BuyOrderReportManagerLocal");
//		return manager;
//	}
	
	public UserReportManagerLocal getUserReportManagerLocal()
	{
		UserReportManagerLocal manager = (UserReportManagerLocal) getEJBHomes("UserReportManagerLocal");
		return manager;
	}

//	public CommercialReportManagerLocal getCommercialReportManagerLocal()
//	{
//		CommercialReportManagerLocal manager = (CommercialReportManagerLocal) getEJBHomes("CommercialReportManagerLocal");
//		return manager;
//	}
//
//	public FinancesReportManagerLocal getFinancesReportManagerLocal()
//	{	
//		FinancesReportManagerLocal manager = (FinancesReportManagerLocal)getEJBHomes("FinancesReportManagerLocal");
//		return manager;
//	}
	
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
