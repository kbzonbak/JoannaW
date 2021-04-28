package bbr.b2b.portal.api.managers.classes;

import java.io.Serializable;

public class InitParamMLDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3722251874956673136L;
	
	private String authorizationCode;

	public String getAuthorizationCode()
	{
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode)
	{
		this.authorizationCode = authorizationCode;
	}
	
	

}
