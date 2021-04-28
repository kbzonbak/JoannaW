package bbr.b2b.logistic.items.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IItem extends IElement {

	public String getSku();
	public String getName();
	public Double getUmc_cd_vendor();
	public Double getUmc_location_vendor();
	public Double getUmd_cd_location();
	public Double getUmd_vendor_cd();
	public Double getUmd_vendor_location();
	public String getCurve();
	public String getItemcode();
	public void setSku(String sku);
	public void setName(String name);
	public void setUmc_cd_vendor(Double umc_cd_vendor);
	public void setUmc_location_vendor(Double umc_location_vendor);
	public void setUmd_cd_location(Double umd_cd_location);
	public void setUmd_vendor_cd(Double umd_vendor_cd);
	public void setUmd_vendor_location(Double umd_vendor_location);
	public void setCurve(String curve);
	public void setItemcode(String itemcode);
}
