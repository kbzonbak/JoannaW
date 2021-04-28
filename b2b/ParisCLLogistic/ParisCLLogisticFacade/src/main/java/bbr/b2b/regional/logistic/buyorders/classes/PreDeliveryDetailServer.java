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
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.PreDeliveryDetailW;
import bbr.b2b.regional.logistic.buyorders.entities.OrderDetail;
import bbr.b2b.regional.logistic.buyorders.entities.OrderDetailId;
import bbr.b2b.regional.logistic.buyorders.entities.PreDeliveryDetail;
import bbr.b2b.regional.logistic.buyorders.entities.PreDeliveryDetailId;
import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PredeliveryDetailDTO;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.utils.ClassUtilities;

@Stateless(name = "servers/buyorders/PreDeliveryDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PreDeliveryDetailServer extends BaseLogisticEJB3Server<PreDeliveryDetail, PreDeliveryDetailId, PreDeliveryDetailW> implements PreDeliveryDetailServerLocal {

	private Map<String, String> mapGetPreDeliveryDetailSQL = new HashMap<String, String>();
	{
		mapGetPreDeliveryDetailSQL.put("LOCATIONCODE", "LO.CODE");
		mapGetPreDeliveryDetailSQL.put("LOCATIONDESC", "LO.NAME");
		mapGetPreDeliveryDetailSQL.put("SKU", "IT.INTERNALCODE");
		mapGetPreDeliveryDetailSQL.put("VENDORITEMCODE", "VI.VENDORITEMCODE");
		mapGetPreDeliveryDetailSQL.put("ITEMDESC", "IT.NAME");
		mapGetPreDeliveryDetailSQL.put("TOTALUNITS", "PD.UNITS");
		mapGetPreDeliveryDetailSQL.put("PENDINGUNITS", "PD.PENDINGUNITS");
		mapGetPreDeliveryDetailSQL.put("RECEIVEDUNITS", "PD.RECEIVEDUNITS");
		mapGetPreDeliveryDetailSQL.put("TODELIVERYUNITS", "PD.TODELIVERYUNITS");
		mapGetPreDeliveryDetailSQL.put("OUTRECEIVEDUNITS", "PD.OUTRECEIVEDUNITS");
		mapGetPreDeliveryDetailSQL.put("TOTALAMOUNT", "PD.TOTALNEED");
		mapGetPreDeliveryDetailSQL.put("TOTALPENDING", "PD.TOTALPENDING");
		mapGetPreDeliveryDetailSQL.put("TOTALRECEIVED", "PD.TOTALRECEIVED");
		mapGetPreDeliveryDetailSQL.put("TOTALTODELIVERY", "PD.TOTALTODELIVERY");	
	}	

	@EJB
	OrderDetailServerLocal orderdetailserver;

	@EJB
	LocationServerLocal locationserver;

	public PreDeliveryDetailW addPreDeliveryDetail(PreDeliveryDetailW predeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PreDeliveryDetailW) addIdentifiable(predeliverydetail);
	}
	public PreDeliveryDetailW[] getPreDeliveryDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PreDeliveryDetailW[]) getIdentifiables();
	}
	
	public PreDeliveryDetailW updatePreDeliveryDetail(PreDeliveryDetailW predeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PreDeliveryDetailW) updateIdentifiable(predeliverydetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(PreDeliveryDetail entity, PreDeliveryDetailW wrapper) {
		wrapper.setOrderid(entity.getOrderdetail().getId() != null ? new Long(entity.getOrderdetail().getId().getOrderid()) : new Long(0));
		wrapper.setItemid(entity.getOrderdetail().getId() != null ? new Long(entity.getOrderdetail().getId().getItemid()) : new Long(0));
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(PreDeliveryDetail entity, PreDeliveryDetailW wrapper) throws OperationFailedException, NotFoundException {
		if ((wrapper.getOrderid() != null && wrapper.getOrderid() > 0) && (wrapper.getItemid() != null && wrapper.getItemid() > 0)) {
			OrderDetailId key = new OrderDetailId();
			key.setOrderid(wrapper.getOrderid());
			key.setItemid(wrapper.getItemid());
			OrderDetail var = orderdetailserver.getReferenceById(key);
			if (var != null) { 
				entity.setOrderdetail(var);
			}
		}
		if (wrapper.getLocationid() != null && wrapper.getLocationid() > 0) { 
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if (location != null) { 
				entity.setLocation(location);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "PreDeliveryDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "PreDeliveryDetail";
	}

	public PreDeliveryDetailW[] getPreDeliveryDetailsByOrderIds(List<Long> orderids) throws OperationFailedException, NotFoundException {
		
		StringBuilder sb = new StringBuilder(
									"SELECT DISTINCT " + //
										"pdd " + //
									"FROM " + //
										"PreDeliveryDetail AS pdd " + //
									"WHERE " + //
										"pdd.orderdetail.order.id IN (:orderids)");
        
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("order.id", "orderids", orderids);

        List list = getByQuery(sb.toString(), properties);

        return (PreDeliveryDetailW[]) list.toArray(new PreDeliveryDetailW[list.size()]);
	}
	
	public PreDeliveryDetailW[] getPreDeliveryDetailsOfOrder(Long ordernumber) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = "SELECT PDD.SEQUENCE, " +
						"PDD.UNITS, " +
						"PDD.PENDINGUNITS, " +
						"PDD.RECEIVEDUNITS, " +
						"PDD.TODELIVERYUNITS, " +
						"PDD.OUTRECEIVEDUNITS, " +
						"PDD.TOTALNEED, " +
						"PDD.TOTALPENDING, " +
						"PDD.TOTALRECEIVED, " +
						"PDD.TOTALTODELIVERY, " +
						"PDD.LOCATION_ID AS LOCATIONID, " +
						"PDD.ORDER_ID AS ORDERID, " +
						"PDD.ITEM_ID AS ITEMID " +
						"FROM LOGISTICA.PREDELIVERYDETAIL AS PDD " +
						"JOIN LOGISTICA.ORDER AS O ON PDD.ORDER_ID = O.ID " +
						"WHERE O.NUMBER = :ordernumber";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("ordernumber", ordernumber);
		query.setResultTransformer(new LowerCaseResultTransformer(PreDeliveryDetailW.class));

		List list = query.list();
		return (PreDeliveryDetailW[]) list.toArray(new PreDeliveryDetailW[list.size()]);
	}
	
	public PreDeliveryDetailW[] getPreDeliveryDetailsOfOrderByFlowType(Long orderid, Long flowtypeid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = "SELECT PDD.SEQUENCE, " +
						"PDD.UNITS, " +
						"PDD.PENDINGUNITS, " +
						"PDD.RECEIVEDUNITS, " +
						"PDD.TODELIVERYUNITS, " +
						"PDD.OUTRECEIVEDUNITS, " +
						"PDD.TOTALNEED, " +
						"PDD.TOTALPENDING, " +
						"PDD.TOTALRECEIVED, " +
						"PDD.TOTALTODELIVERY, " +
						"PDD.LOCATION_ID AS LOCATIONID, " +
						"PDD.ORDER_ID AS ORDERID, " +
						"PDD.ITEM_ID AS ITEMID " +
						"FROM LOGISTICA.PREDELIVERYDETAIL AS PDD " +
						"JOIN LOGISTICA.ORDER AS O ON PDD.ORDER_ID = O.ID " +
						"JOIN LOGISTICA.ITEM AS IT ON PDD.ITEM_ID = IT.ID " +
						"WHERE O.ID = :orderid AND IT.FLOWTYPE_ID = :flowtypeid";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setLong("flowtypeid", flowtypeid);
		query.setResultTransformer(new LowerCaseResultTransformer(PreDeliveryDetailW.class));

		List list = query.list();
		return (PreDeliveryDetailW[]) list.toArray(new PreDeliveryDetailW[list.size()]);
		
	}
	
	public PreDeliveryDetailW[] getPreDeliveryDetailsOfOrderByFlowType(Long[] orderid, Long flowtypeid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = "SELECT PDD.SEQUENCE, " +
						"PDD.UNITS, " +
						"PDD.PENDINGUNITS, " +
						"PDD.RECEIVEDUNITS, " +
						"PDD.TODELIVERYUNITS, " +
						"PDD.OUTRECEIVEDUNITS, " +
						"PDD.TOTALNEED, " +
						"PDD.TOTALPENDING, " +
						"PDD.TOTALRECEIVED, " +
						"PDD.TOTALTODELIVERY, " +
						"PDD.LOCATION_ID AS LOCATIONID, " +
						"PDD.ORDER_ID AS ORDERID, " +
						"PDD.ITEM_ID AS ITEMID " +
						"FROM LOGISTICA.PREDELIVERYDETAIL AS PDD " +
						"JOIN LOGISTICA.ORDER AS O ON PDD.ORDER_ID = O.ID " +
						"JOIN LOGISTICA.ITEM AS IT ON PDD.ITEM_ID = IT.ID " +
						"WHERE O.ID in (:orderid) AND IT.FLOWTYPE_ID = :flowtypeid";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("orderid", orderid);
		query.setLong("flowtypeid", flowtypeid);
		query.setResultTransformer(new LowerCaseResultTransformer(PreDeliveryDetailW.class));

		List list = query.list();
		return (PreDeliveryDetailW[]) list.toArray(new PreDeliveryDetailW[list.size()]);
		
	}
	
	public PreDeliveryDetailW[] getPreDeliveryDetailsOfOrderByItems(Long orderid, Long... itemid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = "SELECT PDD.SEQUENCE, " +
						"PDD.UNITS, " +
						"PDD.PENDINGUNITS, " +
						"PDD.RECEIVEDUNITS, " +
						"PDD.TODELIVERYUNITS, " +
						"PDD.OUTRECEIVEDUNITS, " +
						"PDD.TOTALNEED, " +
						"PDD.TOTALPENDING, " +
						"PDD.TOTALRECEIVED, " +
						"PDD.TOTALTODELIVERY, " +
						"PDD.LOCATION_ID AS LOCATIONID, " +
						"PDD.ORDER_ID AS ORDERID, " +
						"PDD.ITEM_ID AS ITEMID " +
						"FROM LOGISTICA.PREDELIVERYDETAIL AS PDD " +
						"WHERE pdd.order_id = :orderid AND pdd.item_id in (:items)";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setParameterList("items", itemid);
		query.setResultTransformer(new LowerCaseResultTransformer(PreDeliveryDetailW.class));

		List list = query.list();
		return (PreDeliveryDetailW[]) list.toArray(new PreDeliveryDetailW[list.size()]);
		
	}
	
	private String getPreDeliveryDetailQueryString(int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException{
		
		String SQL 				= "";
		String condicionOrderBy	= "";
		
		if (queryType == 1){
			SQL = 
			"SELECT CAST(COUNT(PD.ORDER_ID) AS integer) AS TOTALREG, " +
				"SUM(PD.UNITS) AS TOTALUNITS, " +
				"SUM(PD.PENDINGUNITS) AS PENDINGUNITS, " +
				"SUM(PD.RECEIVEDUNITS) AS RECEIVEDUNITS, " +
				"SUM(PD.TODELIVERYUNITS) AS TODELIVERYUNITS, " +
				"SUM(PD.OUTRECEIVEDUNITS) AS OUTRECEIVEDUNITS, " +
				"SUM(PD.TOTALNEED) AS TOTALAMOUNT, " +
				"SUM(PD.TOTALPENDING) AS TOTALPENDING, " +
				"SUM(PD.TOTALRECEIVED) AS TOTALRECEIVED, " +
				"SUM(PD.TOTALTODELIVERY) AS TOTALTODELIVERY ";
		}
		if (queryType == 2) {
			SQL = 	
			"SELECT " +  
				"PD.ORDER_ID AS ORDERID, " +     
				"PD.ITEM_ID AS ITEMID, " +  
				"LO.CODE AS LOCATIONCODE, " +
				"LO.NAME AS LOCATIONDESC, " + 				
				"IT.INTERNALCODE AS SKU, " +     
				"VI.VENDORITEMCODE, " +    
				"IT.NAME AS ITEMDESC, " +  
				"PD.UNITS AS TOTALUNITS, " +
				"PD.PENDINGUNITS, " +				
				"PD.RECEIVEDUNITS, " +				
				"PD.TODELIVERYUNITS, " +			
				"PD.OUTRECEIVEDUNITS, " +			
				"PD.TOTALNEED AS TOTALAMOUNT, " +				
				"PD.TOTALPENDING, " +				
				"PD.TOTALRECEIVED, " +				
				"PD.TOTALTODELIVERY ";				
			condicionOrderBy = getOrderByString(mapGetPreDeliveryDetailSQL, orderby);		
		}	
		
		SQL = SQL +
			"FROM " +  
				"LOGISTICA.ORDER OC JOIN " +    
				"LOGISTICA.ORDERDETAIL OD ON OC.ID = OD.ORDER_ID JOIN " +     
				"LOGISTICA.ITEM IT ON OD.ITEM_ID = IT.ID JOIN " +
				"LOGISTICA.PREDELIVERYDETAIL AS PD ON PD.ORDER_ID = OD.ORDER_ID AND PD.ITEM_ID = OD.ITEM_ID JOIN " +
				"LOGISTICA.LOCATION AS LO ON LO.ID = PD.LOCATION_ID JOIN " +
				"LOGISTICA.VENDORITEM VI ON VI.ITEM_ID = IT.ID JOIN " +  
				"LOGISTICA.VENDOR VD ON VD.ID = VI.VENDOR_ID " + 
			"WHERE " +
				"OD.ORDER_ID = :orderid AND VD.ID = :vendorid " +			
			condicionOrderBy;		
		return SQL;		
	}
	
	public PreDeliveryDetailReportDataDTO[] getPreDeliveryDetailsByOrder(Long orderid, Long vendorid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException{
		String SQL = getPreDeliveryDetailQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setLong("vendorid", vendorid);
		query.setResultTransformer(new LowerCaseResultTransformer(PreDeliveryDetailReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List<?> list = query.list();
		PreDeliveryDetailReportDataDTO[] result = (PreDeliveryDetailReportDataDTO[]) list.toArray(new PreDeliveryDetailReportDataDTO[list.size()]);		
		return result;	
	}	
	
	public PreDeliveryDetailReportTotalDataDTO getPreDeliveryDetailsCountByOrder(Long orderid,  Long vendorid) throws ServiceException{
		String SQL = getPreDeliveryDetailQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setLong("vendorid", vendorid);		
		query.setResultTransformer(new LowerCaseResultTransformer(PreDeliveryDetailReportTotalDataDTO.class));
		PreDeliveryDetailReportTotalDataDTO result = (PreDeliveryDetailReportTotalDataDTO) query.uniqueResult();
		
		return result;
	}		
	
	public PredeliveryDetailDTO[] getPredeliveryDetailByOrders(Long[] orderids) throws OperationFailedException, NotFoundException {
		PredeliveryDetailDTO[] ocreport = null;
		int chunksize = 50;
		Object[] splitids = ClassUtilities.SplitArray(orderids, Long.class, chunksize);
		Object[] reportarray = new Object[splitids.length];
		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];
			if (ids.length <= 0) {
				reportarray[i] = new PredeliveryDetailDTO[0];
				continue;
			}
			SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.buyorders.entities.PreDeliveryDetail.getPredeliveryDetailByOrders");
			query.setParameterList("orderids", ids);
			query.setResultTransformer(new LowerCaseResultTransformer(PredeliveryDetailDTO.class));
			List list = query.list();
			PredeliveryDetailDTO[] result = (PredeliveryDetailDTO[]) list.toArray(new PredeliveryDetailDTO[list.size()]);
			reportarray[i] = result;
		}
		ocreport = (PredeliveryDetailDTO[]) ClassUtilities.UnsplitArrays(reportarray, PredeliveryDetailDTO.class);
		return ocreport;

	}
	
	public PreDeliveryDetailW[] getPredeliveryDetailsByDelivery(Long deliveryid) throws OperationFailedException, NotFoundException {
				
        StringBuilder sb =
        	new StringBuilder(
        				"SELECT DISTINCT " + //
        					"pdd " + //" +
        				"FROM " + //
        					"OrderDelivery AS odl, " + //
        					"PreDeliveryDetail AS pdd " + //
        				"WHERE " + //
        					"pdd.id.orderid = odl.id.orderid AND odl.id.deliveryid = :deliveryid");
        
        PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("odl.id.deliveryid", "deliveryid", deliveryid);
        List list = getByQuery(sb.toString(), properties);

        return (PreDeliveryDetailW[]) list.toArray(new PreDeliveryDetailW[list.size()]);
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
}
