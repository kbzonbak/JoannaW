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
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierTicketDetailW;
import bbr.b2b.regional.logistic.couriers.entities.CourierTicket;
import bbr.b2b.regional.logistic.couriers.entities.CourierTicketDetail;
import bbr.b2b.regional.logistic.couriers.entities.CourierTicketDetailId;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierTicketMailInfoBean;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;

@Stateless(name = "servers/couriers/CourierTicketDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CourierTicketDetailServer extends BaseLogisticEJB3Server<CourierTicketDetail, CourierTicketDetailId, CourierTicketDetailW> implements CourierTicketDetailServerLocal {
	
	@EJB
	CourierTicketServerLocal courierticketserver;
	
	@EJB
	DODeliveryServerLocal dodeliveryserver;
	
	public CourierTicketDetailW addCourierTicketDetail(CourierTicketDetailW courierticketdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierTicketDetailW) addIdentifiable(courierticketdetail);
	}
	public CourierTicketDetailW[] getCourierTicketDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierTicketDetailW[]) getIdentifiables();
	}
	public CourierTicketDetailW updateCourierTicketDetail(CourierTicketDetailW courierticketdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierTicketDetailW) updateIdentifiable(courierticketdetail);
	}
	public int deleteCourierTicketDetail(PropertyInfoDTO[] pven) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return deleteByProperties(pven);
	}

	@Override
	protected void copyRelationsEntityToWrapper(CourierTicketDetail entity, CourierTicketDetailW wrapper){
		wrapper.setCourierticketid(entity.getCourierticket() != null ? new Long(entity.getCourierticket().getId()) : new Long(0));
		wrapper.setDodeliveryid(entity.getDodelivery() != null ? new Long(entity.getDodelivery().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(CourierTicketDetail entity, CourierTicketDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getCourierticketid() != null && wrapper.getCourierticketid() > 0) {
			CourierTicket courierticket = courierticketserver.getReferenceById(wrapper.getCourierticketid());
		if (courierticket != null) {
			entity.setCourierticket(courierticket);
		}
		}
		
		if (wrapper.getDodeliveryid() != null && wrapper.getDodeliveryid() > 0) {
			DODelivery dodelivery = dodeliveryserver.getReferenceById(wrapper.getDodeliveryid());
		if (dodelivery != null) { 
			entity.setDodelivery(dodelivery);
		}
		}
		
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "CourierTicketDetail";
		
	}

	@Override
	protected void setEntityname() {
		entityname = "CourierTicketDetail";
		
	}
	
	public List<CourierTicketMailInfoBean> getMailInfoByCourierTicket(Long ticketid) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.CourierTicketDetail.getMailInfoByCourierTicket");
		query.setLong("ticketid", ticketid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(CourierTicketMailInfoBean.class));
		List result = (List<CourierTicketMailInfoBean>)query.list();
		return result;
	}

}
