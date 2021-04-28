package bbr.b2b.regional.logistic.deliveries.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.DOReceptionType;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DOReceptionTypeW;

@Stateless(name = "servers/deliveries/DOReceptionTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DOReceptionTypeServer extends LogisticElementServer<DOReceptionType, DOReceptionTypeW> implements DOReceptionTypeServerLocal {


	public DOReceptionTypeW addDOReceptionType(DOReceptionTypeW doreceptiontype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DOReceptionTypeW) addElement(doreceptiontype);
	}
	public DOReceptionTypeW[] getDOReceptionTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DOReceptionTypeW[]) getIdentifiables();
	}
	public DOReceptionTypeW updateDOReceptionType(DOReceptionTypeW doreceptiontype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DOReceptionTypeW) updateElement(doreceptiontype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DOReceptionType entity, DOReceptionTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(DOReceptionType entity, DOReceptionTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DOReceptionType";
	}
	@Override
	protected void setEntityname() {
		entityname = "DOReceptionType";
	}
}
