package bbr.b2b.regional.logistic.vendors.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorLogDTO;

public interface IVendorServer extends IElementServer<Vendor, VendorW> {

	VendorW addVendor(VendorW vendor) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorW[] getVendors() throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorW updateVendor(VendorW vendor) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorLogDTO[] getVendorsByIds(Long[] vendorIds) throws ServiceException;
	VendorW[] getVendorsByIds(Long[] pvkeys, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException;
	VendorLogDTO[] getVendorsByIdsCourier(Long[] pvkeys, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException;
	int countVendorsOfUser(Long[] vdkeys) throws ServiceException;
	VendorLogDTO[] findVendorsOfUserByCode(String code, Long[] vendorIds) throws ServiceException;
	VendorLogDTO[] findVendorsOfUserByName(String name, Long[] vendorIds) throws ServiceException;
	VendorLogDTO[] findVendorsOfUserByInternalCode(String code, Long[] vendorIds) throws ServiceException;
	VendorLogDTO[] findVendorsLogLikeNameAssigned(String name, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException;
	int countVendorsLogLikeNameAssigned(String name, Long[] assignedids) throws ServiceException;
	VendorLogDTO[] findVendorsLogLikeNameNotAssigned(String name, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException;
	int countVendorsLogLikeNameNotAssigned(String name, Long[] assignedids) throws ServiceException;
	VendorLogDTO[] findVendorsLogLikeCodeAssigned(String code, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException;
	int countVendorsLogLikeCodeAssigned(String code, Long[] assignedids) throws ServiceException;
	VendorLogDTO[] findVendorsLogLikeCodeNotAssigned(String code, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException ;
	int countVendorsLogLikeCodeNotAssigned(String code, Long[] assignedids) throws ServiceException;	
	VendorLogDTO[] getVendorsByLocationWithDating(Long locationid, Date since, Date until) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorLogDTO[] getVendorsByExpirationRange(Date since, Date until) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorLogDTO[] getDomesticVendorsOrderByName();
	VendorLogDTO[] findDomesticVendorsByName(String name);
	VendorLogDTO[] findDomesticVendorsByRut(String rut);
	VendorLogDTO[] findDomesticVendorsByInternalCode(String code);
	VendorLogDTO[] getNotDomesticVendorsOrderByName();
	VendorLogDTO[] findNotDomesticVendorsByName(String name);
	VendorLogDTO[] findNotDomesticVendorsByRut(String rut);
	VendorLogDTO[] findNotDomesticVendorsByInternalCode(String code);
	VendorW[] getDomesticVendorsWithCourierByName(String name) throws ServiceException;
	VendorW[] getDomesticVendorsWithOutCourierByName(String name) throws ServiceException;
	VendorW[] getDomesticVendorsWithCourierByCode(String rut) throws ServiceException;
	VendorW[] getDomesticVendorsWithOutCourierByCode(String rut) throws ServiceException;
	VendorW[] getDomesticVendorsWithCourierByInternalCode(String code) throws ServiceException;
	VendorW[] getDomesticVendorsWithOutCourierByInternalCode(String code) throws ServiceException;
	VendorLogDTO[] getDomesticVendorsWithOutCourier() throws ServiceException;
	VendorLogDTO[] getDomesticVendorsWithCourier() throws ServiceException;
	VendorLogDTO[] findVendorsOfUserByNameCourier(String name, Long[] vendorIds) throws ServiceException;
	VendorLogDTO[] findVendorsOfUserByInternalCodeCourier(String code, Long[] vendorIds) throws ServiceException;
	VendorLogDTO[] findVendorsOfUserByCodeCourier(String code, Long[] vendorIds) throws ServiceException;
	VendorLogDTO[] getVendorsByIdsCourier(Long[] vendorIds) throws ServiceException;
	Long[] getFirstVeVCDVendorsByDate(Date since, Date until);
	Long[] getFirstVeVPDVendorsByDate(Date since, Date until);
	int getCountVendors();
	
}
