package bbr.b2b.logistic.soa.msgb2btosoa;

import javax.ejb.Local;

import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;;

@Local
public interface XmlToPLProveedorLocal {

	public void processMessage(PLProveedor message, Long ticketNumber) throws LoadDataException, OperationFailedException;

}
