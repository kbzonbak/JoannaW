package bbr.b2b.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class PreDatingReserveDetailArrayResultDTO extends BaseResultDTO {

	private PreDatingReserveDetailDTO[] details;
	private PageInfoDTO pageinfo;

	public PreDatingReserveDetailDTO[] getDetails() {
		return details;
	}

	public void setDetails(PreDatingReserveDetailDTO[] details) {
		this.details = details;
	}

	public PageInfoDTO getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(PageInfoDTO pageinfo) {
		this.pageinfo = pageinfo;
	}

}
