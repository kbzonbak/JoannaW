package bbr.b2b.logistic.queuehandler;

import javax.ejb.Local;
import javax.ejb.MessageDrivenContext;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;

@Local
public interface EvaluationHandlerLocal {

	void handleMessage(String message, MessageDrivenContext mdbContext) throws OperationFailedException, NotFoundException;	
}
