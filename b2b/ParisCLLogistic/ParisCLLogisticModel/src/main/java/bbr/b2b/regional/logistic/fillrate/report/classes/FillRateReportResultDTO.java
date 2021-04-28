package bbr.b2b.regional.logistic.fillrate.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class FillRateReportResultDTO extends BaseResultDTO{

	private int year;
	private String month;
	private FillRateReportDataDTO[] fillrates = null;
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
	public FillRateReportDataDTO[] getFillrates() {
		return fillrates;
	}
	public void setFillrates(FillRateReportDataDTO[] fillrates) {
		this.fillrates = fillrates;
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
