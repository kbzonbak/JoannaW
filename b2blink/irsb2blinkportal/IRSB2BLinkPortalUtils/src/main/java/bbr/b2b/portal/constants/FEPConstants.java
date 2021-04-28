package bbr.b2b.portal.constants;

public class FEPConstants
{

	private static FEPConstants _instance;

	// Constructor
	public static synchronized FEPConstants getInstance()
	{
		if (_instance == null)
		{
			_instance = new FEPConstants();
		}

		return _instance;
	}

	public static final int			RETAIL_PV_CODE								= 1;
	public static final int			INT_CAT_TODOS								= -1;

	public static final Integer		CATPROD_MAX_LEVEL							= 2;

	public static final String[]	MONTHS_NAME									= { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre",
			"Noviembre", "Diciembre" };

	// cp_key_padre de la raiz de las categorías
	public static final int			KEY_CATEGORY_FATHER							= -1;

	public static final String		STR_CAT_TODOS								= String.valueOf(INT_CAT_TODOS);
	public static final String		STR_FUNCION_LOCAL							= "L";

	public static final int			INTERVAL_SEMESTRE							= 3;
	public static final int			INTERVAL_MONTH								= 2;
	public static final int			INTERVAL_WEEK								= 1;
	public static final int			INTERVAL_DAY								= 0;

	// esta variable se usará para los reportes en que no se puede paginar en BD
	public static final int			MAX_ROWS_PAGINATION							= 50;
	public static final int			MAX_ROWS_PAGINATION_DETAIL					= 100;
	public static final int			MAX_ROWS_PAGINATION_BRIEF					= 200;

	/********************************
	 * FEP CONTANST
	 **********************************/
	public static final int			DEFINABLE_VARIANT_QUERY_TYPE				= 2;
	public static final int			DEFINABLE_ARTICLE_QUERY_TYPE				= 1;

	// Tipos de Plantillas
	public static final String		ARTICLETYPE_CLASS_NAME_MP					= "MODIFY";
	public static final String		ARTICLETYPE_CLASS_NAME_MPS					= "MODIFY SPECIAL";
	public static final String		ARTICLETYPE_CLASS_NAME_CP					= "CREATE";
	public static final String		ARTICLETYPE_CLASS_NAME_CPS					= "CREATE SPECIAL";

	// Tipos de modulos para (Creación o Enrequecimiento)
	public static final String		MODULE_TYPE_NAME_CP							= "ATTCREATE";
	public static final String		MODULE_TYPE_NAME_MP							= "ATTMODIFY";
	public static final String		MODULE_TYPE_NAME_CMP						= "ATTBOTH";

	public static final String		MODULE_TYPE_CP								= "CP";
	public static final String		MODULE_TYPE_MP								= "MP";
	public static final String		MODULE_TYPE_CM								= "CMP";

	// Tipos de Atributos
	public static final String		ATTRIBUTE_NAME_TYPE_STRING					= "String";
	public static final String		ATTRIBUTE_NAME_TYPE_INTEGER					= "Integer";
	public static final String		ATTRIBUTE_NAME_TYPE_FLOAT					= "Float";
	public static final String		ATTRIBUTE_NAME_TYPE_BOOLEAN					= "Boolean";
	public static final String		ATTRIBUTE_NAME_TYPE_DATE					= "Date";
	public static final String		ATTRIBUTE_NAME_TYPE_DATE_TIME				= "DateTime";
	public static final String		ATTRIBUTE_NAME_TYPE_TIME					= "Time";

	public static final String		ATTRIBUTE_NAME_TYPE_LIST					= "List";
	public static final String		ATTRIBUTE_NAME_TYPE_MU_LIST					= "MultipleSelectList";
	public static final String		ATTRIBUTE_NAME_TYPE_CODEDLIST				= "CodedList";
	public static final String		ATTRIBUTE_NAME_TYPE_MUCODEDLIST				= "MultipleSelectCodedList";
	public static final String		ATTRIBUTE_NAME_TYPE_IMAGE					= "Image";
	public static final String		ATTRIBUTE_NAME_TYPE_FILE					= "File";
	public static final String		ATTRIBUTE_NAME_TYPE_HIERARCHI				= "HierarchyList";
	public static final String		ATTRIBUTE_NAME_TYPE_MUHIERARCHI				= "HierarchyMultipleSelectList";
	public static final String		ATTRIBUTE_NAME_TYPE_HIERARCHI_RETAIL		= "HierarchyRetailList";
	public static final String		ATTRIBUTE_NAME_TYPE_MUHIERARCHI_RETAIL		= "HierarchyRetailMultipleSelectList";
	public static final String		ATTRIBUTE_NAME_TYPE_HIERARCHI_RETAIL_CLASS	= "ClassRetailList";
	public static final String		ATTRIBUTE_NAME_TYPE_TRADELIST				= "TradeList";
	public static final String		ATTRIBUTE_NAME_TYPE_COLORLIST				= "ColorList";
	public static final String		ATTRIBUTE_NAME_TYPE_RELATEDPRODUCTLIST		= "RelatedProductList";

	public static final String		ATTRIBUTE_TYPE_GENERALS						= "ATRIBUTOS GENERALES";

