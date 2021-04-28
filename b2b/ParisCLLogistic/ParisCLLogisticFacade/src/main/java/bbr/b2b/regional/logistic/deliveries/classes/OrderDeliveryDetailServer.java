package bbr.b2b.regional.logistic.deliveries.classes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.datings.report.classes.AssignedDatingTotalizerByDockDTO;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryDetailW;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDelivery;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetailId;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryId;
import bbr.b2b.regional.logistic.deliveries.report.classes.BulkDetailDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDetailReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.OrderDeliveryDetailDTO;
import bbr.b2b.regional.logistic.items.classes.ItemServerLocal;
import bbr.b2b.regional.logistic.items.entities.Item;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.report.classes.UploadErrorDTO;

@Stateless(name = "servers/deliveries/OrderDeliveryDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderDeliveryDetailServer extends BaseLogisticEJB3Server<OrderDeliveryDetail, OrderDeliveryDetailId, OrderDeliveryDetailW> implements OrderDeliveryDetailServerLocal {

	private Map<String, String> mapGetDeliveryDetailSQL = new HashMap<String, String>();
	{
		mapGetDeliveryDetailSQL.put("DELIVERYID", "dl.id");
		mapGetDeliveryDetailSQL.put("OCNUMBER", "oc.number");
		mapGetDeliveryDetailSQL.put("LOCATIONCODE", "lo.code");
		mapGetDeliveryDetailSQL.put("LOCATIONDESC", "lo.name");
		mapGetDeliveryDetailSQL.put("DEPARTMENTCODE", "dp.code");
		mapGetDeliveryDetailSQL.put("DEPARTMENTDESC", "dp.name");
		mapGetDeliveryDetailSQL.put("ITEMSKU", "it.internalcode");
		mapGetDeliveryDetailSQL.put("VENDORITEMCODE", "vi.vendoritemcode");
		mapGetDeliveryDetailSQL.put("FLOWTYPE", "ft.name");
		mapGetDeliveryDetailSQL.put("INNERPACK", "it.innerpack");
		mapGetDeliveryDetailSQL.put("ITEMDESC", "it.name");
		mapGetDeliveryDetailSQL.put("PENDINGUNITS", "odd.pendingunits");
		mapGetDeliveryDetailSQL.put("AVAILABLEUNITS", "odd.availableunits");
		mapGetDeliveryDetailSQL.put("RECEIVEDUNITS", "odd.receivedunits");
		mapGetDeliveryDetailSQL.put("ATCCODE", "atccode");
		mapGetDeliveryDetailSQL.put("ORIGINALVENDORCODE", "originalvendorcode");
		mapGetDeliveryDetailSQL.put("ORIGINALVENDORNAME", "originalvendorname");
	}		

	@EJB
	LocationServerLocal locationserver;

	@EJB
	OrderDeliveryServerLocal orderdeliveryserver;

	@EJB
	ItemServerLocal itemserver;

	public OrderDeliveryDetailW addOrderDeliveryDetail(OrderDeliveryDetailW orderdeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDeliveryDetailW) addIdentifiable(orderdeliverydetail);
	}
	public OrderDeliveryDetailW[] getOrderDeliveryDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDeliveryDetailW[]) getIdentifiables();
	}
	public OrderDeliveryDetailW updateOrderDeliveryDetail(OrderDeliveryDetailW orderdeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDeliveryDetailW) updateIdentifiable(orderdeliverydetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OrderDeliveryDetail entity, OrderDeliveryDetailW wrapper) {
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
		wrapper.setOrderid(entity.getOrderdelivery().getId() != null ? new Long(entity.getOrderdelivery().getId().getOrderid()) : new Long(0));
		wrapper.setDeliveryid(entity.getOrderdelivery().getId() != null ? new Long(entity.getOrderdelivery().getId().getDeliveryid()) : new Long(0));
		wrapper.setItemid(entity.getItem() != null ? new Long(entity.getItem().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(OrderDeliveryDetail entity, OrderDeliveryDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getLocationid() != null && wrapper.getLocationid() > 0) { 
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if (location != null) { 
				entity.setLocation(location);
			}
		}
		if ((wrapper.getOrderid() != null && wrapper.getOrderid() > 0) && (wrapper.getDeliveryid() != null && wrapper.getDeliveryid() > 0)) {
			OrderDeliveryId key = new OrderDeliveryId();
			key.setOrderid(wrapper.getOrderid());
			key.setDeliveryid(wrapper.getDeliveryid());
			OrderDelivery var = orderdeliveryserver.getReferenceById(key);
			if (var != null) { 
				entity.setOrderdelivery(var);
			}
		}
		if (wrapper.getItemid() != null && wrapper.getItemid() > 0) { 
			Item item = itemserver.getReferenceById(wrapper.getItemid());
			if (item != null) { 
				entity.setItem(item);
			}
		}
	}

	private String getDeliveryDetailReportQueryString(int queryType, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException{
		
		String SQL 					= "";
		String condicionOrderBy		= "";		
				
		if (queryType == 1){
			SQL = 
				"SELECT " + //
					"CAST(COUNT(dl.id) AS INTEGER) AS totalreg, " + //
					"SUM(odd.pendingunits) AS pendingunits, " + //
					"SUM(odd.availableunits) AS availableunits, " + //
					"SUM(odd.receivedunits) AS receivedunits "; //					
		}
		else {
			SQL = 	
			"SELECT " + //
				"dl.id AS deliveryid, " + //
				"dl.number AS deliverynumber, " + //
				"oc.id AS orderid, " + //
				"oc.number AS ocnumber, " + //
				"lo.id AS locationid, " + //
				"lo.code AS locationcode, " + //
				"lo.name AS locationdesc, " + //
				"dp.id AS departmentid, " + //
				"dp.code AS departmentcode, " + //
				"dp.name AS departmentdesc, " + //
				"it.id AS itemid, " + //
				"it.internalcode AS itemsku, " + //
				"vi.vendoritemcode, " + //
				"ft.id AS flowtypeid, " + //
				"ft.name AS flowtype, " + //
				"it.innerpack, " + //
				"it.name AS itemdesc, " + //
				"odd.pendingunits, " + //
				"odd.availableunits, " + //
				"odd.receivedunits, " + //
				"ia.curve, " + //
				"atc.id AS atcid, " + //
				"COALESCE(atc.code, '') AS atccode, " + //
				"ovd.id AS originalvendorid, " + //
				"COALESCE(ovd.code, '') AS originalvendorcode, " + //
				"COALESCE(ovd.name, '') AS originalvendorname "; //
			
				condicionOrderBy = getOrderByString(mapGetDeliveryDetailSQL, orderby);				
		}
		
		SQL +=
			"FROM " + //
				"logistica.delivery AS dl JOIN " + //
				"logistica.orderdelivery AS odl ON odl.delivery_id = dl.id JOIN " + //
				"logistica.order AS oc ON oc.id = odl.order_id JOIN " + //
				"logistica.orderdeliverydetail AS odd ON odd.order_id = oc.id AND odd.delivery_id = dl.id JOIN " + //
				"logistica.item AS it ON it.id = odd.item_id JOIN " + //
				"logistica.location AS lo ON lo.id = odd.location_id JOIN " + //
				"logistica.flowtype AS ft ON ft.id = it.flowtype_id JOIN " + //
				"logistica.vendor AS vd ON vd.id = oc.vendor_id JOIN " + //
				"logistica.vendoritem AS vi ON vi.vendor_id = vd.id AND vi.item_id = it.id JOIN " + //
				"logistica.department AS dp ON dp.id = oc.department_id LEFT JOIN " + //
				"logistica.item_atc AS ia ON ia.item_id = it.id LEFT JOIN " + //
				"logistica.atc AS atc ON atc.id = ia.atc_id LEFT JOIN " + //
				"logistica.vendor AS ovd ON ovd.code = oc.vendorcodeimp " + //
			"WHERE " + //
				"dl.id = :deliveryid AND vd.id = :vendorid " + //
			condicionOrderBy; //
		
		return SQL;		
	}
	
	public DeliveryDetailReportDataDTO[] getDeliveryDetailReport(Long vendorid, Long deliveryid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException {
				
		String SQL = getDeliveryDetailReportQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("deliveryid", deliveryid);	
		
		query.setResultTransformer(new LowerCaseResultTransformer(DeliveryDetailReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List list = query.list();
		DeliveryDetailReportDataDTO[] result = (DeliveryDetailReportDataDTO[]) list.toArray(new DeliveryDetailReportDataDTO[list.size()]);		
		
		return result;	
	}
	
	public DeliveryDetailReportTotalDataDTO getDeliveryDetailCountReport(Long vendorid, Long deliveryid) throws ServiceException{
				
		String SQL = getDeliveryDetailReportQueryString(1, null);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("deliveryid", deliveryid);	
		
		query.setResultTransformer(new LowerCaseResultTransformer(DeliveryDetailReportTotalDataDTO.class));
		DeliveryDetailReportTotalDataDTO result = (DeliveryDetailReportTotalDataDTO) query.uniqueResult();				
		
		return result;
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
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "OrderDeliveryDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "OrderDeliveryDetail";
	}
	

	public AssignedDatingTotalizerByDockDTO[] getAssignedDatingTotalizerByDateAndLocation(Date since, Date until, Long locationid, Long docktypeid, boolean isbyreport) {
		
		SQLQuery query;
		if (isbyreport) {
			query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail.getAssignedDatingTotalizerByDateLocationAndDockType");
			query.setDate("since", since);
			query.setDate("until", until);
			query.setLong("locationid", locationid.longValue());
			query.setLong("docktypeid", docktypeid);
		}
		else {
			query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail.getAssignedDatingTotalizerByDateAndLocation");
			query.setDate("since", since);
			query.setDate("until", until);
			query.setLong("locationid", locationid.longValue());
		}

		query.setResultTransformer(new LowerCaseResultTransformer(AssignedDatingTotalizerByDockDTO.class));
		
		List list = query.list();
		AssignedDatingTotalizerByDockDTO[] result = (AssignedDatingTotalizerByDockDTO[]) list.toArray(new AssignedDatingTotalizerByDockDTO[list.size()]);		
		return result;
	}
	
	public OrderDeliveryDetailDTO getOrderDeliveryDetailDataByOrderItemLocationAndATC(Long orderid, Long deliveryid, Long itemid, Long locationid, Long atcid) {
		
		String SQL =
				"SELECT " + //
					"odd.order_id AS orderid, " + //
					"odd.delivery_id AS deliveryid, " + //
					"odd.item_id AS itemid, " + //
					"odd.location_id AS locationid, " + //
					"ia.atc_id AS atcid, " + //
					"oc.department_id AS departmentid, " + //
					"it.flowtype_id AS flowtypeid, " + //
					"odd.pendingunits, " + //
					"odd.availableunits, " + //
					"odd.receivedunits " + //
				"FROM " + //
					"logistica.orderdeliverydetail AS odd JOIN " + //
					"logistica.order AS oc ON oc.id = odd.order_id JOIN " + //
					"logistica.item AS it ON it.id = odd.item_id JOIN " + //
					"logistica.orderdetail AS od ON od.order_id = oc.id AND od.item_id = it.id LEFT JOIN " + //
					"logistica.item_atc AS ia ON ia.item_id = od.item_id " + //
				"WHERE " +
					"odd.order_id = :orderid AND odd.delivery_id = :deliveryid AND odd.item_id = :itemid AND odd.location_id = :locationid ";
		
		if (atcid != null) {
			SQL += "AND od.hasatc IS TRUE AND ia.atc_id = :atcid";
		}
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setLong("deliveryid", deliveryid);
		query.setLong("itemid", itemid);
		query.setLong("locationid", locationid);
		if (atcid != null) {
			query.setLong("atcid", atcid);
		}
				
		query.setResultTransformer(new LowerCaseResultTransformer(OrderDeliveryDetailDTO.class));
		
		OrderDeliveryDetailDTO result = (OrderDeliveryDetailDTO)query.uniqueResult();
		return result;
	}
	
	public void doDeleteByOrderDeliveries(Long deliveryid, Long[] orderids) {
		
		String SQL =
				"DELETE " + //
				"FROM logistica.orderdeliverydetail " + //
				"WHERE " + //
					"delivery_id = :deliveryid AND order_id IN (:orderids)"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("deliveryid", deliveryid);
		query.setParameterList("orderids", orderids);

		query.executeUpdate();
	}
	
	//****************************************************************************************************//
	//******************** SET DE QUERIES USADAS PARA AJUSTE DE DESPACHO ******************************// 
	//****************************************************************************************************//	
	public int doLoadCSV(String filename) {
		String SQL =
			"COPY adjustdeliveryupload (line, ordernumber, locationcode, iteminternalcode, atccode, units) " + //
			"FROM " + //
				"'" + filename + "' " + //
			"WITH DELIMITER ';' CSV "; //
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		int total = query.executeUpdate(); // no retorna el contador
		return total;		
	}
	
	public void doCreateTempTableAdjustDelivery() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail.doCreateTempTableAdjustDelivery");
		query.executeUpdate();
	}
	
	public void doCreateTempTableError() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail.doCreateTempTableError");
		query.executeUpdate();
	}
	
	public UploadErrorDTO[] getErrorsOfAdjustDelivery() {
		
		String SQL =
			"SELECT " + //
				"line, " + //
				"error " + //
			"FROM " + //
				"adjustdeliveryerror " + //
			"ORDER BY line ";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(UploadErrorDTO.class));
		List list = query.list();
		UploadErrorDTO[] result = (UploadErrorDTO[]) list.toArray(new UploadErrorDTO[list.size()]);
		return result;
	}
	
	public BulkDetailDataDTO[] getBulkDetailData() {
		
		String SQL =
			"SELECT " + //
				"order_id AS orderid, " + //
				"delivery_id AS deliveryid, " + //
				"location_id AS locationid, " + //
				"item_id AS itemid, " + //
				"COALESCE(atc_id, 0) AS atcid, " + //
				"department_id AS departmentid, " + //
				"flowtype_id AS flowtypeid, " + //
				"units, " + //
				"bulks " + //
			"FROM " + //
				"adjustdeliveryupload " + //
			"WHERE " + //
				"units > 0 " + //
			"ORDER BY order_id, item_id, atc_id";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(BulkDetailDataDTO.class));
		List list = query.list();
		BulkDetailDataDTO[] result = (BulkDetailDataDTO[]) list.toArray(new BulkDetailDataDTO[list.size()]);
		return result;
	}
	
	public int doFillData(Long deliveryid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail.doFillData");
		query.setLong("deliveryid", deliveryid);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckUniqueRows() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail.doCheckUniqueRows");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckValidOrderItemLocation() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail.doCheckValidOrderItemLocation");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckOrderDeliveryAdjustUnits() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail.doCheckOrderDeliveryAdjustUnits");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckPredeliveryAdjustUnits(Long deliveryid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail.doCheckPredeliveryAdjustUnits");
		query.setLong("deliveryid", deliveryid);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckATCItems() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail.doCheckATCItems");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckProportionalATCCurves() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail.doCheckProportionalATCCurves");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckCompleteATCCurves() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail.doCheckCompleteATCCurves");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doAdjustOrderDeliveryDetailUnits() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail.doAdjustOrderDeliveryDetailUnits");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doAdjustOrderDeliveryEstimatedUnits() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryDetail.doAdjustOrderDeliveryEstimatedUnits");
		int total = query.executeUpdate();
		return total;
	}
	
	public Long[] getOrderIdsFromAdjustDelivery() {
		
		List<Long> longlist = new ArrayList<Long>();
		
		String SQL =
			"SELECT DISTINCT " + //
				"order_id " + //
			"FROM " + //
				"adjustdeliveryupload"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		List<BigInteger> list = query.list();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			BigInteger bigInteger = (BigInteger) iterator.next();
			longlist.add(bigInteger.longValue());
		}
		Long[] result =  longlist.toArray(new Long[longlist.size()]);
		return result;		
	}
	
}
