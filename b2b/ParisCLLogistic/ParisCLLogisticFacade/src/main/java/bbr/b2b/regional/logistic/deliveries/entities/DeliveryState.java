package bbr.b2b.regional.logistic.deliveries.entities;

import java.util.Date;
import bbr.b2b.regional.logistic.deliveries.entities.Delivery;
import bbr.b2b.regional.logistic.deliveries.entities.DeliveryStateType;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDeliveryState;

public class DeliveryState implements IDeliveryState {

	private Long id;
	private Date when;
	private Delivery delivery;
	private DeliveryStateType deliverystatetype;

	public Long getId(){ 
		return this.id;
	}
	public Date getWhen(){ 
		return this.when;
	}
	public Delivery getDelivery(){ 
		return this.delivery;
	}
	public DeliveryStateType getDeliverystatetype(){ 
		return this.deliverystatetype;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setWhen(Date when){ 
		this.when = when;
	}
	public void setDelivery(Delivery delivery){ 
		this.delivery = delivery;
	}
	public void setDeliverystatetype(DeliveryStateType deliverystatetype){ 
		this.deliverystatetype = deliverystatetype;
	}
}
