package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDODeliveryDetailPK;

public class DODeliveryDetailPK implements IDODeliveryDetailPK {

	private Long dodeliveryid;
	private Long itemid;

	public DODeliveryDetailPK(){
	}
	public DODeliveryDetailPK(Long dodeliveryid, Long itemid){
		this.dodeliveryid = dodeliveryid;
		this.itemid = itemid;
	}

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = dodeliveryid.longValue() - ((IDODeliveryDetailPK) arg0).getDodeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IDODeliveryDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}
	public boolean equals(Object o){
		if (o != null && o instanceof DODeliveryDetailPK){
			DODeliveryDetailPK that = (DODeliveryDetailPK) o;
			return this.dodeliveryid.equals(that.dodeliveryid) && this.itemid.equals(that.itemid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return dodeliveryid.hashCode() + itemid.hashCode();
	}

	public Long getDodeliveryid(){ 
		return this.dodeliveryid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public void setDodeliveryid(Long dodeliveryid){ 
		this.dodeliveryid = dodeliveryid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
}
