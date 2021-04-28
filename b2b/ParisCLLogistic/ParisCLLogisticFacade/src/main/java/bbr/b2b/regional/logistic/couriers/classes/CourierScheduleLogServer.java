package bbr.b2b.regional.logistic.couriers.classes;

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
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierScheduleLogW;
import bbr.b2b.regional.logistic.couriers.entities.CourierScheduleLog;
import bbr.b2b.regional.logistic.couriers.entities.CourierTag;
import bbr.b2b.regional.logistic.couriers.entities.RescheduleReason;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierScheduleLogDTO;

@Stateless(name = "servers/couriers/CourierScheduleLogServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CourierScheduleLogServer extends LogisticElementServer<CourierScheduleLog, CourierScheduleLogW> implements CourierScheduleLogServerLocal {
	
	private Map<String, String> mapCourierScheduleLogSQL = new HashMap<String, String>();
	{
		mapCourierScheduleLogSQL.put("WHEN", "csl.when1");
		mapCourierScheduleLogSQL.put("WITHDRAWALNUMBER", "csl.withdrawalnumber");
		mapCourierScheduleLogSQL.put("WITHDRAWALDATE", "csl.withdrawaldate");
		mapCourierScheduleLogSQL.put("RESCHEDULEREASONCODE", "rsr.code");
		mapCourierScheduleLogSQL.put("RESCHEDULEREASONDESC", "rsr.description");		
	}
	
	@EJB
	CourierTagServerLocal couriertagServerLocal;
	
	@EJB
	RescheduleReasonServerLocal reschedulereasonServerLocal;
	
	public CourierScheduleLogW updateCourierScheduleLog(CourierScheduleLogW courierschedulelog) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierScheduleLogW) updateElement(courierschedulelog);
	}

	public CourierScheduleLogW addCourierScheduleLog(CourierScheduleLogW courierschedulelog) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierScheduleLogW) addElement(courierschedulelog);
	}
	
	public CourierScheduleLogW[] getCourierScheduleLogs() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierScheduleLogW[]) getIdentifiables();
	}
	
	@Override
	protected void copyRelationsEntityToWrapper(CourierScheduleLog entity, CourierScheduleLogW wrapper) {
		wrapper.setCouriertagid(entity.getCouriertag() != null ? new Long(entity.getCouriertag().getId()) : new Long(0));
		wrapper.setReschedulereasonid(entity.getReschedulereason() != null ? new Long(entity.getReschedulereason().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(CourierScheduleLog entity, CourierScheduleLogW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getCouriertagid() != null && wrapper.getCouriertagid() > 0) { 
			CourierTag couriertag = couriertagServerLocal.getReferenceById(wrapper.getCouriertagid());
			if (couriertag != null) { 
				entity.setCouriertag(couriertag);
			}
		}
		if (wrapper.getReschedulereasonid() != null && wrapper.getReschedulereasonid() > 0) { 
			RescheduleReason reschedulereason = reschedulereasonServerLocal.getReferenceById(wrapper.getReschedulereasonid());
			if (reschedulereason != null) { 
				entity.setReschedulereason(reschedulereason);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "CourierScheduleLog";
	}
	@Override
	protected void setEntityname() {
		entityname = "CourierScheduleLog";
	}
	
	public CourierScheduleLogDTO[] getCourierScheduleLogByDODelivery(Long dodeliveryid, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		
		String SQL =
			"SELECT " + //
				"logistica.tostr(csl.when1) AS when, " + //
				"csl.withdrawalnumber, " + //
				"logistica.tostr(csl.withdrawaldate) AS withdrawaldate, " + //
				"rsr.id AS reschedulereasonid, " + //
				"rsr.code AS reschedulereasoncode, " + //
				"rsr.description AS reschedulereasondesc " + //
			"FROM " + //
				"logistica.courierschedulelog AS csl LEFT JOIN " + //
				"logistica.reschedulereason AS rsr ON rsr.id = csl.reschedulereason_id JOIN " + //
				"logistica.couriertag AS ct ON ct.id = csl.couriertag_id " + //
			"WHERE " + //
				"ct.dodelivery_id = :dodeliveryid "; //
		
		getOrderByString(mapCourierScheduleLogSQL, orderby);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("dodeliveryid", dodeliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(CourierScheduleLogDTO.class));
		
		List list = query.list();
		return (CourierScheduleLogDTO[]) list.toArray(new CourierScheduleLogDTO[list.size()]);
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