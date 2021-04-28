package bbr.b2b.regional.logistic.buyorders.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.VeVUpdateTypeW;
import bbr.b2b.regional.logistic.buyorders.entities.VeVUpdateType;

@Stateless(name = "servers/buyorders/VeVUpdateTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VeVUpdateTypeServer extends LogisticElementServer<VeVUpdateType, VeVUpdateTypeW> implements VeVUpdateTypeServerLocal {


	public VeVUpdateTypeW addVeVUpdateType(VeVUpdateTypeW vevupdatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VeVUpdateTypeW) addElement(vevupdatetype);
	}
	public VeVUpdateTypeW[] getVeVUpdateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VeVUpdateTypeW[]) getIdentifiables();
	}
	public VeVUpdateTypeW updateVeVUpdateType(VeVUpdateTypeW vevupdatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VeVUpdateTypeW) updateElement(vevupdatetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(VeVUpdateType entity, VeVUpdateTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(VeVUpdateType entity, VeVUpdateTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "VeVUpdateType";
	}
	@Override
	protected void setEntityname() {
		entityname = "VeVUpdateType";
	}
}
