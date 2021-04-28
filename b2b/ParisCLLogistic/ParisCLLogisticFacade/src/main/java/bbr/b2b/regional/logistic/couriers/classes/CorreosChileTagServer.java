package bbr.b2b.regional.logistic.couriers.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CorreosChileTagW;
import bbr.b2b.regional.logistic.couriers.entities.CorreosChileTag;
import bbr.b2b.regional.logistic.couriers.entities.RescheduleReason;
import bbr.b2b.regional.logistic.couriers.report.classes.CorreosChileCSVDTO;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;

@Stateless(name = "servers/couriers/CorreosChileTagServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CorreosChileTagServer extends LogisticElementServer<CorreosChileTag, CorreosChileTagW> implements CorreosChileTagServerLocal {
	
	@EJB
	DODeliveryServerLocal deliveryServerLocal;

	@EJB
	RescheduleReasonServerLocal rescheduleReasonServerLocal;
	
	public CorreosChileTagW addCorreosChileTag(CorreosChileTagW correosChileTag) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CorreosChileTagW) addElement(correosChileTag);
	}
	public CorreosChileTagW[] getCorreosChileTags() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CorreosChileTagW[]) getIdentifiables();
	}
	public CorreosChileTagW updateCorreosChileTag(CorreosChileTagW correosChileTag) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CorreosChileTagW) updateElement(correosChileTag);
	}

	@Override
	protected void copyRelationsEntityToWrapper(CorreosChileTag entity, CorreosChileTagW wrapper) {
		wrapper.setDodeliveryid(entity.getDodelivery() != null ? new Long(entity.getDodelivery().getId()) : new Long(0));
		wrapper.setReschedulereasonid(entity.getReschedulereason() != null ? new Long(entity.getReschedulereason().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(CorreosChileTag entity, CorreosChileTagW wrapper) throws OperationFailedException, NotFoundException {
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
	
	public CorreosChileCSVDTO getCorreosChileTagInformation(Long dodeliveryid){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.CorreosChileTag.getCorreosChileTagInformation");
		query.setLong("dodeliveryid", dodeliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(CorreosChileCSVDTO.class));
		CorreosChileCSVDTO result =(CorreosChileCSVDTO) query.uniqueResult();
		return result;	
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "CorreosChileTag";
	}
	@Override
	protected void setEntityname() {
		entityname = "CorreosChileTag";
	}
}
