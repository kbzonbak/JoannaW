package bbr.b2b.regional.logistic.notifications.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.notifications.data.interfaces.IContact;

public class ContactW extends ElementDTO implements IContact {

	private String name;
	private String lastname;
	private String position;
	private String email;
	private Long vendorid;

	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public String getName(){ 
		return this.name;
	}
	public String getLastname(){ 
		return this.lastname;
	}
	public String getPosition(){ 
		return this.position;
	}
	public String getEmail(){ 
		return this.email;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setLastname(String lastname){ 
		this.lastname = lastname;
	}
	public void setPosition(String position){ 
		this.position = position;
	}
	public void setEmail(String email){ 
		this.email = email;
	}
}
