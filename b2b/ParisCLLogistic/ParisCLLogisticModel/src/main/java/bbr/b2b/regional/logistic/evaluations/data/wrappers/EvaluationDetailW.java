package bbr.b2b.regional.logistic.evaluations.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.evaluations.data.interfaces.IEvaluationDetail;
import bbr.b2b.regional.logistic.evaluations.data.interfaces.IEvaluationDetailPK;

public class EvaluationDetailW implements IEvaluationDetail, IEvaluationDetailPK {

	private Long receptionevaluationid;
	private Long receptionerrorid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = receptionevaluationid.longValue() - ((IEvaluationDetailPK) arg0).getReceptionevaluationid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = receptionerrorid.longValue() - ((IEvaluationDetailPK) arg0).getReceptionerrorid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getReceptionevaluationid(){ 
		return this.receptionevaluationid;
	}
	public Long getReceptionerrorid(){ 
		return this.receptionerrorid;
	}
	public void setReceptionevaluationid(Long receptionevaluationid){ 
		this.receptionevaluationid = receptionevaluationid;
	}
	public void setReceptionerrorid(Long receptionerrorid){ 
		this.receptionerrorid = receptionerrorid;
	}
}
