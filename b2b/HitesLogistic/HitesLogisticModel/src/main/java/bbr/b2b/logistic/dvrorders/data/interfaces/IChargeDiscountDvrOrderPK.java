package bbr.b2b.logistic.dvrorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IChargeDiscountDvrOrderPK extends IPrimaryKey {

	public Long getDvrorderid();
	public Long getChargediscountid();
	public void setDvrorderid(Long dvrorderid);
	public void setChargediscountid(Long chargediscountid);
}
