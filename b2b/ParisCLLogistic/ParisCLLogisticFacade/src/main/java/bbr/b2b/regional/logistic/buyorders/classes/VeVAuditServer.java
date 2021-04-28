package bbr.b2b.regional.logistic.buyorders.classes;

import java.math.BigInteger;
import java.time.LocalDateTime;
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
import bbr.b2b.regional.logistic.buyorders.data.wrappers.VeVAuditW;
import bbr.b2b.regional.logistic.buyorders.entities.VeVAudit;
import bbr.b2b.regional.logistic.buyorders.entities.VeVUpdateType;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVAuditDetailDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVUpdateAuditReportDataDTO;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/buyorders/ VeVAuditServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VeVAuditServer extends LogisticElementServer< VeVAudit,  VeVAuditW> implements VeVAuditServerLocal {

	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	VeVUpdateTypeServerLocal vevupdatetypeserver;
	
	
	private Map<String, String> mapVeVAuditSQL = new HashMap<String, String>();
	{
		mapVeVAuditSQL.put("UPDATEDATE", "updatedate");
		mapVeVAuditSQL.put("USERNAME", "veva.username");	
		mapVeVAuditSQL.put("USERTYPE", "veva.usertype");		
		mapVeVAuditSQL.put("USERENTERPRISE", "veva.userenterprisename");		
		mapVeVAuditSQL.put("VENDORNAME", "ve.name");
		mapVeVAuditSQL.put("UPDATETYPE", "vevut.description");
	}
	
	public VeVAuditW addVeVAudit(VeVAuditW vevaudit) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VeVAuditW) addElement(vevaudit);
	}
	public VeVAuditW[] getVeVAudits() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VeVAuditW[]) getIdentifiables();
	}
	public  VeVAuditW updateVeVAudit(VeVAuditW vevaudit) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (VeVAuditW) updateElement(vevaudit);
	}

	@Override
	protected void copyRelationsEntityToWrapper(VeVAudit entity, VeVAuditW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setVevupdatetypeid(entity.getVevupdatetype() != null ? new Long(entity.getVevupdatetype().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(VeVAudit entity, VeVAuditW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getVevupdatetypeid() != null && wrapper.getVevupdatetypeid() > 0) { 
			VeVUpdateType vevupdatetype = vevupdatetypeserver.getReferenceById(wrapper.getVevupdatetypeid());
			if (vevupdatetype != null) { 
				entity.setVevupdatetype(vevupdatetype);
			}
		}
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "VeVAudit";
	}
	@Override
	protected void setEntityname() {
		entityname = "VeVAudit";
	}
	
	public VeVUpdateAuditReportDataDTO[] getVeVUpdateAuditReportByVendorDateTypeAndSearchText(Long vendorid, Date since, Date until, Long vevupdatetypeid, String searchtext, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition = "";
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND veva.vendor_id = :vendorid ";
		}
		if (vevupdatetypeid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND veva.vevupdatetype_id = :vevupdatetypeid ";	
		}
		if (!searchtext.equals("")) {
			whereCondition += "AND veva.itemvalue LIKE :searchtext ";	
		}
		
		String SQL = getQueryString(2, orderby, whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (vevupdatetypeid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vevupdatetypeid", vevupdatetypeid);
		}
		if (!searchtext.equals("")) {
			query.setString("searchtext", "%" + searchtext + "%");	
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(VeVUpdateAuditReportDataDTO.class));	
		
		if (ispaginated) { //Si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		VeVUpdateAuditReportDataDTO[] result = (VeVUpdateAuditReportDataDTO[])list.toArray(new VeVUpdateAuditReportDataDTO[list.size()]);
		
		return result;
	}
		
	public int countVeVUpdateAuditReportByVendorDateTypeAndSearchText(Long vendorid, Date since, Date until, Long vevupdatetypeid, String searchtext) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition = "";
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND veva.vendor_id = :vendorid ";
		}
		if (vevupdatetypeid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND veva.vevupdatetype_id = :vevupdatetypeid ";	
		}
		if (!searchtext.equals("")) {
			whereCondition += "AND veva.itemvalue LIKE :searchtext ";	
		}
		
		String SQL = getQueryString(1, null, whereCondition);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (vevupdatetypeid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vevupdatetypeid", vevupdatetypeid);
		}
		if (!searchtext.equals("")) {
			query.setString("searchtext", "%" + searchtext + "%");	
		}
		
		int result = ((BigInteger)query.uniqueResult()).intValue();
		
		return result;		
	}
	
	public VeVAuditDetailDTO[] getVeVAuditDetailData(Long vendorid, String date, String typedescription) throws OperationFailedException, AccessDeniedException{
		
		String SQL =
				"SELECT " + //
					"veva.username AS username, " + //
					"veva.usertype AS usertype, " + //
					"veva.userenterprisename AS userenterprisename, " + //
					"ve.name AS vendorname, " + //
					"vevut.description AS vevaudittype, " + //
					"veva.item AS item, " + //
					"veva.itemvalue AS itemvalue, " + //
					"veva.initialdata AS initialdata, " + //
					"veva.finaldata AS finaldata, " + //
					"logistica.tostr(veva.when1) AS date " + //
				"FROM " + //
					"logistica.eom_vevaudit AS veva JOIN " + //
					"logistica.eom_vevupdatetype AS vevut ON vevut.id = veva.vevupdatetype_id JOIN " + //
					"logistica.vendor AS ve ON ve.id = veva.vendor_id " + //
				"WHERE " + //
					"veva.vendor_id =:vendorid AND vevut.description = :typedescription AND logistica.tostr(veva.when1) =:date "; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setString("date", date);
		query.setString("typedescription", typedescription);		
		query.setResultTransformer(new LowerCaseResultTransformer(VeVAuditDetailDTO.class));
		List list = query.list();
		VeVAuditDetailDTO[] result = (VeVAuditDetailDTO[])list.toArray(new VeVAuditDetailDTO[list.size()]);
		
		return result;
		
	}
	
	public VeVAuditDetailDTO[] getExcelVeVUpdateAuditReportData(Long vendorid, Date since, Date until, Long vevupdatetypeid, String searchtext) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition = "";
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND veva.vendor_id = :vendorid ";
		}
		
		if (vevupdatetypeid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND veva.vevupdatetype_id = :vevupdatetypeid ";	
		}
		
		if (!searchtext.equals("")) {
			whereCondition += "AND veva.itemvalue LIKE :searchtext ";	
		}
		
		String SQL =
				"SELECT " + //
					"logistica.tostr(veva.when1) AS date, " + //
					"veva.username AS username, " + //
					"veva.usertype AS usertype, " + //
					"veva.userenterprisename AS userenterprisename, " + //
					"ve.name AS vendorname, " + //
					"vevut.description AS vevaudittype, " + //
					"veva.item AS item, " + //
					"veva.itemvalue AS itemvalue, " + //
					"veva.initialdata AS initialdata, " + //
					"veva.finaldata AS finaldata " + //			
				"FROM " + //
					"logistica.eom_vevaudit AS veva JOIN " + //
					"logistica.eom_vevupdatetype AS vevut ON vevut.id = veva.vevupdatetype_id JOIN " + //
					"logistica.vendor AS ve ON ve.id = veva.vendor_id " + //
				"WHERE " + //
					"veva.when1 BETWEEN :since AND :until " + //
					whereCondition + //
				"ORDER BY " + //
					"veva.when1 ASC"; //
					
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (vevupdatetypeid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vevupdatetypeid", vevupdatetypeid);
		}
		if (!searchtext.equals("")) {
			query.setString("searchtext", "%" + searchtext + "%");	
		}		
		query.setResultTransformer(new LowerCaseResultTransformer(VeVAuditDetailDTO.class));
		
		List list = query.list();
		VeVAuditDetailDTO[] result = (VeVAuditDetailDTO[])list.toArray(new VeVAuditDetailDTO[list.size()]);
		
		return result;		
	}
	
	private String getQueryString(int queryType, OrderCriteriaDTO[] orderCriteria, String whereCondition) throws AccessDeniedException {
		String SQL = "";
		String openWith = "";
		String closeWith = "";
		String orderBy = "";
		
		if (queryType == 1) {
			openWith = "WITH audit AS("; //
			closeWith =
					") " + //
					"SELECT " + //
						"COUNT(1) " + //
					"FROM " + //
						"audit";
		}
		else {
			orderBy = getOrderByString(mapVeVAuditSQL, orderCriteria);
		}
		
		SQL +=
			openWith + //
			"SELECT DISTINCT " + //	
				"logistica.tostr(veva.when1) AS updatedate, " + //	
				"veva.username AS username, " + //
				"veva.usertype AS usertype, " + //	
				"veva.userenterprisename AS userenterprise, " + //
				"ve.id AS vendorid, " + //
				"ve.name AS vendorname, " + //
				"ve.rut AS vendorrut, " + //
				"vevut.id AS updatetypeid, " + //
				"vevut.description AS updatetype " + //
			"FROM " + //
				"logistica.eom_vevaudit AS veva JOIN " + //
				"logistica.eom_vevupdatetype AS vevut ON vevut.id = veva.vevupdatetype_id JOIN " + //
				"logistica.vendor AS ve ON ve.id = veva.vendor_id " + //
			"WHERE " + //
				"veva.when1 BETWEEN :since AND :until " + //
				whereCondition + //
			orderBy + //
			closeWith; //

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
}
