package bbr.b2b.logistic.dvrdeliveries.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDeliveryState;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDeliveryStateType;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryStateW;

@Stateless(name = "servers/dvrdeliveries/DvrDeliveryStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DvrDeliveryStateServer extends LogisticElementServer<DvrDeliveryState, DvrDeliveryStateW> implements DvrDeliveryStateServerLocal {


	@EJB
	DvrDeliveryServerLocal dvrdeliveryserver;

	@EJB
	DvrDeliveryStateTypeServerLocal dvrdeliverystatetypeserver;

	public DvrDeliveryStateW addDvrDeliveryState(DvrDeliveryStateW dvrdeliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrDeliveryStateW) addElement(dvrdeliverystate);
	}
	public DvrDeliveryStateW[] getDvrDeliveryStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrDeliveryStateW[]) getIdentifiables();
	}
	public DvrDeliveryStateW updateDvrDeliveryState(DvrDeliveryStateW dvrdeliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrDeliveryStateW) updateElement(dvrdeliverystate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DvrDeliveryState entity, DvrDeliveryStateW wrapper) {
		wrapper.setDvrdeliveryid(entity.getDvrdelivery() != null ? new Long(entity.getDvrdelivery().getId()) : new Long(0));
		wrapper.setDvrdeliverystatetypeid(entity.getDvrdeliverystatetype() != null ? new Long(entity.getDvrdeliverystatetype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DvrDeliveryState entity, DvrDeliveryStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDvrdeliveryid() != null && wrapper.getDvrdeliveryid() > 0) { 
			DvrDelivery dvrdelivery = dvrdeliveryserver.getReferenceById(wrapper.getDvrdeliveryid());
			if (dvrdelivery != null) { 
				entity.setDvrdelivery(dvrdelivery);
			}
		}
		if (wrapper.getDvrdeliverystatetypeid() != null && wrapper.getDvrdeliverystatetypeid() > 0) { 
			DvrDeliveryStateType dvrdeliverystatetype = dvrdeliverystatetypeserver.getReferenceById(wrapper.getDvrdeliverystatetypeid());
			if (dvrdeliverystatetype != null) { 
				entity.setDvrdeliverystatetype(dvrdeliverystatetype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DvrDeliveryState";
	}
	@Override
	protected void setEntityname() {
		entityname = "DvrDeliveryState";
	}
}
