package bbr.b2b.portal.components.basics;

public class BbrUserException extends Exception 
{
	private static final long serialVersionUID = 1046863145110871151L;

	//USEX - USer EXception
	public static final String INVALID_SESSION		 	= "USEX_1000";
	public static final String LOGIN_SAME_ACCOUNT 		= "USEX_1100";
	public static final String UNAUTHORIZED_OPERATION 	= "USEX_1200";
	
	public BbrUserException() 
	{
	}

	public BbrUserException(String message) 
	{
		super(message);
	}

	public BbrUserException(String code, String message) 
	{
		super(message);
		this.code = code;
	}

	public BbrUserException(Throwable cause) 
	{
		super(cause);
	}

	public BbrUserException(String message, Throwable cause) 
	{
		super(message, cause);
	}

	public BbrUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) 
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	private String code = "";
	public String getCode() 
	{
		return code;
	}

	

}
