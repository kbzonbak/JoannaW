package bbr.b2b.regional.logistic.evaluations.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.evaluations.entities.ReceptionError;
import bbr.b2b.regional.logistic.evaluations.data.classes.ReceptionErrorReportDataDTO;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ReceptionErrorW;

public interface IReceptionErrorServer extends IElementServer<ReceptionError, ReceptionErrorW> {

	ReceptionErrorW addReceptionError(ReceptionErrorW receptionerror) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReceptionErrorW[] getReceptionErrors() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReceptionErrorW updateReceptionError(ReceptionErrorW receptionerror) throws AccessDeniedException, OperationFailedException, NotFoundException;

	ReceptionErrorReportDataDTO[] getReceptionErrorsOfEvaluation(Long receptionevaluationid) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
