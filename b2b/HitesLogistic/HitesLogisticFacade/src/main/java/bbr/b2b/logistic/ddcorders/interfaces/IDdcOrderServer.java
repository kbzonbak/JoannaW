package bbr.b2b.logistic.ddcorders.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderW;
import bbr.b2b.logistic.ddcorders.entities.DdcOrder;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderExcelReportResultDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderReportDataDTO;

public interface IDdcOrderServer extends IElementServer<DdcOrder, DdcOrderW> {

	DdcOrderW addDdcOrder(DdcOrderW ddcorder) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcOrderW[] getDdcOrders() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcOrderW updateDdcOrder(DdcOrderW ddcorder) throws AccessDeniedException, OperationFailedException, NotFoundException;
	void doCalculateTotalDdcOrders(Long[] ddcorderids);
	DdcOrderW[] getDdcOrderByIdsAndVendor(Long[] ddcorderids, Long vendorid) throws NotFoundException, OperationFailedException;
	DdcOrderW[] getDdcOrderByNumbers(Long[] ddcordernumbers) throws NotFoundException, OperationFailedException;
	DdcOrderReportDataDTO[] getDdcOrdersByVendorAndCriterium(Long vendorid, String filtervalue, LocalDateTime since, LocalDateTime until, Long ddcorderstatetypeid, Integer criterium, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	int countDdcOrdersByVendorAndCriterium(Long vendorid, String filtervalue, LocalDateTime since, LocalDateTime until, Long ddcorderstatetypeid, Integer criterium) throws AccessDeniedException, OperationFailedException;
	DdcOrderExcelReportResultDTO getDdcOrdersExcelReportByOrders(Long[] ddcorderids) throws OperationFailedException;
	public int countDdcOrdersExcelReportByOrders(Long[] ddcorderids);
	public Long[] getDdcOrderIdsByPages(Long vendorid, String filtervalue, LocalDateTime since, LocalDateTime until, Long ddcorderstatetypeid, Integer criterium, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageRangeDTO) throws OperationFailedException, AccessDeniedException;
	public DdcOrderW[] getOrdersToSendSoa(Long vendorid, LocalDateTime since, Long... soastateids) throws OperationFailedException, NotFoundException;
	public OrderW[] getDdcOrdersToSendSoa(Long vendorid, LocalDateTime since, Long... soastateids) throws OperationFailedException, NotFoundException;
}
