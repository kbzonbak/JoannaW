package bbr.b2b.logistic.items.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.items.data.interfaces.IItemAttribute;

public class ItemAttributeW extends ElementDTO implements IItemAttribute {

	private String attributetype;
	private String code;
	private String value;
	private Long itemid;

	public String getAttributetype() {
		return attributetype;
	}

	public void setAttributetype(String attributetype) {
		this.attributetype = attributetype;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
}
