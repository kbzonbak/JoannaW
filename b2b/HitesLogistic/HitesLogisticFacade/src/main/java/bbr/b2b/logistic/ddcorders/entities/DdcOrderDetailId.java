package bbr.b2b.logistic.ddcorders.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.ddcorders.data.interfaces.IDdcOrderDetaiPK;

public class DdcOrderDetailId implements IDdcOrderDetaiPK {

	private Long ddcorderid;
	private Long itemid;
	private Integer position;

	public DdcOrderDetailId(){
	}
	
	public DdcOrderDetailId(Long ddcorderid, Long itemid, Integer position){
		this.ddcorderid = ddcorderid;
		this.itemid = itemid;
		this.position = position;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof DdcOrderDetailId){
			DdcOrderDetailId that = (DdcOrderDetailId) o;
			return this.ddcorderid.equals(that.ddcorderid) && this.itemid.equals(that.itemid) &&
					this.position.equals(that.position);
		}else{
			return false;
		}
	}
	
	public int hashCode() {
		return ddcorderid.hashCode() + itemid.hashCode() + position.hashCode();
	}
	
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = ddcorderid.longValue() - ((IDdcOrderDetaiPK) arg0).getDdcorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IDdcOrderDetaiPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.longValue() - ((IDdcOrderDetaiPK) arg0).getPosition().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getDdcorderid() {
		return ddcorderid;
	}

	public void setDdcorderid(Long ddcorderid) {
		this.ddcorderid = ddcorderid;
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
