package bbr.b2b.logistic.scheduler.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IPendingMessageType extends IElement {

	public String getCode();
	public String getDescription();
	public Integer getPriority();
	public String getHeaderparameters();
	public void setCode(String code);
	public void setDescription(String description);
	public void setPriority(Integer priority);
	public void setHeaderparameters(String headerparameters);
	
}
