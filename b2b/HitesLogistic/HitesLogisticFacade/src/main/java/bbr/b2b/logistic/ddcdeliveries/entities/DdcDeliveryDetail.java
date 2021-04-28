package bbr.b2b.logistic.ddcdeliveries.entities;

import bbr.b2b.logistic.ddcdeliveries.data.interfaces.IDdcDeliveryDetail;
import bbr.b2b.logistic.items.entities.Item;

public class DdcDeliveryDetail implements IDdcDeliveryDetail {

	private DdcDeliveryDetailId id;
	private Double pendingunits;
	private Double availableunits;
	private Double receivedunits;
	private DdcDelivery ddcdelivery;
	private Item item;
	
	public DdcDeliveryDetailId getId() {
		return id;
	}
	public void setId(DdcDeliveryDetailId id) {
		this.id = id;
	}
	public Double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}
	public Double getAvailableunits() {
		return availableunits;
	}
	public void setAvailableunits(Double availableunits) {
		this.availableunits = availableunits;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public DdcDelivery getDdcdelivery() {
		return ddcdelivery;
	}
	public void setDdcdelivery(DdcDelivery ddcdelivery) {
		this.ddcdelivery = ddcdelivery;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
}
