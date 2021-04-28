package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.customer.data.interfaces.IOrderTypeB2B;

public class OrderTypeB2BW extends ElementDTO implements IOrderTypeB2B {

	private String code;
	private String name;

	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setName(String name){ 
		this.name = name;
	}
}
