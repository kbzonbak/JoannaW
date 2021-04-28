package bbr.b2b.logistic.buyorders.entities;

import bbr.b2b.logistic.buyorders.data.interfaces.IRetailer;

public class Retailer implements IRetailer {

	private Long id;
	private String code;
	private String dni;
	private String description;
	private String gln;

	public Long getId(){ 
		return this.id;
	}
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
	public void setId(Long id){ 
		this.id = id;
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
