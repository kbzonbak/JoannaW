package bbr.b2b.logistic.soa.msgb2btosoa;

import javax.ejb.Local;

import bbr.b2b.b2blink.logistic.xml.NotificacionReciboRec.NotificacionReciboRec;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;


@Local
public interface XmlToAckRecOcSoaLocal {

	public void processMessage(NotificacionReciboRec message) throws ServiceException;

}
