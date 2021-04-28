package bbr.b2b.regional.logistic.deliveries.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryStateTypeW;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryStateType;
import bbr.b2b.regional.logistic.deliveries.entities.DOReceptionType;

@Stateless(name = "servers/deliveries/DODeliveryStateTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DODeliveryStateTypeServer extends LogisticElementServer<DODeliveryStateType, DODeliveryStateTypeW> implements DODeliveryStateTypeServerLocal {


	@EJB
	DOReceptionTypeServerLocal receptiontypeserver;

	public DODeliveryStateTypeW addDODeliveryStateType(DODeliveryStateTypeW dodeliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DODeliveryStateTypeW) addElement(dodeliverystatetype);
	}
	public DODeliveryStateTypeW[] getDODeliveryStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DODeliveryStateTypeW[]) getIdentifiables();
	}
	public DODeliveryStateTypeW updateDODeliveryStateType(DODeliveryStateTypeW dodeliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DODeliveryStateTypeW) updateElement(dodeliverystatetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DODeliveryStateType entity, DODeliveryStateTypeW wrapper) {
		wrapper.setReceptiontypeid(entity.getReceptiontype() != null ? new Long(entity.getReceptiontype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DODeliveryStateType entity, DODeliveryStateTypeW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getReceptiontypeid() != null && wrapper.getReceptiontypeid() > 0) { 
			DOReceptionType receptiontype = receptiontypeserver.getReferenceById(wrapper.getReceptiontypeid());
			if (receptiontype != null) { 
				entity.setReceptiontype(receptiontype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DODeliveryStateType";
	}
	@Override
	protected void setEntityname() {
		entityname = "DODeliveryStateType";
	}
}
