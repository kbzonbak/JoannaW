package bbr.b2b.logistic.soa.msgb2btosoa;

import javax.ejb.Local;
import javax.xml.bind.JAXBException;

import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;

@Local
public interface XmlToInventarioProveedorLocal {
	public void processMessage(Long ticketNumber, InventarioProveedor inventarioProveedor) throws Exception;
}
