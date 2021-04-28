package bbr.b2b.logistic.ddcorders.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.ddcorders.data.interfaces.IDdcOrderStateType;

public class DdcOrderStateTypeW extends ElementDTO implements IDdcOrderStateType {

	private String code;
	private String name;
	private Boolean valid;
	private Boolean showable;
	private Boolean vendorselectable;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public Boolean getShowable() {
		return showable;
	}
	public void setShowable(Boolean showable) {
		this.showable = showable;
	}
	public Boolean getVendorselectable() {
		return vendorselectable;
	}
	public void setVendorselectable(Boolean vendorselectable) {
		this.vendorselectable = vendorselectable;
	}

}
