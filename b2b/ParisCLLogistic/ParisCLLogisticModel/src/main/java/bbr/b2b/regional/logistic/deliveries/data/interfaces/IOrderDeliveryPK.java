package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IOrderDeliveryPK extends IPrimaryKey {

	public Long getOrderid();
	public Long getDeliveryid();
	public void setOrderid(Long orderid);
	public void setDeliveryid(Long deliveryid);
}
