package bbr.b2b.logistic.notifications.data.wrappers;

import bbr.b2b.logistic.notifications.data.interfaces.INotificationType;
import bbr.b2b.common.adtclasses.classes.ElementDTO;

public class NotificationTypeW extends ElementDTO implements INotificationType {

	private String code;
	private String description;
	private Boolean visible;
	private Long visualorder;

	public String getCode(){ 
		return this.code;
	}
	public String getDescription(){ 
		return this.description;
	}
	public Boolean getVisible(){ 
		return this.visible;
	}
	public Long getVisualorder(){ 
		return this.visualorder;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setDescription(String description){ 
		this.description = description;
	}
	public void setVisible(Boolean visible){ 
		this.visible = visible;
	}
	public void setVisualorder(Long visualorder){ 
		this.visualorder = visualorder;
	}
}
