package bbr.b2b.logistic.datings.classes;

import java.math.BigInteger;
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

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.datings.data.wrappers.ReserveDetailW;
import bbr.b2b.logistic.datings.entities.DatingResource;
import bbr.b2b.logistic.datings.entities.DatingResourceId;
import bbr.b2b.logistic.datings.entities.Reserve;
import bbr.b2b.logistic.datings.entities.ReserveDetail;
import bbr.b2b.logistic.datings.entities.ReserveDetailId;
import bbr.b2b.logistic.datings.report.classes.PreDatingReserveDetailDTO;
import bbr.b2b.logistic.datings.report.classes.ReserveDetailBlockingDataDTO;
import bbr.b2b.logistic.datings.report.classes.ReserveDetailDTO;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderW;

@Stateless(name = "servers/datings/ReserveDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReserveDetailServer extends BaseLogisticEJB3Server<ReserveDetail, ReserveDetailId, ReserveDetailW> implements ReserveDetailServerLocal {

	private Map<String, String> mapGetPreDatingReserveDetailSQL = new HashMap<String, String>();
	{
		mapGetPreDatingReserveDetailSQL.put("RESERVEID", "rd.reserve_id");	
		mapGetPreDatingReserveDetailSQL.put("WHEN", "re.when1");
		mapGetPreDatingReserveDetailSQL.put("DOCKCODE", "dk.code");
		mapGetPreDatingReserveDetailSQL.put("STARTS", "mo.starts");
		mapGetPreDatingReserveDetailSQL.put("ENDS", "mo.ends");
	}
	
	private Map<String, String> mapGetBlockingDetailSQL = new HashMap<String, String>();
	{
		mapGetBlockingDetailSQL.put("DOCKCODE", "dk.code");	
		mapGetBlockingDetailSQL.put("DAYOFWEEK", "dayofweek");
		mapGetBlockingDetailSQL.put("RESERVESTART", "reservestart");
		mapGetBlockingDetailSQL.put("RESERVEEND", "reserveend");
	}
	
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
	
	public ReserveDetailDTO[] getReserveDetailsByDateAndLocationExcludeVendor(LocalDateTime since, LocalDateTime until, Long locationid, Long vendorid) throws OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.datings.entities.ReserveDetail.getReserveDetailsByDateAndLocationExcludeVendor");
		query.setParameter("since", since);
		query.setParameter("until", until);
		query.setLong("locationid", locationid);
		query.setLong("vendorid", vendorid);
		query.setResultTransformer(new LowerCaseResultTransformer(ReserveDetailDTO.class));
		List<?> list = query.list();
		ReserveDetailDTO[] result = (ReserveDetailDTO[]) list.toArray(new ReserveDetailDTO[list.size()]);
		return result;
	}
	
	public ReserveDetailW[] getReserveDetailByVendorDateLocation(LocalDateTime when, Long locationid, Long vendorid) throws NotFoundException, OperationFailedException {
		
		ReserveDetailW[] result = null;
		List<ReserveDetailW> resultList = new ArrayList<ReserveDetailW>();
		
		String queryStr = 	"select rd " + //
							"from ReserveDetail as rd, " + //
							"Reserve as res, " + //
							"ResourceBlocking as rb, " + //
							"PreDatingResourceGroup as pdr " + //
							"where " + //
							"rd.reserve.id = res.id " + //
							"and res.id = rb.id " + //
							"and rb.blockinggroup.id = pdr.id " + //
							"and pdr.vendor.id = :vendorid " + //
							"and res.when = :when " + //
							"and res.location.id = :locationid ";
							
		PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
		properties[0] = new PropertyInfoDTO("res.when", "when", when);
		properties[1] = new PropertyInfoDTO("res.location.id", "locationid", locationid);
		properties[2] = new PropertyInfoDTO("pdr.vendor.id", "vendorid", vendorid);

		resultList = getByQuery(queryStr, properties);
		result = (ReserveDetailW[]) resultList.toArray(new ReserveDetailW[resultList.size()]);
		return result;		
		
	}
	
	public ReserveDetailW[] getReserveDetailByIds(Long[] reserveids) throws NotFoundException, OperationFailedException {
		ReserveDetailW[] result = null;
		List<ReserveDetailW> resultList = new ArrayList<ReserveDetailW>();
		
		List<Long> reserveidsList = Arrays.asList(reserveids);
		String queryStr = 	"select rd from ReserveDetail as rd " + // 
							"where rd.reserve.id in (:reserveids) ";
							 
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("rd.reserve.id", "reserveids", reserveidsList);
		
		resultList = getByQuery(queryStr, properties);
		result =(ReserveDetailW[]) resultList.toArray(new ReserveDetailW[resultList.size()]);
		return result;
	}	
	
	public PreDatingReserveDetailDTO[] getPreDatingReserveDetailByLocationAndDate(Long vendorid, Long locationid, LocalDateTime date, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException {
		String SQL = getPreDatingReserveDetailQuery(2, orderby);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("locationid", locationid);
		query.setParameter("date", date);
		query.setResultTransformer(new LowerCaseResultTransformer(PreDatingReserveDetailDTO.class));
		
		if (ispaginated) {// si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List<?> list = query.list();
		PreDatingReserveDetailDTO[] result = (PreDatingReserveDetailDTO[]) list.toArray(new PreDatingReserveDetailDTO[list.size()]);
		return result;
	}
	
	public int countPreDatingReserveDetailByLocationAndDate(Long vendorid, Long locationid, LocalDateTime date) throws AccessDeniedException {
		String SQL = getPreDatingReserveDetailQuery(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("locationid", locationid);
		query.setParameter("date", date);
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;
	}
	
	private String getPreDatingReserveDetailQuery(int queryType,  OrderCriteriaDTO[] orderby) throws AccessDeniedException {
				
		String SQL = "";
		String conditionOrderBy = "";
		
		if (queryType == 1) {
			SQL += 
				"SELECT " + //
					"COUNT(1) "; // 
		}
		else if (queryType == 2) {
			SQL +=
				"SELECT " + //
						"pdr.id AS predatingresourcegroupid, " + //
						"rd.reserve_id AS reserveid, " + //
						"re.when1 AS when, " + //
						"rd.dock_id AS dockid, " + //
						"dk.code AS dockcode, " + //
						"rd.module_id AS moduleid, " + //
						"mo.starts AS starts, " + //
						"mo.ends AS ends "; //
			
			conditionOrderBy = getOrderByString(mapGetPreDatingReserveDetailSQL, orderby);
		}
			
		SQL +=
			"FROM " + //
				"logistica.predatingresourcegroup AS pdr JOIN " + //
				"logistica.resourceblocking AS rb ON rb.blockinggroup_id = pdr.id JOIN " + //
				"logistica.reserve AS re ON re.id = rb.id JOIN " + //				
				"logistica.reservedetail AS rd ON rd.reserve_id = re.id JOIN " + //
				"logistica.dock AS dk ON dk.id = rd.dock_id JOIN " + //
				"logistica.module AS mo ON mo.id = rd.module_id " + //
			"WHERE " + //
				"pdr.vendor_id = :vendorid AND re.location_id = :locationid AND re.when1 = :date " + //
			conditionOrderBy;

		return SQL;
	}
	
	public ReserveDetailW[] getReserveDetailsOfBlockingGroup(Long blockinggroupid) throws NotFoundException, OperationFailedException {
		
		ReserveDetailW[] result = null;
		List<ReserveDetailW> resultList = new ArrayList<ReserveDetailW>();
		
		
		String queryStr = 	"select rd " + //
							"from ReserveDetail as rd, " + //
							"ResourceBlocking as rb " + //
							"where " + //
							"rb.id = rd.reserve.id " + //
							"and rb.blockinggroup.id = :blockinggroupid ";
		
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("rb.blockinggroup.id", "blockinggroupid", blockinggroupid);
		

		resultList = getByQuery(queryStr, properties);
		result = (ReserveDetailW[]) resultList.toArray(new ReserveDetailW[resultList.size()]);
		return result;	
		
	}	

	public ReserveDetailW[] getReserveDetailByDateLocationDockModule(LocalDateTime when, Long locationid, Long dockid, Long moduleid) throws NotFoundException, OperationFailedException {

		ReserveDetailW[] result = null;
		List<ReserveDetailW> resultList = new ArrayList<ReserveDetailW>();
		
		String queryStr = 	"select rd " + // 
							"from ReserveDetail as rd, " + //
							"Reserve as res " + //
							"where " + //
							"res.id = rd.reserve.id " + //
							"and res.when = :when " + //
							"and res.location.id = :locationid " + //
							"and rd.datingresource.dock.id = :dockid " + //
							"and rd.datingresource.module.id = :moduleid ";

		PropertyInfoDTO[] properties = new PropertyInfoDTO[4];
		properties[0] = new PropertyInfoDTO("res.when", "when", when);
		properties[1] = new PropertyInfoDTO("res.location.id", "locationid", locationid);
		properties[2] = new PropertyInfoDTO("rd.datingresource.dock.id", "dockid", dockid);
		properties[3] = new PropertyInfoDTO("rd.datingresource.module.id", "moduleid", moduleid);

		resultList = getByQuery(queryStr, properties);
		result = (ReserveDetailW[]) resultList.toArray(new ReserveDetailW[resultList.size()]);
		return result;
		
	}	
	
	public ReserveDetailW[] getReserveDetailsByDateAnLocation(LocalDateTime since, LocalDateTime until, Long locationid) throws NotFoundException, OperationFailedException {
		
		ReserveDetailW[] result = null;
		List<ReserveDetailW> resultList = new ArrayList<ReserveDetailW>();
		
		String queryStr = 	"select rd " + // 
							"from ReserveDetail as rd, " + //
							"Reserve as res " + //
							"where " + //
							"res.id = rd.reserve.id " + //
							"and res.when >= :since " + //
							"and res.when <= :until " + //
							"and res.location.id = :locationid ";
		
		PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
		properties[0] = new PropertyInfoDTO("res.when", "since", since);
		properties[1] = new PropertyInfoDTO("res.when", "until", until);
		properties[2] = new PropertyInfoDTO("res.location.id", "locationid", locationid);
		
		resultList = getByQuery(queryStr, properties);
		result = (ReserveDetailW[]) resultList.toArray(new ReserveDetailW[resultList.size()]);
		return result;
	}
	
	public ReserveDetailBlockingDataDTO[] getReserveDetailDataOfBlockingGroup(Long blockinggroupid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException {
		
		String SQL = getReserveDetailDataOfBlockingGroupQuery(2, orderby, false);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("blockinggroupid", blockinggroupid);
		query.setResultTransformer(new LowerCaseResultTransformer(ReserveDetailBlockingDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List<?> list = query.list();
		ReserveDetailBlockingDataDTO[] result = (ReserveDetailBlockingDataDTO[]) list.toArray(new ReserveDetailBlockingDataDTO[list.size()]);
		return result;
	}
	
	public int countReserveDetailDataOfBlockingGroup(Long blockinggroupid) throws AccessDeniedException {
		
		String SQL = getReserveDetailDataOfBlockingGroupQuery(1, null, false);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("blockinggroupid", blockinggroupid);
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;
	}
	
	private String getReserveDetailDataOfBlockingGroupQuery(int queryType,  OrderCriteriaDTO[] orderby, boolean isByDay) throws AccessDeniedException {
		
		String SQL = "";
		String condicionOrderBy	= "";
		String daycondition = "";
		
		SQL =
			"SELECT " + // 
                "row_number() OVER (order by rd.reserve_id) as reservedetailid, " + //  
				"rd.reserve_id AS reserveid, " + //  
				"rd.module_id AS moduleid, " + //  
				"rd.dock_id AS dockid, " + //  
				"dk.code AS dockcode, " + //
				"CAST(EXTRACT(ISODOW FROM re.when1) AS INT) AS dayofweek, " + //
				"TO_TIMESTAMP(CAST(TO_CHAR(re.when1, 'YYYY-MM-DD') || ' ' || TO_CHAR(mo.starts, 'HH24:MI:SS') as VARCHAR), 'YYYY-MM-DD HH24:MI:SS') AS reservestart, " + //
				"TO_TIMESTAMP(CAST(TO_CHAR(re.when1, 'YYYY-MM-DD') || ' ' || TO_CHAR(mo.ends, 'HH24:MI:SS') as VARCHAR), 'YYYY-MM-DD HH24:MI:SS') AS reserveend " + //
			"FROM " + //
				"logistica.reservedetail AS rd INNER JOIN " + //
				"logistica.resourceblocking AS rb ON rb.id = rd.reserve_id JOIN " + //
				"logistica.reserve AS re ON re.id = rd.reserve_id JOIN " + //
				"logistica.dock AS dk ON dk.id = rd.dock_id JOIN " + //
				"logistica.module AS mo ON mo.id = rd.module_id " + //
			"WHERE " + //
				"rb.blockinggroup_id = :blockinggroupid "; // 
		
		if (isByDay) {
			daycondition = " AND re.when1 = :day "; //
		}
				
		if (queryType == 1) {
			String vistastart = "WITH aa AS( ";
			String vistaend = ") SELECT COUNT(reservedetailid) FROM aa ";			
			SQL =
				vistastart +
				SQL +
				daycondition +
				vistaend;
		}
		
		else if (queryType == 2) {
			condicionOrderBy = getOrderByString(mapGetBlockingDetailSQL, orderby);
			SQL = SQL + daycondition+ condicionOrderBy;
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
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "ReserveDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "ReserveDetail";
	}
}
