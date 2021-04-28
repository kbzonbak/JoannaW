package bbr.b2b.regional.logistic.vendors.classes;

import java.math.BigInteger;
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
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.vendors.data.wrappers.PropertyVendorW;
import bbr.b2b.regional.logistic.vendors.entities.PropertyVendor;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.vendors.report.classes.InvoiceVendorRollOutDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationSummaryDTO;

@Stateless(name = "servers/vendors/PropertyVendorServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PropertyVendorServer extends LogisticElementServer<PropertyVendor, PropertyVendorW> implements PropertyVendorServerLocal {

	private Map<String, String> mapGetInvoiceVendorRollOutSQL = new HashMap<String, String>();
	{
		mapGetInvoiceVendorRollOutSQL.put("VENDORCODE", "vd.code");
		mapGetInvoiceVendorRollOutSQL.put("VENDORNAME", "vd.name");
		mapGetInvoiceVendorRollOutSQL.put("INVOICESINCE", "invoicesince");
		mapGetInvoiceVendorRollOutSQL.put("INVOICEMAXPREVIOUSDAYS", "invoicemaxpreviousdays");
		mapGetInvoiceVendorRollOutSQL.put("LASTMODIFIED", "lastmodified");
		mapGetInvoiceVendorRollOutSQL.put("MODIFIER", "ispv.modifier");
	}
	
	private Map<String, String> mapGetVendorWithoutInvoiceValidationSQL = new HashMap<String, String>();
	{
		mapGetVendorWithoutInvoiceValidationSQL.put("CODE", "vd.code");
		mapGetVendorWithoutInvoiceValidationSQL.put("NAME", "vd.name");
	}
	
	@EJB
	VendorServerLocal vendorserver;
	
	@Override
	protected void copyRelationsEntityToWrapper(PropertyVendor entity, PropertyVendorW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(PropertyVendor entity, PropertyVendorW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "PropertyVendor";
	}
	@Override
	protected void setEntityname() {
		entityname = "PropertyVendor";
	}
	
	public PropertyVendorW addPropertyVendor(PropertyVendorW propertyvendor) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PropertyVendorW) addElement(propertyvendor);
	}
	
	public PropertyVendorW[] getPropertyVendors() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PropertyVendorW[]) getIdentifiables();
	}
	
	public PropertyVendorW updatePropertyVendor(PropertyVendorW propertyvendor) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (PropertyVendorW) updateElement(propertyvendor);
	}
	
	public InvoiceVendorRollOutDTO[] getInvoiceVendorRollOutReport(OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		String SQL = getInvoiceVendorRollOutReportQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(InvoiceVendorRollOutDTO.class));
		
		List list = query.list();
		InvoiceVendorRollOutDTO[] result = (InvoiceVendorRollOutDTO[]) list.toArray(new InvoiceVendorRollOutDTO[list.size()]);		
		
		return result;
	}
	
	public 	int countInvoiceVendorRollOutReport() throws AccessDeniedException {
		String SQL = getInvoiceVendorRollOutReportQueryString(1, null);		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
						
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;
	}
	
	private String getInvoiceVendorRollOutReportQueryString(int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		String SQL 					= "";
		String conditionOrderBy		= "";		
		
		if (queryType == 1) {
			SQL = 
				"SELECT " + //
					"COUNT(ispv.id) "; //
		}
		else {
			SQL =
				"SELECT " + //
					"vd.id AS vendorid, " + //
					"vd.code AS vendorcode, " + //
					"vd.name AS vendorname, " + //
					"ispv.id AS invoicesincepropid, " + //
					"TO_TIMESTAMP(SUBSTRING (ispv.value, 0, 19), 'YYYY-MM-DD HH24:MI:SS') AS invoicesince, " + //
					"ipdpv.id AS invoicemaxpreviousdayspropid, " + //
					"CAST(ipdpv.value AS INTEGER) AS invoicemaxpreviousdays, " + //
					"ispv.lastmodified AS lastmodified, " + //
					"ispv.modifier AS modifier "; //
						
			conditionOrderBy = getOrderByString(mapGetInvoiceVendorRollOutSQL, orderby);				
		}
		
		SQL += 
			"FROM " + //
				"logistica.vendor AS vd JOIN " + //
				"logistica.propertyvendor AS ispv ON ispv.vendor_id = vd.id JOIN " + //
				"logistica.propertyvendor AS ipdpv ON ipdpv.vendor_id = vd.id " + //
			"WHERE " + //
				"ispv.code = 'INICIO_FACT_VEVPD' AND ipdpv.code = 'DIAS_FACT_VEVPD' " + //
			conditionOrderBy; //
		
		return SQL;		
	}
	
	public int deleteByIds(Long[] propertyvendorids) {
		
		String SQL =
				"DELETE " + //
				"FROM " + //
					"logistica.propertyvendor " + //
				"WHERE " + //
					"id IN (:propertyvendorids)"; //
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("propertyvendorids", propertyvendorids);
						
		int total = query.executeUpdate();
		return total;
	}
	
	public int deleteByVendorsAndCodes(Long[] vendorids, String[] codes) {
		
		String SQL =
				"DELETE " + //
				"FROM " + //
					"logistica.propertyvendor " + //
				"WHERE " + //
					"vendor_id IN (:vendorids) AND code IN (:codes)"; //
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("vendorids", vendorids);
		query.setParameterList("codes", codes);
						
		int total = query.executeUpdate();
		return total;	
	}
	
	public VendorWithoutInvoiceValidationDTO[] getVendorWithoutInvoiceValidationReport(OrderCriteriaDTO[] orderby) throws AccessDeniedException {		
		String SQL = getVendorWithoutInvoiceValidationReportQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorWithoutInvoiceValidationDTO.class));
		
		List list = query.list();
		VendorWithoutInvoiceValidationDTO[] result = (VendorWithoutInvoiceValidationDTO[]) list.toArray(new VendorWithoutInvoiceValidationDTO[list.size()]);		
		
		return result;
	}
	
	public VendorWithoutInvoiceValidationSummaryDTO countVendorWithoutInvoiceValidationReport() throws AccessDeniedException {
		String SQL = getVendorWithoutInvoiceValidationReportQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorWithoutInvoiceValidationSummaryDTO.class));
		
		VendorWithoutInvoiceValidationSummaryDTO result = (VendorWithoutInvoiceValidationSummaryDTO)query.uniqueResult();
		
		return result;
	}
	
	private String getVendorWithoutInvoiceValidationReportQueryString(int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		String SQL 					= "";
		String conditionOrderBy		= "";
		String conditionGroupBy		= "";
		
		if (queryType == 1) {
			SQL = 
				"SELECT " + //
					"CAST(COUNT(pv.id) AS INTEGER) AS totalreg, " + //
					"pv.modifier AS modifier, " + //
					"logistica.tostr(pv.lastmodified) AS lastmodified "; //
			
			conditionGroupBy =
				"GROUP BY " + //
					"pv.modifier, pv.lastmodified "; //
		}
		else {
			SQL =
				"SELECT " + //
					"vd.id AS id, " + //
					"vd.code AS code, " + //
					"vd.name AS name "; //
						
			conditionOrderBy = getOrderByString(mapGetVendorWithoutInvoiceValidationSQL, orderby);				
		}
		
		SQL += 
			"FROM " + //
				"logistica.vendor AS vd JOIN " + //
				"logistica.propertyvendor AS pv ON pv.vendor_id = vd.id " + //
			"WHERE " + //
				"pv.code = 'NO_VALIDA_FACTPL' " + //
			conditionGroupBy + //
			conditionOrderBy; //
		
		return SQL;		
	}
	
	public int addVendorsWithoutInvoiceValidation(List<Long> vendorids, String username, Date now) {
		String SQL =
				"INSERT INTO logistica.propertyvendor (code, description, value, created, creator, lastmodified, modifier, vendor_id) " + //
				"SELECT " + //
					"'NO_VALIDA_FACTPL', " + //
					"'No se exige validación de sus facturas contra Paperless en la carga de PL', " + //
					"'', " + //
					":now, " + //
					":username, " + //
					":now, " + //
					":username, " + //
					"id " + //
				"FROM " + //
					"logistica.vendor " + //
				"WHERE " + //
					"id IN (:vendorids)";
				
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("vendorids", vendorids);
		query.setString("username", username);
		query.setTimestamp("now", now);
		
		int total = query.executeUpdate();
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

}