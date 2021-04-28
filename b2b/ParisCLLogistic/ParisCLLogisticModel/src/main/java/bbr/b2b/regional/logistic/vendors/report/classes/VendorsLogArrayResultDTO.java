package bbr.b2b.regional.logistic.vendors.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class VendorsLogArrayResultDTO extends BaseResultDTO {

	private VendorLogDTO[] vendors;
	private Integer total;
	private PageInfoDTO pageInfo;

	public VendorLogDTO[] getVendors() {
		return vendors;
	}

	public void setVendors(VendorLogDTO[] vendors) {
		this.vendors = vendors;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
}
