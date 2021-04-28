package bbr.b2b.regional.logistic.soa.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.soa.data.classes.SOAStateTypeW;
import bbr.b2b.regional.logistic.soa.entities.SOAStateType;

@Stateless(name = "servers/SOAStateTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SOAStateTypeServer extends LogisticElementServer<SOAStateType, SOAStateTypeW> implements SOAStateTypeServerLocal {

	public SOAStateTypeW addSOAStateType(SOAStateTypeW soastatetype) throws OperationFailedException, NotFoundException {
		return (SOAStateTypeW) addElement(soastatetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(SOAStateType entity, SOAStateTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void copyRelationsWrapperToEntity(SOAStateType entity, SOAStateTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	public SOAStateTypeW[] getSOAStateTypes() throws OperationFailedException, NotFoundException {
		return (SOAStateTypeW[]) getIdentifiables();
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "Tipo de Estado SOA";
	}

	@Override
	protected void setEntityname() {
		entityname = "SOAStateType";
	}

	public SOAStateTypeW updateSOAStateType(SOAStateTypeW soastatetype) throws OperationFailedException, NotFoundException {
		return (SOAStateTypeW) updateElement(soastatetype);
	}
}
