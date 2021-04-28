package bbr.b2b.regional.logistic.evaluations.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.evaluations.entities.EvaluationCriteria;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.EvaluationCriteriaW;

@Stateless(name = "servers/evaluations/EvaluationCriteriaServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EvaluationCriteriaServer extends LogisticElementServer<EvaluationCriteria, EvaluationCriteriaW> implements EvaluationCriteriaServerLocal {


	public EvaluationCriteriaW addEvaluationCriteria(EvaluationCriteriaW evaluationcriteria) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (EvaluationCriteriaW) addElement(evaluationcriteria);
	}
	public EvaluationCriteriaW[] getEvaluationCriterias() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (EvaluationCriteriaW[]) getIdentifiables();
	}
	public EvaluationCriteriaW updateEvaluationCriteria(EvaluationCriteriaW evaluationcriteria) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (EvaluationCriteriaW) updateElement(evaluationcriteria);
	}

	@Override
	protected void copyRelationsEntityToWrapper(EvaluationCriteria entity, EvaluationCriteriaW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(EvaluationCriteria entity, EvaluationCriteriaW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "EvaluationCriteria";
	}
	@Override
	protected void setEntityname() {
		entityname = "EvaluationCriteria";
	}
}
