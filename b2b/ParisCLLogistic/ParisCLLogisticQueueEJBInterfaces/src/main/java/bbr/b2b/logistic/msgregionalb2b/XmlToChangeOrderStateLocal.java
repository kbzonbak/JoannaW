package bbr.b2b.logistic.msgregionalb2b;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.xml.qchangeorderstate.QCAMBIOESTADO;

@Local
public interface XmlToChangeOrderStateLocal {

	public Long processMessage(QCAMBIOESTADO qcambioestado) throws LoadDataException, ServiceException;

}
