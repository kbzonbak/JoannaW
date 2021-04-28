package bbr.b2b.logistic.parameters.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.parameters.entities.Parameter;
import bbr.b2b.logistic.parameters.data.wrappers.ParameterW;

@Stateless(name = "servers/parameters/ParameterServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ParameterServer extends LogisticElementServer<Parameter, ParameterW> implements ParameterServerLocal {


	public ParameterW addParameter(ParameterW parameter) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ParameterW) addElement(parameter);
	}
	public ParameterW[] getParameters() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ParameterW[]) getIdentifiables();
	}
	public ParameterW updateParameter(ParameterW parameter) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ParameterW) updateElement(parameter);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Parameter entity, ParameterW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Parameter entity, ParameterW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Parameter";
	}
	@Override
	protected void setEntityname() {
		entityname = "Parameter";
	}
}
