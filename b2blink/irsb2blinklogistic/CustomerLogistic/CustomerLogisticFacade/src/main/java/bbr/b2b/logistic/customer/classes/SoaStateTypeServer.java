package bbr.b2b.logistic.customer.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateTypeW;
import bbr.b2b.logistic.customer.entities.SoaStateType;

@Stateless(name = "servers/customer/SoaStateTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SoaStateTypeServer extends LogisticElementServer<SoaStateType, SoaStateTypeW> implements SoaStateTypeServerLocal {


	public SoaStateTypeW addSoaStateType(SoaStateTypeW soastatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SoaStateTypeW) addElement(soastatetype);
	}
	public SoaStateTypeW[] getSoaStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SoaStateTypeW[]) getIdentifiables();
	}
	public SoaStateTypeW updateSoaStateType(SoaStateTypeW soastatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SoaStateTypeW) updateElement(soastatetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(SoaStateType entity, SoaStateTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(SoaStateType entity, SoaStateTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "SoaStateType";
	}
	@Override
	protected void setEntityname() {
		entityname = "SoaStateType";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
