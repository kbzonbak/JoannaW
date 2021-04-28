package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.customer.data.interfaces.IClient;

public class ClientW extends ElementDTO implements IClient {

	private String name;
	private String identity;
	private String check_digit;
	private String phone;
	private String phone2;
	private String address;
	private String street_number;
	private String departmet_number;
	private String house_number;
	private String region;
	private String commune;
	private String city;
	private String location;
	private String observation;

	public String getName(){ 
		return this.name;
	}
	public String getIdentity(){ 
		return this.identity;
	}
	public String getCheck_digit(){ 
		return this.check_digit;
	}
	public String getPhone(){ 
		return this.phone;
	}
	public String getPhone2(){ 
		return this.phone2;
	}
	public String getAddress(){ 
		return this.address;
	}
	public String getStreet_number(){ 
		return this.street_number;
	}
	public String getDepartmet_number(){ 
		return this.departmet_number;
	}
	public String getHouse_number(){ 
		return this.house_number;
	}
	public String getRegion(){ 
		return this.region;
	}
	public String getCommune(){ 
		return this.commune;
	}
	public String getCity(){ 
		return this.city;
	}
	public String getLocation(){ 
		return this.location;
	}
	public String getObservation(){ 
		return this.observation;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setIdentity(String identity){ 
		this.identity = identity;
	}
	public void setCheck_digit(String check_digit){ 
		this.check_digit = check_digit;
	}
	public void setPhone(String phone){ 
		this.phone = phone;
	}
	public void setPhone2(String phone2){ 
		this.phone2 = phone2;
	}
	public void setAddress(String address){ 
		this.address = address;
	}
	public void setStreet_number(String street_number){ 
		this.street_number = street_number;
	}
	public void setDepartmet_number(String departmet_number){ 
		this.departmet_number = departmet_number;
	}
	public void setHouse_number(String house_number){ 
		this.house_number = house_number;
	}
	public void setRegion(String region){ 
		this.region = region;
	}
	public void setCommune(String commune){ 
		this.commune = commune;
	}
	public void setCity(String city){ 
		this.city = city;
	}
	public void setLocation(String location){ 
		this.location = location;
	}
	public void setObservation(String observation){ 
		this.observation = observation;
	}
}
