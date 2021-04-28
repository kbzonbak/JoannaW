package bbr.b2b.regional.logistic.fillrate.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IFillRateDetailPK extends IPrimaryKey {

	public Long getFillrateid();
	public Long getItemid();
	public void setFillrateid(Long fillrateid);
	public void setItemid(Long itemid);
}
