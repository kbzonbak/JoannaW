package bbr.b2b.logistic.queuehandler;

import javax.ejb.Local;
import javax.ejb.MessageDrivenContext;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;


@Local
public interface ChangeOrderStateHandlerLocal {

	void handleMessage(String message, String correlationid, MessageDrivenContext mdbContext) throws OperationFailedException, NotFoundException;	

}
