package bbr.b2b.logistic.ddcorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class DdcOrderDetailReportResultDTO extends BaseResultDTO {

	private DdcOrderReportDataDTO ddcorder;
	private DdcOrderDetailDataDTO[] ddcorderdetail;
	private DdcOrderDetailReportTotalDataDTO total;
	private PageInfoDTO pageInfo;
	
	public DdcOrderReportDataDTO getDdcorder() {
		return ddcorder;
	}
	public void setDdcorder(DdcOrderReportDataDTO ddcorder) {
		this.ddcorder = ddcorder;
	}
	public DdcOrderDetailDataDTO[] getDdcorderdetail() {
		return ddcorderdetail;
	}
	public void setDdcorderdetail(DdcOrderDetailDataDTO[] ddcorderdetail) {
		this.ddcorderdetail = ddcorderdetail;
	}
	public DdcOrderDetailReportTotalDataDTO getTotal() {
		return total;
	}
	public void setTotal(DdcOrderDetailReportTotalDataDTO total) {
		this.total = total;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}

}
