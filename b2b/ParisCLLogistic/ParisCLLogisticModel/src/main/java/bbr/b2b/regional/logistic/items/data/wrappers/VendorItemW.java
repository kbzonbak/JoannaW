package bbr.b2b.regional.logistic.items.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.items.data.interfaces.IVendorItem;
import bbr.b2b.regional.logistic.items.data.interfaces.IVendorItemPK;

public class VendorItemW implements IVendorItemPK, IVendorItem {

	private String vendoritemcode;
	private Boolean directdelivery;
	private Long vendorid;
	private Long itemid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = vendorid.longValue() - ((IVendorItemPK) arg0).getVendorid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IVendorItemPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public String getVendoritemcode(){ 
		return this.vendoritemcode;
	}
	public Boolean getDirectdelivery(){ 
		return this.directdelivery;
	}
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public void setVendoritemcode(String vendoritemcode){ 
		this.vendoritemcode = vendoritemcode;
	}
	public void setDirectdelivery(Boolean directdelivery){ 
		this.directdelivery = directdelivery;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
}
