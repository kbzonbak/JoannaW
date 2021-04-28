package bbr.b2b.logistic.ddcorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IDdcOrderDetaiPK extends IPrimaryKey {

	public Long getDdcorderid();
	public void setDdcorderid(Long ddcorderid);
	public Long getItemid();
	public void setItemid(Long itemid);
	public Integer getPosition();
	public void setPosition(Integer position);
}
