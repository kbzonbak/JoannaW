package bbr.b2b.regional.logistic.fillrate.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class FillRateDetailReportResultDTO extends BaseResultDTO{

	private int year;
	private String month;
	private String vendorname;
	private String departmentname;
	private FillRateDetailReportDataDTO[] fillratedetail = null;
	private int totalreg;
	private PageInfoDTO pageInfo;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
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
	public FillRateDetailReportDataDTO[] getFillratedetail() {
		return fillratedetail;
	}
	public void setFillratedetail(FillRateDetailReportDataDTO[] fillratedetail) {
		this.fillratedetail = fillratedetail;
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
