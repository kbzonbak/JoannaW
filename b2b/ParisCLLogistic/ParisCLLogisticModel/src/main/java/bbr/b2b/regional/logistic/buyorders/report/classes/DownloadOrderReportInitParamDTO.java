package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class DownloadOrderReportInitParamDTO implements Serializable {

	private Long[] orderids;
	private String vendorcode;//rut del prov	
	private String originalvendorcode;
		
	public Long[] getOrderids() {
		return orderids;
	}

	public void setOrderids(Long[] orderids) {
		this.orderids = orderids;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public String getOriginalvendorcode() {
		return originalvendorcode;
	}

	public void setOriginalvendorcode(String originalvendorcode) {
		this.originalvendorcode = originalvendorcode;
	}





}
