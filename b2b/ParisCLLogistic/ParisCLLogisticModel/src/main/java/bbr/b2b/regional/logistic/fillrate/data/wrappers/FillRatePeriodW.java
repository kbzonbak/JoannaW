package bbr.b2b.regional.logistic.fillrate.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.fillrate.data.interfaces.IFillRatePeriod;

public class FillRatePeriodW extends ElementDTO implements IFillRatePeriod {

	private String name;
	private Date since;
	private Date until;

	public String getName(){ 
		return this.name;
	}
	public Date getSince(){ 
		return this.since;
	}
	public Date getUntil(){ 
		return this.until;
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
