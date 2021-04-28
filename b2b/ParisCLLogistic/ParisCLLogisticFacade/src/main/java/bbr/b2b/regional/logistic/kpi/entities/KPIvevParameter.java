package bbr.b2b.regional.logistic.kpi.entities;

import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevParameter;

public class KPIvevParameter implements IKPIvevParameter{
	
	private Long id;
	private String code;
	private String description;
	private Double value;
	private String um;
	private Boolean visible;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getUm() {
		return um;
	}
	public void setUm(String um) {
		this.um = um;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
		
}