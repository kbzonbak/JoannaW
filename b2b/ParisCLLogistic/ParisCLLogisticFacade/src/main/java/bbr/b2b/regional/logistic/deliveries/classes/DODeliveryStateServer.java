package bbr.b2b.regional.logistic.deliveries.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryStateW;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryState;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryStateType;

@Stateless(name = "servers/deliveries/DODeliveryStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DODeliveryStateServer extends LogisticElementServer<DODeliveryState, DODeliveryStateW> implements DODeliveryStateServerLocal {


	@EJB
	DODeliveryServerLocal deliveryserver;

	@EJB
	DODeliveryStateTypeServerLocal deliverystatetypeserver;

	public DODeliveryStateW addDODeliveryState(DODeliveryStateW dodeliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DODeliveryStateW) addElement(dodeliverystate);
	}
	public DODeliveryStateW[] getDODeliveryStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DODeliveryStateW[]) getIdentifiables();
	}
	public DODeliveryStateW updateDODeliveryState(DODeliveryStateW dodeliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DODeliveryStateW) updateElement(dodeliverystate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DODeliveryState entity, DODeliveryStateW wrapper) {
		wrapper.setDeliveryid(entity.getDelivery() != null ? new Long(entity.getDelivery().getId()) : new Long(0));
		wrapper.setDeliverystatetypeid(entity.getDeliverystatetype() != null ? new Long(entity.getDeliverystatetype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DODeliveryState entity, DODeliveryStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDeliveryid() != null && wrapper.getDeliveryid() > 0) { 
			DODelivery delivery = deliveryserver.getReferenceById(wrapper.getDeliveryid());
			if (delivery != null) { 
				entity.setDelivery(delivery);
			}
		}
		if (wrapper.getDeliverystatetypeid() != null && wrapper.getDeliverystatetypeid() > 0) { 
			DODeliveryStateType deliverystatetype = deliverystatetypeserver.getReferenceById(wrapper.getDeliverystatetypeid());
			if (deliverystatetype != null) { 
				entity.setDeliverystatetype(deliverystatetype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DODeliveryState";
	}
	@Override
	protected void setEntityname() {
		entityname = "DODeliveryState";
	}
}
