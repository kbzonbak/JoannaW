package bbr.b2b.regional.logistic.evaluations.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IEvaluationDetailPK extends IPrimaryKey {

	public Long getReceptionevaluationid();
	public Long getReceptionerrorid();
	public void setReceptionevaluationid(Long receptionevaluationid);
	public void setReceptionerrorid(Long receptionerrorid);
}
