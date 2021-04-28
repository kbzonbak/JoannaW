package bbr.b2b.logistic.dvrdeliveries.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDocument;

public class Document implements IDocument {

	private Long id;
	private String number;
	private LocalDateTime emitteddate;
	private Double netamount;
	private Double iva;
	private Double totalamount;
	private Boolean validated;
	private Boolean closed;
	private LocalDateTime receptiondate;
	private LocalDateTime when;
	private Long receptionnumber;
	private String asnnumber;
	private String comment;
	private Boolean status;
	private DocumentType documenttype;

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

	public LocalDateTime getEmitteddate() {
		return emitteddate;
	}

	public void setEmitteddate(LocalDateTime emitteddate) {
		this.emitteddate = emitteddate;
	}

	public Double getNetamount() {
		return netamount;
	}

	public void setNetamount(Double netamount) {
		this.netamount = netamount;
	}
	
	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}

	public Boolean getValidated() {
		return validated;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

	public Boolean getClosed() {
		return closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public LocalDateTime getReceptiondate() {
		return receptiondate;
	}

	public void setReceptiondate(LocalDateTime receptiondate) {
		this.receptiondate = receptiondate;
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public void setWhen(LocalDateTime when) {
		this.when = when;
	}

	public Long getReceptionnumber() {
		return receptionnumber;
	}

	public void setReceptionnumber(Long receptionnumber) {
		this.receptionnumber = receptionnumber;
	}

	public String getAsnnumber() {
		return asnnumber;
	}

	public void setAsnnumber(String asnnumber) {
		this.asnnumber = asnnumber;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public DocumentType getDocumenttype() {
		return documenttype;
	}

	public void setDocumenttype(DocumentType documenttype) {
		this.documenttype = documenttype;
	}

}
