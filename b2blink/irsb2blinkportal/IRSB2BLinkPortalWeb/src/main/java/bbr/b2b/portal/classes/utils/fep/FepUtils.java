package bbr.b2b.portal.classes.utils.fep;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.fep.common.data.classes.PersonDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrUI;

public class FepUtils
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================

	public static String getAll_FDescription(BbrUI bbrUI)
	{
		String result = I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_SEARCH, "search_all_f");
		return result;
	}

	public static String getAll_Description(BbrUI bbrUI)
	{
		String result = I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_SEARCH, "search_all");
		return result;
	}

	public static SearchCriterion[] getYesNoCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion yes = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "yes"), 0);
		SearchCriterion no = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "no"), 1);

		result = new SearchCriterion[] { yes, no };
		return result;
	}

	public static SearchCriterion[] getAttributesCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion all = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "all_upper"), -1);
		SearchCriterion name = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "cbx_option_b2b_name"), 0);
		SearchCriterion provider = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "cbx_option_provider"), 1);
		result = new SearchCriterion[] { all, name, provider };
		return result;
	}
	
	public static SearchCriterion[] getDatesCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion all = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "all_upper_f"), -1);
		SearchCriterion creation = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "cbx_option_creation_date"), 0);
		SearchCriterion modification = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "cbx_option_modification_date"), 1);
		result = new SearchCriterion[] { all, creation, modification };
		return result;
	}
	
	public static SearchCriterion[] getStatesCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion active = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "cbx_active"), 1);
		SearchCriterion inactive = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "cbx_inactive"), 0);
		result = new SearchCriterion[] {active, inactive };
		return result;
	}
	
	public static SearchCriterion[] getTemplatesCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion active 		= new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "search_all"), 0);
		SearchCriterion inactive 	= new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "template_name"), 1);
		
		result = new SearchCriterion[] {active, inactive};
		return result;
	}
	
	public static SearchCriterion[] getGenericAttributesForTemplatesCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion name = new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "cbx_option_b2b_name"), 0);
		
		result = new SearchCriterion[] {name};
		return result;
	}
	
	public static SearchCriterion[] getRequestStatesCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion pending 			= new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "pending"), FEPConstants.PENDING_STATE_VALUE);
		SearchCriterion rejected 			= new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "rejected"), FEPConstants.REJECTED_STATE_VALUE);
		SearchCriterion approved 			= new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "approved"), FEPConstants.APPROVED_STATE_VALUE);
		
		result = new SearchCriterion[] {pending, approved, rejected};
		return result;
	}


	public static SearchCriterion[] getProductStateFilterCriteria()
	{
		SearchCriterion[] result;
		SearchCriterion state 			= new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_state"), FEPConstants.STATE_FILTER_VALUE);
		SearchCriterion sku 				= new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_sku"), FEPConstants.SKU_FILTER_VALUE);
		SearchCriterion updateDate 	= new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "cbx_option_modification_date"), FEPConstants.UPDATE_DATE_FILTER_VALUE);
		SearchCriterion creationDate 	= new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "cbx_option_creation_date"), FEPConstants.CREATION_DATE_FILTER_VALUE);
		
		result = new SearchCriterion[] {state, sku, creationDate, updateDate};
		return result;
	}
	
	
	/**
	 * Crea un PersonDTO a partir de un BBRUser
	 */
	public static PersonDTO createPersonDTO(BbrUser bbruser)
	{
		PersonDTO result = null;
		if (bbruser != null)
		{
			result = new PersonDTO();
			try
			{
				BeanExtenderFactory.copyProperties(bbruser, result);
				result.setLastname(bbruser.getLastName());
				result.setCode(bbruser.getRut());
				result.setMail(bbruser.getEmail());
			}
			catch (OperationFailedException e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String getAttributeImageURL(BbrUI bbrUI, String filename, String internalname)
	{
		String url = AppUtils.getInstance().getFullServerContextPath(bbrUI) + "/b2b/mdm/attributes?filename={0}&internalname={1}";
		
		String result = BbrUtils.getInstance().substitute(url, filename,internalname);
		
		return result;
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================

}
