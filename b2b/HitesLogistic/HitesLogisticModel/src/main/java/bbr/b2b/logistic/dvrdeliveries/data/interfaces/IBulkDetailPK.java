package bbr.b2b.logistic.dvrdeliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IBulkDetailPK extends IPrimaryKey {

	public Long getBulkid();
	public Long getDvrdeliveryid();
	public Long getDvrorderid();
	public Long getItemid();
	public Long getLocationid();
	public Integer getPosition();
	public void setBulkid(Long bulkid);
	public void setDvrdeliveryid(Long dvrdeliveryid);
	public void setDvrorderid(Long dvrorderid);
	public void setItemid(Long itemid);
	public void setLocationid(Long locationid);
	public void setPosition(Integer position);
}
