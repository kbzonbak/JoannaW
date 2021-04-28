package bbr.b2b.regional.logistic.couriers.report.classes;

import java.io.Serializable;

public class RescheduleReasonDTO implements Serializable {

	private Long id;
	private String code;
	private String description;
	private String responsibility;
	
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
	public String getResponsibility() {
		return responsibility;
	}
	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}
		
}
