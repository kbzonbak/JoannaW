package bbr.b2b.portal.fep.managers.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.NamingException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import bbr.b2b.common.adtclasses.classes.BaseInitParamDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.util.LoggingUtil;
import bbr.b2b.fep.commercial.data.classes.ProviderArrayResultDTO;
import bbr.b2b.fep.commercial.data.classes.ProviderDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeByClientInitParamDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeInitParamDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeValueDTO;
import bbr.b2b.fep.common.data.classes.AttributesImageDTO;
import bbr.b2b.fep.common.data.classes.AttributesNotImageDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeDataDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeInitParamDTO;
import bbr.b2b.fep.common.data.classes.ErrorInfoArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ErrorInfoDTO;
import bbr.b2b.fep.common.data.classes.ItemAttributeValueDTO;
import bbr.b2b.fep.common.data.classes.PersonDTO;
import bbr.b2b.fep.common.data.classes.ProductReportDTO;
import bbr.b2b.fep.common.data.classes.StandardArticleTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.StandardArticleTypeResultDTO;
import bbr.b2b.fep.cp.data.classes.CPAddNewItemsInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPArticleTypeInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPHistItemArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPHistItemDTO;
import bbr.b2b.fep.cp.data.classes.CPHistItemInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemAttributeValueDTO;
import bbr.b2b.fep.cp.data.classes.CPItemByArticleInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.fep.cp.data.classes.CPItemErrorArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemStateArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemSummaryFlowArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemSummaryFlowInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemTechnicalInfoArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemValueByItemsInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByArticleTypeArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByArticleTypeDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByFilterInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByIDsInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByStatusInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsResumeDataResultDTO;
import bbr.b2b.fep.cp.data.classes.CPPrivilegeArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPPrivilegeDTO;
import bbr.b2b.fep.cp.data.classes.CPPrivilegeInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPProductInfoResumeResultDTO;
import bbr.b2b.fep.cp.data.classes.CPRelatedProductArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPRelatedProductDTO;
import bbr.b2b.fep.cp.data.classes.CPUpdateItemsInitParamDTO;
import bbr.b2b.fep.cp.data.classes.NewItemDataDTO;
import bbr.b2b.fep.managers.interfaces.ContextUtil;
import bbr.b2b.fep.managers.interfaces.ICreateProductManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.users.managers.classes.UserReportManagerLocal;
import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;
import bbr.b2b.portal.utils.FEPUtils;
import bbr.b2b.portal.utils.FileHandlerUtils;
import bbr.b2b.portal.utils.I18NManager;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;
import bbr.common.dataset.util.DataColumn;
import bbr.common.dataset.util.DataRow;
import bbr.common.dataset.util.DataTable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Stateless(name = "managers/FEPCreateProductManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FEPCreateProductManager implements FEPCreateProductManagerLocal {
	@EJB 
	FEPCommonDataManagerLocal commonDatatManager;

	@EJB
	FEPFileStorageManagerLocal fileStorageManager;

	@EJB
	private UserReportManagerLocal userReportManager;

	private ICreateProductManager createManager = null;
	//private ICommonDataManager commonDatatManager = null;

	private static LoggingUtil logger = new LoggingUtil(FEPCreateProductManager.class);

	//Filas y Columnas para la Subida y Descarga de los items
	//	int article_id_column 			= 0;
	//	int item_id_column 				= 1;
	//	int provider_code_column 		= 2;
	//	int provider_rsocial_column 	= 3;
	//	int first_data_column 			= 4;

	String article_id_column_name = "Código del Artículo";
	String item_id_column_name = "Número de solicitud";
	String provider_code_column_name = "Código de Proveedor";
	String provider_rsocial_column_name = "Razón social del Proveedor";

	//int articletype_row_index 	= dinamic_row_index++;	// FILA de dato de plantilla
	//	int groupheader_row_index 	= 0;	// FILA de Grupos relacionados con los tipos de atributos variantes o genéricos.
	//	int help_row_index 			= 1;	// Fila de ayuda del atributo
	//	int madatory_row_index 		= 2;	// Fila que indica si es obligatorio.
	//	int vendorname_row_index 	= 3;	// Fila donde se coloca el nombre del atributo para el proveedor o el áleas en caso de que se solicite
	//	int header_row_index 		= 4;	// Fila donde se coloca el 
	//	int first_data_row 			= 5;	//Fila a partir de la cual se colocan los datos

	@PostConstruct
	public void getRemote() {

		try {
			createManager = ContextUtil.getInstance().getCreateProductManager();
			//commonDatatManager = ContextUtil.getInstance().getCommonDataManager();
			//userManager = bbr.b2b.users.managers.interfaces.ContextUtil.getInstance().getIUserManager();
		} catch (NamingException e) {
			e.printStackTrace();

		}
	}


	@Override
	public FileDownloadInfoResultDTO downloadHistItemByID(Long itemid, Long uskey, String fileformat, Locale locale)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		CPHistItemArrayResultDTO histResult = null;

		try {
			CPHistItemInitParamDTO initparami = new CPHistItemInitParamDTO();
			initparami.setItemid(itemid);
			initparami.setLocale(locale);
			histResult = this.getHistItemByID(initparami);


			if(histResult != null && !histResult.getStatuscode().equals("0")){
				resultDTO.setStatuscode(histResult.getStatuscode());
				resultDTO.setStatusmessage(histResult.getStatusmessage());
				return resultDTO;
			}

			String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_item_history_title", locale);
			if(downloadname == null || downloadname.isEmpty()){
				downloadname = "Historia de Producto";
			}
			downloadname += FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +"."+ fileformat;
			String realname = "Historia de Producto" +  FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +"."+fileformat;


			//Tabla			
			DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "item_hist_title", locale));

			DataColumn col0 = new DataColumn("item_id", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_item_id", locale), String.class);
			DataColumn col1 = new DataColumn("provider_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "provider_code", locale), String.class);
			DataColumn col2 = new DataColumn("provider_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_provider_name", locale), String.class);
			DataColumn col3 = new DataColumn("state_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_state_name", locale), String.class);
			DataColumn col4 = new DataColumn("state_when", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_state_when", locale), Date.class);
			DataColumn col5 = new DataColumn("state_comment", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_state_comment", locale), String.class);
			DataColumn col6 = new DataColumn("user_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_user_code", locale), String.class);
			DataColumn col7 = new DataColumn("user_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_user_name", locale), String.class);
			DataColumn col8 = new DataColumn("role_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_role_name", locale), String.class);
			DataColumn col9 = new DataColumn("state_beforemodified", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_state_beforemodified", locale), Date.class);

			dt0.addColumn(col0);
			dt0.addColumn(col1);
			dt0.addColumn(col2);
			dt0.addColumn(col3);
			dt0.addColumn(col4);
			dt0.addColumn(col5);
			dt0.addColumn(col6);
			dt0.addColumn(col7);
			dt0.addColumn(col8);
			dt0.addColumn(col9);

			DataRow row = null;
			if( histResult != null && histResult.getHistory()!= null && histResult.getHistory().length > 0){
				CPItemDTO item = histResult.getItem();
				for ( CPHistItemDTO hist : histResult.getHistory()) {
					row = dt0.newRow(); 
					row.setCellValue("item_id", item.getId());
					row.setCellValue("provider_code", item.getProvidercode());
					row.setCellValue("provider_name", item.getProvidersocialreason());
					row.setCellValue("state_name", hist.getStatename());
					String dateStr = "";
					try
					{
						dateStr =  FEPUtils.getInstance().getDownloadDateFormat().format(hist.getWhen());
					}
					catch (Exception e)	{
						e.printStackTrace();
					}
					row.setCellValue("state_when", dateStr);

					try
					{
						dateStr =  FEPUtils.getInstance().getDownloadDateFormat().format(hist.getBeforemodified());
					}
					catch (Exception e)	{
						e.printStackTrace();
					}
					row.setCellValue("state_beforemodified", dateStr);

					row.setCellValue("state_comment", hist.getComment());	
					row.setCellValue("user_code", hist.getUsercode());	
					row.setCellValue("user_name", hist.getUsername());
					row.setCellValue("role_name", hist.getRolename());

					dt0.addRow(row);
				}
			}

			resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, fileformat, downloadname, realname, uskey);
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P100");// modulo comercial no disp.
		}
		return resultDTO;
	}

	@Override
	// TODO agregar columnas a los recursos
	public FileDownloadInfoResultDTO downloadItemsByArticleTypeResume(CPItemsByFilterInitParamDTO initparam, Long uskey, String fileformat)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();


		try {
			CPItemsByArticleTypeArrayResultDTO dataResult = createManager.getItemsByArticleTypeResume(initparam);


			if(dataResult != null && !dataResult.getStatuscode().equals("0")){
				resultDTO.setStatuscode(dataResult.getStatuscode());
				resultDTO.setStatusmessage(dataResult.getStatusmessage());
				return resultDTO;
			}

			String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_items_by_articletype_title", initparam.getLocale());
			if(downloadname == null || downloadname.isEmpty()){
				downloadname = "Productos Por Plantilla";
			}
			downloadname += FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +"."+ fileformat;
			String realname = "Productos Por Plantilla" +  FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +"."+fileformat;


			//Tabla			
			DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_items_by_articletype_title", initparam.getLocale()));

			DataColumn col0 = new DataColumn("article_internalname", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "article_internalname", initparam.getLocale()), String.class);
			DataColumn col1 = new DataColumn("trade_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_trade_code", initparam.getLocale()), String.class);
			DataColumn col2 = new DataColumn("item_count", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_item_count", initparam.getLocale()), Long.class);
			DataColumn col3 = new DataColumn("rejected_count", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_rejected_count", initparam.getLocale()), Long.class);
			DataColumn col4 = new DataColumn("approved_count", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_approved_count", initparam.getLocale()), Long.class);

			dt0.addColumn(col0);
			dt0.addColumn(col1);
			dt0.addColumn(col2);
			dt0.addColumn(col3);
			dt0.addColumn(col4);

			DataRow row = null;
			if( dataResult != null && dataResult.getValues()!= null && dataResult.getValues().length > 0){

				for ( CPItemsByArticleTypeDTO itemdata : dataResult.getValues()) {
					row = dt0.newRow(); 
					row.setCellValue("article_internalname", itemdata.getArticletypename());
					row.setCellValue("trade_code", itemdata.getTrade());
					row.setCellValue("item_count", itemdata.getPendingcount() != null ? itemdata.getPendingcount()   : 0L );
					row.setCellValue("rejected_count", itemdata.getRejectedcount() != null ? itemdata.getRejectedcount()   : 0L );
					row.setCellValue("approved_count", itemdata.getApprovedcount() != null ? itemdata.getApprovedcount()   : 0L );

					dt0.addRow(row);
				}
			}

			resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, fileformat, downloadname, realname, uskey);
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P100");// modulo comercial no disp.
		}
		return resultDTO;
	}

	@Override
	/*
	 * Método que crea un fichero excel base para las cargas de solicitudes de enriquicimiento 
	 * Se implementa con POI para dejar el campo Plantilla como lista de selección para que el usuario no cometa errores al elegir 
	 * el archivo tendrá 4 columnas
	 * 1-Código de Proveedor.
	 * 2-Código del artículo 
	 * 3-Plantilla (Columna con lista seleccionable)
	 * 4-Número de días de Expiración
	 */
	public FileDownloadInfoResultDTO downloadRequestFileBase(Long uskey, Locale locale)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		ArticleTypeArrayResultDTO artsTypeResult = null;

		try {
			ArticleTypeInitParamDTO initParam = new ArticleTypeInitParamDTO();
			initParam.setActive(true);

			artsTypeResult = commonDatatManager.getArticleTypeData(initParam, 1, 10000, false, null, locale.getLanguage());
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P100");// modulo comercial no disp.
		}

		if(artsTypeResult != null && !artsTypeResult.getStatuscode().equals("0")){
			resultDTO.setStatuscode(artsTypeResult.getStatuscode());
			resultDTO.setStatusmessage(artsTypeResult.getStatusmessage());
			return resultDTO;
		}

		String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_title_requests", locale);
		if(downloadname == null || downloadname.isEmpty()){
			downloadname = "Solicitudes";
		}
		downloadname += FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +"."+ "xlsx";
		String realname = "Solicitudes" +  FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +"."+"xlsx";

		// Crear el Libro y la Hoja usando POI
		XSSFWorkbook workbook = new XSSFWorkbook();

		// HOJA
		String sheetname =  I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_title_requests", locale);
		XSSFSheet sheet = workbook.createSheet(sheetname);
		XSSFSheet valuesheet = workbook.createSheet("VALORES");
		XSSFDataValidationHelper validationHelper = new XSSFDataValidationHelper(sheet);

		//ESTILOS
		CellStyle headercs = FEPUtils.getInstance().getHeaderCellStyle(workbook);
		CellStyle stringscs = FEPUtils.getInstance().getValueCellStyle(workbook);

		// FILA 0  encabezado 		
		Row headrow = sheet.createRow(0);			
		int provider_code_column = 0;
		int article_code_colum = 1;
		int articletype_code_colum = 2;
		int max_exp_colum = 3;

		String provider_code_column_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "provider_code", locale); //"Código Proveedor";
		String article_code_colum_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "article_code", locale); //"Articulo";
		String articletype_code_colum_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "articletype_name", locale);//"Plantilla";	
		String max_exp_colum_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "article_max_exp", locale);//"Dias de expiración";  

		try {
			//Proveedor
			Cell cell = headrow.createCell(provider_code_column);	
			cell.setCellValue(provider_code_column_name);				cell.setCellStyle(headercs);
			//Código de la plantilla
			cell = headrow.createCell(article_code_colum);	
			cell.setCellValue(article_code_colum_name);			        cell.setCellStyle(headercs);
			//Nombre de la plantilla
			cell = headrow.createCell(articletype_code_colum);	
			cell.setCellValue(articletype_code_colum_name);				cell.setCellStyle(headercs);
			//Días de expiración
			cell = headrow.createCell(max_exp_colum);	
			cell.setCellValue(max_exp_colum_name);						cell.setCellStyle(headercs);


			//Crear datos de Selectores.
			FEPUtils.saveArticlesToSheet(sheet, valuesheet, articletype_code_colum, validationHelper,artsTypeResult.getValues());

			//Salvar los datos de los recursos.
			int datarowIndex = 1;
			int i = 0;
			while (i < artsTypeResult.getValues().length ){
				Row row = sheet.createRow(datarowIndex);	

				//provider_code_column
				cell = row.createCell(provider_code_column);	
				cell.setCellValue("");		 cell.setCellStyle(stringscs);
				//article_code_colum
				cell = row.createCell(article_code_colum);	
				cell.setCellValue(""); cell.setCellStyle(stringscs);
				//articletype_code_colum
				cell = row.createCell(articletype_code_colum);	
				cell.setCellValue("");			cell.setCellStyle(stringscs);
				//max_exp_colum
				cell = row.createCell(max_exp_colum);	
				cell.setCellValue("");			cell.setCellStyle(stringscs);

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
	/*
	 * Método que crea solicitudes  en base a un fichero excel
	 * el archivo tendrá 4 columnas
	 * 1-Código de Proveedor.
	 * 2-Código del artículo 
	 * 3-Plantilla (Columna con lista seleccionable)
	 * 4-Número de días de Expiración
	 */
	public CPItemErrorArrayResultDTO uploadNewRequest(String filename, Long uskey, PersonDTO person, Locale locale)
	{
		CPItemErrorArrayResultDTO result = new CPItemErrorArrayResultDTO();
		ArrayList<ErrorInfoDTO> errors = new ArrayList<>();
		String error = "";

		String file = PortalConstants.getInstance().getFileUploadPath() + uskey + "/" + filename;
		String extension = filename.substring(filename.lastIndexOf('.') + 1);

		//Mapa de valores de Plantilla
		HashMap<String, ArticleTypeDataDTO> articletypes_Map = new HashMap<>();


		ArticleTypeInitParamDTO initparam = new ArticleTypeInitParamDTO();
		ArticleTypeArrayResultDTO artTypes = commonDatatManager.getArticleTypeData(initparam , 1, 10000, false, null, locale.getLanguage());
		if(artTypes !=null && artTypes.getStatuscode().equals("0") && artTypes.getValues() != null && artTypes.getValues().length> 0){
			Arrays.stream(artTypes.getValues()) .forEach(artt-> articletypes_Map.put(artt.getName(),artt) );

		}
		else{
			//No se detectaron plantilla activas, emitir error
			error = "No se encontraron plantillas activas en la base de datos";
			errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));			
		}

		if (errors.size() > 0) {
			result.setErrors( errors.toArray(new ErrorInfoDTO[errors.size()]) );
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P3501");			
		}

		int provider_code_column = 0;
		int article_code_colum = 1;
		int articletype_code_colum = 2;
		int max_exp_colum = 3;

		String provider_code_column_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "provider_code", locale); //"Código Proveedor";
		String article_code_colum_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "article_code", locale); //"Articulo";
		String articletype_code_colum_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "articletype_name", locale);//"Plantilla";	
		String max_exp_colum_name = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "article_max_exp", locale);//"Dias de expiración";  	

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

			//Buscar las columnas definidas

			// Se buscan las filas en blanco
			List<Integer> rowindexList = new ArrayList<Integer>();
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// Validando fila en blanco
				if ( row != null && row.getCell(0) == null && row.getCell(1) == null){
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
			ArrayList<NewItemDataDTO> newitemList = new ArrayList<>();
			//Barrer todas las filas
			for (int j = firstRowData; j <= sheet.getLastRowNum(); j++) {
				row_errors.clear();				
				Row row = sheet.getRow(j);
				if (row == null)
					continue;

				String articlecode = "";
				String articletypecode = "";
				String providercode = "";
				Cell cell = null;

				try
				{
					cell = row.getCell(article_code_colum);
					articlecode = FEPUtils.getValueData("String", cell,cell.getCellType());	
				}
				catch (Exception e)
				{
					;
				}

				//Validaciones				
				if(articlecode == null || articlecode.isEmpty()){
					error = "Dato inválido en la Fila " + (j+1) + " Columna: " + article_code_colum_name;
					FEPUtils.addErrorResult(row_errors, j, article_code_colum, error);
					errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));
				}

				try
				{
					cell = row.getCell(articletype_code_colum);
					articletypecode =  FEPUtils.getValueData("String", cell,cell.getCellType());
				}
				catch (Exception e)
				{
					;
				}


				if(articletypecode == null || articletypecode.isEmpty() || !articletypes_Map.containsKey(articletypecode) ){
					error = "Dato inválido en la Fila " + (j+1) + " Columna: "  + articletype_code_colum_name;
					FEPUtils.addErrorResult(row_errors, j, articletype_code_colum, error);
					errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));
				}


				try
				{
					cell = row.getCell(provider_code_column);
					providercode =  FEPUtils.getValueData("String", cell,cell.getCellType()); 
				}
				catch (Exception e)
				{
					;
				}


				if(providercode == null || providercode.isEmpty()){
					error = "Dato inválido en la Fila " + (j+1) + " Columna: "  + provider_code_column_name;
					FEPUtils.addErrorResult(row_errors, j, provider_code_column, error);
					errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));
				}

				String expitationdaysStr = null;
				Integer expitationdays = 60;
				try
				{
					cell = row.getCell(max_exp_colum);					
					expitationdaysStr = FEPUtils.getValueData("Integer", cell,cell.getCellType());					
					expitationdays =Integer.parseInt(expitationdaysStr);
				}
				catch (Exception e)
				{
					error = "Dato inválido en la Fila " + (j+1) + " Columna: "  + max_exp_colum_name;
					FEPUtils.addErrorResult(row_errors, j, max_exp_colum, error);
					errors.add(PortalStatusCodeUtils.getInstance().setStatusCode(new ErrorInfoDTO(), "P3501", error, false));
				}


				if (row_errors.size() > 0) {
					errors.addAll(row_errors);
					continue;			
				}

				//Crear una nueva solicicitud				
				NewItemDataDTO newItem = new NewItemDataDTO();
				newItem.setArticlecode(articlecode);
				newItem.setProvidercode(providercode);
				newItem.setArticletypecode(articletypecode);
				newItem.setRow(j+1);
				newItem.setExpitationdays(expitationdays);
				newitemList.add(newItem);
			}

			if (errors.size() > 0) {
				result.setErrors( errors.toArray(new ErrorInfoDTO[errors.size()]) );
				PortalStatusCodeUtils.getInstance().setStatusCode(result, "P3501");			
			}
			else{
				//Invocar al modulo para crear las nuevas solicitudes
				NewItemDataDTO[] newItemArr = new NewItemDataDTO[newitemList.size()];
				newItemArr = newitemList.toArray(newItemArr);
				CPAddNewItemsInitParamDTO initparamIt = new CPAddNewItemsInitParamDTO();
				initparamIt.setItems(newItemArr);
				initparamIt.setUser(person);
				initparamIt.setLocale(locale);
				CPItemErrorArrayResultDTO newItemsRes = null; //TODO:createManager.addOrUpdateNewItems( initparamIt  );//newItemArr, person, locale.getLanguage());
				if(newItemsRes != null && !newItemsRes.getStatuscode().equals("0") ){
					if(newItemsRes.getErrors()!= null && newItemsRes.getErrors().length > 0){
						Arrays.stream(newItemsRes.getErrors()).forEach(err->errors.add(err));
						result.setErrors( errors.toArray(new ErrorInfoDTO[errors.size()]) );
					}
				}
				else if(newItemsRes != null && newItemsRes.getStatuscode().equals("0")){
					result.setItems(newItemsRes.getItems());					
				}
				else{
					PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");
				}
			}

			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P4000");
		}
	}


	@Override
	public CPPrivilegeArrayResultDTO getPrivilegies(CPPrivilegeInitParamDTO initparam)
	{
		CPPrivilegeArrayResultDTO resultDTO = new CPPrivilegeArrayResultDTO();
		try {
			return createManager.getPrivilegies(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public ErrorInfoArrayResultDTO addOrEditPrivilegies(CPPrivilegeInitParamDTO initparam)
	{
		ErrorInfoArrayResultDTO resultDTO = new ErrorInfoArrayResultDTO();
		try {
			return createManager.addOrEditPrivilegies(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public CPItemsByArticleTypeArrayResultDTO getItemsByArticleTypeAndTradeResume(CPItemByArticleInitParamDTO initparam)
	{
		CPItemsByArticleTypeArrayResultDTO resultDTO = new CPItemsByArticleTypeArrayResultDTO();
		try {
			return createManager.getItemsByArticleTypeAndTradeResume(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public CPItemArrayResultDTO getItemsByFilter(CPItemsByFilterInitParamDTO initparam)
	{
		CPItemArrayResultDTO resultDTO = new CPItemArrayResultDTO();
		try {
			return createManager.getItemsByFilter(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public CPItemArrayResultDTO getItemsByIDs(CPItemsByIDsInitParamDTO initparam)
	{
		CPItemArrayResultDTO resultDTO = new CPItemArrayResultDTO();
		try {
			return createManager.getItemsByIDs(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public CPItemResultDTO getItemByID(CPItemsByIDsInitParamDTO initparam)
	{
		CPItemResultDTO resultDTO = new CPItemResultDTO();
		try {
			return createManager.getItemByID(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	/*@Override
	public CPItemErrorArrayResultDTO addOrUpdateNewItems(CPAddNewItemsInitParamDTO initparam)
	{
		CPItemErrorArrayResultDTO resultDTO = new CPItemErrorArrayResultDTO();
		try {
			return createManager.addOrUpdateNewItems(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}*/


	@Override
	//public CPItemErrorArrayResultDTO updateItems(CPUpdateItemsInitParamDTO initparam)
	public CPItemErrorArrayResultDTO addOrUpdateItems(CPUpdateItemsInitParamDTO initparam)
	{
		CPItemErrorArrayResultDTO resultDTO = new CPItemErrorArrayResultDTO();
		try {
			return createManager.addOrUpdateItems(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public CPItemStateArrayResultDTO getItemStates(BaseInitParamDTO initparam)
	{
		CPItemStateArrayResultDTO resultDTO = new CPItemStateArrayResultDTO();
		try {
			return createManager.getItemStates(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public ArticleTypeResultDTO getArticleTypeWithCPPrivilegies(CPArticleTypeInitParamDTO initparam)
	{
		ArticleTypeResultDTO resultDTO = new ArticleTypeResultDTO();
		try {
			return createManager.getArticleTypeWithCPPrivilegies(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public CPItemTechnicalInfoArrayResultDTO getItemTechnicalInfoByID(CPItemsByIDsInitParamDTO initparam)
	{
		CPItemTechnicalInfoArrayResultDTO resultDTO = new CPItemTechnicalInfoArrayResultDTO();
		try {
			return createManager.getItemTechnicalInfoByID(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}

	@Override
	public CPItemsResumeDataResultDTO getItemsResumeData(CPItemByArticleInitParamDTO initparam)
	{
		CPItemsResumeDataResultDTO resultDTO = new CPItemsResumeDataResultDTO();
		try {
			return createManager.getItemsResumeData(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public CPHistItemArrayResultDTO getHistItemByID(CPHistItemInitParamDTO initparam)
	{
		CPHistItemArrayResultDTO resultDTO = new CPHistItemArrayResultDTO();
		try {
			return createManager.getHistItemByID(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}




	@Override
	public CPItemArrayResultDTO getItemsByStatusData(CPItemsByStatusInitParamDTO initparam, Long uskey)
	{
		CPItemArrayResultDTO resultDTO = new CPItemArrayResultDTO();
		try {
			if(initparam.getProvidercode() != null && !initparam.getProvidercode().isEmpty())
			{
				userReportManager.saveCompanySelectedAndAddCountUserProvider(uskey, initparam.getProvidercode());	
			}

			return createManager.getItemsByStatusData(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}




	@Override
	public CPItemsByArticleTypeArrayResultDTO getItemsByArticleTypeResume(CPItemsByFilterInitParamDTO initparam, Long uskey)
	{
		CPItemsByArticleTypeArrayResultDTO resultDTO = new CPItemsByArticleTypeArrayResultDTO();
		try {

			if(initparam.getProvidercode() != null && !initparam.getProvidercode().isEmpty())
			{
				userReportManager.saveCompanySelectedAndAddCountUserProvider(uskey, initparam.getProvidercode());	
			}

			return createManager.getItemsByArticleTypeResume(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}



	@Override
	public CPProductInfoResumeResultDTO getProductInfoItemsResumeData(CPItemByArticleInitParamDTO initparam)
	{
		CPProductInfoResumeResultDTO resultDTO = new CPProductInfoResumeResultDTO();
		try {
			return createManager.getProductInfoItemsResumeData(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public FileDownloadInfoResultDTO downloadExcelWithItemsToCreate(CPArticleTypeInitParamDTO initparam, CPItemDTO[] items, boolean includeAleas, Long userKey) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		int provider_code_column 		= 0;
		int provider_rsocial_column 	= 1;
		int first_data_column 			= 2;

		int groupheader_row_index 	= 0;	// FILA de Grupos relacionados con los tipos de atributos variantes o genéricos.
		int help_row_index 			= 1;	// Fila de ayuda del atributo
		//int madatory_row_index 		= 2;	// Fila que indica si es obligatorio.
		int header_row_index 		= 2;	// Fila donde se coloca el 
		int first_data_row 			= 3;	//Fila a partir de la cual se colocan los datos

		//Atributos relacionados a la plantilla
		LinkedHashMap<String,DefinableAttributeDataDTO> mapDefinAttributes = new LinkedHashMap<String,DefinableAttributeDataDTO> ();
		LinkedHashMap<String,DefinableAttributeDataDTO> mapSortedDefinAtt = new LinkedHashMap<String,DefinableAttributeDataDTO> ();

		LinkedHashMap<String , Integer> columnlist = new LinkedHashMap<String , Integer>();//lista de columnas en funcion de su nombre interno

		//LinkedHashMap<Integer,String> mapEditables = new LinkedHashMap<Integer,String>();

		try
		{
			//Buscar la plantilla
			initparam.setIncludeAttributeInfo(true);
			ArticleTypeResultDTO articleTypeRes = this.getArticleTypeWithCPPrivilegies(initparam);
			if( articleTypeRes == null || (articleTypeRes!= null && !articleTypeRes.getStatuscode().equals("0")) ){
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10020");
			}
			else{
				if(articleTypeRes.getDefattributes() !=  null && articleTypeRes.getDefattributes().length > 0 ){
					mapDefinAttributes = articleTypeRes.getDefinableAttributesMap();

					mapSortedDefinAtt = mapDefinAttributes.entrySet()
							.stream()
							.sorted((f1, f2) -> Integer.compare(f1.getValue().getVisualorder(), f2.getValue().getVisualorder()))
							.sorted((f1, f2) -> Long.compare(f1.getValue().getAttributeinfo().getAttribute().getAttributetypeid(), f2.getValue().getAttributeinfo().getAttribute().getAttributetypeid()))
							.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
				}
				else{
					return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10021");
				}

			}

			String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_items_creation_info_title", initparam.getLocale());
			if(downloadname == null || downloadname.isEmpty() || downloadname.equals("M/R")){
				downloadname = "Productos en creación_";
			}
			downloadname += FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +".xlsx";
			String realname = "PRODUCTOS EN CREACIÓN_" +  FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +".xlsx";

			// Crear el Libro y las Hojas usando POI
			XSSFWorkbook workbook = new XSSFWorkbook();
			//CreationHelper createHelper = workbook.getCreationHelper();

			// HOJA Principal
			XSSFSheet sheet = workbook.createSheet("ATRIBUTOS DE PRODUCTOS");
			//Hola de Datos para hacer referencias.
			XSSFSheet valuesheet = workbook.createSheet("VALORESATRIBUTOS");

			Row groupheader_row = sheet.createRow(groupheader_row_index);
			groupheader_row.setHeight((short) 900 );			
			Row helprow = sheet.createRow(help_row_index);
			helprow.setHeight((short) 900 );			
			//Row mandatoryrow = sheet.createRow(madatory_row_index);			
			Row headrow = sheet.createRow(header_row_index);

			//Estilos
			CellStyle helpcs = FEPUtils.getInstance().getHelpColumnCellStyle(workbook);
			CellStyle headercs = FEPUtils.getInstance().getHeaderCellStyle(workbook);
			CellStyle stringcs = FEPUtils.getInstance().getValueCellStyle(workbook);
			//CellStyle linkcs = FEPUtils.getLinkCellStyle(workbook);
			CellStyle stringscsgrey = FEPUtils.getInstance().getValueCellStyleGrey(workbook);
			CellStyle numerics = FEPUtils.getInstance().getNumericValueCellStyle(workbook);
			CellStyle numericsgrey = FEPUtils.getInstance().getNumericValueCellStyleGrey(workbook);

			//Ayuda
			Cell cellhp = helprow.createCell(provider_code_column);			   	cellhp.setCellValue(provider_code_column_name);			cellhp.setCellStyle(helpcs);
			cellhp = helprow.createCell(provider_rsocial_column);			   	cellhp.setCellValue(provider_rsocial_column_name);		cellhp.setCellStyle(helpcs);

			//Obligatoriedad
			/*String mandatory_name = "Obligatorio";
			Cell cellm = mandatoryrow.createCell(provider_code_column);			cellm.setCellValue(mandatory_name);						cellm.setCellStyle(helpcs);	
			cellm = mandatoryrow.createCell(provider_rsocial_column);			cellm.setCellValue("Informativo");	    				cellm.setCellStyle(helpcs);	*/

			//Encabezado
			Cell cellh = headrow.createCell(provider_code_column);				cellh.setCellValue(provider_code_column_name);			cellh.setCellStyle(headercs);
			cellh = headrow.createCell(provider_rsocial_column);				cellh.setCellValue(provider_rsocial_column_name);		cellh.setCellStyle(headercs);

			int itemscount = items != null ? items.length : 0;
			//Lista de valores	
			XSSFDataValidationHelper validationHelper = new XSSFDataValidationHelper(sheet);
			//Crear las columnas dinámicas en base a los atributos definidos. Además buscar en caso que sean listas los valores.			
			Cell cell = null;
			Iterator<DefinableAttributeDataDTO> it = mapSortedDefinAtt.values().iterator();
			int columindex = first_data_column;
			int valuerowindex = 0; //Fila por la cual comenzar a crear los valores.
			ArrayList<String> values = new ArrayList<>();

			int group_init_index = 0;
			int group_end_index = columindex-1 ;

			String  last_header = "INFORMACIÓN SOLICITUD";

			while (it.hasNext())		{
				DefinableAttributeDataDTO da = it.next();

				//Header El nombre interno
				cell = headrow.createCell(columindex);
				cell.setCellValue(da.getAttributeinternalname());
				cell.setCellStyle(headercs);

				// Si el grupo no ha cambiado se incrementa el numero de la columna
				// Sino se crea un nuevo header en el rango 
				if(last_header.equals(da.getAttributetypename())){
					group_end_index ++;				
				}
				else if (!last_header.equals(da.getAttributetypename()) || !it.hasNext() ){				
					cell = groupheader_row.createCell(group_init_index);
					cell.setCellValue(last_header);
					cell.setCellStyle(helpcs);
					CellRangeAddressList group_header_range = new CellRangeAddressList(groupheader_row.getRowNum(),groupheader_row.getRowNum(),group_init_index , group_end_index);
					if(group_init_index != group_end_index){
						try {
							sheet.addMergedRegion(group_header_range.getCellRangeAddress(0));		
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("Ocurrieron errores intentando crear un grupo para el atributo: " + da.getAttributeinternalname());
							return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10005");
						}		
					}
					//crear el group header				
					group_init_index = group_end_index+1;
					group_end_index = group_init_index;	
					last_header = da.getAttributetypename();
				}

				if (!it.hasNext()){
					cell = groupheader_row.createCell(group_init_index);
					cell.setCellValue(last_header);
					cell.setCellStyle(helpcs);
					CellRangeAddressList group_header_range = new CellRangeAddressList(groupheader_row.getRowNum(),groupheader_row.getRowNum(),group_init_index , group_end_index);
					if(group_init_index != group_end_index){
						try {
							sheet.addMergedRegion(group_header_range.getCellRangeAddress(0));		
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("Ocurrieron errores intentando crear un grupo para el atributo: " + da.getAttributeinternalname());
							return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10005");
						}
					}
				}

				//Help
				cell = helprow.createCell(columindex);
				cell.setCellValue(da.getAttributeuserhelp());
				cell.setCellStyle(helpcs);	

				//Mandatory
				/*cell = mandatoryrow.createCell(columindex);
				boolean mandatory = da.getMandatory() && da.getCanwrite();

				if (mandatory){
					cell.setCellValue( "Obligatorio \n (Editable)");
					mapEditables.put(columindex, da.getAtributedatatypeinternalname());
				}
				else if(da.getCanwrite()){
					cell.setCellValue( "Editable");
					mapEditables.put(columindex, da.getAtributedatatypeinternalname());
				}

				cell.setCellStyle(helpcs);	*/
				columnlist.put(da.getAttributeinternalname(), columindex);	

				boolean makeList = false;
				if (  FEPUtils.attributeIsValueList(da.getAtributedatatypeinternalname())){	
					values.clear();
					AttributeValueDTO[] valuesDTO = da.getAttributeinfo() != null ? da.getAttributeinfo().getValues() : null;
					if(valuesDTO == null){
						AttributeResultDTO attvaluesres = commonDatatManager.getAttribute(da.getAttributeid(), initparam.getLocale().getLanguage());
						if (attvaluesres != null && attvaluesres.getStatuscode().equals("0") && attvaluesres.getValues() != null && attvaluesres.getValues().length >0){
							valuesDTO = attvaluesres.getValues();
						}
					}

					if(valuesDTO !=null && valuesDTO.length >0){
						Arrays.stream(valuesDTO).forEach(value-> values.add(value.getValue()));	
						makeList = true;
					}
				}

				if (makeList){
					if(values != null){
						//Llenar datos en una columna de la hora de valores con los datos de la lista
						try {

							Row valuerow = valuesheet.createRow(valuerowindex);
							int i = 0;
							for (String value : values) {								
								cell = valuerow.createCell(i++);
								cell.setCellValue(value);
								cell.setCellStyle(stringcs);		
							}
							String lascolumnname = CellReference.convertNumToColString(values.size()-1);
							// Crear un nombre el el libro asociado a la columna de datos previamente creada
							Name namedCell = workbook.createName();
							namedCell.setNameName("Fila_"+ valuerowindex); // nombre del atributo
							String formulaStr = valuesheet.getSheetName() + "!$A$" + (valuerowindex+1) + ":$" + lascolumnname +  "$" + (valuerowindex+1);
							namedCell.setRefersToFormula(formulaStr);
							DataValidationConstraint constraint = validationHelper.createFormulaListConstraint("Fila_"+ valuerowindex);

							CellRangeAddressList addressList = new CellRangeAddressList(first_data_row, first_data_row + itemscount,columindex , columindex);
							DataValidation dataValidation = validationHelper.createValidation(constraint,addressList);
							sheet.addValidationData(dataValidation);

							valuerowindex++;
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					else{
						logger.error("Ocurrieron errores intentando cargar lista de valores del atributo: " + da.getAttributeinternalname());
						return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");// no se pudo generar el archivo
					}

				}

				//Permitir que los valores booleanos sean seleccionables
				if (da.getAtributedatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_BOOLEAN)){
					FEPUtils.saveBooleanTypeToSheet(sheet, valuesheet, first_data_row, columindex, valuerowindex, itemscount, validationHelper);
					valuerowindex++;
				}


				columindex++;
			}

			//Al llegar acá todas las columnas están creadas y listos para barrer los items.
			if (items != null && items.length > 0 ){
				// Crear un mapa con los items y todos su valores
				for (int i = 0; i < items.length ; i++  ){
					// Tomar la lista de valores de cada item y crear las columnas dinamicas
					CPItemDTO item = items[i];
					ItemAttributeValueDTO[] itemvalues = item.getAttributevalues();
					Row row = sheet.createRow(i+first_data_row); // Salta en encabezado

					cell = row.createCell(provider_code_column);
					cell.setCellValue(item.getProvidercode());
					cell.setCellStyle(stringcs);

					cell = row.createCell(provider_rsocial_column);
					cell.setCellValue(item.getProvidersocialreason());
					cell.setCellStyle(stringcs);

					//Colocar los datos  dinámicos
					if ( itemvalues != null && itemvalues.length >0  ) {					
						for (int j = 0; j<itemvalues.length;j++ ){
							ItemAttributeValueDTO ivalue = itemvalues[j];
							String attval = null;
							boolean isNumeric = false;
							boolean isCanwrite = false;

							//Verificar si existe  y agregar el valor correspondiente
							if (columnlist.containsKey(ivalue.getAttributeinternalname())){
								int columnindex = columnlist.get(ivalue.getAttributeinternalname());
								cell = row.createCell(columnindex);
								//DefinableAttributeDataDTO definableAtt = mapSortedDefinAtt.get(ivalue.getAttributeinternalname().trim());

								attval = ivalue.getValue() != null ? ivalue.getValue() : "" ;

								if( !attval.isEmpty() && ivalue.getUserdatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_BOOLEAN) ){		
									ivalue.setValue(FEPUtils.getBooleanValueByLanguage(ivalue.getValue(),initparam.getLocale()));
									attval = ivalue.getValue();
									cell.setCellValue(attval);

								} 
								/*else if (!attval.isEmpty() && !ivalue.getValue().isEmpty() && FEPUtils.attributeIsaFile(definableAtt.getAtributedatatypeinternalname())){
									//Salvar nombre y link
									//String filename = FEPUtils.getDescriptionFromCodedValue(ivalue.getValue(), MDMConstants.LIST_SEPARATOR);
									//String linktofile = FEPUtils.getCodeFromCodedValue(ivalue.getValue(), MDMConstants.LIST_SEPARATOR);
									cell.setCellValue(ivalue.getValue());
									Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
									link.setAddress(ivalue.getValue());
									cell.setHyperlink(link);
									cell.setCellStyle(linkcs); 

								} */
								else if ( !attval.isEmpty() && (ivalue.getUserdatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_FLOAT) || 
										ivalue.getUserdatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_INTEGER))){

									Double doublevalue = 0D;

									try {
										doublevalue = FEPUtils.getDecimalFormat(attval);
									} catch (ParseException e) {
										e.printStackTrace();
										return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
									}

									cell.setCellValue(doublevalue);
									isNumeric = true;

								} else{
									cell.setCellValue(attval);
								}

							}	

							if(attval != null){

								isCanwrite = mapSortedDefinAtt.containsKey(ivalue.getAttributeinternalname()) && mapSortedDefinAtt.get(ivalue.getAttributeinternalname()).getCanwrite();

								if (isCanwrite && isNumeric){
									cell.setCellStyle(numerics);

								} else if (isNumeric && !isCanwrite){
									cell.setCellStyle(numericsgrey);

								} else if (isCanwrite && !isNumeric){
									cell.setCellStyle(stringcs);

								} else{
									cell.setCellStyle(stringscsgrey);
								}
							}

						}
					}

					for (int k = 0 ; k < headrow.getLastCellNum(); k++){
						cell = row.getCell(k);
						if (cell == null){
							cell = row.createCell(k);
							cell.setCellValue("");
							cell.setCellStyle(stringcs);
							/*if (mapEditables.containsKey(k)){
									if ( mapEditables.get(k).equals("Float") ||  mapEditables.get(k).equals("Integer"))
										cell.setCellStyle(numericcs);
									else
										cell.setCellStyle(stringcs);
								}
								else{
									cell.setCellStyle(stringscsgrey);	
								}*/

						}
					}
				}

				//Se ajustan todas las celdas...
				for (int i=0; i < sheet.getLastRowNum(); i++){
					for (int j=0; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++){
						sheet.autoSizeColumn(j);
					}
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
					closeFileOutpuStream(fileOut);			
				}

				resultDTO.setDownloadfilename(downloadname);
				resultDTO.setRealfilename(realname);

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}


		return resultDTO;


	}


	public CPItemErrorArrayResultDTO uploadExcelWithNewsItems(CPArticleTypeInitParamDTO initparam, CPItemDTO[] items,String filename, Long userKey){
		CPItemErrorArrayResultDTO resultDTO = new CPItemErrorArrayResultDTO();

		int provider_code_column 		= 0;
		int provider_rsocial_column 	= 1;
		int first_data_column 			= 2;

		int header_row_index 		= 2;	// Fila donde se coloca el 
		int first_data_row 			= 3;	//Fila a partir de la cual se colocan los datos

		ArrayList<ErrorInfoDTO> errors = new ArrayList<>();

		List<CPItemDTO> newitemsList = new ArrayList<CPItemDTO>();

		String file = PortalConstants.getInstance().getFileUploadPath() + userKey + "/" + filename;
		String extension = filename.substring(filename.lastIndexOf('.') + 1);

		boolean editmode = !initparam.getItemstatename().equals(FEPConstants.PROVIDER_NEW_NAME_STATUS);

		Long temporaryid = 1L;

		ProviderArrayResultDTO providers = null;

		HashMap<String, ProviderDTO> providermaps = new HashMap<String, ProviderDTO>();

		try {
			providers = commonDatatManager.getProviders();
		} catch (Exception e) {
			;
		}

		if (providers == null){
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10032");
		}

		if (providers != null && providers.getStatuscode().equals("0") ){
			Arrays.stream(providers.getValues()).forEach(provider -> providermaps.put(provider.getProvidercode(), provider));
		}

		try
		{
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
				PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P4000");
			} catch (Exception e) {
				e.printStackTrace();
				PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P4000");
			}

			//Atributos relacionados a la plantilla
			LinkedHashMap<String,DefinableAttributeDataDTO> mapDefinAttributes = null;
			Map<String,String> mapMandatoryDefinAttributes = null;

			//Buscar la plantilla
			initparam.setIncludeAttributeInfo(true);
			ArticleTypeResultDTO articleTypeRes = this.getArticleTypeWithCPPrivilegies(initparam);
			if( articleTypeRes == null || (articleTypeRes!= null && !articleTypeRes.getStatuscode().equals("0")) ){
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10020");
			}
			else{
				if(articleTypeRes.getDefattributes() != null && articleTypeRes.getDefattributes().length > 0 ){
					mapDefinAttributes = articleTypeRes.getDefinableAttributesMap();
					mapMandatoryDefinAttributes = 	articleTypeRes.getMandatoryDefinableAttributesMap();				
				}
				else{
					return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10021");
				}

			}

			Sheet sheet = null;
			String error = "";

			// Hoja principal
			sheet = wb1.getSheetAt(0);

			//Buscar las columnas definidas
			Row headerrow = sheet.getRow(header_row_index);

			// VALIDA SI EL ARCHIVO ESTA VACIO
			if (sheet.getPhysicalNumberOfRows() == 0) {
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10004");		
			}

			boolean allcolumnOK = false;

			// Valida si primera fila corresponde a encabezado con las columnas obligatorias
			if ( sheet.getRow(header_row_index).getCell(provider_code_column) != null && sheet.getRow(header_row_index).getCell(provider_code_column).toString().equals(provider_code_column_name) 
					) {	
				allcolumnOK = true;
			}

			if (!allcolumnOK)
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10003");

			//verificar que los atributos sean consistentes con la plantilla y privilegios que se tienen
			HashSet<String> attributecolums = new HashSet<String>();
			for (int l= first_data_column; l< headerrow.getLastCellNum() ; l++){
				String columName = headerrow.getCell(l).getStringCellValue().trim();
				if (!columName.isEmpty()){
					attributecolums.add(headerrow.getCell(l).getStringCellValue().trim());
					//verificar que el nombre de la columna corresponda con una atributo definido en la plantilla					
					boolean attExist = mapDefinAttributes.containsKey(columName);
					if(!attExist){
						error = "La plantilla no contiene el atributo " + columName;
						FEPUtils.addErrorResult(errors, header_row_index+1, l, error);
					}					
				}				
			}

			//Verificar que no falten atributos obligatorios
			if(mapMandatoryDefinAttributes != null && mapMandatoryDefinAttributes.size() > 0){
				for(String attname : mapMandatoryDefinAttributes.keySet()){
					if(!attributecolums.contains(attname)){
						error = "Falta el atributo obligatorio: " + attname;
						FEPUtils.addErrorResult(errors, header_row_index+1, -1, error);
					}
				}
			}

			//Mapa para tener los valores seleccionados en los atributos tipo lista
			HashMap<Long, List<AttributeValueDTO> > attSelectedValues = new HashMap<>();

			List<ErrorInfoDTO> rowerrors = new ArrayList<ErrorInfoDTO>();

			// cada Fila se corresponde con un item

			for (int j = first_data_row; j <= sheet.getLastRowNum(); j++) {
				attSelectedValues.clear();
				rowerrors.clear();

				Row row = sheet.getRow(j);
				if (row == null)
					continue;				

				CPItemDTO newItem = null;
				Long itemid = null;
				//String itemidStr = "";

				//String articleStr = FEPUtils.getValueData("Integer",row.getCell(article_id_column),row.getCell(article_id_column).getCellType());
				String vendorcodeStr = FEPUtils.getValueData("String",row.getCell(provider_code_column),row.getCell(provider_code_column).getCellType()); 
				String socialreasonStr = FEPUtils.getValueData("String",row.getCell(provider_rsocial_column),row.getCell(provider_rsocial_column).getCellType());

				//Verificar validez del proveedor
				boolean isprovider = providermaps.get(vendorcodeStr) != null;

				//Se crea id temporal

				itemid = temporaryid;
				temporaryid ++;

				if(vendorcodeStr == null || vendorcodeStr.isEmpty()){
					error = "No existen datos para el código del proveedor" + " - " + "(Fila " + (row.getRowNum() + 1) + "," + "Columna " + 1 + ")";
					FEPUtils.addErrorResult(rowerrors, j, 3, error);
				}
				else if (vendorcodeStr != null && !vendorcodeStr.isEmpty() && !isprovider ) {
					error = "El nombre del proveedor " + vendorcodeStr + " no es un proveedor válido " + " - " + "(Fila " + (row.getRowNum() + 1) + "," + "Columna " + 1 + ")";
					FEPUtils.addErrorResult(rowerrors, j, 3, error);
				}

				if(socialreasonStr == null || socialreasonStr.isEmpty()){
					error = "No existen datos para la razón social del proveedor" + " - " + "(Fila " + (row.getRowNum() + 1) + "," + "Columna " + 4 + ")";
					FEPUtils.addErrorResult(rowerrors, j, 4, error);
				}

				newItem = new CPItemDTO();
				newItem.setId(itemid);
				newItem.setArticleid(itemid);

				if (!editmode && isprovider){
					ProviderDTO prov = providermaps.get(vendorcodeStr);

					newItem.setProviderid(prov.getId());
					newItem.setProvidercode(prov.getProvidercode());
					newItem.setProvidersocialreason(prov.getSocialreason());
				}

				// Atributes Values
				List<ItemAttributeValueDTO> attvalues = new ArrayList<ItemAttributeValueDTO>();
				//HashMap<String, ItemAttributeValueDTO> mapitemvalues = new  HashMap<String, ItemAttributeValueDTO> ();					
				attvalues.clear();

				for (int k = first_data_column; k < headerrow.getLastCellNum(); k++) {	
					List<AttributeValueDTO> selected_values =  null;
					CPItemAttributeValueDTO newitemvalue  = new CPItemAttributeValueDTO();
					String colheader_name = headerrow.getCell(k).getStringCellValue().trim();
					Cell cell = row.getCell(k);

					String cellvalue = null;
					DefinableAttributeDataDTO datt = mapDefinAttributes.get(colheader_name);

					try {
						if (cell == null){
							cellvalue = FEPUtils.getValueData(datt.getAtributedatatypeinternalname(),cell, HSSFCell.CELL_TYPE_BLANK);		
						}
						else{
							cellvalue = FEPUtils.getValueData(datt.getAtributedatatypeinternalname(),cell, cell.getCellType());
						}

						if (datt != null){
							if (datt.getMandatory() && datt.getCanwrite() && (cellvalue == null || cellvalue.isEmpty()) ){
								//error = "Solicitud: " + newItem.getId() + " - " +  "Campo obligatorio: " + datt.getAttributeinternalname() + " (Fila " + (row.getRowNum() + 1) + "," + "Columna " + k + ")";
								error =  "Campo obligatorio vacío";
								FEPUtils.addErrorResult(rowerrors, (row.getRowNum() + 1), k+1, newItem.getId().toString(), datt.getAttributevendorname(), error);
								continue;
							}

							if(datt.getCanwrite()){
								String formatedValue = cellvalue != null && !cellvalue.isEmpty() ? cellvalue : "";									

								//fromatear el valor si es lista o codificacion etc.
								if(cellvalue != null && !cellvalue.isEmpty()){
									formatedValue = commonDatatManager.formatValueData(cellvalue, datt, rowerrors,attSelectedValues, j, k, newItem.getId().toString(),datt.getAttributeinternalname(), initparam.getLocale().getLanguage()); //Formatear.
								}

								if(formatedValue != null){
									newitemvalue.setAttributeid(datt.getAttributeid());
									newitemvalue.setAttributeinternalname(datt.getAttributeinternalname());
									newitemvalue.setAttributevendorname(datt.getAttributevendorname());
									newitemvalue.setUserdatatypeinternalname(datt.getAtributedatatypeinternalname());
									newitemvalue.setValue(formatedValue);
									newitemvalue.setVisualorder(datt.getVisualorder());
									newitemvalue.setForvariant(datt.getForvariant());
									newitemvalue.setBullet(datt.getBullet());
									newitemvalue.setCanwrite(datt.getCanwrite());
									newitemvalue.setMandatory(datt.getMandatory());
									newitemvalue.setPicking(false);
									newitemvalue.setUserdatatypeid(datt.getAtributedatatypeid());

									//Buscar los valores seleccionados
									selected_values = attSelectedValues.get(datt.getAttributeid());	
									newitemvalue.setSelectedvalues(selected_values);

									attvalues.add(newitemvalue);									

								}
							}
						}		
					} catch (Exception e) {
						//error = "Ocurrió un error al intentar procesar el atributo " + colheader_name;
						if (datt == null)
						{
							//error = "Solicitud: " + newItem.getId() + " - " +  "Ocurrió un error al intentar procesar la celda: " + colheader_name + " (Fila " + (row.getRowNum() + 1) + "," + "Columna " + k + ")";
							error = "El nombre de la columna no coincide con un atributo válido asociado a la plantilla";
						}
						else{
							//error = "Solicitud: " + newItem.getId() + " - " +  "Ocurrió un error al intentar procesar el atributo: " + datt.getAttributeinternalname() + " (Fila " + (row.getRowNum() + 1) + "," + "Columna " + k + ")";
							error = "Ocurrió un error al intentar procesar el atributo";
						}

						FEPUtils.addErrorResult(rowerrors, (row.getRowNum() + 1), k+1, newItem.getId().toString(),colheader_name, error);							
					}

				}

				//Validar relaciones
				commonDatatManager.validateAttributeRelations(mapDefinAttributes,rowerrors,   attvalues,attSelectedValues,(row.getRowNum() + 1), initparam.getLocale().getLanguage());

				//Agregar valores al item
				CPItemAttributeValueDTO []  newvalues = new  CPItemAttributeValueDTO[attvalues.size()];
				newvalues = attvalues.toArray(newvalues);
				newItem.setAttributevalues(newvalues);
				newItem.setErrors(rowerrors.toArray(new ErrorInfoDTO[rowerrors.size()]));

				newitemsList.add(newItem);

				// Si existe algun error en la fila, no se puede agregar a la lista de retorno.
				if (rowerrors.size() > 0){					
					errors.addAll(rowerrors);
				}

			}// End de for de parseo

			if (errors.size() > 0) {
				// Ordenar errores
				resultDTO.setErrors(errors.toArray(new ErrorInfoDTO[errors.size()]));
				resultDTO = PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P4000");
			}

			resultDTO.setItems(newitemsList.toArray(new CPItemDTO[newitemsList.size()]));

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


	/*private CPItemDTO setCommonArticleData(CPItemDTO it, HashMap<String, String> articleDataItemValueMap) {
		String articlecode = it.getArticlecode();
		ItemAttributeValueDTO[] attvalues = it.getAttributevalues();

		for (ItemAttributeValueDTO attvalue: attvalues) {			
			//Tomar valor de articulo si es no es variante.
			String keyvalue = articlecode + attvalue.getAttributeinternalname();
			String articlevalue = articleDataItemValueMap.get(keyvalue);
			if(articlevalue == null && attvalue.getValue() != null && !attvalue.getValue().isEmpty() && !attvalue.getForvariant() ){
				articleDataItemValueMap.put(keyvalue, attvalue.getValue());
			}
			if(articlevalue != null && !attvalue.getForvariant()){
				attvalue.setValue(articlevalue);	
			}
		}

		return it;
	}*/


	@Override
	public ErrorInfoArrayResultDTO approveItems(Long usertypeid, CPItemDTO[] items, PersonDTO user, String comment) {
		ErrorInfoArrayResultDTO resultDTO = new ErrorInfoArrayResultDTO();
		try {
			return createManager.approveItems(usertypeid, items, user, comment);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public ErrorInfoArrayResultDTO rejectItems(Long usertypeid, CPItemDTO[] items, PersonDTO user, String comment, boolean isclosed) {
		ErrorInfoArrayResultDTO resultDTO = new ErrorInfoArrayResultDTO();
		try {
			return createManager.rejectItems(usertypeid, items, user, comment, isclosed);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public CPItemErrorArrayResultDTO updateBuyerForItemsValues(CPUpdateItemsInitParamDTO initparam) {
		CPItemErrorArrayResultDTO resultDTO = new CPItemErrorArrayResultDTO();
		try {
			return createManager.updateBuyerForItemsValues(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public CPItemSummaryFlowArrayResultDTO getItemsSummaryInFlow(CPItemSummaryFlowInitParamDTO initparam) {
		CPItemSummaryFlowArrayResultDTO resultDTO = new CPItemSummaryFlowArrayResultDTO();
		try {
			return createManager.getItemsSummaryInFlow(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public FileDownloadInfoResultDTO downloadItemsByFilter(CPItemsByFilterInitParamDTO initparam, Long uskey, String fileformat) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		try {
			CPItemArrayResultDTO dataResult = this.getItemsByFilter(initparam);

			if(dataResult != null && !dataResult.getStatuscode().equals("0")){
				resultDTO.setStatuscode(dataResult.getStatuscode());
				resultDTO.setStatusmessage(dataResult.getStatusmessage());
				return resultDTO;
			}

			//Tabla			
			DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_items_by_articletype_title", initparam.getLocale()));

			DataColumn col0 = new DataColumn("request_id", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_request_number", initparam.getLocale()), Long.class);
			DataColumn col1 = new DataColumn("product_sku", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_item_plu", initparam.getLocale()), String.class);
			DataColumn col2 = new DataColumn("provider_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_provider_code", initparam.getLocale()), String.class);
			DataColumn col3 = new DataColumn("provider_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_provider_name", initparam.getLocale()), String.class);
			//DataColumn col2 = new DataColumn("article_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_article_code", initparam.getLocale()), String.class);
			//DataColumn col3 = new DataColumn("product_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_product_code", initparam.getLocale()), String.class);
			DataColumn col4 = new DataColumn("articletype_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_articletype_name", initparam.getLocale()), String.class);
			DataColumn col5 = new DataColumn("product_description", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_product_description", initparam.getLocale()), String.class);
			//DataColumn col6 = new DataColumn("vendor_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_vendor_code", initparam.getLocale()), String.class);
			//DataColumn col7 = new DataColumn("ean_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_ean_code", initparam.getLocale()), String.class);
			//DataColumn col8 = new DataColumn("trade_mark", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_trade_mark", initparam.getLocale()), String.class);
			DataColumn col6 = new DataColumn("creation_date", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_creation_date", initparam.getLocale()), String.class);
			DataColumn col7 = new DataColumn("last_modified", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_last_modified", initparam.getLocale()), String.class);
			DataColumn col8 = new DataColumn("state", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_state", initparam.getLocale()), String.class);

			dt0.addColumn(col0);
			dt0.addColumn(col1);
			dt0.addColumn(col2);
			dt0.addColumn(col3);
			dt0.addColumn(col4);
			dt0.addColumn(col5);
			dt0.addColumn(col6);
			dt0.addColumn(col7);
			dt0.addColumn(col8);
			//			dt0.addColumn(col9);
			//			dt0.addColumn(col10);
			//			dt0.addColumn(col11);

			DataRow row = null;
			if( dataResult != null && dataResult.getValues()!= null && dataResult.getValues().length > 0){
				String creationdate = "";
				String lastmodified  = "";

				for ( CPItemDTO itemDTO : dataResult.getValues()) {
					row = dt0.newRow(); 
					row.setCellValue("request_id", itemDTO.getId());
					row.setCellValue("product_sku", itemDTO.getSku() != null ? itemDTO.getSku().toString() : "");
					row.setCellValue("provider_code", itemDTO.getProvidercode()); //TODO: debe ser el del initparam o del Item?
					row.setCellValue("provider_name", itemDTO.getProvidersocialreason());
					row.setCellValue("articletype_name", itemDTO.getArticletypename());
					row.setCellValue("product_description", itemDTO.getDescription() != null ? itemDTO.getDescription() : "");

					try
					{
						creationdate = itemDTO.getCreationdate().format(FEPUtils.getInstance().getDownloadLocalDateFormat());
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					try
					{
						lastmodified = itemDTO.getLastmodified().format(FEPUtils.getInstance().getDownloadLocalDateFormat());
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}

					row.setCellValue("creation_date", creationdate);
					row.setCellValue("last_modified", lastmodified);
					row.setCellValue("state", itemDTO.getCurrentstatename());

					dt0.addRow(row);
				}
			}

			String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_items_by_articletype_title", initparam.getLocale());
			if(downloadname == null || downloadname.isEmpty() || downloadname.equals("M/R") ){
				downloadname = "Productos por plantilla_";
			}
			String realname = "PRODUCTOS POR PLANTILLA_" +  FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +"."+fileformat;

			resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, fileformat, downloadname, realname, uskey);
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo comercial no disp.
		}
		return resultDTO;
	}


	@Override
	public FileDownloadInfoResultDTO downloadPrivilegesByItemStateAndUserType(CPPrivilegeInitParamDTO initparam, Long uskey, String fileformat) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		try {
			CPPrivilegeArrayResultDTO dataResult = this.getPrivilegies(initparam);


			if(dataResult != null && !dataResult.getStatuscode().equals("0")){
				resultDTO.setStatuscode(dataResult.getStatuscode());
				resultDTO.setStatusmessage(dataResult.getStatusmessage());
				return resultDTO;
			}

			//Tabla			
			DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_privileges_by_itemstate_title", initparam.getLocale()));

			DataColumn col0 = new DataColumn("attribute_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_attribute_name", initparam.getLocale()), String.class);
			DataColumn col1 = new DataColumn("datatype_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_datatype_name", initparam.getLocale()), String.class);
			DataColumn col2 = new DataColumn("privilege", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_privilege", initparam.getLocale()), String.class);

			dt0.addColumn(col0);
			dt0.addColumn(col1);
			dt0.addColumn(col2);

			DataRow row = null;
			if( dataResult != null && dataResult.getValues()!= null && dataResult.getValues().length > 0){

				for ( CPPrivilegeDTO privDTO : dataResult.getValues()) {
					row = dt0.newRow(); 
					row.setCellValue("attribute_name", privDTO.getAttributeinternalname());
					row.setCellValue("datatype_name", privDTO.getDatatypename());

					switch (privDTO.getPrivilegestatus()) {
					case 0: 
						row.setCellValue("privilege", "No Visible" );
						break;

					case 1: 
						row.setCellValue("privilege", "Lectura" );
						break;

					case 2: 
						row.setCellValue("privilege", "Escritura" );
						break;

					default:
						break;
					}

					dt0.addRow(row);
				}
			}

			String downloadname =  I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_privileges_by_itemstate_title", initparam.getLocale());
			if(downloadname == null || downloadname.isEmpty()){
				downloadname = "Privilegios por estado";
			}
			String realname = "PRIVILEGIOS POR ESTADO";
			resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, fileformat, downloadname, realname, uskey);
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P100");// modulo comercial no disp.
		}
		return resultDTO;
	}

	@Override
	public FileDownloadInfoResultDTO downloadItemsByStatusData(CPItemsByStatusInitParamDTO initparam, Long uskey, String fileformat, boolean withSKU) {

		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		try {
			CPItemArrayResultDTO dataResult = createManager.getItemsByStatusData(initparam);

			if(dataResult != null && !dataResult.getStatuscode().equals("0")){
				resultDTO.setStatuscode(dataResult.getStatuscode());
				resultDTO.setStatusmessage(dataResult.getStatusmessage());
				return resultDTO;
			}

			//Tabla			
			DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_items_by_state_title", initparam.getLocale()));

			DataColumn col0 = new DataColumn("request_id", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_request_number", initparam.getLocale()), Long.class);
			DataColumn col1 = new DataColumn("provider_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_provider_code", initparam.getLocale()), String.class);
			DataColumn col2 = new DataColumn("provider_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_provider_name", initparam.getLocale()), String.class);
			DataColumn col3 = new DataColumn("created_by", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_created_by", initparam.getLocale()), String.class);
			DataColumn col4 = new DataColumn("articletype_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_articletype_name", initparam.getLocale()), String.class);
			DataColumn col5 = new DataColumn("creation_date", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_creation_date", initparam.getLocale()), String.class);
			DataColumn col6 = new DataColumn("last_modified", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_last_modified", initparam.getLocale()), String.class);
			DataColumn col7 = new DataColumn("product_sku", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_item_plu", initparam.getLocale()), String.class);
			DataColumn col8 = new DataColumn("product_description", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_product_description", initparam.getLocale()), String.class);
			DataColumn col9 = new DataColumn("state", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_state", initparam.getLocale()), String.class);

			dt0.addColumn(col0);
			dt0.addColumn(col1);
			dt0.addColumn(col2);
			dt0.addColumn(col3);
			dt0.addColumn(col4);
			dt0.addColumn(col5);
			dt0.addColumn(col6);

			if (withSKU)
				dt0.addColumn(col7);

			dt0.addColumn(col8);
			dt0.addColumn(col9);

			DataRow row = null;

			if( dataResult != null && dataResult.getValues()!= null && dataResult.getValues().length > 0){

				String creationdate = "";
				String lastmodified = "";

				for ( CPItemDTO itemDTO : dataResult.getValues()) {
					row = dt0.newRow(); 
					row.setCellValue("request_id", itemDTO.getId());

					if (withSKU)
						row.setCellValue("product_sku", itemDTO.getSku() != null ? itemDTO.getSku().toString() : "");

					row.setCellValue("provider_code", itemDTO.getProvidercode()); //TODO: debe ser el del initparam o del Item?
					row.setCellValue("provider_name", itemDTO.getProvidersocialreason());
					row.setCellValue("articletype_name", itemDTO.getArticletypename());
					row.setCellValue("product_description", itemDTO.getDescription() != null ? itemDTO.getDescription() : "");

					try
					{
						creationdate = itemDTO.getCreationdate().format(FEPUtils.getInstance().getDownloadLocalDateFormat());
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}

					try
					{
						lastmodified = itemDTO.getLastmodified().format(FEPUtils.getInstance().getDownloadLocalDateFormat());
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					row.setCellValue("creation_date", creationdate);
					row.setCellValue("last_modified", lastmodified);
					row.setCellValue("state", itemDTO.getCurrentstatename());
					row.setCellValue("created_by", itemDTO.getResponsablename());

					dt0.addRow(row);
				}
			}
			String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_items_by_state_title", initparam.getLocale());
			if(downloadname == null || downloadname.isEmpty() || downloadname.equals("M/R") ){
				downloadname = "Productos por estado_";
			}
			String realname = "PRODUCTOS POR ESTADO_";
			resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, fileformat, downloadname, realname, uskey);
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo comercial no disp.
		}
		return resultDTO;
	}


	@Override
	public FileDownloadInfoResultDTO downloadExcelWithItemsToEdit(CPArticleTypeInitParamDTO initparam, CPItemDTO[] items, boolean includeAleas, Long userKey) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		//Filas y Columnas para la Subida y Descarga de los items
		int item_id_column 				= 0;
		int provider_code_column 		= 1;
		int provider_rsocial_column 	= 2;
		int first_data_column 			= 3;

		int groupheader_row_index 	= 0;	// FILA de Grupos relacionados con los tipos de atributos variantes o genéricos.
		int help_row_index 			= 1;	// Fila de ayuda del atributo
		//int madatory_row_index 		= 2;	// Fila que indica si es obligatorio.
		int header_row_index 		= 2;	// Fila donde se coloca el 
		int first_data_row 			= 3;	//Fila a partir de la cual se colocan los datos

		//Atributos relacionados a la plantilla
		LinkedHashMap<String,DefinableAttributeDataDTO> mapDefinAttributes = new LinkedHashMap<String,DefinableAttributeDataDTO> ();
		LinkedHashMap<String,DefinableAttributeDataDTO> mapSortedDefinAtt = new LinkedHashMap<String,DefinableAttributeDataDTO> ();

		LinkedHashMap<String , Integer> columnlist = new LinkedHashMap<String , Integer>();//lista de columnas en funcion de su nombre interno

		//LinkedHashMap<Integer,String> mapEditables = new LinkedHashMap<Integer,String>();

		try
		{
			//Buscar la plantilla
			initparam.setIncludeAttributeInfo(true);
			ArticleTypeResultDTO articleTypeRes = this.getArticleTypeWithCPPrivilegies(initparam);
			if( articleTypeRes == null || (articleTypeRes!= null && !articleTypeRes.getStatuscode().equals("0")) ){
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10020");
			}
			else{
				if(articleTypeRes.getDefattributes() !=  null && articleTypeRes.getDefattributes().length > 0 ){
					mapDefinAttributes = articleTypeRes.getDefinableAttributesMap();

					mapSortedDefinAtt = mapDefinAttributes.entrySet()
							.stream()
							.sorted((f1, f2) -> Integer.compare(f1.getValue().getVisualorder(), f2.getValue().getVisualorder()))
							.sorted((f1, f2) -> Long.compare(f1.getValue().getAttributeinfo().getAttribute().getAttributetypeid(), f2.getValue().getAttributeinfo().getAttribute().getAttributetypeid()))
							.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
				}
				else{
					return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10021");
				}

			}

			String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_items_edition_info_title", initparam.getLocale());
			if(downloadname == null || downloadname.isEmpty() || downloadname.equals("M/R")){
				downloadname = "Productos en edición_";
			}
			downloadname += FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +".xlsx";
			String realname = "PRODUCTOS EN EDICIÓN_" +  FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +".xlsx";

			// Crear el Libro y las Hojas usando POI
			XSSFWorkbook workbook = new XSSFWorkbook();
			//CreationHelper createHelper = workbook.getCreationHelper();

			// HOJA Principal
			XSSFSheet sheet = workbook.createSheet("ATRIBUTOS DE PRODUCTOS");
			//Hola de Datos para hacer referencias.
			XSSFSheet valuesheet = workbook.createSheet("VALORESATRIBUTOS");

			Row groupheader_row = sheet.createRow(groupheader_row_index);
			groupheader_row.setHeight((short) 900 );			
			Row helprow = sheet.createRow(help_row_index);
			helprow.setHeight((short) 900 );			
			//Row mandatoryrow = sheet.createRow(madatory_row_index);			
			//Row vendorname_row = sheet.createRow(vendorname_row_index);			
			Row headrow = sheet.createRow(header_row_index);

			//Estilos
			CellStyle helpcs = FEPUtils.getInstance().getHelpColumnCellStyle(workbook);
			CellStyle headercs = FEPUtils.getInstance().getHeaderCellStyle(workbook);
			CellStyle stringcs = FEPUtils.getInstance().getValueCellStyle(workbook);
			//CellStyle linkcs = FEPUtils.getLinkCellStyle(workbook);
			CellStyle stringscsgrey = FEPUtils.getInstance().getValueCellStyleGrey(workbook);
			CellStyle numerics = FEPUtils.getInstance().getNumericValueCellStyle(workbook);
			CellStyle numericsgrey = FEPUtils.getInstance().getNumericValueCellStyleGrey(workbook);

			//Ayuda
			Cell cellhp = helprow.createCell(item_id_column);					cellhp.setCellValue(item_id_column_name);				cellhp.setCellStyle(helpcs);
			cellhp = helprow.createCell(provider_code_column);					cellhp.setCellValue(provider_code_column_name);			cellhp.setCellStyle(helpcs);
			cellhp = helprow.createCell(provider_rsocial_column);				cellhp.setCellValue(provider_rsocial_column_name);		cellhp.setCellStyle(helpcs);

			//Obligatoriedad
			/*String mandatory_name = "Obligatorio";
			Cell cellm = mandatoryrow.createCell(item_id_column);			cellm.setCellValue(mandatory_name);						cellm.setCellStyle(helpcs);	
			cellm = mandatoryrow.createCell(provider_code_column);			cellm.setCellValue(mandatory_name);						cellm.setCellStyle(helpcs);	
			cellm = mandatoryrow.createCell(provider_rsocial_column);		cellm.setCellValue(mandatory_name);	    				cellm.setCellStyle(helpcs);	*/

			//Encabezado
			Cell cellh = headrow.createCell(item_id_column);				cellh.setCellValue(item_id_column_name);				cellh.setCellStyle(headercs);
			cellh = headrow.createCell(provider_code_column);				cellh.setCellValue(provider_code_column_name);			cellh.setCellStyle(headercs);
			cellh = headrow.createCell(provider_rsocial_column);			cellh.setCellValue(provider_rsocial_column_name);		cellh.setCellStyle(headercs);

			int itemscount = items != null ? items.length : 0;
			//Lista de valores	
			XSSFDataValidationHelper validationHelper = new XSSFDataValidationHelper(sheet);
			//Crear las columnas dinámicas en base a los atributos definidos. Además buscar en caso que sean listas los valores.			
			Cell cell = null;
			Iterator<DefinableAttributeDataDTO> it = mapSortedDefinAtt.values().iterator();
			int columindex = first_data_column;
			int valuerowindex = 0; //Fila por la cual comenzar a crear los valores.
			ArrayList<String> values = new ArrayList<>();

			int group_init_index = 0;
			int group_end_index = columindex-1 ;

			String  last_header = "INFORMACIÓN SOLICITUD";

			while (it.hasNext())		{
				DefinableAttributeDataDTO da = it.next();

				//Header El nombre interno
				cell = headrow.createCell(columindex);
				cell.setCellValue(da.getAttributeinternalname());
				cell.setCellStyle(headercs);

				// Si el grupo no ha cambiado se incrementa el numero de la columna
				// Sino se crea un nuevo header en el rango 
				if(last_header.equals(da.getAttributetypename())){
					group_end_index ++;				
				}
				else if (!last_header.equals(da.getAttributetypename()) || !it.hasNext() ){				
					cell = groupheader_row.createCell(group_init_index);
					cell.setCellValue(last_header);
					cell.setCellStyle(helpcs);
					CellRangeAddressList group_header_range = new CellRangeAddressList(groupheader_row.getRowNum(),groupheader_row.getRowNum(),group_init_index , group_end_index);
					if(group_init_index != group_end_index){
						try {
							sheet.addMergedRegion(group_header_range.getCellRangeAddress(0));		
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("Ocurrieron errores intentando crear un grupo para el atributo: " + da.getAttributeinternalname());
							return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10005");
						}
					}

					//crear el group header				
					group_init_index = group_end_index+1;
					group_end_index = group_init_index;	
					last_header = da.getAttributetypename();
				}

				if (!it.hasNext()){
					cell = groupheader_row.createCell(group_init_index);
					cell.setCellValue(last_header);
					cell.setCellStyle(helpcs);
					CellRangeAddressList group_header_range = new CellRangeAddressList(groupheader_row.getRowNum(),groupheader_row.getRowNum(),group_init_index , group_end_index);
					if(group_init_index != group_end_index){
						try {
							sheet.addMergedRegion(group_header_range.getCellRangeAddress(0));		
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("Ocurrieron errores intentando crear un grupo para el atributo: " + da.getAttributeinternalname());
							return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10005");
						}
					}

				}

				//Help
				cell = helprow.createCell(columindex);
				cell.setCellValue(da.getAttributeuserhelp());
				cell.setCellStyle(helpcs);	

				//Mandatory
				/*cell = mandatoryrow.createCell(columindex);
				boolean mandatory = da.getMandatory() && da.getCanwrite();

				if (mandatory){
					cell.setCellValue( "Obligatorio \n (Editable)");
					mapEditables.put(columindex, da.getAtributedatatypeinternalname());
				}
				else if(da.getCanwrite()){
					cell.setCellValue( "Editable");
					mapEditables.put(columindex, da.getAtributedatatypeinternalname());
				}

				cell.setCellStyle(helpcs);	*/
				columnlist.put(da.getAttributeinternalname(), columindex);	

				boolean makeList = false;
				if (  FEPUtils.attributeIsValueList(da.getAtributedatatypeinternalname() )){	
					values.clear();
					AttributeValueDTO[] valuesDTO = da.getAttributeinfo() != null ? da.getAttributeinfo().getValues() : null;
					if(valuesDTO == null){
						AttributeResultDTO attvaluesres = commonDatatManager.getAttribute(da.getAttributeid(), initparam.getLocale().getLanguage());
						if (attvaluesres != null && attvaluesres.getStatuscode().equals("0") && attvaluesres.getValues() != null && attvaluesres.getValues().length >0){
							valuesDTO = attvaluesres.getValues();
						}
					}

					if(valuesDTO !=null && valuesDTO.length >0){
						Arrays.stream(valuesDTO).forEach(value-> values.add(value.getValue()));	
						makeList = true;
					}
				}

				if (makeList){
					if(values != null){
						//Llenar datos en una columna de la hora de valores con los datos de la lista
						try {

							Row valuerow = valuesheet.createRow(valuerowindex);
							int i = 0;
							for (String value : values) {								
								cell = valuerow.createCell(i++);
								cell.setCellValue(value);
								cell.setCellStyle(stringcs);		
							}
							String lascolumnname = CellReference.convertNumToColString(values.size()-1);
							// Crear un nombre el el libro asociado a la columna de datos previamente creada
							Name namedCell = workbook.createName();
							namedCell.setNameName("Fila_"+ valuerowindex); // nombre del atributo
							String formulaStr = valuesheet.getSheetName() + "!$A$" + (valuerowindex+1) + ":$" + lascolumnname +  "$" + (valuerowindex+1);
							namedCell.setRefersToFormula(formulaStr);
							DataValidationConstraint constraint = validationHelper.createFormulaListConstraint("Fila_"+ valuerowindex);

							CellRangeAddressList addressList = new CellRangeAddressList(first_data_row, first_data_row + itemscount,columindex , columindex);
							DataValidation dataValidation = validationHelper.createValidation(constraint,addressList);
							sheet.addValidationData(dataValidation);

							valuerowindex++;
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					else{
						logger.error("Ocurrieron errores intentando cargar lista de valores del atributo: " + da.getAttributeinternalname());
						return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");// no se pudo generar el archivo
					}

				}

				//Permitir que los valores booleanos sean seleccionables
				if (da.getAtributedatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_BOOLEAN)){
					FEPUtils.saveBooleanTypeToSheet(sheet, valuesheet, first_data_row, columindex, valuerowindex, itemscount, validationHelper);
					valuerowindex++;
				}

				columindex++;
			}

			//Al llegar acá todas las columnas están creadas y listos para barrer los items.
			if (items != null && items.length > 0 ){
				// Crear un mapa con los items y todos su valores
				for (int i = 0; i < items.length ; i++  ){
					// Tomar la lista de valores de cada item y crear las columnas dinamicas
					CPItemDTO item = items[i];
					ItemAttributeValueDTO[] itemvalues = item.getAttributevalues();
					Row row = sheet.createRow(i+first_data_row); // Salta en encabezado

					cell = row.createCell(item_id_column);
					cell.setCellValue(item.getId());
					cell.setCellStyle(stringcs);

					cell = row.createCell(provider_code_column);
					cell.setCellValue(item.getProvidercode());
					cell.setCellStyle(stringcs);

					cell = row.createCell(provider_rsocial_column);
					cell.setCellValue(item.getProvidersocialreason());
					cell.setCellStyle(stringcs);

					//Colocar los datos  dinámicos
					if ( itemvalues != null && itemvalues.length >0  ) {					
						for (int j = 0; j<itemvalues.length;j++ ){
							ItemAttributeValueDTO ivalue = itemvalues[j];
							String attval = null;

							boolean isNumeric = false;
							boolean isCanwrite = false;

							//Verificar si existe  y agregar el valor correspondiente
							if (columnlist.containsKey(ivalue.getAttributeinternalname())){
								int columnindex = columnlist.get(ivalue.getAttributeinternalname());
								cell = row.createCell(columnindex);
								//DefinableAttributeDataDTO definableAtt = mapSortedDefinAtt.get(ivalue.getAttributeinternalname().trim());

								attval = ivalue.getValue() != null ? ivalue.getValue() : "" ;

								if( !attval.isEmpty() && ivalue.getUserdatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_BOOLEAN) ){		
									ivalue.setValue(FEPUtils.getBooleanValueByLanguage(ivalue.getValue(),initparam.getLocale()));
									attval = ivalue.getValue();
									cell.setCellValue(attval);

								} 
								/*else if (!attval.isEmpty() && !ivalue.getValue().isEmpty() && FEPUtils.attributeIsaFile(definableAtt.getAtributedatatypeinternalname())){
								//Salvar nombre y link
								//String filename = FEPUtils.getDescriptionFromCodedValue(ivalue.getValue(), MDMConstants.LIST_SEPARATOR);
								//String linktofile = FEPUtils.getCodeFromCodedValue(ivalue.getValue(), MDMConstants.LIST_SEPARATOR);
								cell.setCellValue(ivalue.getValue());
								Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
								link.setAddress(ivalue.getValue());
								cell.setHyperlink(link);
								cell.setCellStyle(linkcs); 

							} */
								else if ( !attval.isEmpty() && (ivalue.getUserdatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_FLOAT) || 
										ivalue.getUserdatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_INTEGER))){

									Double doublevalue = 0D;

									try {
										doublevalue = FEPUtils.getDecimalFormat(attval);
									} catch (ParseException e) {
										e.printStackTrace();
										return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
									}

									cell.setCellValue(doublevalue);
									isNumeric = true;

								} else{
									cell.setCellValue(attval);
								}
							}	

							if(attval != null){

								isCanwrite = mapSortedDefinAtt.containsKey(ivalue.getAttributeinternalname()) && mapSortedDefinAtt.get(ivalue.getAttributeinternalname()).getCanwrite();

								if (isCanwrite && isNumeric){
									cell.setCellStyle(numerics);

								} else if (isNumeric && !isCanwrite){
									cell.setCellStyle(numericsgrey);

								} else if (isCanwrite && !isNumeric){
									cell.setCellStyle(stringcs);

								} else{
									cell.setCellStyle(stringscsgrey);
								}
							}
						}

					}

					for (int k = 0 ; k < headrow.getLastCellNum(); k++){
						cell = row.getCell(k);
						if (cell == null){
							cell = row.createCell(k);
							cell.setCellValue("");
							cell.setCellStyle(stringcs);
							/*if (mapEditables.containsKey(k)){
								if ( mapEditables.get(k).equals("Float") ||  mapEditables.get(k).equals("Integer"))
									cell.setCellStyle(numericcs);
								else
									cell.setCellStyle(stringcs);
							}
							else{
								cell.setCellStyle(stringscsgrey);	
							}*/

						}
					}
				}

			}

			//Se ajustan todas las celdas...
			for (int i=0; i < sheet.getLastRowNum(); i++){
				for (int j=0; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++){
					sheet.autoSizeColumn(j);
				}
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


	@Override
	public CPItemErrorArrayResultDTO uploadExcelWithEditedItems(CPArticleTypeInitParamDTO initparam, CPItemDTO[] items, String filename, Long userKey) {
		CPItemErrorArrayResultDTO resultDTO = new CPItemErrorArrayResultDTO();


		//Filas y Columnas para la Subida y Descarga de los items
		int item_id_column 				= 0;
		int provider_code_column 		= 1;
		int provider_rsocial_column 	= 2;
		int first_data_column 			= 3;

		int header_row_index 		= 2;	// Fila donde se coloca el 
		int first_data_row 			= 3;	//Fila a partir de la cual se colocan los datos

		ArrayList<ErrorInfoDTO> errors = new ArrayList<>();

		List<CPItemDTO> newitemsList = new ArrayList<CPItemDTO>();

		String file = PortalConstants.getInstance().getFileUploadPath() + userKey + "/" + filename;
		String extension = filename.substring(filename.lastIndexOf('.') + 1);

		boolean editmode = !initparam.getItemstatename().equals(FEPConstants.PROVIDER_NEW_NAME_STATUS);

		ProviderArrayResultDTO providers = null;

		HashMap<String, ProviderDTO> providermaps = new HashMap<String, ProviderDTO>();

		try {
			providers = commonDatatManager.getProviders();
		} catch (Exception e) {
			;
		}

		if (providers != null && providers.getStatuscode().equals("0") ){
			Arrays.stream(providers.getValues()).forEach(provider -> providermaps.put(provider.getProvidercode(), provider));
		}

		try
		{
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
				PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P4000");
			} catch (Exception e) {
				e.printStackTrace();
				PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P4000");
			}

			//Atributos relacionados a la plantilla
			LinkedHashMap<String,DefinableAttributeDataDTO> mapDefinAttributes = null;
			Map<String,String> mapMandatoryDefinAttributes = null;
			//LinkedHashMap<String , Integer> columnlist = new LinkedHashMap<String , Integer>();//lista de columnas en funcion de su nombre interno

			//Buscar la plantilla
			initparam.setIncludeAttributeInfo(true);
			ArticleTypeResultDTO articleTypeRes = this.getArticleTypeWithCPPrivilegies(initparam);
			if( articleTypeRes == null || (articleTypeRes!= null && !articleTypeRes.getStatuscode().equals("0")) ){
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10020");
			}
			else{
				if(articleTypeRes.getDefattributes() != null && articleTypeRes.getDefattributes().length > 0 ){
					mapDefinAttributes = articleTypeRes.getDefinableAttributesMap();
					mapMandatoryDefinAttributes = 	articleTypeRes.getMandatoryDefinableAttributesMap();				
				}
				else{
					return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10021");
				}

			}

			Sheet sheet = null;
			String error = "";

			// Hoja principal
			sheet = wb1.getSheetAt(0);

			//Buscar las columnas definidas
			Row headerrow = sheet.getRow(header_row_index);

			// VALIDA SI EL ARCHIVO ESTA VACIO
			if (sheet.getPhysicalNumberOfRows() == 0) {
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10004");		
			}


			boolean allcolumnOK = false;

			// Valida si primera fila corresponde a encabezado con las columnas obligatorias
			if (  sheet.getRow(header_row_index).getCell(item_id_column) != null && sheet.getRow(header_row_index).getCell(item_id_column).toString().equals(item_id_column_name) &&
					sheet.getRow(header_row_index).getCell(provider_code_column) != null && sheet.getRow(header_row_index).getCell(provider_code_column).toString().equals(provider_code_column_name)
					) {	
				allcolumnOK = true;
			}

			if (!allcolumnOK)
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10003");

			//verificar que los atributos sean consistentes con la plantilla y privilegios que se tienen
			HashSet<String> attributecolums = new HashSet<String>();
			for (int l= first_data_column; l< headerrow.getLastCellNum() ; l++){
				String columName = headerrow.getCell(l).getStringCellValue().trim();
				if (!columName.isEmpty()){
					attributecolums.add(headerrow.getCell(l).getStringCellValue().trim());
					//verificar que el nombre de la columna corresponda con una atributo definido en la plantilla					
					boolean attExist = mapDefinAttributes.containsKey(columName);
					if(!attExist){
						error = "La plantilla no contiene el atributo " + columName;
						FEPUtils.addErrorResult(errors, header_row_index+1, l, error);
					}					
				}				
			}

			//Verificar que no falten atributos obligatorios
			if(mapMandatoryDefinAttributes != null && mapMandatoryDefinAttributes.size() > 0){
				for(String attname : mapMandatoryDefinAttributes.keySet()){
					if(!attributecolums.contains(attname)){
						error = "Falta el atributo obligatorio: " + attname;
						FEPUtils.addErrorResult(errors, header_row_index+1, -1, error);
					}
				}
			}

			//Mapa en base de SKUS
			// SI estoy en modo de edición hago un mapa para subir solo los items que pide front end
			HashMap<Long, CPItemDTO> itemMaps = new HashMap<Long, CPItemDTO>();

			if(editmode && items!= null && items.length > 0){
				Arrays.stream(items).forEach(item -> itemMaps.put(item.getId(), item));
			}

			//Mapa para tener los valores seleccionados en los atributos tipo lista
			HashMap<Long, List<AttributeValueDTO> > attSelectedValues = new HashMap<>();

			List<ErrorInfoDTO> rowerrors = new ArrayList<ErrorInfoDTO>();
			//List<ErrorInfoDTO> itemserrors = new ArrayList<ErrorInfoDTO>();

			// cada Fila se corresponde con un item
			for (int j = first_data_row; j <= sheet.getLastRowNum(); j++) {
				attSelectedValues.clear();
				rowerrors.clear();

				Row row = sheet.getRow(j);
				if (row == null)
					continue;				

				CPItemDTO newItem = null;
				String itemidStr = "";

				String vendorcodeStr = FEPUtils.getValueData("String",row.getCell(provider_code_column),row.getCell(provider_code_column).getCellType()); 
				String socialreasonStr = FEPUtils.getValueData("String",row.getCell(provider_rsocial_column),row.getCell(provider_rsocial_column).getCellType());

				//Verificar validez del proveedor
				boolean isprovider = providermaps.get(vendorcodeStr) != null;

				// verificación de que existan datos no nulos
				try {
					itemidStr = FEPUtils.getValueData("Integer",row.getCell(item_id_column),row.getCell(item_id_column).getCellType()); 
				} catch (Exception e) {
					;
				}

				if(editmode && (itemidStr == null || itemidStr.isEmpty())){
					error = "No existen datos para el id de la solicitud" + " - " + "(Fila " + (row.getRowNum() + 1) + "," + "Columna " + 2 + ")";
					FEPUtils.addErrorResult(rowerrors, j, 2, error);
				}

				if(vendorcodeStr == null || vendorcodeStr.isEmpty()){
					error = "No existen datos para el código del proveedor" + " - " + "(Fila " + (row.getRowNum() + 1) + "," + "Columna " + 3 + ")";
					FEPUtils.addErrorResult(rowerrors, j, 3, error);
				}
				else if (vendorcodeStr != null && !vendorcodeStr.isEmpty() && !isprovider ) {
					error = "El nombre del proveedor " + vendorcodeStr + " no es un proveedor válido " + " - " + "(Fila " + (row.getRowNum() + 1) + "," + "Columna " + 3 + ")";
					FEPUtils.addErrorResult(rowerrors, j, 3, error);
				}

				if(socialreasonStr == null || socialreasonStr.isEmpty()){
					error = "No existen datos para la razón social del proveedor" + " - " + "(Fila " + (row.getRowNum() + 1) + "," + "Columna " + 4 + ")";
					FEPUtils.addErrorResult(rowerrors, j, 4, error);
				}

				//Buscar a que item pertenece la fila
				if (editmode && itemMaps.containsKey(Long.parseLong(itemidStr)))
				{
					newItem = itemMaps.get(Long.parseLong(itemidStr));
				}

				if (newItem != null){

					if (!editmode && isprovider){
						ProviderDTO prov = providermaps.get(vendorcodeStr);

						newItem.setProviderid(prov.getId());
						newItem.setProvidercode(prov.getProvidercode());
						newItem.setProvidersocialreason(prov.getSocialreason());
					}

					HashMap<Long, CPItemAttributeValueDTO> attValuesMap = new HashMap<>();
					//List<ItemAttributeValueDTO> actualvalues = new ArrayList<ItemAttributeValueDTO>();
					if (newItem.getAttributevalues() != null && newItem.getAttributevalues().length > 0){
						Arrays.stream(newItem.getAttributevalues()).forEach(attvalue -> attValuesMap.put(attvalue.getAttributeid(), attvalue));
					}

					// Atributes Values

					List<ItemAttributeValueDTO> attvalues = new ArrayList<ItemAttributeValueDTO>();
					attvalues.clear();

					for (int k = first_data_column; k < headerrow.getLastCellNum(); k++) {	
						List<AttributeValueDTO> selected_values =  null;
						CPItemAttributeValueDTO newitemvalue  = new CPItemAttributeValueDTO();
						String colheader_name = headerrow.getCell(k).getStringCellValue().trim();
						Cell cell = row.getCell(k);

						String cellvalue = null;
						DefinableAttributeDataDTO datt = mapDefinAttributes.get(colheader_name);
						try {
							if (cell == null){
								cellvalue = FEPUtils.getValueData(datt.getAtributedatatypeinternalname(),cell, HSSFCell.CELL_TYPE_BLANK);		
							}
							else{
								cellvalue = FEPUtils.getValueData(datt.getAtributedatatypeinternalname(),cell, cell.getCellType());
							}

							if (datt != null){
								if (datt.getMandatory() && datt.getCanwrite() && (cellvalue == null || cellvalue.isEmpty()) ){
									//error = "Solicitud: " + newItem.getId() + " - " +  "Campo obligatorio: " + datt.getAttributeinternalname() + " (Fila " + (row.getRowNum() + 1) + "," + "Columna " + k + ")";
									error =  "Campo obligatorio vacío";
									FEPUtils.addErrorResult(rowerrors, (row.getRowNum() + 1), k+1,newItem.getId().toString(),datt.getAttributeinternalname(), error);
									continue;
								}

								if(datt.getCanwrite()){
									String formatedValue = cellvalue != null && !cellvalue.isEmpty() ? cellvalue : "";									

									//fromatear el valor si es lista o codificacion etc.
									if(cellvalue != null && !cellvalue.isEmpty()){
										formatedValue = commonDatatManager.formatValueData(cellvalue, datt, rowerrors,attSelectedValues, j, k, newItem.getId().toString(),datt.getAttributeinternalname(), initparam.getLocale().getLanguage()); //Formatear.
									}

									if(formatedValue != null){
										newitemvalue.setAttributeid(datt.getAttributeid());
										newitemvalue.setAttributeinternalname(datt.getAttributeinternalname());
										newitemvalue.setAttributevendorname(datt.getAttributevendorname());
										newitemvalue.setUserdatatypeinternalname(datt.getAtributedatatypeinternalname());
										newitemvalue.setValue(formatedValue);
										newitemvalue.setVisualorder(datt.getVisualorder());
										newitemvalue.setForvariant(datt.getForvariant());
										newitemvalue.setBullet(datt.getBullet());
										newitemvalue.setCanwrite(datt.getCanwrite());
										newitemvalue.setMandatory(datt.getMandatory());
										newitemvalue.setPicking(false);
										newitemvalue.setUserdatatypeid(datt.getAtributedatatypeid());

										//Buscar los valores seleccionados
										selected_values = attSelectedValues.get(datt.getAttributeid());	
										newitemvalue.setSelectedvalues(selected_values);

										//attvalues.add(newitemvalue);		
										attValuesMap.put(newitemvalue.getAttributeid(), newitemvalue);

									}

								}
							}		
						} catch (Exception e) {
							//error = "Ocurrió un error al intentar procesar el atributo " + colheader_name;
							if (datt == null)
							{
								//error = "Solicitud: " + newItem.getId() + " - " +  "Ocurrió un error al intentar procesar la celda: " + colheader_name + " (Fila " + (row.getRowNum() + 1) + "," + "Columna " + k + ")";
								error = "El nombre de la columna no coincide con un atributo válido asociado a la plantilla";
							}
							else{
								//error = "Solicitud: " + newItem.getId() + " - " +  "Ocurrió un error al intentar procesar el atributo: " + datt.getAttributeinternalname() + " (Fila " + (row.getRowNum() + 1) + "," + "Columna " + k + ")";
								error = "Ocurrió un error al intentar procesar el atributo";
							}

							FEPUtils.addErrorResult(rowerrors, (row.getRowNum() + 1), k+1, newItem.getId().toString(),colheader_name, error);							
						}

					}

					attvalues = attValuesMap.values()
							.stream()
							.collect(Collectors.toList());

					//Validar relaciones
					commonDatatManager.validateAttributeRelations(mapDefinAttributes,rowerrors,   attvalues,attSelectedValues,(row.getRowNum() + 1), initparam.getLocale().getLanguage());

					//Agregar valores al item
					CPItemAttributeValueDTO []  newvalues = new  CPItemAttributeValueDTO[attvalues.size()];
					newvalues = attvalues.toArray(newvalues);

					newItem.setAttributevalues(newvalues);
					newItem.setErrors(rowerrors.toArray(new ErrorInfoDTO[rowerrors.size()]));
					newitemsList.add(newItem);
				}
				else{
					error = "El producto no pertenece a la lista de productos vigente. " + " fila " + (row.getRowNum() + 1);
					FEPUtils.addErrorResult(rowerrors, (row.getRowNum() + 1), 3, null,null, error);
				}

				// Si existe algun error en la fila, no se puede agregar a la lista de retorno.
				if (rowerrors.size() > 0){					
					errors.addAll(rowerrors);
				}


			}// End de for de parseo

			if (errors.size() > 0) {
				// Ordenar errores
				resultDTO.setErrors(errors.toArray(new ErrorInfoDTO[errors.size()]));
				resultDTO = PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P4000");
			}

			resultDTO.setItems(newitemsList.toArray(new CPItemDTO[newitemsList.size()]));

		}

		catch (Exception e)
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}

		return resultDTO;	
	}


	@Override
	public FileDownloadInfoResultDTO downloaStandardProductSheet(DefinableAttributeInitParamDTO initparam, CPItemDTO[] items, boolean includeAleas, boolean includePosibleValues, Long userKey, Locale locale) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		//Filas y Columnas para la Subida y Descarga de los items
		//int article_code_column 		= 0;
		//int provider_code_column 		= 0;
		//int provider_name_column 		= 1;
		int sku_column 					= 0;
		//int product_description_column 	= 3;
		//int trade_column				= 4;


		//String article_code_column_name = "Código del Artículo";
		//String provider_code_column_name = "Código de Proveedor";
		//String provider_name_column_name = "Nombre de Proveedor";
		String sku_column_name = "SKU del Producto";
		//String product_description_column_name = "Descripcion del Producto";
		//String trade_column_name ="Marca del Producto";

		int header_row_index 		= 0;	// Fila donde se coloca el nombre del atributo plantilla retail
		int att_row_index           = 1;    // Nombre de los atributos homologados
		int first_data_row 			= 2;	// Fila a partir de la cual se colocan los datos (values)

		String skudetail = "1";

		try {
			skudetail = B2BSystemPropertiesUtil.getProperty("SKU_DETAIL");			
		} catch (Exception e) {
			e.printStackTrace();
		}

		int first_data_column = skudetail.equals("0") ? 0 : 1;

		//Atributos relacionados a la plantilla destino (Retail)
		List <DefinableAttributeDataDTO> sortedAttList = new ArrayList<>();

		//Lista de columnas en funcion de su nombre interno
		LinkedHashMap<String , Integer> columnlist = new LinkedHashMap<String , Integer>();

		//Informacion de la plantilla homologada y sus atributos
		StandardArticleTypeArrayResultDTO targetResult = new StandardArticleTypeArrayResultDTO ();
		LinkedHashMap<String, StandardArticleTypeResultDTO> mapDefinTargetAttributes = new LinkedHashMap<> ();

		//Verificar existencia de la plantilla
		ArticleTypeInitParamDTO initParam1 = new ArticleTypeInitParamDTO ();
		initParam1.setId(initparam.getArticletypeid());
		ArticleTypeArrayResultDTO artsTypeResult = commonDatatManager.getArticleTypeData(initParam1	, 1, 10000, false, null, locale.getLanguage());

		if(artsTypeResult != null && !artsTypeResult.getStatuscode().equals("0")){
			resultDTO.setStatuscode(artsTypeResult.getStatuscode());
			resultDTO.setStatusmessage(artsTypeResult.getStatusmessage());
			return resultDTO;
		}

		String arttypename = artsTypeResult.getValues()[0].getName();
		
		Map<String, List<String>> allAttributesMap = null;

		try
		{
			//Buscar los definables sin privilegios de la plantilla destino
			DefinableAttributeArrayResultDTO definableattributes = commonDatatManager.getDefinableAttributesByArticleType(initparam);

			if( definableattributes == null || (definableattributes != null && !definableattributes.getStatuscode().equals("0")) ){
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10020");
			}
			else{
				if(definableattributes.getValues() != null && definableattributes.getValues().length > 0 ){
					sortedAttList = Arrays.asList(definableattributes.getValues());
					sortedAttList.sort((o1, o2) -> Integer.compare (o1.getVisualorder(), (o2.getVisualorder()) ));

				}
				else{
					return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10021");
				}
			}

			//Obtener informacion de la plantilla homologada
			ArticleTypeByClientInitParamDTO filterinitparam = new ArticleTypeByClientInitParamDTO ();
			filterinitparam.setArticletypeid(initparam.getArticletypeid());
			filterinitparam.setClientname(initparam.getClientname());

			targetResult = commonDatatManager.getStandardizedAttributes(filterinitparam);
			if( targetResult == null || (targetResult != null && !targetResult.getStatuscode().equals("0")) ){
				resultDTO.setStatuscode(targetResult.getStatuscode());
				resultDTO.setStatusmessage(targetResult.getStatusmessage());
				return resultDTO;
			}
			else{
				if(targetResult.getValues() != null && targetResult.getValues().length > 0 ){

					allAttributesMap =  Arrays.stream(targetResult.getValues())
							.collect(Collectors.groupingBy(StandardArticleTypeResultDTO::getAttributetargetname, 
									Collectors.mapping(StandardArticleTypeResultDTO::getInternalname, Collectors.toList())));
					
					Arrays.stream(targetResult.getValues()).forEach(defatt->mapDefinTargetAttributes.put(defatt.getAttributetargetname(), defatt));
				}
				else{
					return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P11002");
				}
			}

			//Obtener los valores de atributos de cada items
			CPItemValueByItemsInitParamDTO itemparam = new CPItemValueByItemsInitParamDTO();
			itemparam.setItems(items);
			CPItemArrayResultDTO itemresult = createManager.getItemValuesByItems(itemparam);

			if (itemresult == null || !itemresult.getStatuscode().equals("0") || itemresult.getValues() == null || itemresult.getValues().length <= 0){
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P11004");
			}

			String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_products_sheet_title", initparam.getLocale());

			if(downloadname == null || downloadname.isEmpty() || downloadname.equals("M/R")){
				downloadname = "Ficha de Productos";
			}
			downloadname += "_" +  "[" + arttypename + "]_" + FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +".xlsx";
			String realname = "FICHA DE PRODUCTOS_" + "[" + arttypename + "]_" + FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +".xlsx";

			// Crear el Libro y las Hojas usando POI
			XSSFWorkbook workbook = new XSSFWorkbook();
			CreationHelper createHelper = workbook.getCreationHelper();

			// HOJA Principal
			XSSFSheet sheet = workbook.createSheet("INFORMACIÓN DE PRODUCTOS");

			Row headrow = sheet.createRow(header_row_index);

			Row attrow = sheet.createRow(att_row_index);

			//Estilos
			CellStyle helpcs 	= FEPUtils.getInstance().getHelpColumnCellStyle(workbook);
			CellStyle headercs 	= FEPUtils.getInstance().getHeaderCellStyle(workbook);
			CellStyle valuecs 	= FEPUtils.getInstance().getValueCellStyle(workbook);
			CellStyle linkcs 	= FEPUtils.getLinkCellStyle(workbook);

			//Ayuda
			if (skudetail.equals("1")){
				Cell cellh = headrow.createCell(sku_column);				cellh.setCellValue(sku_column_name);					cellh.setCellStyle(headercs);
				Cell cella = attrow.createCell(sku_column);					cella.setCellValue(sku_column_name);					cella.setCellStyle(helpcs);
			}

			//Crear las columnas dinámicas en base a los atributos definidos. Además buscar en caso que sean listas los valores.			
			Cell cell = null;
			//Iterator<DefinableAttributeDataDTO> it = mapDefinAttributes.values().iterator();
			Iterator<DefinableAttributeDataDTO> it = sortedAttList.iterator();
			int columindex = first_data_column;

			while (it.hasNext())		{
				DefinableAttributeDataDTO da = it.next();

				//Header El nombre interno
				cell = headrow.createCell(columindex);
				cell.setCellValue(da.getAttributeinternalname());
				cell.setCellStyle(headercs);

				List<String> sda = allAttributesMap.get(da.getAttributeinternalname());
				String cellattnames = sda != null ? "[ " + String.join(";", sda) + " ]": "";

				cell = attrow.createCell(columindex);
				cell.setCellValue(cellattnames);
				cell.setCellStyle(helpcs);	

				columnlist.put(da.getAttributeinternalname(), columindex);	

				columindex++;
			}
			
			//Al llegar acá todas las columnas están creadas y listos para barrer los items.
			if (itemresult.getValues() != null && itemresult.getValues().length > 0 ){
				// Crear un mapa con los items y todos su valores
				for (int i = 0; i < itemresult.getValues().length ; i++  ){
					// Tomar la lista de valores de cada item y crear las columnas dinamicas
					CPItemDTO item = itemresult.getValues()[i];
					LinkedHashMap<String, ItemAttributeValueDTO> mapItemValuetAttributes = new LinkedHashMap<String, ItemAttributeValueDTO> ();

					ItemAttributeValueDTO[] itemvalues = item.getAttributevalues();
					if (itemvalues == null || itemvalues.length <= 0){
						continue;
					}

					Arrays.stream(itemvalues).forEach(defatt->mapItemValuetAttributes.put(defatt.getAttributeinternalname(), defatt));	

					Row row = sheet.createRow(i+first_data_row); // Salta en encabezado

					if (skudetail.equals("1")){
						cell = row.createCell(sku_column);
						cell.setCellValue(item.getSku());
						cell.setCellStyle(valuecs);
					}

					//Colocar los datos  dinámicos en base a los atributos de la plantilla homologada
					Iterator<StandardArticleTypeResultDTO> iter = mapDefinTargetAttributes.values().iterator();

					while (iter.hasNext())		{
						StandardArticleTypeResultDTO ivalue = iter.next();

						//Obtener el nombre el atributo llave de cada posicion en el mapa para identificar si existe como columna
						if (columnlist.containsKey(ivalue.getAttributetargetname())){
							int columnindex = columnlist.get(ivalue.getAttributetargetname());
							cell = row.createCell(columnindex);

							//Verificar si existe  y agregar el valor correspondiente
							String stringvalue = "";
							int count = 0;

							boolean hasLink = false;
							Hyperlink link = null;

							for (String value: allAttributesMap.get(ivalue.getAttributetargetname()) ){

								String attval = null;

								if (mapItemValuetAttributes.containsKey(value)){
									ItemAttributeValueDTO attvalue = mapItemValuetAttributes.get(value);

									attval = attvalue != null &&  attvalue.getValue() != null ? attvalue.getValue() : "" ;

									if( !attval.isEmpty() && attvalue.getUserdatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_BOOLEAN) ){		
										attvalue.setValue(FEPUtils.getBooleanValueByLanguage(attvalue.getValue(),initparam.getLocale()));
										stringvalue += attvalue.getValue();

									} else if(!attval.isEmpty() && attvalue.getMetadata() != null && !attvalue.getMetadata().isEmpty() && FEPUtils.attributeIsaFile(attvalue.getUserdatatypeinternalname())){
										link = createHelper.createHyperlink(Hyperlink.LINK_URL);
										link.setAddress(attvalue.getMetadata());

										stringvalue += attvalue.getMetadata();

										hasLink = true;

									} else if ( !attval.isEmpty() && (attvalue.getUserdatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_FLOAT) || 
											attvalue.getUserdatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_INTEGER))){

										Double doublevalue = 0D;

										try {
											doublevalue = FEPUtils.getDecimalFormat(attval);
										} catch (ParseException e) {
											e.printStackTrace();
											return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
										}
										stringvalue += doublevalue;

									} else{
										stringvalue += (attvalue.getValue() != null && !attvalue.getValue().isEmpty()) ? attvalue.getValue() : "";
									}		
									
									count++;

									if ((allAttributesMap.get(ivalue.getAttributetargetname())).size() > 1 && count < (allAttributesMap.get(ivalue.getAttributetargetname())).size()){
										stringvalue += ";";
									}
										
								}

							}

							if (stringvalue != ""){
								
								if ( stringvalue.length() > 0 && stringvalue.charAt(stringvalue.length() - 1) == ';'){
									stringvalue += "no-value";
								}
								
								if (stringvalue.contains(";") || (!stringvalue.contains(";") && !hasLink)){
									cell.setCellValue(stringvalue);
									cell.setCellStyle(valuecs);
								}else if (hasLink){
									cell.setCellValue(stringvalue);
									cell.setHyperlink(link);
									cell.setCellStyle(linkcs); 
								}

							}else{
								cell.setCellValue("");
								cell.setCellStyle(valuecs);
							}
						}					 

					}

					for (int k = 0 ; k < headrow.getLastCellNum(); k++){
						cell = row.getCell(k);
						if (cell == null){
							cell = row.createCell(k);
							cell.setCellValue("");
							cell.setCellStyle(valuecs);
						}
					}

				}

			}

			//Se ajustan todas las celdas...
			for (int i=0; i < sheet.getLastRowNum(); i++){
				for (int j=0; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++){
					sheet.autoSizeColumn(j);
				}
			}

			// Crear Archivo xls	
			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(FEPUtils.getInstance().getFileTransferPath() + realname);
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


	@Override
	public CPItemStateArrayResultDTO getItemsStateByProviderWorkFlow(String providercode) {
		CPItemStateArrayResultDTO result = new CPItemStateArrayResultDTO();
		try {
			return createManager.getItemsStatesByProviderWorkFlow(providercode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public BaseResultDTO removeItems(CPItemDTO[] itemsDTO, Long usertypeid, PersonDTO user) {
		BaseResultDTO result = new BaseResultDTO();
		try {
			return createManager.removeItems(itemsDTO, usertypeid, user);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");// modulo fep no disp.
		}
	}


	@Override
	public CPItemErrorArrayResultDTO updateItemsStatus(CPItemDTO[] items, Long usertypeid, String newstate, PersonDTO person) {
		CPItemErrorArrayResultDTO result = new CPItemErrorArrayResultDTO();
		try {
			return createManager.updateItemsStatus(items, usertypeid, newstate, person);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");// modulo fep no disp.
		}
	}


	@Override
	public FileDownloadInfoResultDTO downloadPdfProductDatasheet(CPItemsByIDsInitParamDTO initparam, Long userKey) {
		FileDownloadInfoResultDTO resultDTO =  new FileDownloadInfoResultDTO();

		AttributesNotImageDTO[] attNotImageDTO = null;
		AttributesImageDTO[] attImageDTO = null;

		try {
			CPItemTechnicalInfoArrayResultDTO prodData = new CPItemTechnicalInfoArrayResultDTO();
			try {
				prodData = createManager.getItemTechnicalInfoByID(initparam);
			}catch (Exception e) {
				e.printStackTrace();
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
			}		

			if (prodData.getValues()[0].getNotImageAttributeValues() == null || prodData.getValues()[0].getNotImageAttributeValues().size() <= 0){
				//			resultDTO.setStatuscode(prodData.getStatuscode());
				//			resultDTO.setStatusmessage(prodData.getStatusmessage());
				//			return resultDTO;
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P11003");
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat completeDateFormat = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");

			// Construcción del PDF
			// Obtener el nombre del archivo		
			String downloadfilename = "PRODUCT_" + prodData.getValues()[0].getItem().getArticletypename() + "_" + dateFormat.format(new Date()) + ".pdf";
			String realfilename = "PRODUCT_" + prodData.getValues()[0].getItem().getArticletypename() + "_"+ dateFormat.format(new Date()) + " hrs_"+ userKey + ".pdf";

			// Setear la ruta de los subreportes
			File reports = new File (B2BSystemPropertiesUtil.getProperty("fep_pdfreports"));
			reports.mkdirs();

			String pathJasperProductImages	= reports.getPath() + "/Product_PPAL_AttImages_FEP.jasper";
			String pathJasperProductAttributes	= reports.getPath() + "/Product_PPAL_AttNotImages_FEP.jasper";
			String pathJasperProductImagesRP	= reports.getPath() + "/Product_REL_AttImages_FEP.jasper";
			String pathJasperProductAttributesRP	= reports.getPath() + "/Product_REL_AttNotImages_FEP.jasper";

			List<CPItemAttributeValueDTO> attNotImages = prodData.getValues()[0].getNotImageAttributeValues();
			List<CPItemAttributeValueDTO> attImages= prodData.getValues()[0].getImageAttributeValues();

			HashMap<String, List<AttributesNotImageDTO>> attNotImageMap = new HashMap<>();
			HashMap<String, List<AttributesImageDTO>> attImageMap = new HashMap<>();

			try {

				//Setear las listas de campos que se le pasan al reporte
				ProductReportDTO pdf = new ProductReportDTO();
				
				String value = "";

				// Setear parámetros del reporte
				HashMap <String,Object> parameters = new HashMap<String,Object> ();

				parameters.put("ARTICLE_CODE", prodData.getValues()[0].getItem().getArticlecode());
				parameters.put("LONG_DESCRIPTION",  prodData.getValues()[0].getItem().getDescription());
				parameters.put("ARTICLETYPE_NAME",  prodData.getValues()[0].getItem().getArticletypename());
				parameters.put("SKU",   prodData.getValues()[0].getItem().getSku());
				parameters.put("STATE",  prodData.getValues()[0].getItem().getCurrentstatename());
				parameters.put("PRODUCT_IMAGES", pathJasperProductImages);
				parameters.put("PRODUCT_ATTRIBUTES", pathJasperProductAttributes);
				parameters.put("RELATED_PRODUCT_IMAGES", pathJasperProductImagesRP);
				parameters.put("RELATED_PRODUCT_ATTRIBUTES", pathJasperProductAttributesRP);

				//Setear el DTO para los atributos que no son del Tipo Imagen
				attNotImageDTO = new AttributesNotImageDTO[attNotImages.size()];


				for (CPItemAttributeValueDTO att: attNotImages)
				{
					value = ( att.getUnit() != null && !att.getUnit().isEmpty() ) ? ( att.getValue() + " " + att.getUnit() ) : att.getValue();
					
					attNotImageDTO [attNotImages.indexOf(att)] = new AttributesNotImageDTO ();
					attNotImageDTO [attNotImages.indexOf(att)].setTypename(att.getTypename());
					attNotImageDTO [attNotImages.indexOf(att)].setAttributevendorname(att.getAttributevendorname());
					attNotImageDTO [attNotImages.indexOf(att)].setValue(value);
					attNotImageDTO [attNotImages.indexOf(att)].setVisualorder(att.getTypevisualorder());
				}

				if (attImages != null && attImages.size() > 0)
				{
					// Construcción del directorio temporal para almacenar los archivos tipo imagenes
					File tempdir = new File (PortalConstants.getInstance().getFileTransferPath() + "IMAGES_" + userKey + "_"+ completeDateFormat.format(new Date()) + " hrs_");
					tempdir.mkdirs();

					//Descarga todas las imagenes al directorio temporal
					fileStorageManager.downloadCPFileImagesObjects(attImages, tempdir.getPath());

					//Setear el DTO para los atributos que son del Tipo Imagen
					attImageDTO = new AttributesImageDTO[attImages.size()];

					for (CPItemAttributeValueDTO att: attImages)
					{
						attImageDTO [attImages.indexOf(att)] = new AttributesImageDTO ();
						attImageDTO [attImages.indexOf(att)].setAttributevendorname(att.getAttributevendorname());
						attImageDTO [attImages.indexOf(att)].setValue((tempdir.getPath() + "/" + FEPUtils.getFilenameByUri(att.getMetadata())));

					}
				}
				else{
					// Construcción del directorio temporal para almacenar los archivos tipo imagenes
					File tempdir = new File (PortalConstants.getInstance().getFileTransferPath() + "IMAGES_" + userKey + "_"+ completeDateFormat.format(new Date()) + " hrs_");
					tempdir.mkdirs();

					//Descarga todas las imagenes al directorio temporal
					fileStorageManager.downloadCPFileImagesObjects(null, tempdir.getPath());

					attImageDTO = new AttributesImageDTO[2];

					attImageDTO [0] = new AttributesImageDTO ();
					attImageDTO [0].setAttributevendorname("IMAGEN FRONTAL");
					attImageDTO [0].setValue((tempdir.getPath() + "/" + FEPUtils.getFilenameByUri(B2BSystemPropertiesUtil.getProperty("DEFAULT_IMAGE"))));

					attImageDTO [1] = new AttributesImageDTO ();
					attImageDTO [1].setAttributevendorname("IMAGEN LATERAL");
					attImageDTO [1].setValue((tempdir.getPath() + "/" + FEPUtils.getFilenameByUri(B2BSystemPropertiesUtil.getProperty("DEFAULT_IMAGE"))));
				}

				pdf.setAttNotImageDTO(Arrays.asList(attNotImageDTO));
				pdf.setAttImageDTO(Arrays.asList(attImageDTO));

				List<AttributesNotImageDTO> attNotImageList = new ArrayList<>();
				List<AttributesImageDTO> attImageList = new ArrayList<>();

				if (prodData.getValues().length > 1){
					for (int i=1; i< prodData.getValues().length; i++){
						//Setear el DTO para los atributos que no son del Tipo Imagen
						attNotImages = prodData.getValues()[i].getNotImageAttributeValues();
						attNotImageDTO = new AttributesNotImageDTO[attNotImages.size()];

						for (CPItemAttributeValueDTO att: attNotImages)
						{
							value = ( att.getUnit() != null && !att.getUnit().isEmpty() ) ? ( att.getValue() + " " + att.getUnit() ) : att.getValue();
							
							attNotImageDTO [attNotImages.indexOf(att)] = new AttributesNotImageDTO ();
							attNotImageDTO [attNotImages.indexOf(att)].setTypename(att.getTypename());
							attNotImageDTO [attNotImages.indexOf(att)].setAttributevendorname(att.getAttributevendorname());
							attNotImageDTO [attNotImages.indexOf(att)].setValue(value);
							attNotImageDTO [attNotImages.indexOf(att)].setSku(prodData.getValues()[i].getItem().getSku());
							attNotImageDTO [attNotImages.indexOf(att)].setVisualorder(att.getTypevisualorder());
						}

						//						attNotImageMap.put(prodData.getValues()[i].getItem().getSku(), 
						//											Arrays.asList(attNotImageDTO));

						attNotImageList.addAll(Arrays.asList(attNotImageDTO));

						attImages= prodData.getValues()[i].getImageAttributeValues();

						if (attImages != null && attImages.size() > 0)
						{
							// Construcción del directorio temporal para almacenar los archivos tipo imagenes
							File tempdir = new File (PortalConstants.getInstance().getFileTransferPath() + "IMAGES_" + userKey + "_"+ completeDateFormat.format(new Date()) + " hrs_");
							tempdir.mkdirs();

							//Descarga todas las imagenes al directorio temporal
							fileStorageManager.downloadCPFileImagesObjects(attImages, tempdir.getPath());

							//Setear el DTO para los atributos que son del Tipo Imagen
							attImageDTO = new AttributesImageDTO[attImages.size()];

							for (CPItemAttributeValueDTO att: attImages)
							{
								attImageDTO [attImages.indexOf(att)] = new AttributesImageDTO ();
								attImageDTO [attImages.indexOf(att)].setAttributevendorname(att.getAttributevendorname());
								attImageDTO [attImages.indexOf(att)].setValue((tempdir.getPath() + "/" + FEPUtils.getFilenameByUri(att.getMetadata())));
								attImageDTO [attImages.indexOf(att)].setSku(prodData.getValues()[i].getItem().getSku());
							}
						}
						else{
							// Construcción del directorio temporal para almacenar los archivos tipo imagenes
							File tempdir = new File (PortalConstants.getInstance().getFileTransferPath() + "IMAGES_" + userKey + "_"+ completeDateFormat.format(new Date()) + " hrs_");
							tempdir.mkdirs();

							//Descarga todas las imagenes al directorio temporal
							fileStorageManager.downloadCPFileImagesObjects(null, tempdir.getPath());

							attImageDTO = new AttributesImageDTO[2];

							attImageDTO [0] = new AttributesImageDTO ();
							attImageDTO [0].setAttributevendorname("IMAGEN FRONTAL");
							attImageDTO [0].setValue((tempdir.getPath() + "/" + FEPUtils.getFilenameByUri(B2BSystemPropertiesUtil.getProperty("DEFAULT_IMAGE"))));
							attImageDTO [0].setSku(prodData.getValues()[i].getItem().getSku());

							attImageDTO [1] = new AttributesImageDTO ();
							attImageDTO [1].setAttributevendorname("IMAGEN LATERAL");
							attImageDTO [1].setValue((tempdir.getPath() + "/" + FEPUtils.getFilenameByUri(B2BSystemPropertiesUtil.getProperty("DEFAULT_IMAGE"))));
							attImageDTO [1].setSku(prodData.getValues()[i].getItem().getSku());
						}

						//						attImageMap.put(prodData.getValues()[i].getItem().getSku(), 
						//								Arrays.asList(attImageDTO));

						attImageList.addAll(Arrays.asList(attImageDTO));

					}

					pdf.setAttNotImageRelatedList(attNotImageList);
					pdf.setAttImageRelatedList(attImageList);
				}

				List<ProductReportDTO> list = Arrays.asList(pdf);

				// Ruta de los archivos JRXML
				String pathMain = reports.getPath() + "/Productdatasheet_FEP.jrxml";
				String pathProductImages = reports.getPath() + "/Product_PPAL_AttImages_FEP.jrxml";
				String pathProductAttributes = reports.getPath() + "/Product_PPAL_AttNotImages_FEP.jrxml";
				String pathProductImagesRP = reports.getPath() + "/Product_REL_AttImages_FEP.jrxml";
				String pathProductAttributesRP = reports.getPath() + "/Product_REL_AttNotImages_FEP.jrxml";

				// Compilamos el .jrxml y lo cargamos
				final JasperDesign design = JRXmlLoader.load(pathMain);
				final JasperReport report = JasperCompileManager.compileReport(design);	

				// Compilamos los subreportes
				JasperCompileManager.compileReportToFile(pathProductImages);
				JasperCompileManager.compileReportToFile(pathProductAttributes);
				JasperCompileManager.compileReportToFile(pathProductImagesRP);
				JasperCompileManager.compileReportToFile(pathProductAttributesRP);

				// Rellenamos el report con los parámetros
				final JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(list));

				// Exportamos a PDF
				JasperExportManager.exportReportToPdfFile(print, PortalConstants.getInstance().getFileTransferPath() + realfilename);

			} catch (JRException jre) {
				jre.printStackTrace();

			} catch (Exception e) {
				e.printStackTrace();
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
			}

			resultDTO.setDownloadfilename(downloadfilename);
			resultDTO.setRealfilename(realfilename);	

		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}

		return resultDTO;
	}


	@Override
	public FileDownloadInfoResultDTO downloadRelatedProducts(Long articletypeid, CPItemDTO[] items, Long uskey) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		List <DefinableAttributeDataDTO> sortedAttList = new ArrayList<>();

		LinkedHashMap<String , Integer> rowlist = new LinkedHashMap<>();//lista de filas en funcion de su nombre interno del atributo
		LinkedHashMap<String , Integer> rowArticletypeList = new LinkedHashMap<>();//lista de filas por plantillas
		LinkedHashMap<Long , Integer> colMainProductList = new LinkedHashMap<>();//lista de filas por plantillas
		int articletypeNum = 0;

		Hyperlink link = null;
		String stringvalue = "";
		boolean hasLink = false;

		//		LocalTime starthour = null;
		//		LocalTime endhour = null;
		//		Duration duration = null;

		try {

			//			starthour = LocalTime.now();

			//Buscar la plantilla
			//Verificar existencia de la plantilla
			ArticleTypeInitParamDTO initParam = new ArticleTypeInitParamDTO ();
			initParam.setId(articletypeid);
			ArticleTypeArrayResultDTO artsTypeResult = commonDatatManager.getArticleTypeData(initParam, 1, 10000, false, null, initParam.getLocale().getLanguage());

			if(artsTypeResult != null && !artsTypeResult.getStatuscode().equals("0")){
				resultDTO.setStatuscode(artsTypeResult.getStatuscode());
				resultDTO.setStatusmessage(artsTypeResult.getStatusmessage());
				return resultDTO;
			}

			//Buscar los definables sin privilegios de la plantilla destino
			DefinableAttributeInitParamDTO definitparam = new DefinableAttributeInitParamDTO();
			definitparam.setArticletypeid(artsTypeResult.getValues()[0].getId());

			DefinableAttributeArrayResultDTO definableattributes = commonDatatManager.getDefinableAttributesByArticleType(definitparam);

			if( definableattributes == null || (definableattributes != null && !definableattributes.getStatuscode().equals("0")) ){
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10020");
			}
			else{
				if(definableattributes.getValues() != null && definableattributes.getValues().length > 0 ){
					sortedAttList = Arrays.asList(definableattributes.getValues());
					sortedAttList.sort((o1, o2) -> Integer.compare (o1.getVisualorder(), (o2.getVisualorder()) ));

				}
				else{
					return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10021");
				}
			}

			String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_related_product_info_title", initParam.getLocale());
			if(downloadname == null || downloadname.isEmpty() || downloadname.equals("M/R")){
				downloadname = "Información-Productos-Complementarios_";
			}
			downloadname += FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +".xlsx";
			String realname = "INFORMACIÓN-PRODUCTOS-COMPLEMENTARIOS_" +  FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +".xlsx";

			// Crear el Libro y las Hojas usando POI
			XSSFWorkbook workbook = new XSSFWorkbook();
			CreationHelper createHelper = workbook.getCreationHelper();

			// HOJA Principal
			XSSFSheet sheet = workbook.createSheet("ATRIBUTOS - PRODUCTOS COMPLEMENTARIOS");

			int header_row_index = 0;
			int first_data_row   = 1;
			Row headrow = sheet.createRow(header_row_index);

			CellStyle headercs 	= FEPUtils.getInstance().getHeaderCellStyle(workbook);
			CellStyle valuecs 	= FEPUtils.getInstance().getValueCellStyle(workbook);
			CellStyle linkcs 	= FEPUtils.getLinkCellStyle(workbook);

			Iterator<DefinableAttributeDataDTO> it = sortedAttList.iterator();
			int firstDataColum = 1;

			Cell cellh = headrow.createCell(0);				cellh.setCellValue(artsTypeResult.getValues()[0].getName());   cellh.setCellStyle(headercs);
			rowArticletypeList.put(artsTypeResult.getValues()[0].getName(), header_row_index);

			if (items != null && items.length > 0){
				int columindex = 0;
				for (CPItemDTO item: items){
					columindex ++;
					cellh = headrow.createCell(columindex);				
					cellh.setCellValue(item.getSku());   
					cellh.setCellStyle(headercs);

					colMainProductList.put(item.getId(), columindex);

				}

			}

			Cell cell = null;
			int rowindex = first_data_row;

			while (it.hasNext())		{
				DefinableAttributeDataDTO da = it.next();

				//Header El nombre interno del atributo
				Row attrow = sheet.createRow(rowindex);
				cell = attrow.createCell(0);
				cell.setCellValue(da.getAttributevendorname());
				cell.setCellStyle(valuecs);

				rowlist.put(da.getAttributeinternalname() + "_" + articletypeNum, rowindex);	

				rowindex++;
			}

			//Obtener los valores de atributos de cada items
			CPItemValueByItemsInitParamDTO itemparam = new CPItemValueByItemsInitParamDTO();
			itemparam.setItems(items);
			CPItemArrayResultDTO itemresult = createManager.getItemValuesByItems(itemparam);

			if (itemresult == null || !itemresult.getStatuscode().equals("0") || itemresult.getValues() == null || itemresult.getValues().length <= 0){
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P11004");
			}

			Row rowinit = null; 

			for (int i = 0; i < itemresult.getValues().length ; i++ ){
				// Tomar la lista de valores de cada item y crear las filsas dinamicas
				CPItemDTO item = itemresult.getValues()[i];

				ItemAttributeValueDTO[] itemvalues = item.getAttributevalues();
				if (itemvalues == null || itemvalues.length <= 0){
					continue;
				}

				for (int j = 0; j<itemvalues.length;j++ ){
					ItemAttributeValueDTO ivalue = itemvalues[j];
					String attval = null;
					attval = ivalue.getValue() != null ? ivalue.getValue() : " " ;

					//Verificar si existe  y agregar el valor correspondientes
					if (rowlist.containsKey(ivalue.getAttributeinternalname() + "_" + articletypeNum)){

						hasLink = false;

						if(!attval.isEmpty() && ivalue.getMetadata() != null && !ivalue.getMetadata().isEmpty() && FEPUtils.attributeIsaFile(ivalue.getUserdatatypeinternalname())){
							link = createHelper.createHyperlink(Hyperlink.LINK_URL);
							link.setAddress(ivalue.getMetadata());
							hasLink = true;

							stringvalue = ivalue.getMetadata();

						} else if(!attval.isEmpty() && ivalue.getValue().contains("http")  ){

							link = createHelper.createHyperlink(Hyperlink.LINK_URL);
							link.setAddress(ivalue.getValue());
							hasLink = true;

							stringvalue = ivalue.getValue();

						} else if ( !attval.isEmpty() && (ivalue.getUserdatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_FLOAT) || 
								ivalue.getUserdatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_INTEGER))){

							Double doublevalue = 0D;
							try {
								doublevalue = FEPUtils.getDecimalFormat(attval);
							} catch (ParseException e) {
								e.printStackTrace();
								return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
							}
							
							stringvalue = doublevalue.toString() + (!ivalue.getUnit().isEmpty() ? " " + ivalue.getUnit() : " ");

						} else{
							stringvalue = attval;
						}

						int rownum = rowlist.get(ivalue.getAttributeinternalname() + "_" + articletypeNum);
						rowinit = sheet.getRow(rownum);
						cell = rowinit.createCell(firstDataColum);

						if (stringvalue != ""){
							if (!hasLink){
								cell.setCellValue(stringvalue);
								cell.setCellStyle(valuecs);
							}else if (hasLink){
								cell.setCellValue(stringvalue);
								cell.setHyperlink(link);
								cell.setCellStyle(linkcs); 
							}

						}else{
							cell.setCellValue("");
							cell.setCellStyle(valuecs);
						}

					}
				}

				firstDataColum++;

			}

			//			endhour = LocalTime.now();
			//			duration = Duration.between(starthour, endhour);
			//
			//			logger.info("TIEMPO PRIMERA PARTE:" + duration.getSeconds() + " (start: " + starthour + ", end: " + endhour + ")");
			//
			//			starthour = LocalTime.now();
			//PRODUCTOS RELACIONADOS, POR PLANTILLA Y POR PRINCIPALES
			CPRelatedProductArrayResultDTO relatedProductResult = new CPRelatedProductArrayResultDTO();

			Long[] itemIds = Arrays.stream(itemresult.getValues())
					.map(x -> x.getId())
					.toArray(Long[]::new);

			relatedProductResult = createManager.getRelatedProductsByMainProducts(itemIds);
			LinkedHashMap<Long, List<CPItemDTO>> relatedItemsMap = new LinkedHashMap<>();
			LinkedHashMap<Long, List<Long>> relatedArticleTypeMap = new LinkedHashMap<>();
			LinkedHashMap<Long, List<Long>> relatedProductsMap = new LinkedHashMap<>();

			if (relatedProductResult != null && relatedProductResult.getValues() != null && relatedProductResult.getValues().length > 0){
				relatedProductsMap = relatedProductResult.getRelatedProductsMap();
				relatedItemsMap = relatedProductResult.getRelatedItemsMap();
				relatedArticleTypeMap = relatedProductResult.getRelatedArticleTypeMap();

			}else{

			}

			//			endhour = LocalTime.now();
			//			duration = Duration.between(starthour, endhour);
			//
			//			logger.info("TIEMPO SEGUNDA PARTE:" + duration.getSeconds() + " (start: " + starthour + ", end: " + endhour + ")");

			Iterator<Entry<Long, List<Long>>> iter = relatedArticleTypeMap.entrySet().iterator();
			while (iter.hasNext()){
				Entry<Long, List<Long>> entry = iter.next();
				Long relatedarticletypeid = entry.getKey();
				List<Long> relatedlist = entry.getValue().stream().distinct().map(x -> x).collect(Collectors.toList());

				//Buscar plantilla y sus definables sin privilegios
				ArticleTypeResultDTO articletypeData = commonDatatManager.getArticleType(relatedarticletypeid, Locale.getDefault().getLanguage());
				List<DefinableAttributeDataDTO> defAtttibutesList = new ArrayList<>();

				if (articletypeData != null && articletypeData.getDefattributes() != null && articletypeData.getDefattributes().length > 0){
					defAtttibutesList = Arrays.asList(articletypeData.getDefattributes());
					defAtttibutesList.sort((o1, o2) -> Integer.compare (o1.getVisualorder(), (o2.getVisualorder()) ));
				}else{
					return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10021");
				}

				HashMap<Long, Integer> articletype_relatedprod_map = new HashMap<>();
				HashMap<Long, Integer> sku_relatedprod_map = new HashMap<>();

				for (Long relID: relatedlist ){
					//SE CONSTRUYE LA FILA CORRESPONDIENTE A LA PLANTILLA
					Iterator<DefinableAttributeDataDTO> itaux = defAtttibutesList.iterator();

					Row auxrow = sheet.createRow(rowindex++);
					Cell cellaux = auxrow.createCell(0);			
					cellaux.setCellValue(articletypeData.getArticletype().getName());   
					cellaux.setCellStyle(headercs);
					articletypeNum++;

					sku_relatedprod_map.put(relID, auxrow.getRowNum());

					//SE LLENAN LAS CELDAS CON LOS NOMBRES DE LOS ATT DE PLANTILLA
					while (itaux.hasNext())	{
						DefinableAttributeDataDTO da = itaux.next();

						//Header El nombre interno del atributo
						Row attrow = sheet.createRow(rowindex);
						cellaux = attrow.createCell(0);
						cellaux.setCellValue(da.getAttributevendorname());
						cellaux.setCellStyle(valuecs);

						rowlist.put(da.getAttributeinternalname() + "_" + articletypeNum , rowindex);	

						rowindex++;

					}

					articletype_relatedprod_map.put(relID, articletypeNum);
				}

				Iterator<Entry<Long, List<Long>>> relatediter = relatedProductsMap.entrySet().iterator();
				Row row = null;
				while (relatediter.hasNext()){
					Entry<Long, List<Long>> entry1 = relatediter.next();
					Long mainproductid = entry1.getKey();
					List<Long> relatedprodlist = entry1.getValue().stream().distinct().map(x -> x).collect(Collectors.toList());

					List<CPItemDTO> itemListDTO = relatedItemsMap.get(mainproductid);

					int columnid = 0;

					if (colMainProductList.containsKey(mainproductid)){
						columnid = colMainProductList.get(mainproductid);
					}

					for (Long relatedID: relatedlist){
						if (relatedprodlist.contains(relatedID)){

							//Buscar el producto relacionado correspondiente y que está asociado a este producto principal
							//Iterator<Entry<Long, HashSet<CPItemDTO>>> iterItemDTO = relatedItemsMap.entrySet().iterator();
							//							while (iterItemDTO.hasNext()){
							//								Entry<Long, HashSet<CPItemDTO>> entry2 = iterItemDTO.next();
							//								Long mainprodItemID= entry2.getKey();
							//								HashSet<CPItemDTO> itemDTOList = entry2.getValue();

							boolean isPresent = false;

							for (CPItemDTO dto: itemListDTO){
								if (isPresent)
									break;

								if (dto.getId().equals(relatedID)){

									isPresent = true;
									int atnum = 0;

									if (articletype_relatedprod_map.containsKey(dto.getId()))
										atnum = articletype_relatedprod_map.get(dto.getId());

									int skucell = 0;
									int count = 0;

									if (sku_relatedprod_map.containsKey(dto.getId())){
										skucell = sku_relatedprod_map.get(dto.getId());

										for (CPRelatedProductDTO rpDto: relatedProductResult.getValues()){
											if (rpDto.getMainproductid().equals(mainproductid) && 
													rpDto.getRelatedproductid().equals(dto.getId())){

												count = rpDto.getCount();
												break;
											}
										}
									}

									Row auxrow = sheet.getRow(skucell);
									cell = auxrow.createCell(columnid);			
									cell.setCellValue(dto.getSku() + " (Cant: " + count + ")");   
									cell.setCellStyle(headercs);

									ItemAttributeValueDTO[] itemvalues = dto.getAttributevalues();
									if (itemvalues == null || itemvalues.length <= 0){
										continue;
									}

									for (int  t= 0; t<itemvalues.length; t++ ){
										ItemAttributeValueDTO ivalue = itemvalues[t];
										String attval = null;
										attval = ivalue.getValue() != null ? ivalue.getValue() : " " ;

										//Verificar si existe  y agregar el valor correspondientes
										if (rowlist.containsKey(ivalue.getAttributeinternalname() + "_" + atnum)){

											hasLink = false;

											if(!attval.isEmpty() && ivalue.getMetadata() != null && !ivalue.getMetadata().isEmpty() 
													&&  FEPUtils.attributeIsaFile(ivalue.getUserdatatypeinternalname())  ){

												link = createHelper.createHyperlink(Hyperlink.LINK_URL);
												link.setAddress(ivalue.getMetadata());
												hasLink = true;

												stringvalue = ivalue.getMetadata();

											}else if(!attval.isEmpty() && ivalue.getValue().contains("http")  ){

												link = createHelper.createHyperlink(Hyperlink.LINK_URL);
												link.setAddress(ivalue.getValue());
												hasLink = true;

												stringvalue = ivalue.getValue();

											} else if ( !attval.isEmpty() && (ivalue.getUserdatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_FLOAT) || 
													ivalue.getUserdatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_INTEGER))){

												Double doublevalue = 0D;
												try {
													doublevalue = FEPUtils.getDecimalFormat(attval);
												} catch (ParseException e) {
													e.printStackTrace();
													return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
												}

												stringvalue = doublevalue.toString() + (!ivalue.getUnit().isEmpty() ? " " + ivalue.getUnit() : " ");

											} else{
												stringvalue = attval;
											}

											int rownum = rowlist.get(ivalue.getAttributeinternalname() + "_" + atnum);
											row = sheet.getRow(rownum);
											cell = row.createCell(columnid);

											if (stringvalue != ""){
												if (!hasLink){
													cell.setCellValue(stringvalue);
													cell.setCellStyle(valuecs);
												}else if (hasLink){
													cell.setCellValue(stringvalue);
													cell.setHyperlink(link);
													cell.setCellStyle(linkcs); 
												}

											}else{
												cell.setCellValue("");
												cell.setCellStyle(valuecs);
											}
										}
									}

								}
							}
							//}
						}
					}
				}

			}

			//			endhour = LocalTime.now();
			//			duration = Duration.between(starthour, endhour);
			//			logger.info("TIEMPO TERCERA PARTE:" + duration.getSeconds() + " (start: " + starthour + ", end: " + endhour + ")");
			//
			//			starthour = LocalTime.now();

			//Ajustar celdas y bordes
			for (int i=0; i < sheet.getLastRowNum(); i++){
				for (int k = 0 ; k < colMainProductList.size() +1; k++){
					cell = sheet.getRow(i).getCell(k);
					if (cell == null){
						cell = sheet.getRow(i).createCell(k);
						cell.setCellValue("");
						cell.setCellStyle(valuecs);
					}
				}
			}

			IntStream.range(0, colMainProductList.size() +1).forEach((columnIndex) -> sheet.autoSizeColumn(columnIndex));

			//			endhour = LocalTime.now();
			//			duration = Duration.between(starthour, endhour);
			//			logger.info("TIEMPO CUARTA PARTE:" + duration.getSeconds() + " (start: " + starthour + ", end: " + endhour + ")");

			// Crear Archivo xls	
			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(FEPUtils.getInstance().getFileTransferPath() + realname);
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


		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}

		return resultDTO;
	}


	@Override
	public CPRelatedProductArrayResultDTO getRelatedProductsByMainProducts(Long[] ids) {
		CPRelatedProductArrayResultDTO result = new CPRelatedProductArrayResultDTO();
		try {
			return createManager.getRelatedProductsByMainProducts(ids);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P800");// modulo fep no disp.
		}
	}

}