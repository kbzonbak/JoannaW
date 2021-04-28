package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;

public class DeleteDatingInitParamDTO implements Serializable {

	private Long reserveid;
	private String comment;

	public Long getReserveid() {
		return reserveid;
	}

	public void setReserveid(Long reserveid) {
		this.reserveid = reserveid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
