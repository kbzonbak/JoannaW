package bbr.b2b.portal.classes.logi.utils;

import com.vaadin.ui.UI;

import bbr.b2b.portal.classes.logi.wrappers.SecurityData;
import bbr.b2b.portal.classes.network.HttpUtils;
import cl.bbr.core.classes.utilities.BbrUtils;

public class LogiUtils
{
	private static LogiUtils	instance			= new LogiUtils();

	private String				securityDataBaseURL	= new String();
	private String				logiLocalURL		= LogiConstants.LOCALHOST_LOGI_SERVER;

	private LogiUtils()
	{
		super();
	}

	public static LogiUtils getInstance()
	{
		return instance;
	}

	public String getSecurityKey(SecurityData securityData, UI ui)
	{
		securityDataBaseURL = securityData.getBaseURL();
		System.out.println("LTO- BaseURL:" + securityDataBaseURL);
		String result = HttpUtils.doPost(securityData.toNameValuePairsList(), logiLocalURL + "/rdTemplate/rdGetSecureKey.aspx");
		return result;
	}

	public String getReportURL(SecurityData securityData, String reportName)
	{
		String result = BbrUtils.getInstance().substitute(securityDataBaseURL + "/rdPage.aspx?rdReport={0}&rdSecureKey={1}", reportName, securityData.getSecurityKey());

		return result;
	}
}
