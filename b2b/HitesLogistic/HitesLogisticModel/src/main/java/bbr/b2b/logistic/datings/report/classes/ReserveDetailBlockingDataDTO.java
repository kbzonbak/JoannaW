package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReserveDetailBlockingDataDTO implements Serializable {

	private Long reservedetailid;
	private Long reserveid;
	private Long moduleid;
	private Long dockid;
	private String dockcode;
	private Integer dayofweek;
	private LocalDateTime reservestart;
	private LocalDateTime reserveend;

	public Long getReservedetailid() {
		return reservedetailid;
	}

	public void setReservedetailid(Long reservedetailid) {
		this.reservedetailid = reservedetailid;
	}

	public Long getReserveid() {
		return reserveid;
	}

	public void setReserveid(Long reserveid) {
		this.reserveid = reserveid;
	}

	public Long getModuleid() {
		return moduleid;
	}

	public void setModuleid(Long moduleid) {
		this.moduleid = moduleid;
	}

	public Long getDockid() {
		return dockid;
	}

	public void setDockid(Long dockid) {
		this.dockid = dockid;
	}

	public String getDockcode() {
		return dockcode;
	}

	public void setDockcode(String dockcode) {
		this.dockcode = dockcode;
	}

	public Integer getDayofweek() {
		return dayofweek;
	}

	public void setDayofweek(Integer dayofweek) {
		this.dayofweek = dayofweek;
	}

	public LocalDateTime getReservestart() {
		return reservestart;
	}

	public void setReservestart(LocalDateTime reservestart) {
		this.reservestart = reservestart;
	}

	public LocalDateTime getReserveend() {
		return reserveend;
	}

	public void setReserveend(LocalDateTime reserveend) {
		this.reserveend = reserveend;
	}

}
