package bbr.b2b.logistic.ddcorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DdcOrderDeliveryStateDataDTO implements Serializable {

	private Long ddcdeliveryid;
	private Long ddcdeliverynumber;
	private Long ddcdeliverystateid;
	private LocalDateTime ddcdeliverystatetypewhen;
	private Long ddcdeliverystatetypeid;
	private String ddcdeliverystatetypecode;
	private String ddcdeliverystatetypename;
	private String ddcdeliverystateusername;
	private String ddcdeliverystateusertype;
	private String ddcdeliverystatecomment;
	
	public Long getDdcdeliveryid() {
		return ddcdeliveryid;
	}
	public void setDdcdeliveryid(Long ddcdeliveryid) {
		this.ddcdeliveryid = ddcdeliveryid;
	}
	public Long getDdcdeliverynumber() {
		return ddcdeliverynumber;
	}
	public void setDdcdeliverynumber(Long ddcdeliverynumber) {
		this.ddcdeliverynumber = ddcdeliverynumber;
	}
	public Long getDdcdeliverystateid() {
		return ddcdeliverystateid;
	}
	public void setDdcdeliverystateid(Long ddcdeliverystateid) {
		this.ddcdeliverystateid = ddcdeliverystateid;
	}
	public LocalDateTime getDdcdeliverystatetypewhen() {
		return ddcdeliverystatetypewhen;
	}
	public void setDdcdeliverystatetypewhen(LocalDateTime ddcdeliverystatetypewhen) {
		this.ddcdeliverystatetypewhen = ddcdeliverystatetypewhen;
	}
	public Long getDdcdeliverystatetypeid() {
		return ddcdeliverystatetypeid;
	}
	public void setDdcdeliverystatetypeid(Long ddcdeliverystatetypeid) {
		this.ddcdeliverystatetypeid = ddcdeliverystatetypeid;
	}
	public String getDdcdeliverystatetypecode() {
		return ddcdeliverystatetypecode;
	}
	public void setDdcdeliverystatetypecode(String ddcdeliverystatetypecode) {
		this.ddcdeliverystatetypecode = ddcdeliverystatetypecode;
	}
	public String getDdcdeliverystatetypename() {
		return ddcdeliverystatetypename;
	}
	public void setDdcdeliverystatetypename(String ddcdeliverystatetypename) {
		this.ddcdeliverystatetypename = ddcdeliverystatetypename;
	}
	public String getDdcdeliverystateusername() {
		return ddcdeliverystateusername;
	}
	public void setDdcdeliverystateusername(String ddcdeliverystateusername) {
		this.ddcdeliverystateusername = ddcdeliverystateusername;
	}
	public String getDdcdeliverystateusertype() {
		return ddcdeliverystateusertype;
	}
	public void setDdcdeliverystateusertype(String ddcdeliverystateusertype) {
		this.ddcdeliverystateusertype = ddcdeliverystateusertype;
	}
	public String getDdcdeliverystatecomment() {
		return ddcdeliverystatecomment;
	}
	public void setDdcdeliverystatecomment(String ddcdeliverystatecomment) {
		this.ddcdeliverystatecomment = ddcdeliverystatecomment;
	}
	
}
