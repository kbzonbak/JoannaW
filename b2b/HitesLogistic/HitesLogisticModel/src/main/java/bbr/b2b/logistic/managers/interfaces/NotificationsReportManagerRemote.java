package bbr.b2b.logistic.managers.interfaces;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Remote
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public interface NotificationsReportManagerRemote extends INotificationsReportManager{

}
