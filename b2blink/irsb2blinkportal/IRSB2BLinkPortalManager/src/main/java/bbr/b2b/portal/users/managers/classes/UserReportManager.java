package bbr.b2b.portal.users.managers.classes;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.codec.binary.Base64;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.common.util.Mailer;
import bbr.b2b.common.util.StringUtils;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.constants.PortalResources;
import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;
import bbr.b2b.portal.utils.FileHandlerUtils;
import bbr.b2b.portal.utils.I18NManager;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;
import bbr.b2b.portal.wrapper.CompressToZipFileInitParamDTO;
import bbr.b2b.portal.wrapper.CompressToZipFileResultDTO;
import bbr.b2b.users.managers.interfaces.ContextUtil;
import bbr.b2b.users.managers.interfaces.ICompanyTypeManager;
import bbr.b2b.users.managers.interfaces.IUserManager;
import bbr.b2b.users.report.classes.ActiveUserLocalDTO;
import bbr.b2b.users.report.classes.ActiveUserReportDataDTO;
import bbr.b2b.users.report.classes.AddFaqInitParamDTO;
import bbr.b2b.users.report.classes.AssignedCompaniesArrayResultDTO;
import bbr.b2b.users.report.classes.AssignedEventsArrayResultDTO;
import bbr.b2b.users.report.classes.AssignedProfilesArrayResultDTO;
import bbr.b2b.users.report.classes.AssignedRelationshipsInitParamDTO;
import bbr.b2b.users.report.classes.AssignedUsersArrayResultDTO;
import bbr.b2b.users.report.classes.ChangeLogIdInitParamDTO;
import bbr.b2b.users.report.classes.CompanyArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyDTO;
import bbr.b2b.users.report.classes.CompanyDataArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import bbr.b2b.users.report.classes.CompanyDataResultDTO;
import bbr.b2b.users.report.classes.CompanyReportInitParamDTO;
import bbr.b2b.users.report.classes.CompanyReportResultDTO;
import bbr.b2b.users.report.classes.CompanyResultDTO;
import bbr.b2b.users.report.classes.CompanyTypeResultDTO;
import bbr.b2b.users.report.classes.ContactUsDTO;
import bbr.b2b.users.report.classes.EventArrayResultDTO;
import bbr.b2b.users.report.classes.FaqReportArrayResultDTO;
import bbr.b2b.users.report.classes.FaqReportInitParamDTO;
import bbr.b2b.users.report.classes.FaqsResultDTO;
import bbr.b2b.users.report.classes.LabelCommentValueArrayResultDTO;
import bbr.b2b.users.report.classes.LabelCommentValueDTO;
import bbr.b2b.users.report.classes.LocationArrayResultDTO;
import bbr.b2b.users.report.classes.PositionResultDTO;
import bbr.b2b.users.report.classes.ProfileArrayResultDTO;
import bbr.b2b.users.report.classes.ProviderUserCompanyDTO;
import bbr.b2b.users.report.classes.ProviderUserProfileDTO;
import bbr.b2b.users.report.classes.SelfManagementUpdateUserInfoDTO;
import bbr.b2b.users.report.classes.UserActivateInitParamDTO;
import bbr.b2b.users.report.classes.UserAdminRetailFilterInitParam;
import bbr.b2b.users.report.classes.UserAuditDTO;
import bbr.b2b.users.report.classes.UserAuditInitParamDTO;
import bbr.b2b.users.report.classes.UserAuditMinDateResultDTO;
import bbr.b2b.users.report.classes.UserAuditReportResultDTO;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UserDataDTO;
import bbr.b2b.users.report.classes.UserDataInfoDTO;
import bbr.b2b.users.report.classes.UserForNewsResultDTO;
import bbr.b2b.users.report.classes.UserInitParamDTO;
import bbr.b2b.users.report.classes.UserLikeInitParamDTO;
import bbr.b2b.users.report.classes.UserMergeInitParamDTO;
import bbr.b2b.users.report.classes.UserReportDataDTO;
import bbr.b2b.users.report.classes.UserReportInitParamDTO;
import bbr.b2b.users.report.classes.UserResultDTO;
import bbr.b2b.users.report.classes.UsersResultDTO;
import bbr.common.dataset.util.CSVConverter;
import bbr.common.dataset.util.DataColumn;
import bbr.common.dataset.util.DataRow;
import bbr.common.dataset.util.DataTable;
import bbr.common.dataset.util.XLSConverterJXL;

