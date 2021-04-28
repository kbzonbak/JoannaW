package bbr.b2b.logistic.datings.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDock extends IElement {

	public String getCode();
	public Integer getVisualorder();
	public Boolean getActive();
	public void setCode(String code);
	public void setVisualorder(Integer visualorder);
	public void setActive(Boolean active);
}
