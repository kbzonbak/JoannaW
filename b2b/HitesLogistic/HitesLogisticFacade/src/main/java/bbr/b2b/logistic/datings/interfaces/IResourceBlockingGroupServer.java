package bbr.b2b.logistic.datings.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.datings.entities.ResourceBlockingGroup;
import bbr.b2b.logistic.datings.data.wrappers.ResourceBlockingGroupW;

public interface IResourceBlockingGroupServer extends IElementServer<ResourceBlockingGroup, ResourceBlockingGroupW> {

	ResourceBlockingGroupW addResourceBlockingGroup(ResourceBlockingGroupW resourceblockinggroup) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ResourceBlockingGroupW[] getResourceBlockingGroups() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ResourceBlockingGroupW updateResourceBlockingGroup(ResourceBlockingGroupW resourceblockinggroup) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ResourceBlockingGroupW[] getResourceBlockingGroupsByDateAndLocation(LocalDateTime since, LocalDateTime until, Long locationid) throws NotFoundException, OperationFailedException; 
	
}
