package bbr.b2b.logistic.ddcdeliveries.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryStateTypeW;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDeliveryStateType;

@Stateless(name = "servers/ddcdeliveries/DdcDeliveryStateTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DdcDeliveryStateTypeServer extends LogisticElementServer<DdcDeliveryStateType, DdcDeliveryStateTypeW> implements DdcDeliveryStateTypeServerLocal {


	public DdcDeliveryStateTypeW addDdcDeliveryStateType(DdcDeliveryStateTypeW ddcdeliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcDeliveryStateTypeW) addElement(ddcdeliverystatetype);
	}
	public DdcDeliveryStateTypeW[] getDdcDeliveryStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcDeliveryStateTypeW[]) getIdentifiables();
	}
	public DdcDeliveryStateTypeW updateDdcDeliveryStateType(DdcDeliveryStateTypeW ddcdeliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcDeliveryStateTypeW) updateElement(ddcdeliverystatetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DdcDeliveryStateType entity, DdcDeliveryStateTypeW wrapper) {
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DdcDeliveryStateType entity, DdcDeliveryStateTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DdcDeliveryStateType";
	}
	@Override
	protected void setEntityname() {
		entityname = "DdcDeliveryStateType";
	}
}
