package bbr.b2b.regional.logistic.locations.entities;

import bbr.b2b.regional.logistic.locations.data.interfaces.ILocation;

public class Location implements ILocation {

	private Long id;
	private String code;
	private String name;
	private String shortname;
	private String address;
	private String email;
	private Integer priority;
	private String phone;
	private String fax;
	private String commune;
	private String city;
	private Boolean warehouse;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public String getShortname(){ 
		return this.shortname;
	}
	public String getAddress(){ 
		return this.address;
	}
	public String getEmail(){ 
		return this.email;
	}
	public Integer getPriority(){ 
		return this.priority;
	}
	public String getPhone(){ 
		return this.phone;
	}
	public String getFax(){ 
		return this.fax;
	}
	public String getCommune(){ 
		return this.commune;
	}
	public String getCity(){ 
		return this.city;
	}
	public Boolean getWarehouse(){ 
		return this.warehouse;
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
	public void setShortname(String shortname){ 
		this.shortname = shortname;
	}
	public void setAddress(String address){ 
		this.address = address;
	}
	public void setEmail(String email){ 
		this.email = email;
	}
	public void setPriority(Integer priority){ 
		this.priority = priority;
	}
	public void setPhone(String phone){ 
		this.phone = phone;
	}
	public void setFax(String fax){ 
		this.fax = fax;
	}
	public void setCommune(String commune){ 
		this.commune = commune;
	}
	public void setCity(String city){ 
		this.city = city;
	}
	public void setWarehouse(Boolean warehouse){ 
		this.warehouse = warehouse;
	}
}
