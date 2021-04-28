package bbr.b2b.portal.constants;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

public class PortalResources {

	private static PortalResources _instance;
	
	//Constructor
	public static synchronized PortalResources getInstance() {
		if (_instance == null) {
			_instance = new PortalResources();
		}
		
		return _instance;
	}

	private PropertyResourceBundle mailProperties;
	private PropertyResourceBundle sysInfoProperties;
	private PropertyResourceBundle contactProperties;
	private PropertyResourceBundle commentContact;
	private PropertyResourceBundle mimeTypeProperties;
	private PropertyResourceBundle pagesFuncMappingProperties;
	
	private PortalResources() {
		Locale locale = new Locale("es", "CL");

		mailProperties 		= (PropertyResourceBundle) PropertyResourceBundle.getBundle(PortalConstants.MAIL_PROPERTIES_FILE, locale);
		sysInfoProperties 	= (PropertyResourceBundle) PropertyResourceBundle.getBundle(PortalConstants.SYSINFO_PROPERTIES_FILE, locale);
		contactProperties	= (PropertyResourceBundle) PropertyResourceBundle.getBundle(PortalConstants.CONTACT_PROPERTIES_FILE, locale);
	    commentContact		= (PropertyResourceBundle) PropertyResourceBundle.getBundle(PortalConstants.COMMENTCONTACT_PROPERTIES_FILE, locale);
	    mimeTypeProperties 	= (PropertyResourceBundle) PropertyResourceBundle.getBundle(PortalConstants.MIMETYPE_PROPERTIES_FILE, locale);
	    pagesFuncMappingProperties = (PropertyResourceBundle) PropertyResourceBundle.getBundle(PortalConstants.PAGES_FUNCS_PROPERTIES_FILE, locale);
	}

	 /**** Acceso a propiedades definidas Contact.properties *
	 **************************************************/
	public String getContactData (String llave){
		return contactProperties.getString(llave);
	}
	
	public Enumeration<String> getContactKeys(){
		return contactProperties.getKeys();
	}
	
	/**** propiedades commentcontact.properties ****/
	
	public String getCommentContactData (String llave){
		return commentContact.getString(llave);
	}
	
	/**** propiedades de mimetype para descargas ***/
	
	public String getMimeTypeData (String llave){
		return mimeTypeProperties.getString(llave);
	}
	
	/**************************************************
	 * Acceso a propiedades definidas Mail.properties *
	 **************************************************/
	public String getMailSession() {
		String mailsession = (String) mailProperties.getString("MAIL_SESSION").trim();
		return mailsession;
	}

	public String getMailFrom() {
		String from = (String) mailProperties.getString("MAIL_FROM").trim();
		return from;
	}
	
	public String getMailSubjectComment() {
		String subject = (String) mailProperties.getString("MAIL_SUBJECT_COMMENT").trim();
		return subject;
	}

	public String getMailToBBR() {
		String tobbr = (String) mailProperties.getString("MAIL_TO_BBR").trim();
		return tobbr;
	}
	
	public String getMailToCCRetailer() {
		String tobbr = (String) mailProperties.getString("MAIL_TO_CC_RETAILER").trim();
		return tobbr;
	}
	
	public int getMailAttachLimitSize() {
		int size = Integer.parseInt(mailProperties.getString("MAIL_ATTACH_LIMIT_SIZE").trim());
		return size;
	}
	

	public String getMailforB2BOpinion(){
		String mails = (String) mailProperties.getString("MAIL_B2B_OPINION".trim());
		return mails;
	}
		
	public String getMailCOforB2BOpinion(){
		String mails = (String) mailProperties.getString("MAIL_B2B_OPINION_CO".trim());
		return mails;
	}
	
	/*****************************************************
	 * Acceso a propiedades definidas SysInfo.properties *
	 *****************************************************/
	public int getSysInfoIntentosDeLogin() {
		int intentosDeLogin = Integer.parseInt(sysInfoProperties.getString("SYSINFO_INTENTOS_LOGIN").trim());
		return intentosDeLogin;
	}

	public int getSysInfoDiasDisponibles() {
		int diasDisponibles = Integer.parseInt(sysInfoProperties.getString("SYSINFO_DIAS_DISPONIBLES").trim());
		return diasDisponibles;
	}

//	public int getSysInfoSessionTime() {
//		int sessionTime = Integer.parseInt(sysInfoProperties.getString("SYSINFO_TIEMPO_SESSION").trim());
//		return sessionTime;
//	}

	public String getSysInfoSemilla() {
		String semilla = (String) sysInfoProperties.getString("SYSINFO_SEMILLA_BASE64").trim();
		return semilla;
	}
	
	public String getPagesFuncMappingValue(String key) {
		try {
			return pagesFuncMappingProperties.getString(key);
		} catch (MissingResourceException e) {
			return null;
		}
	}
	
}
