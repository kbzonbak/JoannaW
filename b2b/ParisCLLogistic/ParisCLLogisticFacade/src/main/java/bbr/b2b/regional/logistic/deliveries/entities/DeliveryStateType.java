package bbr.b2b.regional.logistic.deliveries.entities;

import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDeliveryStateType;

public class DeliveryStateType implements IDeliveryStateType {

	private Long id;
	private String code;
	private String name;
	private Boolean closed;
	private Boolean showable;
	private String action;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public Boolean getClosed(){ 
		return this.closed;
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
	public void setClosed(Boolean closed){ 
		this.closed = closed;
	}
	public void setShowable(Boolean showable){ 
		this.showable = showable;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}	
}
