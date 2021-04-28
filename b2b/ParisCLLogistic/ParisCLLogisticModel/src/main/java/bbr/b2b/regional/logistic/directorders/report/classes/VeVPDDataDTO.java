package bbr.b2b.regional.logistic.directorders.report.classes;

import java.io.Serializable;

public class VeVPDDataDTO implements Serializable{

	private Long directorderid;
	private Long directordernumber;
	private String directorderrequestnumber;
	private String vendorname;
	private Long directorderstatetypeid;
	private String directorderstatetype;
	private Long dodeliveryid;
	private Long dodeliverynumber;
	private Long dodeliverystatetypeid;
	private String dodeliverystatetype;
	private String fcm;
	private String deliverycurrentdate;
	private Long newdirectorderstatetypeid;
	private String newdirectorderstatetype;
	private Long newdodeliverystatetypeid;
	private String newdodeliverystatetype;
	private String newdate;
	private Boolean valid;
	
	public Long getDirectorderid() {
		return directorderid;
	}
	public void setDirectorderid(Long directorderid) {
		this.directorderid = directorderid;
	}
	public Long getDirectordernumber() {
		return directordernumber;
	}
	public void setDirectordernumber(Long directordernumber) {
		this.directordernumber = directordernumber;
	}
	public String getDirectorderrequestnumber() {
		return directorderrequestnumber;
	}
	public void setDirectorderrequestnumber(String directorderrequestnumber) {
		this.directorderrequestnumber = directorderrequestnumber;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public Long getDirectorderstatetypeid() {
		return directorderstatetypeid;
	}
	public void setDirectorderstatetypeid(Long directorderstatetypeid) {
		this.directorderstatetypeid = directorderstatetypeid;
	}
	public String getDirectorderstatetype() {
		return directorderstatetype;
	}
	public void setDirectorderstatetype(String directorderstatetype) {
		this.directorderstatetype = directorderstatetype;
	}
	public Long getDodeliveryid() {
		return dodeliveryid;
	}
	public void setDodeliveryid(Long dodeliveryid) {
		this.dodeliveryid = dodeliveryid;
	}
	public Long getDodeliverynumber() {
		return dodeliverynumber;
	}
	public void setDodeliverynumber(Long dodeliverynumber) {
		this.dodeliverynumber = dodeliverynumber;
	}
	public Long getDodeliverystatetypeid() {
		return dodeliverystatetypeid;
	}
	public void setDodeliverystatetypeid(Long dodeliverystatetypeid) {
		this.dodeliverystatetypeid = dodeliverystatetypeid;
	}
	public String getDodeliverystatetype() {
		return dodeliverystatetype;
	}
	public void setDodeliverystatetype(String dodeliverystatetype) {
		this.dodeliverystatetype = dodeliverystatetype;
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
	public Long getNewdirectorderstatetypeid() {
		return newdirectorderstatetypeid;
	}
	public void setNewdirectorderstatetypeid(Long newdirectorderstatetypeid) {
		this.newdirectorderstatetypeid = newdirectorderstatetypeid;
	}
	public String getNewdirectorderstatetype() {
		return newdirectorderstatetype;
	}
	public void setNewdirectorderstatetype(String newdirectorderstatetype) {
		this.newdirectorderstatetype = newdirectorderstatetype;
	}
	public Long getNewdodeliverystatetypeid() {
		return newdodeliverystatetypeid;
	}
	public void setNewdodeliverystatetypeid(Long newdodeliverystatetypeid) {
		this.newdodeliverystatetypeid = newdodeliverystatetypeid;
	}
	public String getNewdodeliverystatetype() {
		return newdodeliverystatetype;
	}
	public void setNewdodeliverystatetype(String newdodeliverystatetype) {
		this.newdodeliverystatetype = newdodeliverystatetype;
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