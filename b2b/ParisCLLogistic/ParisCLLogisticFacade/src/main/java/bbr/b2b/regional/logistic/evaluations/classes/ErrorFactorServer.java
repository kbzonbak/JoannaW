package bbr.b2b.regional.logistic.evaluations.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ErrorFactorW;
import bbr.b2b.regional.logistic.evaluations.entities.ErrorFactor;
import bbr.b2b.regional.logistic.evaluations.entities.ErrorFactorId;
import bbr.b2b.regional.logistic.evaluations.entities.EvaluationCriteria;
import bbr.b2b.regional.logistic.evaluations.entities.ReceptionError;

@Stateless(name = "servers/evaluations/ErrorFactorServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ErrorFactorServer extends BaseLogisticEJB3Server<ErrorFactor, ErrorFactorId, ErrorFactorW> implements ErrorFactorServerLocal {


	@EJB
	ReceptionErrorServerLocal receptionerrorserver;

	@EJB
	EvaluationCriteriaServerLocal evaluationcriteriaserver;

	public ErrorFactorW addErrorFactor(ErrorFactorW errorfactor) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ErrorFactorW) addIdentifiable(errorfactor);
	}
	public ErrorFactorW[] getErrorFactors() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ErrorFactorW[]) getIdentifiables();
	}
	public ErrorFactorW updateErrorFactor(ErrorFactorW errorfactor) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ErrorFactorW) updateIdentifiable(errorfactor);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ErrorFactor entity, ErrorFactorW wrapper) {
		wrapper.setReceptionerrorid(entity.getReceptionerror() != null ? new Long(entity.getReceptionerror().getId()) : new Long(0));
		wrapper.setEvaluationcriteriaid(entity.getEvaluationcriteria() != null ? new Long(entity.getEvaluationcriteria().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(ErrorFactor entity, ErrorFactorW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getReceptionerrorid() != null && wrapper.getReceptionerrorid() > 0) { 
			ReceptionError receptionerror = receptionerrorserver.getReferenceById(wrapper.getReceptionerrorid());
			if (receptionerror != null) { 
				entity.setReceptionerror(receptionerror);
			}
		}
		if (wrapper.getEvaluationcriteriaid() != null && wrapper.getEvaluationcriteriaid() > 0) { 
			EvaluationCriteria evaluationcriteria = evaluationcriteriaserver.getReferenceById(wrapper.getEvaluationcriteriaid());
			if (evaluationcriteria != null) { 
				entity.setEvaluationcriteria(evaluationcriteria);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "ErrorFactor";
	}
	@Override
	protected void setEntityname() {
		entityname = "ErrorFactor";
	}
}
