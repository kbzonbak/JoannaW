package bbr.b2b.extractors.ripley.dto;

public class VendorDTO {
	private String vendor_fis;
	private String vendor_name;
	private String vendor_county;
	private String vendor_warehouse;
	public String getVendor_fis() {
		return vendor_fis;
	}
	public void setVendor_fis(String vendor_fis) {
		this.vendor_fis = vendor_fis;
	}
	public String getVendor_name() {
		return vendor_name;
	}
	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}
	
	public String getVendor_warehouse() {
		return vendor_warehouse;
	}
	public void setVendor_warehouse(String vendor_warehouse) {
		this.vendor_warehouse = vendor_warehouse;
	}
	public String getVendor_county() {
		return vendor_county;
	}
	public void setVendor_county(String vendor_county) {
		this.vendor_county = vendor_county;
	}
}
