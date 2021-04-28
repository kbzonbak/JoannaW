package bbr.b2b.regional.logistic.vendors.entities;

import java.util.Date;
import bbr.b2b.regional.logistic.vendors.data.interfaces.IRanking;

public class Ranking implements IRanking {

	private Long id;
	private Date when;
	private Date since;
	private Date until;

	public Long getId(){ 
		return this.id;
	}
	public Date getWhen(){ 
		return this.when;
	}
	public Date getSince(){ 
		return this.since;
	}
	public Date getUntil(){ 
		return this.until;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setWhen(Date when){ 
		this.when = when;
	}
	public void setSince(Date since){ 
		this.since = since;
	}
	public void setUntil(Date until){ 
		this.until = until;
	}
}
