package bbr.b2b.logistic.buyorders.entities;

import bbr.b2b.logistic.buyorders.data.interfaces.IOrderType;

public class OrderType implements IOrderType {

	private Long id;
	private String code;
	private String description;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getDescription(){ 
		return this.description;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setDescription(String description){ 
		this.description = description;
	}
}
