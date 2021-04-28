package bbr.b2b.logistic.soa.msgb2btosoa;

import javax.ejb.Local;
import javax.xml.bind.JAXBException;

import bbr.b2b.b2blink.logistic.xml.OC_customer.Order;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;

@Local
public interface XmlToOrderLocal {
	public void processMessage(Order order) throws ServiceException, JAXBException, OperationFailedException;
}
