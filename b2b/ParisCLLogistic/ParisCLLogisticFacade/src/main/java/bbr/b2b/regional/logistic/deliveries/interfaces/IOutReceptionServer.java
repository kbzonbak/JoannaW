package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.OutReception;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OutReceptionW;

public interface IOutReceptionServer extends IElementServer<OutReception, OutReceptionW> {

	OutReceptionW addOutReception(OutReceptionW outreception) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OutReceptionW[] getOutReceptions() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OutReceptionW updateOutReception(OutReceptionW outreception) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
