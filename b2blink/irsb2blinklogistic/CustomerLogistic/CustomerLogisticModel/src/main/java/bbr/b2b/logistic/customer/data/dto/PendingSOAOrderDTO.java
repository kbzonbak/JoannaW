package bbr.b2b.logistic.customer.data.dto;

import java.time.LocalDateTime;

public class PendingSOAOrderDTO {

	long ordernumber;
	LocalDateTime emitted;
	String vendor;
	String currentsoastate;
	LocalDateTime currentsoastatetypedate;
	String currentstatetype;
	String ordertype;
	
	public long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public LocalDateTime getEmitted() {
		return emitted;
	}
	public void setEmitted(LocalDateTime emitted) {
		this.emitted = emitted;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getCurrentsoastate() {
		return currentsoastate;
	}
	public void setCurrentsoastate(String currentsoastate) {
		this.currentsoastate = currentsoastate;
	}
	public LocalDateTime getCurrentsoastatetypedate() {
		return currentsoastatetypedate;
	}
	public void setCurrentsoastatetypedate(LocalDateTime currentsoastatetypedate) {
		this.currentsoastatetypedate = currentsoastatetypedate;
	}
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
	
	
	
}
