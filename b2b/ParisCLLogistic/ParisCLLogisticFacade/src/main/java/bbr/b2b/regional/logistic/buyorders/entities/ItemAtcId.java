package bbr.b2b.regional.logistic.buyorders.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IItemAtcPK;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IOrderDetailPK;

public class ItemAtcId implements IItemAtcPK {

	private Long atcid;
	private Long itemid;

	public ItemAtcId(){
	}
	public ItemAtcId(Long atcid, Long itemid){
		this.atcid = atcid;
		this.itemid = itemid;
	}

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = atcid.longValue() - ((IOrderDetailPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IOrderDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}
	public boolean equals(Object o){
		if (o != null && o instanceof ItemAtcId){
			ItemAtcId that = (ItemAtcId) o;
			return this.atcid.equals(that.atcid) && this.itemid.equals(that.itemid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return atcid.hashCode() + itemid.hashCode();
	}
	public Long getAtcid() {
		return atcid;
	}
	public void setAtcid(Long atcid) {
		this.atcid = atcid;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	
}
