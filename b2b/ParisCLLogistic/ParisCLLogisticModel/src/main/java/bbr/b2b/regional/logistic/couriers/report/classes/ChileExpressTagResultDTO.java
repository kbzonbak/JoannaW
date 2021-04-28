package bbr.b2b.regional.logistic.couriers.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.regional.logistic.couriers.data.wrappers.ChileExpressTagW;

public class ChileExpressTagResultDTO extends BaseResultDTO {

	private ChileExpressTagW chileExpressTagW;
	private Long number;

	public ChileExpressTagW getChileExpressTagW() {
		return chileExpressTagW;
	}

	public void setChileExpressTagW(ChileExpressTagW chileExpressTagW) {
		this.chileExpressTagW = chileExpressTagW;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

}
