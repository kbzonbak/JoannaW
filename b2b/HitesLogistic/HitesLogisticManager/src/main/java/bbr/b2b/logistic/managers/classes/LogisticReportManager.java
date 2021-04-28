package bbr.b2b.logistic.managers.classes;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.logistic.managers.interfaces.LogisticReportManagerLocal;
import bbr.b2b.logistic.managers.interfaces.LogisticReportManagerRemote;
import bbr.b2b.logistic.parameters.classes.ParameterServerLocal;
import bbr.b2b.logistic.parameters.data.wrappers.ParameterW;
import bbr.b2b.logistic.parameters.report.classes.ParameterDTO;
import bbr.b2b.logistic.parameters.report.classes.ParameterResultDTO;
import bbr.b2b.logistic.report.classes.LocationDTO;
import bbr.b2b.logistic.report.classes.LocationsArrayResultDTO;
import bbr.b2b.logistic.utils.LogisticStatusCodeUtils;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.logistic.vendors.report.classes.VendorArrayResultDTO;
import bbr.b2b.logistic.vendors.report.classes.VendorDTO;

@Stateless(name = "managers/LogisticReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LogisticReportManager implements LogisticReportManagerLocal, LogisticReportManagerRemote {

	@EJB
	private LocationServerLocal locationserver;

	@EJB
	private ParameterServerLocal parameterserver;
	
	@EJB
	private VendorServerLocal vendorserver; 
	
	
	public LocationsArrayResultDTO getLocations(){
		LocationsArrayResultDTO resultDTO = new LocationsArrayResultDTO();

		try {
			// Obtiene locales
			LocationW[] locationArr = locationserver.getAllAsArray();
			if (locationArr == null || locationArr.length <= 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L700");
			}

			LocationDTO[] locationsDTOs = new LocationDTO[locationArr.length];
			BeanExtenderFactory.copyProperties(locationArr, locationsDTOs, LocationDTO.class);

			resultDTO.setLocations(locationsDTOs);
			return resultDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	public LocationsArrayResultDTO getLocationsLimitByMax(OrderCriteriaDTO[] ordercriteria, Integer maxvalues){
		LocationsArrayResultDTO resultDTO = new LocationsArrayResultDTO();

		try {
			// Obtiene locales
			LocationW[] locationArr = locationserver.getAllAsArrayOrdered(1, maxvalues, ordercriteria);
			if (locationArr == null || locationArr.length <= 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L700");
			}

			LocationDTO[] locationsDTOs = new LocationDTO[locationArr.length];
			BeanExtenderFactory.copyProperties(locationArr, locationsDTOs, LocationDTO.class);

			// Cantidad total de registros
			int total = locationserver.getCountLocations();
			
			resultDTO.setTotal(total);
			resultDTO.setLocations(locationsDTOs);
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}
	
	
	public LocationsArrayResultDTO getLocationsLikeCode(String code) {
		LocationsArrayResultDTO resultDTO = new LocationsArrayResultDTO();
		try{
			List<LocationW> locationWList = locationserver.getLikeStringProperty("code", code, false);
			LocationW[] locationWs  = locationWList.toArray(new LocationW[locationWList.size()]);
			if(locationWs == null || locationWs.length <=0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L700");
			}
			LocationDTO[] locationDTOs = new LocationDTO[locationWs.length];
			BeanExtenderFactory.copyProperties(locationWs, locationDTOs, LocationDTO.class);
			
			resultDTO.setLocations(locationDTOs);
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	
	public LocationsArrayResultDTO getLocationsLikeName(String name){
		LocationsArrayResultDTO resultDTO = new LocationsArrayResultDTO();
		try{
			 
			List<LocationW> locationWList = locationserver.getLikeStringProperty("name", name, false);
			LocationW[] locationWs  = locationWList.toArray(new LocationW[locationWList.size()]);
			if(locationWs == null || locationWs.length <=0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L700");
			}
			LocationDTO[] locationDTOs = new LocationDTO[locationWs.length];
			BeanExtenderFactory.copyProperties(locationWs, locationDTOs, LocationDTO.class);
			
			resultDTO.setLocations(locationDTOs);
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
	}
	
	
	public VendorArrayResultDTO getVendors(){
		VendorArrayResultDTO resultDTO = new VendorArrayResultDTO();

		try {
			// Obtiene proveedores
			VendorW[] vendorArr = vendorserver.getAllAsArray();
			if (vendorArr == null || vendorArr.length <= 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L600");
			}

			VendorDTO[] vendorDTOs = new VendorDTO[vendorArr.length];
			BeanExtenderFactory.copyProperties(vendorArr, vendorDTOs, VendorDTO.class);

			resultDTO.setVendors(vendorDTOs);
			return resultDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		
	}

	public VendorArrayResultDTO getVendorsLimitByMax(OrderCriteriaDTO[] ordercriteria, Integer maxvalues){
		VendorArrayResultDTO resultDTO = new VendorArrayResultDTO();

		try {
			// Obtiene proveedores
			VendorW[] vendorArr = vendorserver.getAllAsArrayOrdered(1, maxvalues, ordercriteria);
			if (vendorArr == null || vendorArr.length <= 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L600");
			}

			VendorDTO[] vendorDTOs = new VendorDTO[vendorArr.length];
			BeanExtenderFactory.copyProperties(vendorArr, vendorDTOs, VendorDTO.class);

			// Cantidad total de registros
			int total = vendorserver.getCountVendors();
			
			resultDTO.setTotal(total);
			resultDTO.setVendors(vendorDTOs);
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	
	public VendorArrayResultDTO getVendorsLikeCode(String code) {
		VendorArrayResultDTO resultDTO = new VendorArrayResultDTO();
		try{
			List<VendorW> vendorWList = vendorserver.getLikeStringProperty("code", code, false);
			VendorW[] vendorWs  = vendorWList.toArray(new VendorW[vendorWList.size()]);
			if(vendorWs == null || vendorWs.length <=0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L600");
			}
			VendorDTO[] vendorDTOs = new VendorDTO[vendorWs.length];
			BeanExtenderFactory.copyProperties(vendorWs, vendorDTOs, VendorDTO.class);
			
			resultDTO.setVendors(vendorDTOs);
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	
	public VendorArrayResultDTO getVendorsLikeName(String name){
		VendorArrayResultDTO resultDTO = new VendorArrayResultDTO();
		try{
			 
			List<VendorW> vendorWList = vendorserver.getLikeStringProperty("name", name, false);
			VendorW[] vendorWs  = vendorWList.toArray(new VendorW[vendorWList.size()]);
			if(vendorWs == null || vendorWs.length <=0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L600");
			}
			VendorDTO[] vendorDTOs = new VendorDTO[vendorWs.length];
			BeanExtenderFactory.copyProperties(vendorWs, vendorDTOs, VendorDTO.class);
			
			resultDTO.setVendors(vendorDTOs);
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
	}	
	
	
	// Obtiene parámentros desde tabla Parameter
	public ParameterResultDTO getParameterByCode(String code) {
		ParameterResultDTO resultDTO = new ParameterResultDTO();
		try{
			ParameterW[] parameterWs  = parameterserver.getByPropertyAsArray("code", code);
			if(parameterWs == null || parameterWs.length <=0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L900");
			}
			ParameterDTO[] parameterDTOs = new ParameterDTO[parameterWs.length];
			BeanExtenderFactory.copyProperties(parameterWs, parameterDTOs, ParameterDTO.class);
			
			resultDTO.setParameter(parameterDTOs);
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}
	
}
