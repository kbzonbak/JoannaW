package bbr.b2b.regional.logistic.evaluations.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.evaluations.data.interfaces.IEvaluationDetailPK;

public class EvaluationDetailId implements IEvaluationDetailPK {

	private Long receptionevaluationid;
	private Long receptionerrorid;

	public EvaluationDetailId(){
	}
	public EvaluationDetailId(Long receptionevaluationid, Long receptionerrorid){
		this.receptionevaluationid = receptionevaluationid;
		this.receptionerrorid = receptionerrorid;
	}

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
	public boolean equals(Object o){
		if (o != null && o instanceof EvaluationDetailId){
			EvaluationDetailId that = (EvaluationDetailId) o;
			return this.receptionevaluationid.equals(that.receptionevaluationid) && this.receptionerrorid.equals(that.receptionerrorid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return receptionevaluationid.hashCode() + receptionerrorid.hashCode();
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
