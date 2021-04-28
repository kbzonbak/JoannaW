package bbr.b2b.logistic.report.classes;

import java.io.Serializable;

public class ItemDTO implements Serializable {

	private Long id;
	private String sku;
	private String name;
	private Double umc_cd_vendor;
	private Double umc_location_vendor;
	private Double umd_cd_location;
	private Double umd_vendor_cd;
	private Double umd_vendor_location;
	private String partialsupply;	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public Double getUmc_cd_vendor() {
		return umc_cd_vendor;
	}
	public void setUmc_cd_vendor(Double umc_cd_vendor) {
		this.umc_cd_vendor = umc_cd_vendor;
	}
	public Double getUmc_location_vendor() {
		return umc_location_vendor;
	}
	public void setUmc_location_vendor(Double umc_location_vendor) {
		this.umc_location_vendor = umc_location_vendor;
	}
	public Double getUmd_cd_location() {
		return umd_cd_location;
	}
	public void setUmd_cd_location(Double umd_cd_location) {
		this.umd_cd_location = umd_cd_location;
	}
	public Double getUmd_vendor_cd() {
		return umd_vendor_cd;
	}
	public void setUmd_vendor_cd(Double umd_vendor_cd) {
		this.umd_vendor_cd = umd_vendor_cd;
	}
	public Double getUmd_vendor_location() {
		return umd_vendor_location;
	}
	public void setUmd_vendor_location(Double umd_vendor_location) {
		this.umd_vendor_location = umd_vendor_location;
	}
	public String getPartialsupply() {
		return partialsupply;
	}
	public void setPartialsupply(String partialsupply) {
		this.partialsupply = partialsupply;
	}	
}