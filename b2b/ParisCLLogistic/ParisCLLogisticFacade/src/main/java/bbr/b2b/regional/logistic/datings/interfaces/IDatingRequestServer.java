package bbr.b2b.regional.logistic.datings.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingRequestW;
import bbr.b2b.regional.logistic.datings.entities.DatingRequest;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestReportDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ExcelDatingRequestReportData;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedDatingRequestReportDataDTO;

public interface IDatingRequestServer extends IElementServer<DatingRequest, DatingRequestW> {

	DatingRequestW addDatingRequest(DatingRequestW datingrequest) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DatingRequestW[] getDatingRequests() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DatingRequestW updateDatingRequest(DatingRequestW datingrequest) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DatingRequestReportDataDTO[] getDatingRequestReport(Long locationid, Long ordertypeid, Long flowtypeid, Long[] vendorids, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	int getDatingRequestCountReport(Long locationid, Long ordertypeid, Long flowtypeid, Long[] vendorids, Date since, Date until, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException;
	Long getNextSequenceDatingRequestNumber() throws OperationFailedException, NotFoundException;
	ImportedDatingRequestReportDataDTO[] getImportedDatingRequestReport(String value, Long number, int flowType, Long locationid, Long ordertypeid, Long flowtypeid, Long[] vendorids, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	int getImportedDatingRequestCountReport(String value, Long number, int flowType, Long locationid, Long ordertypeid, Long flowtypeid, Long[] vendorids, Date since, Date until, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException;
	ExcelDatingRequestReportData[] getExcelDatingRequest(Long[] deliveryids) throws AccessDeniedException, OperationFailedException;
}