@Stateless(name = "managers/UserReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserReportManager implements UserReportManagerLocal {

	private IUserManager userReportManagerRemote = null;
	private ICompanyTypeManager companyTypeManager = null;
		
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HHmmss");	

	@PostConstruct
	public void getRemote() {
		try {
			userReportManagerRemote = ContextUtil.getInstance().getIUserManager();
			companyTypeManager = ContextUtil.getInstance().getICompanyTypeManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveCompanySelectedAndAddCountUserProvider(Long uskey, String pvcode) {
		try {
			
			// Actualiza el contador de la subempresa para el usuario
			userReportManagerRemote.addCountUserProviderRelation(uskey, pvcode);
			
			// Almacena la última subempresa seleccionada
			userReportManagerRemote.saveRelationinMRUL(uskey, "MRULEMPRESA", pvcode);
			
		} catch (Exception e1) {
			e1.printStackTrace();
			// Si hay algún problema con el módulo de usuario se debe seguir ejecutando el servicio que lo llamó
		}				
	}
	
	public Long[] getProviderIdsByUserId(Long userid){
		
		Long[] result = null;
		
		try {
			result = userReportManagerRemote.getProviderIdsByUserId(userid);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public CompanyDataResultDTO getMostUsedProvidersByUserId(Long userid) {
		
		CompanyDataResultDTO resultDTO = new CompanyDataResultDTO();
		
		try {
			resultDTO = userReportManagerRemote.getMostUsedProvidersByUserId(userid);
			if(resultDTO.getMostUsedCompanyDataDTOs()!=null && resultDTO.getMostUsedCompanyDataDTOs().length <= 0){
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P7002");// sin proveedores
			}
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public CompanyDataArrayResultDTO findCompanyOfUserByCode(Long userid, String code, boolean allenterprises) {
		
		CompanyDataArrayResultDTO resultDTO = new CompanyDataArrayResultDTO();
		
		
		try {
			
			if(allenterprises){ // si tiene todas las empresas la busqueda se hace en todos los proveedores
				
				CompanyArrayResultDTO companyArrayResultDTO = userReportManagerRemote.findCompanyLikeCode(code);
				
				CompanyDataDTO[] companyDataDTOs = Stream.of(companyArrayResultDTO.getCompanyDTOs()).map(c -> {
					CompanyDataDTO comp = new CompanyDataDTO();
					comp.setId(c.getId());
					comp.setRut(c.getRut());
					comp.setName(c.getName());
					return comp;
				}).toArray(CompanyDataDTO[]::new);
				
				resultDTO.setCompanyDataDTOs(companyDataDTOs);
				
				
				
				
			}else{ // si no tiene todas la empresas se busca en los que tiene asignado
				resultDTO =  userReportManagerRemote.findCompanyOfUserByCode(userid, code);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
		
		return resultDTO;
	}


	public CompanyDataArrayResultDTO findCompanyOfUserByCodeAndType(Long userid, String code, boolean allenterprises , String type) {
		
		CompanyDataArrayResultDTO resultDTO = new CompanyDataArrayResultDTO();
		try {
			
			if(allenterprises){ // si tiene todas las empresas la busqueda se hace en todos los proveedores
				
				CompanyArrayResultDTO companyArrayResultDTO = userReportManagerRemote.findCompanyLikeCodeAndType(code,type);
				
				CompanyDataDTO[] companyDataDTOs = Stream.of(companyArrayResultDTO.getCompanyDTOs()).map(c -> {
					CompanyDataDTO comp = new CompanyDataDTO();
					comp.setId(c.getId());
					comp.setRut(c.getRut());
					comp.setName(c.getName());
					return comp;
				}).toArray(CompanyDataDTO[]::new);
				
				resultDTO.setCompanyDataDTOs(companyDataDTOs);
				
				
				
				
			}else{ // si no tiene todas la empresas se busca en los que tiene asignado
				resultDTO =  userReportManagerRemote.findCompanyOfUserByCode(userid, code);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
		
		return resultDTO;
	}

	public CompanyDataArrayResultDTO findCompanyOfUserByName(Long userid, String name, boolean allenterprises) {

		CompanyDataArrayResultDTO resultDTO = new CompanyDataArrayResultDTO();
		
		
		try {
			
			if(allenterprises){ // si tiene todas las empresas la busqueda se hace en todos los proveedores
				
				CompanyArrayResultDTO companyArrayResultDTO = userReportManagerRemote.findCompanyLikeName(name);
				
				CompanyDataDTO[] companyDataDTOs = Stream.of(companyArrayResultDTO.getCompanyDTOs()).map(c -> {
					CompanyDataDTO comp = new CompanyDataDTO();
					comp.setId(c.getId());
					comp.setRut(c.getRut());
					comp.setName(c.getName());
					return comp;
				}).toArray(CompanyDataDTO[]::new);
				
				resultDTO.setCompanyDataDTOs(companyDataDTOs);
						
				
			}else{ // si no tiene todas la empresas se busca en los que tiene asignado
				resultDTO =  userReportManagerRemote.findCompanyOfUserByName(userid, name);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
		
		return resultDTO;
	}
	
	public CompanyDataArrayResultDTO findCompanyOfUserByNameAndType(Long userid, String name, boolean allenterprises, String type ) {

		CompanyDataArrayResultDTO resultDTO = new CompanyDataArrayResultDTO();
		try {
			
			if(allenterprises){ // si tiene todas las empresas la busqueda se hace en todos los proveedores
				
				CompanyArrayResultDTO companyArrayResultDTO = userReportManagerRemote.findCompanyLikeNameAndType(name, type);
				
				CompanyDataDTO[] companyDataDTOs = Stream.of(companyArrayResultDTO.getCompanyDTOs()).map(c -> {
					CompanyDataDTO comp = new CompanyDataDTO();
					comp.setId(c.getId());
					comp.setRut(c.getRut());
					comp.setName(c.getName());
					return comp;
				}).toArray(CompanyDataDTO[]::new);
				
				resultDTO.setCompanyDataDTOs(companyDataDTOs);
						
				
			}else{ // si no tiene todas la empresas se busca en los que tiene asignado
				resultDTO =  userReportManagerRemote.findCompanyOfUserByName(userid, name);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
		
		return resultDTO;
	}
	
	
	public CompanyResultDTO findCompanyById(Long emkey) {
			
		try {
			return userReportManagerRemote.getCompanyById(emkey);
		} catch (Exception e) {
			e.printStackTrace();
			CompanyResultDTO resultDTO = new CompanyResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
		
	}
	
	public BaseResultDTO deleteUserLocalRelations(Long userid, Long[] localsKeyDelete, Long useradmin) {
		try {
			return userReportManagerRemote.deleteUserLocalRelations(userid, localsKeyDelete, useradmin);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
	}

	public BaseResultDTO deleteUserProfileRelations(Long userid, Long[] profilesKeyDelete, Long useradmin) {
		try {
			return userReportManagerRemote.deleteUserProfileRelations(userid, profilesKeyDelete, useradmin);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
	}

	public BaseResultDTO deleteUserCompanyRelations(Long userid, Long[] providersKeyDelete, Long useradmin) {
		try {
			return userReportManagerRemote.deleteUserCompanyRelations(userid, providersKeyDelete, useradmin);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	
	public UserResultDTO getUserByVerificationCode(String verificationCode){
		try {
			return userReportManagerRemote.getUserByVerificationCode(verificationCode);
	
		} catch (Exception e) {
			e.printStackTrace();
			UserResultDTO resultW = new UserResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}	

	public UsersResultDTO getUsersByCompany(Long company){
		try {			
			return userReportManagerRemote.getUsersByCompany(company);
	
		} catch (Exception e) {
			e.printStackTrace();
			UsersResultDTO resultW = new UsersResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}
	
//	public UserResultDTO recoverPassword(String logid, String email){
//		try {
//			return userReportManagerRemote.recoverPassword(logid, email);
//	
//		} catch (Exception e) {
//			e.printStackTrace();
//			UserResultDTO resultW = new UserResultDTO();
//			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
//		}
//	}
	
	
	
	/*public String[] getLocationIdsByUserId(Long userid) throws OperationFailedException, NotFoundException {
		return userReportManagerRemote.getLocationIdsByUserId(userid);
	}*/
	
	
	public UserResultDTO getUserByLogId(String arg0) {
		try {
			return userReportManagerRemote.getUserByLogId(arg0);
	
		} catch (Exception e) {
			e.printStackTrace();
			UserResultDTO resultW = new UserResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}
		
//	public UserUniqueReportResultDTO getUserReportByUserId(Long userId){
//		UserUniqueReportResultDTO resultW = new UserUniqueReportResultDTO();
//		
//		try {
//			resultW = userReportManagerRemote.getUserReportByUserId(userId);
//			
//			// Se busca información de la compañia
//			Long[] companyKey = new Long[1];
//			companyKey[0] = resultW.getUserReport().getCompanyid();			
//			//financesReportManager = bbr.b2b.finances.managers.interfaces.ContextUtil.getInstance().getFinancesReportManager();
//			//VendorFinDTO vendorFinDTO = financesReportManager.findVendorsByIds(companyKey, 0, 0, false, false, null).getVendorDTOs()[0];
//			commercialReportManager = bbr.b2b.commercial.managers.interfaces.ContextUtil.getInstance().getCommercialReportManager();
//			ProviderArrayResultDTO providers = commercialReportManager.findProviderById(companyKey);
//			
//			resultW.getUserReport().setCompanycode(providers.getProviderDTOs()[0].getProvidercode());
//			resultW.getUserReport().setCompanyname(providers.getProviderDTOs()[0].getProvidername());
//			
//			//resultW.getUserReport().setCompanycode(vendorFinDTO.getProvidercode());
//			//resultW.getUserReport().setCompanyname(vendorFinDTO.getName());
//			
//			
//		} catch (Exception e) {			
//			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
//		}
//		
//		return resultW;
//	}
	
	
	public UserReportDataDTO getUserReport(UserReportInitParamDTO initParamW, Long userTypeId, Long companyId, boolean isPaginated, OrderCriteriaDTO[] order){		
		try {			
			return userReportManagerRemote.getUserReport(initParamW, userTypeId, companyId, isPaginated, order);
	
		} catch (Exception e) {
			e.printStackTrace();
			UserReportDataDTO resultW = new UserReportDataDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}
	
//	public UserReportDataDTO getUsersByCompany(Long[] companies){
//		try {			
//			return userReportManagerRemote.getUsersByCompany(companies);
//	
//		} catch (Exception e) {
//			e.printStackTrace();
//			UserReportDataDTO resultW = new UserReportDataDTO();
//			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
//		}
//	}
	
	public FileDownloadInfoResultDTO getExcelUserReport(UserReportInitParamDTO initParamW, Long userKey, String fileType, boolean isForZip) {
		FileDownloadInfoResultDTO resultW = new FileDownloadInfoResultDTO();
		
		UserReportDataDTO reportResult = null;
		reportResult = getUserReport(initParamW, 0L, 0L, false, null);
		
		if (reportResult.getUserReport().length == 0){
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P9000");
		}
				
		DataRow row = null;
		
		// Escribir descripcion del filtro seleccionado
		
		DataTable dt0 = new DataTable("REPORTE_USUARIOS");
		
		DataColumn col01 = new DataColumn("usernameB2B", "ID B2B", String.class, 160);
		DataColumn col02 = new DataColumn("userid", "RUT", String.class);	
		DataColumn col03 = new DataColumn("username", "Nombre(s)", String.class, 130);			
		DataColumn col04 = new DataColumn("lastname", "Apellido(s)", String.class, 130);
		DataColumn col05 = new DataColumn("email", "E-mail", String.class, 240);
		DataColumn col06 = new DataColumn("state", "Estado", String.class);
		DataColumn col07 = new DataColumn("usertype", "Tipo", String.class, 110);		
		DataColumn col08 = new DataColumn("companyid", "ID Empresa", String.class);
		DataColumn col09 = new DataColumn("companyname", "Nombre Empresa", String.class, 250);
		DataColumn col10 = new DataColumn("position", "Cargo", String.class, 120);
		DataColumn col11 = new DataColumn("creationdate", "Fecha Creación", Date.class, 120);
		DataColumn col12 = new DataColumn("lastupdate", "Última Modificación", Date.class, 120);
		DataColumn col13 = new DataColumn("lastlogindate", "Último Login", Date.class, 120);
		
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
		dt0.addColumn(col11);
		dt0.addColumn(col12);
		dt0.addColumn(col13);

		UserDataDTO[] allUsers = reportResult.getUserReport();
		
		UserDataDTO lineUser = null;	
		
		//Long[] company = new Long[1];
		
		for (int i = 0; i < allUsers.length ; i++  ){
			lineUser = allUsers[i];
			
			row = dt0.newRow();
			
			row.setCellValue("usernameB2B", lineUser.getLogid());
			row.setCellValue("userid", lineUser.getPersonalid());
			row.setCellValue("username", lineUser.getUsername());
			row.setCellValue("lastname", lineUser.getLastname());
			row.setCellValue("email", lineUser.getEmail());
			row.setCellValue("state", lineUser.getStatedescription());
			row.setCellValue("usertype", lineUser.getUsertype());		
			row.setCellValue("companyid", lineUser.getCompanycode());		
			row.setCellValue("companyname",lineUser.getCompanyname());				
			row.setCellValue("position", lineUser.getPosition());
			row.setCellValue("creationdate", lineUser.getCreationdate());
			row.setCellValue("lastupdate",lineUser.getLastupdate());
			if(lineUser.getLastlogin() != null)	row.setCellValue("lastlogindate", lineUser.getLastlogin());
			
			dt0.addRow(row);					
		}
		
		// CREAR ARCHIVO
		String downloadfilename = "";
		String realfilename = "";
		Date date = new Date();
		
		// XLS
		if (fileType.equalsIgnoreCase("xls")){
			downloadfilename = "ReporteUsuarios_"+ dateFormat.format(date) +".xls";	
			realfilename = "REPUSU_" + dateFormat.format(new Date()) + " hrs_"+ userKey + ".xls";
			if (isForZip){
				realfilename = "ReporteUsuarios_"+ dateFormat.format(date) +".xls";
			}			
			
			XLSConverterJXL converter = new XLSConverterJXL();
			converter.setExcelheaderbold(true);
			converter.setShowexceltableborder(true);
			
			try {
				converter.ExportToXLS(dt0, PortalConstants.getInstance().getFileTransferPath() + realfilename, Charset.forName("UTF-16"));
			} catch (IOException e) {
				e.printStackTrace();
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P300");// No se pudo generar el archivo
			}				
			
		}else if (fileType.equalsIgnoreCase("csv")){
			downloadfilename = "ReporteUsuarios_"+ dateFormat.format(date) +".csv";			
			realfilename = "REPUSU_" + dateFormat.format(new Date()) + " hrs_"+ userKey + ".csv";
			
			if (isForZip){
				realfilename = "ReporteUsuarios_"+ dateFormat.format(date) +".csv";
			}
			
			CSVConverter converter = new CSVConverter();			
			
			try {
				converter.ExportToCSV(dt0, PortalConstants.getInstance().getFileTransferPath() + realfilename);
			} catch (IOException e) {
				e.printStackTrace();
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P300");// No se pudo generar el archivo
			}			
		}
		
		resultW.setDownloadfilename(downloadfilename);
		resultW.setRealfilename(realfilename);
		
		return resultW;		
	}

//	public FileDownloadInfoResultDTO getExcelUserReportByProviders(Long userKey, String fileType){
//		
//		FileDownloadInfoResultDTO resultW = new FileDownloadInfoResultDTO();		
//		List<DataTable> tables = new ArrayList<DataTable>();
//		
//		
//		// Se busca reporte usuario con filtro TIPO DE USUARIO = PROVEEDOR
//		UserReportInitParamDTO initParamW = new UserReportInitParamDTO();
//		initParamW.setFilterType(0);
//		initParamW.setUserTypeId(new Long(1)); // 1- Proveedor
//		
//		DataTable dtExcel = getExcelUserReportDataTable(initParamW);	
//			
//		if (dtExcel.getRows().size() == 0){
//			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P9000");
//		}
//		
//		// Busco las Keys de usuario		
//		UserReportDataDTO userReports = getUserReport(initParamW, false);
//		List<Long> userKeyList = new ArrayList<Long>();
//		for (UserDataDTO userReport: userReports.getUserReport()){
//			userKeyList.add(userReport.getId());				
//		}
//		
//		Long[] userKeys = userKeyList.toArray(new Long[userKeyList.size()]);				
//		
//		// Busco reporte de usuarios - perfil asociado
//		DataTable dtProfile = getExcelUserProfileReportDataTable(userKeys);			
//		
//		// Busco reporte de usuarios - proveedor comercial asociado
//		DataTable dtCom = getExcelUserProvComReportDataTable(userKeys);		
//				
//		// Busco reporte de usuarios - proveedor finanzas asociado
//		//DataTable dtFin = getExcelUserProvFinReportDataTable(userKeys);
//				
//		// Busco reporte de usuarios - proveedor logistica asociado	
//	//	DataTable dtLog = getExcelUserProvLogReportDataTable(userKeys);
//		
//		// Busco reporte de usuarios - local asociado	
//		//DataTable dtLocal = getExcelUserLocalReportDataTable(userKeys);
//		
//		if (dtExcel.getRows().size() > 0){
//			tables.add(dtExcel);
//		}
//		if (dtProfile.getRows().size() > 0){
//			tables.add(dtProfile);
//		}
//		if (dtCom.getRows().size() > 0){
//			tables.add(dtCom);
//		}		
//		//if (dtFin.getRows().size() > 0){
//		//	tables.add(dtFin);
//		//}
//		/*if (dtLog.getRows().size() > 0){
//			tables.add(dtLog);	
//		}*/
//		
//		//if (dtLocal.getRows().size() > 0){
//		//	tables.add(dtLocal);	
//		//}
//		
//			
//		String realfilename = "REPUSU_" + dateFormat.format(new Date()) + " hrs_" + userKey +"." + fileType;
//		String downloadfilename = "ReporteUsuarios_" + dateFormat.format(new Date()) + " hrs." + fileType;
//		
//		if (fileType.equalsIgnoreCase("xls")){
//			XLSConverterJXL converter = new XLSConverterJXL();
//			
//			try {
//				converter.ExportToXLS(tables, PortalConstants.getInstance().getFileTransferPath() + realfilename);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//				return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P300");// no se pudo generar el archivo
//			}
//		}else if (fileType.equalsIgnoreCase("csv")){
//			CSVConverter converter = new CSVConverter();
//			
//			try {
//				converter.ExportToCSV(dtExcel, PortalConstants.getInstance().getFileTransferPath() + realfilename);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//				return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P300");// no se pudo generar el archivo
//			}
//		}	
//			
//		resultW.setDownloadfilename(downloadfilename);
//		resultW.setRealfilename(realfilename);		
//		return resultW;
//	}
	
	
	public BaseResultDTO updateUser(UserDTO user) {
		try {
			return userReportManagerRemote.updateUser(user);
	
		} catch (Exception e) {
			e.printStackTrace();
			UserResultDTO resultW = new UserResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}
	
	public BaseResultDTO updateUserLastAccess(UserDTO user) {
		try {
			return userReportManagerRemote.updateUserLastAccess(user);
	
		} catch (Exception e) {
			e.printStackTrace();
			UserResultDTO resultW = new UserResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}

	public UserResultDTO getUserById(Long id) {
		try {
			return userReportManagerRemote.getUserById(id);
	
		} catch (Exception e) {
			e.printStackTrace();
			UserResultDTO resultW = new UserResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}
	
	public UserForNewsResultDTO getUsersForNews(long uskey) {
		try {
			Long emkey = userReportManagerRemote.getUserById(uskey).getUser().getEmkey();
			//usuarios activos(1)
			return userReportManagerRemote.getUsersForNews(1, emkey,uskey);
		} catch (Exception e) {
			e.printStackTrace();
			UserForNewsResultDTO resultW = new UserForNewsResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}
	
	public BaseResultDTO sendContactMail(ContactUsDTO contactUsW) {
		BaseResultDTO resultW = new BaseResultDTO();
		
		UserResultDTO userDTO = this.getUserById(contactUsW.getUskey());
		String temp2 = PortalResources.getInstance().getContactData(new Integer(contactUsW.getId()).toString());
		String[] propiedades = temp2.split(",");
		
		String[] to = new String[1];
		String[] bcc = new String[6];
		bcc[0] = "notificacionesb2b@bbr.cl";
		bcc[1] = propiedades[2];
		bcc[2] = "mrivera@bbr.cl";
		to[0] = contactUsW.getEmail();
		String from = PortalResources.getInstance().getMailFrom();
		String subject = "B2B IRSB2BLink Chile : Consulta General " + propiedades[3] ;
		String session = PortalResources.getInstance().getMailSession();
		
		String mensaje = "A través del Portal B2B de IRSB2BLink Chile se ha enviado el siguiente mail de consulta.<br/><br/>";

		mensaje += "<table>";
		mensaje += "<tr align='char'><td>Rut Usuario</td><td>: " + StringUtils.getInstance().formatRut(userDTO.getUser().getPersonalid()) +" </td></tr>";
		mensaje += "<tr align='char'><td>Nombre</td><td>: " + contactUsW.getName()+ "</td></tr>";
		mensaje += "<tr align='char'><td>Email</td><td>: "+ contactUsW.getEmail() + "</td></tr> ";
		mensaje += "<tr align='char'><td>Cargo</td><td>: "+ contactUsW.getPosition() + "</td></tr> ";
		mensaje += "<tr align='char'><td>Teléfono</td><td>: "+ contactUsW.getPhone() + "</td></tr> ";
		mensaje += "<tr align='char'><td>Empresa Trabajo</td><td>: " + contactUsW.getEnterprisename() + " ("+contactUsW.getEnterprisecode()+")</td></tr>";
		mensaje += "<tr align='left'><td valign='top'>Comentario</td><td>: " + contactUsW.getComment()+"</td></tr>";
		mensaje += "</table><br/>";
				
		try {
			//mensaje += "Saludos y Gracias.";
			mensaje += "<imagen>"+B2BSystemPropertiesUtil.getProperty("logoCecudEndpoint")+"</imagen>";
		//	Mailer.getInstance().sendMailBySession(to, null, from, subject, mensaje, session);
			Mailer.getInstance().sendMailBySession(to, null, bcc, from, subject, mensaje, true, null, session);
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P6000"); // error al enviar mensaje
		}
		
		
		return resultW;
				
	}
	
	public LabelCommentValueArrayResultDTO getCommentTypes() {
		LabelCommentValueArrayResultDTO resultW = new LabelCommentValueArrayResultDTO();
		List<LabelCommentValueDTO> list = new ArrayList<LabelCommentValueDTO>();
		LabelCommentValueDTO LabelValueDTO = null;
		String[] propiedades = null;
		String llave = null;
				
		Enumeration<String> temp  = PortalResources.getInstance().getContactKeys();
		while(temp.hasMoreElements()){
			
			llave = temp.nextElement();
			propiedades = PortalResources.getInstance().getContactData(llave).split(",");
			
			LabelValueDTO = new LabelCommentValueDTO();
			LabelValueDTO.setId(Long.parseLong(propiedades[0]));
			LabelValueDTO.setLabel(propiedades[1]);
			
			list.add(LabelValueDTO);
		}
		
		resultW.setLabelValueWs(list.toArray(new LabelCommentValueDTO[list.size()]));
		return resultW;
	}

	public UserDataInfoDTO getUserInfo(long uskey) {
		
		UserDataInfoDTO resultW = new UserDataInfoDTO();
		UserResultDTO userW = null;
        CompanyTypeResultDTO companyTypeResult = null;
        CompanyResultDTO companyResultDTO = null;
		try {
			userW = userReportManagerRemote.getUserById(uskey);

            if (userW.getUser() != null) {
            	companyResultDTO = userReportManagerRemote.getCompanyById(userW.getUser().getEmkey());
            	companyTypeResult = companyTypeManager.findTypeCompanyById(companyResultDTO.getCompanyDTO().getTekey());
            }

		} catch (RuntimeException e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
		
		resultW.setUskey(userW.getUser().getId());
		resultW.setName(userW.getUser().getName() + " " + userW.getUser().getLastname());
		resultW.setPosition(userW.getUser().getPosition());
		resultW.setEmail(userW.getUser().getEmail());
		resultW.setPhone(userW.getUser().getPhone());
		resultW.setDate(DateConverterFactory2.DateToStr(new Date()));
		resultW.setRut(userW.getUser().getPersonalid());
		resultW.setUsertypeid(companyTypeResult.getCompanyTypeResultDTO() != null ? companyTypeResult.getCompanyTypeResultDTO().getId() : 0L);
        resultW.setUsertypedesc(companyTypeResult.getCompanyTypeResultDTO() != null ? companyTypeResult.getCompanyTypeResultDTO().getName() : "");
		resultW.setEnterprisecode(companyResultDTO.getCompanyDTO().getRut());
		resultW.setEnterprisename(companyResultDTO.getCompanyDTO().getName());
		
		return resultW;
		
	}

	public UserResultDTO doChangeLogId(ChangeLogIdInitParamDTO initParamW) {
		try {
			return userReportManagerRemote.doChangeLogId(initParamW);
		} catch (Exception e) {
			e.printStackTrace();
			UserResultDTO resultW = new UserResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}
	
	public BaseResultDTO deleteUser(Long[] userPKs,  String accessToken, Long useradmin) {
		
		try {			
			return userReportManagerRemote.deleteUser(userPKs, accessToken, useradmin);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultW = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}

	public UsersResultDTO activateUser(UserActivateInitParamDTO userActivateInitParam){
		try {
			return userReportManagerRemote.activateUser(userActivateInitParam);
		} catch (Exception e) {
			e.printStackTrace();
			UsersResultDTO resultW = new UsersResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		
		}
	}

	public UsersResultDTO inactivateUser(Long[] userPKs) {
		try {
			return userReportManagerRemote.inactivateUser(userPKs);
		} catch (Exception e) {
			e.printStackTrace();
			UsersResultDTO resultW = new UsersResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}

	}

//	private DataTable getExcelUserProfileReportDataTable(Long[] userKeys){		
//			
//		try {
//			functionalityManager = bbr.b2b.users.managers.interfaces.ContextUtil.getInstance().getIFunctionalityManager();
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}	
//		
//		DataRow row = null;
//		DataTable dt0 = new DataTable("USUARIO_PERFIL");
//				
//		DataColumn col01 = new DataColumn("userid", "ID Usuario", String.class);	
//		DataColumn col02 = new DataColumn("profile", "Perfil", String.class, 250);			
//		
//		dt0.addColumn(col01);
//		dt0.addColumn(col02);		
//		
//		for (Long userId : userKeys){
//			
//			UserDTO user = userReportManagerRemote.getUserById(userId).getUser();
//			ProfileArrayResultDTO profiles = functionalityManager.getProfilesByUserId(userId,0,0,false,false,null);
//			
//			for (ProfileDTO profile : profiles.getProfiles()){
//				row = dt0.newRow();
//				
//				row.setCellValue("userid", user.getRut().trim() + "-" + user.getCheckdigit());
//				row.setCellValue("profile", profile.getDescription());			
//
//				dt0.addRow(row);		
//			}						
//		}		
//		return dt0;			
//	}	
//	
	
//	private DataTable getExcelUserProvComReportDataTable (Long[] userKeys){		
//				
//		try {
//			commercialReportManager = bbr.b2b.commercial.managers.interfaces.ContextUtil.getInstance().getCommercialReportManager();
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//		
//		DataRow row = null;
//		DataTable dt0 = new DataTable("USUARIO_PROVEEDOR_COMERCIAL");
//				
//		DataColumn col01 = new DataColumn("userid", "ID Usuario", String.class);	
//		DataColumn col02 = new DataColumn("companyname", "Nombre Empresa", String.class, 350);		
//		DataColumn col03 = new DataColumn("companycode", "ID Empresa", String.class);
//		//DataColumn col04 = new DataColumn("supplycompanycode", "Código Surtido", String.class, 140);
//		
//		dt0.addColumn(col01);
//		dt0.addColumn(col02);	
//		dt0.addColumn(col03);	
//		//dt0.addColumn(col04);
//		
//		for (Long userId : userKeys){
//			
//			UserDTO user = userReportManagerRemote.getUserById(userId).getUser();
//			Long[] providerIds = null;
//			try {
//				providerIds = userReportManagerRemote.getProviderIdsByUserId(userId);
//			}catch (Exception e) {
//				continue;
//			}
//			
//			if (providerIds.length == 0){
//				continue;
//			}
//			
//			ProviderDataDTO[] providers = commercialReportManager.findProvidersByIds(providerIds,0,0,false,false,null).getProviderDataWs();
//						
//			for (ProviderDataDTO provider : providers){
//				row = dt0.newRow();
//				
//				//String companyName = provider.getGenericprovidername() + " ["+ provider.getSupplyprovidername() + "]";
//				String companyName = provider.getProvidername();
//				
//				row.setCellValue("userid", user.getRut().trim() + "-" + user.getCheckdigit());
//				row.setCellValue("companyname", companyName);
//				row.setCellValue("companycode", provider.getProvidercode());		
//				//row.setCellValue("supplycompanycode", provider.getSupplyprovidercode());
//				
//				dt0.addRow(row);		
//			}						
//		}			
//		return dt0;		
//	}
	
//	private DataTable getExcelUserReportDataTable(UserReportInitParamDTO initParamW) {
//			
//		UserReportDataDTO reportResult = null;
//		reportResult = getUserReport(initParamW, false);
//		
//		DataRow row = null;
//		
//		// Escribir descripcion del filtro seleccionado
//		
//		DataTable dt0 = new DataTable("REPORTE_USUARIOS");
//		
//		DataColumn col01 = new DataColumn("usernameB2B", "Nombre de Usuario B2B", String.class, 160);
//		DataColumn col02 = new DataColumn("userid", "ID Usuario", String.class);	
//		DataColumn col03 = new DataColumn("username", "Nombre de Usuario", String.class, 130);			
//		DataColumn col04 = new DataColumn("lastname", "Apellido de Usuario", String.class, 130);
//		DataColumn col05 = new DataColumn("email", "E-mail", String.class, 240);
//		DataColumn col06 = new DataColumn("state", "Estado", String.class);
//		DataColumn col07 = new DataColumn("usertype", "Tipo de Usuario", String.class, 110);		
//		DataColumn col08 = new DataColumn("companyid", "ID Empresa", String.class);
//		DataColumn col09 = new DataColumn("companyname", "Nombre Empresa", String.class, 250);
//		DataColumn col10 = new DataColumn("position", "Cargo", String.class, 120);
//
//		dt0.addColumn(col01);
//		dt0.addColumn(col02);
//		dt0.addColumn(col03);
//		dt0.addColumn(col04);
//		dt0.addColumn(col05);	
//		dt0.addColumn(col06);
//		dt0.addColumn(col07);
//		dt0.addColumn(col08);		
//		dt0.addColumn(col09);	
//		dt0.addColumn(col10);	
//
//		UserDataDTO[] allUsers = reportResult.getUserReport();
//		
//		UserDataDTO lineUser = null;	
//		
//		try {
//			commercialReportManager = bbr.b2b.commercial.managers.interfaces.ContextUtil.getInstance().getCommercialReportManager();
//			//financesReportManager = bbr.b2b.finances.managers.interfaces.ContextUtil.getInstance().getFinancesReportManager();
//		} catch (NamingException e1) {
//			e1.printStackTrace();
//		}
//		
//		Long[] company = new Long[1];
//		
//		for (int i = 0; i < allUsers.length ; i++  ){
//			lineUser = allUsers[i];
//			
//			row = dt0.newRow();
//			
//			row.setCellValue("usernameB2B", lineUser.getLogid());
//			row.setCellValue("userid", lineUser.getRut());
//			row.setCellValue("username", lineUser.getUsername());
//			row.setCellValue("lastname", lineUser.getLastname());
//			row.setCellValue("email", lineUser.getEmail());
//			row.setCellValue("state", lineUser.getStatedescription());
//			row.setCellValue("usertype", lineUser.getUsertype());		
//			
//			
//			//Debo buscar la informacion de la empresa con el ID
//			company[0] = lineUser.getCompanyid();
//			ProviderArrayResultDTO companies = commercialReportManager.findProviderById(company);
//			row.setCellValue("companyid", companies.getProviderDTOs()[0].getProvidercode());		
//			row.setCellValue("companyname",  companies.getProviderDTOs()[0].getProvidername());	
//			/*VendorArrayResultDTO companies = financesReportManager.findVendorsByIds(company, 0, 0, false, false, null);
//			row.setCellValue("companyid", companies.getVendorDTOs()[0].getProvidercode());		
//			row.setCellValue("companyname",  companies.getVendorDTOs()[0].getName());*/			
//			
//			row.setCellValue("position", lineUser.getPosition());	
//
//			dt0.addRow(row);					
//		}		
//		return dt0;		
//	}
	
	public FaqsResultDTO getFaqAnswerByQuestion(long fakey) 
	{
		FaqsResultDTO resultW = new FaqsResultDTO();
		try 
		{
			resultW = userReportManagerRemote.getFaqAnswerByQuestion(fakey);
			if(resultW.getFaqDTOs() != null && resultW.getFaqDTOs().length > 0)
			{
				BaseResultDTO baseResultW = userReportManagerRemote.addFaqQuestionCount(fakey);
				if(!baseResultW.getStatuscode().equals("1"))
				{
					String answer = resultW.getFaqDTOs()[0].getAnswer();
					
					Base64 decoder = new Base64();
					resultW.getFaqDTOs()[0].setAnswer(new String(decoder.decode(answer)));
				}
				else
				{
					PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
				}				
			}			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
		
		return resultW;
	}
	
	public FaqsResultDTO getFaqQuestionsForUserProfiles(long uskey) {		
		FaqsResultDTO resultW = new FaqsResultDTO();
		try {
			return userReportManagerRemote.getFaqQuestionsForUserProfiles(uskey);
			
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}		
	}
	
	public BaseResultDTO doAddOrUpdateFaq(AddFaqInitParamDTO initParamW, boolean isAddFaq)
	{
		BaseResultDTO resultW = new BaseResultDTO();
		
		try
		{		
			Base64 encoder = new Base64();
			String answer  = encoder.encodeToString(initParamW.getFaq().getAnswer().getBytes());
			initParamW.getFaq().setAnswer(answer);
			
			resultW = userReportManagerRemote.doAddOrUpdateFaq(initParamW, isAddFaq);			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			resultW = PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
		
		return resultW;
	}
	
	public FaqReportArrayResultDTO getFaqReportByProfile(FaqReportInitParamDTO initParamW, boolean isByFilter)
	{
		FaqReportArrayResultDTO resultW =  new FaqReportArrayResultDTO();
		
		try 
		{
			resultW = userReportManagerRemote.getFaqReportByProfile(initParamW, isByFilter);
			
			if (resultW.getStatuscode().equals("0"))
			{
				Base64 decoder = new Base64();
				for (int i = 0; i < resultW.getFaqreport().length; i++)
				{				
					String answer  = resultW.getFaqreport()[i].getAnswer();
					resultW.getFaqreport()[i].setAnswer(new String(decoder.decode(answer)));					
				}	
			}
			else
			{
				resultW = PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			resultW = PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
		
		return resultW;
	}
	
	public BaseResultDTO doDeleteFaqs(Long[] faKeys) 
	{
		BaseResultDTO resultW = new BaseResultDTO();
		try 
		{
			resultW = userReportManagerRemote.doDeleteFaqs(faKeys);			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			resultW = PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
		
		return resultW ;
	}
		
	public ProfileArrayResultDTO getProfilesByFaq(Long fakey)
	{
		ProfileArrayResultDTO resultW = new ProfileArrayResultDTO();
		
		try
		{
			resultW = userReportManagerRemote.getProfilesByFaq(fakey);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			resultW = PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
		
		return resultW;
	}
	
	public BaseResultDTO validateAuthorization(Long userid, String serviceName) {
		BaseResultDTO resultW = new BaseResultDTO();
		try {
			boolean valid = userReportManagerRemote.validateAuthorization(userid, serviceName);
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, valid ? "0" : "P5");// Operación no autorizada
			
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}
	
//	public FileDownloadInfoResultDTO getUserReportByCondition(String formatType,Integer condition, String filename, Long uskey) {
//		
//		FileDownloadInfoResultDTO resultW = new FileDownloadInfoResultDTO();
//		
//		UserReportDataDTO userReportResultW = new UserReportDataDTO();
//		try {
//			userReportResultW = userReportManagerRemote.getUserReportByCondition(condition);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
//		}
//		
//		if(userReportResultW.getUserReport().length <= 0){
//			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P9000");// no existen datos
//		}
//		
//		
//		DataRow row = null;
//		// Escribir descripcion del filtro seleccionado
//		DataTable dt0 = new DataTable("REPORTE_USUARIOS");
//		
//		DataColumn col01 = new DataColumn("usernameB2B", "ID B2B", String.class, 160);
//		DataColumn col02 = new DataColumn("userid", "RUT", String.class);	
//		DataColumn col03 = new DataColumn("username", "Nombre(s)", String.class, 130);			
//		DataColumn col04 = new DataColumn("lastname", "Apellido(s)", String.class, 130);
//		DataColumn col05 = new DataColumn("email", "E-mail", String.class, 240);
//		DataColumn col06 = new DataColumn("state", "Estado", String.class);
//		DataColumn col07 = new DataColumn("usertype", "Tipo", String.class, 110);		
//		DataColumn col08 = new DataColumn("companycode", "ID Empresa", String.class);
//		DataColumn col09 = new DataColumn("companyname", "Nombre Empresa", String.class, 250);
//		DataColumn col10 = new DataColumn("position", "Cargo", String.class, 120);
//		DataColumn col11 = new DataColumn("creationdate", "Fecha Creación", Date.class, 120);
//		DataColumn col12 = new DataColumn("lastupdate", "Última Modificación", Date.class, 120);
//		DataColumn col13 = new DataColumn("lastlogindate", "Último Login", Date.class, 120);
//				
//		DataColumn col14 = null;
//		DataColumn col15 = null;
//		
//		
//		if(condition.intValue()==1){
//			col14 = new DataColumn("code","Perfil",String.class, 120);
//			col15 = new DataColumn("name","Descripción Perfil",String.class, 200);
//		}
//		if(condition.intValue()==2){
//			col14 = new DataColumn("code","Código Empresa",String.class, 120);
//			col15 = new DataColumn("name","Nombre Empresa",String.class, 250);
//			
//		}
//		if(condition.intValue()==3){
//			col14 = new DataColumn("code", "Código Local", String.class, 120);
//			col15 = new DataColumn("name", "Nombre Local", String.class, 200);
//		}
//
//		dt0.addColumn(col01);
//		dt0.addColumn(col02);
//		dt0.addColumn(col03);
//		dt0.addColumn(col04);
//		dt0.addColumn(col05);	
//		dt0.addColumn(col06);
//		dt0.addColumn(col07);
//		dt0.addColumn(col08);		
//		dt0.addColumn(col09);	
//		dt0.addColumn(col10);
//		dt0.addColumn(col11);
//		dt0.addColumn(col12);
//		dt0.addColumn(col13);
//		dt0.addColumn(col14);
//		dt0.addColumn(col15);
//		
//		UserDataDTO lineUser = null;
//		
//		
//		for (int i = 0; i <userReportResultW.getUserReport().length ; i++  ){
//			lineUser = userReportResultW.getUserReport()[i];
//			
//			row = dt0.newRow();
//			
//			row.setCellValue("usernameB2B", lineUser.getLogid());
//			row.setCellValue("userid", lineUser.getRut());
//			row.setCellValue("username", lineUser.getUsername());
//			row.setCellValue("lastname", lineUser.getLastname());
//			row.setCellValue("email", lineUser.getEmail());
//			row.setCellValue("state", lineUser.getStatedescription());
//			row.setCellValue("usertype", "PROVEEDOR");
//			row.setCellValue("companycode", lineUser.getCompanycode());
//			row.setCellValue("companyname", lineUser.getCompanyname());
//			row.setCellValue("position", lineUser.getPosition());
//			try {
//				row.setCellValue("creationdate", DateConverterFactory2.StrToDate(lineUser.getCreationdate()));
//				row.setCellValue("lastupdate", DateConverterFactory2.StrToDate(lineUser.getLastupdate()));
//				if(lineUser.getLastlogin()!= null)	row.setCellValue("lastlogindate", DateConverterFactory2.StrToDate(lineUser.getLastlogin()));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			
//			if(condition.intValue()==1){
//				row.setCellValue("code", lineUser.getProfile());	
//				row.setCellValue("name", lineUser.getProfiledesc());
//			}
//			if(condition.intValue()==2){
//				row.setCellValue("code", lineUser.getProvidercode());
//				row.setCellValue("name", lineUser.getProvidername());
//			}
//			if(condition.intValue()==3){
//				row.setCellValue("code", lineUser.getLocationcode());
//				row.setCellValue("name", lineUser.getLocationname());
//			}
//			
//			dt0.addRow(row);
//		}
//		
//		
//		Date date = new Date();
//		// CREAR ARCHIVO
//		if (formatType.equalsIgnoreCase("xls")){
//			// 1 - Obtener el nombre del archivo
//			String downloadfilename = "ReporteUsuario_"+ dateFormat.format(date) +".xls";
//			String realfilename = "USUARIO"+ filename + date.getTime() + "_" + uskey + ".xls";
//			
//			XLSConverterJXL converter = new XLSConverterJXL();
//			converter.setExcelheaderbold(true);
//			converter.setShowexceltableborder(true);
//			
//			/*************************************** */
//				
//			try {
//				converter.ExportToXLS(dt0, PortalConstants.getInstance().getFileTransferPath() + realfilename,Charset.forName("UTF-16"));
//			} catch (IOException e) {
//				e.printStackTrace();
//				return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P300");// no se pudo generar el archivo
//			}
//			
//			resultW.setRealfilename(realfilename);
//			resultW.setDownloadfilename(downloadfilename);
//			
//		}
//		
//		if (formatType.equalsIgnoreCase("csv")){
//			// 1 - Obtener el nombre del archivo
//			String downloadfilename = "ReporteUsuario_"+ dateFormat.format(date) +".csv";
//			String realfilename = "USUARIO"+ filename + date.getTime() + "_" + uskey + ".csv";
//			
//			CSVConverter converter = new CSVConverter();
//			
//			/*************************************** */
//						
//			try {
//				converter.ExportToCSV(dt0, PortalConstants.getInstance().getFileTransferPath() + realfilename, Charset.forName(PortalConstants.CHARSET_CSV));
//			} catch (IOException e) {
//				e.printStackTrace();
//				return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P300");// no se pudo generar el archivo
//			}
//			
//			resultW.setRealfilename(realfilename);
//			resultW.setDownloadfilename(downloadfilename);
//			
//			
//		}
//		return resultW;				
//	}

	public BaseResultDTO updateUserBasicInfo(UserInitParamDTO userInitParam) {
		
		try {
			return userReportManagerRemote.updateUserBasicInfo(userInitParam);
		} catch (Exception e) {
			e.printStackTrace();
			UserResultDTO resultDTO = new UserResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
			
		}
	}

	public BaseResultDTO addUserLocalRelations(Long userid, Long[] localsKeyAdd, Long useradmin) {
		
		try {
			return userReportManagerRemote.addUserLocalRelations(userid, localsKeyAdd, useradmin);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public BaseResultDTO addUserProfileRelations(Long userid, Long[] profilesKeyAdd, Long useradmin) {
		try {
			return userReportManagerRemote.addUserProfileRelations(userid, profilesKeyAdd, useradmin);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public BaseResultDTO addUserCompanyRelations(Long userid, Long[] providersAdd, Long useradmin) {
		try {
			return userReportManagerRemote.addUserCompanyRelations(userid, providersAdd, useradmin);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public BaseResultDTO deleteAllUserLocalRelations(Long userid, Long useradmin) {
		try {
			return userReportManagerRemote.deleteAllUserLocalRelations(userid, useradmin);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public BaseResultDTO deleteAllUserProfileRelations(Long userid, Long useradmin) {
		try {
			return userReportManagerRemote.deleteAllUserProfileRelations(userid, useradmin);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public BaseResultDTO deleteAllUserCompanyRelations(Long userid, Long useradmin) {
		try {
			return userReportManagerRemote.deleteAllUserCompanyRelations(userid, useradmin);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public BaseResultDTO addAllUserLocalRelations(Long userid) {
		try {
			return userReportManagerRemote.addAllUserLocalRelations(userid);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public BaseResultDTO addAllUserProfileRelations(Long userid, Long useradmin) {
		try {
			return userReportManagerRemote.addAllUserProfileRelations(userid, useradmin);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}		
	}

	public BaseResultDTO addAllUserCompanyRelations(Long userid) {
		
		try {
			return userReportManagerRemote.addAllUserCompanyRelations(userid);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
		
	}
	
	public UserResultDTO addNoProviderBasicUserByAdministration(String email, String phone, Long emKey, Long useradmin)	{
		try {
			return userReportManagerRemote.addNoProviderBasicUserByAdministration(email, phone, emKey, useradmin);	
		} catch (Exception e) {
			e.printStackTrace();
			UserResultDTO resultW = new UserResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}
	
	public UserResultDTO addBasicUser(UserInitParamDTO userInitParam)	{
		try {
			return userReportManagerRemote.addBasicUser(userInitParam);	
		} catch (Exception e) {
			e.printStackTrace();
			UserResultDTO resultW = new UserResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}
	
	public UserResultDTO createUserLike(UserLikeInitParamDTO userLikeInitParam) {
		
		try {
			return userReportManagerRemote.createUserLike(userLikeInitParam);
		} catch (Exception e) {
			e.printStackTrace();
			UserResultDTO resultDTO = new UserResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public CompanyArrayResultDTO findCompanyLikeName(String name) {
		try {
			return userReportManagerRemote.findCompanyLikeName(name);
	
		} catch (Exception e) {
			e.printStackTrace();
			CompanyArrayResultDTO resultDTO = new CompanyArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}


	public CompanyArrayResultDTO findCompanyLikeCode(String code) {
		try {
			return userReportManagerRemote.findCompanyLikeCode(code);
	
		} catch (Exception e) {
			e.printStackTrace();
			CompanyArrayResultDTO resultDTO = new CompanyArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public CompanyArrayResultDTO findCompanyLikeNameAndType(String name, String type)
	{
		try {
			return userReportManagerRemote.findCompanyLikeNameAndType(name, type);
			
		} catch (Exception e) {
			e.printStackTrace();
			CompanyArrayResultDTO resultDTO = new CompanyArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	
	public CompanyArrayResultDTO findCompanyLikeCodeAndType(String code, String type) {
		try {
			return userReportManagerRemote.findCompanyLikeCodeAndType(code, type);
			
		} catch (Exception e) {
			e.printStackTrace();
			CompanyArrayResultDTO resultDTO = new CompanyArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public PositionResultDTO getAllPosition() {
		
		try {
			return userReportManagerRemote.getAllPositions();
		} catch (Exception e) {
			e.printStackTrace();
			PositionResultDTO resultDTO = new PositionResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}	
	
//	public UserResultDTO updateUserAdvanced(UpdateUserInitParamDTO initParams){
//		try {
//			return userReportManagerRemote.updateUserAdvanced(initParams);
//		} catch (Exception e) {
//			e.printStackTrace();
//			UserResultDTO resultDTO = new UserResultDTO();
//			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
//		}
//	}
//	
	public CompanyResultDTO doAddCompany(CompanyDTO companydata){
		try {
			return userReportManagerRemote.doAddCompany(companydata);
		} catch (Exception e) {
			e.printStackTrace();
			CompanyResultDTO resultDTO = new CompanyResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public CompanyReportResultDTO getCompanyReport(CompanyReportInitParamDTO initParams, boolean isPaginated){
		try {
			return userReportManagerRemote.getCompanyReport(initParams, isPaginated);
		} catch (Exception e) {
			e.printStackTrace();
			CompanyReportResultDTO resultDTO = new CompanyReportResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public UsersResultDTO getUsersByProfile(String profileCode){
		try {
			return userReportManagerRemote.getUsersByProfile(profileCode);
		} catch (Exception e) {
			e.printStackTrace();
			UsersResultDTO resultDTO = new UsersResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public UsersResultDTO getUsersFromCompanyByProfile(Long companyId, String profileCode){
		try {
			return userReportManagerRemote.getUsersFromCompanyByProfile(companyId, profileCode);
		} catch (Exception e) {
			e.printStackTrace();
			UsersResultDTO resultDTO = new UsersResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
		
	public CompanyArrayResultDTO getAllPayerCompanies(){
		try {
			return userReportManagerRemote.getAllPayerCompanies();
		} catch (Exception e) {
			e.printStackTrace();
			CompanyArrayResultDTO resultDTO = new CompanyArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public CompanyArrayResultDTO getAllVendorCompanies(){
		try {
			return userReportManagerRemote.getAllPayerCompanies();
		} catch (Exception e) {
			e.printStackTrace();
			CompanyArrayResultDTO resultDTO = new CompanyArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public UsersResultDTO getAllPayerUsers(){
		try {
			return userReportManagerRemote.getAllPayerUsers();
		} catch (Exception e) {
			e.printStackTrace();
			UsersResultDTO resultDTO = new UsersResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public UsersResultDTO getAllVendorUsers(){
		try {
			return userReportManagerRemote.getAllVendorUsers();
		} catch (Exception e) {
			e.printStackTrace();
			UsersResultDTO resultDTO = new UsersResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public EventArrayResultDTO getAllEvents(){
		try {
			return userReportManagerRemote.getAllEvents();
		} catch (Exception e) {
			e.printStackTrace();
			EventArrayResultDTO resultDTO = new EventArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public UsersResultDTO getAllUsers(int rows, int pagenumber, boolean isPaginated){
		try {
			return userReportManagerRemote.getAllUsers(rows, pagenumber, isPaginated);
		} catch (Exception e) {
			e.printStackTrace();
			UsersResultDTO resultDTO = new UsersResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public CompanyArrayResultDTO getAllCompanies(int rows, int pagenumber, boolean isPaginated){
		try {
			return userReportManagerRemote.getAllCompanies(rows, pagenumber, isPaginated);
		} catch (Exception e) {
			e.printStackTrace();
			CompanyArrayResultDTO resultDTO = new CompanyArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public AssignedUsersArrayResultDTO getAssignedUsersToPublishing(AssignedRelationshipsInitParamDTO initParams, boolean ispaginated){
		try {
			return userReportManagerRemote.getAssignedUsersToPublishing(initParams, ispaginated);
		} catch (Exception e) {
			e.printStackTrace();
			AssignedUsersArrayResultDTO resultDTO = new AssignedUsersArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public AssignedCompaniesArrayResultDTO getAssignedCompaniesToPublishing(AssignedRelationshipsInitParamDTO initParams, boolean ispaginated){
		try {
			return userReportManagerRemote.getAssignedCompaniesToPublishing(initParams, ispaginated);
		} catch (Exception e) {
			e.printStackTrace();
			AssignedCompaniesArrayResultDTO resultDTO = new AssignedCompaniesArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public AssignedProfilesArrayResultDTO getAssignedProfilesToPublishing(Long publishingId){
		try {
			return userReportManagerRemote.getAssignedProfilesToPublishing(publishingId);
		} catch (Exception e) {
			e.printStackTrace();
			AssignedProfilesArrayResultDTO resultDTO = new AssignedProfilesArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public AssignedEventsArrayResultDTO getAssignedEventsToPublishing(Long publishingId){
		try {
			return userReportManagerRemote.getAssignedEventsToPublishing(publishingId);
		} catch (Exception e) {
			e.printStackTrace();
			AssignedEventsArrayResultDTO resultDTO = new AssignedEventsArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}	
	
	public UsersResultDTO getUsersByLastNameAndProfile(String lastname, String profileCode){
		try {
			return userReportManagerRemote.getUsersByLastNameAndProfile(lastname, profileCode);
		} catch (Exception e) {
			e.printStackTrace();
			UsersResultDTO resultDTO = new UsersResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public UsersResultDTO getUsersByNameAndProfile(String name, String profileCode){
		try {
			return userReportManagerRemote.getUsersByNameAndProfile(name, profileCode);
		} catch (Exception e) {
			e.printStackTrace();
			UsersResultDTO resultDTO = new UsersResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public UsersResultDTO getUsersByCodeAndProfile(String code, String profileCode){
		try {
			return userReportManagerRemote.getUsersByCodeAndProfile(code, profileCode);
		} catch (Exception e) {
			e.printStackTrace();
			UsersResultDTO resultDTO = new UsersResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}	
	
	public ProfileArrayResultDTO getAssignedProfilesOfUser(Long userid,int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return userReportManagerRemote.getAssignedProfilesOfUser(userid, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			ProfileArrayResultDTO resultDTO = new ProfileArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	
	public ProfileArrayResultDTO getAvailableProfilesOfUser(Long userid) {
		try {
			return userReportManagerRemote.getAvailableProfilesOfUser(userid);
		} catch (Exception e) {
			ProfileArrayResultDTO resultDTO = new ProfileArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public LocationArrayResultDTO getAssignedLocationsOfUser(Long userid,int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return userReportManagerRemote.getAssignedLocationsOfUser(userid, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			LocationArrayResultDTO resultDTO = new LocationArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	
	public LocationArrayResultDTO getAvailableLocationsOfUser(Long userid, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return userReportManagerRemote.getAvailableLocationsOfUser(userid, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			LocationArrayResultDTO resultDTO = new LocationArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public CompanyArrayResultDTO getAssignedCompaniesOfUser(Long userid,int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return userReportManagerRemote.getAssignedCompaniesOfUser(userid, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			CompanyArrayResultDTO resultDTO = new CompanyArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");
		}
	}

	
	public CompanyArrayResultDTO getAvailableCompaniesOfUser(Long userid, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return userReportManagerRemote.getAvailableCompaniesOfUser(userid, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			CompanyArrayResultDTO resultDTO = new CompanyArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public UserResultDTO doMergeUserAttributes(UserMergeInitParamDTO userMergeInitParam){
		UserResultDTO  resultW = new UserResultDTO();
		
		try {
			resultW = userReportManagerRemote.doMergeUserAttributes(userMergeInitParam);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");
		}
		
		return resultW;		
	}
	
	public BaseResultDTO validateActiveSessions(UserDTO user, String accessToken)
	{
		BaseResultDTO  resultW = new BaseResultDTO();
		
		try {
			resultW = userReportManagerRemote.validateActiveSessions(user, accessToken);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
		
		return resultW;
	}
	
	public BaseResultDTO doLogOut(String refreshToken) 
	{
		BaseResultDTO  resultW = new BaseResultDTO();
		
		try {
			resultW = userReportManagerRemote.doLogOut(refreshToken);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
		
		return resultW;
	}
	
	public BaseResultDTO addUserProfileLikeUser(Long userid, Long useridbase){
		
		try {
			return userReportManagerRemote.addUserProfileLikeUser(userid, useridbase);
		} catch (Exception e) {
			e.printStackTrace();
			UserResultDTO resultDTO = new UserResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	
	@Override
	public FileDownloadInfoResultDTO downloadUserRecords(UserReportInitParamDTO initParamW, Long companyTypeId, Long companyId, OrderCriteriaDTO[] usersOrderCriteria, Long uskey, String fileformat, Locale locale ) {
		
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		UserReportDataDTO userRecordsResultDTO = null;
		
		try {
			userRecordsResultDTO = userReportManagerRemote.getUserReport(initParamW, companyTypeId, companyId, false, usersOrderCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P100");// modulo comercial no disp.
		}
		
		UserDataDTO[] userDataDTOs = userRecordsResultDTO.getUserReport();
		
		// Escribir descripcion del filtro seleccionado
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_user_title", locale));

		DataColumn col01 = new DataColumn("username", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_name", locale), String.class);
		DataColumn col02 = new DataColumn("lastname", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastname", locale), String.class);
		DataColumn col03 = new DataColumn("email", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_email", locale), String.class);
		DataColumn col04 = new DataColumn("person_id", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_person_id", locale), String.class);
		DataColumn col05 = new DataColumn("stateindicator", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_state", locale), String.class);		
		DataColumn col06 = new DataColumn("usertype",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_type", locale),String.class);
		DataColumn col07 = new DataColumn("companycode",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_code", locale),String.class);		
		DataColumn col08 = new DataColumn("companyname",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_name", locale),String.class);		
		DataColumn col09 = new DataColumn("position",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position", locale),String.class);		
		DataColumn col10 = new DataColumn("creationdate",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "creation_date", locale),String.class);		
		DataColumn col11 = new DataColumn("lastupdate",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date", locale),String.class);		
		DataColumn col12 = new DataColumn("lastlogin",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_login_date", locale),String.class);		
		
		
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
		dt0.addColumn(col11);
		dt0.addColumn(col12);
						
		DataRow row = null;
		UserDataDTO line = null;
	       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < userDataDTOs.length; i++) {
			line = userDataDTOs[i];

			row = dt0.newRow();
			row.setCellValue("username", line.getUsername());
			row.setCellValue("lastname", line.getLastname());
			row.setCellValue("email", line.getEmail());
			row.setCellValue("person_id", line.getPersonalid());
			row.setCellValue("stateindicator", stateDescription_function(line, locale) );
			row.setCellValue("usertype", line.getUsertype());
			row.setCellValue("companycode", line.getCompanycode());
			row.setCellValue("companyname", line.getCompanyname());
			row.setCellValue("position", line.getPosition());
			row.setCellValue("creationdate", line.getCreationdate() != null ? formatter.format(line.getCreationdate()) : "");
			row.setCellValue("lastupdate", line.getLastupdate() != null ? formatter.format(line.getLastupdate()) : "");
			row.setCellValue("lastlogin", line.getLastlogin() != null ? formatter.format(line.getLastlogin()) : "");
		
			
			dt0.addRow(row);
		}
		
		
		String downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_user_title", locale);
		String realname = "USERREPORT";
		
		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, fileformat, downloadname, realname, uskey);
		
		return resultDTO;
	}
	
	private String stateDescription_function(UserDataDTO user, Locale locale)
	{
		String result = "";
		if (user != null)
		{
			result = (user.isStateindicator() == true) ? I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "active", locale)
			: I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "inactive", locale);
		}

		return result;
	}
	
	public BaseResultDTO addAllCompaniesToUser(Long userid, boolean active, Long useradmin)
	{
		try{
			return userReportManagerRemote.addAllCompaniesToUser(userid, active, useradmin);
		}
		catch (Exception e){
			ProfileArrayResultDTO resultDTO = new ProfileArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public BaseResultDTO addAllLocalsToUser(Long userid, boolean active, Long useradmin)
	{
		try{
			return userReportManagerRemote.addAllLocalsToUser(userid, active,useradmin);
		}
		catch (Exception e){
			ProfileArrayResultDTO resultDTO = new ProfileArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	@Override
	public FileDownloadInfoResultDTO downloadActiveProviderFile(Long userkey, String fomart, Locale locale ) {
		
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		FileDownloadInfoResultDTO excelResult = new FileDownloadInfoResultDTO();
		ActiveUserReportDataDTO activeUserReportDataDTO = null;
		List<CompressToZipFileInitParamDTO> filesToZip = new ArrayList<CompressToZipFileInitParamDTO>();
		CompressToZipFileInitParamDTO fileToZip = null;
		CompressToZipFileResultDTO compressToZipFileResultDTO = null;
		try {
			activeUserReportDataDTO = userReportManagerRemote.getActiveProviderUserReport();
			if(activeUserReportDataDTO.getProviderUserProfileDTOs() == null || activeUserReportDataDTO.getProviderUserProfileDTOs().length == 0)
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
			if(activeUserReportDataDTO.getProviderUserCompanyDTOs() == null || activeUserReportDataDTO.getProviderUserCompanyDTOs().length == 0)
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
			if(activeUserReportDataDTO.getActiveUserLocalDTOs() == null || activeUserReportDataDTO.getActiveUserLocalDTOs().length == 0)
				return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(excelResult, "P100");// modulo comercial no disp.
		}
		
		ProviderUserProfileDTO[] providerUserProfileDTOs = activeUserReportDataDTO.getProviderUserProfileDTOs();
		
		// Escribir descripcion del filtro seleccionado
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_provider_profile_title", locale));

		DataColumn col01 = new DataColumn("username", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_names", locale), String.class);
		DataColumn col02 = new DataColumn("lastname", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastnames", locale), String.class);
		DataColumn col03 = new DataColumn("email", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_email", locale), String.class);
		DataColumn col04 = new DataColumn("person_id", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_person_id", locale), String.class);
		DataColumn col05 = new DataColumn("stateindicator", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "state", locale), String.class);		
		DataColumn col06 = new DataColumn("usertype",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_type", locale),String.class);
		DataColumn col07 = new DataColumn("companycode",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_code", locale),String.class);		
		DataColumn col08 = new DataColumn("companyname",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_name", locale),String.class);		
		DataColumn col09 = new DataColumn("position",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position", locale),String.class);		
		DataColumn col10 = new DataColumn("creationdate",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "creation_date", locale),String.class);		
		DataColumn col11 = new DataColumn("lastupdate",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date", locale),String.class);		
		DataColumn col12 = new DataColumn("lastlogin",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_login_date", locale),String.class);		
		DataColumn col13 = new DataColumn("profilename",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "profile", locale),String.class);		
		DataColumn col14 = new DataColumn("profilecode",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "profilecode", locale),String.class);		
	
		
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
		dt0.addColumn(col11);
		dt0.addColumn(col12);
		dt0.addColumn(col13);
		dt0.addColumn(col14);			
		
		DataRow row = null;
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		for (ProviderUserProfileDTO line : providerUserProfileDTOs) {

			row = dt0.newRow();
			row.setCellValue("username", line.getUsername());
			row.setCellValue("lastname", line.getLastname());
			row.setCellValue("email", line.getEmail());
			row.setCellValue("person_id", line.getPersonalid());
			row.setCellValue("stateindicator", stateDescription_function(line, locale) );
			row.setCellValue("usertype", line.getUsertype());
			row.setCellValue("companycode", line.getCompanycode());
			row.setCellValue("companyname", line.getCompanyname());
			row.setCellValue("position", line.getPosition());
			row.setCellValue("creationdate", line.getCreationdate() != null ? formatter.format(line.getCreationdate()) : "");
			row.setCellValue("lastupdate", line.getLastupdate() != null ? formatter.format(line.getLastupdate()) : "");
			row.setCellValue("lastlogin", line.getLastlogin() != null ? formatter.format(line.getLastlogin()) : "");
			row.setCellValue("profilename", line.getProfilename());
			row.setCellValue("profilecode", line.getProfilecode());
			
			dt0.addRow(row);
		}
		
		
		String downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_provider_profile_title", locale);
		String realname = "ACTIVEPROVIDERPROFILE";
		
		excelResult = FileHandlerUtils.getInstance().downloadFile(dt0, fomart, realname, downloadname, userkey);
		fileToZip = new CompressToZipFileInitParamDTO();
		fileToZip.setSourceFileName(excelResult.getRealfilename());
		fileToZip.setDownloadFileName(excelResult.getDownloadfilename());
		filesToZip.add(fileToZip);
		
		ProviderUserCompanyDTO[] providerUserCompanyDTOs = activeUserReportDataDTO.getProviderUserCompanyDTOs();
		
		// Escribir descripcion del filtro seleccionado
		DataTable dt1 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_provider_companies_title", locale));

		col01 = new DataColumn("username", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_names", locale), String.class);
		col02 = new DataColumn("lastname", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastnames", locale), String.class);
		col03 = new DataColumn("email", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_email", locale), String.class);
		col04 = new DataColumn("person_id", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_person_id", locale), String.class);
		col05 = new DataColumn("stateindicator", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "state", locale), String.class);		
		col06 = new DataColumn("usertype",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_type", locale),String.class);
		col07 = new DataColumn("companycode",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_code", locale),String.class);		
		col08 = new DataColumn("companyname",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_name", locale),String.class);		
		col09 = new DataColumn("position",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position", locale),String.class);		
		col10 = new DataColumn("creationdate",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "creation_date", locale),String.class);		
		col11 = new DataColumn("lastupdate",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date", locale),String.class);		
		col12 = new DataColumn("lastlogin",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_login_date", locale),String.class);		
		col13 = new DataColumn("vendorname",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "vendorname", locale),String.class);		
		col14 = new DataColumn("vendorcode",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "vendorcode", locale),String.class);		
	
		
		dt1.addColumn(col01);
		dt1.addColumn(col02);
		dt1.addColumn(col03);
		dt1.addColumn(col04);
		dt1.addColumn(col05);
		dt1.addColumn(col06);
		dt1.addColumn(col07);
		dt1.addColumn(col08);
		dt1.addColumn(col09);
		dt1.addColumn(col10);
		dt1.addColumn(col11);
		dt1.addColumn(col12);
		dt1.addColumn(col13);
		dt1.addColumn(col14);			
		
		row = null;
		for (ProviderUserCompanyDTO line : providerUserCompanyDTOs) {

			row = dt1.newRow();
			row.setCellValue("username", line.getUsername());
			row.setCellValue("lastname", line.getLastname());
			row.setCellValue("email", line.getEmail());
			row.setCellValue("person_id", line.getPersonalid());
			row.setCellValue("stateindicator", stateDescription_function(line, locale) );
			row.setCellValue("usertype", line.getUsertype());
			row.setCellValue("companycode", line.getCompanycode());
			row.setCellValue("companyname", line.getCompanyname());
			row.setCellValue("position", line.getPosition());
			row.setCellValue("creationdate", line.getCreationdate() != null ? formatter.format(line.getCreationdate()) : "");
			row.setCellValue("lastupdate", line.getLastupdate() != null ? formatter.format(line.getLastupdate()) : "");
			row.setCellValue("lastlogin", line.getLastlogin() != null ? formatter.format(line.getLastlogin()) : "");
			row.setCellValue("vendorname", validateAll(line.getAllenterprises(),line.getProvidername(),locale));
			row.setCellValue("vendorcode", validateAll(line.getAllenterprises(),line.getProvidercode(),locale));
			
			dt1.addRow(row);
		}
		
		downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_provider_companies_title", locale);
		realname = "ACTIVEPROVIDERCOMPANIES";
		excelResult = FileHandlerUtils.getInstance().downloadFile(dt1, fomart ,realname, downloadname, userkey);
		fileToZip = new CompressToZipFileInitParamDTO();
		fileToZip.setSourceFileName(excelResult.getRealfilename());
		fileToZip.setDownloadFileName(excelResult.getDownloadfilename());
		filesToZip.add(fileToZip);
		
		ActiveUserLocalDTO[] activeUserLocalDTOs = activeUserReportDataDTO.getActiveUserLocalDTOs();
		
		// Escribir descripcion del filtro seleccionado
		DataTable dt2 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_active_user_locals_title", locale));

		col01 = new DataColumn("username", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_names", locale), String.class);
		col02 = new DataColumn("lastname", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastnames", locale), String.class);
		col03 = new DataColumn("email", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_email", locale), String.class);
		col04 = new DataColumn("person_id", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_person_id", locale), String.class);
		col05 = new DataColumn("stateindicator", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "state", locale), String.class);		
		col06 = new DataColumn("usertype",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_type", locale),String.class);
		col07 = new DataColumn("companycode",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_code", locale),String.class);		
		col08 = new DataColumn("companyname",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_name", locale),String.class);		
		col09 = new DataColumn("position",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position", locale),String.class);		
		col10 = new DataColumn("creationdate",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "creation_date", locale),String.class);		
		col11 = new DataColumn("lastupdate",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date", locale),String.class);		
		col12 = new DataColumn("lastlogin",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_login_date", locale),String.class);		
		col13 = new DataColumn("localcode",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "local_code", locale),String.class);		
		col14 = new DataColumn("localname",I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "local_name", locale),String.class);		
	
		
		dt2.addColumn(col01);
		dt2.addColumn(col02);
		dt2.addColumn(col03);
		dt2.addColumn(col04);
		dt2.addColumn(col05);
		dt2.addColumn(col06);
		dt2.addColumn(col07);
		dt2.addColumn(col08);
		dt2.addColumn(col09);
		dt2.addColumn(col10);
		dt2.addColumn(col11);
		dt2.addColumn(col12);
		dt2.addColumn(col13);
		dt2.addColumn(col14);			
		
		row = null;
		for (ActiveUserLocalDTO line : activeUserLocalDTOs) {

			row = dt2.newRow();
			row.setCellValue("username", line.getUsername());
			row.setCellValue("lastname", line.getLastname());
			row.setCellValue("email", line.getEmail());
			row.setCellValue("person_id", line.getPersonalid());
			row.setCellValue("stateindicator", stateDescription_function(line, locale) );
			row.setCellValue("usertype", line.getUsertype());
			row.setCellValue("companycode", line.getCompanycode());
			row.setCellValue("companyname", line.getCompanyname());
			row.setCellValue("position", line.getPosition());
			row.setCellValue("creationdate", line.getCreationdate() != null ? formatter.format(line.getCreationdate()) : "");
			row.setCellValue("lastupdate", line.getLastupdate() != null ? formatter.format(line.getLastupdate()) : "");
			row.setCellValue("lastlogin", line.getLastlogin() != null ? formatter.format(line.getLastlogin()) : "");
			row.setCellValue("localcode", validateAll(line.getAlllocals(),line.getLocalcode(),locale));
			row.setCellValue("localname", validateAll(line.getAlllocals(),line.getLocalname(),locale));
			
			dt2.addRow(row);
		}
		
		downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_active_user_locals_title", locale);
		realname = "ACTIVEUSERLOCALS";
		excelResult = FileHandlerUtils.getInstance().downloadFile(dt2, fomart ,realname, downloadname, userkey);
		fileToZip = new CompressToZipFileInitParamDTO();
		fileToZip.setSourceFileName(excelResult.getRealfilename());
		fileToZip.setDownloadFileName(excelResult.getDownloadfilename());
		filesToZip.add(fileToZip);
		
		try
		{
			downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_provider_report_title", locale);
			realname = "ACTIVEPROVIDERREPORT";
			compressToZipFileResultDTO = FileHandlerUtils.getInstance().compressFilesToZipFile(filesToZip, realname ,downloadname, userkey);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
		}

		resultDTO.setDownloadfilename(compressToZipFileResultDTO.getDownloadFileName());
		resultDTO.setRealfilename(compressToZipFileResultDTO.getRealFileName());
		
		return resultDTO;
	}
	
	private String validateAll(boolean isAll, String current, Locale locale)
	{
		if (isAll)
		{
			return I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "all", locale).toUpperCase();
		}
		else
		{
			if (current == null || current.isEmpty())
			{
				return "";
			}
			else
			{
				return current;
			}
		}
	}
	
	public BaseResultDTO doUpdateLastAccessOfUser(String refreshToken) {
		try{
			return userReportManagerRemote.doUpdateLastAccessOfUser(refreshToken);
		}
		catch (Exception e){
			ProfileArrayResultDTO resultDTO = new ProfileArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public UsersResultDTO getAdminUserRTByProfile(Long prkey)
	{
		try{
			return userReportManagerRemote.getAdminUserRTByProfile(prkey);
		}
		catch (Exception e){
			UsersResultDTO resultDTO = new UsersResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public BaseResultDTO deleteAdminRetailFromProfile(Long profileid, Long[] adminIds, SelfManagementUpdateUserInfoDTO userdata)
	{
		try{
			return userReportManagerRemote.deleteAdminRetailFromProfile(profileid, adminIds, userdata);
		}
		catch (Exception e){
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public UsersResultDTO findUserForAdminRetailFilter(UserAdminRetailFilterInitParam initParam)
	{
		try{
			return userReportManagerRemote.findUserForAdminRetailFilter(initParam);
		}
		catch (Exception e){
			UsersResultDTO resultDTO = new UsersResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public BaseResultDTO doAddAdminRetail(Long[] userid, Long profileid, SelfManagementUpdateUserInfoDTO userdata)
	{
		try{
			return userReportManagerRemote.doAddAdminRetail(userid, profileid, userdata);
		}
		catch (Exception e){
			UsersResultDTO resultDTO = new UsersResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	@Override
	public FileDownloadInfoResultDTO downloadUserAuditReport(UserAuditInitParamDTO initparamDTO, Long uskey) {

		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		
		UserAuditReportResultDTO userAuditReportResultDTO = new UserAuditReportResultDTO();
		try {
			userAuditReportResultDTO = userReportManagerRemote.getUserAuditReport(initparamDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
		
		if(!userAuditReportResultDTO.getStatuscode().equals("0")){
			resultDTO.setStatuscode(userAuditReportResultDTO.getStatuscode());
			resultDTO.setStatusmessage(userAuditReportResultDTO.getStatusmessage());
			return resultDTO;
		}
		
		Locale locale = initparamDTO.getLocale();
		DataRow row = null;
		// Escribir descripcion del filtro seleccionado
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_provider_profile_title", locale));
		
		DataColumn col01 = new DataColumn("fecha", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_date", locale), LocalDateTime.class);
		DataColumn col02 = new DataColumn("funcionalidad", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_functionality", locale), String.class);	
		DataColumn col03 = new DataColumn("accion", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_action", locale), String.class);			
		DataColumn col04 = new DataColumn("entidad", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_entity", locale), String.class);
		DataColumn col05 = new DataColumn("detalle", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_detail", locale), String.class);
		DataColumn col06 = new DataColumn("estado", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_state", locale), String.class);
		DataColumn col07 = new DataColumn("usuario_nombre", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_username", locale), String.class);		
		DataColumn col08 = new DataColumn("usuario_apellido", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_userlastname", locale), String.class);
		DataColumn col09 = new DataColumn("usuario_correo", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_useremail", locale), String.class);
		DataColumn col10 = new DataColumn("usuario_cargo", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_userposition", locale), String.class);
		DataColumn col11 = new DataColumn("usuario_tipo", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_usertype", locale), String.class);
		DataColumn col12 = new DataColumn("adminempresa_nombre", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_adminenterprisename", locale), String.class);
		DataColumn col13 = new DataColumn("adminempresa_apellido", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_adminenterpriselastname", locale), String.class);
		DataColumn col14 = new DataColumn("adminempresa_correo", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_adminenterpriseemail", locale), String.class);
		DataColumn col15 = new DataColumn("adminempresa_cargo", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_adminenterpriseposition", locale), String.class);
		DataColumn col16 = new DataColumn("adminempresa_tipo", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_adminenterprisetype", locale), String.class);
		DataColumn col17 = new DataColumn("adminretail_nombre", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_adminretailname", locale), String.class);
		DataColumn col18 = new DataColumn("adminretail_apellido", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_adminretaillastname", locale), String.class);
		DataColumn col19 = new DataColumn("adminretail_correo", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_adminretailemail", locale), String.class);
		DataColumn col20 = new DataColumn("adminretail_cargo", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_adminretailposition", locale), String.class);
		DataColumn col21 = new DataColumn("adminretail_tipo", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "audit_adminretailtype", locale), String.class);
		
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
		dt0.addColumn(col11);
		dt0.addColumn(col12);
		dt0.addColumn(col13);
		dt0.addColumn(col14);
		dt0.addColumn(col15);
		dt0.addColumn(col16);	
		dt0.addColumn(col17);
		dt0.addColumn(col18);
		dt0.addColumn(col19);
		dt0.addColumn(col20);
		dt0.addColumn(col21);
		
		
		UserAuditDTO lineUser = null;
		
		
		for (int i = 0; i <userAuditReportResultDTO.getUserAuditDTOs().length ; i++  ){
			lineUser = userAuditReportResultDTO.getUserAuditDTOs()[i];
			
			row = dt0.newRow();
			
			row.setCellValue("fecha", lineUser.getAuditdate());
			row.setCellValue("funcionalidad", lineUser.getFunctionality());
			row.setCellValue("accion", lineUser.getAction());
			row.setCellValue("entidad", lineUser.getEntity());
			row.setCellValue("detalle", lineUser.getDetail());
			row.setCellValue("estado", lineUser.getState());
			row.setCellValue("usuario_nombre", lineUser.getUsername());
			row.setCellValue("usuario_apellido", lineUser.getUserlastname());
			row.setCellValue("usuario_correo", lineUser.getUseremail());
			row.setCellValue("usuario_cargo", lineUser.getUserposition());
			row.setCellValue("usuario_tipo", lineUser.getUsertype());
			row.setCellValue("adminempresa_nombre", lineUser.getCompanyadminname());
			row.setCellValue("adminempresa_apellido", lineUser.getCompanyadminlastname());
			row.setCellValue("adminempresa_correo", lineUser.getCompanyadminemail());
			row.setCellValue("adminempresa_cargo", lineUser.getCompanyadminposition());
			row.setCellValue("adminempresa_tipo", lineUser.getCompanyadmintype());
			row.setCellValue("adminretail_nombre", lineUser.getRetailadminname());
			row.setCellValue("adminretail_apellido", lineUser.getRetailadminlastname());
			row.setCellValue("adminretail_correo", lineUser.getRetailadminemail());
			row.setCellValue("adminretail_cargo", lineUser.getRetailadminposition());
			row.setCellValue("adminretail_tipo", lineUser.getRetailadmintype());
			
			dt0.addRow(row);
		}
		
		String downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_user_audit", locale);
		String realname = "AUDITUSER";
		
		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, "xls", downloadname, realname, uskey);
		
		try {
			CompressToZipFileInitParamDTO compressToZipFileInitParamDTO = new CompressToZipFileInitParamDTO();
			compressToZipFileInitParamDTO.setSourceFileName(resultDTO.getRealfilename());
			compressToZipFileInitParamDTO.setDownloadFileName(resultDTO.getDownloadfilename());

			CompressToZipFileResultDTO compressToZipFileResultDTO = FileHandlerUtils.getInstance().compressToZipFile(compressToZipFileInitParamDTO);

			resultDTO.setRealfilename(compressToZipFileResultDTO.getRealFileName());
			resultDTO.setDownloadfilename(compressToZipFileResultDTO.getDownloadFileName());
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
			
		}
		
		return resultDTO;
			
	}

	@Override
	public UserAuditMinDateResultDTO getMinAuditDate() {
		try{
			return userReportManagerRemote.getMinAuditDate();
		}
		catch (Exception e){
			UserAuditMinDateResultDTO resultDTO = new UserAuditMinDateResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
}
