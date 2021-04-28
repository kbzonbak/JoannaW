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
import bbr.b2b.logistic.datings.entities.ResourceBlocking;
import bbr.b2b.logistic.datings.entities.ResourceBlockingGroup;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.entities.Location;
import bbr.b2b.logistic.datings.data.wrappers.ResourceBlockingW;

@Stateless(name = "servers/datings/ResourceBlockingServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ResourceBlockingServer extends LogisticElementServer<ResourceBlocking, ResourceBlockingW> implements ResourceBlockingServerLocal {


	@EJB
	ResourceBlockingGroupServerLocal blockinggroupserver;

	@EJB
	LocationServerLocal  locationserver;
	
	public ResourceBlockingW addResourceBlocking(ResourceBlockingW resourceblocking) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ResourceBlockingW) addElement(resourceblocking);
	}
	public ResourceBlockingW[] getResourceBlockings() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ResourceBlockingW[]) getIdentifiables();
	}
	public ResourceBlockingW updateResourceBlocking(ResourceBlockingW resourceblocking) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ResourceBlockingW) updateElement(resourceblocking);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ResourceBlocking entity, ResourceBlockingW wrapper) {
		wrapper.setBlockinggroupid(entity.getBlockinggroup() != null ? new Long(entity.getBlockinggroup().getId()) : new Long(0));
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(ResourceBlocking entity, ResourceBlockingW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getBlockinggroupid() != null && wrapper.getBlockinggroupid() > 0) { 
			ResourceBlockingGroup blockinggroup = blockinggroupserver.getReferenceById(wrapper.getBlockinggroupid());
			if (blockinggroup != null) { 
				entity.setBlockinggroup(blockinggroup);
			}
		}
		if(wrapper.getLocationid() != null && wrapper.getLocationid() > 0) {
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if(location != null) {
				entity.setLocation(location);
			}
		}
	}

	
	public ResourceBlockingW[] getResourceBlockingsByDateAndLocation(LocalDateTime since, LocalDateTime until, Long locationid) throws NotFoundException, OperationFailedException {
		
		ResourceBlockingW[] result = null;
		List<ResourceBlockingW> resultList = new ArrayList<ResourceBlockingW>();

		String queryStr =	"select rb " + //
							"from ResourceBlocking as rb, " + //
							"Reserve as re " + //
							"where " + //
							"re.id = rb.id " + //
							"and re.when >= :since " + //
							"and re.when <= :until " + //
							"and re.location.id = :locationid ";
				
		PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
		properties[0] = new PropertyInfoDTO("re.when", "since", since);
		properties[1] = new PropertyInfoDTO("re.when", "until", until);
		properties[2] = new PropertyInfoDTO("re.location.id", "locationid", locationid);
		
				
		resultList = getByQuery(queryStr, properties);
		result = (ResourceBlockingW[]) resultList.toArray(new ResourceBlockingW[resultList.size()]);
		return result;
		
	}
	
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "ResourceBlocking";
	}
	@Override
	protected void setEntityname() {
		entityname = "ResourceBlocking";
	}
}
