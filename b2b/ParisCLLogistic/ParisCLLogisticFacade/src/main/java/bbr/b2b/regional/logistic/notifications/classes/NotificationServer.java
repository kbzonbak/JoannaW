package bbr.b2b.regional.logistic.notifications.classes;

import java.math.BigInteger;
import java.util.Date;
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
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.notifications.data.classes.NotificationDataDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.VendorNoticationDTO;
import bbr.b2b.regional.logistic.notifications.data.wrappers.NotificationW;
import bbr.b2b.regional.logistic.notifications.entities.Notification;
import bbr.b2b.regional.logistic.notifications.entities.NotificationId;
import bbr.b2b.regional.logistic.notifications.entities.NotificationTime;
import bbr.b2b.regional.logistic.notifications.entities.NotificationType;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/notifications/NotificationServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class NotificationServer extends BaseLogisticEJB3Server<Notification, NotificationId, NotificationW> implements NotificationServerLocal {


	@EJB
	VendorServerLocal vendorServerLocal;

	@EJB
	NotificationTypeServerLocal notificationtypeserver;

	@EJB
	NotificationTimeServerLocal notificationtimeserver;

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
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setNotificationtypeid(entity.getNotificationtype() != null ? new Long(entity.getNotificationtype().getId()) : new Long(0));
		wrapper.setNotificationtimeid(entity.getNotificationtime() != null ? new Long(entity.getNotificationtime().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Notification entity, NotificationW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorServerLocal.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getNotificationtypeid() != null && wrapper.getNotificationtypeid() > 0) { 
			NotificationType notificationtype = notificationtypeserver.getReferenceById(wrapper.getNotificationtypeid());
			if (notificationtype != null) { 
				entity.setNotificationtype(notificationtype);
			}
		}
		if (wrapper.getNotificationtimeid() != null && wrapper.getNotificationtimeid() > 0) { 
			NotificationTime notificationtime = notificationtimeserver.getReferenceById(wrapper.getNotificationtimeid());
			if (notificationtime != null) { 
				entity.setNotificationtime(notificationtime);
			}
		}
	}
	
	public Long[] getNotificationsTypeAssignedByVendor(Long vendorid){
		String SQL = "select distinct N.NOTIFICATIONTYPE_ID from LOGISTICA.NOTIFICATION N where N.VENDOR_ID = :vendorid";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		List<BigInteger> list = query.list();
		Long[] result = new Long[list.size()];
		for(int i=0;i<list.size();i++){
			result[i] = list.get(i).longValue();
		}
		return result;		
	}
	
	public Long[] getNotificationsTimeAssignedByVendor(Long vendorid){
		String SQL = "select distinct N.NOTIFICATIONTIME_ID from LOGISTICA.NOTIFICATION N where N.VENDOR_ID = :vendorid";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		List<BigInteger> list = query.list();
		Long[] result = new Long[list.size()];
		for(int i=0;i<list.size();i++){
			result[i] = list.get(i).longValue();
		}
		return result;		
	}
	
	public VendorNoticationDTO[] getVendorsToNotification(Date hour){
		String SQL = "select distinct "+
					"n.vendor_id as id, "+
					"v.name, " +
					"v.rut, "+
					"v.tradename "+
					"from "+
					"logistica.notification n "+
					"join logistica.vendor v on n.vendor_id = v.id "+
					"join logistica.notificationtime nt on n.notificationtime_id = nt.id "+
					"where nt.hourdate = :hour";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setTimestamp("hour", hour);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorNoticationDTO.class));
		List list = query.list();
		VendorNoticationDTO[] result = (VendorNoticationDTO[]) list.toArray(new VendorNoticationDTO[list.size()]);			
		return result;		
	}
	
	public NotificationDataDTO[] getNotificationsDataByVendor(Long vendorid, Date since,Date until){
		String SQL = "select " +
				"n.NOTIFICATIONTYPE_ID as notificationTypeId," +
				"nt.code as code," +
				"nt.description as description," +
				"CAST(count(distinct nd.id) AS INT)as totalLoad " +
				"from " +
				"logistica.notification n " +
				"join logistica.notificationtype nt on nt.id = n.notificationtype_id " +
				"join logistica.notificationdata nd on n.notificationtype_id = nd.notificationtype_id and n.vendor_id = nd.vendor_id " +
				"where n.vendor_id = :vendorid and (nd.loaddate BETWEEN :since and :until) " +
				"group by n.NOTIFICATIONTYPE_ID, nt.code, nt.description order by n.NOTIFICATIONTYPE_ID asc";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setTimestamp("since", since);
		query.setTimestamp("until", until);
		query.setResultTransformer(new LowerCaseResultTransformer(NotificationDataDTO.class));
		List list = query.list();
		NotificationDataDTO[] result = (NotificationDataDTO[]) list.toArray(new NotificationDataDTO[list.size()]);		
		return result;		
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
