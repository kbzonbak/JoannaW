package bbr.b2b.logistic.managers.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.logistic.parameters.report.classes.ParameterResultDTO;
import bbr.b2b.logistic.report.classes.LocationsArrayResultDTO;
import bbr.b2b.logistic.vendors.report.classes.VendorArrayResultDTO;

public interface ILogisticReportManager {

	public LocationsArrayResultDTO getLocations();
	public LocationsArrayResultDTO getLocationsLimitByMax(OrderCriteriaDTO[] ordercriteria, Integer maxvalues);
	public LocationsArrayResultDTO getLocationsLikeCode(String code);	
	public LocationsArrayResultDTO getLocationsLikeName(String name);	 
	public VendorArrayResultDTO getVendors();
	public VendorArrayResultDTO getVendorsLimitByMax(OrderCriteriaDTO[] ordercriteria, Integer maxvalues);
	public VendorArrayResultDTO getVendorsLikeCode(String code);
	public VendorArrayResultDTO getVendorsLikeName(String name);
	public ParameterResultDTO getParameterByCode(String code);
}
