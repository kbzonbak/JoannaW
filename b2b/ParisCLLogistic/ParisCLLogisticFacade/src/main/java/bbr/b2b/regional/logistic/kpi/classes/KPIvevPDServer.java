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
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDComplianceDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDReportDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDSummaryDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDSummaryTotalDTO;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevPDW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevPD;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/kpi/KPIvevPDServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIvevPDServer extends LogisticElementServer<KPIvevPD, KPIvevPDW> implements KPIvevPDServerLocal {

	private Map<String, String> mapKPIDetailSQL = new HashMap<String, String>();
	{
		mapKPIDetailSQL.put("FCM", "fcm");
		mapKPIDetailSQL.put("VENDORRUT", "vendorrut");
		mapKPIDetailSQL.put("VENDORCODE", "vendorcode");
		mapKPIDetailSQL.put("VENDORSOCIALREASON", "vendorsocialreason");
		mapKPIDetailSQL.put("TOTALDELIVERED", "totaldelivered");
		mapKPIDetailSQL.put("TOTALRECEIVEDDELAYED", "totalreceiveddelayed");
		mapKPIDetailSQL.put("TOTALCOURIERRECEIVEDDELAYED", "totalcourierreceiveddelayed");
		mapKPIDetailSQL.put("TOTALPROVIDERDELAYED", "totalproviderdelayed");
		mapKPIDetailSQL.put("TOTALCOURIERDELAYED", "totalcourierdelayed");
		mapKPIDetailSQL.put("TOTALDELIVERING", "totaldelivering");
		mapKPIDetailSQL.put("TOTALLOSTS", "totallosts");
		mapKPIDetailSQL.put("CREDITNOTES", "creditnotes");
		mapKPIDetailSQL.put("INCONSISTENCIES", "inconsistencies");
		mapKPIDetailSQL.put("CUSTOMERRESPONSABILITIES", "customerresponsabilities");
		mapKPIDetailSQL.put("TOTAL", "total");
		mapKPIDetailSQL.put("META", "meta");
		mapKPIDetailSQL.put("COMPLIANCERATE", "compliancerate");
		mapKPIDetailSQL.put("VENDORDEFAULTRATE", "vendordefaultrate");
		mapKPIDetailSQL.put("COURIERDEFAULTRATE", "courierdefaultrate");	
	}
	
	private Map<String, String> mapKPISummarySQL = new HashMap<String, String>();
	{
		mapKPISummarySQL.put("MONTH", "month");
		mapKPISummarySQL.put("MONTHSTR", "monthstr");
		mapKPISummarySQL.put("YEAR", "year");
		mapKPISummarySQL.put("COMPLIANCERATE", "compliancerate");
		mapKPISummarySQL.put("VENDORDEFAULTRATE", "vendordefaultrate");
		mapKPISummarySQL.put("COURIERDEFAULTRATE", "courierdefaultrate");
	}
	
	@EJB
	DepartmentServerLocal departmentserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	LocationServerLocal locationserver;

	public KPIvevPDW addKPIvevPD(KPIvevPDW kpivevpd) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevPDW) addElement(kpivevpd);
	}
	public KPIvevPDW[] getKPIvevPDs() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevPDW[]) getIdentifiables();
	}
	public KPIvevPDW updateKPIvevPD(KPIvevPDW kpivevpd) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevPDW) updateElement(kpivevpd);
	}

	@Override
	protected void copyRelationsEntityToWrapper(KPIvevPD entity, KPIvevPDW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setDepartmentid(entity.getDepartment() != null ? new Long(entity.getDepartment().getId()) : new Long(0));
		wrapper.setSalestoreid(entity.getSalestore() != null ? new Long(entity.getSalestore().getId()) : new Long(0));

	}
	
	@Override
	protected void copyRelationsWrapperToEntity(KPIvevPD entity, KPIvevPDW wrapper) throws OperationFailedException, NotFoundException {
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
		entitylabel = "KPIvevPD";
	}
	
	protected void setEntityname() {
		entityname = "KPIvevPD";
	}	
	
	public Long[] getAllVendorIds(){
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.kpi.entities.KPIvevPD.getAllVendorIds");
		List list = query.list();
		Long[] result = new Long[list.size()];
		
		for(int i=0; i<list.size(); i++){
			result[i] =((BigInteger)list.get(i)).longValue();			 
		}
		
		return result;	
	}
	
	public Long[] getAllDepartmentIds(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.kpi.entities.KPIvevPD.getAllDepartmentIds");
		List list = query.list();
		Long[] result = new Long[list.size()];
		
		for(int i=0; i<list.size(); i++){
			result[i] =((BigInteger)list.get(i)).longValue();
		}
		return result;	
	}
	
	public void deleteByIds(List<Long> kpivevpdids){
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.kpi.entities.KPIvevPD.deleteByIds");
		query.setParameterList("kpivevpdids", kpivevpdids);
		query.executeUpdate();
	}
	
	public void deleteByVendorAndPeriod(Long vendorid, Date since, Date until, boolean courier) throws OperationFailedException{
		
		String SQL =
			"DELETE FROM logistica.kpivevpd AS k " + //
			"USING (" + //
				"SELECT " + //
					"k.id " + //
				"FROM " + //
					"logistica.kpivevpd AS k LEFT JOIN " + //
					"logistica.hiredcourier AS hc ON hc.vendor_id = k.vendor_id " + //
				"WHERE " + //
					"hc.vendor_id IS " + (courier ? " NOT " : "") + " NULL) AS a " + //
			"WHERE " + //
				"a.id = k.id AND k.fcm >= :since AND k.fcm <= :until "; //
		
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			SQL += "AND k.vendor_id = :vendorid "; //
		}
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		
		query.setDate("since", since);
		query.setDate("until", until);
		if(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		
		query.executeUpdate();		
	}
	
	public KPIvevPDReportDataDTO getInt1879KPIGeneralData(Long vendorid, Long departmentid, Long salestoreid, Date since, Date until) throws OperationFailedException, AccessDeniedException{
		
		String SQL =
			"SELECT " + //
				"SUM(totaldelivered) AS totaldelivered, " + //
				"SUM(totaldelivered + totalreceiveddelayed + totalcourierreceiveddelayed + totalproviderdelayed + totalcourierdelayed + " + //
																									"totallosts) AS totalevaluated " + //
			"FROM " + //
				"logistica.kpivevpd " + //
			"WHERE " + //
				"fcm >= :since AND fcm < :until AND vendor_id = :vendorid AND department_id = :departmentid AND salestore_id = :salestoreid"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		query.setLong("vendorid", vendorid);
		query.setLong("departmentid", departmentid);	
		query.setLong("salestoreid", salestoreid);	
				
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevPDReportDataDTO.class));	

		KPIvevPDReportDataDTO result = (KPIvevPDReportDataDTO) query.uniqueResult();
		return result;
	}
	
	public KPIvevPDReportDataDTO[] getKPIGeneralData(Float meta, Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, boolean courier, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean isPaginated) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition = "";
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.vendor_id = :vendorid "; //
		}
		
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.department_id IN (:departmentids) "; //	
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.salestore_id IN (:salestoreids) "; //	
		}
		
		String SQL = getDataQueryString(1, orderby, whereCondition, courier);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		query.setFloat("meta", meta);
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);	
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);	
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevPDReportDataDTO.class));	
		
		if (isPaginated) {	//Si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		KPIvevPDReportDataDTO[] result = (KPIvevPDReportDataDTO[])list.toArray(new KPIvevPDReportDataDTO[list.size()]);
		
		return result;
	}
	
	public KPIvevPDComplianceDTO countKPIGeneralData(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, boolean courier) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition = "";		
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.vendor_id = :vendorid "; //
		}
		
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.department_id IN (:departmentids) "; //	
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.salestore_id IN (:salestoreids) "; //	
		}
		
		String SQL = "";
		if (vendorid != null && vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			SQL = getDataQueryString(2, null, whereCondition, courier);		
		}
		else {
			SQL = getTotalQueryString(2, null, whereCondition, courier);
		}
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);	
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);	
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevPDComplianceDTO.class));
		
		KPIvevPDComplianceDTO result = (KPIvevPDComplianceDTO)query.uniqueResult();
		return result;		
	}
	
	public KPIvevPDReportDataDTO[] getKPIGeneralDataTotal(Float meta, Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, boolean courier, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean isPaginated) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition = "";
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.vendor_id = :vendorid "; //
		}
		
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.department_id IN (:departmentids) "; //
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.salestore_id IN (:salestoreids) "; //
		}
		
		String SQL = getTotalQueryString(1, orderby, whereCondition, courier);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		query.setFloat("meta", meta);
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);	
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);	
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevPDReportDataDTO.class));	
		
		if (isPaginated) {	//Si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		KPIvevPDReportDataDTO[] result = (KPIvevPDReportDataDTO[])list.toArray(new KPIvevPDReportDataDTO[list.size()]);
		
		return result;
	}
	
	private String getDataQueryString(int queryType, OrderCriteriaDTO[] orderCriteria, String whereCondition, boolean courier) throws AccessDeniedException{
		String SQL = "";
		
		if (queryType == 1) {
			SQL =
				"WITH a AS (" + //
					"SELECT " + //
						"kpi.fcm, " + //
						"ve.id AS vendorid, " +	//
						"ve.rut AS vendorrut, " +	//
						"ve.code AS vendorcode, " + //
						"ve.name AS vendorsocialreason, " + //
						"SUM(kpi.totaldelivered) AS totaldelivered, " + //
						"SUM(kpi.totalreceiveddelayed) AS totalreceiveddelayed, " + //
						"SUM(kpi.totalcourierreceiveddelayed) AS totalcourierreceiveddelayed, " + //
						"SUM(kpi.totalproviderdelayed) AS totalproviderdelayed, " + //
						"SUM(kpi.totalcourierdelayed) AS totalcourierdelayed, " + //
						"SUM(kpi.totaldelivering) AS totaldelivering, " + //
						"SUM(kpi.totallosts) AS totallosts, " + //
						"SUM(kpi.creditnotes) AS creditnotes, " + //
						"SUM(kpi.inconsistencies) AS inconsistencies, " + //
						"SUM(kpi.customerresponsabilities) AS customerresponsabilities, " + //
						"SUM(kpi.totalcourierreceiveddelayed + kpi.totalcourierdelayed + kpi.totallosts) AS totalcourierdeliveries, " + //
						"SUM(kpi.totaldelivered + kpi.totalreceiveddelayed + kpi.totalcourierreceiveddelayed + kpi.totalproviderdelayed + " + //
																		"kpi.totalcourierdelayed + kpi.totallosts) AS totalevaluated, " + //
						"SUM(kpi.totaldelivered + kpi.totalreceiveddelayed + kpi.totalcourierreceiveddelayed + kpi.totalproviderdelayed + " + //
								"kpi.totalcourierdelayed + kpi.totaldelivering + kpi.totallosts + kpi.creditnotes + kpi.inconsistencies + " + //
																									"kpi.customerresponsabilities) AS total " + //
					"FROM " + //
						"logistica.kpivevpd AS kpi JOIN " + //
						"logistica.vendor AS ve ON ve.id = kpi.vendor_id LEFT JOIN " + //
						"logistica.hiredcourier AS hc ON hc.vendor_id = ve.id " + //
					"WHERE " + //
						"kpi.fcm >= :since AND kpi.fcm < :until AND " + //
						"hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL " + //
						whereCondition + //
					"GROUP BY " + //
						"kpi.fcm, ve.id, ve.code, ve.name) " + //
				"SELECT " + //
					"to_char(fcm,'DD-MM-YYYY') AS fcm, " + //
					"vendorid, " +	//
					"vendorrut, " + //
					"vendorcode, " + //
					"vendorsocialreason, " + //
					"totaldelivered, " + //
					"totalreceiveddelayed, " + //
					"totalcourierreceiveddelayed, " + //
					"totalproviderdelayed, " + //
					"totalcourierdelayed, " + //
					"totaldelivering, " + //
					"totallosts, " + //
					"creditnotes, " + //
					"inconsistencies, " + //
					"customerresponsabilities, " + //
					"totalevaluated, " + //
					"total, " + //
					"CASE " + //
						"WHEN totalevaluated = 0 " + //
						"THEN 'No Cumple' " + //
						"WHEN (totaldelivered * 100 / CAST(totalevaluated AS FLOAT)) >= :meta THEN 'Cumple' " + //
						"WHEN (totaldelivered * 100 / CAST(totalevaluated AS FLOAT)) < :meta THEN 'No Cumple' " + //
						"ELSE 'No Cumple' " + //
						"END AS meta, " + //
					"CASE WHEN totalevaluated = 0 " + //
						"THEN 0.0 " + //
						"ELSE (totaldelivered * 100 / CAST(totalevaluated AS FLOAT)) " + //
						"END AS compliancerate, " + //
				(courier ?
					"CASE WHEN totalevaluated = 0 " + //
						"THEN 0.0 " + //
						"ELSE ((totalevaluated - totaldelivered - totalcourierdeliveries) * 100 / CAST(totalevaluated AS FLOAT)) " + //
						"END AS vendordefaultrate, " + //
					"CASE WHEN totalevaluated = 0 " + //
						"THEN 0.0 " + //
						"ELSE (totalcourierdeliveries * 100 / CAST(totalevaluated AS FLOAT)) " + //
						"END AS courierdefaultrate " //
						: //
					"CASE WHEN totalevaluated = 0 " + //
						"THEN 0.0 " + //
						"ELSE (100 - (totaldelivered * 100 / CAST(totalevaluated AS FLOAT))) " + //
						"END AS vendordefaultrate, " + //
					"0.0 AS courierdefaultrate " //
				) + //
				"FROM " + //
					"a " + //
				getOrderByString(mapKPIDetailSQL, orderCriteria); //
		}
		else {
			SQL =
				"WITH kpivevpd AS (" + //
					"SELECT " + //
						"kpi.fcm, " + //
						"kpi.vendor_id, " + //
						"kpi.totaldelivered, " + //
						"kpi.totalreceiveddelayed, " + //
						"kpi.totalcourierreceiveddelayed, " + //
						"kpi.totalproviderdelayed, " + //
						"kpi.totalcourierdelayed, " + //
						"kpi.totaldelivering, " + //
						"kpi.totallosts, " + //
						"kpi.creditnotes, " + //
						"kpi.inconsistencies, " + //
						"kpi.customerresponsabilities " + //
					"FROM " + //
						"logistica.kpivevpd AS kpi JOIN " + //
						"logistica.vendor AS ve ON ve.id = kpi.vendor_id LEFT JOIN " + //
						"logistica.hiredcourier AS hc ON hc.vendor_id = ve.id " + //
					"WHERE " + //
						"fcm >= :since AND fcm < :until AND " + //
						"hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL " + //
						whereCondition + //
					") " + //
				"SELECT " + //
					"COUNT(a.fcm) AS totalreg, " + //
					"b.totaldelivered, " + //
					"b.totalreceiveddelayed, " + //
					"b.totalcourierreceiveddelayed, " + //
					"b.totalproviderdelayed, " + //
					"b.totalcourierdelayed, " + //
					"b.totaldelivering, " + //
					"b.totallosts, " + //
					"b.creditnotes, " + //
					"b.inconsistencies, " + //
					"b.customerresponsabilities, " + //
					"b.totalcourierdeliveries, " + //
					"b.totalevaluated, " + //
					"b.total " + //
				"FROM " + //
					"(SELECT DISTINCT " + //
						"fcm, " + //
						"vendor_id " + //
					"FROM " + //
						"kpivevpd) AS a, " + //
					"(SELECT " + //
						"SUM(totaldelivered) AS totaldelivered, " + //
						"SUM(totalreceiveddelayed) AS totalreceiveddelayed, " + //
						"SUM(totalcourierreceiveddelayed) AS totalcourierreceiveddelayed, " + //
						"SUM(totalproviderdelayed) AS totalproviderdelayed, " + //
						"SUM(totalcourierdelayed) AS totalcourierdelayed, " + //
						"SUM(totaldelivering) AS totaldelivering, " + //
						"SUM(totallosts) AS totallosts, " + //
						"SUM(creditnotes) AS creditnotes, " + //
						"SUM(inconsistencies) AS inconsistencies, " + //
						"SUM(customerresponsabilities) AS customerresponsabilities, " + //
					(courier ? //
						"SUM(totalcourierreceiveddelayed + totalcourierdelayed + totallosts) AS totalcourierdeliveries, " + //
						"SUM(totaldelivered + totalreceiveddelayed + totalcourierreceiveddelayed + totalproviderdelayed + totalcourierdelayed + " + //
																												"totallosts) AS totalevaluated, " + //
						"SUM(totaldelivered + totalreceiveddelayed + totalcourierreceiveddelayed + totalproviderdelayed + totalcourierdelayed + " + //
											"totaldelivering + totallosts + creditnotes + inconsistencies + customerresponsabilities) AS total " //
							: //
						"CAST(0 AS BIGINT) AS totalcourierdeliveries, " + //
						"SUM(totaldelivered + totalreceiveddelayed + totalproviderdelayed + totallosts) AS totalevaluated, " + //
						"SUM(totaldelivered + totalreceiveddelayed + totalproviderdelayed + totaldelivering + totallosts + creditnotes + " + //
																						"inconsistencies + customerresponsabilities) AS total " //
					) + //
					"FROM " + //
						"kpivevpd) AS b " + //
				"GROUP BY " + //
					"b.totaldelivered, b.totalreceiveddelayed, b.totalcourierreceiveddelayed, b.totalproviderdelayed, b.totalcourierdelayed, " + //
					"b.totaldelivering, b.totallosts, b.creditnotes, b.inconsistencies, b.customerresponsabilities, b.totalcourierdeliveries, " + //
					"b.totalevaluated, b.total"; //
		}
					
		return SQL;
	}
	
	private String getTotalQueryString(int queryType, OrderCriteriaDTO[] orderCriteria, String whereCondition, boolean courier) throws AccessDeniedException{
		String SQL = "";
				
		if (queryType == 1) {
			SQL =
				"WITH a AS (" + //
					"SELECT " + //
						"ve.id AS vendorid, " + //
						"ve.rut AS vendorrut, " +	//
						"ve.code AS vendorcode, " + //
						"ve.name AS vendorsocialreason, " + //
						"SUM(kpi.totaldelivered) AS totaldelivered, " + //
						"SUM(kpi.totalreceiveddelayed) AS totalreceiveddelayed, " + //
						"SUM(kpi.totalcourierreceiveddelayed) AS totalcourierreceiveddelayed, " + //
						"SUM(kpi.totalproviderdelayed) AS totalproviderdelayed, " + //
						"SUM(kpi.totalcourierdelayed) AS totalcourierdelayed, " + //
						"SUM(kpi.totaldelivering) AS totaldelivering, " + //
						"SUM(kpi.totallosts) AS totallosts, " + //
						"SUM(kpi.creditnotes) AS creditnotes, " + //
						"SUM(kpi.inconsistencies) AS inconsistencies, " + //
						"SUM(kpi.customerresponsabilities) AS customerresponsabilities, " + //
						"SUM(kpi.totalcourierreceiveddelayed + kpi.totalcourierdelayed + kpi.totallosts) AS totalcourierdeliveries, " + //
						"SUM(kpi.totaldelivered + kpi.totalreceiveddelayed + kpi.totalcourierreceiveddelayed + kpi.totalproviderdelayed + " + //
																		"kpi.totalcourierdelayed + kpi.totallosts) AS totalevaluated, " + //
						"SUM(kpi.totaldelivered + kpi.totalreceiveddelayed + kpi.totalcourierreceiveddelayed + kpi.totalproviderdelayed + " + //
									"kpi.totalcourierdelayed + kpi.totaldelivering + kpi.totallosts + kpi.creditnotes + kpi.inconsistencies + " + //
																									"kpi.customerresponsabilities) AS total " + //
					"FROM " +
						"logistica.kpivevpd AS kpi JOIN " + //
						"logistica.vendor AS ve ON ve.id = kpi.vendor_id LEFT JOIN " + //
						"logistica.hiredcourier AS hc ON hc.vendor_id = ve.id " + //
					"WHERE " + //
						"kpi.fcm >= :since AND kpi.fcm < :until AND " + //
						"hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL " + //
						whereCondition + //
					"GROUP BY " + //
						"ve.id, ve.code, ve.name) " + //
				"SELECT " + //
					"vendorid, " + //
					"vendorrut, " + //
					"vendorcode, " + //
					"vendorsocialreason, " + //
					"totaldelivered, " + //
					"totalreceiveddelayed, " + //
					"totalcourierreceiveddelayed, " + //
					"totalproviderdelayed, " + //
					"totalcourierdelayed, " + //
					"totaldelivering, " + //
					"totallosts, " + //
					"creditnotes, " + //
					"inconsistencies, " + //
					"customerresponsabilities, " + //
					"totalevaluated, " + //
					"total, " + //
					"CASE " +
						"WHEN totalevaluated = 0 THEN 'No Cumple' " +  //
						"WHEN (totaldelivered * 100 / CAST(totalevaluated AS FLOAT)) >= :meta THEN 'Cumple' " + //
						"WHEN (totaldelivered * 100 / CAST(totalevaluated AS FLOAT)) < :meta THEN 'No Cumple' " + //
						"ELSE 'No Cumple' " + //
						"END AS meta, " + //
					"CASE WHEN totalevaluated = 0 " + //
						"THEN 0.0 " + //
						"ELSE (totaldelivered * 100 / CAST(totalevaluated AS FLOAT)) " + //" +
						"END AS compliancerate, " + //
				(courier ?
					"CASE WHEN totalevaluated = 0 " + //
						"THEN 0.0 " + //
						"ELSE ((totalevaluated - totaldelivered - totalcourierdeliveries) * 100 / CAST(totalevaluated AS FLOAT)) " + //
						"END AS vendordefaultrate, " + //
					"CASE WHEN totalevaluated = 0 " + //
						"THEN 0.0 " + //
						"ELSE (totalcourierdeliveries * 100 / CAST(totalevaluated AS FLOAT)) " + //
						"END AS courierdefaultrate " //
						: //
					"CASE WHEN totalevaluated = 0 " + //
						"THEN 0.0 " + //
						"ELSE (100 - (totaldelivered * 100 / CAST(totalevaluated AS FLOAT))) " + //
						"END AS vendordefaultrate, " + //
					"0.0 AS courierdefaultrate " //
				) + //
				"FROM " + //
					"a " + //
				getOrderByString(mapKPIDetailSQL, orderCriteria); //
		}
		else {
			SQL =
				"WITH kpivevpd AS (" + //
					"SELECT " + //
						"kpi.vendor_id, " + //
						"kpi.totaldelivered, " + //
						"kpi.totalreceiveddelayed, " + //
						"kpi.totalcourierreceiveddelayed, " + //
						"kpi.totalproviderdelayed, " + //
						"kpi.totalcourierdelayed, " + //
						"kpi.totaldelivering, " + //
						"kpi.totallosts, " + //
						"kpi.creditnotes, " + //
						"kpi.inconsistencies, " + //
						"kpi.customerresponsabilities " + //
					"FROM " + //
						"logistica.kpivevpd AS kpi JOIN " + //
						"logistica.vendor AS ve ON ve.id = kpi.vendor_id LEFT JOIN " + //
						"logistica.hiredcourier AS hc ON hc.vendor_id = ve.id " + //
					"WHERE " + //
						"kpi.fcm >= :since AND kpi.fcm < :until AND " + //
						"hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL " + //
						whereCondition + //
					") " + //
				"SELECT " + //
					"COUNT(a.vendor_id) AS totalreg, " + //
					"b.totaldelivered, " + //
					"b.totalreceiveddelayed, " + //
					"b.totalcourierreceiveddelayed, " + //
					"b.totalproviderdelayed, " + //
					"b.totalcourierdelayed, " + //
					"b.totaldelivering, " + //
					"b.totallosts, " + //
					"b.creditnotes, " + //
					"b.inconsistencies, " + //
					"b.customerresponsabilities, " + //
					"b.totalcourierdeliveries, " + //
					"b.totalevaluated, " + //
					"b.total " + //
				"FROM " + //
					"(SELECT DISTINCT " + //
						"vendor_id " + //
					"FROM " + //
						"kpivevpd) AS a, " + //
					"(SELECT " + //
						"SUM(totaldelivered) AS totaldelivered, " + //
						"SUM(totalreceiveddelayed) AS totalreceiveddelayed, " + //
						"SUM(totalcourierreceiveddelayed) AS totalcourierreceiveddelayed, " + //
						"SUM(totalproviderdelayed) AS totalproviderdelayed, " + //
						"SUM(totalcourierdelayed) AS totalcourierdelayed, " + //
						"SUM(totaldelivering) AS totaldelivering, " + //
						"SUM(totallosts) AS totallosts, " + //
						"SUM(creditnotes) AS creditnotes, " + //
						"SUM(inconsistencies) AS inconsistencies, " + //
						"SUM(customerresponsabilities) AS customerresponsabilities, " + //
					(courier ? //
						"SUM(totalcourierreceiveddelayed + totalcourierdelayed + totallosts) AS totalcourierdeliveries, " + //
						"SUM(totaldelivered + totalreceiveddelayed + totalcourierreceiveddelayed + totalproviderdelayed + totalcourierdelayed + " + //
																													"totallosts) AS totalevaluated, " + //
						"SUM(totaldelivered + totalreceiveddelayed + totalcourierreceiveddelayed + totalproviderdelayed + totalcourierdelayed + " + //
													"totaldelivering + totallosts + creditnotes + inconsistencies + customerresponsabilities) AS total " //
							: //
						"CAST(0 AS BIGINT) AS totalcourierdeliveries, " + //
						"SUM(totaldelivered + totalreceiveddelayed + totalproviderdelayed + totallosts) AS totalevaluated, " + //
						"SUM(totaldelivered + totalreceiveddelayed + totalproviderdelayed + totaldelivering + totallosts + creditnotes + inconsistencies + " + //
																											"customerresponsabilities) AS total " //
					) + //								
					"FROM " + //
						"kpivevpd) AS b " + //
				"GROUP BY " + //
					"b.totaldelivered, b.totalreceiveddelayed, b.totalcourierreceiveddelayed, b.totalproviderdelayed, b.totalcourierdelayed, " + //
					"b.totaldelivering, b.totallosts, b.creditnotes, b.inconsistencies, b.customerresponsabilities, b.totalcourierdeliveries, " + //
					"b.totalevaluated, b.total"; //
		}
			
		return SQL;
	}
	
	public KPIvevPDSummaryDTO[] getKPIvevPDSummary(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, boolean courier, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException {
		
		String whereCondition = "";
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.vendor_id = :vendorid ";
		}
		
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.department_id in (:departmentids) ";	
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.salestore_id in (:salestoreids) ";	
		}
		
		String SQL = getSummaryQueryString(1, orderby, whereCondition, courier);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);	
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);	
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevPDSummaryDTO.class));	
		
		List list = query.list();
		KPIvevPDSummaryDTO[] result = (KPIvevPDSummaryDTO[])list.toArray(new KPIvevPDSummaryDTO[list.size()]);
		
		return result;
	}
	
	public KPIvevPDSummaryTotalDTO countKPIvevPDSummary(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, boolean courier) throws OperationFailedException, AccessDeniedException {
		
		String whereCondition = "";
		if( vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.vendor_id = :vendorid ";
		}
		
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.department_id in (:departmentids) ";	
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpi.salestore_id in (:salestoreids) ";	
		}
		
		String SQL = getSummaryQueryString(2, null, whereCondition, courier);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);	
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);	
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevPDSummaryTotalDTO.class));	
		
		KPIvevPDSummaryTotalDTO result = (KPIvevPDSummaryTotalDTO)query.uniqueResult();		
		return result;
	}
	
	private String getSummaryQueryString(int queryType, OrderCriteriaDTO[] orderCriteria, String whereCondition, boolean courier) throws AccessDeniedException{
		String SQL = "";
				
		if (queryType == 1) {
			SQL =
				"WITH kpi AS (" + //
					"SELECT " + //
						"logistica.month(kpi.fcm) AS month, " + //
						"date_part('YEAR', kpi.fcm) AS year, " + //
						"date_trunc('MONTH', kpi.fcm) AS firstmonthday, " + //
						"date_trunc('MONTH', kpi.fcm) + interval '1 MONTH' - interval '1 DAY' AS lastmonthday, " + //
						"SUM(kpi.totaldelivered) AS totaldelivered, " + //
						"SUM(kpi.totalcourierreceiveddelayed + kpi.totalcourierdelayed + kpi.totallosts) AS totalcourierdeliveries, " + //
						"SUM(kpi.totaldelivered + kpi.totalreceiveddelayed + kpi.totalcourierreceiveddelayed + kpi.totalproviderdelayed + " + //
																			"kpi.totalcourierdelayed + kpi.totallosts) AS totalevaluated " + //
					"FROM " + //
						"logistica.kpivevpd AS kpi JOIN " + //
						"logistica.vendor AS ve ON ve.id = kpi.vendor_id LEFT JOIN " + //
						"logistica.hiredcourier AS hc ON hc.vendor_id = ve.id " + //
					"WHERE " + //
						"kpi.fcm >= :since AND kpi.fcm < :until AND " + //
						"hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL " + //
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
						 "ELSE (totaldelivered * 100 / CAST(totalevaluated AS FLOAT)) " + //
						 "END AS compliancerate, " + //
				(courier ?
					"CASE WHEN totalevaluated = 0 " + //
						"THEN 0.0 " + //
						"ELSE ((totalevaluated - totaldelivered - totalcourierdeliveries) * 100 / CAST(totalevaluated AS FLOAT)) " + //
						"END AS vendordefaultrate, " + //
					"CASE WHEN totalevaluated = 0 " + //
						"THEN 0.0 " + //
						"ELSE (totalcourierdeliveries * 100 / CAST(totalevaluated AS FLOAT)) " + //
						"END AS courierdefaultrate " //
						: //
					"CASE WHEN totalevaluated = 0 " + //
						"THEN 0.0 " + //
						"ELSE (100 - (totaldelivered * 100 / CAST(totalevaluated AS FLOAT))) " + //
						"END AS vendordefaultrate, " + //
					"0.0 AS courierdefaultrate " //
				) + //
				"FROM " + //
					"kpi " + //
				getOrderByString(mapKPISummarySQL, orderCriteria); //
		}
		else {
			SQL =
				"WITH kpi AS (" + //
					"SELECT " + //
						"COUNT(DISTINCT CAST(date_part('YEAR', kpi.fcm) AS VARCHAR) || CAST(date_part('MONTH', kpi.fcm) AS VARCHAR)) AS periods, " + //
						"SUM(kpi.totaldelivered) AS totaldelivered, " + //
						"SUM(kpi.totalcourierreceiveddelayed + kpi.totalcourierdelayed + kpi.totallosts) AS totalcourierdeliveries, " + //
						"SUM(kpi.totaldelivered + kpi.totalreceiveddelayed + kpi.totalcourierreceiveddelayed + kpi.totalproviderdelayed + " + //
																			"kpi.totalcourierdelayed + kpi.totallosts) AS totalevaluated " + //
					"FROM " + //
						"logistica.kpivevpd AS kpi JOIN " + //
						"logistica.vendor AS ve ON ve.id = kpi.vendor_id LEFT JOIN " + //
						"logistica.hiredcourier AS hc ON hc.vendor_id = ve.id " + //
					"WHERE " + //
						"kpi.fcm >= :since AND kpi.fcm < :until AND " + //
						"hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL " + //
						whereCondition + //
					") " + //
				"SELECT " + //
					"CAST(periods AS INTEGER) AS periods, " + //
					"CASE WHEN totalevaluated = 0 " + //
					 	 "THEN 0.0 " + //
					 	 "ELSE (totaldelivered * 100 / CAST(totalevaluated AS FLOAT)) " + //
					 	 "END AS compliancerate, " + //
				(courier ?
					"CASE WHEN totalevaluated = 0 " + //
						 "THEN 0.0 " + //
						 "ELSE ((totalevaluated - totaldelivered - totalcourierdeliveries) * 100 / CAST(totalevaluated AS FLOAT)) " + //
						 "END AS vendordefaultrate, " + //
					"CASE WHEN totalevaluated = 0 " + //
						 "THEN 0.0 " + //
						 "ELSE (totalcourierdeliveries * 100 / CAST(totalevaluated AS FLOAT)) " + //
						 "END AS courierdefaultrate " //
					: //
					"CASE WHEN totalevaluated = 0 " + //
						 "THEN 0.0 " + //
						 "ELSE (100 - (totaldelivered * 100 / CAST(totalevaluated AS FLOAT))) " + //
						 "END AS vendordefaultrate, " + //
					"0.0 AS courierdefaultrate " //
				) + //
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
