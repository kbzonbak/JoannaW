package bbr.b2b.logistic.ddcdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DdcDeliveryAddResultDTO extends BaseResultDTO {

	private Long[] ddcdeliverynumbers;

	public Long[] getDdcdeliverynumbers() {
		return ddcdeliverynumbers;
	}

	public void setDdcdeliverynumbers(Long[] ddcdeliverynumbers) {
		this.ddcdeliverynumbers = ddcdeliverynumbers;
	}	
}
