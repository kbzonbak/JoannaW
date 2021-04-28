package bbr.b2b.regional.logistic.evaluations.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IEvaluationCriteria extends IElement {

	public String getName();
	public Integer getVisualorder();
	public Double getWeight();
	public void setName(String name);
	public void setVisualorder(Integer visualorder);
	public void setWeight(Double weight);
}
