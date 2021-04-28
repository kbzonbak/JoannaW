package bbr.b2b.regional.logistic.managers.interfaces;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Remote @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public interface MakeFillRateManagerRemote extends IMakeFillRateManager {

}
