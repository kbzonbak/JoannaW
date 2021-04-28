package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class RejectDVHOrdersDataDTO implements Serializable {

	private Integer rownumber;
	private Long dvrorderid;
	private Long dvrordernumber;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
