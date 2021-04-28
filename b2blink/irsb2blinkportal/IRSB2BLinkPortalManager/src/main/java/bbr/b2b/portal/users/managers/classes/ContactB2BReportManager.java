package bbr.b2b.portal.users.managers.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.NamingException;

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
import bbr.b2b.common.adtclasses.classes.FileUploadInitParamDTO;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.utils.FileHandlerUtils;
import bbr.b2b.portal.utils.I18NManager;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;
import bbr.b2b.users.contacts.managers.interfaces.IContactB2BReportManager;
import bbr.b2b.users.managers.interfaces.ContextUtil;
import bbr.b2b.users.managers.interfaces.IUserManager;
import bbr.b2b.users.report.classes.CompanyClassificationArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyClassificationInfoDTO;
import bbr.b2b.users.report.classes.CompanyClassificationInitParamDTO;
import bbr.b2b.users.report.classes.CompanyClassificationReportInitParamDTO;
import bbr.b2b.users.report.classes.CompanyClassificationReportResultDTO;
import bbr.b2b.users.report.classes.ContactB2BArrayResultDTO;
import bbr.b2b.users.report.classes.ContactB2BDTO;
import bbr.b2b.users.report.classes.ContactDataInitParamDTO;
import bbr.b2b.users.report.classes.ContactLogDataDTO;
import bbr.b2b.users.report.classes.ContactPhoneTypeResultDTO;
import bbr.b2b.users.report.classes.ContactPositionArrayResultDTO;
import bbr.b2b.users.report.classes.ContactProvArrayResultDTO;
import bbr.b2b.users.report.classes.EditContactProviderInfoResultDTO;
import bbr.b2b.users.report.classes.ExcelCompanyClassificationDTO;
import bbr.b2b.users.report.classes.ExcelProviderContactReportResultDTO;
import bbr.b2b.users.report.classes.ExcelUploadCompanyClassificationInitParamDTO;
import bbr.b2b.users.report.classes.LogInfoResultDTO;
import bbr.b2b.users.report.classes.LogInfoUserDTO;
import bbr.b2b.users.report.classes.PositionResultDTO;
import bbr.b2b.users.report.classes.ProviderContactReportDataDTO;
import bbr.b2b.users.report.classes.ProviderContactReportInitParamDTO;
import bbr.b2b.users.report.classes.ProviderContactReportResultDTO;
import bbr.b2b.users.report.classes.SendMailContactInitParamDTO;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UserResultDTO;
import bbr.common.dataset.util.DataColumn;
import bbr.common.dataset.util.DataRow;
import bbr.common.dataset.util.DataTable;

