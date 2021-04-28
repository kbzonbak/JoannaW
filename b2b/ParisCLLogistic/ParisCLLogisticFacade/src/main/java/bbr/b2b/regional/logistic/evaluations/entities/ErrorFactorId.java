package bbr.b2b.regional.logistic.evaluations.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.evaluations.data.interfaces.IErrorFactorPK;

public class ErrorFactorId implements IErrorFactorPK {

	private Long evaluationcriteriaid;
	private Long receptionerrorid;

	public ErrorFactorId(){
	}
	public ErrorFactorId(Long evaluationcriteriaid, Long receptionerrorid){
		this.evaluationcriteriaid = evaluationcriteriaid;
		this.receptionerrorid = receptionerrorid;
	}

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
	public boolean equals(Object o){
		if (o != null && o instanceof ErrorFactorId){
			ErrorFactorId that = (ErrorFactorId) o;
			return this.evaluationcriteriaid.equals(that.evaluationcriteriaid) && this.receptionerrorid.equals(that.receptionerrorid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return evaluationcriteriaid.hashCode() + receptionerrorid.hashCode();
	}

	public Long getEvaluationcriteriaid(){ 
		return this.evaluationcriteriaid;
	}
	public Long getReceptionerrorid(){ 
		return this.receptionerrorid;
	}
	public void setEvaluationcriteriaid(Long evaluationcriteriaid){ 
		this.evaluationcriteriaid = evaluationcriteriaid;
	}
	public void setReceptionerrorid(Long receptionerrorid){ 
		this.receptionerrorid = receptionerrorid;
	}
}
