package bbr.b2b.logistic.buyorders.entities;

import bbr.b2b.logistic.buyorders.data.interfaces.IResponsable;

public class Responsable implements IResponsable {

	private Long id;
	private String code;
	private String name;
	private String email;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public String getEmail(){ 
		return this.email;
	}
	public void setId(Long id){ 
		this.id = id;
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
