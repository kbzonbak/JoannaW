package bbr.b2b.logistic.buyorders.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.buyorders.data.interfaces.IOrderAttribute;

public class OrderAttributeW extends ElementDTO implements IOrderAttribute {

	private String attributetype;
	private String code;
	private String value;
	private Long orderid;

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

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

}
