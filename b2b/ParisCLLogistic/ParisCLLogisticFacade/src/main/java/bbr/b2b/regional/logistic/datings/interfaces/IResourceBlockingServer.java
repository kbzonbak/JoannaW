package bbr.b2b.regional.logistic.datings.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.datings.data.wrappers.ResourceBlockingW;
import bbr.b2b.regional.logistic.datings.entities.ResourceBlocking;

public interface IResourceBlockingServer extends IElementServer<ResourceBlocking, ResourceBlockingW> {

	ResourceBlockingW addResourceBlocking(ResourceBlockingW resourceblocking) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ResourceBlockingW[] getResourceBlockings() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ResourceBlockingW updateResourceBlocking(ResourceBlockingW resourceblocking) throws AccessDeniedException, OperationFailedException, NotFoundException;

	ResourceBlockingW[] getResourceBlockingsByDateAndLocation(Date since, Date until, Long locationid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	void doDeleteResourceBlockingsofResourceBlockingGroup(Long blockingroupid) throws NotFoundException, OperationFailedException;
	ResourceBlockingW[] getResourceBlockingsofResourceBlockingGroup(Long blockingroupid) throws NotFoundException, OperationFailedException;

}
