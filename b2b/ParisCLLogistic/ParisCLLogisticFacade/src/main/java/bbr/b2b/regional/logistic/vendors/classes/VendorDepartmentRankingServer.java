package bbr.b2b.regional.logistic.vendors.classes;

import java.math.BigInteger;
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
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorDepartmentRankingW;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.vendors.entities.VendorDepartmentRanking;
import bbr.b2b.regional.logistic.vendors.entities.VendorDepartmentRankingId;
import bbr.b2b.regional.logistic.vendors.entities.VendorRanking;
import bbr.b2b.regional.logistic.vendors.entities.VendorRankingId;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorDepartmentRankingDetailReportDataDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorDepartmentRankingReportDataDTO;

@Stateless(name = "servers/vendors/VendorDepartmentRankingServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VendorDepartmentRankingServer extends BaseLogisticEJB3Server<VendorDepartmentRanking, VendorDepartmentRankingId, VendorDepartmentRankingW> implements VendorDepartmentRankingServerLocal {

	private Map<String, String> mapGetVendorDepartmentRankingsSQL = new HashMap<String, String>();
	{
		mapGetVendorDepartmentRankingsSQL.put("VENDORRUT", "VD.RUT");
		mapGetVendorDepartmentRankingsSQL.put("VENDORNAME", "VD.NAME");
		mapGetVendorDepartmentRankingsSQL.put("DEPARTMENTCODE", "DP.CODE");
		mapGetVendorDepartmentRankingsSQL.put("DEPARTMENTNAME", "DP.NAME");
		mapGetVendorDepartmentRankingsSQL.put("APPROVEDCOUNT", "VDR.APPROVEDCOUNT");
		mapGetVendorDepartmentRankingsSQL.put("NONASSISTANCECOUNT", "VDR.NONASSISTANCECOUNT");
		mapGetVendorDepartmentRankingsSQL.put("REJECTEDCOUNT", "VDR.REJECTEDCOUNT");
		mapGetVendorDepartmentRankingsSQL.put("RANKING", "RK.RANKING");
		mapGetVendorDepartmentRankingsSQL.put("VENDORCLASSIFICATION", "VC.NAME");
		
	}		
	
	private Map<String, String> mapGetOrderDeliveryEvaluationSQL = new HashMap<String, String>();
	{
		mapGetOrderDeliveryEvaluationSQL.put("DATINGNUMBER", "DT.NUMBER");
		mapGetOrderDeliveryEvaluationSQL.put("EVALUATIONTYPE", "ET.NAME");
		mapGetOrderDeliveryEvaluationSQL.put("ORDERNUMBER", "ODE.ORDER1");
		mapGetOrderDeliveryEvaluationSQL.put("DATINGDATE", "DATINGDATE");
		mapGetOrderDeliveryEvaluationSQL.put("DATINGMONTH", "DATINGMONTH");
		mapGetOrderDeliveryEvaluationSQL.put("TOTALAVAILABLEUNITS", "ODE.TOTALAVAILABLEUNITS");
		mapGetOrderDeliveryEvaluationSQL.put("TOTALRECEIVEDUNITS", "ODE.TOTALRECEIVEDUNITS");
		mapGetOrderDeliveryEvaluationSQL.put("DELIVERYSCORE", "ODE.DELIVERYSCORE");
		mapGetOrderDeliveryEvaluationSQL.put("LEVELSERVICEPERCENT", "ODE.LEVELSERVICEPERCENT");
		mapGetOrderDeliveryEvaluationSQL.put("QUALITYPERCENT", "ODE.QUALITYPERCENT");
		
	}		
	
	@EJB
	VendorRankingServerLocal vendorrankingserver;

	@EJB
	VendorServerLocal departmentserver;

	public VendorDepartmentRankingW addVendorDepartmentRanking(VendorDepartmentRankingW vendordepartmentranking) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorDepartmentRankingW) addIdentifiable(vendordepartmentranking);
	}
	public VendorDepartmentRankingW[] getVendorDepartmentRankings() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorDepartmentRankingW[]) getIdentifiables();
	}
	public VendorDepartmentRankingW updateVendorDepartmentRanking(VendorDepartmentRankingW vendordepartmentranking) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorDepartmentRankingW) updateIdentifiable(vendordepartmentranking);
	}

	public VendorDepartmentRankingReportDataDTO[] getVendorDepartmentRankingsByRankingId(Long rankingid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getVendorDepartmentRankingsQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("rankingid", rankingid);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorDepartmentRankingReportDataDTO.class));
		
		if(ispaginated){ // Si se quiere el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		
		VendorDepartmentRankingReportDataDTO[] result = (VendorDepartmentRankingReportDataDTO[]) list.toArray(new VendorDepartmentRankingReportDataDTO[list.size()]);		
		return result;	
	
	}
	
	public int getVendorDepartmentRankingsCountByRankingId(Long rankingid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getVendorDepartmentRankingsQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("rankingid", rankingid);
				
		int totalreg = ((BigInteger)query.uniqueResult()).intValue();
		
		return totalreg;
	}
	
	public VendorDepartmentRankingDetailReportDataDTO[] getOrderDeliveryEvaluationReportByVendorDepartmentRanking(Long vendorid, Long departmentid, Long rankingid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException{
	
		String SQL = getOrderDeliveryEvaluationQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("departmentid", departmentid);
		query.setLong("rankingid", rankingid);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorDepartmentRankingDetailReportDataDTO.class));
		
		if(ispaginated){ // Si se quiere el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		
		VendorDepartmentRankingDetailReportDataDTO[] result = (VendorDepartmentRankingDetailReportDataDTO[]) list.toArray(new VendorDepartmentRankingDetailReportDataDTO[list.size()]);		
		return result;			
	}
	
	public int getOrderDeliveryEvaluationCountByVendorDepartmentRanking(Long vendorid, Long departmentid, Long rankingid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getOrderDeliveryEvaluationQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("departmentid", departmentid);
		query.setLong("rankingid", rankingid);
				
		int totalreg = ((BigInteger)query.uniqueResult()).intValue();
		
		return totalreg;
	}
	
	@Override
	protected void copyRelationsEntityToWrapper(VendorDepartmentRanking entity, VendorDepartmentRankingW wrapper) {
		wrapper.setVendorid(entity.getVendorranking().getId() != null ? new Long(entity.getVendorranking().getId().getVendorid()) : new Long(0));
		wrapper.setRankingid(entity.getVendorranking().getId() != null ? new Long(entity.getVendorranking().getId().getRankingid()) : new Long(0));
		wrapper.setDepartmentid(entity.getDepartment() != null ? new Long(entity.getDepartment().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(VendorDepartmentRanking entity, VendorDepartmentRankingW wrapper) throws OperationFailedException, NotFoundException {
		if ((wrapper.getVendorid() != null && wrapper.getVendorid() > 0) && (wrapper.getRankingid() != null && wrapper.getRankingid() > 0)) {
			VendorRankingId key = new VendorRankingId();
			key.setVendorid(wrapper.getVendorid());
			key.setRankingid(wrapper.getRankingid());
			VendorRanking var = vendorrankingserver.getReferenceById(key);
			if (var != null) { 
				entity.setVendorranking(var);
			}
		}
		if (wrapper.getDepartmentid() != null && wrapper.getDepartmentid() > 0) { 
			Vendor department = departmentserver.getReferenceById(wrapper.getDepartmentid());
			if (department != null) { 
				entity.setDepartment(department);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "VendorDepartmentRanking";
	}
	@Override
	protected void setEntityname() {
		entityname = "VendorDepartmentRanking";
	}
	
	private String getVendorDepartmentRankingsQueryString(int queryType, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException{
		
		String SQL = "WITH RK AS(" +
						"SELECT " +
							"VDR.VENDOR_ID, " +
							"VDR.DEPARTMENT_ID, " +
							"VDR.RANKING_ID, " +
							"CASE " + 
								"WHEN VDR.TOTALAVAILABLEUNITS > 0 " + 
								"THEN VDR.SUMSCOREWEIGHED / VDR.TOTALAVAILABLEUNITS " +
								"ELSE 0 " + 
							"END AS RANKING " +
						"FROM " +
							"LOGISTICA.VENDORDEPARTMENTRANKING AS VDR)";

		String condicionOrderBy		= "";		

		if (queryType == 1){
			SQL = SQL +
					"SELECT " +
						"COUNT(*) ";			
		}
		else {
			SQL = SQL +
					"SELECT " +
						"VD.ID AS VENDORID, " +
						"VD.RUT AS VENDORRUT, " +
						"VD.NAME AS VENDORNAME, " +
						"DP.ID AS DEPARTMENTID, " +
						"DP.CODE AS DEPARTMENTCODE, " +
						"DP.NAME AS DEPARTMENTNAME, " +
						"VDR.APPROVEDCOUNT, " +
						"VDR.NONASSISTANCECOUNT, " +
						"VDR.REJECTEDCOUNT, " +
						"VDR.RANKING_ID AS RANKINGID, " +
						"RK.RANKING, " +
						"VC.NAME AS VENDORCLASSIFICATION ";
		
			condicionOrderBy = getVendorRankingByString(mapGetVendorDepartmentRankingsSQL, orderby);
		}

		String fromSQL = "FROM " +
							"LOGISTICA.VENDORDEPARTMENTRANKING AS VDR JOIN " +
							"LOGISTICA.VENDOR AS VD ON VD.ID = VDR.VENDOR_ID JOIN " +
							"LOGISTICA.DEPARTMENT AS DP ON DP.ID = VDR.DEPARTMENT_ID JOIN " +
							"RK ON RK.VENDOR_ID = VDR.VENDOR_ID AND RK.DEPARTMENT_ID = VDR.DEPARTMENT_ID AND RK.RANKING_ID = VDR.RANKING_ID LEFT JOIN " +
							"LOGISTICA.VENDORCLASSIFICATION AS VC ON (RK.RANKING/100) >= VC.MIN AND (RK.RANKING/100) < VC.MAX ";

		String whereSQL = "WHERE " +
							"VDR.RANKING_ID = :rankingid ";

		SQL = SQL + 
				fromSQL +
					whereSQL +
						condicionOrderBy;		

		return SQL;	
		
	}
	
	private String getOrderDeliveryEvaluationQueryString(int queryType, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException{
		
		String SQL 					= "";
		String condicionOrderBy		= "";	

		if (queryType == 1){
			SQL = "SELECT " +
					"COUNT(*) ";			
		}
		else {
			SQL = "SELECT " +
					"DT.NUMBER AS DATINGNUMBER, " +
					"ET.NAME AS EVALUATIONTYPE, " +
					"ODE.ORDER1 AS ORDERNUMBER, " +
					"LOGISTICA.TOSTR(ODE.DATINGDATE) AS DATINGDATE, " +
					"LOGISTICA.MONTH(ODE.DATINGDATE) AS DATINGMONTH, " +
					"ODE.TOTALAVAILABLEUNITS, " +
					"ODE.TOTALRECEIVEDUNITS, " +
					"ODE.DELIVERYSCORE, " +
					"ODE.LEVELSERVICEPERCENT, " +
					"ODE.QUALITYPERCENT ";

			condicionOrderBy = getVendorRankingByString(mapGetOrderDeliveryEvaluationSQL, orderby);		
		}

		String fromSQL = "FROM " +
							"LOGISTICA.OCDELEVALUATION ODE " +
							"JOIN LOGISTICA.DATING DT ON DT.DELIVERY_ID = ODE.DELIVERY " +
							"JOIN LOGISTICA.EVALUATIONTYPE ET ON ET.ID = ODE.EVALUATIONTYPE_ID " +
							"INNER JOIN LOGISTICA.VENDORDEPARTMENTRANKING VDR ON (VDR.RANKING_ID = ODE.RANKING_ID AND VDR.DEPARTMENT_ID = ODE.DEPARTMENT_ID AND VDR.VENDOR_ID = ODE.VENDOR_ID) ";

		String whereSQL = "WHERE " +
							"ODE.RANKING_ID = :rankingid AND ODE.DEPARTMENT_ID = :departmentid AND ODE.VENDOR_ID = :vendorid ";

		SQL = SQL + 
				fromSQL +
					whereSQL +
						condicionOrderBy;		

		return SQL;	

	}
		
	private String getVendorRankingByString(Map<String, String> mapQuery, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
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
