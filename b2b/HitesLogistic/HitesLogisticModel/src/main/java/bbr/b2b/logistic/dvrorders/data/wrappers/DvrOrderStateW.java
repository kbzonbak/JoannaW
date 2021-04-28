package bbr.b2b.logistic.dvrorders.data.wrappers;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.dvrorders.data.interfaces.IDvrOrderState;

public class DvrOrderStateW extends ElementDTO implements IDvrOrderState {

	private LocalDateTime when;
	private String who;
	private Long dvrorderid;
	private Long dvrorderstatetypeid;

	public LocalDateTime getWhen(){ 
		return this.when;
	}
	public String getWho(){ 
		return this.who;
	}
	public Long getDvrorderid(){ 
		return this.dvrorderid;
	}
	public Long getDvrorderstatetypeid(){ 
		return this.dvrorderstatetypeid;
	}
	public void setWhen(LocalDateTime when){ 
		this.when = when;
	}
	public void setWho(String who){ 
		this.who = who;
	}
	public void setDvrorderid(Long dvrorderid){ 
		this.dvrorderid = dvrorderid;
	}
	public void setDvrorderstatetypeid(Long dvrorderstatetypeid){ 
		this.dvrorderstatetypeid = dvrorderstatetypeid;
	}
}
