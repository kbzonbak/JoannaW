package bbr.b2b.portal.users.managers.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;
import bbr.b2b.portal.utils.FileHandlerUtils;
import bbr.b2b.portal.utils.I18NManager;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;
import bbr.b2b.users.managers.interfaces.ContextUtil;
import bbr.b2b.users.managers.interfaces.IPublishingManager;
import bbr.b2b.users.managers.interfaces.IUserManager;
import bbr.b2b.users.report.classes.AddPopUpInitParamsDTO;
import bbr.b2b.users.report.classes.AddPublishingInitParamsDTO;
import bbr.b2b.users.report.classes.AssignedCompaniesArrayResultDTO;
import bbr.b2b.users.report.classes.AssignedRelationshipsInitParamDTO;
import bbr.b2b.users.report.classes.AssignedUsersArrayResultDTO;
import bbr.b2b.users.report.classes.AttachmentDTO;
import bbr.b2b.users.report.classes.AuditPopUpInitParamDTO;
import bbr.b2b.users.report.classes.AuditPopUpReportDTO;
import bbr.b2b.users.report.classes.AuditPopUpReportInitParamDTO;
import bbr.b2b.users.report.classes.AuditPopUpReportResultDTO;
import bbr.b2b.users.report.classes.AuditPopUpTitleResultDTO;
import bbr.b2b.users.report.classes.AuditPopUpYearResultDTO;
import bbr.b2b.users.report.classes.CompanyDTO;
import bbr.b2b.users.report.classes.DoChangePublishingStateInitParamsDTO;
import bbr.b2b.users.report.classes.ImageDTO;
import bbr.b2b.users.report.classes.ImageResultDTO;
import bbr.b2b.users.report.classes.PopUpBehaviorResultDTO;
import bbr.b2b.users.report.classes.PopUpReportInitParamsDTO;
import bbr.b2b.users.report.classes.PopUpReportResultDTO;
import bbr.b2b.users.report.classes.PopUpResultDTO;
import bbr.b2b.users.report.classes.PopUpTemplateResultDTO;
import bbr.b2b.users.report.classes.PopUpTypeResultDTO;
import bbr.b2b.users.report.classes.PublishingDetailReportResultDTO;
import bbr.b2b.users.report.classes.PublishingReportInitParamsDTO;
import bbr.b2b.users.report.classes.PublishingReportResultDTO;
import bbr.b2b.users.report.classes.PublishingResultDTO;
import bbr.b2b.users.report.classes.PublishingStateResultDTO;
import bbr.b2b.users.report.classes.PublishingTypeResultDTO;
import bbr.b2b.users.report.classes.RuleTypeResultDTO;
import bbr.b2b.users.report.classes.RuleTypesResultDTO;
import bbr.b2b.users.report.classes.UpdatePopUpInitParamsDTO;
import bbr.b2b.users.report.classes.UpdatePublishingInitParamsDTO;
import bbr.b2b.users.report.classes.UploadUserPublishingFileInitParamDTO;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UserPublishingRelationshipsInitParamsDTO;
import bbr.common.dataset.util.DataColumn;
import bbr.common.dataset.util.DataRow;
import bbr.common.dataset.util.DataTable;
import bbr.common.dataset.util.XLSConverterPOI;

