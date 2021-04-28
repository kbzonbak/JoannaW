package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDOReceptionType;

public class DOReceptionTypeW extends ElementDTO implements IDOReceptionType {

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
