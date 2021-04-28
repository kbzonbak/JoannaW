package bbr.b2b.logistic.buyorders.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.buyorders.entities.Responsable;
import bbr.b2b.logistic.buyorders.data.wrappers.ResponsableW;

@Stateless(name = "servers/buyorders/ResponsableServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ResponsableServer extends LogisticElementServer<Responsable, ResponsableW> implements ResponsableServerLocal {


	public ResponsableW addResponsable(ResponsableW responsable) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ResponsableW) addElement(responsable);
	}
	public ResponsableW[] getResponsables() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ResponsableW[]) getIdentifiables();
	}
	public ResponsableW updateResponsable(ResponsableW responsable) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ResponsableW) updateElement(responsable);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Responsable entity, ResponsableW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Responsable entity, ResponsableW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Responsable";
	}
	@Override
	protected void setEntityname() {
		entityname = "Responsable";
	}
}
