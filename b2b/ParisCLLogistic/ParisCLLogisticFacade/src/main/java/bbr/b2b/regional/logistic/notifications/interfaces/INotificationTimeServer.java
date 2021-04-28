package bbr.b2b.regional.logistic.notifications.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.notifications.entities.NotificationTime;
import bbr.b2b.regional.logistic.notifications.data.wrappers.NotificationTimeW;

public interface INotificationTimeServer extends IElementServer<NotificationTime, NotificationTimeW> {

	NotificationTimeW addNotificationTime(NotificationTimeW notificationtime) throws AccessDeniedException, OperationFailedException, NotFoundException;
	NotificationTimeW[] getNotificationTimes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	NotificationTimeW updateNotificationTime(NotificationTimeW notificationtime) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
