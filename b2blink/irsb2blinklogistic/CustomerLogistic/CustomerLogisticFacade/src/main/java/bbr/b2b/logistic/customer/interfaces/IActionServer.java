package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.customer.entities.Action;
import bbr.b2b.logistic.customer.data.wrappers.ActionW;

public interface IActionServer extends IElementServer<Action, ActionW> {

	ActionW addAction(ActionW Action) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ActionW[] getActions() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ActionW updateAction(ActionW Action) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
