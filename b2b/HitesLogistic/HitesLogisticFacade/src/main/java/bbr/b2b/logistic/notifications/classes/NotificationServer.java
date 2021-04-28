package bbr.b2b.logistic.notifications.classes;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.notifications.data.wrappers.NotificationW;
import bbr.b2b.logistic.notifications.entities.Contact;
import bbr.b2b.logistic.notifications.entities.Notification;
import bbr.b2b.logistic.notifications.entities.NotificationId;
import bbr.b2b.logistic.notifications.entities.NotificationTime;
import bbr.b2b.logistic.notifications.entities.NotificationType;
import bbr.b2b.logistic.notifications.report.classes.ContactNotificationDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationCSVDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationDataDTO;

@Stateless(name = "servers/notifications/NotificationServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class NotificationServer extends BaseLogisticEJB3Server<Notification, NotificationId, NotificationW> implements NotificationServerLocal {


	@EJB
	ContactServerLocal contactserver;

	@EJB
	NotificationTimeServerLocal notificationtimeserver;

	@EJB
	NotificationTypeServerLocal notificationtypeserver;

	public NotificationW addNotification(NotificationW notification) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (NotificationW) addIdentifiable(notification);
	}
	public NotificationW[] getNotifications() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (NotificationW[]) getIdentifiables();
	}
	public NotificationW updateNotification(NotificationW notification) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (NotificationW) updateIdentifiable(notification);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Notification entity, NotificationW wrapper) {
		wrapper.setContactid(entity.getContact() != null ? new Long(entity.getContact().getId()) : new Long(0));
		wrapper.setNotificationtimeid(entity.getNotificationtime() != null ? new Long(entity.getNotificationtime().getId()) : new Long(0));
		wrapper.setNotificationtypeid(entity.getNotificationtype() != null ? new Long(entity.getNotificationtype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Notification entity, NotificationW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getContactid() != null && wrapper.getContactid() > 0) { 
			Contact contact = contactserver.getReferenceById(wrapper.getContactid());
			if (contact != null) { 
				entity.setContact(contact);
			}
		}
		if (wrapper.getNotificationtimeid() != null && wrapper.getNotificationtimeid() > 0) { 
			NotificationTime notificationtime = notificationtimeserver.getReferenceById(wrapper.getNotificationtimeid());
			if (notificationtime != null) { 
				entity.setNotificationtime(notificationtime);
			}
		}
		if (wrapper.getNotificationtypeid() != null && wrapper.getNotificationtypeid() > 0) { 
			NotificationType notificationtype = notificationtypeserver.getReferenceById(wrapper.getNotificationtypeid());
			if (notificationtype != null) { 
				entity.setNotificationtype(notificationtype);
			}
		}
	}

	public Long[] getNotificationsTypeAssignedByContact(Long contactid){
		String SQL = 	"select distinct N.NOTIFICATIONTYPE_ID " + //
						"from LOGISTICA.NOTIFICATION N " + //
						"where N.CONTACT_ID = :contactid ";
		SQLQuery query = getSession().createSQLQuery(SQL);
		query.setLong("contactid", contactid);
		List<?> list = query.list();
		Long[] result = new Long[list.size()];
		for(int i=0;i<list.size();i++){
			result[i] = ((BigInteger) list.get(i)).longValue();
		}
		return result;		
	}
	
	public Long[] getNotificationsTimeAssignedByContact(Long contactid){
		String SQL = 	"select distinct N.NOTIFICATIONTIME_ID " + //
						"from LOGISTICA.NOTIFICATION N " + //
						"where N.CONTACT_ID = :contactid ";
		SQLQuery query = getSession().createSQLQuery(SQL);
		query.setLong("contactid", contactid);
		List<?> list = query.list();
		Long[] result = new Long[list.size()];
		for(int i=0;i<list.size();i++){
			result[i] = ((BigInteger) list.get(i)).longValue();
		}
		return result;		
	}
	
	public ContactNotificationDTO[] getContactsToNotification(LocalDateTime hour){
		String SQL = "select distinct "+
					"n.contact_id as contactid, "+
					"v.name as vendorname, " +
					"v.code as vendorcode, "+
					"v.tradename "+
					"from "+
					"logistica.notification n "+
					"join logistica.contact c on n.contact_id = c.id "+
					"join logistica.vendor v on c.vendor_id = v.id "+
					"join logistica.notificationtime nt on n.notificationtime_id = nt.id "+
					"where nt.hourdate\\:\\:time = :hour ";
		SQLQuery query = getSession().createSQLQuery(SQL);
		System.out.println(query.getQueryString());
		query.setParameter("hour", hour);
		query.setResultTransformer(new LowerCaseResultTransformer(ContactNotificationDTO.class));
		List<?> list = (List<?>) query.list();
		ContactNotificationDTO[] result = list.toArray(new ContactNotificationDTO[list.size()]);			
		return result;		
	}
	
	public NotificationDataDTO[] getNotificationsDataByContact(Long contactid, LocalDateTime since, LocalDateTime until){

		String SQL = 	"SELECT " +
						"n.NOTIFICATIONTYPE_ID as notificationTypeId," +
						"nt.code as code," +
						"nt.description as description," +
						"CAST(count(distinct nd.id) AS INT)as totalLoad " +
						"from " +
						"logistica.notification n " +
						"join logistica.contact c on n.contact_id = c.id "+
						"join logistica.vendor v on c.vendor_id = v.id "+
						"join logistica.notificationtype nt on nt.id = n.notificationtype_id " +
						"join logistica.notificationdata nd on n.notificationtype_id = nd.notificationtype_id and c.vendor_id = nd.vendor_id " +
						"where n.contact_id = :contactid and (nd.loaddate BETWEEN :since and :until) " +
						"group by n.NOTIFICATIONTYPE_ID, nt.code, nt.description order by n.NOTIFICATIONTYPE_ID asc";

		SQLQuery query = getSession().createSQLQuery(SQL);
		query.setLong("contactid", contactid);
		query.setParameter("since", since);
		query.setParameter("until", until);
		query.setResultTransformer(new LowerCaseResultTransformer(NotificationDataDTO.class));
		List<?> list = query.list();
		return list.toArray(new NotificationDataDTO[list.size()]);		
	}

	public List<NotificationCSVDTO> doExportCSVNotificationsData(Long contactid, Long vendorid, LocalDateTime since,LocalDateTime until){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.notifications.entities.Notification.doExportCSV");
		query.setLong("contact_id", contactid);
		query.setLong("vendor_id", vendorid);
		query.setParameter("since", since);
		query.setParameter("until", until);
		query.setResultTransformer(new LowerCaseResultTransformer(NotificationCSVDTO.class));
		List<NotificationCSVDTO> list = query.list(); 
		return list;
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "Notification";
	}
	@Override
	protected void setEntityname() {
		entityname = "Notification";
	}
}
