package bbr.b2b.regional.logistic.deliveries.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IBulkDetailPK;

public class BulkDetailId implements IBulkDetailPK {

	private Long bulkid;
	private Long orderid;
	private Long deliveryid;
	private Long itemid;
	private Long locationid;

	public BulkDetailId(){
	}
	public BulkDetailId(Long bulkid, Long orderid, Long deliveryid, Long itemid, Long locationid){
		this.bulkid = bulkid;
		this.orderid = orderid;
		this.deliveryid = deliveryid;
		this.itemid = itemid;
		this.locationid = locationid;
	}

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = bulkid.longValue() - ((IBulkDetailPK) arg0).getBulkid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = orderid.longValue() - ((IBulkDetailPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = deliveryid.longValue() - ((IBulkDetailPK) arg0).getDeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IBulkDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = locationid.longValue() - ((IBulkDetailPK) arg0).getLocationid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}
	public boolean equals(Object o){
		if (o != null && o instanceof BulkDetailId){
			BulkDetailId that = (BulkDetailId) o;
			return this.bulkid.equals(that.bulkid) && this.orderid.equals(that.orderid) && this.deliveryid.equals(that.deliveryid) && this.itemid.equals(that.itemid) && this.locationid.equals(that.locationid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return bulkid.hashCode() + orderid.hashCode() + deliveryid.hashCode() + itemid.hashCode() + locationid.hashCode();
	}

	public Long getBulkid(){ 
		return this.bulkid;
	}
	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getDeliveryid(){ 
		return this.deliveryid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public void setBulkid(Long bulkid){ 
		this.bulkid = bulkid;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setDeliveryid(Long deliveryid){ 
		this.deliveryid = deliveryid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
}
