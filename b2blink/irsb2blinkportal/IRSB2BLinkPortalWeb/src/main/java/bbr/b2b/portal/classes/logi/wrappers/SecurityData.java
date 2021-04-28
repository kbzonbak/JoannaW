package bbr.b2b.portal.classes.logi.wrappers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import bbr.b2b.portal.classes.logi.utils.LogiConstants;

public class SecurityData
{
	private String	securityKey				= null;
	private String	baseURL					= null;
	private String	username				= null;
	private String	roles					= null;
	private String	rights					= null;
	private String	clientBrowserAddress	= null;

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getRoles()
	{
		return roles;
	}

	public void setRoles(String roles)
	{
		this.roles = roles;
	}

	public String getRights()
	{
		return rights;
	}

	public void setRights(String rights)
	{
		this.rights = rights;
	}

	public String getClientBrowserAddress()
	{
		return clientBrowserAddress;
	}

	public void setClientBrowserAddress(String clientBrowserAddress)
	{
		this.clientBrowserAddress = clientBrowserAddress;
	}
	
	public String getSecurityKey()
	{
		return securityKey;
	}

	public void setSecurityKey(String securityKey)
	{
		this.securityKey = securityKey;
	}

	public String getBaseURL()
	{
		return baseURL;
	}

	public void setBaseURL(String baseURL)
	{
		this.baseURL = baseURL;
	}

	public List<NameValuePair> toNameValuePairsList()
	{
		List<NameValuePair> result = new ArrayList<>();

		if (this.username != null)
		{
			result.add(new BasicNameValuePair(LogiConstants.USERNAME, this.username));
		}

		if (this.roles != null)
		{
			result.add(new BasicNameValuePair(LogiConstants.ROLES, this.roles));
		}

		if (this.rights != null)
		{
			result.add(new BasicNameValuePair(LogiConstants.RIGHTS, this.rights));
		}

		if (this.clientBrowserAddress != null)
		{
			result.add(new BasicNameValuePair(LogiConstants.CLIENTBROWSERADDRESS, this.clientBrowserAddress));
		}

		return result;
	}
}
