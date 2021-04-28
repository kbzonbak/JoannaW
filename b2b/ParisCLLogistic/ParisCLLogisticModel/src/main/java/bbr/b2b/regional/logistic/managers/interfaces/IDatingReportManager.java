package bbr.b2b.regional.logistic.managers.interfaces;

import java.time.LocalDateTime;
import java.util.Date;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.datings.report.classes.AddDatingAndDetailsInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.AddDatingAndDetailsResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.AddDatingRequestResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.AssignedDatingInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.AssignedDatingResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingAsNotAttendedInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingDataInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingDataResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestContainerInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestContainerReportResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestOfDeliveryInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestReportInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestReportResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DeleteDatingInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DoCancelDatingInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DocksArrayResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ExcelDatingRequestReportDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedDatingOrderInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedDatingRequestOrdersDeleteInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedDatingRequestReportInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedDatingRequestReportResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedNonDeliveredOrderReportResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ModulesArrayResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.RejectDatingRequestInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ReserveDetailKeyDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ReserverDetailReportDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ResourceBlockingGroupDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ResourceBlokingGroupInitParamDTO;
import bbr.b2b.regional.logistic.evaluations.data.classes.EvaluationReportDataResultDTO;
import bbr.b2b.regional.logistic.evaluations.data.classes.EvaluationReportInitParamDTO;


public interface IDatingReportManager {
	
	DocksArrayResultDTO getDocksOfLocation(String locationcode) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ModulesArrayResultDTO getModules();
	AssignedDatingResultDTO getAssignedDatingByDate(AssignedDatingInitParamDTO initParamW, boolean isbyfilter, boolean isbyreport);
	ResourceBlockingGroupDTO doAddResourceBlockingGroupandDetails(ResourceBlokingGroupInitParamDTO initparam);
	ReserverDetailReportDTO getReserveDetailsDataofBlockingGroup(Long blockinggroupid,int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated, boolean ispageinfo) throws AccessDeniedException, NotFoundException, OperationFailedException;
	BaseResultDTO doDeleteReserveDetailsofResourceBlockingGroup(Long reserveid, ReserveDetailKeyDTO[] detailsid);
	BaseResultDTO doDeleteResourceBlockingGroup(Long resourceblockinggroupid);
	BaseResultDTO doAddDocksAndModules() throws AccessDeniedException, NotFoundException, OperationFailedException;
	DatingDataResultDTO getDatingDataOfDelivery(DatingDataInitParamDTO initParamDTO);
	DatingRequestResultDTO getDatingRequestOfDelivery(DatingRequestOfDeliveryInitParamDTO initParams);
	AddDatingRequestResultDTO doAddDatingRequest(DatingRequestInitParamDTO initParams);
	DatingRequestReportResultDTO getDatingRequestReport(DatingRequestReportInitParamDTO initParams, String[] vendorcodes, boolean byFilter);
	AddDatingAndDetailsResultDTO doAddDatingAndDetails(AddDatingAndDetailsInitParamDTO initParams);
	BaseResultDTO doRejectDatingRequest(RejectDatingRequestInitParamDTO initParams);
	BaseResultDTO doCancelDatingByVendor(DoCancelDatingInitParamDTO initParams); 
	BaseResultDTO doDeleteDating(DeleteDatingInitParamDTO initParamW);
	BaseResultDTO doMarkDatingAsNotAttended(DatingAsNotAttendedInitParamDTO initParams);
	EvaluationReportDataResultDTO getEvaluationReportOfDating(EvaluationReportInitParamDTO initParams);
	ImportedDatingRequestReportResultDTO getImportedDatingRequestReport(ImportedDatingRequestReportInitParamDTO initParams, String[] vendorcodes, boolean byFilter);
	BaseResultDTO doDeleteOrdersFromImportedDatingRequest(ImportedDatingRequestOrdersDeleteInitParamDTO initParams);
	DatingRequestContainerReportResultDTO getDatingsByDatingRequestContainer(DatingRequestContainerInitParamDTO initParams);
	ImportedNonDeliveredOrderReportResultDTO getImportedNonDeliveredOrdersByDating(ImportedDatingOrderInitParamDTO initParams);
	BaseResultDTO doDeleteImportedDatingOrders(ImportedDatingOrderInitParamDTO initParams);
	FileDownloadInfoResultDTO getCSVDatingToDeliveryReport(String locationcode, Long userID) throws OperationFailedException, NotFoundException;
	FileDownloadInfoResultDTO getCSVDatingReport(String locationcode, LocalDateTime since, Long userID) throws OperationFailedException, NotFoundException;
	ExcelDatingRequestReportDataDTO getExcelDatingRequest(Long[] deliveryids);
	void doDailyReportDates(Date date) throws OperationFailedException;
	FileDownloadInfoResultDTO doDailyReportDates(Date since, Date until, Long userid) throws OperationFailedException;
}
