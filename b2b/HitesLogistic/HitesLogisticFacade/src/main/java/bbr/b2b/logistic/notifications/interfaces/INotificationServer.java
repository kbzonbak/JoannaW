package bbr.b2b.logistic.notifications.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.notifications.data.wrappers.NotificationW;
import bbr.b2b.logistic.notifications.entities.Notification;
import bbr.b2b.logistic.notifications.entities.NotificationId;
import bbr.b2b.logistic.notifications.report.classes.ContactNotificationDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationCSVDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationDataDTO;
public interface INotificationServer extends IGenericServer<Notification, NotificationId, NotificationW> {

	NotificationW addNotification(NotificationW notification) throws AccessDeniedException, OperationFailedException, NotFoundException;
	NotificationW[] getNotifications() throws AccessDeniedException, OperationFailedException, NotFoundException;
	NotificationW updateNotification(NotificationW notification) throws AccessDeniedException, OperationFailedException, NotFoundException;
	Long[] getNotificationsTypeAssignedByContact(Long contactid);
	Long[] getNotificationsTimeAssignedByContact(Long contactid);
	ContactNotificationDTO[] getContactsToNotification(LocalDateTime hour);
	NotificationDataDTO[] getNotificationsDataByContact(Long contactid, LocalDateTime since, LocalDateTime until);
	List<NotificationCSVDTO> doExportCSVNotificationsData(Long contactid, Long vendorid, LocalDateTime since,LocalDateTime until);

	
}
