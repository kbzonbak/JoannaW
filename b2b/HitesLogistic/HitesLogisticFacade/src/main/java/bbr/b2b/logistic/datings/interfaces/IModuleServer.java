package bbr.b2b.logistic.datings.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.datings.data.wrappers.ModuleW;
import bbr.b2b.logistic.datings.entities.Module;
import bbr.b2b.logistic.datings.report.classes.ModuleDataDTO;

public interface IModuleServer extends IElementServer<Module, ModuleW> {

	ModuleW addModule(ModuleW module) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ModuleW[] getModules() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ModuleW updateModule(ModuleW module) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ModuleDataDTO[] getModulesActiveByLocation(Long locationid);
	ModuleW[] getModulesByInterval(LocalDateTime since, LocalDateTime until, boolean hasChangeDay);
}
