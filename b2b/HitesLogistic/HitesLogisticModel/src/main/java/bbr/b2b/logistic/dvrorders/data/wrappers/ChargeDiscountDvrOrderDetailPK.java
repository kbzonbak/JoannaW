package bbr.b2b.logistic.dvrorders.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.dvrorders.data.interfaces.IChargeDiscountDvrOrderDetailPK;

public class ChargeDiscountDvrOrderDetailPK implements IChargeDiscountDvrOrderDetailPK {

	private Long dvrorderid;
	private Long itemid;
	private Integer position;
	private Long chargediscountid;

	public ChargeDiscountDvrOrderDetailPK(){
	}
	public ChargeDiscountDvrOrderDetailPK(Long dvrorderid, Long itemid, Integer position, Long chargediscountid){
		this.dvrorderid = dvrorderid;
		this.itemid = itemid;
		this.position = position;
		this.chargediscountid = chargediscountid;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof ChargeDiscountDvrOrderDetailPK){
			ChargeDiscountDvrOrderDetailPK that = (ChargeDiscountDvrOrderDetailPK) o;
			return this.dvrorderid.equals(that.dvrorderid) && this.itemid.equals(that.itemid) && this.position.equals(that.position) && this.chargediscountid.equals(that.chargediscountid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return dvrorderid.hashCode() + itemid.hashCode() + position.hashCode() + chargediscountid.hashCode();
	}
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = dvrorderid.longValue() - ((IChargeDiscountDvrOrderDetailPK) arg0).getDvrorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IChargeDiscountDvrOrderDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.longValue() - ((IChargeDiscountDvrOrderDetailPK) arg0).getPosition().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = chargediscountid.longValue() - ((IChargeDiscountDvrOrderDetailPK) arg0).getChargediscountid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
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
	public Long getChargediscountid(){ 
		return this.chargediscountid;
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
	public void setChargediscountid(Long chargediscountid){ 
		this.chargediscountid = chargediscountid;
	}
}
