package bbr.b2b.regional.logistic.fillrate.entities;

import java.util.Date;
import bbr.b2b.regional.logistic.fillrate.data.interfaces.IFillRatePeriod;

public class FillRatePeriod implements IFillRatePeriod {

	private Long id;
	private String name;
	private Date since;
	private Date until;

	public Long getId(){ 
		return this.id;
	}
	public String getName(){ 
		return this.name;
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
	public void setName(String name){ 
		this.name = name;
	}
	public void setSince(Date since){ 
		this.since = since;
	}
	public void setUntil(Date until){ 
		this.until = until;
	}
}
