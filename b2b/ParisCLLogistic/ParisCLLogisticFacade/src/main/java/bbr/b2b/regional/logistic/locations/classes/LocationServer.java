package bbr.b2b.regional.logistic.locations.classes;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.kpi.data.classes.SaleStoreDTO;
import bbr.b2b.regional.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.locations.report.classes.LocationLogDTO;

@Stateless(name = "servers/locations/LocationServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LocationServer extends LogisticElementServer<Location, LocationW> implements LocationServerLocal {
	
	private Map<String, String> mapGetLocationSQL = new HashMap<String, String>();
	{
		mapGetLocationSQL.put("ID", "LO.ID");
		mapGetLocationSQL.put("CODE", "LO.CODE");
		mapGetLocationSQL.put("NAME", "LO.NAME");
		mapGetLocationSQL.put("SHORTNAME", "LO.SHORTNAME");
		mapGetLocationSQL.put("ADDRESS", "LO.ADDRESS");
		mapGetLocationSQL.put("EMAIL", "LO.EMAIL");
		mapGetLocationSQL.put("PRIORITY", "LO.PRIORITY");
		mapGetLocationSQL.put("PHONE", "LO.PHONE");
		mapGetLocationSQL.put("FAX", "LO.FAX");
		mapGetLocationSQL.put("COMMUNE", "LO.COMMUNE");
		mapGetLocationSQL.put("CITY", "LO.CITY");
		mapGetLocationSQL.put("WAREHOUSE", "LO.WAREHOUSE");		
	}

	public LocationW addLocation(LocationW location) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (LocationW) addElement(location);
	}
	public LocationW[] getLocations() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (LocationW[]) getIdentifiables();
	}
	public LocationW updateLocation(LocationW location) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (LocationW) updateElement(location);
	}
	
	private String getSQLgetLocationOfUser (Long[] vendorIds, OrderCriteriaDTO[] order, boolean isQueryToCount) throws AccessDeniedException{
		String orderBy = "";
		String locationCondition = "";
		StringBuilder sb = new StringBuilder("(");
		for (int i = 0; i < vendorIds.length; i++) {
			sb.append(vendorIds[i]);
			sb.append(",");
		}
		
		sb.delete(sb.length()-1, sb.length());
		sb.append(")");
		locationCondition = sb.toString();
				
		String campos = "";
		
		if(isQueryToCount){
			campos = " COUNT(*) ";
		}else{
			campos =				
				"LO.ID, " +
				"LO.CODE, " +
				"LO.NAME, " + 
				"LO.SHORTNAME, " + 
				"LO.ADDRESS, " + 
				"LO.EMAIL, " + 
				"LO.PRIORITY, " + 
				"LO.PHONE, " + 
				"LO.FAX, " + 
				"LO.COMMUNE, " +
				"LO.CITY, " +
				"LO.WAREHOUSE ";
									
			orderBy = getOrderByString(mapGetLocationSQL, order);
		}		
				
		String SQL = 
			"SELECT " +
				campos +
			"FROM " +
				"LOGISTICA.LOCATION LO " +
			"WHERE " +
				"LO.ID IN " + locationCondition + orderBy;
		return SQL;	
		
	}
	
	private String getSQLLocationsLikeProperty (boolean isCode, boolean isQueryToCount, Long[] assignedids, OrderCriteriaDTO[] order, boolean isAssigned) throws AccessDeniedException{
		
		String campos				= "";
		String orderby				= "";
		String findBy				= "";
		String locationids 			= "";
		String locationCondition	= "";
		String not					= "";
		
		if(!isAssigned){
			not = " NOT "; 
		}
		
		StringBuilder sb = new StringBuilder("(");
		for (int i = 0; i < assignedids.length; i++) {
			sb.append(assignedids[i]);
			sb.append(",");
		}
		
		sb.delete(sb.length()-1, sb.length());
		sb.append(")");
		locationids = sb.toString();
		
		if(assignedids.length > 0){
			locationCondition = " AND LO.ID " + not + " IN " + locationids; 
		}
		
		if(isCode){
			findBy = "UPPER(LO.CODE) LIKE :property ";
		}else{
			findBy = "UPPER(LO.NAME) LIKE :property ";
		}
		
		if(isQueryToCount){
			campos = " COUNT(*) ";
		}else{
			campos = 
				"LO.ID, " +
				"LO.CODE, " +
				"LO.NAME, " + 
				"LO.SHORTNAME, " + 
				"LO.ADDRESS, " + 
				"LO.EMAIL, " + 
				"LO.PRIORITY, " + 
				"LO.PHONE, " + 
				"LO.FAX, " + 
				"LO.COMMUNE, " +
				"LO.CITY, " +
				"LO.WAREHOUSE ";		
			orderby = getOrderByString(mapGetLocationSQL, order);
		}
		
		String SQL =
			"SELECT " +
				campos +
			"FROM " +
				"LOGISTICA.LOCATION LO " +
			"WHERE " +
			 	findBy +
			 	locationCondition + 
			 	orderby;		
		return SQL;		
	}
	
	public LocationW[] getLocationsByIds(Long[] ids) throws ServiceException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.locations.entities.Location.getLocationsByIds");
		query.setParameterList("localids", ids);
		query.setResultTransformer(new LowerCaseResultTransformer(LocationW.class));
		List list = query.list();
		LocationW[] result = (LocationW[]) list.toArray(new LocationW[list.size()]);
		return result;
	}
	
	public LocationW[] getLocationByIds(Long[] lokeys, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException {
		
		String SQL = getSQLgetLocationOfUser(lokeys, order, false);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(LocationW.class));
		if(isPaginated){
			query.setFirstResult((pagenumber-1) * rows);
		    query.setMaxResults(rows);	
		}
		List list = query.list();
		LocationW[] result = (LocationW[]) list.toArray(new LocationW[list.size()]);
		return result;
	}
	
	public LocationW[] getLocationsOfOrder(Long ordernumber) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = "SELECT LO.ID, " +
						"LO.CODE, " +
						"LO.NAME, " +
						"LO.SHORTNAME, " +
						"LO.ADDRESS, " +
						"LO.EMAIL, " +
						"LO.PRIORITY, " +
						"LO.PHONE, " +
						"LO.FAX, " +
						"LO.COMMUNE, " +
						"LO.CITY, " +
						"LO.WAREHOUSE " +
						"FROM LOGISTICA.LOCATION AS LO " +
						"JOIN LOGISTICA.PREDELIVERYDETAIL AS PDD ON PDD.LOCATION_ID = LO.ID " +
						"JOIN LOGISTICA.ORDER AS O ON PDD.ORDER_ID = O.ID " +
						"WHERE O.NUMBER = :ordernumber";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("ordernumber", ordernumber);
		query.setResultTransformer(new LowerCaseResultTransformer(LocationW.class));

		List list = query.list();
		return (LocationW[]) list.toArray(new LocationW[list.size()]);
	
	}
	
	public LocationW[] getDestinationLocationsByDelivery(Long deliveryid) throws OperationFailedException, NotFoundException {
		
        StringBuilder sb =
        	new StringBuilder(
        				"SELECT DISTINCT " + //
        					"lo " + //" +
        				"FROM " + //
        					"OrderDeliveryDetail AS odd, " + //
        					"Location AS lo " + //
        				"WHERE " + //
        					"lo.id = odd.id.locationid AND odd.id.deliveryid = :deliveryid");
        
        PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("odd.id.deliveryid", "deliveryid", deliveryid);
        List list = getByQuery(sb.toString(), properties);

        return (LocationW[]) list.toArray(new LocationW[list.size()]);
	}
	
	public int countLocationOfUser(Long[] lokeys) throws ServiceException{
		String SQL = getSQLgetLocationOfUser(lokeys, null, true);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		Integer count = ((BigInteger)query.uniqueResult()).intValue();
		return count.intValue();
	}	
	
	public LocationLogDTO[] findLocationsLogLikeCodeAssigned(String code, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException {
		String SQL = getSQLLocationsLikeProperty(true, false, assignedids, order, true);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + code + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(LocationLogDTO.class));
		if(isPaginated){
			query.setFirstResult((pagenumber-1) * rows);
		    query.setMaxResults(rows);	
		}
		List list = query.list();
		LocationLogDTO[] result = (LocationLogDTO[]) list.toArray(new LocationLogDTO[list.size()]);
		return result;
	}
	
	public int countLocationsLogLikeCodeAssigned(String code, Long[] assignedids) throws ServiceException {
		String SQL = getSQLLocationsLikeProperty(true, true, assignedids, null, true);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + code + "%");
		Integer count = ((BigInteger)query.uniqueResult()).intValue();
		return count.intValue();
	}
	
	public LocationLogDTO[] findLocationsLogLikeCodeNotAssigned(String code, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException {
		String SQL = getSQLLocationsLikeProperty(true, false, assignedids, order, false);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + code + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(LocationLogDTO.class));
		if(isPaginated){
			query.setFirstResult((pagenumber-1) * rows);
		    query.setMaxResults(rows);	
		}
		List list = query.list();
		LocationLogDTO[] result = (LocationLogDTO[]) list.toArray(new LocationLogDTO[list.size()]);
		return result;
	}
	
	public int countLocationsLogLikeCodeNotAssigned(String code, Long[] assignedids) throws ServiceException {
		String SQL = getSQLLocationsLikeProperty(true, true, assignedids, null, false);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + code + "%");
		Integer count = ((BigInteger)query.uniqueResult()).intValue();
		return count.intValue();
	}
	
	public LocationLogDTO[] findLocationsLogLikeNameAssigned(String name, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException {
		String SQL = getSQLLocationsLikeProperty(false, false, assignedids, order, true);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + name + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(LocationLogDTO.class));
		if(isPaginated){
			query.setFirstResult((pagenumber-1) * rows);
		    query.setMaxResults(rows);	
		}
		List list = query.list();
		LocationLogDTO[] result = (LocationLogDTO[]) list.toArray(new LocationLogDTO[list.size()]);
		return result;
	}
	
	public int countLocationsLogLikeNameAssigned(String name, Long[] assignedids) throws ServiceException {
		String SQL = getSQLLocationsLikeProperty(false, true, assignedids, null, true);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + name + "%");
		Integer count = ((BigInteger)query.uniqueResult()).intValue();
		return count.intValue();
	}
	
	public LocationLogDTO[] findLocationsLogLikeNameNotAssigned(String name, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException {
		String SQL = getSQLLocationsLikeProperty(false, false, assignedids, order, false);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + name + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(LocationLogDTO.class));
		if(isPaginated){
			query.setFirstResult((pagenumber-1) * rows);
		    query.setMaxResults(rows);	
		}
		List list = query.list();
		LocationLogDTO[] result = (LocationLogDTO[]) list.toArray(new LocationLogDTO[list.size()]);
		return result;
	}
	
	public int countLocationsLogLikeNameNotAssigned(String name, Long[] assignedids) throws ServiceException {
		String SQL = getSQLLocationsLikeProperty(false, true, assignedids, null, false);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + name + "%");
		Integer count = ((BigInteger)query.uniqueResult()).intValue();
		return count.intValue();
	}
	
	public SaleStoreDTO[] getSaleStoresOrderedByDescription() throws OperationFailedException, NotFoundException{
		
		String SQL =
			"SELECT DISTINCT " +
				"ID, " +
				"CODE, " +
				"NAME AS DESCRIPTION, " +
				"CODE || ' - ' || NAME AS CODEDESCRIPTIONSTR " +
			"FROM " +
				"LOGISTICA.LOCATION " +
			//"WHERE " +
			//	"WAREHOUSE IS FALSE " +
			"ORDER BY "+
				"DESCRIPTION ASC";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(SaleStoreDTO.class));
		List list = query.list();
		
		return (SaleStoreDTO[]) list.toArray(new SaleStoreDTO[list.size()]);
	}
	
	public SaleStoreDTO[] findSaleStoresByCode(String code) throws OperationFailedException, NotFoundException{
		
		String SQL =
			"SELECT DISTINCT " +
				"ID, " +
				"CODE, " +
				"NAME AS DESCRIPTION, " +
				"CODE || ' - ' || NAME AS CODEDESCRIPTIONSTR " +
			"FROM " +
				"LOGISTICA.LOCATION " +
			"WHERE " +
				"WAREHOUSE IS FALSE AND CODE LIKE :code " +
			"ORDER BY "+
				"DESCRIPTION ASC";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("code", "%" + code.replace("%", "\\%") + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(SaleStoreDTO.class));
		List list = query.list();
		
		return (SaleStoreDTO[]) list.toArray(new SaleStoreDTO[list.size()]);		
		
	}
	
	public SaleStoreDTO[] findSaleStoresByName(String name) throws OperationFailedException, NotFoundException{
		
		String SQL =
			"SELECT DISTINCT " +
				"ID, " +
				"CODE, " +
				"NAME AS DESCRIPTION, " +
				"CODE || ' - ' || NAME AS CODEDESCRIPTIONSTR " +
			"FROM " +
				"LOGISTICA.LOCATION " +
			"WHERE " +
				"WAREHOUSE IS FALSE AND NAME LIKE :name " +
			"ORDER BY "+
				"DESCRIPTION ASC";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("name", "%" + name.replace("%", "\\%") + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(SaleStoreDTO.class));
		List list = query.list();
		
		return (SaleStoreDTO[]) list.toArray(new SaleStoreDTO[list.size()]);		
				
	}
	
	public int getCountLocations(){
		String sql = "SELECT COUNT(1) FROM logistica.location ";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql);
		int total = Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
		return total;
	}
	
	@Override
	protected void copyRelationsEntityToWrapper(Location entity, LocationW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Location entity, LocationW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Location";
	}
	@Override
	protected void setEntityname() {
		entityname = "Location";
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
