package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AssignedDatingInitParamDTO implements Serializable {

	private String locationcode;
	private LocalDateTime date;
	private Long docktypeid;
	
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Long getDocktypeid() {
		return docktypeid;
	}
	public void setDocktypeid(Long docktypeid) {
		this.docktypeid = docktypeid;
	}
}
