package bbr.b2b.regional.logistic.vendors.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class VendorDepartmentRankingReportResultDTO extends BaseResultDTO{

	private String since;
	private String until;
	private VendorDepartmentRankingReportDataDTO[] vendordepartmentrankings = null;
	private int totalreg;
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
	public VendorDepartmentRankingReportDataDTO[] getVendordepartmentrankings() {
		return vendordepartmentrankings;
	}
	public void setVendordepartmentrankings(VendorDepartmentRankingReportDataDTO[] vendordepartmentrankings) {
		this.vendordepartmentrankings = vendordepartmentrankings;
	}
	public int getTotalreg() {
		return totalreg;
	}
	public void setTotalreg(int totalreg) {
		this.totalreg = totalreg;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
		
}
