package bbr.b2b.regional.logistic.couriers.interfaces;

import java.util.List;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierTicketDetailW;
import bbr.b2b.regional.logistic.couriers.entities.CourierTicketDetail;
import bbr.b2b.regional.logistic.couriers.entities.CourierTicketDetailId;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierTicketMailInfoBean;

public interface ICourierTicketDetailServer extends IGenericServer<CourierTicketDetail, CourierTicketDetailId, CourierTicketDetailW> {
	
	CourierTicketDetailW addCourierTicketDetail(CourierTicketDetailW courierticketdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierTicketDetailW[] getCourierTicketDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierTicketDetailW updateCourierTicketDetail(CourierTicketDetailW courierticketdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int deleteCourierTicketDetail(PropertyInfoDTO[] pven) throws AccessDeniedException, OperationFailedException, NotFoundException;
	List<CourierTicketMailInfoBean> getMailInfoByCourierTicket(Long ticketid);
}
