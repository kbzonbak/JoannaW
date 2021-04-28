package bbr.b2b.regional.logistic.evaluations.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.evaluations.entities.ErrorFactor;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ErrorFactorW;
import bbr.b2b.regional.logistic.evaluations.entities.ErrorFactorId;
public interface IErrorFactorServer extends IGenericServer<ErrorFactor, ErrorFactorId, ErrorFactorW> {

	ErrorFactorW addErrorFactor(ErrorFactorW errorfactor) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ErrorFactorW[] getErrorFactors() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ErrorFactorW updateErrorFactor(ErrorFactorW errorfactor) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
