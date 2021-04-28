package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DatingInformationDTO implements Serializable {

	private Long dvrdelvieryid;
	private LocalDateTime confirmationdate;
	private LocalDateTime datingdate;
	private String comment;

	public Long getDvrdelvieryid() {
		return dvrdelvieryid;
	}

	public void setDvrdelvieryid(Long dvrdelvieryid) {
		this.dvrdelvieryid = dvrdelvieryid;
	}

	public LocalDateTime getConfirmationdate() {
		return confirmationdate;
	}

	public void setConfirmationdate(LocalDateTime confirmationdate) {
		this.confirmationdate = confirmationdate;
	}

	public LocalDateTime getDatingdate() {
		return datingdate;
	}

	public void setDatingdate(LocalDateTime datingdate) {
		this.datingdate = datingdate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
