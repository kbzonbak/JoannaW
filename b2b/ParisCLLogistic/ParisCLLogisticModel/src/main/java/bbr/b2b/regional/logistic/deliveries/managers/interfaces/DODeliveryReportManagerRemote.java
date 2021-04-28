package bbr.b2b.regional.logistic.deliveries.managers.interfaces;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.regional.logistic.managers.interfaces.IDODeliveryReportManager;

@Remote @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public interface DODeliveryReportManagerRemote extends IDODeliveryReportManager {

}
