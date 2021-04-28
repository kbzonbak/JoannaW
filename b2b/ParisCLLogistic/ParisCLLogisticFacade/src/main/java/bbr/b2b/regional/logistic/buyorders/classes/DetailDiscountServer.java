package bbr.b2b.regional.logistic.buyorders.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.DetailDiscountW;
import bbr.b2b.regional.logistic.buyorders.entities.DetailDiscount;
import bbr.b2b.regional.logistic.buyorders.entities.OrderDetail;
import bbr.b2b.regional.logistic.buyorders.entities.OrderDetailId;
import bbr.b2b.regional.logistic.buyorders.report.classes.DetailDiscountDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.DetailDiscountReportDataDTO;
import bbr.b2b.regional.logistic.utils.ClassUtilities;

@Stateless(name = "servers/buyorders/DetailDiscountServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DetailDiscountServer extends LogisticElementServer<DetailDiscount, DetailDiscountW> implements DetailDiscountServerLocal {

	private Map<String, String> mapGetDetailDiscountsSQL = new HashMap<String, String>();
	{
		mapGetDetailDiscountsSQL.put("DISCOUNTTYPE", "DISCOUNTTYPE");
		mapGetDetailDiscountsSQL.put("DESCRIPTION", "D.COMMENT");
		mapGetDetailDiscountsSQL.put("DISCOUNTPERCENTAGE", "DISCOUNTPERCENTAGE");
		mapGetDetailDiscountsSQL.put("DISCOUNTVALUE", "DISCOUNTVALUE");
				
	}		
	
	@EJB
	OrderDetailServerLocal orderdetailserver;

	public DetailDiscountW addDetailDiscount(DetailDiscountW detaildiscount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DetailDiscountW) addElement(detaildiscount);
	}
	public DetailDiscountW[] getDetailDiscounts() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DetailDiscountW[]) getIdentifiables();
	}
	public DetailDiscountW updateDetailDiscount(DetailDiscountW detaildiscount) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DetailDiscountW) updateElement(detaildiscount);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DetailDiscount entity, DetailDiscountW wrapper) {
		wrapper.setOrderid(entity.getOrderdetail().getId() != null ? new Long(entity.getOrderdetail().getId().getOrderid()) : new Long(0));
		wrapper.setItemid(entity.getOrderdetail().getId() != null ? new Long(entity.getOrderdetail().getId().getItemid()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DetailDiscount entity, DetailDiscountW wrapper) throws OperationFailedException, NotFoundException {
		if ((wrapper.getOrderid() != null && wrapper.getOrderid() > 0) && (wrapper.getItemid() != null && wrapper.getItemid() > 0)) {
			OrderDetailId key = new OrderDetailId();
			key.setOrderid(wrapper.getOrderid());
			key.setItemid(wrapper.getItemid());
			OrderDetail var = orderdetailserver.getReferenceById(key);
			if (var != null) { 
				entity.setOrderdetail(var);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DetailDiscount";
	}
	@Override
	protected void setEntityname() {
		entityname = "DetailDiscount";
	}
	
	
	public DetailDiscountDTO[] getDetailDiscountByOrders(Long[] orderids) throws OperationFailedException, NotFoundException {
		DetailDiscountDTO[] ocreport = null;
		int chunksize = 50;
		Object[] splitids = ClassUtilities.SplitArray(orderids, Long.class, chunksize);
		Object[] reportarray = new Object[splitids.length];
		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];
			if (ids.length <= 0) {
				reportarray[i] = new DetailDiscountDTO[0];
				continue;
			}
			SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.buyorders.entities.DetailDiscount.getDetailDiscountByOrders");
			query.setParameterList("orderids", ids);
			query.setResultTransformer(new LowerCaseResultTransformer(DetailDiscountDTO.class));
			List list = query.list();
			DetailDiscountDTO[] result = (DetailDiscountDTO[]) list.toArray(new DetailDiscountDTO[list.size()]);
			reportarray[i] = result;
		}
		ocreport = (DetailDiscountDTO[]) ClassUtilities.UnsplitArrays(reportarray, DetailDiscountDTO.class);
		return ocreport;

	}
	
	public DetailDiscountReportDataDTO[] getDetailDiscountsOfOrderDetail(Long orderid, Long itemid, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getDetailDiscountsQueryString(orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setLong("itemid", itemid);
		query.setResultTransformer(new LowerCaseResultTransformer(DetailDiscountReportDataDTO.class));
		
		List list = query.list();
		
		DetailDiscountReportDataDTO[] result = (DetailDiscountReportDataDTO[]) list.toArray(new DetailDiscountReportDataDTO[list.size()]);		
		return result;	
	}
	
	private String getDetailDiscountsQueryString(OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException{
		
		String SQL 					= "";
		String condicionOrderBy		= "";		
				
		SQL = "WITH DISCOUNTTABLES AS(SELECT * " +
									 "FROM LOGISTICA.DETAILDISCOUNT " +
									 "UNION " +
									 "SELECT *, " +
									 "NULL AS ITEM_ID " +
									 "FROM LOGISTICA.ORDERDISCOUNT) " +
			  "SELECT " +
				"CASE " +
					"WHEN DT.ITEM_ID IS NULL " +
					"THEN 'Orden' " + 
					"ELSE 'Detalle' " +
				"END AS DISCOUNTTYPE, " +
				"D.COMMENT AS DESCRIPTION, " +
				"CASE " +
					"WHEN PERCENTAGE = TRUE " +
					"THEN D.VALUE * 100 " + 
					"ELSE (CASE WHEN ODT.FINALPRICE = 0 THEN 0 ELSE D.VALUE / ODT.FINALPRICE * 100 END) " +
				"END AS DISCOUNTPERCENTAGE, " +
				"CASE " +
					"WHEN PERCENTAGE = FALSE " +
					"THEN D.VALUE " + 
					"ELSE ODT.FINALPRICE * D.VALUE " +
				"END AS DISCOUNTVALUE " +
			"FROM " +
				"LOGISTICA.ORDERDETAIL ODT JOIN " +
				"DISCOUNTTABLES DT ON DT.ORDER_ID = ODT.ORDER_ID AND (DT.ITEM_ID IS NULL OR DT.ITEM_ID = ODT.ITEM_ID) JOIN " +
				"LOGISTICA.DISCOUNT D ON D.ID = DT.ID " +
			"WHERE ODT.ORDER_ID = :orderid AND ODT.ITEM_ID = :itemid ";
		
		condicionOrderBy = getDetailDiscountsByString(mapGetDetailDiscountsSQL, orderby);				
		
		SQL = SQL +
				condicionOrderBy;		
		
		return SQL;		
	}
		
	private String getDetailDiscountsByString(Map<String, String> mapQuery, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		if (orderby == null || orderby.length == 0)
			return "";
		StringBuilder sb = new StringBuilder(" ORDER BY ");
		for (OrderCriteriaDTO ob : orderby) {
			String fieldname = mapQuery.get(ob.getPropertyname());
			if (fieldname == null)
				throw new AccessDeniedException("No se puede ordenar por el campo " + ob.getPropertyname());
			sb.append(fieldname);
			sb.append(ob.getAscending() ? " ASC, " : " DESC, ");
		}
		sb.delete(sb.length() - 2, sb.length());
		return sb.toString();
	}
}
