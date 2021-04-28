package bbr.b2b.regional.logistic.deliveries.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.datings.report.classes.AssignedDatingTotalizerByDockDTO;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryDetailW;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetailId;
import bbr.b2b.regional.logistic.deliveries.report.classes.BulkDetailDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDetailReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.OrderDeliveryDetailDTO;
import bbr.b2b.regional.logistic.report.classes.UploadErrorDTO;
public interface IOrderDeliveryDetailServer extends IGenericServer<OrderDeliveryDetail, OrderDeliveryDetailId, OrderDeliveryDetailW> {

	OrderDeliveryDetailW addOrderDeliveryDetail(OrderDeliveryDetailW orderdeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDeliveryDetailW[] getOrderDeliveryDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDeliveryDetailW updateOrderDeliveryDetail(OrderDeliveryDetailW orderdeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DeliveryDetailReportDataDTO[] getDeliveryDetailReport(Long vendorid, Long deliveryid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException;
	DeliveryDetailReportTotalDataDTO getDeliveryDetailCountReport(Long vendorid, Long deliveryid) throws ServiceException;
	AssignedDatingTotalizerByDockDTO[] getAssignedDatingTotalizerByDateAndLocation(Date since, Date until, Long locationid, Long docktypeid, boolean isbyreport);
	OrderDeliveryDetailDTO getOrderDeliveryDetailDataByOrderItemLocationAndATC(Long orderid, Long deliveryid, Long itemid, Long locationid, Long atcid);
	void doDeleteByOrderDeliveries(Long deliveryid, Long[] orderids);
	
	int doLoadCSV(String filename);
	void doCreateTempTableAdjustDelivery();
	void doCreateTempTableError();
	UploadErrorDTO[] getErrorsOfAdjustDelivery();
	BulkDetailDataDTO[] getBulkDetailData();
	int doFillData(Long deliveryid);
	int doCheckUniqueRows();
	int doCheckValidOrderItemLocation();
	int doCheckOrderDeliveryAdjustUnits();
	int doCheckPredeliveryAdjustUnits(Long deliveryid);
	int doCheckATCItems();
	int doCheckProportionalATCCurves();
	int doCheckCompleteATCCurves();
	int doAdjustOrderDeliveryDetailUnits();
	int doAdjustOrderDeliveryEstimatedUnits();
	Long[] getOrderIdsFromAdjustDelivery();
	
}
