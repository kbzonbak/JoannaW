package bbr.b2b.logistic.datings.classes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.datings.entities.ResourceBlockingGroup;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.entities.Location;
import bbr.b2b.logistic.datings.data.wrappers.ResourceBlockingGroupW;

@Stateless(name = "servers/datings/ResourceBlockingGroupServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ResourceBlockingGroupServer extends LogisticElementServer<ResourceBlockingGroup, ResourceBlockingGroupW> implements ResourceBlockingGroupServerLocal {


	@EJB
	LocationServerLocal locationserver;

	public ResourceBlockingGroupW addResourceBlockingGroup(ResourceBlockingGroupW resourceblockinggroup) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ResourceBlockingGroupW) addElement(resourceblockinggroup);
	}
	public ResourceBlockingGroupW[] getResourceBlockingGroups() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ResourceBlockingGroupW[]) getIdentifiables();
	}
	public ResourceBlockingGroupW updateResourceBlockingGroup(ResourceBlockingGroupW resourceblockinggroup) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ResourceBlockingGroupW) updateElement(resourceblockinggroup);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ResourceBlockingGroup entity, ResourceBlockingGroupW wrapper) {
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(ResourceBlockingGroup entity, ResourceBlockingGroupW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getLocationid() != null && wrapper.getLocationid() > 0) { 
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if (location != null) { 
				entity.setLocation(location);
			}
		}
	}

	
	public ResourceBlockingGroupW[] getResourceBlockingGroupsByDateAndLocation(LocalDateTime since, LocalDateTime until, Long locationid) throws NotFoundException, OperationFailedException {
		
		ResourceBlockingGroupW[] result = null;
		List<ResourceBlockingGroupW> resultList = new ArrayList<ResourceBlockingGroupW>();

		String queryStr = 	"select rbg " + //
							"from ResourceBlockingGroup as rbg, " + //
							"ResourceBlocking as rb, " + //
							"Reserve as re " + //
							"where " + //
							"rb.blockinggroup.id = rbg.id " + //
							"and re.id = rb.id " + //
							"and re.when >= :since " + //
							"and re.when <= :until " + //
							"and re.location.id = :locationid ";
				
		PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
		properties[0] = new PropertyInfoDTO("re.when", "since", since);
		properties[1] = new PropertyInfoDTO("re.when", "until", until);
		properties[2] = new PropertyInfoDTO("re.location.id", "locationid", locationid);
		
				
		resultList = getByQuery(queryStr, properties);
		result = (ResourceBlockingGroupW[]) resultList.toArray(new ResourceBlockingGroupW[resultList.size()]);
		return result;
		
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "ResourceBlockingGroup";
	}
	@Override
	protected void setEntityname() {
		entityname = "ResourceBlockingGroup";
	}
}
