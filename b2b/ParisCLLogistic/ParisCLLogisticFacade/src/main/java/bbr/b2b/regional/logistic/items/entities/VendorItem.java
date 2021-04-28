package bbr.b2b.regional.logistic.items.entities;

import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.items.entities.Item;
import bbr.b2b.regional.logistic.items.data.interfaces.IVendorItem;

public class VendorItem implements IVendorItem {

	private VendorItemId id;
	private String vendoritemcode;
	private Boolean directdelivery;
	private Vendor vendor;
	private Item item;

	public VendorItemId getId(){ 
		return this.id;
	}
	public String getVendoritemcode(){ 
		return this.vendoritemcode;
	}
	public Boolean getDirectdelivery(){ 
		return this.directdelivery;
	}
	public Vendor getVendor(){ 
		return this.vendor;
	}
	public Item getItem(){ 
		return this.item;
	}
	public void setId(VendorItemId id){ 
		this.id = id;
	}
	public void setVendoritemcode(String vendoritemcode){ 
		this.vendoritemcode = vendoritemcode;
	}
	public void setDirectdelivery(Boolean directdelivery){ 
		this.directdelivery = directdelivery;
	}
	public void setVendor(Vendor vendor){ 
		this.vendor = vendor;
	}
	public void setItem(Item item){ 
		this.item = item;
	}
}
