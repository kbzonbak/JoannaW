package bbr.b2b.regional.logistic.directorders.interfaces;

import java.util.Date;
import java.util.List;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderIdsByPagesW;
import bbr.b2b.regional.logistic.buyorders.report.classes.PDFVeVPDOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDExcelOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVVendorNotificationDTO;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderW;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrder;
import bbr.b2b.regional.logistic.directorders.report.classes.DirectOrderReportDTO;
import bbr.b2b.regional.logistic.directorders.report.classes.VeVPDDataDTO;
import bbr.b2b.regional.logistic.report.classes.PendingSOAOrderDTO;

public interface IDirectOrderServer extends IElementServer<DirectOrder, DirectOrderW> {

	DirectOrderW addDirectOrder(DirectOrderW directorder) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DirectOrderW[] getDirectOrders() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DirectOrderW updateDirectOrder(DirectOrderW directorder) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DirectOrderReportDTO[] getDirectOrderReportByVendorAndNumber(Long ordernumber, Long vendorid) throws ServiceException;
	VeVPDOrderReportDataDTO[] getVeVPDOrdersByVendorAndCriterium(Long vendorid, Long ocnumber, Long orderstatetypeid, String requestnumber, String clientrut, Date since, Date until, Integer criterium, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated, Long[] salestoresid, String sendnumber) throws ServiceException;
	VeVPDOrderReportTotalDataDTO getVeVPDOrdersCountByVendorAndCriterium(Long vendorid, Long ocnumber, Long orderstatetypeid, String requestnumber, String clientrut, Date since, Date until, Integer criterium, Long[] salestoresid, String sendnumber) throws ServiceException;
	int getDirectOrdersReportCountByOrders(Long[] orderIds);
	VeVPDExcelOrderReportDataDTO[] getExcelVeVPDOrderReportByOrders(Long[] orderids) throws ServiceException;
	OrderIdsByPagesW getVeVPDOrdersIdsByPages(Long vendorid, Long ocnumber, Long ostid, String requestnumber, String clientrut, Date since, Date until, int rows, Integer filterType, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges, Long[] salestoresid, String sendnumber) throws ServiceException;
	Integer doReceiveDirectOrders() throws OperationFailedException;
	DirectOrderW[] getOrdersToSendSoa(Long vendorid, Date since, Long... soastateids) throws OperationFailedException, NotFoundException;
	PendingSOAOrderDTO[] getPendingSOAOrdersByVendor(Long soastatetype, Long vendorid, Date soapendingtime, Date activationdate);
	VeVPDDataDTO getVeVPDDataByDirectOrderNumber(Long directordernumber);	
	PDFVeVPDOrderReportDataDTO[] getPDFVeVPDOrder(Long vendorid, Long ocnumber) throws ServiceException;
	PDFVeVPDOrderReportDataDTO[] getPDFVeVPDOrderByIDs(Long vendorid, List<Long> ocnumber) throws ServiceException;
	List<VeVVendorNotificationDTO> getDirectOrdersByVendorsAndReleasedDate(Long[] vendorIds, Date since, Date until);
	List<DirectOrderW> getByDOTaxDocumentTicket(Long ticketid);
}
