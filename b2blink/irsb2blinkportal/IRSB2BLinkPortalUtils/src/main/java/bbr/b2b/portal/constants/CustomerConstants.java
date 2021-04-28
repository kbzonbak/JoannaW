package bbr.b2b.portal.constants;

public class CustomerConstants
{

	private static CustomerConstants _instance;

	// Constructor
	public static synchronized CustomerConstants getInstance()
	{
		if (_instance == null)
		{
			_instance = new CustomerConstants();
		}

		return _instance;
	}

	// Multi Filtro Estado de Productos(Solicitudes)

	public static final int	REQUEST_NUMBER_FILTER_VALUE		= 2;
	public static final int	REFERENCE_NUMBER_FILTER_VALUE	= 3;
	public static final int	CREATION_DATE_FILTER_VALUE		= 1;

	// Multi Filtro Solicitudes

	public static final int	TYPE_REQUEST_FILTER_VALUE		= 1;
	public static final int	STATE_REQUEST_FILTER_VALUE		= 2;

}
