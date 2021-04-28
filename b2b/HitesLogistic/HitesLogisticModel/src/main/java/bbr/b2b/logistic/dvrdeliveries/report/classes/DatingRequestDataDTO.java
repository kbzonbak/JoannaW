package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DatingRequestDataDTO implements Serializable {

	private String requestername;
	private String email;
	private String phone;
	private LocalDateTime requestdate;
	private LocalDateTime when;
	private String needmodule;
	private Integer trucks;
	private Integer pallets;
	private Integer bulks;
	private Integer estimatedvolume;
	private Integer estimatedtime;
	private String comment;

	public String getRequestername() {
		return requestername;
	}

	public void setRequestername(String requestername) {
		this.requestername = requestername;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDateTime getRequestdate() {
		return requestdate;
	}

	public void setRequestdate(LocalDateTime requestdate) {
		this.requestdate = requestdate;
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public void setWhen(LocalDateTime when) {
		this.when = when;
	}

	public String getNeedmodule() {
		return needmodule;
	}

	public void setNeedmodule(String needmodule) {
		this.needmodule = needmodule;
	}

	public Integer getTrucks() {
		return trucks;
	}

	public void setTrucks(Integer trucks) {
		this.trucks = trucks;
	}

	public Integer getPallets() {
		return pallets;
	}

	public void setPallets(Integer pallets) {
		this.pallets = pallets;
	}

	public Integer getBulks() {
		return bulks;
	}

	public void setBulks(Integer bulks) {
		this.bulks = bulks;
	}

	public Integer getEstimatedvolume() {
		return estimatedvolume;
	}

	public void setEstimatedvolume(Integer estimatedvolume) {
		this.estimatedvolume = estimatedvolume;
	}

	public Integer getEstimatedtime() {
		return estimatedtime;
	}

	public void setEstimatedtime(Integer estimatedtime) {
		this.estimatedtime = estimatedtime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
