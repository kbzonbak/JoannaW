package bbr.b2b.logistic.datings.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.datings.data.interfaces.IDating;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery;
import bbr.b2b.logistic.vendors.entities.Vendor;

public class Dating extends Reserve implements IDating {

	private Long number;
	private String comment;
	private Boolean confirmated;
	private LocalDateTime confirmationdate;
	private Boolean sentstatus;
	private Vendor vendor;
	private DvrDelivery dvrdelivery;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getConfirmated() {
		return confirmated;
	}

	public void setConfirmated(Boolean confirmated) {
		this.confirmated = confirmated;
	}

	public LocalDateTime getConfirmationdate() {
		return confirmationdate;
	}

	public void setConfirmationdate(LocalDateTime confirmationdate) {
		this.confirmationdate = confirmationdate;
	}

	public Boolean getSentstatus() {
		return sentstatus;
	}

	public void setSentstatus(Boolean sentstatus) {
		this.sentstatus = sentstatus;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public DvrDelivery getDvrdelivery() {
		return dvrdelivery;
	}

	public void setDvrdelivery(DvrDelivery dvrdelivery) {
		this.dvrdelivery = dvrdelivery;
	}

}
