package bbr.b2b.logistic.buyorders.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.buyorders.data.interfaces.IClient;

public class ClientW extends ElementDTO implements IClient {

	private String dni;
	private String name;
	private String address;
	private String phone;
	private String email;
	private String city;
	private String commune;
	private String comment;
	private String streetname;
	private String streetnumber;
	private String deparmentnumber;

	public String getDni(){ 
		return this.dni;
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
	public String getEmail(){ 
		return this.email;
	}
	public String getCity(){ 
		return this.city;
	}
	public String getCommune(){ 
		return this.commune;
	}
	public String getComment(){ 
		return this.comment;
	}
	public String getStreetname(){ 
		return this.streetname;
	}
	public String getStreetnumber(){ 
		return this.streetnumber;
	}
	public String getDeparmentnumber(){ 
		return this.deparmentnumber;
	}
	public void setDni(String dni){ 
		this.dni = dni;
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
	public void setEmail(String email){ 
		this.email = email;
	}
	public void setCity(String city){ 
		this.city = city;
	}
	public void setCommune(String commune){ 
		this.commune = commune;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public void setStreetname(String streetname){ 
		this.streetname = streetname;
	}
	public void setStreetnumber(String streetnumber){ 
		this.streetnumber = streetnumber;
	}
	public void setDeparmentnumber(String deparmentnumber){ 
		this.deparmentnumber = deparmentnumber;
	}
}
