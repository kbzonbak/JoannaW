package bbr.b2b.regional.logistic.datings.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.datings.data.wrappers.ReserveDetailW;
import bbr.b2b.regional.logistic.datings.entities.ReserveDetail;
import bbr.b2b.regional.logistic.datings.entities.ReserveDetailId;
import bbr.b2b.regional.logistic.datings.report.classes.AssignedDatingDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.AssignedResourceBlockingDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ReserveDetailDataDTO;
public interface IReserveDetailServer extends IGenericServer<ReserveDetail, ReserveDetailId, ReserveDetailW> {

	ReserveDetailW addReserveDetail(ReserveDetailW reservedetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReserveDetailW[] getReserveDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReserveDetailW updateReserveDetail(ReserveDetailW reservedetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReserveDetailW[] getReserveDetailByDateAndLocationAndDelivery(Long deliveryId, Long locationId, Date date) throws OperationFailedException, NotFoundException;
	ReserveDetailW[] getReserveDetailsByDateAndLocation(Date since, Date until, Long locationid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	AssignedDatingDataDTO[] getAssignedDatingDetailsByDateAndLocation(Date since, Date until, Long locationid, Long docktypeid, boolean isbyreport) throws AccessDeniedException, OperationFailedException, NotFoundException;
	AssignedResourceBlockingDataDTO[] getAssignedResourceBlockingDetailsByDateAndLocation(Date since, Date until, Long locationid, Long docktypeid, boolean isbyreport) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReserveDetailW[] getReserveDetailsofBlockingGroup(Long reserveid) throws AccessDeniedException, NotFoundException, OperationFailedException;
	ReserveDetailW[] getDatingDetailsByDateAndLocationAndDockAndModule(Date when, Long locationid, Long dockid, Long moduleid) throws AccessDeniedException, OperationFailedException, NotFoundException ;
	ReserveDetailDataDTO[] getReserveDetailOfDelivery(Long deliveryId, Long vendorId) throws AccessDeniedException, OperationFailedException, NotFoundException ;
	void doDeleteReserveDetailofReserve(Long reserveid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	void doDeleteReserveDetailsOfReserves(Long[] reserveids) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReserveDetailW[] getReserveDetailsofReserve(Long reserveid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReserveDetailDataDTO[] getReserveDetailsDataofBlockingGroup(Long blockinggroupid,int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException;
	int countReserveDetailsDataofBlockingGroup(Long blockinggroupid) throws AccessDeniedException, NotFoundException, OperationFailedException;
	ReserveDetailW[] getDatingDetailsByDateLocationDockAndModule(Date when, Long locationid, Long dockid, Long moduleid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}
