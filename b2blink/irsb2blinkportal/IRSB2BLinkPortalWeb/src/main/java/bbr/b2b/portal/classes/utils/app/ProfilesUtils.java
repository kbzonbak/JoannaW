package bbr.b2b.portal.classes.utils.app;

import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.users.report.classes.ProfileArrayResultDTO;
import cl.bbr.core.classes.basics.BbrUser;

public class ProfilesUtils 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static ProfilesUtils instance = new ProfilesUtils();
	public static ProfilesUtils getInstance()
	{
		return instance;
	}
	
	public static final String ADMINISTRATOR_PROFILE_NAME 	= "ADMINISTRATOR";
	public static final String PROV_MASTER_PROFILE_NAME 	= "PROV_MASTER";

	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	private ProfilesUtils() 
	{
		
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************
	
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PUBLIC METHODS
	// ****************************************************************************************
	public boolean isSystemAdministrator(BbrUser user)
	{
		boolean result = false;
		
		if(user!=null)
		{
			try 
			{
				ProfileArrayResultDTO profileResult = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal().findProfilesLikeCode(user.getId(), ADMINISTRATOR_PROFILE_NAME);
				if(profileResult != null && profileResult.getProfiles() != null && profileResult.getProfiles().length > 0)
				{
					result = true;
				}
			} 
			catch (BbrUserException | BbrSystemException e) 
			{
				e.printStackTrace();
			}	
		}
		
		return result;
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			PUBLIC METHODS
	// ****************************************************************************************
	
	

}
