package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class ReceptionDetailArrayResultDTO extends BaseResultDTO {

	private ReceptionDetailDTO[] receptiondetails;
	private ReceptionDetailTotalDTO totals;
	private PageInfoDTO pageInfo;
	
	public ReceptionDetailDTO[] getReceptiondetails() {
		return receptiondetails;
	}
	public void setReceptiondetails(ReceptionDetailDTO[] receptiondetails) {
		this.receptiondetails = receptiondetails;
	}
	public ReceptionDetailTotalDTO getTotals() {
		return totals;
	}
	public void setTotals(ReceptionDetailTotalDTO totals) {
		this.totals = totals;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
}
