package bbr.b2b.logistic.soa.msgb2btosoa;

import javax.ejb.Local;

import bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden.NotificacionReciboOrden;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;

@Local
public interface XmlToAckOcSoaLocal {

	public void processMessage(NotificacionReciboOrden message) throws ServiceException;

}