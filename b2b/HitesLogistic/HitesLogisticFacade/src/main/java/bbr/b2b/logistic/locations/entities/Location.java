package bbr.b2b.logistic.locations.entities;

import bbr.b2b.logistic.locations.data.interfaces.ILocation;

public class Location implements ILocation {

	private Long id;
	private String code;
	private String name;
	private String address;
	private String commune;
	private String city;
	private String phone;
	private Boolean warehouse;
	private Integer priority;
	private String email;
	private String gln;
	private String managername;
	private String manageremail;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public String getAddress(){ 
		return this.address;
	}
	public String getCommune(){ 
		return this.commune;
	}
	public String getCity(){ 
		return this.city;
	}
	public String getPhone(){ 
		return this.phone;
	}
	public Boolean getWarehouse(){ 
		return this.warehouse;
	}
	public Integer getPriority(){ 
		return this.priority;
	}
	public String getEmail(){ 
		return this.email;
	}
	public String getGln(){ 
		return this.gln;
	}
	public String getManagername(){ 
		return this.managername;
	}
	public String getManageremail(){ 
		return this.manageremail;
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
	public void setAddress(String address){ 
		this.address = address;
	}
	public void setCommune(String commune){ 
		this.commune = commune;
	}
	public void setCity(String city){ 
		this.city = city;
	}
	public void setPhone(String phone){ 
		this.phone = phone;
	}
	public void setWarehouse(Boolean warehouse){ 
		this.warehouse = warehouse;
	}
	public void setPriority(Integer priority){ 
		this.priority = priority;
	}
	public void setEmail(String email){ 
		this.email = email;
	}
	public void setGln(String gln){ 
		this.gln = gln;
	}
	public void setManagername(String managername){ 
		this.managername = managername;
	}
	public void setManageremail(String manageremail){ 
		this.manageremail = manageremail;
	}
}
