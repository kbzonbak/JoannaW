package bbr.b2b.regional.logistic.evaluations.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.evaluations.entities.OcDelEvaluation;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.OcDelEvaluationW;

public interface IOcDelEvaluationServer extends IElementServer<OcDelEvaluation, OcDelEvaluationW> {

	OcDelEvaluationW addOcDelEvaluation(OcDelEvaluationW ocdelevaluation) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OcDelEvaluationW[] getOcDelEvaluations() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OcDelEvaluationW updateOcDelEvaluation(OcDelEvaluationW ocdelevaluation) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
