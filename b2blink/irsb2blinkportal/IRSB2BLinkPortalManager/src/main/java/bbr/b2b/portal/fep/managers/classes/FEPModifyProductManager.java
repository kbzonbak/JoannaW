package bbr.b2b.portal.fep.managers.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
import bbr.b2b.fep.common.data.classes.ArticleTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeInitParamDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeValueDTO;
import bbr.b2b.fep.common.data.classes.AttributesImageDTO;
import bbr.b2b.fep.common.data.classes.AttributesNotImageDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeDataDTO;
import bbr.b2b.fep.common.data.classes.ErrorInfoArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ErrorInfoDTO;
import bbr.b2b.fep.common.data.classes.ItemAttributeValueDTO;
import bbr.b2b.fep.common.data.classes.PersonDTO;
import bbr.b2b.fep.common.data.classes.ProductReportDTO;
import bbr.b2b.fep.cp.data.classes.NewItemDataDTO;
import bbr.b2b.fep.managers.interfaces.ContextUtil;
import bbr.b2b.fep.managers.interfaces.IModifyProductManager;
import bbr.b2b.fep.mp.data.classes.MPAddNewItemsInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPArticleTypeInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPHistItemArrayResultDTO;
import bbr.b2b.fep.mp.data.classes.MPHistItemDTO;
import bbr.b2b.fep.mp.data.classes.MPHistItemInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPItemArrayResultDTO;
import bbr.b2b.fep.mp.data.classes.MPItemAttributeValueDTO;
import bbr.b2b.fep.mp.data.classes.MPItemByArticleInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPItemDTO;
import bbr.b2b.fep.mp.data.classes.MPItemErrorArrayResultDTO;
import bbr.b2b.fep.mp.data.classes.MPItemResultDTO;
import bbr.b2b.fep.mp.data.classes.MPItemStateArrayResultDTO;
import bbr.b2b.fep.mp.data.classes.MPItemTechnicalInfoResultDTO;
import bbr.b2b.fep.mp.data.classes.MPItemsByArticleTypeArrayResultDTO;
import bbr.b2b.fep.mp.data.classes.MPItemsByArticleTypeDTO;
import bbr.b2b.fep.mp.data.classes.MPItemsByFilterInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPItemsByIDsInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPItemsByStatusInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPItemsResumeDataResultDTO;
import bbr.b2b.fep.mp.data.classes.MPPrivilegeArrayResultDTO;
import bbr.b2b.fep.mp.data.classes.MPPrivilegeDTO;
import bbr.b2b.fep.mp.data.classes.MPPrivilegeInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPProductInfoResumeResultDTO;
import bbr.b2b.fep.mp.data.classes.MPProductStateInfoResumeResultDTO;
import bbr.b2b.fep.mp.data.classes.MPUpdateItemsInitParamDTO;
import bbr.b2b.portal.constants.BbrUtilsResources;
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

