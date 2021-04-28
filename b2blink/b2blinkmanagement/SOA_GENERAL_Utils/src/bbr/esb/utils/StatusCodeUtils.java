package bbr.esb.utils;

import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;

import bbr.common.adtclasses.classes.BaseResultDTO;

public class StatusCodeUtils {

	private static StatusCodeUtils instance;

	public static synchronized StatusCodeUtils getInstance() {
		if (instance == null) {
			instance = new StatusCodeUtils();
		}
		return instance;
	}

	private StatusCodeUtils() {
		Locale locale = new Locale("es", "CL");
		properties = (PropertyResourceBundle) PropertyResourceBundle.getBundle("bbr.esb.utils.StatusCodeUtils", locale);
	}

	protected PropertyResourceBundle properties;

	protected StatusCodeUtils(String bundlepackage) {
		Locale locale = new Locale("es", "CL");
		properties = (PropertyResourceBundle) PropertyResourceBundle.getBundle(bundlepackage, locale);
	}

	protected StatusCodeUtils(String bundlepackage, Locale locale) {
		properties = (PropertyResourceBundle) PropertyResourceBundle.getBundle(bundlepackage, locale);
	}

	private String getStatusMessage(String statuscode) {
		String key = statuscode;
		return properties.getString(key);
	}

	public <T extends BaseResultDTO> T setStatusCode(T result, String statuscode) {
		result.setStatuscode(statuscode);
		result.setStatusmessage(getStatusMessage(statuscode));
		return result;
	}

	public <T extends BaseResultDTO> T setStatusCode(T result, String statuscode, String comment, boolean usemessage) {
		result.setStatuscode(statuscode);
		String status = usemessage ? getStatusMessage(statuscode) + "(" + comment + ")" : comment;
		result.setStatusmessage(status);
		return result;
	}

	public <T extends BaseResultDTO> T setStatusCode(T result, String statuscode, String comment) {
		return setStatusCode(result, statuscode, comment, true);
	}

	public Enumeration<String> listErrorCodes() {
		Enumeration<String> keys = properties.getKeys();
		return keys;
	}

	public PropertyResourceBundle getProperties() {
		return properties;
	}

}