@Stateless(name = "managers/PublishingManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PublishingManager implements PublishingManagerLocal{	
	
	private IPublishingManager publishingmanagerremote = null;
	private IUserManager usermanagerremote = null;
	
	
	
	@PostConstruct
	public void getRemote() {
		try {
			publishingmanagerremote = ContextUtil.getInstance().getIPublishingManager();
			usermanagerremote = ContextUtil.getInstance().getIUserManager();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public PublishingTypeResultDTO getPublishingTypes() {
		PublishingTypeResultDTO result = new PublishingTypeResultDTO();
				
		try {							
			result = publishingmanagerremote.getPublishingTypes();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}		
	}	
	
	public PublishingStateResultDTO getPublishingStates() {
		PublishingStateResultDTO result = new PublishingStateResultDTO();     
		
		try {			
			result = publishingmanagerremote.getPublishingStates();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}		
	}	
	
	public PopUpBehaviorResultDTO getPopUpBehaviors() {
		PopUpBehaviorResultDTO result = new PopUpBehaviorResultDTO();
		try {			
			result = publishingmanagerremote.getPopUpBehaviors();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}	
	}
	
	public PublishingReportResultDTO getPublishingReport(PublishingReportInitParamsDTO initParams, boolean byfilter){
		PublishingReportResultDTO result = new PublishingReportResultDTO();
				
		try {
			result = publishingmanagerremote.getPublishingReport(initParams, byfilter);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}		
	}
	
	public PopUpReportResultDTO getPopUpReport(PopUpReportInitParamsDTO initParams, boolean byfilter){
		PopUpReportResultDTO result = new PopUpReportResultDTO();
		try {
			result = publishingmanagerremote.getPopUpReport(initParams, byfilter);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}	
	}
	
	public PublishingDetailReportResultDTO getPublishingDetailReportById(Long publishingid){
		PublishingDetailReportResultDTO result = new PublishingDetailReportResultDTO();
				
		try{			
			result = publishingmanagerremote.getPublishingDetailReportById(publishingid);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");			
		}		
	}
	
	public PublishingReportResultDTO getActivePublishingsByType(String publishingtypecode){
		PublishingReportResultDTO result = new PublishingReportResultDTO();
		
		try{
			result = publishingmanagerremote.getActivePublishingsByType(publishingtypecode);				
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public PublishingTypeResultDTO getProcedureTypes(){
		PublishingTypeResultDTO result = new PublishingTypeResultDTO();
		
		try{
			result = publishingmanagerremote.getProcedureTypes();			
						
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public PublishingReportResultDTO getActivePublishingsByUserAndType(Long userId, String publishingtypecode, boolean isProcedure){
		PublishingReportResultDTO result = new PublishingReportResultDTO();
		
		try{
			result = publishingmanagerremote.getActivePublishingsByUserAndType(userId, publishingtypecode,isProcedure);			
			
			
						
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public RuleTypesResultDTO getRuleTypes(){
		RuleTypesResultDTO result = new RuleTypesResultDTO();
		
		try{
			result = publishingmanagerremote.getRuleTypes();				
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public RuleTypeResultDTO getRuleTypeByPublishingId(Long publishingId){
		RuleTypeResultDTO result = new RuleTypeResultDTO();
		
		try{
			result = publishingmanagerremote.getRuleTypeByPublishingId(publishingId);				
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public PopUpReportResultDTO getActivePopUpByUserAndFunctionality(Long userId, Long functionalityid){
		PopUpReportResultDTO result = new PopUpReportResultDTO(); 
		try{
			result = publishingmanagerremote.getActivePopUpByUserAndFunctionality(userId, functionalityid);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public PopUpReportResultDTO getActivePopUps(Long uskey){
		PopUpReportResultDTO result = new PopUpReportResultDTO(); 
		try{
			result = publishingmanagerremote.getActivePopUps(uskey);				
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public PublishingResultDTO doAddPublishing(AddPublishingInitParamsDTO initParams, Long userkey){
	
		PublishingResultDTO result = new PublishingResultDTO();
		
		try {	
			return result = publishingmanagerremote.doAddPublishing(initParams);		
		}catch (Exception e) {			
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}		
	}
		
	public PopUpResultDTO doAddPopUp(AddPopUpInitParamsDTO initParams, Long userkey){
		PopUpResultDTO result = new PopUpResultDTO();
		try {	
			return result = publishingmanagerremote.doAddPopUp(initParams);		
		}catch (Exception e) {			
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}		
	}
	
	public PublishingResultDTO doUpdatePublishing(UpdatePublishingInitParamsDTO initParams, Long userkey){
		
		PublishingResultDTO result = new PublishingResultDTO();
		
		try {	
			return result = publishingmanagerremote.doUpdatePublishing(initParams);
		}catch (Exception e) {			
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}				
	}
	
	public PopUpResultDTO doUpdatePopUp(UpdatePopUpInitParamsDTO initParams, Long userkey){
		PopUpResultDTO result = new PopUpResultDTO();
		try {	
			return result = publishingmanagerremote.doUpdatePopUp(initParams);			
		}catch (Exception e) {			
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}		
	}
	
	public BaseResultsDTO doAddUserPublicationRelationshipsByRule(UserPublishingRelationshipsInitParamsDTO initParams){
		BaseResultsDTO result = new BaseResultsDTO(); 
		try{
			result = publishingmanagerremote.doAddUserPublicationRelationshipsByRule(initParams);				
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public BaseResultDTO doDeleteUserPublicationRelationshipsByRule(UserPublishingRelationshipsInitParamsDTO initParams){
		BaseResultDTO result = new BaseResultDTO(); 
		try{
			result = publishingmanagerremote.doDeleteUserPublicationRelationshipsByRule(initParams);				
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public ImageResultDTO getImages(){
		ImageResultDTO result = new ImageResultDTO();
		try {			
			return publishingmanagerremote.getImages();
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}		
	}	
	
	public AttachmentDTO getAttachmentById(Long attachid){
		AttachmentDTO result = new AttachmentDTO();
		try{
			result = publishingmanagerremote.getAttachmentById(attachid);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	public ImageDTO getImageById(Long imageid){
		ImageDTO result = new ImageDTO();
		try{
			result = publishingmanagerremote.getImageById(imageid);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public BaseResultDTO doDeleteAttachmentById(Long attachid){
		BaseResultDTO result = new BaseResultDTO();
		try{
			result = publishingmanagerremote.doDeleteAttachmentById(attachid);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}	
	}
	
	public BaseResultDTO doAddImage(ImageDTO imagedto){
		BaseResultDTO result = new BaseResultDTO();
		try{
			result = publishingmanagerremote.doAddImage(imagedto);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}	
	}
	
	public BaseResultDTO doChangePublishingState(DoChangePublishingStateInitParamsDTO initParams){
		BaseResultDTO result = new BaseResultDTO();
		try{
			result = publishingmanagerremote.doChangePublishingState(initParams);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public BaseResultDTO doChangePopUpState(DoChangePublishingStateInitParamsDTO initParams){
		BaseResultDTO result = new BaseResultDTO();
		try{
			result = publishingmanagerremote.doChangePopUpState(initParams);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public BaseResultDTO doDeletePublishingById(Long[] publishingids){
		BaseResultDTO result = new BaseResultDTO();
		try{
			result = publishingmanagerremote.doDeletePublishingById(publishingids);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public BaseResultDTO doDeletePopUpById(Long[] popupids){
		BaseResultDTO result = new BaseResultDTO();
		try{
			result = publishingmanagerremote.doDeletePopUpById(popupids);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public BaseResultDTO doActivatePublishing(Long[] publishingIds, String lastUser){
		BaseResultDTO result = new BaseResultDTO();
		try{
			result = publishingmanagerremote.doActivatePublishing(publishingIds, lastUser);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public BaseResultDTO doActivatePopUp(Long[] popupIds, String lastUser){
		BaseResultDTO result = new BaseResultDTO();
		try{
			result = publishingmanagerremote.doActivatePopUp(popupIds, lastUser);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public BaseResultDTO doInactivatePublishing(Long[] publishingIds, String lastUser){
		BaseResultDTO result = new BaseResultDTO();
		try{
			result = publishingmanagerremote.doInactivatePublishing(publishingIds, lastUser);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	public BaseResultDTO doInactivatePopUp(Long[] popupIds, String lastUser){
		BaseResultDTO result = new BaseResultDTO();
		try{
			result = publishingmanagerremote.doInactivatePopUp(popupIds, lastUser);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	@Override
	public BaseResultDTO getAuditPopUp(AuditPopUpInitParamDTO initParamDTO, Long uskey) {
		BaseResultDTO result = new BaseResultDTO();
		try{
			result = publishingmanagerremote.getAuditPopUp(initParamDTO, uskey);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}


	@Override
	public PopUpBehaviorResultDTO getPopUpBehaviorByPopUpType(Long poupuptypeid) {
		PopUpBehaviorResultDTO result = new PopUpBehaviorResultDTO();
		try{
			result = publishingmanagerremote.getPopUpBehaviorByPopUpType(poupuptypeid);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}


	@Override
	public PopUpTypeResultDTO getPopUpTypes() {
		PopUpTypeResultDTO result = new PopUpTypeResultDTO();
		try{
			result = publishingmanagerremote.getPopUpTypes();
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}
	
	
	
	@Override
	public PopUpTemplateResultDTO getPopUpTemplates() {
		PopUpTemplateResultDTO result = new PopUpTemplateResultDTO();
		try{
			result = publishingmanagerremote.getPopUpTemplates();
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}

	
	public BaseResultsDTO doUpdateUserPublishingRelationships(UploadUserPublishingFileInitParamDTO initParams){
		BaseResultsDTO result = new BaseResultsDTO();
		Workbook workBook = null;
		
		try {
			File inputFile = new File(initParams.getFilePath() + initParams.getFileName());
			FileInputStream inputStream = new FileInputStream(inputFile);
			
			try{
				workBook = new XSSFWorkbook(inputStream);
			} catch (Exception ex) {
				inputStream = new FileInputStream(inputFile);
				POIFSFileSystem fs = new POIFSFileSystem(inputStream);
				workBook = new HSSFWorkbook(fs);
			}
	
			
		} catch (IOException e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P3501");
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P3501");
		}
		
		try {
			Sheet sheet = workBook.getSheetAt(0);
			
			UserPublishingRelationshipsInitParamsDTO initParamForServer = new UserPublishingRelationshipsInitParamsDTO();
			
			getUpdateUserPublishingInfo(initParams, initParamForServer, result, sheet); 
			
			if (result.getStatuscode().equals("0")){
				result = doAddUserPublicationRelationshipsByRule(initParamForServer);	
			}	
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		} finally {
			if(workBook!= null){
				try {
						workBook.close();
					}
				catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
				
		return result;		
	}

	public BaseResultsDTO doUpdateCompanyPublishingRelationships(UploadUserPublishingFileInitParamDTO initParams){
		BaseResultsDTO result = new BaseResultsDTO();
		
		Workbook workBook = null;
		
		try {
			File inputFile = new File(initParams.getFilePath() + initParams.getFileName());
			FileInputStream inputStream = new FileInputStream(inputFile);

			try{
				workBook = new XSSFWorkbook(inputStream);
			} catch (Exception ex) {
				inputStream = new FileInputStream(inputFile);
				POIFSFileSystem fs = new POIFSFileSystem(inputStream);
				workBook = new HSSFWorkbook(fs);
			}
	
		} catch (IOException e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P3501");
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P3501");
		}
		
		try {
			Sheet sheet = workBook.getSheetAt(0);
			
			UserPublishingRelationshipsInitParamsDTO initParamForServer = new UserPublishingRelationshipsInitParamsDTO();
			
			getUpdateCompanyPublishingInfo(initParams, initParamForServer, result, sheet); 
			
			if (result.getStatuscode().equals("0")){
				result = doAddUserPublicationRelationshipsByRule(initParamForServer);	
			}	
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}	 finally {
			if(workBook!= null){
				try {
						workBook.close();
					}
				catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
				
		return result;		
	}	

	private void getUpdateUserPublishingInfo(UploadUserPublishingFileInitParamDTO initParams, UserPublishingRelationshipsInitParamsDTO initParamForServer, BaseResultsDTO result, Sheet sheet){
		
		final Integer ROWS_COUNT = 4;
		
		try {
			String error = "";
			List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();
			List<String> strKeyList = new ArrayList<String>();

			// Se buscan las filas en blanco
			List<Integer> rowindexList = new ArrayList<Integer>();
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				// Validando fila en blanco
				if ((row.getCell(0) == null || row.getCell(0).toString().trim().length() == 0) && 
					(row.getCell(1) == null || row.getCell(1).toString().trim().length() == 0) && 
					(row.getCell(2) == null || row.getCell(2).toString().trim().length() == 0) && 
					(row.getCell(3) == null || row.getCell(3).toString().trim().length() == 0)) {
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
				error = "El archivo cargado no posee información";
				baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
			}

			NumberFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(0);
			df.setGroupingUsed(false);

			// Valida si primera fila corresponde a encabezado
			int firstRowData = 0;

			if (sheet.getRow(0).getCell(0).toString().equals("Correo") && 
				sheet.getRow(0).getCell(1).toString().equals("Nombre") && 
				sheet.getRow(0).getCell(2).toString().equals("Apellido") && 
				sheet.getRow(0).getCell(3).toString().equals("Cargo")) {

				firstRowData = 1;
			}

			// Guardamos los valores de cada columna de la fila en un arreglo de
			// String
			for (int j = firstRowData; j <= sheet.getLastRowNum(); j++) {
				Row row = sheet.getRow(j);
				if (row == null)
					continue;
				// Serán solo ROWS_COUNT columnas (no usar row.getLastCellNum() por si
				// vienen columnas vacías al final)
				String[] valueArray = new String[ROWS_COUNT];
				for (int k = 0; k < ROWS_COUNT; k++) {
					Cell cell = row.getCell(k);
					valueArray[k] = "";
					if (cell != null) {
						int cellType = cell.getCellType();
						switch (cellType) {
						case HSSFCell.CELL_TYPE_NUMERIC:
							valueArray[k] = df.format(cell.getNumericCellValue());
							break;
						case HSSFCell.CELL_TYPE_STRING:							
							valueArray[k] = cell.getStringCellValue().trim().length() == 0 ? null : cell.getStringCellValue().trim();
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							valueArray[k] = null;
							break;
						default:
							error = "Error en el formato de la celda, columna " + (k + 1) + " fila " + (row.getRowNum() + 1);
							baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
						}
					}
				}

				// Validando fila completa en blanco y fila con algún valor en
				// blanco
				int withData = 0;
				int anyBlank = 0;
				for (int i = 0; i < valueArray.length; i++) {
					withData += (valueArray[i] != null && valueArray[i].length() != 0) ? 1 : 0;
					anyBlank += ((valueArray[i] == null || valueArray[i].length() == 0) && (i == 0)) ? 1 : 0;
				}
				// Fila completa en blanco
				if (withData == 0) {
					continue;
				}
				// Algún valor de la fila en blanco
				if (anyBlank > 0) {
					error = "Favor complete los datos obligatorios de la fila " + (row.getRowNum() + 1);
					baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
				}

				if (baseresulList.isEmpty()) {
					// VALIDA LOS DATOS
					// RUT
					if (valueArray[0] == null) {
						error = "Fila " + (row.getRowNum() + 1) + ": Debe especificar un valor de RUT";
						baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
					}
									
					if (baseresulList.isEmpty()) {
						if(!strKeyList.contains(valueArray[0]))
							strKeyList.add(valueArray[0]);
						else {
							error = "Fila " + (row.getRowNum() + 1) + ": La usuario no puede estar duplicadao.";
							baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
						
						}
					}
				}
			}
			
			if (baseresulList.size() > 0) {
				// Ordenar errores
				result.setBaseresults(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));	
				result = PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
			}
			else{
				initParamForServer.setPublishingId(initParams.getPublishingId());
				initParamForServer.setRuleTypeId(initParams.getRuleTypeId());
				initParamForServer.setStrKeys(strKeyList.toArray(new String[strKeyList.size()]));
			}			
			
		}catch (Exception e) {
			e.printStackTrace();
			result = PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}		
	}
	
	private void getUpdateCompanyPublishingInfo(UploadUserPublishingFileInitParamDTO initParams, UserPublishingRelationshipsInitParamsDTO initParamForServer, BaseResultsDTO result, Sheet sheet){
		
		final Integer ROWS_COUNT = 2;
		
		try {
			String error = "";
			List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();
			List<String> strKeyList = new ArrayList<String>();

			// Se buscan las filas en blanco
			List<Integer> rowindexList = new ArrayList<Integer>();
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				// Validando fila en blanco
				if ((row.getCell(0) == null || row.getCell(0).toString().trim().length() == 0) && 
					(row.getCell(1) == null || row.getCell(1).toString().trim().length() == 0)) {
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
				error = "El archivo cargado no posee información";
				baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
			}

			NumberFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(0);
			df.setGroupingUsed(false);

			// Valida si primera fila corresponde a encabezado
			int firstRowData = 0;

			if (sheet.getRow(0).getCell(0).toString().equals("RUT") && 
				sheet.getRow(0).getCell(1).toString().equals("Razón Social")) {

				firstRowData = 1;
			}

			// Guardamos los valores de cada columna de la fila en un arreglo de
			// String
			for (int j = firstRowData; j <= sheet.getLastRowNum(); j++) {
				Row row = sheet.getRow(j);
				if (row == null)
					continue;
				// Serán solo ROWS_COUNT columnas (no usar row.getLastCellNum() por si
				// vienen columnas vacías al final)
				String[] valueArray = new String[ROWS_COUNT];
				for (int k = 0; k < ROWS_COUNT; k++) {
					Cell cell = row.getCell(k);
					valueArray[k] = "";
					if (cell != null) {
						int cellType = cell.getCellType();
						switch (cellType) {
						case HSSFCell.CELL_TYPE_NUMERIC:
							valueArray[k] = df.format(cell.getNumericCellValue());
							break;
						case HSSFCell.CELL_TYPE_STRING:							
							valueArray[k] = cell.getStringCellValue().trim().length() == 0 ? null : cell.getStringCellValue().trim();
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							valueArray[k] = null;
							break;
						default:
							error = "Error en el formato de la celda, columna " + (k + 1) + " fila " + (row.getRowNum() + 1);
							baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
						}
					}
				}

				// Validando fila completa en blanco y fila con algún valor en
				// blanco
				int withData = 0;
				int anyBlank = 0;
				for (int i = 0; i < valueArray.length; i++) {
					withData += (valueArray[i] != null && valueArray[i].length() != 0) ? 1 : 0;
					anyBlank += ((valueArray[i] == null || valueArray[i].length() == 0) && (i == 0)) ? 1 : 0;
				}
				// Fila completa en blanco
				if (withData == 0) {
					continue;
				}
				// Algún valor de la fila en blanco
				if (anyBlank > 0) {
					error = "Favor complete los datos obligatorios de la fila " + (row.getRowNum() + 1);
					baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
				}

				if (baseresulList.isEmpty()) {
					// VALIDA LOS DATOS
					// CODIGO
					if (valueArray[0] == null) {
						error = "Fila " + (row.getRowNum() + 1) + ": Debe especificar un valor de Código";
						baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
					}
					
					if (baseresulList.isEmpty()) {
						if(!strKeyList.contains(valueArray[0]))
							strKeyList.add(valueArray[0]);
						else {
							error = "Fila " + (row.getRowNum() + 1) + ": La empresa no puede estar duplicada";
							baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
						
						}
					}
				}
			}
			
			if (baseresulList.size() > 0) {
				// Ordenar errores
				result.setBaseresults(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));		
				result = PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
			}
			else{
				initParamForServer.setPublishingId(initParams.getPublishingId());
				initParamForServer.setRuleTypeId(initParams.getRuleTypeId());
				initParamForServer.setStrKeys(strKeyList.toArray(new String[strKeyList.size()]));
			}			
			
		}catch (Exception e) {
			e.printStackTrace();
			result = PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}		
	}
	
	public FileDownloadInfoResultDTO getExcelUserPublishingRelationshipReport(Long publishingId){
		FileDownloadInfoResultDTO result = new FileDownloadInfoResultDTO();
		
		UserDTO[] report = null;
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");	
		
		try{
			
			AssignedRelationshipsInitParamDTO initParams = new AssignedRelationshipsInitParamDTO();
			initParams.setPublishingId(publishingId);			
								
			AssignedUsersArrayResultDTO resultReport = usermanagerremote.getAssignedUsersToPublishing(initParams, false);
			report = resultReport.getAssignedUsers();
			
			String downloadFilePath = new String(B2BSystemPropertiesUtil.getProperty("download_file_path"));
			
			//Crea el directorio si no existe - DSU
			FileHandlerUtils.createPathIfNotExists(downloadFilePath) ;
			
			String downloadPath = downloadFilePath + "USPU_" + sdf.format(now) + "_" + publishingId +"_hrs.xls";
			String downloadFileName = "Listado de Usuarios " + sdf.format(now)+".xls";
			
			DataRow row = null;

			// Escribir descripcion del filtro seleccionado
			DataTable dt0 = new DataTable("USU_PUB");

			DataColumn col01 = new DataColumn("logid", "Correo", String.class);
			DataColumn col02 = new DataColumn("name", "Nombre", String.class);
			DataColumn col03 = new DataColumn("lastname", "Apellido", String.class);
			DataColumn col04 = new DataColumn("position", "Cargo", String.class);
		
			dt0.addColumn(col01);	dt0.addColumn(col02);	dt0.addColumn(col03);	dt0.addColumn(col04);
	
			UserDTO lineReport = null;

			for (int i = 0; i < report.length; i++) {

				lineReport = report[i];
				row = dt0.newRow();

				row.setCellValue("logid", lineReport.getLogid());
				row.setCellValue("name", lineReport.getName());
				row.setCellValue("lastname", lineReport.getLastname());
				row.setCellValue("position", lineReport.getPosition());
											
				dt0.addRow(row);
			}
			
			// CREAR ARCHIVO
			XLSConverterPOI converter = new XLSConverterPOI();
			converter.setExcelheaderbold(true);
			converter.setShowexceltableborder(true);
		
			File downloadFile = new File(downloadPath);
			if (!downloadFile.exists())
				downloadFile.createNewFile();
			
			OutputStream outputStream = new FileOutputStream(downloadFile);	
		
			try {
				converter.ExportToXLS(dt0, outputStream, Charset.forName("UTF-16"));	
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				outputStream.close();
			}			
						
			result.setRealfilename(downloadPath);
			result.setDownloadfilename(downloadFileName);
		}catch(Exception e ){
			e.printStackTrace();
			result = PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}		
		
		return result;
	}
	
	public FileDownloadInfoResultDTO getExcelCompanyPublishingRelationshipReport(Long publishingId){
		FileDownloadInfoResultDTO result = new FileDownloadInfoResultDTO();
		
		CompanyDTO[] report = null;
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");	
		
		try{
			AssignedRelationshipsInitParamDTO initParams = new AssignedRelationshipsInitParamDTO();
			initParams.setPublishingId(publishingId);	
								
			AssignedCompaniesArrayResultDTO resultReport = usermanagerremote.getAssignedCompaniesToPublishing(initParams, false);
			report = resultReport.getAssignedCompanies();
						
			String downloadFilePath = new String(B2BSystemPropertiesUtil.getProperty("download_file_path"));
			
			//Crea el directorio si no existe - DSU
			FileHandlerUtils.createPathIfNotExists(downloadFilePath) ;
			
			String downloadPath = downloadFilePath + "COMPU_" + sdf.format(now) + "_" + publishingId +"_hrs.xls";
			String downloadFileName = "Listado de Empresas " + sdf.format(now) + ".xls";
			
			DataRow row = null;

			// Escribir descripcion del filtro seleccionado
			DataTable dt0 = new DataTable("COM_PUB");

			DataColumn col01 = new DataColumn("code", "RUT", String.class);
			DataColumn col02 = new DataColumn("socialreason", "Razón Social", String.class);
				
			dt0.addColumn(col01);	dt0.addColumn(col02);
	
			CompanyDTO lineReport = null;

			for (int i = 0; i < report.length; i++) {

				lineReport = report[i];
				row = dt0.newRow();

				row.setCellValue("code", lineReport.getRut());
				row.setCellValue("socialreason", lineReport.getName());
															
				dt0.addRow(row);
			}
			
			// CREAR ARCHIVO
			XLSConverterPOI converter = new XLSConverterPOI();
			converter.setExcelheaderbold(true);
			converter.setShowexceltableborder(true);
		
			File downloadFile = new File(downloadPath);
			if (!downloadFile.exists())
				downloadFile.createNewFile();
			
			OutputStream outputStream = new FileOutputStream(downloadFile);	
		
			try {
				converter.ExportToXLS(dt0, outputStream, Charset.forName("UTF-16"));	
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				outputStream.close();
			}			
						
			result.setRealfilename(downloadPath);
			result.setDownloadfilename(downloadFileName);
		}catch(Exception e ){
			e.printStackTrace();
			result = PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}		
		
		return result;			
	}

	@Override
	public AuditPopUpYearResultDTO getAuditPopUpYears() {
		AuditPopUpYearResultDTO result = new AuditPopUpYearResultDTO();
		try{
			result = publishingmanagerremote.getAuditPopUpYears();
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}

	@Override
	public AuditPopUpTitleResultDTO getTitlesOfAuditPopUpByYear(int year) {
		AuditPopUpTitleResultDTO result = new AuditPopUpTitleResultDTO();
		try{
			result = publishingmanagerremote.getTitlesOfAuditPopUpByYear(year);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}

	@Override
	public AuditPopUpReportResultDTO getAuditPopUpReport(AuditPopUpReportInitParamDTO initParamDTO) {
		AuditPopUpReportResultDTO result = new AuditPopUpReportResultDTO();
		try{
			result = publishingmanagerremote.getAuditPopUpReport(initParamDTO);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}
	}

	@Override
	public FileDownloadInfoResultDTO downloadAuditPopUpReport(AuditPopUpReportInitParamDTO initParamDTO,String filetype, Long uskey ) {
		
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		AuditPopUpReportResultDTO auditPopUpReportResultDTO = null;
		Locale locale = initParamDTO.getLocale();
		
		try {
			auditPopUpReportResultDTO = publishingmanagerremote.getAuditPopUpReport(initParamDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuario no disp
		}
		
		AuditPopUpReportDTO[] auditPopUpReportDTOs = auditPopUpReportResultDTO.getAuditPopUpReportDTOs();
		
		// Escribir descripcion del filtro seleccionado
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_auditpopup_title", locale));

		DataColumn col01 = new DataColumn("title", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_title", locale), String.class);
		DataColumn col02 = new DataColumn("type", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_type", locale), String.class);	
		DataColumn col03 = new DataColumn("userid", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "app_user_id", locale), String.class);			
		DataColumn col04 = new DataColumn("username", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_firstname", locale), String.class);
		DataColumn col05 = new DataColumn("userlastname", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_lastname", locale), String.class);
		DataColumn col06 = new DataColumn("useremail", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_email", locale), String.class);
		DataColumn col07 = new DataColumn("companyid", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "app_enterprise_id", locale), String.class);
		DataColumn col08 = new DataColumn("companyname", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_social_reason", locale), String.class);
		DataColumn col09 = new DataColumn("actiondate", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "app_action_date", locale), String.class);
		DataColumn col10 = new DataColumn("action", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_action", locale), String.class);
		
		dt0.addColumn(col01);
		dt0.addColumn(col02);
		dt0.addColumn(col03);
		dt0.addColumn(col04);
		dt0.addColumn(col05);
		dt0.addColumn(col06);
		dt0.addColumn(col07);
		dt0.addColumn(col08);
		dt0.addColumn(col09);
		dt0.addColumn(col10);
		
		
						
		DataRow row = null;
		AuditPopUpReportDTO line = null;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		for (int i = 0; i < auditPopUpReportDTOs.length; i++) {
			line = auditPopUpReportDTOs[i];

			row = dt0.newRow();
			row.setCellValue("title", initParamDTO.getTitle());
			row.setCellValue("type", line.getType());
			row.setCellValue("userid", line.getUserid());
			row.setCellValue("username", line.getUsername());
			row.setCellValue("userlastname", line.getUserlastname());
			row.setCellValue("useremail", line.getUseremail());
			row.setCellValue("companyid", line.getCompanycode());		
			row.setCellValue("companyname", line.getConpanyname());		
			row.setCellValue("actiondate", formatter.format(line.getActiondate()));
			row.setCellValue("action",line.getAction());
			
			dt0.addRow(row);
		}
		
		
		String downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_auditpopup_title", locale);
		String realname = "FICHAPROD";
		
		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, filetype, downloadname, realname, uskey);
		
		return resultDTO;
	}
	
	
	
}
