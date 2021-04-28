package bbr.b2b.logistic.items.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IItemAttribute extends IElement {

	public String getAttributetype();
	public String getCode();
	public String getValue();
	public void setAttributetype(String attributetype);
	public void setCode(String code);
	public void setValue(String value);
	
}
