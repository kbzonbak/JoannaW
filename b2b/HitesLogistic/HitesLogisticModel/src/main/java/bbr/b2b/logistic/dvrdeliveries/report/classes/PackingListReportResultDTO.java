package bbr.b2b.logistic.dvrdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class PackingListReportResultDTO extends BaseResultDTO {

	private PackingListReportDataDTO[] packinglistreportdata;
	private PackingListReportTotalDataDTO total;
	private PageInfoDTO pageInfo;

	public PackingListReportDataDTO[] getPackinglistreportdata() {
		return packinglistreportdata;
	}

	public void setPackinglistreportdata(PackingListReportDataDTO[] packinglistreportdata) {
		this.packinglistreportdata = packinglistreportdata;
	}

	public PackingListReportTotalDataDTO getTotal() {
		return total;
	}

	public void setTotal(PackingListReportTotalDataDTO total) {
		this.total = total;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}

}
