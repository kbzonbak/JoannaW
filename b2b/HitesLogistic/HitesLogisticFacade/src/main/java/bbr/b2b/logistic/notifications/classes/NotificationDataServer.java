package bbr.b2b.logistic.notifications.classes;

import java.time.LocalDateTime;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.notifications.entities.NotificationData;
import bbr.b2b.logistic.notifications.entities.NotificationType;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.entities.Vendor;
import bbr.b2b.logistic.notifications.data.wrappers.NotificationDataW;

@Stateless(name = "servers/notifications/NotificationDataServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class NotificationDataServer extends LogisticElementServer<NotificationData, NotificationDataW> implements NotificationDataServerLocal {


	@EJB
	VendorServerLocal vendorserver;

	@EJB
	NotificationTypeServerLocal notificationTypeserver;

	public NotificationDataW addNotificationData(NotificationDataW notificationdata) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (NotificationDataW) addElement(notificationdata);
	}
	public NotificationDataW[] getNotificationDatas() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (NotificationDataW[]) getIdentifiables();
	}
	public NotificationDataW updateNotificationData(NotificationDataW notificationdata) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (NotificationDataW) updateElement(notificationdata);
	}

	@Override
	protected void copyRelationsEntityToWrapper(NotificationData entity, NotificationDataW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setNotificationtypeid(entity.getNotificationType() != null ? new Long(entity.getNotificationType().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(NotificationData entity, NotificationDataW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getNotificationtypeid() != null && wrapper.getNotificationtypeid() > 0) { 
			NotificationType notificationType = notificationTypeserver.getReferenceById(wrapper.getNotificationtypeid());
			if (notificationType != null) { 
				entity.setNotificationType(notificationType);
			}
		}
	}

	public int doDeleteOldNotificationData(LocalDateTime date){
		String SQL = "delete from logistica.notificationdata where loaddate < :date";
		SQLQuery query = getSession().createSQLQuery(SQL);
		query.setParameter("date", date);
		return query.executeUpdate();		
	}

	
	@Override
	protected void setEntitylabel() {
		entitylabel = "NotificationData";
	}
	@Override
	protected void setEntityname() {
		entityname = "NotificationData";
	}
}
