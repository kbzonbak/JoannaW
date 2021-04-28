package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.entities.Homologation;
import bbr.b2b.logistic.customer.data.wrappers.HomologationW;

public interface IHomologationServer extends IElementServer<Homologation, HomologationW> {

	HomologationW addHomologation(HomologationW homologation) throws AccessDeniedException, OperationFailedException, NotFoundException;
	HomologationW[] getHomologation() throws AccessDeniedException, OperationFailedException, NotFoundException;
	HomologationW updateHomologation(HomologationW homologation) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
