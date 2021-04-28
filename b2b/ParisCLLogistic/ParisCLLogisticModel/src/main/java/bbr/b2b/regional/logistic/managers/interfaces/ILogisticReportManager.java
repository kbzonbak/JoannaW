package bbr.b2b.regional.logistic.managers.interfaces;

import java.util.Set;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderStateTypeArrayResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CommuneCourierArrayResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CommuneCourierDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierInformationResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.HiredCourierArrayResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.HiredCourierDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.HiredCourierResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.VendorAddressInitParamDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.VendorAddressResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.VendorHiredCourierResultsDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryStateTypeArrayResultDTO;
import bbr.b2b.regional.logistic.directorders.report.classes.DirectOrderStateTypeArrayResultDTO;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemReportInitParamDTO;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemReportResultDTO;
import bbr.b2b.regional.logistic.locations.report.classes.LocationLogArrayResultDTO;
import bbr.b2b.regional.logistic.locations.report.classes.LocationsLogArrayResultDTO;
import bbr.b2b.regional.logistic.report.classes.AssignedDataInitParamsDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.AddInvoiceVendorRollOutInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.DeleteInvoiceVendorRollOutInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.DepartmentArrayResultDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.InvoiceVendorRollOutArrayResultDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.InvoiceVendorRollOutInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.UpdateInvoiceVendorRollOutInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationArrayResultDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationUploadInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationUploadResultDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorsLogArrayResultDTO;

public interface ILogisticReportManager {
	
