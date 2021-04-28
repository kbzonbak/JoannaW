package bbr.b2b.regional.logistic.directorders.entities;

import java.util.Date;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrder;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderStateType;
import bbr.b2b.regional.logistic.directorders.data.interfaces.IDirectOrderState;

public class DirectOrderState implements IDirectOrderState {

	private Long id;
	private Date when;
	private String who;
	private String comment;
	private DirectOrder directorder;
	private DirectOrderStateType directorderstatetype;

	public Long getId(){ 
		return this.id;
	}
	public Date getWhen(){ 
		return this.when;
	}
	public String getWho(){ 
		return this.who;
	}
	public String getComment(){ 
		return this.comment;
	}
	public DirectOrder getDirectorder(){ 
		return this.directorder;
	}
	public DirectOrderStateType getDirectorderstatetype(){ 
		return this.directorderstatetype;
	}
	public void setId(Long id){ 
		this.id = id;
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
	public void setDirectorder(DirectOrder directorder){ 
		this.directorder = directorder;
	}
	public void setDirectorderstatetype(DirectOrderStateType directorderstatetype){ 
		this.directorderstatetype = directorderstatetype;
	}
}
