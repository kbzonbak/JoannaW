package bbr.b2b.logistic.customer.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.buyorders.data.dto.HeaderOrderDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderPreDeliveryDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderProductDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.TotalPredeliveryDTO;
import bbr.b2b.logistic.buyorders.data.dto.TotalProductsDTO;
import bbr.b2b.logistic.customer.data.wrappers.DetailW;
import bbr.b2b.logistic.customer.entities.Detail;
import bbr.b2b.logistic.customer.entities.DetailId;

@Stateless(name = "servers/customer/DetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DetailServer extends BaseLogisticEJB3Server<Detail, DetailId, DetailW> implements DetailServerLocal {

	private Map<String, String> mapGetOrderDetailSQL = new HashMap<String, String>();
	{
		mapGetOrderDetailSQL.put("OCNUMBER", "oc.number");
		mapGetOrderDetailSQL.put("POS", "od.position");
		mapGetOrderDetailSQL.put("CODPRODUCTO", "od.sku_buyer");
		mapGetOrderDetailSQL.put("CODPRODUCTOPROVEEDOR", "od.skuvendor");
		mapGetOrderDetailSQL.put("DESCRIPCION", "od.product_description");
		mapGetOrderDetailSQL.put("UMC", "od.code_umc");
		mapGetOrderDetailSQL.put("UMBXUMC", "od.umb_x_umc");
		mapGetOrderDetailSQL.put("COSTOUNITARIO", "od.final_cost");
		mapGetOrderDetailSQL.put("UMBXUMC", "od.umbxumc");
		mapGetOrderDetailSQL.put("SOLICITADO", "od.quantity_umc");
		mapGetOrderDetailSQL.put("COSTOFINAL", "od.final_cost");
		

	}
	
	public DetailW addDetail(DetailW detail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DetailW) addIdentifiable(detail);
	}
	public DetailW[] getDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DetailW[]) getIdentifiables();
	}
	public DetailW updateDetail(DetailW detail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DetailW) updateIdentifiable(detail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Detail entity, DetailW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Detail entity, DetailW wrapper) throws OperationFailedException, NotFoundException {
	}
	
	private String getProductOrderDetailQueryString(int queryType, OrderCriteriaDTO[] orderby, String where) throws AccessDeniedException{
		
		String SQL = "";
		String sqlSelect = "";
		String sqlFrom = "";
		String sqlWhere = "";
		String orderByCondition = ""; 
		
		
		switch (queryType) {
		case 1:
			sqlSelect = 	"SELECT CAST(COUNT(oc.id) AS INTEGER) AS totalregs, " +//
							" SUM(od.final_cost) as costototal ";
			sqlFrom = 	"from logistica.order as oc " + // 
					"left join logistica.detail as od " + //  
					"on od.order_id = oc.id " + //
					"left join logistica.vendor as v on oc.vendor_id = v.id ";
			break;
		case 2:
			sqlSelect =		"	select " + // 
					"	CAST(oc.number as VARCHAR) as ocnumber, " + //
					"	od.position as pos, " + //
					"	od.sku_buyer as codproducto, " + //
					"	od.skuvendor as codprodproveedor, " + //
					"	od.product_description as descripcion, " + //
					"	od.code_umc as umc, " + //
					"	od.umb_x_umc as umbxumc, " + //
					"	od.final_cost as costounitario, " + //
					"	od.quantity_umc as solicitado, " + //
					"	od.final_cost as costofinal ";
			sqlFrom = 	"from logistica.order as oc " + // 
					"left join logistica.detail as od " + //  
					"on od.order_id = oc.id " + //
					"left join logistica.vendor as v on oc.vendor_id = v.id ";
	
					orderByCondition = getOrderByString(mapGetOrderDetailSQL, orderby);	
			break;
		case 3:
			sqlSelect = 	"select " + //
							"CAST(oc.id as VARCHAR) as codlocal, " + //
							"loc.name as nombrelocal, " + //
							"od.sku_buyer as codproducto, " + //
							"od.skuvendor as codprodproveedor, " + //
							"od.product_description as descripcion, " + //
							"od.quantity_umc as solicitado ";
					
			sqlFrom =		"from logistica.order as oc " + //
							"left join logistica.detail as od on " + //
							"oc.id = od.order_id " + //
							"left join logistica.location as loc on " + //
							"loc.id = oc.delivery_place_id " + //
							"left join logistica.predistribution as pred on " + //
							"pred.order_id = oc.id ";
			
							
			
					orderByCondition = getOrderByString(mapGetOrderDetailSQL, orderby);
			break;
		case 4:
			sqlSelect = 	" SELECT CAST(COUNT(oc.id) AS INTEGER) AS totalregs, " +//
								" CAST(SUM(od.quantity_umc) AS INTEGER) as totalquantity ";
					
			sqlFrom =		"from logistica.order as oc " + //
							"left join logistica.detail as od on " + //
							"oc.id = od.order_id " + //
							"left join logistica.location as loc on " + //
							"loc.id = oc.delivery_place_id " + //
							"left join logistica.predistribution as pred on " + //
							"pred.order_id = oc.id ";

		default:
			break;
		}
			
			sqlWhere = 	"WHERE oc.number = :ocnumber " + //
					"AND oc.vendor_id = :vendorid ";

		SQL  = 
				sqlSelect + //
				sqlFrom + //
				sqlWhere + //
				where + //
				orderByCondition;
		
		return SQL;
	}
	
	
	
	public OrderProductDetailDTO[] getProductOrdersDetails(Long ocnumber, Long vendorid, Long clientid,  int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException {
		String where = " AND oc.client_id =:clientid";
		String SQL = getProductOrderDetailQueryString(2, orderby, !clientid.equals(-1L) ? where : "" );
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("ocnumber", ocnumber);
		//TODO map
		if(!clientid.equals(-1L)){
			query.setLong("clientid", clientid);
		}
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		query.setResultTransformer(new LowerCaseResultTransformer(OrderProductDetailDTO.class));
		query.getMaxResults();
		List<?> list = query.list();
		OrderProductDetailDTO[] result = (OrderProductDetailDTO[]) list.toArray(new OrderProductDetailDTO[list.size()]);		
		return result;	
	}
	
	public TotalProductsDTO getCountProductDetailsByOrder(Long ocnumber, Long vendorid, Long clientid) throws AccessDeniedException{
		String where = " AND oc.client_id =:clientid";
		String SQL = getProductOrderDetailQueryString(1, null, !clientid.equals(-1L) ? where : "" );
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("ocnumber", ocnumber);
		query.setLong("vendorid", vendorid);	
		if(!clientid.equals(-1L)){
			query.setLong("clientid", clientid);
		}
		query.setResultTransformer(new LowerCaseResultTransformer(TotalProductsDTO.class));
		TotalProductsDTO total = ((TotalProductsDTO) query.uniqueResult());
		return total;
	}
	
	public TotalPredeliveryDTO getCountPredeliveryDetailsByOrder(Long ocnumber, Long vendorid, Long clientid) throws AccessDeniedException{
		String where = " AND oc.client_id =:clientid";
		String SQL = getProductOrderDetailQueryString(4, null, !clientid.equals(-1L) ? where : "" );
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("ocnumber", ocnumber);
		query.setLong("vendorid", vendorid);	
		if(!clientid.equals(-1L)){
			query.setLong("clientid", clientid);
		}
		query.setResultTransformer(new LowerCaseResultTransformer(TotalPredeliveryDTO.class));
		TotalPredeliveryDTO total = ((TotalPredeliveryDTO) query.uniqueResult());
		return total;
	}
	
	
	private String getOrderByString(Map<String, String> mapQuery, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
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
	
	
	public String getOrderDetailHeaderQueryString(String where){
		
		String SQL = "";
		String sqlSelect = "";
		String sqlFrom = "";
		
		
			sqlSelect =		"SELECT " + //  
							" CAST(oc.number as VARCHAR) as ocnumber, " + // 
							" ost.name as state, " + // 
							" to_char(oc.issue_date, 'YYYY-MM-DD') as emitted, " + // 
							" to_char(oc.created, 'YYYY-MM-DD') as receptiondate, " + // 
							" ot.name as type, " +//
							" loc.code as locationcode, " + // 
							" loc.name as deliverylocation," +//
							" cl.name as clientname  ";
							
			
			sqlFrom = "from logistica.order as oc " +//
					"left join logistica.detail as od on " +//
					"oc.id = od.order_id " +//
					"left join logistica.ordertype as ot on " +//
					"ot.id = oc.ordertype_id " +//
					"left join logistica.client as cl on " +//
					"oc.client_id = cl.id " +//
					"left join logistica.location as loc on " +//
					"oc.delivery_place_id = loc.id " +//
					"left join logistica.orderstatetype as ost on " +//
					"ost.id = oc.orderstatetype_id ";
		SQL  = 
				sqlSelect + //
				sqlFrom + //
				where;
		
		return SQL;
	}
	public HeaderOrderDetailDTO[] getHeaderOrderDetail(String ocnumber, Long vendorid, Long clientid){
		String clientCondition ="";
		if(!clientid.equals(-1L)){
			clientCondition = "AND oc.client_id = :client";
		}
		String sqlWhere = 	"WHERE oc.number = :ocnumber AND oc.vendor_id = :vendorid "+ clientCondition;
		
		String SQL = getOrderDetailHeaderQueryString(sqlWhere);
			
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		
		if(!clientid.equals(-1L)){
			query.setLong("clientid", clientid);
		}
		query.setLong("vendorid", vendorid);
		query.setLong("ocnumber", Long.valueOf(ocnumber));
		
		query.setResultTransformer(new LowerCaseResultTransformer(HeaderOrderDetailDTO.class));
		List<?> list = query.list();
		return list.toArray(new HeaderOrderDetailDTO[list.size()]);
	}
	
	public OrderPreDeliveryDetailDTO[] getPredeliveryOrderDetails(Long ocnumber, Long vendorid, Long clientid, int pagenumber, int rows,OrderCriteriaDTO[] orderby, boolean ispaginated, List<String> productos) throws AccessDeniedException{
		String where = " AND oc.client_id =:clientid AND od.sku_buyer in (:productos) ";
		String SQL = getProductOrderDetailQueryString(3, orderby, !clientid.equals(-1L) ? where : " AND od.sku_buyer in (:productos) " );
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("ocnumber", ocnumber);
		query.setParameterList("productos", productos);
		
		if(!clientid.equals(-1L)){
			query.setLong("clientid", clientid);
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(OrderPreDeliveryDetailDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List<?> list = query.list();
		OrderPreDeliveryDetailDTO[] result = (OrderPreDeliveryDetailDTO[]) list.toArray(new OrderPreDeliveryDetailDTO[list.size()]);		
		return result;
		
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Detail";
	}
	@Override
	protected void setEntityname() {
		entityname = "Detail";
	}
}
