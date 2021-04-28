package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class UploadDODeliveryOfDirectOrdersInitParamDTO implements Serializable{
	
	private String vendorcode;
	private String filename;
	private DirectOrderReprogDateDTO[] data;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public DirectOrderReprogDateDTO[] getData() {
		return data;
	}
	public void setData(DirectOrderReprogDateDTO[] data) {
		this.data = data;
	}	
}
