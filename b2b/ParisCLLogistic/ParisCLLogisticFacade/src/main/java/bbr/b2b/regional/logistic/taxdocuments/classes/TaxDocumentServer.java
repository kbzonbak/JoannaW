package bbr.b2b.regional.logistic.taxdocuments.classes;

import java.math.BigInteger;
import java.util.ArrayList;
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
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.TaxDocumentW;
import bbr.b2b.regional.logistic.taxdocuments.entities.TaxDocument;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionCSVReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailExcelDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailTotalDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionExcelReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.TaxDocumentReportDataDTO;
import bbr.b2b.regional.logistic.utils.ClassUtilities;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/taxdocuments/TaxDocumentServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TaxDocumentServer extends LogisticElementServer<TaxDocument, TaxDocumentW> implements TaxDocumentServerLocal {

	private Map<String, String> mapGetTaxDocumentSQL = new HashMap<String, String>();
	{
		mapGetTaxDocumentSQL.put("ORDERNUMBER", "tdd.ordernumber");
		mapGetTaxDocumentSQL.put("ORDERTYPECODE", "tdd.ordertypecode");
		mapGetTaxDocumentSQL.put("ORDERTYPENAME", "tdd.ordertypename");
		mapGetTaxDocumentSQL.put("DELIVERYLOCATIONCODE", "tdd.deliverylocationcode");
		mapGetTaxDocumentSQL.put("DELIVERYLOCATIONNAME", "tdd.deliverylocationname");
		mapGetTaxDocumentSQL.put("TAXDOCUMENTNUMBER", "tdd.taxdocumentnumber");
		mapGetTaxDocumentSQL.put("RECEPTIONNUMBER", "tdd.receptionnumber");
		mapGetTaxDocumentSQL.put("RECEPTIONDATE", "tdd.receptiondate");
		mapGetTaxDocumentSQL.put("AMOUNT", "amount");
	}
	
	private Map<String, String> mapGetReceptionSQL = new HashMap<String, String>();
	{
		mapGetReceptionSQL.put("ORDERNUMBER", "tdd.ordernumber");
		mapGetReceptionSQL.put("ORDERTYPECODE", "tdd.ordertypecode");
		mapGetReceptionSQL.put("ORDERTYPENAME", "tdd.ordertypename");
		mapGetReceptionSQL.put("DELIVERYLOCATIONCODE", "tdd.deliverylocationcode");
		mapGetReceptionSQL.put("DELIVERYLOCATIONNAME", "tdd.deliverylocationname");
		mapGetReceptionSQL.put("TAXDOCUMENTNUMBER", "tdd.taxdocumentnumber");
		mapGetReceptionSQL.put("RECEPTIONNUMBER", "tdd.receptionnumber");
		mapGetReceptionSQL.put("RECEPTIONDATE", "tdd.receptiondate");
		mapGetReceptionSQL.put("ITEMINTERNALCODE", "tdd.iteminternalcode");
		mapGetReceptionSQL.put("ITEMNAME", "tdd.itemname");
		mapGetReceptionSQL.put("VENDORITEMCODE", "tdd.vendoritemcode");
		mapGetReceptionSQL.put("RECEIVEDUNITS", "receivedunits");
		mapGetReceptionSQL.put("FINALCOST", "tdd.finalcost");
		mapGetReceptionSQL.put("TOTALRECEIVED", "totalreceived");
	}
	
	private Map<String, String> mapGetReceptionDetailSQL = new HashMap<String, String>();
	{
		mapGetReceptionDetailSQL.put("ORDERNUMBER", "tdd.ordernumber");
		mapGetReceptionDetailSQL.put("ORDERTYPECODE", "tdd.ordertypecode");
		mapGetReceptionDetailSQL.put("ORDERTYPENAME", "tdd.ordertypename");
		mapGetReceptionDetailSQL.put("DELIVERYLOCATIONCODE", "tdd.deliverylocationcode");
		mapGetReceptionDetailSQL.put("DELIVERYLOCATIONNAME", "tdd.deliverylocationname");
		mapGetReceptionDetailSQL.put("TAXDOCUMENTNUMBER", "tdd.taxdocumentnumber");
		mapGetReceptionDetailSQL.put("RECEPTIONNUMBER", "tdd.receptionnumber");
		mapGetReceptionDetailSQL.put("RECEPTIONDATE", "tdd.receptiondate");
		mapGetReceptionDetailSQL.put("ITEMINTERNALCODE", "tdd.iteminternalcode");
		mapGetReceptionDetailSQL.put("ITEMNAME", "tdd.itemname");
		mapGetReceptionDetailSQL.put("VENDORITEMCODE", "tdd.vendoritemcode");
		mapGetReceptionDetailSQL.put("RECEIVEDUNITS", "receivedunits");
		mapGetReceptionDetailSQL.put("FINALCOST", "tdd.finalcost");
		mapGetReceptionDetailSQL.put("TOTALRECEIVED", "totalreceived");
	}
	
	private Map<String, String> mapGetTaxDocumentValidationSQL = new HashMap<String, String>();
	{
		mapGetTaxDocumentValidationSQL.put("TAXDOCUMENTEMITTED", "taxdocumentemitted");
		mapGetTaxDocumentValidationSQL.put("TAXDOCUMENTNUMBER", "td.number");
		mapGetTaxDocumentValidationSQL.put("TAXDOCUMENTVALIDATED", "td.validated");
		mapGetTaxDocumentValidationSQL.put("DATINGNUMBER", "dt.number");
		mapGetTaxDocumentValidationSQL.put("DATINGDATE", "datingdate");
		mapGetTaxDocumentValidationSQL.put("ORDERNUMBER", "oc.number");
		mapGetTaxDocumentValidationSQL.put("VENDORCODE", "vd.code");
		mapGetTaxDocumentValidationSQL.put("VENDORTRADENAME", "vd.tradename");
	}
	
	@EJB
	VendorServerLocal vendorserver;
	
	public TaxDocumentW addTaxDocument(TaxDocumentW taxdocument) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (TaxDocumentW) addElement(taxdocument);
	}
	
	public TaxDocumentW[] getTaxDocuments() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (TaxDocumentW[]) getIdentifiables();
	}
	
	public TaxDocumentW updateTaxDocument(TaxDocumentW taxdocument) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (TaxDocumentW) updateElement(taxdocument);
	}

	@Override
	protected void copyRelationsEntityToWrapper(TaxDocument entity, TaxDocumentW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(TaxDocument entity, TaxDocumentW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "TaxDocument";
	}
	@Override
	protected void setEntityname() {
		entityname = "TaxDocument";
	}
	
	public ReceptionReportDataDTO[] getReceptionReportByVendorDeliveryLocationAndDate(Long vendorid, Long deliverylocationid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException {
		String whereCondition = "";
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition = "AND o.deliverylocation_id = :deliverylocationid "; //
		}
		
		whereCondition += "AND td.receptiondate >= :since AND td.receptiondate < :until "; //
		
		String SQL = getReceptionReportQueryString(whereCondition, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("deliverylocationid", deliverylocationid);
		}
		query.setDate("since", since);
		query.setDate("until", until);						
		query.setResultTransformer(new LowerCaseResultTransformer(ReceptionReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		ReceptionReportDataDTO[] result = (ReceptionReportDataDTO[]) list.toArray(new ReceptionReportDataDTO[list.size()]);		
		
		return result;
	}
	
	public ReceptionReportDataDTO[] getReceptionReportByVendorDeliveryLocationAndOrderNumber(Long vendorid, Long deliverylocationid, Long number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException {
		String whereCondition = "";
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition = "AND o.deliverylocation_id = :deliverylocationid "; //
		}
		
		whereCondition += "AND o.number = :number "; //
		
		String SQL = getReceptionReportQueryString(whereCondition, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("deliverylocationid", deliverylocationid);
		}
		query.setLong("number", number);					
		query.setResultTransformer(new LowerCaseResultTransformer(ReceptionReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		ReceptionReportDataDTO[] result = (ReceptionReportDataDTO[]) list.toArray(new ReceptionReportDataDTO[list.size()]);		
		
		return result;
	}
	
	public ReceptionReportDataDTO[] getReceptionReportByVendorDeliveryLocationAndItemInternalCode(Long vendorid, Long deliverylocationid, String code, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException {
		String whereCondition = "";
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition = "AND o.deliverylocation_id = :deliverylocationid "; //
		}
		
		whereCondition += "AND i.internalcode = :code "; //
		
		String SQL = getReceptionReportQueryString(whereCondition, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("deliverylocationid", deliverylocationid);
		}
		query.setString("code", code);					
		query.setResultTransformer(new LowerCaseResultTransformer(ReceptionReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		ReceptionReportDataDTO[] result = (ReceptionReportDataDTO[]) list.toArray(new ReceptionReportDataDTO[list.size()]);		
		
		return result;
	}
	
	public ReceptionReportDataDTO[] getReceptionReportByVendorDeliveryLocationAndNumber(Long vendorid, Long deliverylocationid, String number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException {
		String whereCondition = "";
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition = "AND o.deliverylocation_id = :deliverylocationid "; //
		}
		
		whereCondition += "AND td.receptionnumber = :number "; //
		
		String SQL = getReceptionReportQueryString(whereCondition, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("deliverylocationid", deliverylocationid);
		}
		query.setString("number", number);					
		query.setResultTransformer(new LowerCaseResultTransformer(ReceptionReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		ReceptionReportDataDTO[] result = (ReceptionReportDataDTO[]) list.toArray(new ReceptionReportDataDTO[list.size()]);		
		
		return result;
	}
	
	public ReceptionReportDataDTO[] getReceptionReportByVendorDeliveryLocationAndTaxDocumentNumber(Long vendorid, Long deliverylocationid, Long number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException {
		String whereCondition = "";
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition = "AND o.deliverylocation_id = :deliverylocationid "; //
		}
		
		whereCondition += "AND td.number = :number "; //
		
		String SQL = getReceptionReportQueryString(whereCondition, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("deliverylocationid", deliverylocationid);
		}
		query.setLong("number", number);					
		query.setResultTransformer(new LowerCaseResultTransformer(ReceptionReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		ReceptionReportDataDTO[] result = (ReceptionReportDataDTO[]) list.toArray(new ReceptionReportDataDTO[list.size()]);		
		
		return result;
	}
	
	public ReceptionReportDataDTO[] getReceptionReportByVendorDeliveryLocationAndTaxDocumentEmitted(Long vendorid, Long deliverylocationid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException {
		String whereCondition = "";
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition = "AND o.deliverylocation_id = :deliverylocationid "; //
		}
		
		whereCondition += "AND td.emitted >= :since AND td.emitted < :until "; //
		
		String SQL = getReceptionReportQueryString(whereCondition, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("deliverylocationid", deliverylocationid);
		}
		query.setDate("since", since);
		query.setDate("until", until);						
		query.setResultTransformer(new LowerCaseResultTransformer(ReceptionReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		ReceptionReportDataDTO[] result = (ReceptionReportDataDTO[]) list.toArray(new ReceptionReportDataDTO[list.size()]);		
		
		return result;
	}
	
	public int countReceptionReportByVendorDeliveryLocationAndDate(Long vendorid, Long deliverylocationid, Date since, Date until) throws OperationFailedException, AccessDeniedException {
		String whereCondition = "";
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition = "AND o.deliverylocation_id = :deliverylocationid "; //
		}
		
		whereCondition += "AND td.receptiondate >= :since AND td.receptiondate < :until "; //
		
		String SQL = getReceptionReportQueryString(whereCondition, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("deliverylocationid", deliverylocationid);
		}
		query.setDate("since", since);
		query.setDate("until", until);
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;
	}
	
	public int countReceptionReportByVendorDeliveryLocationAndOrderNumber(Long vendorid, Long deliverylocationid, Long number) throws OperationFailedException, AccessDeniedException {
		String whereCondition = "";
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition = "AND o.deliverylocation_id = :deliverylocationid "; //
		}
		
		whereCondition += "AND o.number = :number "; //
		
		String SQL = getReceptionReportQueryString(whereCondition, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("deliverylocationid", deliverylocationid);
		}
		query.setLong("number", number);					
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;
	}
	
	public int countReceptionReportByVendorDeliveryLocationAndItemInternalCode(Long vendorid, Long deliverylocationid, String code) throws OperationFailedException, AccessDeniedException {
		String whereCondition = "";
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition = "AND o.deliverylocation_id = :deliverylocationid "; //
		}
		
		whereCondition += "AND i.internalcode = :code "; //
		
		String SQL = getReceptionReportQueryString(whereCondition, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("deliverylocationid", deliverylocationid);
		}
		query.setString("code", code);					
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;
	}
	
	public int countReceptionReportByVendorDeliveryLocationAndNumber(Long vendorid, Long deliverylocationid, String number) throws OperationFailedException, AccessDeniedException {
		String whereCondition = "";
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition = "AND o.deliverylocation_id = :deliverylocationid "; //
		}
		
		whereCondition += "AND td.receptionnumber = :number "; //
		
		String SQL = getReceptionReportQueryString(whereCondition, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("deliverylocationid", deliverylocationid);
		}
		query.setString("number", number);					
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;
	}
	
	public int countReceptionReportByVendorDeliveryLocationAndTaxDocumentNumber(Long vendorid, Long deliverylocationid, Long number) throws OperationFailedException, AccessDeniedException {
		String whereCondition = "";
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition = "AND o.deliverylocation_id = :deliverylocationid "; //
		}
		
		whereCondition += "AND td.number = :number "; //
		
		String SQL = getReceptionReportQueryString(whereCondition, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("deliverylocationid", deliverylocationid);
		}
		query.setLong("number", number);					
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;
	}
	
	public int countReceptionReportByVendorDeliveryLocationAndTaxDocumentEmitted(Long vendorid, Long deliverylocationid, Date since, Date until) throws OperationFailedException, AccessDeniedException {
		String whereCondition = "";
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition = "AND o.deliverylocation_id = :deliverylocationid "; //
		}
		
		whereCondition += "AND td.emitted >= :since AND td.emitted < :until "; //
		
		String SQL = getReceptionReportQueryString(whereCondition, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if (deliverylocationid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("deliverylocationid", deliverylocationid);
		}
		query.setDate("since", since);
		query.setDate("until", until);						
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;
	}
	
	private String getReceptionReportQueryString(String whereCondition, int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
						
		String SQL =
			"WITH tdd AS (" + //
					"SELECT DISTINCT " + //
						"td.id AS taxdocument_id, " + //
						"td.number AS taxdocumentnumber, " + //
						"COALESCE(td.receptionnumber, '') AS receptionnumber, " + //
						"COALESCE(logistica.tostr(td.receptiondate), '') AS receptiondate, " + //
						"o.id AS order_id, " + //
						"o.number AS ordernumber, " + //
						"ot.id AS ordertype_id, " + //
						"ot.code AS ordertypecode, " + //
						"ot.name AS ordertypename, " + //
						"l.id AS deliverylocation_id, " + //
						"l.code AS deliverylocationcode, " + //
						"l.name AS deliverylocationname, " + //
						"bd.delivery_id, " + //
						"i.id AS item_id, " + //
						"od.finalcost, " + //
						"bd.location_id " + //
					"FROM " + //
						"logistica.taxdocument AS td JOIN " + //
						"logistica.bulkdetail AS bd ON bd.taxdocument_id = td.id JOIN " + //
						"logistica.orderdetail AS od ON od.order_id = bd.order_id AND od.item_id = bd.item_id JOIN " + //
						"logistica.order AS o ON o.id = od.order_id JOIN " + //
						"logistica.ordertype AS ot ON ot.id = o.ordertype_id JOIN " + //
						"logistica.location AS l ON l.id = o.deliverylocation_id JOIN " + //
						"logistica.item AS i ON i.id = od.item_id " + //
					"WHERE " + //
						"td.vendor_id = :vendorid " + //
						whereCondition + ")"; //
		
		if (queryType == 1) {
			SQL += 
				"SELECT " + //
					"COUNT(DISTINCT (taxdocument_id, order_id)) " + //
				"FROM " + //
					"tdd "; //
		}
		else {
			SQL +=
				"SELECT " + //
					"tdd.order_id AS orderid, " + //
					"tdd.ordernumber AS ordernumber, " + //
					"tdd.ordertype_id AS ordertypeid, " + //
					"tdd.ordertypecode AS ordertypecode, " + //
					"tdd.ordertypename AS ordertypename, " + //
					"tdd.deliverylocation_id AS deliverylocationid, " + //
					"tdd.deliverylocationcode AS deliverylocationcode, " + //
					"tdd.deliverylocationname AS deliverylocationname, " + //
					"tdd.taxdocument_id AS taxdocumentid, " + //
					"tdd.taxdocumentnumber AS taxdocumentnumber, " + //
					"tdd.receptionnumber AS receptionnumber, " + //
					"tdd.receptiondate AS receptiondate, " + //
					"SUM(odd.receivedunits * tdd.finalcost) AS amount " + //
				"FROM " + //
					"tdd JOIN " + //
					"logistica.orderdeliverydetail AS odd ON odd.order_id = tdd.order_id AND odd.delivery_id = tdd.delivery_id AND " + //
															"odd.item_id = tdd.item_id AND odd.location_id = tdd.location_id " + //
				"GROUP BY " + //
					"tdd.order_id, tdd.ordernumber, tdd.ordertype_id, tdd.ordertypecode, tdd.ordertypename, tdd.deliverylocation_id, tdd.deliverylocationcode, " + //
					"tdd.deliverylocationname, tdd.taxdocument_id, tdd.taxdocumentnumber, tdd.receptionnumber, tdd.receptiondate " + //
				getOrderByString(mapGetTaxDocumentSQL, orderby); //
		}
		
		return SQL;
	}
	
	public ReceptionExcelReportDataDTO[] getExcelReceptionReportByTaxDocuments(Long[] taxdocumentids, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		
		String SQL =
				"WITH tdd AS (" + //
						"SELECT DISTINCT " + //
							"td.id AS taxdocument_id, " + //
							"td.number AS taxdocumentnumber, " + //
							"COALESCE(td.receptionnumber, '') AS receptionnumber, " + //
							"COALESCE(logistica.tostr(td.receptiondate), '') AS receptiondate, " + //
							"o.id AS order_id, " + //
							"o.number AS ordernumber, " + //
							"ot.id AS ordertype_id, " + //
							"ot.code AS ordertypecode, " + //
							"ot.name AS ordertypename, " + //
							"l.id AS deliverylocation_id, " + //
							"l.code AS deliverylocationcode, " + //
							"l.name AS deliverylocationname, " + //
							"bd.delivery_id, " + //
							"bd.item_id, " + //
							"od.finalcost, " + //
							"bd.location_id " + //
						"FROM " + //
							"logistica.taxdocument AS td JOIN " + //
							"logistica.bulkdetail AS bd ON bd.taxdocument_id = td.id JOIN " + //
							"logistica.orderdetail AS od ON od.order_id = bd.order_id AND od.item_id = bd.item_id JOIN " + //
							"logistica.order AS o ON o.id = od.order_id JOIN " + //
							"logistica.ordertype AS ot ON ot.id = o.ordertype_id JOIN " + //
							"logistica.location AS l ON l.id = o.deliverylocation_id " + //
						"WHERE " + //
							"td.id IN (:taxdocumentids)) " + //
				"SELECT " + //
					"tdd.order_id AS orderid, " + //
					"tdd.ordernumber AS ordernumber, " + //
					"tdd.ordertype_id AS ordertypeid, " + //
					"tdd.ordertypecode AS ordertypecode, " + //
					"tdd.ordertypename AS ordertypename, " + //
					"tdd.deliverylocation_id AS deliverylocationid, " + //
					"tdd.deliverylocationcode AS deliverylocationcode, " + //
					"tdd.deliverylocationname AS deliverylocationname, " + //
					"tdd.taxdocument_id AS taxdocumentid, " + //
					"tdd.taxdocumentnumber AS taxdocumentnumber, " + //
					"tdd.receptionnumber AS receptionnumber, " + //
					"tdd.receptiondate AS receptiondate, " + //
					"SUM(odd.receivedunits * tdd.finalcost) AS amount " + //
				"FROM " + //
					"tdd JOIN " + //
					"logistica.orderdeliverydetail AS odd ON odd.order_id = tdd.order_id AND odd.delivery_id = tdd.delivery_id AND " + //
															"odd.item_id = tdd.item_id AND odd.location_id = tdd.location_id " + //
				"GROUP BY " + //
					"tdd.order_id, tdd.ordernumber, tdd.ordertype_id, tdd.ordertypecode, tdd.ordertypename, tdd.deliverylocation_id, tdd.deliverylocationcode, " + //
					"tdd.deliverylocationname, tdd.taxdocument_id, tdd.taxdocumentnumber, tdd.receptionnumber, tdd.receptiondate " + //
					getOrderByString(mapGetTaxDocumentSQL, orderby); //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("taxdocumentids", taxdocumentids);
								
		query.setResultTransformer(new LowerCaseResultTransformer(ReceptionExcelReportDataDTO.class));
		
		List list = query.list();
		ReceptionExcelReportDataDTO[] result = (ReceptionExcelReportDataDTO[]) list.toArray(new ReceptionExcelReportDataDTO[list.size()]);		
		
		return result;
	}
	
	public ReceptionCSVReportDataDTO[] getCSVReceptionReportByTaxDocuments(Long[] taxdocumentids, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		
		String SQL =
				"WITH tdd AS (" + //
						"SELECT DISTINCT " + //
							"td.id AS taxdocument_id, " + //
							"td.number AS taxdocumentnumber, " + //
							"COALESCE(td.receptionnumber, '') AS receptionnumber, " + //
							"COALESCE(logistica.tostr(td.receptiondate), '') AS receptiondate, " + //
							"o.id AS order_id, " + //
							"o.number AS ordernumber, " + //
							"ot.id AS ordertype_id, " + //
							"ot.code AS ordertypecode, " + //
							"ot.name AS ordertypename, " + //
							"l.id AS deliverylocation_id, " + //
							"l.code AS deliverylocationcode, " + //
							"l.name AS deliverylocationname, " + //
							"bd.delivery_id, " + //
							"i.id AS item_id, " + //
							"i.internalcode AS iteminternalcode, " + //
							"i.name AS itemname, " + //
							"vi.vendoritemcode AS vendoritemcode, " + //
							"od.finalcost, " + //
							"bd.location_id " + //
						"FROM " + //
							"logistica.taxdocument AS td JOIN " + //
							"logistica.bulkdetail AS bd ON bd.taxdocument_id = td.id JOIN " + //
							"logistica.orderdetail AS od ON od.order_id = bd.order_id AND od.item_id = bd.item_id JOIN " + //
							"logistica.order AS o ON o.id = od.order_id JOIN " + //
							"logistica.ordertype AS ot ON ot.id = o.ordertype_id JOIN " + //
							"logistica.location AS l ON l.id = o.deliverylocation_id JOIN " + //
							"logistica.item AS i ON i.id = od.item_id JOIN " + //
							"logistica.vendoritem AS vi ON vi.vendor_id = td.vendor_id AND vi.item_id = i.id " + //
						"WHERE " + //
							"td.id IN (:taxdocumentids)) " + //		
				"SELECT " + //
					"tdd.order_id AS orderid, " + //
					"tdd.ordernumber AS ordernumber, " + //
					"tdd.ordertype_id AS ordertypeid, " + //
					"tdd.ordertypecode AS ordertypecode, " + //
					"tdd.ordertypename AS ordertypename, " + //
					"tdd.deliverylocation_id AS deliverylocationid, " + //
					"tdd.deliverylocationcode AS deliverylocationcode, " + //
					"tdd.deliverylocationname AS deliverylocationname, " + //
					"tdd.taxdocument_id AS taxdocumentid, " + //
					"tdd.taxdocumentnumber AS taxdocumentnumber, " + //
					"tdd.receptionnumber AS receptionnumber, " + //
					"tdd.receptiondate AS receptiondate, " + //
					"tdd.item_id AS itemid, " + //
					"tdd.iteminternalcode AS iteminternalcode, " + //
					"tdd.itemname AS itemname, " + //
					"tdd.vendoritemcode AS vendoritemcode, " + //
					"SUM(odd.receivedunits) AS receivedunits, " + //
					"tdd.finalcost AS finalcost, " + //
					"SUM(odd.receivedunits * tdd.finalcost) AS totalreceived " + //
				"FROM " + //
					"tdd JOIN " + //
					"logistica.orderdeliverydetail AS odd ON odd.order_id = tdd.order_id AND odd.delivery_id = tdd.delivery_id AND " + //
															"odd.item_id = tdd.item_id AND odd.location_id = tdd.location_id " + //			
				"GROUP BY " + //
					"tdd.order_id, tdd.ordernumber, tdd.ordertype_id, tdd.ordertypecode, tdd.ordertypename, tdd.deliverylocation_id, tdd.deliverylocationcode, " + //
					"tdd.deliverylocationname, tdd.taxdocument_id, tdd.taxdocumentnumber, tdd.receptionnumber, tdd.receptiondate, tdd.item_id, tdd.iteminternalcode, " + //
					"tdd.itemname, tdd.vendoritemcode, tdd.finalcost " + //
				getOrderByString(mapGetReceptionSQL, orderby); //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("taxdocumentids", taxdocumentids);
								
		query.setResultTransformer(new LowerCaseResultTransformer(ReceptionCSVReportDataDTO.class));
		
		List list = query.list();
		ReceptionCSVReportDataDTO[] result = (ReceptionCSVReportDataDTO[]) list.toArray(new ReceptionCSVReportDataDTO[list.size()]);		
		
		return result;
	}
	
	public Long[] getReceptionIdsByVendorDeliveryLocationDateAndPages(Long vendorid, Long deliverylocationid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws OperationFailedException, AccessDeniedException {
		ReceptionReportDataDTO[] result;
		List<Object> totalReceptions = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				result = getReceptionReportByVendorDeliveryLocationAndDate(vendorid, deliverylocationid, since, until, j, rows, orderby, true);
				totalReceptions.add(result);					
			}			
		}
		
		Object[] rArray = (Object[]) totalReceptions.toArray(new Object[totalReceptions.size()]);		
		ReceptionReportDataDTO[] receptions = (ReceptionReportDataDTO[]) ClassUtilities.UnsplitArrays(rArray, ReceptionReportDataDTO.class);
		
		// Obtener solo los ids de las facturas de las p�ginas solicitadas
		Long[] taxdocumentids = new Long[receptions.length];
		for (int i = 0 ; i < receptions.length; i++){
			taxdocumentids[i] = receptions[i].getTaxdocumentid();
		}
		
		return taxdocumentids;
	}
	
	public Long[] getReceptionIdsByVendorDeliveryLocationOrderNumberAndPages(Long vendorid, Long deliverylocationid, Long number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws OperationFailedException, AccessDeniedException {
		ReceptionReportDataDTO[] result;
		List<Object> totalReceptions = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				result = getReceptionReportByVendorDeliveryLocationAndOrderNumber(vendorid, deliverylocationid, number, j, rows, orderby, true);
				totalReceptions.add(result);					
			}			
		}
		
		Object[] rArray = (Object[]) totalReceptions.toArray(new Object[totalReceptions.size()]);		
		ReceptionReportDataDTO[] receptions = (ReceptionReportDataDTO[]) ClassUtilities.UnsplitArrays(rArray, ReceptionReportDataDTO.class);
		
		// Obtener solo los ids de las facturas de las p�ginas solicitadas
		Long[] taxdocumentids = new Long[receptions.length];
		for (int i = 0 ; i < receptions.length; i++){
			taxdocumentids[i] = receptions[i].getTaxdocumentid();
		}
		
		return taxdocumentids;
	}
	
	public Long[] getReceptionIdsByVendorDeliveryLocationItemInternalCodeAndPages(Long vendorid, Long deliverylocationid, String code, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws OperationFailedException, AccessDeniedException {
		ReceptionReportDataDTO[] result;
		List<Object> totalReceptions = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				result = getReceptionReportByVendorDeliveryLocationAndItemInternalCode(vendorid, deliverylocationid, code, j, rows, orderby, true);
				totalReceptions.add(result);					
			}			
		}
		
		Object[] rArray = (Object[]) totalReceptions.toArray(new Object[totalReceptions.size()]);		
		ReceptionReportDataDTO[] receptions = (ReceptionReportDataDTO[]) ClassUtilities.UnsplitArrays(rArray, ReceptionReportDataDTO.class);
		
		// Obtener solo los ids de las facturas de las p�ginas solicitadas
		Long[] taxdocumentids = new Long[receptions.length];
		for (int i = 0 ; i < receptions.length; i++){
			taxdocumentids[i] = receptions[i].getTaxdocumentid();
		}
		
		return taxdocumentids;
	}
	
	public Long[] getReceptionIdsByVendorDeliveryLocationNumberAndPages(Long vendorid, Long deliverylocationid, String number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws OperationFailedException, AccessDeniedException {
		ReceptionReportDataDTO[] result;
		List<Object> totalReceptions = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				result = getReceptionReportByVendorDeliveryLocationAndNumber(vendorid, deliverylocationid, number, j, rows, orderby, true);
				totalReceptions.add(result);					
			}			
		}
		
		Object[] rArray = (Object[]) totalReceptions.toArray(new Object[totalReceptions.size()]);		
		ReceptionReportDataDTO[] receptions = (ReceptionReportDataDTO[]) ClassUtilities.UnsplitArrays(rArray, ReceptionReportDataDTO.class);
		
		// Obtener solo los ids de las facturas de las p�ginas solicitadas
		Long[] taxdocumentids = new Long[receptions.length];
		for (int i = 0 ; i < receptions.length; i++){
			taxdocumentids[i] = receptions[i].getTaxdocumentid();
		}
		
		return taxdocumentids;
	}
	
	public Long[] getReceptionIdsByVendorDeliveryLocationTaxDocumentNumberAndPages(Long vendorid, Long deliverylocationid, Long number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws OperationFailedException, AccessDeniedException {
		ReceptionReportDataDTO[] result;
		List<Object> totalReceptions = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				result = getReceptionReportByVendorDeliveryLocationAndTaxDocumentNumber(vendorid, deliverylocationid, number, j, rows, orderby, true);
				totalReceptions.add(result);					
			}			
		}
		
		Object[] rArray = (Object[]) totalReceptions.toArray(new Object[totalReceptions.size()]);		
		ReceptionReportDataDTO[] receptions = (ReceptionReportDataDTO[]) ClassUtilities.UnsplitArrays(rArray, ReceptionReportDataDTO.class);
		
		// Obtener solo los ids de las facturas de las p�ginas solicitadas
		Long[] taxdocumentids = new Long[receptions.length];
		for (int i = 0 ; i < receptions.length; i++){
			taxdocumentids[i] = receptions[i].getTaxdocumentid();
		}
		
		return taxdocumentids;	
	}
	
	public Long[] getReceptionIdsByVendorDeliveryLocationTaxDocumentEmittedAndPages(Long vendorid, Long deliverylocationid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws OperationFailedException, AccessDeniedException {
		ReceptionReportDataDTO[] result;
		List<Object> totalReceptions = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				result = getReceptionReportByVendorDeliveryLocationAndTaxDocumentEmitted(vendorid, deliverylocationid, since, until, j, rows, orderby, true);
				totalReceptions.add(result);					
			}			
		}
		
		Object[] rArray = (Object[]) totalReceptions.toArray(new Object[totalReceptions.size()]);		
		ReceptionReportDataDTO[] receptions = (ReceptionReportDataDTO[]) ClassUtilities.UnsplitArrays(rArray, ReceptionReportDataDTO.class);
		
		// Obtener solo los ids de las facturas de las p�ginas solicitadas
		Long[] taxdocumentids = new Long[receptions.length];
		for (int i = 0 ; i < receptions.length; i++){
			taxdocumentids[i] = receptions[i].getTaxdocumentid();
		}
		
		return taxdocumentids;
	}
	
	public ReceptionDetailDTO[] getReceptionDetailByTaxDocument(Long taxdocumentid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException {

		String SQL = getReceptionDetailQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("taxdocumentid", taxdocumentid);
		query.setResultTransformer(new LowerCaseResultTransformer(ReceptionDetailDTO.class));
		
		if (ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		ReceptionDetailDTO[] result = (ReceptionDetailDTO[]) list.toArray(new ReceptionDetailDTO[list.size()]);		
		
		return result;
	}
	
	public ReceptionDetailTotalDTO countReceptionDetailByTaxDocument(Long taxdocumentid) throws AccessDeniedException {

		String SQL = getReceptionDetailQueryString(1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("taxdocumentid", taxdocumentid);
		query.setResultTransformer(new LowerCaseResultTransformer(ReceptionDetailTotalDTO.class));
		
		ReceptionDetailTotalDTO result = (ReceptionDetailTotalDTO)query.list().get(0);
		return result;
	}
	
	private String getReceptionDetailQueryString(int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		String SQL 					= "";
		String conditionGroupBy		= "";
		String conditionOrderBy		= "";	
		
		SQL =
			"WITH tdd AS (" + //
					"SELECT DISTINCT " + //
						"bd.order_id, " + //
						"bd.delivery_id, " + //
						"i.id AS item_id, " + //
						"i.internalcode AS iteminternalcode, " + //
						"i.name AS itemname, " + //
						"vi.vendoritemcode AS vendoritemcode, " + //
						"od.finalcost, " + //
						"bd.location_id " + //
					"FROM "+ //
						"logistica.taxdocument AS td JOIN " + //
						"logistica.bulkdetail AS bd ON bd.taxdocument_id = td.id JOIN " + //
						"logistica.orderdetail AS od ON od.order_id = bd.order_id AND od.item_id = bd.item_id JOIN " + //
						"logistica.item AS i ON i.id = od.item_id JOIN " + //
						"logistica.vendoritem AS vi ON vi.vendor_id = td.vendor_id AND vi.item_id = i.id " + //
					"WHERE " + //
						"td.id = :taxdocumentid) "; //

		if (queryType == 1) {
			SQL += 
				"SELECT " + //
					"COUNT(DISTINCT(tdd.item_id)) AS registers, " + //
					"SUM(odd.receivedunits) AS receivedunits, " + //
					"SUM(odd.receivedunits * tdd.finalcost) AS totalreceived "; //
		}
		else {
			SQL +=
				"SELECT " + //
					"tdd.item_id AS itemid, " + //
					"tdd.iteminternalcode AS iteminternalcode, " + //
					"tdd.itemname AS itemname, " + //
					"tdd.vendoritemcode AS vendoritemcode, " + //
					"SUM(odd.receivedunits) AS receivedunits, " + //
					"tdd.finalcost AS finalcost, " + //
					"SUM(odd.receivedunits * tdd.finalcost) AS totalreceived "; //
					
			conditionGroupBy =
				"GROUP BY " + //
					"tdd.item_id, tdd.iteminternalcode, tdd.itemname, tdd.vendoritemcode, tdd.finalcost "; //
			
			conditionOrderBy = getOrderByString(mapGetReceptionDetailSQL, orderby);
		}
		
		SQL += 
			"FROM " + //
				"tdd JOIN " + //
				"logistica.orderdeliverydetail AS odd ON odd.order_id = tdd.order_id AND odd.delivery_id = tdd.delivery_id AND " + //
														"odd.item_id = tdd.item_id AND odd.location_id = tdd.location_id " + //
			conditionGroupBy + //
			conditionOrderBy; //
		
		return SQL;
	}
	
	public ReceptionDetailExcelDTO[] getExcelReceptionDetailByTaxDocuments(Long taxdocumentid, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		
		String SQL =
			"WITH tdd AS (" + //
					"SELECT DISTINCT " + //
						"td.id AS taxdocument_id, " + //
						"td.number AS taxdocumentnumber, " + //
						"COALESCE(td.receptionnumber, '') AS receptionnumber, " + //
						"COALESCE(logistica.tostr(td.receptiondate), '') AS receptiondate, " + //
						"o.id AS order_id, " + //
						"o.number AS ordernumber, " + //
						"ot.id AS ordertype_id, " + //
						"ot.code AS ordertypecode, " + //
						"ot.name AS ordertypename, " + //
						"l.id AS deliverylocation_id, " + //
						"l.code AS deliverylocationcode, " + //
						"l.name AS deliverylocationname, " + //
						"bd.delivery_id, " + //
						"i.id AS item_id, " + //
						"i.internalcode AS iteminternalcode, " + //
						"i.name AS itemname, " + //
						"vi.vendoritemcode AS vendoritemcode, " + //
						"od.finalcost, " + //
						"bd.location_id " + //
					"FROM " + //
						"logistica.taxdocument AS td JOIN " + //
						"logistica.bulkdetail AS bd ON bd.taxdocument_id = td.id JOIN " + //
						"logistica.orderdetail AS od ON od.order_id = bd.order_id AND od.item_id = bd.item_id JOIN " + //
						"logistica.order AS o ON o.id = od.order_id JOIN " + //
						"logistica.ordertype AS ot ON ot.id = o.ordertype_id JOIN " + //
						"logistica.location AS l ON l.id = o.deliverylocation_id JOIN " + //
						"logistica.item AS i ON i.id = od.item_id JOIN " + //
						"logistica.vendoritem AS vi ON vi.vendor_id = td.vendor_id AND vi.item_id = i.id " + //		
					"WHERE " + //
						"td.id = :taxdocumentid) " + //
			"SELECT " + //
				"tdd.order_id AS orderid, " + //
				"tdd.ordernumber AS ordernumber, " + //
				"tdd.ordertype_id AS ordertypeid, " + //
				"tdd.ordertypecode AS ordertypecode, " + //
				"tdd.ordertypename AS ordertypename, " + //
				"tdd.deliverylocation_id AS deliverylocationid, " + //
				"tdd.deliverylocationcode AS deliverylocationcode, " + //
				"tdd.deliverylocationname AS deliverylocationname, " + //
				"tdd.taxdocument_id AS taxdocumentid, " + //
				"tdd.taxdocumentnumber AS taxdocumentnumber, " + //
				"tdd.receptionnumber AS receptionnumber, " + //
				"tdd.receptiondate AS receptiondate, " + //
				"tdd.item_id AS itemid, " + //
				"tdd.iteminternalcode AS iteminternalcode, " + //
				"tdd.itemname AS itemname, " + //
				"tdd.vendoritemcode AS vendoritemcode, " + //
				"SUM(odd.receivedunits) AS receivedunits, " + //
				"tdd.finalcost AS finalcost, " + //
				"SUM(odd.receivedunits * tdd.finalcost) AS totalreceived " + //
			"FROM " + //
				"tdd JOIN " + //
				"logistica.orderdeliverydetail AS odd ON odd.order_id = tdd.order_id AND odd.delivery_id = tdd.delivery_id AND " + //
														"odd.item_id = tdd.item_id AND odd.location_id = tdd.location_id " + //
			"GROUP BY " + //
				"tdd.order_id, tdd.ordernumber, tdd.ordertype_id, tdd.ordertypecode, tdd.ordertypename, tdd.deliverylocation_id, tdd.deliverylocationcode, " + //
				"tdd.deliverylocationname, tdd.taxdocument_id, tdd.taxdocumentnumber, tdd.receptionnumber, tdd.receptiondate, tdd.item_id, tdd.iteminternalcode, " + //
				"tdd.itemname, tdd.vendoritemcode, tdd.finalcost " + //
			getOrderByString(mapGetReceptionDetailSQL, orderby); //		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("taxdocumentid", taxdocumentid);								
		query.setResultTransformer(new LowerCaseResultTransformer(ReceptionDetailExcelDTO.class));
		
		List list = query.list();
		ReceptionDetailExcelDTO[] result = (ReceptionDetailExcelDTO[]) list.toArray(new ReceptionDetailExcelDTO[list.size()]);		
		
		return result;
	}
	
	public TaxDocumentReportDataDTO[] getTaxDocumentReportByDate(Date since, Date until, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
				
		String SQL = getTaxDocumentReportQueryString(2, orderby);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		query.setResultTransformer(new LowerCaseResultTransformer(TaxDocumentReportDataDTO.class));
		
		List list = query.list();
		TaxDocumentReportDataDTO[] result = (TaxDocumentReportDataDTO[]) list.toArray(new TaxDocumentReportDataDTO[list.size()]);		
		
		return result;
	}
	
	public int countTaxDocumentReportByDate(Date since, Date until) throws AccessDeniedException {
				
		String SQL = getTaxDocumentReportQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);					
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;
	}
	
	private String getTaxDocumentReportQueryString(int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		String SQL 					= "";
		String conditionOrderBy		= "";				
		
		if (queryType == 1) {
			SQL = 
				"SELECT " + //
					"COUNT(td.id) "; //
		}
		else {
			SQL =
				"SELECT " + //
					"td.id AS taxdocumentid, " + //
					"logistica.tostr(td.emitted) AS taxdocumentemitted, " + //
					"td.number AS taxdocumentnumber, " + //
					"td.validated AS taxdocumentvalidated, " + //
					"dt.id AS datingid, " + //
					"dt.number AS datingnumber, " + //
					"logistica.tostr(re.when1) AS datingdate, " + //
					"oc.id AS orderid, " + //
					"oc.number AS ordernumber, " + //
					"vd.id AS vendorid, " + //
					"vd.code AS vendorcode, " + //
					"vd.tradename AS vendortradename "; //
								
			conditionOrderBy = getOrderByString(mapGetTaxDocumentValidationSQL, orderby);
		}
		
		SQL +=
			"FROM " + //
				"logistica.taxdocument AS td JOIN " + //
				"logistica.bulkdetail AS bd ON bd.taxdocument_id = td.id JOIN " + //
				"logistica.dating AS dt ON dt.delivery_id = bd.delivery_id JOIN " + //
				"logistica.reserve AS re ON re.id = dt.id JOIN " + //
				"logistica.order AS oc ON oc.id = bd.order_id JOIN " + //   
				"logistica.vendor AS vd ON vd.id = oc.vendor_id " + //
			"WHERE " + //
				"re.when1 >= :since AND re.when1 < :until " + //
			conditionOrderBy; //
		
		return SQL;
	}
	
	public void deleteByIds(Long[] taxdocumentids) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.taxdocuments.entities.TaxDocument.deleteByIds");
		query.setParameterList("taxdocumentids", taxdocumentids);
		query.executeUpdate();
	}
	
	public TaxDocumentW getByOrderDeliveryAndNumber(Long orderid, Long deliveryid, Long taxdocumentnumber) throws NotFoundException, OperationFailedException {
		
		PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
		
		properties[0] = new PropertyInfoDTO("bd.order.id", "orderid", orderid);
		properties[1] = new PropertyInfoDTO("bd.delivery.id", "deliveryid", deliveryid);
		properties[2] = new PropertyInfoDTO("td.number", "taxdocumentnumber", taxdocumentnumber);
       
        StringBuilder sb = new StringBuilder("SELECT " + //
        									  	"td " + //
        									 "FROM " + //
        									 	"BulkDetail bd, " + //
        									 	"TaxDocument td " +	//
        									 "WHERE " + //
        									 	"td.id = bd.taxdocument.id AND td.number = :taxdocumentnumber AND " + //
        										"bd.orderdeliverydetail.orderdelivery.order.id = :orderid AND " + //
        										"bd.orderdeliverydetail.orderdelivery.delivery.id = :deliveryid");
        
        List list = getByQuery(sb.toString(), properties);
        TaxDocumentW result = list == null || list.size() == 0 ? null : (TaxDocumentW)list.get(0);
        return result;
	}
	
	public int deleteTaxDocumentsWithoutBulkDetailByDelivery(Long deliveryid, Long[] taxdocumentids) {
		String SQL =
			"DELETE " + //
			"FROM logistica.taxdocument " + //
			"WHERE " + //
				"id IN (:taxdocumentids) AND id NOT IN (" + //
													"SELECT DISTINCT " + //
														"taxdocument_id " + //
													"FROM " + //
														"logistica.bulkdetail " + //
													"WHERE " + //
														"delivery_id = :deliveryid)"; //
	
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("deliveryid", deliveryid);
		query.setParameterList("taxdocumentids", taxdocumentids);
		
		return query.executeUpdate();
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