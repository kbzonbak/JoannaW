package bbr.b2b.regional.logistic.couriers.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierScheduleLogW;
import bbr.b2b.regional.logistic.couriers.entities.CourierScheduleLog;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierScheduleLogDTO;

public interface ICourierScheduleLogServer extends IElementServer<CourierScheduleLog, CourierScheduleLogW> {
	
	CourierScheduleLogW updateCourierScheduleLog(CourierScheduleLogW courierschedulelog) throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierScheduleLogW addCourierScheduleLog(CourierScheduleLogW courierschedulelog) throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierScheduleLogW[] getCourierScheduleLogs() throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	CourierScheduleLogDTO[] getCourierScheduleLogByDODelivery(Long dodeliveryid, OrderCriteriaDTO[] orderby) throws AccessDeniedException;
	
}
