package bbr.b2b.regional.logistic.buyorders.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IClient;

public class ClientW extends ElementDTO implements IClient {

	private String rut;
	private String name;
	private String address;
	private String phone;
	private String commune;
	private String city;
	private String region;
	private String housenumber;
	private String deptnumber;
	private String roadnumber;
	private String comment;

	public String getRut(){ 
		return this.rut;
	}
	public String getName(){ 
		return this.name;
	}
	public String getAddress(){ 
		return this.address;
	}
	public String getPhone(){ 
		return this.phone;
	}
	public String getCommune(){ 
		return this.commune;
	}
	public String getCity(){ 
		return this.city;
	}
	public String getRegion(){ 
		return this.region;
	}
	public String getHousenumber(){ 
		return this.housenumber;
	}
	public String getDeptnumber(){ 
		return this.deptnumber;
	}
	public String getRoadnumber(){ 
		return this.roadnumber;
	}
	public String getComment(){ 
		return this.comment;
	}
	public void setRut(String rut){ 
		this.rut = rut;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setAddress(String address){ 
		this.address = address;
	}
	public void setPhone(String phone){ 
		this.phone = phone;
	}
	public void setCommune(String commune){ 
		this.commune = commune;
	}
	public void setCity(String city){ 
		this.city = city;
	}
	public void setRegion(String region){ 
		this.region = region;
	}
	public void setHousenumber(String housenumber){ 
		this.housenumber = housenumber;
	}
	public void setDeptnumber(String deptnumber){ 
		this.deptnumber = deptnumber;
	}
	public void setRoadnumber(String roadnumber){ 
		this.roadnumber = roadnumber;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
}
