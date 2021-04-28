package bbr.b2b.regional.logistic.couriers.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.RescheduleReasonW;
import bbr.b2b.regional.logistic.couriers.entities.RescheduleReason;
import bbr.b2b.regional.logistic.couriers.report.classes.RescheduleReasonDTO;

public interface IRescheduleReasonServer extends IElementServer<RescheduleReason, RescheduleReasonW> {
	
	RescheduleReasonW addRescheduleReason(RescheduleReasonW reschedulereason) throws AccessDeniedException, OperationFailedException, NotFoundException;
	RescheduleReasonW[] getRescheduleReasons() throws AccessDeniedException, OperationFailedException, NotFoundException;
	RescheduleReasonW updateRescheduleReason(RescheduleReasonW reschedulereason) throws AccessDeniedException, OperationFailedException, NotFoundException;
	RescheduleReasonDTO[] getShowableRescheduleReasons();
	
}