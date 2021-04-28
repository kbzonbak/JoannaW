package bbr.b2b.regional.logistic.notifications.entities;

import bbr.b2b.regional.logistic.notifications.data.interfaces.INotificationType;

public class NotificationType implements INotificationType {

	private Long id;
	private String code;
	private String description;
	private Boolean visible;
	private Long visualorder;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getDescription(){ 
		return this.description;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setDescription(String description){ 
		this.description = description;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public Long getVisualorder() {
		return visualorder;
	}
	public void setVisualorder(Long visualorder) {
		this.visualorder = visualorder;
	}
}
