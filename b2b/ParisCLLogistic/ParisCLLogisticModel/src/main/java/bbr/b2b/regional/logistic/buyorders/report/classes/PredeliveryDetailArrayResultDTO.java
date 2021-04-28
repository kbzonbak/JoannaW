package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class PredeliveryDetailArrayResultDTO extends BaseResultDTO {

	private PredeliveryDetailDTO[] predeliverydetail;

	public PredeliveryDetailDTO[] getPredeliverydetail() {
		return predeliverydetail;
	}

	public void setPredeliverydetail(PredeliveryDetailDTO[] predeliverydetail) {
		this.predeliverydetail = predeliverydetail;
	}
}
