package bbr.b2b.regional.logistic.buyorders.classes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.buyorders.entities.Client;
import bbr.b2b.regional.logistic.buyorders.entities.Order;
import bbr.b2b.regional.logistic.buyorders.entities.OrderStateType;
import bbr.b2b.regional.logistic.buyorders.entities.OrderType;
import bbr.b2b.regional.logistic.buyorders.report.classes.ExcelOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderIdsByPagesW;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.SchedulePendingOrderDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDExcelOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVVendorNotificationDTO;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.report.classes.PendingSOAOrderDTO;
import bbr.b2b.regional.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.regional.logistic.soa.entities.SOAStateType;
import bbr.b2b.regional.logistic.utils.ClassUtilities;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.ResponsableServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.entities.Responsable;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/buyorders/OrderServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderServer extends LogisticElementServer<Order, OrderW> implements OrderServerLocal {

	private Map<String, String> mapGetOrderSQL = new HashMap<String, String>();
	{
		mapGetOrderSQL.put("ORDERID", "oc.id");
		mapGetOrderSQL.put("ORDERNUMBER", "oc.number");
		mapGetOrderSQL.put("DEPARTMENTDESC", "dp.name");
		mapGetOrderSQL.put("ORDERSTATETYPEDESC", "ost.name");
		mapGetOrderSQL.put("ORDERSTATETYPECODE", "ost.code");
		mapGetOrderSQL.put("ORDERTYPEDESC", "ot.name");
		mapGetOrderSQL.put("ORDERTYPECODE", "ot.code");
		mapGetOrderSQL.put("DELIVERYLOCATIONDESC", "lo.name");
		mapGetOrderSQL.put("DELIVERYLOCATIONCODE", "lo.code");
		mapGetOrderSQL.put("EMITTEDDATE", "oc.emitted");
		mapGetOrderSQL.put("VALIDDATE", "oc.valid");
		mapGetOrderSQL.put("EXPIRATIONDATE", "oc.expiration");
		mapGetOrderSQL.put("TOTALUNITS", "oc.needunits");
		mapGetOrderSQL.put("PENDINGUNITS", "oc.pendingunits");
		mapGetOrderSQL.put("TODELIVERYUNITS", "oc.todeliveryunits");
		mapGetOrderSQL.put("RECEIVEDUNITS", "oc.receivedunits");
		mapGetOrderSQL.put("OUTRECEIVEDUNITS", "oc.outreceivedunits");
		mapGetOrderSQL.put("TOTALAMOUNT", "oc.totalneed");
		mapGetOrderSQL.put("TOTALPENDING", "oc.totalpending");
		mapGetOrderSQL.put("TOTALRECEIVED", "oc.totalreceived");
		mapGetOrderSQL.put("TOTALTODELIVERY", "oc.totaltodelivery");
		mapGetOrderSQL.put("ORIGINALVENDORCODE", "originalvendorcode");
		mapGetOrderSQL.put("ORIGINALVENDORNAME", "originalvendorname");		
	}	
	
	private Map<String, String> mapGetVeVCDOrderSQL = new HashMap<String, String>();
	{
		mapGetVeVCDOrderSQL.put("ORDERNUMBER", "oc.number"); 
		mapGetVeVCDOrderSQL.put("REQUESTNUMBER", "LPAD(oc.requestnumber, 30, '0')");//para ordenamiento de string como N°mero
		mapGetVeVCDOrderSQL.put("COMMUNE", "cl.commune");
		mapGetVeVCDOrderSQL.put("DEPARTMENTCODE", "dp.code");
		mapGetVeVCDOrderSQL.put("DEPARTMENTDESC", "dp.name");
		mapGetVeVCDOrderSQL.put("ORDERSTATETYPEDESC", "ost.name");
		mapGetVeVCDOrderSQL.put("ORDERSTATETYPECODE", "ost.code");
		mapGetVeVCDOrderSQL.put("ORDERTYPEDESC", "ot.name");
		mapGetVeVCDOrderSQL.put("ORDERTYPECODE", "ot.code");
		mapGetVeVCDOrderSQL.put("DELIVERYLOCATIONDESC", "lo.name");
		mapGetVeVCDOrderSQL.put("DELIVERYLOCATIONCODE", "lo.code");
		mapGetVeVCDOrderSQL.put("SALESTORE", "salestore");
		mapGetVeVCDOrderSQL.put("EMITTEDDATE", "oc.emitted");
		mapGetVeVCDOrderSQL.put("VALIDDATE", "oc.valid");
		mapGetVeVCDOrderSQL.put("EXPIRATIONDATE", "oc.expiration");
		mapGetVeVCDOrderSQL.put("ORIGINALDELIVERYDATE", "oc.originaldeliverydate");		
		mapGetVeVCDOrderSQL.put("TOTALUNITS", "oc.needunits");
		mapGetVeVCDOrderSQL.put("PENDINGUNITS", "oc.pendingunits");
		mapGetVeVCDOrderSQL.put("TODELIVERYUNITS", "oc.todeliveryunits");
		mapGetVeVCDOrderSQL.put("RECEIVEDUNITS", "oc.receivedunits");
		mapGetVeVCDOrderSQL.put("OUTRECEIVEDUNITS", "oc.outreceivedunits");
		mapGetVeVCDOrderSQL.put("TOTALAMOUNT", "oc.totalneed");
		mapGetVeVCDOrderSQL.put("TOTALPENDING", "oc.totalpending");
		mapGetVeVCDOrderSQL.put("TOTALRECEIVED", "oc.totalreceived");
		mapGetVeVCDOrderSQL.put("TOTALTODELIVERY", "oc.totaltodelivery");
	}		
	
	@EJB
	VendorServerLocal vendorserver;

	@EJB
	ResponsableServerLocal responsableserver;

	@EJB
	OrderTypeServerLocal ordertypeserver;

	@EJB
	LocationServerLocal deliverylocationserver;

	@EJB
	DepartmentServerLocal departmentserver;

	@EJB
	OrderStateTypeServerLocal currentstatetypeserver;

	@EJB
	ClientServerLocal clientserver;
	
	@EJB
	SOAStateTypeServerLocal soastatetypeserver;

	public OrderW addOrder(OrderW order) throws AccessDeniedException, OperationFailedException, NotFoundException {
		
		
		//revisar esta logica !!!!!!!!
		
//		
////		order = new OrderW();
//		order.setReceivedunits(order.getReceivedunits() == null ? 0D : order.getReceivedunits());
//		order.setTotalreceived(order.getTotalreceived() == null ? 0D : order.getTotalreceived());
//		order.setOutreceivedunits(order.getOutreceivedunits() == null ? 0D : order.getOutreceivedunits());
//		order.setPendingunits(order.getPendingunits() == null ? 0D : order.getPendingunits());
//		order.setTotalpending(order.getTotalpending() == null ? 0D : order.getTotalpending());
//		order.setTodeliveryunits(order.getTodeliveryunits() == null ? 0D : order.getTodeliveryunits());
//		order.setTotaltodelivery(order.getTotaltodelivery() == null ? 0D : order.getTotaltodelivery());
//		order.setNeedunits(order.getNeedunits() == null ? 0D : order.getNeedunits());
//		order.setTotalneed(order.getTotalneed() == null ? 0D : order.getTotalneed());
	
		
		return (OrderW) addElement(order);
	}
	public OrderW[] getOrders() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderW[]) getIdentifiables();
	}
	public OrderW updateOrder(OrderW order) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderW) updateElement(order);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Order entity, OrderW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setResponsableid(entity.getResponsable() != null ? new Long(entity.getResponsable().getId()) : new Long(0));
		wrapper.setOrdertypeid(entity.getOrdertype() != null ? new Long(entity.getOrdertype().getId()) : new Long(0));
		wrapper.setDeliverylocationid(entity.getDeliverylocation() != null ? new Long(entity.getDeliverylocation().getId()) : new Long(0));
		wrapper.setDepartmentid(entity.getDepartment() != null ? new Long(entity.getDepartment().getId()) : new Long(0));
		wrapper.setCurrentstatetypeid(entity.getCurrentstatetype() != null ? new Long(entity.getCurrentstatetype().getId()) : new Long(0));
		wrapper.setClientid(entity.getClient() != null ? new Long(entity.getClient().getId()) : new Long(0));
		wrapper.setCurrentsoastatetypeid(entity.getCurrentsoastatetype() != null ? new Long(entity.getCurrentsoastatetype().getId()) : new Long(0));
		wrapper.setSalestoreid(entity.getSalestore() != null ? new Long(entity.getSalestore().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Order entity, OrderW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getResponsableid() != null && wrapper.getResponsableid() > 0) { 
			Responsable responsable = responsableserver.getReferenceById(wrapper.getResponsableid());
			if (responsable != null) { 
				entity.setResponsable(responsable);
			}
		}
		if (wrapper.getOrdertypeid() != null && wrapper.getOrdertypeid() > 0) { 
			OrderType ordertype = ordertypeserver.getReferenceById(wrapper.getOrdertypeid());
			if (ordertype != null) { 
				entity.setOrdertype(ordertype);
			}
		}
		if (wrapper.getDeliverylocationid() != null && wrapper.getDeliverylocationid() > 0) { 
			Location deliverylocation = deliverylocationserver.getReferenceById(wrapper.getDeliverylocationid());
			if (deliverylocation != null) { 
				entity.setDeliverylocation(deliverylocation);
			}
		}
		if (wrapper.getDepartmentid() != null && wrapper.getDepartmentid() > 0) { 
			Department department = departmentserver.getReferenceById(wrapper.getDepartmentid());
			if (department != null) { 
				entity.setDepartment(department);
			}
		}
		if (wrapper.getCurrentstatetypeid() != null && wrapper.getCurrentstatetypeid() > 0) { 
			OrderStateType currentstatetype = currentstatetypeserver.getReferenceById(wrapper.getCurrentstatetypeid());
			if (currentstatetype != null) { 
				entity.setCurrentstatetype(currentstatetype);
			}
		}
		if (wrapper.getClientid() != null && wrapper.getClientid() > 0) { 
			Client client = clientserver.getReferenceById(wrapper.getClientid());
			if (client != null) { 
				entity.setClient(client);
			}
		}
		if (wrapper.getCurrentsoastatetypeid() != null && wrapper.getCurrentsoastatetypeid() > 0) {
			SOAStateType soastatetype = soastatetypeserver.getReferenceById(wrapper.getCurrentsoastatetypeid());
			if (soastatetype != null) {
				entity.setCurrentsoastatetype(soastatetype);
			}
		}
		if (wrapper.getSalestoreid() != null && wrapper.getSalestoreid() > 0) {
			Location salestore = deliverylocationserver.getReferenceById(wrapper.getSalestoreid());
			if (salestore != null) {
				entity.setSalestore(salestore);
			}
		}		
	}
	
	public OrderW[] getOrdersByIds(List<Long> orderids) throws OperationFailedException, NotFoundException {
		
		StringBuilder sb = new StringBuilder(
				"SELECT DISTINCT " + //
					"oc " + //
				"FROM " + //
					"Order AS oc " + //
				"WHERE " + //
					"oc.id IN (:orderids)");

		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("id", "orderids", orderids);
		
		List list = getByQuery(sb.toString(), properties);
		
		return (OrderW[]) list.toArray(new OrderW[list.size()]);
	}
	
	private String getOrderReportQueryString(int queryType, OrderCriteriaDTO[] orderby, String whereCondition) throws OperationFailedException, AccessDeniedException{
		
		String SQL 					= "";
		String condicionOrderBy		= "";		
				
		if (queryType == 1) {
			SQL = 
				"SELECT " + //
					"CAST(COUNT(oc.id) AS INTEGER) AS totalreg, " + //
					"SUM(oc.needunits) AS totalunits, " + //
					"SUM(oc.pendingunits) AS pendingunits, " + //
					"SUM(oc.todeliveryunits) AS todeliveryunits, " + //
					"SUM(oc.receivedunits) AS receivedunits, " + //
					"SUM(oc.outreceivedunits) AS outreceivedunits, " + //
					"SUM(oc.totalneed) AS totalamount, " + //
					"SUM(oc.totalpending) AS totalpending, " + //
					"SUM(oc.totalreceived) AS totalreceived, " + //
					"SUM(oc.totaltodelivery) AS totaltodelivery "; //
		}
		else {
			SQL = 	
				"SELECT DISTINCT " + //
					"oc.id AS orderid, " + //
					"oc.number AS ordernumber, " + //
					"dp.code AS departmentcode, " + //
					"dp.name AS departmentdesc, " + //
					"ost.name AS orderstatetypedesc, " + //
					"ost.code AS orderstatetypecode, " + //		 
					"ot.name AS ordertypedesc, " + //
					"ot.code AS ordertypecode, " + //		
					"lo.name AS deliverylocationdesc, " + //
					"lo.code AS deliverylocationcode, " + //
					"oc.emitted AS emitteddate, " + //
					"oc.valid AS validdate, " + //
					"oc.expiration AS expirationdate, " + //
					"oc.needunits AS totalunits, " + //
					"oc.pendingunits, " + //
					"oc.todeliveryunits, " + //
					"oc.receivedunits, " + //
					"oc.outreceivedunits, " + //
					"oc.totalneed AS totalamount, " + //
					"oc.totalpending, " + //
					"oc.totalreceived, " + //			  
					"oc.totaltodelivery, " + //
					"ovd.id AS originalvendorid, " + //
					"COALESCE(ovd.code, '') AS originalvendorcode, " + //
					"COALESCE(ovd.name, '') AS originalvendorname "; //
						
				condicionOrderBy = getOrderByString(mapGetOrderSQL, orderby);				
		}
		
		SQL += 
			"FROM " +  
				"logistica.order AS oc JOIN " + //
				"logistica.department AS dp ON dp.id = oc.department_id JOIN " + // 
				"logistica.orderstatetype AS ost ON ost.id = oc.currentstatetype_id JOIN " + //
				"logistica.ordertype AS ot ON ot.id = oc.ordertype_id JOIN " + //
				"logistica.location AS lo ON lo.id = oc.deliverylocation_id JOIN " + //
				"logistica.vendor AS vd ON vd.id = oc.vendor_id LEFT JOIN " + //
				"logistica.vendor AS ovd ON ovd.code = oc.vendorcodeimp " + //
			"WHERE " + //
				"oc.vevcd = FALSE AND vd.id = :vendorid " + //	
				whereCondition + //				  
			condicionOrderBy; //	
		
		return SQL;		
	}
	
	private String getVeVCDOrderReportQueryString(int queryType, OrderCriteriaDTO[] orderby, String whereCondition) throws OperationFailedException, AccessDeniedException{
		
		String SQL 					= "";
		String orderByCondition		= "";				
	
		if (queryType == 1) {
			SQL = 
				"SELECT " + //
					"CAST(COUNT(oc.id) AS INTEGER) AS totalreg, " + //
					"SUM(oc.needunits) AS totalunits, " + //
					"SUM(oc.pendingunits) AS pendingunits, " + //
					"SUM(oc.todeliveryunits) AS todeliveryunits, " + //
					"SUM(oc.receivedunits) AS receivedunits, " + //
					"SUM(oc.outreceivedunits) AS outreceivedunits, " + //
					"SUM(oc.totalneed) AS totalamount, " + //
					"SUM(oc.totalpending) AS totalpending, " + //
					"SUM(oc.totalreceived) AS totalreceived, " + //
					"SUM(oc.totaltodelivery) AS totaltodelivery "; //
		}
		else {
			SQL = 	
				"SELECT " +  
					"oc.id AS orderid, " + //
					"oc.number AS ordernumber, " + //
					"oc.requestnumber, " + //
					"cl.commune, " + //
					"dp.code AS departmentcode, " + // 
					"dp.name AS departmentdesc, " + //
					"ost.name AS orderstatetypedesc, " + //
					"ost.code AS orderstatetypecode, " + //		 
					"ot.name AS ordertypedesc, " + //
					"ot.code AS ordertypecode, " + //		
					"lo.name AS deliverylocationdesc, " + //
					"lo.code AS deliverylocationcode, " + //
					"loss.code || ' - ' || loss.name AS salestore, " + //
					"oc.emitted AS emitteddate, " + //
					"oc.valid AS validdate, " + //
					"oc.expiration AS expirationdate, " + //
					"oc.originaldeliverydate AS originaldeliverydate, " + //
					"oc.needunits AS totalunits, " + //
					"oc.pendingunits, " + //
					"oc.todeliveryunits, " + //
					"oc.receivedunits, " + //
					"oc.outreceivedunits, " + //
					"oc.totalneed AS totalamount, " + //
					"oc.totalpending, " + //
					"oc.totalreceived, " + //
					"oc.totaltodelivery "; //
						
			orderByCondition = getOrderByString(mapGetVeVCDOrderSQL, orderby);				
		}
		
		SQL +=
			"FROM " + //
				"logistica.order AS oc JOIN " + //
				"logistica.department AS dp ON dp.id = oc.department_id JOIN " + //
				"logistica.orderstatetype AS ost ON ost.id = oc.currentstatetype_id JOIN " + //
				"logistica.ordertype AS ot ON ot.id = oc.ordertype_id JOIN " + //
				"logistica.location AS lo ON lo.id = oc.deliverylocation_id LEFT JOIN " + //
				"logistica.location AS loss ON loss.id = oc.salestore_id JOIN " + //
				"logistica.vendor AS vd ON vd.id = oc.vendor_id JOIN " + //
				"logistica.client AS cl ON cl.id = oc.client_id " + // 					 
			"WHERE " + //
				"vd.id = :vendorid AND oc.vevcd IS TRUE " + //		
				whereCondition + //			  
				orderByCondition; //	
		
		return SQL;		
	}
	
	public OrderReportDataDTO[] getOrdersByVendorLocationAndCriterium(Long vendorid, Long originalvendorid, Long locationid, Date now, Long ocnumber, Long orderstatetypeid, Date since, Date until, Integer criterium, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException {
		String whereCondition = "";
		
		if (originalvendorid != null && originalvendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND (vd.rut != 'IMP' OR ovd.id = :originalvendorid) "; //
		}
		
		if (locationid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND oc.deliverylocation_id = :locationid "; //
		}
		
		switch (criterium) {
		case 0:
			// VIGENTES
			whereCondition += "AND (oc.valid <= :now AND oc.expiration >= :now) AND ost.valid = TRUE "; //
			break;
		case 1:
			// PR�XIMAMENTE VIGENTES
			whereCondition += "AND oc.valid >= :now "; //		
			break;
		case 2:
			// N°MERO DE ORDEN
			whereCondition += "AND oc.number = :ordernumber "; //
			break;
		case 3:
			// ESTADO DE ORDEN
			whereCondition += "AND ost.id = :osttypeid "; //
			break;
		case 4:
			// FECHA DE EMISión
			whereCondition += "AND (oc.emitted >= :since AND oc.emitted < :until) "; //
			break;
		case 5:
			// FECHA DE VIGENCIA
			whereCondition += "AND (oc.valid >= :since AND oc.valid < :until) "; //
			break;
		case 6:
			// FECHA DE VENCIMIENTO
			whereCondition += "AND (oc.expiration >= :since AND oc.expiration < :until) "; //
			break;			
		default:
			break;
		}
		
		String SQL = getOrderReportQueryString(2, orderby, whereCondition);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (originalvendorid != null && originalvendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("originalvendorid", originalvendorid);
		}
		if (locationid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("locationid", locationid);
		}
		
		switch (criterium) {
		case 0:
			// VIGENTES
			query.setDate("now", now);			
			break;
		case 1:
			// PR�XIMAMENTE VIGENTES
			query.setDate("now", now);			
			break;
		case 2:
			// N°MERO DE ORDEN
			query.setLong("ordernumber", ocnumber);
			break;
		case 3:
			// ESTADO DE ORDEN
			query.setLong("osttypeid", orderstatetypeid);
			break;
		case 4:
			// FECHA DE EMISión
			query.setDate("since", since);
			query.setDate("until", until);
			break;
		case 5:
			// FECHA DE VIGENCIA
			query.setDate("since", since);
			query.setDate("until", until);
			break;
		case 6:
			// FECHA DE VENCIMIENTO
			query.setDate("since", since);
			query.setDate("until", until);
			break;
		default:
			break;
		}		
		
		query.setResultTransformer(new LowerCaseResultTransformer(OrderReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List list = query.list();
		OrderReportDataDTO[] result = (OrderReportDataDTO[]) list.toArray(new OrderReportDataDTO[list.size()]);		
		return result;
	}
	
	public OrderReportTotalDataDTO getOrdersCountByVendorLocationAndCriterium(Long vendorid, Long originalvendorid, Long locationid, Date now, Long ocnumber, Long orderstatetypeid, Date since, Date until, Integer criterium) throws ServiceException{
		String whereCondition = "";
		
		if (originalvendorid != null && originalvendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND (vd.rut != 'IMP' OR ovd.id = :originalvendorid) "; //
		}
		
		if (locationid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND oc.deliverylocation_id = :locationid "; //
		}
		
		switch (criterium) {
		case 0:
			// VIGENTES
			whereCondition += "AND (oc.valid <= :now AND oc.expiration >= :now) AND ost.valid = TRUE "; //
			break;
		case 1:
			// PR�XIMAMENTE VIGENTES
			whereCondition += "AND oc.valid >= :now "; //		
			break;
		case 2:
			// N°MERO DE ORDEN
			whereCondition += "AND oc.number = :ordernumber "; //
			break;
		case 3:
			// ESTADO DE ORDEN
			whereCondition += "AND ost.id = :osttypeid "; //
			break;
		case 4:
			// FECHA DE EMISión
			whereCondition += "AND (oc.emitted >= :since AND oc.emitted <= :until) "; //
			break;
		case 5:
			// FECHA DE VIGENCIA
			whereCondition += "AND (oc.valid >= :since AND oc.valid <= :until) "; //
			break;
		case 6:
			// FECHA DE VENCIMIENTO
			whereCondition += "AND (oc.expiration >= :since AND oc.expiration <= :until) "; //
			break;			
		default:
			break;
		}
		
		String SQL = getOrderReportQueryString(1, null, whereCondition);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (originalvendorid != null && originalvendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("originalvendorid", originalvendorid);
		}
		if (locationid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("locationid", locationid);
		}

		switch (criterium) {
		case 0:
			// VIGENTES
			query.setDate("now", now);			
			break;
		case 1:
			// PR�XIMAMENTE VIGENTES
			query.setDate("now", now);			
			break;
		case 2:
			// N°MERO DE ORDEN
			query.setLong("ordernumber", ocnumber);
			break;
		case 3:
			// ESTADO DE ORDEN
			query.setLong("osttypeid", orderstatetypeid);
			break;
		case 4:
			// FECHA DE EMISión
			query.setDate("since", since);
			query.setDate("until", until);
			break;
		case 5:
			// FECHA DE VIGENCIA
			query.setDate("since", since);
			query.setDate("until", until);
			break;
		case 6:
			// FECHA DE VENCIMIENTO
			query.setDate("since", since);
			query.setDate("until", until);
			break;
		default:
			break;
		}		
				
		query.setResultTransformer(new LowerCaseResultTransformer(OrderReportTotalDataDTO.class));
		OrderReportTotalDataDTO result = (OrderReportTotalDataDTO) query.uniqueResult();				
		return result;
	}
	
	public SchedulePendingOrderDTO[] getSchedulePendingOrdersByValidStateDate(Date since, Date until) {
				
		String SQL =
			"(SELECT " + //
				"oc.number AS ordernumber, " + //
				"oc.emitted AS emitted, " + //
				"oc.valid AS valid, " + //
				"oc.expiration AS expiration, " + //
				"vd.code AS vendorcode, " + //
				"vd.name AS vendorname, " + //
				"dp.code AS departmentcode, " + //
				"dp.name AS departmentname, " + //
				"ost.name AS orderstatetypename, " + //
				"oc.currentstatetypedate AS orderstatetypedate, " + //
				"ot.name AS ordertypename, " + //
				"dll.code AS deliverylocationcode, " + //
				"dll.name AS deliverylocationname, " + //
				"dsl.code AS destinationlocationcode, " + //
				"dsl.name AS destinationlocationname, " + //
				"it.internalcode AS iteminternalcode, " + //
				"it.barcode AS itembarcode, " + //
				"it.name AS itemname, " + //
				"it.color AS itemcolor, " + //
				"it.dimension AS itemdimension, " + //
				"it.size AS itemsize, " + //
				"it.innerpack AS iteminnerpack, " + //
				"vi.vendoritemcode AS vendoritemcode, " + //
				"COALESCE(ft.name, '') AS flowtypename, " + //
				"pdd.units AS units, " + //
				"pdd.receivedunits AS receivedunits, " + //
				"pdd.todeliveryunits AS todeliveryunits, " + //
				"pdd.pendingunits AS pendingunits " + //
			"FROM " + //
				"logistica.order AS oc JOIN " + //
				"logistica.vendor AS vd ON vd.id = oc.vendor_id JOIN " + //
				"logistica.department AS dp ON dp.id = oc.department_id JOIN " + //
				"logistica.orderstatetype AS ost ON ost.id = oc.currentstatetype_id JOIN " + //
				"logistica.ordertype AS ot ON ot.id = oc.ordertype_id JOIN " + //
				"logistica.location AS dll ON dll.id = oc.deliverylocation_id JOIN " + //
				"logistica.predeliverydetail AS pdd ON pdd.order_id = oc.id JOIN " + //
				"logistica.location AS dsl ON dsl.id = pdd.location_id JOIN " + //
				"logistica.item AS it ON it.id = pdd.item_id JOIN " + //
				"logistica.vendoritem AS vi ON vi.vendor_id = vd.id AND vi.item_id = it.id LEFT JOIN " + //
				"logistica.flowtype AS ft ON ft.id = it.flowtype_id " + //
			"WHERE " + //
				"ost.valid IS TRUE AND vd.domestic IS TRUE AND " + //
				"oc.currentstatetypedate >= :since AND oc.currentstatetypedate < :until AND " + //
				"(ot.code = 'S' OR ot.code = 'C') AND dll.code = '200') " + //
			"UNION " + //
			"(SELECT " + //
				"oc.number AS ordernumber, " + //
				"oc.emitted AS emitted, " + //
				"oc.valid AS valid, " + //
				"oc.expiration AS expiration, " + //
				"vd.code AS vendorcode, " + //
				"vd.name AS vendorname, " + //
				"dp.code AS departmentcode, " + //
				"dp.name AS departmentname, " + //
				"ost.name AS orderstatetypename, " + //
				"oc.currentstatetypedate AS orderstatetypedate, " + //
				"ot.name AS ordertypename, " + //
				"dll.code AS deliverylocationcode, " + //
				"dll.name AS deliverylocationname, " + //
				"dsl.code AS destinationlocationcode, " + //
				"dsl.name AS destinationlocationname, " + //
				"it.internalcode AS iteminternalcode, " + //
				"it.barcode AS itembarcode, " + //
				"it.name AS itemname, " + //
				"it.color AS itemcolor, " + //
				"it.dimension AS itemdimension, " + //
				"it.size AS itemsize, " + //
				"it.innerpack AS iteminnerpack, " + //
				"vi.vendoritemcode AS vendoritemcode, " + //
				"COALESCE(ft.name, '') AS flowtypename, " + //
				"pdd.units AS units, " + //
				"pdd.receivedunits AS receivedunits, " + //
				"pdd.todeliveryunits AS todeliveryunits, " + //
				"pdd.pendingunits AS pendingunits " + //
			"FROM " + //
				"logistica.order AS oc JOIN " + //
				"logistica.vendor AS vd ON vd.id = oc.vendor_id JOIN " + //
				"logistica.department AS dp ON dp.id = oc.department_id JOIN " + //
				"logistica.orderstatetype AS ost ON ost.id = oc.currentstatetype_id JOIN " + //
				"logistica.ordertype AS ot ON ot.id = oc.ordertype_id JOIN " + //
				"logistica.location AS dll ON dll.id = oc.deliverylocation_id JOIN " + //
				"logistica.predeliverydetail AS pdd ON pdd.order_id = oc.id JOIN " + //
				"logistica.location AS dsl ON dsl.id = pdd.location_id JOIN " + //
				"logistica.item AS it ON it.id = pdd.item_id JOIN " + //
				"logistica.vendoritem AS vi ON vi.vendor_id = vd.id AND vi.item_id = it.id LEFT JOIN " + //
				"logistica.flowtype AS ft ON ft.id = it.flowtype_id " + //
			"WHERE " + //
				"ost.valid IS TRUE AND vd.domestic IS TRUE AND " + //
				"oc.currentstatetypedate >= :since AND oc.currentstatetypedate < :until AND " + //
				"(ot.code = 'S' OR ot.code = 'C') AND dll.code = '12' AND (dsl.code = '200' OR dsl.code = '12')) " + //
			"ORDER BY " + //
				"ordernumber";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		query.setResultTransformer(new LowerCaseResultTransformer(SchedulePendingOrderDTO.class));
		
		List list = query.list();
		SchedulePendingOrderDTO[] result = (SchedulePendingOrderDTO[]) list.toArray(new SchedulePendingOrderDTO[list.size()]);		
		return result;
	}
	
	public VeVCDOrderReportDataDTO[] getVeVCDOrdersByVendorLocationAndCriterium(Long[] salestoreid, Long vendorid, Long locationid, Date now, Long ocnumber, Long orderstatetypeid, Date since, Date until, String requestnumber, Integer criterium, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException {
		String whereCondition = "";
		
		if (locationid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			whereCondition += "AND oc.deliverylocation_id = :locationid ";
		}	
		
		if (salestoreid != null && salestoreid.length > 0 && salestoreid[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			whereCondition += " AND oc.salestore_id IN (:salestoreid) ";
		}
		
		switch (criterium) {
		case 0:
			// VIGENTES
			whereCondition += "AND oc.valid <= :now AND oc.expiration >= :now AND ost.valid IS TRUE ";
			break;
		case 1:
			// PR�XIMAMENTE VIGENTES
			whereCondition += "AND oc.valid >= :now ";
			break;
		case 2:
			// N°MERO DE ORDEN COMERCIAL
			whereCondition += "AND oc.number = :ordernumber ";
			break;
		case 3:
			// N°MERO DE ORDEN
			whereCondition += "AND oc.requestnumber = :requestnumber ";
			break;
		case 4:
			// ESTADO DE ORDEN
			whereCondition += "AND ost.id = :osttypeid ";
			break;
		case 5:
			// FECHA DE EMISión
			whereCondition += "AND oc.emitted >= :since AND oc.emitted < :until ";
			break;
		case 6:
			// FECHA PROPUESTA DE INGRESO
			whereCondition += "AND oc.valid >= :since AND oc.valid < :until ";
			break;
		case 7:
			// FECHA DE VENCIMIENTO
			whereCondition += "AND oc.expiration >= :since AND oc.expiration < :until ";
			break;
		case 8: 
			// FECHA DE COMPROMISO
			whereCondition += "AND oc.originaldeliverydate >= :since AND oc.originaldeliverydate < :until ";
			break;	
		case 9:
			// TODOS LOS ESTADOS VIGENTES
			whereCondition += "AND ost.valid IS TRUE ";
			break;		
		default:
			break;
		}
				
		String SQL = getVeVCDOrderReportQueryString(2, orderby, whereCondition);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (locationid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("locationid", locationid);
		}		
		if (salestoreid != null && salestoreid.length > 0 && salestoreid[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreid", salestoreid);
		}
		
		switch (criterium) {
		case 0:
			// VIGENTES
			query.setDate("now", now);			
			break;
		case 1:
			// PR�XIMAMENTE VIGENTES
			query.setDate("now", now);			
			break;
		case 2:
			// N°MERO DE ORDEN COMERCIAL
			query.setLong("ordernumber", ocnumber);
			break;
		case 3:
			// N°MERO DE ORDEN
			query.setString("requestnumber", requestnumber);
			break;
		case 4:
			// ESTADO DE ORDEN
			query.setLong("osttypeid", orderstatetypeid);
			break;
		case 5:
			// FECHA DE EMISión
			query.setDate("since", since);
			query.setDate("until", until);
			break;
		case 6:
			// FECHA PROPUESTA DE INGRESO
			query.setDate("since", since);
			query.setDate("until", until);
			break;
		case 7:
			// FECHA DE VENCIMIENTO
			query.setDate("since", since);
			query.setDate("until", until);
			break;
		case 8:
			// FECHA DE COMPROMISO
			query.setDate("since", since);
			query.setDate("until", until);
			break;			
		case 9:
			// VIGENTES			
			break;
		default:
			break;
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(VeVCDOrderReportDataDTO.class));
		if (ispaginated) { //si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		VeVCDOrderReportDataDTO[] result = (VeVCDOrderReportDataDTO[]) list.toArray(new VeVCDOrderReportDataDTO[list.size()]);		
		return result;	
	}
	
	public VeVCDOrderReportTotalDataDTO getVeVCDOrdersCountByVendorLocationAndCriterium(Long[] salestoreid, Long vendorid, Long locationid, Date now, Long ocnumber, Long orderstatetypeid, Date since, Date until, String requestnumber, Integer criterium) throws ServiceException {
		String whereCondition = "";
		
		if (locationid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			whereCondition += "AND oc.deliverylocation_id = :locationid ";
		}	
		
		if (salestoreid != null && salestoreid.length > 0 && salestoreid[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			whereCondition += " AND oc.salestore_id IN (:salestoreid) ";
		}
		
		switch (criterium) {
		case 0:
			// VIGENTES
			whereCondition += "AND oc.valid <= :now AND oc.expiration >= :now AND ost.valid IS TRUE ";
			break;
		case 1:
			// PR�XIMAMENTE VIGENTES
			whereCondition += "AND oc.valid >= :now ";
			break;
		case 2:
			// N°MERO DE ORDEN COMERCIAL
			whereCondition += "AND oc.number = :ordernumber ";
			break;
		case 3:
			// N°MERO DE ORDEN
			whereCondition += "AND oc.requestnumber = :requestnumber ";
			break;
		case 4:
			// ESTADO DE ORDEN
			whereCondition += "AND ost.id = :osttypeid ";
			break;
		case 5:
			// FECHA DE EMISión
			whereCondition += "AND oc.emitted >= :since AND oc.emitted < :until ";
			break;
		case 6:
			// FECHA PROPUESTA DE INGRESO
			whereCondition += "AND oc.valid >= :since AND oc.valid < :until ";
			break;
		case 7:
			// FECHA DE VENCIMIENTO
			whereCondition += "AND oc.expiration >= :since AND oc.expiration < :until ";
			break;
		case 8: 
			// FECHA DE COMPROMISO
			whereCondition += "AND oc.originaldeliverydate >= :since AND oc.originaldeliverydate < :until ";
			break;	
		case 9:
			// TODOS LOS ESTADOS VIGENTES
			whereCondition += "AND ost.valid IS TRUE ";
			break;		
		default:
			break;
		}
		
		String SQL = getVeVCDOrderReportQueryString(1, null, whereCondition);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (locationid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("locationid", locationid);
		}		
		if (salestoreid != null && salestoreid.length > 0 && salestoreid[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreid", salestoreid);
		}
		
		switch (criterium) {
		case 0:
			// VIGENTES
			query.setDate("now", now);			
			break;
		case 1:
			// PR�XIMAMENTE VIGENTES
			query.setDate("now", now);			
			break;
		case 2:
			// N°MERO DE ORDEN COMERCIAL
			query.setLong("ordernumber", ocnumber);
			break;
		case 3:
			// N°MERO DE ORDEN
			query.setString("requestnumber", requestnumber);
			break;
		case 4:
			// ESTADO DE ORDEN
			query.setLong("osttypeid", orderstatetypeid);
			break;
		case 5:
			// FECHA DE EMISión
			query.setDate("since", since);
			query.setDate("until", until);
			break;
		case 6:
			// FECHA PROPUESTA DE INGRESO
			query.setDate("since", since);
			query.setDate("until", until);
			break;
		case 7:
			// FECHA DE VENCIMIENTO
			query.setDate("since", since);
			query.setDate("until", until);
			break;
		case 8:
			// FECHA DE COMPROMISO
			query.setDate("since", since);
			query.setDate("until", until);
			break;			
		case 9:
			// VIGENTES			
			break;
		default:
			break;
		}
				
		query.setResultTransformer(new LowerCaseResultTransformer(VeVCDOrderReportTotalDataDTO.class));
		VeVCDOrderReportTotalDataDTO result = (VeVCDOrderReportTotalDataDTO) query.uniqueResult();				
		return result;
	}		
	
	public int getOrdersReportCountByOrders(Long[] orderIds){		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.buyorders.entities.Order.getCountOrderReportByOrder");
		query.setParameterList("orderids", orderIds);
		int total = ((BigInteger)query.list().get(0)).intValue();	
		return total;		
	}	
	
	public ExcelOrderReportDataDTO[] getExcelOrderReportByOrders(Long[] orderids) throws ServiceException {
		if (orderids == null || orderids.length <= 0) {
			throw new OperationFailedException("No se puede generar reporte de órdenes. Parámetro nulo o vacío.");
		}
		
		// Se divide el parámetro de entrada en bloques de 50 elementos
		ExcelOrderReportDataDTO[] ocreport = null;
		int chunksize = 50;
		Object[] splitids = ClassUtilities.SplitArray(orderids, Long.class, chunksize);
		Object[] reportarray = new Object[splitids.length];
		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];
			if (ids.length <= 0) {
				reportarray[i] = new OrderReportDataDTO[0];
				continue;
			}
			
			SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.buyorders.entities.Order.getExcelOrderReportByOrder");
			query.setParameterList("orderids", ids);
			query.setResultTransformer(new LowerCaseResultTransformer(ExcelOrderReportDataDTO.class));
			List list = query.list();
			
			ExcelOrderReportDataDTO[] result = (ExcelOrderReportDataDTO[]) list.toArray(new ExcelOrderReportDataDTO[list.size()]);
			reportarray[i] = result;
		}
		
		ocreport = (ExcelOrderReportDataDTO[]) ClassUtilities.UnsplitArrays(reportarray, ExcelOrderReportDataDTO.class);
		return ocreport;
	}
	
	public VeVCDExcelOrderReportDataDTO[] getExcelVeVCDOrderReportByOrders(Long[] orderids) throws ServiceException {
		if (orderids == null || orderids.length <= 0) {
			throw new OperationFailedException("No se puede generar reporte de órdenes. Parámetro nulo o vacío.");
		}
		
		// Se divide el parámetro de entrada en bloques de 50 elementos
		VeVCDExcelOrderReportDataDTO[] ocreport = null;
		int chunksize = 50;
		Object[] splitids = ClassUtilities.SplitArray(orderids, Long.class, chunksize);
		Object[] reportarray = new Object[splitids.length];
		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];
			if (ids.length <= 0) {
				reportarray[i] = new VeVCDOrderReportDataDTO[0];
				continue;
			}
			
			SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.buyorders.entities.Order.getExcelVeVCDOrderReportByOrder");
			query.setParameterList("orderids", ids);
			query.setResultTransformer(new LowerCaseResultTransformer(VeVCDExcelOrderReportDataDTO.class));
			
			List list = query.list();
			VeVCDExcelOrderReportDataDTO[] result = (VeVCDExcelOrderReportDataDTO[]) list.toArray(new VeVCDExcelOrderReportDataDTO[list.size()]);
			reportarray[i] = result;
		}
		
		ocreport = (VeVCDExcelOrderReportDataDTO[]) ClassUtilities.UnsplitArrays(reportarray, VeVCDExcelOrderReportDataDTO.class);
		return ocreport;
	}
		
	public OrderIdsByPagesW getOrdersIdsByPages(Long vendorid, Long originalvendorid, Long locationid, Date now, Long ocnumber, Long ostid, Date since, Date until, int rows, Integer filterType, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws ServiceException{
		
		OrderIdsByPagesW resultW =  new OrderIdsByPagesW();
		
		int total = 0;		
		OrderReportDataDTO[] result;
		OrderReportDataDTO[] orders;		
		List<Object> totalOrders = new ArrayList<Object>();
						
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				
				result = getOrdersByVendorLocationAndCriterium(vendorid, originalvendorid, locationid, now, ocnumber, ostid, since, until, filterType, j, rows, orderby, true);
				totalOrders.add(result);					
			}			
		}
		Object[] reportorderarray = (Object[]) totalOrders.toArray(new Object[totalOrders.size()]);		
		orders = (OrderReportDataDTO[]) ClassUtilities.UnsplitArrays(reportorderarray, OrderReportDataDTO.class);
		
		Long[] orderIds = new Long[orders.length];
		
		//Obtengo solo los ids de las ordenes de las paginas solicitadas		
		for (int i = 0 ; i < orders.length; i++){
			orderIds[i] = orders[i].getOrderid();
		}
		
		//Consultar por cantidad de registros que lanzar�a reporte excel
		if (orderIds.length > 0){
			total = getOrdersReportCountByOrders(orderIds);
			
		}
		resultW.setOrderIds(orderIds);
		resultW.setTotalRows(total);		
		return resultW;		
	}	
	
	public OrderIdsByPagesW getVeVCDOrdersIds(Long[] salestoreid, Long vendorid, Long locationid, Date now, Long ocnumber, Long ostid, Date since, Date until, String requestnumber, Integer filterType, OrderCriteriaDTO[] orderby) throws ServiceException{
		
		OrderIdsByPagesW resultW =  new OrderIdsByPagesW();
		
		VeVCDOrderReportDataDTO[] orders = getVeVCDOrdersByVendorLocationAndCriterium(salestoreid, vendorid, locationid, now, ocnumber, ostid, since, until, requestnumber, filterType, 0, 0, orderby, false);
		Long[] orderIds = new Long[0];
		int total = 0;
		if (orders != null && orders.length > 0) {
			orderIds = new Long[orders.length];
			
			// Obtengo los ids de las órdenes
			for (int i = 0 ; i < orders.length; i++) {
				orderIds[i] = orders[i].getOrderid();
			}
			
			//Consultar por cantidad de registros que lanzaría reporte excel
			if (orderIds.length > 0) {
				total = getOrdersReportCountByOrders(orderIds);
				
			}
		}
		
		resultW.setOrderIds(orderIds);
		resultW.setTotalRows(total);		
		
		return resultW;		
	}
	
	public OrderIdsByPagesW getVeVCDOrdersIdsByPages(Long[] salestoreid, Long vendorid, Long locationid, Date now, Long ocnumber, Long ostid, Date since, Date until, String requestnumber, int rows, Integer filterType, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws ServiceException{
		
		OrderIdsByPagesW resultW =  new OrderIdsByPagesW();
		
		int total = 0;		
		VeVCDOrderReportDataDTO[] result;
		VeVCDOrderReportDataDTO[] orders;		
		List<Object> totalOrders = new ArrayList<Object>();
						
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				
				result = getVeVCDOrdersByVendorLocationAndCriterium(salestoreid, vendorid, locationid, now, ocnumber, ostid, since, until, requestnumber, filterType, j, rows, orderby, true);
				totalOrders.add(result);					
			}			
		}
		Object[] reportorderarray = (Object[]) totalOrders.toArray(new Object[totalOrders.size()]);		
		orders = (VeVCDOrderReportDataDTO[]) ClassUtilities.UnsplitArrays(reportorderarray, VeVCDOrderReportDataDTO.class);
		
		Long[] orderIds = new Long[orders.length];
		
		//Obtengo solo los ids de las ordenes de las paginas solicitadas		
		for (int i = 0 ; i < orders.length; i++){
			orderIds[i] = orders[i].getOrderid();
		}
		
		//Consultar por cantidad de registros que lanzar�a reporte excel
		if (orderIds.length > 0){
			total = getOrdersReportCountByOrders(orderIds);
			
		}
		resultW.setOrderIds(orderIds);
		resultW.setTotalRows(total);		
		return resultW;		
	}
	
	public Integer doExpireBuyOrders() throws OperationFailedException{
		Integer countorders = 0;
		
		String SQL = "select logistica.vence_oc()";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		countorders = ((Integer) query.uniqueResult());				
		return countorders;
		
		
//		Integer countorders = 0;
//		ResultSet resultSet;
//		
//		try{
//			String sql = "select * from logistica.vence_oc()";
//			CallableStatement callStmt1 = getSession().connection().prepareCall(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			resultSet = callStmt1.executeQuery();
//			if (resultSet.absolute(1))
//				countorders = resultSet.getInt(1);
//			return countorders;								
//		} catch (SQLException e) {
//			throw new OperationFailedException("Error al ejecutar CallableStatement", e);
//		}		
	}	
	
	public OrderW[] getOrdersByIdsAndVendor(Long[] orderIds, Long vendorId) throws OperationFailedException, NotFoundException{		

        PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
        PropertyInfoDTO pven = new PropertyInfoDTO("vendor.id", "vendorid", vendorId);
        properties[0] = pven;
        List ids = Arrays.asList(orderIds);
        properties[1] = new PropertyInfoDTO("oc.id", "ids", ids);
		StringBuilder sb = new StringBuilder("FROM Order oc where oc.vendor.id = :vendorid AND oc.id IN (:ids)");
		
        List list = getByQuery(sb.toString(), properties);

        return (OrderW[]) list.toArray(new OrderW[list.size()]);
		
	}
	
	public OrderW[] getOrdersByDelivery(Long deliveryid) throws OperationFailedException, NotFoundException {
		
        StringBuilder sb = new StringBuilder(
        								"SELECT DISTINCT " + //
        									"oc " + //
        								"FROM " + //
        									"Order oc, " + //
        									"OrderDelivery odl " + //
        								"WHERE " + //
        									"oc.id = odl.order.id AND odl.delivery.id = :deliveryid"); //
        
        PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("odl.delivery.id", "deliveryid", deliveryid);
		
		List list = getByQuery(sb.toString(), properties);

        return (OrderW[]) list.toArray(new OrderW[list.size()]);
	}
	
	/*
	 * Retorna las oc pendientes por enviar a SOA
	 * Se puede agregar mas de un estado soa pendiente por enviar
	 * */
	public OrderW[] getOrdersToSendSoa(Long vendorid, Date since, Long... soastateids) throws OperationFailedException, NotFoundException {
		PropertyInfoDTO[] properties = new PropertyInfoDTO[4];
		
		properties[0] = new PropertyInfoDTO("ost.code", "occodestate", "LIBERADA");
		properties[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorid);
		properties[2] = new PropertyInfoDTO("os.when", "since", since );
        properties[3] = new PropertyInfoDTO("currentsoastatetype.id", "currentsoastatetypeid", Arrays.asList(soastateids));

        StringBuilder sb = new StringBuilder("Select distinct oc FROM Order oc, OrderState os, OrderStateType ost " +	//tablas
        		"where oc.id = os.order.id and os.orderstatetype.id = ost.id " +	//on de tablas
        		"and ost.code = :occodestate and oc.vendor.id = :vendorid AND os.when >= :since AND oc.currentsoastatetype.id  in (:currentsoastatetypeid) ");
        List list = getByQuery(sb.toString(), properties);

        return (OrderW[]) list.toArray(new OrderW[list.size()]);
	}
	
	public PendingSOAOrderDTO[] getPendingSOAOrdersByVendor(Long soastatetype, Long vendorid, Date soapendingtime, Date activationdate){		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.buyorders.entities.Order.getPendingSOAOrdersByVendor");
		query.setLong("soastatetype", soastatetype);
		query.setLong("vendorid", vendorid);		
		query.setTimestamp("soapendingtime", soapendingtime);
		query.setTimestamp("activationdate", activationdate);
		query.setResultTransformer(new LowerCaseResultTransformer(PendingSOAOrderDTO.class));
		List list = query.list();
		PendingSOAOrderDTO[] result = (PendingSOAOrderDTO[]) list.toArray(new PendingSOAOrderDTO[list.size()]);
		return result;	
		
	}
	
	public VeVCDDataDTO getVeVCDDataByOrderNumber(Long ordernumber){
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.buyorders.entities.Order.getVeVCDDataByOrderNumber");
		query.setLong("ordernumber", ordernumber);
		query.setResultTransformer(new LowerCaseResultTransformer(VeVCDDataDTO.class));
		
		return (VeVCDDataDTO) query.uniqueResult();

	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "Order";
	}
	@Override
	protected void setEntityname() {
		entityname = "Order";
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

	public PendingSOAOrderDTO[] getPendingSOAOrdersByVendor(Long soastatetype, Long vendorid, Date soapendingtime, Date activationdate, int type){		
//		tipo 1= normal, tipo 2 = solo info de cliente
		SQLQuery query=null;
		if(type==1){
			query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.buyorders.entities.Order.getPendingSOAOrdersByVendor");
		}else if(type==2){
			query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.buyorders.entities.Order.getPendingSOAOrdersByVendorWithClientInfo");
		}
		query.setLong("soastatetype", soastatetype);
		query.setLong("vendorid", vendorid);		
		query.setTimestamp("soapendingtime", soapendingtime);
		query.setTimestamp("activationdate", activationdate);
		query.setResultTransformer(new LowerCaseResultTransformer(PendingSOAOrderDTO.class));
		List list = query.list();
		PendingSOAOrderDTO[] result = (PendingSOAOrderDTO[]) list.toArray(new PendingSOAOrderDTO[list.size()]);
		return result;		
	}
	
	public List<VeVVendorNotificationDTO> getOrdersByVendorsAndReleasedDate(Long[] vendorIds, Date since, Date until) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.buyorders.entities.Order.getOrdersByVendorsAndReleasedDate");
		query.setParameterList("vendorIds", vendorIds);
		query.setDate("since", since);		
		query.setDate("until", until);
		
		query.setResultTransformer(new LowerCaseResultTransformer(VeVVendorNotificationDTO.class));
		List result = (List<VeVVendorNotificationDTO>)query.list();
		return result;
	}
	
}
