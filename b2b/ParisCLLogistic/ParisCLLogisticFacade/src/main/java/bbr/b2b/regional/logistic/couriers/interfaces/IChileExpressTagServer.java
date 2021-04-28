package bbr.b2b.regional.logistic.couriers.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.ChileExpressTagW;
import bbr.b2b.regional.logistic.couriers.entities.ChileExpressTag;

public interface IChileExpressTagServer extends IElementServer<ChileExpressTag, ChileExpressTagW>{

	ChileExpressTagW addChileExpressTag(ChileExpressTagW chileExpressTag) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ChileExpressTagW[] getChileExpressTags() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ChileExpressTagW updateChileExpressTag(ChileExpressTagW chileExpressTag) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
