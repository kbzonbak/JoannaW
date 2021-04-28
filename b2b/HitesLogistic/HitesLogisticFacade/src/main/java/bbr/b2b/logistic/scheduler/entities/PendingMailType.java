package bbr.b2b.logistic.scheduler.entities;

import bbr.b2b.logistic.scheduler.data.interfaces.IPendingMailType;

public class PendingMailType implements IPendingMailType {

	private Long id;
	private String code;
	private String description;
	private Integer priority;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getDescription(){ 
		return this.description;
	}
	public Integer getPriority(){ 
		return this.priority;
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
	public void setPriority(Integer priority){ 
		this.priority = priority;
	}
}
