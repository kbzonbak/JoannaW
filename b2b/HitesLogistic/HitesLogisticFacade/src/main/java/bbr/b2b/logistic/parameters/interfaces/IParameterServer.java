package bbr.b2b.logistic.parameters.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.parameters.entities.Parameter;
import bbr.b2b.logistic.parameters.data.wrappers.ParameterW;

public interface IParameterServer extends IElementServer<Parameter, ParameterW> {

	ParameterW addParameter(ParameterW parameter) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ParameterW[] getParameters() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ParameterW updateParameter(ParameterW parameter) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
