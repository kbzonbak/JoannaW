package bbr.b2b.regional.logistic.datings.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.datings.entities.Module;
import bbr.b2b.regional.logistic.datings.data.wrappers.ModuleW;

public interface IModuleServer extends IElementServer<Module, ModuleW> {

	ModuleW addModule(ModuleW module) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ModuleW[] getModules() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ModuleW updateModule(ModuleW module) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
