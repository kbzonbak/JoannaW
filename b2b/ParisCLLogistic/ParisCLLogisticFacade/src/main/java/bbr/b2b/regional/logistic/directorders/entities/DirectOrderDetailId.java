package bbr.b2b.regional.logistic.directorders.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.directorders.data.interfaces.IDirectOrderDetailPK;

public class DirectOrderDetailId implements IDirectOrderDetailPK {

	private Long orderid;
	private Long itemid;

	public DirectOrderDetailId(){
	}
	public DirectOrderDetailId(Long orderid, Long itemid){
		this.orderid = orderid;
		this.itemid = itemid;
	}

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = orderid.longValue() - ((IDirectOrderDetailPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IDirectOrderDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}
	public boolean equals(Object o){
		if (o != null && o instanceof DirectOrderDetailId){
			DirectOrderDetailId that = (DirectOrderDetailId) o;
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
