package bbr.b2b.regional.logistic.couriers.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.couriers.data.interfaces.IRescheduleReason;

public class RescheduleReasonW extends ElementDTO implements IRescheduleReason {

	private String code;
	private String description;
	private Integer visualorder;
	private Boolean showable;
	private String responsibility;
	
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
