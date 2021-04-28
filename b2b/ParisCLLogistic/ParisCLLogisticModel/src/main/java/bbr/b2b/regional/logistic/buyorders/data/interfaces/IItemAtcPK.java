package bbr.b2b.regional.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IItemAtcPK extends IPrimaryKey {

	public Long getAtcid();
	public void setAtcid(Long atcid);
	public Long getItemid();
	public void setItemid(Long itemid);
}
