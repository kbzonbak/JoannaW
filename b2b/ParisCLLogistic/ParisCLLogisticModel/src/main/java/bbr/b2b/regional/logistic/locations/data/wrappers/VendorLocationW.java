package bbr.b2b.regional.logistic.locations.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.locations.data.interfaces.IVendorLocation;
import bbr.b2b.regional.logistic.locations.data.interfaces.IVendorLocationPK;

public class VendorLocationW implements IVendorLocation, IVendorLocationPK {

	private Integer lastnumber;
	private Long vendorid;
	private Long locationid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = vendorid.longValue() - ((IVendorLocationPK) arg0).getVendorid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = locationid.longValue() - ((IVendorLocationPK) arg0).getLocationid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Integer getLastnumber(){ 
		return this.lastnumber;
	}
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public void setLastnumber(Integer lastnumber){ 
		this.lastnumber = lastnumber;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
}
