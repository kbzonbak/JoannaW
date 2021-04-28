package bbr.b2b.regional.logistic.items.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IVendorItem extends IIdentifiable {

	public String getVendoritemcode();
	public Boolean getDirectdelivery();
	public void setVendoritemcode(String vendoritemcode);
	public void setDirectdelivery(Boolean directdelivery);
}
