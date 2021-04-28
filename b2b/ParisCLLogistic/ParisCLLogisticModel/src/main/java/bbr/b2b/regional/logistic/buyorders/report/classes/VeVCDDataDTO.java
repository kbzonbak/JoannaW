package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVCDDataDTO implements Serializable{

	private Long orderid;
	private Long ordernumber;
	private String orderrequestnumber;
	private String vendorname;
	private Long orderstatetypeid;
	private String orderstatetype;
	private String fpi;
	private String fcm;
	private String deliverycurrentdate;
	private Long neworderstatetypeid;
	private String neworderstatetype;
	private String newdate;
	private Boolean valid;
	
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getOrderrequestnumber() {
		return orderrequestnumber;
	}
	public void setOrderrequestnumber(String orderrequestnumber) {
		this.orderrequestnumber = orderrequestnumber;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public Long getOrderstatetypeid() {
		return orderstatetypeid;
	}
	public void setOrderstatetypeid(Long orderstatetypeid) {
		this.orderstatetypeid = orderstatetypeid;
	}
	public String getOrderstatetype() {
		return orderstatetype;
	}
	public void setOrderstatetype(String orderstatetype) {
		this.orderstatetype = orderstatetype;
	}
	public String getFpi() {
		return fpi;
	}
	public void setFpi(String fpi) {
		this.fpi = fpi;
	}
	public String getFcm() {
		return fcm;
	}
	public void setFcm(String fcm) {
		this.fcm = fcm;
	}
	public String getDeliverycurrentdate() {
		return deliverycurrentdate;
	}
	public void setDeliverycurrentdate(String deliverycurrentdate) {
		this.deliverycurrentdate = deliverycurrentdate;
	}
	public Long getNeworderstatetypeid() {
		return neworderstatetypeid;
	}
	public void setNeworderstatetypeid(Long neworderstatetypeid) {
		this.neworderstatetypeid = neworderstatetypeid;
	}
	public String getNeworderstatetype() {
		return neworderstatetype;
	}
	public void setNeworderstatetype(String neworderstatetype) {
		this.neworderstatetype = neworderstatetype;
	}
	public String getNewdate() {
		return newdate;
	}
	public void setNewdate(String newdate) {
		this.newdate = newdate;
	}
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
			
}
