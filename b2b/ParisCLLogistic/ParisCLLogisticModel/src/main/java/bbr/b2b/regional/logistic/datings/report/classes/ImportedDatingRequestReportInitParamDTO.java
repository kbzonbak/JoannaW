package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class ImportedDatingRequestReportInitParamDTO extends DatingRequestReportInitParamDTO implements Serializable {

	private int filtertype;

	public int getFiltertype() {
		return filtertype;
	}

	public void setFiltertype(int filtertype) {
		this.filtertype = filtertype;
	}
	
}
