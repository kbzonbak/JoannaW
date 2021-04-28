package bbr.b2b.logistic.items.entities;

import bbr.b2b.logistic.items.entities.Item;
import bbr.b2b.logistic.vendors.entities.Vendor;
import bbr.b2b.logistic.items.data.interfaces.IVendorItem;

public class VendorItem implements IVendorItem {

	private VendorItemId id;
	private String vendoritemcode;
	private Item item;
	private Vendor vendor;

	public VendorItemId getId(){ 
		return this.id;
	}
	public String getVendoritemcode(){ 
		return this.vendoritemcode;
	}
	public Item getItem(){ 
		return this.item;
	}
	public Vendor getVendor(){ 
		return this.vendor;
	}
	public void setId(VendorItemId id){ 
		this.id = id;
	}
	public void setVendoritemcode(String vendoritemcode){ 
		this.vendoritemcode = vendoritemcode;
	}
	public void setItem(Item item){ 
		this.item = item;
	}
	public void setVendor(Vendor vendor){ 
		this.vendor = vendor;
	}
}
