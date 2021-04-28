package bbr.b2b.logistic.customer.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IPredistributionPK extends IPrimaryKey {

	public Long getOrderid();
	public String getSkubuyer();
	public Long getLocalid();
	public Integer getPosition();
	public void setOrderid(Long orderid);
	public void setSkubuyer(String skubuyer);
	public void setLocalid(Long localid);
	public void setPosition(Integer position);
}
