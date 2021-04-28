package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class KPIvevPDReportArrayResultDTO extends BaseResultDTO{
	
	private KPIvevPDReportDataDTO[] kpivevpdReportData;
	private KPIvevPDReportDataDTO[] kpivevpdReportDataTotal;
	private PageInfoDTO pageinfo;
	private Float meta;
	private Double compliancerate;
	private Double vendordefaultrate;
	private Double courierdefaultrate;
	
	public KPIvevPDReportDataDTO[] getKpivevpdReportData() {
		return kpivevpdReportData;
	}
	public void setKpivevpdReportData(KPIvevPDReportDataDTO[] kpivevpdReportData) {
		this.kpivevpdReportData = kpivevpdReportData;
	}
	public PageInfoDTO getPageinfo() {
		return pageinfo;
	}
	public void setPageinfo(PageInfoDTO pageinfo) {
		this.pageinfo = pageinfo;
	}
	public KPIvevPDReportDataDTO[] getKpivevpdReportDataTotal() {
		return kpivevpdReportDataTotal;
	}
	public void setKpivevpdReportDataTotal(KPIvevPDReportDataDTO[] kpivevpdReportDataTotal) {
		this.kpivevpdReportDataTotal = kpivevpdReportDataTotal;
	}
	public Float getMeta() {
		return meta;
	}
	public void setMeta(Float meta) {
		this.meta = meta;
	}
	public Double getCompliancerate() {
		return compliancerate;
	}
	public void setCompliancerate(Double compliancerate) {
		this.compliancerate = compliancerate;
	}
	public Double getVendordefaultrate() {
		return vendordefaultrate;
	}
	public void setVendordefaultrate(Double vendordefaultrate) {
		this.vendordefaultrate = vendordefaultrate;
	}
	public Double getCourierdefaultrate() {
		return courierdefaultrate;
	}
	public void setCourierdefaultrate(Double courierdefaultrate) {
		this.courierdefaultrate = courierdefaultrate;
	}
			
}