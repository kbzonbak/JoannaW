package bbr.b2b.regional.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IOrderDetailPK extends IPrimaryKey {

	public Long getOrderid();
	public Long getItemid();
	public void setOrderid(Long orderid);
	public void setItemid(Long itemid);
}
