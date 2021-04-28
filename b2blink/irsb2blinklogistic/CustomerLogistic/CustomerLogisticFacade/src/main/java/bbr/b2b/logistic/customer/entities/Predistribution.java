package bbr.b2b.logistic.customer.entities;

import bbr.b2b.logistic.customer.entities.Detail;
import bbr.b2b.logistic.customer.entities.Location;
import bbr.b2b.logistic.customer.data.interfaces.IPredistribution;

public class Predistribution implements IPredistribution {

	private PredistributionId id;
	private Integer quantity;
	private Detail detail;
	private Location local;
	private String shipping_quantity;
	private String received_quantity;
	private String pending_quantity;

	public PredistributionId getId(){ 
		return this.id;
	}
	public Integer getQuantity(){ 
		return this.quantity;
	}
	public Detail getDetail(){ 
		return this.detail;
	}
	public Location getLocal(){ 
		return this.local;
	}
	public void setId(PredistributionId id){ 
		this.id = id;
	}
	public void setQuantity(Integer quantity){ 
		this.quantity = quantity;
	}
	public void setDetail(Detail detail){ 
		this.detail = detail;
	}
	public void setLocal(Location local){ 
		this.local = local;
	}
	public String getShipping_quantity() {
		return shipping_quantity;
	}
	public void setShipping_quantity(String shipping_quantity) {
		this.shipping_quantity = shipping_quantity;
	}
	public String getReceived_quantity() {
		return received_quantity;
	}
	public void setReceived_quantity(String received_quantity) {
		this.received_quantity = received_quantity;
	}
	public String getPending_quantity() {
		return pending_quantity;
	}
	public void setPending_quantity(String pending_quantity) {
		this.pending_quantity = pending_quantity;
	}
}
