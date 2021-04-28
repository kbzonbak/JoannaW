package bbr.b2b.portal.components.basics;

public class BbrValidateCommercialException extends Exception
{
	private static final long	serialVersionUID	= 1046863145110871151L;

	// COMEX - COMmercial EXception
	public static final String	APP_LOAD			= "COMEX_1000";
	//Módulo Comercial se encuentra en proceso de carga

	public BbrValidateCommercialException()
	{
	}

	public BbrValidateCommercialException(String message)
	{
		super(message);
	}

	public BbrValidateCommercialException(String code, String message)
	{
		super(message);
		this.code = code;
	}

	public BbrValidateCommercialException(Throwable cause)
	{
		super(cause);
	}

	public BbrValidateCommercialException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public BbrValidateCommercialException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	private String code = "";

	public String getCode()
	{
		return code;
	}

}
