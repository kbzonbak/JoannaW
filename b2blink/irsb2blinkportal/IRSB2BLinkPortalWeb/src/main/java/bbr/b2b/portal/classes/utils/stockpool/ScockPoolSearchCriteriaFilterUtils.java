package bbr.b2b.portal.classes.utils.stockpool;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.SearchCriterion;

public class ScockPoolSearchCriteriaFilterUtils
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static ScockPoolSearchCriteriaFilterUtils instance = new ScockPoolSearchCriteriaFilterUtils();

	public static ScockPoolSearchCriteriaFilterUtils getInstance()
	{
		return instance;
	}

	public static final int	STATE	= 2;
	public static final int	SKU		= 1;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	private ScockPoolSearchCriteriaFilterUtils()
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

	public SearchCriterion[] getAlertStateCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion openState = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "open_state"), 1);
		SearchCriterion closeState = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "close_state"), 2);
		SearchCriterion allState = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "all_state"), 0);
		result = new SearchCriterion[] { openState, closeState, allState };

		return result;
	}

	public SearchCriterion[] getTypeAlertCriteria()
	{
		
		SearchCriterion[] result;
		SearchCriterion allState = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "all_state"), 0);
		SearchCriterion errorLoad = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "type_alert_error_load"), 1);
		SearchCriterion stockNegative = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "type_alert_stock_negative"), 2);
		SearchCriterion stockInZero = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "type_alert_stock_in_zero"), 3);
		SearchCriterion vtaProductInactive = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "type_alert_vta_product_inactive"), 4);
		SearchCriterion oversoldClient = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "type_alert_oversold_client"), 5);

		result = new SearchCriterion[] { allState, errorLoad, stockNegative, stockInZero, vtaProductInactive, oversoldClient};

		return result;
	}

	public SearchCriterion[] getStockCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion state = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "state"), STATE);
		SearchCriterion sku = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "sku"), SKU);
		result = new SearchCriterion[] { state, sku };

		return result;
	}

	public SearchCriterion[] getStatesCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion all = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "all"), 0);
		SearchCriterion available = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "onstock"), 1);
		SearchCriterion critical = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "critical_stock"), 2);
		SearchCriterion nostock = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "no_stock"), 3);
		SearchCriterion negative = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "negative_stock"), 4);

		result = new SearchCriterion[] { all, available, critical, nostock, negative };
		return result;

	}

}
