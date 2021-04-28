package bbr.b2b.regional.logistic.items.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IVendorItemPK extends IPrimaryKey {

	public Long getVendorid();
	public Long getItemid();
	public void setVendorid(Long vendorid);
	public void setItemid(Long itemid);
}
