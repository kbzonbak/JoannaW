package bbr.b2b.logistic.ddcorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DdcOrderStateTypeArrayResultDTO extends BaseResultDTO {

	private DdcOrderStateTypeDTO[] ddcorderstatetype;

	public DdcOrderStateTypeDTO[] getDdcorderstatetype() {
		return ddcorderstatetype;
	}

	public void setDdcorderstatetype(DdcOrderStateTypeDTO[] ddcorderstatetype) {
		this.ddcorderstatetype = ddcorderstatetype;
	}

}
