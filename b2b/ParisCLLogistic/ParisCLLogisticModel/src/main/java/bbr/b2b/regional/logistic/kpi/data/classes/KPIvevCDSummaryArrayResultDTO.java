package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class KPIvevCDSummaryArrayResultDTO extends BaseResultDTO {

	private KPIvevCDSummaryDTO[] summary;
	private KPIvevCDSummaryTotalDTO total;
	
	public KPIvevCDSummaryDTO[] getSummary() {
		return summary;
	}
	public void setSummary(KPIvevCDSummaryDTO[] summary) {
		this.summary = summary;
	}
	public KPIvevCDSummaryTotalDTO getTotal() {
		return total;
	}
	public void setTotal(KPIvevCDSummaryTotalDTO total) {
		this.total = total;
	}
}
