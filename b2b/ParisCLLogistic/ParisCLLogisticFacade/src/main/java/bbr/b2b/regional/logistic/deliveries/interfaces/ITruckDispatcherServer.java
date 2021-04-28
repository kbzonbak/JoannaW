package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.TruckDispatcherW;
import bbr.b2b.regional.logistic.deliveries.entities.TruckDispatcher;

public interface ITruckDispatcherServer  extends IElementServer<TruckDispatcher, TruckDispatcherW> {

	TruckDispatcherW addTruckDispatcher(TruckDispatcherW truckdispatcher) throws AccessDeniedException, OperationFailedException, NotFoundException;
	TruckDispatcherW[] getTruckDispatchers() throws AccessDeniedException, OperationFailedException, NotFoundException;
	TruckDispatcherW updateTruckDispatcher(TruckDispatcherW truckdispatcher) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
