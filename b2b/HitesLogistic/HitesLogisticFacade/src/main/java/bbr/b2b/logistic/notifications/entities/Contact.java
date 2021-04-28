package bbr.b2b.logistic.notifications.entities;

import bbr.b2b.logistic.vendors.entities.Vendor;
import bbr.b2b.logistic.notifications.data.interfaces.IContact;

public class Contact implements IContact {

	private Long id;
	private String name;
	private String lastname;
	private String position;
	private String email;
	private Vendor vendor;

	public Long getId(){ 
		return this.id;
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
	public Vendor getVendor(){ 
		return this.vendor;
	}
	public void setId(Long id){ 
		this.id = id;
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
	public void setVendor(Vendor vendor){ 
		this.vendor = vendor;
	}
}
