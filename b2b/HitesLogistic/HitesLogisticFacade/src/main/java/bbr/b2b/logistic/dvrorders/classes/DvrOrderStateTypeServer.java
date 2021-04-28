package bbr.b2b.logistic.dvrorders.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderStateType;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateTypeW;

@Stateless(name = "servers/dvrorders/DvrOrderStateTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DvrOrderStateTypeServer extends LogisticElementServer<DvrOrderStateType, DvrOrderStateTypeW> implements DvrOrderStateTypeServerLocal {


	public DvrOrderStateTypeW addDvrOrderStateType(DvrOrderStateTypeW dvrorderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderStateTypeW) addElement(dvrorderstatetype);
	}
	public DvrOrderStateTypeW[] getDvrOrderStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderStateTypeW[]) getIdentifiables();
	}
	public DvrOrderStateTypeW updateDvrOrderStateType(DvrOrderStateTypeW dvrorderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderStateTypeW) updateElement(dvrorderstatetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DvrOrderStateType entity, DvrOrderStateTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(DvrOrderStateType entity, DvrOrderStateTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DvrOrderStateType";
	}
	@Override
	protected void setEntityname() {
		entityname = "DvrOrderStateType";
	}
}
