package bbr.b2b.regional.logistic.mobile.managers.interfaces;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.regional.logistic.managers.interfaces.IMobileDeliveryReportManager;

@Remote @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public interface MobileDeliveryReportManagerRemote extends IMobileDeliveryReportManager{

}
