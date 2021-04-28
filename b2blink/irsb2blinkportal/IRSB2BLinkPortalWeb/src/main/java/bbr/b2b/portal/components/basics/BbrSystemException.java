package bbr.b2b.portal.components.basics;

public class BbrSystemException extends Exception 
{
	private static final long serialVersionUID = -4759816147166860817L;
	
	//SYEX - SYstem EXception
	public static final String COMMERCIAL_DATA_LOADING	= "SYEX_1000";
	public static final String DBCOM2_DATA_LOADING		= "SYEX_1100";
	public static final String DWHLOG_DATA_LOADING		= "SYEX_1200";

	
	public BbrSystemException() 
	{
	}

	public BbrSystemException(String message) 
	{
		super(message);
	}

	public BbrSystemException(Throwable cause) 
	{
		super(cause);
	}

	public BbrSystemException(String message, Throwable cause) 
	{
		super(message, cause);
	}

	public BbrSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) 
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
