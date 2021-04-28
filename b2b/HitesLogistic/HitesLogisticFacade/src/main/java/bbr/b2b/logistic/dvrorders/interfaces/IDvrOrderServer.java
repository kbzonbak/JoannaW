package bbr.b2b.logistic.dvrorders.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderW;
import bbr.b2b.logistic.dvrorders.entities.DvrOrder;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderReportDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderLabelArrayResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderPdfDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderPdfDetailDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderReportDataDTO;

public interface IDvrOrderServer extends IElementServer<DvrOrder, DvrOrderW> {

	DvrOrderW addDvrOrder(DvrOrderW dvrorder) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrOrderW[] getDvrOrders() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrOrderW updateDvrOrder(DvrOrderW dvrorder) throws AccessDeniedException, OperationFailedException, NotFoundException;
	Integer doExpireDvrOrders() throws OperationFailedException;
	void doCalculateTotalDvrOrder(Long[] dvrorderids);
	DvrOrderW[] getDvrOrderByIds(Long[] dvrorderids) throws NotFoundException, OperationFailedException; 
	DvrOrderW[] getDvrOrderByIdsAndVendor(Long[] dvrorderids, Long vendorid) throws NotFoundException, OperationFailedException;
	DvrOrderW[] getDvrOrderByNumbers(Long[] dvrordernumbers) throws NotFoundException, OperationFailedException;
	DvrOrderReportDataDTO[] getDvrOrdersByVendorAndCriterium(Long vendorid, Long locationid, String filtervalue, Long dvrorderstatetypeid, LocalDateTime since, LocalDateTime until, Integer criterium, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	int getCountDvrOrdersByVendorAndCriterium(Long vendorid, Long locationid, String filtervalue, Long dvrorderstatetypeid, LocalDateTime since, LocalDateTime until, Integer criterium) throws AccessDeniedException, OperationFailedException;
	DvrOrderExcelReportResultDTO getDvrOrdersExcelReportByOrders(Long[] dvrorderids) throws OperationFailedException;
	int countDvrOrdersExcelReportByOrders(Long[] dvrorderids);
	DvhOrderExcelReportResultDTO getDvhOrdersExcelReportByOrders(Long[] dvhorderids) throws OperationFailedException;
	int countDvhOrdersExcelReportByOrders(Long[] dvhorderids);
	DvrOrderPdfDataDTO[] getDvrOrdersPdfDataByOrders(Long[] dvrorderids) throws OperationFailedException;
	DvrOrderLabelArrayResultDTO getDvrOrderLabelsByOrders(Long[] dvrorderids) throws OperationFailedException;
	int countDvrOrderLabelsByOrders(Long[] dvrorderids);
	Long[] getDvrOrderIdsByPages(Long vendorid, Long locationid, String filtervalue, Long dvrorderstatetypeid, LocalDateTime since, LocalDateTime until, Integer criterium, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageRangeDTO) throws OperationFailedException, AccessDeniedException;
	DvrOrderPdfDetailDTO[] getDvrOrdersPdfDetailByOrders(Long[] dvrorderids) throws OperationFailedException;
	int countDvrOrdersPdfDetailByOrders(Long[] dvrorderids);
	
	DvrOrderW[] getDvhOrderByIds(Long[] dvrorderids, Long dvhtypeid) throws NotFoundException, OperationFailedException;
	DvrOrderW[] getDvhOrderByIdsAndVendor(Long[] dvrorderids, Long vendorid, Long dvhtypeid) throws NotFoundException, OperationFailedException;
	DvhOrderReportDataDTO[] getDvhOrdersByVendorAndCriterium(Long vendorid, Long locationid, String filtervalue, Long dvrorderstatetypeid, LocalDateTime since, LocalDateTime until, Integer criterium, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	int getCountDvhOrdersByVendorAndCriterium(Long vendorid, Long locationid, String filtervalue, Long dvrorderstatetypeid, LocalDateTime since, LocalDateTime until, Integer criterium) throws AccessDeniedException, OperationFailedException;
	Long[] getDvhOrderIdsByPages(Long vendorid, Long locationid, String filtervalue, Long dvrorderstatetypeid, LocalDateTime since, LocalDateTime until, Integer criterium, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageRangeDTO) throws OperationFailedException, AccessDeniedException;
	
}
