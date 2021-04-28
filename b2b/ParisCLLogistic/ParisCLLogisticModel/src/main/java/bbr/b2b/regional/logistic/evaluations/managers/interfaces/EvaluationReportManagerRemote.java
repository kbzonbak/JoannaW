package bbr.b2b.regional.logistic.evaluations.managers.interfaces;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.regional.logistic.managers.interfaces.IEvaluationReportManager;

@Remote @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public interface EvaluationReportManagerRemote extends IEvaluationReportManager{

}
