package bbr.b2b.regional.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class ReserverDetailReportDTO extends BaseResultDTO {

	private ReserveDetailDataDTO[] reservesdatail;
	private PageInfoDTO pageinfo;

	public ReserveDetailDataDTO[] getReservesdatail() {
		return reservesdatail;
	}

	public void setReservesdatail(ReserveDetailDataDTO[] reservesdatail) {
		this.reservesdatail = reservesdatail;
	}

	public PageInfoDTO getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(PageInfoDTO pageinfo) {
		this.pageinfo = pageinfo;
	}

}
