package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RescheduleDateDVHOrdersDataDTO implements Serializable {

	private Integer rownumber;
	private Long dvrorderid;
	private Long dvrordernumber;
	private LocalDateTime rescheduledate;
	private String comment;

	public Integer getRownumber() {
		return rownumber;
	}

	public void setRownumber(Integer rownumber) {
		this.rownumber = rownumber;
	}

	public Long getDvrorderid() {
		return dvrorderid;
	}

	public void setDvrorderid(Long dvrorderid) {
		this.dvrorderid = dvrorderid;
	}

	public Long getDvrordernumber() {
		return dvrordernumber;
	}

	public void setDvrordernumber(Long dvrordernumber) {
		this.dvrordernumber = dvrordernumber;
	}

	public LocalDateTime getRescheduledate() {
		return rescheduledate;
	}

	public void setRescheduledate(LocalDateTime rescheduledate) {
		this.rescheduledate = rescheduledate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
