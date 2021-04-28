package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IBulkDetailPK extends IPrimaryKey {

	public Long getBulkid();
	public Long getOrderid();
	public Long getDeliveryid();
	public Long getItemid();
	public Long getLocationid();
	public void setBulkid(Long bulkid);
	public void setOrderid(Long orderid);
	public void setDeliveryid(Long deliveryid);
	public void setItemid(Long itemid);
	public void setLocationid(Long locationid);
}
