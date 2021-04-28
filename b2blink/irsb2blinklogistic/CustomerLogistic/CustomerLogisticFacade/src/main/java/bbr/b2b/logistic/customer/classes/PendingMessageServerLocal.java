package bbr.b2b.logistic.customer.classes;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.customer.data.wrappers.PendingMessageW;
import bbr.b2b.logistic.customer.interfaces.IPendingMessageServer;

@Local
public interface PendingMessageServerLocal extends IPendingMessageServer {

	PendingMessageW[] getMessageToSend(int size) throws OperationFailedException, NotFoundException;

	int doDeletePendingMessage();
}
