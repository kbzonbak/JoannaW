package bbr.b2b.logistic.ddcorders.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.ddcorders.data.interfaces.IDdcOrderChargeDiscountPK;

public class DdcOrderChargeDiscountId implements IDdcOrderChargeDiscountPK {

	private Long ddcorderid;
	private Long ddcchargediscountid;

	public DdcOrderChargeDiscountId(){
	}
	
	public DdcOrderChargeDiscountId(Long ddcorderid, Long ddcchargediscountid){
		this.ddcorderid = ddcorderid;
		this.ddcchargediscountid = ddcchargediscountid;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof DdcOrderChargeDiscountId){
			DdcOrderChargeDiscountId that = (DdcOrderChargeDiscountId) o;
			return this.ddcorderid.equals(that.ddcorderid) && this.ddcchargediscountid.equals(that.ddcchargediscountid);
		}
		else {
			return false;
		}
	}
	
	public int hashCode() {
		return ddcorderid.hashCode() + ddcchargediscountid.hashCode();
	}
	
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = ddcorderid.longValue() - ((IDdcOrderChargeDiscountPK) arg0).getDdcorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = ddcchargediscountid.longValue() - ((IDdcOrderChargeDiscountPK) arg0).getDdcchargediscountid().longValue();
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

	public Long getDdcchargediscountid() {
		return ddcchargediscountid;
	}

	public void setDdcchargediscountid(Long ddcchargediscountid) {
		this.ddcchargediscountid = ddcchargediscountid;
	}

}
