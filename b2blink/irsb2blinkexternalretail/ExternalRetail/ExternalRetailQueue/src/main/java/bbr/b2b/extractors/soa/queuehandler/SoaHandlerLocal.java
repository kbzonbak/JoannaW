package bbr.b2b.extractors.soa.queuehandler;

import javax.ejb.Local;
import javax.ejb.MessageDrivenContext;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;

@Local
public interface SoaHandlerLocal {

	void handleMessage(String from, String message, MessageDrivenContext mdbContext) throws OperationFailedException, NotFoundException;	

}
