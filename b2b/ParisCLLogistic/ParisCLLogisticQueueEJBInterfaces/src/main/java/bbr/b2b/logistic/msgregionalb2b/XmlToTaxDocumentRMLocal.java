package bbr.b2b.logistic.msgregionalb2b;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.xml.qrm.QRM;

@Local
public interface XmlToTaxDocumentRMLocal {

	public void processMessage(QRM qrm) throws LoadDataException, NotFoundException, OperationFailedException, AccessDeniedException;
}
