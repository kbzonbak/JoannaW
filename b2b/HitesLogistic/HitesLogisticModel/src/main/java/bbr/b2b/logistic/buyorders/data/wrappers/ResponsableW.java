package bbr.b2b.logistic.buyorders.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.buyorders.data.interfaces.IResponsable;

public class ResponsableW extends ElementDTO implements IResponsable {

	private String code;
	private String name;
	private String email;

	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public String getEmail(){ 
		return this.email;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setEmail(String email){ 
		this.email = email;
	}
}
