package bbr.b2b.portal.classes.utils.customer;

import static bbr.b2b.portal.constants.logistic.ConstantsDeliverySearchCriteria.DELIVERY_NUMBER;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.CustomerConstants;
import bbr.b2b.portal.constants.FEPConstants;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.utilities.BbrUtils;

public class CustomerSearchCriteriaFilterUtils
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static CustomerSearchCriteriaFilterUtils instance = new CustomerSearchCriteriaFilterUtils();

	public static CustomerSearchCriteriaFilterUtils getInstance()
	{
		return instance;
	}

	public static final String	CUSTOMER_RESOURCES	= "Customer";

	private static final String	CAPTION_PREFIX		= " — ";
	private static final String	CAPTION_SUFIX		= ": ";

	public static final int		ORDER_STATE			= 2;
	public static final int		SHIPPING_DATE		= 1;
	public static final int		ORDER_NUMBER		= 3;
	public static final int		CLIENT				= 4;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	private CustomerSearchCriteriaFilterUtils()
	{

	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	public static String getFilterCaption(String resourceModule, String titleResourceName, Boolean isMainCaption)
	{
		String result = (isMainCaption) ? "" : CAPTION_PREFIX;

		switch (resourceModule)
		{

			case CUSTOMER_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, titleResourceName));
				break;

		}

		result += CAPTION_SUFIX;

		return result;
	}

	public static String getLocalCaption(String resourceModule, String titleResourceName, Boolean isMainCaption, String local)
	{
		String result = (isMainCaption) ? "" : CAPTION_PREFIX;

		switch (resourceModule)
		{

			case CUSTOMER_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, titleResourceName));
				break;

		}

		result += CAPTION_SUFIX + " " + local;
		return result;
	}

	public static String getOrderStateCaption(String resourceModule, String titleResourceName, Boolean isMainCaption, String orderState)
	{
		String result = (isMainCaption) ? "" : CAPTION_PREFIX;

		switch (resourceModule)
		{

			case CUSTOMER_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, titleResourceName));
				break;

		}

		result += CAPTION_SUFIX + " " + orderState;
		return result;
	}

	public static String getOrderNumberCaption(String resourceModule, String titleResourceName, Boolean isMainCaption, Long orderNumber)
	{
		String result = (isMainCaption) ? "" : CAPTION_PREFIX;

		switch (resourceModule)
		{

			case CUSTOMER_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, titleResourceName));
				break;

		}

		result += CAPTION_SUFIX + " " + orderNumber;
		return result;
	}

	public static String getValidOrders(String resourceModule, String titleResourceName, Boolean isMainCaption)
	{
		String result = (isMainCaption) ? "" : CAPTION_PREFIX;

		switch (resourceModule)
		{

			case CUSTOMER_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, titleResourceName));
				break;

		}

		result += CAPTION_SUFIX + " " + I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "valid_orders");
		return result;
	}

	public SearchCriterion[] getPucharseOrdersCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion orderState = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "order_state"), ORDER_STATE);
		SearchCriterion shippingDate = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "shipping_date"), SHIPPING_DATE);
		SearchCriterion orderNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "order_number"), ORDER_NUMBER);
		SearchCriterion client = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client"), CLIENT);
		result = new SearchCriterion[] { orderState, shippingDate, client, orderNumber };

		return result;
	}

	// public SearchCriterion[] getOrderStatesCriteria()
	// {
	// SearchCriterion[] result;
	// SearchCriterion all = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "all"), 0);
	// SearchCriterion received = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "received"), 1);
	// SearchCriterion inProcess = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "in_process"), 2);
	// SearchCriterion error = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "error"), 3);
	// result = new SearchCriterion[] { all, received, inProcess, error };
	// return result;
	// }

	public SearchCriterion[] getOrderStatesCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion all = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "all"), 0);
		SearchCriterion received = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "total_success"), 1);
		SearchCriterion inProcess = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "in_process"), 2);
		SearchCriterion error = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "total_error"), 3);
		result = new SearchCriterion[] { all, received, inProcess, error };
		return result;
	}

	public static SearchCriterion[] getRequestReferencePeriodFilterCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion creationDate = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "cbx_option_creation_date"), CustomerConstants.CREATION_DATE_FILTER_VALUE);
		SearchCriterion requestNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "lbl_request_number"), CustomerConstants.REQUEST_NUMBER_FILTER_VALUE);
		SearchCriterion referenceNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "lbl_reference_number"), CustomerConstants.REFERENCE_NUMBER_FILTER_VALUE);
	
		result = new SearchCriterion[] { creationDate, requestNumber, referenceNumber};
		return result;
	}

	public static SearchCriterion[] getRequestFilterCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion type = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "lbl_type_request"), CustomerConstants.TYPE_REQUEST_FILTER_VALUE);
		SearchCriterion state = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "lbl_state_request"), CustomerConstants.STATE_REQUEST_FILTER_VALUE);

		result = new SearchCriterion[] { type, state };
		return result;
	}

	public SearchCriterion[] getDeliveryCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion validOrders = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "valid"), 0);
		SearchCriterion deliveryNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "delivery_number"), 1);
		SearchCriterion orderNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "order_number"), 2);
		result = new SearchCriterion[] { validOrders, deliveryNumber, orderNumber };
		return result;
	}

	public SearchCriterion[] getLocalsCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion description = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "description"), 0);
		SearchCriterion code = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "code"), 1);
		result = new SearchCriterion[] { description, code };
		return result;
	}

	public static String getPeriodCaption(String resourceModule, String titleResourceName, LocalDateTime startDate, LocalDateTime endDate)
	{
		String result = FilterHeaderUtils.getFilterCaption(resourceModule, titleResourceName, false);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		result += BbrUtils.getInstance().substitute("{0} {1} {2}", startDate.format(formatter), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "to"),
				endDate.format(formatter));

		return result;
	}

	public static String getStartAndUntilDatesCaption(String resourceModule, String titleResourceName, LocalDateTime startDate, LocalDateTime endDate)
	{
		String result = " — ";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		result += BbrUtils.getInstance().substitute("{0}: ({1}) - {2}: ({3})", I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "start_date"),
				startDate.format(formatter), I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "until_date"), endDate.format(formatter));

		return result;
	}

	public static String getDeliveryCaption(int type, Long deliveryNumber, Long orderNumber)
	{
		String result = " — ";
		switch (type)
		{
			case DELIVERY_NUMBER:
				result += I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "delivery_number") + ": " + deliveryNumber;
				break;
			case ORDER_NUMBER:
				result += I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "order_number") + ": " + orderNumber;
				break;
			default:
				result += I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "valid");
				break;
		}
		return result;
	}

	public static String getDeliveryLocationCaption(String deliveryLocation, boolean isMainCaption)
	{
		String result = deliveryLocation;
		if (!isMainCaption)
		{
			result = " — ";
			result += I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "delivery_location") + ": " + deliveryLocation;
		}
		return result;

	}

	public static String getDateCaption(String date, boolean isMainCaption)
	{
		String result = date;
		if (!isMainCaption)
		{
			result = " — ";
			result += I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "date") + ": " + date;
		}
		return result;
	}
}
