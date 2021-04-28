package bbr.b2b.regional.logistic.report.classes;

import java.time.LocalDateTime;

public class PendingSOAOrderDTO {

	long ordernumber;
	String currentsoastate;
	String vendor;
	LocalDateTime emitted;
	String currentstatetype;
	String ordertype;
	LocalDateTime currentsoastatetypedate;
	
	public String getCurrentstatetype() {
		return currentstatetype;
	}

	public void setCurrentstatetype(String currentstatetype) {
		this.currentstatetype = currentstatetype;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public LocalDateTime getCurrentsoastatetypedate() {
		return currentsoastatetypedate;
	}

	public void setCurrentsoastatetypedate(LocalDateTime currentsoastatetypedate) {
		this.currentsoastatetypedate = currentsoastatetypedate;
	}

	public LocalDateTime getEmitted() {
		return emitted;
	}

	public void setEmitted(LocalDateTime emitted) {
		this.emitted = emitted;
	}

	public long getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(long ordernumber) {
		this.ordernumber = ordernumber;
	}

	public String getCurrentsoastate() {
		return currentsoastate;
	}

	public void setCurrentsoastate(String currentsoastate) {
		this.currentsoastate = currentsoastate;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

}
