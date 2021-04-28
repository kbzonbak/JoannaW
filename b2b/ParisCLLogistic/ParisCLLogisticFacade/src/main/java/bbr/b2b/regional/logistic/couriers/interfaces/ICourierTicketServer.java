package bbr.b2b.regional.logistic.couriers.interfaces;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierTicketW;
import bbr.b2b.regional.logistic.couriers.entities.CourierTicket;

public interface ICourierTicketServer extends IElementServer<CourierTicket, CourierTicketW> {
	
	CourierTicketW addCourierTicket(CourierTicketW courierticket) throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierTicketW[] getCourierTickets() throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierTicketW updateCourierTicket(CourierTicketW courierticket) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int deleteCourierTicketDetail(PropertyInfoDTO[] pven) throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierTicketW[] getPendingTickets();
}
