package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.data.wrappers.SoaRecStateW;
import bbr.b2b.logistic.customer.entities.SoaRecState;

public interface ISoaRecStateServer extends IElementServer<SoaRecState, SoaRecStateW> {

	SoaRecStateW addSoaRecState(SoaRecStateW soarecstate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	SoaRecStateW[] getSoaRecStates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	SoaRecStateW updateSoaRecState(SoaRecStateW soarecstate) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
