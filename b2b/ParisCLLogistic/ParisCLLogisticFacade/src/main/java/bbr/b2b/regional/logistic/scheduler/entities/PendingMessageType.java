package bbr.b2b.regional.logistic.scheduler.entities;

import bbr.b2b.regional.logistic.scheduler.data.interfaces.IPendingMessageType;

public class PendingMessageType implements IPendingMessageType {

	private Long id;

	private String code;

	private String description;

	private Integer priority;

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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
