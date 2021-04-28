package bbr.b2b.regional.logistic.evaluations.entities;

import bbr.b2b.regional.logistic.evaluations.entities.ReceptionEvaluation;
import bbr.b2b.regional.logistic.evaluations.entities.ReceptionError;
import bbr.b2b.regional.logistic.evaluations.data.interfaces.IEvaluationDetail;

public class EvaluationDetail implements IEvaluationDetail {

	private EvaluationDetailId id;
	private ReceptionEvaluation receptionevaluation;
	private ReceptionError receptionerror;

	public EvaluationDetailId getId(){ 
		return this.id;
	}
	public ReceptionEvaluation getReceptionevaluation(){ 
		return this.receptionevaluation;
	}
	public ReceptionError getReceptionerror(){ 
		return this.receptionerror;
	}
	public void setId(EvaluationDetailId id){ 
		this.id = id;
	}
	public void setReceptionevaluation(ReceptionEvaluation receptionevaluation){ 
		this.receptionevaluation = receptionevaluation;
	}
	public void setReceptionerror(ReceptionError receptionerror){ 
		this.receptionerror = receptionerror;
	}
}
