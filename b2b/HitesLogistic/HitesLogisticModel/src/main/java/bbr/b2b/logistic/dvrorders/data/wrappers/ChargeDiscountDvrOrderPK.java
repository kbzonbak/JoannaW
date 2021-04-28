package bbr.b2b.logistic.dvrorders.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.dvrorders.data.interfaces.IChargeDiscountDvrOrderPK;

public class ChargeDiscountDvrOrderPK implements IChargeDiscountDvrOrderPK {

	private Long dvrorderid;
	private Long chargediscountid;

	public ChargeDiscountDvrOrderPK(){
	}
	public ChargeDiscountDvrOrderPK(Long dvrorderid, Long chargediscountid){
		this.dvrorderid = dvrorderid;
		this.chargediscountid = chargediscountid;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof ChargeDiscountDvrOrderPK){
			ChargeDiscountDvrOrderPK that = (ChargeDiscountDvrOrderPK) o;
			return this.dvrorderid.equals(that.dvrorderid) && this.chargediscountid.equals(that.chargediscountid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return dvrorderid.hashCode() + chargediscountid.hashCode();
	}
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

	public Long getDvrorderid(){ 
		return this.dvrorderid;
	}
	public Long getChargediscountid(){ 
		return this.chargediscountid;
	}
	public void setDvrorderid(Long dvrorderid){ 
		this.dvrorderid = dvrorderid;
	}
	public void setChargediscountid(Long chargediscountid){ 
		this.chargediscountid = chargediscountid;
	}
}
