package bbr.b2b.logistic.dvrorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderState;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateW;

public interface IDvrOrderStateServer extends IElementServer<DvrOrderState, DvrOrderStateW> {

	DvrOrderStateW addDvrOrderState(DvrOrderStateW dvrorderstate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrOrderStateW[] getDvrOrderStates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrOrderStateW updateDvrOrderState(DvrOrderStateW dvrorderstate) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
