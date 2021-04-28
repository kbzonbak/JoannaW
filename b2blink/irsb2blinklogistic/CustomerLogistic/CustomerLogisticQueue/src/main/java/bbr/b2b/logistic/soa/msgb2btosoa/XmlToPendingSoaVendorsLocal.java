package bbr.b2b.logistic.soa.msgb2btosoa;

import javax.ejb.Local;

import bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes.SolicitudProveedoresOrdenesPendientes;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;

@Local
public interface XmlToPendingSoaVendorsLocal {

	public void processMessage(SolicitudProveedoresOrdenesPendientes message) throws ServiceException;

}
