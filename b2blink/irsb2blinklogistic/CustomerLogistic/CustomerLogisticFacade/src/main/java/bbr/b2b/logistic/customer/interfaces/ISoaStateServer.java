package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.entities.SoaState;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateW;

public interface ISoaStateServer extends IElementServer<SoaState, SoaStateW> {

	SoaStateW addSoaState(SoaStateW soastate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	SoaStateW[] getSoaStates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	SoaStateW updateSoaState(SoaStateW soastate) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
