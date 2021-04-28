package bbr.b2b.logistic.dvrorders.entities;

import bbr.b2b.logistic.dvrorders.entities.DvrOrder;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderStateType;

import java.time.LocalDateTime;

import bbr.b2b.logistic.dvrorders.data.interfaces.IDvrOrderState;

public class DvrOrderState implements IDvrOrderState {

	private Long id;
	private LocalDateTime when;
	private String who;
	private DvrOrder dvrorder;
	private DvrOrderStateType dvrorderstatetype;

	public Long getId(){ 
		return this.id;
	}
	public LocalDateTime getWhen(){ 
		return this.when;
	}
	public String getWho(){ 
		return this.who;
	}
	public DvrOrder getDvrorder(){ 
		return this.dvrorder;
	}
	public DvrOrderStateType getDvrorderstatetype(){ 
		return this.dvrorderstatetype;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setWhen(LocalDateTime when){ 
		this.when = when;
	}
	public void setWho(String who){ 
		this.who = who;
	}
	public void setDvrorder(DvrOrder dvrorder){ 
		this.dvrorder = dvrorder;
	}
	public void setDvrorderstatetype(DvrOrderStateType dvrorderstatetype){ 
		this.dvrorderstatetype = dvrorderstatetype;
	}
}
