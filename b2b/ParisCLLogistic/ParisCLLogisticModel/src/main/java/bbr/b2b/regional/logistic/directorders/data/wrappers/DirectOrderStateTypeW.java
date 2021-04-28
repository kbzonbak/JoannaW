package bbr.b2b.regional.logistic.directorders.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.directorders.data.interfaces.IDirectOrderStateType;

public class DirectOrderStateTypeW extends ElementDTO implements IDirectOrderStateType {

	private String code;
	private String name;
	private Boolean valid;
	private Boolean showable;
	private Boolean vendorselectable;

	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public Boolean getValid(){ 
		return this.valid;
	}
	public Boolean getShowable(){ 
		return this.showable;
	}
	public Boolean getVendorselectable(){ 
		return this.vendorselectable;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setValid(Boolean valid){ 
		this.valid = valid;
	}
	public void setShowable(Boolean showable){ 
		this.showable = showable;
	}
	public void setVendorselectable(Boolean vendorselectable){ 
		this.vendorselectable = vendorselectable;
	}
}
