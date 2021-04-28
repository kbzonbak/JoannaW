package bbr.b2b.regional.logistic.datings.entities;

import bbr.b2b.regional.logistic.datings.data.interfaces.IDockType;

public class DockType implements IDockType {

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
