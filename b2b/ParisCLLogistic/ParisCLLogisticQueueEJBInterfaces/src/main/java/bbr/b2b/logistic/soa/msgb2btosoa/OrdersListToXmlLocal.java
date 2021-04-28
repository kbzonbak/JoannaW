package bbr.b2b.logistic.soa.msgb2btosoa;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.exceptions.ServiceException;

@Local
public interface OrdersListToXmlLocal {

	public void processMessage(String vendorCode, boolean acceptorders) throws ServiceException;

}
