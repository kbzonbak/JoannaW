package bbr.b2b.regional.logistic.evaluations.classes;

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
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.EvaluationDetailW;
import bbr.b2b.regional.logistic.evaluations.entities.EvaluationDetail;
import bbr.b2b.regional.logistic.evaluations.entities.EvaluationDetailId;
import bbr.b2b.regional.logistic.evaluations.entities.ReceptionError;
import bbr.b2b.regional.logistic.evaluations.entities.ReceptionEvaluation;
import bbr.b2b.regional.logistic.evaluations.report.classes.EvaluationDetailReportDataDTO;

@Stateless(name = "servers/evaluations/EvaluationDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EvaluationDetailServer extends BaseLogisticEJB3Server<EvaluationDetail, EvaluationDetailId, EvaluationDetailW> implements EvaluationDetailServerLocal {

	private Map<String, String> mapGetEvaluationDetailSQL = new HashMap<String, String>();
	{
		mapGetEvaluationDetailSQL.put("VENDORRUT", "vd.rut");
		mapGetEvaluationDetailSQL.put("VENDORNAME", "vd.name");
		mapGetEvaluationDetailSQL.put("DELIVERYNUMBER", "dl.number");
		mapGetEvaluationDetailSQL.put("DELIVERYSTATETYPE", "dst.name");
		mapGetEvaluationDetailSQL.put("DATINGDATE", "rs.when1");
		mapGetEvaluationDetailSQL.put("RECEPTIONSCORE", "re.score");
		mapGetEvaluationDetailSQL.put("RECEPTIONERRORCODE", "rr.code");
		mapGetEvaluationDetailSQL.put("RECEPTIONERRORNAME", "rr.name");
	}	
	
	@EJB
	ReceptionErrorServerLocal receptionerrorserver;

	@EJB
	ReceptionEvaluationServerLocal receptionevaluationserver;

	public EvaluationDetailW addEvaluationDetail(EvaluationDetailW evaluationdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (EvaluationDetailW) addIdentifiable(evaluationdetail);
	}
	public EvaluationDetailW[] getEvaluationDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (EvaluationDetailW[]) getIdentifiables();
	}
	public EvaluationDetailW updateEvaluationDetail(EvaluationDetailW evaluationdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (EvaluationDetailW) updateIdentifiable(evaluationdetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(EvaluationDetail entity, EvaluationDetailW wrapper) {
		wrapper.setReceptionerrorid(entity.getReceptionerror() != null ? new Long(entity.getReceptionerror().getId()) : new Long(0));
		wrapper.setReceptionevaluationid(entity.getReceptionevaluation() != null ? new Long(entity.getReceptionevaluation().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(EvaluationDetail entity, EvaluationDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getReceptionerrorid() != null && wrapper.getReceptionerrorid() > 0) { 
			ReceptionError receptionerror = receptionerrorserver.getReferenceById(wrapper.getReceptionerrorid());
			if (receptionerror != null) { 
				entity.setReceptionerror(receptionerror);
			}
		}
		if (wrapper.getReceptionevaluationid() != null && wrapper.getReceptionevaluationid() > 0) { 
			ReceptionEvaluation receptionevaluation = receptionevaluationserver.getReferenceById(wrapper.getReceptionevaluationid());
			if (receptionevaluation != null) { 
				entity.setReceptionevaluation(receptionevaluation);
			}
		}
	}

	private String getQueryString(int queryType, OrderCriteriaDTO[] orderby, Long vendorid) throws OperationFailedException, AccessDeniedException{
		
		String SQL 						= "";
		String SQLCount 				= "";
		String condicionOrderBy			= "";
		String vendorCondition 			= "";
		String viewOpen 				= "";
		String viewClose 				= "";
		
		if (vendorid.longValue() != -1) {
			vendorCondition = "vd.id = :vendorid AND "; //
		}
		
		condicionOrderBy = getOrderByString(mapGetEvaluationDetailSQL, orderby);	
		
		if (queryType == 1) {
			SQLCount =
					"SELECT " + //
						"COUNT(deliveryid) " + //
					"FROM " + //
						"aa "; //
			
			viewOpen = "WITH aa AS ("; //
			viewClose = ") "; //
		}
		
		SQL = 	
			viewOpen + //
			"SELECT	" + //
				"dl.id AS deliveryid, " + //
				"vd.rut AS vendorrut, " + //
				"vd.name AS vendorname, " + //
				"dl.number AS deliverynumber, " + //
				"dst.name AS deliverystatetype, " + //
				"logistica.tostr(rs.when1) AS datingdate, " + //
				"re.score AS receptionscore, " + //
				"rr.code AS receptionerrorcode, " + //
				"rr.name AS receptionerrorname " + //
			"FROM " + //
				"logistica.delivery AS dl JOIN " + //
				"logistica.vendor AS vd ON vd.id = dl.vendor_id JOIN " + //
				"logistica.deliverystatetype AS dst ON dst.id = dl.currentstatetype_id JOIN " + //
				"logistica.dating AS dt ON dt.delivery_id = dl.id JOIN " + //
				"logistica.reserve AS rs ON rs.id = dt.id JOIN " + //
				"logistica.receptionevaluation AS re ON re.dating_id = dt.id JOIN " + //
				"logistica.evaluationdetail AS ed ON ed.receptionevaluation_id = re.id JOIN " + //
				"logistica.receptionerror AS rr ON rr.id = ed.receptionerror_id " + //		
			"WHERE " + //
				vendorCondition + //		
				"rs.when1 >= :since AND rs.when1 <= :until " + //
			condicionOrderBy + //
			viewClose + //
			SQLCount; //
		
		return SQL;		
	}		
	
	public EvaluationDetailReportDataDTO[] getEvaluationDetailReport(Date since, Date until, Long vendorid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException{
		String SQL = getQueryString(2, orderby, vendorid);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid.longValue() != -1) {
			query.setLong("vendorid", vendorid);
		}
		query.setResultTransformer(new LowerCaseResultTransformer(EvaluationDetailReportDataDTO.class));
		
		if (ispaginated) {//si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		EvaluationDetailReportDataDTO[] result = (EvaluationDetailReportDataDTO[]) list.toArray(new EvaluationDetailReportDataDTO[list.size()]);		
		return result;	
	}
	
	public int getEvaluationDetailCountReport(Date since, Date until, Long vendorid) throws AccessDeniedException, OperationFailedException{
		String SQL = getQueryString(1, null, vendorid);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid.longValue() != -1) {
			query.setLong("vendorid", vendorid);
		}
		
		int total = ((BigInteger)query.list().get(0)).intValue();
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
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "EvaluationDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "EvaluationDetail";
	}
}
