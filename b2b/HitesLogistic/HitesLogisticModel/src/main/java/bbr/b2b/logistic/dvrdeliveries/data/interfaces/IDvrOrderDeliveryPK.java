package bbr.b2b.logistic.dvrdeliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IDvrOrderDeliveryPK extends IPrimaryKey {

	public Long getDvrorderid();
	public Long getDvrdeliveryid();
	public void setDvrorderid(Long dvrorderid);
	public void setDvrdeliveryid(Long dvrdeliveryid);
}
