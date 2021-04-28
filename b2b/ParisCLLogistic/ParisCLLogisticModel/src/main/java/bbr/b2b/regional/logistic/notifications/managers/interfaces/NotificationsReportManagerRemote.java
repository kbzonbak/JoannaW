package bbr.b2b.regional.logistic.notifications.managers.interfaces;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.regional.logistic.managers.interfaces.INotificationsReportManager;

@Remote @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public interface NotificationsReportManagerRemote extends INotificationsReportManager{

}
