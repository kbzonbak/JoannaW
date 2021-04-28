package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.customer.data.interfaces.IHomologation;

public class HomologationW extends ElementDTO implements IHomologation {

	private String code;
	private String name;
	private String value;
	private Long buyerid;
	private Long vendorid;

	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public Long getBuyerid() {
		return buyerid;
	}
	public void setBuyerid(Long buyerid) {
		this.buyerid = buyerid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
}
