package bbr.b2b.logistic.soa.queuehandler;

import javax.ejb.Local;
import javax.ejb.MessageDrivenContext;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;

@Local
public interface SOAHandlerLocal {

	void handleMessage(String message, MessageDrivenContext mdbContext, Long ticketNumber) throws OperationFailedException, NotFoundException;	

}
