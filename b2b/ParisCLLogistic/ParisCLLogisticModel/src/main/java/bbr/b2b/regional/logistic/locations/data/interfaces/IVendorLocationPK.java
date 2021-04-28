package bbr.b2b.regional.logistic.locations.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IVendorLocationPK extends IPrimaryKey {

	public Long getVendorid();
	public Long getLocationid();
	public void setVendorid(Long vendorid);
	public void setLocationid(Long locationid);
}
