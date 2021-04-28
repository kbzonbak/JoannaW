package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DvrDeliveryReportDataDTO implements Serializable {

	private Long dvrdeliveryid;
	private Long dvrdeliverynumber;
	private String dvrdeliverystatecode;
	private String dvrdeliverystate;
	private LocalDateTime dvrdeliverystatedate;
	private LocalDateTime datingdate;
	private String dvrdeliverylocationcode;
	private String dvrdeliverylocation;
	private Double availableunits;
	private String nextaction;

	public Long getDvrdeliveryid() {
		return dvrdeliveryid;
	}

	public void setDvrdeliveryid(Long dvrdeliveryid) {
		this.dvrdeliveryid = dvrdeliveryid;
	}

	public Long getDvrdeliverynumber() {
		return dvrdeliverynumber;
	}

	public void setDvrdeliverynumber(Long dvrdeliverynumber) {
		this.dvrdeliverynumber = dvrdeliverynumber;
	}

	public String getDvrdeliverystatecode() {
		return dvrdeliverystatecode;
	}

	public void setDvrdeliverystatecode(String dvrdeliverystatecode) {
		this.dvrdeliverystatecode = dvrdeliverystatecode;
	}

	public String getDvrdeliverystate() {
		return dvrdeliverystate;
	}

	public void setDvrdeliverystate(String dvrdeliverystate) {
		this.dvrdeliverystate = dvrdeliverystate;
	}

	public LocalDateTime getDvrdeliverystatedate() {
		return dvrdeliverystatedate;
	}

	public void setDvrdeliverystatedate(LocalDateTime dvrdeliverystatedate) {
		this.dvrdeliverystatedate = dvrdeliverystatedate;
	}

	public LocalDateTime getDatingdate() {
		return datingdate;
	}

	public void setDatingdate(LocalDateTime datingdate) {
		this.datingdate = datingdate;
	}

	public String getDvrdeliverylocationcode() {
		return dvrdeliverylocationcode;
	}

	public void setDvrdeliverylocationcode(String dvrdeliverylocationcode) {
		this.dvrdeliverylocationcode = dvrdeliverylocationcode;
	}

	public String getDvrdeliverylocation() {
		return dvrdeliverylocation;
	}

	public void setDvrdeliverylocation(String dvrdeliverylocation) {
		this.dvrdeliverylocation = dvrdeliverylocation;
	}

	public Double getAvailableunits() {
		return availableunits;
	}

	public void setAvailableunits(Double availableunits) {
		this.availableunits = availableunits;
	}

	public String getNextaction() {
		return nextaction;
	}

	public void setNextaction(String nextaction) {
		this.nextaction = nextaction;
	}

}
