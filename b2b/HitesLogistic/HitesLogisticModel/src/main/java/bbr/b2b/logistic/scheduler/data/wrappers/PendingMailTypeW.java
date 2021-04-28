package bbr.b2b.logistic.scheduler.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.scheduler.data.interfaces.IPendingMailType;

public class PendingMailTypeW extends ElementDTO implements IPendingMailType {

	private String code;
	private String description;
	private Integer priority;

	public String getCode(){ 
		return this.code;
	}
	public String getDescription(){ 
		return this.description;
	}
	public Integer getPriority(){ 
		return this.priority;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setDescription(String description){ 
		this.description = description;
	}
	public void setPriority(Integer priority){ 
		this.priority = priority;
	}
}