@Stateless(name = "managers/ContactB2BReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ContactB2BReportManager implements ContactB2BReportManagerLocal {
	
	private IUserManager userManager = null;
	
	private IContactB2BReportManager contactB2BReportManager = null;
	
	private DateTimeFormatter dateFormatForLog = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
	@PostConstruct
	public void getRemote() {
		try {
			contactB2BReportManager = ContextUtil.getInstance().getIContactB2BReportManager();
			userManager = ContextUtil.getInstance().getIUserManager();
		} catch (NamingException e) {			
			e.printStackTrace();
		}
	}
	
	public ContactB2BArrayResultDTO getRetailerContacts(int pageNumber, int rows, boolean isPaginated) {

		ContactB2BArrayResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.getRetailerContacts(pageNumber, rows, isPaginated);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}

	public FileDownloadInfoResultDTO downloadRetailerContacts(Long uskey, String fileformat, Locale locale) {

		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		ContactB2BArrayResultDTO contactB2BArrayResultDTO = new ContactB2BArrayResultDTO();

		try {
			contactB2BArrayResultDTO = contactB2BReportManager.getRetailerContacts(0, 0, false);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P100");// modulo comercial no disp.
		}		
			ContactB2BDTO[] contactB2BDTOs = contactB2BArrayResultDTO.getContactB2BDTOs();
	
			DataRow row = null;
			DataTable dt0 = new DataTable("CONTACTOS_RETAILER");
	
			DataColumn col01 = new DataColumn("nombre", "Nombre", String.class);
			DataColumn col02 = new DataColumn("apellido", "Apellido", String.class);
			DataColumn col03 = new DataColumn("cargo", "Cargo", String.class);
			DataColumn col04 = new DataColumn("telefono", "Teléfono", String.class);
			DataColumn col05 = new DataColumn("area", "Área", String.class);
			DataColumn col06 = new DataColumn("email", "Email", String.class);
	
			dt0.addColumn(col01);
			dt0.addColumn(col02);
			dt0.addColumn(col03);
			dt0.addColumn(col04);
			dt0.addColumn(col05);
			dt0.addColumn(col06);
	
			ContactB2BDTO line = null;
	
			for (int i = 0; i < contactB2BDTOs.length; i++) {
	
				line = contactB2BDTOs[i];
				row = dt0.newRow();
	
				row.setCellValue("nombre", line.getName());
				row.setCellValue("apellido", line.getLastname());
				row.setCellValue("cargo", line.getPosition());
				row.setCellValue("telefono", line.getPhone());
				row.setCellValue("area", line.getDepartment());
				row.setCellValue("email", line.getEmail());
	
				dt0.addRow(row);
			}
	
			String downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_provider_retail_contacts",locale);
			String realname = "CONTACTSRETAIL";
			
			resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, fileformat, downloadname, realname, uskey);
			
			return resultDTO;
			
			// Construccion del excel
			// 1 - Obtener el nombre del archivo
//			Date date = new Date();
//			String downloadFilePath = new String(B2BSystemPropertiesUtil.getProperty("download_file_path"));
//			
//			//Crea el directorio si no existe
//			FileHandlerUtils.createPathIfNotExists(downloadFilePath) ;
//			
//			String downloadPath =  "CONTACTSRETAIL_" + date.getTime() + "_" + uskey + ".xls";
//			String downloadFileName = "ContactosRetailer_" + dateFormat.format(date) + ".xls";
//			
//			CompressToZipFileInitParamDTO compressToZipFileInitParamDTO = new CompressToZipFileInitParamDTO();
//			compressToZipFileInitParamDTO.setSourceFileName(downloadPath);
//			compressToZipFileInitParamDTO.setDownloadFileName(downloadFileName);
//			CompressToZipFileResultDTO compressToZipFileResultDTO = null;
//	
			// CREAR ARCHIVO
//			XLSConverterPOI converter = new XLSConverterPOI();
//			converter.setExcelheaderbold(true);
//			converter.setShowexceltableborder(true);
//		
//			File downloadFile = new File(downloadPath);
//			if (!downloadFile.exists())
//				downloadFile.createNewFile();
//			
//			OutputStream outputStream = new FileOutputStream(downloadFile);	
//		
//			try {
//				converter.ExportToXLS(dt0, outputStream, Charset.forName("UTF-16"));	
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally{
//				outputStream.close();
//			}			
//				
//			try {
//				compressToZipFileResultDTO = FileHandlerUtils.getInstance().compressToZipFile(compressToZipFileInitParamDTO);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
//			}
//			
//			resultDTO.setRealfilename(compressToZipFileResultDTO.getRealFileName());
//			resultDTO.setDownloadfilename(compressToZipFileResultDTO.getDownloadFileName());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
//		}
//		
	}
	
	public BaseResultsDTO doUploadRetailerContacts(FileUploadInitParamDTO initParams, Long userId){
		BaseResultsDTO result = new BaseResultsDTO();
		Workbook workBook = null;
		UserResultDTO userResult = null;
		
		try {
			userResult = userManager.getUserById(userId);
			
			File inputFile = new File(initParams.getFilePath()+initParams.getFileName());
			FileInputStream inputStream = new FileInputStream(inputFile);
			
			String extension = FileHandlerUtils.getFileExtension(initParams.getFileName());
			
			if(extension != null)
			{
				if (extension.equalsIgnoreCase("xlsx")) 
				{
					workBook = new XSSFWorkbook(inputStream);
				} 
				else if (extension.equalsIgnoreCase("xls")) 
				{
					POIFSFileSystem fs = new POIFSFileSystem(inputStream);
					workBook = new HSSFWorkbook(fs);
				}	
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
			
			ContactB2BDTO[] contacts = null;
			ContactLogDataDTO contactLog = new ContactLogDataDTO();
			
			contacts = getUpdateContactsB2B(contactLog, userResult.getUser(), result, sheet);			
			
			if (result.getStatuscode().equals("0")){
				result = contactB2BReportManager.updateRetailerContacts(contacts, contactLog);	
			}	
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}	
				
		return result;		
	}
		
	private ContactB2BDTO[] getUpdateContactsB2B(ContactLogDataDTO contactLog, UserDTO user, BaseResultsDTO result, Sheet sheet){
		
		final Integer ROWS_COUNT = 6;
		List<ContactB2BDTO> contactList = new ArrayList<ContactB2BDTO>();
		ContactB2BDTO contactB2BDTO = null;
		LocalDateTime now = LocalDateTime.now();
		
		ContactB2BDTO[] contacts = null;
		
		try {
			String error = "";
			List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();			

			// Se buscan las filas en blanco
			List<Integer> rowindexList = new ArrayList<Integer>();
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				// Validando fila en blanco
				if ((row.getCell(0) == null || row.getCell(0).toString().trim().length() == 0) && 
					(row.getCell(1) == null || row.getCell(1).toString().trim().length() == 0) && 
					(row.getCell(2) == null || row.getCell(2).toString().trim().length() == 0) && 
					(row.getCell(3) == null || row.getCell(3).toString().trim().length() == 0) && 
					(row.getCell(4) == null || row.getCell(4).toString().trim().length() == 0) && 
					(row.getCell(5) == null || row.getCell(5).toString().trim().length() == 0)) {
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

			if (sheet.getRow(0).getCell(0).toString().equals("Nombre") && 
				sheet.getRow(0).getCell(1).toString().equals("Apellido") && 
				sheet.getRow(0).getCell(2).toString().equals("Cargo") && 
				sheet.getRow(0).getCell(3).toString().equals("Teléfono") && 
				sheet.getRow(0).getCell(4).toString().equals("Área") && 
				sheet.getRow(0).getCell(5).toString().equals("Email")) {

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
					// Nombre
					if (valueArray[0] == null) {
						error = "Fila " + (row.getRowNum() + 1) + ": Debe especificar un valor de Nombre";
						baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
					}
					// Apellido
					if (valueArray[1] == null) {
						error = "Fila " + (row.getRowNum() + 1) + ": Debe especificar un valor de Apellido";
						baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
					}
					// Cargo
					if (valueArray[2] == null) {
						error = "Fila " + (row.getRowNum() + 1) + ": Debe especificar un valor de Cargo";
						baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
					}
					// Teléfono
					if (valueArray[3] == null) {
						error = "Fila " + (row.getRowNum() + 1) + ": Debe especificar un valor de Teléfono";
						baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
					}
					// Área
					if (valueArray[4] == null) {
						error = "Fila " + (row.getRowNum() + 1) + ": Debe especificar un valor de Área";
						baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
					}
					// Email
					if (valueArray[5] == null) {
						error = "Fila " + (row.getRowNum() + 1) + ": Debe especificar un valor de Email";
						baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
					}					
									
					if (baseresulList.isEmpty()) {			
						
						contactB2BDTO = new ContactB2BDTO();
						contactB2BDTO.setName(valueArray[0]);
						contactB2BDTO.setLastname(valueArray[1]);
						contactB2BDTO.setPosition(valueArray[2]);
						contactB2BDTO.setPhone(valueArray[3]);
						contactB2BDTO.setDepartment(valueArray[4]);
						contactB2BDTO.setEmail(valueArray[5]);
						contactB2BDTO.setOrder(j);

						contactList.add(contactB2BDTO);					
					}
				}
			}
			
			if (baseresulList.size() > 0) {
				// Ordenar errores
				result.setBaseresults(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));				
			}
			else{
				contacts = contactList.toArray(new ContactB2BDTO[contactList.size()]);
				
				contactLog.setFecha(now);
				contactLog.setName(user.getName());
				contactLog.setLastname(user.getLastname());
				contactLog.setUsdata(user.getName() + " " + user.getLastname() + " (" + now.format(dateFormatForLog) + ")");				
			}			
			
		}catch (Exception e) {
			e.printStackTrace();
			result = PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}		
		
		return contacts;		
	}
	
	public BaseResultDTO sendEmailContact(SendMailContactInitParamDTO initParams, String userRut){
		BaseResultDTO result = new BaseResultDTO();
		
		try{
		
			result = contactB2BReportManager.sendEmailContact(initParams, userRut);
		
			String[] attachments = initParams.getAttachments();
			
			if (attachments != null) {

				// BORRA LOS ARCHIVOS
				for (int i = 0; i < attachments.length; i++) {
					File file = new File(PortalConstants.getInstance().getFileUploadPath() + "CONTACT/" + attachments[i]);

					if (!file.delete()) {
						System.out.println("El archivo " + attachments[i] + " no pudo ser borrado");
					}
				}			
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			result = PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
		}	

		return result;
	}	
	
	public PositionResultDTO getAllProvidersAndPositions() {
		PositionResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.getAllProvidersAndPositions();
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	public ProviderContactReportResultDTO getProviderContactReportByProviderAndPosition(ProviderContactReportInitParamDTO initParams, boolean isPaginated, Long uskey) {
		ProviderContactReportResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.getProviderContactReportByProviderAndPosition(initParams, isPaginated);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	public FileDownloadInfoResultDTO downloadProviderContactReportByProviderAndPosition(ProviderContactReportInitParamDTO initParams, Long uskey, Locale locale, String fileformat) {

		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		ExcelProviderContactReportResultDTO reportResultDTO = new ExcelProviderContactReportResultDTO();
		ProviderContactReportDataDTO[] reportData = null;

		try {
			reportResultDTO = contactB2BReportManager.getExcelProviderContactReportByProviderAndPosition(initParams);
				
			reportData = reportResultDTO.getContactData();
	
			DataRow row = null;
			DataTable dt0 = new DataTable("CONTACTOS_PROVEEDORES");
	
			DataColumn col01 = new DataColumn("codprov",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_provider_code",locale), String.class);
			DataColumn col02 = new DataColumn("razonSocial",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_social_reason_provider",locale), String.class);
			DataColumn col03 = new DataColumn("nombre",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_contact_name",locale), String.class);
			DataColumn col04 = new DataColumn("cargo", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_positions",locale), String.class);
//			DataColumn col05 = new DataColumn("area",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_area",locale), String.class);
			DataColumn col06 = new DataColumn("telefono", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_phones",locale), String.class);
			DataColumn col07 = new DataColumn("email", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_email",locale), String.class);
			DataColumn col08 = new DataColumn("actualizado", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_updated_by",locale), String.class);
			DataColumn col09 = new DataColumn("fecha",  I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_last_update",locale), String.class);
	
			dt0.addColumn(col01);	dt0.addColumn(col02);	dt0.addColumn(col03);	dt0.addColumn(col04);
//			dt0.addColumn(col05);	
			dt0.addColumn(col06);	dt0.addColumn(col07);	dt0.addColumn(col08);
			dt0.addColumn(col09);	
	
			ProviderContactReportDataDTO line = null;
	
			for (int i = 0; i < reportData.length; i++) {
	
				line = reportData[i];
				row = dt0.newRow();
	
				row.setCellValue("codprov", line.getProvidercode());
				row.setCellValue("razonSocial", line.getSocialreason());
				row.setCellValue("nombre", line.getName()+" "+line.getLastname());
				row.setCellValue("cargo", Arrays.stream(line.getPositions()).map(c -> c.getName() + (c.getArea() !=null && !c.getArea().isEmpty()?" " + c.getArea():"")).collect(Collectors.joining(" - ")));
//				row.setCellValue("area",  Arrays.stream(line.getPositions()).map(c -> c.getArea()).collect(Collectors.joining(" - ")));
				row.setCellValue("telefono", Arrays.stream(line.getPhones()).map(p -> p.getNumber()).collect(Collectors.joining(" - ")));
				row.setCellValue("email", line.getEmail());
				row.setCellValue("actualizado", line.getLogname() +" "+ line.getLogapellido());
				row.setCellValue("fecha", line.getFecha());			
	
				dt0.addRow(row);
			}
	
			String downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_providercontact_title", locale);
			String realname = "CONTACTSPROV";
			
			resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, fileformat, downloadname, realname, uskey);
			
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	@Override
	public ContactProvArrayResultDTO getProviderContacts(Long pvkey) { 
		ContactProvArrayResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.getProviderContacts(pvkey);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	@Override
	public PositionResultDTO validateContactProviders(Long pvkey, Long uskey) { 
		PositionResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.validateContactProviders(pvkey, uskey);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	
	public BaseResultDTO addProviderContact(ContactDataInitParamDTO contactProvWs, Long pvkey, ContactLogDataDTO logdata) {
		BaseResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.addProviderContact(contactProvWs, pvkey, logdata);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	public BaseResultDTO updateProviderContact(ContactDataInitParamDTO contactProvWs, Long pvkey, ContactLogDataDTO logdata) {
		BaseResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.updateProviderContact(contactProvWs, pvkey, logdata);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	public BaseResultDTO deleteProviderContact(Long contactProvId, Long pvkey, ContactLogDataDTO logdata) {
		BaseResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.deleteProviderContact(contactProvId, pvkey, logdata);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	@Override
	public BaseResultDTO doConfirmContactInformation(ContactLogDataDTO logdata, Long pvkey) {
		BaseResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.doConfirmContactInformation(logdata, pvkey);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	@Override
	public ContactPhoneTypeResultDTO getPhoneTypes() {
		ContactPhoneTypeResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.getPhoneTypes();
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	@Override
	public ContactPositionArrayResultDTO getPositionsAndContactAssigned(Long ctpkey, Long prkey) {
		ContactPositionArrayResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.getPositionsAndContactAssigned(ctpkey, prkey);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	@Override
	public CompanyClassificationArrayResultDTO getCompanyClassifications() {
		CompanyClassificationArrayResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.getCompanyClassifications();
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	@Override
	public BaseResultDTO doAddCompanyClassification(CompanyClassificationInitParamDTO initParamDTO) {
		BaseResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.doAddCompanyClassification(initParamDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	@Override
	public BaseResultDTO doEditCompanyClassification(CompanyClassificationInitParamDTO initParamDTO) {
		BaseResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.doEditCompanyClassification(initParamDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	@Override
	public BaseResultDTO doDeleteCompanyClassification(Long[] csekey, LogInfoUserDTO loginfo) {
		BaseResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.doDeleteCompanyClassification(csekey, loginfo);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	@Override
	public LogInfoResultDTO getLastCompanyClassificationLogByType(Integer type) {
		LogInfoResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.getLastCompanyClassificationLogByType(type);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	@Override
	public EditContactProviderInfoResultDTO getEditContactProviderInfo(Long ctpkey, Long prkey) {
		EditContactProviderInfoResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.getEditContactProviderInfo(ctpkey, prkey);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	
	public BaseResultsDTO doUpdateCompaniesClassificationExcel(FileUploadInitParamDTO initParams, boolean isMassive, LogInfoUserDTO loginfoDTO) {
		BaseResultsDTO result = new BaseResultsDTO();
		
		Workbook workBook = null;
		
		try {
			String filepath = initParams.getFilePath();
			String filename = initParams.getFileName();
			File inputFile = new File(filepath + filename);
			FileInputStream inputStream = new FileInputStream(inputFile);

			workBook = new XSSFWorkbook(inputStream);
			
			Sheet sheet = workBook.getSheetAt(0);
			String error = "";
			List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();
			List<ExcelCompanyClassificationDTO> excelCompanyClassificationList = new ArrayList<ExcelCompanyClassificationDTO>();
			List<String> validatedata = new ArrayList<String>();
			// Se buscan las filas en blanco
			List<Integer> rowindexList = new ArrayList<Integer>();
			Iterator<Row> rowIterator = sheet.rowIterator();
			final int ROWS_COUNT = 2;
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
			if (sheet.getPhysicalNumberOfRows() <= 1) {
				error = "El archivo cargado no posee información";
				baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
			}
			
			
			NumberFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(0);
			df.setGroupingUsed(false);
			// Valida si primera fila corresponde a encabezado
			int firstRowData = 0;

			if (sheet.getRow(0).getCell(0).toString().equals("Cód. Empresa") && 
				sheet.getRow(0).getCell(1).toString().equals("Cód. Clasificación")) {

				firstRowData = 1;
			}

			// Guardamos los valores de cada columna de la fila en un arreglo de
			// String
			boolean isValidFirstHeader = true;
			boolean isValidSecondHeader = true;
			for (int j = firstRowData; j <= sheet.getLastRowNum(); j++) {
				Row row = sheet.getRow(j);
				if (row == null)
					continue;
				// Serán solo ROWS_COUNT columnas (no usar row.getLastCellNum() por si
				// vienen columnas vacías al final)
				ExcelCompanyClassificationDTO excelCompanyClassificationDTO = new ExcelCompanyClassificationDTO();
				for (int k = 0; k < ROWS_COUNT; k++) {
					Cell cell = row.getCell(k);
					if (cell != null) {
						int cellType = cell.getCellType();
						switch (cellType) {
						case HSSFCell.CELL_TYPE_NUMERIC:
							if(sheet.getRow(0).getCell(0).getStringCellValue().equalsIgnoreCase("Cód. Empresa") && k == 0) {
								excelCompanyClassificationDTO.setCompanycode(df.format(cell.getNumericCellValue()));
							}
							if(sheet.getRow(0).getCell(1).getStringCellValue().equalsIgnoreCase("Cód. Clasificación") && k == 1) {
								excelCompanyClassificationDTO.setClassificationcode(df.format(cell.getNumericCellValue()));
							}
							break;
						case HSSFCell.CELL_TYPE_STRING:	
							if(k == 0){
								if(sheet.getRow(0).getCell(0).getStringCellValue().equalsIgnoreCase("Cód. Empresa")) {
									excelCompanyClassificationDTO.setCompanycode(cell.getStringCellValue().trim().length() == 0 ? null : cell.getStringCellValue().trim());
								}else{
									isValidFirstHeader = false;
								}
							}
							if(k == 1){
								if(sheet.getRow(0).getCell(1).getStringCellValue().equalsIgnoreCase("Cód. Clasificación")) {
									excelCompanyClassificationDTO.setClassificationcode(cell.getStringCellValue().trim().length() == 0 ? null : cell.getStringCellValue().trim());
								}else{
									isValidSecondHeader = false;
								}
							}
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						default:
							error = "Error en el formato de la celda, columna " + (k + 1) + " fila " + (row.getRowNum() + 1);
							baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
						}
					}
				}

				if (isValidFirstHeader && isValidSecondHeader) {
					// Algún valor de la fila en blanco
					if (excelCompanyClassificationDTO.getCompanycode() == null || excelCompanyClassificationDTO.getCompanycode().trim().length() == 0) {
						error = "Favor complete los datos obligatorios de la fila " + (row.getRowNum() + 1);
						baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
					}
				}
				else{
					String headerError = isValidFirstHeader == false ? "Cód. Empresa":"Cód. Clasificación";
					error = "Error en nombre de la columna "+  headerError;
					baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
					break;
				}


				if (baseresulList.isEmpty()) {
					if(!validatedata.contains(excelCompanyClassificationDTO.getCompanycode())) {
						excelCompanyClassificationList.add(excelCompanyClassificationDTO);
						validatedata.add(excelCompanyClassificationDTO.getCompanycode());
					}
					else {
						error = "Fila " + (row.getRowNum() + 1) + ": Cód. Empresa debe estar asociado a solo un Cód. Clasificación (o vacía)";
						baseresulList.add(PortalStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "P3501", error, false));
					}
				}
			}
			
			if (baseresulList.size() > 0) {
				// Ordenar errores
				if(baseresulList.size() == 1){
					result.setStatuscode(baseresulList.get(0).getStatuscode());
					result.setStatusmessage(baseresulList.get(0).getStatusmessage());
				}else{
					result.setBaseresults(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));	
					result = PortalStatusCodeUtils.getInstance().setStatusCode(result, "P200");
				}
			}
			else{
				 
				ExcelCompanyClassificationDTO[] excelCompanyClassificationDTOs = new ExcelCompanyClassificationDTO[excelCompanyClassificationList.size()];
				excelCompanyClassificationList.toArray(excelCompanyClassificationDTOs);
				ExcelUploadCompanyClassificationInitParamDTO initParamDTO = new ExcelUploadCompanyClassificationInitParamDTO();
				initParamDTO.setExcelCompanyClassificationDTOs(excelCompanyClassificationDTOs);
				initParamDTO.setMassive(isMassive);
				initParamDTO.setLoginfoDTO(loginfoDTO);
				BaseResultDTO resultDTO = contactB2BReportManager.doUpdateCompaniesClassificationExcel(initParamDTO);
				BeanExtenderFactory.copyProperties(resultDTO, result);
			}			
		}catch (Exception e) {
			e.printStackTrace();
			result = PortalStatusCodeUtils.getInstance().setStatusCode(result, isMassive ?  "P2001":"P2002" );
		} finally {
			if(workBook != null) {
				try {
					workBook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public FileDownloadInfoResultDTO downloadCompanyClassificationUpdateFileFormat(Long uskey, String fileformat, Locale locale ) {
		
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_company_classification_title", locale));

		DataColumn col01 = new DataColumn("enterprise_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "enterprise_code", locale), String.class);
		DataColumn col02 = new DataColumn("classification_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "classification_code", locale), String.class);
		
		dt0.addColumn(col01);
		dt0.addColumn(col02);
		
		String downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_template_company_classification_title", locale);
		String realname = "CLASIFICACIONEMRPESAS";
		
		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, fileformat, downloadname, realname, uskey);
		
		return resultDTO;
	}
	
	public FileDownloadInfoResultDTO downloadCompanyClassificationExcelReport(CompanyClassificationReportInitParamDTO initParamDTO, Long uskey, String fileformat ) {
		
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		
		try {
			Locale locale = initParamDTO.getLocale();
			CompanyClassificationReportResultDTO reportDTO = null;
			reportDTO = contactB2BReportManager.getCompanyClassificationReportByFilterType(initParamDTO);
			
			DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_company_classification_title", locale));
	
			DataColumn col01 = new DataColumn("enterprise_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "enterprise_code", locale), String.class);
			DataColumn col02 = new DataColumn("classification_code", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "classification_code", locale), String.class);
			
			dt0.addColumn(col01);
			dt0.addColumn(col02);
			CompanyClassificationInfoDTO[] companyClassificationInfoArray = reportDTO.getCompanyClassificationInfoDTOs();
			DataRow row = null;
			CompanyClassificationInfoDTO line = null;
			for (int i = 0; i < companyClassificationInfoArray.length; i++) {
				line = companyClassificationInfoArray[i];
				row = dt0.newRow();
				row.setCellValue("enterprise_code", line.getEmrut());
				row.setCellValue("classification_code", line.getClassificationname());
				
				dt0.addRow(row);
			}
			
			String downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_company_classification_title", locale);
			String realname = "CLASIFICACIONEMRPESAS";
			
			resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, fileformat, downloadname, realname, uskey);
		} catch(Exception ex) {
			ex.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
	
	@Override
	public CompanyClassificationReportResultDTO getCompanyClassificationReportByFilterType(CompanyClassificationReportInitParamDTO initParamDTO) {
		CompanyClassificationReportResultDTO resultDTO = null;

		try {
			resultDTO = contactB2BReportManager.getCompanyClassificationReportByFilterType(initParamDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
		return resultDTO;
	}
}
