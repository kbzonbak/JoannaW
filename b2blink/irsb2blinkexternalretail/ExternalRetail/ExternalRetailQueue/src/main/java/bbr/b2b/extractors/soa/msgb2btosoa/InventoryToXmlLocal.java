package bbr.b2b.extractors.soa.msgb2btosoa;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;

@Local
public interface InventoryToXmlLocal {

	String processMessage(String vendorRut, String buyerRut) throws OperationFailedException, LoadDataException, NotFoundException;
}
