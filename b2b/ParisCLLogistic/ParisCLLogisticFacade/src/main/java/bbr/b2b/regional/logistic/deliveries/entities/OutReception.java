package bbr.b2b.regional.logistic.deliveries.entities;

import java.util.Date;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOutReception;

public class OutReception implements IOutReception {

	private Long id;
	private Long number;
	private Long outdelivery;
	private Date receptiondate;
	private Boolean inprocess;
	private Vendor vendor;
	private Location location;

	public Long getId(){ 
		return this.id;
	}
	public Long getNumber(){ 
		return this.number;
	}
	public Long getOutdelivery(){ 
		return this.outdelivery;
	}
	public Date getReceptiondate(){ 
		return this.receptiondate;
	}
	public Boolean getInprocess(){ 
		return this.inprocess;
	}
	public Vendor getVendor(){ 
		return this.vendor;
	}
	public Location getLocation(){ 
		return this.location;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setNumber(Long number){ 
		this.number = number;
	}
	public void setOutdelivery(Long outdelivery){ 
		this.outdelivery = outdelivery;
	}
	public void setReceptiondate(Date receptiondate){ 
		this.receptiondate = receptiondate;
	}
	public void setInprocess(Boolean inprocess){ 
		this.inprocess = inprocess;
	}
	public void setVendor(Vendor vendor){ 
		this.vendor = vendor;
	}
	public void setLocation(Location location){ 
		this.location = location;
	}
}
