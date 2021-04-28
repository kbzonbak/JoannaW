package bbr.b2b.regional.logistic.locations.entities;

import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.locations.data.interfaces.IVendorLocation;

public class VendorLocation implements IVendorLocation {

	private VendorLocationId id;
	private Integer lastnumber;
	private Vendor vendor;
	private Location location;

	public VendorLocationId getId(){ 
		return this.id;
	}
	public Integer getLastnumber(){ 
		return this.lastnumber;
	}
	public Vendor getVendor(){ 
		return this.vendor;
	}
	public Location getLocation(){ 
		return this.location;
	}
	public void setId(VendorLocationId id){ 
		this.id = id;
	}
	public void setLastnumber(Integer lastnumber){ 
		this.lastnumber = lastnumber;
	}
	public void setVendor(Vendor vendor){ 
		this.vendor = vendor;
	}
	public void setLocation(Location location){ 
		this.location = location;
	}
}
