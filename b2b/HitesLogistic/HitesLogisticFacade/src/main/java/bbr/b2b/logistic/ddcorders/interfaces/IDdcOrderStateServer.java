package bbr.b2b.logistic.ddcorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderStateW;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderState;

public interface IDdcOrderStateServer extends IElementServer<DdcOrderState, DdcOrderStateW> {

	DdcOrderStateW addDdcOrderState(DdcOrderStateW ddcorderstate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcOrderStateW[] getDdcOrderStates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcOrderStateW updateDdcOrderState(DdcOrderStateW ddcorderstate) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
