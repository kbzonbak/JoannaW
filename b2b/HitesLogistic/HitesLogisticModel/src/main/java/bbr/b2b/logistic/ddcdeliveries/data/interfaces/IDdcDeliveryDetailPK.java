package bbr.b2b.logistic.ddcdeliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IDdcDeliveryDetailPK extends IPrimaryKey {

	public Long getDdcdeliveryid();
	public void setDdcdeliveryid(Long ddcdeliveryid);
	public Long getItemid();
	public void setItemid(Long itemid);
	public Integer getPosition();
	public void setPosition(Integer position);
}
