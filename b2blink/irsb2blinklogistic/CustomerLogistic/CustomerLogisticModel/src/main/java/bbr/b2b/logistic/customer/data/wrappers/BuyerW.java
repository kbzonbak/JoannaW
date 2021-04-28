package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.logistic.customer.data.interfaces.IBuyer;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;

public class BuyerW extends ElementDTO implements IBuyer {
	
	private Date created;
	private String code;
	private String name;
	private String gln;
	private String businessarea;
	private String rut;
	private String dv;
	private String razonsocial;
	private String sitename;
	private String billtopartner;

	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public String getGln(){ 
		return this.gln;
	}
	public String getBusinessarea(){ 
		return this.businessarea;
	}
	public String getRut(){ 
		return this.rut;
	}
	public String getDv(){ 
		return this.dv;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setGln(String gln){ 
		this.gln = gln;
	}
	public void setBusinessarea(String businessarea){ 
		this.businessarea = businessarea;
	}
	public void setRut(String rut){ 
		this.rut = rut;
	}
	public void setDv(String dv){ 
		this.dv = dv;
	}
	public String getRazonsocial() {
		return razonsocial;
	}
	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public String getBilltopartner() {
		return billtopartner;
	}
	public void setBilltopartner(String billtopartner) {
		this.billtopartner = billtopartner;
	}
}
