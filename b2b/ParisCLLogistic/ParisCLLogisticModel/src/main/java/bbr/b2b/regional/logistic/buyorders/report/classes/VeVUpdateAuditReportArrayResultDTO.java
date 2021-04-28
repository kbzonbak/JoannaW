package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class VeVUpdateAuditReportArrayResultDTO extends BaseResultDTO{

	private VeVUpdateAuditReportDataDTO[] vevupdates = null;
	private PageInfoDTO pageinfo = null;
	private int totalrows;
	
	public VeVUpdateAuditReportDataDTO[] getVevupdates() {
		return vevupdates;
	}
	public void setVevupdates(VeVUpdateAuditReportDataDTO[] vevupdates) {
		this.vevupdates = vevupdates;
	}
	public PageInfoDTO getPageinfo() {
		return pageinfo;
	}
	public void setPageinfo(PageInfoDTO pageinfo) {
		this.pageinfo = pageinfo;
	}
	public int getTotalrows() {
		return totalrows;
	}
	public void setTotalrows(int totalrows) {
		this.totalrows = totalrows;
	}
	
}
