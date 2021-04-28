package bbr.b2b.regional.logistic.deliveries.interfaces;

import java.util.List;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BulkDetailW;
import bbr.b2b.regional.logistic.deliveries.entities.BulkDetail;
import bbr.b2b.regional.logistic.deliveries.entities.BulkDetailId;
import bbr.b2b.regional.logistic.deliveries.report.classes.AsnDetailW;
import bbr.b2b.regional.logistic.deliveries.report.classes.BulkDetailSummaryDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.PredistributedPackingListDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListTotalDataDTO;
import bbr.b2b.regional.logistic.report.classes.UploadErrorDTO;
public interface IBulkDetailServer extends IGenericServer<BulkDetail, BulkDetailId, BulkDetailW> {

	BulkDetailW addBulkDetail(BulkDetailW bulkdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	BulkDetailW[] getBulkDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	BulkDetailW updateBulkDetail(BulkDetailW bulkdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ProposedPackingListDataDTO[] getProposedPackingList(Long deliveryid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean isByPages) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ProposedPackingListTotalDataDTO getProposedPackingListCount(Long deliveryid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	BulkDetailSummaryDTO[] getBulkDetailSummary(Long deliveryid) throws ServiceException;
	AsnDetailW[] getAsnDetailsByDelivery(Long deliveryid);
	String getRefDocumentByDeliveryId(Long deliveryid);
	void doDeleteByOrderDeliveries(Long deliveryid, Long[] orderids);
	Long[] getTaxDocumentIdsByDelivery(Long deliveryid);
	Long[] getTaxDocumentIdsByBulks(Long[] bulkids);
		
	int doLoadCSV(String filename);
	void doCreateTempTablePredistributedPackingList();
	void doCreateTempTableError();
	UploadErrorDTO[] getErrorsOfPredistributedPackingList();
	List<PredistributedPackingListDataDTO> getPredistributedPackingListDate();
	int doFillOrderDetailData();
	int doFillDestinationLocationData();
	int doCheckUniqueLpnItems();
	int doCheckUniqueOrderByLpn();
	int doCheckUniqueDestinationLocationByLpn();
	int doCheckUniqueContainer();
	int doCheckValidOrders();
	int doCheckValidVendors();
	int doCheckUniqueDeliveryLocation();
	int doCheckValidDeliveryLocation(Long locationid);
	int doCheckValidOrderItems();
	int doCheckValidDestinationLocations();
	int doCheckLpnDestinationLocation();
	int doCheckLpnVendorLogisticCode();
	int doCheckLpnCorrelative();
	int doCheckLpnFlowType();
}
