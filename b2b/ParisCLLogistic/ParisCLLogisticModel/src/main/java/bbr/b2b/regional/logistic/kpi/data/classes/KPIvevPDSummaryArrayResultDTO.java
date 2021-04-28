package bbr.b2b.regional.logistic.kpi.data.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class KPIvevPDSummaryArrayResultDTO extends BaseResultDTO {

	private KPIvevPDSummaryDTO[] summary;
	private KPIvevPDSummaryTotalDTO total;
	
	public KPIvevPDSummaryDTO[] getSummary() {
		return summary;
	}
	public void setSummary(KPIvevPDSummaryDTO[] summary) {
		this.summary = summary;
	}
	public KPIvevPDSummaryTotalDTO getTotal() {
		return total;
	}
	public void setTotal(KPIvevPDSummaryTotalDTO total) {
		this.total = total;
	}
}
