package bbr.b2b.logistic.notifications.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface INotificationType extends IElement {

	public String getCode();
	public String getDescription();
	public Boolean getVisible();
	public Long getVisualorder();
	public void setCode(String code);
	public void setDescription(String description);
	public void setVisible(Boolean visible);
	public void setVisualorder(Long visualorder);
}
