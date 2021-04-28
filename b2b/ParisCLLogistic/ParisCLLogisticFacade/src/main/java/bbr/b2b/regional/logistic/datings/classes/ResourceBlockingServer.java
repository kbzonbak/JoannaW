package bbr.b2b.regional.logistic.datings.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.datings.data.wrappers.ReserveDetailW;
import bbr.b2b.regional.logistic.datings.data.wrappers.ResourceBlockingW;
import bbr.b2b.regional.logistic.datings.entities.ResourceBlocking;
import bbr.b2b.regional.logistic.datings.entities.ResourceBlockingGroup;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;

@Stateless(name = "servers/datings/ResourceBlockingServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ResourceBlockingServer extends LogisticElementServer<ResourceBlocking, ResourceBlockingW> implements ResourceBlockingServerLocal {


	@EJB
	ResourceBlockingGroupServerLocal blockinggroupserver;
	
	@EJB
	LocationServerLocal locationserver;

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
		if (wrapper.getLocationid() != null && wrapper.getLocationid() > 0){
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if(location != null){
				entity.setLocation(location);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "ResourceBlocking";
	}
	@Override
	protected void setEntityname() {
		entityname = "ResourceBlocking";
	}
	
	public ResourceBlockingW[] getResourceBlockingsByDateAndLocation(Date since, Date until, Long locationid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ResourceBlocking.getResourceBlockingsByDateAndLocation");
		query.setDate("since", since);
		query.setDate("until", until);
		query.setLong("locationid", locationid.longValue());
		query.setResultTransformer(new LowerCaseResultTransformer(ResourceBlockingW.class));
		List list = query.list();
		ResourceBlockingW[] result = (ResourceBlockingW[]) list.toArray(new ResourceBlockingW[list.size()]);
		return result;
	}
	
	public void doDeleteResourceBlockingsofResourceBlockingGroup(Long blockingroupid) throws NotFoundException, OperationFailedException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ResourceBlocking.doDeleteResourceBlockingsofResourceBlockingGroup");
		query.setLong("blockingroupid", blockingroupid.longValue());
		query.executeUpdate();
	}
	
	public ResourceBlockingW[] getResourceBlockingsofResourceBlockingGroup(Long blockingroupid) throws NotFoundException, OperationFailedException {

		ResourceBlockingW[] result = null;
		List<ResourceBlockingW> resultList = new ArrayList<ResourceBlockingW>();
		
		String queryStr = 	"select rb " + //
							"from ResourceBlocking as rb " + //
							"where rb.blockinggroup.id = :blockingroupid ";
				
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("rb.id", "blockingroupid", blockingroupid);

		resultList = getByQuery(queryStr, properties);
		result = (ResourceBlockingW[]) resultList.toArray(new ResourceBlockingW[resultList.size()]);
		return result;
		
//		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ResourceBlocking.getResourceBlockingsofResourceBlockingGroup");
//		query.setLong("blockingroupid", blockingroupid.longValue());
//		query.setResultTransformer(new LowerCaseResultTransformer(ResourceBlockingW.class));
//		List list = query.list();
//		ResourceBlockingW[] result = (ResourceBlockingW[]) list.toArray(new ResourceBlockingW[list.size()]);
//		return result;
	}
	
}
