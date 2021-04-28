package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class VeVCDDataChangeInitParamDTO extends UserDataInitParamDTO {

	private VeVCDDataChangeDTO[] data;
	private int filtertype;

	public VeVCDDataChangeDTO[] getData() {
		return data;
	}

	public void setData(VeVCDDataChangeDTO[] data) {
		this.data = data;
	}

	public int getFiltertype() {
		return filtertype;
	}

	public void setFiltertype(int filtertype) {
		this.filtertype = filtertype;
	}
	
}
