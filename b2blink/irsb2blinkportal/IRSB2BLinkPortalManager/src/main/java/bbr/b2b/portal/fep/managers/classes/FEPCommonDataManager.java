package bbr.b2b.portal.fep.managers.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.NamingException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.util.LoggingUtil;
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
import bbr.b2b.fep.common.data.classes.AttributeLanguageArrayResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeLanguageDataDTO;
import bbr.b2b.fep.common.data.classes.AttributeResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeTypeDTO;
import bbr.b2b.fep.common.data.classes.AttributeValueDTO;
import bbr.b2b.fep.common.data.classes.AttributeValuesErrorsResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeValuesResultDTO;
import bbr.b2b.fep.common.data.classes.ClientArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ClientDTO;
import bbr.b2b.fep.common.data.classes.ClientInitParamDTO;
import bbr.b2b.fep.common.data.classes.ConstraintTypesResultDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeDataDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeInitParamDTO;
import bbr.b2b.fep.common.data.classes.ErrorInfoArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ErrorInfoDTO;
import bbr.b2b.fep.common.data.classes.ItemAttributeValueDTO;
import bbr.b2b.fep.common.data.classes.LanguageArrayResultDTO;
import bbr.b2b.fep.common.data.classes.LanguageDTO;
import bbr.b2b.fep.common.data.classes.PersonDTO;
import bbr.b2b.fep.common.data.classes.ProviderWorkFlowArrayResultDTO;
import bbr.b2b.fep.common.data.classes.StandardArticleTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.StandardArticleTypeDTO;
import bbr.b2b.fep.common.data.classes.StandardArticleTypeResultDTO;
import bbr.b2b.fep.common.data.classes.UserDataTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.UserDataTypeDTO;
import bbr.b2b.fep.common.data.classes.UserTypeArrayResultDTO;
import bbr.b2b.fep.managers.interfaces.ContextUtil;
import bbr.b2b.fep.managers.interfaces.ICommonDataManager;
import bbr.b2b.fep.storage.classes.FileObjectDataDTO;
import bbr.b2b.fep.storage.classes.FileObjectInitParamDTO;
import bbr.b2b.fep.storage.classes.FileObjectResultDTO;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.utils.FEPUtils;
import bbr.b2b.portal.utils.FileHandlerUtils;
import bbr.b2b.portal.utils.I18NManager;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;
import bbr.common.dataset.util.DataColumn;
import bbr.common.dataset.util.DataRow;
import bbr.common.dataset.util.DataTable;

