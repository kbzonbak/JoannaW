package bbr.b2b.regional.logistic.mobile.classes;

import java.io.Serializable;

public class DODeliveryUpdateStatusMobileInitParmDTO implements Serializable {

	private Long userid;
	private Long deliveryid;
	private String dodstatuscode;
	private String dodcomment;
	private String imagedata;
	
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public String getDodstatuscode() {
		return dodstatuscode;
	}
	public void setDodstatuscode(String dodstatuscode) {
		this.dodstatuscode = dodstatuscode;
	}
	public String getDodcomment() {
		return dodcomment;
	}
	public void setDodcomment(String dodcomment) {
		this.dodcomment = dodcomment;
	}
	public String getImagedata() {
		return imagedata;
	}
	public void setImagedata(String imagedata) {
		this.imagedata = imagedata;
	}
}
