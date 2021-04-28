package bbr.b2b.regional.logistic.evaluations.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IErrorFactorPK extends IPrimaryKey {

	public Long getEvaluationcriteriaid();
	public Long getReceptionerrorid();
	public void setEvaluationcriteriaid(Long evaluationcriteriaid);
	public void setReceptionerrorid(Long receptionerrorid);
}
