package bbr.b2b.logistic.dvrorders.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.dvrorders.data.interfaces.IChargeDiscountDvrOrderDetail;
import bbr.b2b.logistic.dvrorders.data.interfaces.IChargeDiscountDvrOrderDetaiPK;

public class ChargeDiscountDvrOrderDetailW implements IChargeDiscountDvrOrderDetail, IChargeDiscountDvrOrderDetaiPK {

	private Long chargediscountid;
	private Long dvrorderid;
	private Long itemid;
	private Integer position;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = dvrorderid.longValue() - ((IChargeDiscountDvrOrderDetaiPK) arg0).getDvrorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IChargeDiscountDvrOrderDetaiPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.longValue() - ((IChargeDiscountDvrOrderDetaiPK) arg0).getPosition().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = chargediscountid.longValue() - ((IChargeDiscountDvrOrderDetaiPK) arg0).getChargediscountid().longValue();
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
	public Long getItemid(){ 
		return this.itemid;
	}
	public Integer getPosition(){ 
		return this.position;
	}
	public void setChargediscountid(Long chargediscountid){ 
		this.chargediscountid = chargediscountid;
	}
	public void setDvrorderid(Long dvrorderid){ 
		this.dvrorderid = dvrorderid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
	public void setPosition(Integer position){ 
		this.position = position;
	}
}
