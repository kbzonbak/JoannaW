package bbr.b2b.regional.logistic.evaluations.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.evaluations.entities.EvaluationDetail;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.EvaluationDetailW;
import bbr.b2b.regional.logistic.evaluations.entities.EvaluationDetailId;
import bbr.b2b.regional.logistic.evaluations.report.classes.EvaluationDetailReportDataDTO;
public interface IEvaluationDetailServer extends IGenericServer<EvaluationDetail, EvaluationDetailId, EvaluationDetailW> {

	EvaluationDetailW addEvaluationDetail(EvaluationDetailW evaluationdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	EvaluationDetailW[] getEvaluationDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	EvaluationDetailW updateEvaluationDetail(EvaluationDetailW evaluationdetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	EvaluationDetailReportDataDTO[] getEvaluationDetailReport(Date since, Date until, Long vendorid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	int getEvaluationDetailCountReport(Date since, Date until, Long vendorid) throws AccessDeniedException, OperationFailedException;	
}
