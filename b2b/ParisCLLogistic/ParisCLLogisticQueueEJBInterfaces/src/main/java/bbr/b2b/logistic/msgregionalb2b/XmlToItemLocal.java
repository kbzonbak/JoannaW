package bbr.b2b.logistic.msgregionalb2b;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.xml.qitem.QPRODUCTO;

@Local
public interface XmlToItemLocal {

	public String processMessage(QPRODUCTO qproducto) throws LoadDataException, ServiceException;

}
