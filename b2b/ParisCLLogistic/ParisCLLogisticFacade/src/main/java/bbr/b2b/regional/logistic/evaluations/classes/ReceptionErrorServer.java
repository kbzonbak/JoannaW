package bbr.b2b.regional.logistic.evaluations.classes;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.evaluations.entities.ReceptionError;
import bbr.b2b.regional.logistic.evaluations.data.classes.ReceptionErrorReportDataDTO;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ReceptionErrorW;

@Stateless(name = "servers/evaluations/ReceptionErrorServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReceptionErrorServer extends LogisticElementServer<ReceptionError, ReceptionErrorW> implements ReceptionErrorServerLocal {


	public ReceptionErrorW addReceptionError(ReceptionErrorW receptionerror) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionErrorW) addElement(receptionerror);
	}
	public ReceptionErrorW[] getReceptionErrors() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionErrorW[]) getIdentifiables();
	}
	public ReceptionErrorW updateReceptionError(ReceptionErrorW receptionerror) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionErrorW) updateElement(receptionerror);
	}

	public ReceptionErrorReportDataDTO[] getReceptionErrorsOfEvaluation(Long receptionevaluationid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = "SELECT RER.CODE, " +
						"RER.NAME " +
						"FROM LOGISTICA.RECEPTIONERROR AS RER " +
							"JOIN LOGISTICA.EVALUATIONDETAIL AS ED ON ED.RECEPTIONERROR_ID = RER.ID " +
							"JOIN LOGISTICA.RECEPTIONEVALUATION AS REV ON REV.ID = ED.RECEPTIONEVALUATION_ID " +
						"WHERE REV.ID = :receptionevaluationid";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("receptionevaluationid", receptionevaluationid);
		query.setResultTransformer(new LowerCaseResultTransformer(ReceptionErrorReportDataDTO.class));

		List list = query.list();
		return (ReceptionErrorReportDataDTO[]) list.toArray(new ReceptionErrorReportDataDTO[list.size()]);
		
	}
	
	@Override
	protected void copyRelationsEntityToWrapper(ReceptionError entity, ReceptionErrorW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(ReceptionError entity, ReceptionErrorW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "ReceptionError";
	}
	@Override
	protected void setEntityname() {
		entityname = "ReceptionError";
	}
}
