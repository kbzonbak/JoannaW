package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class UploadPackingListInitParamDTO implements Serializable{
	
	private Long deliveryid;
	private String vendorcode;
	private Integer complementnumber;
	private String filename;
	private PackingListDataDTO[] packinglist;
	
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}	
	public Integer getComplementnumber() {
		return complementnumber;
	}
	public void setComplementnumber(Integer complementnumber) {
		this.complementnumber = complementnumber;
	}	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}	
	public PackingListDataDTO[] getPackinglist() {
		return packinglist;
	}
	public void setPackinglist(PackingListDataDTO[] packinglist) {
		this.packinglist = packinglist;
	}	
}
