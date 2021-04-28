package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class DatingDataDTO implements Serializable {

	private Long id;
	private String number;
	private String when;
	private Boolean confirmed;
	private String confirmationdate;
	private String assigneddate;
	private String comment;
	private Long vendorid;
	private Long deliveryid;
	private Long locationid;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getWhen() {
		return when;
	}
	public void setWhen(String when) {
		this.when = when;
	}
	public Long getLocationid() {
		return locationid;
	}
	public void setLocationid(Long locationid) {
		this.locationid = locationid;
	}
	public Boolean getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}
	public String getConfirmationdate() {
		return confirmationdate;
	}
	public void setConfirmationdate(String confirmationdate) {
		this.confirmationdate = confirmationdate;
	}
	
	public String getAssigneddate() {
		return assigneddate;
	}
	public void setAssigneddate(String assigneddate) {
		this.assigneddate = assigneddate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}	
}
