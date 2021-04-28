package bbr.b2b.logistic.vendors.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.vendors.data.interfaces.IVendor;

public class VendorW extends ElementDTO implements IVendor {

	private String name;
	private String code;
	private String dni;
	private String tradename;
	private String address;
	private String commune;
	private String city;
	private String gln;
	private Boolean domestic;
	private String email;
	private String phone;
	private String country;
	private String internalcode;
	private Integer mincorrelative;

	public String getName(){ 
		return this.name;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getDni(){ 
		return this.dni;
	}
	public String getTradename(){ 
		return this.tradename;
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
	public String getGln(){ 
		return this.gln;
	}
	public Boolean getDomestic(){ 
		return this.domestic;
	}
	public String getEmail(){ 
		return this.email;
	}
	public String getPhone(){ 
		return this.phone;
	}
	public String getCountry(){ 
		return this.country;
	}
	public String getInternalcode(){ 
		return this.internalcode;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setDni(String dni){ 
		this.dni = dni;
	}
	public void setTradename(String tradename){ 
		this.tradename = tradename;
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
	public void setGln(String gln){ 
		this.gln = gln;
	}
	public void setDomestic(Boolean domestic){ 
		this.domestic = domestic;
	}
	public void setEmail(String email){ 
		this.email = email;
	}
	public void setPhone(String phone){ 
		this.phone = phone;
	}
	public void setCountry(String country){ 
		this.country = country;
	}
	public void setInternalcode(String internalcode){ 
		this.internalcode = internalcode;
	}
	public Integer getMincorrelative() {
		return mincorrelative;
	}
	public void setMincorrelative(Integer mincorrelative) {
		this.mincorrelative = mincorrelative;
	}
}
