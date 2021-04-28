package bbr.b2b.regional.logistic.evaluations.classes;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.regional.logistic.datings.entities.Dating;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ReceptionEvaluationW;
import bbr.b2b.regional.logistic.evaluations.entities.ReceptionEvaluation;

@Stateless(name = "servers/evaluations/ReceptionEvaluationServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReceptionEvaluationServer extends LogisticElementServer<ReceptionEvaluation, ReceptionEvaluationW> implements ReceptionEvaluationServerLocal {


	@EJB
	DatingServerLocal datingserver;

	public ReceptionEvaluationW addReceptionEvaluation(ReceptionEvaluationW receptionevaluation) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionEvaluationW) addElement(receptionevaluation);
	}
	public ReceptionEvaluationW[] getReceptionEvaluations() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionEvaluationW[]) getIdentifiables();
	}
	public ReceptionEvaluationW updateReceptionEvaluation(ReceptionEvaluationW receptionevaluation) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ReceptionEvaluationW) updateElement(receptionevaluation);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ReceptionEvaluation entity, ReceptionEvaluationW wrapper) {
		wrapper.setDatingid(entity.getDating() != null ? new Long(entity.getDating().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(ReceptionEvaluation entity, ReceptionEvaluationW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDatingid() != null && wrapper.getDatingid() > 0) { 
			Dating dating = datingserver.getReferenceById(wrapper.getDatingid());
			if (dating != null) { 
				entity.setDating(dating);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "ReceptionEvaluation";
	}
	@Override
	protected void setEntityname() {
		entityname = "ReceptionEvaluation";
	}
	
	public ReceptionEvaluationW[] getReceptionEvaluationsofDeliveryStateTypeBetweenDates(Long statetype, Date since, Date until) throws OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.evaluations.entities.ReceptionEvaluation.getReceptionEvaluationsofDeliveryStateTypeBetweenDates");
		query.setLong("statetype", statetype);
		query.setDate("since", since);
		query.setDate("until", until);
		query.setResultTransformer(new LowerCaseResultTransformer(ReceptionEvaluationW.class));
		List list = query.list();
		return (ReceptionEvaluationW[]) list.toArray(new ReceptionEvaluationW[list.size()]);
	}
	
}
