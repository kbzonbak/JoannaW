package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.customer.data.interfaces.ILocation;

public class LocationW extends ElementDTO implements ILocation {

	private String code;
	private String name;
	private Long buyerid;
	private String ean13;
	private String address;
	

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
	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public Long getBuyerid(){ 
		return this.buyerid;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setBuyerid(Long buyerid){ 
		this.buyerid = buyerid;
	}
}
