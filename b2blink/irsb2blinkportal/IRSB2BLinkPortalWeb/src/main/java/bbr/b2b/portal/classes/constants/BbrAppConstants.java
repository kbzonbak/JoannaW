package bbr.b2b.portal.classes.constants;

import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.constants.customer.CustomerServiceConstants;
import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;

public class BbrAppConstants
{
	public static final boolean	PRODUCTION_MODE			= true;

	public static final int		SESSION_TIME_OUT		= 1200;										// 20MIN=60*20=1200s
	public static final int		SESSION_NEVER_EXPIRES	= -1;

	public static final int		DAYS_MILLISECONDS		= 1000 * 60 * 60 * 24;

	public static final String	B2B_PORTAL				= "PORTAL_PROVEEDORES";
	public static final String	B2B_COMPANY				= "COMPANYDATA";

	public static final String	SESSION_CREATED			= "USER SESSION CREATED : ";
	public static final String	SESSION_DESTROYED		= "USER SESSION DESTROYED : ";

	public static final String	M_SESSION_CREATED		= "USER SESSION CREATED FROM MOBILE: ";
	public static final String	M_SESSION_DESTROYED		= "USER SESSION DESTROYED FROM MOBILE: ";

	public static final String	SOLVENTA_TICKETS		= "SOLV_TICKET";

	public static final String	REFRESH_TOKEN			= "RefreshToken";
	public static final String	CONTEXT_PATH			= "/BBRe-commerce";
	public static final String	LOGI_PATH				= "/logi";
	public static final String	DOWNLOAD_PATH			= "/download/b2b_files/";
	public static final String	PATH_BASE_IMAGES_FILES	= "assets/images/files/";
	public static final String  CUSTOMER_URL 			= B2BSystemPropertiesUtil.getProperty("customerLogistic_url");
	
	public static String getUploadPathOfUser(Long userId)
	{
		return PortalConstants.getInstance().getFileUploadPath() + userId.toString() + "/";
	}

	public static String getUploadPathOfContact()
	{
		return PortalConstants.getInstance().getFileUploadPath() + "CONTACT/";
	}

	public static String getMastersLoadPath()
	{
		return CustomerServiceConstants.getInstance().getFileMastersLoadPath();
	}

	public static String getMastersLoadPathByType(String fileType)
	{
		return CustomerServiceConstants.getInstance().getFileMastersLoadPathByFileType(fileType);
	}

}
