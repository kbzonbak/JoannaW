package bbr.b2b.logistic.dvrorders.entities;

import bbr.b2b.logistic.dvrorders.data.interfaces.IChargeDiscount;

public class ChargeDiscount implements IChargeDiscount {

	private Long id;
	private Boolean charge;
	private String description;
	private Integer visualorder;
	private Boolean percentage;
	private Double value;

	public Long getId(){ 
		return this.id;
	}
	public Boolean getCharge(){ 
		return this.charge;
	}
	public String getDescription(){ 
		return this.description;
	}
	public Integer getVisualorder(){ 
		return this.visualorder;
	}
	public Boolean getPercentage(){ 
		return this.percentage;
	}
	public Double getValue(){ 
		return this.value;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setCharge(Boolean charge){ 
		this.charge = charge;
	}
	public void setDescription(String description){ 
		this.description = description;
	}
	public void setVisualorder(Integer visualorder){ 
		this.visualorder = visualorder;
	}
	public void setPercentage(Boolean percentage){ 
		this.percentage = percentage;
	}
	public void setValue(Double value){ 
		this.value = value;
	}
}
