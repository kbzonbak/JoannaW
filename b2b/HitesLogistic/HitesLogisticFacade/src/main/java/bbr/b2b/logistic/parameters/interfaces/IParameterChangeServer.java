package bbr.b2b.logistic.parameters.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.parameters.entities.ParameterChange;
import bbr.b2b.logistic.parameters.data.wrappers.ParameterChangeW;

public interface IParameterChangeServer extends IElementServer<ParameterChange, ParameterChangeW> {

	ParameterChangeW addParameterChange(ParameterChangeW parameterchange) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ParameterChangeW[] getParameterChanges() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ParameterChangeW updateParameterChange(ParameterChangeW parameterchange) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
