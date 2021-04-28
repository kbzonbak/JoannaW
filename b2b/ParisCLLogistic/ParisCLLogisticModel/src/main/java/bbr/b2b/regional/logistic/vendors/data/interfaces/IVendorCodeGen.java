package bbr.b2b.regional.logistic.vendors.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IVendorCodeGen extends IElement {

	public Long getNumber();
	public String getAlfanumber();
	public String getBase();
	public void setNumber(Long number);
	public void setAlfanumber(String alfanumber);
	public void setBase(String base);
}
