package bbr.b2b.regional.logistic.directorders.report.classes;

import java.io.Serializable;

public class VeVPDMassDataChangeDTO implements Serializable{

	private Long directordernumber;
	private Long directorderstatetypeid;
	private Long dodeliverystatetypeid;
	private String date;
	private int filtertype;
	
	public Long getDirectordernumber() {
		return directordernumber;
	}
	public void setDirectordernumber(Long directordernumber) {
		this.directordernumber = directordernumber;
	}
	public Long getDirectorderstatetypeid() {
		return directorderstatetypeid;
	}
	public void setDirectorderstatetypeid(Long directorderstatetypeid) {
		this.directorderstatetypeid = directorderstatetypeid;
	}
	public Long getDodeliverystatetypeid() {
		return dodeliverystatetypeid;
	}
	public void setDodeliverystatetypeid(Long dodeliverystatetypeid) {
		this.dodeliverystatetypeid = dodeliverystatetypeid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getFiltertype() {
		return filtertype;
	}
	public void setFiltertype(int filtertype) {
		this.filtertype = filtertype;
	}
	
}