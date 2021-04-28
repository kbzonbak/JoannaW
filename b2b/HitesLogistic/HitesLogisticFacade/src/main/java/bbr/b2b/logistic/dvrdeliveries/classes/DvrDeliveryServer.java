package bbr.b2b.logistic.dvrdeliveries.classes;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
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
import javax.persistence.Query;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryW;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDeliveryStateType;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDatingDocumentReportDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDetailDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDetailTotalDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryReportDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.PackingListReportDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.PackingListReportTotalDataDTO;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.entities.Location;
import bbr.b2b.logistic.report.classes.FileUploadErrorDTO;
import bbr.b2b.logistic.utils.ClassUtilities;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/dvrdeliveries/DvrDeliveryServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DvrDeliveryServer extends LogisticElementServer<DvrDelivery, DvrDeliveryW> implements DvrDeliveryServerLocal {

	private Map<String, String> mapGetDeliverySQL = new HashMap<String, String>();
	{
		mapGetDeliverySQL.put("DVRDELIVERYID", "dvrdel.id");
		mapGetDeliverySQL.put("DVRDELIVERYNUMBER", "dvrdel.number");
		mapGetDeliverySQL.put("DVRDELIVERYSTATECODE", "dst.code");
		mapGetDeliverySQL.put("DVRDELIVERYSTATE", "dst.description");
		mapGetDeliverySQL.put("DVRDELIVERYSTATEDATE", "dvrdel.currentstatetypedate");
		mapGetDeliverySQL.put("DATINGDATE", "dt.datingdatetime");
		mapGetDeliverySQL.put("DVRDELIVERYLOCATIONCODE", "loc.code");
		mapGetDeliverySQL.put("DVRDELIVERYLOCATION", "loc.name");
		mapGetDeliverySQL.put("AVAILABLEUNITS", "foo.availableunits");
		mapGetDeliverySQL.put("NEXTACTION", "dst.nextaction");
	}	
	
	private Map<String, String> mapGetDeliveryDetailSQL = new HashMap<String, String>();
	{
		mapGetDeliveryDetailSQL.put("OCNUMBER", "oc.number");
		mapGetDeliveryDetailSQL.put("DESTINATIONLOCATIONCODE", "loc.code");
		mapGetDeliveryDetailSQL.put("DESTINATIONLOCATIONNAME", "loc.name");
		mapGetDeliveryDetailSQL.put("ITEMSKU", "it.sku");
		mapGetDeliveryDetailSQL.put("ITEMDESCRIPTION", "it.name");
		mapGetDeliveryDetailSQL.put("AVAILABLEUNITS", "dvrodd.availableunits");
		mapGetDeliveryDetailSQL.put("RECEIVEDUNITS", "dvrodd.receivedunits");

	}
		
	private Map<String, String> mapGetDvrDeliveryDatingDocumentSQL = new HashMap<String, String>();
	{
		mapGetDvrDeliveryDatingDocumentSQL.put("DELIVERYID", "dvrdel.id");
		mapGetDvrDeliveryDatingDocumentSQL.put("DELIVERYNUMBER", "dvrdel.number");
		mapGetDvrDeliveryDatingDocumentSQL.put("VENDORCODE", "ve.code");
		mapGetDvrDeliveryDatingDocumentSQL.put("VENDORNAME", "ve.name");
		mapGetDvrDeliveryDatingDocumentSQL.put("DOCKNAME", "dk.code");
		mapGetDvrDeliveryDatingDocumentSQL.put("STARTTIME", "MIN(md.starts)");
		mapGetDvrDeliveryDatingDocumentSQL.put("DOCUMENTS", "docs.documents");
	}
	
	private Map<String, String> mapGetPackingListSQL = new HashMap<String, String>();
	{
		mapGetPackingListSQL.put("DESTINATIONLOCATIONCODE", "loc.code");
		mapGetPackingListSQL.put("DESTINATIONLOCATION", "loc.name");
		mapGetPackingListSQL.put("ORDERNUMBER", "oc.number");
		mapGetPackingListSQL.put("LPNCODE", "bu.lpncode");
		mapGetPackingListSQL.put("SKU", "it.sku,");
		mapGetPackingListSQL.put("ITEMDESCRIPTION", "it.name,");
		mapGetPackingListSQL.put("DOCUMENTNUMBER", "doc.number");
		mapGetPackingListSQL.put("DOCUMENTYPE", "dt.name");
		mapGetPackingListSQL.put("UNITS", "bd.units");
	}
	
	
	@EJB
	DvrDeliveryStateTypeServerLocal currentstatetypeserver;

	@EJB
	VendorServerLocal vendorserver;

	@EJB
	LocationServerLocal deliverylocationserver;

	public DvrDeliveryW addDvrDelivery(DvrDeliveryW dvrdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrDeliveryW) addElement(dvrdelivery);
	}
	public DvrDeliveryW[] getDvrDeliverys() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrDeliveryW[]) getIdentifiables();
	}
	public DvrDeliveryW updateDvrDelivery(DvrDeliveryW dvrdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrDeliveryW) updateElement(dvrdelivery);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DvrDelivery entity, DvrDeliveryW wrapper) {
		wrapper.setCurrentstatetypeid(entity.getCurrentstatetype() != null ? new Long(entity.getCurrentstatetype().getId()) : new Long(0));
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setDeliverylocationid(entity.getDeliverylocation() != null ? new Long(entity.getDeliverylocation().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DvrDelivery entity, DvrDeliveryW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getCurrentstatetypeid() != null && wrapper.getCurrentstatetypeid() > 0) { 
			DvrDeliveryStateType currentstatetype = currentstatetypeserver.getReferenceById(wrapper.getCurrentstatetypeid());
			if (currentstatetype != null) { 
				entity.setCurrentstatetype(currentstatetype);
			}
		}
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getDeliverylocationid() != null && wrapper.getDeliverylocationid() > 0) { 
			Location deliverylocation = deliverylocationserver.getReferenceById(wrapper.getDeliverylocationid());
			if (deliverylocation != null) { 
				entity.setDeliverylocation(deliverylocation);
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
	
	private String getDvrDeliveryReportQueryString(int queryType, OrderCriteriaDTO[] orderby, String whereCondition) throws OperationFailedException, AccessDeniedException{
		
		String SQL				= "";
		String sqlView1			= "";
		String sqlView2			= "";
		String sqlSelect 		= "";
		String sqlFrom			= "";
		String sqlWhere			= "";
		String sqlExtraFrom		= "";
		String condicionOrderBy	= "";
		
		if (queryType == 1) {
			sqlSelect =
					"SELECT " + //
						"COUNT(1) "; //
			
		}
		else if (queryType == 2){
			
			sqlView1 = 
					"WITH foo AS ( " + //
							"SELECT " + //
								"dvrdel.id AS dvrdeliveryid, " + //
								"SUM(dvrodd.availableunits) AS availableunits " + //
							"FROM " + //
								"logistica.dvrdelivery as dvrdel JOIN " + //
								"logistica.dvrorderdeliverydetail AS dvrodd ON dvrodd.dvrdelivery_id = dvrdel.id " + //
							"WHERE " + //
								"dvrdel.vendor_id = :vendorid " + //
							"GROUP BY " + //
								"dvrdel.id), "; //
			
			sqlView2 =
						"dattime AS ( " + // 
							"SELECT " + //
								"dvrdel.id as dvrdeliveryid, " + //
								"(date(res.when1) + (MIN(mod.starts))\\:\\:time) as datingdatetime " + //
							"FROM " + //
								"logistica.dvrdelivery AS dvrdel JOIN " + //
								"logistica.dating AS dat ON dat.dvrdelivery_id = dvrdel.id JOIN " + //
								"logistica.reserve as res ON res.id = dat.id JOIN " + //
								"logistica.reservedetail as rd ON rd.reserve_id = res.id JOIN " + //
								"logistica.module as mod ON mod.id= rd.module_id " + //
							"WHERE " + //
								"dvrdel.vendor_id = :vendorid " + //
							"GROUP BY " + //
								"dvrdel.id, res.when1) "; //
			
			sqlSelect =
					"SELECT DISTINCT " + //
							"dvrdel.id AS dvrdeliveryid, " + //
							"dvrdel.number AS dvrdeliverynumber, " + //
							"dst.code AS dvrdeliverystatecode," + //
							"dst.description AS dvrdeliverystate, " + //
							"dvrdel.currentstatetypedate AS dvrdeliverystatedate, " + //
							"dt.datingdatetime AS datingdate,  " + // 
							"loc.code AS dvrdeliverylocationcode, " + //
							"loc.name AS dvrdeliverylocation, " + //
							"foo.availableunits AS availableunits, " + //
							"dst.nextaction AS nextaction ";

			sqlExtraFrom =
					"JOIN "+ //
					"logistica.location AS loc ON dvrdel.deliverylocation_id = loc.id JOIN " + //
					"foo ON foo.dvrdeliveryid = dvrdel.id LEFT JOIN " + //
					"dattime AS dt ON dt.dvrdeliveryid = dvrdel.id "; //					 	

			condicionOrderBy = getOrderByString(mapGetDeliverySQL, orderby);			
		}
		else {
			throw new OperationFailedException("El tipo de query no es válido: " + queryType);
		}
				
		sqlFrom =
				"FROM " + // 
					"logistica.dvrdelivery AS dvrdel JOIN " + //
					"logistica.dvrorderdelivery AS dvrod ON dvrod.dvrdelivery_id = dvrdel.id JOIN " + //
					"logistica.dvrorder AS dvroc ON dvrod.dvrorder_id = dvroc.id JOIN " + //
					"logistica.order AS oc ON oc.id = dvroc.id JOIN " + //
					"logistica.dvrdeliverystatetype AS dst ON dst.id = dvrdel.currentstatetype_id LEFT JOIN " + //
					"logistica.bulk AS bu ON bu.dvrdelivery_id = dvrdel.id LEFT JOIN " + //
					"logistica.document AS doc ON doc.id = bu.document_id "; //
		
		sqlWhere =
				"WHERE " + //
					"dvrdel.vendor_id = :vendorid "; //	 	
		
		SQL +=
			sqlView1 + 
			sqlView2 + 
			sqlSelect+ 
			sqlFrom+ 
			sqlExtraFrom + 
			sqlWhere + 
				whereCondition +
			condicionOrderBy;
		
		return SQL;	
	}
	

	private String getDvrDeliveryDetailReportQueryString(int queryType, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException {
		
		String sql;
		String sqlSelect 		= "";
		String sqlFrom			= "";
		String sqlWhere			= "";
		String sqlExtraFrom		= "";
		String condicionOrderBy	= "";
		
		if (queryType == 1){
			sqlSelect = "SELECT CAST(COUNT(dvrodd.dvrorder_id) AS INTEGER) AS totalreg, " +
						"		SUM(dvrodd.availableunits) AS totalavailable, " +
						"		SUM(dvrodd.receivedunits) AS totalreceived ";
			
			
		}
		else if (queryType == 2){
			sqlSelect = 	
		 					"SELECT DISTINCT " + //
		 					"   oc.id AS orderid, " + //
		 					"	oc.number AS  ocnumber, " + //
		 					"   loc.id AS destinationlocationid, " + //
		 					"	loc.code AS destinationlocationcode, " + // 
		 					"	loc.name AS destinationlocationname, " + // 
		 					"   it.id AS itemid, " + //
		 					"	it.sku AS itemsku, " + // 
		 					"	it.name AS itemdescription, " + // 
		 					"	dvrodd.availableunits as availableunits, " + // 
		 					"	dvrodd.receivedunits as receivedunits ";
			
			sqlExtraFrom = 	"JOIN logistica.item AS it " + // 
		            		"on dvrodd.item_id = it.id " + // 
		            		"JOIN logistica.location AS loc " + // 
		            		"ON loc.id = dvrodd.location_id "; 
			
			condicionOrderBy = getOrderByString(mapGetDeliveryDetailSQL, orderby);
							
		}
		else{
			throw new OperationFailedException("El tipo de query no es válido: " + queryType);
		}
		

		sqlFrom =	"FROM logistica.dvrdelivery AS dvrdel " + // 
					"JOIN logistica.dvrorderdeliverydetail AS dvrodd " + // 
					"ON dvrdel.id = dvrodd.dvrdelivery_id " + // 
					"JOIN logistica.order AS oc " + //
					"ON oc.id = dvrodd.dvrorder_id " + // 
					"JOIN logistica.dvrorder AS dvroc " + // 
					"ON oc.id = dvroc.id " + // 
					"AND  dvroc.id = dvrodd.dvrorder_id " + // 
					"JOIN logistica.vendor AS ve " + // 
					"ON ve.id = oc.vendor_id "; 
				
		sqlWhere = 	"WHERE dvrdel.id = :deliveryid " + //
					"AND ve.id = :vendorid ";
		
		sql  =
				sqlSelect+ 
				sqlFrom+ 
				sqlExtraFrom + 
				sqlWhere + 
				condicionOrderBy;
		
		return sql;	
		
	}

	
	private String getDvrDeliveryDatingDocumentReportQuery(Long vendorid, int queryType, OrderCriteriaDTO[] orderby, String whereCondition) throws AccessDeniedException, OperationFailedException {
		
		String sql = "";
		
		String sqlWith1 = "";
		String sqlWith2 = "";
		String sqlWith3 = "";
		String sqlWith4 = "";
				
		String sqlSelect = "";
		String sqlFrom = ""; 
		String sqlExtraFrom = "";
		String sqlWhere = "";
		String orderByCondition = "";
		String vendorCondition = "";
		String groupBy = "";
				
		if (!vendorid.equals(new Long(LogisticConstants.getINT_TODOS()))) {
			vendorCondition =
					"AND dat.vendor_id = :vendorid "; //
		}
		
		// Count
		if (queryType == 1) {	
			sqlSelect =
					"SELECT "+ //
						"COUNT(DISTINCT dvrdel.id) "; //
		}
		
		// Datos
		else if (queryType == 2) {
			sqlWith1 =
					"WITH nosent AS (" + //	
							"SELECT DISTINCT "+ //
								"dvrdel.id AS dvrdeliveryid, " + // 
								"COUNT(DISTINCT doc.id) AS nosentcount " + //
							"FROM " + //
								"logistica.reserve AS rs JOIN " + //
								"logistica.reservedetail AS rd ON rd.reserve_id = rs.id JOIN " + //
								"logistica.dating AS dat ON dat.id = rs.id JOIN " + //
								"logistica.dvrdelivery AS dvrdel ON dvrdel.id = dat.dvrdelivery_id JOIN " + //
								"logistica.dvrorderdelivery AS dvrod ON dvrod.dvrdelivery_id = dvrdel.id JOIN " + //
								"logistica.order AS oc ON oc.id = dvrod.dvrorder_id JOIN " + // 
								"logistica.dvrdeliverystatetype AS dvrst ON dvrst.id = dvrdel.currentstatetype_id JOIN " + //
								"logistica.bulk AS bu ON bu.dvrdelivery_id = dvrdel.id JOIN " + //
								"logistica.document AS doc ON doc.id = bu.document_id " + // 
							"WHERE " + //
								"dvrst.id IN (:dvrdeliverystateids) AND rs.location_id = :locationid AND doc.status IS FALSE " + vendorCondition + // 
								whereCondition + // 
							"GROUP BY dvrdel.id), "; //
			
			sqlWith2 =
					"nosent1 AS( " + // 	
							"SELECT " + // 
								"dvrdel.id AS dvrdeliveryid, " + //
								"COALESCE(count (DISTINCT ns.nosentcount),0) AS nosentcount " + //
							"FROM " + //
								"logistica.reserve AS rs JOIN " + //
								"logistica.reservedetail AS rd ON rd.reserve_id = rs.id JOIN " + //
								"logistica.dating AS dat ON dat.id = rs.id JOIN " + // 
								"logistica.dvrdelivery AS dvrdel ON dvrdel.id = dat.dvrdelivery_id JOIN " + // 
								"logistica.dvrorderdelivery AS dvrod ON dvrod.dvrdelivery_id = dvrdel.id JOIN " + // 
								"logistica.order AS oc ON oc.id = dvrod.dvrorder_id JOIN " + // 
								"logistica.dvrdeliverystatetype AS dvrst ON dvrst.id = dvrdel.currentstatetype_id JOIN " + // 
								"logistica.bulk AS bu ON bu.dvrdelivery_id = dvrdel.id JOIN " + // 
								"logistica.document AS doc ON doc.id = bu.document_id LEFT JOIN " + // 
								"nosent AS ns ON dvrdel.id = ns.dvrdeliveryid " + //
							"WHERE " + //
								"dvrst.id IN (:dvrdeliverystateids) AND rs.location_id = :locationid " + vendorCondition + // 
								whereCondition + // 
							"GROUP BY dvrdel.id), " ; //		
			
			sqlWith3 =
					"starts AS( " + //
							"SELECT " + //
								"dvrdel.id AS dvrdeliveryid, " + //
								"MIN(md.starts) AS starttime " + //
							"FROM " + //
								"logistica.reserve AS rs JOIN " + //
								"logistica.reservedetail AS rd ON rd.reserve_id = rs.id JOIN " + //
								"logistica.dating AS dat ON dat.id = rs.id JOIN " + // 
								"logistica.dvrdelivery AS dvrdel ON dvrdel.id = dat.dvrdelivery_id JOIN " + // 
								"logistica.dvrorderdelivery AS dvrod ON dvrod.dvrdelivery_id = dvrdel.id JOIN " + // 
								"logistica.order AS oc ON oc.id = dvrod.dvrorder_id JOIN " + // 
								"logistica.dvrdeliverystatetype AS dvrst ON dvrst.id = dvrdel.currentstatetype_id JOIN " + // 
								"logistica.bulk AS bu ON bu.dvrdelivery_id = dvrdel.id JOIN " + // 
								"logistica.document AS doc ON doc.id = bu.document_id JOIN " + // 
								"logistica.module AS md ON md.id = rd.module_id " + // 
							"WHERE "+ //
								"dvrst.id IN (:dvrdeliverystateids) AND rs.location_id = :locationid " + vendorCondition + // 
								whereCondition + // 
							"GROUP BY dvrdel.id), ";
			
			sqlWith4 =
					"documents AS( " + //
							"SELECT " + //
								"dvrdel.id AS dvrdeliveryid, " + //
								"COUNT(DISTINCT doc.id) AS documents " + //
							"FROM " + //
								"logistica.reserve AS rs JOIN " + //
								"logistica.dating AS dat ON dat.id = rs.id JOIN " + //
								"logistica.dvrdelivery AS dvrdel ON dvrdel.id = dat.dvrdelivery_id JOIN " + //
								"logistica.dvrorderdelivery AS dvrod ON dvrod.dvrdelivery_id = dvrdel.id JOIN " + //
								"logistica.order AS oc ON oc.id = dvrod.dvrorder_id JOIN " + //
								"logistica.dvrdeliverystatetype AS dvrst ON dvrst.id = dvrdel.currentstatetype_id JOIN " + //
								"logistica.bulk AS bu ON bu.dvrdelivery_id = dvrdel.id JOIN " + //
								"logistica.document AS doc ON doc.id = bu.document_id " + //
							"WHERE "+ //
								"dvrst.id IN (:dvrdeliverystateids) AND rs.location_id = :locationid " + vendorCondition + // 
							"GROUP BY dvrdel.id) ";
			
			sqlSelect =
					"SELECT DISTINCT " + //				
						"dvrdel.id AS deliveryid, " + //
						"dvrdel.number AS deliverynumber, " + //
						"ve.code AS vendorcode, " + //
						"ve.name as vendorname, " + //
						"ot.id AS ordertypeid, "+ //
						"ot.code AS ordertypecode, "+ //
						"ot.description AS ordertypedescription , " + //						
						"dk.code AS dockname, " + //
						"st.starttime, " + //
						"CASE WHEN ns.nosentcount > 0 " + //
							 "THEN 'Informar envío de ASN' " + //
							 "ELSE CASE WHEN dat.sentstatus IS FALSE " + //
							 		   "THEN 'Informar Cita' " + //
							 		   "ELSE '' " + //
							 		   "END " + //
							 "END AS nextaction, " + //
						"CAST(docs.documents AS INTEGER) AS documents "; //

			sqlExtraFrom = 
					"JOIN "+ //
					"logistica.vendor AS ve ON ve.id = dvrdel.vendor_id JOIN " + //							
					"logistica.dock AS dk ON dk.id = rd.dock_id JOIN " + //
					"starts AS st ON st.dvrdeliveryid = dvrdel.id JOIN " + //
					"nosent1 AS ns ON ns.dvrdeliveryid = dvrdel.id JOIN " + //
					"documents AS docs ON docs.dvrdeliveryid = dvrdel.id "; //
			
			orderByCondition = getOrderByString(mapGetDvrDeliveryDatingDocumentSQL, orderby);			
		}
		else {
			throw new OperationFailedException("El tipo de query no es válido: " + queryType);
		}
				
		sqlFrom = 
				"FROM "+ //
					"logistica.reserve AS rs JOIN " + //
					"logistica.reservedetail as rd ON rd.reserve_id = rs.id JOIN " + //
					"logistica.dating AS dat ON dat.id = rs.id JOIN " + //
					"logistica.dvrdelivery as dvrdel ON dvrdel.id = dat.dvrdelivery_id JOIN " + //
					"logistica.dvrorderdelivery as dvrod ON dvrod.dvrdelivery_id = dvrdel.id JOIN " + //
					"logistica.order AS oc ON oc.id = dvrod.dvrorder_id JOIN " + //
					"logistica.ordertype AS ot ON ot.id = oc.ordertype_id JOIN " + //
					"logistica.dvrdeliverystatetype AS dvrst ON dvrst.id = dvrdel.currentstatetype_id JOIN " + //
					"logistica.bulk AS bu ON bu.dvrdelivery_id = dvrdel.id JOIN " + //
					"logistica.document AS doc ON doc.id = bu.document_id "; //

		sqlWhere =
				"WHERE " + // 
					"dvrst.id IN (:dvrdeliverystateids) AND rs.location_id = :locationid " + vendorCondition + //
					whereCondition; //
							
		sql  = 
				sqlWith1 +
				sqlWith2 + 
				sqlWith3 +
				sqlWith4 +
				sqlSelect+ 
				sqlFrom+ 
				sqlExtraFrom + 
				sqlWhere + 
				groupBy + 
				orderByCondition;
		
		return sql;		
	}
	
	private String getPackingListReportQueryString(int queryType, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException {
		
		String sql;
		String sqlSelect 		= "";
		String sqlFrom			= "";
		String sqlWhere			= "";
		String sqlExtraFrom		= "";
		String condicionOrderBy	= "";
		
		if (queryType == 1){
			sqlSelect = 	"SELECT CAST(count(bd.dvrdelivery_id) AS INTEGER) as totalreg, " + //
							"		SUM(bd.units) AS totalunits " ; 
		}
		else if (queryType == 2){
			sqlSelect = 	"SELECT " + //
							"	dl.number AS deliverynumber, " + //
							"	loc.code as  destinationlocationcode, " + // 
							"	loc.name as  destinationlocation, " + // 
							"	oc.number AS ordernumber, " + // 
							"	bu.lpncode as  lpncode, " + // 
							"	it.sku, " + //
							"	it.name AS itemdescription, " + //	
							"	doc.number as documentnumber, " + //
							"	dt.name as  documentype, " + //
							"	bd.units "; 
			
			sqlExtraFrom = 	"JOIN logistica.dvrdelivery AS dl " + //
							"ON dl.id = bd.dvrdelivery_id " + //
							"JOIN logistica.location as loc " + // 
							"ON loc.id = bd.location_id " + // 
							"JOIN logistica.item as it " + // 
							"ON it.id = bd.item_id " + // 
							"JOIN logistica.document as doc " + //
							"ON doc.id = bu.document_id " + //
							"JOIN logistica.documenttype as dt " + //
							"ON dt.id = doc.documenttype_id ";
			
			condicionOrderBy = getOrderByString(mapGetPackingListSQL, orderby);
		}
		else{
			throw new OperationFailedException("El tipo de query no es válido: " + queryType);
		}
		
		sqlFrom = 	"FROM logistica.bulk AS bu " + // 
					"JOIN logistica.bulkdetail AS bd " + // 
					"ON bd.bulk_id = bu.id " + // 
					"JOIN logistica.order as oc " + //
					"ON oc.id = bd.dvrorder_id " + // 
					"JOIN logistica.vendor as ve " + // 
					"ON ve.id = oc.vendor_id "; 

					
		sqlWhere =	"WHERE bd.dvrdelivery_id = :dvrdeliveryid " + //
					"AND ve.id = :vendorid ";
		
		sql  =
				sqlSelect+ 
				sqlFrom+ 
				sqlExtraFrom + 
				sqlWhere + 
				condicionOrderBy;
		
		return sql;	
		
	}

	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Long getNextSequenceDvrDeliveryNumber() throws OperationFailedException, NotFoundException {
        Query query = getEntityManager().createNativeQuery("select nextval('LOGISTICA.DVRDELIVERYNUMBER_SEQ')");
        BigInteger bigint = (BigInteger) query.getSingleResult();
        Long result = bigint.longValue();
        return result;
	}
	
	public DvrDeliveryW[] getDvrDeliveryByIds(Long[] dvrdeliveryids) throws OperationFailedException, NotFoundException {

		DvrDeliveryW[] result = null;
		List<DvrDeliveryW> resultList = new ArrayList<DvrDeliveryW>();
		
		List<Long> dvrdeliveryidsList = Arrays.asList(dvrdeliveryids);
		String queryStr = 	"select dvrdel " + //
							"from DvrDelivery as dvrdel " + // 
							"where " + //
							"dvrdel.id in (:dvrdeliveryids) ";
							
							 
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("dvrdel.id", "dvrdeliveryids", dvrdeliveryidsList);
		
		resultList = getByQuery(queryStr, properties);
		result =(DvrDeliveryW[]) resultList.toArray(new DvrDeliveryW[resultList.size()]);
		return result;
		
	} 
	
	public DvrDeliveryReportDataDTO getDvrDeliveryReportDataById(Long dvrdeliveryid, Long vendorid) throws AccessDeniedException, OperationFailedException  {
		
		String whereCondition = "AND dvrdel.id = :dvrdeliveryid ";
		String SQL = getDvrDeliveryReportQueryString(2, null, whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DvrDeliveryReportDataDTO.class));
		DvrDeliveryReportDataDTO result = (DvrDeliveryReportDataDTO) query.uniqueResult();
		return result;
	}
	
	public DvrDeliveryReportDataDTO[] getDvrDeliveryReport(Long vendorid, String searchvalue, Long deliverystatetypeid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated, int filtertype) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition = "";
		
		switch (filtertype) {
		case 0:
			// VIGENTES
			whereCondition = "AND dst.valid IS TRUE ";
			break;

		case 1:
			// NÚMERO DE ENTREGA
			whereCondition = "AND dvrdel.number = :deliverynumber ";
			break;

		case 2:
			// NÚMERO DE OC
			whereCondition = "AND oc.number = :ordernumber ";
			break;
			
		case 3:
			// ESTADO
			whereCondition = "AND dst.id = :deliverystatetypeid ";
			break;
			
		case 4:
			// NÚMERO DE BULTO
			whereCondition = "AND bu.lpncode = :lpncode ";
			break;
			
		case 5:
			// NÚMERO DE DOCUMENTO
			whereCondition = "AND doc.number = :documentnumber ";
			break;
			
		default:
			whereCondition = "AND dst.valid IS TRUE ";
			break;
		}
		
		String SQL = getDvrDeliveryReportQueryString(2, orderby, whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		
		switch (filtertype) {
		case 0:
			// VIGENTES
			break;
		case 1:
			// NÚMERO DE ENTREGA
			query.setLong("deliverynumber", Long.valueOf(searchvalue));
			break;
		
		case 2:
			// NÚMERO DE OC
			query.setLong("ordernumber", Long.valueOf(searchvalue));
			break;
		
		case 3:
			query.setLong("deliverystatetypeid", deliverystatetypeid);
			break;
			
		case 4:
			// NÚMERO DE BULTO
			query.setString("lpncode", searchvalue);
			break;
			
		case 5:
			// NÚMERO DE DOCUMENTO
			query.setString("documentnumber", searchvalue);
			break;
	
		default:
			break;
		
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(DvrDeliveryReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List<?> list = query.list();
		DvrDeliveryReportDataDTO[] result = (DvrDeliveryReportDataDTO[]) list.toArray(new DvrDeliveryReportDataDTO[list.size()]);		
		return result;
		
	}
	
	
	public int getCountDvrDeliveryReport(Long vendorid, String searchvalue, Long deliverystatetypeid, int filtertype) throws AccessDeniedException, OperationFailedException{
		String whereCondition = "";
		
		switch (filtertype) {
		case 0:
			// VIGENTES
			whereCondition = "AND dst.valid IS TRUE ";
			break;

		case 1:
			// NÚMERO DE ENTREGA
			whereCondition = "AND dvrdel.number = :deliverynumber ";
			break;

		case 2:
			// NÚMERO DE OC
			whereCondition = "AND oc.number = :ordernumber ";
			break;
			
		case 3:
			// ESTADO
			whereCondition = "AND dst.id = :deliverystatetypeid ";
			break;

		case 4:
			// NÚMERO DE BULTO
			whereCondition = "AND bu.lpncode = :lpncode ";
			break;
			
		case 5:
			// NÚMERO DE DOCUMENTO
			whereCondition = "AND doc.number = :documentnumber ";
			break;
			
		default:
			whereCondition = "AND dst.valid IS TRUE ";
			break;
		}
		
		String SQL = getDvrDeliveryReportQueryString(1, null, whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		
		switch (filtertype) {
		case 0:
			// VIGENTES
			break;
		case 1:
			// NÚMERO DE ENTREGA
			query.setLong("deliverynumber", Long.valueOf(searchvalue));
			break;
		
		case 2:
			// NÚMERO DE OC
			query.setLong("ordernumber", Long.valueOf(searchvalue));
			break;
		
		case 3:
			query.setLong("deliverystatetypeid", deliverystatetypeid);
			break;
			
		case 4:
			// NÚMERO DE BULTO
			query.setString("lpncode", searchvalue);
			break;
			
		case 5:
			// NÚMERO DE DOCUMENTO
			query.setString("documentnumber", searchvalue);
			break;
	
		default:
			break;
		
		}
		
		int total = Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
		return total;
		
	}
	
	public DvrDeliveryDetailDataDTO[] getDvrDeliveryDetailReport(Long vendorid, Long deliveryid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException {
		
		String SQL = getDvrDeliveryDetailReportQueryString(2, orderby);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("deliveryid", deliveryid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DvrDeliveryDetailDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List<?> list = query.list();
		DvrDeliveryDetailDataDTO[] result = (DvrDeliveryDetailDataDTO[]) list.toArray(new DvrDeliveryDetailDataDTO[list.size()]);		
		return result;	
	}
	
	public DvrDeliveryDetailTotalDataDTO getCountDvrDeliveryDetailReport(Long vendorid, Long deliveryid) throws AccessDeniedException, OperationFailedException {
		
		String SQL = getDvrDeliveryDetailReportQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("deliveryid", deliveryid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DvrDeliveryDetailTotalDataDTO.class));
		DvrDeliveryDetailTotalDataDTO result = (DvrDeliveryDetailTotalDataDTO) query.uniqueResult();
		return result;
	}

	
	public PackingListReportDataDTO[] getPackingListReport(Long vendorid, Long dvrdeliveryid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException {
		String SQL = getPackingListReportQueryString(2, orderby);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		query.setLong("vendorid", vendorid);
		query.setResultTransformer(new LowerCaseResultTransformer(PackingListReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List<?> list = query.list();
		PackingListReportDataDTO[] result = (PackingListReportDataDTO[]) list.toArray(new PackingListReportDataDTO[list.size()]);		
		return result;
		
	}
	
	
	public PackingListReportTotalDataDTO getCountPackingListReport(Long vendorid, Long dvrdeliveryid) throws AccessDeniedException, OperationFailedException {
		
		String SQL = getPackingListReportQueryString(1, null);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(PackingListReportTotalDataDTO.class));
		PackingListReportTotalDataDTO result = (PackingListReportTotalDataDTO) query.uniqueResult();
		return result;
	}
	
	
	public DvrDeliveryDatingDocumentReportDataDTO[] getDvrDeliveryDatingDocumentReport(Long[] dvrdeliverystateids, Long vendorid, Long locationid, String filtervalue, LocalDateTime when, Integer criterium, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException {

		String whereCondition = "";
		
		switch (criterium) {
			case 0:
				// Fecha de la Cita
				whereCondition = "AND rs.when1 = :when ";		
				break;
				
			case 1:
				//  Número de OC
				whereCondition = "AND oc.number = :filtervalue ";
				break;
			case 2:
				// Número de Documento
				whereCondition = "AND doc.number = :filtervalue ";
				break;
			
			default:
				break;
		
		}
		
		String sql = getDvrDeliveryDatingDocumentReportQuery(vendorid, 2, orderby, whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql);
		if(!vendorid.equals(-1L)){
			query.setLong("vendorid", vendorid);
		}	
		query.setLong("locationid", locationid);
		query.setParameterList("dvrdeliverystateids", dvrdeliverystateids);
		
		switch (criterium) {
			case 0:
				// Fecha de la Cita
				query.setParameter("when", when.toLocalDate().atStartOfDay());		
				break;
				
			case 1:
				//  Número de OC
				query.setLong("filtervalue", Long.parseLong(filtervalue));
				break;
			case 2:
				// Número de Documento
				query.setString("filtervalue", filtervalue);
				break;
			
			default:
				break;
		
		}
				
		query.setResultTransformer(new LowerCaseResultTransformer(DvrDeliveryDatingDocumentReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List<?> list = query.list();
		DvrDeliveryDatingDocumentReportDataDTO[] result = (DvrDeliveryDatingDocumentReportDataDTO[]) list.toArray(new DvrDeliveryDatingDocumentReportDataDTO[list.size()]);		
		return result;
	}
	
	public int getCountDvrDeliveryDatingDocumentReport(Long[] dvrdeliverystateids, Long vendorid, Long locationid, String filtervalue, LocalDateTime when, Integer criterium) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		
		switch (criterium) {
			case 0:
				// Fecha de la Cita
				whereCondition = "AND rs.when1 = :when ";		
				break;
				
			case 1:
				//  Número de OC
				whereCondition = "AND oc.number = :filtervalue ";
				break;
			case 2:
				// Número de Documento
				whereCondition = "AND doc.number = :filtervalue ";
				break;
			
			default:
				break;
		
		}
				
		String sql = getDvrDeliveryDatingDocumentReportQuery(vendorid, 1, null, whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql);

		if(!vendorid.equals(-1L)){
			query.setLong("vendorid", vendorid);
		}	
		query.setLong("locationid", locationid);
		query.setParameterList("dvrdeliverystateids", dvrdeliverystateids);

		switch (criterium) {
			case 0:
				// Fecha de la Cita
				query.setParameter("when", when.toLocalDate().atStartOfDay());		
				break;
				
			case 1:
				//  Número de OC
				query.setLong("filtervalue", Long.parseLong(filtervalue));
				break;
			case 2:
				// Número de Documento
				query.setString("filtervalue", filtervalue);
				break;
			
			default:
				break;
		
		}
		
		int total = Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
		return total;
	}
	
	
	public DvrDeliveryW[] getDvrDeliveryByOrderIds(Long[] dvrorderids) throws OperationFailedException, NotFoundException {
		DvrDeliveryW[] result = null;
		List<DvrDeliveryW> resultList = new ArrayList<DvrDeliveryW>();
		
		List<Long> dvrorderidsList = Arrays.asList(dvrorderids);
		
		String queryStr = 	"select del from " + //
							"DvrDelivery as del, " + //
							"DvrOrderDelivery as od " + //
							"where " + //
							"od.dvrdelivery.id = del.id " + //
							"and od.dvrorder.id in (:dvrorderids) ";
							
				 
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("od.dvrorder.id", "dvrorderids", dvrorderidsList);
		
		resultList = getByQuery(queryStr, properties);
		result =(DvrDeliveryW[]) resultList.toArray(new DvrDeliveryW[resultList.size()]);
		return result;

	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 												AJUSTE DE UNIDADES																					//
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void doCreateAdjustTempTable() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doCreateAdjustTempTable");
		query.executeUpdate();
	}
	
	public void doCreateAdjustTempErrorTable() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doCreateAdjustTempErrorTable");
		query.executeUpdate();		
	}
	
	public int doLoadCSVAdjust(String filename) throws IOException {
		String[] lines = ClassUtilities.getLineDataArrayFromCSV(filename, Charset.defaultCharset());
		String columns = "rownumber, ordernumber, destinationlocationcode, itemsku, availableunits";
		String[] columnArray = columns.split(",");
		String tableName = "adjustupload";
		String SQL = ClassUtilities.getQueryElementsTempTable(tableName, columnArray, lines);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		int total = query.executeUpdate();
		return total;	
	}
	
	public FileUploadErrorDTO[] getErrorsOfAdjust() {
		
		String SQL =
					"SELECT " + //
						"line, " + //
						"error " + //
					"FROM " + //
						"adjusterror " + //
					"ORDER BY " + //
						"line";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(FileUploadErrorDTO.class));
		
		List<?> list = query.list();
		FileUploadErrorDTO[] result = (FileUploadErrorDTO[]) list.toArray(new FileUploadErrorDTO[list.size()]);
		return result;
	}
	
	public Long[] getDvrOrderIdsFromAdjustUnitsData() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.getDvrOrderIdsFromAdjustUnitsData");
		List<?> list = query.list();
		Long[] result =  list.stream().map(num-> Long.parseLong(num.toString())).toArray(Long[]::new);
		return result;
	}
	
	public int doFillOrderData() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doFillOrderData");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doFillDestinationLocationData() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doFillDestinationLocationData");
		int total = query.executeUpdate();
		return total;		
	}

	public int doFillItemData() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doFillItemData");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doFillDeliveryData(Long dvrdeliveryid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doFillDeliveryData");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doFillDvrOrderDeliveryDetailPosition(Long dvrdeliveryid){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doFillDvrOrderDeliveryDetailPosition");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		int total = query.executeUpdate();
		return total;		
	}
	
	public int doCheckUniqueRows() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doCheckUniqueRows");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckOrders() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doCheckOrders");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckDestinationLocations() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doCheckDestinationLocations");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckItems() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doCheckItems");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckDvrOrderDeliveryDetails() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doCheckDvrOrderDeliveryDetails");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckUnitsEqualOrGreaterThanZero() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doCheckUnitsEqualOrGreaterThanZero");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckUnitsEqualOrLessThanPendingDeliveryDetails() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doCheckUnitsEqualOrLessThanPendingDeliveryDetails");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckUnitsEqualOrLessThanPendingPredeliveryDetails() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doCheckUnitsEqualOrLessThanPendingPredeliveryDetails");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doUpdateDvrOrderDeliveryDetails() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery.doUpdateDvrOrderDeliveryDetails");
		int total = query.executeUpdate();
		return total;
	}
		
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DvrDelivery";
	}
	@Override
	protected void setEntityname() {
		entityname = "DvrDelivery";
	}
}
