package bbr.b2b.regional.logistic.buyorders.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IItemAtcPK;

public class ItemAtcPK implements IItemAtcPK {

	private Long atcid;
	private Long itemid;

	public ItemAtcPK(){
	}
	public ItemAtcPK(Long itemid, Long atcid){
		this.atcid = atcid;
		this.itemid = itemid;
	}

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = atcid.longValue() - ((IItemAtcPK) arg0).getAtcid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IItemAtcPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}
	public boolean equals(Object o){
		if (o != null && o instanceof ItemAtcPK){
			ItemAtcPK that = (ItemAtcPK) o;
			return this.atcid.equals(that.atcid) && this.itemid.equals(that.itemid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return atcid.hashCode() + itemid.hashCode();
	}

	public Long getItemid(){ 
		return this.itemid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
	public Long getAtcid() {
		return atcid;
	}
	public void setAtcid(Long atcid) {
		this.atcid = atcid;
	}
	
}
