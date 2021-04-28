package bbr.b2b.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class ReserveDetailBlockingReportDTO extends BaseResultDTO {

	private ReserveDetailBlockingDataDTO[] reservedetailblockingdata;
	private PageInfoDTO pageinfo;

	public ReserveDetailBlockingDataDTO[] getReservedetailblockingdata() {
		return reservedetailblockingdata;
	}

	public void setReservedetailblockingdata(ReserveDetailBlockingDataDTO[] reservedetailblockingdata) {
		this.reservedetailblockingdata = reservedetailblockingdata;
	}

	public PageInfoDTO getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(PageInfoDTO pageinfo) {
		this.pageinfo = pageinfo;
	}

}
