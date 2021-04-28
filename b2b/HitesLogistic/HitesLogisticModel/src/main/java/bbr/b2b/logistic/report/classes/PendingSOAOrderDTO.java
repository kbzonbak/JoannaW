package bbr.b2b.logistic.report.classes;

import java.time.LocalDateTime;

public class PendingSOAOrderDTO {

	long ordernumber;
	String currentsoastate;
	String vendor;
	LocalDateTime emitted;
	
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
	public LocalDateTime getEmitted() {
		return emitted;
	}
	public void setEmitted(LocalDateTime emitted) {
		this.emitted = emitted;
	}
}
