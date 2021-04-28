package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOrderDeliveryPK;

public class OrderDeliveryPK implements IOrderDeliveryPK {

	private Long orderid;
	private Long deliveryid;

	public OrderDeliveryPK(){
	}
	public OrderDeliveryPK(Long orderid, Long deliveryid){
		this.orderid = orderid;
		this.deliveryid = deliveryid;
	}

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = orderid.longValue() - ((IOrderDeliveryPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = deliveryid.longValue() - ((IOrderDeliveryPK) arg0).getDeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}
	public boolean equals(Object o){
		if (o != null && o instanceof OrderDeliveryPK){
			OrderDeliveryPK that = (OrderDeliveryPK) o;
			return this.orderid.equals(that.orderid) && this.deliveryid.equals(that.deliveryid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return orderid.hashCode() + deliveryid.hashCode();
	}

	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getDeliveryid(){ 
		return this.deliveryid;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setDeliveryid(Long deliveryid){ 
		this.deliveryid = deliveryid;
	}
}
