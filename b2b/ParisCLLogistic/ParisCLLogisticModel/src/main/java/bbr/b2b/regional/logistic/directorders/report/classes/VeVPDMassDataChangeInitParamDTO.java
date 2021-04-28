package bbr.b2b.regional.logistic.directorders.report.classes;

import java.io.Serializable;

public class VeVPDMassDataChangeInitParamDTO implements Serializable{

	private String filename;
	private int filtertype;
	private Long newdirectordertypeid;
	private Long newdodeliverytypeid;
	
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
	public Long getNewdirectordertypeid() {
		return newdirectordertypeid;
	}
	public void setNewdirectordertypeid(Long newdirectordertypeid) {
		this.newdirectordertypeid = newdirectordertypeid;
	}
	public Long getNewdodeliverytypeid() {
		return newdodeliverytypeid;
	}
	public void setNewdodeliverytypeid(Long newdodeliverytypeid) {
		this.newdodeliverytypeid = newdodeliverytypeid;
	}	
}