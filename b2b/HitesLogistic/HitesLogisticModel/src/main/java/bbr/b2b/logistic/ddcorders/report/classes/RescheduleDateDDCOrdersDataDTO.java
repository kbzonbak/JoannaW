package bbr.b2b.logistic.ddcorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RescheduleDateDDCOrdersDataDTO implements Serializable {

	private Integer rownumber;
	private Long ddcorderid;
	private Long ddcordernumber;
	private LocalDateTime rescheduledate;
	private String comment;

	public Integer getRownumber() {
		return rownumber;
	}

	public void setRownumber(Integer rownumber) {
		this.rownumber = rownumber;
	}

	public Long getDdcorderid() {
		return ddcorderid;
	}

	public void setDdcorderid(Long ddcorderid) {
		this.ddcorderid = ddcorderid;
	}

	public Long getDdcordernumber() {
		return ddcordernumber;
	}

	public void setDdcordernumber(Long ddcordernumber) {
		this.ddcordernumber = ddcordernumber;
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
