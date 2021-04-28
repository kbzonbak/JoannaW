package bbr.b2b.regional.logistic.couriers.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierStateW;
import bbr.b2b.regional.logistic.couriers.entities.CourierState;

public interface ICourierStateServer extends IElementServer<CourierState, CourierStateW> {
	
	CourierStateW addCourierState(CourierStateW courierState) throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierStateW[] getCourierStates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierStateW updateCourierState(CourierStateW courierState) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}
