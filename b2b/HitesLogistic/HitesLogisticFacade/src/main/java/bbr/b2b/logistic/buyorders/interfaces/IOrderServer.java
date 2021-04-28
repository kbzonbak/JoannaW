package bbr.b2b.logistic.buyorders.interfaces;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.buyorders.entities.Order;
import bbr.b2b.logistic.report.classes.PendingSOAOrderDTO;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderW;

public interface IOrderServer extends IElementServer<Order, OrderW> {

	OrderW addOrder(OrderW order) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderW[] getOrders() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OrderW updateOrder(OrderW order) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public OrderW[] getOrdersToSendSoa(Long vendorid, LocalDateTime since, Long... soastateids) throws OperationFailedException, NotFoundException;
	public OrderW[] getOrdersToSendSoa(ArrayList<Long> docs, Long vendorid, LocalDateTime since, Long... soastateids) throws OperationFailedException, NotFoundException;
	public PendingSOAOrderDTO[] getPendingSOAOrdersByVendor(Long soastatetype, Long vendorid, Date soapendingtime, Date activationdate);
	public PendingSOAOrderDTO[] getPendingSOADdcOrdersByVendor(Long soastatetype, Long vendorid, Date soapendingtime, Date activationdate);

}
 