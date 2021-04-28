package bbr.b2b.regional.logistic.datings.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.datings.data.interfaces.IReserve;

public class ReserveW extends ElementDTO implements IReserve {

	private Date when;
	private Long locationid;

	public Date getWhen(){ 
		return this.when;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public void setWhen(Date when){ 
		this.when = when;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
}
