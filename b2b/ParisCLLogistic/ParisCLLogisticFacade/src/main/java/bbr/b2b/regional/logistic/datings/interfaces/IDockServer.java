package bbr.b2b.regional.logistic.datings.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.datings.entities.Dock;
import bbr.b2b.regional.logistic.datings.data.wrappers.DockW;

public interface IDockServer extends IElementServer<Dock, DockW> {

	DockW addDock(DockW dock) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DockW[] getDocks() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DockW updateDock(DockW dock) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
