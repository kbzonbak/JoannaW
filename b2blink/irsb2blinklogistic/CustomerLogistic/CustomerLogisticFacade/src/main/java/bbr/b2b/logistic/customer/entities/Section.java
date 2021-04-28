package bbr.b2b.logistic.customer.entities;

import bbr.b2b.logistic.customer.data.interfaces.ISection;

public class Section implements ISection {

	private Long id;
	private String code;
	private String name;
	private Buyer buyer;

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
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
}
