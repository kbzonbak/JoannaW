package bbr.b2b.regional.logistic.directorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderState;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateW;

public interface IDirectOrderStateServer extends IElementServer<DirectOrderState, DirectOrderStateW> {

	DirectOrderStateW addDirectOrderState(DirectOrderStateW directorderstate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DirectOrderStateW[] getDirectOrderStates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DirectOrderStateW updateDirectOrderState(DirectOrderStateW directorderstate) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
