package bbr.b2b.regional.logistic.couriers.classes;

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
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierTicketW;
import bbr.b2b.regional.logistic.couriers.entities.Courier;
import bbr.b2b.regional.logistic.couriers.entities.CourierTicket;

@Stateless(name = "servers/couriers/CourierTicketServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CourierTicketServer extends LogisticElementServer<CourierTicket, CourierTicketW> implements CourierTicketServerLocal {
	
	@EJB
	CourierServerLocal courierServerLocal;

	@Override
	protected void copyRelationsEntityToWrapper(CourierTicket entity, CourierTicketW wrapper) {
		wrapper.setCourierid(entity.getCourier() != null ? new Long(entity.getCourier().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(CourierTicket entity, CourierTicketW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getCourierid() != null && wrapper.getCourierid() > 0) { 
			Courier courier = courierServerLocal.getReferenceById(wrapper.getCourierid());
			if (courier != null) { 
				entity.setCourier(courier);
			}
		}
	}
	
	public CourierTicketW addCourierTicket(CourierTicketW courierticket) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierTicketW) addElement(courierticket);
	}
	public CourierTicketW[] getCourierTickets() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierTicketW[]) getIdentifiables();
	}
	public CourierTicketW updateCourierTicket(CourierTicketW courierticket) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierTicketW) updateElement(courierticket);
	}
	public int deleteCourierTicketDetail(PropertyInfoDTO[] pven) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return deleteByProperties(pven);
	}
		
	public CourierTicketW[] getPendingTickets(){
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.CourierTicket.getPendingTickets");
		query.setResultTransformer(new LowerCaseResultTransformer(CourierTicketW.class));
		
		List list = query.list();
		CourierTicketW[] result = (CourierTicketW[]) list.toArray(new CourierTicketW[list.size()]);		
		
		return result;
		
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "CourierTicket";
	}
	@Override
	protected void setEntityname() {
		entityname = "CourierTicket";
	}
}
