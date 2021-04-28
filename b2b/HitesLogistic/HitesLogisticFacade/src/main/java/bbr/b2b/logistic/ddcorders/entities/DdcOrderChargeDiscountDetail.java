package bbr.b2b.logistic.ddcorders.entities;

import bbr.b2b.logistic.ddcorders.data.interfaces.IDdcOrderChargeDiscountDetail;

public class DdcOrderChargeDiscountDetail implements IDdcOrderChargeDiscountDetail {

	private DdcOrderChargeDiscountDetailId id;
	private DdcChargeDiscount ddcchargediscount;
	private DdcOrderDetail ddcorderdetail;

	public DdcOrderChargeDiscountDetailId getId() {
		return id;
	}
	public void setId(DdcOrderChargeDiscountDetailId id) {
		this.id = id;
	}
	public DdcChargeDiscount getDdcchargediscount() {
		return ddcchargediscount;
	}
	public void setDdcchargediscount(DdcChargeDiscount ddcchargediscount) {
		this.ddcchargediscount = ddcchargediscount;
	}
	public DdcOrderDetail getDdcorderdetail() {
		return ddcorderdetail;
	}
	public void setDdcorderdetail(DdcOrderDetail ddcorderdetail) {
		this.ddcorderdetail = ddcorderdetail;
	}

}
