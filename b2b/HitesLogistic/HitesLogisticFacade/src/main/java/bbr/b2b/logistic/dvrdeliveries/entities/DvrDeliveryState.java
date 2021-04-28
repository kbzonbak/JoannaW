package bbr.b2b.logistic.dvrdeliveries.entities;

import bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDeliveryStateType;

import java.time.LocalDateTime;

import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDvrDeliveryState;

public class DvrDeliveryState implements IDvrDeliveryState {

	private Long id;
	private LocalDateTime when;
	private String user;
	private String usertype;
	private LocalDateTime userwhen;
	private String comment;
	private DvrDelivery dvrdelivery;
	private DvrDeliveryStateType dvrdeliverystatetype;

	public Long getId(){ 
		return this.id;
	}
	public LocalDateTime getWhen(){ 
		return this.when;
	}
	public String getUser(){ 
		return this.user;
	}
	public String getUsertype(){ 
		return this.usertype;
	}
	public LocalDateTime getUserwhen(){ 
		return this.userwhen;
	}
	public String getComment(){ 
		return this.comment;
	}
	public DvrDelivery getDvrdelivery(){ 
		return this.dvrdelivery;
	}
	public DvrDeliveryStateType getDvrdeliverystatetype(){ 
		return this.dvrdeliverystatetype;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setWhen(LocalDateTime when){ 
		this.when = when;
	}
	public void setUser(String user){ 
		this.user = user;
	}
	public void setUsertype(String usertype){ 
		this.usertype = usertype;
	}
	public void setUserwhen(LocalDateTime userwhen){ 
		this.userwhen = userwhen;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public void setDvrdelivery(DvrDelivery dvrdelivery){ 
		this.dvrdelivery = dvrdelivery;
	}
	public void setDvrdeliverystatetype(DvrDeliveryStateType dvrdeliverystatetype){ 
		this.dvrdeliverystatetype = dvrdeliverystatetype;
	}
}
