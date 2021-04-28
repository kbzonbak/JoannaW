package bbr.b2b.regional.logistic.deliveries.entities;

import java.util.Date;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryStateType;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDODeliveryState;

public class DODeliveryState implements IDODeliveryState {

	private Long id;
	private Date when;
	private Date deliverystatedate;
	private String who;
	private String comment;
	private DODelivery delivery;
	private DODeliveryStateType deliverystatetype;

	public Long getId(){ 
		return this.id;
	}
	public Date getWhen(){ 
		return this.when;
	}
	public Date getDeliverystatedate() {
		return deliverystatedate;
	}
	public String getWho(){ 
		return this.who;
	}
	public String getComment(){ 
		return this.comment;
	}
	public DODelivery getDelivery(){ 
		return this.delivery;
	}
	public DODeliveryStateType getDeliverystatetype(){ 
		return this.deliverystatetype;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setWhen(Date when){ 
		this.when = when;
	}
	public void setDeliverystatedate(Date deliverystatedate) {
		this.deliverystatedate = deliverystatedate;
	}
	public void setWho(String who){ 
		this.who = who;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public void setDelivery(DODelivery delivery){ 
		this.delivery = delivery;
	}
	public void setDeliverystatetype(DODeliveryStateType deliverystatetype){ 
		this.deliverystatetype = deliverystatetype;
	}
}
