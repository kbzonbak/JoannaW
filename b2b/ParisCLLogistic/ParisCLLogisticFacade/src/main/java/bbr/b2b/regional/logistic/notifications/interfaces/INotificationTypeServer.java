package bbr.b2b.regional.logistic.notifications.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.notifications.entities.NotificationType;
import bbr.b2b.regional.logistic.notifications.data.wrappers.NotificationTypeW;

public interface INotificationTypeServer extends IElementServer<NotificationType, NotificationTypeW> {

	NotificationTypeW addNotificationType(NotificationTypeW notificationtype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	NotificationTypeW[] getNotificationTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	NotificationTypeW updateNotificationType(NotificationTypeW notificationtype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
