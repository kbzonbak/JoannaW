package bbr.b2b.logistic.customer.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.customer.data.interfaces.ISoaState;

public class SoaStateW extends ElementDTO implements ISoaState {

	private Date when;
	private Long orderid;
	private Long soastatetypeid;

	public Date getWhen(){ 
		return this.when;
	}
	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getSoastatetypeid(){ 
		return this.soastatetypeid;
	}
	public void setWhen(Date when){ 
		this.when = when;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setSoastatetypeid(Long soastatetypeid){ 
		this.soastatetypeid = soastatetypeid;
	}
}
