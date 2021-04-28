package bbr.b2b.regional.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ReserveDetailArrayResultDTO extends BaseResultDTO {

	private ReserveDetailDTO[] reservedetail;

	public ReserveDetailDTO[] getReservedetail() {
		return reservedetail;
	}

	public void setReservedetail(ReserveDetailDTO[] reservedetail) {
		this.reservedetail = reservedetail;
	}

	
}
