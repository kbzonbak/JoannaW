package bbr.b2b.logistic.dvrdeliveries.interfaces;

import java.io.IOException;
import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDatingDocumentReportDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDetailDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDetailTotalDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryReportDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.PackingListReportDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.PackingListReportTotalDataDTO;
import bbr.b2b.logistic.report.classes.FileUploadErrorDTO;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryW;

public interface IDvrDeliveryServer extends IElementServer<DvrDelivery, DvrDeliveryW> {

	DvrDeliveryW addDvrDelivery(DvrDeliveryW dvrdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrDeliveryW[] getDvrDeliverys() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrDeliveryW updateDvrDelivery(DvrDeliveryW dvrdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException;
	Long getNextSequenceDvrDeliveryNumber() throws OperationFailedException, NotFoundException;
	DvrDeliveryW[] getDvrDeliveryByIds(Long[] dvrdeliveryids) throws OperationFailedException, NotFoundException;
	DvrDeliveryReportDataDTO getDvrDeliveryReportDataById(Long dvrdeliveryid, Long vendorid) throws AccessDeniedException, OperationFailedException;
	DvrDeliveryReportDataDTO[] getDvrDeliveryReport(Long vendorid, String searchvalue, Long deliverystatetypeid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated, int filtertype) throws AccessDeniedException, OperationFailedException;
	int getCountDvrDeliveryReport(Long vendorid, String searchvalue, Long deliverystatetypeid, int filtertype) throws AccessDeniedException, OperationFailedException;
	DvrDeliveryDetailDataDTO[] getDvrDeliveryDetailReport(Long vendorid, Long deliveryid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	DvrDeliveryDetailTotalDataDTO getCountDvrDeliveryDetailReport(Long vendorid, Long deliveryid) throws AccessDeniedException, OperationFailedException;
	PackingListReportDataDTO[] getPackingListReport(Long vendorid, Long dvrdeliveryid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	PackingListReportTotalDataDTO getCountPackingListReport(Long vendorid, Long dvrdeliveryid) throws AccessDeniedException, OperationFailedException;
	public DvrDeliveryDatingDocumentReportDataDTO[] getDvrDeliveryDatingDocumentReport(Long[] dvrdeliverystateids, Long vendorid, Long locationid, String filtervalue, LocalDateTime when, Integer criterium, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	int getCountDvrDeliveryDatingDocumentReport(Long[] dvrdeliverystateids, Long vendorid, Long locationid, String filtervalue, LocalDateTime when, Integer criterium) throws AccessDeniedException, OperationFailedException;
	DvrDeliveryW[] getDvrDeliveryByOrderIds(Long[] dvrorderids) throws OperationFailedException, NotFoundException; 	
	
	
	void doCreateAdjustTempTable();
	void doCreateAdjustTempErrorTable();
	int doLoadCSVAdjust(String filename) throws IOException;
	FileUploadErrorDTO[] getErrorsOfAdjust();
	Long[] getDvrOrderIdsFromAdjustUnitsData();
	int doFillOrderData();
	int doFillDestinationLocationData();
	int doFillItemData();
	int doFillDeliveryData(Long dvrdeliveryid);
	int doFillDvrOrderDeliveryDetailPosition(Long dvrdeliveryid);
	int doCheckUniqueRows();
	int doCheckOrders();
	int doCheckDestinationLocations();
	int doCheckItems();
	int doCheckDvrOrderDeliveryDetails();
	int doCheckUnitsEqualOrGreaterThanZero();
	int doCheckUnitsEqualOrLessThanPendingDeliveryDetails();
	int doCheckUnitsEqualOrLessThanPendingPredeliveryDetails();
	int doUpdateDvrOrderDeliveryDetails();
}
