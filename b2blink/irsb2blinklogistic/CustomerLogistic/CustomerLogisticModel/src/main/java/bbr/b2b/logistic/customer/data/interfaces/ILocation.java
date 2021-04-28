package bbr.b2b.logistic.customer.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ILocation extends IElement {

	public String getCode();
	public String getName();
	public void setCode(String code);
	public void setName(String name);
	public String getEan13();
	public void setEan13(String ean13);
	public String getAddress();
	public void setAddress(String address);
}
