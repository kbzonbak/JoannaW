package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class DatingRequestContainerDTO implements Serializable {

	private String datingdate;
	private Long dockid;
	private String dockcode;
	private String datingtime;
	private String datinginterval;
	private Long deliverylocationid;
	private String deliverylocationcode;
	private String deliverylocationname;
	private Long flowtypeid;
	private String flowtypecode;
	private String flowtypename;
	
	public String getDatingdate() {
		return datingdate;
	}
	public void setDatingdate(String datingdate) {
		this.datingdate = datingdate;
	}
	public Long getDockid() {
		return dockid;
	}
	public void setDockid(Long dockid) {
		this.dockid = dockid;
	}
	public String getDockcode() {
		return dockcode;
	}
	public void setDockcode(String dockcode) {
		this.dockcode = dockcode;
	}
	public String getDatingtime() {
		return datingtime;
	}
	public void setDatingtime(String datingtime) {
		this.datingtime = datingtime;
	}
	public String getDatinginterval() {
		return datinginterval;
	}
	public void setDatinginterval(String datinginterval) {
		this.datinginterval = datinginterval;
	}
	public Long getDeliverylocationid() {
		return deliverylocationid;
	}
	public void setDeliverylocationid(Long deliverylocationid) {
		this.deliverylocationid = deliverylocationid;
	}
	public String getDeliverylocationcode() {
		return deliverylocationcode;
	}
	public void setDeliverylocationcode(String deliverylocationcode) {
		this.deliverylocationcode = deliverylocationcode;
	}
	public String getDeliverylocationname() {
		return deliverylocationname;
	}
	public void setDeliverylocationname(String deliverylocationname) {
		this.deliverylocationname = deliverylocationname;
	}
	public Long getFlowtypeid() {
		return flowtypeid;
	}
	public void setFlowtypeid(Long flowtypeid) {
		this.flowtypeid = flowtypeid;
	}
	public String getFlowtypecode() {
		return flowtypecode;
	}
	public void setFlowtypecode(String flowtypecode) {
		this.flowtypecode = flowtypecode;
	}
	public String getFlowtypename() {
		return flowtypename;
	}
	public void setFlowtypename(String flowtypename) {
		this.flowtypename = flowtypename;
	}

}
