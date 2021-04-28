package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.PageInitParamDTO;

public class DvrOrderPdfReportInitParamDTO implements Serializable {

	private Long[] dvrorderids;
	private String vendorcode;
	
	public Long[] getDvrorderids() {
		return dvrorderids;
	}
	public void setDvrorderids(Long[] dvrorderids) {
		this.dvrorderids = dvrorderids;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	
}
