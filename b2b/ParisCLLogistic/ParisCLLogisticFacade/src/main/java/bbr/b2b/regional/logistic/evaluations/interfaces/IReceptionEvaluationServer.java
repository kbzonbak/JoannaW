package bbr.b2b.regional.logistic.evaluations.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.evaluations.entities.ReceptionEvaluation;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ReceptionEvaluationW;

public interface IReceptionEvaluationServer extends IElementServer<ReceptionEvaluation, ReceptionEvaluationW> {

	ReceptionEvaluationW addReceptionEvaluation(ReceptionEvaluationW receptionevaluation) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReceptionEvaluationW[] getReceptionEvaluations() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReceptionEvaluationW updateReceptionEvaluation(ReceptionEvaluationW receptionevaluation) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReceptionEvaluationW[] getReceptionEvaluationsofDeliveryStateTypeBetweenDates(Long statetype, Date since, Date until) throws OperationFailedException, NotFoundException;

}
