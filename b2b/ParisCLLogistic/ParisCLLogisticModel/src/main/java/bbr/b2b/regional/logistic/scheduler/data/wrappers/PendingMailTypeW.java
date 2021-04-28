package bbr.b2b.regional.logistic.scheduler.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.scheduler.data.interfaces.IPendingMailType;

public class PendingMailTypeW  extends ElementDTO implements IPendingMailType  {

	private String code;
	private String description;
	private Integer priority;

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
