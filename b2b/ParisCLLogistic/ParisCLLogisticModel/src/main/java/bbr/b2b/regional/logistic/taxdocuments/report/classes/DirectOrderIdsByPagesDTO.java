package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DirectOrderIdsByPagesDTO extends BaseResultDTO {

	private Long[] directorderids;
	
	public Long[] getDirectorderids() {
		return directorderids;
	}

	public void setDirectorderids(Long[] directorderids) {
		this.directorderids = directorderids;
	}
	
}