package bbr.b2b.portal.classes.factory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.rmi.PortableRemoteObject;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;
import bbr.b2b.portal.users.managers.classes.CompanyTypeReportManagerLocal;
import bbr.b2b.portal.users.managers.classes.UserReportManagerLocal;
import bbr.b2b.users.report.classes.CompanyArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyResultDTO;
import bbr.b2b.users.report.classes.CompanyTypeResultDTO;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UserInitParamDTO;
import bbr.b2b.users.report.classes.UserMergeInitParamDTO;
import bbr.b2b.users.report.classes.UserResultDTO;

public class OpenServicesEJBFactory 
{

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static OpenServicesEJBFactory _instance = new OpenServicesEJBFactory();
	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	private OpenServicesEJBFactory() 
	{		
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PUBLIC METHODS
	// ****************************************************************************************
	public CompanyTypeResultDTO findCompanyTypeById(Long typeId)
	{
		return this.getCompanyTypeReportManagerLocal().findTypeCompanyById(typeId);
	}
	
	public CompanyResultDTO findCompanyById(Long companyId)
	{
		return this.getUserReportManagerLocal().findCompanyById(companyId);
	}
	
	public CompanyArrayResultDTO findCompanyByLikeName(String name)
	{
		return this.getUserReportManagerLocal().findCompanyLikeName(name);
	}

	public BaseResultDTO updateUser(UserDTO user)
	{
		return this.getUserReportManagerLocal().updateUser(user);
	}

	public UserResultDTO getUserByLogId(String logid)
	{
		return this.getUserReportManagerLocal().getUserByLogId(logid);
	}
	
	public BaseResultDTO doLogOut(String refreshToken) 
	{
		return this.getUserReportManagerLocal().doLogOut(refreshToken);
	}
	
	public BaseResultDTO validateActiveSessions(UserDTO user, String accessToken)
	{
		return this.getUserReportManagerLocal().validateActiveSessions(user, accessToken);
	}
	
	public UserResultDTO addBasicUser(UserInitParamDTO userInitParam) 
	{
		return this.getUserReportManagerLocal().addBasicUser(userInitParam);
	}
	
	public UserResultDTO doMergeUserAttributes(UserMergeInitParamDTO userMergeInitParam)
	{
		return this.getUserReportManagerLocal().doMergeUserAttributes(userMergeInitParam);
	}
	
	
	// ****************************************************************************************
	// ENDING SECTION 		---->			PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PRIVATE METHODS
	// ****************************************************************************************
	protected static OpenServicesEJBFactory getFactory() 
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
	
	private UserReportManagerLocal getUserReportManagerLocal()
	{	
		UserReportManagerLocal manager = (UserReportManagerLocal)getEJBHomes("UserReportManagerLocal");
		return manager;
	}

	private CompanyTypeReportManagerLocal getCompanyTypeReportManagerLocal()
	{	
		CompanyTypeReportManagerLocal manager = (CompanyTypeReportManagerLocal)getEJBHomes("CompanyTypeReportManagerLocal");
		return manager;
	}

	// ****************************************************************************************
	// ENDING SECTION 		---->			PRIVATE METHODS
	// ****************************************************************************************

	
}
