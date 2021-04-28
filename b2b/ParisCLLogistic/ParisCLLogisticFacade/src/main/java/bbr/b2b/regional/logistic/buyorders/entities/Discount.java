package bbr.b2b.regional.logistic.buyorders.entities;

import bbr.b2b.regional.logistic.buyorders.data.interfaces.IDiscount;

public class Discount implements IDiscount {

	private Long id;
	private String code;
	private Double value;
	private Boolean percentage;
	private String comment;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public Double getValue(){ 
		return this.value;
	}
	public Boolean getPercentage(){ 
		return this.percentage;
	}
	public String getComment(){ 
		return this.comment;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setValue(Double value){ 
		this.value = value;
	}
	public void setPercentage(Boolean percentage){ 
		this.percentage = percentage;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
}
