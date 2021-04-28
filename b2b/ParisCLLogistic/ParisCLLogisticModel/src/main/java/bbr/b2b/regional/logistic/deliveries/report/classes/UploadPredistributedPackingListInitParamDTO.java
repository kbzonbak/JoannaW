package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class UploadPredistributedPackingListInitParamDTO implements Serializable {

	private String locationcode;
	private String datingdate;
	private String datingtime;
	private Integer durationinminutes;
	private String dockcode;
	private String filename;
	
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public String getDatingdate() {
		return datingdate;
	}
	public void setDatingdate(String datingdate) {
		this.datingdate = datingdate;
	}
	public String getDatingtime() {
		return datingtime;
	}
	public void setDatingtime(String datingtime) {
		this.datingtime = datingtime;
	}
	public Integer getDurationinminutes() {
		return durationinminutes;
	}
	public void setDurationinminutes(Integer durationinminutes) {
		this.durationinminutes = durationinminutes;
	}
	public String getDockcode() {
		return dockcode;
	}
	public void setDockcode(String dockcode) {
		this.dockcode = dockcode;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
		
}
