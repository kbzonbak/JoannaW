package bbr.b2b.logistic.dvrdeliveries.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.dvrdeliveries.entities.DatingRequest;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DatingRequestDataDTO;
import bbr.b2b.logistic.datings.report.classes.DatingRequestReportDataDTO;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DatingRequestW;

public interface IDatingRequestServer extends IElementServer<DatingRequest, DatingRequestW> {

	DatingRequestW addDatingRequest(DatingRequestW datingrequest) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DatingRequestW[] getDatingRequests() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DatingRequestW updateDatingRequest(DatingRequestW datingrequest) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DatingRequestDataDTO getDatingRequestDetailDataReport(Long dvrdeliveryid);
	DatingRequestReportDataDTO[] getDatingRequestReport(Long vendorid, Long locationid, Long validstateid, LocalDateTime since, LocalDateTime until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	int getCountDatingRequestReport(Long vendorid, Long locationid, Long validstateid, LocalDateTime since, LocalDateTime until) throws AccessDeniedException, OperationFailedException;

}
