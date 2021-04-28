package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;

public class DoChangeOrderStateInitParamDTO extends UserDataInitParamDTO {

	private Long orderid;
	private String vendorcode;//rut del prov
	private Long orderstatetypeid;
	private String reprogdate;
	private String comment;
	
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long getOrderstatetypeid() {
		return orderstatetypeid;
	}
	public void setOrderstatetypeid(Long orderstatetypeid) {
		this.orderstatetypeid = orderstatetypeid;
	}
	public String getReprogdate() {
		return reprogdate;
	}
	public void setReprogdate(String reprogdate) {
		this.reprogdate = reprogdate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
