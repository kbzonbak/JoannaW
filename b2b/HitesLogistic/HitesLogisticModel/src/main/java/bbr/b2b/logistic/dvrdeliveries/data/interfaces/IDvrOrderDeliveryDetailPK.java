package bbr.b2b.logistic.dvrdeliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IDvrOrderDeliveryDetailPK extends IPrimaryKey {

	public Long getDvrorderid();
	public Long getDvrdeliveryid();
	public Long getItemid();
	public Long getLocationid();
	public Integer getPosition();
	public void setDvrorderid(Long dvrorderid);
	public void setDvrdeliveryid(Long dvrdeliveryid);
	public void setItemid(Long itemid);
	public void setLocationid(Long locationid);
	public void setPosition(Integer position);
}
