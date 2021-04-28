package bbr.b2b.regional.logistic.buyorders.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IAtc;

public class AtcW extends ElementDTO implements IAtc {

	private String code;

	public String getCode(){ 
		return this.code;
	}
	public void setCode(String code){ 
		this.code = code;
	}
}
