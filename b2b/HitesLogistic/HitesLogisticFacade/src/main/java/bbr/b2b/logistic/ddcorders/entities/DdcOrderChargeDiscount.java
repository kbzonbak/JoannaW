package bbr.b2b.logistic.ddcorders.entities;

import bbr.b2b.logistic.ddcorders.data.interfaces.IDdcOrderChargeDiscount;

public class DdcOrderChargeDiscount implements IDdcOrderChargeDiscount {

	private DdcOrderChargeDiscountId id;
	private DdcChargeDiscount ddcchargediscount;
	private DdcOrder ddcorder;
	
	public DdcOrderChargeDiscountId getId() {
		return id;
	}
	public void setId(DdcOrderChargeDiscountId id) {
		this.id = id;
	}
	public DdcChargeDiscount getDdcchargediscount() {
		return ddcchargediscount;
	}
	public void setDdcchargediscount(DdcChargeDiscount ddcchargediscount) {
		this.ddcchargediscount = ddcchargediscount;
	}
	public DdcOrder getDdcorder() {
		return ddcorder;
	}
	public void setDdcorder(DdcOrder ddcorder) {
		this.ddcorder = ddcorder;
	}
	
}
