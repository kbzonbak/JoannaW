package bbr.b2b.logistic.dvrdeliveries.classes;

import java.math.BigInteger;
import java.time.LocalDateTime;
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
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.datings.report.classes.DatingRequestReportDataDTO;
import bbr.b2b.logistic.dvrdeliveries.entities.DatingRequest;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DatingRequestDataDTO;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DatingRequestW;

@Stateless(name = "servers/dvrdeliveries/DatingRequestServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DatingRequestServer extends LogisticElementServer<DatingRequest, DatingRequestW> implements DatingRequestServerLocal {

	
	private Map<String, String> mapGetDatingRequestSQL = new HashMap<String, String>();
	{
		mapGetDatingRequestSQL.put("DVRDELIVERYID", "dvrdel.id");
		mapGetDatingRequestSQL.put("DELIVERYNUMBER", "dvrdel.number");
		mapGetDatingRequestSQL.put("VENDORCODE", "ve.code");
		mapGetDatingRequestSQL.put("VENDORNAME", "ve.name");
		mapGetDatingRequestSQL.put("DATINGREQUESTWHEN", "dr.when1");
		mapGetDatingRequestSQL.put("REQUESTDATE", "dr.requestdate");
		mapGetDatingRequestSQL.put("DATINGREQUESTMODULE", "dr.needmodule");
		mapGetDatingRequestSQL.put("ESTIMATEDTIME", "dr.estimatedtime");
		mapGetDatingRequestSQL.put("BULKS", "dr.estimatedsbulks");
		mapGetDatingRequestSQL.put("TRUCKS", "dr.trucks");
		mapGetDatingRequestSQL.put("ORDERTYPE", "dot.ordertype");
		mapGetDatingRequestSQL.put("AVAILABLEUNITS", "qt.availableunits");
		mapGetDatingRequestSQL.put("DATINGREQUESTCOMMENT", "dr.comment");
				
	}

	@EJB
	DvrDeliveryServerLocal dvrdeliveryserver;

	public DatingRequestW addDatingRequest(DatingRequestW datingrequest) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingRequestW) addElement(datingrequest);
	}
	public DatingRequestW[] getDatingRequests() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingRequestW[]) getIdentifiables();
	}
	public DatingRequestW updateDatingRequest(DatingRequestW datingrequest) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingRequestW) updateElement(datingrequest);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DatingRequest entity, DatingRequestW wrapper) {
		wrapper.setDvrdeliveryid(entity.getDvrdelivery() != null ? new Long(entity.getDvrdelivery().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DatingRequest entity, DatingRequestW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDvrdeliveryid() != null && wrapper.getDvrdeliveryid() > 0) { 
			DvrDelivery dvrdelivery = dvrdeliveryserver.getReferenceById(wrapper.getDvrdeliveryid());
			if (dvrdelivery != null) { 
				entity.setDvrdelivery(dvrdelivery);
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

	
	private String getDatingRequestReportQuery(Long vendorid, int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException {

		String sql = "";
		String sqlView1 = "";
		String sqlView2 = "";
		String sqlSelect = "";
		String sqlFrom = ""; 
		String sqlExtraFrom = "";
		String sqlWhere = "";
		String orderByCondition = "";
		
		String vendorCondition = "";
		
		
		if (! vendorid.equals(new Long(LogisticConstants.getINT_TODOS()))){
			vendorCondition = " AND dvrdel.vendor_id = :vendorid ";
		}
		
		// Count
		if(queryType == 1){
			sqlSelect = "SELECT COUNT(1) ";
		}
		
		
		// Datos
		else if(queryType == 2){
			sqlView1 = 	"WITH quantity AS ( " + // 
						"	SELECT " + //  
						"		dvrdel.id, " + // 
						"		SUM(dvrodd.pendingunits) AS pendingunits, " + // 
						"		SUM(dvrodd.availableunits) AS availableunits " + // 
						"	FROM logistica.dvrdelivery AS dvrdel " + // 
						"	JOIN logistica.dvrorderdeliverydetail AS dvrodd " + // 
						"	ON dvrodd.dvrdelivery_id = dvrdel.id " + // 
						"	JOIN logistica.datingrequest AS dr " + // 
						"	ON dr.dvrdelivery_id = dvrdel.id " + // 
						"	WHERE " + // 
						"	dr.requestdate >= :since " + // 
						"	AND dr.requestdate <= :until " + // 
						"	AND dvrdel.currentstatetype_id = :validtype " + // 
						"	AND dvrdel.deliverylocation_id = :locationid " + // 
						vendorCondition + // 
						"GROUP BY dvrdel.id " + // 
						"), ";  
					
			sqlView2 = 	"delordertype AS (  " + //
						"	SELECT DISTINCT " + // 
						"		dvrdel.id, " + // 
						"		ot.description as ordertype " + // 
						"	FROM logistica.dvrdelivery as dvrdel " + // 
						"	JOIN logistica.dvrorderdelivery as dvrod " + // 
						"	ON dvrdel.id = dvrod.dvrdelivery_id " + // 
						"	JOIN logistica.datingrequest AS dr " + // 
						"	ON dr.dvrdelivery_id = dvrdel.id " + // 
						"	JOIN logistica.dvrdeliverystatetype AS dvrst " + // 
						"	ON dvrst.id = dvrdel.currentstatetype_id " + // 
						"	JOIN logistica.order as oc " + // 
						"	ON oc.id = dvrod.dvrorder_id " + // 
						"	JOIN logistica.ordertype as ot " + // 
						"	ON ot.id = oc.ordertype_id " + // 
						"	WHERE " + // 
						"	dr.requestdate >= :since " + // 
						"	AND dr.requestdate <= :until " + // 
						"	AND dvrdel.currentstatetype_id = :validtype " + // 
						"	AND dvrdel.deliverylocation_id = :locationid " + // 
						vendorCondition + //
						") "; 

			
			
			sqlSelect = 	"SELECT " + // 
							"	dvrdel.id AS dvrdeliveryid, " + // 
							"	dvrdel.number as deliverynumber, " + // 
							"	ve.code as vendorcode, " + // 
							"	ve.name as vendorname, " + // 
							"	dr.when1 as datingrequestwhen, " + // 
							"	dr.requestdate as requestdate, " + // 
							"	dr.needmodule as datingrequestmodule, " + // 
							"	dr.estimatedtime as estimatedtime, " + // 
							"	dr.estimatedbulks as bulks, " + // 
							"	dr.trucks as trucks, " + // 
							"	dot.ordertype as ordertype, " + // 
							"	qt.availableunits, " + // 
							"	dr.comment AS datingrequestcomment ";

			
			sqlExtraFrom = 	"JOIN logistica.vendor AS ve " + // 
							"ON ve.id = dvrdel.vendor_id " + // 
							"JOIN quantity AS qt " + // 
							"ON qt.id = dvrdel.id " + // 
							"JOIN delordertype AS dot " + // 
							"ON dot.id = dvrdel.id "; 

			orderByCondition = getOrderByString(mapGetDatingRequestSQL, orderby);
			
		}
		
		
		sqlFrom = 	"FROM logistica.dvrdelivery as dvrdel " + // 
					"JOIN logistica.datingrequest as dr " + // 
					"ON dr.dvrdelivery_id = dvrdel.id ";


		sqlWhere = 	"	WHERE " + //
					"	dr.requestdate >= :since " + //
					"	AND dr.requestdate <= :until " + //
					"	AND dvrdel.currentstatetype_id = :validtype " + //
					"	AND dvrdel.deliverylocation_id = :locationid " + //
					vendorCondition;  	
				
				
		sql = 	sqlView1 + 
				sqlView2 + 
				sqlSelect + 
				sqlFrom + 
				sqlExtraFrom + 
				sqlWhere + 
				orderByCondition;
				
		return sql;
		
	}

		
	
	public DatingRequestDataDTO getDatingRequestDetailDataReport(Long dvrdeliveryid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DatingRequest.getDatingRequestDetailDataReport");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(DatingRequestDataDTO.class));
		DatingRequestDataDTO result = (DatingRequestDataDTO) query.uniqueResult();
		return result;
	}
	
	
	public DatingRequestReportDataDTO[] getDatingRequestReport(Long vendorid, Long locationid, Long validstateid, LocalDateTime since, LocalDateTime until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException {
		
		String sql = getDatingRequestReportQuery(vendorid, 2, orderby);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql);

		if(! vendorid.equals(-1L)){
			query.setLong("vendorid", vendorid);
		}	
		query.setLong("locationid", locationid);
		query.setLong("validtype", validstateid);
		query.setParameter("since", since);
		query.setParameter("until", until);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DatingRequestReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List<?> list = query.list();
		DatingRequestReportDataDTO[] result = (DatingRequestReportDataDTO[]) list.toArray(new DatingRequestReportDataDTO[list.size()]);		
		return result;
			
	}


	public int getCountDatingRequestReport(Long vendorid, Long locationid, Long validstateid, LocalDateTime since, LocalDateTime until) throws AccessDeniedException, OperationFailedException {
		
		String sql = getDatingRequestReportQuery(vendorid, 1, null);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql);

		if(! vendorid.equals(-1L)){
			query.setLong("vendorid", vendorid);
		}	
		query.setLong("locationid", locationid);
		query.setLong("validtype", validstateid);
		query.setParameter("since", since);
		query.setParameter("until", until);
		
		int total = Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
		return total;
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DatingRequest";
	}
	@Override
	protected void setEntityname() {
		entityname = "DatingRequest";
	}
}
