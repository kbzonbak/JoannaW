package bbr.b2b.logistic.ddcorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderStateTypeW;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderStateType;

public interface IDdcOrderStateTypeServer extends IElementServer<DdcOrderStateType, DdcOrderStateTypeW> {

	DdcOrderStateTypeW addDdcOrderStateType(DdcOrderStateTypeW ddcorderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcOrderStateTypeW[] getDdcOrderStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcOrderStateTypeW updateDdcOrderStateType(DdcOrderStateTypeW ddcorderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
