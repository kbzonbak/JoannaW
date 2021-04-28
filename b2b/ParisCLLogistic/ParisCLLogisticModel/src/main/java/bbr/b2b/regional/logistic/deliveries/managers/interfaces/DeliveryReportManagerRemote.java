package bbr.b2b.regional.logistic.deliveries.managers.interfaces;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.regional.logistic.managers.interfaces.IDeliveryReportManager;

@Remote @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public interface DeliveryReportManagerRemote extends IDeliveryReportManager {

}
