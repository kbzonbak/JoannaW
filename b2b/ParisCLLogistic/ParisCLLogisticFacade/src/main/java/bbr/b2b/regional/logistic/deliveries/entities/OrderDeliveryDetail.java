package bbr.b2b.regional.logistic.deliveries.entities;

import bbr.b2b.regional.logistic.deliveries.entities.OrderDelivery;
import bbr.b2b.regional.logistic.items.entities.Item;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOrderDeliveryDetail;

public class OrderDeliveryDetail implements IOrderDeliveryDetail {

	private OrderDeliveryDetailId id;
	private Double pendingunits;
	private Double availableunits;
	private Double receivedunits;
	private OrderDelivery orderdelivery;
	private Item item;
	private Location location;

	public OrderDeliveryDetailId getId(){ 
		return this.id;
	}
	public Double getPendingunits(){ 
		return this.pendingunits;
	}
	public Double getAvailableunits(){ 
		return this.availableunits;
	}
	public Double getReceivedunits(){ 
		return this.receivedunits;
	}
	public OrderDelivery getOrderdelivery(){ 
		return this.orderdelivery;
	}
	public Item getItem(){ 
		return this.item;
	}
	public Location getLocation(){ 
		return this.location;
	}
	public void setId(OrderDeliveryDetailId id){ 
		this.id = id;
	}
	public void setPendingunits(Double pendingunits){ 
		this.pendingunits = pendingunits;
	}
	public void setAvailableunits(Double availableunits){ 
		this.availableunits = availableunits;
	}
	public void setReceivedunits(Double receivedunits){ 
		this.receivedunits = receivedunits;
	}
	public void setOrderdelivery(OrderDelivery orderdelivery){ 
		this.orderdelivery = orderdelivery;
	}
	public void setItem(Item item){ 
		this.item = item;
	}
	public void setLocation(Location location){ 
		this.location = location;
	}
}
