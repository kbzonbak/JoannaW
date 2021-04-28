package bbr.b2b.regional.logistic.buyorders.entities;

import bbr.b2b.regional.logistic.buyorders.data.interfaces.IItemAtc;
import bbr.b2b.regional.logistic.items.entities.Item;

public class ItemAtc implements IItemAtc {

	private ItemAtcId id;
	private Long curve;
	private Item item;
	private Atc atc;
	public ItemAtcId getId() {
		return id;
	}
	public void setId(ItemAtcId id) {
		this.id = id;
	}
	public Long getCurve() {
		return curve;
	}
	public void setCurve(Long curve) {
		this.curve = curve;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Atc getAtc() {
		return atc;
	}
	public void setAtc(Atc atc) {
		this.atc = atc;
	}
}
