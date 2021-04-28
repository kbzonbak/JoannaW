package bbr.b2b.regional.logistic.datings.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.datings.data.wrappers.DockTypeW;
import bbr.b2b.regional.logistic.datings.entities.DockType;
import bbr.b2b.regional.logistic.datings.report.classes.DockTypeDTO;

public interface IDockTypeServer extends IElementServer<DockType, DockTypeW> {

	DockTypeW addDockType(DockTypeW docktype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DockTypeW[] getDockTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DockTypeW updateDockType(DockTypeW docktype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DockTypeDTO[] getDockTypesByLocation(Long locationid);
}
