package bbr.b2b.regional.logistic.notifications.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IContactVendorPK extends IPrimaryKey {

	public Long getContactid();
	public Long getVendorid();
	public void setContactid(Long contactid);
	public void setVendorid(Long vendorid);
}
