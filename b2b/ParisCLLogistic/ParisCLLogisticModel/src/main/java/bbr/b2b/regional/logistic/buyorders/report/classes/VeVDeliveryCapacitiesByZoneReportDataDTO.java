package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVDeliveryCapacitiesByZoneReportDataDTO implements Serializable{

	private String zone;
	private Integer zoneid;
	private Integer monday;
	private Integer tuesday;
	private Integer wednesday;
	private Integer thursday;
	private Integer friday;
	private Integer saturday;
	private Integer sunday;
	
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public Integer getMonday() {
		return monday;
	}
	public void setMonday(Integer monday) {
		this.monday = monday;
	}
	public Integer getTuesday() {
		return tuesday;
	}
	public void setTuesday(Integer tuesday) {
		this.tuesday = tuesday;
	}
	public Integer getWednesday() {
		return wednesday;
	}
	public void setWednesday(Integer wednesday) {
		this.wednesday = wednesday;
	}
	public Integer getThursday() {
		return thursday;
	}
	public void setThursday(Integer thursday) {
		this.thursday = thursday;
	}
	public Integer getFriday() {
		return friday;
	}
	public void setFriday(Integer friday) {
		this.friday = friday;
	}
	public Integer getSaturday() {
		return saturday;
	}
	public void setSaturday(Integer saturday) {
		this.saturday = saturday;
	}
	public Integer getSunday() {
		return sunday;
	}
	public void setSunday(Integer sunday) {
		this.sunday = sunday;
	}
	public Integer getZoneid() {
		return zoneid;
	}
	public void setZoneid(Integer zoneid) {
		this.zoneid = zoneid;
	}	
}
