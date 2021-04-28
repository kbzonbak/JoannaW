package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionW;
import bbr.b2b.logistic.customer.entities.Reception;

public interface IReceptionServer extends IElementServer<Reception, ReceptionW> {

	ReceptionW addReception(ReceptionW reception) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReceptionW[] getReceptions() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReceptionW updateReception(ReceptionW reception) throws AccessDeniedException, OperationFailedException, NotFoundException;
	public ReceptionW[] getReceptionsToSendSoa(Long buyerid, Long vendorid, Long soastateid) throws OperationFailedException, NotFoundException;
}
