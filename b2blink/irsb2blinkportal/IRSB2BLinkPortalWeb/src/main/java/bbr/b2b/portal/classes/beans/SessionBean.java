package bbr.b2b.portal.classes.beans;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;

import bbr.b2b.portal.classes.constants.EnumUserType;
import bbr.b2b.users.report.classes.CompanyTypeDTO;
import bbr.b2b.users.report.classes.UserDTO;

@SessionScoped
public class SessionBean implements Serializable
{
	private static final long serialVersionUID = 2634386235809530118L;
	
	private UserDTO		user;
	private Boolean		logged			= false;
	private Boolean		passwordExpires	= false;
	private CompanyTypeDTO	companyType;

	private Long			userId			= -1L;
	private String		userName		= null;
	private String		userLastName		= null;
	private String		emailUser		= null;
	private String		sessionId		= null;
	private Locale		locale			= null;

	private Long			enterpriseId		= -1L;
	private String		enterpriseCode	= "";
	private String		enterpriseName	= "";

	private EnumUserType		enumUserType		= null;

	private Boolean		firstTravelToUser	= true;
	private String		accessToken		= "";
	private String		refreshToken		= "";
	
	public Locale getLocale() 
	{
		return locale;
	}
	public void setLocale(Locale locale) 
	{
		this.locale = locale;
	}
	public UserDTO getUser() 
	{
		return user;
	}
	public void setUser(UserDTO user) 
	{
		this.user = user;
	}
	
	public Boolean getLogged() 
	{
		return logged;
	}
	public void setLogged(Boolean logged) 
	{
		this.logged = logged;
	}
	
	public CompanyTypeDTO getCompanyType() 
	{
		return companyType;
	}
	public void setCompanyType(CompanyTypeDTO companyType) 
	{
		this.companyType = companyType;
	}
	
	public Long getUserId() 
	{
		return userId;
	}
	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}
	
	public String getUserName() 
	{
		return userName;
	}
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}
	
	public String getUserLastName() 
	{
		return userLastName;
	}
	public void setUserLastName(String userLastName) 
	{
		this.userLastName = userLastName;
	}
	
	public String getEmailUser() 
	{
		return emailUser;
	}
	public void setEmailUser(String emailUser) 
	{
		this.emailUser = emailUser;
	}
	
	public String getSessionId() 
	{
		return sessionId;
	}
	public void setSessionId(String sessionId) 
	{
		this.sessionId = sessionId;
	}	
	
	public Boolean getPasswordExpires() 
	{
		return passwordExpires;
	}
	public void setPasswordExpires(Boolean passwordExpires) 
	{
		this.passwordExpires = passwordExpires;
	}
	
	public String getEnterpriseCode() 
	{
		return enterpriseCode;
	}
	public void setEnterpriseCode(String enterpriseCode) 
	{
		this.enterpriseCode = enterpriseCode;
	}
	
	public String getEnterpriseName() 
	{
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) 
	{
		this.enterpriseName = enterpriseName;
	}
	
	public Long getEnterpriseId() 
	{
		return enterpriseId;
	}
	public void setEnterpriseId(Long enterpriseId) 
	{
		this.enterpriseId = enterpriseId;
	}
	
	public String getUserFullName() 
	{
		String result = "";
			
		if(this.user != null)
		{
			result = this.user.getName() + " " + this.user.getLastname();
		}
		return result;
	}
	
	public EnumUserType getEnumUserType() 
	{
		return enumUserType;
	}
	
	public void setEnumUserType(EnumUserType enumUserType) 
	{
		this.enumUserType = enumUserType;
	}
	public Boolean getFirstTravelToUser() {
		return firstTravelToUser;
	}
	public void setFirstTravelToUser(Boolean firstTravelToUser) {
		this.firstTravelToUser = firstTravelToUser;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	
}
