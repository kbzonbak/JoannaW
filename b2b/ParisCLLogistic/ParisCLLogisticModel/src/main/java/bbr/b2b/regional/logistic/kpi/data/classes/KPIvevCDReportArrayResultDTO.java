package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class KPIvevCDReportArrayResultDTO extends BaseResultDTO{
	
	private KPIvevCDReportDataDTO[] kpivevcdReportData;
	private KPIvevCDReportDataDTO[] kpivevcdReportDataTotal;
	private PageInfoDTO pageinfo;
	private Float meta;
	private Double compliancerate;
	private Double defaultrate;	
	
	public Double getCompliancerate() {
		return compliancerate;
	}
	public void setCompliancerate(Double compliancerate) {
		this.compliancerate = compliancerate;
	}
	public Double getDefaultrate() {
		return defaultrate;
	}
	public void setDefaultrate(Double defaultrate) {
		this.defaultrate = defaultrate;
	}
	public KPIvevCDReportDataDTO[] getKpivevcdReportData() {
		return kpivevcdReportData;
	}
	public void setKpivevcdReportData(KPIvevCDReportDataDTO[] kpivevcdReportData) {
		this.kpivevcdReportData = kpivevcdReportData;
	}
	public PageInfoDTO getPageinfo() {
		return pageinfo;
	}
	public void setPageinfo(PageInfoDTO pageinfo) {
		this.pageinfo = pageinfo;
	}
	public KPIvevCDReportDataDTO[] getKpivevcdReportDataTotal() {
		return kpivevcdReportDataTotal;
	}
	public void setKpivevcdReportDataTotal(KPIvevCDReportDataDTO[] kpivevcdReportDataTotal) {
		this.kpivevcdReportDataTotal = kpivevcdReportDataTotal;
	}
	public Float getMeta() {
		return meta;
	}
	public void setMeta(Float meta) {
		this.meta = meta;
	}

}
