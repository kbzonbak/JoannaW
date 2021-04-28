package bbr.b2b.regional.logistic.soa.entities;

import bbr.b2b.regional.logistic.soa.data.interfaces.ISOAStateType;

public class SOAStateType implements ISOAStateType {

	private Long id;
	private String code;
	private String name;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}		
}