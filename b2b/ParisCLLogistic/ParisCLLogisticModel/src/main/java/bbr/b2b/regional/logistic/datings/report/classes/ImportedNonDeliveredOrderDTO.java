package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class ImportedNonDeliveredOrderDTO implements Serializable {

	private Long orderid;
	private Long ordernumber;
	private Long deliveryid;
	private Long asnimp;
	private Long originalvendorid;
	private String originalvendorcode;
	private String originalvendorname;
	
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public Long getAsnimp() {
		return asnimp;
	}
	public void setAsnimp(Long asnimp) {
		this.asnimp = asnimp;
	}
	public Long getOriginalvendorid() {
		return originalvendorid;
	}
	public void setOriginalvendorid(Long originalvendorid) {
		this.originalvendorid = originalvendorid;
	}
	public String getOriginalvendorcode() {
		return originalvendorcode;
	}
	public void setOriginalvendorcode(String originalvendorcode) {
		this.originalvendorcode = originalvendorcode;
	}
	public String getOriginalvendorname() {
		return originalvendorname;
	}
	public void setOriginalvendorname(String originalvendorname) {
		this.originalvendorname = originalvendorname;
	}
	
}
