package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVCDMassDataChangeInitParamDTO implements Serializable{

	private String filename;
	private int filtertype;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getFiltertype() {
		return filtertype;
	}
	public void setFiltertype(int filtertype) {
		this.filtertype = filtertype;
	}
	
}