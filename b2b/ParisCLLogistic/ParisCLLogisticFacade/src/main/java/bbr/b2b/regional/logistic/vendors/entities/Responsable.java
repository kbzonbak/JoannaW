package bbr.b2b.regional.logistic.vendors.entities;

import bbr.b2b.regional.logistic.vendors.data.interfaces.IResponsable;

public class Responsable implements IResponsable {

	private Long id;
	private String code;
	private String rut;
	private String name;
	private String lastname;
	private String email;
	private String phone;
	private String fax;
	private String position;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getRut(){ 
		return this.rut;
	}
	public String getName(){ 
		return this.name;
	}
	public String getLastname(){ 
		return this.lastname;
	}
	public String getEmail(){ 
		return this.email;
	}
	public String getPhone(){ 
		return this.phone;
	}
	public String getFax(){ 
		return this.fax;
	}
	public String getPosition(){ 
		return this.position;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setRut(String rut){ 
		this.rut = rut;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setLastname(String lastname){ 
		this.lastname = lastname;
	}
	public void setEmail(String email){ 
		this.email = email;
	}
	public void setPhone(String phone){ 
		this.phone = phone;
	}
	public void setFax(String fax){ 
		this.fax = fax;
	}
	public void setPosition(String position){ 
		this.position = position;
	}
}
