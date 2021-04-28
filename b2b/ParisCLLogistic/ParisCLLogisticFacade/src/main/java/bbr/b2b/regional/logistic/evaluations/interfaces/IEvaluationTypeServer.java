package bbr.b2b.regional.logistic.evaluations.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.evaluations.entities.EvaluationType;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.EvaluationTypeW;

public interface IEvaluationTypeServer extends IElementServer<EvaluationType, EvaluationTypeW> {

	EvaluationTypeW addEvaluationType(EvaluationTypeW evaluationtype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	EvaluationTypeW[] getEvaluationTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	EvaluationTypeW updateEvaluationType(EvaluationTypeW evaluationtype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
