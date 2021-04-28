package bbr.b2b.logistic.customer.entities;

import bbr.b2b.logistic.customer.entities.Buyer;
import bbr.b2b.logistic.customer.data.interfaces.ILocation;

public class Location implements ILocation {

	private Long id;
	private String code;
	private String name;
	private Buyer buyer;
	private String ean13;
	private String address;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public Buyer getBuyer(){ 
		return this.buyer;
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
	public void setBuyer(Buyer buyer){ 
		this.buyer = buyer;
	}
	public String getEan13() {
		return ean13;
	}
	public void setEan13(String ean13) {
		this.ean13 = ean13;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
