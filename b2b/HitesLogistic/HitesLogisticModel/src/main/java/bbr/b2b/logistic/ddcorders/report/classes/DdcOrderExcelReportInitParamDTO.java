package bbr.b2b.logistic.ddcorders.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.PageInitParamDTO;

public class DdcOrderExcelReportInitParamDTO implements Serializable {

	private Long[] ddcorderids;
	private String vendorcode;
	
	public Long[] getDdcorderids() {
		return ddcorderids;
	}
	public void setDdcorderids(Long[] ddcorderids) {
		this.ddcorderids = ddcorderids;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	
}
