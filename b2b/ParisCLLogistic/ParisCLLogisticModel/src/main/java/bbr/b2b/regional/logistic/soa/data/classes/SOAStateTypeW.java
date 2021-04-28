package bbr.b2b.regional.logistic.soa.data.classes;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.soa.data.interfaces.ISOAStateType;

public class SOAStateTypeW extends ElementDTO implements ISOAStateType {

	private String code;
	private String name;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}		
}
