package bbr.b2b.logistic.customer.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IPendingMessageType extends IElement {

	public String getCode();
	public String getDescription();
	public Integer getPriority();
	public void setCode(String code);
	public void setDescription(String description);
	public void setPriority(Integer priority);
}
