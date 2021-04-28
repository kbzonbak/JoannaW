package bbr.b2b.logistic.datings.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.datings.entities.ResourceBlocking;
import bbr.b2b.logistic.datings.data.wrappers.ResourceBlockingW;

public interface IResourceBlockingServer extends IElementServer<ResourceBlocking, ResourceBlockingW> {

	ResourceBlockingW addResourceBlocking(ResourceBlockingW resourceblocking) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ResourceBlockingW[] getResourceBlockings() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ResourceBlockingW updateResourceBlocking(ResourceBlockingW resourceblocking) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ResourceBlockingW[] getResourceBlockingsByDateAndLocation(LocalDateTime since, LocalDateTime until, Long locationid) throws NotFoundException, OperationFailedException; 
}
