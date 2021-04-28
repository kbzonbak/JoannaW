package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class OrderTypeDTO implements Serializable{

	private Long id;
	private String code;
	private String name;
	private Boolean groupable;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Boolean getGroupable() {
		return groupable;
	}
	public void setGroupable(Boolean groupable) {
		this.groupable = groupable;
	}	
}
