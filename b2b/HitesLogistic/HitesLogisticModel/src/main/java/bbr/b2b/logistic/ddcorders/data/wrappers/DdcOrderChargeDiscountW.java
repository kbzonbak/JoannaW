package bbr.b2b.logistic.ddcorders.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.ddcorders.data.interfaces.IDdcOrderChargeDiscount;
import bbr.b2b.logistic.ddcorders.data.interfaces.IDdcOrderChargeDiscountPK;

public class DdcOrderChargeDiscountW implements IDdcOrderChargeDiscount, IDdcOrderChargeDiscountPK {

	private Long ddcchargediscountid;
	private Long ddcorderid;
		
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

	public Long getDdcchargediscountid() {
		return ddcchargediscountid;
	}

	public void setDdcchargediscountid(Long ddcchargediscountid) {
		this.ddcchargediscountid = ddcchargediscountid;
	}

	public Long getDdcorderid() {
		return ddcorderid;
	}

	public void setDdcorderid(Long ddcorderid) {
		this.ddcorderid = ddcorderid;
	}
}
