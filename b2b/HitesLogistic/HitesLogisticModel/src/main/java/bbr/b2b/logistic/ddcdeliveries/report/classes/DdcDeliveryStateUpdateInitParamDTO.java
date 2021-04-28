package bbr.b2b.logistic.ddcdeliveries.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DdcDeliveryStateUpdateInitParamDTO implements Serializable {
	
	private Long ddcdeliveryid;
	private String vendorcode;
	private Long ddcdeliverystatetypeid;
	private LocalDateTime deliverydate;
	private String comment;
	private String filename;
	
	public Long getDdcdeliveryid() {
		return ddcdeliveryid;
	}
	public void setDdcdeliveryid(Long ddcdeliveryid) {
		this.ddcdeliveryid = ddcdeliveryid;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long getDdcdeliverystatetypeid() {
		return ddcdeliverystatetypeid;
	}
	public void setDdcdeliverystatetypeid(Long ddcdeliverystatetypeid) {
		this.ddcdeliverystatetypeid = ddcdeliverystatetypeid;
	}
	public LocalDateTime getDeliverydate() {
		return deliverydate;
	}
	public void setDeliverydate(LocalDateTime deliverydate) {
		this.deliverydate = deliverydate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}
