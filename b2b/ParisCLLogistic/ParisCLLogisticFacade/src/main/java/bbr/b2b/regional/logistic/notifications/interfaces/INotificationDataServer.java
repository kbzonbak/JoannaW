package bbr.b2b.regional.logistic.notifications.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.notifications.entities.NotificationData;
import bbr.b2b.regional.logistic.notifications.data.wrappers.NotificationDataW;

public interface INotificationDataServer extends IElementServer<NotificationData, NotificationDataW> {

	NotificationDataW addNotificationData(NotificationDataW notificationdata) throws AccessDeniedException, OperationFailedException, NotFoundException;
	NotificationDataW[] getNotificationDatas() throws AccessDeniedException, OperationFailedException, NotFoundException;
	NotificationDataW updateNotificationData(NotificationDataW notificationdata) throws AccessDeniedException, OperationFailedException, NotFoundException;
	public int doDeleteOldNotificationData(Date date);
	
}
