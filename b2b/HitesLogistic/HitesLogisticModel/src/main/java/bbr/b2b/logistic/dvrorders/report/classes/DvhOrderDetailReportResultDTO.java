package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DvhOrderDetailReportResultDTO extends BaseResultDTO {

	private DvhOrderReportDataDTO dvhorder;
	private DvhOrderDetailDataDTO[] dvhorderdetail;
	private DvhOrderDetailReportTotalDataDTO total;
	private PageInfoDTO pageInfo;

	public DvhOrderReportDataDTO getDvhorder() {
		return dvhorder;
	}

	public void setDvhorder(DvhOrderReportDataDTO dvhorder) {
		this.dvhorder = dvhorder;
	}

	public DvhOrderDetailDataDTO[] getDvhorderdetail() {
		return dvhorderdetail;
	}

	public void setDvhorderdetail(DvhOrderDetailDataDTO[] dvhorderdetail) {
		this.dvhorderdetail = dvhorderdetail;
	}

	public DvhOrderDetailReportTotalDataDTO getTotal() {
		return total;
	}

	public void setTotal(DvhOrderDetailReportTotalDataDTO total) {
		this.total = total;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}

}
