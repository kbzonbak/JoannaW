package bbr.b2b.logistic.buyorders.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.logistic.buyorders.entities.OrderType;

@Stateless(name = "servers/buyorders/OrderTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderTypeServer extends LogisticElementServer<OrderType, OrderTypeW> implements OrderTypeServerLocal {


	public OrderTypeW addDvrOrderType(OrderTypeW dvrordertype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderTypeW) addElement(dvrordertype);
	}
	public OrderTypeW[] getDvrOrderTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderTypeW[]) getIdentifiables();
	}
	public OrderTypeW updateDvrOrderType(OrderTypeW dvrordertype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderTypeW) updateElement(dvrordertype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OrderType entity, OrderTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(OrderType entity, OrderTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	
	public OrderTypeW[] getOrderTypeByDvrOrders(Long[] dvrorderids) throws NotFoundException, OperationFailedException {
		
		OrderTypeW[] result = null;
		List<OrderTypeW> resultList = new ArrayList<OrderTypeW>();
		
		List<Long> dvrorderidsList = Arrays.asList(dvrorderids);
		
		String queryStr = 	"select distinct ot " + //
							"from  DvrOrder as dvroc," + //
							"Order as oc, " + //
							"OrderType as ot " + //
							"where " + //
							"dvroc.id = oc.id " + //
							"and ot.id = oc.ordertype.id " + //
							"and dvroc.id in (:dvrorderids) ";
	
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("dvroc.id", "dvrorderids", dvrorderidsList);
			
		resultList = getByQuery(queryStr, properties);
		result =(OrderTypeW[]) resultList.toArray(new OrderTypeW[resultList.size()]);
		
		return result;
	}
	
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "OrderType";
	}
	@Override
	protected void setEntityname() {
		entityname = "OrderType";
	}
}
