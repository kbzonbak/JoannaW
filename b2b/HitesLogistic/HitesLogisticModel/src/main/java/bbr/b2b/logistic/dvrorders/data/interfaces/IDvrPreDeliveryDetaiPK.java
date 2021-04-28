package bbr.b2b.logistic.dvrorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IDvrPreDeliveryDetaiPK extends IPrimaryKey {

	public Long getDvrorderid();
	public Long getItemid();
	public Long getLocationid();
	public Integer getPosition();
	public void setDvrorderid(Long dvrorderid);
	public void setItemid(Long itemid);
	public void setLocationid(Long locationid);
	public void setPosition(Integer position);
}
