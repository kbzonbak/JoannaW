package bbr.b2b.regional.logistic.items.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.items.data.interfaces.IVendorItemPK;

public class VendorItemId implements IVendorItemPK {

	private Long vendorid;
	private Long itemid;

	public VendorItemId(){
	}
	public VendorItemId(Long vendorid, Long itemid){
		this.vendorid = vendorid;
		this.itemid = itemid;
	}

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
	public boolean equals(Object o){
		if (o != null && o instanceof VendorItemId){
			VendorItemId that = (VendorItemId) o;
			return this.vendorid.equals(that.vendorid) && this.itemid.equals(that.itemid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return vendorid.hashCode() + itemid.hashCode();
	}

	public Long getVendorid(){ 
		return this.vendorid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
}
