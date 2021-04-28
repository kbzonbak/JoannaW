package bbr.b2b.logistic.ddcorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDdcOrderStateType extends IElement {

	public String getCode();
	public void setCode(String code);
	public String getName();
	public void setName(String name);
	public Boolean getValid();
	public void setValid(Boolean valid);
	public Boolean getShowable();
	public void setShowable(Boolean showable);
	public Boolean getVendorselectable();
	public void setVendorselectable(Boolean vendorselectable);
}
