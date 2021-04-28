package bbr.b2b.regional.logistic.items.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.items.entities.Unit;
import bbr.b2b.regional.logistic.items.data.wrappers.UnitW;

public interface IUnitServer extends IElementServer<Unit, UnitW> {

	UnitW addUnit(UnitW unit) throws AccessDeniedException, OperationFailedException, NotFoundException;
	UnitW[] getUnits() throws AccessDeniedException, OperationFailedException, NotFoundException;
	UnitW updateUnit(UnitW unit) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
