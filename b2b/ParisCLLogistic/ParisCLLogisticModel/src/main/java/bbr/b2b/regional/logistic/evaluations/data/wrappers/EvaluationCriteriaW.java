package bbr.b2b.regional.logistic.evaluations.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.evaluations.data.interfaces.IEvaluationCriteria;

public class EvaluationCriteriaW extends ElementDTO implements IEvaluationCriteria {

	private String name;
	private Integer visualorder;
	private Double weight;

	public String getName(){ 
		return this.name;
	}
	public Integer getVisualorder(){ 
		return this.visualorder;
	}
	public Double getWeight(){ 
		return this.weight;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setVisualorder(Integer visualorder){ 
		this.visualorder = visualorder;
	}
	public void setWeight(Double weight){ 
		this.weight = weight;
	}
}
