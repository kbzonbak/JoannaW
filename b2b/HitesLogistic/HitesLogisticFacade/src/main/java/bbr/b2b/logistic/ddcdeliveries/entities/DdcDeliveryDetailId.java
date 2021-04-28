package bbr.b2b.logistic.ddcdeliveries.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.ddcdeliveries.data.interfaces.IDdcDeliveryDetailPK;

public class DdcDeliveryDetailId implements IDdcDeliveryDetailPK {

	private Long ddcdeliveryid;
	private Long itemid;
	private Integer position;

	public DdcDeliveryDetailId(){
	}
	
	public DdcDeliveryDetailId(Long ddcdeliveryid, Long itemid, Integer position){
		this.ddcdeliveryid = ddcdeliveryid;
		this.itemid = itemid;
		this.position = position;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof DdcDeliveryDetailId){
			DdcDeliveryDetailId that = (DdcDeliveryDetailId) o;
			return this.ddcdeliveryid.equals(that.ddcdeliveryid) && this.itemid.equals(that.itemid) &&
					this.position.equals(that.position);
		}else{
			return false;
		}
	}
	
	public int hashCode() {
		return ddcdeliveryid.hashCode() + itemid.hashCode() + position.hashCode();
	}
	
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = ddcdeliveryid.longValue() - ((IDdcDeliveryDetailPK) arg0).getDdcdeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IDdcDeliveryDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.longValue() - ((IDdcDeliveryDetailPK) arg0).getPosition().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getDdcdeliveryid() {
		return ddcdeliveryid;
	}

	public void setDdcdeliveryid(Long ddcdeliveryid) {
		this.ddcdeliveryid = ddcdeliveryid;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

}
