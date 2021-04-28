package bbr.b2b.logistic.datings.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.datings.entities.DatingResource;
import bbr.b2b.logistic.datings.data.wrappers.DatingResourceW;
import bbr.b2b.logistic.datings.entities.DatingResourceId;
import bbr.b2b.logistic.datings.entities.Dock;
import bbr.b2b.logistic.datings.entities.Module;

@Stateless(name = "servers/datings/DatingResourceServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DatingResourceServer extends BaseLogisticEJB3Server<DatingResource, DatingResourceId, DatingResourceW> implements DatingResourceServerLocal {


	@EJB
	ModuleServerLocal moduleserver;

	@EJB
	DockServerLocal dockserver;

	public DatingResourceW addDatingResource(DatingResourceW datingresource) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingResourceW) addIdentifiable(datingresource);
	}
	public DatingResourceW[] getDatingResources() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingResourceW[]) getIdentifiables();
	}
	public DatingResourceW updateDatingResource(DatingResourceW datingresource) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingResourceW) updateIdentifiable(datingresource);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DatingResource entity, DatingResourceW wrapper) {
		wrapper.setModuleid(entity.getModule() != null ? new Long(entity.getModule().getId()) : new Long(0));
		wrapper.setDockid(entity.getDock() != null ? new Long(entity.getDock().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DatingResource entity, DatingResourceW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getModuleid() != null && wrapper.getModuleid() > 0) { 
			Module module = moduleserver.getReferenceById(wrapper.getModuleid());
			if (module != null) { 
				entity.setModule(module);
			}
		}
		if (wrapper.getDockid() != null && wrapper.getDockid() > 0) { 
			Dock dock = dockserver.getReferenceById(wrapper.getDockid());
			if (dock != null) { 
				entity.setDock(dock);
			}
		}
	}

	
	public int getMaxVisualOrderbyDock(Long dockid){
		String SQL = "select  COALESCE(max(visualorder), 0) from  logistica.datingresource where dock_id = :dockid ";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("dockid", dockid);
		int max =  (Integer) query.list().get(0);
		return max;	
	}
	
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DatingResource";
	}
	@Override
	protected void setEntityname() {
		entityname = "DatingResource";
	}
}
