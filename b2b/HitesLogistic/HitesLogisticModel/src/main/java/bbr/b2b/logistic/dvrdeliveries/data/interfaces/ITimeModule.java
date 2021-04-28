package bbr.b2b.logistic.dvrdeliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ITimeModule extends IElement {

	public String getDescription();
	public Integer getVisualorder();
	public void setDescription(String description);
	public void setVisualorder(Integer visualorder);
}