@Stateless(name = "managers/FEPModifyProductManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FEPModifyProductManager implements FEPModifyProductManagerLocal {

	@EJB 
	FEPCommonDataManagerLocal commonDataManager;
	
	@EJB
	FEPFileStorageManagerLocal fileStorageManager;
	
	@EJB
	private UserReportManagerLocal userReportManager;

	private IModifyProductManager modifyManager = null;
	//private ICommonDataManager commonDatatManager = null;

	private static LoggingUtil logger = new LoggingUtil(FEPModifyProductManager.class);

	//Filas y Columnas para la Subida y Descarga de los items
	int article_code_column 		= 0;
	int sku_column 					= 1;
	int provider_code_column 		= 2;
	int product_description_column 	= 3;
	int trade_column				= 4;
	int first_data_column 			= 5;

	String article_code_column_name = "Código del Artículo";
	String sku_column_name = "SKU del Producto";
	String provider_code_column_name = "Código de Proveedor";
	String product_description_column_name = "Descripcion del Producto";
	String trade_column_name ="Marca del Producto";

	//int articletype_row_index 	= dinamic_row_index++;	// FILA de dato de plantilla
	//int groupheader_row_index 	= 0;	// FILA de Grupos relacionados con los tipos de atributos variantes o genéricos.
	int help_row_index 			= 0;	// Fila de ayuda del atributo
	int madatory_row_index 		= 1;	// Fila que indica si es obligatorio.
	//int vendorname_row_index 	= 2;	// Fila donde se coloca el nombre del atributo para el proveedor o el áleas en caso de que se solicite
	int header_row_index 		= 2;	// Fila donde se coloca el 
	int first_data_row 			= 3;	//Fila a partir de la cual se colocan los datos

	@PostConstruct
	public void getRemote() {

		try {
			modifyManager = ContextUtil.getInstance().getModifyProductManager();
			//commonDatatManager = ContextUtil.getInstance().getCommonDataManager();
			//userManager = bbr.b2b.users.managers.interfaces.ContextUtil.getInstance().getIUserManager();
		} catch (NamingException e) {
			e.printStackTrace();

		}
	}


	@Override
	//TODO no están las cadenas en recursos de las columnas
	public FileDownloadInfoResultDTO downloadHistItemByID(Long itemid, Long uskey, String fileformat, Locale locale)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		MPHistItemArrayResultDTO histResult = null;

		try {
			MPHistItemInitParamDTO initparami = new MPHistItemInitParamDTO();
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

			DataColumn col0 = new DataColumn("item_plu", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_item_plu", locale), String.class);
			DataColumn col1 = new DataColumn("provider_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "provider_code", locale), String.class);
			DataColumn col2 = new DataColumn("provider_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_provider_name", locale), String.class);
			DataColumn col3 = new DataColumn("state_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_state_name", locale), String.class);
			DataColumn col4 = new DataColumn("state_when", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_state_when", locale), Date.class);
			DataColumn col5 = new DataColumn("state_comment", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_state_comment", locale), String.class);
			DataColumn col6 = new DataColumn("user_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_user_code", locale), String.class);
			DataColumn col7 = new DataColumn("user_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_user_name", locale), String.class);


			dt0.addColumn(col0);
			dt0.addColumn(col1);
			dt0.addColumn(col2);
			dt0.addColumn(col3);
			dt0.addColumn(col4);
			dt0.addColumn(col5);
			dt0.addColumn(col6);
			dt0.addColumn(col7);

			DataRow row = null;
			if( histResult != null && histResult.getHistory()!= null && histResult.getHistory().length > 0){
				MPItemDTO item = histResult.getItem();
				for ( MPHistItemDTO hist : histResult.getHistory()) {
					row = dt0.newRow(); 
					row.setCellValue("item_plu", item.getPlu());
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
					row.setCellValue("state_comment", hist.getComment());	
					row.setCellValue("user_code", hist.getUsercode());	
					row.setCellValue("user_name", hist.getUsername());	
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
	public FileDownloadInfoResultDTO downloadItemsByArticleTypeResume(MPItemByArticleInitParamDTO initparam, Long uskey, String fileformat)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();


		try {
			MPItemsByArticleTypeArrayResultDTO dataResult = this.getItemsByArticleTypeAndTradeResume(initparam, uskey);


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

			dt0.addColumn(col0);
			dt0.addColumn(col1);
			dt0.addColumn(col2);


			DataRow row = null;
			if( dataResult != null && dataResult.getValues()!= null && dataResult.getValues().length > 0){

				for ( MPItemsByArticleTypeDTO itemdata : dataResult.getValues()) {
					row = dt0.newRow(); 
					row.setCellValue("article_internalname", itemdata.getArticletypename());
					row.setCellValue("trade_code", itemdata.getTrade());
					row.setCellValue("item_count", itemdata.getItemcount() != null ? itemdata.getItemcount()  : 0L );

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

				artsTypeResult = commonDataManager.getArticleTypeData(initParam, 1, 10000, false, null, locale.getLanguage());
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
	public MPItemErrorArrayResultDTO uploadNewRequest(String filename, Long uskey, PersonDTO person, Locale locale)
	{
		MPItemErrorArrayResultDTO result = new MPItemErrorArrayResultDTO();
		ArrayList<ErrorInfoDTO> errors = new ArrayList<>();
		String error = "";

		String file = PortalConstants.getInstance().getFileUploadPath() + uskey + "/" + filename;
		String extension = filename.substring(filename.lastIndexOf('.') + 1);

		//Mapa de valores de Plantilla
		HashMap<String, ArticleTypeDataDTO> articletypes_Map = new HashMap<>();


		ArticleTypeInitParamDTO initparam = new ArticleTypeInitParamDTO();
		ArticleTypeArrayResultDTO artTypes = commonDataManager.getArticleTypeData(initparam , 1, 10000, false, null, locale.getLanguage());
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
			//int headerRowindex = 0;

			//Buscar las columnas definidas

			//Row header_row = sheet.getRow(headerRowindex);

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
				MPAddNewItemsInitParamDTO initparamIt = new MPAddNewItemsInitParamDTO();
				//initparamIt.setItems(newItemArr); TODO, actualizar Initparam
				initparamIt.setUser(person);
				initparamIt.setLocale(locale);
				MPItemErrorArrayResultDTO newItemsRes = modifyManager.addOrUpdateNewItems( initparamIt  );//newItemArr, person, locale.getLanguage());
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
	public MPPrivilegeArrayResultDTO getPrivilegies(MPPrivilegeInitParamDTO initparam)
	{
		MPPrivilegeArrayResultDTO resultDTO = new MPPrivilegeArrayResultDTO();
		try {
			return modifyManager.getPrivilegies(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
		
	}


	@Override
	public ErrorInfoArrayResultDTO addOrEditPrivilegies(MPPrivilegeInitParamDTO initparam)
	{
		ErrorInfoArrayResultDTO resultDTO = new ErrorInfoArrayResultDTO();
		try {
			return modifyManager.addOrEditPrivilegies(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public MPItemsByArticleTypeArrayResultDTO getItemsByArticleTypeAndTradeResume(MPItemByArticleInitParamDTO initparam, Long uskey)
	{
		MPItemsByArticleTypeArrayResultDTO resultDTO = new MPItemsByArticleTypeArrayResultDTO();
		try {
			userReportManager.saveCompanySelectedAndAddCountUserProvider(uskey, initparam.getProvidercode());
			return modifyManager.getItemsByArticleTypeAndTradeResume(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public MPItemArrayResultDTO getItemsByFilter(MPItemsByFilterInitParamDTO initparam)
	{
		MPItemArrayResultDTO resultDTO = new MPItemArrayResultDTO();
		try {
			return modifyManager.getItemsByFilter(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public MPItemArrayResultDTO getItemsByIDs(MPItemsByIDsInitParamDTO initparam)
	{
		MPItemArrayResultDTO resultDTO = new MPItemArrayResultDTO();
		try {
			return modifyManager.getItemsByIDs(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public MPItemResultDTO getItemByID(MPItemsByIDsInitParamDTO initparam)
	{
		MPItemResultDTO resultDTO = new MPItemResultDTO();
		try {
			return modifyManager.getItemByID(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public MPItemErrorArrayResultDTO addOrUpdateNewItems(MPAddNewItemsInitParamDTO initparam)
	{
		MPItemErrorArrayResultDTO resultDTO = new MPItemErrorArrayResultDTO();
		try {
			return modifyManager.addOrUpdateNewItems(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public MPItemErrorArrayResultDTO updateItems(MPUpdateItemsInitParamDTO initparam)
	{
		MPItemErrorArrayResultDTO resultDTO = new MPItemErrorArrayResultDTO();
		try {
			return modifyManager.updateItems(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public MPItemStateArrayResultDTO getItemStates(BaseInitParamDTO initparam)
	{
		MPItemStateArrayResultDTO resultDTO = new MPItemStateArrayResultDTO();
		try {
			return modifyManager.getItemStates(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public ArticleTypeResultDTO getArticleTypeWithMPProvilegies(MPArticleTypeInitParamDTO initparam)
	{
		ArticleTypeResultDTO resultDTO = new ArticleTypeResultDTO();
		try {
			return modifyManager.getArticleTypeWithMPProvilegies(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public MPItemTechnicalInfoResultDTO getItemTechnicalInfoByID(MPItemsByIDsInitParamDTO initparam)
	{
		MPItemTechnicalInfoResultDTO resultDTO = new MPItemTechnicalInfoResultDTO();
		try {
			return modifyManager.getItemTechnicalInfoByID(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}

	@Override
	public MPItemsResumeDataResultDTO getItemsResumeData(MPItemByArticleInitParamDTO initparam)
	{
		MPItemsResumeDataResultDTO resultDTO = new MPItemsResumeDataResultDTO();
		try {
			return modifyManager.getItemsResumeData(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}


	@Override
	public MPHistItemArrayResultDTO getHistItemByID(MPHistItemInitParamDTO initparam)
	{
		MPHistItemArrayResultDTO resultDTO = new MPHistItemArrayResultDTO();
		try {
			return modifyManager.getHistItemByID(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}




	@Override
	public MPItemArrayResultDTO getItemsByStatusData(MPItemsByStatusInitParamDTO initparam)
	{
		MPItemArrayResultDTO resultDTO = new MPItemArrayResultDTO();
		try {
			return modifyManager.getItemsByStatusData(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}
	
	@Override
	public MPProductStateInfoResumeResultDTO getProductStateInfoItemsResumeData(MPItemsByStatusInitParamDTO initparam) {
		MPProductStateInfoResumeResultDTO resultDTO = new MPProductStateInfoResumeResultDTO();
		try {
			return modifyManager.getProductStateInfoItemsResumeData(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}




	@Override
	public MPItemsByArticleTypeArrayResultDTO getItemsByArticleTypeResume(MPItemsByFilterInitParamDTO initparam, Long uskey)
	{
		MPItemsByArticleTypeArrayResultDTO resultDTO = new MPItemsByArticleTypeArrayResultDTO();
		try {
			
			if(initparam.getProvidercode() != null && !initparam.getProvidercode().isEmpty())
			{
				userReportManager.saveCompanySelectedAndAddCountUserProvider(uskey, initparam.getProvidercode());	
			}
			

			return modifyManager.getItemsByArticleTypeResume(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}
	
	



	@Override
	public MPProductInfoResumeResultDTO getProductInfoItemsResumeData(MPItemByArticleInitParamDTO initparam)
	{
		MPProductInfoResumeResultDTO resultDTO = new MPProductInfoResumeResultDTO();

		try {
			return modifyManager.getProductInfoItemsResumeData(initparam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
	}

/*
 * 
 * 
 */
	@Override
	public FileDownloadInfoResultDTO downloadExcelItems(MPArticleTypeInitParamDTO initparam, MPItemDTO[] items, boolean includeAleas,boolean includePosibleValues, Long userKey) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		//Atributos relacionados a la plantilla
		LinkedHashMap<String,DefinableAttributeDataDTO> mapDefinAttributes = new LinkedHashMap<String,DefinableAttributeDataDTO> ();
		LinkedHashMap<String , Integer> columnlist = new LinkedHashMap<String , Integer>();//lista de columnas en funcion de su nombre interno

		try
		{
			//Buscar la plantilla
			initparam.setIncludeAttributeInfo(true);
			ArticleTypeResultDTO articleTypeRes = this.getArticleTypeWithMPProvilegies(initparam);
			if( articleTypeRes == null || (articleTypeRes!= null && !articleTypeRes.getStatuscode().equals("0")) ){
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10020");
			}
			else{
				if(articleTypeRes.getDefattributes() !=  null && articleTypeRes.getDefattributes().length > 0 ){
					mapDefinAttributes = articleTypeRes.getDefinableAttributesMap();
					
					mapDefinAttributes.entrySet().stream().sorted((f1, f2) -> Integer.compare(f1.getValue().getVisualorder(), f2.getValue().getVisualorder()));
					
				}
				else{
					return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10021");
				}

			}

			String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_items_info_title", initparam.getLocale());
			if(downloadname == null || downloadname.isEmpty() || downloadname.equals("M/R")){
				downloadname = "Productos_";
			}
			downloadname += FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +".xlsx";
			String realname = "Productos_" +  FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +".xlsx";

			// Crear el Libro y las Hojas usando POI
			XSSFWorkbook workbook = new XSSFWorkbook();
			CreationHelper createHelper = workbook.getCreationHelper();

			// HOJA Principal
			XSSFSheet sheet = workbook.createSheet("ATRIBUTOS DE PRODUCTOS");
			//Hola de Datos para hacer referencias.
			XSSFSheet valuesheet = workbook.createSheet("VALORESATRIBUTOS");

			Row helprow = sheet.createRow(help_row_index);
			helprow.setHeight((short) 900 );			
			Row mandatoryrow = sheet.createRow(madatory_row_index);			
			//Row vendorname_row = sheet.createRow(vendorname_row_index);			
			Row headrow = sheet.createRow(header_row_index);


			//Estilos
			CellStyle helpcs 	= FEPUtils.getInstance().getHelpColumnCellStyle(workbook);
			CellStyle headercs 	= FEPUtils.getInstance().getHeaderCellStyle(workbook);
			CellStyle valuecs 	= FEPUtils.getInstance().getValueCellStyle(workbook);
			CellStyle linkcs 	= FEPUtils.getLinkCellStyle(workbook);


			//Ayuda
			Cell cellhp = helprow.createCell(article_code_column);		cellhp.setCellValue(article_code_column_name);			cellhp.setCellStyle(helpcs);
			cellhp = helprow.createCell(sku_column);					cellhp.setCellValue(sku_column_name);					cellhp.setCellStyle(helpcs);
			cellhp = helprow.createCell(provider_code_column);			cellhp.setCellValue(provider_code_column_name);			cellhp.setCellStyle(helpcs);
			cellhp = helprow.createCell(product_description_column);	cellhp.setCellValue(product_description_column_name);	cellhp.setCellStyle(helpcs);
			cellhp = helprow.createCell(trade_column);					cellhp.setCellValue(trade_column_name);					cellhp.setCellStyle(helpcs);

			//Obligatoriedad
			String mandatory_name = "Obligatorio";
			Cell cellm = mandatoryrow.createCell(article_code_column);   		cellm.setCellValue(mandatory_name);			cellm.setCellStyle(helpcs);	
			cellm = mandatoryrow.createCell(sku_column);						cellm.setCellValue(mandatory_name);			cellm.setCellStyle(helpcs);	
			cellm = mandatoryrow.createCell(provider_code_column);				cellm.setCellValue(mandatory_name);			cellm.setCellStyle(helpcs);	
			cellm = mandatoryrow.createCell(product_description_column);		cellm.setCellValue(mandatory_name);	    	cellm.setCellStyle(helpcs);	
			cellm = mandatoryrow.createCell(trade_column);						cellm.setCellValue(mandatory_name);			cellm.setCellStyle(helpcs);	

			//Encabezado
			Cell cellh = headrow.createCell(article_code_column);		cellh.setCellValue(article_code_column_name);			cellh.setCellStyle(headercs);
			cellh = headrow.createCell(sku_column);						cellh.setCellValue(sku_column_name);					cellh.setCellStyle(headercs);
			cellh = headrow.createCell(provider_code_column);			cellh.setCellValue(provider_code_column_name);			cellh.setCellStyle(headercs);
			cellh = headrow.createCell(product_description_column);		cellh.setCellValue(product_description_column_name);	cellh.setCellStyle(headercs);
			cellh = headrow.createCell(trade_column);					cellh.setCellValue(trade_column_name);					cellh.setCellStyle(headercs);

			//Vendor
			/*Cell cellv = vendorname_row.createCell(article_code_column);		cellv.setCellValue(article_code_column_name);			cellv.setCellStyle(helpcs);
			cellv = vendorname_row.createCell(sku_column);						cellv.setCellValue(sku_column_name);					cellv.setCellStyle(helpcs);
			cellv = vendorname_row.createCell(provider_code_column);			cellv.setCellValue(provider_code_column_name);			cellv.setCellStyle(helpcs);
			cellv = vendorname_row.createCell(product_description_column);		cellv.setCellValue(product_description_column_name);	cellv.setCellStyle(helpcs);
			cellv = vendorname_row.createCell(trade_column);					cellv.setCellValue(trade_column_name);					cellv.setCellStyle(helpcs);*/

			int itemscount = items != null ? items.length : 0;
			//Lista de valores	
			XSSFDataValidationHelper validationHelper = new XSSFDataValidationHelper(sheet);
			//Crear las columnas dinámicas en base a los atributos definidos. Además buscar en caso que sean listas los valores.			
			Cell cell = null;
			Iterator<DefinableAttributeDataDTO> it = mapDefinAttributes.values().iterator();
			int columindex = first_data_column;
			int valuerowindex = 0; //Fila por la cual comenzar a crear los valores.
			ArrayList<String> values = new ArrayList<>();
			while (it.hasNext())		{
				DefinableAttributeDataDTO da = it.next();
				//Header El nombre interno
				cell = headrow.createCell(columindex);
				cell.setCellValue(da.getAttributeinternalname());
				cell.setCellStyle(headercs);
				//Help
				cell = helprow.createCell(columindex);
				cell.setCellValue(da.getAttributeuserhelp());
				cell.setCellStyle(helpcs);	

				//Vendor
				/*cell = vendorname_row.createCell(columindex);
				String vendor_aleas_name = includeAleas ? da.getAleas() : da.getAttributevendorname();//Aleas del definable o vendor name
				cell.setCellValue(vendor_aleas_name != null ? vendor_aleas_name : "");
				cell.setCellStyle(helpcs);*/

				//Mandatory
				cell = mandatoryrow.createCell(columindex);
				boolean mandatory = da.getMandatory() && da.getCanwrite();

				cell.setCellValue( mandatory ? mandatory_name : "");
				cell.setCellStyle(helpcs);	
				columnlist.put(da.getAttributeinternalname(), columindex);	

				boolean makeList = false;
				if(includePosibleValues){
				if (  FEPUtils.attributeIsValueList(da.getAtributedatatypeinternalname())){	
					values.clear();
					AttributeValueDTO[] valuesDTO = da.getAttributeinfo() != null ? da.getAttributeinfo().getValues() : null;
					if(valuesDTO == null){
								AttributeResultDTO attvaluesres = commonDataManager.getAttribute(da.getAttributeid(), initparam.getLocale().getLanguage());
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
								cell.setCellStyle(valuecs);		
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
				}

				columindex++;
			}

			//Al llegar acá todas las columnas están creadas y listos para barrer los items.
			if (items != null && items.length > 0 ){
				// Crear un mapa con los items y todos su valores
				for (int i = 0; i < items.length ; i++  ){
					// Tomar la lista de valores de cada item y crear las columnas dinamicas
					MPItemDTO item = items[i];
					ItemAttributeValueDTO[] itemvalues = item.getAttributevalues();
					Row row = sheet.createRow(i+first_data_row); // Salta en encabezado

					//Ahora coloca las columnas fijas
					cell = row.createCell(article_code_column);
					cell.setCellValue(item.getArticlecode());
					cell.setCellStyle(valuecs);

					cell = row.createCell(sku_column);
					cell.setCellValue(item.getPlu());
					cell.setCellStyle(valuecs);

					cell = row.createCell(provider_code_column);
					cell.setCellValue(item.getProvidercode());
					cell.setCellStyle(valuecs);

					cell = row.createCell(trade_column);
					cell.setCellValue(item.getTrade());
					cell.setCellStyle(valuecs);

					cell = row.createCell(product_description_column);
					cell.setCellValue(item.getDescription());
					cell.setCellStyle(valuecs);

					//Colocar los datos  dinámicos
					if ( itemvalues != null && itemvalues.length >0  ) {					
						for (int j = 0; j<itemvalues.length;j++ ){
							ItemAttributeValueDTO ivalue = itemvalues[j];					 
							//Verificar si existe  y agregar el valor correspondiente
							if (columnlist.containsKey(ivalue.getAttributeinternalname())){
								int columnindex = columnlist.get(ivalue.getAttributeinternalname());
								cell = row.createCell(columnindex);
								DefinableAttributeDataDTO definableAtt = mapDefinAttributes.get(ivalue.getAttributeinternalname().trim());

								if(ivalue != null && !ivalue.getValue().isEmpty() && definableAtt.getAtributedatatypename().equalsIgnoreCase("Boolean")){									
									ivalue.setValue(FEPUtils.getBooleanValueByLanguage(ivalue.getValue(),initparam.getLocale()));
								}	

								if(FEPUtils.attributeIsaFile(definableAtt.getAtributedatatypeinternalname())){
									//Salvar nombre y link
									//String filename = FEPUtils.getDescriptionFromCodedValue(ivalue.getValue(), MDMConstants.LIST_SEPARATOR);
									//String linktofile = FEPUtils.getCodeFromCodedValue(ivalue.getValue(), MDMConstants.LIST_SEPARATOR);
									cell.setCellValue(ivalue.getValue());
									Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
									link.setAddress(ivalue.getValue());
									cell.setHyperlink(link);
									cell.setCellStyle(linkcs); 

								}
								else{
									cell.setCellValue(ivalue.getValue());
									cell.setCellStyle(valuecs);
								}																			 
							}										 
						}					 
					}

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

	public MPItemErrorArrayResultDTO uploadExcelItems(MPArticleTypeInitParamDTO initparam, MPItemDTO[] items,String filename, Long userKey){
		MPItemErrorArrayResultDTO resultDTO = new MPItemErrorArrayResultDTO();

		ArrayList<ErrorInfoDTO> errors = new ArrayList<>();

		String file = PortalConstants.getInstance().getFileUploadPath() + userKey + "/" + filename;
		String extension = filename.substring(filename.lastIndexOf('.') + 1);

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
			ArticleTypeResultDTO articleTypeRes = this.getArticleTypeWithMPProvilegies(initparam);
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
			//int lastcolumnumber = headerrow.getLastCellNum();

			// Se buscan las filas en blanco
			List<Integer> rowindexList = new ArrayList<Integer>();
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// Validando fila en blanco
				if ( (row.getCell(0) == null || row.getCell(0).toString().trim().length() == 0) ) {
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
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10004");		
			}
			
			boolean allcolumnOK = false;

			// Valida si primera fila corresponde a encabezado con las columnas obligatorias
			if (    sheet.getRow(header_row_index) != null && sheet.getRow(header_row_index).getCell(article_code_column) != null && sheet.getRow(header_row_index).getCell(article_code_column).toString().equals(article_code_column_name) &&
					sheet.getRow(header_row_index).getCell(sku_column) != null && sheet.getRow(header_row_index).getCell(sku_column).toString().equals(sku_column_name) &&
					sheet.getRow(header_row_index).getCell(provider_code_column) != null && sheet.getRow(header_row_index).getCell(provider_code_column).toString().equals(provider_code_column_name) &&
					sheet.getRow(header_row_index).getCell(product_description_column) != null && sheet.getRow(header_row_index).getCell(product_description_column).toString().equals(product_description_column_name) &&
					sheet.getRow(header_row_index).getCell(trade_column) != null && sheet.getRow(header_row_index).getCell(trade_column).toString().equals(trade_column_name)					
					) {	
				allcolumnOK = true;
			}
			
			
			if (!allcolumnOK){
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P10003");
			}

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
			HashMap<String, MPItemDTO> itemMaps = new HashMap<String, MPItemDTO>();
			Arrays.stream(items).forEach(item -> itemMaps.put(item.getPlu(), item));
			
			//Mapa para tener los valores seleccionados en los atributos tipo lista
			HashMap<Long, List<AttributeValueDTO> > attSelectedValues = new HashMap<>();

			List<ErrorInfoDTO> rowerrors = new ArrayList<ErrorInfoDTO>();
			List<ErrorInfoDTO> itemserrors = new ArrayList<ErrorInfoDTO>();
			// cada Fila se corresponde con un item
			//Mapa para guardar un item que contentrá los valores de los atributos no variantes que son comunes para todos con el mismo articulo.			
			HashMap<String, String> articleDataItemValueMap = new HashMap<String, String>();
			for (int j = first_data_row; j <= sheet.getLastRowNum(); j++) {
				attSelectedValues.clear();
				rowerrors.clear();
				itemserrors.clear();
				Row row = sheet.getRow(j);
				if (row == null)
					continue;				

				MPItemDTO newItem = null;
				String articleStr = FEPUtils.getValueData("String",row.getCell(article_code_column),row.getCell(article_code_column).getCellType());	
				String skuStr = FEPUtils.getValueData("String",row.getCell(sku_column),row.getCell(sku_column).getCellType());     
				//String vendorcodeStr = FEPUtils.getValueData("String",row.getCell(provider_code_column),row.getCell(provider_code_column).getCellType()); 
				//String descriptionStr = FEPUtils.getValueData("String",row.getCell(product_description_column),row.getCell(product_description_column).getCellType());  
				//String tradeStr = 	 FEPUtils.getValueData("String",row.getCell(trade_column),row.getCell(trade_column).getCellType());		
				
				// verificación de que existan datos no nulos
				if(articleStr == null || articleStr.isEmpty()){
					//error = "Favor complete los datos obligatorios. No existen datos para el código del artículo. " + "columna " + 1 + " fila " + (row.getRowNum() + 1);
					error = "No existen datos para el código del artículo" + " - " + "(Fila " + (row.getRowNum() + 1) + "Columna " + 1 + ")";
					FEPUtils.addErrorResult(rowerrors, j, 1, error);	
				}

				if(skuStr == null || skuStr.isEmpty()){
					//error = "Favor complete los datos obligatorios. No existen datos para el sku del producto. " + "columna " + 2 + " fila " + (row.getRowNum() + 1);
					error = "No existen datos para el sku" + " - " + "(Fila " + (row.getRowNum() + 1) + "," + "Columna " + 2 + ")";
					FEPUtils.addErrorResult(rowerrors, j, 2, error);
				}

				//Buscar a que item pertenece la fila
				if (itemMaps.containsKey(skuStr)){
					newItem = itemMaps.get(skuStr);

					// Atributes Values
					List<ItemAttributeValueDTO> attvalues = new ArrayList<ItemAttributeValueDTO>();
					//HashMap<String, ItemAttributeValueDTO> mapitemvalues = new  HashMap<String, ItemAttributeValueDTO> ();					
					attvalues.clear();

					for (int k = first_data_column; k < headerrow.getLastCellNum(); k++) {	
						List<AttributeValueDTO> selected_values =  null;
						MPItemAttributeValueDTO newitemvalue  = new MPItemAttributeValueDTO();
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
									//error = "Favor complete los datos obligatorios. No existen datos para la columna " + k + " fila " + (row.getRowNum() + 1 + " Nombre de columna: " + datt.getAttributeinternalname());
									error = "SKU: " + newItem.getPlu() + " - " +  "Campo obligatorio: " + datt.getAttributeinternalname() + " (Fila " + (row.getRowNum() + 1) + "," + "Columna " + k + ")";
									FEPUtils.addErrorResult(itemserrors, (row.getRowNum() + 1), k+1,newItem.getPlu(),datt.getAttributeinternalname(), error);
									continue;
								}

								if(datt.getCanwrite()){
									String formatedValue = cellvalue != null && !cellvalue.isEmpty() ? cellvalue : "";									

									//fromatear el valor si es lista o codificacion etc.
									if(cellvalue != null && !cellvalue.isEmpty()){
										formatedValue = commonDataManager.formatValueData(cellvalue, datt, itemserrors,attSelectedValues, j, k, newItem.getPlu(),datt.getAttributeinternalname(), initparam.getLocale().getLanguage()); //Formatear.
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

//										for (MPItemAttributeValueDTO dto: newItem.getAttributevalues())
//										{
//											if (dto.getAttributeid().equals(datt.getAttributeid())){
//												newitemvalue.setUserdatatypeid(dto.getUserdatatypeid());
//											}
//										}

										//Buscar los valores seleccionados
										selected_values = attSelectedValues.get(datt.getAttributeid());	
										newitemvalue.setSelectedvalues(selected_values);
										//Tomar valor de articulo si es no es variante.
										String keyvalue = newItem.getArticlecode() + newitemvalue.getAttributeinternalname();
										String articlevalue = articleDataItemValueMap.get(keyvalue);
										if(articlevalue == null && cellvalue != null && !cellvalue.isEmpty() ){
											articleDataItemValueMap.put(keyvalue, cellvalue);
										}
										if(articlevalue != null && !datt.getForvariant()){
											newitemvalue.setValue(articlevalue);	
										}
										
										attvalues.add(newitemvalue);									
										
									}
								}
							}		
						} catch (Exception e) {
							//error = "Ocurrió un error al intentar procesar el atributo " + colheader_name;
							if (datt == null)
							{
								error = "SKU: " + newItem.getPlu() + " - " +  "Ocurrió un error al intentar procesar la celda: " + colheader_name + " (Fila " + (row.getRowNum() + 1) + "," + "Columna " + k + ")";
							}
							else{
								error = "SKU: " + newItem.getPlu() + " - " +  "Ocurrió un error al intentar procesar el atributo: " + datt.getAttributeinternalname() + " (Fila " + (row.getRowNum() + 1) + "," + "Columna " + k + ")";
							}
						
							FEPUtils.addErrorResult(itemserrors, (row.getRowNum() + 1), k+1, newItem.getPlu(),colheader_name, error);								
						}

					}
					
					//Validar relaciones
					commonDataManager.validateAttributeRelations(mapDefinAttributes,itemserrors,   attvalues,attSelectedValues,(row.getRowNum() + 1), initparam.getLocale().getLanguage());
					
					
					//Agregar valores al item
					MPItemAttributeValueDTO []  newvalues = new  MPItemAttributeValueDTO[attvalues.size()];
					newvalues = attvalues.toArray(newvalues);
					newItem.setAttributevalues(newvalues);
					newItem.setErrors(itemserrors.toArray(new ErrorInfoDTO[itemserrors.size()]));
					
				}
				else{
					//error = "El producto no pertenece a la lista de productos vigente. " + " fila " + (row.getRowNum() + 1);
					error = "El producto no pertenece a la lista de productos vigente. " + "Fila " + (row.getRowNum() + 1) ;
					FEPUtils.addErrorResult(rowerrors, (row.getRowNum() + 1), 3, skuStr,null, error);
				}

				// Si existe algun error en la fila, no se puede agregar a la lista de retorno.
				if (rowerrors.size() > 0){					
					errors.addAll(rowerrors);
				}
				
				if (itemserrors.size() > 0){					
					errors.addAll(itemserrors);
				}

			}// End de for de parseo


			List<MPItemDTO> itemList = new ArrayList<MPItemDTO>();
			if (items != null && items.length > 0){
				for (MPItemDTO it: items){
					it = setCommonArticleData(it,articleDataItemValueMap);				
					itemList.add(it);
				}
			}

			if (errors.size() > 0) {
				// Ordenar errores
				resultDTO.setErrors(errors.toArray(new ErrorInfoDTO[errors.size()]));
				resultDTO = PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P4000");
			}
			
			resultDTO.setItems(itemList.toArray(new MPItemDTO[itemList.size()]));

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


	private MPItemDTO setCommonArticleData(MPItemDTO it, HashMap<String, String> articleDataItemValueMap) {
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
	}


	@Override
	public MPItemErrorArrayResultDTO markItemsAsDownloaded(BaseInitParamDTO initparam, MPItemDTO[] items, PersonDTO user) {
		MPItemErrorArrayResultDTO resultDTO = new MPItemErrorArrayResultDTO();
		try {
			resultDTO = modifyManager.markItemsAsDownloaded(initparam, items, user);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}
		
		return resultDTO;
	}
	
	public FileDownloadInfoResultDTO downloadPdfProductDatasheet(MPItemsByIDsInitParamDTO initparam, Long userKey){

		FileDownloadInfoResultDTO resultDTO =  new FileDownloadInfoResultDTO();

		AttributesNotImageDTO[] attNotImageDTO = null;
		AttributesImageDTO[] attImageDTO = null;

		try {
			MPItemTechnicalInfoResultDTO prodData = new MPItemTechnicalInfoResultDTO();
			try {
				prodData = modifyManager.getItemTechnicalInfoByID(initparam);
			}catch (Exception e) {
				e.printStackTrace();
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
			}		

			if (prodData.getNotImageAttributeValues() == null || prodData.getNotImageAttributeValues().size() <= 0){
				resultDTO.setStatuscode(prodData.getStatuscode());
				resultDTO.setStatusmessage(prodData.getStatusmessage());
				return resultDTO;
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat completeDateFormat = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");

			// Construcción del PDF
			// Obtener el nombre del archivo		
			String downloadfilename = "PRODUCT_" + prodData.getItem().getArticletypename() + "_" + dateFormat.format(new Date()) + ".pdf";
			String realfilename = "PRODUCT_" + prodData.getItem().getArticletypename() + "_"+ dateFormat.format(new Date()) + " hrs_"+ userKey + ".pdf";

			// Setear la ruta de los subreportes
			File reports = new File (B2BSystemPropertiesUtil.getProperty("fep_pdfreports"));
			reports.mkdirs();

			String pathJasperProductImages	= reports.getPath() + "/Productimages.jasper";
			String pathJasperProductAttributes	= reports.getPath() + "/Attributesgroups.jasper";
			
			List<MPItemAttributeValueDTO> attNotImages = prodData.getNotImageAttributeValues();
			List<MPItemAttributeValueDTO> attImages= prodData.getImageAttributeValues();

			try {
				// Setear parámetros del reporte
				HashMap <String,Object> parameters = new HashMap<String,Object> ();

				parameters.put("ARTICLE_CODE", prodData.getItem().getArticlecode());
				parameters.put("LONG_DESCRIPTION", prodData.getItem().getDescription());
				parameters.put("ARTICLETYPE_NAME", prodData.getItem().getArticletypename());
				parameters.put("SKU",  prodData.getItem().getPlu());
				parameters.put("STATE", prodData.getItem().getCurrentstatename());
				parameters.put("PRODUCT_IMAGES", pathJasperProductImages);
				parameters.put("PRODUCT_ATTRIBUTES", pathJasperProductAttributes);

				//Setear el DTO para los atributos que no son del Tipo Imagen
				attNotImageDTO = new AttributesNotImageDTO[attNotImages.size()];

				for (MPItemAttributeValueDTO att: attNotImages)
				{
					attNotImageDTO [attNotImages.indexOf(att)] = new AttributesNotImageDTO ();
					attNotImageDTO [attNotImages.indexOf(att)].setTypename(att.getTypename());
					attNotImageDTO [attNotImages.indexOf(att)].setAttributevendorname(att.getAttributevendorname());
					attNotImageDTO [attNotImages.indexOf(att)].setValue(att.getValue());
				}

				if (attImages != null && attImages.size() > 0)
				{
					// Construcción del directorio temporal para almacenar los archivos tipo imagenes
					File tempdir = new File (PortalConstants.getInstance().getFileTransferPath() + "IMAGES_" + userKey + "_"+ completeDateFormat.format(new Date()) + " hrs_");
					tempdir.mkdirs();

					//Descarga todas las imagenes al directorio temporal
					fileStorageManager.downloadMPFileImagesObjects(attImages, tempdir.getPath());

					//Setear el DTO para los atributos que son del Tipo Imagen
					attImageDTO = new AttributesImageDTO[attImages.size()];

					for (MPItemAttributeValueDTO att: attImages)
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
					fileStorageManager.downloadMPFileImagesObjects(null, tempdir.getPath());

					attImageDTO = new AttributesImageDTO[2];
					
					attImageDTO [0] = new AttributesImageDTO ();
					attImageDTO [0].setAttributevendorname("IMAGEN FRONTAL");
					attImageDTO [0].setValue((tempdir.getPath() + "/" + FEPUtils.getFilenameByUri(B2BSystemPropertiesUtil.getProperty("DEFAULT_IMAGE"))));
					
					attImageDTO [1] = new AttributesImageDTO ();
					attImageDTO [1].setAttributevendorname("IMAGEN LATERAL");
					attImageDTO [1].setValue((tempdir.getPath() + "/" + FEPUtils.getFilenameByUri(B2BSystemPropertiesUtil.getProperty("DEFAULT_IMAGE"))));
				}
					

				//attImageDTO = attImageDTO != null && attImageDTO.length > 0 ? attImageDTO : new AttributesImageDTO [attImages.size()];
				
				//Setear las listas de campos que se le pasan al reporte
				ProductReportDTO pdf = new ProductReportDTO();

				pdf.setAttNotImageDTO(Arrays.asList(attNotImageDTO));
				pdf.setAttImageDTO(Arrays.asList(attImageDTO));
				
				//pdf.setAttImageDTO(Arrays.asList(attImageDTO != null && attImageDTO.length > 0 ? attImageDTO : new AttributesImageDTO [attImages.size()]));

				List<ProductReportDTO> list = Arrays.asList(pdf);

				// Ruta de los archivos JRXML
				
				String pathMain = reports.getPath() + "/Productdatasheet.jrxml";
				String pathProductImages = reports.getPath() + "/Productimages.jrxml";
				String pathProductAttributes = reports.getPath() + "/Attributesgroups.jrxml";

				// Compilamos el .jrxml y lo cargamos
				final JasperDesign design = JRXmlLoader.load(pathMain);
				final JasperReport report = JasperCompileManager.compileReport(design);	

				// Compilamos los subreportes
				JasperCompileManager.compileReportToFile(pathProductImages);
				JasperCompileManager.compileReportToFile(pathProductAttributes);

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
	public BaseResultDTO removeItems(MPItemDTO[] itemsDTO, PersonDTO user) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		try {
			resultDTO = modifyManager.removeItems(itemsDTO, user); //Eliminar comentario cuando se vaya a publicar, para evitar eliminar items que se usan para pruebas.
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P800");// modulo mdm no disp.
		}

		return resultDTO;
	}


	@Override
	public FileDownloadInfoResultDTO downloadPrivilegesByItemState(MPPrivilegeInitParamDTO initparam, Long uskey, String fileformat) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		try {
			MPPrivilegeArrayResultDTO dataResult = this.getPrivilegies(initparam);


			if(dataResult != null && !dataResult.getStatuscode().equals("0")){
				resultDTO.setStatuscode(dataResult.getStatuscode());
				resultDTO.setStatusmessage(dataResult.getStatusmessage());
				return resultDTO;
			}

			String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_privileges_by_itemstate_title", initparam.getLocale());
			if(downloadname == null || downloadname.isEmpty()){
				downloadname = "Privilegios por estado de productos";
			}
			downloadname += FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +"."+ fileformat;
			String realname = "Privilegios por estado de productos" +  FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +"."+fileformat;

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

				for ( MPPrivilegeDTO privDTO : dataResult.getValues()) {
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

			resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, fileformat, downloadname, realname, uskey);
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P100");// modulo comercial no disp.
		}
		return resultDTO;
	}


	@Override
	public FileDownloadInfoResultDTO downloadItemsByFilter(MPItemsByFilterInitParamDTO initparam, Long uskey, String fileformat) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		try {
			MPItemArrayResultDTO dataResult = this.getItemsByFilter(initparam);

			if(dataResult != null && !dataResult.getStatuscode().equals("0")){
				resultDTO.setStatuscode(dataResult.getStatuscode());
				resultDTO.setStatusmessage(dataResult.getStatusmessage());
				return resultDTO;
			}

			String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_items_by_state_title", initparam.getLocale());
			if(downloadname == null || downloadname.isEmpty()){
				downloadname = "Productos por estado";
			}
			downloadname += FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +"."+ fileformat;
			String realname = "Productos por estado" +  FEPUtils.getInstance().getDownloadDateFormat().format( new Date()) +"."+fileformat;

			//Tabla			
			DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_items_by_state_title", initparam.getLocale()));

			DataColumn col0 = new DataColumn("provider_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_provider_code", initparam.getLocale()), String.class);
			DataColumn col1 = new DataColumn("provider_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_provider_name", initparam.getLocale()), String.class);
			DataColumn col2 = new DataColumn("article_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_article_code", initparam.getLocale()), String.class);
			DataColumn col3 = new DataColumn("product_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_product_code", initparam.getLocale()), String.class);
			DataColumn col4 = new DataColumn("articletype_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_articletype_name", initparam.getLocale()), String.class);
			DataColumn col5 = new DataColumn("product_description", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_product_description", initparam.getLocale()), String.class);
			DataColumn col6 = new DataColumn("vendor_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_vendor_code", initparam.getLocale()), String.class);
			DataColumn col7 = new DataColumn("ean_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_ean_code", initparam.getLocale()), String.class);
			DataColumn col8 = new DataColumn("trade_mark", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_trade_mark", initparam.getLocale()), String.class);
			DataColumn col9 = new DataColumn("creation_date", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_creation_date", initparam.getLocale()), LocalDateTime.class);
			DataColumn col10 = new DataColumn("last_modified", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_last_modified", initparam.getLocale()), LocalDateTime.class);
			DataColumn col11 = new DataColumn("state", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_xls_state", initparam.getLocale()), String.class);
			
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
			dt0.addColumn(col10);
			dt0.addColumn(col11);

			DataRow row = null;
			if( dataResult != null && dataResult.getValues()!= null && dataResult.getValues().length > 0){

				for ( MPItemDTO itemDTO : dataResult.getValues()) {
					row = dt0.newRow(); 
					row.setCellValue("provider_code", itemDTO.getProvidercode()); //TODO: debe ser el del initparam o del Item?
					row.setCellValue("provider_name", itemDTO.getProvidersocialreason());
					row.setCellValue("article_code", itemDTO.getArticlecode());
					row.setCellValue("product_code", itemDTO.getPlu());
					row.setCellValue("articletype_name", itemDTO.getArticletypename());
					row.setCellValue("product_description", itemDTO.getDescription());
					row.setCellValue("vendor_code", itemDTO.getVendorcode());
					row.setCellValue("ean_code", itemDTO.getEancode());
					row.setCellValue("trade_mark", itemDTO.getTrade());
					row.setCellValue("creation_date", itemDTO.getCreationdate());
					row.setCellValue("last_modified", itemDTO.getLastmodified());
					row.setCellValue("state", itemDTO.getCurrentstatename());
					
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
	
}