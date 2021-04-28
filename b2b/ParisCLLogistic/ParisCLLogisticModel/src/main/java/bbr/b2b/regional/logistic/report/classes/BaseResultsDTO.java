package bbr.b2b.regional.logistic.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class BaseResultsDTO extends BaseResultDTO {

	private BaseResultDTO[] baseresults;

	public BaseResultDTO[] getBaseresults() {
		return baseresults;
	}

	public void setBaseresults(BaseResultDTO[] baseresults) {
		this.baseresults = baseresults;
	}	
}
