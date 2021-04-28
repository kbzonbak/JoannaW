package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;
import java.util.Date;

public class VeVVendorNotificationDTO implements Serializable {

	private String vendorcode;
	private String vendortradename;
	private Long ordernumber;
	private String requestnumber;
	private Date emitted;
	private Date originaldeliverydate;
	private String vevtype;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getVendortradename() {
		return vendortradename;
	}
	public void setVendortradename(String vendortradename) {
		this.vendortradename = vendortradename;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getRequestnumber() {
		return requestnumber;
	}
	public void setRequestnumber(String requestnumber) {
		this.requestnumber = requestnumber;
	}
	public Date getEmitted() {
		return emitted;
	}
	public void setEmitted(Date emitted) {
		this.emitted = emitted;
	}
	public Date getOriginaldeliverydate() {
		return originaldeliverydate;
	}
	public void setOriginaldeliverydate(Date originaldeliverydate) {
		this.originaldeliverydate = originaldeliverydate;
	}
	public String getVevtype() {
		return vevtype;
	}
	public void setVevtype(String vevtype) {
		this.vevtype = vevtype;
	}
	
}
