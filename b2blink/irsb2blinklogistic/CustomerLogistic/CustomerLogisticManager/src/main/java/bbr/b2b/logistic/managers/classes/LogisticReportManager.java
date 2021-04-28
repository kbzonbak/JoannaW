//package bbr.b2b.logistic.managers.classes;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import javax.ejb.TransactionAttribute;
//import javax.ejb.TransactionAttributeType;
//
//import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
//import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
//import bbr.b2b.common.adtclasses.exceptions.ServiceException;
//import bbr.b2b.common.factories.BeanExtenderFactory;
//import bbr.b2b.common.factories.DateConverterFactory2;
//import bbr.b2b.logistic.buyorders.classes.LocationServerLocal;
//import bbr.b2b.logistic.buyorders.classes.VendorServerLocal;
//import bbr.b2b.logistic.buyorders.data.classes.LocationDTO;
//import bbr.b2b.logistic.buyorders.data.classes.VendorResultDTO;
//import bbr.b2b.logistic.buyorders.data.classes.VendorW;
//import bbr.b2b.logistic.buyorders.entities.Vendor;
//import bbr.b2b.logistic.log.classes.LogisticLogServerLocal;
//import bbr.b2b.logistic.log.data.classes.LogisticLogW;
//import bbr.b2b.logistic.managers.interfaces.LogisticReportManagerLocal;
//import bbr.b2b.logistic.managers.interfaces.LogisticReportManagerRemote;
//import bbr.b2b.logistic.report.classes.AllVendorsIdsDTO;
//import bbr.b2b.logistic.report.classes.LocationArrayResultDTO;
//import bbr.b2b.logistic.report.classes.LocationDataDTO;
//import bbr.b2b.logistic.report.classes.LocationsArrayResultDTO;
//import bbr.b2b.logistic.report.classes.LogisticLogReportDataDTO;
//import bbr.b2b.logistic.report.classes.LogisticLogReportInitParamDTO;
//import bbr.b2b.logistic.report.classes.LogisticLogReportResultDTO;
//import bbr.b2b.logistic.report.classes.VendorDTO;
//import bbr.b2b.logistic.report.classes.VendorsArrayResultDTO;
//import bbr.b2b.logistic.utils.LogisticStatusCodeUtils;
//
//@Stateless(name = "managers/LogisticReportManager")
//@TransactionAttribute(TransactionAttributeType.REQUIRED)
//public class LogisticReportManager implements LogisticReportManagerLocal, LogisticReportManagerRemote {
//
//	@EJB
//	private LocationServerLocal locationserverlocal;
//	
//	@EJB
//	private VendorServerLocal vendorserverlocal;
//	
//	@EJB
//	private LogisticLogServerLocal logisticlogserverlocal;
//
//	public LocationArrayResultDTO getLocationsOrderByNameAndWarehouse() {
//		LocationArrayResultDTO resultW = new LocationArrayResultDTO();
//		LocationDataDTO[] locationsWs;
//		try {
//			locationsWs = locationserverlocal.getLocationsOrderByNameAndWarehouse();
//		} catch (NotFoundException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "700");
//		}
//		catch (OperationFailedException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "1500");
//		}
//		resultW.setLocations(locationsWs);
//		return resultW;
//	}
//	
//	public VendorResultDTO findVendorsById(Long vendorId){
//		VendorResultDTO resultW = new VendorResultDTO();
//		VendorDTO vendorDTO;
//		try {
//			VendorW vendorw = vendorserverlocal.getById(vendorId);
//			vendorDTO = new VendorDTO();
//			BeanExtenderFactory.copyProperties(vendorw, vendorDTO);
//		}catch (OperationFailedException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "111");
//		}catch (NotFoundException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "444");
//		}
//		resultW.setVendor(vendorDTO);
//		return resultW;		
//	}
//	
//	public VendorsArrayResultDTO findVendorsByIds(Long[] vendorIds){
//		
//		VendorsArrayResultDTO resultW = new VendorsArrayResultDTO();
//		VendorW[] vendors = null;
//		try {
//			vendors = vendorserverlocal.getVendorsByIds(vendorIds);								
//		}catch (OperationFailedException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "111");
//		}catch (NotFoundException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "444");
//		}
//		ArrayList<VendorDTO> vendorList = new ArrayList<>();
//		for(VendorW vendor : vendors)
//		{
//			VendorDTO vendorDTO = new VendorDTO();
//			try {
//				DateConverterFactory2.copyProperties(vendor, vendorDTO);
//			} catch (OperationFailedException e) {
//				
//				e.printStackTrace();
//			}
//			vendorList.add(vendorDTO);
//		}
//		resultW.setVendors(vendorList.toArray(new VendorDTO[vendorList.size()]));
//		return resultW;		
//	}
//	
//	
//	public VendorResultDTO findVendorsByDni(String dni){
//		VendorResultDTO resultDTO = new VendorResultDTO();
//		VendorDTO vendorDTO = new VendorDTO(); 
//		try {
//			VendorW vendorW = vendorserverlocal.getVendorByDni(dni);
//			DateConverterFactory2.copyProperties(vendorW, vendorDTO);
//		}catch (OperationFailedException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "111");
//		}catch (NotFoundException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "1500");
//		}		
//		resultDTO.setVendor(vendorDTO);
//		return resultDTO;		
//	}
//	
//	public LocationsArrayResultDTO findLocationsByCode(String code){
//		LocationsArrayResultDTO resultW =  new LocationsArrayResultDTO();		
//		LocationDTO[] localW = null;
//		List<LocationDTO> locals = null;
//		
//		try{
//			locals = locationserverlocal.getByProperty("code", code);		
//		}catch (OperationFailedException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "111");
//		}catch (NotFoundException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "1500");
//		}		
//		localW = locals.toArray(new LocationDTO[locals.size()]);
//		resultW.setLocations(localW);
//		return resultW;
//	}
//	
//	public LocationsArrayResultDTO findLocationsByName(String name){
//		LocationsArrayResultDTO resultW =  new LocationsArrayResultDTO();		
//		LocationDTO[] localW = null;
//		List<LocationDTO> locals = null;
//		
//		try{
//			locals = locationserverlocal.getLikeStringProperty("name", name, false);		
//		}catch (OperationFailedException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "111");
//		}catch (NotFoundException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "1500");
//		}		
//		localW = locals.toArray(new LocationDTO[locals.size()]);
//		resultW.setLocations(localW);
//		return resultW;
//	}
//	
//	public LocationsArrayResultDTO findLocationsByIds(Long[] localIds){
//		LocationsArrayResultDTO resultW =  new LocationsArrayResultDTO();		
//		LocationDTO[] locals = null;
//		
//		try{
//			locals = locationserverlocal.getLocationsByIds(localIds);		
//		}catch (OperationFailedException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "111");
//		}catch (NotFoundException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "1500");
//		}		
//		resultW.setLocations(locals);
//		return resultW;
//	}	
//	
//	public VendorsArrayResultDTO findVendorsOfUserByCode(String code, Long[] vendorids) {
//		VendorsArrayResultDTO resultW = new VendorsArrayResultDTO();
//		try {
//			vendorserverlocal.findVendorsOfUserByCode(code, vendorids);
//		}catch (OperationFailedException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "111");
//		} catch (NotFoundException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "444"); // No se encontraron proveedores
//		}
//		
////		resultW.setVendors(vendorDataWs);
//		return resultW;
//	}
//
//	public VendorsArrayResultDTO findVendorsOfUserByName(String name, Long[] vendorids) {
//		VendorsArrayResultDTO resultW = new VendorsArrayResultDTO();
//		try {
//			vendorserverlocal.findVendorsOfUserByName(name, vendorids);
//		}catch (OperationFailedException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "111");
//		} catch (NotFoundException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "444"); // No se encontraron proveedores
//		}
//		
////		resultW.setVendors(vendorDataWs);
//		return resultW;
//	}	
//	
//	public VendorsArrayResultDTO findVendorsByCode(String code) {
//		VendorsArrayResultDTO resultW = new VendorsArrayResultDTO();
//		try {
//			vendorserverlocal.findVendorsByCode(code);
//		}catch (OperationFailedException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "111");
//			} catch (NotFoundException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "444"); // No se encontraron proveedores
//		}
//		
////		resultW.setVendors(vendorDataWs);
//		return resultW;
//	}
//
//	public VendorsArrayResultDTO findVendorsByName(String name) {
//		VendorsArrayResultDTO resultW = new VendorsArrayResultDTO();
//		try {
//			vendorserverlocal.findVendorsByName(name);
//		}catch (OperationFailedException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "111");
//		} catch (NotFoundException e) {
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "444"); // No se encontraron proveedores
//		}
//		
////		resultW.setVendors(vendorDataWs);
//		return resultW;
//	}	
//	
//	public AllVendorsIdsDTO getAllVendorIds(){
//		Set<Long> vendorids = null;
//		try {
//			Vendor[] vendors = vendorserverlocal.findAllAsArray();
//			vendorids = new HashSet<Long>();
//			
//			for (int i = 0; i < vendors.length; i++) {
//				vendorids.add(vendors[i].getId());
//			}					
//		} catch (ServiceException e) {
//			e.printStackTrace();
//			return null;
//		}
//		AllVendorsIdsDTO allVendorsIds = new AllVendorsIdsDTO();
//		allVendorsIds.setAllVendorsIds(vendorids);
//		return allVendorsIds;
//	}	
//	
//	public void doLogLogisticEvents(Long locationid, String userdni, String username, String userlastname, String usertype, String companyname, String companydni, String report, String action, String comment){
//		
//		try{
//			Date now = new Date();
//			
//			// BUSCA LOCAL CON QUE LANZ� REPORTE
//			LocationDTO location = locationserverlocal.getById(locationid);
//			
//			LogisticLogW logisticlog = new LogisticLogW();
//			
//			logisticlog.setWhen(now);
//			logisticlog.setUserdni(userdni);
//			logisticlog.setUsername(username);
//			logisticlog.setUserlastname(userlastname);
//			logisticlog.setUsertype(usertype);
//			logisticlog.setUsercompany(companyname);
//			logisticlog.setUsercompanydni(companydni);
//			logisticlog.setLocaldescription(location.getName());
//			logisticlog.setLocalcode(location.getCode());
//			logisticlog.setMenu("");
//			logisticlog.setReport(report);
//			logisticlog.setAction(action);
//			logisticlog.setComment(comment);
//					
//			logisticlogserverlocal.addElement(logisticlog);		
//			
//		}catch (ServiceException e) {
//			e.printStackTrace();			
//		}			
//	}
//	
//	public LogisticLogReportResultDTO getLogisticLogReportByRange(LogisticLogReportInitParamDTO initParams){
//		LogisticLogReportResultDTO result = new LogisticLogReportResultDTO();
//		LogisticLogReportDataDTO[] logdata = null;
//		
//		try{
//			logdata = logisticlogserverlocal.getLogisticLogReportByRange(DateConverterFactory2.StrToDate(initParams.getSince()), DateConverterFactory2.StrToDate(initParams.getUntil()));
//			result.setLogdata(logdata);
//			return result;
//		}catch (ServiceException e) {
//			e.printStackTrace();			
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(result, "111");
//		}catch (ParseException e) {
//			e.printStackTrace();
//			return LogisticStatusCodeUtils.getInstance().setStatusCode(result, "111");
//		}		
//	}	
//}
