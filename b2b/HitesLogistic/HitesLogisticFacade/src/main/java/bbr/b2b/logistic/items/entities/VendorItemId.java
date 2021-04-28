package bbr.b2b.logistic.items.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.items.data.interfaces.IVendorItePK;

public class VendorItemId implements IVendorItePK {

	private Long itemid;
	private Long vendorid;

	public VendorItemId(){
	}
	public VendorItemId(Long itemid, Long vendorid){
		this.itemid = itemid;
		this.vendorid = vendorid;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof VendorItemId){
			VendorItemId that = (VendorItemId) o;
			return this.itemid.equals(that.itemid) && this.vendorid.equals(that.vendorid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return itemid.hashCode() + vendorid.hashCode();
	}
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

	public Long getItemid(){ 
		return this.itemid;
	}
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
}
