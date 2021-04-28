package bbr.b2b.logistic.dvrorders.entities;

import bbr.b2b.logistic.dvrorders.entities.ChargeDiscount;
import bbr.b2b.logistic.dvrorders.entities.DvrOrder;
import bbr.b2b.logistic.dvrorders.data.interfaces.IChargeDiscountDvrOrder;

public class ChargeDiscountDvrOrder implements IChargeDiscountDvrOrder {

	private ChargeDiscountDvrOrderId id;
	private ChargeDiscount chargediscount;
	private DvrOrder dvrorder;

	public ChargeDiscountDvrOrderId getId(){ 
		return this.id;
	}
	public ChargeDiscount getChargediscount(){ 
		return this.chargediscount;
	}
	public DvrOrder getDvrorder(){ 
		return this.dvrorder;
	}
	public void setId(ChargeDiscountDvrOrderId id){ 
		this.id = id;
	}
	public void setChargediscount(ChargeDiscount chargediscount){ 
		this.chargediscount = chargediscount;
	}
	public void setDvrorder(DvrOrder dvrorder){ 
		this.dvrorder = dvrorder;
	}
}
