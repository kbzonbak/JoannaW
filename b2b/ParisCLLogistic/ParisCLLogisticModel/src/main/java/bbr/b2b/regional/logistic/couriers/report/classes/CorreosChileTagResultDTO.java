package bbr.b2b.regional.logistic.couriers.report.classes;

import java.util.List;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CorreosChileTagW;

public class CorreosChileTagResultDTO extends BaseResultDTO {
	
	private List<CorreosChileTagW> correosChileTagW;

	public List<CorreosChileTagW> getCorreosChileTagW() {
		return correosChileTagW;
	}

	public void setCorreosChileTagW(List<CorreosChileTagW> correosChileTagW) {
		this.correosChileTagW = correosChileTagW;
	}
}
