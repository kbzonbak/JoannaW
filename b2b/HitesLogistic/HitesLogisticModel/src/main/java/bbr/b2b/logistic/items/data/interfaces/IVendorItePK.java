package bbr.b2b.logistic.items.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IVendorItePK extends IPrimaryKey {

	public Long getItemid();
	public Long getVendorid();
	public void setItemid(Long itemid);
	public void setVendorid(Long vendorid);
}
