package bbr.b2b.regional.logistic.vendors.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class VendorRankingReportResultDTO extends BaseResultDTO{

	private String since;
	private String until;
	private VendorRankingReportDataDTO[] vendorrankings = null;
	private PageInfoDTO pageInfo;
	
	public String getSince() {
		return since;
	}
	public void setSince(String since) {
		this.since = since;
	}
	public String getUntil() {
		return until;
	}
	public void setUntil(String until) {
		this.until = until;
	}
	public VendorRankingReportDataDTO[] getVendorrankings() {
		return vendorrankings;
	}
	public void setVendorrankings(VendorRankingReportDataDTO[] vendorrankings) {
		this.vendorrankings = vendorrankings;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
	
}
