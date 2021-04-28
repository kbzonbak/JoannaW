package bbr.b2b.logistic.soa.msgb2btosoa;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.customer.data.wrappers.BuyerW;
import bbr.b2b.logistic.customer.data.wrappers.ReceptionW;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;

@Local
public interface RecOCSendToXmlLocal {

	public void processMessage(ReceptionW orderreception, VendorW vendor, BuyerW buyer, int count, int totalcount) throws NotFoundException, OperationFailedException, AccessDeniedException, ServiceException;

}
