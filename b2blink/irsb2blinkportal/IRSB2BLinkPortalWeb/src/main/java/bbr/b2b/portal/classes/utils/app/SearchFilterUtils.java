package bbr.b2b.portal.classes.utils.app;

import bbr.b2b.portal.classes.constants.EnumUserType;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.components.basics.BbrUI;

public class SearchFilterUtils
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static SearchFilterUtils instance = new SearchFilterUtils();

	public static SearchFilterUtils getInstance()
	{
		return instance;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	private SearchFilterUtils()
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
	public SearchCriterion[] getActionFilterCriteria(BbrUI bbrUI)
	{
		SearchCriterion[] result;

		SearchCriterion all = new SearchCriterion(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_GENERIC, "all_upper_f").toUpperCase(), -1);
		SearchCriterion accept = new SearchCriterion(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_MANAGEMENT, "app_accept").toUpperCase(), 0);
		SearchCriterion reject = new SearchCriterion(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_MODULES_MANAGEMENT, "app_reject").toUpperCase(), 1);

		result = new SearchCriterion[] { all, accept, reject };

		return result;
	}

	public SearchCriterion[] getClassificationManagementFilterCriteria()
	{
		SearchCriterion[] result;

		SearchCriterion all = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "all").toUpperCase(), -1);
		SearchCriterion classification = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "classification"), 0);
		SearchCriterion code = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "code"), 1);
		SearchCriterion socialReason = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "social_reason"), 2);

		result = new SearchCriterion[] { all, classification, code, socialReason };

		return result;
	}

	public SearchCriterion[] getUserManagementFilterCriteria(EnumUserType enumUserType)
	{
		SearchCriterion[] result;

		SearchCriterion userName = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_user_name"), 0);
		SearchCriterion userLastName = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_user_lastname"), 1);
		SearchCriterion userType = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_user_type"), 2);
		SearchCriterion userState = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_user_state"), 3);
		SearchCriterion userEnterprise = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_user_enterprise"), 4);
		SearchCriterion vendorAccess = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_user_access_to_providers"), 5);
		SearchCriterion userProfile = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_user_profile"), 6);
		SearchCriterion userId = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_user_id"), 7);

		if (enumUserType == EnumUserType.PROVIDER)
		{
			result = new SearchCriterion[] { userState };
		}
		else
		{
			result = new SearchCriterion[] { userId, userName, userLastName, userType, userState, userEnterprise, vendorAccess, userProfile };
		}

		return result;
	}

	public SearchCriterion[] getUserStateFilterCriteria()
	{

		SearchCriterion userAll = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_all"), -1);
		SearchCriterion userActive = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_active"), 1);
		SearchCriterion userInactive = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_inactive"), 0);

		SearchCriterion[] result = new SearchCriterion[] { userAll, userActive, userInactive };

		return result;
	}

	public SearchCriterion[] getValidityFilterCriteria()
	{

		SearchCriterion validityAll = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "validity_all"), -1);
		SearchCriterion validityCurrents = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "validity_currents"), 1);
		SearchCriterion validityHistory = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "validity_history"), 0);

		SearchCriterion[] result = new SearchCriterion[] { validityAll, validityCurrents, validityHistory };

		return result;
	}

	public SearchCriterion[] getDiscountStatesFilterCriteria()
	{

		SearchCriterion discountAll = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "discount_all"), -1);
		SearchCriterion discountYes = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "discount_yes"), 1);
		SearchCriterion discountNo = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "discount_no"), 0);

		SearchCriterion[] result = new SearchCriterion[] { discountAll, discountYes, discountNo };

		return result;
	}

	public SearchCriterion[] getDocumentStatesFilterCriteria()
	{

		SearchCriterion documentsAll = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "documents_all"), -1);
		SearchCriterion documentsYes = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "documents_actived"), 0);
		SearchCriterion documentsNo = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "documents_removed"), 1);

		SearchCriterion[] result = new SearchCriterion[] { documentsAll, documentsYes, documentsNo };

		return result;
	}

	public SearchCriterion[] getSearchCompanyCriteria()
	{
		SearchCriterion description = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "cmp_social_reason"), 0);
		SearchCriterion code = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "cmp_enterprise_code"), 1);

		SearchCriterion[] result = new SearchCriterion[] { description, code };

		return result;
	}

	public SearchCriterion[] getSearchSiteCriteria()
	{
		SearchCriterion description = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "description"), 0);

		SearchCriterion[] result = new SearchCriterion[] { description };

		return result;
	}

	public SearchCriterion[] getSearchExecutiveCriteria()
	{
		SearchCriterion name = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "exe_name"), 0);
		SearchCriterion lastname = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "exe_last_name"), 1);
		SearchCriterion rut = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "exe_rut_no_digit"), 2);

		SearchCriterion[] result = new SearchCriterion[] { name, lastname, rut };

		return result;
	}

	public SearchCriterion[] getVendorCompanyFilterCriteria()
	{
		SearchCriterion socialReason = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "cmp_vendor_social_reason"), 0);
		SearchCriterion code = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "cmp_vendor_code"), 1);
		SearchCriterion payerCompany = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "cmp_payer_company"), 2);

		SearchCriterion[] result = new SearchCriterion[] { socialReason, code, payerCompany };

		return result;
	}

	public SearchCriterion[] getOperationsFilterCriteria()
	{
		SearchCriterion operationNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "operation_number"), 0);
		SearchCriterion documentNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "document_number"), 1);
		SearchCriterion datesRange = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "dates_range"), 2);

		SearchCriterion[] result = new SearchCriterion[] { operationNumber, documentNumber, datesRange };

		return result;
	}

	public SearchCriterion[] getSurplusFilterCriteria()
	{
		SearchCriterion documentNumber = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "document_number"), 0);
		SearchCriterion datesRange = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "dates_range"), 1);

		SearchCriterion[] result = new SearchCriterion[] { documentNumber, datesRange };

		return result;
	}

	public SearchCriterion[] getListVendorTicketsFilterCriteria()
	{
		SearchCriterion valids = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "ticket_valids"), 0);
		SearchCriterion not_valid = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "ticket_not_valids"), 1);
		SearchCriterion case_id = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "ticket_case_id"), 2);

		SearchCriterion[] result = new SearchCriterion[] { valids, not_valid, case_id };

		return result;
	}

	public SearchCriterion[] getListSupervisorTicketsFilterCriteria()
	{
		SearchCriterion valids = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "ticket_valids"), 0);
		SearchCriterion not_valid = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "ticket_not_valids"), 1);
		SearchCriterion case_id = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "ticket_case_id"), 2);
		SearchCriterion analyst_id = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "ticket_analyst_id"), 3);

		SearchCriterion[] result = new SearchCriterion[] { valids, not_valid, case_id, analyst_id };

		return result;
	}

	public SearchCriterion[] getUsersManagerCriteria()
	{
		SearchCriterion description = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"), 0);
		SearchCriterion code = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "code"), 1);

		SearchCriterion[] result = new SearchCriterion[] { description, code };

		return result;
	}

	public SearchCriterion[] getProductSearchCriteria()
	{
		SearchCriterion description = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "product_description"), 1);
		SearchCriterion providerCode = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "provider_code"), 3);
		SearchCriterion retailerCode = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "retailer_code"), 2);
		SearchCriterion eanCode = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "ean_code"), 4);

		SearchCriterion[] result = new SearchCriterion[] { description, retailerCode, eanCode, providerCode };

		return result;
	}

	public SearchCriterion[] getProductSearchLocallyCriteria()
	{
		SearchCriterion description = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "product_description"), 1);
		SearchCriterion providerCode = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "provider_code"), 3);
		SearchCriterion retailerCode = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "retailer_code"), 2);
		SearchCriterion eanCode = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "ean_code"), 4);

		SearchCriterion[] result = new SearchCriterion[] { description, retailerCode, eanCode, providerCode };

		return result;
	}

	public SearchCriterion[] getLocalSearchLocallyCriteria()
	{
		SearchCriterion localCode = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "local_code"), 1);
		SearchCriterion description = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "local_description"), 2);

		SearchCriterion[] result = new SearchCriterion[] { description, localCode };

		return result;
	}

	public SearchCriterion[] getLocalsSearchCriteria()
	{
		SearchCriterion localCode = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "local_code"), 1);
		SearchCriterion description = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "local_description"), 2);

		SearchCriterion[] result = new SearchCriterion[] { description, localCode };

		return result;
	}

	public SearchCriterion[] getUserManagementRequestsSearchCriteria()
	{
		SearchCriterion openRequests = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "open_requests"), 0);
		SearchCriterion closedRequests = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "closed_requests"), 1);

		SearchCriterion[] result = new SearchCriterion[] { openRequests, closedRequests };

		return result;
	}

	public SearchCriterion[] getUserManagementRequestsRetailSearchCriteria()
	{
		SearchCriterion openRequests = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "open_requests"), 0);
		SearchCriterion closedRequests = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "closed_requests"), 1);

		SearchCriterion[] result = new SearchCriterion[] { openRequests, closedRequests };

		return result;
	}

	public SearchCriterion[] getUserProfileManagementFilterCriteria()
	{
		SearchCriterion userName = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_user_name"), 1);
		SearchCriterion userLastName = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_user_lastname"), 2);
		SearchCriterion userId = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_user_id"), 0);

		SearchCriterion[] result = new SearchCriterion[] { userName, userLastName, userId };

		return result;
	}

	public SearchCriterion[] getSugSearchProductFilterCriteria()
	{
		SearchCriterion sku = new SearchCriterion("SKU", 0);
		SearchCriterion code = new SearchCriterion("Código proveedor", 1);
		SearchCriterion description = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"), 2);

		SearchCriterion[] result = new SearchCriterion[] { sku, code, description };

		return result;
	}
}
