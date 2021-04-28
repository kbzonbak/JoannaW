package bbr.b2b.regional.logistic.couriers.interfaces;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.HiredCourierW;
import bbr.b2b.regional.logistic.couriers.entities.HiredCourier;
import bbr.b2b.regional.logistic.couriers.entities.HiredCourierId;
import bbr.b2b.regional.logistic.couriers.report.classes.VendorHiredCourierDTO;

public interface IHiredCourierServer extends IGenericServer<HiredCourier, HiredCourierId, HiredCourierW> {
	Long getByVendor(Long vendorid);
	HiredCourierW addHiredCourier(HiredCourierW hiredCourier) throws AccessDeniedException, OperationFailedException, NotFoundException;
	HiredCourierW[] getHiredCouriers() throws AccessDeniedException, OperationFailedException, NotFoundException;
	HiredCourierW updateHiredCourier(HiredCourierW hiredCourier) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int deleteHiredCourier(PropertyInfoDTO[] pven) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorHiredCourierDTO[] getAllVendorsHiredCourier();
}
