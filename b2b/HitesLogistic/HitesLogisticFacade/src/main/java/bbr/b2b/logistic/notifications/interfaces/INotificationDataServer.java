package bbr.b2b.logistic.notifications.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.notifications.data.wrappers.NotificationDataW;
import bbr.b2b.logistic.notifications.entities.NotificationData;

public interface INotificationDataServer extends IElementServer<NotificationData, NotificationDataW> {

	NotificationDataW addNotificationData(NotificationDataW notificationdata) throws AccessDeniedException, OperationFailedException, NotFoundException;
	NotificationDataW[] getNotificationDatas() throws AccessDeniedException, OperationFailedException, NotFoundException;
	NotificationDataW updateNotificationData(NotificationDataW notificationdata) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int doDeleteOldNotificationData(LocalDateTime date);
}
