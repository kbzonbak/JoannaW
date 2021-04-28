package bbr.b2b.logistic.datings.data.wrappers;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.datings.data.interfaces.IReserve;

public class ReserveW extends ElementDTO implements IReserve {

	private LocalDateTime when;
	private Long locationid;

	public LocalDateTime getWhen(){ 
		return this.when;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public void setWhen(LocalDateTime when){ 
		this.when = when;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
}
