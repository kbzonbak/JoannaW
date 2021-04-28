package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;


public class DvrOrderIdsByPagesDTO extends BaseResultDTO {

	private Long[] dvrorderids;
		
	public Long[] getDvrorderids() {
		return dvrorderids;
	}
	public void setDvrorderids(Long[] dvrorderids) {
		this.dvrorderids = dvrorderids;
	}
}
