package bbr.b2b.logistic.msgb2b;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.xml.reception.Recepcion;

public interface XmlToReceptionLocal {

	public String processMessageOrder(Recepcion receptionParser) throws LoadDataException, ServiceException;
}
