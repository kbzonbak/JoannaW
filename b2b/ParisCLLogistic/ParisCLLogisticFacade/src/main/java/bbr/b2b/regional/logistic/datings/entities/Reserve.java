package bbr.b2b.regional.logistic.datings.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import bbr.b2b.regional.logistic.datings.data.interfaces.IReserve;
import bbr.b2b.regional.logistic.locations.entities.Location;

public class Reserve implements IReserve {

	private Long id;
	private Date when;
	private Location location;
	private Set<ReserveDetail> reservedetail = new HashSet<ReserveDetail>(0);

	public Long getId(){ 
		return this.id;
	}
	public Date getWhen(){ 
		return this.when;
	}
	public Location getLocation(){ 
		return this.location;
	}
	public Set<ReserveDetail> getReservedetail(){ 
		return this.reservedetail;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setWhen(Date when){ 
		this.when = when;
	}
	public void setLocation(Location location){ 
		this.location = location;
	}
	public void setReservedetail(Set<ReserveDetail> reservedetail){ 
		this.reservedetail = reservedetail;
	}
}
