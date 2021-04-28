package bbr.b2b.logistic.dvrorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IDvrOrderDetaiPK extends IPrimaryKey {

	public Long getDvrorderid();
	public Long getItemid();
	public Integer getPosition();
	public void setDvrorderid(Long dvrorderid);
	public void setItemid(Long itemid);
	public void setPosition(Integer position);
}
