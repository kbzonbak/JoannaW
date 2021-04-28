package bbr.b2b.regional.logistic.buyorders.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IOrderState;

public class OrderStateW extends ElementDTO implements IOrderState {

	private Date when;
	private Long orderid;
	private Long orderstatetypeid;

	public Date getWhen(){ 
		return this.when;
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
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setOrderstatetypeid(Long orderstatetypeid){ 
		this.orderstatetypeid = orderstatetypeid;
	}
}
