package bbr.b2b.regional.logistic.buyorders.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IPreDeliveryDetailPK;

public class PreDeliveryDetailId implements IPreDeliveryDetailPK {

	private Long orderid;
	private Long itemid;
	private Long locationid;

	public PreDeliveryDetailId(){
	}
	public PreDeliveryDetailId(Long orderid, Long itemid, Long locationid){
		this.orderid = orderid;
		this.itemid = itemid;
		this.locationid = locationid;
	}

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = orderid.longValue() - ((IPreDeliveryDetailPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IPreDeliveryDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = locationid.longValue() - ((IPreDeliveryDetailPK) arg0).getLocationid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}
	public boolean equals(Object o){
		if (o != null && o instanceof PreDeliveryDetailId){
			PreDeliveryDetailId that = (PreDeliveryDetailId) o;
			return this.orderid.equals(that.orderid) && this.itemid.equals(that.itemid) && this.locationid.equals(that.locationid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return orderid.hashCode() + itemid.hashCode() + locationid.hashCode();
	}

	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
}
