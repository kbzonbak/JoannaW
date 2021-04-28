package bbr.b2b.regional.logistic.notifications.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.notifications.entities.NotificationType;
import bbr.b2b.regional.logistic.notifications.data.wrappers.NotificationTypeW;

@Stateless(name = "servers/notifications/NotificationTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class NotificationTypeServer extends LogisticElementServer<NotificationType, NotificationTypeW> implements NotificationTypeServerLocal {


	public NotificationTypeW addNotificationType(NotificationTypeW notificationtype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (NotificationTypeW) addElement(notificationtype);
	}
	public NotificationTypeW[] getNotificationTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (NotificationTypeW[]) getIdentifiables();
	}
	public NotificationTypeW updateNotificationType(NotificationTypeW notificationtype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (NotificationTypeW) updateElement(notificationtype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(NotificationType entity, NotificationTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(NotificationType entity, NotificationTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "NotificationType";
	}
	@Override
	protected void setEntityname() {
		entityname = "NotificationType";
	}
}
