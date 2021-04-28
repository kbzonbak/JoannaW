package bbr.b2b.logistic.dvrorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderStateType;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateTypeW;

public interface IDvrOrderStateTypeServer extends IElementServer<DvrOrderStateType, DvrOrderStateTypeW> {

	DvrOrderStateTypeW addDvrOrderStateType(DvrOrderStateTypeW dvrorderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrOrderStateTypeW[] getDvrOrderStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DvrOrderStateTypeW updateDvrOrderStateType(DvrOrderStateTypeW dvrorderstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
