package bbr.b2b.regional.logistic.deliveries.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateW;
import bbr.b2b.regional.logistic.deliveries.entities.Delivery;
import bbr.b2b.regional.logistic.deliveries.entities.DeliveryState;
import bbr.b2b.regional.logistic.deliveries.entities.DeliveryStateType;

@Stateless(name = "servers/deliveries/DeliveryStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DeliveryStateServer extends LogisticElementServer<DeliveryState, DeliveryStateW> implements DeliveryStateServerLocal {


	@EJB
	DeliveryServerLocal deliveryserver;

	@EJB
	DeliveryStateTypeServerLocal deliverystatetypeserver;

	public DeliveryStateW addDeliveryState(DeliveryStateW deliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DeliveryStateW) addElement(deliverystate);
	}
	public DeliveryStateW[] getDeliveryStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DeliveryStateW[]) getIdentifiables();
	}
	public DeliveryStateW updateDeliveryState(DeliveryStateW deliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DeliveryStateW) updateElement(deliverystate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DeliveryState entity, DeliveryStateW wrapper) {
		wrapper.setDeliveryid(entity.getDelivery() != null ? new Long(entity.getDelivery().getId()) : new Long(0));
		wrapper.setDeliverystatetypeid(entity.getDeliverystatetype() != null ? new Long(entity.getDeliverystatetype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DeliveryState entity, DeliveryStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDeliveryid() != null && wrapper.getDeliveryid() > 0) { 
			Delivery delivery = deliveryserver.getReferenceById(wrapper.getDeliveryid());
			if (delivery != null) { 
				entity.setDelivery(delivery);
			}
		}
		if (wrapper.getDeliverystatetypeid() != null && wrapper.getDeliverystatetypeid() > 0) { 
			DeliveryStateType deliverystatetype = deliverystatetypeserver.getReferenceById(wrapper.getDeliverystatetypeid());
			if (deliverystatetype != null) { 
				entity.setDeliverystatetype(deliverystatetype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DeliveryState";
	}
	@Override
	protected void setEntityname() {
		entityname = "DeliveryState";
	}
}
