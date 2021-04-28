package bbr.b2b.portal.classes.constants;

import bbr.b2b.portal.constants.PortalConstants;

public class BbrPublishingConstants
{
	public static final String	POPUP_MODE_NORMAL					= "NORMAL";
	public static final String	POPUP_MODE_LINK						= "LINK";
	public static final String	POPUP_MODE_MODULE					= "MOD";
	public static final String	POPUP_MODE_BLOQUEA					= "BLOQUEA";
	public static final String	POPUP_MODE_PERMITE					= "PERMITE";

	public static final String	POPUP_TYPE_CODE_INF					= "INF";
	public static final String	POPUP_TYPE_CODE_AUDIT_USU			= "AUDIT_USU";
	public static final String	POPUP_TYPE_CODE_AUDIT_EMP			= "AUDIT_EMP";

	public static final String	POPUP_TEMPLATE_2					= "TEMPLATE2";
	public static final String	POPUP_TEMPLATE_1					= "TEMPLATE1";

	public static final String	NEWS_PUBLISHING_CODE				= "NOT";
	public static final String	POPUP_PUBLISHING_CODE				= "POPUP";
	public static final String	EMAIL_PUBLISHING_CODE				= "EMAIL";

	public static final String	TICKET_PUBLISHING_CODE				= "TICKET";

	public static final String	NOVELTIES_PUBLISHING_CODE			= "NOV";
	public static final String	COMMERCIAL_PROC_PUBLISHING_CODE		= "PROC_COM";
	public static final String	LOGISTIC_PROC_PUBLISHING_CODE		= "PROC_LOG";
	public static final String	FINANCES_PROC_PUBLISHING_CODE		= "PROC_FIN";

	public static final String	RULE_TYPE_USER_CODE					= "LIST_USER";
	public static final String	RULE_TYPE_COMPANY_CODE				= "LIST_EMPR";
	public static final String	RULE_TYPE_PROFILE_CODE				= "PROFILES";
	public static final String	RULE_TYPE_EVENT_CODE				= "EVENT";
	public static final String	RULE_TYPE_ALL_CODE					= "ALL";

	public static final String	NOTIFICATION_ACTIVE_STATE			= "ACTIVA";
	public static final String	NOTIFICATION_INACTIVE_STATE			= "INACTIVA";
	public static final String	NOTIFICATION_EXPIRED_STATE			= "EXPIRADA";

	// private static final String PUBLISHING_FILES_PATH = File.separator +
	// "b2b" + File.separator + "news" + File.separator;
	public static final String	PUBLISHING_FILES_PATH				= "/BBRe-commerce/b2b/news/";
	public static final String	TICKET_FILES_PATH					= "/BBRe-commerce/b2b/ticket/";

	public static final String	FILE_UPLOAD_URL						= "/BBRe-commerce/ckfilesupload?command=type=Files";
	public static final String	IMAGE_UPLOAD_URL					= "/BBRe-commerce/ckimagesupload?command=type=Images";

	public static final int		DEFAULT_PUBLISHING_MAX_FILES		= 10;
	public static final long	DEFAULT_PUBLISHING_FILE_SIZE_LIMIT	= 2 * 1024 * 1024;												// 2MB

	public static String getUploadFullPath(String publishingCode)
	{
		return PortalConstants.getInstance().getFileUploadPath() + publishingCode + "/";
	}
}
