package bbr.b2b.regional.logistic.evaluations.entities;

import bbr.b2b.regional.logistic.evaluations.data.interfaces.IEvaluationCriteria;

public class EvaluationCriteria implements IEvaluationCriteria {

	private Long id;
	private String name;
	private Integer visualorder;
	private Double weight;

	public Long getId(){ 
		return this.id;
	}
	public String getName(){ 
		return this.name;
	}
	public Integer getVisualorder(){ 
		return this.visualorder;
	}
	public Double getWeight(){ 
		return this.weight;
	}
	public void setId(Long id){ 
		this.id = id;
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
