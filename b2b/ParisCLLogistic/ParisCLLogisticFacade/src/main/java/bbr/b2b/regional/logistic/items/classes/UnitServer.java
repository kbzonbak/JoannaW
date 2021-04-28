package bbr.b2b.regional.logistic.items.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.items.entities.Unit;
import bbr.b2b.regional.logistic.items.data.wrappers.UnitW;

@Stateless(name = "servers/items/UnitServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UnitServer extends LogisticElementServer<Unit, UnitW> implements UnitServerLocal {


	public UnitW addUnit(UnitW unit) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (UnitW) addElement(unit);
	}
	public UnitW[] getUnits() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (UnitW[]) getIdentifiables();
	}
	public UnitW updateUnit(UnitW unit) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (UnitW) updateElement(unit);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Unit entity, UnitW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Unit entity, UnitW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Unit";
	}
	@Override
	protected void setEntityname() {
		entityname = "Unit";
	}
}
