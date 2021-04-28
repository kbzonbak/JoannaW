package bbr.b2b.regional.logistic.couriers.entities;

import bbr.b2b.regional.logistic.couriers.data.interfaces.IRescheduleReason;

public class RescheduleReason implements IRescheduleReason {

	private Long id;
	private String code;
	private String description;
	private Integer visualorder;
	private Boolean showable;
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
	public Integer getVisualorder() {
		return visualorder;
	}
	public void setVisualorder(Integer visualorder) {
		this.visualorder = visualorder;
	}
	public Boolean getShowable() {
		return showable;
	}
	public void setShowable(Boolean showable) {
		this.showable = showable;
	}
	public String getResponsibility() {
		return responsibility;
	}
	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}
	
}
