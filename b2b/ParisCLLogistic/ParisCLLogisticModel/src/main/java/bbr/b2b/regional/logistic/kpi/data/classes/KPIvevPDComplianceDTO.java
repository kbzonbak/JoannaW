package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

public class KPIvevPDComplianceDTO implements Serializable {

	private Long totalreg;
	private Long totaldelivered;
	private Long totalreceiveddelayed;
	private Long totalcourierreceiveddelayed;
	private Long totalproviderdelayed;
	private Long totalcourierdelayed;
	private Long totaldelivering;
	private Long totallosts;
	private Long creditnotes;
	private Long inconsistencies;
	private Long customerresponsabilities;
	private Long totalcourierdeliveries;
	private Long totalevaluated;
	private Long total;
	
	public Long getTotalreg() {
		return totalreg;
	}
	public void setTotalreg(Long totalreg) {
		this.totalreg = totalreg;
	}
	public Long getTotaldelivered() {
		return totaldelivered;
	}
	public void setTotaldelivered(Long totaldelivered) {
		this.totaldelivered = totaldelivered;
	}
	public Long getTotalreceiveddelayed() {
		return totalreceiveddelayed;
	}
	public void setTotalreceiveddelayed(Long totalreceiveddelayed) {
		this.totalreceiveddelayed = totalreceiveddelayed;
	}
	public Long getTotalcourierreceiveddelayed() {
		return totalcourierreceiveddelayed;
	}
	public void setTotalcourierreceiveddelayed(Long totalcourierreceiveddelayed) {
		this.totalcourierreceiveddelayed = totalcourierreceiveddelayed;
	}
	public Long getTotalproviderdelayed() {
		return totalproviderdelayed;
	}
	public void setTotalproviderdelayed(Long totalproviderdelayed) {
		this.totalproviderdelayed = totalproviderdelayed;
	}
	public Long getTotalcourierdelayed() {
		return totalcourierdelayed;
	}
	public void setTotalcourierdelayed(Long totalcourierdelayed) {
		this.totalcourierdelayed = totalcourierdelayed;
	}
	public Long getTotaldelivering() {
		return totaldelivering;
	}
	public void setTotaldelivering(Long totaldelivering) {
		this.totaldelivering = totaldelivering;
	}
	public Long getTotallosts() {
		return totallosts;
	}
	public void setTotallosts(Long totallosts) {
		this.totallosts = totallosts;
	}
	public Long getCreditnotes() {
		return creditnotes;
	}
	public void setCreditnotes(Long creditnotes) {
		this.creditnotes = creditnotes;
	}
	public Long getInconsistencies() {
		return inconsistencies;
	}
	public void setInconsistencies(Long inconsistencies) {
		this.inconsistencies = inconsistencies;
	}
	public Long getCustomerresponsabilities() {
		return customerresponsabilities;
	}
	public void setCustomerresponsabilities(Long customerresponsabilities) {
		this.customerresponsabilities = customerresponsabilities;
	}
	public Long getTotalcourierdeliveries() {
		return totalcourierdeliveries;
	}
	public void setTotalcourierdeliveries(Long totalcourierdeliveries) {
		this.totalcourierdeliveries = totalcourierdeliveries;
	}
	public Long getTotalevaluated() {
		return totalevaluated;
	}
	public void setTotalevaluated(Long totalevaluated) {
		this.totalevaluated = totalevaluated;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
}
