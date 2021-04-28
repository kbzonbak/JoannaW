package bbr.b2b.regional.logistic.directorders.report.classes;

import java.io.Serializable;

public class VeVPDDataChangeDTO implements Serializable{

	private Long directorderid;
	private Long directordernumber;
	private Long newdirectorderstatetypeid;
	private Long newdodeliverystatetypeid;
	private String newdate;
	
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
	public Long getNewdirectorderstatetypeid() {
		return newdirectorderstatetypeid;
	}
	public void setNewdirectorderstatetypeid(Long newdirectorderstatetypeid) {
		this.newdirectorderstatetypeid = newdirectorderstatetypeid;
	}
	public Long getNewdodeliverystatetypeid() {
		return newdodeliverystatetypeid;
	}
	public void setNewdodeliverystatetypeid(Long newdodeliverystatetypeid) {
		this.newdodeliverystatetypeid = newdodeliverystatetypeid;
	}
	public String getNewdate() {
		return newdate;
	}
	public void setNewdate(String newdate) {
		this.newdate = newdate;
	}
		
}