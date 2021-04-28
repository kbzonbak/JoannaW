package bbr.b2b.logistic.customer.entities;

import java.util.Date;
import bbr.b2b.logistic.customer.entities.Order;
import bbr.b2b.logistic.customer.entities.SoaStateType;
import bbr.b2b.logistic.customer.data.interfaces.ISoaState;

public class SoaState implements ISoaState {

	private Long id;
	private Date when;
	private Order order;
	private SoaStateType soastatetype;

	public Long getId(){ 
		return this.id;
	}
	public Date getWhen(){ 
		return this.when;
	}
	public Order getOrder(){ 
		return this.order;
	}
	public SoaStateType getSoastatetype(){ 
		return this.soastatetype;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setWhen(Date when){ 
		this.when = when;
	}
	public void setOrder(Order order){ 
		this.order = order;
	}
	public void setSoastatetype(SoaStateType soastatetype){ 
		this.soastatetype = soastatetype;
	}
}
