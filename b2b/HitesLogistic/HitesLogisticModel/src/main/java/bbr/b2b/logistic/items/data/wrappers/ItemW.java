package bbr.b2b.logistic.items.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.items.data.interfaces.IItem;

public class ItemW extends ElementDTO implements IItem {

	private String sku;
	private String name;
	private Double umc_cd_vendor;
	private Double umc_location_vendor;
	private Double umd_cd_location;
	private Double umd_vendor_cd;
	private Double umd_vendor_location;
	private String curve;
	private String itemcode;

	public String getSku(){ 
		return this.sku;
	}
	public String getName(){ 
		return this.name;
	}
	public Double getUmc_cd_vendor(){ 
		return this.umc_cd_vendor;
	}
	public Double getUmc_location_vendor(){ 
		return this.umc_location_vendor;
	}
	public Double getUmd_cd_location(){ 
		return this.umd_cd_location;
	}
	public Double getUmd_vendor_cd(){ 
		return this.umd_vendor_cd;
	}
	public Double getUmd_vendor_location(){ 
		return this.umd_vendor_location;
	}
	public String getCurve(){ 
		return this.curve;
	}
	public String getItemcode(){ 
		return this.itemcode;
	}
	public void setSku(String sku){ 
		this.sku = sku;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setUmc_cd_vendor(Double umc_cd_vendor){ 
		this.umc_cd_vendor = umc_cd_vendor;
	}
	public void setUmc_location_vendor(Double umc_location_vendor){ 
		this.umc_location_vendor = umc_location_vendor;
	}
	public void setUmd_cd_location(Double umd_cd_location){ 
		this.umd_cd_location = umd_cd_location;
	}
	public void setUmd_vendor_cd(Double umd_vendor_cd){ 
		this.umd_vendor_cd = umd_vendor_cd;
	}
	public void setUmd_vendor_location(Double umd_vendor_location){ 
		this.umd_vendor_location = umd_vendor_location;
	}
	public void setCurve(String curve){ 
		this.curve = curve;
	}
	public void setItemcode(String itemcode){ 
		this.itemcode = itemcode;
	}
}
