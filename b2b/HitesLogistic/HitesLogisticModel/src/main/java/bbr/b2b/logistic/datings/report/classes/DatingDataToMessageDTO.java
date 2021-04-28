package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DatingDataToMessageDTO implements Serializable {

	private Long dvrdeliveryid;
	private Long dvrdeliverynumber;
	private String deliverylocationcode;
	private String deliverylocationname;
	private String dockcode;
	private String action;
	private LocalDateTime reservestart;
	private Long reserveminutes;
	private Double estimatedunits;

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

	public String getDeliverylocationcode() {
		return deliverylocationcode;
	}

	public void setDeliverylocationcode(String deliverylocationcode) {
		this.deliverylocationcode = deliverylocationcode;
	}

	public String getDeliverylocationname() {
		return deliverylocationname;
	}

	public void setDeliverylocationname(String deliverylocationname) {
		this.deliverylocationname = deliverylocationname;
	}

	public String getDockcode() {
		return dockcode;
	}

	public void setDockcode(String dockcode) {
		this.dockcode = dockcode;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public LocalDateTime getReservestart() {
		return reservestart;
	}

	public void setReservestart(LocalDateTime reservestart) {
		this.reservestart = reservestart;
	}

	public Long getReserveminutes() {
		return reserveminutes;
	}

	public void setReserveminutes(Long reserveminutes) {
		this.reserveminutes = reserveminutes;
	}

	public Double getEstimatedunits() {
		return estimatedunits;
	}

	public void setEstimatedunits(Double estimatedunits) {
		this.estimatedunits = estimatedunits;
	}

}
