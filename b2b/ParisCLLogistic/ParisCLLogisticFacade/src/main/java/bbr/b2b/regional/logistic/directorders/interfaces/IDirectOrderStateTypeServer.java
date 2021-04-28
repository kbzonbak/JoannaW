package bbr.b2b.regional.logistic.directorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderStateType;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateTypeW;

public interface IDirectOrderStateTypeServer extends IElementServer<DirectOrderStateType, DirectOrderStateTypeW> {

	DirectOrderStateTypeW addDirectOrderStateType(DirectOrderStateTypeW directorderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DirectOrderStateTypeW[] getDirectOrderStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DirectOrderStateTypeW updateDirectOrderStateType(DirectOrderStateTypeW directorderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
