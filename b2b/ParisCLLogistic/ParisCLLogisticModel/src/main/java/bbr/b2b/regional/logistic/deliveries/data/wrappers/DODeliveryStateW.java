package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDODeliveryState;

public class DODeliveryStateW extends ElementDTO implements IDODeliveryState {

	private Date when;
	private Date deliverystatedate;
	private String who;
	private String comment;
	private Long deliveryid;
	private Long deliverystatetypeid;

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
	public Long getDeliveryid(){ 
		return this.deliveryid;
	}
	public Long getDeliverystatetypeid(){ 
		return this.deliverystatetypeid;
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
	public void setDeliveryid(Long deliveryid){ 
		this.deliveryid = deliveryid;
	}
	public void setDeliverystatetypeid(Long deliverystatetypeid){ 
		this.deliverystatetypeid = deliverystatetypeid;
	}
}
