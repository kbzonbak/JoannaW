package bbr.b2b.logistic.items.data.wrappers;

import bbr.b2b.logistic.items.data.interfaces.IVendorItem;
import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.items.data.interfaces.IVendorItePK;

public class VendorItemW implements IVendorItem, IVendorItePK {

	private String vendoritemcode;
	private Long itemid;
	private Long vendorid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = itemid.longValue() - ((IVendorItePK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = vendorid.longValue() - ((IVendorItePK) arg0).getVendorid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public String getVendoritemcode(){ 
		return this.vendoritemcode;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public void setVendoritemcode(String vendoritemcode){ 
		this.vendoritemcode = vendoritemcode;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
}
