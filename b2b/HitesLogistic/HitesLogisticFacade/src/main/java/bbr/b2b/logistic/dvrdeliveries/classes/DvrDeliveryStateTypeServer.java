package bbr.b2b.logistic.dvrdeliveries.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDeliveryStateType;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryStateTypeW;

@Stateless(name = "servers/dvrdeliveries/DvrDeliveryStateTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DvrDeliveryStateTypeServer extends LogisticElementServer<DvrDeliveryStateType, DvrDeliveryStateTypeW> implements DvrDeliveryStateTypeServerLocal {


	public DvrDeliveryStateTypeW addDvrDeliveryStateType(DvrDeliveryStateTypeW dvrdeliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrDeliveryStateTypeW) addElement(dvrdeliverystatetype);
	}
	public DvrDeliveryStateTypeW[] getDvrDeliveryStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrDeliveryStateTypeW[]) getIdentifiables();
	}
	public DvrDeliveryStateTypeW updateDvrDeliveryStateType(DvrDeliveryStateTypeW dvrdeliverystatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrDeliveryStateTypeW) updateElement(dvrdeliverystatetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DvrDeliveryStateType entity, DvrDeliveryStateTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(DvrDeliveryStateType entity, DvrDeliveryStateTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DvrDeliveryStateType";
	}
	@Override
	protected void setEntityname() {
		entityname = "DvrDeliveryStateType";
	}
}
