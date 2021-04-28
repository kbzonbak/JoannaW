package bbr.b2b.logistic.ddcorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;


public class DdcOrderIdsByPagesDTO extends BaseResultDTO {

	private Long[] ddcorderids;

	public Long[] getDdcorderids() {
		return ddcorderids;
	}

	public void setDdcorderids(Long[] ddcorderids) {
		this.ddcorderids = ddcorderids;
	}
}
