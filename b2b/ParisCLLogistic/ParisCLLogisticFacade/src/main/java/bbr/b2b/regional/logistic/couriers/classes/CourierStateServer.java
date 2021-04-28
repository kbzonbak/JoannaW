package bbr.b2b.regional.logistic.couriers.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierStateW;
import bbr.b2b.regional.logistic.couriers.entities.CourierFile;
import bbr.b2b.regional.logistic.couriers.entities.CourierState;
import bbr.b2b.regional.logistic.couriers.entities.CourierTag;

@Stateless(name = "servers/couriers/CourierStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CourierStateServer extends LogisticElementServer<CourierState, CourierStateW> implements CourierStateServerLocal {
	
	@EJB
	CourierTagServerLocal courierTagServerLocal;
	
	@EJB
	CourierFileServerLocal courierFileServerLocal;

	@Override
	protected void copyRelationsEntityToWrapper(CourierState entity, CourierStateW wrapper) {
		wrapper.setCourierfileid(entity.getCourierfile() != null ? new Long(entity.getCourierfile().getId()) : new Long(0));
		wrapper.setCouriertagid(entity.getCouriertag() != null ? new Long(entity.getCouriertag().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(CourierState entity, CourierStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getCouriertagid() != null && wrapper.getCouriertagid() > 0) { 
			CourierTag courier = courierTagServerLocal.getReferenceById(wrapper.getCouriertagid());
			if (courier != null) { 
				entity.setCouriertag(courier);
			}
		}
		if (wrapper.getCourierfileid() != null && wrapper.getCourierfileid() > 0) { 
			CourierFile courier = courierFileServerLocal.getReferenceById(wrapper.getCourierfileid());
			if (courier != null) { 
				entity.setCourierfile(courier);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "CourierState";
	}
	@Override
	protected void setEntityname() {
		entityname = "CourierState";
	}
	
	public CourierStateW addCourierState(CourierStateW courierState) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierStateW) addElement(courierState);
	}
	public CourierStateW[] getCourierStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierStateW[]) getIdentifiables();
	}
	public CourierStateW updateCourierState(CourierStateW courierState) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierStateW) updateElement(courierState);
	}
	
}
