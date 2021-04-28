package bbr.b2b.logistic.datings.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.datings.data.wrappers.ReserveW;
import bbr.b2b.logistic.datings.entities.Reserve;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.entities.Location;

@Stateless(name = "servers/datings/ReserveServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReserveServer extends LogisticElementServer<Reserve, ReserveW> implements ReserveServerLocal {


	@EJB
	LocationServerLocal locationserver;

	public ReserveW addReserve(ReserveW reserve) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReserveW) addElement(reserve);
	}
	public ReserveW[] getReserves() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReserveW[]) getIdentifiables();
	}
	public ReserveW updateReserve(ReserveW reserve) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReserveW) updateElement(reserve);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Reserve entity, ReserveW wrapper) {
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Reserve entity, ReserveW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getLocationid() != null && wrapper.getLocationid() > 0) { 
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if (location != null) { 
				entity.setLocation(location);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Reserve";
	}
	@Override
	protected void setEntityname() {
		entityname = "Reserve";
	}
}
