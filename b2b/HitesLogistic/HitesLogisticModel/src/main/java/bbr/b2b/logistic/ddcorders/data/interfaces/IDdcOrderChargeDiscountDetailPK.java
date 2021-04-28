package bbr.b2b.logistic.ddcorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IDdcOrderChargeDiscountDetailPK extends IPrimaryKey {

	public Long getDdcchargediscountid();
	public void setDdcchargediscountid(Long ddcchargediscountid);
	public Long getDdcorderid();
	public void setDdcorderid(Long ddcorderid);
	public Long getItemid();
	public void setItemid(Long itemid);
	public Integer getPosition();
	public void setPosition(Integer position);
}
