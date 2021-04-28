package bbr.b2b.regional.logistic.buyorders.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.ClientW;
import bbr.b2b.regional.logistic.buyorders.entities.Client;

@Stateless(name = "servers/buyorders/ClientServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ClientServer extends LogisticElementServer<Client, ClientW> implements ClientServerLocal {


	public ClientW addClient(ClientW client) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ClientW) addElement(client);
	}
	public ClientW[] getClients() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ClientW[]) getIdentifiables();
	}
	public ClientW updateClient(ClientW client) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ClientW) updateElement(client);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Client entity, ClientW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Client entity, ClientW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Client";
	}
	@Override
	protected void setEntityname() {
		entityname = "Client";
	}
}
