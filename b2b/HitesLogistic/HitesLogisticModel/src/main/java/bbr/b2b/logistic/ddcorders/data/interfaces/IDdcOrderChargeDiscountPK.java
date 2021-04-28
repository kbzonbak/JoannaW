package bbr.b2b.logistic.ddcorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IDdcOrderChargeDiscountPK extends IPrimaryKey {

	public Long getDdcchargediscountid();
	public void setDdcchargediscountid(Long ddcchargediscountid);
	public Long getDdcorderid();
	public void setDdcorderid(Long ddcorderid);
}