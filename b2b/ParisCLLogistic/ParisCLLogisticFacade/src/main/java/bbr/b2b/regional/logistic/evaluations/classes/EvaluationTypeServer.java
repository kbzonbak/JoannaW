package bbr.b2b.regional.logistic.evaluations.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.evaluations.entities.EvaluationType;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.EvaluationTypeW;

@Stateless(name = "servers/evaluations/EvaluationTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EvaluationTypeServer extends LogisticElementServer<EvaluationType, EvaluationTypeW> implements EvaluationTypeServerLocal {


	public EvaluationTypeW addEvaluationType(EvaluationTypeW evaluationtype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (EvaluationTypeW) addElement(evaluationtype);
	}
	public EvaluationTypeW[] getEvaluationTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (EvaluationTypeW[]) getIdentifiables();
	}
	public EvaluationTypeW updateEvaluationType(EvaluationTypeW evaluationtype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (EvaluationTypeW) updateElement(evaluationtype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(EvaluationType entity, EvaluationTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(EvaluationType entity, EvaluationTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "EvaluationType";
	}
	@Override
	protected void setEntityname() {
		entityname = "EvaluationType";
	}
}
