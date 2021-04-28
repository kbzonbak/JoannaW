package bbr.b2b.logistic.soa.msgb2btosoa;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;

@Local
public interface OrderSendToXmlLocal {

	public void processMessage(OrderW order, VendorW vendor, boolean acceptorders, int count, int totalcount) throws NotFoundException, OperationFailedException;
}
