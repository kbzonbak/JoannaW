package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class RejectDDCOrdersDataDTO implements Serializable {

	private Integer rownumber;
	private Long ddcorderid;
	private Long ddcordernumber;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
