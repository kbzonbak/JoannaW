package bbr.b2b.logistic.dvrorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDvrOrderStateType extends IElement {

	public String getCode();
	public String getDescription();
	public Boolean getValid();
	public Boolean getVisible();
	public void setCode(String code);
	public void setDescription(String description);
	public void setValid(Boolean valid);
	public void setVisible(Boolean visible);
}
