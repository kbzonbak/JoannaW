package bbr.b2b.logistic.dvrorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IChargeDiscountDvrOrderDetailPK extends IPrimaryKey {

	public Long getDvrorderid();
	public Long getItemid();
	public Integer getPosition();
	public Long getChargediscountid();
	public void setDvrorderid(Long dvrorderid);
	public void setItemid(Long itemid);
	public void setPosition(Integer position);
	public void setChargediscountid(Long chargediscountid);
}