	// Tipos básico de Atributos
	public static final String		ATTRIBUTE_BASIC_TYPE_STRING					= "String";
	public static final String		ATTRIBUTE_BASIC_TYPE_INTEGER				= "Integer";
	public static final String		ATTRIBUTE_BASIC_TYPE_FLOAT					= "Float";
	public static final String		ATTRIBUTE_BASIC_TYPE_BOOLEAN				= "Boolean";
	public static final String		ATTRIBUTE_BASIC_TYPE_DATE					= "Date";
	public static final String		ATTRIBUTE_BASIC_TYPE_DATE_TIME				= "DateTime";
	public static final String		ATTRIBUTE_BASIC_TYPE_TIME					= "Time";

	// Status MP
	public static final int			PENDING_STATE_VALUE							= 0;
	public static final int			APPROVED_STATE_VALUE						= 1;
	public static final int			REJECTED_STATE_VALUE						= 2;
	public static final int			ALL_STATE_VALUE								= -1;
	public static final int			NOTCLOSED_STATE_VALUE						= 3;
	public static final int			FINAL_REJECTED_STATE_VALUE					= 4;

	// Multi Filtro Estado de Productos(Solicitudes)

	public static final int			STATE_FILTER_VALUE							= 0;
	public static final int			SKU_FILTER_VALUE							= 1;
	public static final int			CREATION_DATE_FILTER_VALUE					= 2;
	public static final int			UPDATE_DATE_FILTER_VALUE					= 3;


	// Tipos de Archivos
	public static Long				IMAGE_FILE									= 1L;
	public static Long				DOCUMENT_FILE								= 2L;
	public static Long				COMPRESSED_FILE								= 3L;

	// NOMBRE DE LOS ESTADOS
	public static String			PROVIDER_STATUS_NAME						= "PROVEEDOR";
	public static String			UPDATED_STATUS_NAME							= "ACTUALIZADO";

	// Roles
	// public static String PROVIDER_ROLE_NAME = "PROVEEDOR";
	// public static String BUYER_ROLE_NAME = "COMPRADOR";
	// public static String COMERCIAL_ROLE_NAME = "COMERCIAL";
	public static String			UCDM_ROLE_NAME								= "UCDM";
	public static String			LOGISTIC_ROLE_NAME							= "LOGISTICA";
	public static String			SYSTEM_ROLE_NAME							= "SISTEMA";
	public static String			ERP_ROLE_NAME								= "ERP";
	public static String			PRODUCT_MANAGER_ROLE_NAME					= "PRODUCT_MANAGER";

	// IDs de los Roles de usuarios

	public static Long				PROVIDER_ROLE_ID							= 1L;
	public static Long				BUYER_ROLE_ID								= 2L;
	public static Long				ERP_ROLE_ID									= 3L;
	public static final Long		SYSTEM_USER_TYPE_ID							= 10L;																									//
	public static final Long		UCDM_USER_TYPE_ID							= 4L;																									//

	// Clientes

	public static final String		INTERNAL_CLIENT_NAME						= "BBR SPA";
	public static final String		NO_RETAIL_CLIENT							= "BBR";

	// Nombre de estados MP
	public static String			CREATED_NEW_STATUS_NAME						= "CREADO_NUEVO";

	// NOMBRE DE LOS ESTADOS CREACION
	public static String			PROVIDER_NEW_NAME_STATUS					= "PROVIDER_NEW";
	public static String			PROVIDER_CREATED_NAME_STATUS				= "PROVIDER_CREATE";
	public static String			BUYER_AP_NAME_STATUS						= "BUYER_A";
	public static String			BUYER_R_NAME_STATUS							= "BUYER_R";
	public static String			ERP_A_NAME_STATUS							= "ERP_A";
	public static String			BUYER_RF_NAME_STATUS						= "BUYER_RF";
	public static String			PROVIDER_CANCEL_NAME_STATUS					= "PROVIDER_CANCEL";

	// Listas
	static public String			LIST_SEPARATOR								= ";";
	public static final String		PROD_ATTRIB_CODED_VALUE_BEGIN				= "[";																									// Cadena
																																														// con
																																														// que
																																														// comienza
																																														// un
																																														// valor
																																														// codificado
																																														// ejemplo:
																																														// Kilogramo[kg]
																																														// comienza
																																														// con
																																														// [
																																														// y
	// termina con ]
	public static final String		PROD_ATTRIB_CODED_VALUE_END					= "]";																									// Cadena
																																														// con
																																														// que
																																														// TERMINA
																																														// un
																																														// valor
																																														// codificado
																																														// ejemplo:
																																														// Kilogramo[kg]
																																														// comienza
																																														// con
																																														// [
																																														// y
	// termina con ]

	public static final String		ATTRIBUTES_SERVLET_PATH						= "/IRSB2BLink/BBRe-commerce/b2b/fep/attributes/";

	// Separadores de Decimales
	public static final String		DECIMAL_FORMAT								= "#";
	public static final char		DECIMAL_SEPARATOR							= ',';

	public String getCategoryLevel(String field, int level)
	{

		String cpkeylevel = null;

		switch (level)
		{
			case 1:
				cpkeylevel = field + "1";
				break;
			case 2:
				cpkeylevel = field + "2";
				break;
			case 3:
				cpkeylevel = field + "3";
				break;
			case 4:
				cpkeylevel = field + "4";
				break;
			case 5:
				cpkeylevel = field + "5";
				break;
			case 6:
				cpkeylevel = field + "6";
				break;
			case 7:
				cpkeylevel = field + "7";
				break;
			default:
				break;
		}

		return cpkeylevel;

	}

}
