package bbr.b2b.regional.logistic.notifications.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.notifications.entities.Notification;
import bbr.b2b.regional.logistic.notifications.data.classes.NotificationDataDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.VendorNoticationDTO;
import bbr.b2b.regional.logistic.notifications.data.wrappers.NotificationW;
import bbr.b2b.regional.logistic.notifications.entities.NotificationId;
public interface INotificationServer extends IGenericServer<Notification, NotificationId, NotificationW> {

	NotificationW addNotification(NotificationW notification) throws AccessDeniedException, OperationFailedException, NotFoundException;
	NotificationW[] getNotifications() throws AccessDeniedException, OperationFailedException, NotFoundException;
	NotificationW updateNotification(NotificationW notification) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public Long[] getNotificationsTypeAssignedByVendor(Long vendorid);
	public Long[] getNotificationsTimeAssignedByVendor(Long vendorid);
	public NotificationDataDTO[] getNotificationsDataByVendor(Long vendorid, Date since,Date until);
	public VendorNoticationDTO[] getVendorsToNotification(Date hour);
	
}
