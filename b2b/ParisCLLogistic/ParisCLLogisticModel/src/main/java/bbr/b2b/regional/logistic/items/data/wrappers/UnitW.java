package bbr.b2b.regional.logistic.items.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.items.data.interfaces.IUnit;

public class UnitW extends ElementDTO implements IUnit {

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
