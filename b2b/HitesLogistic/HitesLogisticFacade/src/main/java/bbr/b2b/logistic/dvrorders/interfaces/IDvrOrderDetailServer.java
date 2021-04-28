package bbr.b2b.logistic.dvrorders.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderDetailW;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderDetail;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderDetailId;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderDetailDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderDetailExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderDetailReportTotalDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderDetailDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderDetailExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderDetailReportTotalDataDTO;
public interface IDvrOrderDetailServer extends IGenericServer<DvrOrderDetail, DvrOrderDetailId, DvrOrderDetailW> {

	DvrOrderDetailW addDvrOrderDetail(DvrOrderDetailW dvrorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrOrderDetailW[] getDvrOrderDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrOrderDetailW updateDvrOrderDetail(DvrOrderDetailW dvrorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	void doCalculateTotalDvrOrderDetail(Long[] dvrorderids);
	DvrOrderDetailDataDTO[] getDvrOrdersDetailByDvrOrder(Long dvrorderid, Long vendorid, Long excludetypeid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException;
	DvrOrderDetailReportTotalDataDTO getCountDvrOrdersDetailsByOrder(Long dvrorderid, Long vendorid, Long excludetypeid) throws AccessDeniedException;		
	DvrOrderDetailExcelReportResultDTO getDvrOrderDetailExcelReportByOrder(Long dvrorderid) throws OperationFailedException;
	int countDvrOrderDetailExcelReportByOrder(Long dvrorderid);
	DvhOrderDetailExcelReportResultDTO getDvhOrderDetailExcelReportByOrder(Long dvhorderid) throws OperationFailedException;
	int countDvhOrderDetailExcelReportByOrder(Long dvhorderid);
	DvhOrderDetailDataDTO[] getDvhOrdersDetailByDvrOrder(Long dvrorderid, Long vendorid, Long dvhtypeid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException;
	DvhOrderDetailReportTotalDataDTO getCountDvhOrdersDetailsByOrder(Long dvrorderid, Long vendorid, Long dvhtypeid) throws AccessDeniedException;
}
