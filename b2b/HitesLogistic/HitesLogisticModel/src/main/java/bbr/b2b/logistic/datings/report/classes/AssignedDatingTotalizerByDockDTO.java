package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;

public class AssignedDatingTotalizerByDockDTO implements Serializable {

	private Long dockid;
	private Double units;
	private Double bulks;

	public Long getDockid() {
		return dockid;
	}

	public void setDockid(Long dockid) {
		this.dockid = dockid;
	}

	public Double getUnits() {
		return units;
	}

	public void setUnits(Double units) {
		this.units = units;
	}

	public Double getBulks() {
		return bulks;
	}

	public void setBulks(Double bulks) {
		this.bulks = bulks;
	}
	
}
