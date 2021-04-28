package bbr.b2b.regional.logistic.directorders.report.classes;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class VeVPDDataChangeInitParamDTO extends UserDataInitParamDTO {

	private VeVPDDataChangeDTO[] data;
	private int filtertype;

	public VeVPDDataChangeDTO[] getData() {
		return data;
	}

	public void setData(VeVPDDataChangeDTO[] data) {
		this.data = data;
	}

	public int getFiltertype() {
		return filtertype;
	}

	public void setFiltertype(int filtertype) {
		this.filtertype = filtertype;
	}
	
}