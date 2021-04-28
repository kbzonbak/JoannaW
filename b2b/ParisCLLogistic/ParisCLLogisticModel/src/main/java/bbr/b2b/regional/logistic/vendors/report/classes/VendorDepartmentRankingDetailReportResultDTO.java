package bbr.b2b.regional.logistic.vendors.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class VendorDepartmentRankingDetailReportResultDTO extends BaseResultDTO{

	private String since;
	private String until;
	private String vendorname;
	private String departmentname;
	private VendorDepartmentRankingDetailReportDataDTO[] vendordepartmentrankingdetails = null;
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
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public VendorDepartmentRankingDetailReportDataDTO[] getVendordepartmentrankingdetails() {
		return vendordepartmentrankingdetails;
	}
	public void setVendordepartmentrankingdetails(VendorDepartmentRankingDetailReportDataDTO[] vendordepartmentrankingdetails) {
		this.vendordepartmentrankingdetails = vendordepartmentrankingdetails;
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
