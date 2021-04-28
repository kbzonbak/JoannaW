package bbr.b2b.regional.logistic.datings.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.regional.logistic.datings.entities.Dating;
import bbr.b2b.regional.logistic.datings.report.classes.DailyReportDatesDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ExcelDatingReportDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestContainerDTO;

public interface IDatingServer extends IElementServer<Dating, DatingW> {

	DatingW addDating(DatingW dating) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DatingW[] getDatings() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DatingW updateDating(DatingW dating) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	String getFormattedDatingDateTime(Long datingid);
	DatingDataDTO[] getDatingDataByDateAndLocation(Date since, Date until, Long locationid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ExcelDatingReportDataDTO[] getExcelDatingReport(Date since, Date until, Long locationid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	Long getNextSequenceDatingNumber() throws OperationFailedException, NotFoundException;
	FileDownloadInfoResultDTO getCSVDatingToDeliveryReport(Long locationid, Long userID) throws OperationFailedException, NotFoundException;
	FileDownloadInfoResultDTO getCSVDatingReport(Long locationid, Date since, Long userID) throws OperationFailedException, NotFoundException;
	DailyReportDatesDTO[] getDailyReportDates(Date since, Date until) throws OperationFailedException;
	Long getNextSequenceDailyReportDates() throws OperationFailedException, NotFoundException;
	DatingRequestContainerDTO[] getDatingsByDatingRequestContainer(Long datingrequestid, OrderCriteriaDTO[] orderby)  throws AccessDeniedException;
	DatingDataDTO getDatingDataByDelivery(Long deliveryid);
}
