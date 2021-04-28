package bbr.b2b.regional.logistic.couriers.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierTagW;
import bbr.b2b.regional.logistic.couriers.entities.CourierTag;
import bbr.b2b.regional.logistic.couriers.entities.RescheduleReason;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;

@Stateless(name = "servers/couriers/CourierTagServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CourierTagServer extends LogisticElementServer<CourierTag, CourierTagW> implements CourierTagServerLocal {
	
	@EJB
	DODeliveryServerLocal deliveryServerLocal;
	
	@EJB
	RescheduleReasonServerLocal reschedulereasonServerLocal;
	
	public CourierTagW updateCouriertag(CourierTagW courierTag) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierTagW) updateElement(courierTag);
	}

	public CourierTagW addCouriertag(CourierTagW courierTag) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierTagW) addElement(courierTag);
	}
	
	public CourierTagW[] getCouriertags() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierTagW[]) getIdentifiables();
	}
	
	@Override
	protected void copyRelationsEntityToWrapper(CourierTag entity, CourierTagW wrapper) {
		wrapper.setDodeliveryid(entity.getDodelivery() != null ? new Long(entity.getDodelivery().getId()) : new Long(0));
		wrapper.setReschedulereasonid(entity.getReschedulereason() != null ? new Long(entity.getReschedulereason().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(CourierTag entity, CourierTagW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDodeliveryid() != null && wrapper.getDodeliveryid() > 0) { 
			DODelivery dodelivery = deliveryServerLocal.getReferenceById(wrapper.getDodeliveryid());
			if (dodelivery != null) { 
				entity.setDodelivery(dodelivery);
			}
		}
		if (wrapper.getReschedulereasonid() != null && wrapper.getReschedulereasonid() > 0) { 
			RescheduleReason reschedulereason = reschedulereasonServerLocal.getReferenceById(wrapper.getReschedulereasonid());
			if (reschedulereason != null) { 
				entity.setReschedulereason(reschedulereason);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "CourierTag";
	}
	@Override
	protected void setEntityname() {
		entityname = "CourierTag";
	}
}
