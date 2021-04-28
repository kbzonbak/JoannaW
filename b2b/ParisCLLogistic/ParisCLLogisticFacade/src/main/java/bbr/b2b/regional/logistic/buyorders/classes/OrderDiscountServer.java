package bbr.b2b.regional.logistic.buyorders.classes;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDiscountW;
import bbr.b2b.regional.logistic.buyorders.entities.Order;
import bbr.b2b.regional.logistic.buyorders.entities.OrderDiscount;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDiscountDTO;
import bbr.b2b.regional.logistic.utils.ClassUtilities;

@Stateless(name = "servers/buyorders/OrderDiscountServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderDiscountServer extends LogisticElementServer<OrderDiscount, OrderDiscountW> implements OrderDiscountServerLocal {


	@EJB
	OrderServerLocal orderserver;

	public OrderDiscountW addOrderDiscount(OrderDiscountW orderdiscount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDiscountW) addElement(orderdiscount);
	}
	public OrderDiscountW[] getOrderDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDiscountW[]) getIdentifiables();
	}
	public OrderDiscountW updateOrderDiscount(OrderDiscountW orderdiscount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDiscountW) updateElement(orderdiscount);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OrderDiscount entity, OrderDiscountW wrapper) {
		wrapper.setOrderid(entity.getOrder() != null ? new Long(entity.getOrder().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(OrderDiscount entity, OrderDiscountW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOrderid() != null && wrapper.getOrderid() > 0) { 
			Order order = orderserver.getReferenceById(wrapper.getOrderid());
			if (order != null) { 
				entity.setOrder(order);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "OrderDiscount";
	}
	@Override
	protected void setEntityname() {
		entityname = "OrderDiscount";
	}
	
	public OrderDiscountDTO[] getOrderDiscountByOrders(Long[] orderids) throws OperationFailedException, NotFoundException {
		OrderDiscountDTO[] ocreport = null;
		int chunksize = 50;
		Object[] splitids = ClassUtilities.SplitArray(orderids, Long.class, chunksize);
		Object[] reportarray = new Object[splitids.length];
		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];
			if (ids.length <= 0) {
				reportarray[i] = new OrderDiscountDTO[0];
				continue;
			}
			SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.buyorders.entities.OrderDiscount.getOrderDiscountByOrders");
			query.setParameterList("orderids", ids);
			query.setResultTransformer(new LowerCaseResultTransformer(OrderDiscountDTO.class));
			List list = query.list();
			OrderDiscountDTO[] result = (OrderDiscountDTO[]) list.toArray(new OrderDiscountDTO[list.size()]);
			reportarray[i] = result;
		}
		ocreport = (OrderDiscountDTO[]) ClassUtilities.UnsplitArrays(reportarray, OrderDiscountDTO.class);
		return ocreport;

	}
	
	
	
}
