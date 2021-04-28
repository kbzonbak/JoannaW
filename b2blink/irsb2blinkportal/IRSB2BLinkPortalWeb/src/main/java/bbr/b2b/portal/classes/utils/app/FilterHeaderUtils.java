package bbr.b2b.portal.classes.utils.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.utilities.BbrUtils;

public class FilterHeaderUtils
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	private FilterHeaderUtils()
	{

	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	public static final String	COMMERCIAL_RESOURCES		= "Commercial";
	public static final String	CUSTOMER_RESOURCES			= "Customer";
	public static final String	FINANCES_RESOURCES			= "Finances";
	public static final String	USERS_RESOURCES				= "Users";
	public static final String	MODULES_GENERICS_RESOURCES	= "Modules_Generics";
	public static final String	FEP_ATTRIBUTES				= "FEP_ATTRIBUTES";
	public static final String	RES_FILTERS					= "Filters";

	public static final String	CAPTION_PREFIX				= " — ";
	public static final String	CAPTION_SUFIX				= ": ";

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================
	public static String getClassificationFilterCaption(String titleResourceName, Boolean isMainCaption)
	{
		String result = (isMainCaption) ? "" : CAPTION_PREFIX;

		result += (titleResourceName);

		return result;
	}

	public static String getFilterCaption(String resourceModule, String titleResourceName, Boolean isMainCaption)
	{
		String result = (isMainCaption) ? "" : CAPTION_PREFIX;

		switch (resourceModule)
		{
			case COMMERCIAL_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, titleResourceName));
				break;
			case FINANCES_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FINANCES, titleResourceName));
				break;
			case USERS_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, titleResourceName));
				break;
			case MODULES_GENERICS_RESOURCES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, titleResourceName));
				break;
			case FEP_ATTRIBUTES:
				result += (I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, titleResourceName));
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

	// public static String getProductCategoryFilterCaption(Boolean
	// isRetailerCategory, Integer level)
	// {
	// // (Se asume que esta función sólo se llamará desde módulos comerciales)
	// // Formato Resultado: Jerarquía {Proveedor/Retailer}: [Nivel X]
	// // Nombre_Categoría
	//
	// String result = CAPTION_PREFIX;
	//
	// result += (isRetailerCategory) ?
	// I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
	// "retailer_hierarchy_product") :
	// I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
	// "provider_hierarchy_product");
	// result += CAPTION_SUFIX;
	//
	// String categoryLevel = (level != null && level > 0) ? level.toString() :
	// I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
	// "products");
	//
	// result += (level != CategoryUtils.ALL_CATEGORIES_OPTION_LEVEL) ?
	// BbrUtils.getInstance().substitute("[{0} {1}] ",
	// I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
	// "level"), categoryLevel) : "";
	//
	// return result;
	// }

	// public static String getSectionCategoryFilterCaption(Integer level)
	// {
	//
	// String result = CAPTION_PREFIX;
	//
	// result +=
	// I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
	// "product_rt_level_2");
	// result += CAPTION_SUFIX;
	//
	// String categoryLevel = (level != null && level > 0) ? level.toString() :
	// I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
	// "products");
	//
	// result += (level != CategoryUtils.ALL_CATEGORIES_OPTION_LEVEL) ?
	// BbrUtils.getInstance().substitute("[{0} {1}] ",
	// I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
	// "level"), categoryLevel) : "";
	//
	// return result;
	// }

	public static String getProductCategoryFilterCaption(Integer level)
	{
		// (Se asume que esta función sólo se llamará desde módulos comerciales)
		// Formato Resultado: Categoría Producto Nivel X : Nombre_Categoría

		String result = CAPTION_PREFIX;

		result += I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "product_category") + BbrUtils.getInstance().substitute(" {0} {1} ", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "level"), level);

		result += CAPTION_SUFIX;

		return result;
	}

	// public static String getLocalCategoryFilterCaption(Boolean
	// isRetailerCategory, Integer level)
	// {
	// // (Se asume que esta función sólo se llamarí desde módulos comerciales)
	// // Formato Resultado: Jerarquía {Proveedor/Retailer}: [Nivel X]
	// // Nombre_Categoría
	//
	// String result = CAPTION_PREFIX;
	//
	// result += (isRetailerCategory) ?
	// I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
	// "retailer_hierarchy_local") :
	// I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
	// "provider_hierarchy_local");
	// result += CAPTION_SUFIX;
	//
	// String categoryLevel = (level != null && level > 0) ? level.toString() :
	// I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
	// "locals");
	//
	// result += (level != CategoryUtils.ALL_CATEGORIES_OPTION_LEVEL) ?
	// BbrUtils.getInstance().substitute("[{0} {1}] ",
	// I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL,
	// "level"), categoryLevel) : "";
	//
	// return result;
	// }

	public static String getPeriodCaption(String resourceModule, String titleResourceName, LocalDateTime startDate, LocalDateTime endDate)
	{
		String result = FilterHeaderUtils.getFilterCaption(resourceModule, titleResourceName, false);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		result += BbrUtils.getInstance().substitute("{0} {1} {2}", startDate.format(formatter), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "to"), endDate.format(formatter));

		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================

}
