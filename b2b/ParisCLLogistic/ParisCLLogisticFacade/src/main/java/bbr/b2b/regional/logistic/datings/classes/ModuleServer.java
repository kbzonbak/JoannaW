package bbr.b2b.regional.logistic.datings.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.datings.entities.Module;
import bbr.b2b.regional.logistic.datings.data.wrappers.ModuleW;

@Stateless(name = "servers/datings/ModuleServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ModuleServer extends LogisticElementServer<Module, ModuleW> implements ModuleServerLocal {


	public ModuleW addModule(ModuleW module) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ModuleW) addElement(module);
	}
	public ModuleW[] getModules() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ModuleW[]) getIdentifiables();
	}
	public ModuleW updateModule(ModuleW module) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ModuleW) updateElement(module);
	}
	
	@Override
	protected void copyRelationsEntityToWrapper(Module entity, ModuleW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Module entity, ModuleW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Module";
	}
	@Override
	protected void setEntityname() {
		entityname = "Module";
	}
}
