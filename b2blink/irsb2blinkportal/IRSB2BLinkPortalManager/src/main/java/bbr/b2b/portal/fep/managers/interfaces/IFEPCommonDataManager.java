package bbr.b2b.portal.fep.managers.interfaces;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.fep.commercial.data.classes.ProviderArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeByClientInitParamDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeClassArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeInitParamDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeConstraintDataDTO;
import bbr.b2b.fep.common.data.classes.AttributeDTO;
import bbr.b2b.fep.common.data.classes.AttributeInitParamDTO;
import bbr.b2b.fep.common.data.classes.AttributeResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeValueDTO;
import bbr.b2b.fep.common.data.classes.AttributeValuesErrorsResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeValuesResultDTO;
import bbr.b2b.fep.common.data.classes.ClientArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ClientInitParamDTO;
import bbr.b2b.fep.common.data.classes.ConstraintTypesResultDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeDataDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeInitParamDTO;
import bbr.b2b.fep.common.data.classes.ErrorInfoArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ErrorInfoDTO;
import bbr.b2b.fep.common.data.classes.ItemAttributeValueDTO;
import bbr.b2b.fep.common.data.classes.LanguageArrayResultDTO;
import bbr.b2b.fep.common.data.classes.PersonDTO;
import bbr.b2b.fep.common.data.classes.ProviderWorkFlowArrayResultDTO;
import bbr.b2b.fep.common.data.classes.StandardArticleTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.StandardArticleTypeDTO;
import bbr.b2b.fep.common.data.classes.UserDataTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.UserTypeArrayResultDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;

public interface IFEPCommonDataManager extends IGenericEJBInterface {
	/***************************  ATRIBUTOS ************************/
	
	//Devuelve todos los datoss asociados a un atributo en un lenguage solicitado
	public AttributeResultDTO getAttribute(Long attributeid, String languagecode);
	
	//CU 500, 501 Devuelve los atributos paginados y filtrados para un lenguage determinado
	public AttributeArrayResultDTO getAttributes(AttributeInitParamDTO initParam,int pagenumber, int rows, boolean ispaginated, OrderCriteriaDTO [] orders, String languagecode);
	

	//Devuelve los atributos que no pertenecen a una plantilla y filtrados para un lenguage determinado
	public  AttributeArrayResultDTO getAttributesNotInArticleType( AttributeInitParamDTO initParam,Long articletypeid,OrderCriteriaDTO[] orders, String languagecode);
	
	//Devuelve los valores asociados a un atributo en en lenguaje solicitado
	public AttributeValuesResultDTO getAttributeValues(Long attributeid, String languagecode);
	public AttributeValuesResultDTO getAttributeRelatedValues(Long attributeid, AttributeValueDTO[] values, String languagecode);
	public AttributeValuesResultDTO getAttributeValuesByItemValues(Long attributeid, ItemAttributeValueDTO[] values, String languagecode);
	
	//Devuelve todos los tipos de atributos definidos en la base de datos para un lenguaje determinado
	public AttributeTypeArrayResultDTO getAttributeTypes(String languagecode);
	
	//Devuelve todos los tipos de datos de usuario para un idioma determinado
	public  UserDataTypeArrayResultDTO getUserDataTypes(String languagecode, boolean showIntern);
	
	//CU 506 Eliminar un atributos y todas sus relaciones
    public BaseResultDTO removeAttributes(AttributeDTO[] attributes, boolean force, String modifyby);
    
 // Adiciona o edita un atributo
	public AttributeResultDTO addorEditAttribute(AttributeDTO attributedto, AttributeConstraintDataDTO[] constraints,	AttributeValueDTO[] values, String languagecode, Long userId);

	/*********************** CONTRAINTS *********************************************************/
	
	//Devuelve la lista de restricciones que existen en el sistema
	public ConstraintTypesResultDTO getConstraintTypes(String languagecode);

	public ConstraintTypesResultDTO getConstraintTypesByDataType(Long usertypeid, String languagecode);
	
	
	/***********************   PLANTILLAS ARTICLETYPE  *******************************************/

	//Devuelve todos los tipos de plantillas que existen, creación, ficha etc.
	public ArticleTypeClassArrayResultDTO getArticleTypeClasses(String module,String languagecode);

	//Devuelve la data de plantilla, sin los detalles de los atributos. sólo para el reporte de busqueda de plantillas
	//Para el detalle se usa getArticleType
	public  ArticleTypeArrayResultDTO getArticleTypeData(ArticleTypeInitParamDTO initParam,int pagenumber, int rows, boolean ispaginated,	 OrderCriteriaDTO[] orders, String languagecode);

	//Devuelve la plantilla y la información de los atributos que le pertenecen
	public  ArticleTypeResultDTO getArticleType(Long articletypeid, String languagecode);

