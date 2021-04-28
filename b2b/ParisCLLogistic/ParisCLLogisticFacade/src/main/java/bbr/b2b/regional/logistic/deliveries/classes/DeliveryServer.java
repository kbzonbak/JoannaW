package bbr.b2b.regional.logistic.deliveries.classes;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.entities.OrderType;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderContainerDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PreDeliveryDetailDataDTO;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryW;
import bbr.b2b.regional.logistic.deliveries.entities.Delivery;
import bbr.b2b.regional.logistic.deliveries.entities.DeliveryStateType;
import bbr.b2b.regional.logistic.deliveries.entities.DeliveryType;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDeliveryOfOrdersAndFlowTypesDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.LabelDODeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListDataDTO;
import bbr.b2b.regional.logistic.items.classes.FlowTypeServerLocal;
import bbr.b2b.regional.logistic.items.entities.FlowType;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/deliveries/DeliveryServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DeliveryServer extends LogisticElementServer<Delivery, DeliveryW> implements DeliveryServerLocal {

	private Map<String, String> mapGetDeliverySQL = new HashMap<String, String>();
	{
		mapGetDeliverySQL.put("DELIVERYNUMBER", "dl.number");
		mapGetDeliverySQL.put("DELIVERYTYPECODE", "dt.code");
		mapGetDeliverySQL.put("DELIVERYTYPENAME", "dt.name");
		mapGetDeliverySQL.put("CONTAINER", "dl.container");
		mapGetDeliverySQL.put("GUIDE", "dl.refdocument");
		mapGetDeliverySQL.put("IMP", "dl.imp");		
		mapGetDeliverySQL.put("FLOWTYPECODE", "ft.code");		
		mapGetDeliverySQL.put("FLOWTYPEDESC", "ft.name");		
		mapGetDeliverySQL.put("CREATIONDATE", "creationdate");
		mapGetDeliverySQL.put("DELIVERYSTATETYPECODE", "dst.code");	
		mapGetDeliverySQL.put("DELIVERYSTATETYPEDESC", "dst.name");	
		mapGetDeliverySQL.put("DELIVERYLOCATIONCODE", "lo.code");
		mapGetDeliverySQL.put("DELIVERYLOCATION", "lo.name");
		mapGetDeliverySQL.put("ACTION", "dst.action1");		
		mapGetDeliverySQL.put("SCHEDULING", "dl.number"); // Se ordena por cualquier atributo, solo para que la query funcione
	}
	
	private Map<String, String> mapGetPreDeliverySQL = new HashMap<String, String>();
	{
		mapGetPreDeliverySQL.put("ORDERNUMBER", "oc.number");
		mapGetPreDeliverySQL.put("ITEMSKU", "it.internalcode");
		mapGetPreDeliverySQL.put("FLOWTYPE", "ft.name");
		mapGetPreDeliverySQL.put("ITEMDESC", "it.name");
		mapGetPreDeliverySQL.put("LOCATIONCODE", "lo.code");		
		mapGetPreDeliverySQL.put("LOCATIONDESC", "lo.name");		
		mapGetPreDeliverySQL.put("TOTALUNITS", "pdd.units");		
		mapGetPreDeliverySQL.put("TODELIVERYUNITS", "pdd.units");
		mapGetPreDeliverySQL.put("CURVE", "curve");		
		mapGetPreDeliverySQL.put("ATCCODE", "atccode");
		mapGetPreDeliverySQL.put("ORIGINALVENDORCODE", "originalvendorcode");		
		mapGetPreDeliverySQL.put("ORIGINALVENDORNAME", "originalvendorname");
	}
	
	private Map<String, String> mapGetOrdersContainersSQL = new HashMap<String, String>();
	{
		mapGetOrdersContainersSQL.put("ORDERNUMBER", "oc.number");
		mapGetOrdersContainersSQL.put("CONTAINER", "dl.container");
	}
	
	@EJB
	VendorServerLocal vendorserver;

	@EJB
	DeliveryStateTypeServerLocal currentstatetypeserver;

	@EJB
	LocationServerLocal locationserver;

	@EJB
	FlowTypeServerLocal flowtypeserver;

	@EJB
	OrderTypeServerLocal ordertypeserver;

	@EJB
	DeliveryTypeServerLocal deliverytypeserver;

	public DeliveryW addDelivery(DeliveryW delivery) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DeliveryW) addElement(delivery);
	}
	
	public void deleteDelivery(Long deliveryid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(deliveryid);
	}
	
	public DeliveryW[] getDeliverys() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DeliveryW[]) getIdentifiables();
	}
	public DeliveryW updateDelivery(DeliveryW delivery) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DeliveryW) updateElement(delivery);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Delivery entity, DeliveryW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setCurrentstatetypeid(entity.getCurrentstatetype() != null ? new Long(entity.getCurrentstatetype().getId()) : new Long(0));
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
		wrapper.setFlowtypeid(entity.getFlowtype() != null ? new Long(entity.getFlowtype().getId()) : new Long(0));
		wrapper.setOrdertypeid(entity.getOrdertype() != null ? new Long(entity.getOrdertype().getId()) : new Long(0));
		wrapper.setDeliverytypeid(entity.getDeliverytype() != null ? new Long(entity.getDeliverytype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Delivery entity, DeliveryW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getCurrentstatetypeid() != null && wrapper.getCurrentstatetypeid() > 0) { 
			DeliveryStateType currentstatetype = currentstatetypeserver.getReferenceById(wrapper.getCurrentstatetypeid());
			if (currentstatetype != null) { 
				entity.setCurrentstatetype(currentstatetype);
			}
		}
		if (wrapper.getLocationid() != null && wrapper.getLocationid() > 0) { 
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if (location != null) { 
				entity.setLocation(location);
			}
		}
		if (wrapper.getFlowtypeid() != null && wrapper.getFlowtypeid() > 0) { 
			FlowType flowtype = flowtypeserver.getReferenceById(wrapper.getFlowtypeid());
			if (flowtype != null) { 
				entity.setFlowtype(flowtype);
			}
		}
		if (wrapper.getOrdertypeid() != null && wrapper.getOrdertypeid() > 0) { 
			OrderType ordertype = ordertypeserver.getReferenceById(wrapper.getOrdertypeid());
			if (ordertype != null) { 
				entity.setOrdertype(ordertype);
			}
		}
		if (wrapper.getDeliverytypeid() != null && wrapper.getDeliverytypeid() > 0) { 
			DeliveryType deliverytype = deliverytypeserver.getReferenceById(wrapper.getDeliverytypeid());
			if (deliverytype != null) { 
				entity.setDeliverytype(deliverytype);
			}
		}
	}

	private String getQueryString(int queryType, OrderCriteriaDTO[] orderby, String whereCondition) throws OperationFailedException, AccessDeniedException{
		
		String SQL 				= "";
		String orderByCondition	= "";

		if (queryType == 1){
			SQL = 			
				"SELECT " + //
					"COUNT (DISTINCT dl.id)"; //		
		} else {
			SQL =
				"SELECT DISTINCT " + //
					"dl.id AS deliveryid, " + //  
					"dl.number AS deliverynumber, " + //  
					"dt.code AS deliverytypecode, " + //
					"dt.name AS deliverytypename, " + //
					"dl.container AS container, " + //	
					"dl.refdocument AS guide, " + // JPE 20190403: NO SE EST� MOSTRANDO
					"dl.imp AS imp, " + // JPE 20190403: NO SE EST� MOSTRANDO
					"ft.code AS flowtypecode, " + //
					"ft.name AS flowtypedesc, " + //
					"logistica.tostr(dl.created) AS creationdate, " + // 
					"dst.code AS deliverystatetypecode, " + //  
					"dst.name AS deliverystatetypedesc, " + //
					"lo.code AS deliverylocationcode, " + //
					"lo.name AS deliverylocation, " + // 
					"dst.action1 AS action ";
			
			orderByCondition = getOrderByString(mapGetDeliverySQL, orderby);
		}
		
		SQL +=
				"FROM " + //
					"logistica.delivery AS dl JOIN " + //  
					"logistica.vendor AS vd ON vd.id = dl.vendor_id JOIN " + //  
					"logistica.deliverystatetype AS dst ON dst.id = dl.currentstatetype_id JOIN " + //  
					"logistica.location AS lo ON lo.id = dl.location_id JOIN " + //
					"logistica.orderdelivery AS odl ON odl.delivery_id = dl.id JOIN " + //
					"logistica.order AS oc ON oc.id = odl.order_id JOIN " + //
					"logistica.deliverytype AS dt ON dt.id = dl.deliverytype_id JOIN " + //
					"logistica.flowtype AS ft ON ft.id = odl.flowtype_id LEFT JOIN " + //
					"logistica.vendor AS ovd ON ovd.code = oc.vendorcodeimp " + //
				"WHERE " + 
					"vd.id = :vendorid " +
					whereCondition +	
				orderByCondition ;		
		
		return SQL;		
	}		
	
	private String getPreDeliveryQueryString(int queryType, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException{
		
		String SQL 				= "";
		String orderByCondition	= "";		
		
		if (queryType == 1){
			SQL = 			
				"SELECT " + //
					"COUNT (DISTINCT oc.id) "; //		
		} else {
			SQL =
				"SELECT " + //
					"oc.id AS orderid, " + //
					"oc.number AS ordernumber, " + //
					"it.id AS itemid, " + //
					"lo.id AS locationid, " + //
					"it.internalcode AS itemsku, " + //
					"ft.id AS flowtypeid, " + //
					"ft.name AS flowtype, " + //
					"it.name AS itemdesc, " + // 
					"lo.code AS locationcode, " + //
					"lo.name AS locationdesc, " + //
					"pdd.units AS totalunits, " + // 
					"pdd.units AS todeliveryunits, " + //
					"COALESCE(ia.curve, 0) AS curve, " + //
					"atc.id AS atcid, " + //
					"COALESCE(atc.code, '') AS atccode, " + 
					"ovd.id AS originalvendorid, " + //
					"COALESCE(ovd.code, '') AS originalvendorcode, " + //
					"COALESCE(ovd.name, '') AS originalvendorname "; //
			
			orderByCondition = getOrderByString(mapGetPreDeliverySQL, orderby);
		}
		
		SQL += 
			"FROM " + //
				"logistica.order AS oc JOIN " + //
				"logistica.orderdetail AS od ON od.order_id = oc.id JOIN " + //
				"logistica.item AS it ON it.id = od.item_id JOIN " + // 
				"logistica.predeliverydetail AS pdd ON pdd.order_id = od.order_id AND pdd.item_id = od.item_id JOIN " + //
				"logistica.location AS lo ON lo.id = pdd.location_id JOIN " + //
				"logistica.flowtype AS ft ON ft.id = it.flowtype_id JOIN " + // 
				"logistica.vendor AS vd ON vd.id = oc.vendor_id LEFT JOIN " + //
				"logistica.vendor AS ovd ON ovd.code = oc.vendorcodeimp LEFT JOIN " + //
				"logistica.item_atc AS ia ON ia.item_id = it.id LEFT JOIN " + //
				"logistica.atc AS atc ON atc.id = ia.atc_id " + //
			"WHERE " + //
				"vd.id = :vendorid AND oc.id IN (:ordersid) " + //
				orderByCondition ;
		
		return SQL;		
	}		
	
	public DeliveryReportDataDTO[] getDeliveryReport(String text, Long number, Long vendorid, Long originalvendorid, int filtertype, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException{
		String whereCondition = "";
		
		if (originalvendorid != null && originalvendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND (vd.rut != 'IMP' OR ovd.id = :originalvendorid) "; //
		}
		
		switch (filtertype) {
		case 0:
			whereCondition += "AND dst.showable = :showable "; //
			break;
		case 1:
			whereCondition += "AND dl.number = :deliverynumber "; //
			break;		
		case 2:
			whereCondition += "AND oc.number = :ordernumber "; //		
			break;
		case 3:
			whereCondition += "AND dst.id = :dstid "; //
			break;		
		case 4:
			whereCondition += "AND dl.container = :container "; //
			break;	
		case 5:
			whereCondition += "AND dl.refdocument = :guide "; //
			break;	
		case 6:
			whereCondition += "AND dl.imp = :imp "; //
			break;	
		default:
			break;
		}
		
		String SQL = getQueryString(2, orderby, whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (originalvendorid != null && originalvendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("originalvendorid", originalvendorid);
		}
		
		switch (filtertype) {
		case 0:
			query.setBoolean("showable", true);
			break;
		case 1:
			query.setLong("deliverynumber", number);
			break;
		case 2:
			query.setLong("ordernumber" , number);
			break;
		case 3:
			query.setLong("dstid", number);	
			break;	
		case 4:
			query.setString("container", text);	
			break;		
		case 5:
			query.setLong("guide", number);	
			break;		
		case 6:
			query.setString("imp", text);	
			break;		
		default:
			break;
		}				
		
		query.setResultTransformer(new LowerCaseResultTransformer(DeliveryReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List list = query.list();
		DeliveryReportDataDTO[] result = (DeliveryReportDataDTO[]) list.toArray(new DeliveryReportDataDTO[list.size()]);		
		
		return result;
	}
	
	public Integer getDeliveryCountReport(String text, Long number, Long vendorid, Long originalvendorid, int filtertype) throws OperationFailedException, AccessDeniedException{
		String whereCondition = "";
		
		if (originalvendorid != null && originalvendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND (vd.rut != 'IMP' OR ovd.id = :originalvendorid) "; //
		}
		
		switch (filtertype) {
		case 0:
			whereCondition += "AND dst.showable = :showable "; //
			break;
		case 1:
			whereCondition += "AND dl.number = :deliverynumber "; //
			break;		
		case 2:
			whereCondition += "AND oc.number = :ordernumber "; //		
			break;
		case 3:
			whereCondition += "AND dst.id = :dstid "; //
			break;		
		case 4:
			whereCondition += "AND dl.container = :container "; //
			break;	
		case 5:
			whereCondition += "AND dl.refdocument = :guide "; //
			break;	
		case 6:
			whereCondition += "AND dl.imp = :imp "; //
			break;	
		default:
			break;
		}
		
		String SQL = getQueryString(1, null, whereCondition);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (originalvendorid != null && originalvendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("originalvendorid", originalvendorid);
		}
		
		switch (filtertype) {
		case 0:
			query.setBoolean("showable", true);
			break;
		case 1:
			query.setLong("deliverynumber", number);
			break;
		case 2:
			query.setLong("ordernumber" , number);
			break;
		case 3:
			query.setLong("dstid", number);	
			break;	
		case 4:
			query.setString("container", text);	
			break;		
		case 5:
			query.setLong("guide", number);	
			break;		
		case 6:
			query.setString("imp", text);	
			break;		
		default:
			break;
		}					
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;	
	}
	
	public ProposedPackingListDataDTO[] getProposedPackingListByDetailOrInnerpack(Long deliveryid, Long vendorid) throws OperationFailedException, AccessDeniedException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.Delivery.getProposedPackingListByDetailOrInnerpack");
		query.setLong("vendorid", vendorid);
		query.setLong("deliveryid", deliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(ProposedPackingListDataDTO.class));
		List list = query.list();
		ProposedPackingListDataDTO[] result = (ProposedPackingListDataDTO[]) list.toArray(new ProposedPackingListDataDTO[list.size()]);
		return result;	
	}
	
	public DeliveryDTO[] getCencosudImportedDeliveriesByContainerDeliveryLocationFlowTypeAndCodeType(String containernumber, Long deliverylocationid, Long flowtypeid, String codetype) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL =
			"SELECT DISTINCT " + //
				"d.id, d.number, d.created, d.currentstatetypedate, d.receptionnumber, d.receptiondate, d.refdocument, d.container, d.imp, " + //
				"d.complementscount, d.hasatc, d.deliverytype_id AS deliverytypeid, d.currentstatetype_id AS currentstatetypeid, " + //
				"d.location_id AS locationid, d.ordertype_id AS ordertypeid, d.flowtype_id AS flowtypeid, d.vendor_id AS vendorid " + //
			"FROM " + //
				"logistica.delivery AS d JOIN " + //
				"logistica.orderdelivery AS od ON od.delivery_id = d.id JOIN " + //
				"logistica.order AS oc ON oc.id = od.order_id JOIN " + //
				"logistica.vendor AS vd ON vd.id = oc.vendor_id JOIN " + //
				"logistica.bulk AS bu ON bu.delivery_id = od.delivery_id " + //				
			"WHERE " + //
				"vd.rut = 'IMP' AND d.container = :containernumber AND oc.deliverylocation_id = :deliverylocationid AND " + //
				"od.flowtype_id = :flowtypeid AND d.hasatc IS " + (codetype.equals("ATC") ? "TRUE" : "FALSE"); //
								
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("containernumber", containernumber);
		query.setLong("deliverylocationid", deliverylocationid);
		query.setLong("flowtypeid", flowtypeid);				
		query.setResultTransformer(new LowerCaseResultTransformer(DeliveryDTO.class));

		List list = query.list();
		return (DeliveryDTO[]) list.toArray(new DeliveryDTO[list.size()]);
	}
	
	public Long getNextSequenceDeliveryNumber() throws OperationFailedException, NotFoundException {
        Query query = getEntityManager().createNativeQuery("select nextval('LOGISTICA.DELIVERYNUMBER_SEQ')");
        BigInteger bigint = (BigInteger) query.getSingleResult();
        Long result = bigint.longValue();
        return result;
	}
	
	public String getNextSequenceTransactionNumber(int length) throws OperationFailedException, NotFoundException {
        Query query = getEntityManager().createNativeQuery("select nextval('LOGISTICA.TRANSACTIONNUMBER_SEQ')");
        BigInteger bigint = (BigInteger) query.getSingleResult();
        Long sequence = bigint.longValue();
        
        String result = Long.toString(sequence);
		int diff = length - result.length();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < diff; i++) {
			buffer.append("0");
		}
		buffer.append(result);
		result = buffer.toString();
		return result;
	}
	
	public PreDeliveryDetailDataDTO[] getPreDeliveryDetailsForImportedDelivery(Long[] orderid, Long vendorid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException{
		String SQL = getPreDeliveryQueryString(2, orderby);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setParameterList("ordersid", orderid);
		query.setResultTransformer(new LowerCaseResultTransformer(PreDeliveryDetailDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List list = query.list();
		PreDeliveryDetailDataDTO[] result = (PreDeliveryDetailDataDTO[]) list.toArray(new PreDeliveryDetailDataDTO[list.size()]);		
		return result;
	}
	
	public Integer getCountPreDeliveryDetailsForImportedDelivery(Long[] orderid, Long vendorid) throws OperationFailedException, AccessDeniedException{
		
		String SQL = getPreDeliveryQueryString(1, null);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setParameterList("ordersid", orderid);		
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;	
	}
	
	public OrderContainerDataDTO[] getOrdersContainersForImportedDelivery(Long[] orderids, Long vendorid, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, - Integer.parseInt(B2BSystemPropertiesUtil.getProperty("deliverymaxcreationmonths")));
		Date creationdate = cal.getTime();
		
		String SQL =
				"SELECT " + //
					"oc.id AS orderid, " + //
					"oc.number AS ordernumber, " + //
					"dl.id AS deliveryid, " + //
					"dl.container AS container " + //
				"FROM " + //
					"logistica.order AS oc JOIN " + //
					"logistica.orderdelivery AS od ON od.order_id = oc.id JOIN " + //
					"logistica.delivery AS dl ON dl.id = od.delivery_id " + // 
				"WHERE " + //
					"oc.id IN (:orderids) AND oc.vendor_id = :vendorid AND dl.created >= :creationdate " + //
				getOrderByString(mapGetOrdersContainersSQL, orderby);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setParameterList("orderids", orderids);
		query.setDate("creationdate", creationdate);
		query.setResultTransformer(new LowerCaseResultTransformer(OrderContainerDataDTO.class));
		
		List list = query.list();
		OrderContainerDataDTO[] result = (OrderContainerDataDTO[]) list.toArray(new OrderContainerDataDTO[list.size()]);		
		return result;
	}
	
	public LabelDODeliveryReportDataDTO[] getLabelDODeliveryReport(Long deliveryid, Long vendorid) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.Delivery.getLabelDODeliveryReport");
		query.setLong("vendorid", vendorid);
		query.setLong("deliveryid", deliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(LabelDODeliveryReportDataDTO.class));
		List list = query.list();
		LabelDODeliveryReportDataDTO[] result = (LabelDODeliveryReportDataDTO[]) list.toArray(new LabelDODeliveryReportDataDTO[list.size()]);
		return result;	
	}
	
	public AddDeliveryOfOrdersAndFlowTypesDataDTO[] getAddedDeliveriesByContainerDeliveryLocationFlowTypeAndATC(List<Long> deliveryids) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.Delivery.getAddedDeliveriesByContainerDeliveryLocationFlowTypeAndATC");
		query.setParameterList("deliveryids", deliveryids);
		query.setResultTransformer(new LowerCaseResultTransformer(AddDeliveryOfOrdersAndFlowTypesDataDTO.class));
		List list = query.list();
		AddDeliveryOfOrdersAndFlowTypesDataDTO[] result = (AddDeliveryOfOrdersAndFlowTypesDataDTO[]) list.toArray(new AddDeliveryOfOrdersAndFlowTypesDataDTO[list.size()]);
		return result;
	}
	
	public DeliveryW[] getDeliveryByOrder(Long orderid) throws OperationFailedException, NotFoundException {		
		StringBuilder sb = new StringBuilder(
				"SELECT " + //
					"dl " + //
				"FROM " + //
					"Delivery AS dl, " + //
					"OrderDelivery AS odl " + //
				"WHERE " + //
					"odl.delivery = dl AND odl.order.id = :orderid");
		
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("odl.order.id", "orderid", orderid);
		
		List list = getByQuery(sb.toString(), properties);
		
		return (DeliveryW[]) list.toArray(new DeliveryW[list.size()]);
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
		entitylabel = "Delivery";
	}
	@Override
	protected void setEntityname() {
		entityname = "Delivery";
	}
}
