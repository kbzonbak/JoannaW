package bbr.b2b.regional.logistic.couriers.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierFileW;
import bbr.b2b.regional.logistic.couriers.entities.CourierFile;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierFileDTO;

public interface ICourierFileServer extends IElementServer<CourierFile, CourierFileW> {
	
	CourierFileW addCourierFile(CourierFileW courierfile) throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierFileW[] getCourierFiles() throws AccessDeniedException, OperationFailedException, NotFoundException;
	CourierFileW updateCourierFile(CourierFileW courierfile) throws AccessDeniedException, OperationFailedException, NotFoundException;
	Integer getDayOff();
	CourierFileDTO[] getCourierFileReport(Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	Integer getCourierFileCountReport(Date since, Date until) throws OperationFailedException, AccessDeniedException;
	CourierFileW[] getEmptyCourierFiles();
}
