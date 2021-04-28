package bbr.b2b.regional.logistic.datings.classes;

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
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.datings.data.wrappers.ReserveDetailW;
import bbr.b2b.regional.logistic.datings.entities.DatingResource;
import bbr.b2b.regional.logistic.datings.entities.DatingResourceId;
import bbr.b2b.regional.logistic.datings.entities.Reserve;
import bbr.b2b.regional.logistic.datings.entities.ReserveDetail;
import bbr.b2b.regional.logistic.datings.entities.ReserveDetailId;
import bbr.b2b.regional.logistic.datings.report.classes.AssignedDatingDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.AssignedResourceBlockingDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ReserveDetailDataDTO;

@Stateless(name = "servers/datings/ReserveDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReserveDetailServer extends BaseLogisticEJB3Server<ReserveDetail, ReserveDetailId, ReserveDetailW> implements ReserveDetailServerLocal {


	@EJB
	ReserveServerLocal reserveserver;

	@EJB
	DatingResourceServerLocal datingresourceserver;

	public ReserveDetailW addReserveDetail(ReserveDetailW reservedetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReserveDetailW) addIdentifiable(reservedetail);
	}
	public ReserveDetailW[] getReserveDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReserveDetailW[]) getIdentifiables();
	}
	public ReserveDetailW updateReserveDetail(ReserveDetailW reservedetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReserveDetailW) updateIdentifiable(reservedetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ReserveDetail entity, ReserveDetailW wrapper) {
		wrapper.setReserveid(entity.getReserve() != null ? new Long(entity.getReserve().getId()) : new Long(0));
		wrapper.setDockid(entity.getDatingresource().getId() != null ? new Long(entity.getDatingresource().getId().getDockid()) : new Long(0));
		wrapper.setModuleid(entity.getDatingresource().getId() != null ? new Long(entity.getDatingresource().getId().getModuleid()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(ReserveDetail entity, ReserveDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getReserveid() != null && wrapper.getReserveid() > 0) { 
			Reserve reserve = reserveserver.getReferenceById(wrapper.getReserveid());
			if (reserve != null) { 
				entity.setReserve(reserve);
			}
		}
		if ((wrapper.getDockid() != null && wrapper.getDockid() > 0) && (wrapper.getModuleid() != null && wrapper.getModuleid() > 0)) {
			DatingResourceId key = new DatingResourceId();
			key.setDockid(wrapper.getDockid());
			key.setModuleid(wrapper.getModuleid());
			DatingResource var = datingresourceserver.getReferenceById(key);
			if (var != null) { 
				entity.setDatingresource(var);
			}
		}
	}
	
	private Map<String, String> mapGetBlockingDetailSQL = new HashMap<String, String>();
	{
		mapGetBlockingDetailSQL.put("DOCKCODE", "DK.CODE");	
		mapGetBlockingDetailSQL.put("WHEN", "RE.WHEN1");	
		mapGetBlockingDetailSQL.put("STARTS", "MO.STARTS");		
		mapGetBlockingDetailSQL.put("ENDS", "MO.ENDS");		
		mapGetBlockingDetailSQL.put("DAYOFWEEK", "case when extract(dow from re.when1) != 0 then extract(dow from re.when1) else 7 end");		
	
	}	

	@Override
	protected void setEntitylabel() {
		entitylabel = "ReserveDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "ReserveDetail";
	}
	
	public ReserveDetailW[] getReserveDetailByDateAndLocationAndDelivery(Long deliveryId, Long locationId, Date date) throws OperationFailedException, NotFoundException{		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ReserveDetail.getReserveDetailByDateAndLocationAndDelivery");
		query.setLong("deliveryid", deliveryId);
		query.setLong("locationid", locationId);	
		query.setDate("date", date);
		query.setResultTransformer(new LowerCaseResultTransformer(ReserveDetailW.class));
		List list = query.list();
		ReserveDetailW[] result = (ReserveDetailW[]) list.toArray(new ReserveDetailW[list.size()]);
		return result;
	}	
	
	public ReserveDetailW[] getReserveDetailsByDateAndLocation(Date since, Date until, Long locationid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ReserveDetail.getReserveDetailsByDateAndLocation");
		query.setDate("since", since);
		query.setDate("until", until);
		query.setLong("locationid", locationid);
		query.setResultTransformer(new LowerCaseResultTransformer(ReserveDetailW.class));
		List list = query.list();
		ReserveDetailW[] result = (ReserveDetailW[]) list.toArray(new ReserveDetailW[list.size()]);
		return result;
	}
	
	public AssignedDatingDataDTO[] getAssignedDatingDetailsByDateAndLocation(Date since, Date until, Long locationid, Long docktypeid, boolean isbyreport) throws AccessDeniedException, OperationFailedException, NotFoundException {
		
		SQLQuery query;
		if (isbyreport) {
			query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ReserveDetail.getAssignedDatingDetailsByDateLocationAndDockType");
			query.setDate("since", since);
			query.setDate("until", until);
			query.setLong("locationid", locationid);
			query.setLong("docktypeid", docktypeid);
		}
		else {
			query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ReserveDetail.getAssignedDatingDetailsByDateAndLocation");
			query.setDate("since", since);
			query.setDate("until", until);
			query.setLong("locationid", locationid);
		}

		query.setResultTransformer(new LowerCaseResultTransformer(AssignedDatingDataDTO.class));
		List list = query.list();
		AssignedDatingDataDTO[] result = (AssignedDatingDataDTO[]) list.toArray(new AssignedDatingDataDTO[list.size()]);
		return result;
	}
	
	public AssignedResourceBlockingDataDTO[] getAssignedResourceBlockingDetailsByDateAndLocation(Date since, Date until, Long locationid, Long docktypeid, boolean isbyreport) throws AccessDeniedException, OperationFailedException, NotFoundException {
		
		SQLQuery query;
		if (isbyreport) {
			query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ReserveDetail.getAssignedResourceBlockingDetailsByDateLocationAndDockType");
			query.setDate("since", since);
			query.setDate("until", until);
			query.setLong("locationid", locationid);
			query.setLong("docktypeid", docktypeid);
		}
		else {
			query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ReserveDetail.getAssignedResourceBlockingDetailsByDateAndLocation");
			query.setDate("since", since);
			query.setDate("until", until);
			query.setLong("locationid", locationid);
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(AssignedResourceBlockingDataDTO.class));
		List list = query.list();
		AssignedResourceBlockingDataDTO[] result = (AssignedResourceBlockingDataDTO[]) list.toArray(new AssignedResourceBlockingDataDTO[list.size()]);
		return result;
	}
	
	public ReserveDetailW[] getReserveDetailsofBlockingGroup(Long reserveid) throws AccessDeniedException, NotFoundException, OperationFailedException {
		ReserveDetailW[] result = null;
		List<ReserveDetailW> resultList = new ArrayList<ReserveDetailW>();
		
		String queryStr = 	"select rd " + //
							"from ReserveDetail as rd, " + //
							"ResourceBlocking as rb " + 
							"where " + 
							"rb.id = rd.reserve.id " + 
							"and rb.blockinggroup.id = :reserveid ";
				
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("rd.reserve.id", "reserveid", reserveid);

		resultList = getByQuery(queryStr, properties);
		result = (ReserveDetailW[]) resultList.toArray(new ReserveDetailW[resultList.size()]);
		return result;
		
		
		
		
		
		
		
		
		
		//		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ReserveDetail.getReserveDetailsOfBlockingGroup");
//		query.setLong("reserveid", reserveid);
//		query.setResultTransformer(new LowerCaseResultTransformer(ReserveDetailW.class));
//		List list = query.list();
//		ReserveDetailW[] result = (ReserveDetailW[]) list.toArray(new ReserveDetailW[list.size()]);
//		return result;
		
		
//		SELECT
//		RD.RESERVE_ID AS RESERVEID, 
//		RD.MODULE_ID AS MODULEID, 
//		RD.DOCK_ID AS DOCKID,
//		RD.WHEN1 AS WHEN
//	FROM 
//		LOGISTICA.RESERVEDETAIL RD INNER JOIN 
//		LOGISTICA.RESOURCEBLOCKING RB ON RD.RESERVE_ID = RB.ID
//	WHERE 
//		RB.BLOCKINGGROUP_ID = :reserveid
	}
	
	public ReserveDetailW[] getDatingDetailsByDateAndLocationAndDockAndModule(Date when, Long locationid, Long dockid, Long moduleid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ReserveDetail.getDatingDetailsByDateAndLocationAndDockAndModule");
		query.setDate("when", when);
		query.setLong("locationid", locationid.longValue());
		query.setLong("dockid", dockid.longValue());
		query.setLong("moduleid", moduleid.longValue());
		query.setResultTransformer(new LowerCaseResultTransformer(ReserveDetailW.class));
		List list = query.list();
		ReserveDetailW[] result = (ReserveDetailW[]) list.toArray(new ReserveDetailW[list.size()]);
		return result;
	}	
	
	public ReserveDetailDataDTO[] getReserveDetailOfDelivery(Long deliveryId, Long vendorId) throws AccessDeniedException, OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ReserveDetail.getReserveDetailOfDelivery");
		query.setLong("deliveryid", deliveryId);
		query.setLong("vendorid", vendorId);
		query.setResultTransformer(new LowerCaseResultTransformer(ReserveDetailDataDTO.class));
		List list = query.list();
		ReserveDetailDataDTO[] result = (ReserveDetailDataDTO[]) list.toArray(new ReserveDetailDataDTO[list.size()]);
		return result;		
	}	
	
	public void doDeleteReserveDetailofReserve(Long reserveid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ReserveDetail.doDeleteReserveDetailofReserve");
		query.setLong("reserveid", reserveid);
		query.executeUpdate();
	}
	
	public void doDeleteReserveDetailsOfReserves(Long[] reserveids) throws AccessDeniedException, OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ReserveDetail.doDeleteReserveDetailsOfReserves");
		query.setParameterList("reserveids", reserveids);
		query.executeUpdate();
	}
	
	public ReserveDetailW[] getReserveDetailsofReserve(Long reserveid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		ReserveDetailW[] result = null;
		List<ReserveDetailW> resultList = new ArrayList<ReserveDetailW>();
		
		String queryStr = 	"select rd " + //
							"from ReserveDetail as rd " + //
							"where rd.reserve.id = :reserveid ";
				
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("rd.reserve.id", "reserveid", reserveid);

		resultList = getByQuery(queryStr, properties);
		result = (ReserveDetailW[]) resultList.toArray(new ReserveDetailW[resultList.size()]);
		return result;
	}
	
	public ReserveDetailDataDTO[] getReserveDetailsDataofBlockingGroup(Long blockinggroupid,int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException {
		String SQL = getReserveDetailsDataofBlockingGroupQuery(false,orderby, false);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("blockinggroupid", blockinggroupid);
		query.setResultTransformer(new LowerCaseResultTransformer(ReserveDetailDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List list = query.list();
		ReserveDetailDataDTO[] result = (ReserveDetailDataDTO[]) list.toArray(new ReserveDetailDataDTO[list.size()]);
		return result;
	}
	
	public int countReserveDetailsDataofBlockingGroup(Long blockinggroupid) throws AccessDeniedException, NotFoundException, OperationFailedException {
		String SQL = getReserveDetailsDataofBlockingGroupQuery(true,null,false);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("blockinggroupid", blockinggroupid);
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;
	}
	
	private String getReserveDetailsDataofBlockingGroupQuery(boolean isQueryToCount, OrderCriteriaDTO[] orderby, boolean isByDay) throws AccessDeniedException{
		String SQL = "";
		String condicionOrderBy	= "";
		String daycodition = "";
		
		SQL = 		"select "+
					"RD.RESERVE_ID AS reserveid, "+
					"RD.MODULE_ID AS moduleid, "+
					"RD.DOCK_ID AS dockid, "+
					"dk.code as dockcode, "+
					"LOGISTICA.TOSTR(re.when1) as when, " +
					"cast((case when extract(dow from re.when1) != 0 then extract(dow from re.when1) else 7 end) as text)  as dayofweek, "+
					"LOGISTICA.TOSTR(mo.starts) as starts, "+
					"LOGISTICA.TOSTR(mo.ends) as ends "+
					"from LOGISTICA.RESERVEDETAIL RD "+
					"inner join LOGISTICA.RESOURCEBLOCKING RB on RD.RESERVE_ID = RB.ID "+
					"join logistica.reserve re on rd.reserve_id = re.id "+
					"join logistica.dock dk on rd.dock_id = dk.id "+
					"join logistica.module mo on rd.module_id = mo.id "+
					"WHERE RB.BLOCKINGGROUP_ID = :blockinggroupid ";
		
		if(isByDay){
			daycodition = " and re.when1 = :day ";
		}
		
		condicionOrderBy = getReserverByString(mapGetBlockingDetailSQL, orderby);	
		
		SQL = SQL + daycodition + condicionOrderBy;
		
		if(isQueryToCount){
			String vistastart = "with AA as( ";
			String vistaend = ") select count(*) from AA ";		
			SQL = vistastart+SQL+vistaend;
		}
		
		return SQL;
	}
	
	public ReserveDetailW[] getDatingDetailsByDateLocationDockAndModule(Date when, Long locationid, Long dockid, Long moduleid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.ReserveDetail.getDatingDetailsByDateLocationDockAndModule");
		query.setDate("when", when);
		query.setLong("locationid", locationid);
		query.setLong("dockid", dockid);
		query.setLong("moduleid", moduleid);
		query.setResultTransformer(new LowerCaseResultTransformer(ReserveDetailW.class));
		List list = query.list();
		ReserveDetailW[] result = (ReserveDetailW[]) list.toArray(new ReserveDetailW[list.size()]);
		return result;
	}
	
	private String getReserverByString(Map<String, String> mapQuery, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
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
