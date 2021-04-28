package bbr.b2b.logistic.scheduler.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IPendingMailType extends IElement {

	public String getCode();
	public String getDescription();
	public Integer getPriority();
	public void setCode(String code);
	public void setDescription(String description);
	public void setPriority(Integer priority);
}
