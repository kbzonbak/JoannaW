package bbr.b2b.regional.logistic.evaluations.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.OcDelEvaluationW;
import bbr.b2b.regional.logistic.evaluations.entities.EvaluationType;
import bbr.b2b.regional.logistic.evaluations.entities.OcDelEvaluation;
import bbr.b2b.regional.logistic.vendors.classes.VendorDepartmentRankingServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.VendorDepartmentRanking;
import bbr.b2b.regional.logistic.vendors.entities.VendorDepartmentRankingId;

@Stateless(name = "servers/evaluations/OcDelEvaluationServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OcDelEvaluationServer extends LogisticElementServer<OcDelEvaluation, OcDelEvaluationW> implements OcDelEvaluationServerLocal {


	@EJB
	VendorDepartmentRankingServerLocal vendordepartmentrankingserver;

	@EJB
	EvaluationTypeServerLocal evaluationtypeserver;

	public OcDelEvaluationW addOcDelEvaluation(OcDelEvaluationW ocdelevaluation) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OcDelEvaluationW) addElement(ocdelevaluation);
	}
	public OcDelEvaluationW[] getOcDelEvaluations() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OcDelEvaluationW[]) getIdentifiables();
	}
	public OcDelEvaluationW updateOcDelEvaluation(OcDelEvaluationW ocdelevaluation) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OcDelEvaluationW) updateElement(ocdelevaluation);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OcDelEvaluation entity, OcDelEvaluationW wrapper) {
		wrapper.setVendorid(entity.getVendordepartmentranking().getId() != null ? new Long(entity.getVendordepartmentranking().getId().getVendorid()) : new Long(0));
		wrapper.setRankingid(entity.getVendordepartmentranking().getId() != null ? new Long(entity.getVendordepartmentranking().getId().getRankingid()) : new Long(0));
		wrapper.setDepartmentid(entity.getVendordepartmentranking().getId() != null ? new Long(entity.getVendordepartmentranking().getId().getDepartmentid()) : new Long(0));
		wrapper.setEvaluationtypeid(entity.getEvaluationtype() != null ? new Long(entity.getEvaluationtype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(OcDelEvaluation entity, OcDelEvaluationW wrapper) throws OperationFailedException, NotFoundException {
		if ((wrapper.getVendorid() != null && wrapper.getVendorid() > 0) && (wrapper.getRankingid() != null && wrapper.getRankingid() > 0) && (wrapper.getDepartmentid() != null && wrapper.getDepartmentid() > 0)) {
			VendorDepartmentRankingId key = new VendorDepartmentRankingId();
			key.setVendorid(wrapper.getVendorid());
			key.setRankingid(wrapper.getRankingid());
			key.setDepartmentid(wrapper.getDepartmentid());
			VendorDepartmentRanking var = vendordepartmentrankingserver.getReferenceById(key);
			if (var != null) { 
				entity.setVendordepartmentranking(var);
			}
		}
		if (wrapper.getEvaluationtypeid() != null && wrapper.getEvaluationtypeid() > 0) { 
			EvaluationType evaluationtype = evaluationtypeserver.getReferenceById(wrapper.getEvaluationtypeid());
			if (evaluationtype != null) { 
				entity.setEvaluationtype(evaluationtype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "OcDelEvaluation";
	}
	@Override
	protected void setEntityname() {
		entityname = "OcDelEvaluation";
	}
}
