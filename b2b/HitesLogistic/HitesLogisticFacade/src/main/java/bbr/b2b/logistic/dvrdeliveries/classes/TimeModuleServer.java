package bbr.b2b.logistic.dvrdeliveries.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.dvrdeliveries.entities.TimeModule;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.TimeModuleW;

@Stateless(name = "servers/dvrdeliveries/TimeModuleServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TimeModuleServer extends LogisticElementServer<TimeModule, TimeModuleW> implements TimeModuleServerLocal {


	public TimeModuleW addTimeModule(TimeModuleW timemodule) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (TimeModuleW) addElement(timemodule);
	}
	public TimeModuleW[] getTimeModules() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (TimeModuleW[]) getIdentifiables();
	}
	public TimeModuleW updateTimeModule(TimeModuleW timemodule) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (TimeModuleW) updateElement(timemodule);
	}

	@Override
	protected void copyRelationsEntityToWrapper(TimeModule entity, TimeModuleW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(TimeModule entity, TimeModuleW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "TimeModule";
	}
	@Override
	protected void setEntityname() {
		entityname = "TimeModule";
	}
}
