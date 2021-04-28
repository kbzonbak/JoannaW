package bbr.b2b.regional.logistic.deliveries.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.TruckDispatcherW;
import bbr.b2b.regional.logistic.deliveries.entities.TruckDispatcher;


@Stateless(name = "servers/deliveries/TruckDispatcherServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TruckDispatcherServer extends LogisticElementServer<TruckDispatcher, TruckDispatcherW> implements TruckDispatcherServerLocal  {

	public TruckDispatcherW addTruckDispatcher(TruckDispatcherW truckdispatcher) throws AccessDeniedException, OperationFailedException, NotFoundException{
		return (TruckDispatcherW) addElement(truckdispatcher);
	}
	
	public TruckDispatcherW[] getTruckDispatchers() throws AccessDeniedException, OperationFailedException, NotFoundException{
		return (TruckDispatcherW[]) getIdentifiables();
	}
	
	public TruckDispatcherW updateTruckDispatcher(TruckDispatcherW truckdispatcher) throws AccessDeniedException, OperationFailedException, NotFoundException{
		return (TruckDispatcherW) updateElement(truckdispatcher);
	}
	
	@Override
	protected void copyRelationsEntityToWrapper(TruckDispatcher entity, TruckDispatcherW wrapper) {
		
	}

	@Override
	protected void copyRelationsWrapperToEntity(TruckDispatcher entity, TruckDispatcherW wrapper) throws OperationFailedException, NotFoundException {
	
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "TruckDispatcher";
	}
	@Override
	protected void setEntityname() {
		entityname = "TruckDispatcher";
	}
	
}
