package bbr.b2b.logistic.customer.interfaces;

import java.text.ParseException;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.buyorders.data.dto.HistoryDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.HistoryHeaderDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportDataDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportInitParamDTO;
import bbr.b2b.logistic.customer.data.wrappers.OrderW;
import bbr.b2b.logistic.customer.entities.Order;

public interface IOrderServer extends IElementServer<Order, OrderW> {

	OrderW addOrder(OrderW order) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderW[] getOrders() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderW updateOrder(OrderW order) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	
	public OrderReportDataDTO[] getOrdersByVendorAndCriterium(Long vendorid, Long clientid, OrderReportInitParamDTO initparam) throws AccessDeniedException, OperationFailedException, ParseException;
	public int getCountOrdersByVendorAndCriterium(Long vendorid, Long clientid, OrderReportInitParamDTO initparam) throws AccessDeniedException, OperationFailedException, ParseException;
	
	
	public HistoryHeaderDTO getHistoryHeader(String ocnumber, Long vendorid, Long clientid);
	public HistoryDetailDTO[] getHistoryDetail(String ocnumber, Long vendorid, Long clientid);
	public int getCountHistoryDetail(String ocnumber, Long vendorid, Long clientid);

}
