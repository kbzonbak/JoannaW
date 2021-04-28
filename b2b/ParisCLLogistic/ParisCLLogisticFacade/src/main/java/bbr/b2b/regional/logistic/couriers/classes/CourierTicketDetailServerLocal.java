package bbr.b2b.regional.logistic.couriers.classes;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierTicketDetailW;
import bbr.b2b.regional.logistic.couriers.interfaces.ICourierTicketDetailServer;

@Local
public interface CourierTicketDetailServerLocal extends ICourierTicketDetailServer {

	CourierTicketDetailW addCourierTicketDetail(CourierTicketDetailW courierticketdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierTicketDetailW[] getCourierTicketDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierTicketDetailW updateCourierTicketDetail(CourierTicketDetailW courierticketdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int deleteCourierTicketDetail(PropertyInfoDTO[] pven) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}
