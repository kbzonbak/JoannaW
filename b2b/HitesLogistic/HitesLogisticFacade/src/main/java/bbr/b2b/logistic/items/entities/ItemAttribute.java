package bbr.b2b.logistic.items.entities;

import bbr.b2b.logistic.items.entities.Item;
import bbr.b2b.logistic.items.data.interfaces.IItemAttribute;

public class ItemAttribute implements IItemAttribute {

	private Long id;
	private String attributetype;
	private String code;
	private String value;
	private Item item;

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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
