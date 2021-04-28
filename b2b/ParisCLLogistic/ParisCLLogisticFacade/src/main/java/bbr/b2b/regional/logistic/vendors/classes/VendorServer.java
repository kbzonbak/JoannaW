package bbr.b2b.regional.logistic.vendors.classes;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorLogDTO;

@Stateless(name = "servers/vendors/VendorServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VendorServer extends LogisticElementServer<Vendor, VendorW> implements VendorServerLocal {

	private Map<String, String> mapGetVendorSQL = new HashMap<String, String>();
	{
		mapGetVendorSQL.put("ID", "vd.id");
		mapGetVendorSQL.put("RUT", "vd.rut");
		mapGetVendorSQL.put("NUMBER", "vd.number");
		mapGetVendorSQL.put("NAME", "vd.name");
		mapGetVendorSQL.put("TRADENAME", "vd.tradename");
		mapGetVendorSQL.put("ADDRESS", "vd.address");
		mapGetVendorSQL.put("PHONE", "vd.phone");
		mapGetVendorSQL.put("COMMUNE", "vd.commune");
		mapGetVendorSQL.put("CITY", "vd.city");
		mapGetVendorSQL.put("EMAIL", "vd.email");
		mapGetVendorSQL.put("FAX", "vd.fax");
		mapGetVendorSQL.put("CODE", "vd.code");
		mapGetVendorSQL.put("DOMESTIC", "vd.domestic");
		mapGetVendorSQL.put("LASTBULKNUMBER", "vd.lastbulknumber");
		mapGetVendorSQL.put("LOGISTICSCODE", "vd.logisticscode");	
		mapGetVendorSQL.put("GLV", "vd.gln");
		mapGetVendorSQL.put("FIRSTVEVCDDATE", "vd.firstvevcddate");
		mapGetVendorSQL.put("FIRSTVEVPDDATE", "vd.firstvevpddate");
	}	
	
	public VendorW addVendor(VendorW vendor) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorW) addElement(vendor);
	}
	public VendorW[] getVendors() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorW[]) getIdentifiables();
	}
	public VendorW updateVendor(VendorW vendor) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VendorW) updateElement(vendor);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Vendor entity, VendorW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Vendor entity, VendorW wrapper) throws OperationFailedException, NotFoundException {
	}
	
	private String getSQLgetVendorsOfUser (Long[] vendorIds, OrderCriteriaDTO[] order, boolean isQueryToCount) throws AccessDeniedException{
		String orderBy = "";
		String vendorCondition = "";
		StringBuilder sb = new StringBuilder("(");
		for (int i = 0; i < vendorIds.length; i++) {
			sb.append(vendorIds[i]);
			sb.append(",");
		}
		
		sb.delete(sb.length()-1, sb.length());
		sb.append(")");
		vendorCondition = sb.toString();		
		
		String campos = "";
		
		if (isQueryToCount) {
			campos = " COUNT(*) "; //
		} else {
			campos =
				"vd.id, " + //
				"vd.rut, " + //
				"vd.number, " + //
				"vd.name, " + //
				"vd.tradename, " + //
				"vd.address, " + //
				"vd.phone, " + //
				"vd.commune, " + //
				"vd.city, " + //
				"vd.email, " + //
				"vd.fax, " + //
				"vd.code, " + //
				"vd.domestic, " + //
				"vd.lastbulknumber, " + // 
        		"vd.logisticscode, " + //
        		"vd.gln, " + //
        		"vd.firstvevcddate, " + //
        		"vd.firstvevpddate "; //
			
			orderBy = getOrderByString(mapGetVendorSQL, order);
		}		
				
		String SQL = 
			"SELECT " + //
				campos + //
			"FROM " + //
				"logistica.vendor AS vd " + //
			"WHERE " + //
				"vd.id IN " + vendorCondition + //
			orderBy; //
		
		return SQL;		
	}
	
	private String getSQLgetVendorsOfUserCourier (Long[] vendorIds, OrderCriteriaDTO[] order, boolean isQueryToCount) throws AccessDeniedException{
		String orderBy = "";
		String vendorCondition = "";
		StringBuilder sb = new StringBuilder("(");
		for (int i = 0; i < vendorIds.length; i++) {
			sb.append(vendorIds[i]);
			sb.append(",");
		}
		
		sb.delete(sb.length()-1, sb.length());
		sb.append(")");
		vendorCondition = sb.toString();		
		
		String campos = "";
		
		if (isQueryToCount) {
			campos = " COUNT(*) "; //
		} else {
			campos = 
				"vd.id, " + //
		        "vd.rut, " + //
	   		 	"vd.number, " + //
		        "vd.name, " + //
		        "vd.tradename, " + //
		        "vd.address, " + //
		        "vd.phone, " + //
		        "vd.commune, " + //
		        "vd.city, " + //
		        "vd.email, " + //
		        "vd.fax, " + //
		        "vd.code, " + //
		        "vd.domestic, " + //
		        "vd.lastbulknumber, " + //
		        "vd.logisticscode, " + //
		        "CASE WHEN hc.courier_id IS NULL " + //
		        	"THEN FALSE " + //
        		 	"ELSE TRUE " + //
        		 	"END AS courier "; //
			
			orderBy = getOrderByString(mapGetVendorSQL, order);
		}		
				
		String SQL = 
			"SELECT " + //
				campos + //
			"FROM " + //
				"logistica.vendor AS vd LEFT JOIN " + //
				"logistica.hiredcourier AS hc ON hc.vendor_id = vd.id " + //
			"WHERE " + //
				"vd.id IN " + vendorCondition + //
			orderBy; //
		
		return SQL;		
	}
	
	private String getSQLVendorsLikeProperty (boolean isCode, boolean isQueryToCount, Long[] assignedids, OrderCriteriaDTO[] order, boolean isAssigned) throws AccessDeniedException{
		
		String campos			= "";
		String orderby			= "";
		String findBy			= "";
		String vendorids 		= "";
		String vendorCondition	= "";
		String not				= "";
		
		if(!isAssigned){
			not = " NOT "; //
		}
		
		StringBuilder sb = new StringBuilder("(");
		for (int i = 0; i < assignedids.length; i++) {
			sb.append(assignedids[i]);
			sb.append(",");
		}
		
		sb.delete(sb.length()-1, sb.length());
		sb.append(")");
		vendorids = sb.toString();
		
		if(assignedids.length > 0){
			vendorCondition = " AND vd.id " + not + " IN " + vendorids; //
		}
		
		if(isCode){
			findBy = "UPPER(vd.rut) LIKE :property "; //
		}else{
			findBy = "UPPER(vd.name) LIKE :property "; //
		}
		
		if(isQueryToCount){
			campos = " COUNT(*) "; //
		}else{
			campos =
				"vd.id, " + //
				"vd.rut, " + //
				"vd.number, " + //
				"vd.name, " + //
				"vd.tradename, " + //
				"vd.address, " + //
				"vd.phone, " + //
				"vd.commune, " + //
				"vd.city, " + //
				"vd.email, " + //
				"vd.fax, " + //
				"vd.code, " + //
				"vd.domestic, " + //
				"vd.lastbulknumber, " + // 
        		"vd.logisticscode, " + //
        		"vd.gln, " + //
        		"vd.firstvevcddate, " + //
        		"vd.firstvevpddate "; //
						
			orderby = getOrderByString(mapGetVendorSQL, order);
		}
		
		String SQL =
			"SELECT " + //
				campos + //
			"FROM " + //
				"logistica.vendor AS vd " + //
			"WHERE " + //
			 	findBy + //
			 	vendorCondition + // 
			orderby; //
		
		return SQL;		
	}
	
	public VendorLogDTO[] getVendorsByIds(Long[] vendorIds) throws ServiceException{
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.getVendorsByIds");
		query.setParameterList("vendorids", vendorIds);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		List list = query.list();
		return (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);	
	}
	
	public VendorLogDTO[] getVendorsByIdsCourier(Long[] vendorIds) throws ServiceException{
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.getVendorsByIdsCourier");
		query.setParameterList("vendorids", vendorIds);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		List list = query.list();
		return (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);	
	}
	
	public VendorW[] getVendorsByIds(Long[] pvkeys, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException{
		
		String SQL = getSQLgetVendorsOfUser(pvkeys, order, false);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorW.class));
		if(isPaginated){
			query.setFirstResult((pagenumber-1) * rows);
		    query.setMaxResults(rows);	
		}
		List list = query.list();
		VendorW[] result = (VendorW[]) list.toArray(new VendorW[list.size()]);
		return result;
	}
	
	public VendorLogDTO[] getVendorsByIdsCourier(Long[] pvkeys, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException{
		
		String SQL = getSQLgetVendorsOfUserCourier(pvkeys, order, false);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		if(isPaginated){
			query.setFirstResult((pagenumber-1) * rows);
		    query.setMaxResults(rows);	
		}
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		return result;
	}
	
	public int countVendorsOfUser(Long[] vdkeys) throws ServiceException{
		String SQL = getSQLgetVendorsOfUser(vdkeys, null, true);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		Integer count = ((BigInteger)query.uniqueResult()).intValue();
		return count.intValue();
	}
	
	public VendorLogDTO[] findVendorsOfUserByCode(String code, Long[] vendorIds) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.findVendorsOfUserByCode");
		query.setString("code", "%" + code + "%");
		query.setParameterList("vendorids", vendorIds);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		List list = query.list();
		return (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);		
	}
	
	public VendorLogDTO[] findVendorsOfUserByName(String name, Long[] vendorIds) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.findVendorsOfUserByName");
		query.setString("name", "%" + name + "%");
		query.setParameterList("vendorids", vendorIds);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		List list = query.list();
		return (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);		
	}
	
	public VendorLogDTO[] findVendorsOfUserByInternalCode(String code, Long[] vendorIds) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.findVendorsOfUserByInternalCode");
		query.setString("code", "%" + code + "%");
		query.setParameterList("vendorids", vendorIds);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		List list = query.list();
		return (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);		
	}
	
	public VendorLogDTO[] findVendorsOfUserByNameCourier(String name, Long[] vendorIds) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.findVendorsOfUserByNameCourier");
		query.setString("name", "%" + name + "%");
		query.setParameterList("vendorids", vendorIds);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		List list = query.list();
		return (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);		
	}
	
	public VendorLogDTO[] findVendorsOfUserByInternalCodeCourier(String code, Long[] vendorIds) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.findVendorsOfUserByInternalCodeCourier");
		query.setString("code", "%" + code + "%");
		query.setParameterList("vendorids", vendorIds);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		List list = query.list();
		return (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);		
	}
	
	public VendorLogDTO[] findVendorsOfUserByCodeCourier(String code, Long[] vendorIds) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.findVendorsOfUserByCodeCourier");
		query.setString("code", "%" + code + "%");
		query.setParameterList("vendorids", vendorIds);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		List list = query.list();
		return (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);		
	}
	
	public VendorW[] getDomesticVendorsWithCourierByName(String name) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.getDomesticVendorsWithCourierByName");
		query.setString("name", "%" + name + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorW.class));
		List list = query.list();
		return (VendorW[]) list.toArray(new VendorW[list.size()]);		
	}
	
	public VendorLogDTO[] getDomesticVendorsWithOutCourier() throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.getDomesticVendorsWithOutCourier");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		List list = query.list();
		return (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);				
	}
	
	public VendorLogDTO[] getDomesticVendorsWithCourier() throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.getDomesticVendorsWithCourier");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		List list = query.list();
		return (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);				
	}
	
	public VendorW[] getDomesticVendorsWithOutCourierByName(String name) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.getVendorsWithOutCourierByName");
		query.setString("name", "%" + name + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorW.class));
		List list = query.list();
		return (VendorW[]) list.toArray(new VendorW[list.size()]);				
	}
	
	public VendorW[] getDomesticVendorsWithCourierByCode(String rut) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.getDomesticVendorsWithCourierByCode");
		query.setString("rut", "%" + rut + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorW.class));
		List list = query.list();
		return (VendorW[]) list.toArray(new VendorW[list.size()]);		
	}
	
	public VendorW[] getDomesticVendorsWithOutCourierByCode(String rut) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.getDomesticVendorsWithOutCourierByCode");
		query.setString("rut", "%" + rut + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorW.class));
		List list = query.list();
		return (VendorW[]) list.toArray(new VendorW[list.size()]);				
	}
	
	public VendorW[] getDomesticVendorsWithCourierByInternalCode(String code) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.getDomesticVendorsWithCourierByInternalCode");
		query.setString("code", "%" + code + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorW.class));
		List list = query.list();
		return (VendorW[]) list.toArray(new VendorW[list.size()]);		
	}
	
	public VendorW[] getDomesticVendorsWithOutCourierByInternalCode(String code) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.getDomesticVendorsWithOutCourierByInternalCode");
		query.setString("code", "%" + code + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorW.class));
		List list = query.list();
		return (VendorW[]) list.toArray(new VendorW[list.size()]);				
	}
	
	public VendorLogDTO[] findVendorsLogLikeNameAssigned(String name, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException {
		String SQL = getSQLVendorsLikeProperty(false, false, assignedids, order, true);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + name + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		if(isPaginated){
			query.setFirstResult((pagenumber-1) * rows);
		    query.setMaxResults(rows);	
		}
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		return result;
	}
	
	public int countVendorsLogLikeNameAssigned(String name, Long[] assignedids) throws ServiceException {
		String SQL = getSQLVendorsLikeProperty(false, true, assignedids, null, true);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + name + "%");
		Integer count = ((BigInteger)query.uniqueResult()).intValue();
		return count.intValue();
	}
	
	public VendorLogDTO[] findVendorsLogLikeNameNotAssigned(String name, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException {
		String SQL = getSQLVendorsLikeProperty(false, false, assignedids, order, false);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + name + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		if(isPaginated){
			query.setFirstResult((pagenumber-1) * rows);
		    query.setMaxResults(rows);	
		}
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		return result;
	}
	
	public int countVendorsLogLikeNameNotAssigned(String name, Long[] assignedids) throws ServiceException {
		String SQL = getSQLVendorsLikeProperty(false, true, assignedids, null, false);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + name + "%");
		Integer count = ((BigInteger)query.uniqueResult()).intValue();
		return count.intValue();
	}
	
	public VendorLogDTO[] findVendorsLogLikeCodeAssigned(String code, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException {
		String SQL = getSQLVendorsLikeProperty(true, false, assignedids, order, true);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + code + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		if(isPaginated){
			query.setFirstResult((pagenumber-1) * rows);
		    query.setMaxResults(rows);	
		}
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		return result;
	}
	
	public int countVendorsLogLikeCodeAssigned(String code, Long[] assignedids) throws ServiceException {
		String SQL = getSQLVendorsLikeProperty(true, true, assignedids, null, true);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + code + "%");
		Integer count = ((BigInteger)query.uniqueResult()).intValue();
		return count.intValue();
	}
	
	public VendorLogDTO[] findVendorsLogLikeCodeNotAssigned(String code, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException {
		String SQL = getSQLVendorsLikeProperty(true, false, assignedids, order, false);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + code + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		if(isPaginated){
			query.setFirstResult((pagenumber-1) * rows);
		    query.setMaxResults(rows);	
		}
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		return result;
	}
	
	public int countVendorsLogLikeCodeNotAssigned(String code, Long[] assignedids) throws ServiceException {
		String SQL = getSQLVendorsLikeProperty(true, true, assignedids, null, false);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("property", "%" + code + "%");
		Integer count = ((BigInteger)query.uniqueResult()).intValue();
		return count.intValue();
	}
	
	public VendorLogDTO[] getVendorsByLocationWithDating(Long locationid, Date since, Date until) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO JMA TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.getVendorsByLocationWithDating");
		query.setLong("locationid", locationid);
		query.setDate("since", since);
		query.setDate("until", until);
		
		
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		return result;
	}
	
	public VendorLogDTO[] getVendorsByExpirationRange(Date since, Date until) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO JMA TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.getVendorsByExpirationRange");
		query.setDate("since", since);
		query.setDate("until", until);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		return result;
	}
	
	public VendorLogDTO[] getDomesticVendorsOrderByName(){
		
		String whereCondition = "";
		
		String SQL = getSQLDomesticVendorsLikeProperty(whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		
		return result;
		
	}
	
	public VendorLogDTO[] findDomesticVendorsByName(String name){
		
		String whereCondition = "AND name LIKE :name ";
		
		String SQL = getSQLDomesticVendorsLikeProperty(whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("name", "%" + name.replace("%", "\\%") + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		
		return result;		
	}
	
	public VendorLogDTO[] findDomesticVendorsByRut(String rut){
		
		String whereCondition = "AND rut LIKE :rut ";
		
		String SQL = getSQLDomesticVendorsLikeProperty(whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("rut", "%" + rut.replace("%", "\\%") + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		
		return result;		
	}
	
	public VendorLogDTO[] findDomesticVendorsByInternalCode(String code){
		
		String whereCondition = "AND code LIKE :code ";
		
		String SQL = getSQLDomesticVendorsLikeProperty(whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("code", "%" + code.replace("%", "\\%") + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		
		return result;
	}
	
	private String getSQLDomesticVendorsLikeProperty(String whereCondition){
		
		String SQL =
			"SELECT " + //
				"id, " + //
		        "rut, " + //
	   		 	"number, " + //
		        "name, " + //
		        "tradename, " + //
		        "address, " + //
		        "phone, " + //
		        "commune, " + //
		        "city, " + //
		        "email, " + //
		        "fax, " + //
		        "code, " + //
		        "domestic, " + //
		        "lastbulknumber, " + //
		        "logisticscode " + //
			"FROM " + //
				"logistica.vendor " + //
			"WHERE " + //
				"domestic IS TRUE " + //
				whereCondition + //
			"ORDER BY " + //
				"name"; //
			
		return SQL;	
	}
	
	public VendorLogDTO[] getNotDomesticVendorsOrderByName(){
		
		String whereCondition = "";
		
		String SQL = getSQLNotDomesticVendorsLikeProperty(whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		
		return result;
		
	}
	
	public VendorLogDTO[] findNotDomesticVendorsByName(String name){
		
		String whereCondition = "AND name LIKE :name ";
		
		String SQL = getSQLNotDomesticVendorsLikeProperty(whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("name", "%" + name.replace("%", "\\%") + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		
		return result;		
	}
	
	public VendorLogDTO[] findNotDomesticVendorsByRut(String rut){
		
		String whereCondition = "AND rut LIKE :rut ";
		
		String SQL = getSQLNotDomesticVendorsLikeProperty(whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("rut", "%" + rut.replace("%", "\\%") + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		
		return result;		
	}
	
	public VendorLogDTO[] findNotDomesticVendorsByInternalCode(String code){
		
		String whereCondition = "AND code LIKE :code ";
		
		String SQL = getSQLNotDomesticVendorsLikeProperty(whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("code", "%" + code.replace("%", "\\%") + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorLogDTO.class));
		
		List list = query.list();
		VendorLogDTO[] result = (VendorLogDTO[]) list.toArray(new VendorLogDTO[list.size()]);
		
		return result;
	}
	
	private String getSQLNotDomesticVendorsLikeProperty(String whereCondition){
		
		String SQL =
			"SELECT " + //
				"id, " + //
		        "rut, " + //
	   		 	"number, " + //
		        "name, " + //
		        "tradename, " + //
		        "address, " + //
		        "phone, " + //
		        "commune, " + //
		        "city, " + //
		        "email, " + //
		        "fax, " + //
		        "code, " + //
		        "domestic, " + //
		        "lastbulknumber, " + //
		        "logisticscode " + //
			"FROM " + //
				"logistica.vendor " + //
			"WHERE " + //
				"domestic IS FALSE " + //
				whereCondition + //
			"ORDER BY " + //
				"name"; //
			
		return SQL;	
	}
	
	public Long[] getFirstVeVCDVendorsByDate(Date since, Date until) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.getFirstVeVCDVendorsByDate");
		query.setDate("since", since);
		query.setDate("until", until);
				
		List list = query.list();
		Long[] result = new Long[list.size()];
		// El valor en la query es un BigInteger y debe convertirse a Long
		for(int i = 0; i < list.size(); i++){
			result[i] = ((BigInteger)list.get(i)).longValue();			 
		}
		
		return result;		
	}
	
	public Long[] getFirstVeVPDVendorsByDate(Date since, Date until) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Vendor.getFirstVeVPDVendorsByDate");
		query.setDate("since", since);
		query.setDate("until", until);
				
		List list = query.list();
		Long[] result = new Long[list.size()];
		// El valor en la query es un BigInteger y debe convertirse a Long
		for(int i = 0; i < list.size(); i++){
			result[i] = ((BigInteger)list.get(i)).longValue();			 
		}
		
		return result;		
	}
		
	
	public int getCountVendors(){
		String sql = "SELECT COUNT(1) FROM logistica.vendor ";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(sql);
		int total = Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
		return total;
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "Vendor";
	}
	@Override
	protected void setEntityname() {
		entityname = "Vendor";
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
