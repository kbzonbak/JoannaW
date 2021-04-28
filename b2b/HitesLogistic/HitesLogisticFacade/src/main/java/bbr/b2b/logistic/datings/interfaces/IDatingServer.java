package bbr.b2b.logistic.datings.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.datings.entities.Dating;
import bbr.b2b.logistic.datings.report.classes.DatingDataToMessageDTO;
import bbr.b2b.logistic.datings.report.classes.DatingInformationDTO;
import bbr.b2b.logistic.datings.report.classes.DatingTimeDockDataDTO;
import bbr.b2b.logistic.datings.report.classes.ExcelDatingReportDataDTO;
import bbr.b2b.logistic.datings.data.wrappers.DatingW;

public interface IDatingServer extends IElementServer<Dating, DatingW> {

	DatingW addDating(DatingW dating) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DatingW[] getDatings() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DatingW updateDating(DatingW dating) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DatingInformationDTO getDatingInformation(Long dvrdeliveryid);
	DatingTimeDockDataDTO[] getDatingTimeDockData(Long dvrdeliveryid);
	DatingW[] getDatingByDateAndLocation(LocalDateTime since, LocalDateTime until, Long locationid) throws NotFoundException, OperationFailedException;
	DatingW[] getDatingByDvrDeliveryIds(Long[] dvrdeliveryids) throws NotFoundException, OperationFailedException;
	DatingDataToMessageDTO[] getDatingDataToMessage(Long dvrdeliveryid);
	ExcelDatingReportDataDTO[] getExcelDatingReport(Long locationid, LocalDateTime since, LocalDateTime until);
}
