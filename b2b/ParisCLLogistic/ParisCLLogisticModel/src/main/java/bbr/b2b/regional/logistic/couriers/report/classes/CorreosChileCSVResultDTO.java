package bbr.b2b.regional.logistic.couriers.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class CorreosChileCSVResultDTO  extends BaseResultDTO {

	private CorreosChileCSVDTO chileCSVDTO;

	public CorreosChileCSVDTO getChileCSVDTO() {
		return chileCSVDTO;
	}

	public void setChileCSVDTO(CorreosChileCSVDTO chileCSVDTO) {
		this.chileCSVDTO = chileCSVDTO;
	}
}
