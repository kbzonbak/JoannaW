package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOutReceptionDetailPK;

public class OutReceptionDetailPK implements IOutReceptionDetailPK {

	private Long outreceptionid;
	private Long orderid;
	private Long itemid;
	private Long locationid;

	public OutReceptionDetailPK(){
	}
	public OutReceptionDetailPK(Long outreceptionid, Long orderid, Long itemid, Long locationid){
		this.outreceptionid = outreceptionid;
		this.orderid = orderid;
		this.itemid = itemid;
		this.locationid = locationid;
	}

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = outreceptionid.longValue() - ((IOutReceptionDetailPK) arg0).getOutreceptionid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = orderid.longValue() - ((IOutReceptionDetailPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IOutReceptionDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = locationid.longValue() - ((IOutReceptionDetailPK) arg0).getLocationid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}
	public boolean equals(Object o){
		if (o != null && o instanceof OutReceptionDetailPK){
			OutReceptionDetailPK that = (OutReceptionDetailPK) o;
			return this.outreceptionid.equals(that.outreceptionid) && this.orderid.equals(that.orderid) && this.itemid.equals(that.itemid) && this.locationid.equals(that.locationid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return outreceptionid.hashCode() + orderid.hashCode() + itemid.hashCode() + locationid.hashCode();
	}

	public Long getOutreceptionid(){ 
		return this.outreceptionid;
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
	public void setOutreceptionid(Long outreceptionid){ 
		this.outreceptionid = outreceptionid;
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
