package bbr.b2b.logistic.soa.msgb2btosoa;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;

@Local
public interface RecOCToXmlLocal {

	public void processMessage(String vendorCode, String buyerCode) throws NotFoundException, OperationFailedException, AccessDeniedException, ServiceException;

}
