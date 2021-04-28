package bbr.b2b.regional.logistic.taxdocuments.managers.interfaces;

import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.regional.logistic.managers.interfaces.ITaxDocumentReportManager;

@Remote @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public interface TaxDocumentReportManagerRemote extends ITaxDocumentReportManager {

}