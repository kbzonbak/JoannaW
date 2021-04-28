package bbr.b2b.logistic.buyorders.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.buyorders.data.interfaces.IRetailer;

public class RetailerW extends ElementDTO implements IRetailer {

	private String code;
	private String dni;
	private String description;
	private String gln;

	public String getCode(){ 
		return this.code;
	}
	public String getDni(){ 
		return this.dni;
	}
	public String getDescription(){ 
		return this.description;
	}
	public String getGln(){ 
		return this.gln;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setDni(String dni){ 
		this.dni = dni;
	}
	public void setDescription(String description){ 
		this.description = description;
	}
	public void setGln(String gln){ 
		this.gln = gln;
	}
}
