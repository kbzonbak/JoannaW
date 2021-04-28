package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDeliveryState;

public class DeliveryStateW extends ElementDTO implements IDeliveryState {

	private Date when;
	private Long deliveryid;
	private Long deliverystatetypeid;

	public Date getWhen(){ 
		return this.when;
	}
	public Long getDeliveryid(){ 
		return this.deliveryid;
	}
	public Long getDeliverystatetypeid(){ 
		return this.deliverystatetypeid;
	}
	public void setWhen(Date when){ 
		this.when = when;
	}
	public void setDeliveryid(Long deliveryid){ 
		this.deliveryid = deliveryid;
	}
	public void setDeliverystatetypeid(Long deliverystatetypeid){ 
		this.deliverystatetypeid = deliverystatetypeid;
	}
}
