package bbr.b2b.logistic.ddcorders.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderStateTypeW;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderStateType;

@Stateless(name = "servers/ddcorders/DdcOrderStateTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DdcOrderStateTypeServer extends LogisticElementServer<DdcOrderStateType, DdcOrderStateTypeW> implements DdcOrderStateTypeServerLocal {

	public DdcOrderStateTypeW addDdcOrderStateType(DdcOrderStateTypeW ddcorderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderStateTypeW) addElement(ddcorderstatetype);
	}
	public DdcOrderStateTypeW[] getDdcOrderStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderStateTypeW[]) getIdentifiables();
	}
	public DdcOrderStateTypeW updateDdcOrderStateType(DdcOrderStateTypeW ddcorderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderStateTypeW) updateElement(ddcorderstatetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DdcOrderStateType entity, DdcOrderStateTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(DdcOrderStateType entity, DdcOrderStateTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DdcOrderStateType";
	}
	@Override
	protected void setEntityname() {
		entityname = "DdcOrderStateType";
	}
}
