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

public class VeVPucharseOrderSearchFilterUtils
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************

	private static VeVPucharseOrderSearchFilterUtils instance = new VeVPucharseOrderSearchFilterUtils();

	public static VeVPucharseOrderSearchFilterUtils getInstance()
	{
		return instance;
	}

	public static final String	LOGISTIC_RESOURCES	= "Commercial";

	private static final String	CAPTION_PREFIX		= " — ";
	private static final String	CAPTION_SUFIX		= ": ";

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	private VeVPucharseOrderSearchFilterUtils()
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

	public static String getClientRutCaption(String resourceModule, String titleResourceName, Boolean isMainCaption, String clientRut)
	{
		String result = (isMainCaption) ? "" : CAPTION_PREFIX;

		switch (resourceModule)
		{

			case LOGISTIC_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, titleResourceName));
				break;

		}

		result += CAPTION_SUFIX + " " + clientRut;
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

	public SearchCriterion[] getVeVPucharseOrdersCriteria()
	{
		SearchCriterion[] result;

		SearchCriterion validOrders = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "valid_orders"), 0);
		SearchCriterion orderNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "order_number"), 1);
		SearchCriterion commitmentDate = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "commitment_date"), 2);
		SearchCriterion emittedDate = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "emitted_date"), 3);
		SearchCriterion orderStatus = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "order_status"), 4);
		SearchCriterion clientRut = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "client_rut"), 5);
		SearchCriterion voucherNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "voucher_number"), 6);

		result = new SearchCriterion[] { validOrders, orderNumber, commitmentDate, emittedDate, orderStatus, clientRut, voucherNumber };

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

	public static String getDeliveryLocationCaption(String deliveryLocation, boolean isMainCaption)
	{
		String result = deliveryLocation;
		if (!isMainCaption)
		{
			result = " — ";
			result += I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "delivery_location") + ": " + deliveryLocation;
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
