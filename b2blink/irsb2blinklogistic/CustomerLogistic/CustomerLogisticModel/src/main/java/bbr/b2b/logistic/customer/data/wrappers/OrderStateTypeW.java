package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.customer.data.interfaces.IOrderStateType;

public class OrderStateTypeW extends ElementDTO implements IOrderStateType {

	private String code;
	private String name;
	private Boolean valid;
	private Boolean showable;

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
