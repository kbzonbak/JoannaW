package bbr.b2b.regional.logistic.scheduler.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IPendingMessageType extends IElement {
	public String getCode();

	public void setCode(String code);

	public String getDescription();

	public void setDescription(String description);

	public Integer getPriority();

	public void setPriority(Integer priority);

}
