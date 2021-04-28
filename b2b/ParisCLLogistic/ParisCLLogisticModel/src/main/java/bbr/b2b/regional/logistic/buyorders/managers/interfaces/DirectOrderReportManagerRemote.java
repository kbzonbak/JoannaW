package bbr.b2b.regional.logistic.buyorders.managers.interfaces;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.regional.logistic.managers.interfaces.IDirectOrderReportManager;

@Remote @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public interface DirectOrderReportManagerRemote extends IDirectOrderReportManager {

}
