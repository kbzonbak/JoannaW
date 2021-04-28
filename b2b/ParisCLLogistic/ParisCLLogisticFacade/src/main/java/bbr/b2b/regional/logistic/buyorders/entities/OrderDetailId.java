package bbr.b2b.regional.logistic.buyorders.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IOrderDetailPK;

public class OrderDetailId implements IOrderDetailPK {

	private Long orderid;
	private Long itemid;

	public OrderDetailId(){
	}
	public OrderDetailId(Long orderid, Long itemid){
		this.orderid = orderid;
		this.itemid = itemid;
	}

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = orderid.longValue() - ((IOrderDetailPK) arg0).getOrderid().longValue();
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
		if (o != null && o instanceof OrderDetailId){
			OrderDetailId that = (OrderDetailId) o;
			return this.orderid.equals(that.orderid) && this.itemid.equals(that.itemid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return orderid.hashCode() + itemid.hashCode();
	}

	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
}
