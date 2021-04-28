package bbr.b2b.logistic.notifications.classes;

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
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.notifications.entities.Contact;
import bbr.b2b.logistic.notifications.report.classes.ContactReportDTO;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.entities.Vendor;
import bbr.b2b.logistic.notifications.data.wrappers.ContactW;

@Stateless(name = "servers/notifications/ContactServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ContactServer extends LogisticElementServer<Contact, ContactW> implements ContactServerLocal {

	@EJB
	VendorServerLocal vendorserver;

	private static Map<String, String> mapGetContactsSQL = new HashMap<String, String>();
	static {
		mapGetContactsSQL.put("NAME", "CO.NAME");
		mapGetContactsSQL.put("LASTNAME", "CO.LASTNAME");
		mapGetContactsSQL.put("POSITION", "CO.CARGO");
		mapGetContactsSQL.put("EMAIL", "CO.EMAIL");
		mapGetContactsSQL.put("COUNTEVENTS", "count(distinct N.NOTIFICATIONTYPE_ID)");
		mapGetContactsSQL.put("COUNTHOURS", "count(distinct N.NOTIFICATIONTIME_ID)");
	}	
	
	
	public ContactW addContact(ContactW contact) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ContactW) addElement(contact);
	}
	public ContactW[] getContacts() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ContactW[]) getIdentifiables();
	}
	public ContactW updateContact(ContactW contact) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ContactW) updateElement(contact);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Contact entity, ContactW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Contact entity, ContactW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
	}

	private String getContactsResportSQL(Boolean isQueryToCount, OrderCriteriaDTO[] order) throws AccessDeniedException{
		String SQL = "SELECT ";
		String orderCondition = "";
		
		if(isQueryToCount){
			SQL += "COUNT(CO.ID) ";
		}
		
		else{
			SQL += "CO.ID as contactid, " +
					"CO.NAME as name," +
					"CO.LASTNAME as lastname," +
					"CO.CARGO as position," +
					"CO.EMAIL as email," +
					"CO.VENDOR_ID as vendorid ";
			orderCondition = getOrderByString(mapGetContactsSQL, order);
		}
		SQL += "from LOGISTICA.CONTACT CO " +
				"where CO.VENDOR_ID = :vendorid ";
		SQL += orderCondition;
		
		return SQL;
		
	}
	
	public ContactReportDTO[] getContactsReport(Long vendorid,Integer pagenumber,Integer rows,OrderCriteriaDTO[] order, Boolean isPaginated) throws AccessDeniedException{
		String SQL = getContactsResportSQL(false, order);
		SQLQuery query = getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setResultTransformer(new LowerCaseResultTransformer(ContactReportDTO.class));
		if(isPaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List<?> list = query.list();
		return list.toArray(new ContactReportDTO[list.size()]);		
	}
	
	public int countContactsReport(Long vendorid) throws AccessDeniedException{
		String SQL = getContactsResportSQL(true, null);
		SQLQuery query = getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		return Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
	}
	

	
	@Override
	protected void setEntitylabel() {
		entitylabel = "Contact";
	}
	@Override
	protected void setEntityname() {
		entityname = "Contact";
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
