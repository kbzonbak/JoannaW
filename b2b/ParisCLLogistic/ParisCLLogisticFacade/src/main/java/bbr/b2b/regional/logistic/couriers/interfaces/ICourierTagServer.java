package bbr.b2b.regional.logistic.couriers.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierTagW;
import bbr.b2b.regional.logistic.couriers.entities.CourierTag;

public interface ICourierTagServer extends IElementServer<CourierTag, CourierTagW> {
	
	CourierTagW updateCouriertag(CourierTagW courierTag) throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierTagW addCouriertag(CourierTagW courierTag) throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierTagW[] getCouriertags() throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}
