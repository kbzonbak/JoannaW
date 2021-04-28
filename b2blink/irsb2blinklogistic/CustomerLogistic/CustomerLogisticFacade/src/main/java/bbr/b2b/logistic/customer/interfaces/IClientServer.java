package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.entities.Client;
import bbr.b2b.logistic.customer.data.wrappers.ClientW;

public interface IClientServer extends IElementServer<Client, ClientW> {

	ClientW addClient(ClientW client) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ClientW[] getClients() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ClientW updateClient(ClientW client) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
