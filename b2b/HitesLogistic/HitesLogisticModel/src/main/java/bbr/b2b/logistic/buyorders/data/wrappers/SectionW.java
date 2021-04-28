package bbr.b2b.logistic.buyorders.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.buyorders.data.interfaces.ISection;

public class SectionW extends ElementDTO implements ISection {

	private String name;
	private String code;

	public String getName(){ 
		return this.name;
	}
	public String getCode(){ 
		return this.code;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setCode(String code){ 
		this.code = code;
	}
}
