package bbr.b2b.regional.logistic.buyorders.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.BlockedOrderW;
import bbr.b2b.regional.logistic.buyorders.entities.BlockedOrder;
import bbr.b2b.regional.logistic.buyorders.report.classes.BlockedOrderDTO;

public interface IBlockedOrderServer extends IElementServer<BlockedOrder, BlockedOrderW> {

	BlockedOrderW addBlockedOrder(BlockedOrderW blockedorder) throws AccessDeniedException, OperationFailedException, NotFoundException;
	BlockedOrderW[] getBlockedOrders() throws AccessDeniedException, OperationFailedException, NotFoundException;
	BlockedOrderW updateBlockedOrder(BlockedOrderW blockedorder) throws AccessDeniedException, OperationFailedException, NotFoundException;
	BlockedOrderDTO[] getBlockedOrdersByBlockingDate(Date since, Date until);
}
