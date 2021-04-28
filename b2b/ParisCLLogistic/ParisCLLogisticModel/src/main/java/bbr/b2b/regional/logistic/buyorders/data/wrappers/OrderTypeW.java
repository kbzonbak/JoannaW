package bbr.b2b.regional.logistic.buyorders.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IOrderType;

public class OrderTypeW extends ElementDTO implements IOrderType {

	private String code;
	private String name;
	private Boolean groupable;

	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public Boolean getGroupable(){ 
		return this.groupable;
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
