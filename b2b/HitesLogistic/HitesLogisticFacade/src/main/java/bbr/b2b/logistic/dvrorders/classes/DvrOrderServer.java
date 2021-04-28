package bbr.b2b.logistic.dvrorders.classes;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.buyorders.classes.ClientServerLocal;
import bbr.b2b.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.buyorders.classes.ResponsableServerLocal;
import bbr.b2b.logistic.buyorders.classes.RetailerServerLocal;
import bbr.b2b.logistic.buyorders.classes.SectionServerLocal;
import bbr.b2b.logistic.buyorders.entities.Client;
import bbr.b2b.logistic.buyorders.entities.OrderType;
import bbr.b2b.logistic.buyorders.entities.Responsable;
import bbr.b2b.logistic.buyorders.entities.Retailer;
import bbr.b2b.logistic.buyorders.entities.Section;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderW;
import bbr.b2b.logistic.dvrorders.entities.DvrOrder;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderStateType;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderExcelReportDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderReportDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderExcelReportDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderExcelReportResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderLabelArrayResultDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderLabelDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderPdfDataDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderPdfDetailDTO;
import bbr.b2b.logistic.dvrorders.report.classes.DvrOrderReportDataDTO;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.entities.Location;
import bbr.b2b.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.logistic.soa.entities.SOAStateType;
import bbr.b2b.logistic.utils.ClassUtilities;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/dvrorders/DvrOrderServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DvrOrderServer extends LogisticElementServer<DvrOrder, DvrOrderW> implements DvrOrderServerLocal {

	private Map<String, String> mapGetOrderSQL = new HashMap<String, String>();
	{
		mapGetOrderSQL.put("B2BLINK", "oc.number");
		mapGetOrderSQL.put("ORDERNUMBER", "oc.number");
		mapGetOrderSQL.put("DVRORDERNUMBER", "oc.number");
		mapGetOrderSQL.put("DVRREFERENCENUMBER", "dvrreferencenumber");
		mapGetOrderSQL.put("DVRORDERSTATETYPECODE", "dvrost.code");
		mapGetOrderSQL.put("DVRORDERSTATETYPENAME", "dvrost.description");
		mapGetOrderSQL.put("ORDERTYPECODE", "ot.code");
		mapGetOrderSQL.put("ORDERTYPENAME", "ot.description");
		mapGetOrderSQL.put("DELIVERYLOCATIONCODE", "loc.code");
		mapGetOrderSQL.put("DELIVERYLOCATIONNAME", "loc.name");
		mapGetOrderSQL.put("EMITTEDDATE", "oc.emitted");
		mapGetOrderSQL.put("DELIVERYDATE", "dvroc.deliverydate");
		mapGetOrderSQL.put("EXPIRATIONDATE", "dvroc.expiration");		
		mapGetOrderSQL.put("CLIENTNAME", "clientname");
		mapGetOrderSQL.put("CLIENTDNI", "clientdni");
		mapGetOrderSQL.put("CLIENTADDRESS", "clientaddress");
		mapGetOrderSQL.put("CLIENTCOMMUNE", "clientcommune");
		mapGetOrderSQL.put("CLIENTCITY", "clientcity");		
		mapGetOrderSQL.put("TOTALAMOUNT", "dvroc.totalneed");
		mapGetOrderSQL.put("TOTALPENDING", "dvroc.totalpending");
		mapGetOrderSQL.put("TOTALRECEIVED", "dvroc.totalreceived");
		mapGetOrderSQL.put("TOTALTODELIVERY", "dvroc.totaltodelivery");
		mapGetOrderSQL.put("COMMENT", "dvroc.comment");
		mapGetOrderSQL.put("NETAMOUNT", "dvroc.netamount");		
	}
	
	
	private Map<String, String> mapGetDVHOrderSQL = new HashMap<String, String>();
	{
		mapGetDVHOrderSQL.put("B2BLINK", "oc.number");
		mapGetDVHOrderSQL.put("ORDERNUMBER", "oc.number");
		mapGetDVHOrderSQL.put("DVRORDERNUMBER", "oc.number");
		mapGetDVHOrderSQL.put("DVRREFERENCENUMBER", "dvrreferencenumber");
		mapGetDVHOrderSQL.put("DVRORDERSTATETYPECODE", "dvrost.code");
		mapGetDVHOrderSQL.put("DVRORDERSTATETYPENAME", "dvrost.description");
		mapGetDVHOrderSQL.put("ORDERTYPECODE", "ot.code");
		mapGetDVHOrderSQL.put("ORDERTYPENAME", "ot.description");
		mapGetDVHOrderSQL.put("DELIVERYLOCATIONCODE", "loc.code");
		mapGetDVHOrderSQL.put("DELIVERYLOCATIONNAME", "loc.name");
		mapGetDVHOrderSQL.put("EMITTEDDATE", "oc.emitted");
		mapGetDVHOrderSQL.put("DELIVERYDATE", "dvroc.deliverydate");
		mapGetDVHOrderSQL.put("RESCHEDULINGDATE",  "COALESCE(dvroc.reschedulingdate, dvroc.deliverydate)");  
		mapGetDVHOrderSQL.put("RESCHEDULINGCOUNTER", "COALESCE(dvroc.reschedulingcounter, 0)");
		mapGetDVHOrderSQL.put("CLIENTNAME", "clientname");
		mapGetDVHOrderSQL.put("CLIENTDNI", "clientdni");
		mapGetDVHOrderSQL.put("CLIENTADDRESS", "clientaddress");
		mapGetDVHOrderSQL.put("CLIENTCOMMUNE", "clientcommune");
		mapGetDVHOrderSQL.put("CLIENTCITY", "clientcity");		
		mapGetDVHOrderSQL.put("TOTALAMOUNT", "dvroc.totalneed");
		mapGetDVHOrderSQL.put("TOTALPENDING", "dvroc.totalpending");
		mapGetDVHOrderSQL.put("TOTALRECEIVED", "dvroc.totalreceived");
		mapGetDVHOrderSQL.put("TOTALTODELIVERY", "dvroc.totaltodelivery");
		mapGetDVHOrderSQL.put("COMMENT", "dvroc.comment");
		mapGetDVHOrderSQL.put("NETAMOUNT", "dvroc.netamount");		
	}

	@EJB
	DvrOrderStateTypeServerLocal currentstatetypeserver;

	@EJB
	ClientServerLocal clientserver;

	@EJB
	LocationServerLocal locationserver;

	@EJB
	SectionServerLocal sectionserver;

	@EJB
	RetailerServerLocal retailerserver;

	@EJB
	VendorServerLocal vendorserver;

	@EJB
	ResponsableServerLocal responsableserver;

	@EJB
	OrderTypeServerLocal ordertypeserver;
	
	@EJB
	SOAStateTypeServerLocal soastatetypeserver;
	
	public DvrOrderW addDvrOrder(DvrOrderW dvrorder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderW) addElement(dvrorder);
	}
	public DvrOrderW[] getDvrOrders() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderW[]) getIdentifiables();
	}
	public DvrOrderW updateDvrOrder(DvrOrderW dvrorder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderW) updateElement(dvrorder);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DvrOrder entity, DvrOrderW wrapper) {
		wrapper.setCurrentstatetypeid(entity.getCurrentstatetype() != null ? new Long(entity.getCurrentstatetype().getId()) : new Long(0));
		wrapper.setClientid(entity.getClient() != null ? new Long(entity.getClient().getId()) : new Long(0));
		wrapper.setDeliverylocationid(entity.getDeliverylocation() != null ? new Long(entity.getDeliverylocation().getId()) : new Long(0));
		wrapper.setSectionid(entity.getSection() != null ? new Long(entity.getSection().getId()) : new Long(0));
		wrapper.setRetailerid(entity.getRetailer() != null ? new Long(entity.getRetailer().getId()) : new Long(0));
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setResponsableid(entity.getResponsable() != null ? new Long(entity.getResponsable().getId()) : new Long(0));
		wrapper.setOrdertypeid(entity.getOrdertype() != null ? new Long(entity.getOrdertype().getId()) : new Long(0));
		wrapper.setSalelocationid(entity.getSalelocation() != null ? new Long(entity.getSalelocation().getId()) : new Long(0));
		wrapper.setCurrentsoastatetypeid(entity.getCurrentsoastatetype() != null ? new Long(entity.getCurrentsoastatetype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DvrOrder entity, DvrOrderW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getCurrentstatetypeid() != null && wrapper.getCurrentstatetypeid() > 0) { 
			DvrOrderStateType currentstatetype = currentstatetypeserver.getReferenceById(wrapper.getCurrentstatetypeid());
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
		if (wrapper.getDeliverylocationid() != null && wrapper.getDeliverylocationid() > 0) { 
			Location deliverylocation = locationserver.getReferenceById(wrapper.getDeliverylocationid());
			if (deliverylocation != null) { 
				entity.setDeliverylocation(deliverylocation);
			}
		}
		if (wrapper.getSectionid() != null && wrapper.getSectionid() > 0) { 
			Section section = sectionserver.getReferenceById(wrapper.getSectionid());
			if (section != null) { 
				entity.setSection(section);
			}
		}
		if (wrapper.getRetailerid() != null && wrapper.getRetailerid() > 0) { 
			Retailer retailer = retailerserver.getReferenceById(wrapper.getRetailerid());
			if (retailer != null) { 
				entity.setRetailer(retailer);
			}
		}
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
		if (wrapper.getSalelocationid() != null && wrapper.getSalelocationid() > 0) { 
			Location salelocation = locationserver.getReferenceById(wrapper.getSalelocationid());
			if (salelocation != null) { 
				entity.setSalelocation(salelocation);
			}
		}
		if (wrapper.getCurrentsoastatetypeid() != null && wrapper.getCurrentsoastatetypeid() > 0){
			SOAStateType soastatetype = soastatetypeserver.getReferenceById(wrapper.getCurrentsoastatetypeid());
			if(soastatetype != null){
				entity.setCurrentsoastatetype(soastatetype);
			}
		}	
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

	
	
	private String getDvrOrdersQueryString(Long locationid, int queryType, OrderCriteriaDTO[] orderby, String whereCondition) throws AccessDeniedException, OperationFailedException {
		
		String SQL = "";
		String sqlSelect = "";
		String sqlFrom1 = "";
		String locationCondition = "";
		String orderByCondition = "";
				
		
		if (! locationid.equals(new Long(LogisticConstants.getINT_TODOS()))){
			locationCondition = " AND loc.id = :locationid ";
		}
		
		// Count
		if (queryType == 1) {
			sqlSelect = "SELECT COUNT(oc.id) "; 
		}
		
		// Datos
		else if(queryType == 2){

			
			sqlSelect = "SELECT " + // 
						"	CAST('' AS VARCHAR)  AS b2blink, " + //
						"	oc.id AS dvrorderid, " + //
						"	oc.number AS dvrordernumber, " + //
						"   COALESCE(dvroc.referencenumber, '') AS dvrreferencenumber, " + //
						"	dvrost.code AS dvrorderstatetypecode, " + //
						"	dvrost.description AS dvrorderstatetypename, " + //
						"	ot.code AS ordertypecode, " + //
						"	ot.description as ordertypename, " + //
						"	loc.code as deliverylocationcode, " + //
						"	loc.name as deliverylocationname, " + //
						"	oc.emitted as  emitteddate, " + //
						"	dvroc.deliverydate AS deliverydate, " + //
						"	dvroc.expiration AS expirationdate, " + //
						"	dvroc.payment_days as paymentdays, " + //
						"	cl.id AS clientid, " + //
						"	COALESCE(cl.name, '') AS clientname, " + //
						"	COALESCE(cl.dni, '') AS clientdni, " + //
						"	COALESCE(cl.address, '') AS clientaddress, " + //
						"	COALESCE(cl.commune, '') AS clientcommune, " + //
						"	COALESCE(cl.city, '') AS clientcity, " + //
						"	dvroc.totalneed AS totalamount, " + //
						"	dvroc.totalpending AS 	totalpending, " + //
						"	dvroc.totalreceived AS 	totalreceived, " + //
						"	dvroc.totaltodelivery AS totaltodelivery, " + //
						"	dvroc.totalunits AS totalunits, " + // 
						"	dvroc.todeliveryunits AS todeliveryunits, " + //
						"	dvroc.receivedunits AS receivedunits, " + //
						"	dvroc.pendingunits AS pendingunits, " + //
						"	dvroc.comment AS comment, " + //
						"	dvroc.netamount ";			
			
			orderByCondition = getOrderByString(mapGetOrderSQL, orderby);
		}
		
		else {
			throw new AccessDeniedException("El tipo de query noes válido");
			
		}
		
		sqlFrom1 = 	"FROM logistica.order as oc " + //
					"JOIN logistica.dvrorder as dvroc " + // 
					"ON dvroc.id = oc.id " + // 
					"JOIN logistica.dvrorderstatetype as dvrost " + // 
					"ON dvrost.id = dvroc.currentstatetype_id " + //
					"JOIN logistica.ordertype as ot " + //
					"ON ot.id = oc.ordertype_id " + // 
					"JOIN logistica.location as loc " + // 
					"ON loc.id = dvroc.deliverylocation_id " + //
					"JOIN logistica.vendor as ve " + // 
					"ON ve.id = oc.vendor_id " + //
					"LEFT JOIN logistica.client AS cl " + //
					"ON cl.id = oc.client_id "; //

		
		SQL  = 	sqlSelect + 
				sqlFrom1 +
				"WHERE oc.vendor_id = :vendorid " +
				"AND ot.code != 'DVH' " + 
				whereCondition + 
				locationCondition + 
				orderByCondition;
		
		return SQL;
	}
	
	
	private String getDvhOrdersQueryString(Long locationid, int queryType, OrderCriteriaDTO[] orderby, String whereCondition) throws AccessDeniedException, OperationFailedException {
		
		String sqlWith1 = "";
		String SQL = "";
		String sqlSelect = "";
		String sqlExtraFrom = "";
		String sqlFrom1 = "";
		String locationCondition = "";
		String orderByCondition = "";
				
		
		if (! locationid.equals(new Long(LogisticConstants.getINT_TODOS()))){
			locationCondition = " AND loc.id = :locationid ";
		}
		
		// Count
		if (queryType == 1) {
			sqlSelect = "SELECT COUNT(oc.id) "; 
		}
		
		// Datos
		else if(queryType == 2){

			
			sqlWith1 = "WITH warningoc AS ( " + //
						"	SELECT " + //
						"		dvroc.id as dvrocid " + //
						"	FROM logistica.order AS oc " + //
						"	JOIN logistica.dvrorder AS dvroc ON dvroc.id = oc.id " + //
						"	JOIN logistica.dvrorderstatetype AS dvrost ON dvrost.id = dvroc.currentstatetype_id " + //
						"	JOIN logistica.ordertype AS ot ON ot.id = oc.ordertype_id " + //
						"	WHERE 	oc.vendor_id = :vendorid " + //
						"			AND ot.code = 'DVH' " + //
						whereCondition + //
						"	AND dvrost.valid IS true " + //
						"	AND COALESCE(dvroc.reschedulingdate, dvroc.deliverydate)  < :now " + //
						"	AND dvroc.pendingunits > 0 " + //
						") " ;
			
			sqlSelect = "SELECT " + // 
						"	CAST('' AS VARCHAR)  AS b2blink, " + //
						"	oc.id AS dvrorderid, " + //
						"	oc.number AS dvrordernumber, " + //
						"   COALESCE(dvroc.referencenumber, '') AS dvrreferencenumber, " + //
						"	dvrost.code AS dvrorderstatetypecode, " + //
						"	dvrost.description AS dvrorderstatetypename, " + //
						"	ot.code AS ordertypecode, " + //
						"	ot.description as ordertypename, " + //
						"	loc.code as deliverylocationcode, " + //
						"	loc.name as deliverylocationname, " + //
						"	oc.emitted as  emitteddate, " + //
						"	dvroc.deliverydate AS deliverydate, " + //
						//"	dvroc.reschedulingdate AS reschedulingdate, " + //
						"	COALESCE(dvroc.reschedulingdate, dvroc.deliverydate)  AS reschedulingdate, " + //
						"	COALESCE(dvroc.reschedulingcounter, 0) AS reschedulingcounter, " + //
						"	dvroc.payment_days as paymentdays, " + //
						"	cl.id AS clientid, " + //
						"	COALESCE(cl.name, '') AS clientname, " + //
						"	COALESCE(cl.dni, '') AS clientdni, " + //
						"	COALESCE(cl.address, '') AS clientaddress, " + //
						"	COALESCE(cl.commune, '') AS clientcommune, " + //
						"	COALESCE(cl.city, '') AS clientcity, " + //
						"	dvroc.totalneed AS totalamount, " + //
						"	dvroc.totalpending AS 	totalpending, " + //
						"	dvroc.totalreceived AS 	totalreceived, " + //
						"	dvroc.totaltodelivery AS totaltodelivery, " + //
						"	dvroc.totalunits AS totalunits, " + // 
						"	dvroc.todeliveryunits AS todeliveryunits, " + //
						"	dvroc.receivedunits AS receivedunits, " + //
						"	dvroc.pendingunits AS pendingunits, " + //
						"	dvroc.comment AS comment, " + //
						"	dvroc.netamount, " + //
						"	CASE " + //
						"		WHEN woc.dvrocid IS NOT NULL then true " + //
						"		ELSE false " + //
						"	END as warningoc ";		
			
			sqlExtraFrom = 	"LEFT JOIN warningoc as woc " + //
							"ON woc.dvrocid = dvroc.id ";
			
			orderByCondition = getOrderByString(mapGetDVHOrderSQL, orderby);
		}
		
		else {
			throw new AccessDeniedException("El tipo de query noes válido");
			
		}
		
		sqlFrom1 = 	"FROM logistica.order as oc " + //
					"JOIN logistica.dvrorder as dvroc " + // 
					"ON dvroc.id = oc.id " + // 
					"JOIN logistica.dvrorderstatetype as dvrost " + // 
					"ON dvrost.id = dvroc.currentstatetype_id " + //
					"JOIN logistica.ordertype as ot " + //
					"ON ot.id = oc.ordertype_id " + // 
					"JOIN logistica.location as loc " + // 
					"ON loc.id = dvroc.deliverylocation_id " + //
					"JOIN logistica.vendor as ve " + // 
					"ON ve.id = oc.vendor_id " + //
					"LEFT JOIN logistica.client AS cl " + //
					"ON cl.id = oc.client_id "; //

		
		SQL  = 	sqlWith1 + 
				sqlSelect + 
				sqlFrom1 +
				sqlExtraFrom + 
				"WHERE oc.vendor_id = :vendorid " +
				"AND ot.code = 'DVH' " + 
				whereCondition + 
				locationCondition + 
				orderByCondition;
		
		return SQL;
	}

	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Integer doExpireDvrOrders() throws OperationFailedException{
		Integer countorders = 0;
		
		String SQL = "select logistica.vence_dvroc()";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		countorders = ((Integer) query.uniqueResult());				
		return countorders;		
	}

	
	public void doCalculateTotalDvrOrder(Long[] dvrorderids){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrder.doCalculateTotalDvrOrder");
		query.setParameterList("dvrorderids", dvrorderids);
		query.executeUpdate();
	}
	

	public DvrOrderW[] getDvrOrderByIds(Long[] dvrorderids) throws NotFoundException, OperationFailedException {
		DvrOrderW[] result = null;
		List<DvrOrderW> resultList = new ArrayList<DvrOrderW>();
		
		List<Long> dvrorderidsList = Arrays.asList(dvrorderids);
		String queryStr = 	"select dvroc from DvrOrder as dvroc " + // 
							"where dvroc.id in (:dvrorderids) ";
							 
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("dvroc.id ", "dvrorderids", dvrorderidsList);
		
		resultList = getByQuery(queryStr, properties);
		result =(DvrOrderW[]) resultList.toArray(new DvrOrderW[resultList.size()]);
		return result;
	}	
	
	public DvrOrderW[] getDvrOrderByIdsAndVendor(Long[] dvrorderids, Long vendorid) throws NotFoundException, OperationFailedException {
		DvrOrderW[] result = null;
		List<DvrOrderW> resultList = new ArrayList<DvrOrderW>();
		
		List<Long> dvrorderidsList = Arrays.asList(dvrorderids);
		String queryStr = 	"select dvroc from DvrOrder as dvroc, " + // 
							"Vendor as ve " + //
							"where " + //
							"ve.id = dvroc.vendor.id " + //
							"and ve.id = :vendorid " + //
							"and dvroc.id in (:dvrorderids) ";
							 
		PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
		properties[0] = new PropertyInfoDTO("ve.id", "vendorid", vendorid);
		properties[1] = new PropertyInfoDTO("dvroc.id ", "dvrorderids", dvrorderidsList);
		
		resultList = getByQuery(queryStr, properties);
		result =(DvrOrderW[]) resultList.toArray(new DvrOrderW[resultList.size()]);
		return result;
	}	
	
	public DvrOrderW[] getDvrOrderByNumbers(Long[] dvrordernumbers) throws NotFoundException, OperationFailedException {
		DvrOrderW[] result = null;
		List<DvrOrderW> resultList = new ArrayList<DvrOrderW>();
		
		List<Long> dvrordernumbersList = Arrays.asList(dvrordernumbers);
		String queryStr = 	"select dvroc from DvrOrder as dvroc " + // 
							"where dvroc.number in (:dvrordernumbers) ";
							 
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("dvroc.number", "dvrordernumbers", dvrordernumbersList);
		
		resultList = getByQuery(queryStr, properties);
		result =(DvrOrderW[]) resultList.toArray(new DvrOrderW[resultList.size()]);
		return result;
	}	
	
	
	public DvrOrderReportDataDTO[] getDvrOrdersByVendorAndCriterium(Long vendorid, Long locationid, String filtervalue, Long dvrorderstatetypeid, LocalDateTime since, LocalDateTime until, Integer criterium, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		
		switch (criterium) {
			case 0:
				// ORDENES VIGENTES
				whereCondition = "AND DVROST.VALID = TRUE ";			
				break;
				
			case 1:
				// ESTADO DE ORDEN
				whereCondition = "AND DVROST.ID = :dvrorderstatetypeid ";
				break;
				
			case 2:
				// NÚMERO DE ORDEN
				whereCondition = "AND OC.NUMBER = :filtervalue "; 
				break;
				
			case 3:
				// FECHA DE EMISIÓN
				whereCondition = "AND oc.emitted >= :since AND oc.emitted <= :until ";
				break;
				
			case 4:
				// FECHA DE ENTREGA
				whereCondition = "AND dvroc.deliverydate >= :since AND dvroc.deliverydate <= :until ";
				break;
				
			case 5:
				// NÚMERO DE BOLETA
				whereCondition = "AND dvroc.referencenumber = :filtervalue ";
				break;
				
			default:
				break;
		}
		
		String SQL = getDvrOrdersQueryString(locationid, 2, orderby, whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (! locationid.equals(new Long(LogisticConstants.getINT_TODOS()))){
			query.setLong("locationid", locationid);
		}		
		
		switch (criterium) {
		case 1:
			// ESTADO DE ORDEN
			query.setLong("dvrorderstatetypeid", dvrorderstatetypeid);
			break;
			
		case 2:
			// NÚMERO DE ORDEN
			query.setLong("filtervalue", Long.parseLong(filtervalue));
			break;
			
		case 3:
			// FECHA DE EMISIÓN
		case 4:
			// FECHA DE ENTREGA
			query.setParameter("since", since.toLocalDate().atStartOfDay());
			query.setParameter("until", until.toLocalDate().atStartOfDay());
			break;
			
		case 5:
			// NÚMERO DE BOLETA
			query.setString("filtervalue", filtervalue);
			break;
			
		default:
			break;
		}
				
		query.setResultTransformer(new LowerCaseResultTransformer(DvrOrderReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List<?> list = query.list();
		DvrOrderReportDataDTO[] result = (DvrOrderReportDataDTO[]) list.toArray(new DvrOrderReportDataDTO[list.size()]);		
		return result;	
	}
	
	public int getCountDvrOrdersByVendorAndCriterium(Long vendorid, Long locationid, String filtervalue, Long dvrorderstatetypeid, LocalDateTime since, LocalDateTime until, Integer criterium) throws AccessDeniedException, OperationFailedException {

		String whereCondition = "";
		
		switch (criterium) {
			case 0:
				// ORDENES VIGENTES
				whereCondition = "AND DVROST.VALID = TRUE ";			
				break;
				
			case 1:
				// ESTADO DE ORDEN
				whereCondition = "AND DVROST.ID = :dvrorderstatetypeid ";
				break;
				
			case 2:
				// NÚMERO DE ORDEN
				whereCondition = "AND OC.NUMBER = :filtervalue "; 
				break;
				
			case 3:
				// FECHA DE EMISIÓN
				whereCondition = "AND oc.emitted >= :since AND oc.emitted <= :until ";
				break;
				
			case 4:
				// FECHA DE ENTREGA
				whereCondition = "AND dvroc.deliverydate >= :since AND dvroc.deliverydate <= :until ";
				break;
				
			case 5:
				// NÚMERO DE BOLETA
				whereCondition = "AND dvroc.referencenumber = :filtervalue ";
				break;
				
			default:
				break;
		}
		
		String SQL = getDvrOrdersQueryString(locationid, 1, null, whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (! locationid.equals(new Long(LogisticConstants.getINT_TODOS()))){
			query.setLong("locationid", locationid);
		}		
		
		switch (criterium) {
		case 1:
			// ESTADO DE ORDEN
			query.setLong("dvrorderstatetypeid", dvrorderstatetypeid);
			break;
			
		case 2:
			// NÚMERO DE ORDEN
			query.setLong("filtervalue", Long.parseLong(filtervalue));
			break;
			
		case 3:
			// FECHA DE EMISIÓN
		case 4:
			// FECHA DE ENTREGA
			query.setParameter("since", since.toLocalDate().atStartOfDay());
			query.setParameter("until", until.toLocalDate().atStartOfDay());
			break;
		
		case 5:
			// NÚMERO DE BOLETA
			query.setString("filtervalue", filtervalue);
			break;
			
		default:
			break;
		}
				
		int total = Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
		return total;
		
	}
		
	public DvrOrderExcelReportResultDTO getDvrOrdersExcelReportByOrders(Long[] dvrorderids) throws OperationFailedException {
		DvrOrderExcelReportResultDTO resultDTO = new DvrOrderExcelReportResultDTO();
		
		if (dvrorderids == null || dvrorderids.length <= 0) {
			throw new OperationFailedException("No se puede generar reporte de órdenes. Parámetro nulo o vacío.");
		}

		// Se divide el parámetro de entrada en bloques de 50 elementos
		DvrOrderExcelReportDataDTO[] dvrOrders = null;
		
		int chunksize = 50;
		Object[] splitids = ClassUtilities.splitArray(dvrorderids, Long.class, chunksize);
		Object[] dvrOrdersArray = new Object[splitids.length];

		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];
			if (ids.length <= 0) {
				dvrOrdersArray[i] = new DvrOrderExcelReportDataDTO[0];
				continue;
			}
			SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrder.getDvrOrdersExcelReportByOrders");
			query.setParameterList("dvrorderids", ids);
			query.setResultTransformer(new LowerCaseResultTransformer(DvrOrderExcelReportDataDTO.class));
			
			List<?> list = query.list();
			DvrOrderExcelReportDataDTO[] dvrOrdersTmp = list.toArray(new DvrOrderExcelReportDataDTO[list.size()]);
			
			dvrOrdersArray[i] = dvrOrdersTmp;
		}
		
		dvrOrders = (DvrOrderExcelReportDataDTO[]) ClassUtilities.unsplitArrays(dvrOrdersArray, DvrOrderExcelReportDataDTO.class);
		resultDTO.setDvrorders(dvrOrders);
		
		return resultDTO;
	}
	
	public int countDvrOrdersExcelReportByOrders(Long[] dvrorderids) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrder.countDvrOrdersExcelReportByOrders");
		query.setParameterList("dvrorderids", dvrorderids);
		return Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
	}
	
	public DvhOrderExcelReportResultDTO getDvhOrdersExcelReportByOrders(Long[] dvhorderids) throws OperationFailedException {
		DvhOrderExcelReportResultDTO resultDTO = new DvhOrderExcelReportResultDTO();
		
		if (dvhorderids == null || dvhorderids.length <= 0) {
			throw new OperationFailedException("No se puede generar reporte de órdenes. Parámetro nulo o vacío.");
		}

		// Se divide el parámetro de entrada en bloques de 50 elementos
		DvhOrderExcelReportDataDTO[] dvhOrders = null;
		
		int chunksize = 50;
		Object[] splitids = ClassUtilities.splitArray(dvhorderids, Long.class, chunksize);
		Object[] dvhOrdersArray = new Object[splitids.length];

		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];
			if (ids.length <= 0) {
				dvhOrdersArray[i] = new DvhOrderExcelReportDataDTO[0];
				continue;
			}
			SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrder.getDvhOrdersExcelReportByOrders");
			query.setParameterList("dvhorderids", ids);
			query.setResultTransformer(new LowerCaseResultTransformer(DvhOrderExcelReportDataDTO.class));
			
			List<?> list = query.list();
			DvhOrderExcelReportDataDTO[] dvhOrdersTmp = list.toArray(new DvhOrderExcelReportDataDTO[list.size()]);
			
			dvhOrdersArray[i] = dvhOrdersTmp;
		}
		
		dvhOrders = (DvhOrderExcelReportDataDTO[]) ClassUtilities.unsplitArrays(dvhOrdersArray, DvhOrderExcelReportDataDTO.class);
		resultDTO.setDvhorders(dvhOrders);
		
		return resultDTO;
	}
	
	public int countDvhOrdersExcelReportByOrders(Long[] dvhorderids) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrder.countDvhOrdersExcelReportByOrders");
		query.setParameterList("dvhorderids", dvhorderids);
		return Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
	}
	
	public DvrOrderPdfDataDTO[] getDvrOrdersPdfDataByOrders(Long[] dvrorderids) throws OperationFailedException {
		DvrOrderPdfDataDTO[] resultDTO;
		
		if (dvrorderids == null || dvrorderids.length <= 0) {
			throw new OperationFailedException("No se puede generar reporte de órdenes. Parámetro nulo o vacío.");
		}

		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrder.getDvrOrdersPdfDataByOrders");
		query.setParameterList("dvrorderids", dvrorderids);
		query.setResultTransformer(new LowerCaseResultTransformer(DvrOrderPdfDataDTO.class));
		
		List<?> list = query.list();
		resultDTO = (DvrOrderPdfDataDTO[]) list.toArray(new DvrOrderPdfDataDTO[list.size()]);
				
		return resultDTO;
	}

	public DvrOrderLabelArrayResultDTO getDvrOrderLabelsByOrders(Long[] dvrorderids) throws OperationFailedException {
		DvrOrderLabelArrayResultDTO resultDTO = new DvrOrderLabelArrayResultDTO();
		
		if (dvrorderids == null || dvrorderids.length <= 0) {
			throw new OperationFailedException("No se puede generar reporte de órdenes. Parámetro nulo o vacío.");
		}

		// Se divide el parámetro de entrada en bloques de 50 elementos
		DvrOrderLabelDTO[] labels = null;
		
		int chunksize = 50;
		Object[] splitids = ClassUtilities.splitArray(dvrorderids, Long.class, chunksize);
		Object[] labelArray = new Object[splitids.length];

		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];
			if (ids.length <= 0) {
				labelArray[i] = new DvrOrderLabelDTO[0];
				continue;
			}
			SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrder.getDvrOrderLabelsByOrders");
			query.setParameterList("dvrorderids", ids);
			query.setResultTransformer(new LowerCaseResultTransformer(DvrOrderLabelDTO.class));
			
			List<?> list = query.list();
			DvrOrderLabelDTO[] labelsTmp = list.toArray(new DvrOrderLabelDTO[list.size()]);
			
			labelArray[i] = labelsTmp;
		}
		
		labels = (DvrOrderLabelDTO[]) ClassUtilities.unsplitArrays(labelArray, DvrOrderLabelDTO.class);
		resultDTO.setLabels(labels);
		
		return resultDTO;
	}
	
	public int countDvrOrderLabelsByOrders(Long[] dvrorderids) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrder.countDvrOrderLabelsByOrders");
		query.setParameterList("dvrorderids", dvrorderids);
		return Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
	}
	
	public Long[] getDvrOrderIdsByPages(Long vendorid, Long locationid, String filtervalue, Long dvrorderstatetypeid, LocalDateTime since, LocalDateTime until, Integer criterium, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageRangeDTO) throws OperationFailedException, AccessDeniedException {
		Long[] result = null;
				
		List<Object> dvrOrderList = new ArrayList<>();
		DvrOrderReportDataDTO[] dvrOrders;			
		for (int i = 0; i < pageRangeDTO.length; i++) {
			for (int j = pageRangeDTO[i].getSincePage(); j <= pageRangeDTO[i].getUntilPage(); j++) {
				dvrOrders = getDvrOrdersByVendorAndCriterium(vendorid, locationid, filtervalue, dvrorderstatetypeid, since, until, criterium, j, rows, orderby, true);
						
				dvrOrderList.add(dvrOrders);
			}
		}

		Object[] dvrOrderArray = (Object[]) dvrOrderList.toArray(new Object[dvrOrderList.size()]);
		dvrOrders = (DvrOrderReportDataDTO[]) ClassUtilities.unsplitArrays(dvrOrderArray, DvrOrderReportDataDTO.class);

		result = new Long[dvrOrders.length];

		// Obtengo solo los ids de las órdenes de las páginas solicitadas
		for (int i = 0; i < dvrOrders.length; i++) {
			result[i] = dvrOrders[i].getDvrorderid();
		}
			
		return result;
	}
	
	public DvrOrderPdfDetailDTO[] getDvrOrdersPdfDetailByOrders(Long[] dvrorderids) throws OperationFailedException {
		DvrOrderPdfDetailDTO[] resultDTO;
		
		if (dvrorderids == null || dvrorderids.length <= 0) {
			throw new OperationFailedException("No se puede generar reporte de órdenes. Parámetro nulo o vacío.");
		}

		// Se divide el parámetro de entrada en bloques de 50 elementos
		int chunksize = 50;
		Object[] splitids = ClassUtilities.splitArray(dvrorderids, Long.class, chunksize);
		Object[] detailArray = new Object[splitids.length];

		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];
			if (ids.length <= 0) {
				detailArray[i] = new DvrOrderPdfDetailDTO[0];
				continue;
			}
			SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrder.getDvrOrdersPdfDetailByOrders");
			query.setParameterList("dvrorderids", ids);
			query.setResultTransformer(new LowerCaseResultTransformer(DvrOrderPdfDetailDTO.class));
			
			List<?> list = query.list();
			DvrOrderPdfDetailDTO[] detailTmp = list.toArray(new DvrOrderPdfDetailDTO[list.size()]);
			
			detailArray[i] = detailTmp;
		}
		
		resultDTO = (DvrOrderPdfDetailDTO[]) ClassUtilities.unsplitArrays(detailArray, DvrOrderPdfDetailDTO.class);
				
		return resultDTO;
	}
	
	public int countDvrOrdersPdfDetailByOrders(Long[] dvrorderids) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrorders.entities.DvrOrder.countDvrOrdersPdfDetailByOrders");
		query.setParameterList("dvrorderids", dvrorderids);
		return Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
	}

	
	public DvrOrderW[] getDvhOrderByIds(Long[] dvrorderids, Long dvhtypeid) throws NotFoundException, OperationFailedException {
		DvrOrderW[] result = null;
		List<DvrOrderW> resultList = new ArrayList<DvrOrderW>();
		
		List<Long> dvrorderidsList = Arrays.asList(dvrorderids);
		String queryStr = 	"select dvroc " + //
							"from DvrOrder as dvroc, " + // 
							"OrderType as ot, " + //
							"where " + //
							"dvroc.ordertype.id = ot.id " + //
							"and ot.id = :dvhtypeid " + //
							"and dvroc.id in (:dvrorderids) ";
							 
		PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
		properties[0] = new PropertyInfoDTO("ot.id", "dvhtypeid", dvhtypeid);
		properties[1] = new PropertyInfoDTO("dvroc.id ", "dvrorderids", dvrorderidsList);
		
		resultList = getByQuery(queryStr, properties);
		result =(DvrOrderW[]) resultList.toArray(new DvrOrderW[resultList.size()]);
		return result;
	}	
	
	public DvrOrderW[] getDvhOrderByIdsAndVendor(Long[] dvrorderids, Long vendorid, Long dvhtypeid) throws NotFoundException, OperationFailedException {
		DvrOrderW[] result = null;
		List<DvrOrderW> resultList = new ArrayList<DvrOrderW>();
		
		List<Long> dvrorderidsList = Arrays.asList(dvrorderids);
		String queryStr = 	"select dvroc from DvrOrder as dvroc, " + // 
							"OrderType as ot  " + //
							"where " + //
							"dvroc.ordertype.id = ot.id " + //
							"and ot.id = :dvhtypeid " + //
							"and dvroc.vendor.id = :vendorid " + //
							"and dvroc.id in (:dvrorderids) ";
							 
		PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
		properties[0] = new PropertyInfoDTO("ot.id", "dvhtypeid", dvhtypeid);
		properties[1] = new PropertyInfoDTO("ve.id", "vendorid", vendorid);
		properties[2] = new PropertyInfoDTO("dvroc.id ", "dvrorderids", dvrorderidsList);
		
		resultList = getByQuery(queryStr, properties);
		result =(DvrOrderW[]) resultList.toArray(new DvrOrderW[resultList.size()]);
		return result;
	}	
	
	
	
	public DvhOrderReportDataDTO[] getDvhOrdersByVendorAndCriterium(Long vendorid, Long locationid, String filtervalue, Long dvrorderstatetypeid, LocalDateTime since, LocalDateTime until, Integer criterium, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException{
		String whereCondition = "";
		
		LocalDateTime now = LocalDateTime.now();
		
		switch (criterium) {
			case 0:
				// ORDENES VIGENTES
				whereCondition = "AND DVROST.VALID IS TRUE ";			
				break;
				
			case 1:
				// ESTADO DE ORDEN
				whereCondition = "AND DVROST.ID = :dvrorderstatetypeid ";
				break;
				
			case 2:
				// NÚMERO DE ORDEN
				whereCondition = "AND OC.NUMBER = :filtervalue "; 
				break;
				
			case 3:
				// FECHA DE EMISIÓN
				whereCondition = "AND oc.emitted >= :since AND oc.emitted <= :until ";
				break;
				
			case 4:
				// FECHA DE ENTREGA
				whereCondition = "AND dvroc.deliverydate >= :since AND dvroc.deliverydate <= :until ";
				break;
				
			case 5:
				// NÚMERO DE BOLETA
				whereCondition = "AND dvroc.referencenumber = :filtervalue ";
				break;
				
			default:
				break;
		}
		
		String SQL = getDvhOrdersQueryString(locationid, 2, orderby, whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setParameter("now", now.toLocalDate().atStartOfDay());
		if (! locationid.equals(new Long(LogisticConstants.getINT_TODOS()))){
			query.setLong("locationid", locationid);
		}		
		
		switch (criterium) {
		case 1:
			// ESTADO DE ORDEN
			query.setLong("dvrorderstatetypeid", dvrorderstatetypeid);
			break;
			
		case 2:
			// NÚMERO DE ORDEN
			query.setLong("filtervalue", Long.parseLong(filtervalue));
			break;
			
		case 3:
			// FECHA DE EMISIÓN
		case 4:
			// FECHA DE ENTREGA
			query.setParameter("since", since.toLocalDate().atStartOfDay());
			query.setParameter("until", until.toLocalDate().atStartOfDay());
			break;
			
		case 5:
			// NÚMERO DE BOLETA
			query.setString("filtervalue", filtervalue);
			break;
			
		default:
			break;
		}
				
		query.setResultTransformer(new LowerCaseResultTransformer(DvhOrderReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List<?> list = query.list();
		DvhOrderReportDataDTO[] result = (DvhOrderReportDataDTO[]) list.toArray(new DvhOrderReportDataDTO[list.size()]);		
		return result;	

		
	}
	
	public int getCountDvhOrdersByVendorAndCriterium(Long vendorid, Long locationid, String filtervalue, Long dvrorderstatetypeid, LocalDateTime since, LocalDateTime until, Integer criterium) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition = "";
		
		switch (criterium) {
			case 0:
				// ORDENES VIGENTES
				whereCondition = "AND DVROST.VALID = TRUE ";			
				break;
				
			case 1:
				// ESTADO DE ORDEN
				whereCondition = "AND DVROST.ID = :dvrorderstatetypeid ";
				break;
				
			case 2:
				// NÚMERO DE ORDEN
				whereCondition = "AND OC.NUMBER = :filtervalue "; 
				break;
				
			case 3:
				// FECHA DE EMISIÓN
				whereCondition = "AND oc.emitted >= :since AND oc.emitted <= :until ";
				break;
				
			case 4:
				// FECHA DE ENTREGA
				whereCondition = "AND dvroc.deliverydate >= :since AND dvroc.deliverydate <= :until ";
				break;
				
			case 5:
				// NÚMERO DE BOLETA
				whereCondition = "AND dvroc.referencenumber = :filtervalue ";
				break;
				
			default:
				break;
		}
		
		String SQL = getDvhOrdersQueryString(locationid, 1, null, whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (! locationid.equals(new Long(LogisticConstants.getINT_TODOS()))){
			query.setLong("locationid", locationid);
		}		
		
		switch (criterium) {
		case 1:
			// ESTADO DE ORDEN
			query.setLong("dvrorderstatetypeid", dvrorderstatetypeid);
			break;
			
		case 2:
			// NÚMERO DE ORDEN
			query.setLong("filtervalue", Long.parseLong(filtervalue));
			break;
			
		case 3:
			// FECHA DE EMISIÓN
		case 4:
			// FECHA DE ENTREGA
			query.setParameter("since", since.toLocalDate().atStartOfDay());
			query.setParameter("until", until.toLocalDate().atStartOfDay());
			break;
		
		case 5:
			// NÚMERO DE BOLETA
			query.setString("filtervalue", filtervalue);
			break;
			
		default:
			break;
		}
				
		int total = Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
		return total;		
	}

	
	public Long[] getDvhOrderIdsByPages(Long vendorid, Long locationid, String filtervalue, Long dvrorderstatetypeid, LocalDateTime since, LocalDateTime until, Integer criterium, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageRangeDTO) throws OperationFailedException, AccessDeniedException {
		Long[] result = null;
				
		List<Object> dvrOrderList = new ArrayList<>();
		DvhOrderReportDataDTO[] dvrOrders;			
		for (int i = 0; i < pageRangeDTO.length; i++) {
			for (int j = pageRangeDTO[i].getSincePage(); j <= pageRangeDTO[i].getUntilPage(); j++) {
				dvrOrders = getDvhOrdersByVendorAndCriterium(vendorid, locationid, filtervalue, dvrorderstatetypeid, since, until, criterium, j, rows, orderby, true);
						
				dvrOrderList.add(dvrOrders);
			}
		}

		Object[] dvrOrderArray = (Object[]) dvrOrderList.toArray(new Object[dvrOrderList.size()]);
		dvrOrders = (DvhOrderReportDataDTO[]) ClassUtilities.unsplitArrays(dvrOrderArray, DvhOrderReportDataDTO.class);

		result = new Long[dvrOrders.length];

		// Obtengo solo los ids de las órdenes de las páginas solicitadas
		for (int i = 0; i < dvrOrders.length; i++) {
			result[i] = dvrOrders[i].getDvrorderid();
		}
			
		return result;
	}


	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DvrOrder";
	}
	@Override
	protected void setEntityname() {
		entityname = "DvrOrder";
	}
}
