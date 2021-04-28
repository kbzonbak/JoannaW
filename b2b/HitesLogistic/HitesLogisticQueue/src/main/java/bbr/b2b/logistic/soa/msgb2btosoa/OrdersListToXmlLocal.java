package bbr.b2b.logistic.soa.msgb2btosoa;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;

@Local
public interface OrdersListToXmlLocal {

	public void processMessage(String vendorRut, String buyerRut, boolean acceptorders) throws NotFoundException, OperationFailedException;

}
