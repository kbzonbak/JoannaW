package bbr.b2b.regional.logistic.kpi.classes;

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
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDComplianceDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDReportDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDSummaryDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDSummaryTotalDTO;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevCDW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevCD;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/kpi/KPIvevCDServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIvevCDServer extends LogisticElementServer<KPIvevCD, KPIvevCDW> implements KPIvevCDServerLocal {

	private Map<String, String> mapKPIDetailSQL = new HashMap<String, String>();
	{
		mapKPIDetailSQL.put("FPI", "fpi");
		mapKPIDetailSQL.put("VENDORRUT", "ve.rut");
		mapKPIDetailSQL.put("VENDORCODE", "ve.code");
		mapKPIDetailSQL.put("VENDORSOCIALREASON", "ve.name");
		mapKPIDetailSQL.put("TOTALRECEIVEDCONFORMED", "a.totalreceivedconformed");
		mapKPIDetailSQL.put("TOTALRECEIVEDDELAYED", "a.totalreceiveddelayed");
		mapKPIDetailSQL.put("TOTALPROVIDERDELAYED", "a.totalproviderdelayed");
		mapKPIDetailSQL.put("CREDITNOTES", "a.creditnotes");
		mapKPIDetailSQL.put("TOTALEVALUATED", "a.totalevaluated");
		mapKPIDetailSQL.put("TOTAL", "a.total");
		mapKPIDetailSQL.put("META", "meta");
		mapKPIDetailSQL.put("COMPLIANCERATE", "compliancerate");
		mapKPIDetailSQL.put("DEFAULTRATE", "defaultrate");
	}
	
	private Map<String, String> mapKPISummarySQL = new HashMap<String, String>();
	{
		mapKPISummarySQL.put("MONTH", "month");
		mapKPISummarySQL.put("MONTHSTR", "monthstr");
		mapKPISummarySQL.put("YEAR", "year");
		mapKPISummarySQL.put("COMPLIANCERATE", "compliancerate");
		mapKPISummarySQL.put("DEFAULTRATE", "defaultrate");
	}
	
	@EJB
	DepartmentServerLocal departmentserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	LocationServerLocal locationserver;

	public KPIvevCDW addKPIvevCD(KPIvevCDW kpivevcd) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevCDW) addElement(kpivevcd);
	}
	public KPIvevCDW[] getKPIvevCDs() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevCDW[]) getIdentifiables();
	}
	public KPIvevCDW updateKPIvevCD(KPIvevCDW kpivevcd) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevCDW) updateElement(kpivevcd);
	}

	@Override
	protected void copyRelationsEntityToWrapper(KPIvevCD entity, KPIvevCDW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setDepartmentid(entity.getDepartment() != null ? new Long(entity.getDepartment().getId()) : new Long(0));
		wrapper.setSalestoreid(entity.getSalestore() != null ? new Long(entity.getSalestore().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(KPIvevCD entity, KPIvevCDW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDepartmentid() != null && wrapper.getDepartmentid() > 0) { 
			Department department = departmentserver.getReferenceById(wrapper.getDepartmentid());
			if (department != null) { 
				entity.setDepartment(department);
			}
		}
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getSalestoreid() != null && wrapper.getSalestoreid() > 0) { 
			Location salestore = locationserver.getReferenceById(wrapper.getSalestoreid());
			if (salestore != null) { 
				entity.setSalestore(salestore);
			}
		}
	}
	
	protected void setEntitylabel() {
		entitylabel = "KPIvevCD";
	}
	
	protected void setEntityname() {
		entityname = "KPIvevCD";
	}	
	
	public Long[] getAllVendorIds(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.kpi.entities.KPIvevCD.getAllVendorIds");
		List list = query.list();
		Long[] result = new Long[list.size()];
		// El valor en la query es un BigInteger
		// Debe convertirse a Long
		for(int i=0; i<list.size(); i++){
			result[i] =((BigInteger)list.get(i)).longValue();			 
		}
		return result;	
	}
	
	public Long[] getAllDepartmentIds(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.kpi.entities.KPIvevCD.getAllDepartmentIds");
		List list = query.list();
		Long[] result = new Long[list.size()];
		// El valor en la query es un BigInteger
		// Debe convertirse a Long
		for(int i=0; i<list.size(); i++){
			result[i] =((BigInteger)list.get(i)).longValue();
		}
		return result;	
	}
	
	public void deleteByIds(List<Long> kpivevcdids){
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.kpi.entities.KPIvevCD.deleteByIds");
		query.setParameterList("kpivevcdids", kpivevcdids);
		query.executeUpdate();
	}
	
	public void deleteByVendorAndPeriod(Long vendorid, Date since, Date until) throws OperationFailedException{
		
		String SQL =
			"DELETE " + //
			"FROM " + //
				"logistica.kpivevcd " + //
			"WHERE " + //
				"fpi >= :since AND fpi <= :until "; //
		
		if(vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			SQL += "AND vendor_id = :vendorid "; //
		}
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		
		query.setDate("since", since);
		query.setDate("until", until);
		if(vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		
		query.executeUpdate();		
	}

	public KPIvevCDReportDataDTO[] getKPIGeneralData(Float meta, Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean isPaginated) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition = "";
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND vendor_id = :vendorid ";
		}
		
		if (departmentids != null && departmentids.length > 0 && departmentids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND department_id in (:departmentids) ";	
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND salestore_id in (:salestoreids) ";	
		}
		
		String SQL = getDataQueryString(1, orderby, whereCondition);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		query.setFloat("meta", meta);
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);	
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);	
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevCDReportDataDTO.class));	
		
		if (isPaginated) {	//Si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		KPIvevCDReportDataDTO[] result = (KPIvevCDReportDataDTO[])list.toArray(new KPIvevCDReportDataDTO[list.size()]);
		
		return result;
	}
	
	public KPIvevCDComplianceDTO countKPIGeneralData(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition = "";
		if( vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND vendor_id = :vendorid ";
		}
		
		if (departmentids != null && departmentids.length > 0 && departmentids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND department_id in (:departmentids) ";	
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND salestore_id in (:salestoreids) ";	
		}
		
		String SQL = "";
		if (vendorid != null && vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			SQL = getDataQueryString(2, null, whereCondition);		
		}
		else {
			SQL = getTotalQueryString(2, null, whereCondition);
		}
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);	
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);	
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevCDComplianceDTO.class));	
		
		KPIvevCDComplianceDTO result = (KPIvevCDComplianceDTO)query.uniqueResult();		
		return result;
	}
	
	public KPIvevCDReportDataDTO[] getKPIGeneralDataTotal(Float meta, Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean isPaginated) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition = "";
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND vendor_id = :vendorid ";
		}
		
		if (departmentids != null && departmentids.length > 0 && departmentids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND department_id in (:departmentids) ";	
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND salestore_id in (:salestoreids) ";	
		}
		
		String SQL = getTotalQueryString(1, orderby, whereCondition);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		query.setFloat("meta", meta);
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);	
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);	
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevCDReportDataDTO.class));	
		
		if (isPaginated) {	//Si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		KPIvevCDReportDataDTO[] result = (KPIvevCDReportDataDTO[])list.toArray(new KPIvevCDReportDataDTO[list.size()]);
				
		return result;
	}
	
	private String getDataQueryString(int queryType, OrderCriteriaDTO[] orderCriteria, String whereCondition) throws AccessDeniedException{
		String SQL = "";
				
		if(queryType == 1){
			SQL =
				"WITH a AS (" + //
					"SELECT " + //
						"fpi, " + //
						"vendor_id, " + //
						"SUM(totalreceivedconformed) AS totalreceivedconformed, " + //
						"SUM(totalreceiveddelayed) AS totalreceiveddelayed, " + //
						"SUM(totalproviderdelayed) AS totalproviderdelayed, " + //
						"SUM(creditnotes) AS creditnotes, " + //
						"SUM(totalreceivedconformed + totalreceiveddelayed + totalproviderdelayed) AS totalevaluated, " + //
						"SUM(totalreceivedconformed + totalreceiveddelayed + totalproviderdelayed + creditnotes) AS total " + //
					"FROM " + //
						"logistica.kpivevcd " + //
					"WHERE " + //
						"fpi >= :since AND fpi < :until " + //
						whereCondition + //
					"GROUP BY " + //
						"fpi, vendor_id) " + //
				"SELECT " + //
					"TO_CHAR(a.fpi,'DD-MM-YYYY') AS fpi, " + //
					"ve.id AS vendorid, " + //
					"ve.rut AS vendorrut, " + //
					"ve.code AS vendorcode, " + //
					"ve.name AS vendorsocialreason, " + //		
					"a.totalreceivedconformed, " + //
					"a.totalreceiveddelayed, " + //
					"a.totalproviderdelayed, " + //
					"a.creditnotes, " + //
					"a.totalevaluated, " + //
					"a.total, " + //
					"CASE WHEN a.totalevaluated = 0 THEN 'No Cumple' " + //
						 "WHEN (a.totalreceivedconformed * 100 / CAST(a.totalevaluated AS FLOAT)) >= :meta THEN 'Cumple' " + //
						 "WHEN (a.totalreceivedconformed * 100 / CAST(a.totalevaluated AS FLOAT)) < :meta THEN 'No Cumple' " + //
						 "ELSE 'No Cumple' " + //
						 "END AS meta, " + //
					"CASE WHEN a.totalevaluated = 0 THEN 0.0 " + //
						 "ELSE (a.totalreceivedconformed * 100 / CAST(a.totalevaluated AS FLOAT)) " + //
						 "END AS compliancerate, " + //
					"CASE WHEN a.totalevaluated = 0 THEN 0.0 " + //
						 "ELSE ((a.totalevaluated - a.totalreceivedconformed) * 100 / CAST(a.totalevaluated AS FLOAT)) " + //
						 "END AS defaultrate " + //
				"FROM " + //
					"a JOIN " + //
					"logistica.vendor AS ve ON ve.id = a.vendor_id " + //
			getOrderByString(mapKPIDetailSQL, orderCriteria); //		
		}
		else {
			SQL =
				"WITH kpivevcd AS (" + //
					"SELECT " + //
						"fpi, " + //
						"vendor_id, " + //
						"totalreceivedconformed, " + //
						"totalreceiveddelayed, " + //
						"totalproviderdelayed, " + //
						"creditnotes " + //
					"FROM " + //
						"logistica.kpivevcd " + //
					"WHERE " + //
						"fpi >= :since AND fpi < :until " + //
						whereCondition + //
						") " + //
				"SELECT " + //
					"COUNT(a.fpi) AS totalreg, " + //
					"b.totalreceivedconformed, " + //
					"b.totalreceiveddelayed, " + //
					"b.totalproviderdelayed, " + //
					"b.creditnotes, " + //
					"b.totalevaluated, " + //
					"b.total " + //
				"FROM " + //
					"(SELECT DISTINCT " + //
						"fpi, " + //
						"vendor_id " + //
					"FROM " + //
						"kpivevcd) AS a, " + //
					"(SELECT " + //
						"SUM(totalreceivedconformed) AS totalreceivedconformed, " + //
						"SUM(totalreceiveddelayed) AS totalreceiveddelayed, " + //
						"SUM(totalproviderdelayed) AS totalproviderdelayed, " + //
						"SUM(creditnotes) AS creditnotes, " + //
						"SUM(totalreceivedconformed + totalreceiveddelayed + totalproviderdelayed) AS totalevaluated, " + //
						"SUM(totalreceivedconformed + totalreceiveddelayed + totalproviderdelayed + creditnotes) AS total " + //
					"FROM " + //
						"kpivevcd) AS b " + //
				"GROUP BY " + //
					"b.totalreceivedconformed, b.totalreceiveddelayed, b.totalproviderdelayed, b.creditnotes, b.totalevaluated, b.total"; //
		}
			
		return SQL;
	}
	
	private String getTotalQueryString(int queryType, OrderCriteriaDTO[] orderCriteria, String whereCondition) throws AccessDeniedException{
		String SQL = "";
		
		if (queryType == 1) {
			SQL =
				"WITH a AS (" + //
					"SELECT " + //
						"vendor_id, " + //
						"SUM(totalreceivedconformed) AS totalreceivedconformed, " + //
						"SUM(totalreceiveddelayed) AS totalreceiveddelayed, " + //
						"SUM(totalproviderdelayed) AS totalproviderdelayed, " + //
						"SUM(creditnotes) AS creditnotes, " + //
						"SUM(totalreceivedconformed + totalreceiveddelayed + totalproviderdelayed) AS totalevaluated, " + //
						"SUM(totalreceivedconformed + totalreceiveddelayed + totalproviderdelayed + creditnotes) AS total " + //
					"FROM " + //
						"logistica.kpivevcd " + //
					"WHERE " + //
						"fpi >= :since AND FPI < :until " + //
						whereCondition + //
					"GROUP BY " + //
						"vendor_id) " + //
				"SELECT " + //
					"ve.id AS vendorid, " + //
					"ve.rut AS vendorrut, " + //
					"ve.code AS vendorcode, " + //
					"ve.name AS vendorsocialreason, " + //
					"a.totalreceivedconformed, " + //
					"a.totalreceiveddelayed, " + //
					"a.totalproviderdelayed, " + //
					"a.creditnotes, " + //
					"a.totalevaluated, " + //
					"a.total, " + //
					"CASE WHEN a.totalevaluated = 0 THEN 'No Cumple' " + //
						 "WHEN (totalreceivedconformed * 100 / CAST(a.totalevaluated AS FLOAT)) >= :meta THEN 'Cumple' " + //
						 "WHEN (totalreceivedconformed * 100 / CAST(a.totalevaluated AS FLOAT)) < :meta THEN 'No Cumple' " + //
						 "ELSE 'No Cumple' " + //
						 "END AS meta, " + //
					"CASE WHEN a.totalevaluated = 0 THEN 0.0 " + //
						 "ELSE (totalreceivedconformed * 100 / CAST(a.totalevaluated AS FLOAT)) " + //
						 "END AS compliancerate, " + //
					"CASE WHEN a.totalevaluated = 0 THEN 0.0 " + //
						 "ELSE ((a.totalevaluated - totalreceivedconformed) * 100 / CAST(a.totalevaluated AS FLOAT)) " + //
						 "END AS defaultrate " + //
				"FROM " + //
					"a JOIN " + //
					"logistica.vendor AS ve ON ve.id = a.vendor_id " + //	
			getOrderByString(mapKPIDetailSQL, orderCriteria); //
		}
		else {
			SQL =
				"WITH kpivevcd AS (" + //
					"SELECT " + //
						"vendor_id, " + //
						"totalreceivedconformed, " + //
						"totalreceiveddelayed, " + //
						"totalproviderdelayed, " + //
						"creditnotes " + //
					"FROM " + //
						"logistica.kpivevcd " + //
					"WHERE " + //
						"fpi >= :since AND FPI < :until " + //
						whereCondition + //
					") " + //
				"SELECT " + //
					"COUNT(a.vendor_id) AS totalreg, " + //
					"b.totalreceivedconformed, " + //
					"b.totalreceiveddelayed, " + //
					"b.totalproviderdelayed, " + //
					"b.creditnotes, " + //
					"b.totalevaluated, " + //
					"b.total " + //
				"FROM " + //
					"(SELECT DISTINCT " + //
						"vendor_id " + //
					"FROM " + //
						"kpivevcd) AS a, " + //
					"(SELECT " + //
						"SUM(totalreceivedconformed) AS totalreceivedconformed, " + //
						"SUM(totalreceiveddelayed) AS totalreceiveddelayed, " + //
						"SUM(totalproviderdelayed) AS totalproviderdelayed, " + //
						"SUM(creditnotes) AS creditnotes, " + //
						"SUM(totalreceivedconformed + totalreceiveddelayed + totalproviderdelayed) AS totalevaluated, " + //
						"SUM(totalreceivedconformed + totalreceiveddelayed + totalproviderdelayed + creditnotes) AS total " + //
					"FROM " + //
						"kpivevcd) AS b " + //
				"GROUP BY " + //
					"b.totalreceivedconformed, b.totalreceiveddelayed, b.totalproviderdelayed, b.creditnotes, b.totalevaluated, b.total"; //
		}
					
		return SQL;
	}
	
	public KPIvevCDSummaryDTO[] getKPIvevCDSummary(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException {
		
		String whereCondition = "";
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND vendor_id = :vendorid ";
		}
		
		if (departmentids != null && departmentids.length > 0 && departmentids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND department_id in (:departmentids) ";	
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND salestore_id in (:salestoreids) ";	
		}
		
		String SQL = getSummaryQueryString(1, orderby, whereCondition);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);	
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);	
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevCDSummaryDTO.class));	
		
		List list = query.list();
		KPIvevCDSummaryDTO[] result = (KPIvevCDSummaryDTO[])list.toArray(new KPIvevCDSummaryDTO[list.size()]);
		
		return result;
	}
	
	public KPIvevCDSummaryTotalDTO countKPIvevCDSummary(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until) throws OperationFailedException, AccessDeniedException {
		
		String whereCondition = "";
		if( vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND vendor_id = :vendorid ";
		}
		
		if (departmentids != null && departmentids.length > 0 && departmentids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND department_id in (:departmentids) ";	
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND salestore_id in (:salestoreids) ";	
		}
		
		String SQL = getSummaryQueryString(2, null, whereCondition);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);	
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0].longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);	
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevCDSummaryTotalDTO.class));	
		
		KPIvevCDSummaryTotalDTO result = (KPIvevCDSummaryTotalDTO)query.uniqueResult();		
		return result;
	}
	
	private String getSummaryQueryString(int queryType, OrderCriteriaDTO[] orderCriteria, String whereCondition) throws AccessDeniedException{
		String SQL = "";
				
		if (queryType == 1) {
			SQL =
				"WITH kpi AS (" + //
					"SELECT " + //
						"logistica.month(fpi) AS month, " + //
						"date_part('YEAR', fpi) AS year, " + //
						"date_trunc('MONTH', fpi) AS firstmonthday, " + //
						"date_trunc('MONTH', fpi) + interval '1 MONTH' - interval '1 DAY' AS lastmonthday, " + //
						"SUM(totalreceivedconformed) AS totalreceivedconformed, " + //
						"SUM(totalreceivedconformed + totalreceiveddelayed + totalproviderdelayed) AS totalevaluated, " + //
						"SUM(totalreceivedconformed + totalreceiveddelayed + totalproviderdelayed + creditnotes) AS total " + //
					"FROM " + //
						"logistica.kpivevcd " + //
					"WHERE " + //
						"fpi >= :since AND fpi < :until " + //
						whereCondition + //
					"GROUP BY " + //
						"month, year, firstmonthday, lastmonthday) " + //
				"SELECT " + //
					"month, " + //
					"CAST(year AS INTEGER) AS year, " + //
					"CASE WHEN firstmonthday > :since " + //
						 "THEN logistica.tostr(firstmonthday) " + //
						 "ELSE logistica.tostr(:since) " + //
						 "END AS since, " + //
					"CASE WHEN lastmonthday < :until " + //
						 "THEN logistica.tostr(lastmonthday) " + //
						 "ELSE logistica.tostr(date(:until) - interval '1 DAY') " + //
						 "END AS until, " + //
					"CASE WHEN totalevaluated = 0 " + //
						 "THEN 0.0 " + //
						 "ELSE (totalreceivedconformed * 100 / CAST(totalevaluated AS FLOAT)) " + //
						 "END AS compliancerate, " + //
					"CASE WHEN totalevaluated = 0 " + //
						 "THEN 0.0 " + //
						 "ELSE ((total - totalreceivedconformed) * 100 / CAST(totalevaluated AS FLOAT)) " + //
						 "END AS defaultrate " + //
				"FROM " + //
					"kpi " + //
				getOrderByString(mapKPISummarySQL, orderCriteria); //		
		}
		else {
			SQL =
				"WITH kpi AS (" + //
					"SELECT " + //
						"COUNT(DISTINCT CAST(date_part('YEAR', fpi) AS VARCHAR) || CAST(date_part('MONTH', fpi) AS VARCHAR)) AS periods, " + //
						"SUM(totalreceivedconformed) AS totalreceivedconformed, " + //
						"SUM(totalreceivedconformed + totalreceiveddelayed + totalproviderdelayed) AS totalevaluated, " + //
						"SUM(totalreceivedconformed + totalreceiveddelayed + totalproviderdelayed + creditnotes) AS total " + //
					"FROM " + //
						"logistica.kpivevcd " + //
					"WHERE " + //
						"fpi >= :since AND fpi < :until " + //
						whereCondition + //
					") " + //
				"SELECT " + //
					"CAST(periods AS INTEGER) AS periods, " + //
					"CASE WHEN totalevaluated = 0 THEN 0.0 " + //
					 	 "ELSE (totalreceivedconformed * 100 / CAST(totalevaluated AS FLOAT)) " + //
					 	 "END AS compliancerate, " + //
					"CASE WHEN totalevaluated = 0 THEN 0.0 " + //
					 	 "ELSE ((total - totalreceivedconformed) * 100 / CAST(totalevaluated AS FLOAT)) " + //
					 	 "END AS defaultrate " + //
				"FROM " + //
					"kpi "; //
		}
			
		return SQL;
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
