package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IOutDocument extends IElement {

	public String getRefdocument();
	public void setRefdocument(String refdocument);
}
