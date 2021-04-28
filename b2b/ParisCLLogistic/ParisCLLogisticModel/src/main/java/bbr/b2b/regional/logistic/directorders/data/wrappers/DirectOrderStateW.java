package bbr.b2b.regional.logistic.directorders.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.directorders.data.interfaces.IDirectOrderState;

public class DirectOrderStateW extends ElementDTO implements IDirectOrderState {

	private Date when;
	private String who;
	private String comment;
	private Long orderid;
	private Long orderstatetypeid;

	public Date getWhen(){ 
		return this.when;
	}
	public String getWho(){ 
		return this.who;
	}
	public String getComment(){ 
		return this.comment;
	}
	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getOrderstatetypeid(){ 
		return this.orderstatetypeid;
	}
	public void setWhen(Date when){ 
		this.when = when;
	}
	public void setWho(String who){ 
		this.who = who;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setOrderstatetypeid(Long orderstatetypeid){ 
		this.orderstatetypeid = orderstatetypeid;
	}
}
