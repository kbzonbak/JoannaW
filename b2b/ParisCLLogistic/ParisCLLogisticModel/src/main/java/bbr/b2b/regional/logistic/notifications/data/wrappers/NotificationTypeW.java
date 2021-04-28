package bbr.b2b.regional.logistic.notifications.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.notifications.data.interfaces.INotificationType;

public class NotificationTypeW extends ElementDTO implements INotificationType {

	private String code;
	private String description;

	public String getCode(){ 
		return this.code;
	}
	public String getDescription(){ 
		return this.description;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setDescription(String description){ 
		this.description = description;
	}
}
