package bbr.b2b.regional.logistic.directorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDirectOrderStateType extends IElement {

	public String getCode();
	public String getName();
	public Boolean getValid();
	public Boolean getShowable();
	public Boolean getVendorselectable();
	public void setCode(String code);
	public void setName(String name);
	public void setValid(Boolean valid);
	public void setShowable(Boolean showable);
	public void setVendorselectable(Boolean vendorselectable);
}
