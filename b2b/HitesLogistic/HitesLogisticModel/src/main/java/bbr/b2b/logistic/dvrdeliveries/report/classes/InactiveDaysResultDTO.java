package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.time.DayOfWeek;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class InactiveDaysResultDTO extends BaseResultDTO {

	private DayOfWeek[] inactivedays;

	public DayOfWeek[] getInactivedays() {
		return inactivedays;
	}

	public void setInactivedays(DayOfWeek[] inactivedays) {
		this.inactivedays = inactivedays;
	}

}
