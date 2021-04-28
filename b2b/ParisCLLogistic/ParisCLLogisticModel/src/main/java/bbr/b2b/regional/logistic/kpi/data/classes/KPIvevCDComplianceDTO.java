package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

public class KPIvevCDComplianceDTO implements Serializable {

	private Long totalreg;
	private Long totalreceivedconformed;
	private Long totalreceiveddelayed;
	private Long totalproviderdelayed;
	private Long creditnotes;
	private Long totalevaluated;
	private Long total;
	
	public Long getTotalreg() {
		return totalreg;
	}
	public void setTotalreg(Long totalreg) {
		this.totalreg = totalreg;
	}
	public Long getTotalreceivedconformed() {
		return totalreceivedconformed;
	}
	public void setTotalreceivedconformed(Long totalreceivedconformed) {
		this.totalreceivedconformed = totalreceivedconformed;
	}
	public Long getTotalreceiveddelayed() {
		return totalreceiveddelayed;
	}
	public void setTotalreceiveddelayed(Long totalreceiveddelayed) {
		this.totalreceiveddelayed = totalreceiveddelayed;
	}
	public Long getTotalproviderdelayed() {
		return totalproviderdelayed;
	}
	public void setTotalproviderdelayed(Long totalproviderdelayed) {
		this.totalproviderdelayed = totalproviderdelayed;
	}
	public Long getCreditnotes() {
		return creditnotes;
	}
	public void setCreditnotes(Long creditnotes) {
		this.creditnotes = creditnotes;
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
