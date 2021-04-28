package bbr.b2b.logistic.soa.msgb2btosoa;

import javax.ejb.Local;

import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;

@Local
public interface XmlToInventProvSoaLocal {

	public void processMessage(InventarioProveedor message, Long ticketNumber) throws ServiceException;

}
