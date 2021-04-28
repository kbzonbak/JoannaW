package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.entities.SoaStateType;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateTypeW;

public interface ISoaStateTypeServer extends IElementServer<SoaStateType, SoaStateTypeW> {

	SoaStateTypeW addSoaStateType(SoaStateTypeW soastatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	SoaStateTypeW[] getSoaStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	SoaStateTypeW updateSoaStateType(SoaStateTypeW soastatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
