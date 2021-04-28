package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class DODeliveryResultBean implements Serializable{
	
	Long dodeliverynumber;
	Long directordernumber;
	String comment;
	
	public DODeliveryResultBean() {
	}
	
	public DODeliveryResultBean(Long dodeliverynumber, Long directordernumber, String comment) {
		super();
		this.dodeliverynumber = dodeliverynumber;
		this.directordernumber = directordernumber;
		this.comment = comment;
	}
	
	public Long getDodeliverynumber() {
		return dodeliverynumber;
	}
	public void setDodeliverynumber(Long dodeliverynumber) {
		this.dodeliverynumber = dodeliverynumber;
	}
	public Long getDirectordernumber() {
		return directordernumber;
	}
	public void setDirectordernumber(Long directordernumber) {
		this.directordernumber = directordernumber;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
