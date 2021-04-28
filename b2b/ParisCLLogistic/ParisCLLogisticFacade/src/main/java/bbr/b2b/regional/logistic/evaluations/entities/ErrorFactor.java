package bbr.b2b.regional.logistic.evaluations.entities;

import bbr.b2b.regional.logistic.evaluations.entities.EvaluationCriteria;
import bbr.b2b.regional.logistic.evaluations.entities.ReceptionError;
import bbr.b2b.regional.logistic.evaluations.data.interfaces.IErrorFactor;

public class ErrorFactor implements IErrorFactor {

	private ErrorFactorId id;
	private Boolean affects;
	private EvaluationCriteria evaluationcriteria;
	private ReceptionError receptionerror;

	public ErrorFactorId getId(){ 
		return this.id;
	}
	public Boolean getAffects(){ 
		return this.affects;
	}
	public EvaluationCriteria getEvaluationcriteria(){ 
		return this.evaluationcriteria;
	}
	public ReceptionError getReceptionerror(){ 
		return this.receptionerror;
	}
	public void setId(ErrorFactorId id){ 
		this.id = id;
	}
	public void setAffects(Boolean affects){ 
		this.affects = affects;
	}
	public void setEvaluationcriteria(EvaluationCriteria evaluationcriteria){ 
		this.evaluationcriteria = evaluationcriteria;
	}
	public void setReceptionerror(ReceptionError receptionerror){ 
		this.receptionerror = receptionerror;
	}
}
