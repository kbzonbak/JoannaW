package bbr.b2b.portal.classes.utils.logistic;

import static bbr.b2b.portal.constants.logistic.ConstantsDeliverySearchCriteria.DELIVERY_NUMBER;
import static bbr.b2b.portal.constants.logistic.ConstantsDeliverySearchCriteria.ORDER_NUMBER;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.utilities.BbrUtils;

public class PucharseOrderSearchFilterUtils
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static PucharseOrderSearchFilterUtils instance = new PucharseOrderSearchFilterUtils();

	public static PucharseOrderSearchFilterUtils getInstance()
	{
		return instance;
	}

	public static final String	LOGISTIC_RESOURCES	= "Commercial";
	public static final String	CUSTOMER_RESOURCES	= "Customer";
	public static final String	RES_FILTERS	= "Filter";
	

	private static final String	CAPTION_PREFIX	= " — ";
	private static final String	CAPTION_SUFIX		= ": ";
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	private PucharseOrderSearchFilterUtils()
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

			case LOGISTIC_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, titleResourceName));
				break;
			case CUSTOMER_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, titleResourceName));
				break;
			case RES_FILTERS:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, titleResourceName));
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

			case LOGISTIC_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, titleResourceName));
				break;
			case CUSTOMER_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, titleResourceName));
				break;
			case RES_FILTERS:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, titleResourceName));
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

			case LOGISTIC_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, titleResourceName));
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

			case LOGISTIC_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, titleResourceName));
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

			case LOGISTIC_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, titleResourceName));
				break;

		}

		result += CAPTION_SUFIX + " " + I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "valid_orders");
		return result;
	}

	public SearchCriterion[] getPucharseOrdersCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion validOrders = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "valid_orders"), 0);
		SearchCriterion orderStatus = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "order_status"), 1);
		SearchCriterion orderNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "order_number"), 2);
		SearchCriterion createAt = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "created_at"), 3);
		SearchCriterion deliveryAt = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_b2b_date"), 4);
		result = new SearchCriterion[] { validOrders, orderStatus, orderNumber, createAt, deliveryAt };
		return result;
	}

	public SearchCriterion[] getDeliveryCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion validOrders = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "valid"), 0);
		SearchCriterion deliveryNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "delivery_number"), 1);
		SearchCriterion orderNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "order_number"), 2);
		SearchCriterion deliveryState = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "state"), 3);
		SearchCriterion bulkNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "bulk_number"), 4);
		SearchCriterion documentNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "document_number"), 5);
		result = new SearchCriterion[] { validOrders, deliveryNumber, orderNumber, deliveryState, bulkNumber, documentNumber };
		return result;
	}

	public SearchCriterion[] getLocalsCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion description = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "description"), 0);
		SearchCriterion code = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "code"), 1);
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

	public static String getDeliveryCaption(int type, String searchValue)
	{
		String result = " — ";
		switch (type)
		{
			case DELIVERY_NUMBER:
				result += I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "delivery_number") + ": " + searchValue;
				break;
			case ORDER_NUMBER:
				result += I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "order_number") + ": " + searchValue;
				break;
			default:
				result += I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "valid");
				break;
		}
		return result;
	}
	
	public static String getDeliveryLocationCaption(String deliveryLocation, boolean isMainCaption){
		String result = deliveryLocation;
		if(!isMainCaption)
		{
			result = " — ";
			result += I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "delivery_location") + ": " + deliveryLocation;
		}
		return result;
		
	}
	
	public static String getDateCaption(String date, boolean isMainCaption){
		String result = date;
		if(!isMainCaption)
		{
			result = " — ";
			result += I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "date") + ": " + date;
		}
		return result;
	}
}
