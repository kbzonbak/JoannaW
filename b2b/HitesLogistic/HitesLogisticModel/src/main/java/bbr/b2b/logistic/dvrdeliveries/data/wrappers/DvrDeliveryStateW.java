package bbr.b2b.logistic.dvrdeliveries.data.wrappers;

import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDvrDeliveryState;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;

public class DvrDeliveryStateW extends ElementDTO implements IDvrDeliveryState {

	private LocalDateTime when;
	private String user;
	private String usertype;
	private LocalDateTime userwhen;
	private String comment;
	private Long dvrdeliveryid;
	private Long dvrdeliverystatetypeid;

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
	public Long getDvrdeliveryid(){ 
		return this.dvrdeliveryid;
	}
	public Long getDvrdeliverystatetypeid(){ 
		return this.dvrdeliverystatetypeid;
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
	public void setDvrdeliveryid(Long dvrdeliveryid){ 
		this.dvrdeliveryid = dvrdeliveryid;
	}
	public void setDvrdeliverystatetypeid(Long dvrdeliverystatetypeid){ 
		this.dvrdeliverystatetypeid = dvrdeliverystatetypeid;
	}
}
