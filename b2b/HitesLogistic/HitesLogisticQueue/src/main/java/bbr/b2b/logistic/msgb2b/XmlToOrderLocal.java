package bbr.b2b.logistic.msgb2b;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.xml.order.Orden;


public interface XmlToOrderLocal {

	public Long processMessageOrder(Orden orderParser) throws LoadDataException, ServiceException;
}

