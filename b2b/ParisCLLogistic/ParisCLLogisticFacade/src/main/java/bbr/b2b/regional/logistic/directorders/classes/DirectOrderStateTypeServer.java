package bbr.b2b.regional.logistic.directorders.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderStateType;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateTypeW;

@Stateless(name = "servers/directorders/DirectOrderStateTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DirectOrderStateTypeServer extends LogisticElementServer<DirectOrderStateType, DirectOrderStateTypeW> implements DirectOrderStateTypeServerLocal {


	public DirectOrderStateTypeW addDirectOrderStateType(DirectOrderStateTypeW directorderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DirectOrderStateTypeW) addElement(directorderstatetype);
	}
	public DirectOrderStateTypeW[] getDirectOrderStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DirectOrderStateTypeW[]) getIdentifiables();
	}
	public DirectOrderStateTypeW updateDirectOrderStateType(DirectOrderStateTypeW directorderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DirectOrderStateTypeW) updateElement(directorderstatetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DirectOrderStateType entity, DirectOrderStateTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(DirectOrderStateType entity, DirectOrderStateTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DirectOrderStateType";
	}
	@Override
	protected void setEntityname() {
		entityname = "DirectOrderStateType";
	}
}
