package bbr.b2b.logistic.customer.entities;

import bbr.b2b.logistic.customer.data.interfaces.IVendor;

public class Vendor implements IVendor {

	private Long id;
	private String code;
	private String name;
	private String rut;
	private String dv;
	private String gln;
	private String address;
	private String phone;
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
	public String getRut(){ 
		return this.rut;
	}
	public String getDv(){ 
		return this.dv;
	}
	public String getGln(){ 
		return this.gln;
	}
	public String getAddress(){ 
		return this.address;
	}
	public String getPhone(){ 
		return this.phone;
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
	public void setRut(String rut){ 
		this.rut = rut;
	}
	public void setDv(String dv){ 
		this.dv = dv;
	}
	public void setGln(String gln){ 
		this.gln = gln;
	}
	public void setAddress(String address){ 
		this.address = address;
	}
	public void setPhone(String phone){ 
		this.phone = phone;
	}
	public void setEmail(String email){ 
		this.email = email;
	}
}
