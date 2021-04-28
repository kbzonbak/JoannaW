package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IDODeliveryDetailPK extends IPrimaryKey {

	public Long getDodeliveryid();
	public Long getItemid();
	public void setDodeliveryid(Long dodeliveryid);
	public void setItemid(Long itemid);
}
