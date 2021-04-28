package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVDeliveryTimesReportDataDTO implements Serializable {

	private String commune;

	private Integer time;

	private String communecode;

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getCommunecode() {
		return communecode;
	}

	public void setCommunecode(String communecode) {
		this.communecode = communecode;
	}

}
