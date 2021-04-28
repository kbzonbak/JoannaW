package bbr.b2b.logistic.notifications.data.wrappers;

import bbr.b2b.logistic.notifications.data.interfaces.IContact;
import bbr.b2b.common.adtclasses.classes.ElementDTO;

public class ContactW extends ElementDTO implements IContact {

	private String name;
	private String lastname;
	private String position;
	private String email;
	private Long vendorid;

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
	public Long getVendorid(){ 
		return this.vendorid;
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
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
}
