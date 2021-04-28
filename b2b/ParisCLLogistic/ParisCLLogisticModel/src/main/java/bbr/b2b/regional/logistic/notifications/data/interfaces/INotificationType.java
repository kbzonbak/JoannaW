package bbr.b2b.regional.logistic.notifications.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface INotificationType extends IElement {

	public String getCode();
	public String getDescription();
	public void setCode(String code);
	public void setDescription(String description);
}
