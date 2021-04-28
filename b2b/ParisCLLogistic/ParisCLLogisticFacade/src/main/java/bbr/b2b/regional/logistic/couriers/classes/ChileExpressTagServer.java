package bbr.b2b.regional.logistic.couriers.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.ChileExpressTagW;
import bbr.b2b.regional.logistic.couriers.entities.ChileExpressTag;
import bbr.b2b.regional.logistic.couriers.entities.RescheduleReason;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;

@Stateless(name = "servers/couriers/ChileExpressTagServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ChileExpressTagServer extends LogisticElementServer<ChileExpressTag, ChileExpressTagW> implements ChileExpressTagServerLocal {

	@EJB
	DODeliveryServerLocal deliveryServerLocal;
	
	@EJB
	RescheduleReasonServerLocal rescheduleReasonServerLocal;

	public ChileExpressTagW addChileExpressTag(ChileExpressTagW chileExpressTag) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChileExpressTagW) addElement(chileExpressTag);
	}
	public ChileExpressTagW[] getChileExpressTags() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChileExpressTagW[]) getIdentifiables();
	}
	public ChileExpressTagW updateChileExpressTag(ChileExpressTagW chileExpressTag) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChileExpressTagW) updateElement(chileExpressTag);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ChileExpressTag entity, ChileExpressTagW wrapper) {
		wrapper.setDodeliveryid(entity.getDodelivery() != null ? new Long(entity.getDodelivery().getId()) : new Long(0));
		wrapper.setReschedulereasonid(entity.getReschedulereason() != null ? new Long(entity.getReschedulereason().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(ChileExpressTag entity, ChileExpressTagW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDodeliveryid() != null && wrapper.getDodeliveryid() > 0) { 
			DODelivery dodelivery = deliveryServerLocal.getReferenceById(wrapper.getDodeliveryid());
			if (dodelivery != null) { 
				entity.setDodelivery(dodelivery);
			}
		}
		if (wrapper.getReschedulereasonid() != null && wrapper.getReschedulereasonid() > 0) { 
			RescheduleReason reschedulereason = rescheduleReasonServerLocal.getReferenceById(wrapper.getReschedulereasonid());
			if (reschedulereason != null) { 
				entity.setReschedulereason(reschedulereason);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "ChileExpressTag";
	}
	@Override
	protected void setEntityname() {
		entityname = "ChileExpressTag";
	}
	
}
