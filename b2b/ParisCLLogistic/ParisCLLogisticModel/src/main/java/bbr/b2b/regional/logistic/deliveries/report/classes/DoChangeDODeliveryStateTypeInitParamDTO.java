package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.util.Date;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class DoChangeDODeliveryStateTypeInitParamDTO extends UserDataInitParamDTO {

	private Long dodeliveryid;
	private String vendorcode;
	private Long dodeliverystatetypeid;
	private String reprogdate;
	private Date commiteddate;
	private String comment;
	
	public Long getDodeliveryid() {
		return dodeliveryid;
	}
	public void setDodeliveryid(Long dodeliveryid) {
		this.dodeliveryid = dodeliveryid;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long getDodeliverystatetypeid() {
		return dodeliverystatetypeid;
	}
	public void setDodeliverystatetypeid(Long dodeliverystatetypeid) {
		this.dodeliverystatetypeid = dodeliverystatetypeid;
	}
	public String getReprogdate() {
		return reprogdate;
	}
	public void setReprogdate(String reprogdate) {
		this.reprogdate = reprogdate;
	}
	public Date getCommiteddate() {
		return commiteddate;
	}
	public void setCommiteddate(Date commiteddate) {
		this.commiteddate = commiteddate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
