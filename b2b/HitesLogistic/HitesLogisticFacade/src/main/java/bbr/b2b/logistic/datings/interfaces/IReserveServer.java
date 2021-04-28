package bbr.b2b.logistic.datings.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.datings.entities.Reserve;
import bbr.b2b.logistic.datings.data.wrappers.ReserveW;

public interface IReserveServer extends IElementServer<Reserve, ReserveW> {

	ReserveW addReserve(ReserveW reserve) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReserveW[] getReserves() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReserveW updateReserve(ReserveW reserve) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
