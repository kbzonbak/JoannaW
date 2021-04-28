package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DvrOrderLabelArrayResultDTO extends BaseResultDTO {

	private DvrOrderLabelDTO[] labels;

	public DvrOrderLabelDTO[] getLabels() {
		return labels;
	}

	public void setLabels(DvrOrderLabelDTO[] labels) {
		this.labels = labels;
	}

}
