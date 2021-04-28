package bbr.b2b.logistic.managers.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.logistic.notifications.report.classes.ContactInfoDTO;
import bbr.b2b.logistic.notifications.report.classes.ContactNotificationDTO;
import bbr.b2b.logistic.notifications.report.classes.ContactNotificationInitParamDTO;
import bbr.b2b.logistic.notifications.report.classes.ContactReportArrayDTO;
import bbr.b2b.logistic.notifications.report.classes.ContactReportInitParamDTO;
import bbr.b2b.logistic.notifications.report.classes.DeleteContactInitParamDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationCSVDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationConfigDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationDataDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationTimeArrayDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationTypesArrayDTO;

public interface INotificationsReportManager {
	
	
	public NotificationTypesArrayDTO getNotificationTypes();
	public NotificationTimeArrayDTO getNotificationTimes();
	public NotificationConfigDTO getContactNotificationsConfig(Long contactid);
	public ContactReportArrayDTO getContactsReport(ContactReportInitParamDTO initParamDTO, Boolean isPaginated);
	public BaseResultDTO doAddContact(ContactNotificationInitParamDTO initParamDTO);
	public ContactInfoDTO getContactInfo(Long contactid);
	public ContactInfoDTO doUpdateContact(ContactInfoDTO initParamDTO);
	public BaseResultDTO doAddOrUpdateNotificationConfig(NotificationConfigDTO initParamDTO);
	public BaseResultDTO doDeleteContact(DeleteContactInitParamDTO initParamDTO);	
	public int doDeleteOldNotificationData();
	public ContactNotificationDTO[] getContactToNotification(LocalDateTime hour);
	public NotificationDataDTO[] getNotificationsDataByContact(Long contactid, LocalDateTime when);
	public List<NotificationCSVDTO> doExportCSVNotificationsData(Long contactid, Long vendorid, LocalDateTime when);
	public void doAddNotificationData(Long vendorid, String number, String type);
	
}
