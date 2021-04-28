package bbr.b2b.regional.logistic.vendors.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.vendors.data.interfaces.IRanking;

public class RankingW extends ElementDTO implements IRanking {

	private Date when;
	private Date since;
	private Date until;

	public Date getWhen(){ 
		return this.when;
	}
	public Date getSince(){ 
		return this.since;
	}
	public Date getUntil(){ 
		return this.until;
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
