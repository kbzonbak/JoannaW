package bbr.b2b.logistic.scheduler.entities;

import bbr.b2b.logistic.scheduler.data.interfaces.IPendingMessageType;

public class PendingMessageType implements IPendingMessageType {

	private Long id;
	private String code;
	private String description;
	private Integer priority;
	private String headerparameters;

	public Long getId() {
		return this.id;
	}

	public String getCode() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public String getHeaderparameters() {
		return headerparameters;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setHeaderparameters(String headerparameters) {
		this.headerparameters = headerparameters;
	}

}