	OrderStateTypeArrayResultDTO getShowableOrderStateTypes();
	DirectOrderStateTypeArrayResultDTO getShowableDirectOrderStateTypes();
	OrderStateTypeArrayResultDTO getOrderStateTypes();
	DirectOrderStateTypeArrayResultDTO getDirectOrderStateTypes();
	DirectOrderStateTypeArrayResultDTO getVendorSelectableDirectOrderStateTypes();
	LocationLogArrayResultDTO getLocationsOrderByNameAndWarehouse();	
	LocationsLogArrayResultDTO getWareHouseLocationsOrderByName();
	VendorsLogArrayResultDTO findVendorsById(Long vendorId);	
	VendorsLogArrayResultDTO findVendorsByIds(Long[] vendorIds);
	VendorsLogArrayResultDTO findVendorsByIdsCourier(Long[] vendorIds);
	VendorsLogArrayResultDTO findVendorsByRut(String rut);	
	VendorsLogArrayResultDTO findVendorsByName(String name);	
	LocationsLogArrayResultDTO findLocationsByCode(String code);	
	LocationsLogArrayResultDTO findLocationsByName(String name);	
	LocationsLogArrayResultDTO findLocationsByIds(Long[] localIds);		
	VendorsLogArrayResultDTO findVendorsOfUserByCode(String code, String[] vendorcodes);	
	VendorsLogArrayResultDTO findVendorsOfUserByName(String name, String[] vendorcodes);	
	VendorsLogArrayResultDTO findVendorsOfUserByInternalCode(String code, String[] vendorcodess);
	VendorsLogArrayResultDTO findVendorsOfUserByCodeCourier(String code, String[] vendorcodes);	
	VendorsLogArrayResultDTO findVendorsOfUserByNameCourier(String name, String[] vendorcodes);	
	VendorsLogArrayResultDTO findVendorsOfUserByInternalCodeCourier(String code, String[] vendorcodes);
	VendorsLogArrayResultDTO findVendorsByIds(Long[] vendorIds, int pagenumber, int rows, boolean getPageInfo, boolean isPaginated, OrderCriteriaDTO[] order);
	LocationsLogArrayResultDTO findLocationByIds(Long[] locationIds, int pagenumber, int rows, boolean getPageInfo, boolean isPaginated, OrderCriteriaDTO[] order);
	Set getAllVendorIds();
	Set getAllLocalIds();	
	VendorsLogArrayResultDTO findVendorsLogLikeNameAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids);
	VendorsLogArrayResultDTO findVendorsLogLikeCodeAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids);
	LocationsLogArrayResultDTO findLocalsLikeNameAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids);
	LocationsLogArrayResultDTO findLocalsLikeCodeAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids);
	VendorsLogArrayResultDTO findVendorsLogLikeNameNotAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids);
	VendorsLogArrayResultDTO findVendorsLogLikeCodeNotAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids);
	LocationsLogArrayResultDTO findLocalsLikeNameNotAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids);
	LocationsLogArrayResultDTO findLocalsLikeCodeNotAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids);	
	DepartmentArrayResultDTO getDepartments();
	DepartmentArrayResultDTO findDepartmentsByCode(String code);
	DepartmentArrayResultDTO findDepartmentsByName(String name);
	String getNextAvailableVendorCode() throws ServiceException;
	VendorItemReportResultDTO getVendorItemsByFilter(VendorItemReportInitParamDTO initParams, boolean isByFilter);
	VendorsLogArrayResultDTO getDomesticVendorsOrderByName();
	VendorsLogArrayResultDTO findDomesticVendorsByName(String name);
	VendorsLogArrayResultDTO findDomesticVendorsByRut(String rut);
	VendorsLogArrayResultDTO findDomesticVendorsByInternalCode(String code);
	VendorsLogArrayResultDTO getNotDomesticVendorsOrderByName();
	VendorsLogArrayResultDTO findNotDomesticVendorsByName(String name);
	VendorsLogArrayResultDTO findNotDomesticVendorsByRut(String rut);
	VendorsLogArrayResultDTO findNotDomesticVendorsByInternalCode(String code);
	VendorsLogArrayResultDTO getVendorsOrderByName();
	DODeliveryStateTypeArrayResultDTO getClosedDODeliveryStateTypes();
	DODeliveryStateTypeArrayResultDTO getShowableDODeliveryStateTypes();
	DODeliveryStateTypeArrayResultDTO getModifiableDODeliveryStateTypes();
	
	HiredCourierResultDTO addHiredCourier(HiredCourierDTO hiredCourierDTO);
	HiredCourierResultDTO updateHiredCourier(HiredCourierDTO oldHiredCourierDTO, HiredCourierDTO newHiredCourierDTO);
	HiredCourierArrayResultDTO findAllHiredCouriers();
	HiredCourierResultDTO deleteHiredCourier(HiredCourierDTO[] hiredCourierDTO);
	VendorHiredCourierResultsDTO getAllVendorsHiredCourier();
	CourierResultDTO findAllCouriers();
	
	VendorsLogArrayResultDTO getDomesticVendorsWithCourierByName(String name);
	VendorsLogArrayResultDTO getDomesticVendorsWithOutCourierByName(String name);
	VendorsLogArrayResultDTO getDomesticVendorsWithCourierByCode(String rut);
	VendorsLogArrayResultDTO getDomesticVendorsWithOutCourierByCode(String rut);
	VendorsLogArrayResultDTO getDomesticVendorsWithCourierByInternalCode(String code);
	VendorsLogArrayResultDTO getDomesticVendorsWithOutCourierByInternalCode(String code);
	VendorsLogArrayResultDTO getDomesticVendorsWithCourier();
	VendorsLogArrayResultDTO getDomesticVendorsWithOutCourier();
	
	BaseResultDTO addCommuneCourier(CommuneCourierDTO communeCourier);
	BaseResultDTO updateCommuneCourier(CommuneCourierDTO communeCourier);
	BaseResultDTO deleteCommuneCourier(CommuneCourierDTO[] communeCourier);
	CommuneCourierArrayResultDTO getAllCommuneCouriers();
	
	VendorsLogArrayResultDTO findVendorsByIdsCourier(Long[] vendorIds, int pagenumber, int rows, boolean getPageInfo, boolean isPaginated, OrderCriteriaDTO[] order);
	
	CourierInformationResultDTO getCourierInformation(String vendorcode);
	HiredCourierArrayResultDTO getHiredCouriersByVendorId(String vendorcode);
	CommuneCourierArrayResultDTO getCommunesByCourierId(Long courierid);
	VendorAddressResultDTO getVendorAddressByHiredCourierId(String vendorcode, Long courierid);
	BaseResultDTO updateVendorAddress(VendorAddressInitParamDTO initParamDTO);
	BaseResultDTO addVendorAddress(VendorAddressInitParamDTO initParamDTO);
	
	InvoiceVendorRollOutArrayResultDTO getInvoiceVendorRollOutReport(InvoiceVendorRollOutInitParamDTO initParamDTO);
	BaseResultDTO doAddInvoiceVendorRollOut(AddInvoiceVendorRollOutInitParamDTO initParamDTO, String username);
	BaseResultDTO doUpdateInvoiceVendorRollOut(UpdateInvoiceVendorRollOutInitParamDTO initParamDTO, String username);
	BaseResultDTO doDeleteInvoiceVendorRollOutsByIds(DeleteInvoiceVendorRollOutInitParamDTO initParamDTO);
	
	VendorWithoutInvoiceValidationArrayResultDTO getVendorWithoutInvoiceValidationReport(VendorWithoutInvoiceValidationInitParamDTO initParamDTO, boolean getSummaryInfo);
	VendorWithoutInvoiceValidationUploadResultDTO doUploadVendorsWithoutInvoiceValidation(VendorWithoutInvoiceValidationUploadInitParamDTO initParamDTO);
	
	VendorsLogArrayResultDTO getVendorsLimitByMax(OrderCriteriaDTO[] ordercriteria, Integer maxvalues, Boolean isdomestic);
	VendorsLogArrayResultDTO getVendorsLikeCode(String code, Boolean isdomestic);
	VendorsLogArrayResultDTO getVendorsLikeName(String name, Boolean isdomestic);

	LocationsLogArrayResultDTO getLocationsLimitByMax(OrderCriteriaDTO[] ordercriteria, Integer maxvalues);
	LocationsLogArrayResultDTO getLocationsLikeCode(String code);
	LocationsLogArrayResultDTO getLocationsLikeName(String name);
}
