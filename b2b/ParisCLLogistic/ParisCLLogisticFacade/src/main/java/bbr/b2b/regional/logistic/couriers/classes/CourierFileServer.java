package bbr.b2b.regional.logistic.couriers.classes;

import java.math.BigInteger;
import java.util.Calendar;
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
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierFileW;
import bbr.b2b.regional.logistic.couriers.entities.Courier;
import bbr.b2b.regional.logistic.couriers.entities.CourierFile;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierFileDTO;

@Stateless(name = "servers/couriers/CourierFileServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CourierFileServer extends LogisticElementServer<CourierFile, CourierFileW> implements CourierFileServerLocal {
	
	@EJB
	CourierServerLocal courierServerLocal;
	
	private Map<String, String> mapGetCourierFileSQL = new HashMap<String, String>();
	{
		mapGetCourierFileSQL.put("UPLOADDATE", "cf.uploaddate");
		mapGetCourierFileSQL.put("FILENAME", "cf.filename");
	}

	@Override
	protected void copyRelationsEntityToWrapper(CourierFile entity, CourierFileW wrapper) {
		wrapper.setCourierid(entity.getCourier() != null ? new Long(entity.getCourier().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(CourierFile entity, CourierFileW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getCourierid() != null && wrapper.getCourierid() > 0) { 
			Courier courier = courierServerLocal.getReferenceById(wrapper.getCourierid());
			if (courier != null) { 
				entity.setCourier(courier);
			}
		}
	}
	
	public CourierFileW addCourierFile(CourierFileW courierfile) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierFileW) addElement(courierfile);
	}
	public CourierFileW[] getCourierFiles() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierFileW[]) getIdentifiables();
	}
	public CourierFileW updateCourierFile(CourierFileW courierfile) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (CourierFileW) updateElement(courierfile);
	}
	
	public Integer getDayOff(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.CourierFile.getDayOff");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, -1);
		cal.set(Calendar.HOUR_OF_DAY,0);
		query.setDate("date", cal.getTime());
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.DAY_OF_YEAR, -1);
		cal2.set(Calendar.HOUR_OF_DAY,24);
		query.setDate("date2", cal2.getTime());
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;
	}
	
	public CourierFileDTO[] getCourierFileReport(Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException{
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(until);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		until = cal.getTime();
		
		String SQL = getQueryString(2, orderby);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);		
		query.setDate("since", since);
		query.setDate("until", until);		
		query.setResultTransformer(new LowerCaseResultTransformer(CourierFileDTO.class));
		if (ispaginated) { //si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		CourierFileDTO[] result = (CourierFileDTO[]) list.toArray(new CourierFileDTO[list.size()]);		
		return result;
	}
	
	public Integer getCourierFileCountReport(Date since, Date until) throws OperationFailedException, AccessDeniedException{
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(until);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		until = cal.getTime();
		
		String SQL = getQueryString(1, null);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);		
		query.setDate("since", since);
		query.setDate("until", until);
			
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;	
	}	
	
	private String getQueryString(int queryType, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException{
		
		String SQL 				= "";
		String orderByCondition	= "";
		
		if (queryType == 1) {
			SQL = 			
				"SELECT " + //
					"COUNT (1)"; //		
		}
		else {
			SQL =
				"SELECT " + //
					"cf.uploaddate, " + //
					"cf.filename "; //
			
			if (orderby != null) {
				orderByCondition = getOrderByString(mapGetCourierFileSQL, orderby);
			}
		}
		
		SQL +=
			"FROM " + //
				"logistica.courierfile AS cf JOIN " + //
				"logistica.courier AS c ON cf.courier_id = c.id AND c.code = 'CE' " + //					
			"WHERE " + //
				"cf.dayloaded IS TRUE AND cf.uploaddate >= :since AND cf.uploaddate < :until " + //
			orderByCondition ; //		
		
		return SQL;		
	}
	
	public CourierFileW[] getEmptyCourierFiles() {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.CourierFile.getEmptyCourierFiles");
		query.setResultTransformer(new LowerCaseResultTransformer(CourierFileW.class));
		
		List list = query.list();
		CourierFileW[] result = (CourierFileW[]) list.toArray(new CourierFileW[list.size()]);		
		return result;
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
		entitylabel = "CourierFile";
	}
	@Override
	protected void setEntityname() {
		entityname = "CourierFile";
	}
}
