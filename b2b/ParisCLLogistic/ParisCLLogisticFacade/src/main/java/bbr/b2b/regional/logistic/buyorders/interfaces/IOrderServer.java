package bbr.b2b.regional.logistic.buyorders.interfaces;

import java.util.Date;
import java.util.List;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.buyorders.entities.Order;
import bbr.b2b.regional.logistic.buyorders.report.classes.ExcelOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderIdsByPagesW;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.SchedulePendingOrderDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDExcelOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVVendorNotificationDTO;
import bbr.b2b.regional.logistic.report.classes.PendingSOAOrderDTO;

public interface IOrderServer extends IElementServer<Order, OrderW> {

	OrderW addOrder(OrderW order) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderW[] getOrders() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderW updateOrder(OrderW order) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	OrderW[] getOrdersByIds(List<Long> orderids) throws OperationFailedException, NotFoundException;
	Integer doExpireBuyOrders() throws OperationFailedException;
	OrderW[] getOrdersByIdsAndVendor(Long[] orderIds, Long vendorId) throws OperationFailedException, NotFoundException;
	OrderW[] getOrdersByDelivery(Long deliveryid) throws OperationFailedException, NotFoundException;
	
	OrderReportDataDTO[] getOrdersByVendorLocationAndCriterium(Long vendorid, Long originalvendorid, Long locationid, Date now, Long ocnumber, Long orderstatetypeid, Date since, Date until, Integer criterium, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException;
	OrderReportTotalDataDTO getOrdersCountByVendorLocationAndCriterium(Long vendorid, Long originalvendorid, Long locationid, Date now, Long ocnumber, Long orderstatetypeid, Date since, Date until, Integer criterium) throws ServiceException;
	SchedulePendingOrderDTO[] getSchedulePendingOrdersByValidStateDate(Date since, Date until);
	VeVCDOrderReportDataDTO[] getVeVCDOrdersByVendorLocationAndCriterium(Long[] salestoreid, Long vendorid, Long locationid, Date now, Long ocnumber, Long orderstatetypeid, Date since, Date until, String requestnumber, Integer criterium, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException;
	VeVCDOrderReportTotalDataDTO getVeVCDOrdersCountByVendorLocationAndCriterium(Long[] salestoreid, Long vendorid, Long locationid, Date now, Long ocnumber, Long orderstatetypeid, Date since, Date until, String requestnumber, Integer criterium) throws ServiceException;
	
	int getOrdersReportCountByOrders(Long[] orderIds);
	ExcelOrderReportDataDTO[] getExcelOrderReportByOrders(Long[] orderids) throws ServiceException;
	OrderIdsByPagesW getOrdersIdsByPages(Long vendorid, Long originalvendorid, Long locationid, Date now, Long ocnumber, Long ostid, Date since, Date until, int rows, Integer filterType, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws ServiceException;

	VeVCDExcelOrderReportDataDTO[] getExcelVeVCDOrderReportByOrders(Long[] orderids) throws ServiceException;
	OrderIdsByPagesW getVeVCDOrdersIds(Long[] salestoreid, Long vendorid, Long locationid, Date now, Long ocnumber, Long ostid, Date since, Date until, String requestnumber, Integer filterType, OrderCriteriaDTO[] orderby) throws ServiceException;
	OrderIdsByPagesW getVeVCDOrdersIdsByPages(Long[] salestoreid, Long vendorid, Long locationid, Date now, Long ocnumber, Long ostid, Date since, Date until, String requestnumber, int rows, Integer filterType, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws ServiceException;
		
	OrderW[] getOrdersToSendSoa(Long vendorid, Date since, Long... soastateids) throws OperationFailedException, NotFoundException;
	PendingSOAOrderDTO[] getPendingSOAOrdersByVendor(Long soastatetype, Long vendorid, Date soapendingtime, Date activationdate);
	
	VeVCDDataDTO getVeVCDDataByOrderNumber(Long ordernumber);
	PendingSOAOrderDTO[] getPendingSOAOrdersByVendor(Long soastatetype, Long vendorid, Date soapendingtime, Date activationdate, int type);	

	List<VeVVendorNotificationDTO> getOrdersByVendorsAndReleasedDate(Long[] vendorIds, Date since, Date until);	
}
