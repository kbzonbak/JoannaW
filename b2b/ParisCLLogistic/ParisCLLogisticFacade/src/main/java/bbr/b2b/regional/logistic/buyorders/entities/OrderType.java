package bbr.b2b.regional.logistic.buyorders.entities;

import bbr.b2b.regional.logistic.buyorders.data.interfaces.IOrderType;

public class OrderType implements IOrderType {

	private Long id;
	private String code;
	private String name;
	private Boolean groupable;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public Boolean getGroupable(){ 
		return this.groupable;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setGroupable(Boolean groupable){ 
		this.groupable = groupable;
	}
}
