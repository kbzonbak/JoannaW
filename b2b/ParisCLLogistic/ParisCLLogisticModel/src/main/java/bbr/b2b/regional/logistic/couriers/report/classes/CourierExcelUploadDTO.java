package bbr.b2b.regional.logistic.couriers.report.classes;

import java.io.Serializable;

public class CourierExcelUploadDTO implements Serializable {

	private int line;
	private Long directordernumber;
	private Long dodeliverynumber;
	
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public Long getDirectordernumber() {
		return directordernumber;
	}
	public void setDirectordernumber(Long directordernumber) {
		this.directordernumber = directordernumber;
	}
	public Long getDodeliverynumber() {
		return dodeliverynumber;
	}
	public void setDodeliverynumber(Long dodeliverynumber) {
		this.dodeliverynumber = dodeliverynumber;
	}
	
}
