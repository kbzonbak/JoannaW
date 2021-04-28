package bbr.b2b.regional.logistic.datings.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.datings.data.wrappers.ResourceBlockingGroupW;
import bbr.b2b.regional.logistic.datings.entities.ResourceBlockingGroup;

public interface IResourceBlockingGroupServer extends IElementServer<ResourceBlockingGroup, ResourceBlockingGroupW> {

	ResourceBlockingGroupW addResourceBlockingGroup(ResourceBlockingGroupW resourceblockinggroup) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ResourceBlockingGroupW[] getResourceBlockingGroups() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ResourceBlockingGroupW updateResourceBlockingGroup(ResourceBlockingGroupW resourceblockinggroup) throws AccessDeniedException, OperationFailedException, NotFoundException;

	ResourceBlockingGroupW[] getResourceBlockingGroupsByDateAndLocation(Date since, Date until, Long locationid)	throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}
