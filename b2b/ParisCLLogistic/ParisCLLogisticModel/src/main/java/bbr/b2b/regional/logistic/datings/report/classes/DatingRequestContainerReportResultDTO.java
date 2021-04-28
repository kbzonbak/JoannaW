package bbr.b2b.regional.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DatingRequestContainerReportResultDTO extends BaseResultDTO {

	private DatingRequestContainerDTO[] containerdatings;

	public DatingRequestContainerDTO[] getContainerdatings() {
		return containerdatings;
	}

	public void setContainerdatings(DatingRequestContainerDTO[] containerdatings) {
		this.containerdatings = containerdatings;
	}
		
}
