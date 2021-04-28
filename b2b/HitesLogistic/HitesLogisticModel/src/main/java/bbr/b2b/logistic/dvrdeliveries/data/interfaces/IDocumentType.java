package bbr.b2b.logistic.dvrdeliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDocumentType extends IElement {

	public String getCode();
	public String getName();
	public void setCode(String code);
	public void setName(String name);
	public String getType();
	public void setType(String type);
}
