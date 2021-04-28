package bbr.esb.web.constants;

public class BbrAppConstants 
{
	public static final String 	B2B_PORTAL			= "PORTAL_PROVEEDORES";
	public static final String  SESSION_BEAN_NAME	= "UserSessionBean";

	public static final String  SESSION_CREATED 	= "USER SESSION CREATED : "; 
	public static final String  SESSION_DESTROYED 	= "USER SESSION DESTROYED : ";

	public static final String  RES_B2B_PAGES	= "/bbr/esb/web/resources/B2BPages.properties";
	public static final String  RES_B2B_UTILS	= "/bbr/esb/web/resources/B2BUtils.properties";

	// i18n
	public static final String  RES_GENERIC	= "bbr.b2b.portal.resources.i18n.generic.Generic";
	public static final String  RES_SYSTEM	= "bbr.b2b.portal.resources.i18n.system.System";
	public static final String  RES_LOGIN	= "bbr.b2b.portal.resources.i18n.login.Login";
	
	// authentication types
	public static final String  AUTHENTICATION_TYPE_LOGIN = "login";
	public static final String  AUTHENTICATION_TYPE_LDAP = "ldap";
	public static final String  AUTHENTICATION_TYPE_LDAP_INTERNAL = "ldap_internal";

	// LDAP headers
	public static final String  REQUEST_REMOTE_USER_GROUPS = "REMOTE_USER_GROUPS";
	public static final String  REQUEST_REMOTE_USER_LASTNAME = "REMOTE_USER_LASTNAME";
	public static final String  REQUEST_REMOTE_USER_FIRSTNAME = "REMOTE_USER_FIRSTNAME";
	public static final String  REQUEST_REMOTE_USER_EMAIL = "REMOTE_USER_EMAIL";
	public static final String  REQUEST_REMOTE_USER_GECOS = "REMOTE_USER_GECOS";
	
	// LDAP group name
	public static final String  LDAP_GROUP_NAME = "b2b_profiles";

}
