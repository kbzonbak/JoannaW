package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDeliveryStateType;

public class DeliveryStateTypeW extends ElementDTO implements IDeliveryStateType {

	private String code;
	private String name;
	private Boolean closed;
	private Boolean showable;
	private String action;

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
