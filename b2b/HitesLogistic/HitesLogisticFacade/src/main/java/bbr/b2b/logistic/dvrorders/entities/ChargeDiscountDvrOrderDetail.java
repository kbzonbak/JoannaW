package bbr.b2b.logistic.dvrorders.entities;

import bbr.b2b.logistic.dvrorders.data.interfaces.IChargeDiscountDvrOrderDetail;

public class ChargeDiscountDvrOrderDetail implements IChargeDiscountDvrOrderDetail {

	private ChargeDiscountDvrOrderDetailId id;
	private ChargeDiscount chargediscount;
	private DvrOrderDetail dvrorderdetail;

	public ChargeDiscountDvrOrderDetailId getId(){ 
		return this.id;
	}
	public ChargeDiscount getChargediscount(){ 
		return this.chargediscount;
	}
	public DvrOrderDetail getDvrorderdetail(){ 
		return this.dvrorderdetail;
	}
	public void setId(ChargeDiscountDvrOrderDetailId id){ 
		this.id = id;
	}
	public void setChargediscount(ChargeDiscount chargediscount){ 
		this.chargediscount = chargediscount;
	}
	public void setDvrorderdetail(DvrOrderDetail dvrorderdetail){ 
		this.dvrorderdetail = dvrorderdetail;
	}
}
