package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IOutReceptionDetailPK extends IPrimaryKey {

	public Long getOutreceptionid();
	public Long getOrderid();
	public Long getItemid();
	public Long getLocationid();
	public void setOutreceptionid(Long outreceptionid);
	public void setOrderid(Long orderid);
	public void setItemid(Long itemid);
	public void setLocationid(Long locationid);
}
