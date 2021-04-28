package bbr.b2b.regional.logistic.buyorders.classes;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.BlockedOrderW;
import bbr.b2b.regional.logistic.buyorders.entities.BlockedOrder;
import bbr.b2b.regional.logistic.buyorders.report.classes.BlockedOrderDTO;

@Stateless(name = "servers/buyorders/BlockedOrderServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BlockedOrderServer extends LogisticElementServer<BlockedOrder, BlockedOrderW> implements BlockedOrderServerLocal {


	public BlockedOrderW addBlockedOrder(BlockedOrderW blockedorder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BlockedOrderW) addElement(blockedorder);
	}
	public BlockedOrderW[] getBlockedOrders() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BlockedOrderW[]) getIdentifiables();
	}
	public BlockedOrderW updateBlockedOrder(BlockedOrderW blockedorder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BlockedOrderW) updateElement(blockedorder);
	}

	@Override
	protected void copyRelationsEntityToWrapper(BlockedOrder entity, BlockedOrderW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(BlockedOrder entity, BlockedOrderW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "BlockedOrder";
	}
	@Override
	protected void setEntityname() {
		entityname = "BlockedOrder";
	}
	
	public BlockedOrderDTO[] getBlockedOrdersByBlockingDate(Date since, Date until) {
		
		String SQL =
			"SELECT " + //
				"when1 AS blocked, " + //
				"username, " + //
				"useremail, " + //
				"usertype, " + //
				"ordernumber, " + //
				"vendorcode, " + //
				"vendorname, " + //
				"deliverylocationcode, " + //
				"deliverylocationname, " + //
				"destinationlocationcode, " + //
				"destinationlocationname, " + //
				"iteminternalcode, " + //
				"itemname " + //
			"FROM " + //
				"logistica.blockedorder " + //
			"WHERE " + //
				"when1 >= :since AND when1 < :until " + //
			"ORDER BY " + //
				"ordernumber";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		query.setResultTransformer(new LowerCaseResultTransformer(BlockedOrderDTO.class));
		
		List list = query.list();
		BlockedOrderDTO[] result = (BlockedOrderDTO[]) list.toArray(new BlockedOrderDTO[list.size()]);		
		return result;
	}
}