package bbr.b2b.regional.logistic.datings.classes;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.datings.data.wrappers.ResourceBlockingGroupW;
import bbr.b2b.regional.logistic.datings.entities.ResourceBlockingGroup;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;

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

	@Override
	protected void setEntitylabel() {
		entitylabel = "ResourceBlockingGroup";
	}
	@Override
	protected void setEntityname() {
		entityname = "ResourceBlockingGroup";
	}
	
	public ResourceBlockingGroupW[] getResourceBlockingGroupsByDateAndLocation(Date since, Date until, Long locationid)	throws AccessDeniedException, OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ResourceBlockingGroup.getResourceBlockingGroupsByDateAndLocation");
		query.setDate("since", since);
		query.setDate("until", until);
		query.setLong("locationid", locationid);		
		query.setResultTransformer(new LowerCaseResultTransformer(ResourceBlockingGroupW.class));
		List list = query.list();		
		ResourceBlockingGroupW[] result = (ResourceBlockingGroupW[]) list.toArray(new ResourceBlockingGroupW[list.size()]);
		return result;
	}
	
}
