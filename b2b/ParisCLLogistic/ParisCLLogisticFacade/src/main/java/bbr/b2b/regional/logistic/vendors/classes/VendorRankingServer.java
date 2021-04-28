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
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorRankingW;
import bbr.b2b.regional.logistic.vendors.entities.Ranking;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.vendors.entities.VendorRanking;
import bbr.b2b.regional.logistic.vendors.entities.VendorRankingId;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorRankingDetailReportDataDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorRankingReportDataDTO;

@Stateless(name = "servers/vendors/VendorRankingServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VendorRankingServer extends BaseLogisticEJB3Server<VendorRanking, VendorRankingId, VendorRankingW> implements VendorRankingServerLocal {

	private Map<String, String> mapGetVendorRankingsSQL = new HashMap<String, String>();
	{
		mapGetVendorRankingsSQL.put("VENDORRUT", "VD.RUT");
		mapGetVendorRankingsSQL.put("VENDORNAME", "VD.NAME");
		mapGetVendorRankingsSQL.put("APPROVEDCOUNT", "VR.APPROVEDCOUNT");
		mapGetVendorRankingsSQL.put("NONASSISTANCECOUNT", "VR.NONASSISTANCECOUNT");
		mapGetVendorRankingsSQL.put("REJECTEDCOUNT", "VR.REJECTEDCOUNT");
		mapGetVendorRankingsSQL.put("RANKING", "RK.RANKING");
		mapGetVendorRankingsSQL.put("VENDORCLASSIFICATION", "VC.NAME");
		
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
	VendorServerLocal vendorserver;

	@EJB
	RankingServerLocal rankingserver;

	public VendorRankingW addVendorRanking(VendorRankingW vendorranking) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorRankingW) addIdentifiable(vendorranking);
	}
	public VendorRankingW[] getVendorRankings() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorRankingW[]) getIdentifiables();
	}
	public VendorRankingW updateVendorRanking(VendorRankingW vendorranking) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorRankingW) updateIdentifiable(vendorranking);
	}
	
	public VendorRankingReportDataDTO[] getVendorRankingsByRankingId(Long rankingid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getVendorRankingsQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("rankingid", rankingid);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorRankingReportDataDTO.class));
		
		if(ispaginated){ // Si se quiere el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		
		VendorRankingReportDataDTO[] result = (VendorRankingReportDataDTO[]) list.toArray(new VendorRankingReportDataDTO[list.size()]);		
		return result;	
		
	}
	
	public int getVendorRankingsCountByRankingId(Long rankingid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getVendorRankingsQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("rankingid", rankingid);
				
		int totalreg = ((BigInteger)query.uniqueResult()).intValue();
		
		return totalreg;
	}
	
	public VendorRankingDetailReportDataDTO[] getOrderDeliveryEvaluationReportByVendorRanking(Long vendorid, Long rankingid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getOrderDeliveryEvaluationQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("rankingid", rankingid);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorRankingDetailReportDataDTO.class));
		
		if(ispaginated){ // Si se quiere el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
				
		List list = query.list();
		
		VendorRankingDetailReportDataDTO[] result = (VendorRankingDetailReportDataDTO[]) list.toArray(new VendorRankingDetailReportDataDTO[list.size()]);		
		return result;	
	}
	
	public int getOrderDeliveryEvaluationCountByVendorRanking(Long vendorid, Long rankingid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getOrderDeliveryEvaluationQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("rankingid", rankingid);
				
		int totalreg = ((BigInteger)query.uniqueResult()).intValue();
		
		return totalreg;
	}

	@Override
	protected void copyRelationsEntityToWrapper(VendorRanking entity, VendorRankingW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setRankingid(entity.getRanking() != null ? new Long(entity.getRanking().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(VendorRanking entity, VendorRankingW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getRankingid() != null && wrapper.getRankingid() > 0) { 
			Ranking ranking = rankingserver.getReferenceById(wrapper.getRankingid());
			if (ranking != null) { 
				entity.setRanking(ranking);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "VendorRanking";
	}
	@Override
	protected void setEntityname() {
		entityname = "VendorRanking";
	}
	
	private String getVendorRankingsQueryString(int queryType, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException{
		
		String SQL = "WITH RK AS(" +
						"SELECT " +
							"VR.VENDOR_ID, " +
							"VR.RANKING_ID, " +
							"CASE " + 
								"WHEN VR.TOTALAVAILABLEUNITS > 0 " + 
								"THEN VR.SUMSCOREWEIGHED / VR.TOTALAVAILABLEUNITS " +
								"ELSE 0 " + 
							"END AS RANKING " +
						"FROM " +
							"LOGISTICA.VENDORRANKING AS VR) ";
		
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
					"VR.APPROVEDCOUNT, " +
					"VR.NONASSISTANCECOUNT, " +
					"VR.REJECTEDCOUNT, " +
					"VR.RANKING_ID AS RANKINGID, " +
					"RK.RANKING, " +
					"VC.NAME AS VENDORCLASSIFICATION ";
						
			condicionOrderBy = getVendorRankingByString(mapGetVendorRankingsSQL, orderby);			
		}
		
		String fromSQL = "FROM " +
							"LOGISTICA.VENDORRANKING AS VR JOIN " +
							"LOGISTICA.VENDOR AS VD ON VD.ID = VR.VENDOR_ID JOIN " +
							"RK ON RK.VENDOR_ID = VR.VENDOR_ID AND RK.RANKING_ID = VR.RANKING_ID LEFT JOIN " +
							"LOGISTICA.VENDORCLASSIFICATION AS VC ON (RK.RANKING/100) >= VC.MIN AND (RK.RANKING/100) < VC.MAX ";
		
		String whereSQL = "WHERE " +
							"VR.RANKING_ID = :rankingid ";
		
		SQL = SQL + 
				fromSQL +
					whereSQL +
						condicionOrderBy;		
		
		return SQL;	
			
	}
	
	private String getOrderDeliveryEvaluationQueryString(int queryType, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException{
		
		String SQL = "";
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
							"INNER JOIN LOGISTICA.VENDORRANKING VR ON (VR.RANKING_ID = ODE.RANKING_ID AND VR.VENDOR_ID = ODE.VENDOR_ID) ";

		String whereSQL = "WHERE " +
							"ODE.RANKING_ID = :rankingid AND ODE.VENDOR_ID = :vendorid ";

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
