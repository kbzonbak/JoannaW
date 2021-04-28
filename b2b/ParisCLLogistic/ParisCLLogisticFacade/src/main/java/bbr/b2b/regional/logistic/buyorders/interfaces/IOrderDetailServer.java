package bbr.b2b.regional.logistic.buyorders.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Set;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDetailW;
import bbr.b2b.regional.logistic.buyorders.entities.OrderDetail;
import bbr.b2b.regional.logistic.buyorders.entities.OrderDetailId;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDetailFillRateDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderDetailReportTotalDataDTO;
public interface IOrderDetailServer extends IGenericServer<OrderDetail, OrderDetailId, OrderDetailW> {

	OrderDetailW addOrderDetail(OrderDetailW orderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDetailW[] getOrderDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDetailW updateOrderDetail(OrderDetailW orderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderDetailW[] getOrderDetailsByOrderIds(List<Long> orderids) throws OperationFailedException, NotFoundException;
	boolean doValidateOrderATCs(Long orderid, Set<String> itemcodes);
	boolean doValidateOrderSKUs(Long orderid, List<String> itemcodes);
	OrderDetailReportDataDTO[] getOrdersDetailsByOrder(Long orderid, Long vendorid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException;
	OrderDetailReportTotalDataDTO getCountOrdersDetailsByOrder(Long orderid, Long vendorid) throws ServiceException;
	VeVCDOrderDetailReportDataDTO[] getVeVCDOrdersDetailsByOrder(Long orderid, Long vendorid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException;
	VeVCDOrderDetailReportTotalDataDTO getCountVeVCDOrdersDetailsByOrder(Long orderid,  Long vendorid) throws ServiceException;
	FileDownloadInfoResultDTO getCSVOrderVeVReport(Long[] salestoreid, Long vendorid, Long orderstatetypeid, Date since, Date until, Long locationid, int filtertype, Long userId) throws OperationFailedException, NotFoundException;
	int countCSVOrderVeVReport(Long[] salestoreid, Long vendorid, Long orderstatetypeid, Date since, Date until, Long locationid, int filtertype);
	OrderDetailFillRateDTO[] getOrderDetailFillrate(Date since, Date until) throws ServiceException;
	
}