@Stateless(name = "managers/FEPCommonDataManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FEPCommonDataManager implements FEPCommonDataManagerLocal {

	private ICommonDataManager commonDatatManager = null;

	@EJB
	private FEPFileStorageManagerLocal fileStorageManager = null; 

	private static LoggingUtil logger = new LoggingUtil(FEPCommonDataManager.class);

	@PostConstruct
	public void getRemote() {

		try {
			commonDatatManager = ContextUtil.getInstance().getCommonDataManager();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public AttributeResultDTO getAttribute(Long attributeid, String languagecode) {
		AttributeResultDTO resultDTO = new AttributeResultDTO();
		try {
			return commonDatatManager.getAttribute(attributeid, languagecode,true);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}

	@Override
	public LanguageArrayResultDTO getLanguages() {
		LanguageArrayResultDTO resultDTO = new LanguageArrayResultDTO();
		try {
			return commonDatatManager.getLaguages();
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public AttributeArrayResultDTO getAttributes(AttributeInitParamDTO initParam, int pagenumber, int rows, boolean ispaginated, OrderCriteriaDTO[] orders, String languagecode) {
		AttributeArrayResultDTO resultDTO = new AttributeArrayResultDTO();

		//TODO: Datos de pruebas de servicios
		//Locale locale = new Locale(languagecode);
		//this.downloadAttributesFileBase(1L, locale);
		//this.downloadRequestFileBase(1L, locale);

		//this.uploadNewAttributes("test.xlsx", 1L, null, locale, MDMConstants.MODULE_TYPE_NAME_CP);

		try {
			return commonDatatManager.getAttributes( initParam,  pagenumber,  rows,  ispaginated,  orders,  languagecode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public AttributeArrayResultDTO getAttributesNotInArticleType(AttributeInitParamDTO initParam, Long articletypeid, OrderCriteriaDTO[] orders, String languagecode) {
		AttributeArrayResultDTO resultDTO = new AttributeArrayResultDTO();
		try {
			return commonDatatManager.getAttributesNotInArticleType(  initParam,  articletypeid,  orders,  languagecode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public AttributeValuesResultDTO getAttributeValues(Long attributeid,String languagecode) {
		AttributeValuesResultDTO resultDTO = new AttributeValuesResultDTO();
		try {
			return commonDatatManager.getAttributeValues(  attributeid,  languagecode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}

	@Override
	public AttributeValuesResultDTO getAttributeRelatedValues(Long attributeid, AttributeValueDTO[] values, String languagecode)  {
		AttributeValuesResultDTO resultDTO = new AttributeValuesResultDTO();
		try {
			return commonDatatManager.getAttributeRelatedValues(attributeid, values, languagecode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}

	@Override
	public AttributeValuesResultDTO getAttributeValuesByItemValues(Long attributeid, ItemAttributeValueDTO[] values, String languagecode)  {
		AttributeValuesResultDTO resultDTO = new AttributeValuesResultDTO();
		try {
			AttributeValueDTO[] attvalues = null;
			ArrayList<AttributeValueDTO> attvaluelist = new ArrayList<>();
			if(values != null && values.length > 0){
				for(ItemAttributeValueDTO value : values){
					if(value.getSelectedvalues() != null){						 
						attvaluelist.addAll(value.getSelectedvalues());
					}					 
				}
				attvalues =  new AttributeValueDTO[attvaluelist.size()];
				attvalues = attvaluelist.toArray(attvalues);
			}

			return commonDatatManager.getAttributeRelatedValues(attributeid, attvalues, languagecode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}

	@Override
	public AttributeTypeArrayResultDTO getAttributeTypes(String languagecode) {
		AttributeTypeArrayResultDTO resultDTO = new AttributeTypeArrayResultDTO();
		try {
			return commonDatatManager.getAttributeTypes( languagecode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public UserDataTypeArrayResultDTO getUserDataTypes(String languagecode, boolean showIntern) {
		UserDataTypeArrayResultDTO resultDTO = new UserDataTypeArrayResultDTO();
		try {
			return commonDatatManager.getUserDataTypes( languagecode, showIntern);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public ConstraintTypesResultDTO getConstraintTypes(String languagecode) {
		ConstraintTypesResultDTO resultDTO = new ConstraintTypesResultDTO();
		try {
			return commonDatatManager.getConstraintTypes( languagecode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public ConstraintTypesResultDTO getConstraintTypesByDataType(Long usertypeid, String languagecode) {
		ConstraintTypesResultDTO resultDTO = new ConstraintTypesResultDTO();
		try {
			return commonDatatManager.getConstraintTypesByDataType(usertypeid, languagecode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public ArticleTypeClassArrayResultDTO getArticleTypeClasses(String module, String languagecode) {
		ArticleTypeClassArrayResultDTO resultDTO = new ArticleTypeClassArrayResultDTO();
		try {
			return commonDatatManager.getArticleTypeClasses( module, languagecode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public ArticleTypeArrayResultDTO getArticleTypeData(ArticleTypeInitParamDTO initParam, int pagenumber, int rows, boolean ispaginated, OrderCriteriaDTO[] orders, String languagecode) {
		ArticleTypeArrayResultDTO resultDTO = new ArticleTypeArrayResultDTO();
		try {
			return commonDatatManager.getArticleTypeData(  initParam,  pagenumber,  rows,  ispaginated,  orders, languagecode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}

	@Override
	public BaseResultDTO removeArticleTypes(ArticleTypeDTO[] articletypes, boolean force, PersonDTO user) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		try {
			resultDTO = commonDatatManager.removeArticleTypes( articletypes,force,user);
		} catch (Exception e) {
			e.printStackTrace();
			resultDTO = PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}

		return resultDTO;
	}



	@Override
	public ArticleTypeResultDTO getArticleType(Long articletypeid, String languagecode) {
		ArticleTypeResultDTO resultDTO = new ArticleTypeResultDTO();
		try {
			return commonDatatManager.getArticleType(  articletypeid,  languagecode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public DefinableAttributeArrayResultDTO getDefinableAttributes(Long articletypeid, boolean includeAttributeInfo, int querytype, String languagecode) {
		DefinableAttributeArrayResultDTO resultDTO = new DefinableAttributeArrayResultDTO();
		try {
			return commonDatatManager.getDefinableAttributes(  articletypeid,  includeAttributeInfo,  querytype,  languagecode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public BaseResultDTO addOrEditArticleType(ArticleTypeDTO articleTypeDTO, DefinableAttributeDataDTO[] attributes, String modifyby, String languagecode) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		try {
			return commonDatatManager.addOrEditArticleType(  articleTypeDTO, attributes,  modifyby,  languagecode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}

	@Override
	public FileDownloadInfoResultDTO downloadAttributes(AttributeInitParamDTO initParam, OrderCriteriaDTO[] orders, String filename,Long uskey,String fileformat, Locale locale) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		AttributeArrayResultDTO attsResult = null;

		try {
			attsResult = this.getAttributes(initParam, 1, 10000, false, orders, locale.getLanguage());
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo comercial no disp.
		}

		if(attsResult != null && !attsResult.getStatuscode().equals("0")){
			resultDTO.setStatuscode(attsResult.getStatuscode());
			resultDTO.setStatusmessage(attsResult.getStatusmessage());
			return resultDTO;
		}

		String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attributes", locale);
		if(downloadname == null || downloadname.isEmpty() || downloadname.equals("M/R")){
			downloadname = "Atributos";
		}

		String realname = "ATRIBUTOS";

		//Tabla			
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attributes", locale));

		DataColumn col0 = new DataColumn("b2b_Name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_internal_name", locale), String.class);
		DataColumn col1 = new DataColumn("attribute_data_type", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_data_type", locale), String.class);
		DataColumn col2 = new DataColumn("attribute_type", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_type", locale), String.class);
		DataColumn col3 = new DataColumn("attribute_state", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_active", locale), String.class);
		DataColumn col4 = new DataColumn("attribute_vendor_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_vendor_name", locale), String.class);
		DataColumn col5 = new DataColumn("attribute_user_help", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_user_help", locale), String.class);
		DataColumn col6 = new DataColumn("attribute_client_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_client_name", locale), String.class);

		dt0.addColumn(col0);
		dt0.addColumn(col1);
		dt0.addColumn(col2);
		dt0.addColumn(col3);
		dt0.addColumn(col4);
		dt0.addColumn(col5);
		dt0.addColumn(col6);

		DataRow row = null;
		if( attsResult != null && attsResult.getValues()!= null && attsResult.getValues().length > 0){
			for (AttributeDTO att : attsResult.getValues()) {
				row = dt0.newRow(); 
				row.setCellValue("b2b_Name", att.getInternalname());
				row.setCellValue("attribute_data_type", att.getDatatypename());
				row.setCellValue("attribute_vendor_code", att.getVendorname());
				row.setCellValue("attribute_type", att.getAttributetypeinternalname());
				row.setCellValue("attribute_state", att.getActive() ? "Activo" : "Inactivo" );
				row.setCellValue("attribute_user_help", att.getUserhelp());		
				row.setCellValue("attribute_client_name", att.getClientinternalname());
				dt0.addRow(row);
			}
		}

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, fileformat, downloadname, realname, uskey);

		return resultDTO;
	}

	@Override
	public FileDownloadInfoResultDTO downloadArticleType(ArticleTypeInitParamDTO initParam, OrderCriteriaDTO [] orders, String filename, Long usKey, String fileformat, Locale locale)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		ArticleTypeArrayResultDTO artsTypeResult = null;

		try {
			artsTypeResult = commonDatatManager.getArticleTypeData(  initParam,  1,  100000,  false,  orders, locale.getLanguage());
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
		if(artsTypeResult != null && !artsTypeResult.getStatuscode().equals("0")){
			resultDTO.setStatuscode(artsTypeResult.getStatuscode());
			resultDTO.setStatusmessage(artsTypeResult.getStatusmessage());
			return resultDTO;
		}

		//Tabla			
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_article_type", locale));

		//Columnas asociadas a la plantilla
		DataColumn col0 = new DataColumn("article_internalname", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "article_internalname", locale), String.class);
		DataColumn col1 = new DataColumn("article_state", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "article_state", locale), String.class);
		DataColumn col2 = new DataColumn("article_username", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "article_username", locale), String.class);
		DataColumn col3 = new DataColumn("article_dateupdate", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "article_dateupdate", locale), String.class);

		// Columnas asociadas al atributo
		DataColumn col4 = new DataColumn("attribute_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attribute_name", locale), String.class);
		DataColumn col5 = new DataColumn("attribute_vendor_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attribute_vendor_code", locale), String.class);
		DataColumn col6 = new DataColumn("attribute_datatype", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attribute_datatype", locale), String.class);
		DataColumn col7 = new DataColumn("attribute_mandatory", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attribute_mandatory", locale), String.class);
		DataColumn col8 = new DataColumn("attribute_user_help", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attribute_user_help", locale), String.class);

		dt0.addColumn(col0);
		dt0.addColumn(col1);
		dt0.addColumn(col2);
		dt0.addColumn(col3);
		dt0.addColumn(col4);
		dt0.addColumn(col5);
		dt0.addColumn(col6);
		dt0.addColumn(col7);
		dt0.addColumn(col8);

		DataRow row = null;
		if( artsTypeResult != null && artsTypeResult.getValues()!= null && artsTypeResult.getValues().length > 0){


			for (ArticleTypeDTO art : artsTypeResult.getValues()) {

				ArticleTypeResultDTO artResult = this.getArticleType(art.getId(), locale.getLanguage());

				if( artResult != null && artResult.getStatuscode().equals("0") && artResult.getDefattributes()!= null && artResult.getDefattributes().length > 0){
					for (DefinableAttributeDataDTO att : artResult.getDefattributes()){
						row = dt0.newRow(); 
						row.setCellValue("article_internalname", art.getName());
						row.setCellValue("article_state", art.getActive() ? "Activo" : "Inactivo");
						row.setCellValue("article_username", art.getModifyby());
						String datstr = "";
						try
						{
							datstr = art.getLastmodified().format(FEPUtils.getInstance().getDownloadLocalDateFormat());
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						row.setCellValue("article_dateupdate", datstr);
						row.setCellValue("attribute_name", att.getAttributeinternalname());
						row.setCellValue("attribute_vendor_code", att.getAttributevendorname());
						row.setCellValue("attribute_datatype", att.getAtributedatatypename());
						row.setCellValue("attribute_mandatory", att.getMandatory()? "SI" : "NO");
						row.setCellValue("attribute_user_help", att.getAttributeuserhelp());
						dt0.addRow(row);
					}
				}else{
					row = dt0.newRow(); 
					row.setCellValue("article_internalname", art.getName());
					row.setCellValue("article_state", art.getActive() ? "Activo" : "Inactivo");
					row.setCellValue("article_username", art.getModifyby());
					String datstr = "";
					try
					{
						datstr = art.getLastmodified().format(FEPUtils.getInstance().getDownloadLocalDateFormat());
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					row.setCellValue("article_dateupdate", datstr);
					dt0.addRow(row);
				}

			}
		}
		String downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_article_type", locale);
		String realname = "PLANTILLAS";
		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, fileformat, downloadname, realname, usKey);

		return resultDTO;
	}


	@Override
	public FileDownloadInfoResultDTO downloadAttributeValues(Long attributeid,AttributeValueDTO[] values, Long uskey, String fileformat, Locale locale) {

		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		LanguageArrayResultDTO langsResult = null;


		try {
			langsResult = commonDatatManager.getLaguages();
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}

		if(langsResult != null && !langsResult.getStatuscode().equals("0")){
			resultDTO.setStatuscode(langsResult.getStatuscode());
			resultDTO.setStatusmessage(langsResult.getStatusmessage());
			return resultDTO;
		}

		//Tabla			
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_attribute_values_title", locale));

		HashMap<String, LanguageDTO> laguageMap = new HashMap<>();
		//Crea las columnas, una para cada idioma
		for(LanguageDTO language : langsResult.getValues()){
			laguageMap.put(language.getName(), language);
			DataColumn col = new DataColumn(language.getName(), language.getDescription(), String.class);	
			dt0.addColumn(col);
		}


		DataRow row = null;
		HashMap<Integer, DataRow> rowMap = new HashMap<>();
		if(values != null && values.length > 0.){
			for (AttributeValueDTO attval : values) {
				row = rowMap.get(attval.getVisualorder());
				if (row == null){
					row = dt0.newRow(); 
					rowMap.put(attval.getVisualorder(), row);
					dt0.addRow(row);
				}				
				row.setCellValue(attval.getLanguagecode(), attval.getValue());				
			}
		}

		String downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_attribute_values_title", locale);
		String realname = "VALORES_ATRIBUTO";
		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, fileformat, downloadname, realname, uskey);
		return resultDTO;

	}

	@Override
	public FileDownloadInfoResultDTO downloadDBAttributeValues(Long attributeid, Long uskey, String fileformat, Locale locale) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		AttributeValuesResultDTO  valuesResult = null;

		try {
			valuesResult = commonDatatManager.getAllLanguagesAttributeValues(attributeid);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}

		if(valuesResult != null && !valuesResult.getStatuscode().equals("0")){
			resultDTO.setStatuscode(valuesResult.getStatuscode());
			resultDTO.setStatusmessage(valuesResult.getStatusmessage());
			return resultDTO;
		}

		resultDTO = this.downloadAttributeValues(attributeid, valuesResult.getValues(), uskey, fileformat, locale);

		return resultDTO;		

	}

	@Override
	public AttributeValuesErrorsResultDTO uploadAttributesValues(String filename,AttributeValueDTO[] values, Long uskey, Locale locale) {
		AttributeValuesErrorsResultDTO result = new AttributeValuesErrorsResultDTO();
		ArrayList<ErrorInfoDTO> errors = new ArrayList<>();

		String file = PortalConstants.getInstance().getFileUploadPath() + uskey + "/" + filename;
		String extension = filename.substring(filename.lastIndexOf('.') + 1);

		//Mapa de valores que tiene FE
		HashMap<String, AttributeValueDTO> originalAttval_eMap = new HashMap<>();
		//Nuevo Mapa de valores 
		HashMap<String, AttributeValueDTO> newAttval_eMap = new HashMap<>();

		//Primero crear un mapa con los valores originales para poder modificarlos al final		
		for(AttributeValueDTO or_value : values){
			String la_code = (or_value.getLanguagecode() != null && !or_value.getLanguagecode().isEmpty()) ? or_value.getLanguagecode() : locale.getLanguage();
			String att_val = or_value.getValue() != null ? or_value.getValue() : "";
			originalAttval_eMap.put(la_code + att_val, or_value);
		}


		LanguageArrayResultDTO langsResult = null;
		HashMap<String, LanguageDTO> languageCodeMap = new HashMap<>();
		HashMap<String, LanguageDTO> languageDescMap = new HashMap<>();
		HashMap<Integer, LanguageDTO> languageColumnMap = new HashMap<>();
		try {
			langsResult  = commonDatatManager.getLaguages();
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");// modulo comercial no disp.
		}

		if(langsResult != null && !langsResult.getStatuscode().equals("0")){
			result.setStatuscode(langsResult.getStatuscode());
			result.setStatusmessage(langsResult.getStatusmessage());
			return result;
		}
		else{
			for(LanguageDTO language : langsResult.getValues()){
				languageDescMap.put(language.getDescription(), language);
				languageCodeMap.put(language.getName(), language);				
			}
		}			


		InputStream inp = null;
		Workbook wb1 = null;

		// EXCEL
		try {
			inp = new FileInputStream(file);

			if (extension.equalsIgnoreCase("xlsx")) {
				wb1 = new XSSFWorkbook(inp);
			} else if (extension.equalsIgnoreCase("xls")) {
				POIFSFileSystem fs = new POIFSFileSystem(inp);
				wb1 = new HSSFWorkbook(fs);
			}
		} catch (IOException e) {
			e.printStackTrace();
			PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");
		} catch (Exception e) {
			e.printStackTrace();
			PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");
		}


		Sheet sheet = null;
		String error = "";

		try {
			// Get the first sheet
			sheet = wb1.getSheetAt(0);
			int headerRowindex = 0;

			//Buscar las columnas definidas

			Row header_row = sheet.getRow(headerRowindex);
			int lastcolumnumber = header_row.getLastCellNum();
			for(int colum_index = 0 ; colum_index< lastcolumnumber;colum_index++){
				String languagedescription = null;
				LanguageDTO languageOK = null;
				try {					
					Cell headercell = header_row.getCell(colum_index);					
					if(headercell != null){
						languagedescription = headercell.getStringCellValue();
						languageOK = languageDescMap.get(languagedescription);
					}
				} catch (Exception e) {
					;
				}

				if(languageOK == null){
					error = "El encabezado de la columna " + (colum_index + 1) + " es un idioma inv�lido";
					errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));
				}
				else{
					languageColumnMap.put(colum_index, languageOK);
				}
			}


			if (errors.size() > 0) {
				result.setErrors( errors.toArray(new ErrorInfoDTO[errors.size()]) );
				return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");			
			}



			// Se buscan las filas en blanco
			List<Integer> rowindexList = new ArrayList<Integer>();
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// Validando fila en blanco
				if ( row.getCell(0) == null ){
					rowindexList.add(row.getRowNum());
				}
			}

			// Se eliminan aquellas filas en blanco
			for (Iterator<Integer> iterator = rowindexList.iterator(); iterator.hasNext();) {
				Integer rowindex = iterator.next();
				Row row = sheet.getRow(rowindex);
				sheet.removeRow(row);
			}

			// VALIDA SI EL ARCHIVO ESTA VACIO
			if (sheet.getPhysicalNumberOfRows() == 0) {
				return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P10001");			
			}


			int firstRowData = 1;
			ArrayList<ErrorInfoDTO> row_errors = new ArrayList<>();
			//Barrer todas las filas
			for (int j = firstRowData; j <= sheet.getLastRowNum(); j++) {
				row_errors.clear();

				Row row = sheet.getRow(j);
				if (row == null)
					continue;

				//Descripción del idioma corresponiente a cada columna
				for(int colum_index = 0 ; colum_index< lastcolumnumber;colum_index++){
					String attvalue = "";
					LanguageDTO language = languageColumnMap.get(colum_index);
					try {					
						Cell cell = row.getCell(colum_index);					
						if(cell != null){
							attvalue = cell.getStringCellValue();							
						}
					} catch (Exception e) {
						attvalue = "";
					}
					boolean isdefaultlanguage = language.getIsdefault();

					if(isdefaultlanguage && (attvalue == null || attvalue.isEmpty()) ){
						error = "Se requiere entrar un dato válido en columna. Idioma por defecto. "  +  (colum_index+1)  +  " Fila: " + (j+1);
						row_errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));
						continue;
					} 

					String att_val_key = language.getName() + attvalue;
					AttributeValueDTO att_valDTO = originalAttval_eMap.get(att_val_key);

					if(att_valDTO != null){
						att_valDTO.setValue(attvalue);
						att_valDTO.setVisualorder(j);
						newAttval_eMap.put(att_val_key, att_valDTO);
					}
					else{
						//Crear uno nuevo para este idioma y agregarlo al mapa
						AttributeValueDTO newatt_valDTO = new AttributeValueDTO(); 
						newatt_valDTO.setLanguageid(language.getId());
						newatt_valDTO.setLanguagecode(language.getName());
						newatt_valDTO.setValue(attvalue);
						newatt_valDTO.setVisualorder(j);
						newAttval_eMap.put(att_val_key, newatt_valDTO);
					}
				}				


			}

			if (errors.size() > 0) {
				result.setErrors( errors.toArray(new ErrorInfoDTO[errors.size()]) );
				return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");			
			}
			AttributeValueDTO[] attval_arr = newAttval_eMap.values().toArray(new AttributeValueDTO[newAttval_eMap.values().size()] );
			result.setValues(attval_arr);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");
		}

	}


	@Override
	public FileDownloadInfoResultDTO downloadAttributesLanguageData(Long uskey, String fileformat, Locale locale){
		FileDownloadInfoResultDTO result = new FileDownloadInfoResultDTO();

		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		AttributeLanguageArrayResultDTO  attsResult = null;
		LanguageArrayResultDTO langsResult = null;

		try {
			attsResult = commonDatatManager.getAttributesLanguageData();
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P4000");// 
		}

		if(attsResult != null && !attsResult.getStatuscode().equals("0")){
			resultDTO.setStatuscode(attsResult.getStatuscode());
			resultDTO.setStatusmessage(attsResult.getStatusmessage());
			return resultDTO;
		}

		try {
			langsResult = commonDatatManager.getLaguages();
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P4000");// modulo comercial no disp.
		}

		if(langsResult != null && !langsResult.getStatuscode().equals("0")){
			resultDTO.setStatuscode(langsResult.getStatuscode());
			resultDTO.setStatusmessage(langsResult.getStatusmessage());
			return resultDTO;
		}


		String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_attribute_laguages_title", locale);
		if(downloadname == null || downloadname.isEmpty()){
			downloadname = "Idioma de Atributos";
		}
		downloadname += FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +"."+ fileformat;
		String realname = "IDIOMA_ATRIBUTO" +  FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +"."+fileformat;

		//		String downloadname = downloadname + "_"+ dateFormat.format(date)+".xlsx";
		//		String realname = downloadname+ "_" + dateFormat.format(date) + "_" + uskey + ".xlsx";

		// Crear el Libro y la Hoja usando POI
		XSSFWorkbook workbook = new XSSFWorkbook();

		// HOJA
		String sheetname =  I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_attribute_laguages_title", locale);
		XSSFSheet sheet = workbook.createSheet(sheetname);
		XSSFSheet valuesheet = workbook.createSheet("VALORES");
		XSSFDataValidationHelper validationHelper = new XSSFDataValidationHelper(sheet);

		//ESTILOS
		CellStyle headercs = FEPUtils.getInstance().getHeaderCellStyle(workbook);
		CellStyle stringscs = FEPUtils.getInstance().getValueCellStyle(workbook);

		// FILA 0  encabezado 		
		Row headrow = sheet.createRow(0);			
		int attribute_column = 0;
		int language_code_colum = 1;
		int vendor_code_colum = 2;
		int user_help_colum = 3;

		String attribute_column_name 		= I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attribute", locale); //"C�digo Cobro";
		String language_code_colum_name 	= I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "language_code", locale); //"Capacidad Total";
		String vendor_code_colum_name 		= I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attribute_vendor_code", locale);//"Cadena";		
		String user_help_colum_name 		= I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attribute_user_help", locale);//"C�d. Tienda";		

		try {
			//Atributo
			Cell cell = headrow.createCell(attribute_column);	
			cell.setCellValue(attribute_column_name);				cell.setCellStyle(headercs);
			//language_code_colum
			cell = headrow.createCell(language_code_colum);	
			cell.setCellValue(language_code_colum_name);			cell.setCellStyle(headercs);
			//vendor_code_colum
			cell = headrow.createCell(vendor_code_colum);	
			cell.setCellValue(vendor_code_colum_name);				cell.setCellStyle(headercs);
			//user_help_colum_name
			cell = headrow.createCell(user_help_colum);	
			cell.setCellValue(user_help_colum_name);				cell.setCellStyle(headercs);


			//Crear datos de Selectores.
			saveLanguagesToSheet(sheet, valuesheet, language_code_colum, validationHelper,langsResult.getValues());

			//Salvar los datos de los recursos.
			int datarowIndex = 1;
			for (AttributeLanguageDataDTO attdata : attsResult.getValues() ){
				Row row = sheet.createRow(datarowIndex);	

				//attribute_column
				cell = row.createCell(attribute_column);	
				cell.setCellValue(attdata.getInternalname());		 cell.setCellStyle(stringscs);
				//language_code_colum
				cell = row.createCell(language_code_colum);	
				cell.setCellValue(attdata.getLanguagedescription()); cell.setCellStyle(stringscs);
				//vendor_code_colum
				cell = row.createCell(vendor_code_colum);	
				cell.setCellValue(attdata.getVendorname());			cell.setCellStyle(stringscs);
				//user_help_colum
				cell = row.createCell(user_help_colum);	
				cell.setCellValue(attdata.getUserhelp());			cell.setCellStyle(stringscs);

				datarowIndex++;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P300");
		}


		// Crear Archivo xls	
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(PortalConstants.getInstance().getFileTransferPath() + realname);
			workbook.write(fileOut);
			workbook.setSheetHidden(1, true);

		} catch (IOException e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P300");
		}
		finally{
			if(fileOut != null){
				try {
					fileOut.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		result.setDownloadfilename(downloadname);
		result.setRealfilename(realname);

		return result;
	}



	@Override
	public ErrorInfoArrayResultDTO uploadAttributesLanguageData(String filename, Long uskey, Locale locale) {
		ErrorInfoArrayResultDTO result = new ErrorInfoArrayResultDTO();
		ArrayList<ErrorInfoDTO> errors = new ArrayList<>();

		String file = PortalConstants.getInstance().getFileUploadPath() + uskey + "/" + filename;
		String extension = filename.substring(filename.lastIndexOf('.') + 1);


		LanguageArrayResultDTO langsResult = null;
		HashMap<String, LanguageDTO> languageMap = new HashMap<>();
		try {
			langsResult  = commonDatatManager.getLaguages();
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");// modulo comercial no disp.
		}

		if(langsResult != null && !langsResult.getStatuscode().equals("0")){
			result.setStatuscode(langsResult.getStatuscode());
			result.setStatusmessage(langsResult.getStatusmessage());
			return result;
		}
		else{
			for(LanguageDTO language : langsResult.getValues()){
				languageMap.put(language.getDescription(), language);
			}
		}			


		InputStream inp = null;
		Workbook wb1 = null;

		// EXCEL
		try {
			inp = new FileInputStream(file);

			if (extension.equalsIgnoreCase("xlsx")) {
				wb1 = new XSSFWorkbook(inp);
			} else if (extension.equalsIgnoreCase("xls")) {
				POIFSFileSystem fs = new POIFSFileSystem(inp);
				wb1 = new HSSFWorkbook(fs);
			}
		} catch (IOException e) {
			e.printStackTrace();
			PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");
		} catch (Exception e) {
			e.printStackTrace();
			PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");
		}

		int attribute_column = 0;
		int language_code_colum = 1;
		int vendor_code_colum = 2;
		int user_help_colum = 3;

		String attribute_column_name 		= I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attribute", locale); //"C�digo Cobro";
		String language_code_colum_name 	= I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "language_code", locale); //"Capacidad Total";
		String vendor_code_colum_name 		= I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attribute_vendor_code", locale);//"Cadena";		

		Sheet sheet = null;
		String error = "";

		try {
			// Get the first sheet
			sheet = wb1.getSheetAt(0);
			int headerRowindex = 0;

			// Se buscan las filas en blanco
			List<Integer> rowindexList = new ArrayList<Integer>();
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// Validando fila en blanco
				if ( (row.getCell(attribute_column) == null || row.getCell(attribute_column).toString().trim().length() == 0) &&
						(row.getCell(language_code_colum) == null || row.getCell(language_code_colum).toString().trim().length()  == 0 )				 
						){
					rowindexList.add(row.getRowNum());
				}
			}

			// Se eliminan aquellas filas en blanco
			for (Iterator<Integer> iterator = rowindexList.iterator(); iterator.hasNext();) {
				Integer rowindex = iterator.next();
				Row row = sheet.getRow(rowindex);
				sheet.removeRow(row);
			}

			// VALIDA SI EL ARCHIVO ESTA VACIO
			if (sheet.getPhysicalNumberOfRows() == 0) {
				return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P10001");			
			}

			/**** Validaciones ****/
			// Formato del archivo. Que vengan las columnas en su orden
			if( !sheet.getRow(headerRowindex).getCell(attribute_column).toString().equals(attribute_column_name)){
				error = "El encabezado de la columna " + (attribute_column + 1) + " debe decir " + attribute_column_name;
				errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));
			}		
			if( !sheet.getRow(headerRowindex).getCell(language_code_colum).toString().equals(language_code_colum_name)){
				error = "El encabezado de la columna " + (language_code_colum + 1) + " debe decir " + language_code_colum_name;
				errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));
			}

			if( !sheet.getRow(headerRowindex).getCell(vendor_code_colum).toString().equals(vendor_code_colum_name)){
				error = "El encabezado de la columna " + (vendor_code_colum + 1) + " debe decir " + vendor_code_colum_name;
				errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));
			}



		} catch (Exception e) {
			e.printStackTrace();			
			PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");
		}

		if (errors.size() > 0) {
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");			
		}


		ArrayList<AttributeLanguageDataDTO> att_list = new ArrayList<>();
		int firstRowData = 1;
		try {
			ArrayList<ErrorInfoDTO> row_errors = new ArrayList<>();
			for (int j = firstRowData; j <= sheet.getLastRowNum(); j++) {
				row_errors.clear();

				Row row = sheet.getRow(j);
				if (row == null)
					continue;

				//C�digo Atributo
				String attribute_internalname = null;
				try {
					attribute_internalname = row.getCell(attribute_column).getStringCellValue();
				} catch (Exception e) {
					;
				}

				if(attribute_internalname == null || attribute_internalname.isEmpty()){
					error = "Se requiere entrar un dato v�lido en columna "  +  attribute_column_name  +  " Fila: " + (j+1);
					row_errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));
				}


				//Codigo de Idioma
				String languagedescription = null;
				LanguageDTO language =null;
				try {
					languagedescription = row.getCell(language_code_colum).getStringCellValue();
				} catch (Exception e) {
					;
				}
				if(languagedescription == null || languagedescription.isEmpty()){
					error = "Se requiere entrar un dato v�lido en columna "  +  language_code_colum_name  +  " Fila: " + (j+1);
					row_errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));
				}
				else{
					// buscar si es un idioma valido
					if(languageMap.containsKey(languagedescription)){
						language = languageMap.get(languagedescription);
					}
					else{
						error = "Se requiere entrar un dato v�lido en columna "  +  language_code_colum_name  +  " Fila: " + (j+1);
						row_errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));
					}
				}



				String vendor_code = null;
				try {
					vendor_code = row.getCell(vendor_code_colum).getStringCellValue();
				} catch (Exception e) {
					error = "Se requiere entrar un dato v�lido en columna "  +  vendor_code_colum_name  +  " Fila: " + (j+1);
					row_errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));
				}

				String user_help = null;
				try {
					user_help = row.getCell(user_help_colum).getStringCellValue();
				} catch (Exception e) {
					user_help =""; // Opcional no generar error
				}


				if(row_errors.size() > 0){
					errors.addAll(row_errors);
					continue;
				}

				AttributeLanguageDataDTO att = new AttributeLanguageDataDTO();
				att.setInternalname(attribute_internalname);
				att.setUserhelp(user_help);
				att.setVendorname(vendor_code);
				att.setLanguageid(language.getId());
				att.setLanguagedescription(language.getDescription());
				att_list.add(att);

			}

			if (errors.size() > 0) {
				// Ordenar errores
				result.setValues( errors.toArray(new ErrorInfoDTO[errors.size()]) );
				return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");			
			}

			AttributeLanguageDataDTO[] att_arr = att_list.toArray(new AttributeLanguageDataDTO[att_list.size()] );


			//Ahora invocar el servicio para agregar o modificar los idiomas de los atributos			
			ErrorInfoArrayResultDTO addResRes = commonDatatManager.addOrUpdateAttributeLanguageData(att_arr);

			if(addResRes == null ){				
				return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P600");
			}
			else{
				return addResRes;
			}


		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");
		}

	}


	@Override
	public BaseResultDTO removeAttributes(AttributeDTO[] attributes, boolean force, String modifyby) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		try {
			resultDTO = commonDatatManager.removeAttributes(attributes,force,modifyby);

			//Eliminar todos los ficheros asociados a los atributos eliminados.
			if(resultDTO != null && resultDTO.getStatuscode().equals("0")){
				String att_dir_path = FEPUtils.getInstance().getAttributesFilesPath();

				for(AttributeDTO attribute : attributes){
					if(attribute.getFilename() != null && !attribute.getInternalname().isEmpty()){
						//Eliminar fichero asociado
						try {
							File filetoremove = new File(att_dir_path,attribute.getFilename());
							if(filetoremove.exists()){
								filetoremove.delete();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

			return resultDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}



	@Override
	public AttributeResultDTO addorEditAttribute(AttributeDTO attributedto, AttributeConstraintDataDTO[] constraints, AttributeValueDTO[] values, String languagecode, Long userId)
	{
		AttributeResultDTO resultDTO = new AttributeResultDTO();
		FileObjectInitParamDTO initparam = new FileObjectInitParamDTO();
		FileObjectDataDTO fileobjectdata = new FileObjectDataDTO();
		FileObjectResultDTO fileobjetresult = new FileObjectResultDTO();

		try {

			String filename = attributedto.getTempfilename();
			if(filename != null && !filename.isEmpty()){
				fileobjectdata.setArchivetypeid(FEPConstants.IMAGE_FILE);
				fileobjectdata.setDescription(filename);
				fileobjectdata.setFiletypecode("IMAGE");
				//fileobjectdata.setName(PortalConstants.INTERNAL_FILES_ATTRIBUTE_IMAGES +"/"+ attributedto.getFilename());
				fileobjectdata.setName(filename);

				fileobjectdata.setOwnercode(PortalConstants.INTERNAL_FILES);
				fileobjectdata.setOwnerdescription("Ayuda de atributo " + attributedto.getInternalname());
				fileobjectdata.setCheckfileerror(true);

				initparam.setFileObject(fileobjectdata);


				fileobjetresult = fileStorageManager.addOrUpdateFile(initparam, userId);

				if (fileobjetresult != null && fileobjetresult.getValue()!= null && fileobjetresult.getValue().getUri() != null){
					attributedto.setFilename(fileobjetresult.getValue().getUri());
				}
				else{
					resultDTO.setStatuscode(fileobjetresult.getStatuscode()); 
					resultDTO.setStatusmessage(fileobjetresult.getStatusmessage());
					return resultDTO;
				}

			}
			return commonDatatManager.addorEditAttribute( attributedto,  constraints,  values,  languagecode);

		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	/**
	 * Guarda los valores de idiomas en una hoja de valores y crea un nombre para poder crear dropdowns en el excel
	 * @param sheet  Hola de Datos principal	
	 * @param valuesheet Hoja de datos donde se colocar�n los valores
	 * @param v_index	 indice que se usar� para dos cosas, es la columna donde se crean los selectores y a la vez es el numero de la fila donde se guardaran en la hoja de valores
	 * @param validationHelper  Helper para crear validaciones en la hoja
	 * @return la hoja principal
	 */
	private XSSFSheet saveLanguagesToSheet(XSSFSheet sheet, XSSFSheet valuesheet,  int v_index, XSSFDataValidationHelper validationHelper, LanguageDTO[] langagues) {
		//Crear los valores para los Selectores de Cadena en la hoja de valores
		try {
			if(langagues != null &&  langagues.length >0){
				Row valuerow = valuesheet.createRow(v_index);
				for (int i = 0; i<  langagues.length ; i++){					
					String name =langagues[i].getDescription();
					Cell cell = valuerow.createCell(i);
					cell.setCellValue(name);		
				}

				String lascolumnname = CellReference.convertNumToColString(langagues.length-1);
				// Crear un nombre en el libro asociado a la columna de datos previamente creada
				Name namedCell = valuesheet.getWorkbook().createName();
				namedCell.setNameName("Fila_"+ v_index); // nombre 
				String formulaStr = valuesheet.getSheetName() + "!$A$" + (v_index+1) + ":$" + lascolumnname +  "$" + (v_index+1);
				namedCell.setRefersToFormula(formulaStr);
				DataValidationConstraint constraint = validationHelper.createFormulaListConstraint("Fila_"+ v_index);

				CellRangeAddressList addressList = new CellRangeAddressList(1, 1000,v_index , v_index);// v_index es el numero de la columna ,, 1000 filas
				DataValidation dataValidation = validationHelper.createValidation(constraint,addressList);
				sheet.addValidationData(dataValidation);
			}

		} catch (Exception e) {
			logger.error("No se pudo obtener tipos de cobro para utilizarlos en la descarga de recursos");
		} 

		return sheet;

	}


	@Override	
	public String formatValueData(String cellvalue, DefinableAttributeDataDTO da, List<ErrorInfoDTO> rowerrors,HashMap<Long, List<AttributeValueDTO> > attSelectedValues, int row, int column,String rowname, String columname,String languagecode) {

		String result = "";
		String error = "";
		String formatedCellvalue = "";

		if(cellvalue != null && !cellvalue.isEmpty() && da != null ){

			// Si el atributo es de tipo Booleano
			if (da.getAtributedatatypeinternalname().equals("Boolean")){

				boolean bvaltrue = cellvalue.trim().equalsIgnoreCase("true") || cellvalue.trim().equalsIgnoreCase("si") || cellvalue.trim().equalsIgnoreCase("sí");
				boolean bvalfalse = cellvalue.trim().equalsIgnoreCase("false") || cellvalue.trim().equalsIgnoreCase("no");

				if (bvaltrue || bvalfalse)
				{
					return bvaltrue ? "SI" : "NO"; // cel.getStringCellValue().trim();
				}
				else if (cellvalue.equals("")){
					return formatedCellvalue;
				} else {
					//					error = "Solicitud: " + rowname + " - " + "El valor no es válido para parámetros de verdadero o falso " + da.getAttributeinternalname() + " del tipo: " + da.getAttributetypename()
					//					+ " (Fila " + row + "," + "Columna " + column + ")";
					error = "El valor no es válido para parámetros de verdadero o falso del tipo " + da.getAttributetypename();
					FEPUtils.addErrorResult(rowerrors, row, column, rowname, da.getAttributevendorname(), error);
					return null;
				}

			}

			//Si el atributo es de tipo lista. se busca la lista correspondiente al atributo y se formatea
			AttributeValueDTO[] values = null;
			boolean isaList = false;

			//Mapas para validaciones de c�digos
			HashMap<String, String> codes_map  = new  HashMap<String, String>();
			HashSet<String> name_codes_set 	   = new  HashSet<String>();
			HashMap<String,AttributeValueDTO> all_attvalues_map	   = new  HashMap<String,AttributeValueDTO>();

			if (FEPUtils.attributeIsValueList(da.getAtributedatatypeinternalname())){
				isaList = true;
				values = da.getAttributeinfo() != null ? da.getAttributeinfo().getValues() : null;
				if(values == null){
					AttributeValuesResultDTO attvaluesres = commonDatatManager.getAttributeValues(da.getAttributeid(),languagecode);
					if (attvaluesres != null && attvaluesres.getStatuscode().equals("0") && attvaluesres.getValues() != null){
						values = attvaluesres.getValues();
					}
				}	

				if(values == null || values.length <1 ){
					//String error = "No se pudo obtener los valores asociados al atributo: " + da.getAttributeinternalname() + " del tipo: " + da.getAttributetypename()  ;
					//					error = "SKU: " + rowname + " - " + "No se pudo obtener los valores asociados al atributo: " + da.getAttributeinternalname() + " del tipo: " + da.getAttributetypename()
					//					+ " (Fila " + row + "," + "Columna " + column + ")";
					error = "No se pudo obtener los valores asociados al atributo del tipo " + da.getAttributetypename();
					FEPUtils.addErrorResult(rowerrors, row, column, rowname, da.getAttributevendorname(), error);
					return null;
				}
			}			
			else{
				isaList = false;
				result = cellvalue;// NO se formatea.
				return result;
			}

			if(isaList ){
				// Hacer un mapa con los valores de las listas y sus codigos
				for (AttributeValueDTO value : values){
					name_codes_set.add(value.getValue());
					String codevalue =  FEPUtils.getCodeFromCodedValue(value.getValue(),FEPConstants.LIST_SEPARATOR);
					codes_map.put(codevalue, value.getValue());
					all_attvalues_map.put(value.getValue(), value);
				}	

				//Como es una lista, los valores estaran separados por el separados por el LIST_SEPARATOR por lo que es necesario separarlos para validarlos uno a uno
				String [] cellvalues = cellvalue != null && !cellvalue.isEmpty() ? cellvalue.split(FEPConstants.LIST_SEPARATOR) : null;

				if(cellvalues == null || cellvalues.length < 1){				
					cellvalues = new String[1];
					cellvalues[0] = cellvalue;
				}


				for(String simplevalue : cellvalues){
					//Empezar las validaciones en funcion del tipo de dato
					boolean contain_all = name_codes_set.contains(simplevalue);
					boolean contain_code = simplevalue != null && !simplevalue.isEmpty() && codes_map.containsKey(simplevalue);

					if(contain_code || contain_all ){
						String full_name_code = "";
						if(contain_code){
							full_name_code = codes_map.get(simplevalue);
						}
						if(contain_all){
							full_name_code = simplevalue;
						}	

						//Acá se agrega a la lista de valores seleccionados para luego poder hacer una validacion de relaciones.
						AttributeValueDTO contained_value = all_attvalues_map.get(full_name_code);
						if(contained_value != null){
							//Adicionar al set de valores seleccionados
							List<AttributeValueDTO> tmp_setattvalue = attSelectedValues.get(contained_value.getAttributeid());
							if(tmp_setattvalue == null){
								tmp_setattvalue = new ArrayList<>();					
							}
							//Si no existe este valor se agrega
							tmp_setattvalue.add(contained_value);
							attSelectedValues.put(contained_value.getAttributeid(), tmp_setattvalue);							
						}

						//En caso de contenerlo formatear el nuevo valor 
						if(formatedCellvalue.isEmpty()){
							formatedCellvalue =  full_name_code != null && !full_name_code.isEmpty() ? full_name_code : formatedCellvalue;
						}
						else{
							String nv = full_name_code != null && !full_name_code.isEmpty() ? full_name_code : "";
							formatedCellvalue +=  FEPConstants.LIST_SEPARATOR  + nv ;
						}	


					}

					if (! (contain_all || contain_code)){
						//						error = "SKU: " + rowname + " - " + "Valor inválido. " +  simplevalue + " Debe estar contenido en la lista del atributo: " +  da.getAttributeinternalname() 
						//						+ " (Fila " + row + "," + " Columna " + column + ")";
						error = "Valor inválido. " +  simplevalue + " Debe estar contenido en la lista del atributo";
						FEPUtils.addErrorResult(rowerrors, row, column, rowname, da.getAttributevendorname(), error);
						return null;
					}		
				}				
				result = formatedCellvalue;						
			}			
		}		
		else{
			return result;
		}		

		return result;

	}

	/*
	 * Valida que los atributos relacionados estén correctamente pero a partir de valores cadena es decir no se tiene el valor del atributo como tal sino el texto 
	 * Esto requiere un proceso más pesado ya que es necesario buscar los atributos dependientes y primero buscar sus ids para poder usar getAttributeRelatedValues
	 */
	@Override
	public boolean validateAttributeRelations(LinkedHashMap<String,DefinableAttributeDataDTO> defattMap, List<ErrorInfoDTO> rowerrors, List<ItemAttributeValueDTO> values,HashMap<Long, List<AttributeValueDTO> > attSelectedValues, int row, String languagecode){
		boolean result = false;

		if(values != null && values.size() > 0){
			ItemAttributeValueDTO[] valueArr = new ItemAttributeValueDTO[values.size()];
			valueArr = values.toArray(valueArr);
			//Inicialmente barrer todos los atributos y buscar los que tienen relacion
			for(ItemAttributeValueDTO itemAttValue : values){
				DefinableAttributeDataDTO defatt = defattMap.get(itemAttValue.getAttributeinternalname());
				//Si es un atributo dependiente buscar sus valores relacionados
				if(defatt != null && defatt.getAttributeinfo() != null && defatt.getAttributeinfo().getRelatedattributes() != null && defatt.getAttributeinfo().getRelatedattributes().length>0 ){				
					AttributeValuesResultDTO attvalues = this.getAttributeValuesByItemValues(defatt.getAttributeid(), valueArr, languagecode);
					//Hacer un mapa desde estos valores para verificar si el valor configurado está dentro de los posibles.
					HashSet<String> posiblevalues = new HashSet<>();
					if(attvalues != null && attvalues.getStatuscode().equals("0") && attvalues.getValues() != null && attvalues.getValues().length>0){					
						Arrays.stream(attvalues.getValues()).forEach(value->posiblevalues.add(value.getValue()));	

						if(!posiblevalues.contains(itemAttValue.getValue())){
							String error = "Valor inválido. " +  itemAttValue.getValue() + " Debe estar contenido en la lista relacionada del atributo: " +  defatt.getAttributevendorname();
							FEPUtils.addErrorResult(rowerrors, row, -1, error);	
						}	
					}
					else if (attvalues == null || !attvalues.getStatuscode().equals("0") || (defatt.getAttributeinfo().getRelatedattributes() != null && defatt.getAttributeinfo().getRelatedattributes().length<1)){
						String error = "No se pudo obtener los valores relacionados al atributo: " + defatt.getAttributevendorname();
						FEPUtils.addErrorResult(rowerrors, row, -1, error);
					}
				}

			}
		}

		return result;

	}

	@Override
	public FileDownloadInfoResultDTO downloadAttributesFileBase(Long uskey, Locale locale) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		UserDataTypeArrayResultDTO userdatatypes = null;
		AttributeTypeArrayResultDTO attrtypes = null;
		ClientArrayResultDTO clients = null;

		String languagecode = locale.getLanguage();

		//Se obtienen los tipos de datos del usuario para el idioma local definido
		try {
			userdatatypes = commonDatatManager.getUserDataTypes(languagecode, false);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}

		if(userdatatypes != null && !userdatatypes.getStatuscode().equals("0")){
			resultDTO.setStatuscode(userdatatypes.getStatuscode());
			resultDTO.setStatusmessage(userdatatypes.getStatusmessage());
			return resultDTO;
		}

		//Se obtienen los tipos de atributo (Grupo al que pertenece el atributo) para el idioma local definido
		try {
			attrtypes = commonDatatManager.getAttributeTypes(languagecode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");
		}

		if(attrtypes != null && !attrtypes.getStatuscode().equals("0")){
			resultDTO.setStatuscode(attrtypes.getStatuscode());
			resultDTO.setStatusmessage(attrtypes.getStatusmessage());
			return resultDTO;
		}

		//Obtener los nombre de los clientes
		try {
			clients = this.getAllClients(null);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");
		}

		if(clients != null && !clients.getStatuscode().equals("0")){
			resultDTO.setStatuscode(clients.getStatuscode());
			resultDTO.setStatusmessage(clients.getStatusmessage());
			return resultDTO;
		}

		String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_title_base_attributes", locale);
		if( downloadname == null || downloadname.isEmpty() || downloadname.equals("M/R") ){
			downloadname = "Nuevos Atributos_";
		}
		downloadname += FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +".xlsx";
		String realname = "NUEVOS ATRIBUTOS_" +  FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +".xlsx";

		// Crear el Libro y la Hoja usando POI
		XSSFWorkbook workbook = new XSSFWorkbook();

		// HOJA
		String sheetname =  "Nuevos Atributos";
		XSSFSheet sheet = workbook.createSheet(sheetname);
		XSSFSheet valuesheet = workbook.createSheet("VALORES");
		XSSFDataValidationHelper validationHelper = new XSSFDataValidationHelper(sheet);

		//ESTILOS
		CellStyle headercs = FEPUtils.getInstance().getHeaderCellStyle(workbook);
		CellStyle stringscs = FEPUtils.getInstance().getValueCellStyle(workbook);

		// FILA 0  encabezado 		
		Row headrow = sheet.createRow(0);			
		int internal_name_column = 0;
		int data_type_column = 1;
		int attribute_type_column = 2;
		int isactive_column = 3;
		int vendor_name_column = 4;
		int user_help_column = 5;
		int client_name_column = 6;

		String internal_name_column_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_internal_name", locale); //"Nombre B2B del atributo";
		String data_type_column_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_data_type", locale); //"Tipo de dato";
		String attribute_type_column_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_type", locale);//"Grupo al que pertenece";	
		String isactive_column_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_active", locale);//"Si es Activo o no"; 
		String vendor_name_column_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_vendor_name", locale);//"Glosa Proveedor"; 
		String user_help_column_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_user_help", locale);//"Ayuda Proveedor"; 
		String client_name_column_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_client_name", locale);//"Nombre cliente"; 

		try {
			//Nombre B2B
			Cell cell = headrow.createCell(internal_name_column);	
			cell.setCellValue(internal_name_column_name);				cell.setCellStyle(headercs);

			//Tipo de Dato
			cell = headrow.createCell(data_type_column);	
			cell.setCellValue(data_type_column_name);			        cell.setCellStyle(headercs);

			//Tipo de atributo (Grupo)
			cell = headrow.createCell(attribute_type_column);	
			cell.setCellValue(attribute_type_column_name);				cell.setCellStyle(headercs);

			//Activo o no
			cell = headrow.createCell(isactive_column);	
			cell.setCellValue(isactive_column_name);					cell.setCellStyle(headercs);

			//Glosa Proveedor
			cell = headrow.createCell(vendor_name_column);	
			cell.setCellValue(vendor_name_column_name);					cell.setCellStyle(headercs);

			//Ayuda al Proveedor
			cell = headrow.createCell(user_help_column);	
			cell.setCellValue(user_help_column_name);					cell.setCellStyle(headercs);

			//Nombre cliente
			cell = headrow.createCell(client_name_column);	
			cell.setCellValue(client_name_column_name);					cell.setCellStyle(headercs);

			//Crear datos de Selectores.
			FEPUtils.saveUserDataTypesToSheet(sheet, valuesheet, data_type_column, validationHelper, userdatatypes.getValues());
			FEPUtils.saveAttributeTypeToSheet(sheet, valuesheet, attribute_type_column, validationHelper, attrtypes.getValues());
			FEPUtils.saveClientsToSheet(sheet, valuesheet, client_name_column, validationHelper, clients.getValues());
			FEPUtils.saveSimpleBooleanTypeToSheet(sheet, valuesheet, isactive_column, validationHelper);

			//Salvar los datos de los recursos.
			int datarowIndex = 1;
			int i = 0;
			while (i < attrtypes.getValues().length){
				Row row = sheet.createRow(datarowIndex);	

				//Nombre B2B
				cell = row.createCell(internal_name_column);	
				cell.setCellValue("");		cell.setCellStyle(stringscs);

				//Tipo de Dato
				cell = row.createCell(data_type_column);	
				cell.setCellValue("");      cell.setCellStyle(stringscs);

				//Tipo de atributo (Grupo)
				cell = row.createCell(attribute_type_column);	
				cell.setCellValue("");	    cell.setCellStyle(stringscs);

				//Activo o no
				cell = row.createCell(isactive_column);	
				cell.setCellValue("");		cell.setCellStyle(stringscs);

				//Glosa Proveedor
				cell = row.createCell(vendor_name_column);	
				cell.setCellValue("");		cell.setCellStyle(stringscs);

				//Ayuda al Proveedor
				cell = row.createCell(user_help_column);	
				cell.setCellValue("");		cell.setCellStyle(stringscs);

				//Nombre cliente
				cell = row.createCell(client_name_column);	
				cell.setCellValue("");		cell.setCellStyle(stringscs);

				datarowIndex++;

				i++;
			}

			// Crear Archivo xls	
			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(PortalConstants.getInstance().getFileTransferPath() + realname);
				workbook.setSheetHidden(1, true);
				workbook.write(fileOut);

			} catch (IOException e) {
				e.printStackTrace();
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
			}
			finally{
				if(fileOut != null){
					try {
						fileOut.close();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			resultDTO.setDownloadfilename(downloadname);
			resultDTO.setRealfilename(realname);

		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
		}

		return resultDTO;

	}


	@Override
	public AttributeArrayResultDTO uploadNewAttributes(String filename, Long uskey, Locale locale, String moduletype) {
		AttributeArrayResultDTO result = new AttributeArrayResultDTO();
		ArrayList<ErrorInfoDTO> errors = new ArrayList<>();

		String error = "";

		ArrayList<ErrorInfoDTO> row_errors = new ArrayList<>();
		ArrayList<AttributeDTO> newattributeList = new ArrayList<>();

		String file = PortalConstants.getInstance().getFileUploadPath() + uskey + "/" + filename;
		String extension = filename.substring(filename.lastIndexOf('.') + 1);

		String languagecode = locale.getLanguage();

		//Mapa de valores de Tipos de Datos
		HashMap<String, UserDataTypeDTO> userdatatypes_Map = new HashMap<>();

		UserDataTypeArrayResultDTO userdatatypes = commonDatatManager.getUserDataTypes(languagecode, false);

		if(userdatatypes != null && userdatatypes.getStatuscode().equals("0") && userdatatypes.getValues() != null && userdatatypes.getValues().length > 0){
			Arrays.stream(userdatatypes.getValues()) .forEach(usdt-> userdatatypes_Map.put(usdt.getName(),usdt) );
		}
		else{
			//No se detectaron tipos de datos de usuarios, emitir error
			error = "No se encontraron tipos de datos en la base de datos";
			errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P9000", error, false));			
		}

		//Mapa para valores de tipos de atributos
		HashMap<String, AttributeTypeDTO> attributetypes_Map = new HashMap<>();

		AttributeTypeArrayResultDTO attrtypes = commonDatatManager.getAttributeTypes(languagecode);

		if(attrtypes != null && attrtypes.getStatuscode().equals("0") && attrtypes.getValues() != null && attrtypes.getValues().length > 0){
			Arrays.stream(attrtypes.getValues()) .forEach(atttyp-> attributetypes_Map.put(atttyp.getName(),atttyp) );

		}
		else{
			//No se detectaron tipos de datos de usuarios, emitir error
			error = "No se encontraron tipos de datos en la base de datos";
			errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P9000", error, false));			
		}

		//Maoa para valores de clientes
		HashMap<String, ClientDTO> clientsMap = new HashMap<>();

		ClientArrayResultDTO clients = commonDatatManager.getClients(null);

		if (clients != null && clients.getStatuscode().equals("0") && clients.getValues() != null && clients.getValues().length > 0){
			Arrays.stream(clients.getValues()) 
			.forEach(client-> clientsMap.put(client.getInternalname(), client));
		}else{
			error = "No se encontraron datos de clientes en la base de datos";
			errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P9000", error, false));			
		}


		if (errors.size() > 0) {
			result.setErrors( errors.toArray(new ErrorInfoDTO[errors.size()]) );
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P3501");			
		}

		int internal_name_column = 0;
		int data_type_colum = 1;
		int attribute_type_colum = 2;
		int isactive_colum = 3;
		int vendor_name_colum = 4;
		int user_help_colum = 5;	
		int client_name_column = 6;

		String internal_name_column_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_internal_name", locale); //"Nombre B2B del atributo";
		String data_type_colum_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_data_type", locale); //"Tipo de dato";
		String attribute_type_colum_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_type", locale);//"Grupo al que pertenece";	
		String isactive_colum_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_active", locale);//"Si es Activo o no"; 
		String vendor_name_colum_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_vendor_name", locale);//"Glosa Proveedor"; 
		String user_help_colum_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_user_help", locale);//"Ayuda Proveedor"; 
		String client_column_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_client_name", locale);//"Nombre Cliente"; 

		//Para el manejo de las cabeceras
		List<String> headerrowlist = new ArrayList<String>(
				Arrays.asList(internal_name_column_name, data_type_colum_name, attribute_type_colum_name, isactive_colum_name, vendor_name_colum_name,
						user_help_colum_name, client_column_name) 
				);

		InputStream inp = null;
		Workbook wb1 = null;

		// EXCEL
		try {
			inp = new FileInputStream(file);

			if (extension.equalsIgnoreCase("xlsx")) {
				wb1 = new XSSFWorkbook(inp);
			} else if (extension.equalsIgnoreCase("xls")) {
				POIFSFileSystem fs = new POIFSFileSystem(inp);
				wb1 = new HSSFWorkbook(fs);
			}
		} catch (IOException e) {
			e.printStackTrace();
			PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");
		} catch (Exception e) {
			e.printStackTrace();
			PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");
		}

		Sheet sheet = null;

		try {
			// Get the first sheet
			sheet = wb1.getSheetAt(0);
			int headerRowindex = 0;

			//Buscar las columnas definidas

			Row header_row = sheet.getRow(headerRowindex);

			// Se buscan las filas en blanco
			/*List<Integer> rowindexList = new ArrayList<Integer>();
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// Validando fila en blanco
				if ( row.getCell(0).toString().isEmpty() ){
					rowindexList.add(row.getRowNum());
				}
			}

			// Se eliminan aquellas filas en blanco
			for (Iterator<Integer> iterator = rowindexList.iterator(); iterator.hasNext();) {
				Integer rowindex = iterator.next();
				Row row = sheet.getRow(rowindex);
				sheet.removeRow(row);
			}*/

			// VALIDA SI EL ARCHIVO ESTA VACIO
			if (sheet.getPhysicalNumberOfRows() == 0) {
				return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P10001");			
			}

			//Validar encabezados
			for (int columnindex = 0; columnindex < headerrowlist.size(); columnindex++){
				String cellname = headerrowlist.get(columnindex);

				if (header_row.getCell(columnindex) == null ||
						!header_row.getCell(columnindex).toString().trim().equals(cellname) ){
					error = "El valor del encabezado " + " - " + "(Fila " + headerRowindex + " Columna " + columnindex + ")" + " ,no es válido " + " - Valor esperado: " + cellname;
					FEPUtils.addErrorResult(row_errors, headerRowindex, columnindex, error);
				}
			}

			if (row_errors != null && row_errors.size() > 0){
				result.setErrors( row_errors.toArray(new ErrorInfoDTO[row_errors.size()]) );
				return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P10003");
			}

			int firstRowData = 1;

			//Barrer todas las filas
			for (int j = firstRowData; j <= sheet.getLastRowNum(); j++) {
				row_errors.clear();				
				Row row = sheet.getRow(j);
				if (row == null)
					continue;

				String internalname = "";
				String datatype = "";
				String attributetype = "";
				String isactive = "";
				String vendorname = "";
				String userhelp = "";
				String clientname = "";

				Cell cell = null;

				//Internalname
				try
				{
					cell = row.getCell(internal_name_column);
					internalname = FEPUtils.getValueData("String", cell, cell.getCellType());

					if (internalname.isEmpty()){
						error = "Falta dato obligatorio - Fila " + (j) + " Columna: " + internal_name_column_name;
						FEPUtils.addErrorResult(row_errors, j, internal_name_column, error);
					}
				}
				catch (AccessDeniedException e)
				{
					error = "Dato inválido - Fila " + (j) + " Columna: " + internal_name_column_name;
					FEPUtils.addErrorResult(row_errors, j, internal_name_column, error);
				}

				//Tipo de dato
				try
				{
					cell = row.getCell(data_type_colum);
					datatype =  FEPUtils.getValueData("String", cell,cell.getCellType());

					if (!userdatatypes_Map.containsKey(datatype) ){
						error = "Tipo de dato de atributo no existe - Fila " + (j) + " Columna: "  + data_type_colum_name;
						FEPUtils.addErrorResult(row_errors, j, data_type_colum, error);
					}
				}
				catch (AccessDeniedException e)
				{
					error = "Dato inválido - Fila " + (j) + " Columna: "  + data_type_colum_name;
					FEPUtils.addErrorResult(row_errors, j, data_type_colum, error);
				}

				//Tipo de Atributo
				try
				{
					cell = row.getCell(attribute_type_colum);
					attributetype =  FEPUtils.getValueData("String", cell,cell.getCellType());

					if ( !attributetypes_Map.containsKey(attributetype)){
						error = "Tipo de atributo no existe - Fila " + (j) + " Columna: "  + attribute_type_colum_name;
						FEPUtils.addErrorResult(row_errors, j, attribute_type_colum, error);
					}
				}
				catch (AccessDeniedException e)
				{
					error = "Dato inválido - Fila " + (j) + " Columna: "  + attribute_type_colum_name;
					FEPUtils.addErrorResult(row_errors, j, attribute_type_colum, error);
				}

				//Is Active
				try
				{
					cell = row.getCell(isactive_colum);
					isactive =  FEPUtils.getValueData("Boolean", cell,cell.getCellType());

					if (isactive.equalsIgnoreCase("Si") && isactive.equalsIgnoreCase("Sí") && isactive.equalsIgnoreCase("No") ){
						error = "Dato inválido - Fila " + (j) + " Columna: "  + isactive_colum_name;
						FEPUtils.addErrorResult(row_errors, j, isactive_colum, error);
					}
				}
				catch (AccessDeniedException e)
				{
					error = "Dato inválido - Fila " + (j) + " Columna: "  + isactive_colum_name;
					FEPUtils.addErrorResult(row_errors, j, isactive_colum, error);
				}

				//Vendorname
				try
				{
					cell = row.getCell(vendor_name_colum);
					vendorname =  FEPUtils.getValueData("String", cell,cell.getCellType());

					if (vendorname.isEmpty()){
						error = "Falta dato obligatorio - Fila " + (j) + " Columna: "  + vendor_name_colum_name;
						FEPUtils.addErrorResult(row_errors, j, vendor_name_colum, error);
					}

				}
				catch (AccessDeniedException e)
				{
					error = "Dato inválido - Fila " + (j) + " Columna: "  + vendor_name_colum_name;
					FEPUtils.addErrorResult(row_errors, j, vendor_name_colum, error);
				}

				//Userhelp
				try
				{
					cell = row.getCell(user_help_colum);
					userhelp =  FEPUtils.getValueData("String", cell,cell.getCellType());
				}
				catch (AccessDeniedException e)
				{
					error = "Dato inválido - Fila " + (j) + " Columna: "  + user_help_colum_name;
					FEPUtils.addErrorResult(row_errors, j, user_help_colum, error);
				}

				//ClientName
				try
				{
					cell = row.getCell(client_name_column);
					clientname =  FEPUtils.getValueData("String", cell,cell.getCellType());

					if (!clientsMap.containsKey(clientname)){
						error = "Tipo de cliente no existe - Fila " + (j) + " Columna: "  + client_column_name;
						FEPUtils.addErrorResult(row_errors, j, client_name_column, error);
					}
				}
				catch (AccessDeniedException e)
				{
					error = "Dato inválido - Fila " + (j) + " Columna: "  + client_column_name;
					FEPUtils.addErrorResult(row_errors, j, client_name_column, error);
				}

				//Si hay errores, se pasa a la siguiente fila sin guardar los datos de este atributo
				if (row_errors != null && row_errors.size() > 0) {
					errors.addAll(row_errors);
					continue;			
				}

				//Crear nuevo atributo
				AttributeDTO attributedto = new AttributeDTO();
				attributedto.setInternalname(internalname);
				attributedto.setDatatypeid(userdatatypes_Map.get(datatype).getId());
				attributedto.setAttributetypeid(attributetypes_Map.get(attributetype).getId());
				attributedto.setActive((isactive != "" && (isactive.equalsIgnoreCase("si") || isactive.equalsIgnoreCase("sí"))) ? true : false);
				attributedto.setVendorname(vendorname);
				attributedto.setUserhelp(userhelp);
				attributedto.setAttmoduletypeinternalname(moduletype);
				attributedto.setClientinternalname(clientname);

				newattributeList.add(attributedto);
			}

			if (errors != null && errors.size() > 0) {
				result.setErrors( errors.toArray(new ErrorInfoDTO[errors.size()]) );
				return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P3501");			
			}else{
				//Invocar al modulo para crear los nuevos atributos
				AttributeDTO[] newAttributeArr = new AttributeDTO[newattributeList.size()];
				newAttributeArr = newattributeList.toArray(newAttributeArr);
				ArrayList<ErrorInfoDTO> newattr_errors = new ArrayList<>();

				for (AttributeDTO dto: newattributeList){
					AttributeResultDTO newAttRes = commonDatatManager.addorEditAttribute(dto, null, null, languagecode);
					if(newAttRes != null && !newAttRes.getStatuscode().equals("0") ){
						error = "Problemas al crear el atributo: " + dto.getInternalname() + " - " + newAttRes.getStatusmessage();
						newattr_errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));
						continue;
					}
				}

				if (newattr_errors.size() > 0){
					errors.addAll(newattr_errors);
					result.setErrors( errors.toArray(new ErrorInfoDTO[errors.size()]) );
				}
				else {
					result.setValues(newAttributeArr);					
				}
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");
		}
	}


	@Override
	public ProviderArrayResultDTO getProviders() {
		ProviderArrayResultDTO result = new ProviderArrayResultDTO();

		try {
			return commonDatatManager.getProviders();
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");
		}
	}


	@Override
	public ProviderArrayResultDTO getProviderByCodes(String[] pvcodes) {
		ProviderArrayResultDTO result = new ProviderArrayResultDTO();

		try {
			return commonDatatManager.getProviderByCodes(pvcodes);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");
		}
	}


	@Override
	public UserTypeArrayResultDTO getUserTypes() {
		UserTypeArrayResultDTO result = new UserTypeArrayResultDTO();

		try {
			return commonDatatManager.getUserTypes();
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");
		}
	}

	@Override
	public ClientArrayResultDTO getAllClients(ClientInitParamDTO initparam) {
		ClientArrayResultDTO resultDTO = new ClientArrayResultDTO ();
		try {
			return commonDatatManager.getClients(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo fep no disp.
		}
	}

	@Override
	public UserTypeArrayResultDTO getRolesByProviderWorkFlow(String providercode) {
		UserTypeArrayResultDTO result = new UserTypeArrayResultDTO();

		try {
			return commonDatatManager.getRolesByProviderWorkFlow(providercode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");// modulo fep no disp.
		}

	}

	@Override
	public ProviderWorkFlowArrayResultDTO getProviderWorkFlows() {
		ProviderWorkFlowArrayResultDTO result = new ProviderWorkFlowArrayResultDTO();
		try {
			return commonDatatManager.getProviderWorkFlows();
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");// modulo fep no disp.
		}
	}

	@Override
	public UserTypeArrayResultDTO getRolesByUserProfiles(String profiles) {
		UserTypeArrayResultDTO result = new UserTypeArrayResultDTO();
		try {
			return commonDatatManager.getRolesByUserProfiles(profiles);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");// modulo fep no disp.
		}
	}

	@Override
	public ErrorInfoArrayResultDTO addOrEditStandardArticleTypes(StandardArticleTypeDTO[] standardarticletypes) {
		ErrorInfoArrayResultDTO result = new ErrorInfoArrayResultDTO();
		try {
			return commonDatatManager.addOrEditStandardArticleTypes(standardarticletypes);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");
		}
	}

	@Override
	public BaseResultDTO addOrUpdateStandardArticleType(StandardArticleTypeDTO standardArticleType) {
		BaseResultDTO result = new BaseResultDTO();
		try {
			return commonDatatManager.addOrUpdateStandardArticleType(standardArticleType);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");
		}
	}

	@Override
	public AttributeArrayResultDTO getAttributesByFilter(ArticleTypeByClientInitParamDTO initparam) {
		AttributeArrayResultDTO result = new AttributeArrayResultDTO();
		try {
			return commonDatatManager.getAttributesByFilter(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");
		}
	}

	@Override
	public StandardArticleTypeArrayResultDTO getStandardizedAttributes(ArticleTypeByClientInitParamDTO initparam) {
		StandardArticleTypeArrayResultDTO result = new StandardArticleTypeArrayResultDTO();
		try {
			return commonDatatManager.getStandardizedAttributes(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");
		}
	}

	@Override
	public DefinableAttributeArrayResultDTO getDefinableAttributesByArticleType(DefinableAttributeInitParamDTO initparam) {
		DefinableAttributeArrayResultDTO result = new DefinableAttributeArrayResultDTO();
		try {
			return commonDatatManager.getDefinableAttributesByArticleType(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");
		}
	}

	@Override
	public FileDownloadInfoResultDTO downloadStandardizedAttributesList(ArticleTypeByClientInitParamDTO initparam) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		int retailatt_column_num 	= 0;
		int first_data_column 		= 1;

		int header_row_index 		= 0;	// Fila donde se coloca el 
		int first_data_row 			= 1;	//Fila a partir de la cual se colocan los datos

		String retailatt_colum_name = "Atributos Retail";

		//Mapa con la relación Attributo Retail - Atributos B2B (o FEP)
		Map<String, List<String>> allAttributesMap = null;
		HashMap<String, List<String>> sortedAllAttributesMap = new HashMap<> ();

		//lista de columnas en funcion de los atributos FEP homologados
		LinkedHashMap<String , Integer> columnlistMap = new LinkedHashMap<>();

		StandardArticleTypeArrayResultDTO standardAttributes = new StandardArticleTypeArrayResultDTO();

		//LinkedHashMap<Long, ArrayList<StandardArticleTypeResultDTO>> standardizerAttMap = null;

		try
		{
			//Buscar atritbutos homologados
			standardAttributes = getStandardizedAttributes(initparam);
			if (standardAttributes != null && standardAttributes.getValues() != null && standardAttributes.getValues().length > 0){

				allAttributesMap =  Arrays.stream(standardAttributes.getValues())
						.collect(Collectors.groupingBy(StandardArticleTypeResultDTO::getAttributetargetname, 
								Collectors.mapping(StandardArticleTypeResultDTO::getInternalname, Collectors.toList())));
				
				//Ordenar values por nombre retail (key target) de los atributos
				sortedAllAttributesMap = allAttributesMap.entrySet().stream()
		                .sorted(Map.Entry.comparingByKey())
		                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

			}else{
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P11002");
			}

			//Llenar mapa con los datos de las columnas a crear
			int column = 1;
			
			for (Map.Entry<String, List<String>> entry: sortedAllAttributesMap.entrySet()){
				
				for (String attname: entry.getValue() ){

					if (!columnlistMap.containsKey(attname)){
						columnlistMap.put(attname, column);
						column ++;
					}

				}
				
			}

			String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_standard_attributes_title", initparam.getLocale());
			if(downloadname == null || downloadname.isEmpty() || downloadname.equals("M/R")){
				downloadname = "Atributos homologados ";
			}
			downloadname += FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +".xlsx";
			String realname = "ATRIBUTOS HOMOLOGADOS " +  FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +".xlsx";

			// Crear el Libro y las Hojas usando POI
			XSSFWorkbook workbook = new XSSFWorkbook();

			// HOJA Principal
			XSSFSheet sheet = workbook.createSheet("ATRIBUTOS HOMOLOGADOS");

			Row headrow = sheet.createRow(header_row_index);

			//Estilos
			CellStyle headercs = FEPUtils.getInstance().getHeaderCellStyle(workbook);
			CellStyle retailStringcs = FEPUtils.getInstance().getValueCellStyle(workbook);
			CellStyle valuecs = FEPUtils.getInstance().getValueCellStyle(workbook);
			valuecs.setAlignment(CellStyle.ALIGN_CENTER);

			//Encabezado
			headercs.setVerticalAlignment(CellStyle.ALIGN_CENTER);
			Cell cellh = headrow.createCell(retailatt_column_num);				
			cellh.setCellValue(retailatt_colum_name);			
			cellh.setCellStyle(headercs);

			//Crear las columnas dinámicas en base a los atributos homologados		
			Cell cell = null;
			Iterator<String> it = columnlistMap.keySet().iterator();
			int columindex = first_data_column;

			while (it.hasNext()) {
				String columnname = it.next();

				//Header El nombre interno
				cell = headrow.createCell(columindex);
				cell.setCellValue(columnname);
				cell.setCellStyle(headercs);

				columindex++;
			}

			//Al llegar acá todas las columnas están creadas y listos para barrer el listado de attributos retail homologados
			Iterator<String> iter = sortedAllAttributesMap.keySet().iterator();
			int i = 0;
			while (iter.hasNext()){

				String retailattname = iter.next();
				Row row = sheet.createRow(i + first_data_row); // Salta en encabezado

				cell = row.createCell(retailatt_column_num);
				cell.setCellValue(retailattname);
				cell.setCellStyle(retailStringcs);

				//List<String> fepattributes = sortedAllAttributesMap.get(retailattname);
				List<String> fepAttributes = sortedAllAttributesMap.get(retailattname);
				String attval = " X "; 

				//Revisar si los atributos asociados están entre la lista de columnas
				//Se marca con una X la relación fila - columna (Attributo Retail - Attributo FEP)
				for (String value: fepAttributes){
					//Verificar si existe  y agregar el valor correspondiente
					if (columnlistMap.containsKey(value)) {
						int columnindex = columnlistMap.get(value);
						cell = row.createCell(columnindex);
						cell.setCellValue(attval);
						cell.setCellStyle(valuecs);
					}	
				}

				i++;
			}

			//Ajustar celdas vacías o nulas y todos los bordes
			for (int j=0; j < sheet.getLastRowNum() +1; j++){
				for (int k = 0 ; k < columnlistMap.size() +1; k++){
					cell = sheet.getRow(j).getCell(k);
					if (cell == null){
						cell = sheet.getRow(j).createCell(k);
						cell.setCellValue("");
						cell.setCellStyle(valuecs);
					}
				}
			}

			//Ajustar ancho de las celdas al tamaño del texto
			sheet.autoSizeColumn(retailatt_column_num);

			// Crear Archivo xls	
			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(PortalConstants.getInstance().getFileTransferPath() + realname);
				workbook.write(fileOut);

			} catch (IOException e) {
				e.printStackTrace();
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
			}
			finally{
				closeFileOutpuStream(fileOut);			
			}

			resultDTO.setDownloadfilename(downloadname);
			resultDTO.setRealfilename(realname);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}

		return resultDTO;
	}

	private void closeFileOutpuStream(FileOutputStream file){
		if(file != null){
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
