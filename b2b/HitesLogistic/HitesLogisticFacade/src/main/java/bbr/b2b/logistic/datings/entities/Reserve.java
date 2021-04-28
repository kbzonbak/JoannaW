package bbr.b2b.logistic.datings.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.datings.data.interfaces.IReserve;
import bbr.b2b.logistic.locations.entities.Location;

public class Reserve implements IReserve {

	private Long id;
	private LocalDateTime when;
	private Location location;

	public Long getId(){ 
		return this.id;
	}
	public LocalDateTime getWhen(){ 
		return this.when;
	}
	public Location getLocation(){ 
		return this.location;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setWhen(LocalDateTime when){ 
		this.when = when;
	}
	public void setLocation(Location location){ 
		this.location = location;
	}
}
