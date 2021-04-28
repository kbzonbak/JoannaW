package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class ReserveDetailDTO implements Serializable {

	private Long reserveid;
	private Long moduleid;
	private Long dockid;

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

}