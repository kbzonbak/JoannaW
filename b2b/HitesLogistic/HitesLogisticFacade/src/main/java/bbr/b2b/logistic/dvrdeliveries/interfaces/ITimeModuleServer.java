package bbr.b2b.logistic.dvrdeliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.dvrdeliveries.entities.TimeModule;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.TimeModuleW;

public interface ITimeModuleServer extends IElementServer<TimeModule, TimeModuleW> {

	TimeModuleW addTimeModule(TimeModuleW timemodule) throws AccessDeniedException, OperationFailedException, NotFoundException;
	TimeModuleW[] getTimeModules() throws AccessDeniedException, OperationFailedException, NotFoundException;
	TimeModuleW updateTimeModule(TimeModuleW timemodule) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
