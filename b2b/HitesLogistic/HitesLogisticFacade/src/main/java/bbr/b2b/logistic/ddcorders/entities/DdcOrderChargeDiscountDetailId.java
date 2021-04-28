package bbr.b2b.logistic.ddcorders.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.ddcorders.data.interfaces.IDdcOrderChargeDiscountDetailPK;

public class DdcOrderChargeDiscountDetailId implements IDdcOrderChargeDiscountDetailPK {

	private Long ddcorderid;
	private Long itemid;
	private Integer position;
	private Long ddcchargediscountid;

	public DdcOrderChargeDiscountDetailId(){
	}
	
	public DdcOrderChargeDiscountDetailId(Long ddcorderid, Long itemid, Integer position, Long ddcchargediscountid){
		this.ddcorderid = ddcorderid;
		this.itemid = itemid;
		this.position = position;
		this.ddcchargediscountid = ddcchargediscountid;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof DdcOrderChargeDiscountDetailId){
			DdcOrderChargeDiscountDetailId that = (DdcOrderChargeDiscountDetailId) o;
			return this.ddcorderid.equals(that.ddcorderid) && this.itemid.equals(that.itemid) &&
					this.position.equals(that.position) && this.ddcchargediscountid.equals(that.ddcchargediscountid);
		}else{
			return false;
		}
	}
	
	public int hashCode() {
		return ddcorderid.hashCode() + itemid.hashCode() + position.hashCode() + ddcchargediscountid.hashCode();
	}
	
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = ddcorderid.longValue() - ((IDdcOrderChargeDiscountDetailPK) arg0).getDdcorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IDdcOrderChargeDiscountDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.longValue() - ((IDdcOrderChargeDiscountDetailPK) arg0).getPosition().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = ddcchargediscountid.longValue() - ((IDdcOrderChargeDiscountDetailPK) arg0).getDdcchargediscountid().longValue();
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

	public Long getDdcchargediscountid() {
		return ddcchargediscountid;
	}

	public void setDdcchargediscountid(Long ddcchargediscountid) {
		this.ddcchargediscountid = ddcchargediscountid;
	}

}
