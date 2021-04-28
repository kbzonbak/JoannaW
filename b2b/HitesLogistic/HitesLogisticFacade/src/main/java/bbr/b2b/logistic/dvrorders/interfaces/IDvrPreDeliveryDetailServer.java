package bbr.b2b.logistic.dvrorders.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.dvrorders.entities.DvrPreDeliveryDetail;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrPreDeliveryDetailW;
import bbr.b2b.logistic.dvrorders.entities.DvrPreDeliveryDetailId;
import bbr.b2b.logistic.dvrorders.report.classes.DvrPredeliveryDetailDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrPredeliveryDetailExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrPredeliveryDetailReportTotalDataDTO;
public interface IDvrPreDeliveryDetailServer extends IGenericServer<DvrPreDeliveryDetail, DvrPreDeliveryDetailId, DvrPreDeliveryDetailW> {

	DvrPreDeliveryDetailW addDvrPreDeliveryDetail(DvrPreDeliveryDetailW dvrpredeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrPreDeliveryDetailW[] getDvrPreDeliveryDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrPreDeliveryDetailW updateDvrPreDeliveryDetail(DvrPreDeliveryDetailW dvrpredeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	void doCalculateTotalDvrPredelivery(Long[] dvrorderids);
	DvrPredeliveryDetailDataDTO[] getDvrPredeliveryDetailByOrder(Long dvrorderid, Long vendorid,  int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException;
	DvrPredeliveryDetailReportTotalDataDTO getCountDvrPredeliveryDetailsByOrder(Long dvrorderid, Long vendorid) throws AccessDeniedException;
	DvrPredeliveryDetailExcelReportResultDTO getDvrPredeliveryDetailExcelReportByOrder(Long dvrorderid) throws OperationFailedException;
}
