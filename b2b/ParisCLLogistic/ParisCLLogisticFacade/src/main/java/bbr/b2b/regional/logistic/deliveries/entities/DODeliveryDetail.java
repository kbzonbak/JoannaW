package bbr.b2b.regional.logistic.deliveries.entities;

import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;
import bbr.b2b.regional.logistic.items.entities.Item;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDODeliveryDetail;

public class DODeliveryDetail implements IDODeliveryDetail {

	private DODeliveryDetailId id;
	private Double pendingunits;
	private Double availableunits;
	private Double receivedunits;
	private DODelivery dodelivery;
	private Item item;

	public DODeliveryDetailId getId(){ 
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
	public DODelivery getDodelivery(){ 
		return this.dodelivery;
	}
	public Item getItem(){ 
		return this.item;
	}
	public void setId(DODeliveryDetailId id){ 
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
	public void setDodelivery(DODelivery dodelivery){ 
		this.dodelivery = dodelivery;
	}
	public void setItem(Item item){ 
		this.item = item;
	}
}
