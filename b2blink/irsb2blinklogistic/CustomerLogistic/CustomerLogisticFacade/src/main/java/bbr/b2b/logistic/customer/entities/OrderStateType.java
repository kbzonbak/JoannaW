package bbr.b2b.logistic.customer.entities;

import bbr.b2b.logistic.customer.data.interfaces.IOrderStateType;

public class OrderStateType implements IOrderStateType {

	private Long id;
	private String code;
	private String name;
	private Boolean valid;
	private Boolean showable;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public Boolean getValid(){ 
		return this.valid;
	}
	public Boolean getShowable(){ 
		return this.showable;
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
	public void setValid(Boolean valid){ 
		this.valid = valid;
	}
	public void setShowable(Boolean showable){ 
		this.showable = showable;
	}
}
