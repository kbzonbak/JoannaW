package bbr.b2b.regional.logistic.items.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class VendorItemReportResultDTO extends BaseResultDTO{

	private VendorItemReportDataDTO[] vendoritemdatas;
	private PageInfoDTO pageInfo;
	private int totalreg;
	
	public VendorItemReportDataDTO[] getVendoritemdatas() {
		return vendoritemdatas;
	}
	public void setVendoritemdatas(VendorItemReportDataDTO[] vendoritemdatas) {
		this.vendoritemdatas = vendoritemdatas;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
	public int getTotalreg() {
		return totalreg;
	}
	public void setTotalreg(int totalreg) {
		this.totalreg = totalreg;
	}
	
	
}
