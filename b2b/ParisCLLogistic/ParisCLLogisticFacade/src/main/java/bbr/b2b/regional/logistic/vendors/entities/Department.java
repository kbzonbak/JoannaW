package bbr.b2b.regional.logistic.vendors.entities;

import bbr.b2b.regional.logistic.vendors.data.interfaces.IDepartment;

public class Department implements IDepartment {

	private Long id;
	private String code;
	private String name;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
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
}
