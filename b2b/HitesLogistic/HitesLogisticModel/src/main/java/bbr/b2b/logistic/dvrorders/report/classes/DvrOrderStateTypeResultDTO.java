package bbr.b2b.logistic.dvrorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DvrOrderStateTypeResultDTO extends BaseResultDTO {

	private DvrOrderStateTypeDTO[] dvrorderstatetype;

	public DvrOrderStateTypeDTO[] getDvrorderstatetype() {
		return dvrorderstatetype;
	}

	public void setDvrorderstatetype(DvrOrderStateTypeDTO[] dvrorderstatetype) {
		this.dvrorderstatetype = dvrorderstatetype;
	}

}
