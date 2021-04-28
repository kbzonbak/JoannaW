package bbr.b2b.logistic.dvrorders.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.dvrorders.data.interfaces.IChargeDiscountDvrOrder;
import bbr.b2b.logistic.dvrorders.data.interfaces.IChargeDiscountDvrOrderPK;

public class ChargeDiscountDvrOrderW implements IChargeDiscountDvrOrder, IChargeDiscountDvrOrderPK {

	private Long chargediscountid;
	private Long dvrorderid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = dvrorderid.longValue() - ((IChargeDiscountDvrOrderPK) arg0).getDvrorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = chargediscountid.longValue() - ((IChargeDiscountDvrOrderPK) arg0).getChargediscountid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getChargediscountid(){ 
		return this.chargediscountid;
	}
	public Long getDvrorderid(){ 
		return this.dvrorderid;
	}
	public void setChargediscountid(Long chargediscountid){ 
		this.chargediscountid = chargediscountid;
	}
	public void setDvrorderid(Long dvrorderid){ 
		this.dvrorderid = dvrorderid;
	}
}
