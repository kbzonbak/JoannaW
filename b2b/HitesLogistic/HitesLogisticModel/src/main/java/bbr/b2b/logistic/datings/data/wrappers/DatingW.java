package bbr.b2b.logistic.datings.data.wrappers;

import java.time.LocalDateTime;

import bbr.b2b.logistic.datings.data.interfaces.IDating;

public class DatingW extends ReserveW implements IDating {

	private Long number;
	private String comment;
	private Boolean confirmated;
	private LocalDateTime confirmationdate;
	private Boolean sentstatus;
	private Long vendorid;
	private Long dvrdeliveryid;

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

	public Long getVendorid() {
		return vendorid;
	}

	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}

	public Long getDvrdeliveryid() {
		return dvrdeliveryid;
	}

	public void setDvrdeliveryid(Long dvrdeliveryid) {
		this.dvrdeliveryid = dvrdeliveryid;
	}

}
