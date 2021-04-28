package bbr.b2b.regional.logistic.datings.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDock extends IElement {

	public String getCode();
	public Boolean getActive();
	public Integer getVisualorder();
	public void setCode(String code);
	public void setActive(Boolean active);
	public void setVisualorder(Integer visualorder);
}
