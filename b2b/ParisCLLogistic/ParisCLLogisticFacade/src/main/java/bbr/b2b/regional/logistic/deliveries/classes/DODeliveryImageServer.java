package bbr.b2b.regional.logistic.deliveries.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryImageW;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryImage;
import bbr.b2b.regional.logistic.deliveries.entities.TruckDispatcher;

@Stateless(name = "servers/deliveries/DODeliveryImageServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DODeliveryImageServer extends LogisticElementServer<DODeliveryImage, DODeliveryImageW> implements DODeliveryImageServerLocal {

	@EJB
	TruckDispatcherServerLocal truckdispatcherserver;
	
	@EJB
	DODeliveryServerLocal dodeliveryserver;
	
	public DODeliveryImageW addDODeliveryImage(DODeliveryImageW dodeliveryimage) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DODeliveryImageW) addElement(dodeliveryimage);
	}

	public DODeliveryImageW[] getDODeliverysImage() throws AccessDeniedException, OperationFailedException, NotFoundException{
		return (DODeliveryImageW[]) getIdentifiables();
	}
	
	public DODeliveryImageW updateDODeliveryImage(DODeliveryImageW dodeliveryimage) throws AccessDeniedException, OperationFailedException, NotFoundException{
		return (DODeliveryImageW) updateElement(dodeliveryimage);
	}

	
	
	@Override
	protected void copyRelationsEntityToWrapper(DODeliveryImage entity, DODeliveryImageW wrapper) {
		wrapper.setTruckdispatcherid(entity.getTruckdispatcher() != null ? new Long(entity.getTruckdispatcher().getId()) : new Long(0));
		wrapper.setDodeliveryid(entity.getDodelivery() != null ? new Long(entity.getDodelivery().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DODeliveryImage entity, DODeliveryImageW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getTruckdispatcherid() != null && wrapper.getTruckdispatcherid() > 0) { 
			TruckDispatcher truckdispatcher = truckdispatcherserver.getReferenceById(wrapper.getTruckdispatcherid());
			if (truckdispatcher != null) { 
				entity.setTruckdispatcher(truckdispatcher);
			}
		}
		if(wrapper.getDodeliveryid() != null && wrapper.getDodeliveryid() > 0){
			DODelivery dodelivery = dodeliveryserver.getReferenceById(wrapper.getDodeliveryid());
			if(dodelivery != null){
				entity.setDodelivery(dodelivery);
			}
		}

	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DODeliveryImage";
	}
	@Override
	protected void setEntityname() {
		entityname = "DODeliveryImage";
	}
}
