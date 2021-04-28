package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DvrDeliveryDatingDocumentDetailReportDataDTO implements Serializable {

	private Long documentid;
	private String documentnumber;
	private Long ordernumber;
	private String documenttype;
	private LocalDateTime emitteddate;
	private Double netamount;
	private Double totalamount;
	private Double taxamount;
	private Boolean validated;
	private Boolean sentstatus;

	public Long getDocumentid() {
		return documentid;
	}

	public void setDocumentid(Long documentid) {
		this.documentid = documentid;
	}

	public String getDocumentnumber() {
		return documentnumber;
	}

	public void setDocumentnumber(String documentnumber) {
		this.documentnumber = documentnumber;
	}

	public Long getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}

	public String getDocumenttype() {
		return documenttype;
	}

	public void setDocumenttype(String documenttype) {
		this.documenttype = documenttype;
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

	public Double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}

	public Double getTaxamount() {
		return taxamount;
	}

	public void setTaxamount(Double taxamount) {
		this.taxamount = taxamount;
	}

	public Boolean getValidated() {
		return validated;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

	public Boolean getSentstatus() {
		return sentstatus;
	}

	public void setSentstatus(Boolean sentstatus) {
		this.sentstatus = sentstatus;
	}

}
