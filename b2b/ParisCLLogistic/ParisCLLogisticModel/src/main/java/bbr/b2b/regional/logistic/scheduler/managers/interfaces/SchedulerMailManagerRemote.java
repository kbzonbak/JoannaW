package bbr.b2b.regional.logistic.scheduler.managers.interfaces;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.regional.logistic.managers.interfaces.ISchedulerMailManager;

@Remote @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public interface SchedulerMailManagerRemote extends ISchedulerMailManager {

}
