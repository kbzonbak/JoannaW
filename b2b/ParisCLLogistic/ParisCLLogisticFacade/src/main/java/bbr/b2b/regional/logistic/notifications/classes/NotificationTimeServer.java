package bbr.b2b.regional.logistic.notifications.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.notifications.entities.NotificationTime;
import bbr.b2b.regional.logistic.notifications.data.wrappers.NotificationTimeW;

@Stateless(name = "servers/notifications/NotificationTimeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class NotificationTimeServer extends LogisticElementServer<NotificationTime, NotificationTimeW> implements NotificationTimeServerLocal {


	public NotificationTimeW addNotificationTime(NotificationTimeW notificationtime) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (NotificationTimeW) addElement(notificationtime);
	}
	public NotificationTimeW[] getNotificationTimes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (NotificationTimeW[]) getIdentifiables();
	}
	public NotificationTimeW updateNotificationTime(NotificationTimeW notificationtime) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (NotificationTimeW) updateElement(notificationtime);
	}

	@Override
	protected void copyRelationsEntityToWrapper(NotificationTime entity, NotificationTimeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(NotificationTime entity, NotificationTimeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "NotificationTime";
	}
	@Override
	protected void setEntityname() {
		entityname = "NotificationTime";
	}
}
