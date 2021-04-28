package bbr.b2b.regional.logistic.managers.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.ContactInfoDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.ContactNotificationInitParam;
import bbr.b2b.regional.logistic.notifications.data.classes.ContactReportArrayDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.ContactReportInitParam;
import bbr.b2b.regional.logistic.notifications.data.classes.DeleteContactInitParam;
import bbr.b2b.regional.logistic.notifications.data.classes.NotificationConfigDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.NotificationDataDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.NotificationTimeArrayDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.NotificationTypesArrayDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.VendorNoticationDTO;

public interface INotificationsReportManager {

	public NotificationTypesArrayDTO getNotificationTypes();
	
	public NotificationTimeArrayDTO getNotificationTimes();
	
	public void createTime();
	
	public BaseResultDTO doAddContact(ContactNotificationInitParam initParam);
	
	public BaseResultDTO doDeleteContact(DeleteContactInitParam initParam);
	
	public ContactReportArrayDTO getContactsReport(ContactReportInitParam initParam, Boolean isPaginated);
	
	public ContactInfoDTO doUpdateContact(ContactInfoDTO initParam);
	
	public ContactInfoDTO getContactInfo(Long contactid);
	
	public NotificationConfigDTO getVendorNotificationsConfig(String vendorcode);
	
	public BaseResultDTO doAddOrUpdateNotificationConfig(NotificationConfigDTO initParam);
	
	public void doAddNotificationData(String vendorcode,String number,String type);
	
	public VendorNoticationDTO[] getVendorsToNotification(Date hour);
	
	public NotificationDataDTO[] getNotificationsDataByVendor(String vendorcode,Date hour);
	
	public int doDeleteOldNotificationData();
	
}