	// Devuelve los atributos de dependencia del tipo de query
    public  DefinableAttributeArrayResultDTO getDefinableAttributes(Long articletypeid, boolean includeAttributeInfo,int querytype, String languagecode);

    //Adicionar o Edita una Plantilla
    public BaseResultDTO addOrEditArticleType(ArticleTypeDTO articleTypeDTO, DefinableAttributeDataDTO[] attributes,String modifyby, String languagecode);

    public BaseResultDTO removeArticleTypes(ArticleTypeDTO[] articletypes, boolean force, PersonDTO user);
    
    //Descargas de valores de atributo en multiples idiomas.    
    public FileDownloadInfoResultDTO downloadDBAttributeValues(Long attributeid, Long uskey, String fileformat, Locale locale);
    public FileDownloadInfoResultDTO downloadAttributeValues(Long attributeid, AttributeValueDTO[] values, Long uskey, String fileformat, Locale locale);

    //Subida de valores de un atributo
    public AttributeValuesErrorsResultDTO uploadAttributesValues(String filename, AttributeValueDTO[] values, Long uskey, Locale locale);

    // CU 508, descarga de información de idioma de atributos
    public FileDownloadInfoResultDTO downloadAttributesLanguageData(Long uskey, String fileformat, Locale locale);
    // Subida de traducción de atributos.
    public ErrorInfoArrayResultDTO uploadAttributesLanguageData(String filename, Long uskey, Locale locale);

 
    //CU 507, Descarga de atributos en base a un filtro
    public FileDownloadInfoResultDTO downloadAttributes(AttributeInitParamDTO initParam, OrderCriteriaDTO[] orders, String filename, Long uskey, String fileformat, Locale locale);
    
    //Descarga el fichero excel base a llenar para la carga masiva
    public FileDownloadInfoResultDTO downloadAttributesFileBase(Long uskey, Locale locale);
    
    public AttributeArrayResultDTO uploadNewAttributes(String filename, Long uskey, Locale locale, String moduletype);

    //Devuelve todos los lenguajes
    public LanguageArrayResultDTO getLanguages();
    
    //CU 527, Descarga los tipos de plantillas en base a un filtro y los atributos asociados		
	public FileDownloadInfoResultDTO downloadArticleType(ArticleTypeInitParamDTO initParam, OrderCriteriaDTO [] orders, String filename, Long usKey, String fileformat, Locale locale);
	
	//Utilitarios generan excepciones que debe ser manejadas
	//Devuelve el valor que le corresponde correctamente formateado a un valor codificado .
	public String formatValueData(String cellvalue, DefinableAttributeDataDTO da, List<ErrorInfoDTO> rowerrors,HashMap<Long, List<AttributeValueDTO> > attSelectedValues, int row, int column, String rowname, String columname,String languagecode);

	//boolean validateAttributeRelations(Collection<DefinableAttributeDataDTO>  definableatts, List<ErrorInfoDTO> rowerrors, List<ItemAttributeValueDTO> values, String languagecode);

	boolean validateAttributeRelations(LinkedHashMap<String,DefinableAttributeDataDTO> defattMap, List<ErrorInfoDTO> rowerrors, List<ItemAttributeValueDTO> values,HashMap<Long, List<AttributeValueDTO> > attSelectedValues, int row, String languagecode);
	
	/***********************  PROVIDERS  ****************************************************/
	public ProviderArrayResultDTO getProviders();
	public ProviderArrayResultDTO getProviderByCodes(String[] pvcodes);
	public ProviderWorkFlowArrayResultDTO getProviderWorkFlows ();
	
	/***********************   USERTYPES (ROLES)  *******************************************/
	public UserTypeArrayResultDTO getUserTypes ();
	public UserTypeArrayResultDTO getRolesByProviderWorkFlow (String providercode);
	public UserTypeArrayResultDTO getRolesByUserProfiles(String profiles);
	
	/***********************   HOMOLOGADOR  *************************************************/
	public ClientArrayResultDTO getAllClients (ClientInitParamDTO initparam);
	public ErrorInfoArrayResultDTO addOrEditStandardArticleTypes (StandardArticleTypeDTO [] standardarticletypes);
	public BaseResultDTO addOrUpdateStandardArticleType( StandardArticleTypeDTO standardArticleType);
	public AttributeArrayResultDTO getAttributesByFilter (ArticleTypeByClientInitParamDTO initparam);
	public StandardArticleTypeArrayResultDTO getStandardizedAttributes(ArticleTypeByClientInitParamDTO initparam);
	public DefinableAttributeArrayResultDTO getDefinableAttributesByArticleType(DefinableAttributeInitParamDTO initparam);
	public FileDownloadInfoResultDTO downloadStandardizedAttributesList (ArticleTypeByClientInitParamDTO initparam);
	
}
