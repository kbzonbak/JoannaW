package bbr.b2b.logistic.buyorders.entities;

import bbr.b2b.logistic.buyorders.data.interfaces.IOrderAttribute;

public class OrderAttribute implements IOrderAttribute {

	private Long id;
	private String attributetype;
	private String code;
	private String value;
	private Order order;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
