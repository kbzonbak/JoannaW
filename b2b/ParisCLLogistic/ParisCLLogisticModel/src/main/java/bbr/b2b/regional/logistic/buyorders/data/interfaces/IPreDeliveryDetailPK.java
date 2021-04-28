package bbr.b2b.regional.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IPreDeliveryDetailPK extends IPrimaryKey {

	public Long getOrderid();
	public Long getItemid();
	public Long getLocationid();
	public void setOrderid(Long orderid);
	public void setItemid(Long itemid);
	public void setLocationid(Long locationid);
}
