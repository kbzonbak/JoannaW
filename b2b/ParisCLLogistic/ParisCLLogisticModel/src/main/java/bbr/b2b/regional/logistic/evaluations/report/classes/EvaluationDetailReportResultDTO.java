package bbr.b2b.regional.logistic.evaluations.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class EvaluationDetailReportResultDTO extends BaseResultDTO {

	private EvaluationDetailReportDataDTO[] evaluations;
	private PageInfoDTO pageInfo;
	
	public EvaluationDetailReportDataDTO[] getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(EvaluationDetailReportDataDTO[] evaluations) {
		this.evaluations = evaluations;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}	
}
