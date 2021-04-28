package bbr.b2b.regional.logistic.evaluations.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.evaluations.entities.EvaluationCriteria;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.EvaluationCriteriaW;

public interface IEvaluationCriteriaServer extends IElementServer<EvaluationCriteria, EvaluationCriteriaW> {

	EvaluationCriteriaW addEvaluationCriteria(EvaluationCriteriaW evaluationcriteria) throws AccessDeniedException, OperationFailedException, NotFoundException;
	EvaluationCriteriaW[] getEvaluationCriterias() throws AccessDeniedException, OperationFailedException, NotFoundException;
	EvaluationCriteriaW updateEvaluationCriteria(EvaluationCriteriaW evaluationcriteria) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
