package bbr.b2b.logistic.msgregionalb2b;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.xml.qplimport.QPLIMPORTADOREQ;

@Local
public interface XmlToPLImportLocal {

	public String processMessage(QPLIMPORTADOREQ qplimportadoreq) throws LoadDataException, ServiceException;
//	void doAddDeliveries(QPLIMPORTADOREQ qplimportadoreq, OrderW order, ArrayList boxList, ArrayList palletList, ArrayList bulkDetailList) throws LoadDataException, ServiceException;
}
