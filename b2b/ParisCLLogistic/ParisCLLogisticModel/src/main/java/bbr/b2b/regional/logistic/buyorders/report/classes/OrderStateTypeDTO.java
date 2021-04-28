package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;


public class OrderStateTypeDTO implements Serializable {
	
	private Long id; 
	private String code;
	private String name;
	private Boolean valid;
	private Boolean showable;
	
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
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public Boolean getShowable() {
		return showable;
	}
	public void setShowable(Boolean showable) {
		this.showable = showable;
	}	
}
