package bbr.b2b.logistic.dvrdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class TimeModuleReportDataDTO extends BaseResultDTO {

	private TimeModuleDTO[] timemodule;

	public TimeModuleDTO[] getTimemodule() {
		return timemodule;
	}

	public void setTimemodule(TimeModuleDTO[] timemodule) {
		this.timemodule = timemodule;
	}

}
