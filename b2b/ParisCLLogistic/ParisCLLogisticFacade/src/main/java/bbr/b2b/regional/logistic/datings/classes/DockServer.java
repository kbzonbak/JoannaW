package bbr.b2b.regional.logistic.datings.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.datings.data.wrappers.DockW;
import bbr.b2b.regional.logistic.datings.entities.Dock;
import bbr.b2b.regional.logistic.datings.entities.DockType;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;

@Stateless(name = "servers/datings/DockServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DockServer extends LogisticElementServer<Dock, DockW> implements DockServerLocal {

	@EJB
	DockTypeServerLocal docktypeserver;
	
	@EJB
	LocationServerLocal locationserver;

	public DockW addDock(DockW dock) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DockW) addElement(dock);
	}
	public DockW[] getDocks() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DockW[]) getIdentifiables();
	}
	public DockW updateDock(DockW dock) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DockW) updateElement(dock);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Dock entity, DockW wrapper) {
		wrapper.setDocktypeid(entity.getDocktype() != null ? new Long(entity.getDocktype().getId()) : new Long(0));
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Dock entity, DockW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDocktypeid() != null && wrapper.getDocktypeid() > 0) { 
			DockType docktype = docktypeserver.getReferenceById(wrapper.getDocktypeid());
			if (docktype != null) { 
				entity.setDocktype(docktype);
			}
		}
		if (wrapper.getLocationid() != null && wrapper.getLocationid() > 0) { 
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if (location != null) { 
				entity.setLocation(location);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Dock";
	}
	@Override
	protected void setEntityname() {
		entityname = "Dock";
	}
}
