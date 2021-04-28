package bbr.b2b.regional.logistic.kpi.managers.interfaces;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.regional.logistic.managers.interfaces.IKPIReportManager;

@Remote @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public interface KPIReportManagerRemote extends IKPIReportManager{

}
