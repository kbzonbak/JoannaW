package bbr.b2b.regional.logistic.locations.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.locations.data.interfaces.IVendorLocationPK;

public class VendorLocationPK implements IVendorLocationPK {

	private Long vendorid;
	private Long locationid;

	public VendorLocationPK(){
	}
	public VendorLocationPK(Long vendorid, Long locationid){
		this.vendorid = vendorid;
		this.locationid = locationid;
	}

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
	public boolean equals(Object o){
		if (o != null && o instanceof VendorLocationPK){
			VendorLocationPK that = (VendorLocationPK) o;
			return this.vendorid.equals(that.vendorid) && this.locationid.equals(that.locationid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return vendorid.hashCode() + locationid.hashCode();
	}

	public Long getVendorid(){ 
		return this.vendorid;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
}
