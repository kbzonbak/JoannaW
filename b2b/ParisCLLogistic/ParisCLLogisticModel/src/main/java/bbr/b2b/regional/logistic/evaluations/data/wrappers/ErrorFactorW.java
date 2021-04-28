package bbr.b2b.regional.logistic.evaluations.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.evaluations.data.interfaces.IErrorFactor;
import bbr.b2b.regional.logistic.evaluations.data.interfaces.IErrorFactorPK;

public class ErrorFactorW implements IErrorFactorPK, IErrorFactor {

	private Boolean affects;
	private Long evaluationcriteriaid;
	private Long receptionerrorid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = evaluationcriteriaid.longValue() - ((IErrorFactorPK) arg0).getEvaluationcriteriaid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = receptionerrorid.longValue() - ((IErrorFactorPK) arg0).getReceptionerrorid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Boolean getAffects(){ 
		return this.affects;
	}
	public Long getEvaluationcriteriaid(){ 
		return this.evaluationcriteriaid;
	}
	public Long getReceptionerrorid(){ 
		return this.receptionerrorid;
	}
	public void setAffects(Boolean affects){ 
		this.affects = affects;
	}
	public void setEvaluationcriteriaid(Long evaluationcriteriaid){ 
		this.evaluationcriteriaid = evaluationcriteriaid;
	}
	public void setReceptionerrorid(Long receptionerrorid){ 
		this.receptionerrorid = receptionerrorid;
	}
}
