package bbr.b2b.logistic.msgregionalb2b;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.xml.qreception.QRECEPCION;

@Local
public interface XmlToReceptionLocal {

	public Boolean processMessage(QRECEPCION qrecepcion) throws LoadDataException, ServiceException;
	public void doChangeStateOrder(QRECEPCION recepcionParser) throws LoadDataException, ServiceException;
}
