package bbr.b2b.portal.classes.utils.users;

import bbr.b2b.portal.classes.constants.EnumUserType;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UserDataDTO;

public class UserManagerUtils
{
	private String		userType	= null;
	private UserDataDTO	userData	= null;

	public static boolean getIsExternalRetail()
	{
		return PortalConstants.getInstance().isExternalRetail();
	}

	public void setUserData(UserDataDTO userData)
	{
		this.userData = userData;
		this.setUserTypeSelected(this.userData.getCompanycode());
	}

	private void setUserTypeSelected(String userType)
	{
		this.userType = userType;
	}

	public boolean isRetailOrBbrType()
	{
		boolean isProviderType = this.userType != null && (userType.equalsIgnoreCase(EnumUserType.RETAILER.getValue()) || userType.equalsIgnoreCase(EnumUserType.BBR.getValue()));
		return isProviderType;
	}

	public boolean isRetailType()
	{
		boolean isProviderType = this.userType != null && (userType.equalsIgnoreCase(EnumUserType.RETAILER.getValue()));
		return isProviderType;
	}

	public boolean isBbrType()
	{
		boolean isProviderType = this.userType != null && (userType.equalsIgnoreCase(EnumUserType.BBR.getValue()));
		return isProviderType;
	}

	public static String getValidFullnameOrEmailByUser(UserDataDTO userData)
	{
		String userFullText = (userData.getUsername() != null && userData.getLastname() != null)
				? (userData.getUsername() + " " + userData.getLastname())
				: userData.getEmail() != null ? userData.getEmail() : "";
		return userFullText;
	}

	public static String getValidFullnameOrEmailByUser(UserDTO userData)
	{
		String userFullText = (userData.getName() != null && userData.getLastname() != null)
				? (userData.getName() + " " + userData.getLastname())
				: userData.getEmail() != null ? userData.getEmail() : "";
		return userFullText;
	}
}
